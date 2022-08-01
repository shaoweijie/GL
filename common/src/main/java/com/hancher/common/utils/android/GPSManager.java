package com.hancher.common.utils.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.Settings;

/**
 * 作者：Hancher
 * 时间：2019/4/13.
 * 邮箱：ytu_shaoweijie@163.com
 * 版本：v1.0
 * <point>
 * 说明：
 */
public class GPSManager {
    public static void startListener() {

    }

    /* 检查gps状态并引导用户打开gps */
    /**
     * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
     * @param context
     * @return true 表示开启
     */
    public static final boolean isOpen(final Context context) {
        LocationManager locationManager =
                (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            LogUtil.d("GPS="+gps+", AGPS="+network);
            return true;
        }

        return false;
    }


    /**
     * 判断当前是否打开GPS，如果没有打开则弹出对话框，跳转到设置手动打开GPS。
     * 跳转回Activity，返回requestCode值的结果
     * @param activity
     * @param requestCode
     * @return
     */
    public void openGpsBySetting(final Activity activity, final int requestCode) {
        AlertDialog.Builder da = new AlertDialog.Builder(activity);
        da.setTitle("提示：");
        da.setMessage("为了更好的为您服务，请您打开您的GPS!");
        da.setCancelable(false);
        //设置左边按钮监听
        da.setNeutralButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

                // 转到手机设置界面，用户设置GPS
                Intent intent = new Intent(
                        Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                activity.startActivityForResult(intent, requestCode); // 设置完成后返回到原来的界面
            }
        });
        //设置右边按钮监听
        da.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                arg0.dismiss();
            }
        });
        da.show();
    }

    /**
     * 强制帮用户打开GPS
     *     <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
     *     <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
     *     <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
     *     <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
     *     <uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
     *     <uses-permission android:name="android.permission.INTERNET" />
     * @param context
     */
    public static final void openGpsBySelf(Context context) {
        Intent GPSIntent = new Intent();
        GPSIntent.setClassName("com.android.settings",
                "com.android.settings.widget.SettingsAppWidgetProvider");
        GPSIntent.addCategory("android.intent.category.ALTERNATIVE");
        GPSIntent.setData(Uri.parse("custom:3"));
        try {
            PendingIntent.getBroadcast(context, 0, GPSIntent, 0).send();
        } catch (PendingIntent.CanceledException e) {
            LogUtil.d("强制打开GPS错误："+e);
        }
    }

//    /**
//     * 判断当前是否打开GPS，如果没有打开则弹出对话框，跳转到设置手动打开GPS。
//     * 跳转回Activity，返回requestCode值的结果
//     * @param activity
//     * @param requestCode
//     * @return
//     */
//    public boolean isGPSAvaible(final Activity activity, final int requestCode) {
//        LocationManager locationManager = (LocationManager) activity
//                .getSystemService(Context.LOCATION_SERVICE);
//        // 判断GPS模块是否开启，如果没有则跳转至设置开启界面，设置完毕后返回到当前页面
//        if (!locationManager
//                .isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
//            AlertDialog.Builder da = new AlertDialog.Builder(activity);
//            da.setTitle("提示：");
//            da.setMessage("为了更好的为您服务，请您打开您的GPS!");
//            da.setCancelable(false);
//            //设置左边按钮监听
//            da.setNeutralButton("确定",
//                    new android.content.DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface arg0, int arg1) {
//
//                            // 转到手机设置界面，用户设置GPS
//                            Intent intent = new Intent(
//                                    Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                            activity.startActivityForResult(intent, requestCode); // 设置完成后返回到原来的界面
//                        }
//                    });
//            //设置右边按钮监听
//            da.setPositiveButton("取消",
//                    new android.content.DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface arg0, int arg1) {
//                            arg0.dismiss();
//                        }
//                    });
//            da.show();
//        } else {
//            return true;
//        }
//        return false;
//    }
//
//    /**
//     * @see #isGPSAvaible(Activity, int)
//     * @param activity
//     * @return
//     */
//    public boolean isGPSAvaible(final Context activity) {
//        LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
//        // 判断GPS模块是否开启，如果没有则跳转至设置开启界面，设置完毕后返回到当前页面
//        if (!locationManager
//                .isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
//            AlertDialog.Builder da = new AlertDialog.Builder(activity);
//            da.setTitle("提示：");
//            da.setMessage("为了更好的为您服务，请您打开您的GPS!");
//            da.setCancelable(false);
//            //设置左边按钮监听
//            da.setNeutralButton("确定", new android.content.DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface arg0, int arg1) {
//
//                            // 转到手机设置界面，用户设置GPS
//                            Intent intent = new Intent(
//                                    Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                            activity.startActivity(intent); // 设置完成后返回到原来的界面
//                        }
//                    });
//            //设置右边按钮监听
//            da.setPositiveButton("取消", new android.content.DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface arg0, int arg1) {
//                            arg0.dismiss();
//                        }
//                    });
//            da.show();
//        } else {
//            return true;
//        }
//        return false;
//    }
}
