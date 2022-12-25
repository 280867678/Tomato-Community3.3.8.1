package com.one.tomato.entity.event;

/* compiled from: PostVideoPlayEvent.kt */
/* loaded from: classes3.dex */
public final class PostVideoPlayEvent {

    /* renamed from: boolean  reason: not valid java name */
    private boolean f6076boolean;

    public static /* synthetic */ PostVideoPlayEvent copy$default(PostVideoPlayEvent postVideoPlayEvent, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = postVideoPlayEvent.f6076boolean;
        }
        return postVideoPlayEvent.copy(z);
    }

    public final boolean component1() {
        return this.f6076boolean;
    }

    public final PostVideoPlayEvent copy(boolean z) {
        return new PostVideoPlayEvent(z);
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            if (obj instanceof PostVideoPlayEvent) {
                if (this.f6076boolean == ((PostVideoPlayEvent) obj).f6076boolean) {
                }
            }
            return false;
        }
        return true;
    }

    public int hashCode() {
        boolean z = this.f6076boolean;
        if (z) {
            return 1;
        }
        return z ? 1 : 0;
    }

    public String toString() {
        return "PostVideoPlayEvent(boolean=" + this.f6076boolean + ")";
    }

    public PostVideoPlayEvent(boolean z) {
        this.f6076boolean = z;
    }

    public final boolean getBoolean() {
        return this.f6076boolean;
    }

    public final void setBoolean(boolean z) {
        this.f6076boolean = z;
    }
}
