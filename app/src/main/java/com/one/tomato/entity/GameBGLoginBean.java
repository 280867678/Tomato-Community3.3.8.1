package com.one.tomato.entity;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: GameBGLoginBean.kt */
/* loaded from: classes3.dex */
public final class GameBGLoginBean {
    private final String url;

    public static /* synthetic */ GameBGLoginBean copy$default(GameBGLoginBean gameBGLoginBean, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            str = gameBGLoginBean.url;
        }
        return gameBGLoginBean.copy(str);
    }

    public final String component1() {
        return this.url;
    }

    public final GameBGLoginBean copy(String str) {
        return new GameBGLoginBean(str);
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            return (obj instanceof GameBGLoginBean) && Intrinsics.areEqual(this.url, ((GameBGLoginBean) obj).url);
        }
        return true;
    }

    public int hashCode() {
        String str = this.url;
        if (str != null) {
            return str.hashCode();
        }
        return 0;
    }

    public String toString() {
        return "GameBGLoginBean(url=" + this.url + ")";
    }

    public GameBGLoginBean(String str) {
        this.url = str;
    }

    public final String getUrl() {
        return this.url;
    }
}
