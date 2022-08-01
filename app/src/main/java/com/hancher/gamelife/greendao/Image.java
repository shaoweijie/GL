package com.hancher.gamelife.greendao;

import com.hancher.common.utils.java.UuidUtil;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/6/24 0024 21:08 <br/>
 * 描述 : 图片存储
 */
@Entity
public class Image {

    @Id(autoincrement = true)
    Long id;
    @Unique
    public String uuid = UuidUtil.getUuidNoLine();
    public Integer deleteflag = 0;
    public Long createtime = System.currentTimeMillis();
    public Long updatetime;

    public String name;
    public String fileUrl;
    public String httpUrl;
    public String content;
    @Generated(hash = 360883043)
    public Image(Long id, String uuid, Integer deleteflag, Long createtime,
            Long updatetime, String name, String fileUrl, String httpUrl,
            String content) {
        this.id = id;
        this.uuid = uuid;
        this.deleteflag = deleteflag;
        this.createtime = createtime;
        this.updatetime = updatetime;
        this.name = name;
        this.fileUrl = fileUrl;
        this.httpUrl = httpUrl;
        this.content = content;
    }
    @Generated(hash = 1590301345)
    public Image() {
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
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getFileUrl() {
        return this.fileUrl;
    }
    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
    public String getHttpUrl() {
        return this.httpUrl;
    }
    public void setHttpUrl(String httpUrl) {
        this.httpUrl = httpUrl;
    }
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
}
