package com.hancher.gamelife.bak.test;

import android.Manifest;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.hancher.common.third.weatherhe.HeWeatherUtil;
import com.hancher.common.third.weatherhe.WeatherChartHelper;
import com.hancher.common.utils.android.LogUtil;
import com.hancher.gamelife.R;
import com.permissionx.guolindev.PermissionX;
import com.qweather.sdk.bean.base.Code;
import com.qweather.sdk.bean.geo.GeoBean;
import com.qweather.sdk.bean.weather.WeatherDailyBean;
import com.qweather.sdk.view.QWeather;


public class TestHeWeatherChartActivity extends AppCompatActivity {

    private TextView textView;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_he_weather_chart);
        LogUtil.d("start");
        textView = findViewById(R.id.textView14);
        imageView = findViewById(R.id.imageView13);
        textView.setOnClickListener(v -> {
            textView.setText("");
            QWeather.getGeoCityLookup(TestHeWeatherChartActivity.this, "芝罘",
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
                            QWeather.getWeather7D(TestHeWeatherChartActivity.this,
                                    geoBean.getLocationBean().get(0).getId(),
                                    new QWeather.OnResultWeatherDailyListener() {
                                        @Override
                                        public void onError(Throwable throwable) {

                                        }

                                        @Override
                                        public void onSuccess(WeatherDailyBean weatherDailyBean) {
                                            Bitmap bitmap = WeatherChartHelper.getChartBitmap(
                                                    TestHeWeatherChartActivity.this,
                                                    weatherDailyBean.getDaily());
                                            imageView.setImageBitmap(bitmap);
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