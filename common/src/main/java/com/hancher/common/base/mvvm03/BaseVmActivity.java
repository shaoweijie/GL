package com.hancher.common.base.mvvm03;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.hancher.common.utils.android.LogUtil;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

import io.reactivex.disposables.Disposable;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/3/11 9:05 <br/>
 * 描述 : 当只有一个vm时，我们选择使用这个基类
 */
public abstract class BaseVmActivity<B extends ViewDataBinding, M extends ViewModel>
        extends BaseActivity<B> {

    protected M viewModel;
    protected B binding;
    protected Disposable d;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        StateBarUtil.white(this);
        //bing 反射 200ms
        try {
            ParameterizedType superclass = (ParameterizedType) getClass().getGenericSuperclass();

            // 获取泛型B类 ViewDataBinding
            Class<B> ViewDataBindingClass = (Class<B>) superclass.getActualTypeArguments()[0];
            // 反射方法 binding = ViewDataBinding.inflate(LayoutInflater layoutInflater)
            Method ViewDataBindingClassMethod = ViewDataBindingClass.getDeclaredMethod("inflate", ViewDataBindingClass);
            // 调用反射的上述方法，获得binding
            binding = (B) ViewDataBindingClassMethod.invoke(null, getLayoutInflater());
            setContentView(binding.getRoot());

            // 获取泛型M类 ViewModel
            Class<M> mClass = (Class<M>) ((ParameterizedType) superclass).getActualTypeArguments()[1];
            viewModel = new ViewModelProvider(this).get(mClass);

            // 获取 binding.setView(this) 方法
            DataBindingUtil.setContentView(this, getLayoutId());
        } catch (Exception e) {
            LogUtil.e(e);
        }
        initViewModel();
        initView();
        initListener();
    }

    protected abstract int getLayoutId();


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
