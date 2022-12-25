package com.gen.p059mh.webapps.listener;

import java.util.HashMap;

/* renamed from: com.gen.mh.webapps.listener.IWebDataOperation */
/* loaded from: classes2.dex */
public interface IWebDataOperation {
    boolean isCloseButtonHidden();

    boolean isLocalDefaults();

    void setAppID(String str);

    void setCloseButtonHidden(boolean z);

    void setInitParams(HashMap<String, Object> hashMap);

    void setLocalDefaults(boolean z);
}
