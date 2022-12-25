package p007b.p014c.p015a.p018c.p020b;

import com.koushikdutta.async.http.filter.ChunkedInputFilter;

/* JADX INFO: Access modifiers changed from: package-private */
/* renamed from: b.c.a.c.b.a */
/* loaded from: classes2.dex */
public /* synthetic */ class C0650a {

    /* renamed from: a */
    public static final /* synthetic */ int[] f250a = new int[ChunkedInputFilter.State.values().length];

    static {
        try {
            f250a[ChunkedInputFilter.State.CHUNK_LEN.ordinal()] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            f250a[ChunkedInputFilter.State.CHUNK_LEN_CR.ordinal()] = 2;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            f250a[ChunkedInputFilter.State.CHUNK.ordinal()] = 3;
        } catch (NoSuchFieldError unused3) {
        }
        try {
            f250a[ChunkedInputFilter.State.CHUNK_CR.ordinal()] = 4;
        } catch (NoSuchFieldError unused4) {
        }
        try {
            f250a[ChunkedInputFilter.State.CHUNK_CRLF.ordinal()] = 5;
        } catch (NoSuchFieldError unused5) {
        }
        try {
            f250a[ChunkedInputFilter.State.COMPLETE.ordinal()] = 6;
        } catch (NoSuchFieldError unused6) {
        }
    }
}
