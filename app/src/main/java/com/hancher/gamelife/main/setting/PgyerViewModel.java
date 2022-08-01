package com.hancher.gamelife.main.setting;

import android.app.Activity;

import androidx.lifecycle.MutableLiveData;

import com.afollestad.materialdialogs.MaterialDialog;
import com.hancher.common.base.mvvm02.BaseViewModel;
import com.hancher.common.net.download.DownloadHelper;
import com.hancher.common.net.download.DownloadListener;
import com.hancher.common.utils.android.DialogUtil;
import com.hancher.common.utils.android.IntentUtil;
import com.hancher.common.utils.android.LogUtil;
import com.hancher.common.utils.android.PathUtil;
import com.hancher.common.utils.android.ScreenAlwaysLightUtil;
import com.hancher.common.utils.android.ToastUtil;
import com.hancher.gamelife.api.LastestApkBean;
import com.hancher.gamelife.api.PgyerApi;

import java.io.File;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/4/13 10:05 <br/>
 * 描述 : 获取蒲公英最新版本信息、下载最新apk
 */
public class PgyerViewModel extends BaseViewModel {
    private MutableLiveData<LastestApkBean> lastRelease = new MutableLiveData<>();
    public MutableLiveData<LastestApkBean> getLastRelease() {
        return lastRelease;
    }

    protected void onActive() {
        LogUtil.i("检查版本");
        addDisposable(PgyerApi.getLastest().subscribe(bean -> {
            LogUtil.d("检查版本成功");
            LastestApkBean lastestApk = bean.getData();
            if (lastestApk == null) {
                LogUtil.e("未知异常");
                return;
            }
            lastRelease.setValue(lastestApk);
        }));
    }

    public void downloadApk(Activity activity) {
        if (lastRelease.getValue() == null){
            ToastUtil.show("无信息");
            return;
        }

        String fileName = lastRelease.getValue().getBuildName() + lastRelease.getValue().getBuildVersion()+".apk";
        File saveFile = new File(PathUtil.externalAppCacheDir, fileName);
        String url = lastRelease.getValue().getDownloadURL();
        LogUtil.d("下载apk:" + saveFile.getName() + ", 下载路径:" + url);

        DownloadHelper.download(PgyerApi.HOST_PGYER, url, saveFile.getAbsolutePath(),
                new DownloadListener() {

                    MaterialDialog dialog = DialogUtil.progress(activity)
                            .title("下载最新版本")
                            .content("下载开始...")
                            .onPositive((dialog, which) -> dialog.dismiss())
                            .build();

                    @Override
                    public void onStart() {
                        ScreenAlwaysLightUtil.setOn(activity);
                        dialog.show();
                    }

                    @Override
                    public void onProgress(int progress) {
                        dialog.setContent("下载中...");
                        dialog.setProgress(progress);
                    }

                    @Override
                    public void onFinish(String path) {
                        dialog.setContent("下载完成...");
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        File downFile = new File(path);
                        LogUtil.i("下载完成 文件位置:" + downFile.getAbsolutePath()
                                + ", 文件大小:" + downFile.length());
                        activity.startActivity(IntentUtil.getInstallIntent(activity, downFile));
                        ScreenAlwaysLightUtil.setOff(activity);
                    }

                    @Override
                    public void onFail(String errorInfo) {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        ToastUtil.show("下载失败:" + errorInfo);
                        ScreenAlwaysLightUtil.setOff(activity);
                    }
                });
    }

}
