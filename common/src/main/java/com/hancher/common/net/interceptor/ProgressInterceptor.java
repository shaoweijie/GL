package com.hancher.common.net.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/2/4 10:48 <br/>
 * 描述 : 进度拦截器
 */
public class ProgressInterceptor implements Interceptor {

    public ProgressListener listener;

    public ProgressInterceptor(ProgressListener listener) {
        this.listener = listener;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request().newBuilder().build();
        Request request2 = request.newBuilder()
                .method(request.method(), new ProgressRequestBody(request.body(), listener))
                .build();
        Response response = chain.proceed(request2);
        return response.newBuilder()
                .body(new ProgressResponseBody(response.body(), listener))
                .build();
    }

}
