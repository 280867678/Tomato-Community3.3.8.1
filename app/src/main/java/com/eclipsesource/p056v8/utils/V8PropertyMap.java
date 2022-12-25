package com.eclipsesource.p056v8.utils;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

/* renamed from: com.eclipsesource.v8.utils.V8PropertyMap */
/* loaded from: classes2.dex */
class V8PropertyMap<V> implements Map<String, V> {
    private Hashtable<String, V> map = new Hashtable<>();
    private Set<String> nulls = new HashSet();

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.Map
    public /* bridge */ /* synthetic */ Object put(String str, Object obj) {
        return put2(str, (String) obj);
    }

    @Override // java.util.Map
    public int size() {
        return this.map.size() + this.nulls.size();
    }

    @Override // java.util.Map
    public boolean isEmpty() {
        return this.map.isEmpty() && this.nulls.isEmpty();
    }

    @Override // java.util.Map
    public boolean containsKey(Object obj) {
        return this.map.containsKey(obj) || this.nulls.contains(obj);
    }

    @Override // java.util.Map
    public boolean containsValue(Object obj) {
        if (obj != null || this.nulls.isEmpty()) {
            if (obj != null) {
                return this.map.containsValue(obj);
            }
            return false;
        }
        return true;
    }

    @Override // java.util.Map
    public V get(Object obj) {
        if (this.nulls.contains(obj)) {
            return null;
        }
        return this.map.get(obj);
    }

    /* renamed from: put  reason: avoid collision after fix types in other method */
    public V put2(String str, V v) {
        if (v == null) {
            if (this.map.containsKey(str)) {
                this.map.remove(str);
            }
            this.nulls.add(str);
            return null;
        }
        if (this.nulls.contains(str)) {
            this.nulls.remove(str);
        }
        return this.map.put(str, v);
    }

    @Override // java.util.Map
    public V remove(Object obj) {
        if (this.nulls.contains(obj)) {
            this.nulls.remove(obj);
            return null;
        }
        return this.map.remove(obj);
    }

    @Override // java.util.Map
    public void putAll(Map<? extends String, ? extends V> map) {
        for (Map.Entry<? extends String, ? extends V> entry : map.entrySet()) {
            put2(entry.getKey(), (String) entry.getValue());
        }
    }

    @Override // java.util.Map
    public void clear() {
        this.map.clear();
        this.nulls.clear();
    }

    @Override // java.util.Map
    public Set<String> keySet() {
        HashSet hashSet = new HashSet(this.map.keySet());
        hashSet.addAll(this.nulls);
        return hashSet;
    }

    @Override // java.util.Map
    public Collection<V> values() {
        ArrayList arrayList = new ArrayList(this.map.values());
        for (int i = 0; i < this.nulls.size(); i++) {
            arrayList.add(null);
        }
        return arrayList;
    }

    @Override // java.util.Map
    public Set<Map.Entry<String, V>> entrySet() {
        HashSet hashSet = new HashSet(this.map.entrySet());
        for (String str : this.nulls) {
            hashSet.add(new AbstractMap.SimpleEntry(str, null));
        }
        return hashSet;
    }
}
