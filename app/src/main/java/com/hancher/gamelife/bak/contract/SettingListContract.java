package com.hancher.gamelife.bak.contract;

import com.hancher.gamelife.main.setting.SettingListAdapter;

import java.util.List;

/**
 * 作者：Hancher
 * 时间：2021/2/2 14:06
 * 邮箱：ytu_shaoweijie@163.com
 * <p>
 * 说明：
 */
public interface SettingListContract {
    interface Model {
    }

    interface View {
    }

    interface Presenter {
        List<SettingListAdapter.SettingListItem> getAllSettings();
    }
}
