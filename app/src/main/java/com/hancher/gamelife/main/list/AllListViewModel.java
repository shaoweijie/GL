package com.hancher.gamelife.main.list;

import android.graphics.Bitmap;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hancher.common.utils.android.AsyncUtils;
import com.hancher.common.utils.android.ImageUtil;
import com.hancher.common.utils.java.DateUtil;
import com.hancher.common.utils.java.TextUtil;
import com.hancher.gamelife.MainApplication;
import com.hancher.gamelife.greendao.Character;
import com.hancher.gamelife.greendao.CharacterDao;
import com.hancher.gamelife.greendao.ColockIn;
import com.hancher.gamelife.greendao.ColockInDao;
import com.hancher.gamelife.greendao.ColockInType;
import com.hancher.gamelife.greendao.Diary;
import com.hancher.gamelife.greendao.DiaryDao;
import com.nlf.calendar.Solar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/8/30 0030 23:25 <br/>
 * 描述 :
 */
public class AllListViewModel extends ViewModel {
    MutableLiveData<List<AllListAdapter.AllListItem>> allList = new MutableLiveData<>();

    public MutableLiveData<List<AllListAdapter.AllListItem>> getAllList() {
        return allList;
    }

    public void queryAllList() {
        AsyncUtils.run(emitter -> {
            long oneMonthLast = System.currentTimeMillis() - 30 * 24 * 60 * 60 * 1000L;
            List<AllListAdapter.AllListItem> allListData = new ArrayList<>();
            // 日记
            List<Diary> diary = MainApplication.getInstance().getDaoSession().getDiaryDao().queryBuilder()
                    .where(DiaryDao.Properties.Deleteflag.notEq(1),
                            DiaryDao.Properties.Createtime.gt(oneMonthLast))
                    .list();
            for (Diary item : diary) {
                allListData.add(new AllListAdapter.AllListItem(item));
            }
            // 打卡
            List<ColockInType> colockInType = MainApplication.getInstance().getDaoSession().getColockInTypeDao().loadAll();
            HashMap<String, ColockInType> map = new HashMap<>();
            for(ColockInType item: colockInType){
                map.put(item.getUuid(), item);
            }
            List<ColockIn> colockIn = MainApplication.getInstance().getDaoSession().getColockInDao()
                    .queryBuilder()
                    .where(ColockInDao.Properties.DeleteFlag.notEq(1),
                            ColockInDao.Properties.CreateTime.gt(oneMonthLast))
                    .list();
            for (ColockIn item : colockIn) {
                ColockInType type = map.get(item.getTypeUuid());
                String imageStr = type.getImage();
                if (TextUtil.isEmpty(imageStr)) continue;
                Bitmap bitmap = ImageUtil.string2Bitmap(imageStr);
                allListData.add(new AllListAdapter.AllListItem(item, bitmap, type.getTitle()));
            }
            // 生日
            List<Character> character = MainApplication.getInstance().getDaoSession().getCharacterDao().queryBuilder()
                    .where(CharacterDao.Properties.Deleteflag.notEq(1))
                    .list();
            for (Character item : character) {
                int countdown = getCountDownBirthday(item.getBirthday(), item.getBirthdaySolar());
                if (countdown>-1 && countdown<31){
                    allListData.add(new AllListAdapter.AllListItem(item, countdown));
                }
            }
            Collections.sort(allListData, (o1, o2) -> {
                if (o1.getCreatetime() == o2.getCreatetime()){
                    return 0;
                }
                return (o1.getCreatetime() - o2.getCreatetime()) < 0 ? 1 : -1;
            });
            emitter.onNext(allListData);
            emitter.onComplete();
        }, (Consumer<List<AllListAdapter.AllListItem>>) allListItems -> this.allList.setValue(allListItems));
    }

    private int getCountDownBirthday(String birthday, Boolean birthdaySolar) {
//        LogUtil.d("birthday:"+birthday+", is solar:"+birthdaySolar);
        if (birthdaySolar == null || TextUtil.isEmpty(birthday)){
            return -1;
        }
        Date date;
        if (birthday.contains("-")){
            date = DateUtil.changeDate(birthday, DateUtil.Type.STRING_YMD, DateUtil.Type.DATE);
        } else {
            date = DateUtil.changeDate(birthday, DateUtil.Type.STRING_YMD_3, DateUtil.Type.DATE);
        }
        if (date == null){
            return -1;
        }
        Solar solar = Solar.fromDate(date);
        if (birthdaySolar){
            return BirthdayCountdownUtil.getSolarDateCountdown(solar);
        } else {
            return BirthdayCountdownUtil.getLunarDateCountdown(solar.getLunar());
        }
    }
}
