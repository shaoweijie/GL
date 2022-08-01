package com.hancher.common.net.interceptor;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/2/5 16:40 <br/>
 * 描述 : 进度监听
 */
public interface ProgressListener {
    /**
     * 当前请求进度<br/>
     * 注意这里是主进程
     *
     * @param soFarBytes
     * @param totalBytes
     */
    void onRequestProgress(long soFarBytes, long totalBytes);

    /**
     * 当前回复进度<br/>
     * 注意这里是主进程
     *
     * @param soFarBytes
     * @param totalBytes
     */
    void onResponseProgress(long soFarBytes, long totalBytes);

    /**
     * 错误<br/>
     * 注意这里是主进程
     *
     * @param throwable
     */
    void onError(Throwable throwable);
}
