package com.hancher.gamelife.main.me;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.hancher.common.activity.WebActivity;
import com.hancher.common.base.mvvm02.BaseFragment;
import com.hancher.common.utils.android.AssetUtil;
import com.hancher.common.utils.android.ImageUtil;
import com.hancher.common.utils.android.ToastUtil;
import com.hancher.gamelife.R;
import com.hancher.gamelife.databinding.MeFragmentBinding;
import com.hancher.gamelife.test.ErrorActivity;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleWithBorderTransformation;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/8/27 0027 21:50 <br/>
 * 描述 : 第二版主页我
 */
public class MeFragmentVM extends BaseFragment<MeFragmentBinding> {

    private MeAdapter adapter;
    private MeAdapter adapter2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        initView();
        initListener();
        return view;
    }

    private void initListener() {
        if (getContext() == null){
            return;
        }
        View.OnClickListener jumpModify = v -> {
            // TODO: swj: 2021/8/28 0028 跳转到设置个人信息界面
            ToastUtil.show("跳转到设置个人信息界面，正在开发");
        };
        binding.hHead.setOnClickListener(jumpModify);
        binding.userName.setOnClickListener(jumpModify);
        binding.userVal.setOnClickListener(jumpModify);
        binding.imageQrCode.setOnClickListener(jumpModify);

        OnItemClickListener listItemClickListener = (adapter, view, position) -> {
            MeAdapter.MeItem data = (MeAdapter.MeItem) adapter.getItem(position);
            if (data.getIntent() == null){
                ToastUtil.show("功能开发中...");
                return;
            }
            startActivity(data.getIntent());
        };
        adapter.setOnItemClickListener(listItemClickListener);
        adapter2.setOnItemClickListener(listItemClickListener);
    }

    private void initView() {
        if (getContext() == null){
            return;
        }

        Bitmap image = AssetUtil.getImage565(getContext(), "zdy.png");
        if (image != null){
            Bitmap image2 = ImageUtil.getFaceBitmapByClip(image);
            //高斯模糊
            Glide.with(this).load(image)
                    .apply(RequestOptions.bitmapTransform(new BlurTransformation(10,2)))
                    .into(binding.hBack);
            // 圆形剪裁
            Glide.with(this).load(image2)
                    .apply(RequestOptions.bitmapTransform(new CropCircleWithBorderTransformation()))
                    .into(binding.hHead);
        }

        adapter = new MeAdapter(updateSettingList1());
        binding.recyclerview.setLayoutManager(new GridLayoutManager(getContext(), 4));
        binding.recyclerview.setAdapter(adapter);

        adapter2 = new MeAdapter(updateSettingList2());
        binding.recyclerview2.setLayoutManager(new GridLayoutManager(getContext(), 4));
        binding.recyclerview2.setAdapter(adapter2);


//        binding.imageSetting.setOnClickListener(v -> startActivity(new Intent(getActivity(), SettingListActivity.class)));
    }

    public ArrayList<MeAdapter.MeItem> updateSettingList1() {
        ArrayList<MeAdapter.MeItem> data = new ArrayList<>();
        data.add(new MeAdapter.MeItem(MeAdapter.MeItem.SETTING, "加密解密", new Intent(getContext(), EncryptActivity.class), R.drawable.item_encrypt));
        data.add(new MeAdapter.MeItem(MeAdapter.MeItem.SETTING, "备份数据", new Intent(getContext(), BackupActivity.class), R.drawable.item_backup));

        Intent taifengIntent = WebActivity.getIntent(getActivity(),"http://typhoon.zjwater.gov.cn/wap.htm","台风轨迹");
        data.add(new MeAdapter.MeItem(MeAdapter.MeItem.SETTING, "台风轨迹", taifengIntent, R.drawable.item_cartoon));

        Intent hefeng = WebActivity.getIntent(getActivity(),"https://www.qweather.com/","和风天气");
        data.add(new MeAdapter.MeItem(MeAdapter.MeItem.SETTING, "和风天气", hefeng, R.drawable.item_lunar));

        Intent yiqing = WebActivity.getIntent(getActivity(),"https://voice.baidu.com/act/newpneumonia/newpneumonia/?from=osari_aladin_banner","国内疫情");
        data.add(new MeAdapter.MeItem(MeAdapter.MeItem.SETTING, "国内疫情", yiqing, R.drawable.item_master));
        Intent ytyiqing = WebActivity.getIntent(getActivity(),"https://voice.baidu.com/act/newpneumonia/newpneumonia/?from=osari_aladin_banner&city=%E5%B1%B1%E4%B8%9C-%E7%83%9F%E5%8F%B0","烟台疫情");
        data.add(new MeAdapter.MeItem(MeAdapter.MeItem.SETTING, "烟台疫情", ytyiqing, R.drawable.item_master));
        Intent qdyiqing = WebActivity.getIntent(getActivity(),"https://voice.baidu.com/act/newpneumonia/newpneumonia/?from=osari_aladin_banner&city=%E5%B1%B1%E4%B8%9C-%E9%9D%92%E5%B2%9B","青岛疫情");
        data.add(new MeAdapter.MeItem(MeAdapter.MeItem.SETTING, "青岛疫情", qdyiqing, R.drawable.item_master));
        return data;
    }

    public ArrayList<MeAdapter.MeItem> updateSettingList2() {
        ArrayList<MeAdapter.MeItem> data = new ArrayList<>();
        data.add(new MeAdapter.MeItem(MeAdapter.MeItem.SETTING, "错误测试", new Intent(getContext(), ErrorActivity.class), R.drawable.item_repair));
        return data;
    }
}