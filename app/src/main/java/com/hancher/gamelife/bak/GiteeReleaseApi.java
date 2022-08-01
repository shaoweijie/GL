package com.hancher.gamelife.bak;

import com.hancher.common.net.NetUtil;
import com.hancher.common.utils.android.AsyncUtils;
import com.hancher.gamelife.api.ConfigContract;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * 作者: Hancher
 * 日期: 2020/9/6 10:21
 * 描述: 账户密码记录表
 */
public class GiteeReleaseApi {

    public static Observable<GiteeReleaseBean> getLastestApkInfo() {
        return new NetUtil<>(ReleaseApiService.class).setBaseUrl(ConfigContract.HOST_FREE3V).build()
                .getLastestApkInfo()
                .compose(AsyncUtils.getThreadTransformer());
    }

    interface ReleaseApiService {
        @GET("release/gamelife.json")
        Observable<GiteeReleaseBean> getLastestApkInfo();
    }

}
