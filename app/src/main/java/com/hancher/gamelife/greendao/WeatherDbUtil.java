package com.hancher.gamelife.greendao;

import com.hancher.common.utils.android.LogUtil;
import com.hancher.common.utils.java.DateUtil;
import com.hancher.gamelife.MainApplication;
import com.qweather.sdk.bean.weather.WeatherNowBean;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/6/20 0020 18:21 <br/>
 * 描述 : 天气存储工具类
 */
public class WeatherDbUtil {

    private static Weather getWeatherByHeNowBean(WeatherNowBean.NowBaseBean bean) {
        Weather weather = new Weather();
        weather.setObsTime(bean.getObsTime());
        weather.setText(bean.getText());
        weather.setWindDir(bean.getWindDir());
        weather.setWindScale(bean.getWindScale());
        try {
            weather.setTime(DateUtil.changeDate(bean.getObsTime(), DateUtil.Type.STRING_YMD_HM_3,
                    DateUtil.Type.LONG_STAMP));
            weather.setFeelsLike(Integer.parseInt(bean.getFeelsLike()));
            weather.setTemp(Integer.parseInt(bean.getTemp()));
            weather.setIcon(Integer.parseInt(bean.getIcon()));
            weather.setWind360(Integer.parseInt(bean.getWind360()));
            weather.setWindSpeed(Integer.parseInt(bean.getWindSpeed()));
            weather.setHumidity(Integer.parseInt(bean.getHumidity()));
            weather.setPrecip(Float.parseFloat(bean.getPrecip()));
            weather.setPressure(Integer.parseInt(bean.getPressure()));
            weather.setVis(Integer.parseInt(bean.getVis()));
            weather.setCloud(Integer.parseInt(bean.getCloud()));
            weather.setDew(Integer.parseInt(bean.getDew()));
        } catch (Exception e) {
            LogUtil.e("change weather type err:", e);
        }
        return weather;
    }
    /**
     * 和风天气实体类转换成db实体类
     * @param bean
     * @return
     */
    public static Weather save2Db(WeatherNowBean bean) {
        Weather weather = getWeatherByHeNowBean(bean.getNow());
        MainApplication.getInstance().getDaoSession().getWeatherDao().insert(weather);
        return weather;
    }
}
