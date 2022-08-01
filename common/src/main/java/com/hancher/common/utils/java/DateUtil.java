package com.hancher.common.utils.java;

import com.hancher.common.utils.android.LogUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/3/1 10:08 <br/>
 * 描述 : 日期工具类，方便使用
 */
public class DateUtil {

    private static final long ONE_SECOND = 1000L;
    private static final long ONE_MINUTE = 60000L;
    private static final long ONE_HOUR = 3600000L;
    private static final long ONE_DAY = 86400000L;
    private static final long ONE_WEEK = 604800000L;
    private static final long ONE_MONTH = 2592000000L;
    private static final long ONE_YEAR = 31536000000L;

    private static final String ONE_SECOND_AGO = "秒前";
    private static final String ONE_MINUTE_AGO = "分钟前";
    private static final String ONE_HOUR_AGO = "小时前";
    private static final String ONE_DAY_AGO = "天前";
    private static final String ONE_WEEK_AGO = "周前";
    private static final String ONE_MONTH_AGO = "月前";
    private static final String ONE_YEAR_AGO = "年前";

    static Map<Type, String> strFormatMap = new HashMap<>();

    static {

        strFormatMap.put(Type.STRING_D, "dd");

        strFormatMap.put(Type.STRING_YM, "yyyy-MM");

        strFormatMap.put(Type.STRING_YMD, "yyyy-MM-dd");
        strFormatMap.put(Type.STRING_YMD_2, "yyyy/MM/dd");
        strFormatMap.put(Type.STRING_YMD_3, "yyyy/M/dd");
        strFormatMap.put(Type.STRING_HMS, "HH:mm:ss");

        strFormatMap.put(Type.STRING_YMD_HM, "yyyyMMdd HHmm");
        strFormatMap.put(Type.STRING_YMD_HM_2, "yyyy-MM-dd HH:mm");
        strFormatMap.put(Type.STRING_YMD_HM_3, "yyyy-MM-dd'T'HH:mmX");


        strFormatMap.put(Type.STRING_YMD_HMS, "yyyy-MM-dd HH:mm:ss");
        strFormatMap.put(Type.STRING_YMD_HMS_2, "yyyy/M/dd HH:mm:ss");
        strFormatMap.put(Type.STRING_YMD_HMS_3, "yyyyMMdd_HHmmss");

        strFormatMap.put(Type.STRING_YMD_HMS_SSS, "yyyyMMdd_HHmmss_SSS");

    }

    public enum Type {
        /**
         * dd
         */
        STRING_D,

        /**
         * yyyy-YM
         */
        STRING_YM,

        /**
         * yyyy-MM-dd
         */
        STRING_YMD,
        /**
         * yyyy/MM/dd
         */
        STRING_YMD_2,
        /**
         * yyyy/M/dd
         */
        STRING_YMD_3,
        /**
         * HH:mm:ss
         */
        STRING_HMS,

        /**
         * yyyyMMdd HHmm
         */
        STRING_YMD_HM,
        /**
         * yyyy-MM-dd HH:mm
         */
        STRING_YMD_HM_2,
        /**
         * yyyy-MM-dd'T'HH:mmX
         */
        STRING_YMD_HM_3,

        /**
         * yyyy-MM-dd HH:mm:ss
         */
        STRING_YMD_HMS,
        /**
         * yyyy/M/dd HH:mm:ss
         */
        STRING_YMD_HMS_2,
        /**
         * "yyyyMMdd_HHmmss"
         */
        STRING_YMD_HMS_3,

        /**
         * "yyyyMMdd_HHmmss_SSS"
         */
        STRING_YMD_HMS_SSS,

        /**
         * 星期三
         */
        STRING_WEEK,
        /**
         * 凌晨
         */
        STRING_DAY_DESCRIPTION,
        /**
         * 昨天，2天前
         */
        STRING_RELATIVE,

        /**
         * 3
         */
        INT_DAY_OF_WEEK,
        /**
         * 31 dd
         */
        INT_DAY_OF_MONTH,
        /**
         * 一月中的第几个周
         */
        INT_WEEK_OF_MONTH,

