package com.one.tomato.entity.event;

import com.one.tomato.entity.PostList;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PostRefreshEvent.kt */
/* loaded from: classes3.dex */
public final class PostRefreshEvent {
    private final int category;
    private final int channelId;
    private final PostList data;

    public static /* synthetic */ PostRefreshEvent copy$default(PostRefreshEvent postRefreshEvent, PostList postList, int i, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            postList = postRefreshEvent.data;
        }
        if ((i3 & 2) != 0) {
            i = postRefreshEvent.category;
        }
        if ((i3 & 4) != 0) {
            i2 = postRefreshEvent.channelId;
        }
        return postRefreshEvent.copy(postList, i, i2);
    }

    public final PostList component1() {
        return this.data;
    }

    public final int component2() {
        return this.category;
    }

    public final int component3() {
        return this.channelId;
    }

    public final PostRefreshEvent copy(PostList data, int i, int i2) {
        Intrinsics.checkParameterIsNotNull(data, "data");
        return new PostRefreshEvent(data, i, i2);
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            if (obj instanceof PostRefreshEvent) {
                PostRefreshEvent postRefreshEvent = (PostRefreshEvent) obj;
                if (Intrinsics.areEqual(this.data, postRefreshEvent.data)) {
                    if (this.category == postRefreshEvent.category) {
                        if (this.channelId == postRefreshEvent.channelId) {
                        }
                    }
                }
            }
            return false;
        }
        return true;
    }

    public int hashCode() {
        PostList postList = this.data;
        return ((((postList != null ? postList.hashCode() : 0) * 31) + this.category) * 31) + this.channelId;
    }

    public String toString() {
        return "PostRefreshEvent(data=" + this.data + ", category=" + this.category + ", channelId=" + this.channelId + ")";
    }

    public PostRefreshEvent(PostList data, int i, int i2) {
        Intrinsics.checkParameterIsNotNull(data, "data");
        this.data = data;
        this.category = i;
        this.channelId = i2;
    }

    public final int getCategory() {
        return this.category;
    }

    public final int getChannelId() {
        return this.channelId;
    }

    public final PostList getData() {
        return this.data;
    }
}
