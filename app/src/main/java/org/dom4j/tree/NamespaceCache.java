package org.dom4j.tree;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.dom4j.Namespace;

/* loaded from: classes4.dex */
public class NamespaceCache {
    protected static Map<String, Map<String, WeakReference<Namespace>>> cache = new ConcurrentHashMap();
    protected static Map<String, WeakReference<Namespace>> noPrefixCache = new ConcurrentHashMap();

    public Namespace get(String str, String str2) {
        Map<String, WeakReference<Namespace>> uRICache = getURICache(str2);
        WeakReference<Namespace> weakReference = uRICache.get(str);
        Namespace namespace = weakReference != null ? weakReference.get() : null;
        if (namespace == null) {
            synchronized (uRICache) {
                WeakReference<Namespace> weakReference2 = uRICache.get(str);
                if (weakReference2 != null) {
                    namespace = weakReference2.get();
                }
                if (namespace == null) {
                    Namespace createNamespace = createNamespace(str, str2);
                    uRICache.put(str, new WeakReference<>(createNamespace));
                    namespace = createNamespace;
                }
            }
        }
        return namespace;
    }

    public Namespace get(String str) {
        WeakReference<Namespace> weakReference = noPrefixCache.get(str);
        Namespace namespace = weakReference != null ? weakReference.get() : null;
        if (namespace == null) {
            synchronized (noPrefixCache) {
                WeakReference<Namespace> weakReference2 = noPrefixCache.get(str);
                if (weakReference2 != null) {
                    namespace = weakReference2.get();
                }
                if (namespace == null) {
                    namespace = createNamespace("", str);
                    noPrefixCache.put(str, new WeakReference<>(namespace));
                }
            }
        }
        return namespace;
    }

    protected Map<String, WeakReference<Namespace>> getURICache(String str) {
        Map<String, WeakReference<Namespace>> map = cache.get(str);
        if (map == null) {
            synchronized (cache) {
                map = cache.get(str);
                if (map == null) {
                    map = new ConcurrentHashMap<>();
                    cache.put(str, map);
                }
            }
        }
        return map;
    }

    protected Namespace createNamespace(String str, String str2) {
        return new Namespace(str, str2);
    }
}
