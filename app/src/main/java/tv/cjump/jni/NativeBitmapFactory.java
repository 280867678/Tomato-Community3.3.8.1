package tv.cjump.jni;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.support.p002v4.internal.view.SupportMenu;
import android.util.Log;
import java.lang.reflect.Field;

/* loaded from: classes4.dex */
public class NativeBitmapFactory {
    static Field nativeIntField = null;
    static boolean nativeLibLoaded = false;
    static boolean notLoadAgain = false;

    private static native Bitmap createBitmap(int i, int i2, int i3, boolean z);

    private static native Bitmap createBitmap19(int i, int i2, int i3, boolean z);

    private static native boolean init();

    private static native boolean release();

    public static void loadLibs() {
        if (notLoadAgain) {
            return;
        }
        if (!DeviceUtils.isRealARMArch() && !DeviceUtils.isRealX86Arch()) {
            notLoadAgain = true;
            nativeLibLoaded = false;
        } else if (nativeLibLoaded) {
        } else {
            try {
                if (Build.VERSION.SDK_INT >= 11 && Build.VERSION.SDK_INT < 23) {
                    System.loadLibrary("ndkbitmap");
                    nativeLibLoaded = true;
                } else {
                    notLoadAgain = true;
                    nativeLibLoaded = false;
                }
            } catch (Error e) {
                e.printStackTrace();
                notLoadAgain = true;
                nativeLibLoaded = false;
            } catch (Exception e2) {
                e2.printStackTrace();
                notLoadAgain = true;
                nativeLibLoaded = false;
            }
            if (nativeLibLoaded) {
                if (!init()) {
                    release();
                    notLoadAgain = true;
                    nativeLibLoaded = false;
                } else {
                    initField();
                    if (!testLib()) {
                        release();
                        notLoadAgain = true;
                        nativeLibLoaded = false;
                    }
                }
            }
            Log.e("NativeBitmapFactory", "loaded" + nativeLibLoaded);
        }
    }

    public static synchronized void releaseLibs() {
        synchronized (NativeBitmapFactory.class) {
            boolean z = nativeLibLoaded;
            nativeIntField = null;
            nativeLibLoaded = false;
            if (z) {
                release();
            }
        }
    }

    static void initField() {
        try {
            nativeIntField = Bitmap.Config.class.getDeclaredField("nativeInt");
            nativeIntField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            nativeIntField = null;
            e.printStackTrace();
        }
    }

    @SuppressLint({"NewApi"})
    private static boolean testLib() {
        if (nativeIntField == null) {
            return false;
        }
        Bitmap bitmap = null;
        try {
            try {
                bitmap = createNativeBitmap(2, 2, Bitmap.Config.ARGB_8888, true);
                boolean z = bitmap != null && bitmap.getWidth() == 2 && bitmap.getHeight() == 2;
                if (z) {
                    if (Build.VERSION.SDK_INT >= 17 && !bitmap.isPremultiplied()) {
                        bitmap.setPremultiplied(true);
                    }
                    Canvas canvas = new Canvas(bitmap);
                    Paint paint = new Paint();
                    paint.setColor(SupportMenu.CATEGORY_MASK);
                    paint.setTextSize(20.0f);
                    canvas.drawRect(0.0f, 0.0f, bitmap.getWidth(), bitmap.getHeight(), paint);
                    canvas.drawText("TestLib", 0.0f, 0.0f, paint);
                    if (Build.VERSION.SDK_INT >= 17) {
                        z = bitmap.isPremultiplied();
                    }
                }
                if (bitmap != null) {
                    bitmap.recycle();
                }
                return z;
            } catch (Error unused) {
                if (bitmap != null) {
                    bitmap.recycle();
                }
                return false;
            } catch (Exception e) {
                Log.e("NativeBitmapFactory", "exception:" + e.toString());
                if (bitmap != null) {
                    bitmap.recycle();
                }
                return false;
            }
        } catch (Throwable th) {
            if (bitmap != null) {
                bitmap.recycle();
            }
            throw th;
        }
    }

    public static int getNativeConfig(Bitmap.Config config) {
        try {
            if (nativeIntField != null) {
                return nativeIntField.getInt(config);
            }
            return 0;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return 0;
        } catch (IllegalArgumentException e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    public static Bitmap createBitmap(int i, int i2, Bitmap.Config config) {
        return createBitmap(i, i2, config, config.equals(Bitmap.Config.ARGB_4444) || config.equals(Bitmap.Config.ARGB_8888));
    }

    public static synchronized Bitmap createBitmap(int i, int i2, Bitmap.Config config, boolean z) {
        synchronized (NativeBitmapFactory.class) {
            if (nativeLibLoaded && nativeIntField != null) {
                return createNativeBitmap(i, i2, config, z);
            }
            return Bitmap.createBitmap(i, i2, config);
        }
    }

    private static Bitmap createNativeBitmap(int i, int i2, Bitmap.Config config, boolean z) {
        int nativeConfig = getNativeConfig(config);
        return Build.VERSION.SDK_INT == 19 ? createBitmap19(i, i2, nativeConfig, z) : createBitmap(i, i2, nativeConfig, z);
    }
}
