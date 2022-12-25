package com.google.android.exoplayer2.source.hls.playlist;

import com.google.android.exoplayer2.offline.FilterableManifest;
import java.util.Collections;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class HlsPlaylist implements FilterableManifest<HlsPlaylist, Object> {
    public final String baseUri;

    /* JADX INFO: Access modifiers changed from: protected */
    public HlsPlaylist(String str, List<String> list) {
        this.baseUri = str;
        Collections.unmodifiableList(list);
    }
}
