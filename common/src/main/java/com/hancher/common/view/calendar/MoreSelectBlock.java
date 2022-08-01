package com.hancher.common.view.calendar;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;

import com.hancher.common.view.calendar.base.BaseBlock;
import com.hancher.common.view.calendar.base.Dd1;

import java.util.ArrayList;

/**
 * Created by yujinzhao on 17/2/7.
 */

public class MoreSelectBlock extends BaseBlock {
    ArrayList<Dd1> selectDa1s;
    ArrayList<Dd1> showDd1s;
    Paint blackP = new Paint(Paint.ANTI_ALIAS_FLAG);//黑色画笔
    Paint pinkP = new Paint(Paint.ANTI_ALIAS_FLAG);//浅红色画笔
    Paint redP = new Paint(Paint.ANTI_ALIAS_FLAG);//红色画笔
    Paint grayP = new Paint(Paint.ANTI_ALIAS_FLAG);//灰色画笔
    float drawSize;//绘画背景的尺寸
    float radius;
    private static final float textNubSize = 0.8f;

    public MoreSelectBlock() {

        blackP.setColor(Color.parseColor("#313131"));
        redP.setColor(Color.parseColor("#ff473d"));
        pinkP.setColor(Color.parseColor("#fff1f0"));
        grayP.setColor(Color.parseColor("#bcbcbc"));
    }

    /**
     *
     * @param canvas
     * @param datas
     * @param size
     * @param startIndex
     */
    @Override
    public void draw(Canvas canvas, ArrayList<Dd1> datas, float size, PointF startIndex) {
        drawSize = size * 0.7f;
        radius = drawSize / 2;
        blackP.setTextSize(radius * textNubSize);
        redP.setTextSize(radius * textNubSize);
        grayP.setTextSize(radius * textNubSize);
        showDd1s = datas;
        super.draw(canvas, datas, size, startIndex);
    }

    @Override
    public void drawBlock(float x, float y, Canvas canvas, Dd1 date, int index) {
        boolean isSelect = false;
        if (selectDa1s != null) {
            for (int i = 0; i < selectDa1s.size(); i++) {
                if (date.isseclet(selectDa1s.get(i))) {
                    isSelect = true;
                    drawmBlock(x, y, canvas, date, index);
                    break;
                }
            }
        }
        showDateNub(x, y, canvas, date, isSelect);
    }

    /**
     * 画选中的方块
     *
     * @param x
     * @param y
     * @param canvas
     * @param date
     * @param index
     */
    private void drawmBlock(float x, float y, Canvas canvas, Dd1 date, int index) {
        int type = testBlockType(index);
        switch (type) {
            case 1:
                drawLeftRound(x, y, canvas);
                break;
            case 2:
                drawRect(x, y, canvas);
                break;
            case 3:
                drawRightRound(x, y, canvas);
                break;
            case 4:
                drawRound(x, y, canvas);
                break;
        }
    }

    /**
     * 123 4
     *
     * @param index
     * @return
     */
    private int testBlockType(int index) {
        Dd1 leftDd = null;
        Dd1 rightDd = null;
        boolean isHaveLeft = false;
        boolean isHaveRight = false;
        if (index  != 0) {
            leftDd = showDd1s.get(index - 1);
        }
        if (index != 41) {
            rightDd = showDd1s.get(index + 1);
        }
        for (int i = 0; i < selectDa1s.size(); i++) {
            Dd1 dd1 = selectDa1s.get(i);
            if (leftDd != null && dd1.isseclet(leftDd)) {
                isHaveLeft = true;
            }
            if (rightDd != null && dd1.isseclet(rightDd)) {
                isHaveRight = true;
            }
        }
        if (isHaveLeft && isHaveRight) {
            return 2;
        } else if (isHaveLeft) {
            return 3;
        } else if (isHaveRight) {
            return 1;
        } else {
            return 4;
        }
    }

    private void showDateNub(float x, float y, Canvas canvas, Dd1 date, boolean isSelect) {
        if (date.isukow) {
            drawBlock(grayP, canvas, date.d + "", x + size / 2, y, y + size);
            return;
        }
        if (isSelect) {
            drawBlock(redP, canvas, date.d + "", x + size / 2, y, y + size);
        } else {
            drawBlock(blackP, canvas, date.d + "", x + size / 2, y, y + size);
        }

    }

    /**
     * 画圆圈
     *
     * @param x
     * @param y
     * @param canvas
     */
    public void drawRound(float x, float y, Canvas canvas) {
        canvas.drawCircle(x + size / 2, y + size / 2, radius, pinkP);
    }

    public void drawLeftRound(float x, float y, Canvas canvas) {
        drawRound(x, y, canvas);
        canvas.drawRect(x + size / 2, y + (size - drawSize) / 2, x + size, y + size - (size - drawSize) / 2, pinkP);
    }

    public void drawRightRound(float x, float y, Canvas canvas) {
        drawRound(x, y, canvas);
        canvas.drawRect(x, y + (size - drawSize) / 2, x + size / 2, y + size - (size - drawSize) / 2, pinkP);
    }

    public void drawRect(float x, float y, Canvas canvas) {
        canvas.drawRect(x, y + (size - drawSize) / 2, x + size, y + size - (size - drawSize) / 2, pinkP);
    }
}
