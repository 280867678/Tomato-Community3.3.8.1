package com.tencent.liteav.p126k;

import com.tencent.liteav.beauty.TXCVideoPreprocessor;
import com.tencent.liteav.beauty.p115b.TXCGPUWatermarkFilter;

/* renamed from: com.tencent.liteav.k.l */
/* loaded from: classes3.dex */
public class TXCGPUWatermarkTextureFilter extends TXCGPUWatermarkFilter {

    /* renamed from: x */
    private String f4544x;

    public TXCGPUWatermarkTextureFilter(String str, String str2) {
        super(str, str2);
        this.f4544x = "WatermarkTexture";
        ((TXCGPUWatermarkFilter) this).f2992t = true;
        ((TXCGPUWatermarkFilter) this).f2993u = 770;
    }

    public TXCGPUWatermarkTextureFilter() {
        this("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n \nvarying vec2 textureCoordinate;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = inputTextureCoordinate.xy;\n}", "varying lowp vec2 textureCoordinate;\n \nuniform sampler2D inputImageTexture;\n \nvoid main()\n{\n     gl_FragColor = texture2D(inputImageTexture, textureCoordinate);\n}");
    }

    /* renamed from: a */
    public void m1322a(TXCVideoPreprocessor.C3397d[] c3397dArr) {
        if (((TXCGPUWatermarkFilter) this).f2990r == null) {
            ((TXCGPUWatermarkFilter) this).f2990r = new TXCGPUWatermarkFilter.C3383a[c3397dArr.length];
        }
        for (int i = 0; i < c3397dArr.length; i++) {
            TXCGPUWatermarkFilter.C3383a[] c3383aArr = ((TXCGPUWatermarkFilter) this).f2990r;
            if (c3383aArr[i] == null) {
                c3383aArr[i] = new TXCGPUWatermarkFilter.C3383a();
            }
            TXCGPUWatermarkFilter.C3383a[] c3383aArr2 = ((TXCGPUWatermarkFilter) this).f2990r;
            if (c3383aArr2[i].f3000d == null) {
                c3383aArr2[i].f3000d = new int[1];
            }
            m2731a(c3397dArr[i].f3261f, c3397dArr[i].f3262g, c3397dArr[i].f3257b, c3397dArr[i].f3258c, c3397dArr[i].f3259d, i);
            ((TXCGPUWatermarkFilter) this).f2990r[i].f3000d[0] = c3397dArr[i].f3260e;
        }
    }
}
