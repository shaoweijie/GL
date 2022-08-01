package com.hancher.gamelife.greendao;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Unique;

import java.io.Serializable;

/**
 * 作者: Hancher
 * 日期: 2020/8/30 15:31
 * 描述: 三合一实体类
 *
 * OKHTTP gson 实体类：
 * 实现 Serializable
 * @SerializedName("name") 中元组名
 * @Since(1.0) 版本号
 * @Expose(serialize = false, deserialize = true) 当前字段关闭序列化
 *
 * GreenDao 实体类注释：
 * @Entity 类
 * @Id(autoincrement = true) 自增
 * @Unique 创建索引
 * @Property(nameInDb = "USERNAME") 自定义数据库元组名
 * @NotNull 非空
 * @Transient 标记这个属性不映射到数据库中
 *
 * RecyclerView item 实体类：
 * 实现 MultiItemEntity，增加 getItemType
 *
 */
@Entity
public class Account implements Serializable , MultiItemEntity {

    @Transient
    private static final long serialVersionUID = 20200927L;
    @Transient
    int itemType;
    @Id(autoincrement = true)
    Long id;
    @Unique
    String uuid;
    String account;
    String psd;
    @SerializedName("note1")
    String note;
    @SerializedName("user1")
    String user;
    String createtime;
    String updatetime;
    String deleteflag;

    @Override
    public String toString() {
        return "Account{" +
                "itemType=" + itemType +
                ", id=" + id +
                ", uuid='" + uuid + '\'' +
                ", account='" + account + '\'' +
                ", psd='" + psd + '\'' +
                ", note='" + note + '\'' +
                ", user='" + user + '\'' +
                ", createtime='" + createtime + '\'' +
                ", updatetime='" + updatetime + '\'' +
                ", deleteflag='" + deleteflag + '\'' +
                '}';
    }

    @Generated(hash = 1380688715)
    public Account(Long id, String uuid, String account, String psd, String note,
            String user, String createtime, String updatetime, String deleteflag) {
        this.id = id;
        this.uuid = uuid;
        this.account = account;
        this.psd = psd;
        this.note = note;
        this.user = user;
        this.createtime = createtime;
        this.updatetime = updatetime;
        this.deleteflag = deleteflag;
    }

    @Generated(hash = 882125521)
    public Account() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return this.account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPsd() {
        return this.psd;
    }

    public void setPsd(String psd) {
        this.psd = psd;
    }

    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getUser() {
        return this.user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getCreatetime() {
        return this.createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getUpdatetime() {
        return this.updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    @Override
    public int getItemType() {
        return 0;
    }

    public String getDeleteflag() {
        return this.deleteflag;
    }

    public void setDeleteflag(String deleteflag) {
        this.deleteflag = deleteflag;
    }
}
