package com.hancher.common.third.weatherhe;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.hancher.common.utils.java.DateUtil;
import com.qweather.sdk.bean.weather.WeatherDailyBean;

import org.xclcharts.chart.PointD;
import org.xclcharts.chart.SplineChart;
import org.xclcharts.chart.SplineData;
import org.xclcharts.renderer.XEnum;
import org.xclcharts.renderer.plot.PlotGrid;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * 作者：Hancher
 * 时间：2020/9/28 0028 上午 8:36
 * 邮箱：ytu_shaoweijie@163.com
 * <p>
 * 说明：天气折线图绘制工具
 */
public class WeatherChartHelper {
    public static int[] bitmapWidthHeigh;
    public static int dayOfCount = 7;
    public static int padding = 0;

    /**
     * 对接和风天气v4.1
     *
     * @param weatherDailyBeanList
     * @return
     */
    public static Bitmap getChartBitmap(Context context, List<WeatherDailyBean.DailyBean> weatherDailyBeanList) {
        bitmapWidthHeigh = new int[]{1200, 600};
        dayOfCount = weatherDailyBeanList.size();
        padding = bitmapWidthHeigh[0] / (dayOfCount + 1) / 2;

        String[] dayCode = new String[dayOfCount];
        String[] nightCode = new String[dayOfCount];
        String[] maxTmp = new String[dayOfCount];
        String[] minTmp = new String[dayOfCount];
        String[] weektime = new String[dayOfCount];
        for (int i = 0; i < dayOfCount; i++) {
            dayCode[i] = weatherDailyBeanList.get(i).getIconDay();
            nightCode[i] = weatherDailyBeanList.get(i).getIconNight();
            maxTmp[i] = weatherDailyBeanList.get(i).getTempMax();
            minTmp[i] = weatherDailyBeanList.get(i).getTempMin();
            weektime[i] = DateUtil.changeDate(weatherDailyBeanList.get(i).getFxDate(),
                    DateUtil.Type.STRING_YMD, DateUtil.Type.STRING_WEEK);
        }
        return getChartBitmap(context, dayCode, nightCode, maxTmp, minTmp, weektime);
    }


    /**
     * 排除第三方实体类，方便兼容
     *
     *
     * @param context
     * @param dayCode
     * @param nightCode
     * @param maxTmp
     * @param minTmp
     * @return
     */
    public static Bitmap getChartBitmap(Context context, String[] dayCode, String[] nightCode, String[] maxTmp, String[] minTmp, String[] weektime) {
        SplineChart mChart = new SplineChart();//表
        LinkedList<String> mLabels = new LinkedList<>();//标签
        LinkedList<SplineData> mChartLines = new LinkedList<>();
        List<PointD> highLinePoints = new ArrayList<PointD>();//上方线
        List<PointD> lowLinePoints = new ArrayList<PointD>();//下方线
        Double chartMax = 0d;
        Double chartMin = 0d;
        for (int i = 0; i < dayOfCount; i++) {
            mLabels.add(weektime[i]);
            if (i == 0) {
                chartMax = Double.parseDouble(maxTmp[i]);
                chartMin = Double.parseDouble(minTmp[i]);
            } else {
                chartMax = Math.max(chartMax, Double.parseDouble(maxTmp[i]));
                chartMin = Math.min(chartMin, Double.parseDouble(minTmp[i]));
            }
            highLinePoints.add(new PointD(i, Double.parseDouble(maxTmp[i])));
            lowLinePoints.add(new PointD(i, Double.parseDouble(minTmp[i])));
        }
        setSplineLineStyle(0, highLinePoints, mChartLines);
        setSplineLineStyle(1, lowLinePoints, mChartLines);
        setSqlineChartStyle(mChart, mLabels, mChartLines, chartMax, chartMin, dayOfCount);
        Bitmap mBitmap = getSplineChartBitmap(context, mChart, dayCode, nightCode);
        return mBitmap;
    }

    private static Bitmap getSplineChartBitmap(Context context, SplineChart chart, String[] dayCode, String[] nightCode) {
        Bitmap mBitmap = Bitmap.createBitmap(bitmapWidthHeigh[0], bitmapWidthHeigh[1] + 4 * padding, Bitmap.Config.ARGB_8888);
        Canvas mCanvas = new Canvas(mBitmap);
        try {
            chart.setChartRange(bitmapWidthHeigh[0], bitmapWidthHeigh[1]);
            chart.render(mCanvas);
        } catch (Exception e) {
            e.printStackTrace();
            return mBitmap;
        }
        rendarIcons(context, mCanvas, dayCode, nightCode);
        mCanvas.save();
        mCanvas.restore();
        return mBitmap;
    }

