package com.hancher.gamelife.main.tv.app;

import java.io.Serializable;
import java.util.List;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/2/25 10:30 <br/>
 * 描述 : 网络传输bean文件
 */
public class TvBean implements Serializable {
    private Info info;

    private List<Group> groups;

    public void setInfo(Info info) {
        this.info = info;
    }

    public Info getInfo() {
        return this.info;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public List<Group> getGroups() {
        return this.groups;
    }

    @Override
    public String toString() {
        return "TvBean{" +
                "info=" + info +
                ", groups=" + groups +
                '}';
    }

    public static class Info implements Serializable {
        private int count;

        private String updatetime;

        private int version;

        public void setCount(int count) {
            this.count = count;
        }

        public int getCount() {
            return this.count;
        }

        public void setUpdatetime(String updatetime) {
            this.updatetime = updatetime;
        }

        public String getUpdatetime() {
            return this.updatetime;
        }

        public void setVersion(int version) {
            this.version = version;
        }

        public int getVersion() {
            return this.version;
        }

        @Override
        public String toString() {
            return "Info{" +
                    "count=" + count +
                    ", updatetime='" + updatetime + '\'' +
                    ", version=" + version +
                    '}';
        }
    }

    public static class Group implements Serializable {
        private String listname;

        private List<Url> urllist;

        public void setListname(String listname) {
            this.listname = listname;
        }

        public String getListname() {
            return this.listname;
        }

        public void setUrllist(List<Url> urllist) {
            this.urllist = urllist;
        }

        public List<Url> getUrllist() {
            return this.urllist;
        }

        @Override
        public String toString() {
            return "Group{" +
                    "listname='" + listname + '\'' +
                    ", urllist=" + urllist +
                    '}';
        }

        public static class Url implements Serializable {
            private String itemname;

            private String url;

            public void setItemname(String itemname) {
                this.itemname = itemname;
            }

            public String getItemname() {
                return this.itemname;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getUrl() {
                return this.url;
            }

            @Override
            public String toString() {
                return "Url{" +
                        "itemname='" + itemname + '\'' +
                        ", url='" + url + '\'' +
                        '}';
            }
        }
    }
}
