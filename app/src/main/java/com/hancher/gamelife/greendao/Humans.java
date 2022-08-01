package com.hancher.gamelife.greendao;

import com.chad.library.adapter.base.entity.MultiItemEntity;

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
public class Humans implements Serializable , MultiItemEntity {

    @Transient
    private static final long serialVersionUID = 20201209L;

    @Transient
    int itemType;

    @Id(autoincrement = true)
    Long id;
    
    @Unique
    String uuid;

    String recorduuid;

    String fielduuid;

    String fieldvalue;

    String createtime;

    String updatetime;

    String deleteflag;

    @Generated(hash = 1529489348)
    public Humans(Long id, String uuid, String recorduuid, String fielduuid,
            String fieldvalue, String createtime, String updatetime,
            String deleteflag) {
        this.id = id;
        this.uuid = uuid;
        this.recorduuid = recorduuid;
        this.fielduuid = fielduuid;
        this.fieldvalue = fieldvalue;
        this.createtime = createtime;
        this.updatetime = updatetime;
        this.deleteflag = deleteflag;
    }

    @Generated(hash = 1883775088)
    public Humans() {
    }

    @Override
    public int getItemType() {
        return 0;
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

    public String getFielduuid() {
        return this.fielduuid;
    }

    public void setFielduuid(String fielduuid) {
        this.fielduuid = fielduuid;
    }

    public String getFieldvalue() {
        return this.fieldvalue;
    }

    public void setFieldvalue(String fieldvalue) {
        this.fieldvalue = fieldvalue;
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

    public String getDeleteflag() {
        return this.deleteflag;
    }

    public void setDeleteflag(String deleteflag) {
        this.deleteflag = deleteflag;
    }

    @Override
    public String toString() {
        return "Humans{" +
                "itemType=" + itemType +
                ", id=" + id +
                ", uuid='" + uuid + '\'' +
                ", recorduuid='" + recorduuid + '\'' +
                ", fielduuid='" + fielduuid + '\'' +
                ", fieldvalue='" + fieldvalue + '\'' +
                ", createtime='" + createtime + '\'' +
                ", updatetime='" + updatetime + '\'' +
                ", deleteflag='" + deleteflag + '\'' +
                '}';
    }

    public String getRecorduuid() {
        return this.recorduuid;
    }

    public void setRecorduuid(String recorduuid) {
        this.recorduuid = recorduuid;
    }
}
