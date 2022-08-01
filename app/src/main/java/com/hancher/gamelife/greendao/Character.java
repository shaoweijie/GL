package com.hancher.gamelife.greendao;

import com.hancher.common.utils.java.UuidUtil;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/6/25 0025 8:03 <br/>
 * 描述 : 人物关系，替代 Humans
 */
@Entity
public class Character {
    @Id(autoincrement = true)
    Long id;
    @Unique
    public String uuid = UuidUtil.getUuidNoLine();
    public Integer deleteflag = 0;
    public Long createtime = System.currentTimeMillis();
    public Long updatetime;

    public String name;
    public String school;
    public String company;
    public String organize;
    public String birthday;
    public Boolean birthdaySolar = true;
    public String headPicUuid;
    public Boolean female = true;
    @Generated(hash = 1442510982)
    public Character(Long id, String uuid, Integer deleteflag, Long createtime,
            Long updatetime, String name, String school, String company,
            String organize, String birthday, Boolean birthdaySolar,
            String headPicUuid, Boolean female) {
        this.id = id;
        this.uuid = uuid;
        this.deleteflag = deleteflag;
        this.createtime = createtime;
        this.updatetime = updatetime;
        this.name = name;
        this.school = school;
        this.company = company;
        this.organize = organize;
        this.birthday = birthday;
        this.birthdaySolar = birthdaySolar;
        this.headPicUuid = headPicUuid;
        this.female = female;
    }
    @Generated(hash = 1853959157)
    public Character() {
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
    public String getSchool() {
        return this.school;
    }
    public void setSchool(String school) {
        this.school = school;
    }
    public String getCompany() {
        return this.company;
    }
    public void setCompany(String company) {
        this.company = company;
    }
    public String getOrganize() {
        return this.organize;
    }
    public void setOrganize(String organize) {
        this.organize = organize;
    }
    public String getBirthday() {
        return this.birthday;
    }
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    public Boolean getBirthdaySolar() {
        return this.birthdaySolar;
    }
    public void setBirthdaySolar(Boolean birthdaySolar) {
        this.birthdaySolar = birthdaySolar;
    }
    public String getHeadPicUuid() {
        return this.headPicUuid;
    }
    public void setHeadPicUuid(String headPicUuid) {
        this.headPicUuid = headPicUuid;
    }
    public Boolean getFemale() {
        return this.female;
    }
    public void setFemale(Boolean female) {
        this.female = female;
    }

}