    private static void rendarIcons(Context context, Canvas mCanvas, String[] dayCode, String[] nightCode) {
        Paint mBitPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBitPaint.setFilterBitmap(true);
        mBitPaint.setDither(true);
        Rect mSrcRect, mDestRect;
        Bitmap iconBitmap;
        boolean firstRow;
        for (int i = 0; i < dayOfCount; i++) {
            for (int j = 0; j < 2; j++) {
                firstRow = j == 0;
                iconBitmap = WeatherIconHelper.getInstance(context).getAssetPic(firstRow ? dayCode[i] : nightCode[i]);
                mSrcRect = new Rect(0, 0, iconBitmap.getWidth(), iconBitmap.getHeight());
                mDestRect = new Rect(
                        i * bitmapWidthHeigh[0] / dayOfCount,
                        firstRow ? bitmapWidthHeigh[1] : bitmapWidthHeigh[1] + 2 * padding,
                        i * bitmapWidthHeigh[0] / dayOfCount + 2 * padding,
                        firstRow ? bitmapWidthHeigh[1] + 2 * padding : bitmapWidthHeigh[1] + 4 * padding);
                mCanvas.drawBitmap(iconBitmap, mSrcRect, mDestRect, mBitPaint);
            }
        }
    }

    private static void setSqlineChartStyle(SplineChart chart, final LinkedList<String> labels,
                                            LinkedList<SplineData> chartData,
                                            Double chartMax, Double chartMin, int dayOfCount) {
        try {
            //设置绘图区默认缩进px值,留置空间显示Axis,Axistitle....
            chart.setPadding(padding,padding,padding,padding);
            //数据源
            chart.setCategories(labels);
            chart.setDataSource(chartData);

            //坐标系
            //数据轴最大值
            chart.getDataAxis().setAxisMax(chartMax + 2);
            chart.getDataAxis().setAxisMin(chartMin - 4);
            //数据轴刻度间隔
            chart.getDataAxis().setAxisSteps(1);

            //标签轴最大值
            chart.setCategoryAxisMax(6);
            //标签轴最小值
            chart.setCategoryAxisMin(0);
            chart.setDotLabelFormatter(value -> {
                String label = value.substring(4, value.length() - 2) + "℃";
                return (label);
            });

            //背景网格
            PlotGrid plot = chart.getPlotGrid();
            plot.hideHorizontalLines();
            plot.hideVerticalLines();

            chart.getCategoryAxis().getAxisPaint().setColor(Color.WHITE);
            chart.getCategoryAxis().getAxisPaint().setTextSize(50);
            chart.getCategoryAxis().hideTickMarks();
            chart.getCategoryAxis().getTickLabelPaint().setColor(Color.WHITE);
            chart.getCategoryAxis().getTickLabelPaint().setFakeBoldText(true);
            chart.getCategoryAxis().setTickLabelMargin(30);
            chart.getCategoryAxis().getTickLabelPaint().setTextSize(50);

            //不使用精确计算，忽略Java计算误差,提高性能
            chart.disableHighPrecision();

            chart.disablePanMode();
            chart.hideBorder();
            chart.getPlotLegend().hide();
            chart.getDataAxis().hide();
            chart.disableScale();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void setSplineLineStyle(int number, List<PointD> line, LinkedList<SplineData> data) {
        int lineColor = number == 0 ? Color.rgb(255, 99, 37) : Color.rgb(56,128,254);

        SplineData dataSeries1 = new SplineData(number == 0 ? "最高温度" : "最低温度", line, lineColor); //Color.rgb(54, 141, 238)
        //线
//        dataSeries1.setLineStyle(XEnum.LineStyle.DASH);
        dataSeries1.getLinePaint().setStrokeWidth(6);//粗线
        //点
        dataSeries1.setDotStyle(XEnum.DotStyle.RING);
        dataSeries1.getDotPaint().setColor(Color.WHITE);//点颜色Color.rgb(30, 179, 143)
        dataSeries1.getPlotLine().getPlotDot().setRingInnerColor(lineColor);//点内部颜色
//        //点上标签
        dataSeries1.getLabelOptions().setLabelBoxStyle(XEnum.LabelBoxStyle.TEXT);
        dataSeries1.setLabelVisible(true);//标签可见
        dataSeries1.getDotLabelPaint().setTextSize(50);//字体大小
        dataSeries1.getLabelOptions().setMargin(number == 0 ? 30 : -80);
        data.add(dataSeries1);

    }
}
