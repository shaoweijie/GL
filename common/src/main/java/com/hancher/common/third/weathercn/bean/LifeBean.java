package com.hancher.common.third.weathercn.bean;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/4/2 9:24 <br/>
 * 描述 : 生活指数
 * {
 * "zs":{
 * "date":"2021031511",
 * "ac_name":"空调开启指数",
 * "ac_hint":"较少开启",
 * "ac_des_s":"体感舒适，不需要开启空调。",
 * "ag_name":"过敏指数",
 * "ag_hint":"极不易发",
 * "ag_des_s":"无需担心过敏。",
 * "cl_name":"晨练指数",
 * "cl_hint":"不宜",
 * "cl_des_s":"有较强降水，建议在室内做适当锻炼。",
 * "co_name":"舒适度指数",
 * "co_hint":"很不舒适",
 * "co_des_s":"天气凉有雨雪大风，注意保暖防寒。",
 * "ct_name":"穿衣指数",
 * "ct_hint":"冷",
 * "ct_des_s":"建议着棉衣加羊毛衫等冬季服装。",
 * "dy_name":"钓鱼指数",
 * "dy_hint":"不宜",
 * "dy_des_s":"风力太大，不适合垂钓。",
 * "fs_name":"防晒指数",
 * "fs_hint":"弱",
 * "fs_des_s":"涂抹8-12SPF防晒护肤品。",
 * "gj_name":"逛街指数",
 * "gj_hint":"较不宜",
 * "gj_des_s":"有降水，风力很大，较不适宜逛街",
 * "gl_name":"太阳镜指数",
 * "gl_hint":"不需要",
 * "gl_des_s":"白天能见度差不需要佩戴太阳镜",
 * "gm_name":"感冒指数",
 * "gm_hint":"易发",
 * "gm_des_s":"大幅度降温，风力较强，增加衣服。",
 * "gz_name":"干燥指数",
 * "gz_hint":"适宜",
 * "gz_des_s":"风速偏大，气温适宜，但体感温度会低一些，建议多使用保湿型护肤品涂抹皮肤，预防皮肤干燥。",
 * "hc_name":"划船指数",
 * "hc_hint":"不适宜",
 * "hc_des_s":"风力很大，不适宜划船。",
 * "jt_name":"交通指数",
 * "jt_hint":"一般",
 * "jt_des_s":"有降水且路面湿滑，注意保持车距。",
 * "lk_name":"路况指数",
 * "lk_hint":"潮湿",
 * "lk_des_s":"有降水，路面潮湿，请小心驾驶。",
 * "ls_name":"晾晒指数",
 * "ls_hint":"不宜",
 * "ls_des_s":"降水可能会淋湿衣物，请选择在室内晾晒。",
 * "mf_name":"美发指数",
 * "mf_hint":"一般",
 * "mf_des_s":"风大尘多，注意头发清洁和滋润。",
 * "nl_name":"夜生活指数",
 * "nl_hint":"较不适宜",
 * "nl_des_s":"建议夜生活最好在室内进行。",
 * "pj_name":"啤酒指数",
 * "pj_hint":"较不适宜",
 * "pj_des_s":"有些凉意，少量饮用常温啤酒。",
 * "pk_name":"放风筝指数",
 * "pk_hint":"不宜",
 * "pk_des_s":"天气不好，不适宜放风筝。",
 * "pl_name":"空气污染扩散条件指数",
 * "pl_hint":"优",
 * "pl_des_s":"气象条件非常有利于空气污染物扩散。",
 * "pp_name":"化妆指数",
 * "pp_hint":"保湿",
 * "pp_des_s":"请选用滋润型化妆品。",
 * "tr_name":"旅游指数",
 * "tr_hint":"一般",
 * "tr_des_s":"风大有降水，影响出行，注意防风防雨。",
 * "uv_name":"紫外线强度指数",
 * "uv_hint":"最弱",
 * "uv_des_s":"辐射弱，涂擦SPF8-12防晒护肤品。",
 * "wc_name":"风寒指数",
 * "wc_hint":"凉",
 * "wc_des_s":"风力较大，感觉有点凉，室外活动注意适当增减衣物。",
 * "xc_name":"洗车指数",
 * "xc_hint":"不宜",
 * "xc_des_s":"有雨，雨水和泥水会弄脏爱车。",
 * "xq_name":"心情指数",
 * "xq_hint":"较差",
 * "xq_des_s":"雨水可能会使心绪无端地挂上轻愁。",
 * "yd_name":"运动指数",
 * "yd_hint":"较不宜",
 * "yd_des_s":"有降水，推荐您在室内进行休闲运动。",
 * "yh_name":"约会指数",
 * "yh_hint":"不适宜",
 * "yh_des_s":"建议在室内约会，免去天气的骚扰。",
 * "ys_name":"雨伞指数",
 * "ys_hint":"带伞",
 * "ys_des_s":"有降水，带雨伞，短期外出可收起雨伞。",
 * "zs_name":"中暑指数",
 * "zs_hint":"无中暑风险",
 * "zs_des_s":"天气舒适，令人神清气爽的一天，不用担心中暑的困扰。"
 * },
 * "cn":"烟台"
 * }
 */
