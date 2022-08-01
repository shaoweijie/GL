package com.hancher.common.net.download;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import com.hancher.common.utils.android.LogUtil;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.Executors;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/2/6 10:21 <br/>
 * 描述 : http 文件下载工具,
 * 保存时监听（拦截器监听会造成body重组），
 * 适配gitee等，下载链接在header中重定向的功能
 * 建议放弃，使用 FileDownloader 代替（支持断点续传，多任务下载）（不支持重定向功能）
 */
public class DownloadHelper {
    public interface DownloadService {
        @Streaming
        @GET
        Call<ResponseBody> download(@Url String url);
//    Call<ResponseBody> download(@Url String url,@Header("RANGE") String start);
    }

    private static int sBufferSize = 8192;

    public static void download(String baseUrl, String url, final String path,
                                final DownloadListener downloadListener) {
        Handler mainThread = new Handler(Looper.getMainLooper());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                //通过线程池获取一个线程，指定callback在子线程中运行。
                .callbackExecutor(Executors.newSingleThreadExecutor())
                .build();

        DownloadService service = retrofit.create(DownloadService.class);
        File oldFile = new File(path);
//        String header = "bytes=" + (oldFile.exists() ? oldFile.length() : 0) + "-";
//        Call<ResponseBody> call = service.download(url,header);
//        LogUtil.d("header:",header);
        Call<ResponseBody> call = service.download(url);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull final Response<ResponseBody> response) {
                //将Response写入到从磁盘中，详见下面分析
                //注意，这个方法是运行在子线程中的
                LogUtil.d("");
                ResponseBody body = response.body();
                if (body == null) {
                    // 可能下载完成，数据为空
                    mainThread.post(() -> downloadListener.onFinish(oldFile.getAbsolutePath()));
                    return;
                }
                writeFileFromIS(new File(path), body.byteStream(), body.contentLength(), mainThread, downloadListener);
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable throwable) {
                LogUtil.d("");
                mainThread.post(() -> downloadListener.onFail(throwable.getMessage()));
            }
        });
    }

    //将输入流写入文件
    private static void writeFileFromIS(File file, InputStream is, long totalLength,
                                        Handler mainThread, DownloadListener downloadListener) {
        //开始下载
        mainThread.post(() -> downloadListener.onStart());

        //创建文件
        if (!file.exists()) {
            try {
                if (!file.getParentFile().exists())
                    file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (Exception e) {
                mainThread.post(() -> downloadListener.onFail(e.getLocalizedMessage()));
            }
        }

        OutputStream os = null;
        long currentLength = 0;
        try {
//            os = new BufferedOutputStream(new FileOutputStream(file,true));
            os = new BufferedOutputStream(new FileOutputStream(file));
            byte data[] = new byte[sBufferSize];
            int len;
            int oldPorgress = -1;
            while ((len = is.read(data, 0, sBufferSize)) != -1) {
                os.write(data, 0, len);
                currentLength += len;
                //计算当前下载进度
                int finalCurrentLength = (int) (100 * currentLength / totalLength);
                if (oldPorgress != finalCurrentLength) {
                    mainThread.post(() -> downloadListener.onProgress(finalCurrentLength));
                    oldPorgress = finalCurrentLength;
                }
            }
            //下载完成，并返回保存的文件路径
            mainThread.post(() -> downloadListener.onFinish(file.getAbsolutePath()));
        } catch (IOException e) {
            mainThread.post(() -> downloadListener.onFail(e.getLocalizedMessage()));
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
