package com.hancher.gamelife.test;

import android.Manifest;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.hancher.common.utils.android.ToastUtil;
import com.hancher.gamelife.R;
import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.RequestCallback;

/**
 * 权限分类：安装时权限、运行时权限、特殊权限
 * https://developer.android.google.cn/guide/topics/permissions/overview
 */
public class PermissionXTestActivity extends AppCompatActivity {

    RequestCallback requestCallback = (allGranted, grantedList, deniedList) -> {
        StringBuffer stringBuffer = new StringBuffer("授权回调：\n");
        stringBuffer.append(allGranted?"全部同意：\n":"以下权限已同意:\n");
        for (String item: grantedList){
            stringBuffer.append(item).append("\n");
        }
        if (!allGranted){
            stringBuffer.append("\n以下权限未同意:\n");
            for(String item: deniedList){
                stringBuffer.append(item.substring(item.lastIndexOf("."))).append("\n");
            }
        }
        if (allGranted) {
            ToastUtil.show(stringBuffer.toString());
        } else {
            ToastUtil.showErr(stringBuffer.toString());
        }
    };

    private void requestDangerousPermission() {
        PermissionX.init(this)
                .permissions(
                        Manifest.permission.SYSTEM_ALERT_WINDOW,
                        Manifest.permission.WRITE_SETTINGS
                )
                .onExplainRequestReason( (scope, deniedList) -> {
                    scope.showRequestReasonDialog(deniedList,"PermissionX需要您同意以下权限才能正常使用","允许","拒绝");
                })
                .request(requestCallback);
    }

    private void requestRunPermission() {
        PermissionX.init(this)
                .permissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_NETWORK_STATE
                )
                .request(requestCallback);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission_xtest);
        requestRunPermission();
        requestDangerousPermission();
    }


}