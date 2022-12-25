package com.tencent.liteav.videoediter.audio;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* renamed from: com.tencent.liteav.videoediter.audio.b */
/* loaded from: classes3.dex */
public class BufferUtils {
    /* renamed from: a */
    public static short[] m551a(ByteBuffer byteBuffer, int i) {
        if (byteBuffer == null) {
            return null;
        }
        short[] sArr = new short[i / 2];
        byteBuffer.order(ByteOrder.nativeOrder()).asShortBuffer().get(sArr);
        return sArr;
    }

    /* renamed from: a */
    public static ByteBuffer m549a(short[] sArr) {
        ByteBuffer allocateDirect = ByteBuffer.allocateDirect(sArr.length * 2);
        allocateDirect.order(ByteOrder.nativeOrder()).asShortBuffer().put(sArr);
        allocateDirect.position(0);
        return allocateDirect;
    }

    /* renamed from: a */
    public static ByteBuffer m550a(ByteBuffer byteBuffer, short[] sArr) {
        int length = sArr.length * 2;
        if (byteBuffer != null && byteBuffer.capacity() >= length) {
            byteBuffer.position(0);
            byteBuffer.order(ByteOrder.nativeOrder()).asShortBuffer().put(sArr);
            byteBuffer.limit(length);
            return byteBuffer;
        }
        return m549a(sArr);
    }
}
