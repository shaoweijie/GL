package com.hancher.gamelife.bak.fragment;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.hancher.common.base.mvp01.BaseFragment;
import com.hancher.common.utils.android.LogUtil;
import com.hancher.gamelife.bak.list.ScrollingAdapter;
import com.hancher.gamelife.bak.presenter.ScrollingPresenter;
import com.hancher.gamelife.databinding.FragmentScrollingBinding;

import java.util.ArrayList;

/**
 * 现在回归大地你的思绪纷乱，是你的心灵在探究
 */
public class ScrollingFragment extends BaseFragment<FragmentScrollingBinding,ScrollingPresenter> implements OnItemClickListener {

    @Override
    protected void beforeOnCreateView() {
        initList();
    }

    private void initList() {
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        ArrayList<ScrollingAdapter.ScrollingItem> items = presenter.getListData();
        LogUtil.d("数据个数:",items.size()," fragment:"+this);
        ScrollingAdapter adapter = new ScrollingAdapter(items);
        adapter.setAnimationEnable(true);
        binding.recyclerview.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {

    }
}