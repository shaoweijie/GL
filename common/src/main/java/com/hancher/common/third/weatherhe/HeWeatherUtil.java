package com.hancher.common.third.weatherhe;

import com.qweather.sdk.bean.base.Code;
import com.qweather.sdk.bean.geo.GeoBean;
import com.qweather.sdk.bean.weather.WeatherDailyBean;
import com.qweather.sdk.bean.weather.WeatherNowBean;
import com.qweather.sdk.view.HeConfig;

/**
 * 作者：Hancher
 * 时间：2020/2/1.
 * 邮箱：ytu_shaoweijie@163.com
 * 版本：v1.0
 * <p>
 * 说明：需要 gson、okhttp
 * 官方说明 https://dev.heweather.com/docs/sdk/android-entity
 * 静态方法：
 * HeWeather.getWeatherNow
 * HeWeather.getWeather3D
 * HeWeather.getWeather24Hourly
 * HeWeather.getMinuteLy
 * HeWeather.getWarning
 * HeWeather.get1DIndices
 */
public class HeWeatherUtil {
    public static final String publicId = "HE2203040125161233";
    public static final String appKey = "4606fafb68cc482798e7a0e5de749088";

    public static void init() {
        HeConfig.init(publicId, appKey);
        HeConfig.switchToDevService();
    }

    public static void init(String publicId, String appKey) {
        HeConfig.init(publicId, appKey);
        HeConfig.switchToDevService();
    }

    public static boolean isBeanSuccess(WeatherNowBean weatherNowBean) {
        return Code.OK == weatherNowBean.getCode();
    }

    public static boolean isBeanSuccess(WeatherDailyBean weatherDailyBean) {
        return Code.OK == weatherDailyBean.getCode();
    }

    public static StringBuffer getNowIfo(WeatherNowBean weatherNowBean) {
        String obsTime = weatherNowBean.getNow().getObsTime();//2013-12-30 13:14
        String fellsLike = weatherNowBean.getNow().getFeelsLike();//体感温度 23
        String temp = weatherNowBean.getNow().getTemp();//温度
        String icon = weatherNowBean.getNow().getIcon();//实况天气状况代码 100
        String text = weatherNowBean.getNow().getText();//晴
        String wind360 = weatherNowBean.getNow().getWind360();//305
        String windDir = weatherNowBean.getNow().getWindDir();//风向 西北
        String windScale = weatherNowBean.getNow().getWindScale();//风力 3-4
        String windSpeed = weatherNowBean.getNow().getWindSpeed();//风速，公里/小时 15
        String humidity = weatherNowBean.getNow().getHumidity();//相对湿度 40
        String precip = weatherNowBean.getNow().getPrecip();//降水量 0
        String pressure = weatherNowBean.getNow().getPressure();//大气压强 1020
        String vis = weatherNowBean.getNow().getVis();//能见度，默认单位：公里 10
        String cloud = weatherNowBean.getNow().getCloud();//云量 23
        String dew = weatherNowBean.getNow().getDew();//实况云量 23
        StringBuffer result = new StringBuffer("WeatherNowBean:{\n");
        result.append("  obsTime=").append(obsTime).append("\n");
        result.append("  fellsLike=").append(fellsLike).append("\n");
        result.append("  temp=").append(temp).append("\n");
        result.append("  icon=").append(icon).append("\n");
        result.append("  text=").append(text).append("\n");
        result.append("  wind360=").append(wind360).append("\n");
        result.append("  windDir=").append(windDir).append("\n");
        result.append("  windScale=").append(windScale).append("\n");
        result.append("  windSpeed=").append(windSpeed).append("\n");
        result.append("  humidity=").append(humidity).append("\n");
        result.append("  precip=").append(precip).append("\n");
        result.append("  pressure=").append(pressure).append("\n");
        result.append("  vis=").append(vis).append("\n");
        result.append("  cloud=").append(cloud).append("\n");
        result.append("  dew=").append(dew).append("\n");
        result.append("}");
        return result;
    }

