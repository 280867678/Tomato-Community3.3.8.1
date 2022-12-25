package com.tencent.rtmp;

import android.os.Bundle;

/* loaded from: classes3.dex */
public interface ITXLivePlayListener {
    void onNetStatus(Bundle bundle);

    void onPlayEvent(int i, Bundle bundle);
}
