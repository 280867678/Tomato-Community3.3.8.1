package com.gen.p059mh.webapps.listener;

import java.util.Map;

/* renamed from: com.gen.mh.webapps.listener.AppController */
/* loaded from: classes2.dex */
public interface AppController {
    void checkTabBar(String str);

    void handlePageData(String str, Object obj);

    void onCallBack(String str, String str2, Object obj);

    void renderPage(Map<String, String> map);

    void selectComponent(String str);

    void setComponentData(String str, String str2);

    void setData(String str);

    void setTabBarBadge(boolean z, int i, String str);

    void showTabBar(boolean z, boolean z2);

    void showTabBarRedDot(boolean z, int i);
}
