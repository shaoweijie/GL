package com.hancher.common.utils.java;

import com.hancher.common.utils.android.LogUtil;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/3/13 10:48 <br/>
 * 描述 : 获取网络时间
 */
public class NetTimeUtil {
    public static String[] webUrlList = new String[]{
            "http://www.bjtime.cn",
            "http://www.baidu.com",
            "http://www.taobao.com",
            "http://www.ntsc.ac.cn",
            "http://www.360.cn",
            "http://www.beijing-time.org",
    };

    /**
     * 获取指定网站的日期时间
     *
     * @return 返回日期long类型
     * @author HWJ
     * @date 2017年7月7日
     */
    public static long getWebsiteDatetimeActiveLong() {
        for (int i = 0; i < webUrlList.length; i++) {
            try {
                URL url = new URL(webUrlList[i]);// 取得资源对象
                URLConnection uc = url.openConnection();// 生成连接对象
                uc.connect();// 发出连接
                long ld = uc.getDate();// 读取网站日期时间
                return ld;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        LogUtil.w("获取网络位置失败");
        return 0;
    }
}
