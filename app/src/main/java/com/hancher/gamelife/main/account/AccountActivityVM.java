package com.hancher.gamelife.main.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import androidx.annotation.Nullable;

import com.afollestad.materialdialogs.MaterialDialog;
import com.hancher.common.base.mvvm01.VMBaseActivity;
import com.hancher.common.utils.android.LogUtil;
import com.hancher.common.utils.android.RecyclerViewUtil;
import com.hancher.common.utils.android.ToolBarUtil;
import com.hancher.common.utils.java.DecryptUtil;
import com.hancher.common.utils.java.TextUtil;
import com.hancher.gamelife.R;
import com.hancher.gamelife.databinding.ActivityAccountBinding;
import com.hancher.gamelife.greendao.Account;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;

public class AccountActivityVM extends VMBaseActivity<ActivityAccountBinding, AccountViewModel> {

    private AccountAdapter adapter = new AccountAdapter(new ArrayList<>());
    private MaterialDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initListener();
    }

    private void initListener() {
        adapter.setOnItemClickListener((adapter, view, position) -> {
            LogUtil.i(position, "/", adapter.getData().size());
            Account data = (Account) adapter.getData().get(position);
            StringBuffer content = new StringBuffer();
            if (!TextUtil.isEmptyOrNullStr(data.getAccount())) {
                content.append("运营商：").append(data.getAccount()).append("\n");
            }
            if (!TextUtil.isEmptyOrNullStr(data.getUser())) {
                content.append("用户名：").append(data.getUser()).append("\n");
            }
            if (!TextUtil.isEmptyOrNullStr(data.getPsd())) {
                content.append("密码：").append(DecryptUtil.decrypt2(data.getPsd())).append("\n");
            }
            if (!TextUtil.isEmptyOrNullStr(data.getNote())) {
                content.append("备注：").append(data.getNote()).append("\n");
            }
            LogUtil.v("当前点击项:", data);
            dialog = new MaterialDialog.Builder(this)
                    .content(content)
                    .positiveText("编辑")
                    .negativeText("删除")
                    .onPositive((dialog, which) -> {
                        LogUtil.v("当前编辑项:", data);
                        dialog.dismiss();
                        Intent intent = new Intent(AccountActivityVM.this,
                                AccountEditActivityVM.class);
                        intent.putExtra(AccountEditActivityVM.UUID, data.getUuid());
                        startActivity(intent);
                    })
                    .onNegative((dialog, which) -> {
                        LogUtil.v("当前删除项:", data);
                        viewModel.deleteAccount(data.getUuid());
                    })
                    .show();
        });
        binding.searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                viewModel.updateAccountList(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() < 3) {
                    return false;
                }
                viewModel.updateAccountList(newText);
                return true;
            }
        });
        viewModel.getAllAccounts().observe(this, accounts -> RecyclerViewUtil.update(adapter, accounts));
        binding.btnFloat3.setOnClickListener(v -> {
            Intent intent = new Intent(this, AccountEditActivityVM.class);
            startActivity(intent);
        });
    }

    private void initView() {
        ToolBarUtil.initToolbar(binding.superToolbar, this, true);
        RecyclerViewUtil.initLine(this, binding.recyclerview, adapter, null, false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.updateAccountList("");
    }

    @Override
    protected void onPause() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        binding.searchView.setMenuItem(menu.findItem(R.id.menu_search));
        return true;
    }
}