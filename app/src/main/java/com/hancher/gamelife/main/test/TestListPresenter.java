package com.hancher.gamelife.main.test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hancher.common.base.mvp01.BasePresenter;
import com.hancher.common.utils.android.AssetUtil;
import com.hancher.common.utils.android.AsyncUtils;
import com.hancher.common.utils.android.LogUtil;
import com.hancher.common.utils.android.ToastUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;

import io.reactivex.functions.Consumer;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2022/6/19 0019 17:36 <br/>
 * 描述 :
 */
public class TestListPresenter extends BasePresenter<TestListFragment> {
    public void queryTestList(Consumer<ArrayList<TestListAdapter.TestListItem>> onNext) {
        AsyncUtils.run(emitter -> {
            String jsonStr = AssetUtil.getJson(mView.getContext(), "test_list.json");
            Type type = new TypeToken<ArrayList<TestListAdapter.TestListItem>>() {}.getType();
            Gson gson = new Gson();
            ArrayList<TestListAdapter.TestListItem> list = gson.fromJson(jsonStr, type);
            LogUtil.d("list:"+list);
            emitter.onNext(list);
            emitter.onComplete();
        }, onNext, throwable -> ToastUtil.showErr(throwable.getMessage()));
    }
}
