package com.gen.p059mh.webapps.modules;

import java.io.Serializable;
import java.util.LinkedHashMap;

/* renamed from: com.gen.mh.webapps.modules.StorageLinkedHashMap */
/* loaded from: classes2.dex */
public class StorageLinkedHashMap<K, V> extends LinkedHashMap<K, V> implements Serializable {
    public StorageLinkedHashMap(boolean z) {
        super(16, 0.75f, z);
    }
}
