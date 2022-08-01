package com.hancher.gamelife.bak;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hancher.common.net.bean.ResultBean;
import com.hancher.common.net.download.UploadListener;
import com.hancher.common.utils.android.AsyncUtils;
import com.hancher.common.utils.android.LogUtil;
import com.hancher.common.utils.android.PathUtil;
import com.hancher.common.utils.android.ToastUtil;
import com.hancher.common.utils.endurance.IOUtil;
import com.hancher.common.utils.endurance.PreferenceUtil;
import com.hancher.common.utils.java.DateUtil;

import java.io.File;
import java.lang.reflect.Type;

import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/3/17 13:17 <br/>
 * 描述 : 图片存储工具类
 */
public class ImageSaveUtil {
    public final static String SAVE_LOCAL = "FileSettingActivity_SAVE_LOCAL";
    public final static String SAVE_SERVER = "FileSettingActivity_SAVE_SERVER";

    public static void saveImageAsync(String selectFilePath, Consumer<String> onNext) {

        boolean saveLocal = PreferenceUtil.getBoolean(SAVE_LOCAL, true);
        boolean saveServer = PreferenceUtil.getBoolean(SAVE_SERVER, true);

        AsyncUtils.run((ObservableOnSubscribe<String>) emitter -> {

            String result = selectFilePath;
            if (saveLocal) {
                /**
                 * selectFilePath
                 * -》
                 * /storage/emulated/0/Android/data/com.hancher.gamelife/files/image
                 */
                File selectFile = new File(selectFilePath);
                String newPath = PathUtil.externalAppFileImageDir + File.separator +
                        DateUtil.getNow(DateUtil.Type.STRING_YMD_2);
                File copyFile = new File(newPath, selectFile.getName());
                if (!IOUtil.copyFile(selectFile, copyFile)) {
                    LogUtil.e("复制文件失败");
                }

                result = "file://" + copyFile.getAbsolutePath();
                if (!saveServer) {
                    LogUtil.d("不需要上传，直接返回图片");
                    emitter.onNext(result);
                    emitter.onComplete();
                    return;
                }
            }

            if (saveServer) {
                final String resultServerFail = result;
                LogUtil.i("上传图片开始1：", selectFilePath);
                HancherImageApi.upload(selectFilePath, new UploadListener() {
                    @Override
                    public void onStart() {
                        LogUtil.i("上传图片开始2：", selectFilePath);
                    }

                    @Override
                    public void onProgress(int progress) {
                    }

                    @Override
                            public void onFinish(String result2) {
                                try {
                                    Gson gson = new Gson();
                                    Type type = new TypeToken<ResultBean<HancherImageBean>>() {
                                    }.getType();
                                    ResultBean<HancherImageBean> imageBean = gson.fromJson(result2, type);
                                    LogUtil.i("上传图片成功：", imageBean.getData().getUrl());
                                    result2 = imageBean.getData().getUrl();
                                    emitter.onNext(result2);
                                    emitter.onComplete();
                                } catch (Exception e) {
                                    LogUtil.e("err:", e);
                                    emitter.onNext(resultServerFail);
                                    emitter.onComplete();
                                }

                            }

                            @Override
                            public void onFail(String e) {
                                ToastUtil.showErr(e);
                                emitter.onNext(resultServerFail);
                                emitter.onComplete();
                            }
                        });
            }

        }, onNext);

    }
}
