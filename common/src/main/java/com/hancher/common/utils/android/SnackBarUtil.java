package com.hancher.common.utils.android;

import android.view.View;

import com.google.android.material.snackbar.Snackbar;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/4/16 9:53 <br/>
 * 描述 : 可交互弹窗
 */
public class SnackBarUtil {
    public static void show(View parentView, String message, String clickMessage, View.OnClickListener listener) {
        LogUtil.print(LogUtil.DEBUG, "SnackBar消息:" + message);
        Snackbar.make(parentView, message, Snackbar.LENGTH_LONG)
                .setAction(clickMessage, listener).show();
    }
}