        /**
         * 精确度到毫秒 的 时间戳
         */
        STRING_STAMP,
        /**
         * 精确度到秒 的 时间戳
         */
        STRING_STAMP_S,
        LONG_STAMP,
        DATE,

        //非当前时间的类型
        STRING_YMD_LAST_MONTH,
        STRING_YMD_YESDAY,
        LONG_FIRST_OF_MONTH,
        LONG_FIRST_OF_NEXT_MONTH,
        DATE_FIRST_OF_MONTH,
        DATE_FIRST_OF_NEXT_MONTH,
        ;
    }

    /**
     * 获取当前时间
     * @param outType
     * @return
     */
    public static<T> T getNow(Type outType){
        return getOutType(new Date(),outType);
    }
    /**
     * 转换类型
     * @param time 输入
     * @param inType 输入类型
     * @param outType 输出类型
     * @param <T> 输出
     * @return 输出
     */
    public static<T> T changeDate(Object time, Type inType, Type outType) {
        Date date = getDate(time,inType);
        return getOutType(date,outType);
    }

    private static<T> T getOutType(Date date, Type outType) {
        if (date == null) {
            LogUtil.w("传入对象为空");
        }
        try {
            SimpleDateFormat simpleDateFormat;
            String strFormat = strFormatMap.get(outType);
            if (!TextUtil.isEmpty(strFormat)) {
                simpleDateFormat = new SimpleDateFormat(strFormat, Locale.CHINA);
                String strResult = simpleDateFormat.format(date);
                return (T) strResult;
            }
            Object result = null;
            Calendar calendar = Calendar.getInstance(Locale.CHINA);
            switch (outType) {
                case STRING_YMD_YESDAY:
                    calendar.setTime(date);
                    calendar.add(Calendar.DAY_OF_MONTH, -1);
                    simpleDateFormat = new SimpleDateFormat(strFormatMap.get(Type.STRING_YMD), Locale.CHINA);
                    result = simpleDateFormat.format(calendar.getTime());
                    break;
                case STRING_YMD_LAST_MONTH:
                    Date lastMonth = getFirstOfMonth(date, -1);
                    simpleDateFormat = new SimpleDateFormat(strFormatMap.get(Type.STRING_YMD), Locale.CHINA);
                    result = simpleDateFormat.format(lastMonth);
                    break;
                case LONG_STAMP:
                    result = date.getTime();
                    break;
                case STRING_STAMP:
                    result = String.valueOf(date.getTime());
                    break;
                case STRING_STAMP_S:
                    String tmp = String.valueOf(date.getTime());
                    result = tmp.substring(0, tmp.length() - 3);
                    break;
                case STRING_WEEK:
                    result = getWeek(date);
                    break;
                case INT_DAY_OF_WEEK:
                    calendar.setTime(date);
                    result = calendar.get(Calendar.DAY_OF_WEEK) - 1;
                    if ((int) result == 0) {
                        result = 7;
                    }
                    break;
                case INT_DAY_OF_MONTH:
                    calendar.setTime(date);
                    result = calendar.get(Calendar.DAY_OF_MONTH);
                    break;
                case INT_WEEK_OF_MONTH:
                    calendar.setFirstDayOfWeek(Calendar.MONDAY);
                    calendar.setTime(date);
                    result = calendar.get(Calendar.WEEK_OF_MONTH);
                    break;
                case STRING_RELATIVE:
                    result = getRelativeTime(new Date(), date);
                    break;
                case DATE:
                    result = date;
                    break;
                case STRING_DAY_DESCRIPTION:
                    result = getTimeDescription(date);
                    break;
                case DATE_FIRST_OF_MONTH:
                    result = getFirstOfMonth(date,0);
                    break;
                case DATE_FIRST_OF_NEXT_MONTH:
                    result = getFirstOfMonth(date,1);
                    break;
                case LONG_FIRST_OF_MONTH:
                    result = getFirstOfMonth(date,0).getTime();
                    break;
                case LONG_FIRST_OF_NEXT_MONTH:
                    result = getFirstOfMonth(date,1).getTime();
                    break;
                default:
                    LogUtil.e("不支持当前输出类型");
                    break;
            }
            return (T) result;
        } catch (Exception e) {
            LogUtil.e("date 转输出格式失败",e);
        }
        return null;
    }

