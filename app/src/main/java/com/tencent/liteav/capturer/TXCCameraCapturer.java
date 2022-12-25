package com.tencent.liteav.capturer;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Build;
import android.support.p002v4.app.NotificationManagerCompat;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.p110f.TXCConfigCenter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import org.slf4j.Marker;

/* renamed from: com.tencent.liteav.capturer.a */
/* loaded from: classes3.dex */
public class TXCCameraCapturer implements Camera.AutoFocusCallback, Camera.ErrorCallback {

    /* renamed from: c */
    private static final String f3398c = TXCCameraCapturer.class.getSimpleName();

    /* renamed from: d */
    private Camera f3401d;

    /* renamed from: g */
    private int f3404g;

    /* renamed from: i */
    private int f3406i;

    /* renamed from: j */
    private int f3407j;

    /* renamed from: k */
    private int f3408k;

    /* renamed from: l */
    private int f3409l;

    /* renamed from: m */
    private SurfaceTexture f3410m;

    /* renamed from: n */
    private boolean f3411n;

    /* renamed from: o */
    private boolean f3412o;

    /* renamed from: p */
    private boolean f3413p;

    /* renamed from: a */
    private Matrix f3399a = new Matrix();

    /* renamed from: b */
    private int f3400b = 0;

    /* renamed from: e */
    private boolean f3402e = true;

    /* renamed from: f */
    private int f3403f = 15;

    /* renamed from: h */
    private int f3405h = 1;

    /* renamed from: q */
    private boolean f3414q = false;

    /* renamed from: a */
    public void m2402a(SurfaceTexture surfaceTexture) {
        this.f3410m = surfaceTexture;
    }

