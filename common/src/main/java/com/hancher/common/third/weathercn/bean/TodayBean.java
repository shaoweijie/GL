package com.hancher.common.third.weathercn.bean;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/3/15 16:32 <br/>
 * 描述 : 今日天气
 */
public class TodayBean {

    private Weatherinfo weatherinfo;

    public void setWeatherinfo(Weatherinfo weatherinfo) {
        this.weatherinfo = weatherinfo;
    }

    public Weatherinfo getWeatherinfo() {
        return weatherinfo;
    }

    public class Weatherinfo {

        private String city;
        private String cityname;
        private String temp;
        private String tempn;
        private String weather;
        private String wd;
        private String ws;
        private String weathercode;
        private String weathercoden;
        private String fctime;

        public void setCity(String city) {
            this.city = city;
        }

        public String getCity() {
            return city;
        }

        public void setCityname(String cityname) {
            this.cityname = cityname;
        }

        public String getCityname() {
            return cityname;
        }

        public void setTemp(String temp) {
            this.temp = temp;
        }

        public String getTemp() {
            return temp;
        }

        public void setTempn(String tempn) {
            this.tempn = tempn;
        }

        public String getTempn() {
            return tempn;
        }

        public void setWeather(String weather) {
            this.weather = weather;
        }

        public String getWeather() {
            return weather;
        }

        public void setWd(String wd) {
            this.wd = wd;
        }

        public String getWd() {
            return wd;
        }

        public void setWs(String ws) {
            this.ws = ws;
        }

        public String getWs() {
            return ws;
        }

        public void setWeathercode(String weathercode) {
            this.weathercode = weathercode;
        }

        public String getWeathercode() {
            return weathercode;
        }

        public void setWeathercoden(String weathercoden) {
            this.weathercoden = weathercoden;
        }

        public String getWeathercoden() {
            return weathercoden;
        }

        public void setFctime(String fctime) {
            this.fctime = fctime;
        }

        public String getFctime() {
            return fctime;
        }

        @Override
        public String toString() {
            return "Weatherinfo{" +
                    "city='" + city + '\'' +
                    ", cityname='" + cityname + '\'' +
                    ", temp='" + temp + '\'' +
                    ", tempn='" + tempn + '\'' +
                    ", weather='" + weather + '\'' +
                    ", wd='" + wd + '\'' +
                    ", ws='" + ws + '\'' +
                    ", weathercode='" + weathercode + '\'' +
                    ", weathercoden='" + weathercoden + '\'' +
                    ", fctime='" + fctime + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "WeatherCnBean{" +
                "weatherinfo=" + weatherinfo +
                '}';
    }
}
