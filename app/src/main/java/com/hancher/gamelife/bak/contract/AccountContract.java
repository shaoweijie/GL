package com.hancher.gamelife.bak.contract;

import com.hancher.gamelife.greendao.Account;

import java.util.List;

/**
 * 作者：Hancher
 * 时间：2020/9/23 0023 上午 11:00
 * 邮箱：ytu_shaoweijie@163.com
 * <p>
 * 说明：
 */
public interface AccountContract {
    interface Model {
    }

    interface View {
        void updateAccountListUi(List<Account> data);

        void onAccountDeleted();
    }

    interface Presenter {

        /**
         * 同步本地数据库与服务器数据库
         */
        void startSyncAccount();

        /**
         * 用本地数据库内容更新当前列表
         */
        void updateAccountList();

        /**
         * 用本地数据库内容更新当前列表
         */
        void updateAccountList(String key);

        void deleteAccount(String Account);
    }
}
