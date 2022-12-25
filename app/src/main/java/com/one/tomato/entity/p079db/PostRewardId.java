package com.one.tomato.entity.p079db;

import org.litepal.crud.LitePalSupport;

/* renamed from: com.one.tomato.entity.db.PostRewardId */
/* loaded from: classes3.dex */
public class PostRewardId extends LitePalSupport {
    private int membeId;
    private int postId;

    public int getPostId() {
        return this.postId;
    }

    public void setPostId(int i) {
        this.postId = i;
    }

    public int getMembeId() {
        return this.membeId;
    }

    public void setMembeId(int i) {
        this.membeId = i;
    }
}
