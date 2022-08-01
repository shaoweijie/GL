package com.hancher.gamelife.test;

import com.hancher.common.base.mvp01.BaseActivity;
import com.hancher.gamelife.databinding.ActivityAppBarTestBinding;

public class AppBarTestActivity extends BaseActivity<ActivityAppBarTestBinding> {
    @Override
    protected void initView() {
        super.initView();
        StringBuilder stringBuilder = new StringBuilder("内容\n");
        for (int i = 0; i < 999; i++) {
            stringBuilder.append(i+" ==\n");
        }
        binding.tvContent.setText(stringBuilder);

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}