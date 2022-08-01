package com.hancher.common.utils.endurance;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hancher.common.utils.android.LogUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 作者：Hancher
 * 时间：2019/1/25.
 * 邮箱：ytu_shaoweijie@163.com
 * 版本：v1.0
 * <point>
 * 说明：sqlite 数据库工具
 */
public class DBUtil {

    public static boolean restoreDb(String innerDbStr, String extDbStr){
        try {
            File innerDbFile = new File(innerDbStr);
            File extDbFile = new File(extDbStr);
            if (!innerDbFile.exists() || !extDbFile.exists()) {
                LogUtil.w("恢复或备份的数据库文件不存在！");
                return false;
            }

            SQLiteDatabase innerDb = SQLiteDatabase.openOrCreateDatabase(innerDbFile, null);
            SQLiteDatabase extDb = SQLiteDatabase.openOrCreateDatabase(extDbFile, null);
            Cursor oldTables = getTables(innerDb);
            if (oldTables.moveToFirst()) {
                do {
                    String table = oldTables.getString(0);
                    if (isSystemTable(table)){
                        continue;
                    }
                    restoreTable(innerDb,extDb,table);
                    LogUtil.v("恢复表"+table+"结束");
                } while (oldTables.moveToNext());
            }
        } catch (Exception e){
            LogUtil.e(e);
            return false;
        }
        return true;
    }

    private static boolean restoreTable(SQLiteDatabase innerDb, SQLiteDatabase extDb, String table){
        Cursor innerCursor = null, extCursor = null;
        try {
            innerCursor = innerDb.query(table, null, null, null, null, null, null);
            extCursor = extDb.query(table, null, null, null, null, null, null);
        } catch (Exception e) {
            LogUtil.w("inner or ext do not have table:", table);
            return false;
        }
        if (extCursor.moveToFirst()) {
            int innerUuidCol = innerCursor.getColumnIndex("UUID");
            int extUuidCol = extCursor.getColumnIndex("UUID");
            int innerUpdateCol = innerCursor.getColumnIndex("UPDATETIME");
            int extUpdateCol = extCursor.getColumnIndex("UPDATETIME");
            LogUtil.v("innerUuidCol:", innerUuidCol, ", newUuidCol:", extUuidCol, ", oldUpdateCol", innerUpdateCol, ", newUpdateCol", extUpdateCol);
            if (innerUuidCol == -1 || extUuidCol == -1 || innerUpdateCol == -1 || extUpdateCol == -1) {
                LogUtil.w("恢复表", table, "不存在 uuid 或者 updatetime 字段，放弃恢复");
                return false;
            }
            try {
                innerDb.beginTransaction();//开始事务机制
                int updateRow=0,insertRow=0;
                do {
                    String extUuidValue = extCursor.getString(extUuidCol);
                    if (moveToUuidRow(innerCursor, innerUuidCol, extUuidValue)) {
                        updateRow+=updateRestoreRow(innerCursor, extCursor,innerDb,table,innerUpdateCol,extUpdateCol,extUuidValue);
                    } else {
                        insertRow+=insertRestoreRow(extCursor, innerDb,table);
                    }
                } while (extCursor.moveToNext());
                LogUtil.v("update:",updateRow,", insert:",insertRow);
                innerDb.setTransactionSuccessful();//事务已经执行成功
            } catch (Exception e){
                LogUtil.e(e);
            } finally {
                innerDb.endTransaction();//结束事务
            }
        } else {
            LogUtil.w(table,"表移动到第一行失败");
        }
        return true;
    }

    private static int updateRestoreRow(Cursor innerCursor, Cursor extCursor, SQLiteDatabase innerDb, String table,
                                         int innerUpdateCol, int extUpdateCol, String uuidValue) {
        try {
            long innerUpdateTime = innerCursor.getLong(innerUpdateCol);
            long extUpdateTime = extCursor.getLong(extUpdateCol);
            if (innerUpdateTime < extUpdateTime){
                ContentValues cv = new ContentValues();
                for (int i = 0; i < extCursor.getColumnCount(); i++) {
                    cv.put(extCursor.getColumnName(i),extCursor.getString(i));
                }
                return innerDb.update(table,cv,"UUID=?",new String[]{uuidValue});
            }
        } catch (Exception e) {
            LogUtil.e(e);
            return 0;
        }
        return 0;
    }

