package com.hancher.common.base.mvvm02;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

import com.hancher.common.utils.android.LogUtil;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

import io.reactivex.disposables.Disposable;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/3/11 9:05 <br/>
 * 描述 :
 */
public abstract class BaseActivity<B extends ViewBinding> extends AppCompatActivity {

    protected B binding;
    protected Disposable d;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        StateBarUtil.white(this);
        //bing 反射 200ms
        try {
            ParameterizedType superclass = (ParameterizedType) getClass().getGenericSuperclass();
            Class<B> aClass = (Class<B>) superclass.getActualTypeArguments()[0];
            Method method = aClass.getDeclaredMethod("inflate", LayoutInflater.class);
            binding = (B) method.invoke(null, getLayoutInflater());
//            DataBindingUtil.setContentView();
            setContentView(binding.getRoot());
        } catch (Exception e) {
            LogUtil.e(e);
        }
        initViewModel();
        initView();
        initListener();
    }

    protected void initListener() {

    }

    protected void initViewModel() {

    }

    protected void initView() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        initPermission();
        initData();
    }

    protected void initPermission() {
    }

    protected void initData() {
    }

    @Override
    protected void onDestroy() {
        if (d!=null && !d.isDisposed()){
            d.dispose();
        }
        super.onDestroy();
    }
}
