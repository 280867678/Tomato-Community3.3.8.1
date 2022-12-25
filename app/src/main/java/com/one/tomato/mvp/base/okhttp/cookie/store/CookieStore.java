package com.one.tomato.mvp.base.okhttp.cookie.store;

import java.util.List;
import okhttp3.Cookie;
import okhttp3.HttpUrl;

/* loaded from: classes3.dex */
public interface CookieStore {
    List<Cookie> loadCookie(HttpUrl httpUrl);

    void saveCookie(HttpUrl httpUrl, List<Cookie> list);
}
