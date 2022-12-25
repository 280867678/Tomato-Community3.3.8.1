package com.tomatolive.library.model;

import java.util.List;

/* loaded from: classes3.dex */
public class IndexRankEntity {
    private List<String> avatars;
    private String type;

    public String getType() {
        return this.type;
    }

    public void setType(String str) {
        this.type = str;
    }

    public List<String> getAvatars() {
        return this.avatars;
    }

    public void setAvatars(List<String> list) {
        this.avatars = list;
    }

    public String toString() {
        return "IndexRankEntity{type='" + this.type + "'}";
    }
}
