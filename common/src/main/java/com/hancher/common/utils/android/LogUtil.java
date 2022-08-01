package com.hancher.common.utils.android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;

import com.hancher.common.base.mvvm02.BaseApplication;
import com.hancher.common.utils.java.DateUtil;
import com.hancher.common.utils.java.TextUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

/**
 * 作者：Hancher
 * 时间：2019/1/12.
 * 邮箱：ytu_shaoweijie@163.com
 * 版本：v1.0
 * <point>
 * 说明：
 */
public class LogUtil {
    private static final String TAG = "HancherLogUtil";

    public final static boolean FORCE = true;

    public final static int VERBOSE = 0x1;
    public final static int INFO = 0x2;
    public final static int DEBUG = 0x4;
    public final static int WARNING = 0x8;
    public final static int ERROR = 0x16;
    public final static int SHOW = INFO | ERROR;

    private final static boolean WRITE_TO_FILE = false;
    private static String logFile = null;
    private static boolean initLogFile = false;

    /**
     * 打印调用逻辑，多行显示
     * @param message
     * @return
     */
    private static String formatAllMessage(String message) {
        StackTraceElement[] targetElement = Thread.currentThread().getStackTrace();
        StringBuffer str = new StringBuffer();
        for (int i = 0; i < targetElement.length; i++) {

            String fileName = targetElement[i].getFileName();   //文件名
            String className = targetElement[i].getClassName(); //类名
            int lineNum = targetElement[i].getLineNumber();     //行数
            String methName = targetElement[i].getMethodName(); //方法名

            str.append(fileName.substring(0, fileName.length() - 4))
                    .append(methName)
                    .append("(): ")
                    .append(message)
                    .append(" (")
                    .append(fileName)
                    .append(":")
                    .append(lineNum)
                    .append(")");
            if (i!=targetElement.length-1) {
                str.append("\n");
            }

        }
        return str.toString();
    }
    /**
     * 格式化文本，格式化成如下模样<br>
     * LogTestActivity.onCreate(): Hello World (LogTestActivity.java:12)
     * @param message
     * @return
     */
    private static String formatMessage(String message, int i) {
        /**
         * i=2 HancherLogUtil.formatMessage()
         * i=3 HancherLogUtil.print()
         * i=4 HancherLogUtil.v()
         * i=5 who call it
         */
        StackTraceElement[] targetElement = Thread.currentThread().getStackTrace();

        String fileName = targetElement[i].getFileName();   //文件名
        String className = targetElement[i].getClassName(); //类名
        int lineNum = targetElement[i].getLineNumber();     //行数
        String methName = targetElement[i].getMethodName(); //方法名

        //严格按(FileName:LineNuber)的格式来写 才可以定位，后面不能加东西的样子
//        String str =
//                fileName.substring(0, fileName.length() - 4) + methName + "(): " +
//                        message +
//                        " (" + fileName + ":" + lineNum + ")";
        String str2 = " (" + fileName + ":" + lineNum + ") "
                + methName + "(): "
                + message;
        return str2;
    }
    static void print(int type,Object... messages){
        StringBuffer message = new StringBuffer();
        if ((messages[0] instanceof String) && ((String)messages[0]).contains("%")){
            Object[] messagesTmp = Arrays.copyOfRange(messages, 1, messages.length);
            message.append(String.format((String) messages[0], messagesTmp));
        } else {
            for (Object item : messages) {
                message.append(item);
            }
        }
        if (FORCE || (SHOW & type) != 0){
            String formatMessage = formatMessage(message.toString(),5);
            switch (type){
                case INFO:Log.i(TAG, formatMessage);break;
                case DEBUG:Log.d(TAG, formatMessage);break;
                case WARNING:Log.w(TAG, formatMessage);break;
                case ERROR:Log.e(TAG, formatMessage);break;
                default: Log.v(TAG, formatMessage);break;
            }
            writeToFile(formatMessage);
        }
    }

    public static void v(Object... message) {
        if (ApkInfoUtil.isApkInDebug(BaseApplication.getInstance())) {
            print(VERBOSE, message);
        }
    }
    public static void i(){
        print(INFO,"");
    }
    public static void i(Object... message) {
        print(INFO,message);
    }
    public static void d() {
        if (ApkInfoUtil.isApkInDebug(BaseApplication.getInstance())) {
            print(DEBUG, "");
        }
    }
    public static void d(Object... message) {
        if (ApkInfoUtil.isApkInDebug(BaseApplication.getInstance())) {
            print(DEBUG, message);
        }
    }
    public static void w(Object... message) {
        print(WARNING,message);
    }
    public static void e(Throwable e){
        print(ERROR,e);
        for (int i = 0; i < e.getStackTrace().length; i++) {
            print(ERROR,e.getStackTrace()[i]+" ");
        }
    }
    public static void e(Object... message) {
        print(ERROR, message);
    }

