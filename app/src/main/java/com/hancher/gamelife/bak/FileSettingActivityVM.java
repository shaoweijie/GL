package com.hancher.gamelife.bak;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.hancher.common.base.mvvm01.VMBaseActivity;
import com.hancher.common.utils.android.LogUtil;
import com.hancher.common.utils.endurance.PreferenceUtil;
import com.hancher.gamelife.databinding.SettingListActivityBinding;
import com.hancher.gamelife.main.setting.SettingListAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.hancher.gamelife.bak.ImageSaveUtil.SAVE_LOCAL;
import static com.hancher.gamelife.bak.ImageSaveUtil.SAVE_SERVER;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/3/16 17:32 <br/>
 * 描述 : 配置文件存储方式
 */
public class FileSettingActivityVM extends VMBaseActivity<SettingListActivityBinding, FileSettingViewModel>
        implements OnItemClickListener {
    private SettingListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActionBar();
        initRecyclerView();
    }

    private void initActionBar() {
        setSupportActionBar(binding.superToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void initRecyclerView() {
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SettingListAdapter(getAllSettings());
        adapter.setAnimationEnable(true);
        adapter.setUseEmpty(true);
        adapter.setOnItemClickListener(this);
        binding.recyclerview.setAdapter(adapter);
    }

    private List<SettingListAdapter.SettingListItem> getAllSettings() {
        List<SettingListAdapter.SettingListItem> datas = new ArrayList<>();
        datas.add(new SettingListAdapter.SettingListItem(SettingListAdapter.SettingListItem.CHECKBOX)
                .setName("图片本地存储")
                .setCheckBoxCheck(PreferenceUtil.getBoolean(SAVE_LOCAL, true))
                .setHeadLine(true));

        datas.add(new SettingListAdapter.SettingListItem(SettingListAdapter.SettingListItem.CHECKBOX)
                .setCheckBoxCheck(PreferenceUtil.getBoolean(SAVE_SERVER, true))
                .setName("图片服务器存储")
                .setFootLine(true));

        return datas;
    }

    @Override
    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
        if (position == 0) {
            boolean saveLocal = PreferenceUtil.getBoolean(SAVE_LOCAL, true);
            PreferenceUtil.setValue(SAVE_LOCAL, !saveLocal);
            saveLocal = PreferenceUtil.getBoolean(SAVE_LOCAL, true);
            LogUtil.d("本地保存修改为", saveLocal);
            ((SettingListAdapter.SettingListItem) adapter.getData().get(position)).setCheckBoxCheck(saveLocal);
        } else {
            boolean saveServer = PreferenceUtil.getBoolean(SAVE_SERVER, true);
            PreferenceUtil.setValue(SAVE_SERVER, !saveServer);
            saveServer = PreferenceUtil.getBoolean(SAVE_SERVER, true);
            LogUtil.d("远程保存修改为", saveServer);
            ((SettingListAdapter.SettingListItem) adapter.getData().get(position)).setCheckBoxCheck(saveServer);
        }
        adapter.notifyItemRangeChanged(position, 1);
    }
}
