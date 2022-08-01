package com.hancher.gamelife.bak;

import com.hancher.common.net.NetUtil;
import com.hancher.common.net.bean.ResultBean;
import com.hancher.common.utils.android.AsyncUtils;
import com.hancher.gamelife.greendao.Account;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 作者: Hancher
 * 日期: 2020/9/6 10:21
 * 描述: 账户密码记录表
 */
public class EasyApi {
    public static Observable<ResultBean<List<Account>>> queryAccount() {
        return new NetUtil<>(EasyService.class).build()
                .query("account", "ID,account,uuid,psd,note1,user1,createtime,updatetime,deleteflag")
                .compose(AsyncUtils.getThreadTransformer());
    }

    public static Observable<ResultBean<List<Account>>> addAccount(Account account) {
        String values = account.getAccount() + "," +
                account.getUuid() + "," +
                account.getPsd() + "," +
                account.getNote() + "," +
                account.getUser() + "," +
                account.getCreatetime() + "," +
                account.getUpdatetime() + "," +
                account.getDeleteflag();
        return new NetUtil<>(EasyService.class).build()
                .add("account", "account,uuid,psd,note1,user1,createtime,updatetime,deleteflag", values)
                .compose(AsyncUtils.getThreadTransformer());
    }

    public static Observable<ResultBean<List<Account>>> editAccount(Account account) {
        String fields = "account=" + account.getAccount() + "," +
//                "uuid="+ account.getUuid()+","+
                "psd=" + account.getPsd() + "," +
                "note1=" + account.getNote() + "," +
                "user1=" + account.getUser() + "," +
                "createtime=" + account.getCreatetime() + "," +
                "updatetime=" + account.getUpdatetime() + "," +
                "deleteflag=" + account.getDeleteflag();
        return new NetUtil<>(EasyService.class).build()
                .edit("account", fields, "uuid=" + account.getUuid())
                .compose(AsyncUtils.getThreadTransformer());
    }

    public static Observable<ResultBean<List<Account>>> removeAccount(Account account) {
        String fields = "deleteflag=1";
        return new NetUtil<>(EasyService.class).build()
                .edit("account", fields, "uuid=" + account.getUuid())
                .compose(AsyncUtils.getThreadTransformer());
    }
    interface EasyService {

        @POST("api/query.asp")
        @FormUrlEncoded
        Observable<ResultBean<List<Account>>> query(@Field("table") String table, @Field("fields") String fields);

        @POST("api/add.asp")
        @FormUrlEncoded
        Observable<ResultBean<List<Account>>> add(@Field("table") String table, @Field("fields") String fields, @Field("values") String values);

        @POST("api/edit.asp")
        @FormUrlEncoded
        Observable<ResultBean<List<Account>>> edit(@Field("table") String table, @Field("fields") String fields, @Field("wheres") String wheres);

        @POST("api/delete.asp")
        @FormUrlEncoded
        Observable<ResultBean<List<Account>>> delete(@Field("table") String table, @Field("wheres") String wheres);

//        @POST("api/query.asp")
//        Observable<Result<UserBean>> quer(@Query("loginName") String loginName, @Query("password") String password);
//
//        @GET("api/logout")
//        Observable<Result<String>> logout(@Query("userId") String userId);
//
//        @POST("api/updateUsername")
//        @FormUrlEncoded
//        Observable<Result<String>> updateUsername(@Field("nickName") String nickName);
//
//        @POST("api/changePassword")
//        @FormUrlEncoded
//        Observable<Result<String>> changePassword(@Field("token") String token);
    }

}
