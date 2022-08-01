package com.hancher.common.utils.android;

import android.app.Activity;
import android.view.WindowManager;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/4/15 16:20 <br/>
 * 描述 : 屏幕常亮工具
 */
public class ScreenAlwaysLightUtil {

    public static void setOn(Activity activity) {
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    public static void setOff(Activity activity) {
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }
}
