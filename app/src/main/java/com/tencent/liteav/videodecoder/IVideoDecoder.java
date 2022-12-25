package com.tencent.liteav.videodecoder;

import android.view.Surface;
import com.tencent.liteav.basic.p108d.TXINotifyListener;
import com.tencent.liteav.basic.p111g.TXSNALPacket;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;

/* renamed from: com.tencent.liteav.videodecoder.a */
/* loaded from: classes3.dex */
public interface IVideoDecoder {
    int config(Surface surface);

    void decode(TXSNALPacket tXSNALPacket);

    boolean isHevc();

    void setListener(TXIVideoDecoderListener tXIVideoDecoderListener);

    void setNotifyListener(WeakReference<TXINotifyListener> weakReference);

    int start(ByteBuffer byteBuffer, ByteBuffer byteBuffer2, boolean z, boolean z2);

    void stop();
}