    private static int insertRestoreRow(Cursor extCursor, SQLiteDatabase innerDb, String table) {
        ContentValues cv = new ContentValues();
        for (int i = 0; i < extCursor.getColumnCount(); i++) {
            if ("_id".equals(extCursor.getColumnName(i))){
                continue;
            }
            cv.put(extCursor.getColumnName(i),extCursor.getString(i));
        }
        if (innerDb.insert(table,null,cv)>0){
            return 1;
        }
        return 0;
    }

    private static boolean moveToUuidRow(Cursor innerCursor, int innerUuidCol, String extUuidValue) {
        if (innerCursor.moveToFirst()){
            do {
                if (extUuidValue.equals(innerCursor.getString(innerUuidCol))){
//                    LogUtil.v("找到相同的uuid:",extUuidValue);
                    return true;
                }
            } while (innerCursor.moveToNext());
        }
        return false;
    }

    /**
     * 查询某张表中的全部数据
     * @param externalDbPath
     * @param table
     * @return
     */
    public static Cursor selectAllFromExternal(String externalDbPath, String table){
        LogUtil.d("externalDbPath="+externalDbPath+", table="+table);
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(externalDbPath, null);
        Cursor cursor = db.query (table,null,null,null,null,null,null);
        printCursorInfo(cursor);
        return cursor;
    }
    /**
     * 获取当前数据库所有表
     * @param db
     * @return android_metadata 与 sqlite_sequence 为系统的表，不能操作
     */
    public static Cursor getTables(SQLiteDatabase db){
        return db.rawQuery("select name from sqlite_master where type='table' order by name", null);
    }

    /**
     * android_metadata sqlite_sequence 为 sqlite 的系统表
     * @param table
     * @return
     */
    public static boolean isSystemTable(String table){
        return "android_metadata".equals(table) || "sqlite_sequence".equals(table);
    }

    /**
     * 将cursor转换成list/map的形式，方便ListView使用
     * @param cursor
     * @return
     */
    public static ArrayList<Map<String, String>> changeCursorToListMap(Cursor cursor){
        if (cursor == null || !cursor.moveToFirst())
            return null;
        int column_count = cursor.getColumnCount();
        ArrayList<Map<String, String>> rowsData = new ArrayList<>();
        ArrayList<String> keys = new ArrayList<>();
        for (int i = 0; i < column_count; i++)
            keys.add(cursor.getColumnName(i));
        do {
            for (int i = 0; i < column_count; i++) {
                String value = cursor.getString(i);
                Map<String, String> oneRowData = new HashMap<>();
                oneRowData.put(keys.get(i),value);
                rowsData.add(oneRowData);
            }
        } while (cursor.moveToNext());
        return rowsData;
    }

    /**
     * 打印cursor
     * @param cursor
     */
    public static void printCursorInfo(Cursor cursor) {
        LogUtil.d("start====================================================");
        if (cursor == null) {
            LogUtil.d("cursor is null");
            LogUtil.d("end======================================================");
            return;
        }
        ArrayList<String> list = new ArrayList<String>();
        int column_count = cursor.getColumnCount();
        int row_count = cursor.getCount();
        LogUtil.d("cursor row=" + row_count + ", column=" + column_count);
        for (int i = 0; i < column_count; i++) {
            list.add(cursor.getColumnName(i));
        }
        if (cursor.moveToFirst()) {
            do {
                StringBuffer info = new StringBuffer("cursor=[ ");
                for (int i = 0; i < column_count; i++) {
                    info.append(list.get(i));
                    info.append("=");
                    info.append(cursor.getString(i));
                    switch (cursor.getType(i)) {
                        case Cursor.FIELD_TYPE_INTEGER:
                            info.append("(integer)");
                            break;
                        case Cursor.FIELD_TYPE_BLOB:
                            info.append("(byte)");
                            break;
                        case Cursor.FIELD_TYPE_FLOAT:
                            info.append("(float)");
                            break;
                        case Cursor.FIELD_TYPE_STRING:
                            info.append("(string)");
                            break;
                        case Cursor.FIELD_TYPE_NULL:
                            break;
                        default:
                            break;
                    }
                    if (column_count - i != 1) {
                        info.append(", ");
                    }
                }
                info.append(" ]");
                LogUtil.d(info.toString());
            } while (cursor.moveToNext());
        }
        LogUtil.d("end======================================================");
    }
}
