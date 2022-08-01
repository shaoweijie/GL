package com.hancher.gamelife.greendao;

import com.hancher.common.utils.java.UuidUtil;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/7/30 0030 20:53 <br/>
 * 描述 : 记账实体类
 */
@Entity
public class Money {
    @Id(autoincrement = true)
    Long id;
    @Unique
    public String uuid = UuidUtil.getUuidNoLine();
    public Long createtime = System.currentTimeMillis();
    public Long updatetime = System.currentTimeMillis();
    public Integer deleteflag = 0;

    public Float money;
    public Boolean incomeExpenses;
    public String moneyType;
    public String book;
    public String description;
    @Generated(hash = 709349866)
    public Money(Long id, String uuid, Long createtime, Long updatetime,
            Integer deleteflag, Float money, Boolean incomeExpenses,
            String moneyType, String book, String description) {
        this.id = id;
        this.uuid = uuid;
        this.createtime = createtime;
        this.updatetime = updatetime;
        this.deleteflag = deleteflag;
        this.money = money;
        this.incomeExpenses = incomeExpenses;
        this.moneyType = moneyType;
        this.book = book;
        this.description = description;
    }
    @Generated(hash = 338860089)
    public Money() {
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
    public Float getMoney() {
        return this.money;
    }
    public void setMoney(Float money) {
        this.money = money;
    }
    public Boolean getIncomeExpenses() {
        return this.incomeExpenses;
    }
    public void setIncomeExpenses(Boolean incomeExpenses) {
        this.incomeExpenses = incomeExpenses;
    }
    public String getMoneyType() {
        return this.moneyType;
    }
    public void setMoneyType(String moneyType) {
        this.moneyType = moneyType;
    }
    public String getBook() {
        return this.book;
    }
    public void setBook(String book) {
        this.book = book;
    }
    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