    public static StringBuffer getGeoInfo(GeoBean geoBean) {
        StringBuffer result = new StringBuffer("GeoBean:{\n");
        String code = geoBean.getCode().toString();
        result.append(code).append("\n");
        for (GeoBean.LocationBean bean : geoBean.getLocationBean()) {
            result.append("getId=").append(bean.getId()).append("\n");
            result.append("getName=").append(bean.getName()).append("\n");
            result.append("getLon=").append(bean.getLon()).append("\n");
            result.append("getLat=").append(bean.getLat()).append("\n");
            result.append("getAdm2=").append(bean.getAdm2()).append("\n");
            result.append("getAdm1=").append(bean.getAdm1()).append("\n");
            result.append("getCountry=").append(bean.getCountry()).append("\n");
            result.append("getTz=").append(bean.getTz()).append("\n");
            result.append("getUtcOffset=").append(bean.getUtcOffset()).append("\n");
            result.append("getIsDst=").append(bean.getIsDst()).append("\n");
            result.append("getType=").append(bean.getType()).append("\n");
            result.append("getRank=").append(bean.getRank()).append("\n");
            result.append("getFxLink=").append(bean.getFxLink()).append("\n\n");
        }
        result.append("}");
        return result;
    }

    private void printNow(WeatherNowBean weatherNowBean) {
        weatherNowBean.getNow().getObsTime();//2013-12-30 13:14
        weatherNowBean.getNow().getFeelsLike();//体感温度 23
        weatherNowBean.getNow().getTemp();//温度
        weatherNowBean.getNow().getIcon();//实况天气状况代码 100
        weatherNowBean.getNow().getText();//晴
        weatherNowBean.getNow().getWind360();//305
        weatherNowBean.getNow().getWindDir();//风向 西北
        weatherNowBean.getNow().getWindScale();//风力 3-4
        weatherNowBean.getNow().getWindSpeed();//风速，公里/小时 15
        weatherNowBean.getNow().getHumidity();//相对湿度 40
        weatherNowBean.getNow().getPrecip();//降水量 0
        weatherNowBean.getNow().getPressure();//大气压强 1020
        weatherNowBean.getNow().getVis();//能见度，默认单位：公里 10
        weatherNowBean.getNow().getCloud();//云量 23
        weatherNowBean.getNow().getDew();//实况云量 23
    }

    /**
     * getFxDate	预报日期	2013-12-30
     * getSunrise	日出时间	07:36
     * getSunset	日落时间	16:58
     * getMoonRise	月升时间	04:47
     * getMoonSet	月落时间	14:59
     * getMoonPhase	月相名称	满月
     * getTempMax	最高温度	4
     * getTempMin	最低温度	-5
     * getIconDay	白天天气状况代码	100
     * getIconNight	晚间天气状况代码	100
     * getTextDay	白天天气状况描述	晴
     * getTextNight	晚间天气状况描述	晴
     * getWind360Day	白天风向360角度	310
     * getWind360Night	夜间风向360角度	310
     * getWindDirDay	白天风向	西北风
     * getWindDirNight	夜间风向	西北风
     * getWindScaleDay	白天风力	1-2
     * getWindScaleNight	夜间风力	1-2
     * getWindSpeedDay	白天风速，公里/小时	14
     * getWindSpeedNight	夜间风速，公里/小时	14
     * getHumidity	相对湿度	37
     * getPrecip	降水量	0
     * getPressure	大气压强	1018
     * getCloud	当天云量	23
     * getUvIndex	紫外线强度指数	3
     * getVis	能见度，单位：公里	10
     *
     * @param weatherDailyBean
     */
    private void printForecast(WeatherDailyBean weatherDailyBean) {
        weatherDailyBean.getDaily().get(0).getFxDate();//2013-12-30
        weatherDailyBean.getDaily().get(0).getSunrise();//日出时间	07:36
        weatherDailyBean.getDaily().get(0).getSunset();//日落时间	16:58
        weatherDailyBean.getDaily().get(0).getMoonRise();//月升时间 04:47
        weatherDailyBean.getDaily().get(0).getMoonSet();//晴
    }

    /**
     * 200	请求成功
     * 204	请求成功，但你查询的地区暂时没有你需要的数据。
     * 400	请求错误，可能包含错误的请求参数或缺少必选的请求参数。
     * 401	认证失败，可能使用了错误的KEY、数字签名错误、KEY的类型错误（如使用SDK的KEY去访问Web API）。
     * 402	超过访问次数或余额不足以支持继续访问服务，你可以充值、升级访问量或等待访问量重置。
     * 403	无访问权限，可能是绑定的PackageName、BundleID、域名IP地址不一致，或者是需要额外付费的数据。
     * 404	查询的数据或地区不存在。
     * 429	超过限定的QPM（每分钟访问次数），请参考QPM说明
     * 500	无响应或超时，接口服务异常请联系我们
     */
    private void printErrorCode() {

    }
}
