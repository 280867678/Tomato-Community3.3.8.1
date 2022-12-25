package cn.bertsir.zbar.p036Qr;

import cn.bertsir.zbar.format_control.BarcodeFormat;
import cn.bertsir.zbar.format_control.BarcodeType;
import java.util.List;

/* renamed from: cn.bertsir.zbar.Qr.Symbol */
/* loaded from: classes2.dex */
public class Symbol {
    public static final int CODABAR = 38;
    public static final int CODE128 = 128;
    public static final int CODE39 = 39;
    public static final int CODE93 = 93;
    public static final int DATABAR = 34;
    public static final int DATABAR_EXP = 35;
    public static final int EAN13 = 13;
    public static final int EAN8 = 8;
    public static final int I25 = 25;
    public static final int ISBN10 = 10;
    public static final int ISBN13 = 14;
    public static final int NONE = 0;
    public static final int PARTIAL = 1;
    public static final int PDF417 = 57;
    public static final int QRCODE = 64;
    public static final int UPCA = 12;
    public static final int UPCE = 9;
    public static int cropHeight;
    public static int cropWidth;
    public static int cropX;
    public static int cropY;
    public static List<BarcodeFormat> scanFormat;
    public static int screenHeight;
    public static int screenWidth;
    private long peer;
    private int type;
    public static BarcodeType scanType = BarcodeType.ALL;
    public static boolean is_only_scan_center = false;
    public static boolean is_auto_zoom = false;
    public static boolean doubleEngine = false;
    public static boolean looperScan = false;
    public static int looperWaitTime = 0;

    private native void destroy(long j);

    private native long getComponents(long j);

    private native int getLocationSize(long j);

    private native int getLocationX(long j, int i);

    private native int getLocationY(long j, int i);

    private native int getType(long j);

    private static native void init();

    public native int getConfigMask();

    public native int getCount();

    public native String getData();

    public native byte[] getDataBytes();

    public native int getModifierMask();

    public native int getOrientation();

    public native int getQuality();

    /* JADX INFO: Access modifiers changed from: package-private */
    public native long next();

    static {
        System.loadLibrary("zbar");
        init();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Symbol(long j) {
        this.peer = j;
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

    public int getType() {
        if (this.type == 0) {
            this.type = getType(this.peer);
        }
        return this.type;
    }

    public int[] getBounds() {
        int locationSize = getLocationSize(this.peer);
        if (locationSize <= 0) {
            return null;
        }
        int[] iArr = new int[4];
        int i = Integer.MAX_VALUE;
        int i2 = Integer.MAX_VALUE;
        int i3 = Integer.MIN_VALUE;
        int i4 = Integer.MIN_VALUE;
        for (int i5 = 0; i5 < locationSize; i5++) {
            int locationX = getLocationX(this.peer, i5);
            if (i > locationX) {
                i = locationX;
            }
            if (i3 < locationX) {
                i3 = locationX;
            }
            int locationY = getLocationY(this.peer, i5);
            if (i2 > locationY) {
                i2 = locationY;
            }
            if (i4 < locationY) {
                i4 = locationY;
            }
        }
        iArr[0] = i;
        iArr[1] = i2;
        iArr[2] = i3 - i;
        iArr[3] = i4 - i2;
        return iArr;
    }

    public int[] getLocationPoint(int i) {
        return new int[]{getLocationX(this.peer, i), getLocationY(this.peer, i)};
    }

    public SymbolSet getComponents() {
        return new SymbolSet(getComponents(this.peer));
    }
}
