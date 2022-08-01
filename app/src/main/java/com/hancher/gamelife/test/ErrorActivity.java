package com.hancher.gamelife.test;

import com.hancher.common.base.mvvm02.BaseActivity;
import com.hancher.gamelife.databinding.ErrorActivityBinding;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/10/10 0010 0:49 <br/>
 * 描述 : 错误测试
 */
public class ErrorActivity extends BaseActivity<ErrorActivityBinding> {
    @Override
    protected void initListener() {
        binding.btnNull.setOnClickListener(v -> {
            String nullStr = null;
            nullStr.length();
        });
    }
}
