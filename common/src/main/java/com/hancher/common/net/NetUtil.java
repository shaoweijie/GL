package com.hancher.common.net;

import com.google.gson.Gson;
import com.hancher.common.net.cookie.CookieJarImpl;
import com.hancher.common.net.https.HttpsAdapter;
import com.hancher.common.net.interceptor.LogInterceptor;
import com.hancher.common.net.interceptor.ProgressInterceptor;
import com.hancher.common.net.interceptor.ProgressListener;
import com.hancher.common.utils.android.AsyncUtils;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/2/4 15:17 <br/>
 * 描述 : 网络工具类
 */
public class NetUtil<S> {
    private static final long DEFAULT_TIMEOUT = 20L;
    Class<S> clazz;
    long timeOut = DEFAULT_TIMEOUT;
    String baseUrl = "";
    ProgressListener listener = null;
    boolean printLog = true;
    boolean useCookie = true;
    boolean useJson = true;

    InputStream certification = null;
    InputStream bksFile = null;
    String password = null;

    public NetUtil(Class<S> clazz) {
        this.clazz = clazz;
    }
    public static <T> Observable<T> getVirtualData(String jsonStr, Type beanType){
        return Observable.create((ObservableEmitter<T> emitter) -> {
            Thread.sleep(4000L);
            Gson gson = new Gson();
            T data = gson.fromJson(jsonStr, beanType);
            emitter.onNext(data);
            emitter.onComplete();
        }).compose(AsyncUtils.getThreadTransformer());
    }
    //https://www.jianshu.com/p/a229722b55fe 上传
//    https://blog.csdn.net/qq_30574785/article/details/79024656 下载
//    https://blog.csdn.net/shiming_shi/article/details/91377521 监听

    public S build() {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.connectTimeout(timeOut, TimeUnit.SECONDS);
        if (useCookie) {
            clientBuilder.cookieJar(CookieJarImpl.getInstance());
        }
        if (printLog) {
            clientBuilder.addInterceptor(new LogInterceptor());
        }
        if (listener != null) {
            clientBuilder.addInterceptor(new ProgressInterceptor(listener));
        }
        if (certification != null) {
            HttpsAdapter.SSLParams params = HttpsAdapter.getSslSocketFactory(new InputStream[]{certification}, bksFile, password);
            clientBuilder.sslSocketFactory(params.sSLSocketFactory, params.trustManager);
        }
        OkHttpClient client = clientBuilder.build();

        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client);
        if (useJson) {
            retrofitBuilder.addConverterFactory(GsonConverterFactory.create());
        }
//        else {
////            retrofitBuilder.addConverterFactory(SimpleXmlConverterFactory.create());
////            https://www.w3cschool.cn/jaxb2/jaxb2-8pa22zoi.html
//            retrofitBuilder.addConverterFactory(JaxbConverterFactory.create());
//        }
        Retrofit retrofit = retrofitBuilder.build();
        return retrofit.create(this.clazz);
    }

    public boolean isUseJson() {
        return useJson;
    }

    public NetUtil<S> setUseJson(boolean useJson) {
        this.useJson = useJson;
        return this;
    }

    public boolean isUseCookie() {
        return useCookie;
    }

    public NetUtil<S> setUseCookie(boolean useCookie) {
        this.useCookie = useCookie;
        return this;
    }

    public boolean isPrintLog() {
        return printLog;
    }

    public NetUtil<S> setPrintLog(boolean printLog) {
        this.printLog = printLog;
        return this;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public NetUtil<S> setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public ProgressListener getListener() {
        return listener;
    }

    public NetUtil<S> setListener(ProgressListener listener) {
        this.listener = listener;
        return this;
    }

    public InputStream getCertification() {
        return certification;
    }

    public NetUtil<S> setCertification(InputStream certification) {
        this.certification = certification;
        return this;
    }

    public InputStream getBksFile() {
        return bksFile;
    }

    public NetUtil<S> setBksFile(InputStream bksFile) {
        this.bksFile = bksFile;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public NetUtil<S> setPassword(String password) {
        this.password = password;
        return this;
    }

    public Class<S> getClazz() {
        return clazz;
    }

    public NetUtil<S> setClazz(Class<S> clazz) {
        this.clazz = clazz;
        return this;
    }

    public long getTimeOut() {
        return timeOut;
    }

    public NetUtil<S> setTimeOut(long timeOut) {
        this.timeOut = timeOut;
        return this;
    }
}
