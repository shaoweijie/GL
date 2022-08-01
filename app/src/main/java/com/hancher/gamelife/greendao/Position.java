package com.hancher.gamelife.greendao;

import com.hancher.common.utils.java.UuidUtil;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/6/19 0019 16:42 <br/>
 * 描述 : 位置数据库 GreenDao实体类
 */
@Entity
public class Position {

    @Id(autoincrement = true)
    Long id;
    @Unique
    String uuid = UuidUtil.getUuidNoLine();
    public Integer deleteflag = 0;

    String coorType;
    Double latitude;
    Double longitude;
    Float radius;
    String address;
    String addressDescribe;
    String poiName;
    String poiAddr;
    String poiTag;
    Long timeStamp;
    String time;
    String locateType;
    @Generated(hash = 1150839219)
    public Position(Long id, String uuid, Integer deleteflag, String coorType,
            Double latitude, Double longitude, Float radius, String address,
            String addressDescribe, String poiName, String poiAddr, String poiTag,
            Long timeStamp, String time, String locateType) {
        this.id = id;
        this.uuid = uuid;
        this.deleteflag = deleteflag;
        this.coorType = coorType;
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
        this.address = address;
        this.addressDescribe = addressDescribe;
        this.poiName = poiName;
        this.poiAddr = poiAddr;
        this.poiTag = poiTag;
        this.timeStamp = timeStamp;
        this.time = time;
        this.locateType = locateType;
    }
    @Generated(hash = 958937587)
    public Position() {
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
    public String getCoorType() {
        return this.coorType;
    }
    public void setCoorType(String coorType) {
        this.coorType = coorType;
    }
    public Double getLatitude() {
        return this.latitude;
    }
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
    public Double getLongitude() {
        return this.longitude;
    }
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
    public Float getRadius() {
        return this.radius;
    }
    public void setRadius(Float radius) {
        this.radius = radius;
    }
    public String getAddress() {
        return this.address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getAddressDescribe() {
        return this.addressDescribe;
    }
    public void setAddressDescribe(String addressDescribe) {
        this.addressDescribe = addressDescribe;
    }
    public String getPoiName() {
        return this.poiName;
    }
    public void setPoiName(String poiName) {
        this.poiName = poiName;
    }
    public String getPoiAddr() {
        return this.poiAddr;
    }
    public void setPoiAddr(String poiAddr) {
        this.poiAddr = poiAddr;
    }
    public String getPoiTag() {
        return this.poiTag;
    }
    public void setPoiTag(String poiTag) {
        this.poiTag = poiTag;
    }
    public Long getTimeStamp() {
        return this.timeStamp;
    }
    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }
    public String getTime() {
        return this.time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getLocateType() {
        return this.locateType;
    }
    public void setLocateType(String locateType) {
        this.locateType = locateType;
    }
    public Integer getDeleteflag() {
        return this.deleteflag;
    }
    public void setDeleteflag(Integer deleteflag) {
        this.deleteflag = deleteflag;
    }

}
