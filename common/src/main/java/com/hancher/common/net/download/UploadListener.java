package com.hancher.common.net.download;

/**
 * @author : Hancher ytu_shaoweijie@163.com
 * @time : 2021/2/3 14:17
 * @describe : 上传监听
 */
public interface UploadListener {

    void onStart();

    void onProgress(int progress);

    void onFinish(String result);

    void onFail(String errorInfo);
}
