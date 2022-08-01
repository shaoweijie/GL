package com.hancher.gamelife.spiders;

import java.util.List;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2022/4/7 0007 23:06 <br/>
 * 描述 : json实体类
 */
public class SpidersTestEntry {

    private int index;
    private List<Data> data;
    public void setIndex(int index) {
        this.index = index;
    }
    public int getIndex() {
        return index;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }
    public List<Data> getData() {
        return data;
    }

    @Override
    public String toString() {
        return "SpidersTestEntry{" +
                "index=" + index +
                ", data=" + data +
                '}';
    }

    public class Data {

        private String url;
        private String pattern;
        public void setUrl(String url) {
            this.url = url;
        }
        public String getUrl() {
            return url;
        }

        public void setPattern(String pattern) {
            this.pattern = pattern;
        }
        public String getPattern() {
            return pattern;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "url='" + url + '\'' +
                    ", pattern='" + pattern + '\'' +
                    '}';
        }
    }
}
