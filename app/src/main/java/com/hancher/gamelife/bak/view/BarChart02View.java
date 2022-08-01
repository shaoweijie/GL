package com.hancher.gamelife.bak.view;
/**
 * Copyright 2014  XCL-Charts
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * @Project XCL-Charts
 * @Description Android图表基类库
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 * @Copyright Copyright (c) 2014 XCL-Charts (www.xclcharts.com)
 * @license http://www.apache.org/licenses/  Apache v2 License
 * @version 1.0
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import com.hancher.common.utils.android.LogUtil;

import org.xclcharts.chart.BarChart;
import org.xclcharts.chart.BarData;
import org.xclcharts.common.DensityUtil;
import org.xclcharts.event.click.BarPosition;
import org.xclcharts.renderer.XEnum;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

/**
 * @ClassName BarChart02View
 * @Description  柱形图例子(横向)
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 */
public class BarChart02View extends DemoView {

    private static final String TAG = "BarChart02View";
    private BarChart chart = new BarChart();

    //标签轴
    private List<String> chartLabels = new LinkedList<String>();
    private List<BarData> chartData = new LinkedList<BarData>();

    private double[] moneyInAndOut = new double[]{500,800};

    public BarChart02View(Context context) {
        super(context);
//        initView(moneyInAndOut);
    }

    public BarChart02View(Context context, AttributeSet attrs){
        super(context, attrs);
//        initView(moneyInAndOut);
    }

    public BarChart02View(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
//        initView(moneyInAndOut);
    }

    public void initView(double[] data)
    {
        moneyInAndOut = data;
        chartLabels();
        chartDataSet();
        chartRender();

        //綁定手势滑动事件
        this.bindTouch(this,chart);
        postInvalidate();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //图所占范围大小
        if(null !=chart)chart.setChartRange(w,h);
    }


    private void chartRender()
    {
        try {
            //设置绘图区默认缩进px值,留置空间显示Axis,Axistitle....
//            int [] ltrb = getBarLnDefaultSpadding();
            chart.setPadding(
                    DensityUtil.dip2px(getContext(), 15),
                    DensityUtil.dip2px(getContext(), 20),
                    DensityUtil.dip2px(getContext(), 20),
                    DensityUtil.dip2px(getContext(), 20));


//            chart.setTitle("收支情况");
//            chart.addSubtitle("(XCL-Charts Demo)");
//            chart.setTitleVerticalAlign(XEnum.VerticalAlign.MIDDLE);
//            chart.setTitleAlign(XEnum.HorizontalAlign.CENTER);

            //数据源
            chart.setDataSource(chartData);
            chart.setCategories(chartLabels);

            //轴标题
            chart.getAxisTitle().setLeftTitle("");
            chart.getAxisTitle().setLowerTitle("");
            chart.getAxisTitle().setRightTitle("");

            //数据轴 Math.max(moneyInAndOut[0],moneyInAndOut[1])
            double max = Math.max(moneyInAndOut[0],moneyInAndOut[1]) / 1000;
            double axisMax = (int) max + 1;
            double axisSteps = (int) (max/5) + 1;
            chart.getDataAxis().setAxisMax(axisMax);
            chart.getDataAxis().setAxisMin(0);
            chart.getDataAxis().setAxisSteps(axisSteps);

            chart.getDataAxis().getTickLabelPaint().
                    setColor(Color.rgb(199, 88, 122));


            chart.getDataAxis().setLabelFormatter(value -> {
                String tmp = value+"k";
                return tmp;
            });

            //网格
            chart.getPlotGrid().hideHorizontalLines();
            chart.getPlotGrid().hideVerticalLines();
            chart.getPlotGrid().hideEvenRowBgColor();

//            //标签轴文字旋转-45度
//            chart.getCategoryAxis().setTickLabelRotateAngle(-45f);
            //横向显示柱形
            chart.setChartDirection(XEnum.Direction.HORIZONTAL);
            //在柱形顶部显示值
            chart.getBar().setItemLabelVisible(true);
            chart.getBar().getItemLabelPaint().setTextSize(22);

            chart.setItemLabelFormatter(value -> {
                DecimalFormat df=new DecimalFormat("#0.0");
                String label = df.format(value);
                return label;
            });

            //激活点击监听
            chart.ActiveListenItemClick();
            chart.showClikedFocus();


			/*
			chart.setDataAxisPosition(XEnum.DataAxisPosition.BOTTOM);
			chart.getDataAxis().setVerticalTickPosition(XEnum.VerticalAlign.BOTTOM);

			chart.setCategoryAxisPosition(XEnum.CategoryAxisPosition.LEFT);
			chart.getCategoryAxis().setHorizontalTickAlign(Align.LEFT);
			*/

        } catch (Exception e) {
            LogUtil.e(e);
            Log.e(TAG, e.toString());
        }


    }
    private void chartDataSet()
    {
        //标签对应的柱形数据集
        List<Double> dataSeriesA= new LinkedList<Double>();
        dataSeriesA.add(moneyInAndOut[0]/1000);
//        dataSeriesA.add((double)250);
//        dataSeriesA.add((double)400);
        BarData BarDataA = new BarData("收入",dataSeriesA,Color.parseColor("#f44336"));


        List<Double> dataSeriesB= new LinkedList<Double>();
        dataSeriesB.add(moneyInAndOut[1]/1000);
//        dataSeriesB.add((double)150);
//        dataSeriesB.add((double)450);
        BarData BarDataB = new BarData("支出",dataSeriesB,Color.parseColor("#4CAF50"));

        chartData.clear();
        chartData.add(BarDataA);
        chartData.add(BarDataB);
    }

    private void chartLabels()
    {
        chartLabels.clear();
        chartLabels.add("");
//        chartLabels.add("槟榔");
//        chartLabels.add("纯净水(强势插入，演示多行标签)");
    }



    @Override
    public void render(Canvas canvas) {
        try{
            chart.render(canvas);
        } catch (Exception e){
            Log.e(TAG, e.toString());
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        super.onTouchEvent(event);
        if(event.getAction() == MotionEvent.ACTION_UP)
        {
            triggerClick(event.getX(),event.getY());
        }
        return true;
    }


    //触发监听
    private void triggerClick(float x,float y)
    {
        BarPosition record = chart.getPositionRecord(x,y);
        if( null == record) return;

        BarData bData = chartData.get(record.getDataID());
        Double bValue = bData.getDataSet().get(record.getDataChildID());

        Toast.makeText(this.getContext(),
                "info:" + record.getRectInfo() +
                        " Key:" + bData.getKey() +
                        " Current Value:" + Double.toString(bValue),
                Toast.LENGTH_SHORT).show();

        chart.showFocusRectF(record.getRectF());
        chart.getFocusPaint().setStyle(Style.STROKE);
        chart.getFocusPaint().setStrokeWidth(3);
        chart.getFocusPaint().setColor(Color.GREEN);
        this.invalidate();
    }

}