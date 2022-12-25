package com.one.tomato.entity.event;

/* compiled from: PostPayEvent.kt */
/* loaded from: classes3.dex */
public final class PostPayEvent {
    private final int artcId;
    private final boolean isPay;

    public static /* synthetic */ PostPayEvent copy$default(PostPayEvent postPayEvent, boolean z, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            z = postPayEvent.isPay;
        }
        if ((i2 & 2) != 0) {
            i = postPayEvent.artcId;
        }
        return postPayEvent.copy(z, i);
    }

    public final boolean component1() {
        return this.isPay;
    }

    public final int component2() {
        return this.artcId;
    }

    public final PostPayEvent copy(boolean z, int i) {
        return new PostPayEvent(z, i);
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            if (obj instanceof PostPayEvent) {
                PostPayEvent postPayEvent = (PostPayEvent) obj;
                if (this.isPay == postPayEvent.isPay) {
                    if (this.artcId == postPayEvent.artcId) {
                    }
                }
            }
            return false;
        }
        return true;
    }

    public int hashCode() {
        boolean z = this.isPay;
        if (z) {
            z = true;
        }
        int i = z ? 1 : 0;
        int i2 = z ? 1 : 0;
        return (i * 31) + this.artcId;
    }

    public String toString() {
        return "PostPayEvent(isPay=" + this.isPay + ", artcId=" + this.artcId + ")";
    }

    public PostPayEvent(boolean z, int i) {
        this.isPay = z;
        this.artcId = i;
    }

    public final int getArtcId() {
        return this.artcId;
    }

    public final boolean isPay() {
        return this.isPay;
    }
}
