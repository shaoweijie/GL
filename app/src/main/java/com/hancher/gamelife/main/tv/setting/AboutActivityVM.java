package com.hancher.gamelife.main.tv.setting;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.afollestad.materialdialogs.MaterialDialog;
import com.hancher.common.base.mvvm01.VMBaseActivity;
import com.hancher.common.net.download.DownloadListener;
import com.hancher.common.utils.android.ApkInfoUtil;
import com.hancher.common.utils.android.DialogUtil;
import com.hancher.common.utils.android.IntentUtil;
import com.hancher.common.utils.android.LogUtil;
import com.hancher.common.utils.android.ScreenAlwaysLightUtil;
import com.hancher.common.utils.android.ToastUtil;
import com.hancher.common.utils.android.ToolBarUtil;
import com.hancher.gamelife.R;
import com.hancher.gamelife.databinding.AboutActivityBinding;

import java.io.File;

public class AboutActivityVM extends VMBaseActivity<AboutActivityBinding, PgyerUpdateViewModel> {

    private MaterialDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ToolBarUtil.initToolbar(binding.superToolbar,this,true);
        initObserve();
        binding.textVersion.setText(getString(R.string.about_version,
                ApkInfoUtil.getVersionName(this), ApkInfoUtil.getVersionCode(this)));
    }

    private void initObserve() {
        //获取最新版本
        viewModel.getLastRelease().observe(this, lastestApkBean -> {
            if (lastestApkBean.getBuildHaveNewVersion()) {
                binding.btnCheck.setText("下载新版本:" + lastestApkBean.getBuildVersion());
            } else {
                binding.btnCheck.setText("当前为最新版本");
            }
        });
        //下载apk，提示下载
        binding.btnCheck.setOnClickListener(v -> {
            if (viewModel.getLastRelease() == null || viewModel.getLastRelease().getValue() == null) {
                viewModel.checkApk();
            } else if (viewModel.getLastRelease().getValue().getBuildHaveNewVersion()) {
                viewModel.downloadApk(new DownloadListener() {
                    @Override
                    public void onStart() {
                        ScreenAlwaysLightUtil.setOn(AboutActivityVM.this);
                        dialog = DialogUtil.progress(AboutActivityVM.this)
                                .title("下载最新版本")
                                .content("下载开始...")
                                .onPositive((dialog, which) -> dialog.dismiss())
                                .build();
                        dialog.show();
                    }

                    @Override
                    public void onProgress(int progress) {
                        dialog.setContent("下载中...");
                        dialog.setProgress(progress);
                    }

                    @Override
                    public void onFinish(String path) {
                        dialog.setContent("下载完成...");
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        File downFile = new File(path);
                        LogUtil.i("下载完成 文件位置:" + downFile.getAbsolutePath() + ", 文件大小:" + downFile.length());
                        Intent intent = IntentUtil.getInstallIntent(AboutActivityVM.this, downFile);
                        AboutActivityVM.this.startActivity(intent);
                    }

                    @Override
                    public void onFail(String errorInfo) {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        ToastUtil.show("下载失败:" + errorInfo);
                        ScreenAlwaysLightUtil.setOff(AboutActivityVM.this);
                    }
                });
            } else {
                ToastUtil.show("当前为最新版本");
            }
        });
        viewModel.checkApk();
    }

    @Override
    public String[] getPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return new String[]{
                    Manifest.permission.INTERNET,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.REQUEST_INSTALL_PACKAGES};
        } else {
            return new String[]{
                    Manifest.permission.INTERNET,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE};
        }
    }

    @Override
    protected void onPause() {
        ScreenAlwaysLightUtil.setOff(AboutActivityVM.this);
        super.onPause();
    }
}