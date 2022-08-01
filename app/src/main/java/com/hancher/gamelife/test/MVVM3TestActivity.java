package com.hancher.gamelife.test;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.hancher.common.base.mvvm03.BaseVmActivity;
import com.hancher.gamelife.R;
import com.hancher.gamelife.databinding.ActivityMvvm3TestBinding;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/10/10 0010 0:49 <br/>
 * 描述 : MVVM v3.0测试
 */
public class MVVM3TestActivity extends BaseVmActivity<ActivityMvvm3TestBinding,MVVM2TestViewModel> {

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.countdown();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mvvm3_test;
    }
}
