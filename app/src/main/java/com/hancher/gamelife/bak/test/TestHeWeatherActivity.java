package com.hancher.gamelife.bak.test;

import android.Manifest;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.hancher.common.third.weatherhe.HeWeatherUtil;
import com.hancher.common.utils.android.LogUtil;
import com.hancher.gamelife.R;
import com.permissionx.guolindev.PermissionX;
import com.qweather.sdk.bean.base.Code;
import com.qweather.sdk.bean.geo.GeoBean;
import com.qweather.sdk.bean.weather.WeatherNowBean;
import com.qweather.sdk.view.QWeather;


public class TestHeWeatherActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_he_weather);
        LogUtil.d("start");
        HeWeatherUtil.init();
        textView = findViewById(R.id.textView14);
        textView.setOnClickListener(v -> {
            textView.setText("");
            QWeather.getGeoCityLookup(TestHeWeatherActivity.this, "120.48,36.14",
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
                                return;
                            }
                            QWeather.getWeatherNow(TestHeWeatherActivity.this,
                                    geoBean.getLocationBean().get(0).getId(),
                                    new QWeather.OnResultWeatherNowListener() {
                                        @Override
                                        public void onError(Throwable throwable) {
                                            LogUtil.e("get weather not err:", throwable);
                                        }

                                        @Override
                                        public void onSuccess(WeatherNowBean weatherNowBean) {
                                            StringBuffer info = HeWeatherUtil.getNowIfo(weatherNowBean);
                                            LogUtil.d("get weather:"+info);
                                            textView.append(info);
                                        }
                                    });
                }
            });
        });
        PermissionX.init(this).permissions(Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_WIFI_STATE)
                .request((allGranted, grantedList, deniedList) -> {
                    if (allGranted){
                        LogUtil.d("permission allow");
                    }
                });
    }
}