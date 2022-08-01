package com.hancher.common.third.weathercn.bean;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/3/15 16:32 <br/>
 * 描述 : 实时天气
 */
public class NowBean {

    private String nameen;
    private String cityname;
    private String city;
    private String temp;
    private String tempf;
    private String WD;
    private String wde;
    private String WS;
    private String wse;
    private String SD;
    private String time;
    private String weather;
    private String weathere;
    private String weathercode;
    private String qy;
    private String njd;
    private String sd;
    private String rain;
    private String rain24h;
    private String aqi;
    private String limitnumber;
    private String aqi_pm25;
    private String date;

    public void setNameen(String nameen) {
        this.nameen = nameen;
    }

    public String getNameen() {
        return nameen;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getTemp() {
        return temp;
    }

    public void setTempf(String tempf) {
        this.tempf = tempf;
    }

    public String getTempf() {
        return tempf;
    }

    public void setWD(String WD) {
        this.WD = WD;
    }

    public String getWD() {
        return WD;
    }

    public void setWde(String wde) {
        this.wde = wde;
    }

    public String getWde() {
        return wde;
    }

    public void setWS(String WS) {
        this.WS = WS;
    }

    public String getWS() {
        return WS;
    }

    public void setWse(String wse) {
        this.wse = wse;
    }

    public String getWse() {
        return wse;
    }

    public void setSD(String SD) {
        this.SD = SD;
    }

    public String getSD() {
        return SD;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeathere(String weathere) {
        this.weathere = weathere;
    }

    public String getWeathere() {
        return weathere;
    }

    public void setWeathercode(String weathercode) {
        this.weathercode = weathercode;
    }

    public String getWeathercode() {
        return weathercode;
    }

    public void setQy(String qy) {
        this.qy = qy;
    }

    public String getQy() {
        return qy;
    }

    public void setNjd(String njd) {
        this.njd = njd;
    }

    public String getNjd() {
        return njd;
    }

    public void setSd(String sd) {
        this.sd = sd;
    }

    public String getSd() {
        return sd;
    }

    public void setRain(String rain) {
        this.rain = rain;
    }

    public String getRain() {
        return rain;
    }

    public void setRain24h(String rain24h) {
        this.rain24h = rain24h;
    }

    public String getRain24h() {
        return rain24h;
    }

    public void setAqi(String aqi) {
        this.aqi = aqi;
    }

    public String getAqi() {
        return aqi;
    }

    public void setLimitnumber(String limitnumber) {
        this.limitnumber = limitnumber;
    }

    public String getLimitnumber() {
        return limitnumber;
    }

    public void setAqi_pm25(String aqi_pm25) {
        this.aqi_pm25 = aqi_pm25;
    }

    public String getAqi_pm25() {
        return aqi_pm25;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "NowBean{" +
                "nameen='" + nameen + '\'' +
                ", cityname='" + cityname + '\'' +
                ", city='" + city + '\'' +
                ", temp='" + temp + '\'' +
                ", tempf='" + tempf + '\'' +
                ", WD='" + WD + '\'' +
                ", wde='" + wde + '\'' +
                ", WS='" + WS + '\'' +
                ", wse='" + wse + '\'' +
                ", SD=" + SD +
                ", time='" + time + '\'' +
                ", weather='" + weather + '\'' +
                ", weathere='" + weathere + '\'' +
                ", weathercode='" + weathercode + '\'' +
                ", qy='" + qy + '\'' +
                ", njd='" + njd + '\'' +
                ", sd=" + sd +
                ", rain='" + rain + '\'' +
                ", rain24h='" + rain24h + '\'' +
                ", aqi='" + aqi + '\'' +
                ", limitnumber='" + limitnumber + '\'' +
                ", aqi_pm25='" + aqi_pm25 + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
