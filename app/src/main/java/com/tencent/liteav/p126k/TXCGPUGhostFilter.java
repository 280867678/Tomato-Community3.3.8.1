package com.tencent.liteav.p126k;

import android.opengl.GLES20;
import com.tencent.liteav.basic.p109e.TXCGPUFilter;
import com.tencent.liteav.p126k.TXCVideoEffect;
import java.nio.FloatBuffer;

/* renamed from: com.tencent.liteav.k.d */
/* loaded from: classes3.dex */
public class TXCGPUGhostFilter extends TXCGPUFilter {

    /* renamed from: s */
    private int f4490s = -1;

    /* renamed from: t */
    private int f4491t = -1;

    /* renamed from: u */
    private int f4492u = -1;

    /* renamed from: v */
    private float f4493v = 0.0f;

    /* renamed from: r */
    private TXCGPUFilter f4489r = new TXCGPUFilter("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n \nvarying vec2 textureCoordinate;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = inputTextureCoordinate.xy;\n}", "precision lowp float;  \nvarying vec2 textureCoordinate;  \n\tuniform sampler2D inputImageTexture;  \n\tuniform float shift;  \n\tuniform float alpha;  \n\tvoid main() { vec4 colorShift = texture2D(inputImageTexture, textureCoordinate + vec2(shift, 0.0));  \n\tvec4 color = texture2D(inputImageTexture, textureCoordinate + vec2(shift * 0.1, 0.0));  \n\tgl_FragColor = vec4(mix(colorShift.rgb, color.rgb, alpha), color.a);  \n}  \n");

    public TXCGPUGhostFilter() {
        super("attribute vec4 position;  \nattribute vec4 inputTextureCoordinate;\nuniform vec2 step;  \nvarying vec2 textureCoordinate;  \nvarying vec2 oneBackCoord;  \nvarying vec2 twoBackCoord;  \nvarying vec2 threeBackCoord;  \nvarying vec2 fourBackCoord;  \nvarying vec2 oneForwardCoord;  \nvarying vec2 twoForwardCoord;  \nvarying vec2 threeForwardCoord;  \nvarying vec2 fourForwardCoord;  \nvoid main() {  \n\tgl_Position = position;  \n\tvec2 coord = inputTextureCoordinate.xy;  \n\ttextureCoordinate = coord;  \n\toneBackCoord = coord.xy - step;  \n\ttwoBackCoord = coord.xy - 2.0 * step;  \n\tthreeBackCoord = coord.xy - 3.0 * step;  \n\tfourBackCoord = coord.xy - 4.0 * step;  \n\toneForwardCoord = coord.xy + step;  \n\ttwoForwardCoord = coord.xy + 2.0 * step;  \n\tthreeForwardCoord = coord.xy + 3.0 * step;  \n\tfourForwardCoord = coord.xy + 4.0 * step;  \n}  \n", "precision mediump float;  \nuniform sampler2D inputImageTexture;  \nvarying vec2 textureCoordinate;  \nvarying vec2 oneBackCoord;  \nvarying vec2 twoBackCoord;  \nvarying vec2 threeBackCoord;  \nvarying vec2 fourBackCoord;  \nvarying vec2 oneForwardCoord;  \nvarying vec2 twoForwardCoord;  \nvarying vec2 threeForwardCoord;  \nvarying vec2 fourForwardCoord;  \nvoid main() {   \n\tlowp vec4 fragmentColor = texture2D(inputImageTexture, textureCoordinate) * 0.18;  \n\tfragmentColor += texture2D(inputImageTexture, oneBackCoord) * 0.15;  \n\tfragmentColor += texture2D(inputImageTexture, twoBackCoord) * 0.12;  \n\tfragmentColor += texture2D(inputImageTexture, threeBackCoord) * 0.09;  \n\tfragmentColor += texture2D(inputImageTexture, fourBackCoord) * 0.05;  \n\tfragmentColor += texture2D(inputImageTexture, oneForwardCoord) * 0.15;  \n\tfragmentColor += texture2D(inputImageTexture, twoForwardCoord) * 0.12;  \n\tfragmentColor += texture2D(inputImageTexture, threeForwardCoord) * 0.09;  \n\tfragmentColor += texture2D(inputImageTexture, fourForwardCoord) * 0.05;  \n\tgl_FragColor = fragmentColor;  \n}  \n");
        this.f2626o = true;
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: a */
    public boolean mo1321a() {
        if (!super.mo1321a()) {
            return false;
        }
        this.f4492u = GLES20.glGetUniformLocation(this.f2612a, "step");
        if (!this.f4489r.mo2653c()) {
            mo1351e();
            return false;
        }
        this.f4490s = GLES20.glGetUniformLocation(this.f4489r.m3011q(), "shift");
        this.f4491t = GLES20.glGetUniformLocation(this.f4489r.m3011q(), "alpha");
        return true;
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: a */
    public void mo1333a(int i, int i2) {
        super.mo1333a(i, i2);
        TXCGPUFilter tXCGPUFilter = this.f4489r;
        if (tXCGPUFilter != null) {
            tXCGPUFilter.mo1333a(i, i2);
        }
        if (i != 0) {
            float f = this.f4493v;
            if (f == 0.0d) {
                return;
            }
            m3030a(this.f4492u, new float[]{f / this.f2616e, 0.0f});
        }
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: e */
    public void mo1351e() {
        super.mo1351e();
        this.f4489r.mo1351e();
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: a */
    public void mo1353a(boolean z) {
        this.f4489r.mo1353a(z);
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: a */
    public int mo1355a(int i, int i2, int i3) {
        if (!this.f2618g) {
            return -1;
        }
        GLES20.glBindFramebuffer(36160, this.f2624m);
        mo1324a(i, this.f2619h, this.f2620i);
        TXCGPUFilter.AbstractC3355a abstractC3355a = this.f2623l;
        if (abstractC3355a instanceof TXCGPUFilter.AbstractC3355a) {
            abstractC3355a.mo458a(i3);
        }
        if (i2 != this.f2624m) {
            return this.f4489r.mo1355a(this.f2625n, i2, i3);
        }
        return this.f4489r.mo2294a(this.f2625n);
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: b */
    public int mo1352b(int i, FloatBuffer floatBuffer, FloatBuffer floatBuffer2) {
        if (!this.f2618g) {
            return -1;
        }
        GLES20.glBindFramebuffer(36160, this.f2624m);
        mo1324a(i, floatBuffer, floatBuffer2);
        TXCGPUFilter.AbstractC3355a abstractC3355a = this.f2623l;
        if (abstractC3355a instanceof TXCGPUFilter.AbstractC3355a) {
            abstractC3355a.mo458a(i);
        }
        this.f4489r.m3025b(this.f2625n);
        return 1;
    }

    /* renamed from: a */
    public void m1354a(TXCVideoEffect.C3538e c3538e) {
        m1356a(c3538e.f4605b, c3538e.f4604a, c3538e.f4606c);
    }

    /* renamed from: a */
    private void m1356a(float f, float f2, float f3) {
        this.f4493v = f;
        int i = this.f2616e;
        if (i != 0) {
            m3030a(this.f4492u, new float[]{f / i, 0.0f});
        }
        this.f4489r.m3035a(this.f4491t, f3);
        this.f4489r.m3035a(this.f4490s, f2);
    }
}
