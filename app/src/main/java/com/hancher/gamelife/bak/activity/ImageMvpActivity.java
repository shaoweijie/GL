package com.hancher.gamelife.bak.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.hancher.common.base.mvp01.BaseMvpActivity;
import com.hancher.gamelife.bak.contract.ImageContract;
import com.hancher.gamelife.bak.presenter.ImagePresenter;
import com.hancher.gamelife.databinding.ImageActivityBinding;

public class ImageMvpActivity extends BaseMvpActivity<ImageActivityBinding, ImagePresenter>
        implements ImageContract.View {

    public static final String PATH = "path";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Glide.with(binding.centerImage).load(getIntent().getStringExtra(PATH));
    }
}