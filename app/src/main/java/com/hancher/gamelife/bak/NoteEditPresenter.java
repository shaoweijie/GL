//package com.hancher.gamelife.main.note;
//
//import android.text.TextUtils;
//
//import com.baidu.location.BDAbstractLocationListener;
//import com.baidu.location.BDLocation;
//import com.hancher.common.base.v01.BasePresenter;
//import com.hancher.mapbaidu.BaiduMapHelper;
//import com.hancher.common.utils.android.AsyncUtils;
//import com.hancher.common.utils.android.LogUtil;
//import com.hancher.common.utils.android.ToastUtil;
//import com.hancher.common.utils.java.TextUtil;
//import com.hancher.gamelife.MainApplication;
//import com.hancher.gamelife.greendao.NoteEntity;
//import com.hancher.gamelife.greendao.DiaryDao;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import interfaces.heweather.com.interfacesmodule.bean.weather.WeatherNowBean;
//import interfaces.heweather.com.interfacesmodule.view.HeWeather;
//import io.reactivex.ObservableOnSubscribe;
//import io.reactivex.disposables.Disposable;
//import io.reactivex.functions.Consumer;
//
///**
// * 作者：Hancher
// * 时间：2020/10/16 0016 下午 2:05
// * 邮箱：ytu_shaoweijie@163.com
// * <p>
// * 说明：
// */
//public class NoteEditPresenter extends BasePresenter<NoteEditActivity> implements NoteEditContract.Presenter {
//    private Disposable d;
//
//    @Override
//    public void queryCurrentItem(String uuid) {
//
//        if (TextUtils.isEmpty(uuid)){
//            LogUtil.d(uuid);
//            ToastUtil.show("uuid为空");
//            return;
//        }
//        AsyncUtils.run((ObservableOnSubscribe<NoteEntity>) emitter -> {
//            List<NoteEntity> list = MainApplication.getInstance()
//                    .getDaoSession().getDiaryDao().queryBuilder()
//                    .where(DiaryDao.Properties.Uuid.eq(uuid))
//                    .build().list();
//            if (list.size() > 0) {
//                emitter.onNext(list.get(0));
//            }
//        }, noteEntity -> mView.updateData(noteEntity));
//    }
//
//    @Override
//    public void saveItemData(NoteEntity itemData, boolean isUpdate) {
//
//        LogUtil.d(isUpdate ? "更新" : "插入" + "数据=\n", itemData);
//        AsyncUtils.run(emitter -> {
//            String[] pic = itemData.getPicture().split(" ");
//            List<String> picList = new ArrayList<>();
//            for (int i = 0; i < pic.length; i++) {
//                if (pic[i].contains("http")) {
//                    picList.add(pic[i].trim());
//                }
//            }
////            ImgChrUtil.upload()
//        }, (Consumer<NoteEntity>) entity -> {
//
//        });
//        AsyncUtils.run(emitter -> {
//            long result = 1L;
//            if (isUpdate) {
//                MainApplication.getInstance().getDaoSession().getDiaryDao()
//                        .update(itemData);
//            } else {
//                result = MainApplication.getInstance().getDaoSession().getDiaryDao()
//                        .insert(itemData);
//                LogUtil.d("插入行：", result);
//            }
//            MainApplication.getInstance().getDaoSession().clear();
//            emitter.onNext(result);
//        }, (Consumer<Long>) o -> mView.onDataSaveSuccess(o));
//    }
//
//    @Override
//    public void getPositionAsync() {
//        LogUtil.d("开始位置异步请求");
//        BaiduMapHelper.getInstance().start(new BDAbstractLocationListener() {
//            @Override
//            public void onReceiveLocation(BDLocation bdLocation) {
//                BaiduMapHelper.getInstance().stop();
//                if(!BaiduMapHelper.isBDLocationSuccess(bdLocation)){
//                    LogUtil.e("百度定位失败：",bdLocation.getLocType());
//                    return;
//                }
//                String positionStr = TextUtil.double2String(bdLocation.getLongitude()) + ","
//                        + TextUtil.double2String(bdLocation.getLatitude());
//                LogUtil.d("百度定位成功："+positionStr);
//                BaiduMapHelper.saveLocation(bdLocation);
//                mView.updatePosition(bdLocation);
//                BaiduMapHelper.getInstance().stop();
//            }
//        });
//    }
//
//    @Override
//    public void autoUpdateLocationAndWeather() {
//        getPositionAsync();
//        LogUtil.d("倒计时5秒后开始请求天气信息");
//        d = AsyncUtils.runCountdown(5000L, aLong -> {
//            BDLocation location = BaiduMapHelper.getLastLocation();
//            if (location==null){
//                LogUtil.w("位置信息为空,放弃更新天气");
//                return;
//            }
//            getWeatherAsync(TextUtil.double2String(location.getLongitude()),TextUtil.double2String(location.getLatitude()));
//        });
//    }
//
//    @Override
//    public void onPaused() {
//        BaiduMapHelper.getInstance().stop();
//        super.onPaused();
//    }
//
//    @Override
//    public void getWeatherAsync(String longitude, String latitude) {
//        if (TextUtil.isEmptyOrNullStr(longitude) || TextUtil.isEmptyOrNullStr(latitude)){
//            LogUtil.w("无定位信息，无法获取天气");
//            return;
//        }
//        LogUtil.d("开始请求天气信息, 位置:",longitude,",",latitude);
//        String positionStr = longitude + "," + latitude;
//        HeWeather.getWeatherNow(MainApplication.getInstance(), positionStr, new HeWeather.OnResultWeatherNowListener() {
//            @Override
//            public void onError(Throwable throwable) {
//                ToastUtil.showErr(throwable.getMessage());
//            }
//
//            @Override
//            public void onSuccess(WeatherNowBean weatherNowBean) {
//                LogUtil.d("天气获取成功："+weatherNowBean.getNow().getTemp());
//                if (mView == null){
//                    return;
//                }
//                mView.updateWeather(weatherNowBean);
//            }
//        });
//    }
//
//    @Override
//    public void onDestroyed() {
//        if (d!=null){
//            d.dispose();
//        }
//        super.onDestroyed();
//    }
//}
