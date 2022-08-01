//package com.hancher.gamelife.main.note;
//
//import com.baidu.location.BDLocation;
//import com.hancher.gamelife.greendao.NoteEntity;
//
//import interfaces.heweather.com.interfacesmodule.bean.weather.WeatherNowBean;
//
///**
// * 作者：Hancher
// * 时间：2020/10/16 0016 下午 2:05
// * 邮箱：ytu_shaoweijie@163.com
// * <p>
// * 说明：
// */
//public interface NoteEditContract {
//    interface Model {
//    }
//
//    interface View {
//        void updateData(NoteEntity o);
//        void onDataSaveSuccess(Long effectRow);
//
//        void updatePosition(BDLocation bdLocation);
//
//        void updateWeather(WeatherNowBean weatherNowBean);
//    }
//
//    interface Presenter {
//        void queryCurrentItem(String stringExtra);
//
//        void saveItemData(NoteEntity itemData, boolean isUpdate);
//
//        void getPositionAsync();
//
//        void getWeatherAsync(String s, String toString);
//
//        void autoUpdateLocationAndWeather();
//    }
//}
