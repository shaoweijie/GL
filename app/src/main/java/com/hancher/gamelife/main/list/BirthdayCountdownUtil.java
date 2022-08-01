package com.hancher.gamelife.main.list;

import com.hancher.common.utils.android.LogUtil;
import com.nlf.calendar.Lunar;
import com.nlf.calendar.Solar;

import java.util.Date;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/8/31 0031 0:17 <br/>
 * 描述 : 生日倒计时
 */
public class BirthdayCountdownUtil {

    /**
     * 计算阳历倒计时
     *
     * @param solar 目标阳历
     * @return 倒计时，0即为今天，-1 报错
     */
    public static int getSolarDateCountdown(Solar solar) {
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
    public static  int getLunarDateCountdown(Lunar lunar) {
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
}
