//package com.hancher.gamelife.main.account;
//
//import android.app.ProgressDialog;
//import android.text.TextUtils;
//
//import com.hancher.common.base.v01.BasePresenter;
//import com.hancher.common.utils.android.AsyncUtils;
//import com.hancher.common.utils.android.LogUtil;
//import com.hancher.common.utils.android.ToastUtil;
//import com.hancher.common.utils.java.TextUtil;
//import com.hancher.gamelife.MainApplication;
//import com.hancher.gamelife.bak.EasyApi;
//import com.hancher.gamelife.bak.contract.AccountContract;
//import com.hancher.gamelife.greendao.Account;
//import com.hancher.gamelife.greendao.AccountDao;
//
//import org.greenrobot.greendao.query.QueryBuilder;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import io.reactivex.ObservableEmitter;
//import io.reactivex.ObservableOnSubscribe;
//import io.reactivex.Observer;
//import io.reactivex.disposables.Disposable;
//import io.reactivex.functions.Consumer;
//
///**
// * 作者：Hancher
// * 时间：2020/9/23 0023 上午 11:00
// * 邮箱：ytu_shaoweijie@163.com
// * <p>
// * 说明：
// */
//public class AccountPresenter extends BasePresenter<AccountActivity> implements AccountContract.Presenter {
//
//    @Override
//    public void updateAccountList() {
//        updateAccountList("");
//    }
//
//    @Override
//    public void updateAccountList(String key) {
//        LogUtil.d("开始更新列表，关键词：",key);
//        AsyncUtils.runWithWaitDialog(emitter -> {
//            QueryBuilder<Account> queryBuilder = MainApplication.getInstance()
//                    .getDaoSession().getAccountDao().queryBuilder();
//            if (!TextUtils.isEmpty(key)) {
//                String key2 = "%" + key + "%";
//                queryBuilder.where(
//                        queryBuilder.and(
//                                AccountDao.Properties.Deleteflag.notEq("1"),
//                                queryBuilder.or(AccountDao.Properties.Account.like(key2),
//                                        AccountDao.Properties.User.like(key2),
//                                        AccountDao.Properties.Note.like(key2))
//                        )
//                );
//            } else {
//                queryBuilder.where(AccountDao.Properties.Deleteflag.notEq("1"));
//            }
//            List<Account> list = queryBuilder.build().list();
//            emitter.onNext(list);
//            emitter.onComplete();
//        }, (Consumer<List<Account>>) o -> mView.updateAccountListUi(o), new ProgressDialog(mView));
//    }
//
//    @Override
//    public void deleteAccount(String uuid) {
//        if (TextUtils.isEmpty(uuid)){
//            ToastUtil.show("uuid 为空，停止删除");
//            return;
//        }
//        AsyncUtils.runWithWaitDialog(emitter -> {
//                LogUtil.d("数据库删除开始" + uuid);
//                Account account = MainApplication.getInstance().getDaoSession().getAccountDao()
//                        .queryBuilder().where(AccountDao.Properties.Uuid.eq(uuid)).build().list().get(0);
//                account.setDeleteflag("1");
//                account.setUpdatetime(String.valueOf(System.currentTimeMillis()));
//                MainApplication.getInstance().getDaoSession().getAccountDao()
//                        .update(account);
//                LogUtil.d("数据库删除结束");
//                emitter.onNext(uuid);
//                emitter.onComplete();
//            }, (Consumer<String>) s -> mView.onAccountDeleted(), new ProgressDialog(mView)
//        );
//    }
//
//    @Override
//    public void startSyncAccount() {
//        LogUtil.d("开始拉取服务器数据");
//        Disposable d = EasyApi.queryAccount().subscribe(result -> {
//            if (result.isOK()) {
//                LogUtil.d("服务器数据拉取成功，从服务器获取条数："+result.getData().size());
//                LogUtil.v("服务器数据拉取成功，第一条数据：",result.getData().get(0));
//                updateLocalAndServerDb(result.getData());
//            } else {
//                ToastUtil.showErr("服务器数据拉取同步失败，服务器报错："+result.getMessage());
//            }
//        }, error -> {
//            ToastUtil.showErr("服务器数据拉取同步失败，网络报错："+error.getMessage());
//        });
//    }
//
//    /**
//     * 比对服务器与本地数据库，并相互更新
//     * @param serverData
//     */
//    private void updateLocalAndServerDb(List<Account> serverData) {
//        LogUtil.d("开始比对本地与服务器数据");
//        AsyncUtils.run(new ObservableOnSubscribe<ArrayList<Long>>() {
//                    @Override
//                    public void subscribe(ObservableEmitter<ArrayList<Long>> emitter) throws Exception {
//                        LogUtil.d("开始获取本地服务器数据");
//                        List<Account> dbData = MainApplication.getInstance().getDaoSession().getAccountDao().queryBuilder().build().list();
//                        LogUtil.d("本地数据成功获取条数："+dbData.size());
//                        int[] localResult = updateLocalDb(serverData, dbData);
//                        int[] serverResult = updateServerDb(serverData, dbData);
//                        ArrayList<Long> result = new ArrayList<>();
//                        result.add((long) localResult[0]);
//                        result.add((long) localResult[1]);
//                        result.add((long) serverResult[0]);
//                        result.add((long) serverResult[1]);
//                        emitter.onNext(result);
//                        emitter.onComplete();
//                    }
//                },
//                new Observer<ArrayList<Long>>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(ArrayList<Long> result) {
//                        ToastUtil.show("本地插入："+result.get(0)+"、更新："+result.get(1)+"，远程插入："+result.get(2)+"、更新："+result.get(3));
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        LogUtil.e(e);
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        updateAccountList();
//                    }
//                });
//    }
//
//    /**
//     * 比对服务器与本地数据库，更新服务器数据库
//     * @param serverData
//     * @param dbData
//     * @return 返回插入、更新个数
//     */
//    private int[] updateServerDb(List<Account> serverData, List<Account> dbData) {
//        LogUtil.d("开始比对并更新服务器数据");
//        int[] result2 = {0,0};
//        int compareResult = -1;
//        for (Account dbItem : dbData){
//            try {
//                compareResult = compareItem(serverData, dbItem);
//                if (compareResult == 1){
//                    Disposable d = EasyApi.addAccount(dbItem).subscribe(result -> {
//                        if (result.isOK()) {
//                        } else {
//                            ToastUtil.showErr(result.getMessage());
//                        }
//                    }, error -> {
//                        ToastUtil.showErr(error.getMessage());
//                    });
//                    result2[0] += 1;
//                } else if (compareResult == 2){
//                    Disposable d = EasyApi.editAccount(dbItem).subscribe(result -> {
//                        if (result.isOK()) {
//                        } else {
//                            ToastUtil.showErr(result.getMessage());
//                        }
//                    }, error -> {
//                        ToastUtil.showErr(error.getMessage());
//                    });
//                    result2[1] += 1;
//                }
//            } catch (Exception e){
//                LogUtil.e("当前item上传服务器数据库失败，compareResult=",compareResult,",",dbItem);
//                LogUtil.e(e);
//            }
//        }
//        return result2;
//    }
//
//    /**
//     * 比对服务器与本地数据库，更新本地数据库
//     * @param serverData
//     * @param dbData
//     * @return 返回插入、更新个数
//     */
//    private int[] updateLocalDb(List<Account> serverData, List<Account> dbData) {
//        LogUtil.d("开始比对并更新本地数据");
//        int[] result = {0,0};
//        int compareResult = -1;
//        for (Account serverItem : serverData){
//            try {
//                compareResult = compareItem(dbData, serverItem);
//                if (compareResult == 1) {
//                    MainApplication.getInstance().getDaoSession().getAccountDao().insert(serverItem);
//                    result[0] += 1;
//                } else if (compareResult == 2) {
//                    MainApplication.getInstance().getDaoSession().getAccountDao().update(serverItem);
//                    result[1] += 1;
//                }
//            } catch (Exception e){
//                LogUtil.e("当前item导入数据库失败，compareResult=",compareResult,",",serverItem);
//                LogUtil.e(e);
//            }
//        }
//        return result;
//    }
//
//
//    /**
//     * 检测当前数据是否需要插入或更新
//     * @param existData
//     * @param newItem
//     * @return 0 无需操作，1 插入， 2 更新
//     */
//    private int compareItem(List<Account> existData, Account newItem) {
//        for (Account existItem : existData){
//            if (newItem.getUuid().equals(existItem.getUuid())){
//                //找到相同uuid，检查是否更新
//                if (TextUtil.isEmptyOrNullStr(newItem.getUpdatetime())){
//                    //服务器更新时间为空不需要本地更新
//                    return 0;
//                } else if (TextUtil.isEmptyOrNullStr(existItem.getUpdatetime())){
//                    //服务器更新时间不为空，本地时间为空，需要更新
//                    return 2;
//                } else {
//                    //服务器更新时间，本地更新时间，均不为空，则比较是否需要更新
//                    if (Long.parseLong(newItem.getUpdatetime()) > Long.parseLong(existItem.getUpdatetime())){
//                        return 2;
//                    } else {
//                        return 0;
//                    }
//                }
//            }
//        }
//        // 未找到，执行插入
//        return 1;
//    }
//}
