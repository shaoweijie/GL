package com.hancher.gamelife.main.tv.setting;

import androidx.lifecycle.MutableLiveData;

import com.hancher.common.base.mvvm02.BaseViewModel;
import com.hancher.common.net.download.DownloadHelper;
import com.hancher.common.net.download.DownloadListener;
import com.hancher.common.utils.android.LogUtil;
import com.hancher.common.utils.android.PathUtil;
import com.hancher.common.utils.android.ToastUtil;

import java.io.File;

import io.reactivex.disposables.Disposable;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/4/13 10:05 <br/>
 * 描述 : 蒲公英app托管
 */
public class PgyerUpdateViewModel extends BaseViewModel {
    private MutableLiveData<LastestApkBean> lastRelease = new MutableLiveData<>();
    public MutableLiveData<LastestApkBean> getLastRelease() {
        return lastRelease;
    }
    static String HOST_PGYER = "https://www.pgyer.com/";
    static String HOST_PGYER_APIKEY = "80ad50364f73176d9ec65c70793f2da0";
    static String HOST_PGYER_APPKEY = "10485466823527c8099b3e2d835217d8";

    /**
     * 检查是否有更新
     */
    public void checkApk() {
        LogUtil.i("检查版本");
        Disposable d = PgyerApi.getLastest().subscribe(bean -> {
            LogUtil.d("检查版本成功");
            LastestApkBean lastestApk = bean.getData();
            if (lastestApk == null) {
                LogUtil.e("未知异常");
                return;
            }
            lastRelease.setValue(lastestApk);
        }, throwable -> ToastUtil.showErr(throwable.getMessage()));
        addDisposable(d);
    }

    /**
     * 下载app到 /storage/emulated/0/Android/data/com.hancher.xx/cache/name_version.apk
     * @param listener
     */
    public void downloadApk(DownloadListener listener) {
        LogUtil.d("新版本："+lastRelease.getValue());
        String fileName = lastRelease.getValue().getBuildFileKey();
        File saveFile = new File(PathUtil.externalAppCacheDir, fileName);
        String url = lastRelease.getValue().getDownloadURL();
        LogUtil.d("下载apk:" + saveFile.getName() + ", 下载路径:" + url);
        DownloadHelper.download(HOST_PGYER, url, saveFile.getAbsolutePath(), listener);
    }

}
