package com.youdao.sdk.ydtranslate;

import java.io.Serializable;
import java.util.List;

/* loaded from: classes4.dex */
public class WebExplain implements Serializable {
    private String key;
    private List<String> means;

    public String getKey() {
        return this.key;
    }

    public void setKey(String str) {
        this.key = str;
    }

    public List<String> getMeans() {
        return this.means;
    }

    public void setMeans(List<String> list) {
        this.means = list;
    }
}
