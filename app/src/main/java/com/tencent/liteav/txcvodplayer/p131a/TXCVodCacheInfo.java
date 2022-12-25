package com.tencent.liteav.txcvodplayer.p131a;

import com.tencent.ijk.media.player.IjkMediaMeta;

/* renamed from: com.tencent.liteav.txcvodplayer.a.a */
/* loaded from: classes3.dex */
public class TXCVodCacheInfo {

    /* renamed from: a */
    String f5376a;

    /* renamed from: b */
    String f5377b;

    /* renamed from: c */
    String f5378c;

    /* JADX INFO: Access modifiers changed from: package-private */
    public TXCVodCacheInfo(String str, String str2, String str3) {
        this.f5376a = str2;
        this.f5377b = str;
        this.f5378c = str3;
    }

    /* renamed from: a */
    public String m678a() {
        if ("mp4".equals(this.f5378c)) {
            return m673c();
        }
        return null;
    }

    /* renamed from: b */
    public String m674b() {
        if (IjkMediaMeta.IJKM_KEY_M3U8.equals(this.f5378c)) {
            return m673c();
        }
        return null;
    }

    /* renamed from: c */
    public String m673c() {
        return this.f5376a + "/" + this.f5377b;
    }

    /* renamed from: d */
    public String m672d() {
        return this.f5377b;
    }
}
