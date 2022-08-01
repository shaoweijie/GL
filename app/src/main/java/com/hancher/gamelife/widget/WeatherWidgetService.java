package com.hancher.gamelife.widget;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.IBinder;
import android.widget.RemoteViews;

import com.baidu.location.BDLocation;
import com.hancher.common.third.mapbaidu.BdMapManager;
import com.hancher.common.third.mapbaidu.BdMapUtil;
import com.hancher.common.third.weatherhe.HeWeatherUtil;
import com.hancher.common.third.weatherhe.WeatherChartHelper;
import com.hancher.common.third.weatherhe.WeatherIconHelper;
import com.hancher.common.utils.android.AsyncUtils;
import com.hancher.common.utils.android.LogUtil;
import com.hancher.common.utils.android.NotificationUtil;
import com.hancher.common.utils.endurance.PreferenceUtil;
import com.hancher.common.utils.java.DateUtil;
import com.hancher.common.utils.java.TextUtil;
import com.hancher.gamelife.R;
import com.qweather.sdk.bean.base.Code;
import com.qweather.sdk.bean.geo.GeoBean;
import com.qweather.sdk.bean.weather.WeatherDailyBean;
import com.qweather.sdk.bean.weather.WeatherNowBean;
import com.qweather.sdk.view.QWeather;
import com.tencent.mmkv.MMKV;

import java.util.List;

import io.reactivex.disposables.Disposable;

