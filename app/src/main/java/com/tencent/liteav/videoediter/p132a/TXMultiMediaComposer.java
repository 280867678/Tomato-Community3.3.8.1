package com.tencent.liteav.videoediter.p132a;

import android.os.Handler;
import com.tencent.liteav.p119d.Frame;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;

/* renamed from: com.tencent.liteav.videoediter.a.c */
/* loaded from: classes3.dex */
public class TXMultiMediaComposer implements Runnable {

    /* renamed from: a */
    private AbstractC3663a f5470a;

    /* renamed from: b */
    private Handler f5471b;

    /* renamed from: c */
    private List<String> f5472c;

    /* renamed from: d */
    private String f5473d;

    /* renamed from: e */
    private long f5474e;

    /* renamed from: f */
    private long f5475f;

    /* renamed from: g */
    private boolean f5476g;

    /* renamed from: h */
    private long f5477h;

    /* renamed from: i */
    private Runnable f5478i;

    /* compiled from: TXMultiMediaComposer.java */
    /* renamed from: com.tencent.liteav.videoediter.a.c$a */
    /* loaded from: classes3.dex */
    public interface AbstractC3663a {
        /* renamed from: a */
        void m562a(float f);

        /* renamed from: a */
        void m561a(int i, String str);
    }

    @Override // java.lang.Runnable
    public void run() {
        if (!this.f5476g) {
            return;
        }
        List<String> list = this.f5472c;
        if (list == null || list.size() <= 0) {
            m566a(-1, "未设置视频源");
            return;
        }
        String str = this.f5473d;
        if (str == null || str.isEmpty()) {
            m566a(-1, "未设置输出路径");
            return;
        }
        TXMultiMediaExtractor tXMultiMediaExtractor = new TXMultiMediaExtractor();
        C3659a c3659a = new C3659a();
        try {
            tXMultiMediaExtractor.m558a(this.f5472c);
            c3659a.m585a(this.f5473d);
            long mo557c = tXMultiMediaExtractor.mo557c();
            tXMultiMediaExtractor.m555f();
            TXMediaExtractor tXMediaExtractor = new TXMediaExtractor();
            tXMediaExtractor.m570a(this.f5472c.get(0));
            if (tXMediaExtractor.m571a() != null) {
                c3659a.m586a(tXMediaExtractor.m571a());
            }
            if (tXMediaExtractor.m569b() != null) {
                c3659a.m581b(tXMediaExtractor.m569b());
            }
            tXMediaExtractor.mo556e();
            int m579c = c3659a.m579c();
            if (m579c < 0) {
                m566a(-1, m579c != -8 ? m579c != -7 ? m579c != -6 ? m579c != -5 ? m579c != -4 ? "封装器启动失败" : "创建封装器失败" : "不支持的视频格式" : "封装器AddVideoTrack错误" : "不支持的音频格式" : "封装器AddAudioTrack错误");
                return;
            }
            tXMultiMediaExtractor.mo560a(this.f5474e);
            Frame frame = new Frame();
            frame.m2341a(ByteBuffer.allocate(512000));
            do {
                tXMultiMediaExtractor.mo559a(frame);
                if ((frame.m2327f() & 4) == 0) {
                    if (this.f5475f > 0 && frame.m2329e() > this.f5475f) {
                        break;
                    } else if (frame.m2346a().startsWith("video")) {
                        c3659a.m584a(frame.m2338b(), frame.m2310o());
                        this.f5477h++;
                        if (this.f5477h >= 50) {
                            m565a(frame.m2310o().presentationTimeUs - this.f5474e, this.f5475f < 0 ? mo557c : this.f5475f - this.f5474e);
                            this.f5477h = 0L;
                        }
                    } else {
                        c3659a.m580b(frame.m2338b(), frame.m2310o());
                    }
                }
                if (!this.f5476g) {
                    break;
                }
            } while ((frame.m2327f() & 4) == 0);
            if (c3659a.m577d() < 0) {
                m566a(-1, "停止封装器失败");
            } else if (this.f5476g) {
                m566a(0, "");
            }
        } catch (IOException e) {
            e.printStackTrace();
            m566a(-1, "获取数据格式失败");
        } finally {
            tXMultiMediaExtractor.mo556e();
            this.f5476g = false;
        }
    }

    /* renamed from: a */
    private void m565a(long j, long j2) {
        if (this.f5478i != null || this.f5470a == null) {
            return;
        }
        final float f = 1.0f;
        if (j2 > 0 && j <= j2) {
            f = (((float) j) * 1.0f) / ((float) j2);
        }
        this.f5478i = new Runnable() { // from class: com.tencent.liteav.videoediter.a.c.1
            @Override // java.lang.Runnable
            public void run() {
                if (TXMultiMediaComposer.this.f5470a != null) {
                    TXMultiMediaComposer.this.f5470a.m562a(f);
                }
                TXMultiMediaComposer.this.f5478i = null;
            }
        };
        this.f5471b.post(this.f5478i);
    }

    /* renamed from: a */
    private void m566a(final int i, final String str) {
        this.f5471b.post(new Runnable() { // from class: com.tencent.liteav.videoediter.a.c.2
            @Override // java.lang.Runnable
            public void run() {
                if (TXMultiMediaComposer.this.f5470a != null) {
                    TXMultiMediaComposer.this.f5470a.m561a(i, str);
                }
            }
        });
    }
}
