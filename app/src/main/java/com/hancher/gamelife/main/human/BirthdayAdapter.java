package com.hancher.gamelife.main.human;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.hancher.common.utils.android.LogUtil;
import com.hancher.common.utils.java.DateUtil;
import com.hancher.gamelife.R;
import com.hancher.gamelife.greendao.Character;
import com.nlf.calendar.Lunar;
import com.nlf.calendar.Solar;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/7/27 0027 22:13 <br/>
 * 描述 : 生日列表适配器
 */
public class BirthdayAdapter extends BaseQuickAdapter<BirthdayAdapter.BirthdayItem, BaseViewHolder> {

    public static final int TYPE_SOLAR = 0;
    public static final int TYPE_LUNAR = 1;

    public BirthdayAdapter() {
        super(R.layout.recycleritem_birthday);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, BirthdayAdapter.BirthdayItem item) {
        switch (item.getType()) {
            case TYPE_SOLAR:
                baseViewHolder.setImageResource(R.id.img_solar_lunar, R.drawable.item_solar);
                break;
            case TYPE_LUNAR:
                baseViewHolder.setImageResource(R.id.img_solar_lunar, R.drawable.item_lunar);
                break;
        }

        baseViewHolder.setText(R.id.text_name, item.getName());
        baseViewHolder.setText(R.id.text_date, item.getDate());

        if (item.getCoutdown() == -1) {
            baseViewHolder.setText(R.id.text_countdown, "今年没有生日");
        } else if (item.getCoutdown() == 0) {
            baseViewHolder.setText(R.id.text_countdown, "今天");
        } else {
            baseViewHolder.setText(R.id.text_countdown, item.getCoutdown() + "天");
        }
    }

    public static class BirthdayItem {
        public int type;
        public String name;
        public String date;
        public String picture;
        public Character character;
        public String uuid;
        public int coutdown;

        public BirthdayItem(Character item, int itemType) {
            type = itemType;
            name = item.getName();
            character = item;
            uuid = item.getUuid();

            //获取当前阳历日期
//            LogUtil.d("date:"+item.getBirthday());
            Date solarDate = null;
            try {
                if (item.getBirthday().contains("-")) {
                    solarDate = DateUtil.changeDate(item.getBirthday(), DateUtil.Type.STRING_YMD, DateUtil.Type.DATE);
                } else if (item.getBirthday().contains("/")){
                    solarDate = DateUtil.changeDate(item.getBirthday(), DateUtil.Type.STRING_YMD_2, DateUtil.Type.DATE);
                }
                if (solarDate == null) {
                    //另一种格式的字符串
                    solarDate = DateUtil.changeDate(item.getBirthday(), DateUtil.Type.STRING_YMD_3, DateUtil.Type.DATE);
                }
            } catch (Exception e) {
                solarDate = new Date();
            }
            //根据阳历日期更新倒计时、显示日期
            Solar solar = Solar.fromDate(solarDate);
            if (type == TYPE_SOLAR) {
                date = item.getBirthday();
                coutdown = getSolarDateCountdown(solar);
            } else {
                Lunar lunar = solar.getLunar();
                date = lunar.toString();
                coutdown = getLunarDateCountdown(lunar);
            }
        }

        /**
         * 计算阳历倒计时
         *
         * @param solar 目标阳历
         * @return 倒计时，0即为今天，-1 报错
         */
        private int getSolarDateCountdown(Solar solar) {
            try {
                Solar currentSolar = Solar.fromDate(new Date());
                Solar nextSolar = Solar.fromYmd(currentSolar.getYear(), solar.getMonth(), solar.getDay());
                // 若今年已经过完阳历生日，查找下一年阳历生日
                if (currentSolar.getJulianDay() - nextSolar.getJulianDay() > 1) {
                    nextSolar = Solar.fromYmd(currentSolar.getYear() + 1, solar.getMonth(), solar.getDay());
                }
                // 向上取整
                return (int) Math.ceil(nextSolar.getJulianDay() - currentSolar.getJulianDay());
            } catch (Exception e) {
                LogUtil.w("日期错误:", e);
            }
            return -1;
        }

        /**
         * 计算阴历倒计时
         *
         * @param lunar 目标阴历
         * @return 倒计时，0即为今天，-1 报错（很不幸，有的人一年中没有阴历生日，有的人却有两个）
         */
        private int getLunarDateCountdown(Lunar lunar) {
            try {
                Solar currentSolar = Solar.fromDate(new Date());
                Lunar currentLunar = currentSolar.getLunar();
                Lunar nextLunar = Lunar.fromYmd(currentLunar.getYear(), Math.abs(lunar.getMonth()), lunar.getDay());
                // 若今年已经过完阳历生日，查找下一年阳历生日
                if (currentLunar.getSolar().getJulianDay() - nextLunar.getSolar().getJulianDay() > 1) {
                    //当一个人有两个阴历生日时，月份有两个，lunar转换成了负数
                    nextLunar = Lunar.fromYmd(currentLunar.getYear() + 1, Math.abs(lunar.getMonth()), lunar.getDay());
                }
                // 向上取整
                return (int) Math.ceil(nextLunar.getSolar().getJulianDay() - currentLunar.getSolar().getJulianDay());
            } catch (Exception e) {
                LogUtil.w("日期错误:", e);
            }
            return -1;
        }

        public String getUuid() {
            return uuid;
        }

        public int getType() {
            return type;
        }

        public String getName() {
            return name;
        }

        public String getDate() {
            return date;
        }

        public String getPicture() {
            return picture;
        }

        public Character getCharacter() {
            return character;
        }

        public int getCoutdown() {
            return coutdown;
        }
    }
}
