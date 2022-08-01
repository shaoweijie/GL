package com.hancher.common.base.mvp01;

/**
 * 作者：Hancher
 * 时间：2019/2/15.
 * 邮箱：ytu_shaoweijie@163.com
 * 版本：v1.0
 * <point>
 * 说明：
 */
public class BasePresenter<A>{
    /**
     * 可能是activity或者fragment
     */
    protected A mView;

    public BasePresenter(){}

    public void onCreated(A currentView) {
        this.mView = currentView;
    }

    public void onStarted() {
    }

    public void onResumed() {
    }

    public void onPaused() {
    }

    public void onStoped() {
    }

    public void onDestroyed() {
        if (mView !=null) {
            this.mView = null;
        }
    }
}
