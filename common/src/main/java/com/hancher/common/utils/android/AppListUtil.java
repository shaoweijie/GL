package com.hancher.common.utils.android;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：Hancher
 * 时间：2019/2/13.
 * 邮箱：ytu_shaoweijie@163.com
 * 版本：v1.0
 * <p>
 * 说明：
 */
public class AppListUtil {
    public static ArrayList<AppBean> getLauncherAppInfo(PackageManager packageManager) {
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> resolveinfoList = packageManager.queryIntentActivities(resolveIntent, PackageManager.MATCH_ALL);
        ArrayList<AppBean> list = new ArrayList<AppBean>();
        for (ResolveInfo resolveinfoItem :
                resolveinfoList) {
            AppBean item= new AppBean()
                    .setName(resolveinfoItem.activityInfo.loadLabel(packageManager))
                    .setEnabled(resolveinfoItem.activityInfo.isEnabled())
                    .setPackageName(resolveinfoItem.activityInfo.packageName)
                    .setSystemApp((resolveinfoItem.activityInfo.applicationInfo.flags
                            & ApplicationInfo.FLAG_SYSTEM) == 1)
                    .setSystemUpdateApp((resolveinfoItem.activityInfo.applicationInfo.flags
                            & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) == 1)
                    .setInSd((resolveinfoItem.activityInfo.applicationInfo.flags
                            & ApplicationInfo.FLAG_EXTERNAL_STORAGE) ==1)
                    .setLauncherActivity(resolveinfoItem.activityInfo.targetActivity)
                    .setIcon(resolveinfoItem.activityInfo.loadIcon(packageManager))
                    ;
            list.add(item);
//            HancherLogUtil.d( "name="+item.getName()
//                    +", packageName="+item.getPackageName()
//                    +", isEnabled="+item.isEnabled()
//                    +", isSystemApp="+item.isSystemApp()
//                    +", isSystemUpdateApp="+item.isSystemUpdateApp()
//                    +", isInSd="+item.isInSd()
//                    +", launcherActivity="+item.getLauncherActivity()
//                    +", loadIcon="+item.getIcon()
//            );
        }
        return list;
    }
    public static ArrayList<AppBean> getPackageAppInfo(PackageManager packageManager){
        ArrayList<AppBean> list = new ArrayList<AppBean>();
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        for (PackageInfo packageinfoItem :
                packageInfos) {
            AppBean item = new AppBean()
                    .setPackageName(packageinfoItem.applicationInfo.packageName)
                    .setName(packageinfoItem.applicationInfo.loadLabel(packageManager))
                    .setEnabled(packageinfoItem.applicationInfo.enabled)
                    .setIcon(packageinfoItem.applicationInfo.loadIcon(packageManager));
            list.add(item);

//            LogUtil.d( "name="+item.getName()
//                            +", packageName="+item.getPackageName()
//                            +", isEnabled="+item.isEnabled()
////                    +", isSystemApp="+item.isSystemApp()
////                    +", isSystemUpdateApp="+item.isSystemUpdateApp()
////                    +", isInSd="+item.isInSd()
////                    +", launcherActivity="+item.getLauncherActivity()
//                            +", loadIcon="+item.getIcon()
//            );
        }
        LogUtil.i("package size=%d", list.size());
        return list;
    }
    public static ArrayList<AppBean> getDisableAppInfo(PackageManager packageManager){
        ArrayList<AppBean> list = new ArrayList<AppBean>();
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        for (PackageInfo packageinfoItem :
                packageInfos) {
            if (packageinfoItem.applicationInfo.enabled) continue;
            AppBean item = new AppBean()
                    .setPackageName(packageinfoItem.applicationInfo.packageName)
                    .setName(packageinfoItem.applicationInfo.loadLabel(packageManager))
                    .setEnabled(packageinfoItem.applicationInfo.enabled)
                    .setIcon(packageinfoItem.applicationInfo.loadIcon(packageManager));
            list.add(item);

            LogUtil.d( "name="+item.getName()
                            +", packageName="+item.getPackageName()
                            +", isEnabled="+item.isEnabled()
//                    +", isSystemApp="+item.isSystemApp()
//                    +", isSystemUpdateApp="+item.isSystemUpdateApp()
//                    +", isInSd="+item.isInSd()
//                    +", launcherActivity="+item.getLauncherActivity()
                            +", loadIcon="+item.getIcon()
            );
        }
        return list;
    }
    public static class AppBean {
        private String launcherActivity;
        private boolean isEnabled;
        private Drawable icon;
        private CharSequence name;
        private String packageName;
        private boolean isSystemApp;
        private boolean isSystemUpdateApp;
        private boolean isInSd;

        public Drawable getIcon() {
            return icon;
        }

        public AppBean setIcon(Drawable icon) {
            this.icon = icon;
            return this;
        }

        public CharSequence getName() {
            return name;
        }

        public AppBean setName(CharSequence name) {
            this.name = name;
            return this;
        }

        public String getPackageName() {
            return packageName;
        }

        public AppBean setPackageName(String packageName) {
            this.packageName = packageName;
            return this;
        }

        public boolean isSystemApp() {
            return isSystemApp;
        }

        public AppBean setSystemApp(boolean systemApp) {
            isSystemApp = systemApp;
            return this;
        }

        public boolean isSystemUpdateApp() {
            return isSystemUpdateApp;
        }

        public AppBean setSystemUpdateApp(boolean systemUpdateApp) {
            isSystemUpdateApp = systemUpdateApp;
            return this;
        }

        public boolean isInSd() {
            return isInSd;
        }

        public AppBean setInSd(boolean inSd) {
            isInSd = inSd;
            return this;
        }

        public String getLauncherActivity() {
            return launcherActivity;
        }

        public AppBean setLauncherActivity(String launcherActivity) {
            this.launcherActivity = launcherActivity;
            return this;
        }

        public boolean isEnabled() {
            return isEnabled;
        }

        public AppBean setEnabled(boolean enabled) {
            isEnabled = enabled;
            return this;
        }
    }
}
