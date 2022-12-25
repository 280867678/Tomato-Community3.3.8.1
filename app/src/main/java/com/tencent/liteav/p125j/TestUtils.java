package com.tencent.liteav.p125j;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.os.Environment;
import com.tencent.liteav.basic.p109e.TXCGPUFilter;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* renamed from: com.tencent.liteav.j.d */
/* loaded from: classes3.dex */
public class TestUtils {

    /* renamed from: d */
    private static final String f4429d = Environment.getExternalStorageDirectory().getPath();

    /* renamed from: e */
    private static final String f4430e = f4429d + File.separator + "src.h264";

    /* renamed from: f */
    private static final String f4431f = f4429d + File.separator + "src.aac";

    /* renamed from: g */
    private static final String f4432g = f4429d + File.separator + "src.pcm";

    /* renamed from: a */
    public static FileOutputStream f4426a = null;

    /* renamed from: b */
    public static FileOutputStream f4427b = null;

    /* renamed from: c */
    public static FileOutputStream f4428c = null;

    /* renamed from: a */
    public static Bitmap m1430a(int i, int i2, int i3) {
        Bitmap createBitmap = Bitmap.createBitmap(i2, i3, Bitmap.Config.ARGB_8888);
        ByteBuffer order = ByteBuffer.allocate(i2 * i3 * 4).order(ByteOrder.nativeOrder());
        order.position(0);
        TXCGPUFilter tXCGPUFilter = new TXCGPUFilter();
        tXCGPUFilter.mo2653c();
        GLES20.glViewport(0, 0, i2, i3);
        tXCGPUFilter.m3025b(i);
        GLES20.glReadPixels(0, 0, i2, i3, 6408, 5121, order);
        createBitmap.copyPixelsFromBuffer(order);
        tXCGPUFilter.mo1351e();
        return createBitmap;
    }
}
