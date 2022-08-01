package com.hancher.gamelife.main;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.hancher.common.base.mvp01.BaseActivity;
import com.hancher.common.utils.android.AsyncUtils;
import com.hancher.common.utils.android.LogUtil;
import com.hancher.common.utils.android.ToastUtil;
import com.hancher.gamelife.databinding.SplashActivityBinding;
import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.request.PermissionBuilder;
import com.tencent.mmkv.MMKV;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/3/11 15:33 <br/>
 * 描述 : 开屏界面
 * 无倒计时会导致闪屏
 */
public class SplashActivity extends BaseActivity<SplashActivityBinding> {
    public int step = 0;
    public static String[] DANGEROUS_PERMISSION = new String[]{
            Manifest.permission.SYSTEM_ALERT_WINDOW,
            Manifest.permission.WRITE_SETTINGS,
            Manifest.permission.MANAGE_EXTERNAL_STORAGE,
            Manifest.permission.REQUEST_INSTALL_PACKAGES
    };
    public static String[] RUN_PERMISSION = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.READ_SMS,
            Manifest.permission.READ_PHONE_NUMBERS,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_PHONE_STATE
    };

    private void requestNeedPermission() {
        LogUtil.d("step："+step);
        String[] permissions;
        if (step == 0){
            permissions = DANGEROUS_PERMISSION;
        } else if (step == 1){
            permissions = RUN_PERMISSION;
        } else {
            coutdownJump();
            return;
        }
        boolean grantAll = true;
        // 检查权限是否存在
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
            for(String item : permissions){
                if(checkSelfPermission(item) != PackageManager.PERMISSION_GRANTED){
                    grantAll = false;
                    break;
                }
            }
        }
        // 申请权限并跳转
        if (grantAll){
            step++;
            requestNeedPermission();
        } else {
            PermissionBuilder build = PermissionX.init(this).permissions(permissions);
            if (step==0){
                build.onExplainRequestReason((scope, deniedList) -> {
                    scope.showRequestReasonDialog(deniedList, "PermissionX需要您同意以下权限才能正常使用", "允许", "拒绝");
                });
            }
            step++;
            build.request((allGranted, grantedList, deniedList) -> {
                StringBuffer stringBuffer = new StringBuffer("授权回调：\n");
                stringBuffer.append(allGranted?"全部同意：\n":"以下权限已同意:\n");
                for (String item: grantedList){
                    stringBuffer.append(item.substring(item.lastIndexOf(".")+1)).append("\n");
                }
                if (!allGranted){
                    stringBuffer.append("\n以下权限未同意:\n");
                    for(String item: deniedList){
                        stringBuffer.append(item.substring(item.lastIndexOf(".")+1)).append("\n");
                    }
                }
                if (allGranted) {
                    ToastUtil.show(stringBuffer.toString());
                } else {
                    ToastUtil.showErr(stringBuffer.toString());
                }
                requestNeedPermission();
            });
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding.splashCountdown.setOnClickListener(v -> jump());

//        MmkvUtil.init(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.splashCountdown.setVisibility(View.GONE);
        step=0;
        requestNeedPermission();
    }

    private void coutdownJump() {
        binding.splashCountdown.setVisibility(View.VISIBLE);
        d = AsyncUtils.runCountdown(0L, 3L, 0L, 1000L,
                aLong -> {
                    LogUtil.d((3-aLong) + "秒后关闭");
                    binding.splashCountdown.setText((3-aLong) + "秒后关闭");
                },
                () -> {
                    LogUtil.d("倒计时结束");
                    jump();
                }
        );
    }

    private void jump() {
        MMKV.defaultMMKV().encode("splash.last",System.currentTimeMillis());
        overridePendingTransition(0, 0);
        SplashActivity.this.startActivity(new Intent(SplashActivity.this, GameLifeMvpActivity.class));
        SplashActivity.this.finish();
    }
}