public class LifeBean {

    private Zs zs;
    private String cn;

    public void setZs(Zs zs) {
        this.zs = zs;
    }

    public Zs getZs() {
        return zs;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }

    public String getCn() {
        return cn;
    }

    public class Zs {

        private String date;
        private String ac_name;
        private String ac_hint;
        private String ac_des_s;
        private String ag_name;
        private String ag_hint;
        private String ag_des_s;
        private String cl_name;
        private String cl_hint;
        private String cl_des_s;
        private String co_name;
        private String co_hint;
        private String co_des_s;
        private String ct_name;
        private String ct_hint;
        private String ct_des_s;
        private String dy_name;
        private String dy_hint;
        private String dy_des_s;
        private String fs_name;
        private String fs_hint;
        private String fs_des_s;
        private String gj_name;
        private String gj_hint;
        private String gj_des_s;
        private String gl_name;
        private String gl_hint;
        private String gl_des_s;
        private String gm_name;
        private String gm_hint;
        private String gm_des_s;
        private String gz_name;
        private String gz_hint;
        private String gz_des_s;
        private String hc_name;
        private String hc_hint;
        private String hc_des_s;
        private String jt_name;
        private String jt_hint;
        private String jt_des_s;
        private String lk_name;
        private String lk_hint;
        private String lk_des_s;
        private String ls_name;
        private String ls_hint;
        private String ls_des_s;
        private String mf_name;
        private String mf_hint;
        private String mf_des_s;
        private String nl_name;
        private String nl_hint;
        private String nl_des_s;
        private String pj_name;
        private String pj_hint;
        private String pj_des_s;
        private String pk_name;
        private String pk_hint;
        private String pk_des_s;
        private String pl_name;
        private String pl_hint;
        private String pl_des_s;
        private String pp_name;
        private String pp_hint;
        private String pp_des_s;
        private String tr_name;
        private String tr_hint;
        private String tr_des_s;
        private String uv_name;
        private String uv_hint;
        private String uv_des_s;
        private String wc_name;
        private String wc_hint;
        private String wc_des_s;
        private String xc_name;
        private String xc_hint;
        private String xc_des_s;
        private String xq_name;
        private String xq_hint;
        private String xq_des_s;
        private String yd_name;
        private String yd_hint;
        private String yd_des_s;
        private String yh_name;
        private String yh_hint;
        private String yh_des_s;
        private String ys_name;
        private String ys_hint;
        private String ys_des_s;
        private String zs_name;
        private String zs_hint;
        private String zs_des_s;

        public void setDate(String date) {
            this.date = date;
        }

        public String getDate() {
            return date;
        }

        public void setAc_name(String ac_name) {
            this.ac_name = ac_name;
        }

        public String getAc_name() {
            return ac_name;
        }

        public void setAc_hint(String ac_hint) {
            this.ac_hint = ac_hint;
        }

        public String getAc_hint() {
            return ac_hint;
        }

        public void setAc_des_s(String ac_des_s) {
            this.ac_des_s = ac_des_s;
        }

        public String getAc_des_s() {
            return ac_des_s;
        }

        public void setAg_name(String ag_name) {
            this.ag_name = ag_name;
        }

        public String getAg_name() {
            return ag_name;
        }

        public void setAg_hint(String ag_hint) {
            this.ag_hint = ag_hint;
        }

        public String getAg_hint() {
            return ag_hint;
        }

        public void setAg_des_s(String ag_des_s) {
            this.ag_des_s = ag_des_s;
        }

        public String getAg_des_s() {
            return ag_des_s;
        }

