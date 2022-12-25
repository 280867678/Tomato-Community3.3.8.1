package com.tencent.liteav.beauty;

import android.util.Log;
import com.tencent.liteav.basic.util.TXCSystemUtil;
import java.nio.ByteBuffer;

/* loaded from: classes3.dex */
public class NativeLoad {
    private static final String TAG = "NativeLoad";

    public static native void OnLoadBeauty();

    public static native void nativeClearQueue();

    public static native void nativeDeleteYuv2Yuv();

    public static native void nativeGlMapBufferToQueue(int i, int i2, ByteBuffer byteBuffer);

    public static native void nativeGlReadPixs(int i, int i2, byte[] bArr);

    public static native boolean nativeGlReadPixsFromQueue(int i, int i2, byte[] bArr);

    public static native void nativeGlReadPixsToQueue(int i, int i2);

    public static native int nativeLoadGLProgram(int i);

    public static native void nativeglTexImage2D(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, byte[] bArr, int i9);

    /* renamed from: com.tencent.liteav.beauty.NativeLoad$a */
    /* loaded from: classes3.dex */
    private static class C3366a {

        /* renamed from: a */
        public static final NativeLoad f2759a = new NativeLoad();

        static {
            TXCSystemUtil.m2873e();
        }
    }

    private NativeLoad() {
        OnLoadBeauty();
        Log.e(TAG, "NativeLoad: load jni");
    }

    public static NativeLoad getInstance() {
        return C3366a.f2759a;
    }
}
