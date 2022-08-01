package com.hancher.gamelife.bak.fragment;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.hancher.common.base.mvp01.BaseFragment;
import com.hancher.common.utils.android.LogUtil;
import com.hancher.gamelife.R;
import com.hancher.gamelife.bak.list.FieldFilterAdapter;
import com.hancher.gamelife.bak.presenter.FieldFilterPresenter;
import com.hancher.gamelife.databinding.FieldFilterFragmentBinding;

import java.util.List;

public class FieldFilterFragment extends BaseFragment<FieldFilterFragmentBinding, FieldFilterPresenter> {
    private OnFilterDataChangeListener listener;
    private FieldFilterAdapter adapter;
    private List<FieldFilterAdapter.FieldFilterItem> datas = null;

    @Override
    protected void beforeOnCreateView() {
        initView();
        updateData(null);
    }

    private void updateData(List<FieldFilterAdapter.FieldFilterItem> datas) {
        if (datas == null || datas.size() == 0){
            LogUtil.i("过滤数据空");
            binding.recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter = new FieldFilterAdapter(null);
            View notDataView = getLayoutInflater().inflate(R.layout.recyclerview_empty, binding.recyclerview, false);
            adapter.setEmptyView(notDataView);
            adapter.setUseEmpty(true);
        } else {
            LogUtil.i("过滤数据个数:", datas.size());
            adapter = new FieldFilterAdapter(datas);
            adapter.setAnimationEnable(true);
            binding.recyclerview.setAdapter(adapter);
        }
    }

    private void initView() {
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
//        //添加Android自带的分割线
//        binding.recyclerview.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
//        binding.recyclerview.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.HORIZONTAL));

        binding.filterText.setOnClickListener(v -> {
            LogUtil.i("展开过滤");
            binding.filterText.setVisibility(View.GONE);
            binding.filterBtn.setVisibility(View.VISIBLE);
            binding.recyclerview.setVisibility(View.VISIBLE);
            updateData(datas);
        });
        binding.filterBtn.setOnClickListener(v -> {
            LogUtil.i("提交过滤");
            binding.filterText.setVisibility(View.VISIBLE);
            binding.filterBtn.setVisibility(View.GONE);
            binding.recyclerview.setVisibility(View.GONE);
            if (listener!=null) {
                listener.onFilterDataChange(adapter.getData());
            }
        });
    }

    public void setListener(OnFilterDataChangeListener listener) {
        this.listener = listener;
    }

    public void setDatas(List<FieldFilterAdapter.FieldFilterItem> datas) {
        this.datas = datas;
    }

    public interface OnFilterDataChangeListener{
        void onFilterDataChange(List<FieldFilterAdapter.FieldFilterItem> FieldDatas);
    }
}