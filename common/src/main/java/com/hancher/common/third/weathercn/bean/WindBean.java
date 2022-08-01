package com.hancher.common.third.weathercn.bean;

import java.util.List;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/4/2 9:34 <br/>
 * 描述 : 风
 * {
 * "f":[
 * {
 * "fa":"07",
 * "fb":"01",
 * "fc":"11",
 * "fd":"4",
 * "fe":"南风",
 * "ff":"东北风",
 * "fg":"4-5级",
 * "fh":"5-6级",
 * "fk":"4",
 * "fl":"1",
 * "fm":"71.8",
 * "fn":"36.7",
 * "fi":"3/15",
 * "fj":"今天"
 * },....
 * ]
 * }
 */
public class WindBean {
    private List<F> f;

    public void setF(List<F> f) {
        this.f = f;
    }

    public List<F> getF() {
        return f;
    }

    /**
     * Auto-generated: 2021-04-02 9:34:13
     *
     * @author json.cn (i@json.cn)
     * @website http://www.json.cn/java2pojo/
     */
    public class F {

        private String fa;
        private String fb;
        private String fc;
        private String fd;
        /**
         * 白天风向
         */
        private String fe;
        /**
         * 夜晚风向
         */
        private String ff;
        /**
         * 白天风级别
         */
        private String fg;
        /**
         * 夜晚风级别
         */
        private String fh;
        /**
         * 风级别
         */
        private String fk;
        private String fl;
        private String fm;
        private String fn;
        /**
         * 日期 3/15
         */
        private String fi;
        /**
         * 星期 今天/周二
         */
        private String fj;

        public void setFa(String fa) {
            this.fa = fa;
        }

        public String getFa() {
            return fa;
        }

        public void setFb(String fb) {
            this.fb = fb;
        }

        public String getFb() {
            return fb;
        }

        public void setFc(String fc) {
            this.fc = fc;
        }

        public String getFc() {
            return fc;
        }

        public void setFd(String fd) {
            this.fd = fd;
        }

        public String getFd() {
            return fd;
        }

        public void setFe(String fe) {
            this.fe = fe;
        }

        public String getFe() {
            return fe;
        }

        public void setFf(String ff) {
            this.ff = ff;
        }

        public String getFf() {
            return ff;
        }

        public void setFg(String fg) {
            this.fg = fg;
        }

        public String getFg() {
            return fg;
        }

        public void setFh(String fh) {
            this.fh = fh;
        }

        public String getFh() {
            return fh;
        }

        public void setFk(String fk) {
            this.fk = fk;
        }

        public String getFk() {
            return fk;
        }

        public void setFl(String fl) {
            this.fl = fl;
        }

        public String getFl() {
            return fl;
        }

        public void setFm(String fm) {
            this.fm = fm;
        }

        public String getFm() {
            return fm;
        }

        public void setFn(String fn) {
            this.fn = fn;
        }

        public String getFn() {
            return fn;
        }

        public void setFi(String fi) {
            this.fi = fi;
        }

        public String getFi() {
            return fi;
        }

        public void setFj(String fj) {
            this.fj = fj;
        }

        public String getFj() {
            return fj;
        }

        @Override
        public String toString() {
            return "F{" +
                    "fa='" + fa + '\'' +
                    ", fb='" + fb + '\'' +
                    ", fc='" + fc + '\'' +
                    ", fd='" + fd + '\'' +
                    ", fe='" + fe + '\'' +
                    ", ff='" + ff + '\'' +
                    ", fg='" + fg + '\'' +
                    ", fh='" + fh + '\'' +
                    ", fk='" + fk + '\'' +
                    ", fl='" + fl + '\'' +
                    ", fm='" + fm + '\'' +
                    ", fn='" + fn + '\'' +
                    ", fi='" + fi + '\'' +
                    ", fj='" + fj + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "WindBean{" +
                "f=" + f +
                '}';
    }
}
