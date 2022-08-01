package com.hancher.common.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.multidex.MultiDex;

import com.hancher.common.service.BackgroundService;
import com.hancher.common.utils.android.ApkInfoUtil;
import com.hancher.common.utils.android.LogUtil;
import com.hancher.common.utils.android.NetConnectUtil;
import com.hancher.common.utils.android.PathUtil;
import com.tencent.mmkv.MMKV;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.EventBusException;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：Hancher
 * 时间：2020/1/9.
 * 邮箱：ytu_shaoweijie@163.com
 * 版本：v1.0
 * <p>
 * 说明：
 */
public abstract class BaseApplication extends Application {

    public static BaseApplication instance;
    public static BaseApplication getInstance(){
        return instance;
    }
    protected abstract void init();
    private static List<Activity> activities = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        String processName = ApkInfoUtil.getProcessName(this);
        LogUtil.i("进程名:", processName);
        if (!TextUtils.isEmpty(processName) && !processName.contains(":")) {
            instance = this;
            MMKV.initialize(this) ;
            PathUtil.initFilePath(this);
            NetConnectUtil.NetChangeReceiver.register(this);
            init();
            recordActivity();
//            CrashUtil.init((t, e) -> {
//                ToastUtil.showErr(e);
//                AsyncUtils.runCountdown(0L, 1L, 0L, 6000L, ExistUtil::exist);
//            });
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(new Intent(instance, BackgroundService.class));
            } else {
                startService(new Intent(instance, BackgroundService.class));
            }
        }
    }

    public void recordActivity(){
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
                activities.add(activity);
                try {
                    EventBus.getDefault().register(this);
                } catch (EventBusException err){
                    LogUtil.d(err.getMessage());
                }
            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {

            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {

            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {

            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {
                activities.remove(activity);
                try {
                    EventBus.getDefault().unregister(this);
                } catch (Exception exception){
                    LogUtil.e("EventBus unregister fail. ", exception);
                }
            }
        });
    }

    /**
     * 退出app
     */
    public static void finish(){
        for (Activity item : activities) {
            item.finish();
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // 多个dex
        MultiDex.install(this);
    }
}
