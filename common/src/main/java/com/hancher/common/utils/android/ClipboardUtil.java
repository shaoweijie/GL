package com.hancher.common.utils.android;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.hancher.common.base.mvvm02.BaseApplication;
import com.hancher.common.utils.java.TextUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * 作者：Hancher
 * 时间：2020/10/19 0019 下午 3:23
 * 邮箱：ytu_shaoweijie@163.com
 * <p>
 * 说明：剪切板工具
 */
public class ClipboardUtil {
    static class LastClipMessage{
        private CharSequence text;

        public LastClipMessage(CharSequence text) {
            this.text = text;
        }

        public CharSequence getText() {
            return text;
        }

        public LastClipMessage setText(CharSequence text) {
            this.text = text;
            return this;
        }
    }
    public static void startListener(){
        LogUtil.d("start");
        ClipboardManager clipboard = (ClipboardManager) BaseApplication.getInstance().getSystemService(Context.CLIPBOARD_SERVICE);
        clipboard.addPrimaryClipChangedListener(() -> {
            LogUtil.d("剪切板发生变化");
            ClipData clipData = clipboard.getPrimaryClip();
            CharSequence text = clipData.getItemAt(0).getText();
            EventBus.getDefault().post(clipData);
            EventBus.getDefault().post(new LastClipMessage(text));
        });
    }
    /**
     * 将字符串复制到粘贴板
     * @param data
     */
    public static void copy(String data) {
        ClipboardManager clipboard = (ClipboardManager) BaseApplication.getInstance()
                .getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText(null, data);
        clipboard.setPrimaryClip(clipData);
    }

    /**
     * 将粘贴板中内容取出
     * @return
     */
    public static CharSequence paste() {
        ClipboardManager clipboard = (ClipboardManager) BaseApplication.getInstance()
                .getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = clipboard.getPrimaryClip();
        if (clipData != null && clipData.getItemCount() > 0) {
            CharSequence text = clipData.getItemAt(0).getText();
            return text;
        }
        return null;
    }
    /**
     * 作者 : Hancher ytu_shaoweijie@163.com <br/>
     * 时间 : 2021/4/16 10:30 <br/>
     * 描述 : 剪切板数据变动ld
     */
    public static class ClipboardChangeLiveData extends MutableLiveData<String> {
        ClipboardManager.OnPrimaryClipChangedListener mOnPrimaryClipChangedListener = () -> {
            CharSequence message = ClipboardUtil.paste();
            if (TextUtil.isEmpty(message)) {
                LogUtil.w("剪切板内容发生变化，然而取出后发现为空字符串");
                return;
            }
            if (message.equals(ClipboardChangeLiveData.this.getValue())) {
                LogUtil.d("剪切板内容发生变化，然而字符串未发生变化");
                return;
            }
            ClipboardChangeLiveData.this.setValue(message.toString());
        };

        @Override
        protected void onActive() {
            super.onActive();
            ClipboardManager clipboard = (ClipboardManager) BaseApplication.getInstance()
                    .getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.addPrimaryClipChangedListener(mOnPrimaryClipChangedListener);
        }

        @Override
        protected void onInactive() {
            ClipboardManager clipboard = (ClipboardManager) BaseApplication.getInstance()
                    .getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.removePrimaryClipChangedListener(mOnPrimaryClipChangedListener);
            super.onInactive();
        }
    }
}
