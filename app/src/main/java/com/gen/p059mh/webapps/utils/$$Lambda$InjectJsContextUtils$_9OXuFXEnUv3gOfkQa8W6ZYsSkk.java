package com.gen.p059mh.webapps.utils;

import java.io.File;
import java.util.Comparator;

/* compiled from: lambda */
/* renamed from: com.gen.mh.webapps.utils.-$$Lambda$InjectJsContextUtils$_9OXuFXEnUv3gOfkQa8W6ZYsSkk  reason: invalid class name */
/* loaded from: classes2.dex */
public final /* synthetic */ class $$Lambda$InjectJsContextUtils$_9OXuFXEnUv3gOfkQa8W6ZYsSkk implements Comparator {
    public static final /* synthetic */ $$Lambda$InjectJsContextUtils$_9OXuFXEnUv3gOfkQa8W6ZYsSkk INSTANCE = new $$Lambda$InjectJsContextUtils$_9OXuFXEnUv3gOfkQa8W6ZYsSkk();

    private /* synthetic */ $$Lambda$InjectJsContextUtils$_9OXuFXEnUv3gOfkQa8W6ZYsSkk() {
    }

    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        int compareTo;
        compareTo = ((File) obj).getName().compareTo(((File) obj2).getName());
        return compareTo;
    }
}
