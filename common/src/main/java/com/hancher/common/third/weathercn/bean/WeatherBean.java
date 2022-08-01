package com.hancher.common.third.weathercn.bean;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/4/2 8:46 <br/>
 * 描述 : 封装其他bean
 */
public class WeatherBean {
    private NowBean nowBean = null;
    private TodayBean todayBean = null;
    private LifeBean lifeBean = null;
    private WindBean windBean = null;

    public WindBean getWindBean() {
        return windBean;
    }

    public WeatherBean setWindBean(WindBean windBean) {
        this.windBean = windBean;
        return this;
    }

    public LifeBean getLifeBean() {
        return lifeBean;
    }

    public WeatherBean setLifeBean(LifeBean lifeBean) {
        this.lifeBean = lifeBean;
        return this;
    }

    public NowBean getNowBean() {
        return nowBean;
    }

    public WeatherBean setNowBean(NowBean nowBean) {
        this.nowBean = nowBean;
        return this;
    }

    public TodayBean getTodayBean() {
        return todayBean;
    }

    public WeatherBean setTodayBean(TodayBean todayBean) {
        this.todayBean = todayBean;
        return this;
    }

    @Override
    public String toString() {
        return "WeatherBean{" +
                "nowBean=" + nowBean +
                ", todayBean=" + todayBean +
                ", lifeBean=" + lifeBean +
                ", windBean=" + windBean +
                '}';
    }
}
