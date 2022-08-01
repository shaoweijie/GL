package com.hancher.common.utils.android;

import android.graphics.Color;
import android.widget.Toast;

import com.hancher.common.base.mvvm02.BaseApplication;
import com.muddzdev.styleabletoast.StyleableToast;

/**
 * 作者: Hancher
 * 日期: 2020/9/6 12:09
 * 描述: Toast工具
 * https://github.com/Muddz/StyleableToast
 * Android11 后台服务不能使用自定义的Toast
 */
public class ToastUtil {
    private static final int COLOR_BLUE = 0;
    private static final int COLOR_RED = 1;

    private static void showToastInternal(String message, int color) {
        StyleableToast.Builder builder = new StyleableToast.Builder(BaseApplication.getInstance().getApplicationContext())
                .text(message);
        switch (color) {
            case COLOR_BLUE:
                builder.textColor(Color.parseColor("#6063b2"))
                        .stroke(2,Color.parseColor("#989ad1"))
                        .backgroundColor(Color.WHITE)
                        .length(Toast.LENGTH_SHORT);
                break;
            case COLOR_RED:
                builder.textColor(Color.parseColor("#FF5A5F"))
                        .stroke(2,Color.parseColor("#FF5A5F"))
                        .backgroundColor(Color.WHITE)
                        .textSize(10)
                        .length(Toast.LENGTH_LONG);
                break;
        }
        builder.build().show();
    }

    public static void showErr(Throwable throwable) {

        StackTraceElement[] st = throwable.getStackTrace();
        StringBuffer messageBuffer = new StringBuffer(throwable.getMessage()).append("\n");
        for (StackTraceElement item:st){
            messageBuffer.append(item.toString()).append("\n");
        }
        String message = messageBuffer.toString();
        LogUtil.print(LogUtil.ERROR, "红色异常Toast:" + message);
        showToastInternal(message, COLOR_RED);
    }

    public static void showErr(String message) {
        LogUtil.print(LogUtil.ERROR, "红色异常Toast:" + message);
        showToastInternal(message, COLOR_RED);
    }

    public static void show(int msg) {
        String message = BaseApplication.getInstance().getResources().getString(msg);
        LogUtil.print(LogUtil.DEBUG, "蓝色Toast:" + message);
        showToastInternal(message, COLOR_BLUE);
    }

    public static void show(String message) {
        LogUtil.print(LogUtil.DEBUG, "蓝色Toast:" + message);
        showToastInternal(message, COLOR_BLUE);
    }
}
