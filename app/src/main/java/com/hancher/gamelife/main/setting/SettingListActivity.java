package com.hancher.gamelife.main.setting;

import android.content.Intent;

import com.hancher.common.activity.WebActivity;
import com.hancher.common.base.mvvm02.BaseActivity;
import com.hancher.common.utils.android.ExistUtil;
import com.hancher.common.utils.android.RecyclerViewUtil;
import com.hancher.common.utils.android.ToolBarUtil;
import com.hancher.gamelife.databinding.SettingListActivityBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/7/18 0018 18:08 <br/>
 * 描述 : 设置列表
 */
public class SettingListActivity extends BaseActivity<SettingListActivityBinding> {

    private SettingListAdapter adapter;

    @Override
    protected void initListener() {
        adapter.setOnItemClickListener((adapter, view, position) -> {
            SettingListAdapter.SettingListItem item = (SettingListAdapter.SettingListItem) adapter.getItem(position);
            if (position == adapter.getData().size() - 1) {
//                setLogout();
//                exitApp();
//                MainApplication.finish();
                ExistUtil.restartApplication(this);
                return;
            }
            startActivity(item.getIntent());
        });
    }

    @Override
    protected void initView() {
        ToolBarUtil.initToolbar(binding.superToolbar, this, true);
        adapter = new SettingListAdapter(getAllSettings());
        RecyclerViewUtil.initLine(this, binding.recyclerview, adapter, null, false);
    }

    public List<SettingListAdapter.SettingListItem> getAllSettings() {
        List<SettingListAdapter.SettingListItem> datas = new ArrayList<>();
//        // 存储管理
//        SettingListItem fileSetting = new SettingListItem(SettingListItem.ARROW)
//                .setName("存储管理")
//                .setHeadLine(true)
//                .setIntent(new Intent(this, FileSettingActivity.class));
//        datas.add(fileSetting);
        // 问题反馈
        SettingListAdapter.SettingListItem conntactMe = new SettingListAdapter.SettingListItem(SettingListAdapter.SettingListItem.ARROW)
                .setName("问题反馈")
                .setHeadLine(true)
                .setIntent(new Intent(this, ContactMeActivity.class));
        datas.add(conntactMe);
        // 隐私政策
        Intent privacyIntent = new Intent(this, WebActivity.class);
        privacyIntent.putExtra(WebActivity.URL, "http://hancher57.3vhost.net/privacy/privacy.html");
        privacyIntent.putExtra(WebActivity.TITLE, "隐私政策");
        SettingListAdapter.SettingListItem privacySetting = new SettingListAdapter.SettingListItem(SettingListAdapter.SettingListItem.ARROW)
                .setName("隐私政策")
                .setHeadLine(true)
                .setIntent(privacyIntent);
        datas.add(privacySetting);
        // 关于
        SettingListAdapter.SettingListItem aboutSetting = new SettingListAdapter.SettingListItem(SettingListAdapter.SettingListItem.ARROW)
                .setName("关于")
                .setIntent(new Intent(this, AboutActivity.class));
        datas.add(aboutSetting);
        // 退出
        SettingListAdapter.SettingListItem exitSetting = new SettingListAdapter.SettingListItem(SettingListAdapter.SettingListItem.DEFAULT)
                .setName("退出")
                .setHeadLine(true)
                .setFootLine(true);
        datas.add(exitSetting);
        return datas;
    }
}
