package com.hancher.gamelife.main.colockin;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.afollestad.materialdialogs.MaterialDialog;
import com.hancher.common.base.mvvm01.VMBaseActivity;
import com.hancher.common.utils.android.RecyclerViewUtil;
import com.hancher.common.utils.android.ToolBarUtil;
import com.hancher.gamelife.R;
import com.hancher.gamelife.databinding.ColockInActivityBinding;
import com.hancher.gamelife.greendao.ColockInType;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/7/17 0017 21:09 <br/>
 * 描述 : 点击打卡界面
 */
public class ColockInActivityVM extends VMBaseActivity<ColockInActivityBinding, ColockInViewModel> {
    private ColockInAdapter adapter = new ColockInAdapter();
    private MaterialDialog inputDialog;
    private MaterialDialog waitingDialog;
    private MaterialDialog deleteDialog;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RecyclerViewUtil.initGrid(this, binding.recyclerview, adapter, binding.swipeRefresh, false, 5);
        ToolBarUtil.initToolbar(binding.superToolbar,this, true);
        initObseve();
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.queryAllType();
    }

    private void initObseve() {
        viewModel.getColockInTypes().observe(this, colockInItems -> {
            RecyclerViewUtil.update(adapter, colockInItems);
        });
        adapter.setOnItemClickListener((adapter, view, position) -> {
            ColockInType type = (ColockInType) adapter.getItem(position);
            inputDialog = new MaterialDialog.Builder(ColockInActivityVM.this)
                    .title("备注")
                    .input("在这里填写备注", "", (dialog, input) -> {
                        viewModel.addColockIn(type, input);
                        dialog.dismiss();
                        waitingDialog = new MaterialDialog.Builder(ColockInActivityVM.this)
                                .title("保存中")
                                .progress(true,100)
                                .show();
                    }).show();

        });
        binding.btnFloat.setOnClickListener(v -> {
            Intent intent = new Intent(ColockInActivityVM.this, ColockInTypeAddActivityVM.class);
            ColockInActivityVM.this.startActivity(intent);
        });
        // 保存成功，关闭等待界面，重新查询所有类型
        viewModel.getState().observe(this,state -> {
            if (state == ColockInViewModel.STATE.SAVE_FINISH){
                if (waitingDialog!=null && waitingDialog.isShowing()){
                    waitingDialog.dismiss();
                }
                startActivity(new Intent(ColockInActivityVM.this, ColockInListActivityVM.class));
            }
        });
        // 长按删除功能
        adapter.setOnItemLongClickListener((adapter, view, position) -> {
            ColockInType type = (ColockInType) adapter.getItem(position);
            deleteDialog = new MaterialDialog.Builder(ColockInActivityVM.this)
                    .title("是否删除此类型？")
                    .positiveText("删除")
                    .onPositive((dialog, which) -> {
                        viewModel.removeType(type);
                        dialog.dismiss();
                    })
                    .show();
            return false;
        });
        // 删除成功后，更新列表
        viewModel.getState().observe(this, state -> {
            if (state == ColockInViewModel.STATE.DELETE_FINISH) {
                viewModel.queryAllType();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_colockin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_list:
                startActivity(new Intent(this, ColockInListActivityVM.class));
                break;
//            case R.id.menu_item_setting:
//                startActivity(new Intent(this, SettingListActivity.class));
//                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        if (deleteDialog!=null && deleteDialog.isShowing()){
            deleteDialog.dismiss();
        }
        if (inputDialog!=null && inputDialog.isShowing()){
            inputDialog.dismiss();
        }
        if (waitingDialog!=null && waitingDialog.isShowing()){
            waitingDialog.dismiss();
        }
        super.onDestroy();
    }
}
