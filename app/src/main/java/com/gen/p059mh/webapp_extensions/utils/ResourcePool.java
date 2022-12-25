package com.gen.p059mh.webapp_extensions.utils;

import java.util.ArrayList;
import java.util.Iterator;

/* renamed from: com.gen.mh.webapp_extensions.utils.ResourcePool */
/* loaded from: classes2.dex */
public class ResourcePool {
    private static ArrayList<ResourcePool> resourcePools = new ArrayList<>();
    private ArrayList<OnReleaseListener> listeners = new ArrayList<>();

    /* renamed from: com.gen.mh.webapp_extensions.utils.ResourcePool$OnReleaseListener */
    /* loaded from: classes2.dex */
    public interface OnReleaseListener {
        void onRelease();
    }

    static {
        resourcePools.add(new ResourcePool());
    }

    private static void push(ResourcePool resourcePool) {
        if (resourcePool == null || resourcePools.contains(resourcePool)) {
            return;
        }
        resourcePools.add(resourcePool);
    }

    public static ResourcePool current() {
        ArrayList<ResourcePool> arrayList = resourcePools;
        return arrayList.get(arrayList.size() - 1);
    }

    static void pop(ResourcePool resourcePool) {
        if (resourcePool == null || !resourcePools.contains(resourcePool)) {
            return;
        }
        resourcePools.remove(resourcePool);
    }

    public ResourcePool() {
        push(this);
    }

    public void release() {
        Iterator<OnReleaseListener> it2 = this.listeners.iterator();
        while (it2.hasNext()) {
            it2.next().onRelease();
        }
        this.listeners.clear();
        pop(this);
    }

    public void addRelease(OnReleaseListener onReleaseListener) {
        this.listeners.add(onReleaseListener);
    }
}
