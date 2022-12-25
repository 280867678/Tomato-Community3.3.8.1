package com.one.tomato.entity;

import java.util.ArrayList;

/* loaded from: classes3.dex */
public class SerializePostListBean {
    private ArrayList<PostList> data;
    private String title;

    public SerializePostListBean(String str, ArrayList<PostList> arrayList) {
        this.title = str;
        this.data = arrayList;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public ArrayList<PostList> getData() {
        return this.data;
    }

    public void setData(ArrayList<PostList> arrayList) {
        this.data = arrayList;
    }
}
