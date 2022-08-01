package com.hancher.gamelife.main.tv.app;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hancher.common.base.mvvm01.VMBaseActivity;
import com.hancher.common.utils.android.LogUtil;
import com.hancher.common.utils.android.NetConnectUtil;
import com.hancher.common.utils.android.RecyclerViewUtil;
import com.hancher.common.utils.android.ToastUtil;
import com.hancher.common.utils.android.ToolBarUtil;
import com.hancher.gamelife.R;
import com.hancher.gamelife.databinding.TvListActivityBinding;
import com.hancher.gamelife.main.tv.setting.AboutActivityVM;

import java.util.ArrayList;
import java.util.List;

public class TvListActivityVM extends VMBaseActivity<TvListActivityBinding, TvListViewModel> {

    public static final int SPAN_COUNT = 4;
    private TvListAdapter adapter = new TvListAdapter(null);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ToolBarUtil.initToolbar(binding.superToolbar,this,false);
        RecyclerViewUtil.initGrid(this,binding.recyclerview,adapter,binding.swipeRefresh,false, SPAN_COUNT);
        initObserve();
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.readTvList(this, SPAN_COUNT);
        viewModel.crawlingYtTvList(SPAN_COUNT);
    }

    private void initObserve() {
        viewModel.getTvs().observe(this, tvListItems -> updateList());
        viewModel.getYts().observe(this, tvListItems -> updateList());
        adapter.setOnItemClickListener((adapter, view, position) -> {
            LogUtil.i(position, "/", adapter.getData().size());
            TvListAdapter.TvListItem data = (TvListAdapter.TvListItem) adapter.getData().get(position);
            if (data.getItemType() != TvListAdapter.TYPE_ITEM) {
                return;
            }
            if (!NetConnectUtil.NetChangeReceiver.isWifiConnected) {
                if (NetConnectUtil.NetChangeReceiver.isMobileConnected) {
                    ToastUtil.showErr("当前正在使用流量");
                } else {
                    ToastUtil.show("请先打开wifi");
                }
                return;
            }
            Intent intent = new Intent(this, PlayerActivityVM.class);
            intent.putExtra(PlayerActivityVM.TITLE, data.getTitle());
            intent.putExtra(PlayerActivityVM.URL, data.getUrl());
            startActivity(intent);
        });
        LogUtil.i("刷新列表完成");
    }

    private void updateList() {
        List<TvListAdapter.TvListItem> data = new ArrayList<>();
        List<TvListAdapter.TvListItem> yts = viewModel.getYts().getValue();
        List<TvListAdapter.TvListItem> tvs = viewModel.getTvs().getValue();
        if (yts!=null&&yts.size()>0){
            data.addAll(yts);
        }
        if (tvs!=null&&tvs.size()>0){
            data.addAll(tvs);
        }
        if (data.size()==0){
            return;
        }
        RecyclerViewUtil.update(adapter,data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.menu_item_setting){
            LogUtil.i("跳转到设置");
            startActivity(new Intent(this, AboutActivityVM.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public String[] getPermission() {
        return new String[]{Manifest.permission.INTERNET};
    }
}