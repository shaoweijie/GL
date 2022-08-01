package com.hancher.common.utils.android;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import androidx.core.app.ActivityCompat;

import com.hancher.common.base.mvvm02.BaseApplication;
import com.tencent.mmkv.MMKV;

/**
 * 作者：Hancher
 * 时间：2019/2/13.
 * 邮箱：ytu_shaoweijie@163.com
 * 版本：v1.0
 * <point>
 * 说明：判断SIM卡通信类型
 */
public class SimUtil {
    public static void collect(){
        MMKV.defaultMMKV().encode("SIM_OPERATOR_NAME",getSIMOperatorName(BaseApplication.getInstance()));
        MMKV.defaultMMKV().encode("SIM_OPERATOR_NAME",getPhoneNumber(BaseApplication.getInstance()));
    }
    /**
     * 网络类型
     *
     */
    //没有连接
    public static final String NETWORN_NONE = "无网络连接";
    //wifi连接
    public static final String NETWORN_WIFI = "wifi";
    //手机网络数据连接
    public static final String NETWORN_2G = "2G";
    public static final String NETWORN_3G = "3G";
    public static final String NETWORN_4G = "4G";
    public static final String NETWORK_TYPE_UNKNOWN = "未知";  //0
    public static final String NETWORK_TYPE_GPRS = "GPRS";   //1
    public static final String NETWORK_TYPE_EDGE = "EDGE";   //2
    public static final String NETWORK_TYPE_UMTS = "UMTS";   //3
    public static final String NETWORK_TYPE_CDMA = "CDMA(IS95A or IS95B)";//4
    public static final String NETWORK_TYPE_EVDO_0 = "EVDO(revision 0)";// 5
    public static final String NETWORK_TYPE_EVDO_A = "EVDO(revision A)";//  6
    public static final String NETWORK_TYPE_1xRTT = "1xRTT";//  7
    public static final String NETWORK_TYPE_HSDPA = "HSDPA";   // 8
    public static final String NETWORK_TYPE_HSUPA = "HSUPA";   //9
    public static final String NETWORK_TYPE_HSPA = "HSPA";//10
    public static final String NETWORK_TYPE_IDEN = "iDen";//11
    public static final String NETWORK_TYPE_EVDO_B = "EVDO(revision B)";//12
    public static final String NETWORK_TYPE_LTE = "LTE";//13
    public static final String NETWORK_TYPE_EHRPD = "eHRPD";//14
    public static final String NETWORK_TYPE_HSPAP = "HSPA+";//15
    public static final String NETWORK_TYPE_GSM = "GSM";//16

    @SuppressLint("MissingPermission")
    public static String getNetworkType(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //是否有连接
        if (null == connManager) {
            return NETWORN_NONE;
        }
        //是否有网络
         NetworkInfo activeNetInfo = connManager.getActiveNetworkInfo();
        if (activeNetInfo == null || !activeNetInfo.isAvailable()) {
            return NETWORN_NONE;
        }
        // 是否是Wifi
        NetworkInfo wifiInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (null != wifiInfo) {
            NetworkInfo.State state = wifiInfo.getState();
            if (null != state)
                if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {
                    return NETWORN_WIFI;
                }
        }
        // 是否是移动网络
        NetworkInfo networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (null != networkInfo) {
            NetworkInfo.State state = networkInfo.getState();
            String strSubTypeName = networkInfo.getSubtypeName();
            if (null != state)
                if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {
                    switch (activeNetInfo.getSubtype()) {
                        case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                            return NETWORK_TYPE_UNKNOWN;
                        case TelephonyManager.NETWORK_TYPE_GPRS: // 联通2g
                            return NETWORK_TYPE_GPRS;
                        case TelephonyManager.NETWORK_TYPE_EDGE: // 移动2g
                            return NETWORK_TYPE_EDGE;
                        case TelephonyManager.NETWORK_TYPE_UMTS:
                            return NETWORK_TYPE_UMTS;
                        case TelephonyManager.NETWORK_TYPE_CDMA: // 电信2g
                            return NETWORK_TYPE_CDMA;
                        case TelephonyManager.NETWORK_TYPE_EVDO_0:
                            return NETWORK_TYPE_EVDO_0;
                        case TelephonyManager.NETWORK_TYPE_EVDO_A: // 电信3g
                            return NETWORK_TYPE_EVDO_A;
                        case TelephonyManager.NETWORK_TYPE_1xRTT:
                            return NETWORK_TYPE_1xRTT;
                        case TelephonyManager.NETWORK_TYPE_HSDPA:
                            return NETWORK_TYPE_HSDPA;
                        case TelephonyManager.NETWORK_TYPE_HSUPA:
                            return NETWORK_TYPE_HSUPA;
                        case TelephonyManager.NETWORK_TYPE_HSPA:
                            return NETWORK_TYPE_HSPA;
                        case TelephonyManager.NETWORK_TYPE_IDEN:
                            return NETWORK_TYPE_IDEN;
                        case TelephonyManager.NETWORK_TYPE_EVDO_B:
                            return NETWORK_TYPE_EVDO_B;
                        case TelephonyManager.NETWORK_TYPE_LTE:
                            return NETWORK_TYPE_LTE;
                        case TelephonyManager.NETWORK_TYPE_EHRPD:
                            return NETWORK_TYPE_EHRPD;
                        case TelephonyManager.NETWORK_TYPE_HSPAP:
                            return NETWORK_TYPE_HSPAP;
                        default://有机型返回16,17
                            //中国移动 联通 电信 三种3G制式
                            if (strSubTypeName.equalsIgnoreCase("TD-SCDMA")
                                    || strSubTypeName.equalsIgnoreCase("WCDMA")
                                    || strSubTypeName.equalsIgnoreCase("CDMA2000")) {
                                return NETWORN_3G;
                            } else {
                                return NETWORK_TYPE_UNKNOWN;
                            }
                    }
                }
        }
        return NETWORN_NONE;
    }

