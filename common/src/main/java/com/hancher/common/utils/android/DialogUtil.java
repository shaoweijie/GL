package com.hancher.common.utils.android;

import android.app.Activity;

import com.afollestad.materialdialogs.MaterialDialog;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/3/23 10:34 <br/>
 * 描述 : dialog工具
 */
public class DialogUtil {

    public static MaterialDialog.Builder build(Activity activity) {
        return new MaterialDialog.Builder(activity);
    }

    /**
     * 水平加载进度条
     * @param activity
     * @return
     */
    public static MaterialDialog.Builder progress(Activity activity) {
        return new MaterialDialog.Builder(activity)
                .progress(false, 100, true)
                .autoDismiss(false)
                .cancelable(false);
    }

    public static void err(Activity activity, Throwable throwable) {
        StackTraceElement[] targetElement = throwable.getStackTrace();
        StringBuffer str = new StringBuffer(throwable.toString());
        for (int i = 0; i < targetElement.length; i++) {

            String fileName = targetElement[i].getFileName();   //文件名
            String className = targetElement[i].getClassName(); //类名
            int lineNum = targetElement[i].getLineNumber();     //行数
            String methName = targetElement[i].getMethodName(); //方法名
            if (fileName == null) {
                if (className != null) {
                    fileName = className;
                } else {
                    fileName = " ";
                }
            }
            if (methName == null) {
                methName = " ";
            }
            str.append(fileName.substring(0, fileName.length() - 4))
                    .append(methName)
                    .append("(): ")
                    .append(" (")
                    .append(fileName)
                    .append(":")
                    .append(lineNum)
                    .append(")");
            if (i != targetElement.length - 1) {
                str.append("\n");
            }

        }

        LogUtil.print(LogUtil.ERROR, throwable.toString());
        new MaterialDialog.Builder(activity).title("错误").content(str)
                .positiveText("确认").onPositive((dialog, which) -> {
            dialog.dismiss();
        }).show();
    }
}
