package com.gen.p059mh.webapps.listener;

import java.util.List;

/* renamed from: com.gen.mh.webapps.listener.WebAppCallBack */
/* loaded from: classes2.dex */
public interface WebAppCallBack {
    void emit(String str, List<String> list);

    void navigateBack(int i);

    void navigateTo(String str);

    void onRefresh(boolean z);

    void reLaunch(String str);

    void redirectTo(String str);

    void scroll(int i, int i2);
}
