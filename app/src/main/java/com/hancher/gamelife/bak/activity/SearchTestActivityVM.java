package com.hancher.gamelife.bak.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.hancher.common.activity.SearchActivity;
import com.hancher.common.base.mvvm01.VMBaseActivity;
import com.hancher.common.base.mvvm02.BaseViewModel;
import com.hancher.common.utils.android.LogUtil;
import com.hancher.gamelife.databinding.SearchTestActivityBinding;

import java.util.ArrayList;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/4/3 9:24 <br/>
 * 描述 : 搜索界面
 */
public class SearchTestActivityVM extends VMBaseActivity<SearchTestActivityBinding, BaseViewModel> {

    private final static int SEARCH_REQUEST_CODE = 123;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding.txtInfo.setOnClickListener(v -> {
            ArrayList<CharSequence> list = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                list.add("EEEEEEEEE");
            }
            list.add("AAAAAAA");
            list.add("BBBBBBBBBB");
            list.add("CCCCCCCCC");
            list.add("DDDDDD");
            Intent intent = new Intent(this, SearchActivity.class);
            intent.putCharSequenceArrayListExtra(SearchActivity.DATA, list);
            startActivityForResult(intent, SEARCH_REQUEST_CODE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.intent(data);
        if (requestCode == SEARCH_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                String filiter = data.getStringExtra(SearchActivity.RESUlT_DATA);
                LogUtil.d("filiter:", filiter);
                binding.txtInfo.setText(filiter);
            }
        }
    }
}
