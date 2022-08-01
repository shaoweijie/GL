package com.hancher.common.base.mvvm01;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
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
 * 说明：自动绑定布局，自动创建vm
 * @deprecated
 */
public abstract class VMBaseFragment<B extends ViewBinding, M extends ViewModel> extends Fragment {
    protected M viewModel;
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

        //viewModel 反射
        try {
            Type superclass = getClass().getGenericSuperclass();
            Class<M> mClass = (Class<M>) ((ParameterizedType) superclass).getActualTypeArguments()[1];
            viewModel = ViewModelProviders.of(this).get(mClass);
        } catch (Exception e) {
            LogUtil.e(e);
        }
        beforeOnCreateView();
        return binding.getRoot();
    }

    protected void beforeOnCreateView() {

    }

}
