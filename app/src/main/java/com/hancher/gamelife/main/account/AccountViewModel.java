package com.hancher.gamelife.main.account;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hancher.common.utils.android.AsyncUtils;
import com.hancher.common.utils.android.LogUtil;
import com.hancher.common.utils.android.ToastUtil;
import com.hancher.gamelife.MainApplication;
import com.hancher.gamelife.greendao.Account;
import com.hancher.gamelife.greendao.AccountDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/8/29 0029 21:49 <br/>
 * 描述 :
 */
public class AccountViewModel extends ViewModel {
    private MutableLiveData<List<Account>> allAccounts = new MutableLiveData<>();
    private MutableLiveData<Account> account = new MutableLiveData<>();
    private MutableLiveData<Long> updateState = new MutableLiveData<>();

    public MutableLiveData<Long> getUpdateState() {
        return updateState;
    }

    public MutableLiveData<List<Account>> getAllAccounts() {
        return allAccounts;
    }

    public MutableLiveData<Account> getAccount() {
        return account;
    }

    /**
     * 更新列表
     * @param key 过滤关键词
     */
    public void updateAccountList(String key) {
        LogUtil.d("开始更新列表，关键词：", key);
        AsyncUtils.run(emitter -> {
            QueryBuilder<Account> queryBuilder = MainApplication.getInstance()
                    .getDaoSession().getAccountDao().queryBuilder();
            if (!TextUtils.isEmpty(key)) {
                String key2 = "%" + key + "%";
                queryBuilder.where(
                    queryBuilder.and(
                        AccountDao.Properties.Deleteflag.notEq("1"),
                        queryBuilder.or(AccountDao.Properties.Account.like(key2),
                            AccountDao.Properties.User.like(key2),
                            AccountDao.Properties.Note.like(key2))
                    )
                );
            } else {
                queryBuilder.where(AccountDao.Properties.Deleteflag.notEq("1"));
            }
            List<Account> list = queryBuilder.build().list();
            emitter.onNext(list);
            emitter.onComplete();
        }, (Consumer<List<Account>>) o -> allAccounts.setValue(o));
    }

    /**
     * 删除一条记录
     * @param uuid uuid
     */
    public void deleteAccount(String uuid) {
        if (TextUtils.isEmpty(uuid)) {
            ToastUtil.show("uuid 为空，停止删除");
            return;
        }
        AsyncUtils.run(emitter -> {
            LogUtil.d("数据库删除开始" + uuid);
            Account account = MainApplication.getInstance().getDaoSession().getAccountDao()
                    .queryBuilder().where(AccountDao.Properties.Uuid.eq(uuid)).build().list().get(0);
            account.setDeleteflag("1");
            account.setUpdatetime(String.valueOf(System.currentTimeMillis()));
            MainApplication.getInstance().getDaoSession().getAccountDao()
                    .update(account);
            LogUtil.d("数据库删除结束");
            emitter.onNext(uuid);
            emitter.onComplete();
        }, (Consumer<String>) s -> updateAccountList(""));
    }

    /**
     * 查询一条记录
     * @param uuid uuid
     */
    public void queryCurrentItem(String uuid) {
        if (TextUtils.isEmpty(uuid)) {
            LogUtil.d(uuid);
            ToastUtil.show("uuid为空");
            return;
        }
        AsyncUtils.run(emitter -> {
            List<Account> list = MainApplication.getInstance().getDaoSession().getAccountDao()
                    .queryBuilder().where(AccountDao.Properties.Uuid.eq(uuid))
                    .build().list();
            if (list.size() > 0) {
                emitter.onNext(list.get(0));
            }
            emitter.onComplete();
        }, (Consumer<Account>) item -> account.setValue(item));
    }

    /**
     * 保存
     * @param account 一条记录
     */
    public void saveAccount(Account account) {
        LogUtil.v("保存account=", account);
        AsyncUtils.run(emitter -> {
            long result = MainApplication.getInstance().getDaoSession().getAccountDao()
                    .insertOrReplace(account);
            emitter.onNext(result);
            emitter.onComplete();
        }, (Consumer<Long>) o -> updateState.setValue(o));
    }
}
