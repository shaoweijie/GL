package com.hancher.gamelife.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;

/**
 * 作者: Hancher
 * 日期: 2020/8/30 15:31
 * 描述:
 * GreenDao 实体类注释：
 *
 * @Entity 类
 * @Id(autoincrement = true) 自增
 * @Unique 创建索引
 * @Property(nameInDb = "USERNAME") 自定义数据库元组名
 * @NotNull 非空
 * @Transient 标记这个属性不映射到数据库中
 */
@Entity
public class Rank {

    @Id(autoincrement = true)
    Long id;
    @Unique
    String uuid;
    Integer rank;
    Long starttime;
    Long endtime;
    String unitExperience;
    String allExperience;
    Integer rankType;
    Integer deleteflag;

    @Generated(hash = 1574719976)
    public Rank(Long id, String uuid, Integer rank, Long starttime, Long endtime,
                String unitExperience, String allExperience, Integer rankType,
                Integer deleteflag) {
        this.id = id;
        this.uuid = uuid;
        this.rank = rank;
        this.starttime = starttime;
        this.endtime = endtime;
        this.unitExperience = unitExperience;
        this.allExperience = allExperience;
        this.rankType = rankType;
        this.deleteflag = deleteflag;
    }

    @Generated(hash = 531117843)
    public Rank() {
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

    public Integer getRank() {
        return this.rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Long getStarttime() {
        return this.starttime;
    }

    public void setStarttime(Long starttime) {
        this.starttime = starttime;
    }

    public Long getEndtime() {
        return this.endtime;
    }

    public void setEndtime(Long endtime) {
        this.endtime = endtime;
    }

    public String getUnitExperience() {
        return this.unitExperience;
    }

    public void setUnitExperience(String unitExperience) {
        this.unitExperience = unitExperience;
    }

    public String getAllExperience() {
        return this.allExperience;
    }

    public void setAllExperience(String allExperience) {
        this.allExperience = allExperience;
    }

    public Integer getRankType() {
        return this.rankType;
    }

    public void setRankType(Integer rankType) {
        this.rankType = rankType;
    }

    public Integer getDeleteflag() {
        return this.deleteflag;
    }

    public void setDeleteflag(Integer deleteflag) {
        this.deleteflag = deleteflag;
    }
}
