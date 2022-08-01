package com.hancher.gamelife.greendao;

import com.hancher.common.utils.java.UuidUtil;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/7/17 0017 22:03 <br/>
 * 描述 : 打卡类型
 */
@Entity
public class ColockInType {
    @Id(autoincrement = true)
    Long id;
    @Unique
    String uuid = UuidUtil.getUuidNoLine();
    Integer deleteflag = 0;
    Long createtime = System.currentTimeMillis();
    Long updatetime = System.currentTimeMillis();

    String image;
    String title;
    Long count = 0L;
    @Generated(hash = 1484846026)
    public ColockInType(Long id, String uuid, Integer deleteflag, Long createtime,
            Long updatetime, String image, String title, Long count) {
        this.id = id;
        this.uuid = uuid;
        this.deleteflag = deleteflag;
        this.createtime = createtime;
        this.updatetime = updatetime;
        this.image = image;
        this.title = title;
        this.count = count;
    }
    @Generated(hash = 2065730160)
    public ColockInType() {
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
    public String getImage() {
        return this.image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Long getCount() {
        return this.count;
    }
    public void setCount(Long count) {
        this.count = count;
    }
}
