package com.tencent.liteav.p127l;

import android.content.Context;
import android.opengl.GLES20;
import android.util.Log;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.p109e.CropRect;
import com.tencent.liteav.basic.p112h.TXCCombineFrame;
import com.tencent.liteav.beauty.TXIVideoPreprocessorListener;
import com.tencent.liteav.beauty.p115b.TXCGPURotateScaleFilter;
import java.util.LinkedList;
import java.util.Queue;

/* renamed from: com.tencent.liteav.l.a */
/* loaded from: classes3.dex */
public class TXCCombineProcessor {

    /* renamed from: g */
    private Context f4642g;

    /* renamed from: a */
    private TXCGPURotateScaleFilter[] f4636a = null;

    /* renamed from: b */
    private TXCCombineVideoFilter f4637b = null;

    /* renamed from: c */
    private TXCCombineFrame[] f4638c = null;

    /* renamed from: d */
    private float[] f4639d = null;

    /* renamed from: e */
    private int f4640e = 0;

    /* renamed from: f */
    private int f4641f = 0;

    /* renamed from: h */
    private final Queue<Runnable> f4643h = new LinkedList();

    /* renamed from: i */
    private String f4644i = "CombineProcessor";

    /* renamed from: j */
    private TXIVideoPreprocessorListener f4645j = new TXIVideoPreprocessorListener() { // from class: com.tencent.liteav.l.a.4
        @Override // com.tencent.liteav.beauty.TXIVideoPreprocessorListener
        public void didProcessFrame(byte[] bArr, int i, int i2, int i3, long j) {
        }

        @Override // com.tencent.liteav.beauty.TXIVideoPreprocessorListener
        public int willAddWatermark(int i, int i2, int i3) {
            return 0;
        }

        @Override // com.tencent.liteav.beauty.TXIVideoPreprocessorListener
        public void didProcessFrame(int i, int i2, int i3, long j) {
            TXCCombineProcessor.this.f4638c[TXCCombineProcessor.this.f4641f].f2726b = 0;
            TXCCombineProcessor.this.f4638c[TXCCombineProcessor.this.f4641f].f2725a = i;
            TXCCombineProcessor.this.f4638c[TXCCombineProcessor.this.f4641f].f2727c = i2;
            TXCCombineProcessor.this.f4638c[TXCCombineProcessor.this.f4641f].f2728d = i3;
        }
    };

    public TXCCombineProcessor(Context context) {
        this.f4642g = null;
        this.f4642g = context.getApplicationContext();
    }

    /* renamed from: a */
    public void m1285a(final int i, final int i2) {
        m1282a(new Runnable() { // from class: com.tencent.liteav.l.a.1
            @Override // java.lang.Runnable
            public void run() {
                if (TXCCombineProcessor.this.f4637b != null) {
                    TXCCombineProcessor.this.f4637b.m1273a(i, i2);
                }
            }
        });
    }

    /* renamed from: b */
    public void m1277b(final int i, final int i2) {
        m1282a(new Runnable() { // from class: com.tencent.liteav.l.a.2
            @Override // java.lang.Runnable
            public void run() {
                if (TXCCombineProcessor.this.f4637b != null) {
                    TXCCombineProcessor.this.f4637b.m1267b(i, i2);
                }
            }
        });
    }

    /* renamed from: a */
    public void m1284a(final CropRect cropRect) {
        m1282a(new Runnable() { // from class: com.tencent.liteav.l.a.3
            @Override // java.lang.Runnable
            public void run() {
                if (TXCCombineProcessor.this.f4637b != null) {
                    TXCCombineProcessor.this.f4637b.m1271a(cropRect);
                }
            }
        });
    }

