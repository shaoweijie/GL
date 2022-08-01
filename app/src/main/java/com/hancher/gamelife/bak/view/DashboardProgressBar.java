package com.hancher.gamelife.bak.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * 双进度条仪表盘
 */
public class DashboardProgressBar extends View {
    private Paint mPaint;
    /**
     * 画布高度
     */
    private int height;
    /**
     * 画布宽度
     */
    private int width;
    /**
     * 画布扭曲tan值, 0.5773f = tan30
     */
    private final static float skewX = 0.212556f;
    /**
     * 进度条总宽度
     */
    private final static float speedWidth = 0.95f;
    /**
     * 中间白条总宽度
     */
    private final static float divWidth = 0.01f;
    /**
     * at 左侧空白总宽度
     */
    private final static float atPaddingLeftWidth = 0.01f;
    /**
     * at 总宽度宽度
     */
    private final static float atWidth = 0.03f;

    /**
     * 速度进度条速度 0 - speedRankValues[speedRank+1]
     */
    private float speedProgress = 0;
    /**
     * 速度进度条各个等级颜色变量
     */
    private int speedRankColor;
    /**
     * 速度进度条5个等级
     */
    private final static int speedRank = 4;
    /**
     * 速度进度条等级间，等级分割速度，包括头和尾
     */
    private float[] speedRankValues = new float[speedRank+1];
    /**
     * at 单个等级宽度
     */
    protected float latticeWidth;
    /**
     * at 单个等级高度
     */
    protected float latticeHeight;
    /**
     * at 每个等级之前的距离
     */
    private float mPadding = 1f;
    /**
     * at 等级个数
     */
    private int latticeCount = 10;

    /**
     * at 当前等级
     */
    protected int atProgress = 2; //现在的进度
    /**
     * 画布扭曲导致的左偏移
     */
    private float skewLeftPadding;


    public DashboardProgressBar(Context context) {
        super(context);
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
    }

    public DashboardProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public DashboardProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        width = getWidth();
        height = getHeight();
        canvas.skew(-skewX,0);
        skewLeftPadding = height * skewX;

        drawLeftSpeed(canvas, skewLeftPadding,0,speedWidth * (width-skewLeftPadding) + skewLeftPadding,height);
        drawLeftDiv(canvas, speedWidth * (width-skewLeftPadding) + skewLeftPadding,0,(speedWidth + divWidth) * (width-skewLeftPadding) + skewLeftPadding,height);
        drawRightAT((speedWidth + divWidth + atPaddingLeftWidth) * (width-skewLeftPadding) + skewLeftPadding,0,width,height, canvas);
    }

    private void drawLeftDiv(Canvas canvas, float left, float top, float right, float bottom) {
        mPaint.setColor(0xffffffff);
        canvas.drawRect(left,top,right,bottom,mPaint);
    }

    private void drawLeftSpeed(Canvas canvas, float left, float top, float right, float bottom) {

        mPaint.setColor(0xFF00E3F7);
        float progressValue = 1.0f;
        for (int i = 1; i < speedRank+1; i++) {
            if (speedProgress<speedRankValues[i]){
                progressValue =  (i - 1) * 1.0f / speedRank +
                        (speedProgress - speedRankValues[i-1]) / (speedRankValues[i] - speedRankValues[i-1]) / speedRank;
                break;
            }
        }
        canvas.drawRect(left,0.25f * (bottom - top) + top,(right-left) * progressValue + left,0.75f * (bottom - top) + top,mPaint);
        speedRankColor = 0x33ffffff;
        for (int i = 0; i < speedRank; i++,speedRankColor+=0x33000000) {
            mPaint.setColor(speedRankColor);
            canvas.drawRect(
                    (float) (right-left) * i / speedRank + left,
                    0.25f * (bottom - top) + top,
                    (float) (right-left) * (i + 1 ) / speedRank+ left,
                    0.75f * (bottom - top) + top,
                    mPaint);
        }
    }



    private void drawRightAT(float left, float top, float right, float bottom, Canvas canvas) {
        float hight = bottom - top;
        latticeWidth = right - left;
        latticeHeight = (hight-mPadding*(latticeCount-1))/latticeCount;

        for (int i=0; i<latticeCount;i++){
            if (i < atProgress) {
                mPaint.setColor(Color.RED);
            }else {
                mPaint.setColor(Color.WHITE);
            }
            canvas.drawRect( left, bottom - i * (latticeHeight + mPadding) - latticeHeight,  left + latticeWidth, bottom - i * (latticeHeight + mPadding), mPaint);
        }
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

    public float getAtProgress() {
        return atProgress;
    }

    public void setAtProgress(int atProgress) {
        if (atProgress < 1 || atProgress > 10.001f) {
            return;
        }
        this.atProgress = atProgress;
        postInvalidate();
    }

    public float[] getSpeedRankValues() {
        return speedRankValues;
    }

    public void setSpeedRankValues(float[] speedRankValues) {
        this.speedRankValues = speedRankValues;
    }
}
