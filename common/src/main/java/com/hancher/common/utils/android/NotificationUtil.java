package com.hancher.common.utils.android;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.core.app.NotificationCompat;

import com.hancher.common.R;

import java.util.Arrays;
import java.util.List;

/**
 * 作者：Hancher
 * 时间：2020/2/18.
 * 邮箱：ytu_shaoweijie@163.com
 * 版本：v1.0
 * <p>
 * 说明：先createAllNotificationChannels创建channel，使用五种类型的channel
 */
public class NotificationUtil {
    public final static String HIGH_CHANNEL = "hight";
    public final static String DEFAULT_CHANNEL = "default";
    public final static String LOW_CHANNEL = "low";
    public final static String MIN_CHANNEL = "min";
    public final static String MEDIA_CHANNEL = "media";
    public final static String[] CHANNELS = new String[]{
            HIGH_CHANNEL, DEFAULT_CHANNEL, LOW_CHANNEL, MIN_CHANNEL,
            MEDIA_CHANNEL
    };

    /**
     * 创建之前，必须先创建通道
     *
     * @param context
     */
    public static void createChannels(Context context) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.O) {
            LogUtil.d("版本小于Android8，不用创建通道");
            return;
        }

        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (nm == null) {
            LogUtil.w("NotificationManager null");
            return;
        }

        boolean needCreate = false;
        for (int i = 0; i < CHANNELS.length; i++) {
            if (nm.getNotificationChannel(CHANNELS[i]) == null) {
                needCreate = true;
                break;
            }
        }
        if (!needCreate) {
            LogUtil.d("通道以创建，可直接使用");
            return;
        }

        //媒体特殊通道：无声音、无振动、默认重要级别
        NotificationChannel mediaChannel = new NotificationChannel(
                MEDIA_CHANNEL, "媒体频道通知", NotificationManager.IMPORTANCE_DEFAULT);
        mediaChannel.setSound(null, null);
        mediaChannel.setVibrationPattern(null);
        //四个等级的通道
        NotificationChannel highChannel = new NotificationChannel(
                HIGH_CHANNEL, "紧急频道通知", NotificationManager.IMPORTANCE_HIGH);
        NotificationChannel importanceChannel = new NotificationChannel(
                DEFAULT_CHANNEL, "重要频道通知", NotificationManager.IMPORTANCE_DEFAULT);
        NotificationChannel defaultChannel = new NotificationChannel(
                LOW_CHANNEL, "默认频道通知", NotificationManager.IMPORTANCE_LOW);
        NotificationChannel minChannel = new NotificationChannel(
                MIN_CHANNEL, "最低频道通知", NotificationManager.IMPORTANCE_MIN);

        List<NotificationChannel> channels = Arrays.asList(highChannel, importanceChannel,
                defaultChannel, minChannel, mediaChannel);
        nm.createNotificationChannels(channels);
    }

    public static void sendNotification(Context context, String title, String content, int notifyId) {
        sendNotification(context, title, content, notifyId, null);
    }

    /**
     * 发送通知
     *
     * @param context
     * @param title
     * @param content
     * @param notifyId
     */
    public static void sendNotification(Context context, String title, String content, int notifyId, Bitmap bigIcon) {
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (nm == null) {
            LogUtil.w("NotificationManager null");
            return;
        }

        if (bigIcon == null) {
            bigIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.notification);
        }

        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.O) {
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, MIN_CHANNEL)
                    .setSmallIcon(android.R.drawable.stat_notify_chat)
                    .setContentTitle(title)
                    .setContentText(content)
//                    .setTicker("notification ticker")
//                    .setPriority(1000)
//                    .setAutoCancel(true)
//                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
//                    .setNumber(3)
//                    .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
//                    .setContentIntent(pendingResult)
//                    .setOngoing(true)
                    ;

            if (bigIcon != null) {
                mBuilder.setLargeIcon(bigIcon);
            }

            Notification notification = mBuilder.build();
            nm.notify(notifyId, notification);
        } else {

//        //创建点击通知时发送的广播
//        Intent intent = new Intent(context,NotificationService.class);
//        intent.setAction(ACTION_SIMPLE);
//        PendingIntent pi = PendingIntent.getService(context,0,intent,0);
//        //创建删除通知时发送的广播
//        Intent deleteIntent = new Intent(context,NotificationService.class);
//        deleteIntent.setAction(ACTION_DELETE);
//        PendingIntent deletePendingIntent = PendingIntent.getService(context,0,deleteIntent,0);
            //创建通知
            Notification.Builder nb = new Notification.Builder(context, NotificationUtil.MIN_CHANNEL)
                    //设置通知左侧的小图标
                    .setSmallIcon(android.R.drawable.stat_notify_chat)
                    //设置通知标题
                    .setContentTitle(title)
                    //设置通知内容
                    .setContentText(content)
                    //设置点击通知后自动删除通知
                    .setAutoCancel(true)
                    //设置显示通知时间
                    .setShowWhen(true)
//                //设置通知右侧的大图标
//                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.mipmap.ic_notifiation_big))
//                //设置点击通知时的响应事件
//                .setContentIntent(pi)
//                //设置删除通知时的响应事件
//                .setDeleteIntent(deletePendingIntent)
                    ;

            if (bigIcon != null) {
                nb.setLargeIcon(bigIcon);
            }

            //发送通知
            Notification notification = nb.build();
            nm.notify(notifyId, notification);
        }
    }

    public void cannelNotification(Context context, int notifyId) {
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (nm == null) {
            LogUtil.w("NotificationManager null");
            return;
        }

        nm.cancel(notifyId);
    }
}
