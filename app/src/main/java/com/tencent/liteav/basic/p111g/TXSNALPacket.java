package com.tencent.liteav.basic.p111g;

import android.media.MediaCodec;
import java.nio.ByteBuffer;

/* renamed from: com.tencent.liteav.basic.g.b */
/* loaded from: classes3.dex */
public class TXSNALPacket {
    public byte[] nalData = null;
    public int nalType = -1;
    public long gopIndex = 0;
    public long gopFrameIndex = 0;
    public long frameIndex = 0;
    public long refFremeIndex = 0;
    public long pts = 0;
    public long dts = 0;
    public int codecId = 0;
    public ByteBuffer buffer = null;
    public MediaCodec.BufferInfo info = null;
}
