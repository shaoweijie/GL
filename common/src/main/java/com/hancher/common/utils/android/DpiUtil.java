package com.hancher.common.utils.android;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class DpiUtil {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     * @param context
     * @param dpValue dp大小
     * @return px大小
     */
    public static int dip2px(Context context, float dpValue) {
        // 获取当前手机的像素密度
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f); // 四舍五入取整
    }

    //

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     * @param context
     * @param pxValue px大小
     * @return dp大小
     */
    public static int px2dip(Context context, float pxValue) {
        // 获取当前手机的像素密度
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f); // 四舍五入取整
    }

    /**
     * 获取手机分辨率
     * @param context
     * @return
     */
    public static DisplayMetrics getWindowSize(Context context){
        // 获取屏幕分辨率
        WindowManager wm = (WindowManager) (context.getSystemService(Context.WINDOW_SERVICE));
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm;
    }
}
