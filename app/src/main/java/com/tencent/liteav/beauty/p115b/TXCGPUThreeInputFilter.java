package com.tencent.liteav.beauty.p115b;

import android.opengl.GLES20;
import com.tencent.liteav.basic.p109e.TXCGPUFilter;
import com.tencent.liteav.basic.p109e.TXCRotation;
import com.tencent.liteav.basic.p109e.TXCTextureRotationUtil;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/* renamed from: com.tencent.liteav.beauty.b.ab */
/* loaded from: classes3.dex */
public class TXCGPUThreeInputFilter extends TXCGPUFilter {

    /* renamed from: r */
    public int f2951r;

    /* renamed from: s */
    public int f2952s;

    /* renamed from: t */
    public int f2953t;

    /* renamed from: u */
    public int f2954u;

    /* renamed from: v */
    public int f2955v;

    /* renamed from: w */
    public int f2956w;

    /* renamed from: x */
    private ByteBuffer f2957x;

    /* renamed from: y */
    private ByteBuffer f2958y;

    public TXCGPUThreeInputFilter(String str) {
        this("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\nattribute vec4 inputTextureCoordinate2;\nattribute vec4 inputTextureCoordinate3;\n \nvarying vec2 textureCoordinate;\nvarying vec2 textureCoordinate2;\nvarying vec2 textureCoordinate3;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = inputTextureCoordinate.xy;\n    textureCoordinate2 = inputTextureCoordinate2.xy;\n    textureCoordinate3 = inputTextureCoordinate3.xy;\n}", str);
        m2745a(TXCRotation.NORMAL, false, true);
    }

    public TXCGPUThreeInputFilter(String str, String str2) {
        super(str, str2);
        this.f2953t = -1;
        this.f2956w = -1;
        m2745a(TXCRotation.NORMAL, false, true);
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: a */
    public boolean mo1321a() {
        boolean mo1321a = super.mo1321a();
        GLES20.glUseProgram(m3011q());
        this.f2951r = GLES20.glGetAttribLocation(m3011q(), "inputTextureCoordinate2");
        this.f2954u = GLES20.glGetAttribLocation(m3011q(), "inputTextureCoordinate3");
        this.f2952s = GLES20.glGetUniformLocation(m3011q(), "inputImageTexture2");
        this.f2955v = GLES20.glGetUniformLocation(m3011q(), "inputImageTexture3");
        GLES20.glEnableVertexAttribArray(this.f2951r);
        GLES20.glEnableVertexAttribArray(this.f2954u);
        return mo1321a;
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: a */
    public int mo1355a(int i, int i2, int i3) {
        return m2746a(i, i2, i3, this.f2624m, this.f2625n);
    }

    /* renamed from: a */
    public int m2746a(int i, int i2, int i3, int i4, int i5) {
        this.f2953t = i2;
        this.f2956w = i3;
        return super.mo1355a(i, i4, i5);
    }

    /* renamed from: b */
    public int m2744b(int i, int i2, int i3) {
        this.f2953t = i2;
        this.f2956w = i3;
        return m3025b(i);
    }

    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: a */
    public void mo1333a(int i, int i2) {
        super.mo1333a(i, i2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tencent.liteav.basic.p109e.TXCGPUFilter
    /* renamed from: i */
    public void mo2657i() {
        GLES20.glEnableVertexAttribArray(this.f2951r);
        GLES20.glActiveTexture(33987);
        GLES20.glBindTexture(3553, this.f2953t);
        GLES20.glUniform1i(this.f2952s, 3);
        this.f2957x.position(0);
        GLES20.glVertexAttribPointer(this.f2951r, 2, 5126, false, 0, (Buffer) this.f2957x);
        GLES20.glEnableVertexAttribArray(this.f2954u);
        GLES20.glActiveTexture(33988);
        GLES20.glBindTexture(3553, this.f2956w);
        GLES20.glUniform1i(this.f2955v, 4);
        this.f2958y.position(0);
        GLES20.glVertexAttribPointer(this.f2954u, 2, 5126, false, 0, (Buffer) this.f2958y);
    }

    /* renamed from: a */
    public void m2745a(TXCRotation tXCRotation, boolean z, boolean z2) {
        float[] m2991a = TXCTextureRotationUtil.m2991a(tXCRotation, z, z2);
        ByteBuffer order = ByteBuffer.allocateDirect(32).order(ByteOrder.nativeOrder());
        FloatBuffer asFloatBuffer = order.asFloatBuffer();
        asFloatBuffer.put(m2991a);
        asFloatBuffer.flip();
        this.f2957x = order;
        ByteBuffer order2 = ByteBuffer.allocateDirect(32).order(ByteOrder.nativeOrder());
        FloatBuffer asFloatBuffer2 = order2.asFloatBuffer();
        asFloatBuffer2.put(m2991a);
        asFloatBuffer2.flip();
        this.f2958y = order2;
    }
}
