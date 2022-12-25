package com.p076mh.webappStart.android_plugin_impl.plugins.plugin;

import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.BasePluginImpl;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.mh.webappStart.android_plugin_impl.plugins.plugin.CachedImplsMapManager */
/* loaded from: classes3.dex */
public class CachedImplsMapManager {
    private static final CachedImplsMapManager ourInstance = new CachedImplsMapManager();
    protected Map<String, BasePluginImpl> cacheImplsMap;

    public static CachedImplsMapManager getInstance() {
        return ourInstance;
    }

    private CachedImplsMapManager() {
        this.cacheImplsMap = null;
        this.cacheImplsMap = new HashMap();
    }

    public Map<String, BasePluginImpl> getImplsContainer() {
        return this.cacheImplsMap;
    }
}
