package com.hancher.common.utils.android;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.appcompat.app.AlertDialog;

/**
 * 作者：Hancher
 * 时间：2019/4/12.
 * 邮箱：ytu_shaoweijie@163.com
 * 版本：v1.0
 * <point>
 * 说明：
 */
public class PermissionUtil {
    public final static int PERMISSION_REQUEST_CODE = 4868494;

    public static void checkResult(Activity activity, int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == PermissionUtil.PERMISSION_REQUEST_CODE){
            for (int i = 0; i < permissions.length; i++) {
                String permission = permissions[i].startsWith("android.permission") ?
                        permissions[i].substring(19) : permissions[i];
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED){
                    LogUtil.d("权限 "+permission+" 授权成功！");
                } else {
                    LogUtil.w("权限 "+permission+" 授权失败！");
                    checkSelfPermission(activity,permissions,"授权失败，请重新授权！");
                    return;
                }
            }
        }
        LogUtil.i("所有授权均成功");
    }

    /**
     * 原生方法请求
     * @param activity
     * @param permissions
     * @param message
     * @return
     */
    public static boolean checkSelfPermission(Activity activity, String[] permissions, String message){
        return checkSelfPermission(activity, permissions, message, PERMISSION_REQUEST_CODE);
    }
    /**
     * 原生方法请求
     * @param activity
     * @param permissions
     * @param requestCode
     * @return
     */
    public static boolean checkSelfPermission(Activity activity, String[] permissions, String message, int requestCode) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            LogUtil.w("android 6 以下不需要检验权限");
            return true;
        } else {
            for (int i = 0; i < permissions.length; i++) {
                //无权限则申请权限
                if(activity.checkSelfPermission(permissions[i]) != PackageManager.PERMISSION_GRANTED){
                    if ( message != null) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                        builder.setMessage(message)
                                .setNegativeButton("取消", (dialog, which) -> dialog.dismiss())
                                .setPositiveButton("继续", (dialog, which) ->
                                        activity.requestPermissions(permissions,requestCode));
                        builder.create().show();
                    } else {
                        activity.requestPermissions(permissions,requestCode);
                    }
                    return false;
                }
            }
        }

        return true;
    }

}
