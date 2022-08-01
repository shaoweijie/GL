package com.hancher.gamelife.test;

import androidx.databinding.ObservableField;

import com.hancher.common.base.mvvm02.BaseViewModel;
import com.hancher.common.utils.android.AsyncUtils;
import com.hancher.common.utils.android.LogUtil;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/3/11 9:17 <br/>
 * 描述 : 登录操作
 */
public class MVVM2TestViewModel extends BaseViewModel {

    public ObservableField<String> content = new ObservableField<>();

    public void countdown() {
        AsyncUtils.runCountdown(10000L, new Observer<Long>() {
            @Override
            public void onSubscribe(@NotNull Disposable d) {
                LogUtil.d("1");
            }

            @Override
            public void onNext(@NotNull Long aLong) {
                LogUtil.d("1");
                content.set("time:"+aLong);
            }

            @Override
            public void onError(@NotNull Throwable e) {

                LogUtil.d("1");
            }

            @Override
            public void onComplete() {
                LogUtil.d("1");
                content.set("time:ok");
            }
        });
    }

}
