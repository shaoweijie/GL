package com.hancher.gamelife.bak.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.hancher.common.base.mvp01.BaseMvpActivity;
import com.hancher.common.utils.android.LogUtil;
import com.hancher.gamelife.bak.contract.FlowDialogContract;
import com.hancher.gamelife.bak.fragment.FlowDialogFragment;
import com.hancher.gamelife.bak.presenter.FlowDialogPresenter;
import com.hancher.gamelife.databinding.FlowDialogActivityBinding;

public class FlowDialogMvpActivity extends BaseMvpActivity<FlowDialogActivityBinding, FlowDialogPresenter>
        implements FlowDialogContract.View{

    private FlowDialogFragment fragment = new FlowDialogFragment();
    private final static String fragmentTag = "FlowDialogFragment";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding.btnTest.setOnClickListener(v -> {
            if (fragment.isAdded()){
                getSupportFragmentManager()
                        .beginTransaction()
                        .remove(fragment)
                        .commit();
            }
            fragment.show(getSupportFragmentManager(), fragmentTag);
        });
    }

    @Override
    protected void onDestroy() {
        try {
            fragment.dismiss();
        } catch (Exception e){
            LogUtil.e(e);
        }
        super.onDestroy();
    }
}