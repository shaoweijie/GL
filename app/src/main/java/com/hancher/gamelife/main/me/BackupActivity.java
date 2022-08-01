package com.hancher.gamelife.main.me;

import android.Manifest;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.hancher.common.base.mvvm02.BaseActivity;
import com.hancher.common.utils.android.IntentUtil;
import com.hancher.common.utils.android.LogUtil;
import com.hancher.common.utils.android.PathUtil;
import com.hancher.common.utils.android.ToastUtil;
import com.hancher.common.utils.android.ToolBarUtil;
import com.hancher.common.utils.android.UriUtil;
import com.hancher.gamelife.databinding.BackupActivityBinding;
import com.permissionx.guolindev.PermissionX;

import java.io.File;

public class BackupActivity extends BaseActivity<BackupActivityBinding> {
    final static int REQUEST_CODE_FILE_PIC_SELECT = 210901;
    BackupViewModel backupViewModel;

    @Override
    protected void initViewModel() {
        backupViewModel = ViewModelProviders.of(this).get(BackupViewModel.class);
    }

    @Override
    protected void initView() {
        ToolBarUtil.initToolbar(binding.superToolbar, this, true);
        PermissionX.init(this)
                .permissions(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .request((allGranted, grantedList, deniedList) -> {
                    if (!allGranted){
                        ToastUtil.show("以下权限未同意:\n"+deniedList);
                        return;
                    }
                    PermissionX.init(this)
                            .permissions(Manifest.permission.MANAGE_EXTERNAL_STORAGE)
                            .request((allGranted1, grantedList1, deniedList1) -> {
                                if (!allGranted1){
                                    ToastUtil.show("以下权限未同意:\n"+deniedList1);
                                }
                            });
                });
    }

    @Override
    protected void initListener() {
        binding.btnBackup.setOnClickListener(v -> {
            PathUtil.initFilePath(this);
            backupViewModel.startBackup();
        });
        binding.btnRestore.setOnClickListener(v -> {
            LogUtil.d("选择备份文件, 开始恢复");
            startActivityForResult(IntentUtil.getSelectFileIntent(), REQUEST_CODE_FILE_PIC_SELECT);
        });
        backupViewModel.getBackupInfo().observe(this, s -> binding.txtLog.append(s + "\n"));
        backupViewModel.getBackupZipPath().observe(this, s -> {
            LogUtil.d(String.format("备份文件 存在:%b 位置:%s", new File(s).exists(), s));
            Intent intent = IntentUtil.getShareFileIntent(this, new File(s));
            BackupActivity.this.startActivity(intent);
        });
        binding.btnClear.setOnClickListener(v -> binding.txtLog.setText(""));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.d("requestCode:", requestCode, ", resultCode:", resultCode, "data:", data);
        switch (requestCode) {
            case REQUEST_CODE_FILE_PIC_SELECT:
                if (resultCode == RESULT_OK) {
                    String selectFilePath = UriUtil.getPathByUri(this, data.getData());
                    ToastUtil.show("选择文件：" + selectFilePath);
                    backupViewModel.startRecover(selectFilePath);
                }
                break;
        }
    }
}