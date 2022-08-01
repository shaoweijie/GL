package com.hancher.common.utils.android;

import android.app.Activity;
import android.view.View;

import com.hancher.common.R;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/10/9 0009 23:07 <br/>
 * 描述 : 状态栏工具
 */
public class StateBarUtil {
    public static void white(Activity activity) {
        //将状态栏修改为白色
        activity.getWindow().setBackgroundDrawableResource(R.color.background);
        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        try {
            activity.getWindow().setStatusBarColor(activity.getResources().getColor(R.color.background));
        } catch (Exception e) {
            LogUtil.e("change status bar color err:", e);
        }
    }
}
