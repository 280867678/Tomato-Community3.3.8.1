package com.gen.p059mh.webapp_extensions.views.camera.smartCamera;

import android.support.p002v4.util.ArrayMap;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/* renamed from: com.gen.mh.webapp_extensions.views.camera.smartCamera.SizeMap */
/* loaded from: classes2.dex */
public class SizeMap {
    private final ArrayMap<AspectRatio, SortedSet<Size>> mRatios = new ArrayMap<>();

    public boolean add(Size size) {
        for (AspectRatio aspectRatio : this.mRatios.keySet()) {
            if (aspectRatio.matches(size)) {
                SortedSet<Size> sortedSet = this.mRatios.get(aspectRatio);
                if (sortedSet.contains(size)) {
                    return false;
                }
                sortedSet.add(size);
                return true;
            }
        }
        TreeSet treeSet = new TreeSet();
        treeSet.add(size);
        this.mRatios.put(AspectRatio.m4118of(size.getWidth(), size.getHeight()), treeSet);
        return true;
    }

    public void remove(AspectRatio aspectRatio) {
        this.mRatios.remove(aspectRatio);
    }

    public Set<AspectRatio> ratios() {
        return this.mRatios.keySet();
    }

    public SortedSet<Size> sizes(AspectRatio aspectRatio) {
        return this.mRatios.get(aspectRatio);
    }

    public void clear() {
        this.mRatios.clear();
    }
}
