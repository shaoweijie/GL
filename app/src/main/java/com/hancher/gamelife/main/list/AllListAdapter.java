package com.hancher.gamelife.main.list;

import android.graphics.Bitmap;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.module.UpFetchModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hancher.common.utils.java.DateUtil;
import com.hancher.common.utils.java.TextUtil;
import com.hancher.gamelife.R;
import com.hancher.gamelife.greendao.Character;
import com.hancher.gamelife.greendao.ColockIn;
import com.hancher.gamelife.greendao.Diary;
import com.nlf.calendar.Solar;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;
import java.util.List;

public class AllListAdapter extends BaseMultiItemQuickAdapter<AllListAdapter.AllListItem, BaseViewHolder>
        implements LoadMoreModule, UpFetchModule {

    public static final int DIARY = 0;
    public static final int COLOCKIN = 1;
    public static final int BIRTHDAY = 2;

    public AllListAdapter(@Nullable List<AllListItem> data) {
        super(data);
        addItemType(DIARY, R.layout.colock_in_list_item);
        addItemType(COLOCKIN, R.layout.colock_in_list_item);
        addItemType(BIRTHDAY, R.layout.colock_in_list_item);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, AllListItem item) {
        switch (item.getItemType()) {
            case DIARY:
                String date = DateUtil.changeDate(item.getDiary().getCreatetime(), DateUtil.Type.LONG_STAMP,
                        DateUtil.Type.STRING_YMD_HMS);
                String tag = item.getDiary().getTag();
                String secordLine = "";
                if (!TextUtil.isEmpty(date)) secordLine += date;
                if (!TextUtil.isEmpty(tag)) secordLine += " " + tag;
                baseViewHolder.setText(R.id.item_time, secordLine);
                baseViewHolder.setText(R.id.item_title, item.getDiary().getTitle());
                baseViewHolder.setImageResource(R.id.item_image, R.drawable.item_note);
                break;
            case COLOCKIN:
                String date2 = DateUtil.changeDate(item.getColockIn().getCreateTime(), DateUtil.Type.LONG_STAMP,
                        DateUtil.Type.STRING_YMD_HMS);
                baseViewHolder.setText(R.id.item_time, date2);
                baseViewHolder.setText(R.id.item_title, item.getColockTitle()+" "+item.getColockIn().getDescription());
                baseViewHolder.setImageBitmap(R.id.item_image, item.getColockImage());
                break;
            case BIRTHDAY:
                baseViewHolder.setText(R.id.item_time, item.getBirthdayString());
                baseViewHolder.setText(R.id.item_title, item.getBirthdayName());
                baseViewHolder.setImageResource(R.id.item_image, item.getCharacter().getBirthdaySolar() ?
                        R.drawable.item_solar : R.drawable.item_lunar);
                break;
            default:
                break;
        }
    }

    /**
     * 作者 : Hancher ytu_shaoweijie@163.com <br/>
     * 时间 : 2021/6/20 0020 15:58 <br/>
     * 描述 : 混合条目
     */
    public static class AllListItem implements MultiItemEntity {

        int type;
        Diary diary;

        ColockIn colockIn;
        Bitmap colockImage;
        String colockTitle;

        Character character;
        String birthdayString;
        String birthdayName;

        long createtime;

        public AllListItem(Diary item) {
            type = DIARY;
            diary = item;
            createtime = item.getCreatetime();
        }

        public AllListItem(ColockIn item, Bitmap colockImage, String title) {
            type = COLOCKIN;
            colockIn = item;
            this.colockImage = colockImage;
            createtime = item.getCreateTime();
            colockTitle = title;
        }

        public AllListItem(Character item, int birthdayCountdown) {
            type = BIRTHDAY;
            character = item;
            createtime = System.currentTimeMillis() - birthdayCountdown * 24 * 60 * 60 * 1000;
            birthdayName = String.format("距离【%s】生日还有 %d 天", item.getName(), birthdayCountdown);
            Date birthday;
            if (item.getBirthday().contains("-")) {
                birthday = DateUtil.changeDate(item.getBirthday(), DateUtil.Type.STRING_YMD, DateUtil.Type.DATE);
            } else if (item.getBirthday().contains("/")){
                birthday = DateUtil.changeDate(item.getBirthday(), DateUtil.Type.STRING_YMD_2, DateUtil.Type.DATE);
            } else {
                return;
            }
            Solar solar = Solar.fromDate(birthday);
            birthdayString = String.format("%s %s座",
                    item.getBirthdaySolar() ? solar.toString() : solar.getLunar().toString(),
                    solar.getXingZuo());
        }

        @Override
        public int getItemType() {
            return type;
        }

        public String getColockTitle() {
            return colockTitle;
        }

        public AllListItem setColockTitle(String colockTitle) {
            this.colockTitle = colockTitle;
            return this;
        }

        public String getBirthdayName() {
            return birthdayName;
        }

        public AllListItem setBirthdayName(String birthdayName) {
            this.birthdayName = birthdayName;
            return this;
        }

        public String getBirthdayString() {
            return birthdayString;
        }

        public AllListItem setBirthdayString(String birthdayString) {
            this.birthdayString = birthdayString;
            return this;
        }

        public Bitmap getColockImage() {
            return colockImage;
        }

        public AllListItem setColockImage(Bitmap colockImage) {
            this.colockImage = colockImage;
            return this;
        }

        public Diary getDiary() {
            return diary;
        }

        public AllListItem setDiary(Diary diary) {
            this.diary = diary;
            return this;
        }

        public ColockIn getColockIn() {
            return colockIn;
        }

        public AllListItem setColockIn(ColockIn colockIn) {
            this.colockIn = colockIn;
            return this;
        }

        public Character getCharacter() {
            return character;
        }

        public AllListItem setCharacter(Character character) {
            this.character = character;
            return this;
        }

        public long getCreatetime() {
            return createtime;
        }

        public AllListItem setCreatetime(long createtime) {
            this.createtime = createtime;
            return this;
        }
    }
}
