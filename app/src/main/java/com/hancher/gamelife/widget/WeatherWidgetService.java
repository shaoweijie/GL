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
            LogUtil.w("??????????????????????????????????????????1??????");
            return super.onStartCommand(intent, flags, startId);
        }
        mSubmitTime = System.currentTimeMillis();
        PreferenceUtil.setValue(WEATHER_SERVICE_LAST_SUBMIT_TIME, System.currentTimeMillis());
        startNotification();
        LogUtil.i("widget??????????????????");
        HeWeatherUtil.init();
        updatePosition();
        return super.onStartCommand(intent, flags, startId);
    }

    public void startNotification() {
        if (Build.VERSION.SDK_INT< Build.VERSION_CODES.O){
            return;
        }
        //????????????
        Notification.Builder nb = new Notification.Builder(getApplicationContext(), NotificationUtil.MIN_CHANNEL)
                //??????????????????????????????
                .setSmallIcon(R.drawable.icon_weather)
                //??????????????????
                .setContentTitle("?????????????????????")
                //??????????????????
                .setContentText("????????????????????????????????????????????????????????????")
                //???????????????????????????????????????
                .setAutoCancel(true)
                //????????????????????????
                .setShowWhen(true);
        notification = nb.build();
        startForeground(1,notification);
    }

    private void updatePosition() {

        disposable = AsyncUtils.runCountdown(0L, 1L, 60000L, 0L,
                aLong -> LogUtil.d("onNext:"+aLong),
                () -> {
                    LogUtil.w("60???????????????????????????????????????");
                    updateWeatherWithDefaultWeather();
                });

        LogUtil.i("????????????????????????!!!");
        BdMapManager.getInstance().start(getApplicationContext(), BdMapManager.START_TYPE.CYCLE,
                bdLocation -> {
                    LogUtil.d("???????????????"+WeatherWidgetService.this);
                    if (WeatherWidgetService.this.disposable!=null && !WeatherWidgetService.this.disposable.isDisposed()){
                        WeatherWidgetService.this.disposable.dispose();
                    }
                    if(!BdMapUtil.isBDLocationSuccess(bdLocation)){
                        LogUtil.e("?????????????????????",bdLocation.getLocType());
                        updateWeatherWithDefaultWeather();
                        return;
                    }
                    String positionStr = TextUtil.double2String(bdLocation.getLongitude()) + ","
                            + TextUtil.double2String(bdLocation.getLatitude());
                    LogUtil.i("?????????????????????"+positionStr);
//                    BaiduMapHelper.saveLocation(bdLocation);
                    setLocation(bdLocation);
                    updateWidgetView(false);
                    changePositionToStr(positionStr);
                });

//        BaiduMapHelper.getInstance().startOnce(new BDAbstractLocationListener() {
//            @Override
//            public void onReceiveLocation(BDLocation bdLocation) {
////                HancherLogUtil.v("???????????????"+WeatherWidgetService.this);
//                if (WeatherWidgetService.this.disposable!=null && !WeatherWidgetService.this.disposable.isDisposed()){
//                    WeatherWidgetService.this.disposable.dispose();
//                }
//                BaiduMapHelper.getInstance().stop();
//                if(!BaiduMapHelper.isBDLocationSuccess(bdLocation)){
//                    LogUtil.e("?????????????????????",bdLocation.getLocType());
//                    updateWeatherWithDefaultWeather();
//                    return;
//                }
//                String positionStr = TextUtil.double2String(bdLocation.getLongitude()) + ","
//                        + TextUtil.double2String(bdLocation.getLatitude());
//                LogUtil.i("?????????????????????"+positionStr);
////                BaiduMapHelper.saveLocation(bdLocation);
//                setLocation(bdLocation);
//                updateWidgetView(false);
//                updateNowWeather(positionStr);
//            }
//        });
    }

    private void changePositionToStr(String positionStr){
        LogUtil.i("??????????????????????????????");
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
        updateNowWeather(location);//????????????
    }

    public void updateNowWeather(String positionStr) {
        LogUtil.i("????????????????????????,???????????????",positionStr);
        QWeather.getWeatherNow(WeatherWidgetService.this, positionStr,
                new QWeather.OnResultWeatherNowListener() {
                    @Override
                    public void onError(Throwable throwable) {
                        LogUtil.e("???????????????????????????",throwable);
                    }

                    @Override
                    public void onSuccess(WeatherNowBean weatherNowBean) {
                        if (!HeWeatherUtil.isBeanSuccess(weatherNowBean)){
                            LogUtil.e("???????????????????????????",weatherNowBean.getCode());
                            return;
                        }
                        LogUtil.i("???????????????????????????",weatherNowBean.getNow().getText());
                        setWeatherNowBean(weatherNowBean.getNow());
                        updateWidgetView(false);
                        updateForecast(positionStr);
                    }
                });
//        HeWeather.getWeatherNow(getApplicationContext(), positionStr, new HeWeather.OnResultWeatherNowListener() {
//            @Override
//            public void onError(Throwable throwable) {
//                LogUtil.e("???????????????????????????",throwable);
//            }
//
//            @Override
//            public void onSuccess(WeatherNowBean weatherNowBean) {
//                if (!HeWeatherHelper.isBeanSuccess(weatherNowBean)){
//                    LogUtil.e("???????????????????????????",weatherNowBean.getCode());
//                    return;
//                }
//                LogUtil.i("???????????????????????????",weatherNowBean.getNow().getText());
//                setWeatherNowBean(weatherNowBean.getNow());
//                updateWidgetView(false);
//                updateForecast(positionStr);
//            }
//        });
    }

    public void updateForecast(String positionStr){
        LogUtil.i("????????????????????????,???????????????",positionStr);


        QWeather.getWeather7D(WeatherWidgetService.this, positionStr,
                new QWeather.OnResultWeatherDailyListener() {
                    @Override
                    public void onError(Throwable throwable) {
                        LogUtil.e("???????????????????????????",throwable);
                    }

                    @Override
                    public void onSuccess(WeatherDailyBean weatherDailyBean) {
                        if (!HeWeatherUtil.isBeanSuccess(weatherDailyBean)){
                            LogUtil.e("???????????????????????????",weatherDailyBean.getCode());
                            return;
                        }
                        LogUtil.i("???????????????????????????",weatherDailyBean.getDaily().get(0).getTextDay());
                        setWeatherDailyBeanList(weatherDailyBean.getDaily());
                        updateWidgetView(true);
                    }
                });

//        HeWeather.getWeather7D(getApplicationContext(), positionStr, new HeWeather.OnResultWeatherDailyListener() {
//            @Override
//            public void onError(Throwable throwable) {
//                LogUtil.e("???????????????????????????",throwable);
//            }
//
//            @Override
//            public void onSuccess(WeatherDailyBean weatherDailyBean) {
//                if (!HeWeatherHelper.isBeanSuccess(weatherDailyBean)){
//                    LogUtil.e("???????????????????????????",weatherDailyBean.getCode());
//                    return;
//                }
//                LogUtil.i("???????????????????????????",weatherDailyBean.getDaily().get(0).getTextDay());
//                setWeatherDailyBeanList(weatherDailyBean.getDaily());
//                updateWidgetView(true);
//            }
//        });
    }

    public void updateWidgetView(boolean needStop) {
        LogUtil.i("??????widget??????");
        AppWidgetManager manager = AppWidgetManager.getInstance(WeatherWidgetService.this);
        int[] ids = manager.getAppWidgetIds(new ComponentName(WeatherWidgetService.this,WeatherWidgetProvider.class));
        RemoteViews views = new RemoteViews(WeatherWidgetService.this.getPackageName(),R.layout.weather_widget);

        StringBuffer tmp = new StringBuffer();
        String submitTime = DateUtil.changeDate(mSubmitTime, DateUtil.Type.LONG_STAMP, DateUtil.Type.STRING_YMD_HMS);
        tmp.append("?????????????????????").append(submitTime).append("\n");

        if (weatherNowBean!=null){
            views.setTextViewText(R.id.widget_weather, weatherNowBean.getText() + "\n" + weatherNowBean.getWindDir() + weatherNowBean.getWindScale() + "???");
            views.setTextViewText(R.id.widget_now_temperature, weatherNowBean.getTemp() + "???");
            views.setImageViewBitmap(R.id.widget_now_pic, WeatherIconHelper.getInstance(WeatherWidgetService.this).getAssetPic(weatherNowBean.getIcon()));
            String time = weatherNowBean.getObsTime();
            LogUtil.d("??????????????????1:", time);
            time = time.substring(0, time.indexOf('+'));
            time = time.replace('T', ' ');
            LogUtil.d("??????????????????2:", time);
            time = DateUtil.changeDate(time, DateUtil.Type.STRING_YMD_HM_2, DateUtil.Type.STRING_YMD_HMS);
            tmp.append("?????????????????????").append(time).append("\n");
        }

        if (weatherDailyBeanList !=null && weatherDailyBeanList.size()>0){
            //?????????
            Bitmap bitmap = WeatherChartHelper.getChartBitmap(WeatherWidgetService.this,weatherDailyBeanList);
            views.setImageViewBitmap(R.id.widget_image, bitmap);
            //???????????????????????????
            Intent prevInten = new Intent(this,WeatherWidgetService.class);
            PendingIntent Pprevintent=PendingIntent.getService(this, 0, prevInten, 0);
            views.setOnClickPendingIntent(R.id.widget_image,Pprevintent);
        }

        if (location!=null){
            tmp.append("?????????????????????").append(location.getTime()).append("\n");
            tmp.append("???????????????").append(BdMapUtil.getFormatAddress(location)).append("\n");
        }
        views.setTextViewText(R.id.widget_note,tmp.toString().trim());

        manager.updateAppWidget(ids, views);

        if (needStop){
            LogUtil.i("???????????????????????????????????????");
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
