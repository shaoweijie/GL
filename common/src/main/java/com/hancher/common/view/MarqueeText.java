package com.hancher.common.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * 作者：Hancher
 * 时间：2019/1/28.
 * 邮箱：ytu_shaoweijie@163.com
 * 版本：v1.0
 * <p>
 * 说明：跑马灯TextView
 */
public class MarqueeText extends AppCompatTextView {
        public MarqueeText(Context context) {
            super(context);
        }
        public MarqueeText(Context context, AttributeSet attrs) {
            super(context, attrs);
        }
        public MarqueeText(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }
        //返回textview是否处在选中的状态
        //而只有选中的textview才能够实现跑马灯效果
        @Override
        public boolean isFocused() {
            return true;
        }

}
