package com.hancher.gamelife.main.tv.setting;

import com.hancher.common.net.NetUtil;
import com.hancher.common.net.bean.ResultBean;
import com.hancher.common.utils.android.ApkInfoUtil;
import com.hancher.common.utils.android.AsyncUtils;
import com.hancher.gamelife.MainApplication;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 作者: Hancher
 * 日期: 2020/9/6 10:21
 * 描述: 蒲公英接口
 */
public class PgyerApi {

    public static Observable<ResultBean<LastestApkBean>> getLastest() {
        return new NetUtil<>(HancherService.class).setBaseUrl(PgyerUpdateViewModel.HOST_PGYER).build()
                .getLastest(PgyerUpdateViewModel.HOST_PGYER_APIKEY, PgyerUpdateViewModel.HOST_PGYER_APPKEY, ApkInfoUtil.getVersionCode(MainApplication.getInstance()))
                .compose(AsyncUtils.getThreadTransformer());
    }

    interface HancherService {

        @POST("apiv2/app/check")
        @FormUrlEncoded
        Observable<ResultBean<LastestApkBean>> getLastest(@Field("_api_key") String apiKey,
                                                          @Field("appKey") String appKey,
                                                          @Field("buildVersion") long buildVersion);

    }

}
