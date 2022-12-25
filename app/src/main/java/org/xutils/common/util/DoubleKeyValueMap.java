package org.xutils.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes4.dex */
public class DoubleKeyValueMap<K1, K2, V> {
    private final ConcurrentHashMap<K1, ConcurrentHashMap<K2, V>> k1_k2V_map = new ConcurrentHashMap<>();

    public void put(K1 k1, K2 k2, V v) {
        if (k1 == null || k2 == null || v == null) {
            return;
        }
        if (this.k1_k2V_map.containsKey(k1)) {
            ConcurrentHashMap<K2, V> concurrentHashMap = this.k1_k2V_map.get(k1);
            if (concurrentHashMap != null) {
                concurrentHashMap.put(k2, v);
                return;
            }
            ConcurrentHashMap<K2, V> concurrentHashMap2 = new ConcurrentHashMap<>();
            concurrentHashMap2.put(k2, v);
            this.k1_k2V_map.put(k1, concurrentHashMap2);
            return;
        }
        ConcurrentHashMap<K2, V> concurrentHashMap3 = new ConcurrentHashMap<>();
        concurrentHashMap3.put(k2, v);
        this.k1_k2V_map.put(k1, concurrentHashMap3);
    }

    public Set<K1> getFirstKeys() {
        return this.k1_k2V_map.keySet();
    }

    public ConcurrentHashMap<K2, V> get(K1 k1) {
        return this.k1_k2V_map.get(k1);
    }

    public V get(K1 k1, K2 k2) {
        ConcurrentHashMap<K2, V> concurrentHashMap = this.k1_k2V_map.get(k1);
        if (concurrentHashMap == null) {
            return null;
        }
        return concurrentHashMap.get(k2);
    }

    public Collection<V> getAllValues(K1 k1) {
        ConcurrentHashMap<K2, V> concurrentHashMap = this.k1_k2V_map.get(k1);
        if (concurrentHashMap == null) {
            return null;
        }
        return concurrentHashMap.values();
    }

    public Collection<V> getAllValues() {
        Set<K1> keySet = this.k1_k2V_map.keySet();
        if (keySet != null) {
            ArrayList arrayList = new ArrayList();
            for (K1 k1 : keySet) {
                Collection<V> values = this.k1_k2V_map.get(k1).values();
                if (values != null) {
                    arrayList.addAll(values);
                }
            }
            return arrayList;
        }
        return null;
    }

    public boolean containsKey(K1 k1, K2 k2) {
        if (this.k1_k2V_map.containsKey(k1)) {
            return this.k1_k2V_map.get(k1).containsKey(k2);
        }
        return false;
    }

    public boolean containsKey(K1 k1) {
        return this.k1_k2V_map.containsKey(k1);
    }

    public int size() {
        int i = 0;
        if (this.k1_k2V_map.size() == 0) {
            return 0;
        }
        for (ConcurrentHashMap<K2, V> concurrentHashMap : this.k1_k2V_map.values()) {
            i += concurrentHashMap.size();
        }
        return i;
    }

    public void remove(K1 k1) {
        this.k1_k2V_map.remove(k1);
    }

    public void remove(K1 k1, K2 k2) {
        ConcurrentHashMap<K2, V> concurrentHashMap = this.k1_k2V_map.get(k1);
        if (concurrentHashMap != null) {
            concurrentHashMap.remove(k2);
        }
        if (concurrentHashMap == null || concurrentHashMap.isEmpty()) {
            this.k1_k2V_map.remove(k1);
        }
    }

    public void clear() {
        if (this.k1_k2V_map.size() > 0) {
            for (ConcurrentHashMap<K2, V> concurrentHashMap : this.k1_k2V_map.values()) {
                concurrentHashMap.clear();
            }
            this.k1_k2V_map.clear();
        }
    }
}
