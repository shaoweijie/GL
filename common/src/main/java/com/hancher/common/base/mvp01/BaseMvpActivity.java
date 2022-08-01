package com.hancher.common.base.mvp01;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

import com.hancher.common.utils.android.LogUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/8/25 0025 0:01 <br/>
 * 描述 :
 */
public abstract class BaseMvpActivity<B extends ViewBinding, T extends BasePresenter>
        extends BaseActivity<B> {
    public T presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        try {
            Type superclass = getClass().getGenericSuperclass();
            Class<T> aClass = (Class<T>) ((ParameterizedType) superclass).getActualTypeArguments()[1];
            presenter = aClass.newInstance();
        } catch (Exception e) {
            LogUtil.e(e);
        }
        if(presenter != null){
            presenter.onCreated(this);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onStarted();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResumed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.onPaused();
    }

    @Override
    protected void onStop() {
        presenter.onStoped();
        super.onStop();
    }

    @Override
    protected void onDestroy(){
        if (presenter !=null) {
            presenter.onDestroyed();
        }
        super.onDestroy();
    }
}
