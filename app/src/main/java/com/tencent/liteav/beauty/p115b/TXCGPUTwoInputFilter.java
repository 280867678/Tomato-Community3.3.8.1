package com.tencent.liteav.beauty.p115b;

import android.opengl.GLES20;
import com.tencent.liteav.basic.p109e.TXCGPUFilter;
import com.tencent.liteav.basic.p109e.TXCRotation;
import com.tencent.liteav.basic.p109e.TXCTextureRotationUtil;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/* renamed from: com.tencent.liteav.beauty.b.ac */
/* loaded from: classes3.dex */
public class TXCGPUTwoInputFilter extends TXCGPUFilter {

    /* renamed from: r */
    private ByteBuffer f2959r;

    /* renamed from: u */
    public int f2960u;

    /* renamed from: v */
    public int f2961v;

    /* renamed from: w */
    public int f2962w;

    public TXCGPUTwoInputFilter(String str) {
        this("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\nattribute vec4 inputTextureCoordinate2;\n \nvarying vec2 textureCoordinate;\nvarying vec2 textureCoordinate2;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = inputTextureCoordinate.xy;\n    textureCoordinate2 = inputTextureCoordinate2.xy;\n}", str);
    }

    public TXCGPUTwoInputFilter(String str, String str2) {
        super(str, str2);
        this.f2960u = -1;
        this.f2962w = -1;
        m2742a(TXCRotation.NORMAL, false, true);
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: a */
    public boolean mo1321a() {
        boolean mo1321a = super.mo1321a();
        if (mo1321a) {
            this.f2960u = GLES20.glGetAttribLocation(m3011q(), "inputTextureCoordinate2");
            this.f2961v = GLES20.glGetUniformLocation(m3011q(), "inputImageTexture2");
            GLES20.glEnableVertexAttribArray(this.f2960u);
        }
        return mo1321a;
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: b */
    public void mo2293b() {
        super.mo2293b();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: i */
    public void mo2657i() {
        GLES20.glActiveTexture(33987);
        GLES20.glBindTexture(3553, this.f2962w);
        GLES20.glUniform1i(this.f2961v, 3);
        int i = this.f2960u;
        if (i != -1) {
            GLES20.glEnableVertexAttribArray(i);
            this.f2959r.position(0);
            GLES20.glVertexAttribPointer(this.f2960u, 2, 5126, false, 0, (Buffer) this.f2959r);
        }
    }

    /* renamed from: a */
    public void m2742a(TXCRotation tXCRotation, boolean z, boolean z2) {
        float[] m2991a = TXCTextureRotationUtil.m2991a(tXCRotation, z, z2);
        ByteBuffer order = ByteBuffer.allocateDirect(32).order(ByteOrder.nativeOrder());
        FloatBuffer asFloatBuffer = order.asFloatBuffer();
        asFloatBuffer.put(m2991a);
        asFloatBuffer.flip();
        this.f2959r = order;
    }

    /* renamed from: c */
    public int m2741c(int i, int i2) {
        this.f2962w = i2;
        return mo1355a(i, this.f2624m, this.f2625n);
    }

    /* renamed from: a */
    public int m2743a(int i, int i2, int i3, int i4) {
        this.f2962w = i2;
        return mo1355a(i, i3, i4);
    }

    /* renamed from: d */
    public int m2740d(int i, int i2) {
        this.f2962w = i2;
        return m3025b(i);
    }
}
