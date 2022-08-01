package com.hancher.common.utils.android;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.StrictMode;
import android.provider.Settings;

import androidx.annotation.RequiresApi;

import java.io.File;

public class IntentUtil {
    /**
     * 使用已安装的文件选择器
     * @return
     */
    public static Intent getSelectFileIntent(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        return Intent.createChooser(intent, "选择一个文件");
    }
    /*
    startActivityForResult(IntentUtil.getSelectFileIntent(), CommonConfig.REQUEST_CODE_FILE_SELECT);

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case CommonConfig.REQUEST_CODE_FILE_SELECT:
                if (resultCode == RESULT_OK) {
                    UriUtil.getPathByUri(this, data.getData());
                }
                break;
        }
        presenter.startRecover();
    }
     */


    /**
     * 文件分享页面
     *
     * @param context
     * @param file
     * @return
     */
    public static Intent getShareFileIntent(Context context, File file) {
        if (file == null || !file.exists()) {
            ToastUtil.show("分享文件不存在："+file.getAbsolutePath());
            return null;
        }
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();

        Intent share = new Intent(Intent.ACTION_SEND);
        share.putExtra(Intent.EXTRA_STREAM, UriUtil.getUri(context, file));
        share.setType(UriUtil.getMimeType(file.getAbsolutePath()));//此处可发送多种文件
        share.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        return Intent.createChooser(share, "分享文件");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Intent getInstallPermission(Context context){
        Uri packageURI = Uri.parse("package:"+context.getPackageName());
        return new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES,packageURI);
    }

    /**
     * 获取安装apk的Intent
     * @param context
     * @param file
     * @return
     */
    public static Intent getInstallIntent(Context context, File file) {
        if (file == null || !file.exists()) {
            ToastUtil.show("分享文件不存在");
            return null;
        }
        Intent install = new Intent();
        install.setAction(Intent.ACTION_VIEW);
        install.addCategory(Intent.CATEGORY_DEFAULT);
        install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//        install.setDataAndType("content://Uri","appplication/vnd.android.package-archive");
        install.setDataAndType(UriUtil.getUri(context, file), UriUtil.getMimeType(file.getAbsolutePath()));
        return install;
    }

    public static Intent getHomeIntent() {
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        return homeIntent;
    }
}