        public void setCl_name(String cl_name) {
            this.cl_name = cl_name;
        }

        public String getCl_name() {
            return cl_name;
        }

        public void setCl_hint(String cl_hint) {
            this.cl_hint = cl_hint;
        }

        public String getCl_hint() {
            return cl_hint;
        }

        public void setCl_des_s(String cl_des_s) {
            this.cl_des_s = cl_des_s;
        }

        public String getCl_des_s() {
            return cl_des_s;
        }

        public void setCo_name(String co_name) {
            this.co_name = co_name;
        }

        public String getCo_name() {
            return co_name;
        }

        public void setCo_hint(String co_hint) {
            this.co_hint = co_hint;
        }

        public String getCo_hint() {
            return co_hint;
        }

        public void setCo_des_s(String co_des_s) {
            this.co_des_s = co_des_s;
        }

        public String getCo_des_s() {
            return co_des_s;
        }

        public void setCt_name(String ct_name) {
            this.ct_name = ct_name;
        }

        public String getCt_name() {
            return ct_name;
        }

        public void setCt_hint(String ct_hint) {
            this.ct_hint = ct_hint;
        }

        public String getCt_hint() {
            return ct_hint;
        }

        public void setCt_des_s(String ct_des_s) {
            this.ct_des_s = ct_des_s;
        }

        public String getCt_des_s() {
            return ct_des_s;
        }

        public void setDy_name(String dy_name) {
            this.dy_name = dy_name;
        }

        public String getDy_name() {
            return dy_name;
        }

        public void setDy_hint(String dy_hint) {
            this.dy_hint = dy_hint;
        }

        public String getDy_hint() {
            return dy_hint;
        }

        public void setDy_des_s(String dy_des_s) {
            this.dy_des_s = dy_des_s;
        }

        public String getDy_des_s() {
            return dy_des_s;
        }

        public void setFs_name(String fs_name) {
            this.fs_name = fs_name;
        }

        public String getFs_name() {
            return fs_name;
        }

        public void setFs_hint(String fs_hint) {
            this.fs_hint = fs_hint;
        }

        public String getFs_hint() {
            return fs_hint;
        }

        public void setFs_des_s(String fs_des_s) {
            this.fs_des_s = fs_des_s;
        }

        public String getFs_des_s() {
            return fs_des_s;
        }

        public void setGj_name(String gj_name) {
            this.gj_name = gj_name;
        }

        public String getGj_name() {
            return gj_name;
        }

        public void setGj_hint(String gj_hint) {
            this.gj_hint = gj_hint;
        }

        public String getGj_hint() {
            return gj_hint;
        }

        public void setGj_des_s(String gj_des_s) {
            this.gj_des_s = gj_des_s;
        }

        public String getGj_des_s() {
            return gj_des_s;
        }

        public void setGl_name(String gl_name) {
            this.gl_name = gl_name;
        }

        public String getGl_name() {
            return gl_name;
        }

        public void setGl_hint(String gl_hint) {
            this.gl_hint = gl_hint;
        }

        public String getGl_hint() {
            return gl_hint;
        }

        public void setGl_des_s(String gl_des_s) {
            this.gl_des_s = gl_des_s;
        }

        public String getGl_des_s() {
            return gl_des_s;
        }

        public void setGm_name(String gm_name) {
            this.gm_name = gm_name;
        }

        public String getGm_name() {
            return gm_name;
        }

        public void setGm_hint(String gm_hint) {
            this.gm_hint = gm_hint;
        }

        public String getGm_hint() {
            return gm_hint;
        }

        public void setGm_des_s(String gm_des_s) {
            this.gm_des_s = gm_des_s;
        }

        public String getGm_des_s() {
            return gm_des_s;
        }

        public void setGz_name(String gz_name) {
            this.gz_name = gz_name;
        }

        public String getGz_name() {
            return gz_name;
        }

        public void setGz_hint(String gz_hint) {
            this.gz_hint = gz_hint;
        }

        public String getGz_hint() {
            return gz_hint;
        }

        public void setGz_des_s(String gz_des_s) {
            this.gz_des_s = gz_des_s;
        }

        public String getGz_des_s() {
            return gz_des_s;
        }

        public void setHc_name(String hc_name) {
            this.hc_name = hc_name;
        }

        public String getHc_name() {
            return hc_name;
        }