    private static Date getDate(Object time, Type inType) {
        try {
            if (time == null) {
                LogUtil.w("传入对象为空");
                return null;
            }
            SimpleDateFormat simpleDateFormat;
            String strFormat = strFormatMap.get(inType);
            if (!TextUtil.isEmpty(strFormat)) {
                simpleDateFormat = new SimpleDateFormat(strFormat, Locale.CHINA);
                return simpleDateFormat.parse((String) time);
            }
            switch (inType) {
                case STRING_STAMP:
                    return new Date(Long.parseLong((String) time));
                case LONG_STAMP:
                    return new Date((long) time);
                case DATE:
                    return (Date) time;
                default:
                    LogUtil.e("不支持当前输入格式");
                    return null;
            }
        } catch (Exception e){
            LogUtil.w("time 转中间格式 date 失败",e);
        }
        return null;
    }
    private static String getTimeDescription(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null){
            calendar.setTime(date);
        }
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour >= 0 && hour < 5) {
            return "凌晨";
        } else if (hour >= 5 && hour < 7) {
            return "清晨";
        } else if (hour >= 7 && hour < 9) {
            return "早上";
        } else if (hour >= 9 && hour < 12) {
            return "上午";
        } else if (hour >= 12 && hour < 14) {
            return "中午";
        } else if (hour >= 14 && hour < 17) {
            return "下午";
        } else if (hour >= 17 && hour < 19) {
            return "傍晚";
        } else if (hour >= 19 && hour < 21) {
            return "晚上";
        } else if (hour >= 21 && hour < 24) {
            return "深夜";
        }
        return "";
    }
    private static String getWeek(Date date){
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTime(date);
        switch (calendar.get(Calendar.DAY_OF_WEEK)){
            case 1:return "周日";
            case 2:return "周一";
            case 3:return "周二";
            case 4:return "周三";
            case 5:return "周四";
            case 6:return "周五";
            case 7:return "周六";
            default: return "";
        }
    }
    /**
     * 获取相对时间
     * @param referenceDate 参考时间
     * @param testDate 测试时间
     * @return 参考时间：20190615，测试时间20190614，返回昨天
     */
    public static String getRelativeTime(Date referenceDate, Date testDate) {
        long delta = referenceDate.getTime() - testDate.getTime();
        if (delta < ONE_SECOND){
            return "刚刚";
        } else if (delta < ONE_MINUTE) {
            long seconds = delta/ONE_SECOND;
            return seconds + ONE_SECOND_AGO;
        }else if (delta < ONE_HOUR) {
            return (delta/ONE_MINUTE) + ONE_MINUTE_AGO;
        }else if (delta < ONE_DAY) {
            return (delta/ONE_HOUR) + ONE_HOUR_AGO;
        } else if (delta < 2 * ONE_DAY) {
            return "昨天";
        } else if (delta < ONE_WEEK) {
            return (delta / ONE_DAY) + ONE_DAY_AGO;
        } else if (delta < ONE_MONTH) {
            return (delta/ONE_WEEK) + ONE_WEEK_AGO;
        } else if (delta < ONE_YEAR) {
            return (delta/ONE_MONTH) + ONE_MONTH_AGO;
        } else {
            return (delta/ONE_YEAR) + ONE_YEAR_AGO;
        }
    }


    private static Date getFirstOfMonth(Date date, int addMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (addMonth!=0){
            calendar.add(Calendar.MONTH,addMonth);
        }
        return new Date(calendar.get(Calendar.YEAR)-1900, calendar.get(Calendar.MONTH),1);
    }
}
