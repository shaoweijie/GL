package com.hancher.gamelife.bak.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.hancher.common.base.mvp01.BaseMvpActivity;
import com.hancher.common.utils.android.AsyncUtils;
import com.hancher.gamelife.R;
import com.hancher.gamelife.bak.contract.ProcessContract;
import com.hancher.gamelife.bak.presenter.ProcessPresenter;
import com.hancher.gamelife.databinding.ActivityProcessBinding;

import io.reactivex.ObservableOnSubscribe;

public class ProcessMvpActivity extends BaseMvpActivity<ActivityProcessBinding, ProcessPresenter>
        implements ProcessContract.View, View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding.button7.setOnClickListener(this);
        binding.button8.setOnClickListener(this);
        binding.button9.setOnClickListener(this);
        binding.button10.setOnClickListener(this);
        binding.dashboardProgressBar.setSpeedRankValues(new float[]{0,5.0f,10.0f,45.0f,50.0f});
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button7:
                binding.dashboardProgressBar.setSpeedProgress(binding.dashboardProgressBar.getSpeedProgress()-5.0f);
                binding.xxProgressBar.setSpeedProgress(binding.xxProgressBar.getSpeedProgress()-0.05f);
                break;
            case R.id.button8:
                binding.dashboardProgressBar.setSpeedProgress(binding.dashboardProgressBar.getSpeedProgress()+5.0f);
                binding.xxProgressBar.setSpeedProgress(binding.xxProgressBar.getSpeedProgress()+0.05f);
                break;
            case R.id.button9:
                AsyncUtils.run((ObservableOnSubscribe<Float>) emitter -> {
                    while (binding.dashboardProgressBar.getSpeedProgress()>0.1f) {
                        emitter.onNext(binding.dashboardProgressBar.getSpeedProgress() - 0.1f);
                        Thread.sleep(20);
                    }
                },(speed)->{
                    binding.dashboardProgressBar.setSpeedProgress(speed);
                    binding.txtLogs.setText(speed+"");
                });
                AsyncUtils.run((ObservableOnSubscribe<Float>) emitter -> {
                    while (binding.xxProgressBar.getSpeedProgress()>0.05f) {
                        emitter.onNext(binding.xxProgressBar.getSpeedProgress() - 0.05f);
                        Thread.sleep(20);
                    }
                },(speed)->{
                    binding.xxProgressBar.setSpeedProgress(speed);
                });
                break;
            case R.id.button10:
                AsyncUtils.run((ObservableOnSubscribe<Float>) emitter -> {
                    while (binding.dashboardProgressBar.getSpeedProgress()<49.9f) {
                        emitter.onNext(binding.dashboardProgressBar.getSpeedProgress() + 0.1f);
                        Thread.sleep(20);
                    }
                },(speed)->{
                    binding.dashboardProgressBar.setSpeedProgress(speed);
                    binding.txtLogs.setText(speed+"");
                });
                AsyncUtils.run((ObservableOnSubscribe<Float>) emitter -> {
                    while (binding.xxProgressBar.getSpeedProgress()<0.95f) {
                        emitter.onNext(binding.xxProgressBar.getSpeedProgress() + 0.05f);
                        Thread.sleep(40);
                    }
                },(speed)->{
                    binding.xxProgressBar.setSpeedProgress(speed);
                });
                break;
        }
        binding.txtLogs.setText(binding.dashboardProgressBar.getSpeedProgress()+"");
    }
}