    /* renamed from: a */
    public boolean m2401a(boolean z) {
        this.f3413p = z;
        Camera camera = this.f3401d;
        if (camera != null) {
            boolean z2 = true;
            Camera.Parameters parameters = camera.getParameters();
            List<String> supportedFlashModes = parameters.getSupportedFlashModes();
            if (z) {
                if (supportedFlashModes != null && supportedFlashModes.contains("torch")) {
                    TXCLog.m2913i(f3398c, "set FLASH_MODE_TORCH");
                    parameters.setFlashMode("torch");
                }
                z2 = false;
            } else {
                if (supportedFlashModes != null && supportedFlashModes.contains("off")) {
                    TXCLog.m2913i(f3398c, "set FLASH_MODE_OFF");
                    parameters.setFlashMode("off");
                }
                z2 = false;
            }
            try {
                this.f3401d.setParameters(parameters);
                return z2;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    /* renamed from: a */
    public void m2403a(int i) {
        this.f3404g = i;
    }

    /* renamed from: b */
    public void m2399b(int i) {
        this.f3403f = i;
    }

    /* renamed from: a */
    public void m2405a(float f, float f2) {
        if (!this.f3414q) {
            return;
        }
        try {
            this.f3401d.cancelAutoFocus();
            Camera.Parameters parameters = this.f3401d.getParameters();
            if (this.f3411n) {
                ArrayList arrayList = new ArrayList();
                arrayList.add(new Camera.Area(m2404a(f, f2, 2.0f), 1000));
                parameters.setFocusAreas(arrayList);
            }
            if (this.f3412o) {
                ArrayList arrayList2 = new ArrayList();
                arrayList2.add(new Camera.Area(m2404a(f, f2, 3.0f), 1000));
                parameters.setMeteringAreas(arrayList2);
            }
            this.f3401d.setParameters(parameters);
            this.f3401d.autoFocus(this);
        } catch (Exception unused) {
        }
    }

    /* renamed from: b */
    public void m2398b(boolean z) {
        this.f3414q = z;
    }

    /* renamed from: a */
    private Rect m2404a(float f, float f2, float f3) {
        float f4 = f3 * 200.0f;
        if (this.f3402e) {
            f = 1.0f - f;
        }
        int i = 0;
        while (i < this.f3408k / 90) {
            float f5 = (-(-(f2 - 0.5f))) + 0.5f;
            i++;
            f2 = (-(f - 0.5f)) + 0.5f;
            f = f5;
        }
        int i2 = (int) ((f * 2000.0f) - 1000.0f);
        int i3 = (int) ((f2 * 2000.0f) - 1000.0f);
        if (i2 < -1000) {
            i2 = NotificationManagerCompat.IMPORTANCE_UNSPECIFIED;
        }
        if (i3 < -1000) {
            i3 = NotificationManagerCompat.IMPORTANCE_UNSPECIFIED;
        }
        int i4 = (int) f4;
        int i5 = i2 + i4;
        int i6 = i4 + i3;
        if (i5 > 1000) {
            i5 = 1000;
        }
        if (i6 > 1000) {
            i6 = 1000;
        }
        return new Rect(i2, i3, i5, i6);
    }

    /* renamed from: a */
    public int m2407a() {
        Camera camera = this.f3401d;
        if (camera != null) {
            Camera.Parameters parameters = camera.getParameters();
            if (parameters.getMaxZoom() > 0 && parameters.isZoomSupported()) {
                return parameters.getMaxZoom();
            }
        }
        return 0;
    }

    /* renamed from: c */
    public boolean m2396c(int i) {
        Camera camera = this.f3401d;
        if (camera != null) {
            Camera.Parameters parameters = camera.getParameters();
            if (parameters.getMaxZoom() > 0 && parameters.isZoomSupported()) {
                if (i >= 0 && i <= parameters.getMaxZoom()) {
                    try {
                        parameters.setZoom(i);
                        this.f3401d.setParameters(parameters);
                        return true;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return false;
                    }
                }
                String str = f3398c;
                TXCLog.m2914e(str, "invalid zoom value : " + i + ", while max zoom is " + parameters.getMaxZoom());
                return false;
            }
            TXCLog.m2914e(f3398c, "camera not support zoom!");
            return false;
        }
        return false;
    }

    /* renamed from: a */
    public void m2406a(float f) {
        if (this.f3401d != null) {
            if (f > 1.0f) {
                f = 1.0f;
            }
            if (f < -1.0f) {
                f = -1.0f;
            }
            Camera.Parameters parameters = this.f3401d.getParameters();
            int minExposureCompensation = parameters.getMinExposureCompensation();
            int maxExposureCompensation = parameters.getMaxExposureCompensation();
            if (minExposureCompensation != 0 && maxExposureCompensation != 0) {
                int m2966d = TXCConfigCenter.m2988a().m2966d();
                float f2 = maxExposureCompensation;
                float f3 = f * f2;
                if (m2966d != 0 && m2966d <= maxExposureCompensation && m2966d >= minExposureCompensation) {
                    TXCLog.m2915d(f3398c, "camera setExposureCompensation: " + m2966d);
                    parameters.setExposureCompensation(m2966d);
                } else if (f3 <= f2 && f3 >= minExposureCompensation) {
                    TXCLog.m2915d(f3398c, "camera setExposureCompensation: " + f3);
                    parameters.setExposureCompensation((int) f3);
                }
            } else {
                TXCLog.m2914e(f3398c, "camera not support setExposureCompensation!");
            }
            try {
                this.f3401d.setParameters(parameters);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* renamed from: d */
    public void m2393d(int i) {
        this.f3405h = i;
        this.f3408k = (((this.f3409l - 90) + (this.f3405h * 90)) + 360) % 360;
    }

    /* renamed from: c */
    public int m2395c(boolean z) {
        try {
            if (this.f3410m == null) {
                return -2;
            }
            if (this.f3401d != null) {
                m2400b();
            }
            Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
            int i = -1;
            int i2 = -1;
            for (int i3 = 0; i3 < Camera.getNumberOfCameras(); i3++) {
                Camera.getCameraInfo(i3, cameraInfo);
                TXCLog.m2913i(f3398c, "camera index " + i3 + ", facing = " + cameraInfo.facing);
                if (cameraInfo.facing == 1) {
                    i = i3;
                }
                if (cameraInfo.facing == 0) {
                    i2 = i3;
                }
            }
            TXCLog.m2913i(f3398c, "camera front, id = " + i);
            TXCLog.m2913i(f3398c, "camera back , id = " + i2);
            if (i == -1 && i2 != -1) {
                i = i2;
            }
            if (i2 == -1 && i != -1) {
                i2 = i;
            }
            this.f3402e = z;
            if (this.f3402e) {
                this.f3401d = Camera.open(i);
            } else {
                this.f3401d = Camera.open(i2);
            }
            Camera.Parameters parameters = this.f3401d.getParameters();
            List<String> supportedFocusModes = parameters.getSupportedFocusModes();
            if (this.f3414q && supportedFocusModes != null && supportedFocusModes.contains("auto")) {
                TXCLog.m2913i(f3398c, "support FOCUS_MODE_AUTO");
                parameters.setFocusMode("auto");
            } else if (supportedFocusModes != null && supportedFocusModes.contains("continuous-video")) {
                TXCLog.m2913i(f3398c, "support FOCUS_MODE_CONTINUOUS_VIDEO");
                parameters.setFocusMode("continuous-video");
            }
            if (Build.VERSION.SDK_INT >= 14) {
                if (parameters.getMaxNumFocusAreas() > 0) {
                    this.f3411n = true;
                }
                if (parameters.getMaxNumMeteringAreas() > 0) {
                    this.f3412o = true;
                }
            }
            String str = "";
            List<Camera.Size> supportedPreviewSizes = parameters.getSupportedPreviewSizes();
            if (supportedPreviewSizes != null) {
                String str2 = str;
                for (int i4 = 0; i4 < supportedPreviewSizes.size(); i4++) {
                    Camera.Size size = supportedPreviewSizes.get(i4);
                    str2 = str2 + String.format("camera supported preview size %d x %d\n", Integer.valueOf(size.width), Integer.valueOf(size.height));
                }
                str = str2;
            }
            C3413a m2391e = m2391e(this.f3404g);
            if (m2391e == null) {
                this.f3401d.release();
                this.f3401d = null;
                TXCLog.m2915d(f3398c, "不支持的视频分辨率");
                return -3;
            }
            TXCLog.m2913i(f3398c, str + String.format("choose preview size %d x %d ", Integer.valueOf(m2391e.f3416a), Integer.valueOf(m2391e.f3417b)));
            this.f3406i = m2391e.f3416a;
            this.f3407j = m2391e.f3417b;
            parameters.setPreviewSize(m2391e.f3416a, m2391e.f3417b);
            int[] m2388g = m2388g(this.f3403f);
            if (m2388g != null) {
                parameters.setPreviewFpsRange(m2388g[0], m2388g[1]);
            } else {
                parameters.setPreviewFrameRate(m2389f(this.f3403f));
            }
            if (!this.f3402e) {
                i = i2;
            }
            this.f3409l = m2387h(i);
            this.f3408k = (((this.f3409l - 90) + (this.f3405h * 90)) + 360) % 360;
            this.f3401d.setDisplayOrientation(0);
            this.f3401d.setPreviewTexture(this.f3410m);
            this.f3401d.setParameters(parameters);
            this.f3401d.setErrorCallback(this);
            this.f3401d.startPreview();
            return 0;
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        } catch (Exception e2) {
            e2.printStackTrace();
            return -1;
        }
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [android.graphics.SurfaceTexture, android.hardware.Camera] */
    /* renamed from: b */
    public void m2400b() {
        Camera camera = this.f3401d;
        if (camera != null) {
            try {
                try {
                    camera.setErrorCallback(null);
                    this.f3401d.setPreviewCallback(null);
                    this.f3401d.stopPreview();
                    this.f3401d.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } finally {
                this.f3401d = null;
                this.f3410m = null;
            }
        }
    }

    /* renamed from: c */
    public int m2397c() {
        return this.f3408k;
    }

    /* renamed from: d */
    public boolean m2394d() {
        return this.f3402e;
    }

    /* renamed from: e */
    public int m2392e() {
        return this.f3406i;
    }

    /* renamed from: f */
    public int m2390f() {
        return this.f3407j;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* renamed from: e */
    private C3413a m2391e(int i) {
        List<Camera.Size> supportedPreviewSizes = this.f3401d.getParameters().getSupportedPreviewSizes();
        ArrayList arrayList = new ArrayList();
        switch (i) {
            case 1:
            case 2:
            case 4:
                arrayList.add(new C3413a(640, 360));
                arrayList.add(new C3413a(768, 432));
                arrayList.add(new C3413a(960, 544));
                arrayList.add(new C3413a(960, 540));
                arrayList.add(new C3413a(800, 480));
                arrayList.add(new C3413a(640, 480));
                arrayList.add(new C3413a(960, 720));
                arrayList.add(new C3413a(1280, 720));
                break;
            case 3:
                arrayList.add(new C3413a(480, 320));
                arrayList.add(new C3413a(640, 360));
                arrayList.add(new C3413a(640, 480));
                arrayList.add(new C3413a(768, 432));
                break;
            case 5:
                arrayList.add(new C3413a(960, 544));
                arrayList.add(new C3413a(960, 540));
                arrayList.add(new C3413a(960, 720));
                arrayList.add(new C3413a(1280, 720));
                arrayList.add(new C3413a(800, 480));
                arrayList.add(new C3413a(640, 360));
                arrayList.add(new C3413a(640, 480));
                break;
            case 6:
                arrayList.add(new C3413a(1280, 720));
                arrayList.add(new C3413a(1920, 1088));
                arrayList.add(new C3413a(1920, 1080));
                arrayList.add(new C3413a(960, 544));
                arrayList.add(new C3413a(960, 540));
                arrayList.add(new C3413a(960, 720));
                arrayList.add(new C3413a(800, 480));
                arrayList.add(new C3413a(640, 360));
                arrayList.add(new C3413a(640, 480));
                arrayList.add(new C3413a(480, 320));
                arrayList.add(new C3413a(640, 360));
                arrayList.add(new C3413a(640, 480));
                arrayList.add(new C3413a(768, 432));
                break;
            case 7:
                arrayList.add(new C3413a(1920, 1088));
                arrayList.add(new C3413a(1920, 1080));
                arrayList.add(new C3413a(1280, 720));
                arrayList.add(new C3413a(960, 544));
                arrayList.add(new C3413a(960, 540));
                arrayList.add(new C3413a(960, 720));
                arrayList.add(new C3413a(800, 480));
                arrayList.add(new C3413a(768, 432));
                arrayList.add(new C3413a(640, 360));
                arrayList.add(new C3413a(640, 480));
                break;
        }
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            C3413a c3413a = (C3413a) arrayList.get(i2);
            for (int i3 = 0; i3 < supportedPreviewSizes.size(); i3++) {
                Camera.Size size = supportedPreviewSizes.get(i3);
                if (size.width == c3413a.f3416a && size.height == c3413a.f3417b) {
                    String str = f3398c;
                    TXCLog.m2911w(str, "wanted:" + c3413a.f3416a + Marker.ANY_MARKER + c3413a.f3417b);
                    return c3413a;
                }
            }
        }
        return null;
    }

    @Override // android.hardware.Camera.AutoFocusCallback
    public void onAutoFocus(boolean z, Camera camera) {
        if (z) {
            TXCLog.m2913i(f3398c, "AUTO focus success");
        } else {
            TXCLog.m2913i(f3398c, "AUTO focus failed");
        }
    }

    @Override // android.hardware.Camera.ErrorCallback
    public void onError(int i, Camera camera) {
        String str = f3398c;
        TXCLog.m2911w(str, "camera catch error " + i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: TXCCameraCapturer.java */
    /* renamed from: com.tencent.liteav.capturer.a$a */
    /* loaded from: classes3.dex */
    public class C3413a {

        /* renamed from: a */
        public int f3416a;

        /* renamed from: b */
        public int f3417b;

        C3413a(int i, int i2) {
            this.f3416a = 1280;
            this.f3417b = 720;
            this.f3416a = i;
            this.f3417b = i2;
        }
    }

    /* renamed from: f */
    private int m2389f(int i) {
        List<Integer> supportedPreviewFrameRates = this.f3401d.getParameters().getSupportedPreviewFrameRates();
        if (supportedPreviewFrameRates == null) {
            TXCLog.m2914e(f3398c, "getSupportedFPS error");
            return 1;
        }
        int intValue = supportedPreviewFrameRates.get(0).intValue();
        for (int i2 = 0; i2 < supportedPreviewFrameRates.size(); i2++) {
            int intValue2 = supportedPreviewFrameRates.get(i2).intValue();
            if (Math.abs(intValue2 - i) - Math.abs(intValue - i) < 0) {
                intValue = intValue2;
            }
        }
        String str = f3398c;
        TXCLog.m2913i(str, "choose fpts=" + intValue);
        return intValue;
    }

    /* renamed from: g */
    private int[] m2388g(int i) {
        int[] iArr;
        int i2 = i * 1000;
        String str = "camera supported preview fps range: wantFPS = " + i2 + "\n";
        List<int[]> supportedPreviewFpsRange = this.f3401d.getParameters().getSupportedPreviewFpsRange();
        if (supportedPreviewFpsRange == null || supportedPreviewFpsRange.size() <= 0) {
            return null;
        }
        int[] iArr2 = supportedPreviewFpsRange.get(0);
        Collections.sort(supportedPreviewFpsRange, new Comparator<int[]>() { // from class: com.tencent.liteav.capturer.a.1
            @Override // java.util.Comparator
            /* renamed from: a */
            public int compare(int[] iArr3, int[] iArr4) {
                return iArr3[1] - iArr4[1];
            }
        });
        for (int[] iArr3 : supportedPreviewFpsRange) {
            str = str + "camera supported preview fps range: " + iArr3[0] + " - " + iArr3[1] + "\n";
        }
        Iterator<int[]> it2 = supportedPreviewFpsRange.iterator();
        while (true) {
            if (!it2.hasNext()) {
                iArr = iArr2;
                break;
            }
            iArr = it2.next();
            if (iArr[0] <= i2 && i2 <= iArr[1]) {
                break;
            }
        }
        TXCLog.m2913i(f3398c, str + "choose preview fps range: " + iArr[0] + " - " + iArr[1]);
        return iArr;
    }

    /* renamed from: h */
    private int m2387h(int i) {
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        Camera.getCameraInfo(i, cameraInfo);
        if (cameraInfo.facing == 1) {
            return (360 - cameraInfo.orientation) % 360;
        }
        return (cameraInfo.orientation + 360) % 360;
    }
}
