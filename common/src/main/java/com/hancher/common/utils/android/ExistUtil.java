package com.hancher.common.utils.android;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/10/10 0010 1:57 <br/>
 * 描述 : 退出工具类
 */
public class ExistUtil {
    public static void kill(){
        android.os.Process.killProcess(android.os.Process.myPid());
    }
    public static void exist(){
        System.exit(0);
    }

    /**
     * @deprecated 不好用
     * @param context 上下文
     */
    public static void killByAm(Context context){
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        manager.killBackgroundProcesses(context.getPackageName());
    }

    /**
     * 重启
     * @param context
     */
    public static void restartApplication(Context context) {
        final Intent intent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }
}
