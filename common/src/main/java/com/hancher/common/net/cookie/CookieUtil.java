package com.hancher.common.net.cookie;

import com.hancher.common.utils.android.LogUtil;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

/**
 * 作者 : Hancher ytu_shaoweijie@163.com <br/>
 * 时间 : 2021/3/4 11:27 <br/>
 * 描述 : cookie管理工具类
 */
public class CookieUtil {
//    /**
//     * 获取指定URL对应的cookie
//     *
//     * @param baseUrl
//     * @param url
//     * @return
//     */
//    public static List<Cookie> cookies(String baseUrl, String url, CookieJarImpl cookieJar) {
//        //一般手动取出cookie的目的只是交给 webview 等等，非必要情况不要自己操作
//        CookieStore cookieStore = cookieJar.getCookieStore();
//        HttpUrl httpUrl = HttpUrl.parse(baseUrl + url);
//        List<Cookie> cookies = cookieStore.getCookie(httpUrl);
//        LogUtil.e(httpUrl.host() + "对应的cookie如下：" + cookies.toString());
//        return cookies;
//    }
//
//    /**
//     * 获取所有的cookie
//     *
//     * @return
//     */
//    public static List<Cookie> cookieList(CookieJarImpl cookieJar) {
//        CookieStore cookieStore = cookieJar.getCookieStore();
//        List<Cookie> allCookie = cookieStore.getAllCookie();
//        LogUtil.e("所有cookie如下: ", allCookie.toString());
//        return allCookie;
//    }
//
//    public static String getCookie(CookieJarImpl cookieJar) {
//        CookieStore cookieStore = cookieJar.getCookieStore();
//        List<Cookie> allCookie = cookieStore.getAllCookie();
//        for (int i = 0; i < allCookie.size(); i++) {
//            return allCookie.get(i).toString();
//        }
//        return null;
//    }
//
//    /**
//     * 删除cookie（这里是全部删除，也可指定的地址删除）
//     */
//    public static void removeCookie(CookieJarImpl cookieJar) {
//        CookieStore cookieStore = cookieJar.getCookieStore();
//        cookieStore.removeAllCookie();
//
//    }

    /**
     * 获取对应链接的cookie
     *
     * @param url
     * @return
     */
    public static List<Cookie> gotCookies(String url) {
        CookieStore cookieStore = CookieJarImpl.getInstance().getCookieStore();
        HttpUrl httpUrl = HttpUrl.parse(url);
        List<Cookie> cookies = cookieStore.getCookie(httpUrl);
        LogUtil.e(httpUrl.host() + "对应的cookie如下：" + cookies.toString());
        return cookies;
    }
}