package com.one.tomato.entity;

import android.support.annotation.NonNull;

/* loaded from: classes3.dex */
public class DomainUrl {
    public String url;
    public int weight;

    public DomainUrl(String str, int i) {
        this.url = str;
        this.weight = i;
    }

    @NonNull
    public String toString() {
        return "url = " + this.url + ", weight = " + this.weight;
    }
}
