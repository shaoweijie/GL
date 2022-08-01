package com.hancher.gamelife.greendao;

import com.hancher.common.utils.java.UuidUtil;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/7/30 0030 21:10 <br/>
 * 描述 : 计划任务
 */
@Entity
public class Task {

    @Id(autoincrement = true)
    Long id;
    @Unique
    public String uuid = UuidUtil.getUuidNoLine();
    public Long createtime = System.currentTimeMillis();
    public Long updatetime = System.currentTimeMillis();
    public Integer deleteflag = 0;


    public String title;
    public String description;
    public Boolean finsh = false;
    public Long finshtime = System.currentTimeMillis();
    @Generated(hash = 2123164976)
    public Task(Long id, String uuid, Long createtime, Long updatetime,
            Integer deleteflag, String title, String description, Boolean finsh,
            Long finshtime) {
        this.id = id;
        this.uuid = uuid;
        this.createtime = createtime;
        this.updatetime = updatetime;
        this.deleteflag = deleteflag;
        this.title = title;
        this.description = description;
        this.finsh = finsh;
        this.finshtime = finshtime;
    }
    @Generated(hash = 733837707)
    public Task() {
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
    public Long getCreatetime() {
        return this.createtime;
    }
    public void setCreatetime(Long createtime) {
        this.createtime = createtime;
    }
    public Long getUpdatetime() {
        return this.updatetime;
    }
    public void setUpdatetime(Long updatetime) {
        this.updatetime = updatetime;
    }
    public Integer getDeleteflag() {
        return this.deleteflag;
    }
    public void setDeleteflag(Integer deleteflag) {
        this.deleteflag = deleteflag;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Boolean getFinsh() {
        return this.finsh;
    }
    public void setFinsh(Boolean finsh) {
        this.finsh = finsh;
    }
    public Long getFinshtime() {
        return this.finshtime;
    }
    public void setFinshtime(Long finshtime) {
        this.finshtime = finshtime;
    }

}
