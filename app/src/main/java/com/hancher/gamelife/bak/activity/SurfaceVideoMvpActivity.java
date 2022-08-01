package com.hancher.gamelife.bak.activity;

import android.Manifest;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.hancher.common.base.mvp01.BaseMvpActivity;
import com.hancher.common.utils.android.LogUtil;
import com.hancher.gamelife.bak.contract.SurfaceVideoContract;
import com.hancher.gamelife.bak.presenter.SurfaceVideoPresenter;
import com.hancher.gamelife.databinding.ActivitySurfaceVideoBinding;

public class SurfaceVideoMvpActivity extends BaseMvpActivity<ActivitySurfaceVideoBinding, SurfaceVideoPresenter>
        implements SurfaceVideoContract.View  {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
    }

    @Override
    protected void onResume() {
        super.onResume();

        binding.videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                LogUtil.d();
            }
        });
        binding.videoView.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
            @Override
            public void onSeekComplete(MediaPlayer mp) {
                LogUtil.d();
            }
        });

        binding.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                LogUtil.d();
                binding.videoView.start();
            }
        });
        binding.videoView.openVideo(Uri.parse("/storage/emulated/0/demo.mp4"));
        binding.videoView.initVideoView();
    }

    //todo
    public String[] getPermission() {
        return new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};
    }
}