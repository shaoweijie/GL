package com.hancher.gamelife.greendao;

import com.hancher.common.utils.java.UuidUtil;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/7/17 0017 22:04 <br/>
 * 描述 : 打卡记录
 */
@Entity
public class ColockIn {

    @Id(autoincrement = true)
    Long id;
    @Unique
    String uuid = UuidUtil.getUuidNoLine();
    Integer deleteFlag = 0;
    Long createTime = System.currentTimeMillis();
    Long updateTime = System.currentTimeMillis();

    String typeUuid;
    String description;
    @Generated(hash = 223688216)
    public ColockIn(Long id, String uuid, Integer deleteFlag, Long createTime,
            Long updateTime, String typeUuid, String description) {
        this.id = id;
        this.uuid = uuid;
        this.deleteFlag = deleteFlag;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.typeUuid = typeUuid;
        this.description = description;
    }
    @Generated(hash = 1887553214)
    public ColockIn() {
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
    public Integer getDeleteFlag() {
        return this.deleteFlag;
    }
    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
    public Long getCreateTime() {
        return this.createTime;
    }
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
    public Long getUpdateTime() {
        return this.updateTime;
    }
    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }
    public String getTypeUuid() {
        return this.typeUuid;
    }
    public void setTypeUuid(String typeUuid) {
        this.typeUuid = typeUuid;
    }
    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
