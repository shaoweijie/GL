//package com.hancher.weathercn;
//
//import com.hancher.common.CommonConfig;
//import com.hancher.common.net.NetUtil;
//import com.hancher.common.utils.android.AsyncUtils;
//
//import io.reactivex.Observable;
//import okhttp3.ResponseBody;
//import retrofit2.http.GET;
//import retrofit2.http.Headers;
//import retrofit2.http.Path;
//import retrofit2.http.Query;
//
///**
// * 作者 : Hancher ytu_shaoweijie@163.com <br/>
// * 时间 : 2021/3/15 15:56 <br/>
// * 描述 : 中国天气网，抓取天气
// */
//public class WeatherCnApi {
//
//    public static Observable<ResponseBody> getWeather(String cityCode) {
//        return new NetUtil<>(WeatherCnApiService.class)
//                .setBaseUrl(CommonConfig.HOST_WEATHER_CN_API)
//                .setUseJson(false)
//                .build()
//                .getWeather(cityCode, String.valueOf(System.currentTimeMillis()))
//                .compose(AsyncUtils.getThreadTransformer());
//    }
//
//    interface WeatherCnApiService {
//        @Headers("Referer:" + CommonConfig.HOST_WEATHER_CN)
//        @GET("weather_index/{city}.html")
//        Observable<ResponseBody> getWeather(@Path("city") String city, @Query("_") String time);
//    }
//
//}
