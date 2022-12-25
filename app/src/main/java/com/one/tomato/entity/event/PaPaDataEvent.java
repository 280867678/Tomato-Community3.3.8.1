package com.one.tomato.entity.event;

import com.one.tomato.entity.PostList;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public class PaPaDataEvent {
    private ArrayList<PostList> list;

    public void setList(ArrayList<PostList> arrayList) {
        this.list = arrayList;
    }

    public ArrayList<PostList> getList() {
        return this.list;
    }
}
