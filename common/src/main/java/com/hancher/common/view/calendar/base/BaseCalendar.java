package com.hancher.common.view.calendar.base;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.hancher.common.R;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by yujinzhao on 17/2/6.
 */

public class BaseCalendar extends View {

    /**
     * 是不是多选选模式
     */
    private boolean isMultiSelect = false;
    private boolean isSingleSelect = true;

    /**
     * 控件默认宽度
     */
    private float viewWidth;
    private float viewHeight;
    /**
     * 头部控件
     */
    private float headHeight;
    private float weekViewHeight;//星期部分高度
    private float contentHeight;//日历内容部分高度
    private float contentBaseBlockSize;//日历部分每格尺寸

    private PointF weekPointF;
    private PointF contentPointF;//日期起始点


    private Paint headPaint = new Paint(Paint.ANTI_ALIAS_FLAG);//头部画笔
    private Paint weekPaint = new Paint(Paint.ANTI_ALIAS_FLAG);//头部画笔

    private static String[] weeks = {"日", "一", "二", "三", "四", "五", "六"};

    private BaseBlock baseBlock;

    private String titleTimeStr = "";

    public void setBaseBlock(BaseBlock baseBlock) {
        this.baseBlock = baseBlock;
        invalidate();
    }

    TimeUtil1 timeUtil1 = new TimeUtil1();
    ArrayList<Dd1> dateList = timeUtil1.getlist(timeUtil1.getN(), timeUtil1.getY());//界面显示的日期集合


    public BaseCalendar(Context context) {
        this(context, null);
    }

