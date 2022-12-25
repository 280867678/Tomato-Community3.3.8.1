package com.tencent.liteav.videoediter.p132a;

import android.annotation.TargetApi;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.p119d.Frame;
import java.io.IOException;
import java.util.HashMap;

@TargetApi(16)
/* renamed from: com.tencent.liteav.videoediter.a.b */
/* loaded from: classes3.dex */
public class TXMediaExtractor {

    /* renamed from: a */
    private MediaExtractor f5462a;

    /* renamed from: b */
    private MediaExtractor f5463b;

    /* renamed from: d */
    private MediaFormat f5465d;

    /* renamed from: e */
    private MediaFormat f5466e;

    /* renamed from: c */
    private HashMap<Integer, MediaFormat> f5464c = new HashMap<>();

    /* renamed from: f */
    private long f5467f = 0;

    /* renamed from: g */
    private boolean f5468g = true;

    /* renamed from: h */
    private boolean f5469h = true;

    /* renamed from: a */
    public synchronized void m570a(String str) throws IOException {
        m567f();
        this.f5462a = new MediaExtractor();
        this.f5462a.setDataSource(str);
        int trackCount = this.f5462a.getTrackCount();
        for (int i = 0; i < trackCount; i++) {
            MediaFormat trackFormat = this.f5462a.getTrackFormat(i);
            if (trackFormat != null) {
                this.f5464c.put(Integer.valueOf(i), trackFormat);
                String string = trackFormat.getString("mime");
                if (string != null && string.startsWith("video")) {
                    this.f5465d = trackFormat;
                    this.f5462a.selectTrack(i);
                    this.f5468g = false;
                } else if (string != null && string.startsWith("audio")) {
                    this.f5466e = trackFormat;
                    this.f5463b = new MediaExtractor();
                    this.f5463b.setDataSource(str);
                    this.f5463b.selectTrack(i);
                    this.f5469h = false;
                }
                TXCLog.m2915d("TXMediaExtractor", "track index: " + i + ", format: " + trackFormat);
                long j = trackFormat.getLong("durationUs");
                if (this.f5467f < j) {
                    this.f5467f = j;
                }
            }
        }
    }

    /* renamed from: a */
    public synchronized MediaFormat m571a() {
        return this.f5465d;
    }

    /* renamed from: b */
    public synchronized MediaFormat m569b() {
        return this.f5466e;
    }

    /* renamed from: c */
    public synchronized long mo557c() {
        return this.f5467f;
    }

    /* renamed from: a */
    public synchronized void mo560a(long j) {
        if (this.f5462a != null) {
            this.f5462a.seekTo(j, 0);
            this.f5468g = false;
        }
        if (this.f5463b != null && this.f5462a != null) {
            this.f5463b.seekTo(this.f5462a.getSampleTime(), 0);
            this.f5469h = false;
        }
    }

    /* renamed from: d */
    public synchronized long m568d() {
        long j;
        j = 0;
        if (this.f5462a != null) {
            j = this.f5462a.getSampleTime();
        }
        if (this.f5463b != null && j > this.f5463b.getSampleTime()) {
            j = this.f5463b.getSampleTime();
        }
        return j;
    }

    /* JADX WARN: Code restructure failed: missing block: B:33:0x00b8, code lost:
        return r4;
     */
    /* renamed from: a */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public synchronized int mo559a(Frame frame) {
        MediaFormat mediaFormat;
        if (frame != null) {
            if (frame.m2338b() != null) {
                while (true) {
                    MediaExtractor mediaExtractor = null;
                    if (this.f5468g) {
                        mediaExtractor = this.f5463b;
                    } else if (this.f5469h) {
                        mediaExtractor = this.f5462a;
                    } else if (this.f5462a != null && this.f5463b != null) {
                        if (this.f5462a.getSampleTime() > this.f5463b.getSampleTime()) {
                            mediaExtractor = this.f5463b;
                        } else {
                            mediaExtractor = this.f5462a;
                        }
                    }
                    if (mediaExtractor == null) {
                        TXCLog.m2911w("TXMediaExtractor", "extractor = null!");
                        if (frame != null && frame.m2338b() != null) {
                            frame.m2331d(0);
                            frame.m2334c(4);
                        }
                        return -1;
                    }
                    int readSampleData = mediaExtractor.readSampleData(frame.m2338b(), 0);
                    if (readSampleData < 0) {
                        if (mediaExtractor == this.f5462a) {
                            this.f5468g = true;
                        } else {
                            this.f5469h = true;
                        }
                        if (this.f5468g && this.f5469h) {
                            frame.m2331d(0);
                            frame.m2334c(4);
                            break;
                        }
                    } else {
                        long sampleTime = mediaExtractor.getSampleTime();
                        int sampleFlags = mediaExtractor.getSampleFlags();
                        int sampleTrackIndex = mediaExtractor.getSampleTrackIndex();
                        if (sampleTrackIndex < this.f5464c.size() && (mediaFormat = this.f5464c.get(Integer.valueOf(sampleTrackIndex))) != null) {
                            frame.m2342a(mediaFormat.getString("mime"));
                        }
                        frame.m2343a(sampleTime);
                        frame.m2334c(sampleFlags);
                        frame.m2331d(readSampleData);
                        frame.m2338b().position(0);
                        mediaExtractor.advance();
                    }
                }
            }
        }
        TXCLog.m2914e("TXMediaExtractor", "frame input is invalid");
        if (frame != null && frame.m2338b() != null) {
            frame.m2331d(0);
            frame.m2334c(4);
        }
        return -1;
    }

    /* renamed from: f */
    private synchronized void m567f() {
        if (this.f5462a != null) {
            this.f5462a.release();
            this.f5462a = null;
        }
        if (this.f5463b != null) {
            this.f5463b.release();
            this.f5463b = null;
        }
        this.f5464c.clear();
        this.f5465d = null;
        this.f5466e = null;
        this.f5467f = 0L;
        this.f5468g = true;
        this.f5469h = true;
    }

    /* renamed from: e */
    public synchronized void mo556e() {
        m567f();
    }
}
