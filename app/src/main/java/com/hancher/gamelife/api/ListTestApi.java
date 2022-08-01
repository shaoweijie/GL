package com.hancher.gamelife.api;

import com.google.gson.reflect.TypeToken;
import com.hancher.common.net.NetUtil;
import com.hancher.common.net.bean.ResultBean;
import com.hancher.common.utils.android.ApkInfoUtil;
import com.hancher.common.utils.android.AsyncUtils;
import com.hancher.gamelife.MainApplication;

import java.io.Serializable;
import java.lang.reflect.Type;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.GET;

/**
 * 作者: Hancher
 * 日期: 2020/9/6 10:21
 * 测试接口
 */
public class ListTestApi {
    public static boolean TEST_LIST_API = true;
    public static String HOST_PGYER = "https://www.pgyer.com/";
    static String HOST_PGYER_APIKEY = "80ad50364f73176d9ec65c70793f2da0";
    static String HOST_PGYER_APPKEY = "8a8083cff6d143dc20cdd0d6e6b95930";
    static String TEST_LIST = "{\"code\":200,\"message\":\"msg\",\"data\":{\"packageName\":\"com.tentent.qq\",\"name\":\"qq\"}}";

    public class TestBean implements Serializable {
        String packageName;
        String name;

        @Override
        public String toString() {
            return "TestBean{" +
                    "packageName='" + packageName + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
    public static Observable<ResultBean<TestBean>> getTestList() {
        if (TEST_LIST_API){
            Type type = new TypeToken<ResultBean<TestBean>>() {}.getType();
            return NetUtil.getVirtualData(TEST_LIST,type);
        } else {
            return new NetUtil<>(HancherService.class).setBaseUrl(HOST_PGYER).build()
                    .getLastest(HOST_PGYER_APIKEY, HOST_PGYER_APPKEY, ApkInfoUtil.getVersionCode(MainApplication.getInstance()))
                    .compose(AsyncUtils.getThreadTransformer());
        }
    }

    interface HancherService {
        @GET
        Observable<ResultBean<TestBean>> getLastest(@Field("_api_key") String apiKey,
                                                    @Field("appKey") String appKey,
                                                    @Field("buildVersion") long buildVersion);
    }

}
