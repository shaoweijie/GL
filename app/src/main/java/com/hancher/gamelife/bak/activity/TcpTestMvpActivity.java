package com.hancher.gamelife.bak.activity;

import android.Manifest;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.hancher.common.base.mvp01.BaseMvpActivity;
import com.hancher.common.utils.android.PathUtil;
import com.hancher.gamelife.R;
import com.hancher.gamelife.bak.contract.TcpTestContract;
import com.hancher.gamelife.bak.presenter.TcpTestPresenter;
import com.hancher.gamelife.databinding.TcpTestActivityBinding;

import java.io.File;

public class TcpTestMvpActivity extends BaseMvpActivity<TcpTestActivityBinding, TcpTestPresenter>
        implements TcpTestContract.View, View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding.btnConnect.setOnClickListener(this);
        binding.btnListIp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_connect:
                File demoFile = new File(PathUtil.sdDownload,"demo.mp4");
                binding.txtLog.setText("准备发送文件Download/demo.mp4");
                presenter.tcpConnect(binding.textOriIp.getText().toString(), demoFile);
                break;
            case R.id.btn_list_ip:break;
        }
    }

    @Override
    public void changeLogText(String log) {
        binding.txtLog.setText(log);
    }

    //todo
    public String[] getPermission() {

        return new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE };
    }
}