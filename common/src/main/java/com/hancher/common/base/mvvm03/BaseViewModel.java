package com.hancher.common.base.mvvm03;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/3/23 9:39 <br/>
 * 描述 : vm基类
 */
public class BaseViewModel extends ViewModel {
    private List<Disposable> disposableList = new ArrayList<>();

    @Override
    protected void onCleared() {
        for (Disposable item : disposableList) {
            if (item != null && !item.isDisposed()) {
                item.dispose();
            }
        }
        super.onCleared();
    }

    protected void addDisposable(Disposable d) {
        disposableList.add(d);
    }
}
