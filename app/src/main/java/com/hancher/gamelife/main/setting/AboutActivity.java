package com.hancher.gamelife.main.setting;

import android.Manifest;
import android.content.Intent;
import android.os.Build;

import androidx.lifecycle.ViewModelProviders;

import com.hancher.common.base.mvvm02.BaseActivity;
import com.hancher.common.utils.android.ApkInfoUtil;
import com.hancher.common.utils.android.IntentUtil;
import com.hancher.common.utils.android.ScreenAlwaysLightUtil;
import com.hancher.common.utils.android.ToastUtil;
import com.hancher.common.utils.android.ToolBarUtil;
import com.hancher.gamelife.R;
import com.hancher.gamelife.databinding.AboutActivityBinding;
import com.permissionx.guolindev.PermissionX;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/8/27 0027 0:20 <br/>
 * 描述 : 关于我们
 */
public class AboutActivity extends BaseActivity<AboutActivityBinding> {
    PgyerViewModel pgyerViewModel;

    @Override
    protected void initViewModel() {
        pgyerViewModel = ViewModelProviders.of(this).get(PgyerViewModel.class);
        //获取最新版本
        pgyerViewModel.getLastRelease().observe(this, lastestApkBean -> {
            if (lastestApkBean.getBuildHaveNewVersion()) {
                binding.btnCheck.setText("下载新版本:" + lastestApkBean.getBuildVersion());
            } else {
                binding.btnCheck.setText("当前为最新版本");
            }
        });
    }

    @Override
    protected void initListener() {
        //点击升级
        binding.btnCheck.setOnClickListener(v -> {
            // android8 检查安装权限，无权则跳转到设置
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if(!getPackageManager().canRequestPackageInstalls()){
                    Intent intent = IntentUtil.getInstallPermission(AboutActivity.this);
                    startActivity(intent);
                    return;
                }
            }
            // 读写权限
            PermissionX.init(this)
                    .permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE)
                    .onExplainRequestReason((scope, deniedList) -> scope.showRequestReasonDialog(
                            deniedList, "即将重新申请的权限是程序必须依赖的权限",
                            "我已明白", "取消"))
                    .request((allGranted, grantedList, deniedList) -> {
                        if (!allGranted) {
                            ToastUtil.show("您拒绝了如下权限："+ deniedList);
                            return;
                        }
                        if (pgyerViewModel.getLastRelease().getValue()==null) {
                            ToastUtil.show("无更新信息");
                            return;
                        }
                        if (!pgyerViewModel.getLastRelease().getValue().getBuildHaveNewVersion()) {
                            ToastUtil.show("已最新");
                            return;
                        }
                        pgyerViewModel.downloadApk(AboutActivity.this);
                    });
        });
    }


    @Override
    protected void initView() {
        ToolBarUtil.initToolbar(binding.superToolbar, this, true);
        binding.textVersion.setText(getString(R.string.about_version,
                ApkInfoUtil.getVersionName(this), ApkInfoUtil.getVersionCode(this)));
    }

    @Override
    protected void onPause() {
        ScreenAlwaysLightUtil.setOff(AboutActivity.this);
        super.onPause();
    }
}