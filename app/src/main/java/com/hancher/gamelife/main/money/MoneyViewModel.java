package com.hancher.gamelife.main.money;

import android.graphics.Bitmap;

import androidx.lifecycle.MutableLiveData;

import com.hancher.common.base.mvvm02.BaseViewModel;
import com.hancher.common.utils.android.AssetUtil;
import com.hancher.common.utils.android.AsyncUtils;
import com.hancher.gamelife.MainApplication;
import com.hancher.gamelife.greendao.ColockIn;
import com.hancher.gamelife.greendao.ColockInDao;
import com.hancher.gamelife.greendao.ColockInType;
import com.hancher.gamelife.greendao.ColockInTypeDao;

import java.util.List;

import io.reactivex.functions.Consumer;

import static com.hancher.gamelife.main.money.MoneyViewModel.STATE.DELETE_FINISH;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/7/17 0017 21:48 <br/>
 * 描述 : 点击打卡界面vm
 */
public class MoneyViewModel extends BaseViewModel {
    private MutableLiveData<List<ColockInType>> colockInTypes = new MutableLiveData<>();
    private MutableLiveData<List<Bitmap>> icons = new MutableLiveData<List<Bitmap>>();
    private MutableLiveData<STATE> state = new MutableLiveData<>();
    private MutableLiveData<List<ColockIn>> colockIns = new MutableLiveData<>();

    public MutableLiveData<List<ColockInType>> getColockInTypes() {
        return colockInTypes;
    }

    public MutableLiveData<List<Bitmap>> getIcons() {
        return icons;
    }

    public MutableLiveData<STATE> getState() {
        return state;
    }

    public MutableLiveData<List<ColockIn>> getColockIns() {
        return colockIns;
    }

    /**
     * 保存一条打卡记录
     *
     * @param type        类型
     * @param description 描述
     */
    public void addColockIn(ColockInType type, CharSequence description) {
        AsyncUtils.run(emitter -> {
            ColockIn colockIn = new ColockIn();
            colockIn.setTypeUuid(type.getUuid());
            colockIn.setDescription(description.toString());
            //插入记录
            MainApplication.getInstance().getDaoSession().getColockInDao().insert(colockIn);
            //统计总计
            long count = MainApplication.getInstance().getDaoSession().getColockInDao().queryBuilder()
                    .where(ColockInDao.Properties.TypeUuid.eq(type.getUuid())).count();
            type.setCount(count);
            //更新类型总计
            MainApplication.getInstance().getDaoSession().getColockInTypeDao().update(type);
            emitter.onNext(STATE.SAVE_FINISH);
            emitter.onComplete();
        }, (Consumer<STATE>) data -> {
            state.setValue(data);
        });
    }

    /**
     * 获取所有打卡记录
     * @param count
     */
    public void queryAllColockIn(int count) {
        AsyncUtils.run(emitter -> {
            List<ColockIn> list = MainApplication.getInstance().getDaoSession().getColockInDao()
                    .queryBuilder().orderDesc(ColockInDao.Properties.CreateTime)
                    .where(ColockInDao.Properties.DeleteFlag.notEq(1)).limit(count).list();
            emitter.onNext(list);
            emitter.onComplete();
        }, (Consumer<List<ColockIn>>) datas -> colockIns.setValue(datas));
    }

    /**
     * 删除一条打卡记录
     * @param colockIn
     */
    public void removeColockIn(ColockIn colockIn) {
        AsyncUtils.run(emitter -> {
            colockIn.setDeleteFlag(1);
            MainApplication.getInstance().getDaoSession().getColockInDao().update(colockIn);
            emitter.onNext(DELETE_FINISH);
            emitter.onComplete();
        }, (Consumer<STATE>) result -> state.setValue(result));
    }

    /**
     * 删除一个打卡类型
     * @param type
     */
    public void removeType(ColockInType type) {
        AsyncUtils.run(emitter -> {
            type.setDeleteflag(1);
            MainApplication.getInstance().getDaoSession().getColockInTypeDao().update(type);
            emitter.onNext(true);
            emitter.onComplete();
        }, (Consumer<Boolean>) result -> state.setValue(DELETE_FINISH));
    }

    public enum STATE {SAVE_FINISH, DELETE_FINISH}

    /**
     * 查询所有打卡类型
     */
    public void queryAllType() {
        AsyncUtils.run(emitter -> {
            List<ColockInType> data = MainApplication.getInstance().getDaoSession()
                    .getColockInTypeDao().queryBuilder()
                    .where(ColockInTypeDao.Properties.Deleteflag.notEq(1)).list();
            emitter.onNext(data);
            emitter.onComplete();
        }, (Consumer<List<ColockInType>>) datas -> colockInTypes.setValue(datas));
    }

    /**
     * 查询assets中的所有图标
     */
    public void readAllTypeIcons() {
        AsyncUtils.run(emitter -> {
            List<Bitmap> images = AssetUtil.getAllImageList(
                    MainApplication.getInstance().getApplicationContext(), "colockintype");
            emitter.onNext(images);
            emitter.onComplete();
        }, (Consumer<List<Bitmap>>) stringBitmapMap -> icons.setValue(stringBitmapMap));
    }

    /**
     * 保存一条打卡类型
     *
     * @param type 打卡类型
     */
    public void saveType(ColockInType type) {
        AsyncUtils.run(emitter -> {
            MainApplication.getInstance().getDaoSession().getColockInTypeDao().insert(type);
            emitter.onNext(STATE.SAVE_FINISH);
            emitter.onComplete();
        }, (Consumer<STATE>) data -> state.setValue(data));
    }
}
