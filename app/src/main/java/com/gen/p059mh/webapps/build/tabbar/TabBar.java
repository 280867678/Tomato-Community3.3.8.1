package com.gen.p059mh.webapps.build.tabbar;

import android.view.ViewGroup;
import com.gen.p059mh.webapps.listener.ITabBarOperation;
import java.util.Map;

/* renamed from: com.gen.mh.webapps.build.tabbar.TabBar */
/* loaded from: classes2.dex */
public interface TabBar {
    void init(ViewGroup viewGroup, Map map);

    void release();

    void setOperation(ITabBarOperation iTabBarOperation);

    void setTabBarBadge(boolean z, int i, String str);

    void showTabBarRedDot(boolean z, int i);
}
