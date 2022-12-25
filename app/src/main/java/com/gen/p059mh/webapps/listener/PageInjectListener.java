package com.gen.p059mh.webapps.listener;

import com.gen.p059mh.webapps.utils.ResourcesLoader;

/* renamed from: com.gen.mh.webapps.listener.PageInjectListener */
/* loaded from: classes2.dex */
public interface PageInjectListener {

    /* renamed from: com.gen.mh.webapps.listener.PageInjectListener$-CC  reason: invalid class name */
    /* loaded from: classes2.dex */
    public final /* synthetic */ class CC {
        public static boolean $default$aem(PageInjectListener pageInjectListener) {
            return false;
        }
    }

    boolean aem();

    String provideDefaultPath();

    ResourcesLoader.ResourceType provideResourceType();
}
