package com.hancher.gamelife.main.human;

import android.graphics.Bitmap;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hancher.common.utils.android.AsyncUtils;
import com.hancher.common.utils.android.ImageUtil;
import com.hancher.common.utils.android.LogUtil;
import com.hancher.common.utils.java.TextUtil;
import com.hancher.gamelife.MainApplication;
import com.hancher.gamelife.bak.list.CharacterExtAdapter;
import com.hancher.gamelife.greendao.Character;
import com.hancher.gamelife.greendao.CharacterDao;
import com.hancher.gamelife.greendao.CharacterExt;
import com.hancher.gamelife.greendao.CharacterExtDao;
import com.hancher.gamelife.greendao.Image;
import com.hancher.gamelife.greendao.ImageDao;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/7/27 0027 21:52 <br/>
 * 描述 : 人物列表
 */
public class CharactersViewModel extends ViewModel {
    private MutableLiveData<List<CharactersAdapter.CharacterItem>> characters = new MutableLiveData<>();
    private MutableLiveData<List<BirthdayAdapter.BirthdayItem>> birthdays = new MutableLiveData<>();
    private MutableLiveData<List<BirthdayAdapter.BirthdayItem>> characterList = new MutableLiveData<>();
    private MutableLiveData<List<CharacterExtAdapter.CharacterExtItem>> characterExts = new MutableLiveData<>();
    private MutableLiveData<Character> character = new MutableLiveData<>();
    private MutableLiveData<Image> head = new MutableLiveData<>();
    public MutableLiveData<List<CharactersAdapter.CharacterItem>> getCharacters() {
        return characters;
    }
    public MutableLiveData<List<BirthdayAdapter.BirthdayItem>> getBirthdays() {
        return birthdays;
    }
    public MutableLiveData<List<BirthdayAdapter.BirthdayItem>> getCharacterList() {
        return characterList;
    }
    public MutableLiveData<List<CharacterExtAdapter.CharacterExtItem>> getCharacterExts() {
        return characterExts;
    }
    public MutableLiveData<Character> getCharacter() {
        return character;
    }


    public MutableLiveData<Image> getHead() {
        return head;
    }

// TODO: swj: 2021/9/29 0029 人物vm导入

    private MutableLiveData<List<BirthdayAdapter.BirthdayItem>> birthday = new MutableLiveData<>();

    public MutableLiveData<List<BirthdayAdapter.BirthdayItem>> getBirthday() {
        return birthday;
    }

