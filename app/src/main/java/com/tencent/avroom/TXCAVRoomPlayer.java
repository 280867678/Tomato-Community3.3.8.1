package com.tencent.avroom;

import android.content.Context;
import android.view.TextureView;
import com.tencent.liteav.TXCRenderAndDec;
import com.tencent.rtmp.p134ui.TXCloudVideoView;

/* renamed from: com.tencent.avroom.c */
/* loaded from: classes3.dex */
public class TXCAVRoomPlayer extends TXCRenderAndDec {

    /* renamed from: a */
    TXCloudVideoView f1999a;

    /* renamed from: b */
    private DataCollectionPlayer f2000b = null;

    public TXCAVRoomPlayer(Context context, int i) {
        super(context, i);
    }

    /* renamed from: a */
    public void m3503a(TXCloudVideoView tXCloudVideoView) {
        if (tXCloudVideoView != null) {
            this.f1999a = tXCloudVideoView;
        }
        TXCloudVideoView tXCloudVideoView2 = this.f1999a;
        if (tXCloudVideoView2 == null) {
            return;
        }
        tXCloudVideoView2.setVisibility(0);
        TextureView textureView = new TextureView(this.f1999a.getContext());
        this.f1999a.addVideoView(textureView);
        m1382q().m888a(textureView);
    }

    /* renamed from: a */
    public DataCollectionPlayer m3505a() {
        return this.f2000b;
    }

    /* renamed from: a */
    public void m3504a(DataCollectionPlayer dataCollectionPlayer) {
        this.f2000b = dataCollectionPlayer;
    }
}