    /* renamed from: a */
    public int m1279a(TXCCombineFrame[] tXCCombineFrameArr, int i) {
        if (tXCCombineFrameArr == null || tXCCombineFrameArr.length <= 0) {
            Log.e(this.f4644i, "frames is null or no frames!");
            return -1;
        }
        if (this.f4640e < tXCCombineFrameArr.length) {
            this.f4640e = tXCCombineFrameArr.length;
            m1278b();
        }
        m1280a(tXCCombineFrameArr);
        m1281a(this.f4643h);
        this.f4638c = (TXCCombineFrame[]) tXCCombineFrameArr.clone();
        for (int i2 = 0; i2 < tXCCombineFrameArr.length; i2++) {
            TXCGPURotateScaleFilter[] tXCGPURotateScaleFilterArr = this.f4636a;
            if (tXCGPURotateScaleFilterArr[i2] != null && tXCCombineFrameArr[i2].f2729e != null) {
                tXCGPURotateScaleFilterArr[i2].m2647a(tXCCombineFrameArr[i2].f2729e.f2733b, tXCCombineFrameArr[i2].f2729e.f2732a);
                this.f4636a[i2].m2645b(tXCCombineFrameArr[i2].f2729e.f2734c);
                GLES20.glViewport(0, 0, tXCCombineFrameArr[i2].f2731g.f2535c, tXCCombineFrameArr[i2].f2731g.f2536d);
                TXCCombineFrame[] tXCCombineFrameArr2 = this.f4638c;
                tXCCombineFrameArr2[i2].f2725a = this.f4636a[i2].mo2294a(tXCCombineFrameArr2[i2].f2725a);
            }
        }
        return this.f4637b.m1269a(this.f4638c, i);
    }

    /* renamed from: a */
    private void m1282a(Runnable runnable) {
        synchronized (this.f4643h) {
            this.f4643h.add(runnable);
        }
    }

    /* renamed from: a */
    private void m1281a(Queue<Runnable> queue) {
        while (true) {
            Runnable runnable = null;
            synchronized (queue) {
                if (!queue.isEmpty()) {
                    runnable = queue.poll();
                }
            }
            if (runnable != null) {
                runnable.run();
            } else {
                return;
            }
        }
    }

    /* renamed from: a */
    private void m1280a(TXCCombineFrame[] tXCCombineFrameArr) {
        if (this.f4636a == null) {
            this.f4636a = new TXCGPURotateScaleFilter[tXCCombineFrameArr.length];
            for (int i = 0; i < tXCCombineFrameArr.length; i++) {
                this.f4636a[i] = new TXCGPURotateScaleFilter();
                this.f4636a[i].mo1353a(true);
                if (!this.f4636a[i].mo2653c()) {
                    TXCLog.m2914e(this.f4644i, "mRotateScaleFilter[i] failed!");
                    return;
                }
            }
        }
        if (this.f4636a != null) {
            for (int i2 = 0; i2 < tXCCombineFrameArr.length; i2++) {
                TXCGPURotateScaleFilter[] tXCGPURotateScaleFilterArr = this.f4636a;
                if (tXCGPURotateScaleFilterArr[i2] != null) {
                    tXCGPURotateScaleFilterArr[i2].mo1333a(tXCCombineFrameArr[i2].f2727c, tXCCombineFrameArr[i2].f2728d);
                }
            }
        }
        if (this.f4637b == null) {
            this.f4637b = new TXCCombineVideoFilter();
        }
    }

    /* renamed from: b */
    private void m1278b() {
        if (this.f4636a != null) {
            int i = 0;
            while (true) {
                TXCGPURotateScaleFilter[] tXCGPURotateScaleFilterArr = this.f4636a;
                if (i >= tXCGPURotateScaleFilterArr.length) {
                    break;
                }
                if (tXCGPURotateScaleFilterArr[i] != null) {
                    tXCGPURotateScaleFilterArr[i].mo1351e();
                    this.f4636a[i] = null;
                }
                i++;
            }
            this.f4636a = null;
        }
        TXCCombineVideoFilter tXCCombineVideoFilter = this.f4637b;
        if (tXCCombineVideoFilter != null) {
            tXCCombineVideoFilter.m1274a();
            this.f4637b = null;
        }
    }

    /* renamed from: a */
    public void m1286a() {
        m1278b();
    }
}
