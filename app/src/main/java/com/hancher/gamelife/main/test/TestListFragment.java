package com.hancher.gamelife.main.test;

import android.content.ComponentName;
import android.content.Intent;

import com.hancher.common.base.mvp01.BaseFragment;
import com.hancher.common.utils.android.LogUtil;
import com.hancher.common.utils.android.RecyclerViewUtil;
import com.hancher.gamelife.databinding.AllListFragmentBinding;

import java.util.ArrayList;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/8/28 0028 20:16 <br/>
 * 描述 : 笔记列表
 */
public class TestListFragment extends BaseFragment<AllListFragmentBinding,TestListPresenter> {

    private TestListAdapter adapter = new TestListAdapter(new ArrayList<>());

    @Override
    protected void beforeOnCreateView() {
        RecyclerViewUtil.initGrid(getActivity(),binding.recyclerview,adapter, binding.swipeRefresh, false,2);
        adapter.setOnItemClickListener((adapter, view, position) -> {
            TestListAdapter.TestListItem item = (TestListAdapter.TestListItem) adapter.getItem(position);
            LogUtil.d("点击："+item);
            Intent intent = new Intent();
            intent.setComponent(new ComponentName(item.getPackageName(),item.getClassName()));
            startActivity(intent);
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.queryTestList(list -> RecyclerViewUtil.update(adapter,list));
    }
}