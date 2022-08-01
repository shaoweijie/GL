package com.hancher.common.utils.java;

import com.hancher.common.utils.android.LogUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 作者：Hancher
 * 时间：2020/1/4.
 * 邮箱：ytu_shaoweijie@163.com
 * 版本：v1.0
 * <p>
 * 说明：
 */
public class TextUtil {
    public static String long2String(long d) {
        return double2String(d,0);
    }
    public static String double2String(double d) {
        return double2String(d,6);
    }

    public static String double2String(double d,int fraction){
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(fraction);
        return df.format(d);
    }
    /**
     * Returns true if the string is null or 0-length.
     * @param str the string to be examined
     * @return true if str is null or zero length
     */
    public static boolean isEmpty(CharSequence str) {
        return str == null || str.length() == 0;
    }
    /**
     * 自动格式化计算机二进制数据大小
     * @param size 2048
     * @param code KB
     * @return 2MB
     */
    public static String changeSize(long size, String code){
        String[] ranks = {"B","KB","MB","GB","TB"};
        int rank = -1;
        for (int i = 0; i < ranks.length; i++) {
            if(ranks[i].equals(code)){
                rank = i;
                break;
            }
        }
        if (rank==-1){
            LogUtil.e("rank=-1");
            return null;
        }

        double result = size;
        while ((result>1023 && rank<4) || (result<1 && rank>0)){
            if (result>1023){
                result/=1024;
                rank++;
            }else {
                result*=1024;
                rank--;
            }
        }
        java.text.DecimalFormat df = new java.text.DecimalFormat("#0.00");
        return new StringBuilder(df.format(result))+ranks[rank];
    }

    /**
     * 是否是email
     *
     * @return boolean正确的邮箱格式
     */
    public static boolean isEmail(String str) {
        String regex = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        return match(regex, str);
    }


    /**
     * @param regex 正则表达式字符串
     * @param str   要匹配的字符串
     * @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
     */
    private static boolean match(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * 获取属性名数组
     */
    public static String[] getFiledName(Object o) {
        Field[] fields = o.getClass().getDeclaredFields();
        String[] fieldNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            fieldNames[i] = fields[i].getName();
        }
        return fieldNames;
    }

    /**
     * 根据属性名获取属性值
     */
    public static Object getFieldValueByName(String fieldName, Object o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[]{});
            Object value = method.invoke(o, new Object[]{});
            return value;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 判断是否为空，或者为字符串NULL
     * @param s "NULL"
     * @return true
     */
    public final static boolean isEmptyOrNullStr(String s){
        if (TextUtil.isEmpty(s) || "NULL".equalsIgnoreCase(s))
            return true;
        return false;
    }

    /**
     * 格式化浮点数为字符串
     * @param number 3.0
     * @return "3"
     */
    public static String formatFloat(float number){
        if (number - ((int)number) > 0 ){
            return String.valueOf(number);
        }
        return String.valueOf((int)number);
    }

    /**
     * 判断坐标是否在中国，相当不标准的坐标计算
     * @param longitude 经度
     * @param laititude 纬度
     * @return
     */
    public static boolean isPositionInChina(double longitude, double laititude){
        return longitude > 73 && longitude <140 && laititude >73 && laititude <104;
    }
}
