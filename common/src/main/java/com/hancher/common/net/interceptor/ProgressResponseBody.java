package com.hancher.common.net.interceptor;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.Nullable;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/2/4 10:57 <br/>
 * 描述 : 重写ResponseBody监听文件下载进度
 */
public class ProgressResponseBody extends ResponseBody {
    private final ResponseBody body;
    private final ProgressListener listener;
    private final Handler mainThread;
    private BufferedSource bufferedSource;

    public ProgressResponseBody(ResponseBody body, ProgressListener listener) {
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
        return body.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(new ProgressSource(body.source()));
        }
        return bufferedSource;
    }

    final class ProgressSource extends ForwardingSource {
        private long soFarBytes = 0;
        private long totalBytes = -1;

        public ProgressSource(Source delegate) {
            super(delegate);
        }

        @Override
        public long read(Buffer sink, long byteCount) {
            long bytesRead = 0L;
            try {
                bytesRead = super.read(sink, byteCount);
            } catch (Exception e) {
                mainThread.post(() -> listener.onError(e));
            }
            if (totalBytes < 0) {
                totalBytes = contentLength();
            }
            soFarBytes += (bytesRead != -1 ? bytesRead : 0);
            mainThread.post(() -> listener.onResponseProgress(soFarBytes, totalBytes));
            return bytesRead;
        }
    }
}