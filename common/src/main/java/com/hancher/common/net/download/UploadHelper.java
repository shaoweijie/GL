package com.hancher.common.net.download;

import com.hancher.common.net.NetUtil;
import com.hancher.common.net.interceptor.ProgressListener;
import com.hancher.common.utils.android.LogUtil;

import java.io.File;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/2/6 9:10 <br/>
 * 描述 : 上传工具类，拦截器监听(回调在主线程)
 */
public class UploadHelper {
    interface UploadService {

        @Multipart
        @POST
        Call<ResponseBody> upload(@Url String uploadUrl,
                                  @Part("description") RequestBody description,
                                  @Part MultipartBody.Part file);

        @Multipart
        @POST
        Call<ResponseBody> upload(@Url String uploadUrl, @Part MultipartBody.Part file);
    }

    public static void uploadFile(String baseUrl, String url, String storeKey, File file, UploadListener listener) {
        RequestBody requestFile = RequestBody.create(MultipartBody.FORM, file);
        MultipartBody.Part body = MultipartBody.Part.createFormData(storeKey, file.getName(), requestFile);
        ProgressListener progressListener = new ProgressListener() {
            @Override
            public void onRequestProgress(long soFarBytes, long totalBytes) {
                listener.onProgress((int) (100 * soFarBytes / totalBytes));
            }

            @Override
            public void onResponseProgress(long soFarBytes, long totalBytes) {

            }

            @Override
            public void onError(Throwable throwable) {
                listener.onFail(throwable.toString());
            }
        };
        UploadService service = new NetUtil<>(UploadService.class).setBaseUrl(baseUrl)
                .setListener(progressListener).build();
        Call<ResponseBody> call = service.upload(url, body);
        listener.onStart();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String str = response.body().string();
                    listener.onFinish(str);
                } catch (Exception e) {
                    LogUtil.e("err:", e);
                    listener.onFail(e.toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                LogUtil.e(t.toString());
                listener.onFail(t.toString());
            }
        });

    }
}
