package com.hancher.common.base.mvvm03;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ViewDataBinding;

import com.hancher.common.utils.android.LogUtil;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

import io.reactivex.disposables.Disposable;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/3/11 9:05 <br/>
 * 描述 : 当vm不存在或者存在一个以上时，使用此基类
 */
public abstract class BaseActivity<B extends ViewDataBinding> extends AppCompatActivity {

    protected B binding;
    protected Disposable d;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            // getGenericSuperclass 获得带有泛型的父类
            // ParameterizedType 参数化类型，即泛型
            ParameterizedType superclass = (ParameterizedType) getClass().getGenericSuperclass();
            // 获取泛型B类 ViewDataBinding
            Class<B> aClass = (Class<B>) superclass.getActualTypeArguments()[0];
            // 反射方法 binding = ViewDataBinding.inflate(LayoutInflater layoutInflater)
            Method method = aClass.getDeclaredMethod("inflate", LayoutInflater.class);
            // 调用反射的上述方法，获得binding
            binding = (B) method.invoke(null, getLayoutInflater());

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
        if (d != null && !d.isDisposed()) {
            d.dispose();
        }
        super.onDestroy();
    }
}
