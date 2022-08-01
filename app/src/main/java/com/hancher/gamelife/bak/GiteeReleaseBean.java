package com.hancher.gamelife.bak;

import java.util.List;

/**
 * 作者：Hancher
 * 时间：2021/2/2 17:02
 * 邮箱：ytu_shaoweijie@163.com
 * <p>
 * 说明：apk安装包的实体类
 */
public class GiteeReleaseBean {

    private int version;
    private ArtifactType artifactType;
    private String applicationId;
    private String variantName;
    private List<Elements> elements;

    public void setVersion(int version) {
        this.version = version;
    }

    public int getVersion() {
        return version;
    }

    public void setArtifactType(ArtifactType artifactType) {
        this.artifactType = artifactType;
    }
    public ArtifactType getArtifactType() {
        return artifactType;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }
    public String getApplicationId() {
        return applicationId;
    }

    public void setVariantName(String variantName) {
        this.variantName = variantName;
    }
    public String getVariantName() {
        return variantName;
    }

    public void setElements(List<Elements> elements) {
        this.elements = elements;
    }
    public List<Elements> getElements() {
        return elements;
    }


    public class ArtifactType {

        private String type;
        private String kind;
        public void setType(String type) {
            this.type = type;
        }
        public String getType() {
            return type;
        }

        public void setKind(String kind) {
            this.kind = kind;
        }
        public String getKind() {
            return kind;
        }

    }

    public class Elements {

        private String type;
        private List<String> filters;
        private int versionCode;
        private String versionName;
        private String outputFile;
        private String url;

        public String getUrl() {
            return url;
        }

        public Elements setUrl(String url) {
            this.url = url;
            return this;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }

        public void setFilters(List<String> filters) {
            this.filters = filters;
        }
        public List<String> getFilters() {
            return filters;
        }

        public void setVersionCode(int versionCode) {
            this.versionCode = versionCode;
        }
        public int getVersionCode() {
            return versionCode;
        }

        public void setVersionName(String versionName) {
            this.versionName = versionName;
        }
        public String getVersionName() {
            return versionName;
        }

        public void setOutputFile(String outputFile) {
            this.outputFile = outputFile;
        }
        public String getOutputFile() {
            return outputFile;
        }

    }
}
