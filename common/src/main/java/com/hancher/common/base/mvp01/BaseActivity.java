package com.hancher.common.base.mvp01;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

import com.hancher.common.utils.android.LogUtil;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/8/25 0025 0:01 <br/>
 * 描述 :
 */
public abstract class BaseActivity<B extends ViewBinding>
        extends AppCompatActivity {

    protected B binding;
    protected Disposable d;
    protected List<Disposable> disposableList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        StateBarUtil.white(this);

        //bing 反射 200ms
        try {
            Type superclass = getClass().getGenericSuperclass();
            Class<B> aClass = (Class<B>) ((ParameterizedType) superclass).getActualTypeArguments()[0];
            Method method = aClass.getDeclaredMethod("inflate", LayoutInflater.class);
            binding = (B) method.invoke(null, getLayoutInflater());
            setContentView(binding.getRoot());
        } catch (Exception e) {
            LogUtil.e(e);
        }

        initView();
        initListener();
    }

    protected void initListener() {

    }

    protected void initView() {

    }

    @Override
    protected void onDestroy() {
        if (d!=null && !d.isDisposed()){
            d.dispose();
        }
        for(Disposable item : disposableList){
            if (item!=null && !item.isDisposed()){
                item.dispose();
            }
        }
        super.onDestroy();
    }
}
