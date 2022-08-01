package com.hancher.common.third;

import android.content.Context;

import com.tencent.bugly.crashreport.CrashReport;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/6/19 0019 10:38 <br/>
 * 描述 : 模块启用后，直接使用init启动即可
 */
public class BuglyManager {

    /**
     * 启动bugly
     * @param context
     * @param appid
     */
    public static void init(Context context, String appid) {
        CrashReport.initCrashReport(context, appid, false);
    }

    public static void test(){
        CrashReport.testJavaCrash();
    }
}
