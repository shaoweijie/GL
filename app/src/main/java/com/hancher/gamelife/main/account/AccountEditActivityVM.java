package com.hancher.gamelife.main.account;

import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.hancher.common.base.mvvm01.VMBaseActivity;
import com.hancher.common.utils.android.ToastUtil;
import com.hancher.common.utils.android.ToolBarUtil;
import com.hancher.common.utils.java.DecryptUtil;
import com.hancher.common.utils.java.UuidUtil;
import com.hancher.gamelife.databinding.ActivityAccountEditBinding;
import com.hancher.gamelife.greendao.Account;

public class AccountEditActivityVM extends VMBaseActivity<ActivityAccountEditBinding, AccountViewModel> {

    public static final String UUID = "uuid";
    private Account account;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ToolBarUtil.initToolbar(binding.superToolbar, this, true);
        initListener();
    }

    private void initListener() {
        viewModel.getAccount().observe(this, o -> {
            binding.editAccount.setText(o.getAccount());
            binding.editDescription.setText(o.getNote());
            binding.editName.setText(o.getUser());
            binding.editPsw.setText(DecryptUtil.decrypt2(o.getPsd()));
            account = o;
        });
        viewModel.getUpdateState().observe(this, o -> {
            if (o < 1){
                ToastUtil.show("更新失败");
                return;
            }
            AccountEditActivityVM.this.finish();
        });
        binding.btnFloat.setOnClickListener(v -> {
            account.setUpdatetime(String.valueOf(System.currentTimeMillis()));
            account.setDeleteflag("0");
            account.setAccount(binding.editAccount.getText().toString());
            account.setUser(binding.editName.getText().toString());
            account.setPsd(DecryptUtil.encrypt2(binding.editPsw.getText().toString()));
            account.setNote(binding.editDescription.getText().toString());
            viewModel.saveAccount(account);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        String uuid = getIntent().getStringExtra(UUID);
        if (!TextUtils.isEmpty(uuid)){
            viewModel.queryCurrentItem(uuid);
        }else {
            account = new Account();
            account.setUuid(UuidUtil.getUuidNoLine());
            account.setCreatetime(String.valueOf(System.currentTimeMillis()));
        }
    }
}