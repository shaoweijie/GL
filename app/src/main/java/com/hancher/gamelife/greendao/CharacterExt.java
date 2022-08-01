package com.hancher.gamelife.greendao;

import com.hancher.common.utils.java.UuidUtil;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/6/25 0025 8:03 <br/>
 * 描述 : 人物关系，替代 Humans
 */
@Entity
public class CharacterExt {
    @Id(autoincrement = true)
    Long id;
    @Unique
    public String uuid = UuidUtil.getUuidNoLine();
    public Integer deleteflag = 0;
    public Long createtime = System.currentTimeMillis();
    public Long updatetime;

    public String characterUuid;
    public String keyType;
    public String value;
    @Generated(hash = 2011779444)
    public CharacterExt(Long id, String uuid, Integer deleteflag, Long createtime,
            Long updatetime, String characterUuid, String keyType, String value) {
        this.id = id;
        this.uuid = uuid;
        this.deleteflag = deleteflag;
        this.createtime = createtime;
        this.updatetime = updatetime;
        this.characterUuid = characterUuid;
        this.keyType = keyType;
        this.value = value;
    }
    @Generated(hash = 779372517)
    public CharacterExt() {
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
    public String getCharacterUuid() {
        return this.characterUuid;
    }
    public void setCharacterUuid(String characterUuid) {
        this.characterUuid = characterUuid;
    }
    public String getKeyType() {
        return this.keyType;
    }
    public void setKeyType(String keyType) {
        this.keyType = keyType;
    }
    public String getValue() {
        return this.value;
    }
    public void setValue(String value) {
        this.value = value;
    }

}
