package com.googlecode.mp4parser.authoring.builder;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public class ByteBufferHelper {
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0085, code lost:
        if ((r1 instanceof java.nio.MappedByteBuffer) == false) goto L30;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x008d, code lost:
        if ((r0.get(r2) instanceof java.nio.MappedByteBuffer) == false) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x00a8, code lost:
        if (((java.nio.ByteBuffer) r0.get(r2)).limit() != (((java.nio.ByteBuffer) r0.get(r2)).capacity() - r1.capacity())) goto L22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x00aa, code lost:
        r2 = (java.nio.ByteBuffer) r0.get(r2);
        r2.limit(r1.limit() + r2.limit());
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static List<ByteBuffer> mergeAdjacentBuffers(List<ByteBuffer> list) {
        ArrayList arrayList = new ArrayList(list.size());
        Iterator<ByteBuffer> it2 = list.iterator();
        while (it2.hasNext()) {
            ByteBuffer next = it2.next();
            int size = arrayList.size() - 1;
            if (size >= 0 && next.hasArray() && ((ByteBuffer) arrayList.get(size)).hasArray() && next.array() == ((ByteBuffer) arrayList.get(size)).array() && ((ByteBuffer) arrayList.get(size)).arrayOffset() + ((ByteBuffer) arrayList.get(size)).limit() == next.arrayOffset()) {
                ByteBuffer byteBuffer = (ByteBuffer) arrayList.remove(size);
                arrayList.add(ByteBuffer.wrap(next.array(), byteBuffer.arrayOffset(), byteBuffer.limit() + next.limit()).slice());
            } else {
                next.reset();
                arrayList.add(next);
            }
        }
        return arrayList;
    }
}
