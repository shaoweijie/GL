package com.hancher.gamelife.test;

import android.net.Uri;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts.OpenDocument;
import androidx.activity.result.contract.ActivityResultContracts.OpenMultipleDocuments;

import com.hancher.common.base.mvp01.BaseMvpActivity;
import com.hancher.common.utils.android.AsyncUtils;
import com.hancher.common.utils.android.LogUtil;
import com.hancher.common.utils.android.PathUtil;
import com.hancher.common.utils.android.ToastUtil;
import com.hancher.common.utils.android.UriUtil;
import com.hancher.gamelife.databinding.ActivityCapturePictureInVideoBinding;
import com.tencent.mmkv.MMKV;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;

public class CapturePictureInVideoActivity extends
        BaseMvpActivity<ActivityCapturePictureInVideoBinding, CapturePictureInVideoPresenter> {
    private ActivityResultLauncher<String[]> selectVideo;
    private ActivityResultLauncher<String[]> selectPicture;
    private List<String> picPaths = new ArrayList<>();
    private final static String CAPTURE_PICTURE_IN_VIDEO = "CAPTURE_PICTURE_IN_VIDEO";

    @Override
    protected void initView() {
        String video = MMKV.defaultMMKV().getString(CAPTURE_PICTURE_IN_VIDEO, "");
        binding.txtSelectVideo.setText("选择视频\n" + video);
    }

    @Override
    protected void initListener() {
        super.initListener();

        // 1 选择视频
        selectVideo = registerForActivityResult(new OpenDocument(),
                result -> {
                    String path = UriUtil.getPathByUri(CapturePictureInVideoActivity.this, result);
                    MMKV.defaultMMKV().encode(CAPTURE_PICTURE_IN_VIDEO, path);
                    binding.txtSelectVideo.setText("选择视频\n" + path);
                });
        binding.btnSelectVideo.setOnClickListener(v -> selectVideo.launch(new String[]{"*/*"}));

        // 2 视频截取图片
        binding.btnCaptureVideoPicture.setOnClickListener(v -> {
            String video = MMKV.defaultMMKV().getString(CAPTURE_PICTURE_IN_VIDEO, "");
            int time;
            try {
                time = Integer.parseInt(binding.editCapture.getEditableText().toString());
            } catch (NumberFormatException e) {
                ToastUtil.showErr(e);
                return;
            }
            Disposable d1 = presenter.capturePicturesInVideo(video, time, pictures -> {
                ToastUtil.show(pictures.toString());
                picPaths.clear();
                picPaths.addAll(pictures);
                binding.txtSelectPicture.setText(String.format("选择图片：%d\n%s", picPaths.size(), picPaths.toString()));
            });
            disposableList.add(d1);
        });

        // 3 图片选择
        selectPicture = registerForActivityResult(new OpenMultipleDocuments(),
                result -> {
                    picPaths.clear();
                    for (Uri item : result) {
                        picPaths.add(UriUtil.getPathByUri(CapturePictureInVideoActivity.this, item));
                    }
                    binding.txtSelectPicture.setText(String.format("选择图片：%d\n%s", picPaths.size(), picPaths.toString()));
                });
        binding.btnSelectPicture.setOnClickListener(v -> selectPicture.launch(new String[]{"*/*"}));

        // 4 组合图片
        binding.btnCombination.setOnClickListener(v -> {
            List<File> files = new ArrayList<>();
            for (String item : picPaths) {
                files.add(new File(item));
            }
            LogUtil.d("图片拼接个数%d", files.size());
            int widthCount = Integer.parseInt(binding.editWeight.getEditableText().toString());
            Disposable d2 = presenter.combinationPicture(files, widthCount, s -> ToastUtil.show("组合图片成功\n" + s));
            disposableList.add(d2);
        });

        binding.btnFastSelectPicture.setOnClickListener(v -> {
            AsyncUtils.run((ObservableOnSubscribe<Boolean>) emitter -> {
                File dir = new File(PathUtil.sdDownload, "VideoCombination");
                for (File item : dir.listFiles()) {
                    item.delete();
                }
                dir = new File(PathUtil.sdDownload, "VideoCapture");
                for (File item : dir.listFiles()) {
                    item.delete();
                }
            }, result -> {
                if (result) {
                    ToastUtil.show("删除成功");
                } else {
                    ToastUtil.showErr("删除失败");
                }
            }, throwable -> ToastUtil.showErr(throwable));
        });

    }
}