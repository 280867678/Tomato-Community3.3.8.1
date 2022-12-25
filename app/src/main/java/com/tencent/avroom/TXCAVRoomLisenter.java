package com.tencent.avroom;

import android.os.Bundle;

/* loaded from: classes3.dex */
public interface TXCAVRoomLisenter {
    void onAVRoomEvent(long j, int i, Bundle bundle);

    void onAVRoomStatus(long j, Bundle bundle);

    void onMemberChange(long j, boolean z);

    void onVideoStateChange(long j, boolean z);
}
