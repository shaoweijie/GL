package com.hancher.gamelife.main.colockin;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.afollestad.materialdialogs.MaterialDialog;
import com.hancher.common.base.mvvm01.VMBaseActivity;
import com.hancher.common.utils.android.LogUtil;
import com.hancher.common.utils.android.RecyclerViewUtil;
import com.hancher.common.utils.android.ToolBarUtil;
import com.hancher.gamelife.R;
import com.hancher.gamelife.databinding.ColockInListActivityBinding;
import com.hancher.gamelife.greendao.ColockIn;
import com.hancher.gamelife.greendao.ColockInType;

import java.util.List;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/7/17 0017 21:09 <br/>
 * 描述 : 点击打卡界面
 */
public class ColockInListActivityVM extends VMBaseActivity<ColockInListActivityBinding, ColockInViewModel> {
    private ColockInListAdapter adapter = new ColockInListAdapter();
    private int count = 20;
    private MaterialDialog deleteDialog;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RecyclerViewUtil.initLine(this, binding.recyclerview, adapter, binding.swipeRefresh, false);
        ToolBarUtil.initToolbar(binding.superToolbar, this, true);
        initObseve();
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.queryAllType();
        viewModel.queryAllColockIn(count);
    }

    private void initObseve() {
        viewModel.getColockInTypes().observe(this, colockInItems -> updateAdapter());
        viewModel.getColockIns().observe(this, colockIns -> updateAdapter());
        adapter.getLoadMoreModule().setOnLoadMoreListener(() -> {
            count += 10;
            viewModel.queryAllColockIn(count);
        });
        adapter.addChildClickViewIds(R.id.swipe_delete);
        adapter.setOnItemChildClickListener((adapter, view, position) -> {
            LogUtil.d("点击："+view);
            if (view.getId() != R.id.swipe_delete){
                return;
            }
            deleteDialog = new MaterialDialog.Builder(ColockInListActivityVM.this)
                    .title("确定要删除此条打卡吗？")
                    .positiveText("确定")
                    .negativeText("取消")
                    .onNegative((dialog, which) -> {
                        dialog.dismiss();
                    })
                    .onPositive((dialog, which) -> {
                        ColockIn colockIn = (ColockIn) adapter.getItem(position);
                        viewModel.removeColockIn(colockIn);
                    })
                    .show();
        });
        // 删除成功后，更新列表
        viewModel.getState().observe(this, state -> {
            if (state == ColockInViewModel.STATE.DELETE_FINISH) {
                viewModel.queryAllColockIn(count);
            }
        });
    }

    private void updateAdapter() {
        List<ColockIn> colockIns = viewModel.getColockIns().getValue();
        if (colockIns == null || colockIns.size() == 0) {
            return;
        }
        List<ColockInType> types = viewModel.getColockInTypes().getValue();
        if (types == null || types.size() == 0) {
            return;
        }
        adapter.setType(types);
        RecyclerViewUtil.update(adapter, colockIns);
        if (colockIns.size()==count){
            adapter.getLoadMoreModule().loadMoreComplete();
            LogUtil.i("加载完成:"+colockIns.size());
        } else if (colockIns.size()<count){
            adapter.getLoadMoreModule().loadMoreEnd();
            LogUtil.i("加载到尾部："+colockIns.size());
        } else {
            LogUtil.w("数据大于计算？为什么？"+colockIns.size());;
        }
    }

    @Override
    protected void onDestroy() {
        if (deleteDialog!=null && deleteDialog.isShowing()){
            deleteDialog.dismiss();
        }
        super.onDestroy();
    }
}