    /**
     * 查询旧的生日列表，旧数据库不再使用
     * @deprecated
     */
    public void queryBirthdayData() {
//        AsyncUtils.run((ObservableOnSubscribe<List<BirthdayAdapter.BirthdayItem>>) emitter -> {
//            LogUtil.d("开始查询生日数据");
//            QueryBuilder<Humans> queryBuilder = MainApplication.getInstance().getDaoSession().getHumansDao().queryBuilder();
//            queryBuilder.where(queryBuilder.or(HumansDao.Properties.Deleteflag.isNull(), HumansDao.Properties.Deleteflag.notEq("1")));
//            List<Humans> queryData = queryBuilder.list();
//
//            List<BirthdayAdapter.BirthdayItem> listData = new ArrayList<>();
//
//            // 第一遍遍历出Fielduuid
//            for (Humans queryItem : queryData) {
//                if (!HumansFieldContract.FIELD_UUID_BIRTHDAY.equals(queryItem.getFielduuid())) {
//                    continue;
//                }
//
//                //第二次遍历，获取姓名、头像等数据
//                BirthdayAdapter.BirthdayItem solarItem = new BirthdayAdapter.BirthdayItem(BirthdayAdapter.TYPE_SOLAR);
//                BirthdayAdapter.BirthdayItem lunarItem = new BirthdayAdapter.BirthdayItem(BirthdayAdapter.TYPE_LUNAR);
//                int type = -1;
//                for (Humans queryItem2 : queryData) {
//                    if (queryItem.getRecorduuid().equals(queryItem2.getRecorduuid())) {
//                        if (HumansFieldContract.FIELD_UUID_NAME.equals(queryItem2.getFielduuid())) {
//                            solarItem.setName(queryItem2.getFieldvalue());
//                            lunarItem.setName(queryItem2.getFieldvalue());
//                        } else if (HumansFieldContract.FIELD_UUID_HEAD.equals(queryItem2.getFielduuid())) {
//                            solarItem.setPicture(queryItem2.getFieldvalue());
//                            lunarItem.setPicture(queryItem2.getFieldvalue());
//                        } else if (HumansFieldContract.FIELD_UUID_BIRTHDAY_SOLAR.equals(queryItem2.getFielduuid())) {
//                            if (queryItem2.getFieldvalue().contains("阴历")) {
//                                type = BirthdayAdapter.TYPE_LUNAR;
//                            } else if (queryItem2.getFieldvalue().contains("阳历")) {
//                                type = BirthdayAdapter.TYPE_SOLAR;
//                            }
//                        }
//                    }
//                }
//
//                //阳历阴历，设置生日日期标题
//                solarItem.setDate(queryItem.getFieldvalue());
//                Date date = DateUtil.changeDate(queryItem.getFieldvalue(), DateUtil.Type.STRING_YMD, DateUtil.Type.DATE);
//                Solar solar = Solar.fromDate(date);
//                Lunar lunar = solar.getLunar();
//                lunarItem.setDate(lunar.toString());
//
//                boolean haveNextSolarYear = true, haveNextLunarYear = true;
//                Solar currentSolar = Solar.fromDate(new Date());
//                //计算阳历倒计时
//                try {
//                    Solar nextSolar = Solar.fromYmd(currentSolar.getYear(), solar.getMonth(), solar.getDay());
//                    if (currentSolar.getJulianDay() - nextSolar.getJulianDay() > 1) {
//                        nextSolar = Solar.fromYmd(currentSolar.getYear() + 1, solar.getMonth(), solar.getDay());
//                    }
//                    solarItem.setCoutdown((int) Math.ceil(nextSolar.getJulianDay() - currentSolar.getJulianDay()));
//                } catch (Exception e) {
//                    LogUtil.w("日期错误:", e);
//                    haveNextSolarYear = false;
//                }
//                //计算阴历倒计时
//                try {
//                    Lunar currentLunar = currentSolar.getLunar();
//                    Lunar nextLunar = Lunar.fromYmd(currentLunar.getYear(), Math.abs(lunar.getMonth()), lunar.getDay());
//                    if (currentLunar.getSolar().getJulianDay() - nextLunar.getSolar().getJulianDay() > 1) {
//                        nextLunar = Lunar.fromYmd(currentLunar.getYear() + 1, Math.abs(lunar.getMonth()), lunar.getDay());
//                    }
//                    lunarItem.setCoutdown((int) Math.ceil(nextLunar.getSolar().getJulianDay() - currentLunar.getSolar().getJulianDay()));
//                } catch (Exception e) {
//                    LogUtil.w("日期错误:", e);
//                    haveNextLunarYear = false;
//                }
//
//                //设置跳转的uuid
//                solarItem.setHumanUuid(queryItem.getRecorduuid());
//                lunarItem.setHumanUuid(queryItem.getRecorduuid());
//
//                if (type == BirthdayAdapter.TYPE_LUNAR) {
//                    if (haveNextLunarYear) {
//                        listData.add(lunarItem);
//                    }
//                } else if (type == BirthdayAdapter.TYPE_SOLAR) {
//                    if (haveNextSolarYear) {
//                        listData.add(solarItem);
//                    }
//                } else {
//                    if (haveNextLunarYear) {
//                        listData.add(lunarItem);
//                    }
//                    if (haveNextSolarYear) {
//                        listData.add(solarItem);
//                    }
//                }
//            }
//
//            Collections.sort(listData, (o1, o2) -> o1.getCoutdown() - o2.getCoutdown());
//            emitter.onNext(listData);
//            emitter.onComplete();
//        }, birthdayItems -> {
//            LogUtil.d("查询到生日数据:" + birthdayItems.size());
//            birthdays.setValue(birthdayItems);
//        });
    }

    /**
     * 查询人物，并转换成列表中的单条
     * @param filiter
     */
    public void queryCharacters(String filiter) {
        AsyncUtils.run(new ObservableOnSubscribe<List<CharactersAdapter.CharacterItem>>() {
            @Override
            public void subscribe(@NotNull ObservableEmitter<List<CharactersAdapter.CharacterItem>> emitter) throws Exception {
                List<Character> characters = MainApplication.getInstance().getDaoSession()
                        .getCharacterDao().queryBuilder()
                        .where(CharacterDao.Properties.Name.like("%" + filiter + "%"))
                        .list();
                List<Image> images = MainApplication.getInstance().getDaoSession().getImageDao().queryBuilder().list();
                LogUtil.d("characters:" + characters.size() + ", images:" + images.size());
                //数据库转化rv实体
                List<CharactersAdapter.CharacterItem> rvList = new ArrayList<>();
                for (Character character : characters) {
                    CharactersAdapter.CharacterItem characterItem = new CharactersAdapter.CharacterItem()
                            .setName(character.getName())
                            .setUuid(character.getUuid());
                    Image image = findImageByUuid(images, character.getHeadPicUuid());
                    if (image != null) {
                        characterItem.setPicture(ImageUtil.string2Bitmap(image.content));
                    }
                    rvList.add(characterItem);
                }
                emitter.onNext(rvList);
                emitter.onComplete();
            }

            public Image findImageByUuid(List<Image> images, String headPicUuid) {
                for (Image image : images) {
                    if (image.getUuid().equals(headPicUuid)) {
                        return image;
                    }
                }
                return null;
            }
        }, humanListItems -> {
            characters.setValue(humanListItems);
        });
        return;
    }

