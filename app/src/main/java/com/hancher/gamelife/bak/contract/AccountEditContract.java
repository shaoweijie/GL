package com.hancher.gamelife.bak.contract;

import com.hancher.gamelife.greendao.Account;

/**
 * 作者：Hancher
 * 时间：2020/9/26 0026 下午 2:00
 * 邮箱：ytu_shaoweijie@163.com
 * <p>
 * 说明：
 */
public interface AccountEditContract {
    interface Model {
    }

    interface View {
        void updateAccount(Account o);

        void onUpdateAccountSuccess(Long o);
    }

    interface Presenter {
        void queryCurrentItem(String uuid);

        void saveAccount(Account account, boolean isUpdate);
    }
}
