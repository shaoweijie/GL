//package com.hancher.weathercn;
//
//import androidx.lifecycle.MutableLiveData;
//
////import com.baidu.location.BDLocation;
//import com.google.gson.Gson;
//import com.hancher.common.base.BaseApplication;
//import com.hancher.weathercn.bean.LifeBean;
//import com.hancher.weathercn.bean.NowBean;
//import com.hancher.weathercn.bean.TodayBean;
//import com.hancher.weathercn.bean.WeatherBean;
//import com.hancher.weathercn.bean.WindBean;
//import com.hancher.common.utils.android.AssetUtil;
//import com.hancher.common.utils.android.LogUtil;
//import com.hancher.common.utils.android.ToastUtil;
//import com.hancher.common.utils.java.TextUtil;
//
//import org.json.JSONObject;
//
//import io.reactivex.disposables.Disposable;
//
///**
// * 作者 : Hancher ytu_shaoweijie@163.com <br/>
// * 时间 : 2021/3/15 16:42 <br/>
// * 描述 : 天气
// */
//public class WeatherCnLiveData extends MutableLiveData<WeatherBean> {
//    private Disposable d = null;
//
//    public WeatherCnLiveData() {
//        super();
//    }
//
//    public void setPosition(String province, String city, String district) {
//        LogUtil.d("p:", province, ",c:", city, "d:", district);
//        String json = AssetUtil.getJson(BaseApplication.getInstance(), "weather_cn_city.json");
//        JSONObject jsonObject = null, provinceObject = null, cityObject = null, districtObject = null;
//        try {
//            jsonObject = new JSONObject(json);
//        } catch (Exception e) {
//            LogUtil.e("asset json str parse err:", e);
//        }
//
//        try {
//            provinceObject = jsonObject.getJSONObject(province);
//        } catch (Exception e) {
//            LogUtil.e("province json parse err:", e);
//        }
//
//        try {
//            cityObject = provinceObject.getJSONObject(city);
//        } catch (Exception e) {
//            LogUtil.w("city no found, next use province instead of city. err:", e);
//            try {
//                cityObject = provinceObject.getJSONObject(province);
//            } catch (Exception jsonException) {
//                LogUtil.e("use province instead of city. err:", e);
//            }
//        }
//
//        try {
//            districtObject = cityObject.getJSONObject(district);
//        } catch (Exception e) {
//            LogUtil.w("district no found, next use city instead of district. err:", e);
//            try {
//                districtObject = cityObject.getJSONObject(city);
//            } catch (Exception jsonException) {
//                LogUtil.w("district no found, next use province instead of district. err:", e);
//                try {
//                    districtObject = cityObject.getJSONObject(province);
//                } catch (Exception jsonException2) {
//                    LogUtil.e("use province instead of district. err:", e);
//                }
//            }
//        }
//        try {
//            String cityCode = districtObject.getString("AREAID");
//            setPosition(cityCode);
//        } catch (Exception e) {
//            LogUtil.e("parse areaid fail, err:", e);
//        }
//    }
//
//    public void setPosition(String cityCode) {
//        LogUtil.d("cityCode:", cityCode);//烟台 101120501
//        stop();
//        d = WeatherCnApi.getWeather(cityCode).subscribe(
//                responseBody -> {
//                    String body = responseBody.string();
//                    String[] jsons = body.split("var");
//                    WeatherBean weatherBean = new WeatherBean();
////            var cityDZ ={"weatherinfo":{"city":"101120501","cityname":"烟台","temp":"11℃","tempn":"4℃","weather":"小雨转多云","wd":"南风转东北风","ws":"4-5级转5-6级","weathercode":"d7","weathercoden":"n1","fctime":"202103151100"}};
////            var alarmDZ ={"w":[]};
////            var dataSK = {"nameen":"yantai","cityname":"烟台","city":"101120501","temp":"11","tempf":"51","WD":"西南风","wde":"SW","WS":"3级","wse":"<12km/h","SD":"74%","time":"14:00","weather":"阴","weathere":"Overcast","weathercode":"d02","qy":"1001","njd":"7.5km","sd":"74%","rain":"0","rain24h":"0","aqi":"93","limitnumber":"","aqi_pm25":"93","date":"03月15日(星期一)"};
////            var dataZS={"zs":{"date":"2021031511","ac_name":"空调开启指数","ac_hint":"较少开启","ac_des_s":"体感舒适，不需要开启空调。","ag_name":"过敏指数","ag_hint":"极不易发","ag_des_s":"无需担心过敏。","cl_name":"晨练指数","cl_hint":"不宜","cl_des_s":"有较强降水，建议在室内做适当锻炼。","co_name":"舒适度指数","co_hint":"很不舒适","co_des_s":"天气凉有雨雪大风，注意保暖防寒。","ct_name":"穿衣指数","ct_hint":"冷","ct_des_s":"建议着棉衣加羊毛衫等冬季服装。","dy_name":"钓鱼指数","dy_hint":"不宜","dy_des_s":"风力太大，不适合垂钓。","fs_name":"防晒指数","fs_hint":"弱","fs_des_s":"涂抹8-12SPF防晒护肤品。","gj_name":"逛街指数","gj_hint":"较不宜","gj_des_s":"有降水，风力很大，较不适宜逛街","gl_name":"太阳镜指数","gl_hint":"不需要","gl_des_s":"白天能见度差不需要佩戴太阳镜","gm_name":"感冒指数","gm_hint":"易发","gm_des_s":"大幅度降温，风力较强，增加衣服。","gz_name":"干燥指数","gz_hint":"适宜","gz_des_s":"风速偏大，气温适宜，但体感温度会低一些，建议多使用保湿型护肤品涂抹皮肤，预防皮肤干燥。","hc_name":"划船指数","hc_hint":"不适宜","hc_des_s":"风力很大，不适宜划船。","jt_name":"交通指数","jt_hint":"一般","jt_des_s":"有降水且路面湿滑，注意保持车距。","lk_name":"路况指数","lk_hint":"潮湿","lk_des_s":"有降水，路面潮湿，请小心驾驶。","ls_name":"晾晒指数","ls_hint":"不宜","ls_des_s":"降水可能会淋湿衣物，请选择在室内晾晒。","mf_name":"美发指数","mf_hint":"一般","mf_des_s":"风大尘多，注意头发清洁和滋润。","nl_name":"夜生活指数","nl_hint":"较不适宜","nl_des_s":"建议夜生活最好在室内进行。","pj_name":"啤酒指数","pj_hint":"较不适宜","pj_des_s":"有些凉意，少量饮用常温啤酒。","pk_name":"放风筝指数","pk_hint":"不宜","pk_des_s":"天气不好，不适宜放风筝。","pl_name":"空气污染扩散条件指数","pl_hint":"优","pl_des_s":"气象条件非常有利于空气污染物扩散。","pp_name":"化妆指数","pp_hint":"保湿","pp_des_s":"请选用滋润型化妆品。","tr_name":"旅游指数","tr_hint":"一般","tr_des_s":"风大有降水，影响出行，注意防风防雨。","uv_name":"紫外线强度指数","uv_hint":"最弱","uv_des_s":"辐射弱，涂擦SPF8-12防晒护肤品。","wc_name":"风寒指数","wc_hint":"凉","wc_des_s":"风力较大，感觉有点凉，室外活动注意适当增减衣物。","xc_name":"洗车指数","xc_hint":"不宜","xc_des_s":"有雨，雨水和泥水会弄脏爱车。","xq_name":"心情指数","xq_hint":"较差","xq_des_s":"雨水可能会使心绪无端地挂上轻愁。","yd_name":"运动指数","yd_hint":"较不宜","yd_des_s":"有降水，推荐您在室内进行休闲运动。","yh_name":"约会指数","yh_hint":"不适宜","yh_des_s":"建议在室内约会，免去天气的骚扰。","ys_name":"雨伞指数","ys_hint":"带伞","ys_des_s":"有降水，带雨伞，短期外出可收起雨伞。","zs_name":"中暑指数","zs_hint":"无中暑风险","zs_des_s":"天气舒适，令人神清气爽的一天，不用担心中暑的困扰。"},"cn":"烟台"};
////            var fc={"f":[{"fa":"07","fb":"01","fc":"11","fd":"4","fe":"南风","ff":"东北风","fg":"4-5级","fh":"5-6级","fk":"4","fl":"1","fm":"71.8","fn":"36.7","fi":"3\/15","fj":"今天"},{"fa":"00","fb":"01","fc":"6","fd":"1","fe":"东北风","ff":"东北风","fg":"4-5级","fh":"3-4级","fk":"1","fl":"1","fm":"74.4","fn":"64.3","fi":"3\/16","fj":"周二"},{"fa":"01","fb":"01","fc":"8","fd":"3","fe":"东北风","ff":"东风","fg":"3-4级","fh":"3-4级","fk":"1","fl":"2","fm":"74.5","fn":"45.8","fi":"3\/17","fj":"周三"},{"fa":"02","fb":"02","fc":"8","fd":"3","fe":"东北风","ff":"北风","fg":"<3级","fh":"<3级","fk":"1","fl":"8","fm":"93.0","fn":"66.0","fi":"3\/18","fj":"周四"},{"fa":"01","fb":"02","fc":"9","fd":"3","fe":"北风","ff":"西北风","fg":"3-4级","fh":"<3级","fk":"8","fl":"7","fm":"89.1","fn":"67.2","fi":"3\/19","fj":"周五"}]}
//
////            LogUtil.d("weather json size:", jsons.length);
////            for (int i = 0; i < jsons.length; i++) {
////                if (TextUtil.isEmpty(jsons[i])){
////                    continue;
////                }
////                try {
////                    String tmp = jsons[i].substring(jsons[i].indexOf("{"));
////                    if (tmp.endsWith(";"))
////                        tmp = tmp.substring(0,tmp.length()-1);
////                    LogUtil.d("tmp",i,":", tmp);
////                } catch (Exception e) {
////                    LogUtil.e("str:", jsons[i]);
////                    LogUtil.e("e:",e);
////                }
////            }
//                    try {
//                        String tmp = jsons[1].substring(jsons[1].indexOf("{"));
//                        tmp = tmp.substring(0, tmp.length() - 1);
//                        Gson gson = new Gson();
//                        TodayBean todayBean = gson.fromJson(tmp, TodayBean.class);
//                        weatherBean.setTodayBean(todayBean);
//                        LogUtil.d("todayBean:", todayBean);
//                    } catch (Exception e) {
//                        ToastUtil.showErr("todayBean err:" + e);
//                    }
//                    try {
//                        String tmp = jsons[3].substring(jsons[3].indexOf("{"));
//                        tmp = tmp.substring(0, tmp.length() - 1);
//                        Gson gson = new Gson();
//                        NowBean nowBean = gson.fromJson(tmp, NowBean.class);
//                        weatherBean.setNowBean(nowBean);
//                        LogUtil.d("nowBean:", nowBean);
//                    } catch (Exception e) {
//                        ToastUtil.showErr("nowBean err:" + e);
//                    }
//                    try {
//                        String tmp = jsons[4].substring(jsons[4].indexOf("{"));
//                        tmp = tmp.substring(0, tmp.length() - 1);
//                        Gson gson = new Gson();
//                        LifeBean lifeBean = gson.fromJson(tmp, LifeBean.class);
//                        weatherBean.setLifeBean(lifeBean);
//                        LogUtil.d("lifeBean:", lifeBean);
//                    } catch (Exception e) {
//                        ToastUtil.showErr("lifeBean err:" + e);
//                    }
//                    try {
//                        String tmp = jsons[5].substring(jsons[5].indexOf("{"));
//                        Gson gson = new Gson();
//                        WindBean windBean = gson.fromJson(tmp, WindBean.class);
//                        weatherBean.setWindBean(windBean);
//                        LogUtil.d("windBean:", windBean);
//                    } catch (Exception e) {
//                        ToastUtil.showErr("windBean err:" + e);
//                    }
//                    WeatherCnLiveData.this.setValue(weatherBean);
//                },
//                ToastUtil::showErr);
//    }
//
//    public void setPosition(BDLocation bdLocation) {
//        LogUtil.d("bd:", bdLocation);
//        if (bdLocation != null && !TextUtil.isEmpty(bdLocation.getProvince())
//                && !TextUtil.isEmpty(bdLocation.getCity())
//                && !TextUtil.isEmpty(bdLocation.getCountry())) {
//            setPosition(removeLocationSuffix(bdLocation.getProvince()),
//                    removeLocationSuffix(bdLocation.getCity()),
//                    removeLocationSuffix(bdLocation.getDistrict()));
//        }
//    }
//
//    private String removeLocationSuffix(String location) {
//        return location.replace("省", "")
//                .replace("市", "")
//                .replace("县", "")
//                .replace("区", "");
//    }
//
//    public void stop() {
//        if (d != null && !d.isDisposed()) {
//            d.dispose();
//        }
//    }
//
//    @Override
//    protected void onActive() {
//        super.onActive();
//    }
//
//    @Override
//    protected void onInactive() {
//        stop();
//        super.onInactive();
//    }
//}
