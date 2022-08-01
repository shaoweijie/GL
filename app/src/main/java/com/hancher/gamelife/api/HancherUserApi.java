package com.hancher.gamelife.api;

import com.hancher.common.net.NetUtil;
import com.hancher.common.net.bean.ResultBean;
import com.hancher.common.utils.android.AsyncUtils;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 作者: Hancher
 * 日期: 2020/9/6 10:21
 * 描述: 用户操作接口
 */
public class HancherUserApi {

    public static Observable<ResultBean<HancherUserBean>> register(String user, String password, String imei) {
        return new NetUtil<>(HancherService.class).setBaseUrl(ConfigContract.HOST_HANCHER).build()
                .register(user, password, imei)
                .compose(AsyncUtils.getThreadTransformer());
    }

    public static Observable<ResultBean<HancherUserBean>> login(String user, String password) {
        return new NetUtil<>(HancherService.class).setBaseUrl(ConfigContract.HOST_HANCHER).build()
                .login(user, password)
                .compose(AsyncUtils.getThreadTransformer());
    }

    interface HancherService {

        @POST("gl/user/register")
        @FormUrlEncoded
        Observable<ResultBean<HancherUserBean>> register(@Field("username") String user,
                                                         @Field("password") String password,
                                                         @Field("imei") String imei);

        @POST("gl/user/login")
        @FormUrlEncoded
        Observable<ResultBean<HancherUserBean>> login(@Field("username") String user,
                                                      @Field("password") String password);

        @GET("api/logout")
        Observable<ResultBean<HancherUserBean>> logout(@Query("userId") String userId);

        @POST("api/updateIcon")
        @FormUrlEncoded
        Observable<ResultBean<HancherUserBean>> updateIcon(@Field("url") String url);

        @POST("api/updateUsername")
        @FormUrlEncoded
        Observable<ResultBean<HancherUserBean>> updateUsername(@Field("nickName") String nickName);

        @POST("api/changePassword")
        @FormUrlEncoded
        Observable<ResultBean<HancherUserBean>> changePassword(@Field("token") String token);
    }

}
