package com.hancher.gamelife.main.me;

import com.hancher.common.base.mvvm02.BaseActivity;
import com.hancher.common.utils.android.ClipboardUtil;
import com.hancher.common.utils.android.ToolBarUtil;
import com.hancher.common.utils.java.DecryptUtil;
import com.hancher.gamelife.databinding.ActivityEncryptBinding;

public class EncryptActivity extends BaseActivity<ActivityEncryptBinding> {

    @Override
    protected void initListener() {
        binding.pasteIn.setOnClickListener(v -> binding.editIn.setText(ClipboardUtil.paste()));
        binding.copyOut.setOnClickListener(v ->
                ClipboardUtil.copy(binding.editOut.getText().toString()));
        binding.encrypt1.setOnClickListener(v ->
                binding.editOut.setText(DecryptUtil.encrypt(binding.editIn.getText().toString())));
        binding.encrypt2.setOnClickListener(v ->
                binding.editOut.setText(DecryptUtil.encrypt2(binding.editIn.getText().toString())));
        binding.decrypt1.setOnClickListener(v ->
                binding.editOut.setText(DecryptUtil.decrypt(binding.editIn.getText().toString())));
        binding.decrypt2.setOnClickListener(v ->
                binding.editOut.setText(DecryptUtil.decrypt2(binding.editIn.getText().toString())));
    }

    @Override
    protected void initView() {
        ToolBarUtil.initToolbar(binding.superToolbar,this,true);
    }
}