package com.hancher.common.net.cookie;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * @author : Hancher ytu_shaoweijie@163.com
 * @time : 2021/2/3 15:57
 * @describe : cookie持久化
 */
public class CookieJarImpl implements CookieJar {

    private CookieStore cookieStore = new SPCookieStore();

    private CookieJarImpl() {
    }

    private static CookieJarImpl instance = new CookieJarImpl();

    public static CookieJarImpl getInstance() {
        return instance;
    }

    @Override
    public synchronized void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        cookieStore.saveCookie(url, cookies);
    }

    @Override
    public synchronized List<Cookie> loadForRequest(HttpUrl url) {
        return cookieStore.loadCookie(url);
    }

    public CookieStore getCookieStore() {
        return cookieStore;
    }
}
