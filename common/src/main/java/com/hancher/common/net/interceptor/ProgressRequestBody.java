package com.hancher.common.net.interceptor;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.Nullable;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/2/4 11:01 <br/>
 * 描述 : 重写RequestBody监听文件上传进度
 */
public class ProgressRequestBody extends RequestBody {
    private final RequestBody body;
    private final ProgressListener listener;
    private final Handler mainThread;
    private BufferedSink bufferedSink;

    public ProgressRequestBody(RequestBody body, ProgressListener listener) {
        this.body = body;
        this.listener = listener;
        mainThread = new Handler(Looper.getMainLooper());
    }

    @Nullable
    @Override
    public MediaType contentType() {
        return body.contentType();
    }

    @Override
    public long contentLength() {
        try {
            return body.contentLength();
        } catch (IOException e) {
            mainThread.post(() -> listener.onError(e));
        }
        return -1;
    }

    @Override
    public void writeTo(BufferedSink sink) {
        if (bufferedSink == null) {
            bufferedSink = Okio.buffer(new ProgressSink(sink));
        }
        try {
            body.writeTo(bufferedSink);
        } catch (IOException e) {
            mainThread.post(() -> listener.onError(e));
        }
        try {
            bufferedSink.flush();
        } catch (IOException e) {
            mainThread.post(() -> listener.onError(e));
        }
    }

    final class ProgressSink extends ForwardingSink {
        private long soFarBytes = 0;
        private long totalBytes = -1;

        public ProgressSink(okio.Sink delegate) {
            super(delegate);
        }

        @Override
        public void write(Buffer source, long byteCount) {
            try {
                super.write(source, byteCount);
            } catch (IOException e) {
                mainThread.post(() -> listener.onError(e));
            }

            if (totalBytes < 0) {
                totalBytes = contentLength();
            }
            soFarBytes += byteCount;

            mainThread.post(() -> listener.onRequestProgress(soFarBytes, totalBytes));
        }
    }
}
