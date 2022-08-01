package com.hancher.gamelife.bak.contract;

/**
 * 作者：Hancher
 * 时间：2020/9/23 0023 上午 11:00
 * 邮箱：ytu_shaoweijie@163.com
 * <p>
 * 说明：
 */
public interface AboutContract {
    interface Model {
    }

    interface View {
        void updateInfo(String message);
    }

    interface Presenter {
        void checkApk();
    }
}
