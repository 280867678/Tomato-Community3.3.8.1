package com.tencent.liteav.p119d;

import android.annotation.TargetApi;
import android.media.MediaCodec;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.List;

@TargetApi(16)
/* renamed from: com.tencent.liteav.d.e */
/* loaded from: classes3.dex */
public class Frame implements Serializable {
    private int audioBitRate;
    private List bitmapList;
    private float blurLevel;
    private int bufferIndex;
    private MediaCodec.BufferInfo bufferInfo;
    private ByteBuffer byteBuffer;
    private int channelCount;
    private int frameFormat;
    private int frameRate;
    private int height;
    private float mCropOffsetRatio;
    private String mime;
    private long originSampleTime;
    private long reverseSampleTime;
    private int rotation;
    private int sampleRate;
    private long speedSampleTime;
    private boolean tailFrame;
    private int textureId;
    private int trackId;
    private int width;

    public String toString() {
        return "";
    }

    public Frame() {
        this.bufferInfo = new MediaCodec.BufferInfo();
        this.frameFormat = 4;
    }

    public Frame(ByteBuffer byteBuffer, int i, long j, int i2, int i3, int i4) {
        this.bufferInfo = new MediaCodec.BufferInfo();
        this.frameFormat = 4;
        this.byteBuffer = byteBuffer;
        this.trackId = i4;
        MediaCodec.BufferInfo bufferInfo = this.bufferInfo;
        bufferInfo.flags = i3;
        bufferInfo.presentationTimeUs = j;
        this.bufferIndex = i2;
        bufferInfo.size = i;
    }

    public Frame(String str, ByteBuffer byteBuffer, MediaCodec.BufferInfo bufferInfo) {
        this.bufferInfo = new MediaCodec.BufferInfo();
        this.frameFormat = 4;
        this.mime = str;
        this.byteBuffer = byteBuffer;
        this.bufferInfo = new MediaCodec.BufferInfo();
        this.bufferInfo.set(bufferInfo.offset, bufferInfo.size, bufferInfo.presentationTimeUs, bufferInfo.flags);
    }

    /* renamed from: a */
    public String m2346a() {
        return this.mime;
    }

    /* renamed from: a */
    public void m2342a(String str) {
        this.mime = str;
    }

    /* renamed from: b */
    public ByteBuffer m2338b() {
        return this.byteBuffer;
    }

    /* renamed from: c */
    public int m2335c() {
        return this.trackId;
    }

    /* renamed from: d */
    public int m2332d() {
        return this.bufferIndex;
    }

    /* renamed from: a */
    public void m2341a(ByteBuffer byteBuffer) {
        this.byteBuffer = byteBuffer;
    }

    /* renamed from: a */
    public void m2344a(int i) {
        this.trackId = i;
    }

    /* renamed from: b */
    public void m2337b(int i) {
        this.bufferIndex = i;
    }

    /* renamed from: a */
    public void m2343a(long j) {
        this.bufferInfo.presentationTimeUs = j;
    }

    /* renamed from: e */
    public long m2329e() {
        return this.bufferInfo.presentationTimeUs;
    }

    /* renamed from: c */
    public void m2334c(int i) {
        this.bufferInfo.flags = i;
    }

    /* renamed from: d */
    public void m2331d(int i) {
        this.bufferInfo.size = i;
    }

    /* renamed from: f */
    public int m2327f() {
        return this.bufferInfo.flags;
    }

    /* renamed from: g */
    public int m2325g() {
        return this.bufferInfo.size;
    }

    /* renamed from: h */
    public int m2323h() {
        return this.rotation;
    }

    /* renamed from: e */
    public void m2328e(int i) {
        this.rotation = i;
    }

    /* renamed from: i */
    public int m2321i() {
        return this.frameRate;
    }

    /* renamed from: f */
    public void m2326f(int i) {
        this.frameRate = i;
    }

    /* renamed from: j */
    public int m2319j() {
        return this.sampleRate;
    }

    /* renamed from: g */
    public void m2324g(int i) {
        this.sampleRate = i;
    }

    /* renamed from: k */
    public int m2317k() {
        return this.channelCount;
    }

    /* renamed from: h */
    public void m2322h(int i) {
        this.channelCount = i;
    }

    /* renamed from: l */
    public int m2315l() {
        return this.audioBitRate;
    }

    /* renamed from: i */
    public void m2320i(int i) {
        this.audioBitRate = i;
    }

    /* renamed from: m */
    public int m2313m() {
        return this.width;
    }

    /* renamed from: j */
    public void m2318j(int i) {
        this.width = i;
    }

    /* renamed from: n */
    public int m2311n() {
        return this.height;
    }

    /* renamed from: k */
    public void m2316k(int i) {
        this.height = i;
    }

    /* renamed from: o */
    public MediaCodec.BufferInfo m2310o() {
        return this.bufferInfo;
    }

    /* renamed from: p */
    public boolean m2309p() {
        return (m2327f() & 4) != 0;
    }

    /* renamed from: q */
    public boolean m2308q() {
        return m2325g() == 0 || m2327f() == 2;
    }

    /* renamed from: r */
    public boolean m2307r() {
        return this.tailFrame;
    }

    /* renamed from: a */
    public void m2339a(boolean z) {
        this.tailFrame = z;
    }

    /* renamed from: a */
    public void m2345a(float f) {
        this.blurLevel = f;
    }

    /* renamed from: s */
    public float m2306s() {
        return this.blurLevel;
    }

    /* renamed from: b */
    public void m2336b(long j) {
        this.speedSampleTime = j;
    }

    /* renamed from: t */
    public long m2305t() {
        return this.speedSampleTime;
    }

    /* renamed from: u */
    public long m2304u() {
        return this.reverseSampleTime;
    }

    /* renamed from: c */
    public void m2333c(long j) {
        this.reverseSampleTime = j;
    }

    /* renamed from: v */
    public long m2303v() {
        return this.originSampleTime;
    }

    /* renamed from: d */
    public void m2330d(long j) {
        this.originSampleTime = j;
    }

    /* renamed from: w */
    public List m2302w() {
        return this.bitmapList;
    }

    /* renamed from: a */
    public void m2340a(List list) {
        this.bitmapList = list;
    }

    /* renamed from: x */
    public int m2301x() {
        return this.textureId;
    }

    /* renamed from: l */
    public void m2314l(int i) {
        this.textureId = i;
    }

    /* renamed from: y */
    public int m2300y() {
        return this.frameFormat;
    }

    /* renamed from: m */
    public void m2312m(int i) {
        this.frameFormat = i;
    }
}
