package com.hancher.common.utils.android;

import android.app.Activity;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.hancher.common.R;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.List;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/7/12 0012 22:30 <br/>
 * 描述 : 用于重复的初始化rv, 更新data<br/>
 * adapter.setOnItemClickListener 单击<br/>
 * adapter.setOnItemLongClickListener 长按<br/>
 * adapter.getLoadMoreModule().setOnLoadMoreListener 上滑<br/>
 * swipeRefresh.setOnRefreshListener 下拉
 */
public class RecyclerViewUtil {
    public static class Builder{
        Activity activity;
        RecyclerView recyclerView;
        BaseQuickAdapter adapter;
        TwinklingRefreshLayout refreshLayout;
        RefreshListenerAdapter refreshListener;
        boolean refresh = false;
        int col = 1;

        public RefreshListenerAdapter getRefreshListener() {
            return refreshListener;
        }

        public Builder setRefreshListener(RefreshListenerAdapter refreshListener) {
            this.refreshListener = refreshListener;
            return this;
        }

        public Activity getActivity() {
            return activity;
        }

        public Builder setActivity(Activity activity) {
            this.activity = activity;
            return this;
        }

        public RecyclerView getRecyclerView() {
            return recyclerView;
        }

        public Builder setRecyclerView(RecyclerView recyclerView) {
            this.recyclerView = recyclerView;
            return this;
        }

        public BaseQuickAdapter getAdapter() {
            return adapter;
        }

        public Builder setAdapter(BaseQuickAdapter adapter) {
            this.adapter = adapter;
            return this;
        }

        public TwinklingRefreshLayout getRefreshLayout() {
            return refreshLayout;
        }

        public Builder setRefreshLayout(TwinklingRefreshLayout refreshLayout) {
            this.refreshLayout = refreshLayout;
            return this;
        }

        public boolean isRefresh() {
            return refresh;
        }

        public Builder setRefresh(boolean refresh) {
            this.refresh = refresh;
            return this;
        }

        public int getCol() {
            return col;
        }

        public Builder setCol(int col) {
            this.col = col;
            return this;
        }

        public void build(){
            if (col == 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(activity));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(activity,col));
            }
            refreshLayout.setOnRefreshListener(refreshListener);
            View notDataView = activity.getLayoutInflater().inflate(R.layout.recyclerview_empty,
                    recyclerView, false);
            adapter.setEmptyView(notDataView);
            adapter.setUseEmpty(true);
            adapter.setAnimationEnable(true);
//            if (adapter instanceof LoadMoreModule) {
//                adapter.getLoadMoreModule().setEnableLoadMore(true);
//                //当自动加载开启，同时数据不满一屏时，是否继续执行自动加载更多(默认为true)
//                adapter.getLoadMoreModule().setEnableLoadMoreIfNotFullPage(false);
//                adapter.getLoadMoreModule().setAutoLoadMore(true);
//            }
            recyclerView.setAdapter(adapter);
//            if (swipeRefresh!=null) {
//                swipeRefresh.setEnabled(isRefresh);
//                swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
//            }
        }

    }
    /**
     * 初始化垂直列表
     * @param activity 活动
     * @param recyclerview 列表
     * @param adapter 数据
     * @param swipeRefresh 下拉加载
     * @param refresh 刷新
     */
    public static void initLine(Activity activity, RecyclerView recyclerview, BaseQuickAdapter adapter,
                                SwipeRefreshLayout swipeRefresh, boolean refresh) {
        recyclerview.setLayoutManager(new LinearLayoutManager(activity));
        init(activity, recyclerview, adapter, swipeRefresh, refresh);
    }

    /**
     * 初始化表格列表
     * @param activity
     * @param recyclerview
     * @param swipeRefresh
     * @param adapter
     * @param col
     */
    public static void initGrid(Activity activity, RecyclerView recyclerview, BaseQuickAdapter adapter,
                                SwipeRefreshLayout swipeRefresh, boolean refresh, int col) {
        recyclerview.setLayoutManager(new GridLayoutManager(activity,col));
        init(activity, recyclerview, adapter, swipeRefresh, refresh);
    }

    private static void init(Activity activity, RecyclerView recyclerview, BaseQuickAdapter adapter,
                             SwipeRefreshLayout swipeRefresh, boolean isRefresh) {
        View notDataView = activity.getLayoutInflater().inflate(R.layout.recyclerview_empty,
                recyclerview, false);
        adapter.setEmptyView(notDataView);
        adapter.setUseEmpty(true);
        adapter.setAnimationEnable(true);
        if (adapter instanceof LoadMoreModule) {
            adapter.getLoadMoreModule().setEnableLoadMore(true);
            //当自动加载开启，同时数据不满一屏时，是否继续执行自动加载更多(默认为true)
            adapter.getLoadMoreModule().setEnableLoadMoreIfNotFullPage(false);
            adapter.getLoadMoreModule().setAutoLoadMore(true);
        }
        recyclerview.setAdapter(adapter);
        if (swipeRefresh!=null) {
            swipeRefresh.setEnabled(isRefresh);
            swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        }
    }

    /**
     * 更新rw 数据
     * @param adapter
     * @param datas
     */
    public static void update(BaseQuickAdapter adapter, List datas) {
        int oldSize = 0;
        try {
            oldSize = adapter.getData().size();
        } catch (Exception ignored) {
        }
        int newSize = datas.size();
        LogUtil.d("列表数据个数:", oldSize, "->", newSize);

        adapter.getData().clear();
        adapter.addData(datas);

        if (newSize == 0) {
            adapter.notifyItemRangeRemoved(0, oldSize);
        } else if (oldSize > newSize) {
            adapter.notifyItemRangeRemoved(newSize, oldSize - newSize);
            adapter.notifyItemRangeChanged(0, newSize);
        } else if (newSize > oldSize) {
            adapter.notifyItemRangeInserted(oldSize, newSize - oldSize);
            adapter.notifyItemRangeChanged(0, oldSize);
        } else {
            adapter.notifyItemRangeChanged(0, oldSize);
        }

        if (adapter instanceof LoadMoreModule && adapter.getLoadMoreModule().isLoading()) {
            adapter.getLoadMoreModule().loadMoreComplete();
        }
    }
}