        public void setHc_hint(String hc_hint) {
            this.hc_hint = hc_hint;
        }

        public String getHc_hint() {
            return hc_hint;
        }

        public void setHc_des_s(String hc_des_s) {
            this.hc_des_s = hc_des_s;
        }

        public String getHc_des_s() {
            return hc_des_s;
        }

        public void setJt_name(String jt_name) {
            this.jt_name = jt_name;
        }

        public String getJt_name() {
            return jt_name;
        }

        public void setJt_hint(String jt_hint) {
            this.jt_hint = jt_hint;
        }

        public String getJt_hint() {
            return jt_hint;
        }

        public void setJt_des_s(String jt_des_s) {
            this.jt_des_s = jt_des_s;
        }

        public String getJt_des_s() {
            return jt_des_s;
        }

        public void setLk_name(String lk_name) {
            this.lk_name = lk_name;
        }

        public String getLk_name() {
            return lk_name;
        }

        public void setLk_hint(String lk_hint) {
            this.lk_hint = lk_hint;
        }

        public String getLk_hint() {
            return lk_hint;
        }

        public void setLk_des_s(String lk_des_s) {
            this.lk_des_s = lk_des_s;
        }

        public String getLk_des_s() {
            return lk_des_s;
        }

        public void setLs_name(String ls_name) {
            this.ls_name = ls_name;
        }

        public String getLs_name() {
            return ls_name;
        }

        public void setLs_hint(String ls_hint) {
            this.ls_hint = ls_hint;
        }

        public String getLs_hint() {
            return ls_hint;
        }

        public void setLs_des_s(String ls_des_s) {
            this.ls_des_s = ls_des_s;
        }

        public String getLs_des_s() {
            return ls_des_s;
        }

        public void setMf_name(String mf_name) {
            this.mf_name = mf_name;
        }

        public String getMf_name() {
            return mf_name;
        }

        public void setMf_hint(String mf_hint) {
            this.mf_hint = mf_hint;
        }

        public String getMf_hint() {
            return mf_hint;
        }

        public void setMf_des_s(String mf_des_s) {
            this.mf_des_s = mf_des_s;
        }

        public String getMf_des_s() {
            return mf_des_s;
        }

        public void setNl_name(String nl_name) {
            this.nl_name = nl_name;
        }

        public String getNl_name() {
            return nl_name;
        }

        public void setNl_hint(String nl_hint) {
            this.nl_hint = nl_hint;
        }

        public String getNl_hint() {
            return nl_hint;
        }

        public void setNl_des_s(String nl_des_s) {
            this.nl_des_s = nl_des_s;
        }

        public String getNl_des_s() {
            return nl_des_s;
        }

        public void setPj_name(String pj_name) {
            this.pj_name = pj_name;
        }

        public String getPj_name() {
            return pj_name;
        }

        public void setPj_hint(String pj_hint) {
            this.pj_hint = pj_hint;
        }

        public String getPj_hint() {
            return pj_hint;
        }

        public void setPj_des_s(String pj_des_s) {
            this.pj_des_s = pj_des_s;
        }

        public String getPj_des_s() {
            return pj_des_s;
        }

        public void setPk_name(String pk_name) {
            this.pk_name = pk_name;
        }

        public String getPk_name() {
            return pk_name;
        }

        public void setPk_hint(String pk_hint) {
            this.pk_hint = pk_hint;
        }

        public String getPk_hint() {
            return pk_hint;
        }

        public void setPk_des_s(String pk_des_s) {
            this.pk_des_s = pk_des_s;
        }

        public String getPk_des_s() {
            return pk_des_s;
        }

        public void setPl_name(String pl_name) {
            this.pl_name = pl_name;
        }

        public String getPl_name() {
            return pl_name;
        }

        public void setPl_hint(String pl_hint) {
            this.pl_hint = pl_hint;
        }

        public String getPl_hint() {
            return pl_hint;
        }

        public void setPl_des_s(String pl_des_s) {
            this.pl_des_s = pl_des_s;
        }

        public String getPl_des_s() {
            return pl_des_s;
        }

        public void setPp_name(String pp_name) {
            this.pp_name = pp_name;
        }

        public String getPp_name() {
            return pp_name;
        }

        public void setPp_hint(String pp_hint) {
            this.pp_hint = pp_hint;
        }

        public String getPp_hint() {
            return pp_hint;
        }

