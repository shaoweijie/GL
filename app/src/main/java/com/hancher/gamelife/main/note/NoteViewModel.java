package com.hancher.gamelife.main.note;

import android.content.Context;
import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hancher.common.third.mapbaidu.BdMapManager;
import com.hancher.common.utils.android.AsyncUtils;
import com.hancher.common.utils.android.ClipboardUtil;
import com.hancher.common.utils.android.LogUtil;
import com.hancher.common.utils.android.ToastUtil;
import com.hancher.common.utils.java.TextUtil;
import com.hancher.gamelife.MainApplication;
import com.hancher.gamelife.greendao.Diary;
import com.hancher.gamelife.greendao.DiaryDao;
import com.hancher.gamelife.greendao.Position;
import com.hancher.gamelife.greendao.PositionDao;
import com.hancher.gamelife.greendao.PositionDbUtil;
import com.hancher.gamelife.greendao.Weather;
import com.hancher.gamelife.greendao.WeatherDao;
import com.hancher.gamelife.greendao.WeatherDbUtil;
import com.qweather.sdk.bean.geo.GeoBean;
import com.qweather.sdk.bean.weather.WeatherNowBean;
import com.qweather.sdk.view.QWeather;

import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.List;

import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;

import static com.hancher.gamelife.main.note.NoteListActivityVM.NUMBER_OF_PAGE;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/8/28 0028 20:20 <br/>
 * 描述 : 笔记的vm
 */
public class NoteViewModel extends ViewModel {

    private MutableLiveData<Position> baiduMapLiveData = new MutableLiveData();
    private MutableLiveData<List<NoteAdapter.NoteItem>> notes = new MutableLiveData<>();

    public MutableLiveData<List<NoteAdapter.NoteItem>> getNotes() {
        return notes;
    }
    public MutableLiveData<Position> getBaiduMapLiveData() {
        return baiduMapLiveData;
    }

    /**
     * 删除一条笔记
     *
     * @param uuid uuid
     * @param page
     */
    public void deleteItem(String uuid, int page) {
        AsyncUtils.run(emitter -> {
            LogUtil.d("数据库删除开始,item=" + uuid);
            List<Diary> diarys = MainApplication.getInstance().getDaoSession().getDiaryDao()
                    .queryBuilder().where(DiaryDao.Properties.Uuid.eq(uuid)).list();
            if (diarys == null || diarys.size() == 0) {
                return;
            }
            diarys.get(0).setDeleteflag(1);
            diarys.get(0).setUpdatetime(System.currentTimeMillis());
            MainApplication.getInstance().getDaoSession().getDiaryDao().update(diarys.get(0));
            LogUtil.d("数据库删除结束");
            emitter.onNext(diarys.get(0));
            emitter.onComplete();
        }, (Consumer<Diary>) s -> {
            // TODO: swj: 2021/8/28 0028 查询笔记
            queryData(null, page);
        });
    }

    public void queryData(String filiter, int page) {
        LogUtil.d("开始查询日记");
        AsyncUtils.run(emitter -> {
            QueryBuilder<Diary> queryBuilder = MainApplication.getInstance()
                    .getDaoSession().getDiaryDao().queryBuilder();

            //过滤条件
            if (TextUtil.isEmpty(filiter)) {
                queryBuilder.where(DiaryDao.Properties.Deleteflag.notEq(1));
            } else {
                String filiterWhere = "%" + filiter + "%";
                WhereCondition filiterCondition = queryBuilder.or(
                        DiaryDao.Properties.Title.like(filiterWhere),
                        DiaryDao.Properties.Content.like(filiterWhere),
                        DiaryDao.Properties.Tag.like(filiterWhere));
                queryBuilder.where(DiaryDao.Properties.Deleteflag.notEq(1),filiterCondition);
            }
            // 排序和分页
            queryBuilder.orderDesc(DiaryDao.Properties.Createtime);
            queryBuilder.limit(page * NUMBER_OF_PAGE).offset(0);

            List<Diary> list = queryBuilder.build().list();
            LogUtil.d("日记从数据库中查询成功:" + list.size());

            emitter.onNext(NoteUtil.db2list(list));
            emitter.onComplete();
        }, (Consumer<List<NoteAdapter.NoteItem>>) noteEntities -> {
            LogUtil.d("日记查询成功:" + noteEntities.size());
            notes.setValue(noteEntities);
        });
    }

    /**
     * 查询当前位置，使用livedata返回
     * @param context
     */
    public void startBdLocation(Context context) {
        BdMapManager.getInstance().start(context, BdMapManager.START_TYPE.ONCE,
                location -> {
                    Position position = PositionDbUtil.save2Db(location);
                    baiduMapLiveData.setValue(position);
                });
    }

