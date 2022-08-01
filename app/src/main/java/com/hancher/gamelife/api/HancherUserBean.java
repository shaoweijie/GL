package com.hancher.gamelife.api;

import java.io.Serializable;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/3/10 13:52 <br/>
 * 描述 : 用户实体类
 */
public class HancherUserBean implements Serializable {

    private String user;
    private String password;
    private String createtime;
    private String updatetime;
    private String uuid;
    private String deleteflag;
    private String imei;
    private String realname;
    private String idcard;
    private String nickname;

    public HancherUserBean() {
    }

    public String getImei() {
        return imei;
    }

    public HancherUserBean setImei(String imei) {
        this.imei = imei;
        return this;
    }

    public String getRealname() {
        return realname;
    }

    public HancherUserBean setRealname(String realname) {
        this.realname = realname;
        return this;
    }

    public String getIdcard() {
        return idcard;
    }

    public HancherUserBean setIdcard(String idcard) {
        this.idcard = idcard;
        return this;
    }

    public String getNickname() {
        return nickname;
    }

    public HancherUserBean setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public String getUser() {
        return user;
    }

    public HancherUserBean setUser(String user) {
        this.user = user;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public HancherUserBean setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getCreatetime() {
        return createtime;
    }

    public HancherUserBean setCreatetime(String createtime) {
        this.createtime = createtime;
        return this;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public HancherUserBean setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
        return this;
    }

    public String getUuid() {
        return uuid;
    }

    public HancherUserBean setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getDeleteflag() {
        return deleteflag;
    }

    public HancherUserBean setDeleteflag(String deleteflag) {
        this.deleteflag = deleteflag;
        return this;
    }

    @Override
    public String toString() {
        return "HancherUserBean{" +
                "user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", createtime='" + createtime + '\'' +
                ", updatetime='" + updatetime + '\'' +
                ", uuid='" + uuid + '\'' +
                ", deleteflag='" + deleteflag + '\'' +
                '}';
    }
}
