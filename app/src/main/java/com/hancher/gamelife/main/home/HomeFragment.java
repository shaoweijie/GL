package com.hancher.gamelife.main.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;

import com.hancher.common.base.mvvm02.BaseFragment;
import com.hancher.common.utils.android.LogUtil;
import com.hancher.gamelife.R;
import com.hancher.gamelife.databinding.FragmentHomeBinding;
import com.hancher.gamelife.main.account.AccountActivityVM;
import com.hancher.gamelife.main.colockin.ColockInActivityVM;
import com.hancher.gamelife.main.human.BirthdayActivity;
import com.hancher.gamelife.main.human.CharactersActivity;
import com.hancher.gamelife.main.note.NoteListActivityVM;
import com.hancher.gamelife.main.tv.app.TvListActivityVM;

import java.util.ArrayList;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/8/27 0027 21:38 <br/>
 * 描述 : 主页表格主页
 */
public class HomeFragment extends BaseFragment<FragmentHomeBinding> {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        initList();
        return view;
    }

    private void initList() {
        binding.recyclerview.setLayoutManager(new GridLayoutManager(getContext(), 5));
//        添加Android自带的分割线
//        binding.recyclerview.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
//        binding.recyclerview.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.HORIZONTAL));
        ArrayList<HomeAdapter.HomeItem> items = getListData();
        LogUtil.i("主页数据个数:",items.size());
        HomeAdapter adapter = new HomeAdapter(items);
        adapter.setAnimationEnable(true);
        binding.recyclerview.setAdapter(adapter);
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            if (getContext() != null) {
                getContext().startActivity(getListData().get(position).getIntent());
            }
        });
    }

    public ArrayList<HomeAdapter.HomeItem> getListData() {
        ArrayList<HomeAdapter.HomeItem> datas = new ArrayList<>();
        datas.add(new HomeAdapter.HomeItem(HomeAdapter.ACTIVITY, "账户记录", new Intent(getContext(), AccountActivityVM.class), R.drawable.item_account));
        datas.add(new HomeAdapter.HomeItem(HomeAdapter.ACTIVITY, "笔记记录", new Intent(getContext(), NoteListActivityVM.class), R.drawable.item_note));
//        datas.add(new HomeAdapter.HomeItem(HomeAdapter.ACTIVITY, "加密解密", new Intent(getContext(), EncryptActivityD.class), R.drawable.item_encrypt));
//        datas.add(new HomeItem(HomeAdapter.ACTIVITY, "备份恢复", new Intent(getContext(), BackupActivity.class), R.drawable.item_backup));
        datas.add(new HomeAdapter.HomeItem(HomeAdapter.ACTIVITY, "观察人类", new Intent(getContext(), CharactersActivity.class), R.drawable.item_person));
//        datas.add(new HomeAdapter.HomeItem(HomeAdapter.ACTIVITY, "记账本", new Intent(getContext(), MoneyActivityD.class), R.drawable.item_money));
        datas.add(new HomeAdapter.HomeItem(HomeAdapter.ACTIVITY, "生日倒计时", new Intent(getContext(), BirthdayActivity.class), R.drawable.item_birthday));
        datas.add(new HomeAdapter.HomeItem(HomeAdapter.ACTIVITY, "打卡", new Intent(getContext(), ColockInActivityVM.class), R.drawable.colock_in));

        datas.add(new HomeAdapter.HomeItem(HomeAdapter.ACTIVITY, "电视", new Intent(getContext(), TvListActivityVM.class), R.drawable.icon_tv));

//        datas.add(new HomeItem(HomeAdapter.ACTIVITY, "数据库修复", new Intent(getContext(), RepairDbActivity.class), R.drawable.item_test));
//        datas.add(new HomeItem(HomeAdapter.ACTIVITY, "人生倒计时", new Intent(getContext(), CountdownActivity.class), R.drawable.item_test));
//        datas.add(new HomeItem(HomeAdapter.ACTIVITY, "自定义播放器", new Intent(getContext(), SurfaceVideoActivity.class), R.drawable.item_test));
//        datas.add(new HomeItem(HomeAdapter.ACTIVITY, "进度条", new Intent(getContext(), ProcessActivity.class), R.drawable.item_test));
//        datas.add(new HomeItem(HomeAdapter.ACTIVITY, "动画效果", new Intent(getContext(), BaseanimActivity.class), R.drawable.item_test));
//        datas.add(new HomeItem(HomeAdapter.ACTIVITY, "TCP客户端", new Intent(getContext(), TcpTestActivity.class), R.drawable.item_test));
//        datas.add(new HomeItem(HomeAdapter.ACTIVITY, "登录", new Intent(getContext(), LoginActivity.class), R.drawable.item_test));
//        datas.add(new HomeItem(HomeAdapter.ACTIVITY, "设置", new Intent(getContext(), SettingsActivity.class), R.drawable.item_test));
//        datas.add(new HomeItem(HomeAdapter.ACTIVITY, "人脸检测", new Intent(getContext(), FaceTestActivity.class), R.drawable.item_test));
//        datas.add(new HomeItem(HomeAdapter.ACTIVITY, "流布局弹窗", new Intent(getContext(), FlowDialogActivity.class), R.drawable.item_test));
//        datas.add(new HomeItem(HomeAdapter.ACTIVITY, "关于", new Intent(getContext(), AboutActivity.class), R.drawable.item_test));
//        Intent privacyIntent = new Intent(getContext(), WebActivity.class);
//        privacyIntent.putExtra(WebActivity.URL, "http://hancher57.3vhost.net/privacy/privacy.html");
//        privacyIntent.putExtra(WebActivity.TITLE, "隐私政策");
//        datas.add(new HomeItem(HomeAdapter.ACTIVITY, "隐私政策", privacyIntent, R.drawable.item_test));
//        datas.add(new HomeItem(HomeAdapter.ACTIVITY, "蓝牙测试", new Intent(getContext(), BtTestActivity.class), R.drawable.item_test));
//        datas.add(new HomeItem(HomeAdapter.ACTIVITY, "搜索测试", new Intent(getContext(), SearchTestActivity.class), R.drawable.item_test));
//        datas.add(new HomeItem(HomeAdapter.ACTIVITY, "定位测试", new Intent(getContext(), TestBaiduMapActivity.class), R.drawable.item_test));
//        datas.add(new HomeItem(HomeAdapter.ACTIVITY, "天气测试", new Intent(getContext(), TestHeWeatherActivity.class), R.drawable.item_test));
//        datas.add(new HomeItem(HomeAdapter.ACTIVITY, "天气图表", new Intent(getContext(), TestHeWeatherChartActivity.class), R.drawable.item_test));

        return datas;
    }
}