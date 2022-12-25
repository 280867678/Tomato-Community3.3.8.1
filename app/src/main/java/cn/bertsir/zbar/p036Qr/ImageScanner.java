package cn.bertsir.zbar.p036Qr;

/* renamed from: cn.bertsir.zbar.Qr.ImageScanner */
/* loaded from: classes2.dex */
public class ImageScanner {
    private long peer = create();

    private native long create();

    private native void destroy(long j);

    private native long getResults(long j);

    private static native void init();

    public native void enableCache(boolean z);

    public native void parseConfig(String str);

    public native int scanImage(Image image);

    public native void setConfig(int i, int i2, int i3) throws IllegalArgumentException;

    static {
        System.loadLibrary("zbar");
        init();
    }

    protected void finalize() {
        destroy();
    }

    public synchronized void destroy() {
        if (this.peer != 0) {
            destroy(this.peer);
            this.peer = 0L;
        }
    }

    public SymbolSet getResults() {
        return new SymbolSet(getResults(this.peer));
    }
}
