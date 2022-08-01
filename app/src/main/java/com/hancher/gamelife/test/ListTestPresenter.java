package com.hancher.gamelife.test;

import com.hancher.common.base.mvp01.BasePresenter;
import com.hancher.common.utils.android.AppListUtil;
import com.hancher.common.utils.android.AsyncUtils;
import com.hancher.common.utils.android.ToastUtil;

import java.util.ArrayList;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2022/6/19 0019 17:36 <br/>
 * 描述 :
 */
public class ListTestPresenter extends BasePresenter<ListTestActivity> {

    /**
     * 作者 : Hancher ytu_shaoweijie@163.com <br/>
     * 时间 : 2022/6/27 11:42 <br/>
     * 描述 : 延时3秒后，获取app包名信息，虽然此接口是系统接口，但是存在超时，因此异步处理此逻辑
     */
    public Disposable getPackageList(Consumer<ArrayList<MultiListTestAdapter.TestListItem>> onNext) {
        Disposable d = AsyncUtils.run(emitter -> {
            Thread.sleep(3000L);
            ArrayList<AppListUtil.AppBean> apps = AppListUtil.getPackageAppInfo(mView.getPackageManager());

            ArrayList<MultiListTestAdapter.TestListItem> list = new ArrayList<>();
            for (int i = 0; i < apps.size(); i++) {
                list.add(new MultiListTestAdapter.TestListItem()
                        .setTitle(apps.get(i).getName().toString())
                        .setPackageName(apps.get(i).getPackageName())
                        .setType(i % 2));
            }
            emitter.onNext(list);
            emitter.onComplete();
        }, onNext, throwable -> {
            ToastUtil.show(throwable.getMessage());
        });
        return d;
    }
}