public class WeatherWidgetService extends Service {
    private long mSubmitTime = 0;
    public Notification notification;
    private BDLocation location;
    private WeatherNowBean.NowBaseBean weatherNowBean;
    private List<WeatherDailyBean.DailyBean> weatherDailyBeanList;
    private Disposable disposable;
    private final static String WEATHER_SERVICE_LAST_SUBMIT_TIME = "weather_service_last_submit_time";

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.v("flags="+flags+", startId="+startId+", intent="+intent,", service=",this);
        if (System.currentTimeMillis() - PreferenceUtil.getLong(WEATHER_SERVICE_LAST_SUBMIT_TIME, 0) < 60 * 1000) {
            LogUtil.w("请求过于频繁，请保证间隔大于1分钟");
            return super.onStartCommand(intent, flags, startId);
        }
        mSubmitTime = System.currentTimeMillis();
        PreferenceUtil.setValue(WEATHER_SERVICE_LAST_SUBMIT_TIME, System.currentTimeMillis());
        startNotification();
        LogUtil.i("widget服务开始更新");
        HeWeatherUtil.init();
        updatePosition();
        return super.onStartCommand(intent, flags, startId);
    }

    public void startNotification() {
        if (Build.VERSION.SDK_INT< Build.VERSION_CODES.O){
            return;
        }
        //创建通知
        Notification.Builder nb = new Notification.Builder(getApplicationContext(), NotificationUtil.MIN_CHANNEL)
                //设置通知左侧的小图标
                .setSmallIcon(R.drawable.icon_weather)
                //设置通知标题
                .setContentTitle("后台服务运行中")
                //设置通知内容
                .setContentText("后台服务利用通知，获取位置，更新桌面插件")
                //设置点击通知后自动删除通知
                .setAutoCancel(true)
                //设置显示通知时间
                .setShowWhen(true);
        notification = nb.build();
        startForeground(1,notification);
    }

    private void updatePosition() {

        disposable = AsyncUtils.runCountdown(0L, 1L, 60000L, 0L,
                aLong -> LogUtil.d("onNext:"+aLong),
                () -> {
                    LogUtil.w("60秒没有定位到位置，停止定位");
                    updateWeatherWithDefaultWeather();
                });

        LogUtil.i("百度定位请求发出!!!");
        BdMapManager.getInstance().start(getApplicationContext(), BdMapManager.START_TYPE.CYCLE,
                bdLocation -> {
                    LogUtil.d("当前进程："+WeatherWidgetService.this);
                    if (WeatherWidgetService.this.disposable!=null && !WeatherWidgetService.this.disposable.isDisposed()){
                        WeatherWidgetService.this.disposable.dispose();
                    }
                    if(!BdMapUtil.isBDLocationSuccess(bdLocation)){
                        LogUtil.e("百度定位失败：",bdLocation.getLocType());
                        updateWeatherWithDefaultWeather();
                        return;
                    }
                    String positionStr = TextUtil.double2String(bdLocation.getLongitude()) + ","
                            + TextUtil.double2String(bdLocation.getLatitude());
                    LogUtil.i("百度定位成功："+positionStr);
//                    BaiduMapHelper.saveLocation(bdLocation);
                    setLocation(bdLocation);
                    updateWidgetView(false);
                    changePositionToStr(positionStr);
                });

//        BaiduMapHelper.getInstance().startOnce(new BDAbstractLocationListener() {
//            @Override
//            public void onReceiveLocation(BDLocation bdLocation) {
////                HancherLogUtil.v("当前进程："+WeatherWidgetService.this);
//                if (WeatherWidgetService.this.disposable!=null && !WeatherWidgetService.this.disposable.isDisposed()){
//                    WeatherWidgetService.this.disposable.dispose();
//                }
//                BaiduMapHelper.getInstance().stop();
//                if(!BaiduMapHelper.isBDLocationSuccess(bdLocation)){
//                    LogUtil.e("百度定位失败：",bdLocation.getLocType());
//                    updateWeatherWithDefaultWeather();
//                    return;
//                }
//                String positionStr = TextUtil.double2String(bdLocation.getLongitude()) + ","
//                        + TextUtil.double2String(bdLocation.getLatitude());
//                LogUtil.i("百度定位成功："+positionStr);
////                BaiduMapHelper.saveLocation(bdLocation);
//                setLocation(bdLocation);
//                updateWidgetView(false);
//                updateNowWeather(positionStr);
//            }
//        });
    }

    private void changePositionToStr(String positionStr){
        LogUtil.i("地理位置转化天气位置");
        QWeather.getGeoCityLookup(WeatherWidgetService.this, positionStr,
                new QWeather.OnResultGeoListener() {
                    @Override
                    public void onError(Throwable throwable) {
                        LogUtil.e("get location err:", throwable);
                    }

                    @Override
                    public void onSuccess(GeoBean geoBean) {
                        StringBuffer info = HeWeatherUtil.getGeoInfo(geoBean);
                        LogUtil.d("geoBean:"+info);
                        if (geoBean.getCode()!= Code.OK || geoBean.getLocationBean()==null
                                || geoBean.getLocationBean().size()==0){
                            LogUtil.d("location err");
                            updateWeatherWithDefaultWeather();
                            return;
                        }
                        MMKV.defaultMMKV().encode("WEATHER_LOCATION", geoBean.getLocationBean().get(0).getId());
                        updateNowWeather(geoBean.getLocationBean().get(0).getId());
                    }
                });
    }

    private void updateWeatherWithDefaultWeather() {
        String location = MMKV.defaultMMKV().decodeString("WEATHER_LOCATION","101120512");
        updateNowWeather(location);//烟台代码
    }

    public void updateNowWeather(String positionStr) {
        LogUtil.i("实时天气请求发出,实时位置：",positionStr);
        QWeather.getWeatherNow(WeatherWidgetService.this, positionStr,
                new QWeather.OnResultWeatherNowListener() {
                    @Override
                    public void onError(Throwable throwable) {
                        LogUtil.e("实时天气请求失败：",throwable);
                    }

                    @Override
                    public void onSuccess(WeatherNowBean weatherNowBean) {
                        if (!HeWeatherUtil.isBeanSuccess(weatherNowBean)){
                            LogUtil.e("实时天气请求失败：",weatherNowBean.getCode());
                            return;
                        }
                        LogUtil.i("实时天气请求成功：",weatherNowBean.getNow().getText());
                        setWeatherNowBean(weatherNowBean.getNow());
                        updateWidgetView(false);
                        updateForecast(positionStr);
                    }
                });
//        HeWeather.getWeatherNow(getApplicationContext(), positionStr, new HeWeather.OnResultWeatherNowListener() {
//            @Override
//            public void onError(Throwable throwable) {
//                LogUtil.e("实时天气请求失败：",throwable);
//            }
//
//            @Override
//            public void onSuccess(WeatherNowBean weatherNowBean) {
//                if (!HeWeatherHelper.isBeanSuccess(weatherNowBean)){
//                    LogUtil.e("实时天气请求失败：",weatherNowBean.getCode());
//                    return;
//                }
//                LogUtil.i("实时天气请求成功：",weatherNowBean.getNow().getText());
//                setWeatherNowBean(weatherNowBean.getNow());
//                updateWidgetView(false);
//                updateForecast(positionStr);
//            }
//        });
    }

    public void updateForecast(String positionStr){
        LogUtil.i("未来预测请求发出,实时位置：",positionStr);


        QWeather.getWeather7D(WeatherWidgetService.this, positionStr,
                new QWeather.OnResultWeatherDailyListener() {
                    @Override
                    public void onError(Throwable throwable) {
                        LogUtil.e("实时天气请求失败：",throwable);
                    }

                    @Override
                    public void onSuccess(WeatherDailyBean weatherDailyBean) {
                        if (!HeWeatherUtil.isBeanSuccess(weatherDailyBean)){
                            LogUtil.e("未来预测请求失败：",weatherDailyBean.getCode());
                            return;
                        }
                        LogUtil.i("未来预测请求成功：",weatherDailyBean.getDaily().get(0).getTextDay());
                        setWeatherDailyBeanList(weatherDailyBean.getDaily());
                        updateWidgetView(true);
                    }
                });

//        HeWeather.getWeather7D(getApplicationContext(), positionStr, new HeWeather.OnResultWeatherDailyListener() {
//            @Override
//            public void onError(Throwable throwable) {
//                LogUtil.e("未来预测请求失败：",throwable);
//            }
//
//            @Override
//            public void onSuccess(WeatherDailyBean weatherDailyBean) {
//                if (!HeWeatherHelper.isBeanSuccess(weatherDailyBean)){
//                    LogUtil.e("未来预测请求失败：",weatherDailyBean.getCode());
//                    return;
//                }
//                LogUtil.i("未来预测请求成功：",weatherDailyBean.getDaily().get(0).getTextDay());
//                setWeatherDailyBeanList(weatherDailyBean.getDaily());
//                updateWidgetView(true);
//            }
//        });
    }

    public void updateWidgetView(boolean needStop) {
        LogUtil.i("更新widget视图");
        AppWidgetManager manager = AppWidgetManager.getInstance(WeatherWidgetService.this);
        int[] ids = manager.getAppWidgetIds(new ComponentName(WeatherWidgetService.this,WeatherWidgetProvider.class));
        RemoteViews views = new RemoteViews(WeatherWidgetService.this.getPackageName(),R.layout.weather_widget);

        StringBuffer tmp = new StringBuffer();
        String submitTime = DateUtil.changeDate(mSubmitTime, DateUtil.Type.LONG_STAMP, DateUtil.Type.STRING_YMD_HMS);
        tmp.append("点击提交时间：").append(submitTime).append("\n");

        if (weatherNowBean!=null){
            views.setTextViewText(R.id.widget_weather, weatherNowBean.getText() + "\n" + weatherNowBean.getWindDir() + weatherNowBean.getWindScale() + "级");
            views.setTextViewText(R.id.widget_now_temperature, weatherNowBean.getTemp() + "℃");
            views.setImageViewBitmap(R.id.widget_now_pic, WeatherIconHelper.getInstance(WeatherWidgetService.this).getAssetPic(weatherNowBean.getIcon()));
            String time = weatherNowBean.getObsTime();
            LogUtil.d("实时天气时间1:", time);
            time = time.substring(0, time.indexOf('+'));
            time = time.replace('T', ' ');
            LogUtil.d("实时天气时间2:", time);
            time = DateUtil.changeDate(time, DateUtil.Type.STRING_YMD_HM_2, DateUtil.Type.STRING_YMD_HMS);
            tmp.append("实时天气时间：").append(time).append("\n");
        }

        if (weatherDailyBeanList !=null && weatherDailyBeanList.size()>0){
            //曲线图
            Bitmap bitmap = WeatherChartHelper.getChartBitmap(WeatherWidgetService.this,weatherDailyBeanList);
            views.setImageViewBitmap(R.id.widget_image, bitmap);
            //点击曲线图更新信息
            Intent prevInten = new Intent(this,WeatherWidgetService.class);
            PendingIntent Pprevintent=PendingIntent.getService(this, 0, prevInten, 0);
            views.setOnClickPendingIntent(R.id.widget_image,Pprevintent);
        }

        if (location!=null){
            tmp.append("百度定位时间：").append(location.getTime()).append("\n");
            tmp.append("百度定位：").append(BdMapUtil.getFormatAddress(location)).append("\n");
        }
        views.setTextViewText(R.id.widget_note,tmp.toString().trim());

        manager.updateAppWidget(ids, views);

        if (needStop){
            LogUtil.i("更新完毕关闭通知，结束服务");
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.cancelAll();
            stopSelf();
            weatherNowBean = null;
            weatherDailyBeanList = null;
        }
    }

    public BDLocation getLocation() {
        return location;
    }

    public void setLocation(BDLocation location) {
        this.location = location;
    }

    public WeatherNowBean.NowBaseBean getWeatherNowBean() {
        return weatherNowBean;
    }

    public void setWeatherNowBean(WeatherNowBean.NowBaseBean weatherNowBean) {
        this.weatherNowBean = weatherNowBean;
    }

    public List<WeatherDailyBean.DailyBean> getWeatherDailyBeanList() {
        return weatherDailyBeanList;
    }

    public void setWeatherDailyBeanList(List<WeatherDailyBean.DailyBean> weatherDailyBeanList) {
        this.weatherDailyBeanList = weatherDailyBeanList;
    }
}
