package com.one.tomato.entity.event;

import com.one.tomato.entity.PostList;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public class PostDetailListDataEvent {
    private int category;
    private int channelId;
    private boolean isShow;
    private ArrayList<PostList> list;
    private int pageNo;
    private PostList postList;
    private int postion;

    public int getChannelId() {
        return this.channelId;
    }

    public void setChannelId(int i) {
        this.channelId = i;
    }

    public boolean isShow() {
        return this.isShow;
    }

    public void setShow(boolean z) {
        this.isShow = z;
    }

    public PostList getPostList() {
        return this.postList;
    }

    public void setPostList(PostList postList) {
        this.postList = postList;
    }

    public ArrayList<PostList> getList() {
        return this.list;
    }

    public void setList(ArrayList<PostList> arrayList) {
        this.list = arrayList;
    }

    public int getPostion() {
        return this.postion;
    }

    public void setPostion(int i) {
        this.postion = i;
    }

    public int getCategory() {
        return this.category;
    }

    public void setCategory(int i) {
        this.category = i;
    }

    public int getPageNo() {
        return this.pageNo;
    }

    public void setPageNo(int i) {
        this.pageNo = i;
    }
}
