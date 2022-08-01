# json数据返回
1. json定义

2. 定义接口
android/app/src/main/java/com/hancher/gamelife/main/tv/setting/PgyerApi.java

a. 封装retrofit2接口
```java
    interface HancherService {
        @POST("apiv2/app/check")
        @FormUrlEncoded
        Observable<ResultBean<LastestApkBean>> getLastest(@Field("_api_key") String apiKey,
                                                          @Field("appKey") String appKey,
                                                          @Field("buildVersion") long buildVersion);
    }
```
b. 封装调用方法
```java
    public static Observable<ResultBean<LastestApkBean>> getLastest() {
        return new NetUtil<>(HancherService.class)
                .setBaseUrl(PgyerUpdateViewModel.HOST_PGYER).build()
                .getLastest(PgyerUpdateViewModel.HOST_PGYER_APIKEY, PgyerUpdateViewModel.HOST_PGYER_APPKEY, ApkInfoUtil.getVersionCode(MainApplication.getInstance()))
                .compose(AsyncUtils.getThreadTransformer());
    }
```
3. vm中调用
```java
PgyerApi.getLastest().subscribe(bean -> {
            LogUtil.d("检查版本成功");
            LastestApkBean lastestApk = bean.getData();
            if (lastestApk == null) {
                LogUtil.e("未知异常");
                return;
            }
            lastRelease.setValue(lastestApk);
        })
```