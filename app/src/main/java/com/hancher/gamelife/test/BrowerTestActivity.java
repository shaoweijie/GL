package com.hancher.gamelife.test;

import android.content.Intent;

import com.hancher.common.activity.WebActivity;
import com.hancher.common.base.mvp01.BaseActivity;
import com.hancher.gamelife.databinding.ActivityBrowerTestBinding;

public class BrowerTestActivity extends BaseActivity<ActivityBrowerTestBinding> {

    @Override
    protected void initListener() {
        super.initListener();
        binding.button14.setOnClickListener(view -> {
            Intent intent = WebActivity.getIntent(BrowerTestActivity.this,
                    "file:///android_asset/电商活动策划1.htm", "电商策划");
            BrowerTestActivity.this.startActivity(intent);
        });
    }
}