package com.hancher.gamelife.test;

import android.os.Handler;

import com.hancher.common.base.mvp01.BaseMvpActivity;
import com.hancher.common.utils.android.LogUtil;
import com.hancher.common.utils.android.RecyclerViewUtil;
import com.hancher.common.utils.android.ToastUtil;
import com.hancher.gamelife.api.ListTestApi;
import com.hancher.gamelife.databinding.ActivityListTestBinding;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.ArrayList;

import io.reactivex.disposables.Disposable;

public class ListTestActivity extends BaseMvpActivity<ActivityListTestBinding, ListTestPresenter> {
    private MultiListTestAdapter adapter = new MultiListTestAdapter(new ArrayList<>());
    private RefreshListenerAdapter refreshListener = new RefreshListenerAdapter() {
        @Override
        public void onRefresh(TwinklingRefreshLayout refreshLayout) {
            super.onRefresh(refreshLayout);
            LogUtil.i("下拉开始");
            new Handler().postDelayed(() -> {
                LogUtil.i("下拉完成");
                refreshLayout.finishRefreshing();
            },2000);
        }

        @Override
        public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
            super.onLoadMore(refreshLayout);
            LogUtil.i("加载更多开始");
            new Handler().postDelayed(() -> {
                LogUtil.i("加载更多结束");
                refreshLayout.finishLoadmore();
            },2000);
        }
    };


    @Override
    protected void initView() {
        super.initView();
        new RecyclerViewUtil.Builder()
                .setActivity(this)
                .setRecyclerView(binding.recyclerview)
                .setRefreshLayout(binding.refreshLayout)
                .setAdapter(adapter)
                .setRefreshListener(refreshListener)
                .setCol(5)
                .build();
    }

    @Override
    protected void initListener() {
        super.initListener();

        LogUtil.i("三秒后获取到应用列表");
        Disposable d1 = presenter.getPackageList(appBeans -> {
            LogUtil.i("异步获取到app数量: "+appBeans.size());
            RecyclerViewUtil.update(adapter, appBeans);
        });
        disposableList.add(d1);

        LogUtil.i("模拟请求网络连接，四秒后获取到bean对象");
        Disposable d2 = ListTestApi.getTestList().subscribe(testBeanResultBean ->
                        LogUtil.i("bean:%s", testBeanResultBean.toString()),
                throwable -> ToastUtil.showErr(throwable.getMessage()));
        disposableList.add(d2);
    }
}