        public void setPp_des_s(String pp_des_s) {
            this.pp_des_s = pp_des_s;
        }

        public String getPp_des_s() {
            return pp_des_s;
        }

        public void setTr_name(String tr_name) {
            this.tr_name = tr_name;
        }

        public String getTr_name() {
            return tr_name;
        }

        public void setTr_hint(String tr_hint) {
            this.tr_hint = tr_hint;
        }

        public String getTr_hint() {
            return tr_hint;
        }

        public void setTr_des_s(String tr_des_s) {
            this.tr_des_s = tr_des_s;
        }

        public String getTr_des_s() {
            return tr_des_s;
        }

        public void setUv_name(String uv_name) {
            this.uv_name = uv_name;
        }

        public String getUv_name() {
            return uv_name;
        }

        public void setUv_hint(String uv_hint) {
            this.uv_hint = uv_hint;
        }

        public String getUv_hint() {
            return uv_hint;
        }

        public void setUv_des_s(String uv_des_s) {
            this.uv_des_s = uv_des_s;
        }

        public String getUv_des_s() {
            return uv_des_s;
        }

        public void setWc_name(String wc_name) {
            this.wc_name = wc_name;
        }

        public String getWc_name() {
            return wc_name;
        }

        public void setWc_hint(String wc_hint) {
            this.wc_hint = wc_hint;
        }

        public String getWc_hint() {
            return wc_hint;
        }

        public void setWc_des_s(String wc_des_s) {
            this.wc_des_s = wc_des_s;
        }

        public String getWc_des_s() {
            return wc_des_s;
        }

        public void setXc_name(String xc_name) {
            this.xc_name = xc_name;
        }

        public String getXc_name() {
            return xc_name;
        }

        public void setXc_hint(String xc_hint) {
            this.xc_hint = xc_hint;
        }

        public String getXc_hint() {
            return xc_hint;
        }

        public void setXc_des_s(String xc_des_s) {
            this.xc_des_s = xc_des_s;
        }

        public String getXc_des_s() {
            return xc_des_s;
        }

        public void setXq_name(String xq_name) {
            this.xq_name = xq_name;
        }

        public String getXq_name() {
            return xq_name;
        }

        public void setXq_hint(String xq_hint) {
            this.xq_hint = xq_hint;
        }

        public String getXq_hint() {
            return xq_hint;
        }

        public void setXq_des_s(String xq_des_s) {
            this.xq_des_s = xq_des_s;
        }

        public String getXq_des_s() {
            return xq_des_s;
        }

        public void setYd_name(String yd_name) {
            this.yd_name = yd_name;
        }

        public String getYd_name() {
            return yd_name;
        }

        public void setYd_hint(String yd_hint) {
            this.yd_hint = yd_hint;
        }

        public String getYd_hint() {
            return yd_hint;
        }

        public void setYd_des_s(String yd_des_s) {
            this.yd_des_s = yd_des_s;
        }

        public String getYd_des_s() {
            return yd_des_s;
        }

        public void setYh_name(String yh_name) {
            this.yh_name = yh_name;
        }

        public String getYh_name() {
            return yh_name;
        }

        public void setYh_hint(String yh_hint) {
            this.yh_hint = yh_hint;
        }

        public String getYh_hint() {
            return yh_hint;
        }

        public void setYh_des_s(String yh_des_s) {
            this.yh_des_s = yh_des_s;
        }

        public String getYh_des_s() {
            return yh_des_s;
        }

        public void setYs_name(String ys_name) {
            this.ys_name = ys_name;
        }

        public String getYs_name() {
            return ys_name;
        }

        public void setYs_hint(String ys_hint) {
            this.ys_hint = ys_hint;
        }

        public String getYs_hint() {
            return ys_hint;
        }

        public void setYs_des_s(String ys_des_s) {
            this.ys_des_s = ys_des_s;
        }

        public String getYs_des_s() {
            return ys_des_s;
        }

        public void setZs_name(String zs_name) {
            this.zs_name = zs_name;
        }

        public String getZs_name() {
            return zs_name;
        }

        public void setZs_hint(String zs_hint) {
            this.zs_hint = zs_hint;
        }

        public String getZs_hint() {
            return zs_hint;
        }

        public void setZs_des_s(String zs_des_s) {
            this.zs_des_s = zs_des_s;
        }

        public String getZs_des_s() {
            return zs_des_s;
        }

    }
}
