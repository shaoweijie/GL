package com.hancher.gamelife.bak;

import com.hancher.common.net.NetUtil;
import com.hancher.common.net.bean.ResultBean;
import com.hancher.common.net.download.UploadHelper;
import com.hancher.common.net.download.UploadListener;
import com.hancher.common.utils.android.AsyncUtils;
import com.hancher.gamelife.api.ConfigContract;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * 作者: Hancher
 * 日期: 2020/9/6 10:21
 * 描述: 账户密码记录表
 */
public class HancherImageApi {

    public static Observable<ResultBean<List<HancherImageBean>>> getAllImages() {
        return new NetUtil<>(HancherService.class).setBaseUrl(ConfigContract.HOST_HANCHER).build()
                .getAllImages()
                .compose(AsyncUtils.getThreadTransformer());
    }

//    public static String getImageUrl(String suffix) {
//        if (suffix.startsWith("link/")) {
//            return CommonConfig.HOST_HANCHER + suffix;
//        }
//        return suffix;
//    }

    interface HancherService {
        @GET("gl/image/getAll")
        Observable<ResultBean<List<HancherImageBean>>> getAllImages();
    }

    public static void upload(String path, UploadListener listener) {
        UploadHelper.uploadFile(ConfigContract.HOST_HANCHER,
                ConfigContract.HOST_HANCHER + "gl/image/upload",
                "uploadImage", new File(path), listener);
    }

}
