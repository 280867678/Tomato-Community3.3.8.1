package com.tencent.liteav.p122g;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.text.TextUtils;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.videoediter.ffmpeg.TXFFMediaRetriever;

/* renamed from: com.tencent.liteav.g.h */
/* loaded from: classes3.dex */
public class TXMediaRetriever {

    /* renamed from: a */
    private MediaMetadataRetriever f4087a = new MediaMetadataRetriever();

    /* renamed from: b */
    private TXFFMediaRetriever f4088b = new TXFFMediaRetriever();

    /* renamed from: a */
    public void m1714a(String str) {
        try {
            this.f4087a.setDataSource(str);
        } catch (IllegalArgumentException e) {
            TXCLog.m2914e("MediaMetadataRetrieverW", "set data source error , path = " + str);
            e.printStackTrace();
        }
        this.f4088b.m498a(str);
    }

    /* renamed from: a */
    public long m1716a() {
        String extractMetadata = this.f4087a.extractMetadata(9);
        if (TextUtils.isEmpty(extractMetadata)) {
            TXCLog.m2914e("MediaMetadataRetrieverW", "getDuration error: duration is empty,use ff to get!");
            return m1712c() > m1713b() ? m1712c() : m1713b();
        }
        return Long.parseLong(extractMetadata);
    }

    /* renamed from: b */
    public long m1713b() {
        return this.f4088b.m491h();
    }

    /* renamed from: c */
    public long m1712c() {
        return this.f4088b.m493f();
    }

    /* renamed from: d */
    public int m1711d() {
        String extractMetadata = this.f4087a.extractMetadata(24);
        if (TextUtils.isEmpty(extractMetadata)) {
            TXCLog.m2914e("MediaMetadataRetrieverW", "getRotation error: rotation is empty,use ff to get!");
            return this.f4088b.m499a();
        }
        return Integer.parseInt(extractMetadata);
    }

    /* renamed from: e */
    public int m1710e() {
        String extractMetadata = this.f4087a.extractMetadata(19);
        if (TextUtils.isEmpty(extractMetadata)) {
            TXCLog.m2914e("MediaMetadataRetrieverW", "getHeight error: height is empty,use ff to get!");
            return this.f4088b.m496c();
        }
        return Integer.parseInt(extractMetadata);
    }

    /* renamed from: f */
    public int m1709f() {
        String extractMetadata = this.f4087a.extractMetadata(18);
        if (TextUtils.isEmpty(extractMetadata)) {
            TXCLog.m2914e("MediaMetadataRetrieverW", "getHeight error: height is empty,use ff to get!");
            return this.f4088b.m497b();
        }
        return Integer.parseInt(extractMetadata);
    }

    /* renamed from: g */
    public float m1708g() {
        return this.f4088b.m495d();
    }

    /* renamed from: h */
    public long m1707h() {
        return this.f4088b.m494e();
    }

    /* renamed from: i */
    public int m1706i() {
        return this.f4088b.m492g();
    }

    /* renamed from: a */
    public Bitmap m1715a(long j) {
        return this.f4087a.getFrameAtTime(j, 3);
    }

    /* renamed from: j */
    public Bitmap m1705j() {
        return this.f4087a.getFrameAtTime();
    }

    /* renamed from: k */
    public void m1704k() {
        this.f4087a.release();
    }
}
