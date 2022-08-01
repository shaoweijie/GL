package com.hancher.gamelife.bak;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/3/8 16:47 <br/>
 * 描述 : 图片存储实体类
 */
public class HancherImageBean {
    private String id;
    private String name;
    private String url;
    private String uuid;
    private String createtime;
    private String updatetime;
    private Boolean deleteflag;

    public String getId() {
        return id;
    }

    public HancherImageBean setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public HancherImageBean setName(String name) {
        this.name = name;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public HancherImageBean setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getUuid() {
        return uuid;
    }

    public HancherImageBean setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getCreatetime() {
        return createtime;
    }

    public HancherImageBean setCreatetime(String createtime) {
        this.createtime = createtime;
        return this;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public HancherImageBean setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
        return this;
    }

    public Boolean getDeleteflag() {
        return deleteflag;
    }

    public HancherImageBean setDeleteflag(Boolean deleteflag) {
        this.deleteflag = deleteflag;
        return this;
    }

    @Override
    public String toString() {
        return "HancherImageBean{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", uuid='" + uuid + '\'' +
                ", createtime=" + createtime +
                ", updatetime=" + updatetime +
                ", deleteflag=" + deleteflag +
                '}';
    }
}