    private MutableLiveData<Weather> weatherCnLiveData = new MutableLiveData();

    public MutableLiveData<Weather> getWeatherCnLiveData() {
        return weatherCnLiveData;
    }

    private MutableLiveData<Diary> diary = new MutableLiveData<>();

    public MutableLiveData<Diary> getDiary() {
        return diary;
    }

    private MutableLiveData<State> state = new MutableLiveData<>();

    public MutableLiveData<State> getState() {
        return state;
    }

    private ClipboardUtil.ClipboardChangeLiveData clipboard = new ClipboardUtil.ClipboardChangeLiveData();

    public ClipboardUtil.ClipboardChangeLiveData getClipboard() {
        return clipboard;
    }

    public void startNowWeather(Context context, String longiLat) {
        LogUtil.d("经纬度转id开始 longiLat="+longiLat);
        QWeather.getGeoCityLookup(context, longiLat, new QWeather.OnResultGeoListener() {
            @Override
            public void onError(Throwable throwable) {
                LogUtil.e("get geo err:", throwable);
            }

            @Override
            public void onSuccess(GeoBean geoBean) {
                if (geoBean == null || geoBean.getLocationBean().size()==0){
                    LogUtil.e("get geo null");
                    return;
                }
                LogUtil.d("经纬度转id，获取id成功，获取天气开始id="+geoBean.getLocationBean().get(0).getId());
                QWeather.getWeatherNow(context, geoBean.getLocationBean().get(0).getId(),
                        new QWeather.OnResultWeatherNowListener() {
                            @Override
                            public void onError(Throwable throwable) {
                                LogUtil.e("get now weather err:", throwable);
                            }

                            @Override
                            public void onSuccess(WeatherNowBean weatherNowBean) {
                                Weather weather = WeatherDbUtil.save2Db(weatherNowBean);
                                weatherCnLiveData.setValue(weather);
                            }
                        });
            }
        });

//        QWeather.getWeatherNow(context, "青岛",
//                new QWeather.OnResultWeatherNowListener() {
//                    @Override
//                    public void onError(Throwable throwable) {
//                        LogUtil.e("get now weather err:", throwable);
//                    }
//
//                    @Override
//                    public void onSuccess(WeatherNowBean weatherNowBean) {
//                        Weather weather = WeatherDbUtil.save2Db(weatherNowBean);
//                        weatherCnLiveData.setValue(weather);
//                    }
//                });
    }

    /**
     * 从db中查询出位置，利用LiveData返回
     * @param positionUuid
     */
    public void queryLocationInDb(String positionUuid) {
        List<Position> positions = MainApplication.getInstance().getDaoSession().getPositionDao()
                .queryBuilder()
                .where(PositionDao.Properties.Uuid.eq(positionUuid)).list();
        if (positions.size()>0){
            baiduMapLiveData.setValue(positions.get(0));
        }
    }

    public void queryWeatherInDb(String weatherUuid) {
        List<Weather> weathers = MainApplication.getInstance().getDaoSession().getWeatherDao()
                .queryBuilder()
                .where(WeatherDao.Properties.Uuid.eq(weatherUuid)).list();
        if (weathers.size()>0){
            weatherCnLiveData.setValue(weathers.get(0));
        }
    }

    public enum State {
        SAVE_FINISH
    }

    /**
     * 查询数据库，当前界面的对应的实体类
     * @param uuid
     */
    public void queryCurrentItem(String uuid) {
        if (TextUtils.isEmpty(uuid)) {
            LogUtil.d(uuid);
            ToastUtil.show("uuid为空");
            return;
        }
        AsyncUtils.run((ObservableOnSubscribe<Diary>) emitter -> {
            List<Diary> list = MainApplication.getInstance()
                    .getDaoSession().getDiaryDao().queryBuilder()
                    .where(DiaryDao.Properties.Uuid.eq(uuid))
                    .build().list();
            if (list.size() > 0) {
                emitter.onNext(list.get(0));
            }
        }, noteEntity -> {
            diary.setValue(noteEntity);
        });
    }

    public void saveItemData(Diary itemData, boolean isUpdate) {
        LogUtil.d(isUpdate ? "更新" : "插入" + "数据=\n", itemData);
        AsyncUtils.run(emitter -> {
            long result = 1L;
            if (isUpdate) {
                MainApplication.getInstance().getDaoSession().getDiaryDao()
                        .update(itemData);
            } else {
                result = MainApplication.getInstance().getDaoSession().getDiaryDao()
                        .insert(itemData);
                LogUtil.d("插入行：", result);
            }
            MainApplication.getInstance().getDaoSession().clear();
            emitter.onNext(result);
        }, (Consumer<Long>) o -> state.setValue(State.SAVE_FINISH));
    }
}
