package com.hancher.common.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import com.hancher.common.R;
import com.hancher.common.utils.android.ApkInfoUtil;
import com.hancher.common.utils.android.ClipboardUtil;
import com.hancher.common.utils.android.DensityUtil;
import com.hancher.common.utils.android.LogUtil;
import com.hancher.common.utils.android.MmkvUtil;
import com.hancher.common.utils.android.NetConnectUtil;
import com.hancher.common.utils.android.NotificationUtil;
import com.hancher.common.utils.android.PhoneInfoUtil;
import com.hancher.common.utils.android.SimUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.EventBusException;

public class BackgroundService extends Service {
    public BackgroundService() {
    }
    IBinder mBinder;
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startCollectInfo();
        startListener();
        return START_STICKY;
    }

    private void startListener() {
        ClipboardUtil.startListener();
        NetConnectUtil.startListener();
    }

    private void startCollectInfo() {
        ApkInfoUtil.collect();
        DensityUtil.collect();
        PhoneInfoUtil.collect();
        SimUtil.collect();
        LogUtil.d(MmkvUtil.getAllMmkv());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.i("register eventbus");
        try {
            EventBus.getDefault().register(this);
        } catch (EventBusException err){
            LogUtil.d(err.getMessage());
        }
        startNotification();
    }
    public void startNotification() {
        if (Build.VERSION.SDK_INT< Build.VERSION_CODES.O){
            return;
        }
        //创建通知
        NotificationUtil.createChannels(this);
//        NotificationUtil.sendNotification(this, "后台服务运行中","后台服务利用通知，获取位置，更新桌面插件",1);
        Notification.Builder nb = new Notification.Builder(getApplicationContext(), NotificationUtil.MIN_CHANNEL)
                //设置通知左侧的小图标
                .setSmallIcon(R.drawable.icon_gcoding)
                //设置通知标题
                .setContentTitle("后台服务运行中")
                //设置通知内容
                .setContentText("后台服务利用通知，获取位置，更新桌面插件")
                //设置点击通知后自动删除通知
                .setAutoCancel(true)
                //设置显示通知时间
                .setShowWhen(true);
        Notification notification = nb.build();
        startForeground(1,notification);
    }

    @Override
    public void onDestroy() {
        LogUtil.i("unregister eventbus");
        try {
            EventBus.getDefault().unregister(this);
        } catch (Exception exception){
            LogUtil.e("EventBus unregister fail. ", exception);
        }
        super.onDestroy();
    }
}