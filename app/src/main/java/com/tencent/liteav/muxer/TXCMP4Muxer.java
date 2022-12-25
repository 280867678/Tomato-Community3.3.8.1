package com.tencent.liteav.muxer;

import android.content.Context;
import android.media.MediaCodec;
import android.media.MediaFormat;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.p110f.TXCConfigCenter;
import java.nio.ByteBuffer;

/* renamed from: com.tencent.liteav.muxer.c */
/* loaded from: classes3.dex */
public class TXCMP4Muxer implements ITXCMP4Muxer {

    /* renamed from: a */
    private int f4705a;

    /* renamed from: b */
    private ITXCMP4Muxer f4706b;

    public TXCMP4Muxer(Context context, int i) {
        this.f4705a = 0;
        if (i == 0) {
            this.f4705a = 0;
            this.f4706b = new TXCMP4SWMuxer();
            TXCLog.m2913i("TXCMP4Muxer", "TXCMP4Muxer: use sw model ");
        } else if (i == 1) {
            this.f4705a = 1;
            this.f4706b = new TXCMP4HWMuxer();
            TXCLog.m2913i("TXCMP4Muxer", "TXCMP4Muxer: use hw model ");
        } else if (m1236a(context)) {
            this.f4705a = 0;
            this.f4706b = new TXCMP4SWMuxer();
            TXCLog.m2913i("TXCMP4Muxer", "TXCMP4Muxer: use sw model ");
        } else {
            this.f4705a = 1;
            this.f4706b = new TXCMP4HWMuxer();
            TXCLog.m2913i("TXCMP4Muxer", "TXCMP4Muxer: use hw model ");
        }
    }

    /* renamed from: a */
    public static boolean m1236a(Context context) {
        TXCConfigCenter.m2988a().m2985a(context);
        return TXCConfigCenter.m2988a().m2964e() == 1;
    }

    @Override // com.tencent.liteav.muxer.ITXCMP4Muxer
    /* renamed from: a */
    public void mo1232a(MediaFormat mediaFormat) {
        this.f4706b.mo1232a(mediaFormat);
    }

    @Override // com.tencent.liteav.muxer.ITXCMP4Muxer
    /* renamed from: b */
    public void mo1226b(MediaFormat mediaFormat) {
        this.f4706b.mo1226b(mediaFormat);
    }

    @Override // com.tencent.liteav.muxer.ITXCMP4Muxer
    /* renamed from: a */
    public void mo1231a(String str) {
        this.f4706b.mo1231a(str);
    }

    @Override // com.tencent.liteav.muxer.ITXCMP4Muxer
    /* renamed from: a */
    public void mo1230a(ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo) {
        this.f4706b.mo1230a(byteBuffer, bufferInfo);
    }

    @Override // com.tencent.liteav.muxer.ITXCMP4Muxer
    /* renamed from: a */
    public void mo1228a(byte[] bArr, int i, int i2, long j, int i3) {
        this.f4706b.mo1228a(bArr, i, i2, j, i3);
    }

    @Override // com.tencent.liteav.muxer.ITXCMP4Muxer
    /* renamed from: b */
    public void mo1224b(byte[] bArr, int i, int i2, long j, int i3) {
        this.f4706b.mo1224b(bArr, i, i2, j, i3);
    }

    @Override // com.tencent.liteav.muxer.ITXCMP4Muxer
    /* renamed from: a */
    public int mo1235a() {
        return this.f4706b.mo1235a();
    }

    @Override // com.tencent.liteav.muxer.ITXCMP4Muxer
    /* renamed from: b */
    public int mo1227b() {
        return this.f4706b.mo1227b();
    }

    @Override // com.tencent.liteav.muxer.ITXCMP4Muxer
    /* renamed from: a */
    public void mo1234a(int i) {
        this.f4706b.mo1234a(i);
    }

    @Override // com.tencent.liteav.muxer.ITXCMP4Muxer
    /* renamed from: c */
    public boolean mo1223c() {
        return this.f4706b.mo1223c();
    }

    @Override // com.tencent.liteav.muxer.ITXCMP4Muxer
    /* renamed from: d */
    public boolean mo1221d() {
        return this.f4706b.mo1221d();
    }
}
