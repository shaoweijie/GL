package com.hancher.gamelife.api;

import java.io.Serializable;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/4/13 10:13 <br/>
 * 描述 : 最新apk实体类
 */
public class LastestApkBean implements Serializable {

    private String buildBuildVersion;
    private String forceUpdateVersion;
    private String forceUpdateVersionNo;
    private boolean needForceUpdate;
    private String downloadURL;
    private boolean buildHaveNewVersion;
    private String buildVersionNo;
    private String buildVersion;
    private String buildUpdateDescription;
    private String appKey;
    private String buildKey;
    private String buildName;
    private String buildIcon;
    private String buildFileKey;
    private String buildFileSize;

    public void setBuildBuildVersion(String buildBuildVersion) {
        this.buildBuildVersion = buildBuildVersion;
    }

    public String getBuildBuildVersion() {
        return buildBuildVersion;
    }

    public void setForceUpdateVersion(String forceUpdateVersion) {
        this.forceUpdateVersion = forceUpdateVersion;
    }

    public String getForceUpdateVersion() {
        return forceUpdateVersion;
    }

    public void setForceUpdateVersionNo(String forceUpdateVersionNo) {
        this.forceUpdateVersionNo = forceUpdateVersionNo;
    }

    public String getForceUpdateVersionNo() {
        return forceUpdateVersionNo;
    }

    public void setNeedForceUpdate(boolean needForceUpdate) {
        this.needForceUpdate = needForceUpdate;
    }

    public boolean getNeedForceUpdate() {
        return needForceUpdate;
    }

    public void setDownloadURL(String downloadURL) {
        this.downloadURL = downloadURL;
    }

    public String getDownloadURL() {
        return downloadURL;
    }

    public void setBuildHaveNewVersion(boolean buildHaveNewVersion) {
        this.buildHaveNewVersion = buildHaveNewVersion;
    }

    public boolean getBuildHaveNewVersion() {
        return buildHaveNewVersion;
    }

    public void setBuildVersionNo(String buildVersionNo) {
        this.buildVersionNo = buildVersionNo;
    }

    public String getBuildVersionNo() {
        return buildVersionNo;
    }

    public void setBuildVersion(String buildVersion) {
        this.buildVersion = buildVersion;
    }

    public String getBuildVersion() {
        return buildVersion;
    }

    public void setBuildUpdateDescription(String buildUpdateDescription) {
        this.buildUpdateDescription = buildUpdateDescription;
    }

    public String getBuildUpdateDescription() {
        return buildUpdateDescription;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setBuildKey(String buildKey) {
        this.buildKey = buildKey;
    }

    public String getBuildKey() {
        return buildKey;
    }

    public void setBuildName(String buildName) {
        this.buildName = buildName;
    }

    public String getBuildName() {
        return buildName;
    }

    public void setBuildIcon(String buildIcon) {
        this.buildIcon = buildIcon;
    }

    public String getBuildIcon() {
        return buildIcon;
    }

    public void setBuildFileKey(String buildFileKey) {
        this.buildFileKey = buildFileKey;
    }

    public String getBuildFileKey() {
        return buildFileKey;
    }

    public void setBuildFileSize(String buildFileSize) {
        this.buildFileSize = buildFileSize;
    }

    public String getBuildFileSize() {
        return buildFileSize;
    }

    @Override
    public String toString() {
        return "LastestApkBean{" +
                "buildBuildVersion='" + buildBuildVersion + '\'' +
                ", forceUpdateVersion='" + forceUpdateVersion + '\'' +
                ", forceUpdateVersionNo='" + forceUpdateVersionNo + '\'' +
                ", needForceUpdate=" + needForceUpdate +
                ", downloadURL='" + downloadURL + '\'' +
                ", buildHaveNewVersion=" + buildHaveNewVersion +
                ", buildVersionNo='" + buildVersionNo + '\'' +
                ", buildVersion='" + buildVersion + '\'' +
                ", buildUpdateDescription='" + buildUpdateDescription + '\'' +
                ", appKey='" + appKey + '\'' +
                ", buildKey='" + buildKey + '\'' +
                ", buildName='" + buildName + '\'' +
                ", buildIcon='" + buildIcon + '\'' +
                ", buildFileKey='" + buildFileKey + '\'' +
                ", buildFileSize='" + buildFileSize + '\'' +
                '}';
    }
}
