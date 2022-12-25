package com.tomatolive.library.model.event;

/* loaded from: classes3.dex */
public class SearchEvent extends BaseEvent {
    public boolean isHistory;
    public boolean isRecommend;
    public String keyword;

    public SearchEvent() {
    }

    public SearchEvent(String str) {
        this.keyword = str;
    }

    public SearchEvent(String str, boolean z, boolean z2) {
        this.keyword = str;
        this.isRecommend = z;
        this.isHistory = z2;
    }
}
