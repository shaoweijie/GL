package com.hancher.common.base.mvp01;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import com.hancher.common.utils.android.LogUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 作者：Hancher
 * 时间：2020/1/2.
 * 邮箱：ytu_shaoweijie@163.com
 * 版本：v1.0
 * <p>
 * 说明：自动绑定布局，自动创建presenter
 */
public abstract class BaseFragment<B extends ViewBinding, T extends BasePresenter> extends Fragment {
    protected T presenter;
    protected B binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        try {
            Type superclass = getClass().getGenericSuperclass();
            Class<?> aClass = (Class<?>) ((ParameterizedType) superclass).getActualTypeArguments()[0];
            Method method = aClass.getDeclaredMethod("inflate", LayoutInflater.class, ViewGroup.class, boolean.class);
            binding = (B) method.invoke(null, getLayoutInflater(), container, false);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            LogUtil.e(e);
        }
        try {
            Type superclass = getClass().getGenericSuperclass();
            Class<T> aClass = (Class<T>) ((ParameterizedType) superclass).getActualTypeArguments()[1];
            presenter =  aClass.newInstance();
        } catch (Exception e) {
            LogUtil.e(e);
        }
        if (presenter != null) {
            presenter.onCreated(this);
        }
        beforeOnCreateView();
        return binding.getRoot();
    }

    protected abstract void beforeOnCreateView();

    @Override
    public void onStart() {
        super.onStart();
        presenter.onStarted();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResumed();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPaused();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.onStoped();
    }

    @Override
    public void onDestroyView() {
        if (presenter != null) {
            presenter.onDestroyed();
        }
        super.onDestroyView();
    }
}
