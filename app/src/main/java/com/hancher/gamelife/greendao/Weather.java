package com.hancher.gamelife.greendao;

import com.hancher.common.utils.java.UuidUtil;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/6/20 0020 18:03 <br/>
 * 描述 : 实体类
 */
@Entity
public class Weather {

    @Id(autoincrement = true)
    Long id;
    @Unique
    public String uuid = UuidUtil.getUuidNoLine();
    public Integer deleteflag = 0;

    public String obsTime;
    public Long time;
    /**
     * 体感温度
     */
    public Integer feelsLike;
    public Integer temp;
    public Integer icon;
    public String text;
    public Integer wind360;
    public String windDir;
    public String windScale;
    public Integer windSpeed;
    /**
     * 相对湿度
     */
    public Integer humidity;
    /**
     * 降水量
     */
    public Float precip;
    /**
     * 大气压
     */
    public Integer pressure;
    /**
     * 能见度
     */
    public Integer vis;
    /**
     * 云量
     */
    public Integer cloud;
    /**
     * 实时云量
     */
    public Integer dew;
    @Generated(hash = 1828675744)
    public Weather(Long id, String uuid, Integer deleteflag, String obsTime,
            Long time, Integer feelsLike, Integer temp, Integer icon, String text,
            Integer wind360, String windDir, String windScale, Integer windSpeed,
            Integer humidity, Float precip, Integer pressure, Integer vis,
            Integer cloud, Integer dew) {
        this.id = id;
        this.uuid = uuid;
        this.deleteflag = deleteflag;
        this.obsTime = obsTime;
        this.time = time;
        this.feelsLike = feelsLike;
        this.temp = temp;
        this.icon = icon;
        this.text = text;
        this.wind360 = wind360;
        this.windDir = windDir;
        this.windScale = windScale;
        this.windSpeed = windSpeed;
        this.humidity = humidity;
        this.precip = precip;
        this.pressure = pressure;
        this.vis = vis;
        this.cloud = cloud;
        this.dew = dew;
    }
    @Generated(hash = 556711069)
    public Weather() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUuid() {
        return this.uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    public Integer getDeleteflag() {
        return this.deleteflag;
    }
    public void setDeleteflag(Integer deleteflag) {
        this.deleteflag = deleteflag;
    }
    public String getObsTime() {
        return this.obsTime;
    }
    public void setObsTime(String obsTime) {
        this.obsTime = obsTime;
    }
    public Long getTime() {
        return this.time;
    }
    public void setTime(Long time) {
        this.time = time;
    }
    public Integer getFeelsLike() {
        return this.feelsLike;
    }
    public void setFeelsLike(Integer feelsLike) {
        this.feelsLike = feelsLike;
    }
    public Integer getTemp() {
        return this.temp;
    }
    public void setTemp(Integer temp) {
        this.temp = temp;
    }
    public Integer getIcon() {
        return this.icon;
    }
    public void setIcon(Integer icon) {
        this.icon = icon;
    }
    public String getText() {
        return this.text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public Integer getWind360() {
        return this.wind360;
    }
    public void setWind360(Integer wind360) {
        this.wind360 = wind360;
    }
    public String getWindDir() {
        return this.windDir;
    }
    public void setWindDir(String windDir) {
        this.windDir = windDir;
    }
    public String getWindScale() {
        return this.windScale;
    }
    public void setWindScale(String windScale) {
        this.windScale = windScale;
    }
    public Integer getWindSpeed() {
        return this.windSpeed;
    }
    public void setWindSpeed(Integer windSpeed) {
        this.windSpeed = windSpeed;
    }
    public Integer getHumidity() {
        return this.humidity;
    }
    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }
    public Float getPrecip() {
        return this.precip;
    }
    public void setPrecip(Float precip) {
        this.precip = precip;
    }
    public Integer getPressure() {
        return this.pressure;
    }
    public void setPressure(Integer pressure) {
        this.pressure = pressure;
    }
    public Integer getVis() {
        return this.vis;
    }
    public void setVis(Integer vis) {
        this.vis = vis;
    }
    public Integer getCloud() {
        return this.cloud;
    }
    public void setCloud(Integer cloud) {
        this.cloud = cloud;
    }
    public Integer getDew() {
        return this.dew;
    }
    public void setDew(Integer dew) {
        this.dew = dew;
    }
    
}