    public BaseCalendar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseCalendar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DulidayCalendar, defStyleAttr, 0);
        isMultiSelect = a.getBoolean(R.styleable.DulidayCalendar_multiselect, false);
        a.recycle();
        initPaint();
        initDate();
    }

    private void initDate() {
        titleTimeStr = timeUtil1.getshowyear() + "年" + (timeUtil1.getshowmouth() + 1) + "月";
    }

    private void initPaint() {
        headPaint.setColor(Color.parseColor("#313131"));
        weekPaint.setColor(Color.parseColor("#313131"));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewHeight = h;
        contentHeight = w / 7 * 6;

        headHeight = (h - contentHeight) / 1.5f;
        weekViewHeight = (h - contentHeight) / 2.5f;

        weekPointF = new PointF(0, headHeight);
        contentPointF = new PointF(0, headHeight + weekViewHeight);
        contentBaseBlockSize = viewWidth / 7;

//        HancherLogUtil.d("w="+w
//                +", h="+h
//                +", oldw="+oldw
//                +", oldh="+oldh
//                +", contentHeight="+contentHeight
//                +", headHeight="+headHeight
//                +", weekViewHeight="+weekViewHeight
//                +", contentBaseBlockSize="+contentBaseBlockSize
//        );
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawTitle(canvas);
        drawWeekText(canvas);
        drawBlock(canvas);
    }

    private void drawBlock(Canvas canvas) {
        customDrawBlock(baseBlock);
        if (baseBlock != null) {
            baseBlock.draw(canvas, dateList, contentBaseBlockSize, contentPointF);
        }
    }

    /**
     * 自定义回话方块内容
     * 绘画方格的方法
     *
     * @param baseBlock
     */
    public void customDrawBlock(BaseBlock baseBlock) {

    }

    /**
     * 画日期文字
     *
     * @param canvas
     */
    private void drawWeekText(Canvas canvas) {
        float textWidth = viewWidth / weeks.length;
        weekPaint.setTextSize(weekViewHeight / 3);
        for (int i = 0; i < weeks.length; i++) {
            drawText(weekPaint, canvas, weeks[i], textWidth / 2 + textWidth * i, weekPointF.y, weekPointF.y + weekViewHeight);
        }
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.parseColor("#e7e7e7"));
        canvas.drawLine(weekPointF.x, weekPointF.y + weekViewHeight, weekPointF.x + viewWidth, weekPointF.y + weekViewHeight, paint);
    }

    /**
     * 画头部控件
     *
     * @param canvas
     */
    private void drawTitle(Canvas canvas) {
//        HancherLogUtil.d((headHeight / 2)+",  "+(viewWidth / 5 * 3 / 8)+"");
        headPaint.setTextSize(Math.min(headHeight / 2, viewWidth / 5 * 3 / 8));
        PointF liftP = new PointF(viewWidth / 5, headHeight / 2);
        PointF rightP = new PointF(viewWidth / 5 * 4, headHeight / 2);
        drawBitmap(canvas, liftP, R.drawable.left, headPaint);
        drawBitmap(canvas, rightP, R.drawable.right, headPaint);
        drawText(headPaint, canvas, titleTimeStr, viewWidth / 2, 0, headHeight);
//        canvas.drawLine(0, headHeight, viewWidth, headHeight, headPaint);
    }

    /**
     * 设置日历默认宽
     *
     * @param size
     */
    private void setDefaultWidth(int size) {
        viewWidth = size;
    }

    //默认执行，计算view的宽高,在onDraw()之前
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeigth = MeasureSpec.getSize(heightMeasureSpec);
        int minSize = measureHeigth > measureWidth ? measureWidth : measureHeigth;
        setDefaultWidth(minSize);
        int width = measureWidth(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);
        //设置宽高
        setMeasuredDimension(width, height);
    }

    //根据xml的设定获取宽度
    private int measureWidth(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        //wrap_content
        if (specMode == MeasureSpec.AT_MOST) {
            specSize = (int) viewWidth;
        }
        //fill_parent或者精确值
        else if (specMode == MeasureSpec.EXACTLY) {
        }
        return specSize;
    }

    //根据xml的设定获取高度
    private int measureHeight(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        //wrap_content
        if (specMode == MeasureSpec.AT_MOST) {
            specSize = (int) (viewWidth + viewWidth / 10);
        }
        //fill_parent或者精确值
        else if (specMode == MeasureSpec.EXACTLY) {
        }
        return specSize;
    }

    /**
     * 画资源文件中的图片
     *
     * @param canvas
     * @param pointF
     * @param drawable
     * @param mPaint
     */
    private void drawBitmap(Canvas canvas, PointF pointF, int drawable, Paint mPaint) {
        InputStream is = getResources().openRawResource(drawable);
        Bitmap mBitmap = BitmapFactory.decodeStream(is);
        int x = mBitmap.getWidth();
        int y = mBitmap.getHeight();
        canvas.drawBitmap(mBitmap, pointF.x - (x / 2), pointF.y - (y / 2), mPaint);
    }

    /**
     * 写文字
     *
     * @param paint
     * @param canvas
     * @param str
     * @param x
     * @param rectTop
     * @param rectBottom
     */
    private void drawText(Paint paint, Canvas canvas, String str, float x, float rectTop, float rectBottom) {
        paint.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        float baseline = (rectBottom + rectTop - fontMetrics.bottom - fontMetrics.top) / 2;
        canvas.drawText(str, x, baseline, paint);
    }

    PointF downPoint = null;
    long downTime;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downTime = System.currentTimeMillis();
                if (event.getY() < headHeight) {//点击在头部
                    if (event.getX() < viewWidth / 3) {
                        changeMonth(false);
                    } else if (event.getX() > viewWidth / 3 * 2) {
                        changeMonth(true);
                    }
                } else if (event.getY() > headHeight + weekViewHeight) {//点击在日历内容面板
                    downPoint = new PointF(event.getX(), event.getY());
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (event.getY() > headHeight + weekViewHeight) {
                    if (isMultiSelect && downPoint != null) {
                        int firstIndex = countIndex(getContentPoint(downPoint), contentBaseBlockSize);
                        int selectIndex = countIndex(getContentPoint(new PointF(event.getX(), event.getY())), contentBaseBlockSize);
//                        Log.e("yjz", "第一个点击的位置：" + countIndex(getContentPoint(downPoint), contentBaseBlockSize));
//                        Log.e("yjz", "选中位置：" + countIndex(getContentPoint(new PointF(event.getX(), event.getY())), contentBaseBlockSize));
                        if (firstIndex >= dateList.size() || selectIndex >= dateList.size()
                                || firstIndex < 0 || selectIndex < 0) {//边缘越界
                            break;
                        } else {
                            onMoveClickListener(downTime, dateList.get(firstIndex), dateList.get(selectIndex));
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (isSingleSelect) {//单选模式
                    int startIndex = countIndex(getContentPoint(downPoint), contentBaseBlockSize);
                    int endIndex = countIndex(getContentPoint(new PointF(event.getX(), event.getY())), contentBaseBlockSize);
                    if (startIndex == endIndex) {
//                        Log.e("yjz", countIndex(getContentPoint(downPoint), contentBaseBlockSize) + "");
                        onClickListener(dateList.get(startIndex));
                    }
                }
                downPoint = null;
                break;
        }
        return true;
    }

    /**
     * 单选模式的点击
     *
     * @param selectDd1
     */
    public void onClickListener(Dd1 selectDd1) {

    }

    /**
     * 移动选择
     *
     * @param firstDd1
     * @param selectDd1
     */
    public void onMoveClickListener(long downTime, Dd1 firstDd1, Dd1 selectDd1) {

    }

    /**
     * 把点击的坐标转换成日历面板的相对坐标
     *
     * @param eventP
     * @return
     */
    public PointF getContentPoint(PointF eventP) {
        return new PointF(eventP.x - contentPointF.x, eventP.y - contentPointF.y);
    }

    /**
     * 计算位置
     */
    private int countIndex(PointF p, float size) {
        int x = (int) (p.x / size);
        if(x > 6){
            x = 6;
        }
        int y = (int) (p.y / size);
        if(y>6){
            y = 6;
        }
        return x + y * 7;
    }

    private int changemouth = 0;

    /**
     * 改变日期
     *
     * @param addMonth
     */
    private void changeMonth(boolean addMonth) {
        if (addMonth) {
            changemouth++;
        } else {
            changemouth--;
        }
        dateList = timeUtil1.getlist(timeUtil1.getN(), timeUtil1.getY() + changemouth);
        titleTimeStr = new SimpleDateFormat("yyyy年M月").format(new Date(timeUtil1.getCalendar().getTimeInMillis()));
        invalidate();
    }
}
