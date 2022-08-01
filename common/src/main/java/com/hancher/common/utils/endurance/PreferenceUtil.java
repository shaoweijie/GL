package com.hancher.common.utils.endurance;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.text.TextUtils;

import com.hancher.common.base.mvvm02.BaseApplication;
import com.hancher.common.utils.android.LogUtil;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 作者：Hancher
 * 时间：2020/5/7.
 * 邮箱：ytu_shaoweijie@163.com
 * 版本：v1.0
 * <p>
 * 说明：
 */
public class PreferenceUtil {
    public static final String FILENAME = "HancherPerference";

    public static String getString(String key, String defaultValue) {
        SharedPreferences share = BaseApplication.getInstance().getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        return share.getString(key, defaultValue);
    }

    public static int getInt(String key, int defaultValue) {
        SharedPreferences share = BaseApplication.getInstance().getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        return share.getInt(key, defaultValue);
    }

    public static long getLong(String key, long defaultValue) {
        SharedPreferences share = BaseApplication.getInstance().getSharedPreferences(FILENAME,Context.MODE_PRIVATE);
        return share.getLong(key, defaultValue);
    }
    public static boolean getBoolean(String key,boolean defaultValue){
        SharedPreferences share = BaseApplication.getInstance().getSharedPreferences(FILENAME,Context.MODE_PRIVATE);
        return share.getBoolean(key, defaultValue);
    }
    public static void setValue(HashMap<String,String> values){
        SharedPreferences share = BaseApplication.getInstance().getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = share.edit();
        for(Map.Entry<String, String> entry: values.entrySet()){
            edit.putString(entry.getKey(), entry.getValue());
        }
        edit.commit() ;
    }
    public static void setValue(String key, String value){
        SharedPreferences share = BaseApplication.getInstance().getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = share.edit();
        edit.putString(key, value);
        edit.commit() ;
    }
    public static void setValue(String key, long value){
        SharedPreferences share = BaseApplication.getInstance().getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = share.edit();
        edit.putLong(key, value);
        edit.commit() ;
    }
    public static void setValue(String key, boolean value){
        SharedPreferences share = BaseApplication.getInstance().getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = share.edit();
        edit.putBoolean(key, value);
        edit.commit() ;
    }

    public static void setParcelable(String key, Parcelable value) {
        String valueStr = ParcelableUtil.object2String(value);
        setValue(key,valueStr);
    }

    public static <T> T getParcelable(String key, Parcelable.Creator<T> creator) {
        return getParcelable(key,creator,null);
    }

    public static <T> T getParcelable(String key, Parcelable.Creator<T> creator, Parcelable value) {
        String valueStr = PreferenceUtil.getString(key, null);
        if (TextUtils.isEmpty(valueStr)){
            return null;
        }
        return ParcelableUtil.unmarshall(valueStr,creator);
    }

    public static void setSerializable(String key, Serializable value) {
        String valueStr = SerializableUtil.serialize(value);
        setValue(key,valueStr);
    }

    public static <T> T getSerializable(String key) {
        try {
            String valueStr = PreferenceUtil.getString(key, null);
            T objs = (T) SerializableUtil.deSerialization(valueStr);
            return objs;
        } catch (Exception e){
            LogUtil.e(e);
        }
        return null;
    }



}