    /**
     * 查询人物，并转换成生日单条
     */
    public void queryCharacterList(){
        AsyncUtils.run((ObservableOnSubscribe<List<BirthdayAdapter.BirthdayItem>>) emitter -> {
            List<Character> charactersTmp = MainApplication.getInstance().getDaoSession()
                    .getCharacterDao().loadAll();
            // 转换成生日所需要的item
            List<BirthdayAdapter.BirthdayItem> result = new ArrayList<>();
            for(Character item: charactersTmp){
                if (TextUtil.isEmpty(item.getBirthday())){
                    // 没有设置生日
                    continue;
                }
                // 获取阳历、阴历列表实体类
                BirthdayAdapter.BirthdayItem birthdayTmp,birthdayTmp2;
                if(item.getBirthdaySolar() == null){
                    birthdayTmp = new BirthdayAdapter.BirthdayItem(item, BirthdayAdapter.TYPE_LUNAR);
                    result.add(birthdayTmp);
                    birthdayTmp2 = new BirthdayAdapter.BirthdayItem(item, BirthdayAdapter.TYPE_SOLAR);
                    result.add(birthdayTmp2);
                } else if (item.getBirthdaySolar()){
                    birthdayTmp2 = new BirthdayAdapter.BirthdayItem(item, BirthdayAdapter.TYPE_SOLAR);
                    result.add(birthdayTmp2);
                } else {
                    birthdayTmp = new BirthdayAdapter.BirthdayItem(item, BirthdayAdapter.TYPE_LUNAR);
                    result.add(birthdayTmp);
                }
            }
            // 排序
            Collections.sort(result, (o1, o2) -> o1.getCoutdown() - o2.getCoutdown());

            emitter.onNext(result);
            emitter.onComplete();
        }, birthdayItems -> characterList.setValue(birthdayItems));
    }
    public void queryCharacter(String recordUuid) {
        AsyncUtils.run((ObservableOnSubscribe<Character>) emitter -> {
            Character character = MainApplication.getInstance().getDaoSession().getCharacterDao()
                    .queryBuilder().where(CharacterDao.Properties.Uuid.eq(recordUuid))
                    .unique();
            emitter.onNext(character);
            emitter.onComplete();
        }, character -> {
            this.character.setValue(character);
        });
    }

    public void queryHead(String headPicUuid) {
        AsyncUtils.run((ObservableOnSubscribe<Image>) emitter -> {
            if (TextUtil.isEmpty(headPicUuid)){
                emitter.onComplete();
                return;
            }
            Image image = MainApplication.getInstance().getDaoSession().getImageDao()
                    .queryBuilder().where(ImageDao.Properties.Uuid.eq(headPicUuid))
                    .unique();
            emitter.onNext(image);
            emitter.onComplete();
        }, image -> {
            this.head.setValue(image);
        });
    }

    public void saveImage(Bitmap bitmap) {
        LogUtil.d("");
        AsyncUtils.run((ObservableOnSubscribe<Image>) emitter -> {
            Bitmap imageBitmap = com.hancher.common.utils.android.ImageUtil.changeBitmapSize(bitmap, 256);
            String imageStr = ImageUtil.bitmap2String(imageBitmap);
            Image image = new Image();
            image.setContent(imageStr);
            image.setName("人物头像");
            MainApplication.getInstance().getDaoSession().getImageDao().insert(image);
            emitter.onNext(image);
            emitter.onComplete();
        }, image -> {
            head.setValue(image);
            character.getValue().setHeadPicUuid(image.getUuid());
        });
    }

    public void queryExt(String uuid) {
        LogUtil.i("开始查询人物ext信息:"+uuid);
        AsyncUtils.run((ObservableOnSubscribe<List<CharacterExtAdapter.CharacterExtItem>>) emitter -> {
            List<CharacterExt> exts = MainApplication.getInstance().getDaoSession().getCharacterExtDao().queryBuilder()
                    .where(CharacterExtDao.Properties.CharacterUuid.eq(uuid))
                    .list();
            LogUtil.d("数据库数量："+exts.size());
            List<CharacterExtAdapter.CharacterExtItem> extItems = new ArrayList<>();
            for(CharacterExt item: exts){
                extItems.add(new CharacterExtAdapter.CharacterExtItem().setKey(item.getKeyType())
                        .setValue(item.getValue())
                        .setUuid(item.getUuid()));
            }
            emitter.onNext(extItems);
            emitter.onComplete();
        }, value -> characterExts.setValue(value));
    }
}
