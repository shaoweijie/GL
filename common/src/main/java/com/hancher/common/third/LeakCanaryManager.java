package com.hancher.common.third;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * 作者：Hancher
 * 时间：2020/1/28.
 * 邮箱：ytu_shaoweijie@163.com
 * 版本：v1.0
 * <p>
 * 说明：
 */
public class LeakCanaryManager {
    /**
     * 直接调用，启动
     * LeakCanaryManager.init(this);
     *
     * @param application
     */
    public static void init(Application application) {
        if (!LeakCanary.isInAnalyzerProcess(application)) {
            LeakCanary.install(application);
        }
    }
}
