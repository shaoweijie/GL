package com.hancher.common.view.toolbar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/8/27 0027 22:27 <br/>
 * 描述 : 拥有双击事件的Toolbar
 */
public class SuperToolbar extends Toolbar{

    /**
     * 手势检测器
     */
    private GestureDetector mDetector = null;

    public SuperToolbar(@NonNull Context context) {
        super(context);
    }

    public SuperToolbar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SuperToolbar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOnTwoTapListener(GestureDetector.SimpleOnGestureListener onTwoTapListener) {
        mDetector = new GestureDetector(getContext(), onTwoTapListener);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        super.onTouchEvent(ev);
        if (mDetector!=null) {
            mDetector.onTouchEvent(ev);
        }
        return true;
    }
}
