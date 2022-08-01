package com.hancher.common.utils.android;

import android.content.Context;
import android.util.DisplayMetrics;

import com.hancher.common.base.mvvm02.BaseApplication;
import com.tencent.mmkv.MMKV;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/4/2 13:46 <br/>
 * 描述 : dp px sp转换工具 <br/>
 * px 像素点 <br/>
 * dp 单位英寸像素点 <br/>
 * sp 单位英寸单位比例像素点 <br/>
 */
public class DensityUtil {
    public static void collect(){
        DisplayMetrics displayMetrics = BaseApplication.getInstance().getResources().getDisplayMetrics();
        MMKV.defaultMMKV().encode("DENSITY", displayMetrics.density);
        MMKV.defaultMMKV().encode("DENSITY_DPI", displayMetrics.densityDpi);
        MMKV.defaultMMKV().encode("WIDTH_PIXELS", displayMetrics.widthPixels);
        MMKV.defaultMMKV().encode("HEIGHT_PIXELS", displayMetrics.heightPixels);
    }
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
