package com.hancher.common.utils.android;

import android.content.Context;
import android.os.Build;
import android.os.SystemClock;
import android.telephony.TelephonyManager;

import com.hancher.common.base.mvvm02.BaseApplication;
import com.tencent.mmkv.MMKV;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 作者：Hancher
 * 时间：2019/2/13.
 * 邮箱：ytu_shaoweijie@163.com
 * 版本：v1.0
 * <point>
 * 说明：
 */
public class PhoneInfoUtil {

    public static void collect(){
        TelephonyManager telephonyManager = (TelephonyManager) BaseApplication.getInstance()
                .getSystemService(Context.TELEPHONY_SERVICE);
        String imei;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            imei = telephonyManager.getDeviceId();
        } else {
            try {
                Method method = telephonyManager.getClass().getMethod("getImei");
                imei = (String) method.invoke(telephonyManager);
            } catch (Exception e) {
                imei = "";
            }
        }
        MMKV.defaultMMKV().encode("IMEI",imei);
        MMKV.defaultMMKV().encode("SDK_INT",Build.VERSION.SDK_INT);
        MMKV.defaultMMKV().encode("RELEASE",Build.VERSION.RELEASE);
        MMKV.defaultMMKV().encode("CODENAME",Build.VERSION.CODENAME);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            MMKV.defaultMMKV().encode("BASE_OS",Build.VERSION.BASE_OS);
        }
        MMKV.defaultMMKV().encode("INCREMENTAL",Build.VERSION.INCREMENTAL);
        MMKV.defaultMMKV().encode("BOARD",Build.BOARD);
        MMKV.defaultMMKV().encode("BRAND",Build.BRAND);
        MMKV.defaultMMKV().encode("DEVICE",Build.DEVICE);
        MMKV.defaultMMKV().encode("MODEL",Build.MODEL);
        MMKV.defaultMMKV().encode("HARDWARE",Build.HARDWARE);
        MMKV.defaultMMKV().encode("PRODUCT_TIME",Build.TIME);
        MMKV.defaultMMKV().encode("BOOTLOADER",Build.BOOTLOADER);
        MMKV.defaultMMKV().encode("HOST",Build.HOST);
        MMKV.defaultMMKV().encode("CHANGE_LIST_ID",Build.ID);
        MMKV.defaultMMKV().encode("MANUFACTURER",Build.MANUFACTURER);
        MMKV.defaultMMKV().encode("PRODUCT",Build.PRODUCT);
        MMKV.defaultMMKV().encode("TAGS",Build.TAGS);
        MMKV.defaultMMKV().encode("BUILD_TYPE",Build.TYPE);
        MMKV.defaultMMKV().encode("USER",Build.USER);
        MMKV.defaultMMKV().encode("FINGERPRINT",Build.FINGERPRINT);
        MMKV.defaultMMKV().encode("DISPLAY",Build.DISPLAY);

        Locale locale = BaseApplication.getInstance().getResources().getConfiguration().locale;
        MMKV.defaultMMKV().encode("CURRENT_LAGUAGE",locale.getLanguage());

        TimeZone tz = TimeZone.getDefault();
        MMKV.defaultMMKV().encode("CURRENT_TIMEZONE",tz.getDisplayName(false, TimeZone.SHORT) + " " + tz.getID());
    }

    /**
     * 获取imei<br/>
     * 需要权限：READ_PHONE_STATE
     *
     * @return
     */
    public static String getDeviceImei() {
        TelephonyManager telephonyManager = (TelephonyManager) BaseApplication.getInstance()
                .getSystemService(Context.TELEPHONY_SERVICE);
        String imei;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            imei = telephonyManager.getDeviceId();
        } else {
            try {
                Method method = telephonyManager.getClass().getMethod("getImei");
                imei = (String) method.invoke(telephonyManager);
            } catch (Exception e) {
                return "";
            }
        }
        return imei;
    }

    /**
     * 内核版本
     */
    public static String getLinuxCore_Ver() {
        Process process = null;
        String kernelVersion = "";
        try {
            process = Runtime.getRuntime().exec("cat /proc/version");
        } catch (IOException e) {
            e.printStackTrace();
        }
        // get the output line
        InputStream outs = process.getInputStream();
        InputStreamReader isrout = new InputStreamReader(outs);
        BufferedReader brout = new BufferedReader(isrout, 8 * 1024);


        String result = "";
        String line;
        // get the whole standard output string
        try {
            while ((line = brout.readLine()) != null) {
                result += line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            if (result != "") {
                String Keyword = "version ";
                int index = result.indexOf(Keyword);
                line = result.substring(index + Keyword.length());
                index = line.indexOf(" ");
                kernelVersion = line.substring(0, index);
            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return kernelVersion;
    }
    /**
     * 手机SDK,即API level
     */
    public static String getBuild() {
        String result =
                "SDK_INT=" + Build.VERSION.SDK_INT +//27
                        ", RELEASE=" + Build.VERSION.RELEASE +//8.1.0
                        ", CODENAME=" + Build.VERSION.CODENAME +//REL
                        ", BASE_OS=" + Build.VERSION.BASE_OS +//基带版本
                        ", INCREMENTAL=" + Build.VERSION.INCREMENTAL +//内部版本
                        ", BOARD=" + Build.BOARD +//msm8909
                        ", BRAND=" + Build.BRAND +//RADGER
                        ", DEVICE=" + Build.DEVICE +//IMP-F01K
                        ", MODEL=" + Build.MODEL +//IMP-F01K
                        ", HARDWARE=" + Build.HARDWARE +//qcom
                        ", TIME=" + Build.TIME +//1546506898000
                        ", BOOTLOADER=" + Build.BOOTLOADER +//unknown
                        ", HOST=" + Build.HOST +//HPT
                        ", ID=" + Build.ID +//OPM1.171019.026
                        ", MANUFACTURER=" + Build.MANUFACTURER +//InspireMobile
                        ", PRODUCT=" + Build.PRODUCT +//IMP-F01K
                        ", TAGS=" + Build.TAGS +//test-keys
                        ", TYPE=" + Build.TYPE +//userdebug
                        ", UNKNOWN=" + Build.UNKNOWN +//unknown
                        ", USER=" + Build.USER +//HPT
                        ", getRadioVersion=" + Build.getRadioVersion() +//
                        ", FINGERPRINT=" + Build.FINGERPRINT +//RADGER/IMP-F01K/IMP-F01K:8.1.0/OPM1.171019.026/MIKI12071520:user/release-keys
                        ", DISPLAY=" + Build.DISPLAY//msm8909-userdebug 8.1.0 OPM1.171019.026 eng.HPT.20190103.171458 test-keys
                ;
        return result;
    }


    /**
     * 手机语言环境
     */
    public static String getLanguage(Context context) {
        Locale locale = context.getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        return language;
    }

    /**
     * 时区
     */
    public static String getTimezone() {
        TimeZone tz = TimeZone.getDefault();
        String s = tz.getDisplayName(false, TimeZone.SHORT) + " " + tz.getID();
        return s;
    }

    /**
     * 开机时间
     */
    public static String getBootTimes() {
        long ut = SystemClock.elapsedRealtime() / 1000;
        if (ut == 0) {
            ut = 1;
        }
        int m = (int) ((ut / 60) % 60);
        int h = (int) ((ut / 3600));
        return h + "小时" + m + "分钟";
    }

}
