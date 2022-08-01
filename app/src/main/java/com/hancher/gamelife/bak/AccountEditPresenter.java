//package com.hancher.gamelife.main.account;
//
//import android.text.TextUtils;
//
//import com.hancher.common.base.v01.BasePresenter;
//import com.hancher.common.utils.android.AsyncUtils;
//import com.hancher.common.utils.android.LogUtil;
//import com.hancher.common.utils.android.ToastUtil;
//import com.hancher.gamelife.MainApplication;
//import com.hancher.gamelife.bak.contract.AccountEditContract;
//import com.hancher.gamelife.greendao.Account;
//import com.hancher.gamelife.greendao.AccountDao;
//
//import java.util.List;
//
//import io.reactivex.functions.Consumer;
//
///**
// * 作者：Hancher <br/>
// * 时间：2020/9/26 0026 下午 2:00 <br/>
// * 邮箱：ytu_shaoweijie@163.com <br/>
// * 说明：
// */
//public class AccountEditPresenter extends BasePresenter<AccountEditActivity> implements AccountEditContract.Presenter {
//
//    @Override
//    public void queryCurrentItem(String uuid) {
//        if (TextUtils.isEmpty(uuid)) {
//            LogUtil.d(uuid);
//            ToastUtil.show("uuid为空");
//            return;
//        }
//        AsyncUtils.run(emitter -> {
//            List<Account> list = MainApplication.getInstance()
//                    .getDaoSession().getAccountDao().queryBuilder()
//                    .where(AccountDao.Properties.Uuid.eq(uuid))
//                    .build().list();
//            if (list.size() > 0) {
//                emitter.onNext(list.get(0));
//            }
//            emitter.onComplete();
//        }, (Consumer<Account>) account -> mView.updateAccount(account));
//    }
//
//    @Override
//    public void saveAccount(Account account, boolean isUpdate) {
//        LogUtil.v("保存account=", account);
//        AsyncUtils.run(emitter -> {
//            if (isUpdate) {
//                MainApplication.getInstance().getDaoSession().getAccountDao()
//                        .update(account);
//                emitter.onNext(1L);
//            } else {
//                Long result = MainApplication.getInstance().getDaoSession().getAccountDao()
//                        .insert(account);
//                emitter.onNext(result);
//            }
//            emitter.onComplete();
//        }, (Consumer<Long>) o -> mView.onUpdateAccountSuccess(o));
//    }
//}
