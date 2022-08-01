package com.hancher.gamelife.test;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.hancher.common.third.weatherhe.HeWeatherUtil;
import com.hancher.common.utils.android.LogUtil;
import com.hancher.common.utils.android.ToastUtil;
import com.hancher.gamelife.R;
import com.qweather.sdk.bean.weather.WeatherNowBean;
import com.qweather.sdk.view.QWeather;

public class WeatherHeTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_test);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.d("");
        HeWeatherUtil.init();

        QWeather.getWeatherNow(this, "101120512",
                new QWeather.OnResultWeatherNowListener() {
                    @Override
                    public void onError(Throwable throwable) {
                        LogUtil.e("实时天气请求失败：",throwable);
                    }

                    @Override
                    public void onSuccess(WeatherNowBean weatherNowBean) {
                        if (!HeWeatherUtil.isBeanSuccess(weatherNowBean)){
                            ToastUtil.showErr("实时天气请求失败："+weatherNowBean.getCode());
                            return;
                        }
                        ToastUtil.show("实时天气请求成功："+weatherNowBean.getNow().getText());
                    }
                });
    }
}