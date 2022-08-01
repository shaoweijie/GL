package com.hancher.gamelife.test;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;

import com.hancher.common.base.mvp01.BasePresenter;
import com.hancher.common.base.mvvm02.BaseApplication;
import com.hancher.common.utils.android.AsyncUtils;
import com.hancher.common.utils.android.LogUtil;
import com.hancher.common.utils.android.PathUtil;
import com.hancher.common.utils.android.PicUtil;
import com.hancher.common.utils.android.PictureCombineUtil;
import com.hancher.common.utils.android.ToastUtil;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2022/7/3 0003 12:03 <br/>
 * 描述 :
 */
public class CapturePictureInVideoPresenter extends BasePresenter<CapturePictureInVideoActivity> {
    public Disposable capturePictureInVideo(String path, int time, Consumer<List<String>> callback) {
        return AsyncUtils.run(new ObservableOnSubscribe<List<String>>() {
            @Override
            public void subscribe(@NotNull ObservableEmitter<List<String>> emitter) throws Exception {
                LogUtil.d("开始视频截图 位置=%s 时间=%d", path, time);
                String capturePath = videoShotScreen(path, PathUtil.sdDownload, time);
                List<String> list = new ArrayList<>();
                list.add(capturePath);
                emitter.onNext(list);
                emitter.onComplete();
            }
        }, callback, ToastUtil::showErr);
    }

    public Disposable capturePicturesInVideo(String videoPath, int time, Consumer<List<String>> callback) {
        return AsyncUtils.run(emitter -> {
            File dir = new File(PathUtil.sdDownload, "VideoCapture");
            if (!dir.exists()){
                dir.mkdirs();
            }
            LogUtil.d("开始视频截图 位置=%s 时间=%d 视频输出位置=%s", videoPath, time, dir.getAbsolutePath());
            List<String> capturePath = videoShotScreens(videoPath, dir.getAbsolutePath(), time);
            emitter.onNext(capturePath);
            emitter.onComplete();
        }, callback, ToastUtil::showErr);
    }

    /**
     *
     * @param videoSource
     * @param saveFilePath 文件夹
     * @param count
     * @return
     */
    public List<String> videoShotScreens(String videoSource, String saveFilePath, int count){
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(videoSource);
        int duration = Integer.parseInt(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));//时长(毫秒)
        LogUtil.d("视频总时长为:%d",duration);
        int every = duration / count;
        //这里传入的时间是  微秒  值,单位需要自己换算
        List<String> data = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Bitmap bitmap = retriever.getFrameAtTime((every/2+every*i)*1000, MediaMetadataRetriever.OPTION_CLOSEST);
            LogUtil.d("截取图片 时间=%d 图片=%s",(every/2+every*i), bitmap.toString());
            if (bitmap != null) {
                try {
                    String file = PicUtil.saveBitmap2File(bitmap, saveFilePath);
                    data.add(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return data;
    }
    /**
     * 视频中截图
     * @param videoSource
     * @param saveFilePath
     * @param time 毫秒
     * @return
     */
    public String videoShotScreen(String videoSource, String saveFilePath, int time){
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(videoSource);
        int duration = Integer.parseInt(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));//时长(毫秒)
        LogUtil.d("视频总时长为:%d",duration);
        //视频时长要大于截图的时长  同时传入时长要大于0
        if (duration > time * 1000 || time <=0){
            LogUtil.e("the time is out of video");
            return "";
        }
        //这里传入的时间是  微秒  值,单位需要自己换算
        Bitmap bitmap = retriever.getFrameAtTime(time * 1000L, MediaMetadataRetriever.OPTION_CLOSEST);
        LogUtil.d("截取图片"+bitmap);
        if (bitmap != null) {
            try {
                return PicUtil.saveBitmap2File(bitmap, saveFilePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public Disposable combinationPicture(List<File> pics, int widthCount, Consumer<String> callback) {
        return AsyncUtils.run(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NotNull ObservableEmitter<String> emitter) throws Exception {

                File dir = new File(PathUtil.sdDownload, "VideoCombination");
                if (!dir.exists()){
                    dir.mkdirs();
                }
                File file = new File(dir.getAbsolutePath(), System.currentTimeMillis() + ".jpg");
                PictureCombineUtil.combinePicture(pics,widthCount, file);
                Uri uri = Uri.fromFile(file);
                BaseApplication.getInstance().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
                emitter.onNext(file.getAbsolutePath());
                emitter.onComplete();
            }
        }, callback, ToastUtil::showErr);
    }
}
