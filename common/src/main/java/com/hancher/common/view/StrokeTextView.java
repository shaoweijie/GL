package com.hancher.common.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * 描边文字
 */
public class StrokeTextView extends AppCompatTextView {
    private Paint mTextPaint;

    private boolean hasStroke = true;
    private float borderWidth = 5f;
    private int borderColor = 0xffffffff;
    private int textColor = 0xff000000;

    public StrokeTextView(Context context) {
        super(context);
        initPaint();
    }

    public StrokeTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public StrokeTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        if (mTextPaint == null) {
            mTextPaint = getPaint();
            mTextPaint.setStrokeWidth(borderWidth);  // 描边宽度borderWidth
            mTextPaint.setStyle(Paint.Style.FILL);
            setTextColor(textColor);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (hasStroke) {
            mTextPaint.setStyle(Paint.Style.STROKE); //描边种类
            setTextColor(borderColor);
            super.onDraw(canvas);
            mTextPaint.setStyle(Paint.Style.FILL);
            setTextColor(textColor);
            super.onDraw(canvas);
        } else {
            super.onDraw(canvas);
        }
    }
}
