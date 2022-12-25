package com.gen.p059mh.webapp_extensions.views.player.custom;

/* renamed from: com.gen.mh.webapp_extensions.views.player.custom.ResourceEntity */
/* loaded from: classes2.dex */
public class ResourceEntity {
    private boolean callback;
    private String name;
    private int resolution;
    private long size;
    private String url;

    public String getName() {
        return this.name;
    }

    public String getUrl() {
        return this.url;
    }

    public long getSize() {
        return this.size;
    }

    public boolean isCallback() {
        return this.callback;
    }

    public ResourceEntity(String str, String str2, int i, boolean z) {
        this.name = str;
        this.url = str2;
        this.resolution = i;
        this.callback = z;
    }

    public int getResolution() {
        return this.resolution;
    }
}
