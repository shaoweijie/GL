package com.hancher.common.utils.android;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/8/29 0029 0:45 <br/>
 * 描述 : 文件位置 <br/>
 * externalAppCacheDir          /storage/emulated/0/Android/data/com.hancher.gamelife/cache
 * externalAppCacheFilesDir     /storage/emulated/0/Android/data/com.hancher.gamelife/cache/files
 * externalAppFileDir           /storage/emulated/0/Android/data/com.hancher.gamelife/files
 * externalAppFileImageDir      /storage/emulated/0/Android/data/com.hancher.gamelife/files/image
 * externalAppBackupDir         /storage/emulated/0/Android/data/com.hancher.gamelife/files/backup
 * externalAppBackupDbDir       /storage/emulated/0/Android/data/com.hancher.gamelife/files/backup/db
 * externalAppBackupDb          /storage/emulated/0/Android/data/com.hancher.gamelife/files/backup/db/Hancher.db
 * innerDB                      /data/data/com.hancher.gamelife/databases/Hancher.db
 * innerDbDir                   /data/data/com.hancher.gamelife/databases
 * sdDownload                   /storage/emulated/0/Download                        @Deprecated
 * sdDownloadHancher            /storage/emulated/0/Download/Hancher                @Deprecated
 * sdDownloadHancherZip         /storage/emulated/0/Download/Hancher/backup.zip    @Deprecated
 * sdHancher                    /storage/emulated/0/Hancher                         @Deprecated
 * sdHancherBackupZip           /storage/emulated/0/Hancher/backup.zip             @Deprecated
 * sdHancherAppBackupDb         /storage/emulated/0/Hancher/backup/db/Hancher.db    @Deprecated
 */
public class PathUtil {
    public static String DB_NAME = "Hancher.db";

    public static String externalAppCacheDir;
    public static String externalAppCacheFilesDir;
    public static String externalAppFileDir;
    public static String externalAppFileImageDir;
    public static String externalAppBackupDir;
    public static String externalAppBackupDbDir;
    public static String externalAppBackupDb;
    public static String innerDB;
    public static String innerDbDir;
    public static String sdDownload;
    public static String sdDownloadHancher;
    public static String sdDownloadHancherZip;
    public static String sdHancher;
    public static String sdHancherBackupZip;
    public static String sdHancherAppBackupDb;

    public static void initFilePath(Context context) {
        if (context == null) {
            LogUtil.w("Context null");
            return;
        }
        if (externalAppCacheDir == null) {
            externalAppCacheDir = context.getExternalCacheDir().getAbsolutePath();
        }
        if (externalAppCacheFilesDir == null) {
            externalAppCacheFilesDir = context.getExternalCacheDir().getAbsolutePath()
                    + File.separator + "files";
        }
        if (externalAppFileDir == null) {
            externalAppFileDir = context.getExternalFilesDir("").getAbsolutePath();
        }
        if (externalAppFileImageDir == null) {
            externalAppFileImageDir = context.getExternalFilesDir("image").getAbsolutePath();
        }
        if (externalAppBackupDir == null) {
            externalAppBackupDir = context.getExternalFilesDir("backup").getAbsolutePath();
        }
        if (externalAppBackupDbDir == null) {
            externalAppBackupDbDir = context.getExternalFilesDir("backup").getAbsolutePath()
                    + File.separator + "db";
        }
        if (externalAppBackupDb == null) {
            externalAppBackupDb = context.getExternalFilesDir("backup").getAbsolutePath()
                    + File.separator + "db" + File.separator + DB_NAME;
        }
        if (innerDB == null) {
            innerDB = context.getDatabasePath(DB_NAME).getAbsolutePath();
        }
        if (innerDbDir == null){
            innerDbDir = context.getDatabasePath(DB_NAME).getParent();
        }
        if (sdDownload == null) {
            sdDownload = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
        }
        if (sdDownloadHancher == null) {
            sdDownloadHancher = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator + "Hancher";
        }
        if (sdDownloadHancherZip == null) {
            sdDownloadHancherZip = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator + "Hancher"
                    + File.separator + "backup.zip";
        }
        if (sdHancher == null) {
            sdHancher = Environment.getExternalStoragePublicDirectory("Hancher")
                    .getAbsolutePath();
        }
        if (sdHancherBackupZip == null) {
            sdHancherBackupZip = Environment.getExternalStoragePublicDirectory("Hancher")
                    .getAbsolutePath() + File.separator + "backup.zip";
        }
        if (sdHancherAppBackupDb == null){
            sdHancherAppBackupDb = Environment.getExternalStoragePublicDirectory("Hancher")
                    .getAbsolutePath()  + File.separator + "db" + File.separator + DB_NAME;
        }
    }
}
