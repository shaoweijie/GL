package com.hancher.common.utils.android;

import android.content.Context;
import android.os.Build;

import com.tencent.mmkv.MMKV;

import java.util.List;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2022/2/9 0009 21:41 <br/>
 * 描述 : 字典工具
 */
public class MmkvUtil {

    public static void init(Context context) {
        MMKV kv = MMKV.defaultMMKV();
        kv.encode("mmkv.path", MMKV.getRootDir());
        kv.encode("version.debug", ApkInfoUtil.isApkInDebug(context) + "");//true,false
        kv.encode("version.name", ApkInfoUtil.getVersionName(context));//0.16.9
        kv.encode("version.code", ApkInfoUtil.getVersionCode(context) + "");//82
        kv.encode("build.radio.version", Build.getRadioVersion());//82
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                kv.encode("build.serial", Build.getSerial());//82
            }
        } catch (Exception exception) {
            LogUtil.d("getSerial err:", exception.getMessage());
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            List<Build.Partition> fingerPartitions = Build.getFingerprintedPartitions();
            StringBuffer stringBuffer = new StringBuffer();
            for (Build.Partition item : fingerPartitions) {
                stringBuffer.append("【").append(item.getName()).append("  ").append(item.getFingerprint()).append("】 ");
            }
            kv.encode("build.fingerprint", stringBuffer.toString());//82
        }

        LogUtil.d(getAllMmkv());
    }

    public static String getAllMmkv() {
        MMKV kv = MMKV.defaultMMKV();
        StringBuffer stringBuffer = new StringBuffer("mmkv:\n");
        for (String item : kv.allKeys()) {
            stringBuffer.append(item).append(":\t").append(kv.decodeString(item, ""))
                    .append("\n");
        }
        return stringBuffer.toString();
    }
}
