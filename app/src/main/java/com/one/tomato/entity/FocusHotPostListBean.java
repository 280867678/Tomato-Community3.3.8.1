package com.one.tomato.entity;

import java.util.ArrayList;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: FocusHotPostListBean.kt */
/* loaded from: classes3.dex */
public final class FocusHotPostListBean {
    private ArrayList<PostList> data;
    private ArrayList<PostList> hotList;
    private int isFollowFlag;

    public FocusHotPostListBean(ArrayList<PostList> data, ArrayList<PostList> hotList, int i) {
        Intrinsics.checkParameterIsNotNull(data, "data");
        Intrinsics.checkParameterIsNotNull(hotList, "hotList");
        this.data = data;
        this.hotList = hotList;
        this.isFollowFlag = i;
    }

    public final ArrayList<PostList> getData() {
        return this.data;
    }

    public final ArrayList<PostList> getHotList() {
        return this.hotList;
    }

    public final int isFollowFlag() {
        return this.isFollowFlag;
    }

    public final void setData(ArrayList<PostList> arrayList) {
        Intrinsics.checkParameterIsNotNull(arrayList, "<set-?>");
        this.data = arrayList;
    }

    public final void setFollowFlag(int i) {
        this.isFollowFlag = i;
    }

    public final void setHotList(ArrayList<PostList> arrayList) {
        Intrinsics.checkParameterIsNotNull(arrayList, "<set-?>");
        this.hotList = arrayList;
    }
}
