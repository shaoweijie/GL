package com.hancher.common.utils.android;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import com.hancher.common.base.mvvm02.BaseApplication;
import com.tencent.mmkv.MMKV;

import java.util.List;

public class ApkInfoUtil {

    public static void collect(){
        LogUtil.d("start");
        ApplicationInfo info = BaseApplication.getInstance().getApplicationInfo();
        MMKV.defaultMMKV().encode("FLAG_DEBUGGABLE", ((info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0));
        MMKV.defaultMMKV().encode("FLAG_SYSTEM", ((info.flags & ApplicationInfo.FLAG_SYSTEM) != 0));


        ActivityManager am = (ActivityManager) BaseApplication.getInstance().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == android.os.Process.myPid()) {
                MMKV.defaultMMKV().encode("CURRENT_PROCESS_NAME", procInfo.processName);
            }
        }

        MMKV.defaultMMKV().encode("CURRENT_PACKAGE_NAME", BaseApplication.getInstance().getPackageName());

        PackageManager packageManager = BaseApplication.getInstance().getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(BaseApplication.getInstance().getPackageName(), 0);
            MMKV.defaultMMKV().encode("VERSION_CODE", Build.VERSION.SDK_INT >= Build.VERSION_CODES.P ?
                            packageInfo.getLongVersionCode(): packageInfo.versionCode);
            MMKV.defaultMMKV().encode("VERSION_NAME", packageInfo.packageName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        LogUtil.d("end");
    }
    /**
     * 判断当前应用是否是debug状态
     * @param context
     * @return
     */
    public static boolean isApkInDebug(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取当前进程名
     * @return com.hancher.gamelife
     */
    public static String getProcessName(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == android.os.Process.myPid()) {
                return procInfo.processName;
            }
        }
        return null;
    }
    /**
     * 获取当前进程名
     * @return com.hancher.gamelife
     */
    public static String getProcessName() {
        return getProcessName(BaseApplication.getInstance());
    }

    /**
     * 获取当前版本名
     *
     * @return 0.16.9
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取当前版本号
     * @return 82, 失败返回-1
     */
    public static long getVersionCode(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                return packageInfo.getLongVersionCode();
            } else {
                return packageInfo.versionCode;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
}
