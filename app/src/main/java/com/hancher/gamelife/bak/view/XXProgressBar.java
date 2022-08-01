package com.hancher.gamelife.bak.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.hancher.gamelife.R;

/**
 * 双进度条仪表盘
 */
public class XXProgressBar extends View {
    private Paint mPaint;
    /**
     * 画布高度
     */
    private int height;
    /**
     * 画布宽度
     */
    private int width;
    private float speedProgress = 0;


    public XXProgressBar(Context context) {
        super(context);
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
    }

    public XXProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public XXProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.bg, null);
//        bitmap1.getWidth();
//        bitmap1.getHeight();
        setMeasuredDimension(bitmap1.getWidth(), bitmap1.getHeight());//这里讲解设置度量
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        width = getWidth();
        height = getHeight();
        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.bg, null);
        canvas.drawBitmap(bitmap1,0f,0f, mPaint);
        canvas.save();
        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), speedProgress>0.8f?R.drawable.orgeen:R.drawable.gree, null);
        canvas.clipRect(new Rect(0, 0, (int)(width * speedProgress), height));
        canvas.drawBitmap(bitmap2,1f,4f, mPaint);
        canvas.restore();
    }

    public void setSpeedProgress(float speedProgress) {
        if (speedProgress == this.speedProgress){
            return;
        }
        this.speedProgress = speedProgress;
        postInvalidate();
    }

    public float getSpeedProgress() {
        return speedProgress;
    }
}