    /**
     * SIM卡运营商
     */
    public static String getSIMOperatorName(Context activity) {
        String simOperator = "";
        String imsi = getImsi(activity);
        if (imsi != null) {
            if (imsi.startsWith("46000") || imsi.startsWith("46002")
                    || imsi.startsWith("46004") || imsi.startsWith("46007") ){
                simOperator = "中国移动";
            } else if (imsi.startsWith("46001") || imsi.startsWith("46006") || imsi.startsWith("46009")) {
                simOperator = "中国联通";
            } else if (imsi.startsWith("46003") || imsi.startsWith("46005") || imsi.startsWith("46011")) {
                simOperator = "中国电信";
            }
        }else{
            simOperator = "无SIM卡";
        }
        return simOperator;
    }

    /**
     * 获取IMSI，国际移动用户识别码
     * READ_PHONE_STATE
     * @param activity
     * @return
     */
    @SuppressLint({"HardwareIds", "MissingPermission"})
    public static String getImsi(Context activity) {
        TelephonyManager tpm = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE) != PackageManager
                .PERMISSION_GRANTED) {
//            String[] PERMISSIONS = {Manifest.permission.READ_PHONE_STATE};
//            ActivityCompat.requestPermissions(activity,PERMISSIONS,1);
            return null;
        }
        if (tpm != null) {
            try {
                return tpm.getSubscriberId();
            } catch (SecurityException se) {
                return null;
            }
        }
        return null;
    }
    /**
     * ICCID,集成电路卡识别码（固化在手机SIM卡中） ICCID为IC卡的唯一识别号码
     */
    @SuppressLint("MissingPermission")
    public static String getICCID(Activity activity) {
        TelephonyManager tm = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_PHONE_STATE};
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, 1);
        }
        return tm.getSimSerialNumber();
    }

    /**
     * 是否漫游
     */
    public static boolean getIsRoam(Context context){
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.isNetworkRoaming();
    }

    /**
     * 获取手机号码
     * READ_SMS / READ_PHONE_NUMBERS / READ_PHONE_STATE<br/>
     * 电信不好用<br/>
     * @param activity
     * @return
     */
    @SuppressLint({"HardwareIds", "MissingPermission"})
    public static String getPhoneNumber(Context activity) {
        TelephonyManager mTelephonyMgr = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//            String[] PERMISSIONS;
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//                PERMISSIONS = new String[]{
//                        Manifest.permission.READ_SMS,
//                        Manifest.permission.READ_PHONE_NUMBERS,
//                        Manifest.permission.READ_PHONE_STATE
//                };
//            }else {
//                PERMISSIONS = new String[]{
//                        Manifest.permission.READ_SMS,
//                        Manifest.permission.READ_PHONE_STATE
//                };
//            }
//            ActivityCompat.requestPermissions(activity,PERMISSIONS,1);
            return null;
        }
        if (mTelephonyMgr != null) {
            return mTelephonyMgr.getLine1Number();
        }
        return null;

    }
}
