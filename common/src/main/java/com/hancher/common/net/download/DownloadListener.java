package com.hancher.common.net.download;

/**
 * @author : Hancher ytu_shaoweijie@163.com
 * @time : 2021/2/3 14:17
 * @describe : 下载监听
 */
public interface DownloadListener {

    public void onStart();//下载开始

    public void onProgress(int progress);//下载进度

    public void onFinish(String path);//下载完成

    public void onFail(String errorInfo);//下载失败
}
