package com.hancher.gamelife.greendao;

import com.hancher.common.utils.java.UuidUtil;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;

/**
 * 作者：Hancher
 * 时间：2020/10/11 0011 下午 3:08
 * 邮箱：ytu_shaoweijie@163.com
 * <p>
 * 说明：日记表实体类
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
 */
@Entity
public class Diary {

    @Id(autoincrement = true)
    Long id;
    @Unique
    public String uuid = UuidUtil.getUuidNoLine();
    public Long createtime = System.currentTimeMillis();
    public Long updatetime = System.currentTimeMillis();
    public Integer deleteflag = 0;

    public String content;
    public String equipment;
    public String title;
    public String tag;
    public String weatherUuid;
    public String positionUuid;
    public String diaryBook;
    public String imageUuid;
    @Generated(hash = 1369665275)
    public Diary(Long id, String uuid, Long createtime, Long updatetime,
            Integer deleteflag, String content, String equipment, String title,
            String tag, String weatherUuid, String positionUuid, String diaryBook,
            String imageUuid) {
        this.id = id;
        this.uuid = uuid;
        this.createtime = createtime;
        this.updatetime = updatetime;
        this.deleteflag = deleteflag;
        this.content = content;
        this.equipment = equipment;
        this.title = title;
        this.tag = tag;
        this.weatherUuid = weatherUuid;
        this.positionUuid = positionUuid;
        this.diaryBook = diaryBook;
        this.imageUuid = imageUuid;
    }
    @Generated(hash = 112123061)
    public Diary() {
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
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getEquipment() {
        return this.equipment;
    }
    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTag() {
        return this.tag;
    }
    public void setTag(String tag) {
        this.tag = tag;
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
    public String getWeatherUuid() {
        return this.weatherUuid;
    }
    public void setWeatherUuid(String weatherUuid) {
        this.weatherUuid = weatherUuid;
    }
    public String getPositionUuid() {
        return this.positionUuid;
    }
    public void setPositionUuid(String positionUuid) {
        this.positionUuid = positionUuid;
    }
    public String getDiaryBook() {
        return this.diaryBook;
    }
    public void setDiaryBook(String diaryBook) {
        this.diaryBook = diaryBook;
    }
    public String getImageUuid() {
        return this.imageUuid;
    }
    public void setImageUuid(String imageUuid) {
        this.imageUuid = imageUuid;
    }
}