    public static void e(String message, Exception e) {
        StringBuffer err = new StringBuffer(message);
        StackTraceElement[] errTrace = e.getStackTrace();
        for (StackTraceElement item : errTrace) {
            err.append("\n").append(item.toString());
        }
        print(ERROR, err.toString());
    }

    public static void intent(Intent intent) {
        if (intent == null) {
            print(DEBUG, "intent null");
            return;
        }
        StringBuffer message = new StringBuffer("intent:");
        message.append("\nflag=").append(intent.getFlags());

        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            message.append("\nbundle=[");
            int i = 0;
            for (String key : bundle.keySet()) {
                message.append(key).append(":").append(bundle.getString(key));
                i++;
                if (i != bundle.size()) {
                    message.append(",");
                }
            }
            message.append("]");
        }
        print(DEBUG, message.toString());
    }

    public static void point(String message) {
        if (FORCE || (SHOW & DEBUG) != 0) {
            String formatMessage = formatAllMessage(message);
            Log.d(TAG, formatMessage);
            writeToFile(formatMessage);
        }
    }

    public static String json(String json) {
        if (FORCE || (SHOW & DEBUG) != 0) {
            String formatMessage = formatAllMessage(jsonFormart(json));
            Log.d(TAG, formatMessage);
            writeToFile(formatMessage);
            return formatMessage;
        }
        return null;
    }
    private static String getLevelStr(int level) {
        StringBuffer levelStr = new StringBuffer();
        for (int levelI = 0; levelI < level; levelI++) {
            levelStr.append("\t");
        }
        return levelStr.toString();
    }
    public static String jsonFormart(String s) {
        int level = 0;
        //存放格式化的json字符串
        StringBuffer jsonForMatStr = new StringBuffer();
        for(int index=0;index<s.length();index++)//将字符串中的字符逐个按行输出
        {
            //获取s中的每个字符
            char c = s.charAt(index);
//          System.out.println(s.charAt(index));

            //level大于0并且jsonForMatStr中的最后一个字符为\n,jsonForMatStr加入\t
            if (level > 0 && '\n' == jsonForMatStr.charAt(jsonForMatStr.length() - 1)) {
                jsonForMatStr.append(getLevelStr(level));
//                System.out.println("123"+jsonForMatStr);
            }
            //遇到"{"和"["要增加空格和换行，遇到"}"和"]"要减少空格，以对应，遇到","要换行
            switch (c) {
                case '{':
                case '[':
                    jsonForMatStr.append(c + "\n");
                    level++;
                    break;
                case ',':
                    jsonForMatStr.append(c + "\n");
                    break;
                case '}':
                case ']':
                    jsonForMatStr.append("\n");
                    level--;
                    jsonForMatStr.append(getLevelStr(level));
                    jsonForMatStr.append(c);
                    break;
                default:
                    jsonForMatStr.append(c);
                    break;
            }
        }
        return jsonForMatStr.toString();
    }
    public static void showDialog(Context context, String message){
        if (FORCE||(SHOW & DEBUG)!=0){
            print(DEBUG,message);
            AlertDialog.Builder builder = new AlertDialog.Builder(context).setMessage(message)
                    .setPositiveButton("确定", (dialog, which) -> dialog.dismiss());
            builder.create().show();
        }
    }

    /**
     * 初始化文件
     * @param context
     */
    public static void initWriteToFile(Context context){
        if (!WRITE_TO_FILE){
            return;
        }
        if (TextUtils.isEmpty(logFile)){
            if (context == null){
                e("还没初始化就使用文件输出无法继续进行");
                return;
            }
            String parentDir = context.getExternalFilesDir("log").getAbsolutePath();
            File file = new File(parentDir);
            if (!file.exists()){
                file.mkdirs();
            }
            logFile = parentDir + File.separator +"hancher.log";
        }
        initLogFile = true;
        i("初始化成功，开始记录日志：");
    }
    /**
     * 需要先调用initWriteToFile
     * @param message
     */
    private static void writeToFile(String message){
        if (!WRITE_TO_FILE || !initLogFile){
            return;
        }

        FileWriter writer = null;
        try {
            writer = new FileWriter(logFile, true);
            writer.write(DateUtil.getNow(DateUtil.Type.STRING_YMD_HMS) + " " + message + "\n");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null ){
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    e("关闭输出失败e="+e);
                }
            }
        }
    }

    /**
     * 利用反射遍历对象所有属性，速度极慢，但内容很全
     * @param o
     * @return
     */
    public static StringBuilder o(Object o) {
        StringBuilder log = new StringBuilder();
        String[] fieldNames = TextUtil.getFiledName(o);
        for (int j = 0; j < fieldNames.length; j++) {     //遍历所有属性
            String name = fieldNames[j];    //获取属性的名字
            Object value = TextUtil.getFieldValueByName(name, o);
            if (value == null) continue;
            log.append(name);
            log.append("=");
            log.append(value);
            if (j != fieldNames.length - 1)
                log.append(", ");
        }
        print(DEBUG,log.toString());
        return log;
    }

}
