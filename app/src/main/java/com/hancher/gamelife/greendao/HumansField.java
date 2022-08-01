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
public class HumansField implements Serializable , MultiItemEntity {

    @Transient
    private static final long serialVersionUID = 20201209L;

    @Transient
    int itemType;

    @Id(autoincrement = true)
    Long id;
    
    @Unique
    String uuid;

    String typeweight;

    String type;

    String fieldweight;

    String fieldName;

    String createtime;

    String updatetime;

    String deleteflag;

    @Generated(hash = 176265156)
    public HumansField(Long id, String uuid, String typeweight, String type,
            String fieldweight, String fieldName, String createtime,
            String updatetime, String deleteflag) {
        this.id = id;
        this.uuid = uuid;
        this.typeweight = typeweight;
        this.type = type;
        this.fieldweight = fieldweight;
        this.fieldName = fieldName;
        this.createtime = createtime;
        this.updatetime = updatetime;
        this.deleteflag = deleteflag;
    }

    @Generated(hash = 220550522)
    public HumansField() {
    }

    @Override
    public int getItemType() {
        return 0;
    }

    @Override
    public String toString() {
        return "HumansField{" +
                "itemType=" + itemType +
                ", id=" + id +
                ", uuid='" + uuid + '\'' +
                ", typeweight='" + typeweight + '\'' +
                ", type='" + type + '\'' +
                ", fieldweight='" + fieldweight + '\'' +
                ", fieldName='" + fieldName + '\'' +
                ", createtime='" + createtime + '\'' +
                ", updatetime='" + updatetime + '\'' +
                ", deleteflag='" + deleteflag + '\'' +
                '}';
    }

    public HumansField setItemType(int itemType) {
        this.itemType = itemType;
        return this;
    }

    public Long getId() {
        return id;
    }

    public HumansField setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUuid() {
        return uuid;
    }

    public HumansField setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getTypeweight() {
        return typeweight;
    }

    public HumansField setTypeweight(String typeweight) {
        this.typeweight = typeweight;
        return this;
    }

    public String getType() {
        return type;
    }

    public HumansField setType(String type) {
        this.type = type;
        return this;
    }

    public String getFieldweight() {
        return fieldweight;
    }

    public HumansField setFieldweight(String fieldweight) {
        this.fieldweight = fieldweight;
        return this;
    }

    public String getFieldName() {
        return fieldName;
    }

    public HumansField setFieldName(String fieldName) {
        this.fieldName = fieldName;
        return this;
    }

    public String getCreatetime() {
        return createtime;
    }

    public HumansField setCreatetime(String createtime) {
        this.createtime = createtime;
        return this;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public HumansField setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
        return this;
    }

    public String getDeleteflag() {
        return deleteflag;
    }

    public HumansField setDeleteflag(String deleteflag) {
        this.deleteflag = deleteflag;
        return this;
    }
}
