package com.king.zxing.camera;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Handler;
import android.support.annotation.FloatRange;
import android.view.SurfaceHolder;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.king.zxing.camera.open.OpenCamera;
import com.king.zxing.camera.open.OpenCameraInterface;
import com.king.zxing.util.LogUtils;
import java.io.IOException;

/* loaded from: classes3.dex */
public final class CameraManager {
    private AutoFocusManager autoFocusManager;
    private OpenCamera camera;
    private final CameraConfigurationManager configManager;
    private final Context context;
    private Rect framingRect;
    private int framingRectHorizontalOffset;
    private Rect framingRectInPreview;
    private float framingRectRatio;
    private int framingRectVerticalOffset;
    private boolean initialized;
    private boolean isFullScreenScan;
    private boolean isTorch;
    private OnSensorListener onSensorListener;
    private OnTorchListener onTorchListener;
    private final PreviewCallback previewCallback;
    private boolean previewing;
    private int requestedCameraId = -1;
    private int requestedFramingRectHeight;
    private int requestedFramingRectWidth;

    /* loaded from: classes3.dex */
    public interface OnSensorListener {
        void onSensorChanged(boolean z, boolean z2, float f);
    }

    /* loaded from: classes3.dex */
    public interface OnTorchListener {
        void onTorchChanged(boolean z);
    }

    public CameraManager(Context context) {
        this.context = context.getApplicationContext();
        this.configManager = new CameraConfigurationManager(context);
        this.previewCallback = new PreviewCallback(this.configManager);
    }

    public void openDriver(SurfaceHolder surfaceHolder) throws IOException {
        int i;
        OpenCamera openCamera = this.camera;
        if (openCamera == null) {
            openCamera = OpenCameraInterface.open(this.requestedCameraId);
            if (openCamera == null) {
                throw new IOException("Camera.open() failed to return object from driver");
            }
            this.camera = openCamera;
        }
        if (!this.initialized) {
            this.initialized = true;
            this.configManager.initFromCameraParameters(openCamera);
            int i2 = this.requestedFramingRectWidth;
            if (i2 > 0 && (i = this.requestedFramingRectHeight) > 0) {
                setManualFramingRect(i2, i);
                this.requestedFramingRectWidth = 0;
                this.requestedFramingRectHeight = 0;
            }
        }
        Camera camera = openCamera.getCamera();
        Camera.Parameters parameters = camera.getParameters();
        String flatten = parameters == null ? null : parameters.flatten();
        try {
            this.configManager.setDesiredCameraParameters(openCamera, false);
        } catch (RuntimeException unused) {
            LogUtils.m3902w("Camera rejected parameters. Setting only minimal safe-mode parameters");
            LogUtils.m3903i("Resetting to saved camera params: " + flatten);
            if (flatten != null) {
                Camera.Parameters parameters2 = camera.getParameters();
                parameters2.unflatten(flatten);
                try {
                    camera.setParameters(parameters2);
                    this.configManager.setDesiredCameraParameters(openCamera, true);
                } catch (RuntimeException unused2) {
                    LogUtils.m3902w("Camera rejected even safe-mode parameters! No configuration");
                }
            }
        }
        camera.setPreviewDisplay(surfaceHolder);
    }

    public synchronized boolean isOpen() {
        return this.camera != null;
    }

    public OpenCamera getOpenCamera() {
        return this.camera;
    }

    public void closeDriver() {
        OpenCamera openCamera = this.camera;
        if (openCamera != null) {
            openCamera.getCamera().release();
            this.camera = null;
            this.framingRect = null;
            this.framingRectInPreview = null;
        }
        this.isTorch = false;
        OnTorchListener onTorchListener = this.onTorchListener;
        if (onTorchListener != null) {
            onTorchListener.onTorchChanged(false);
        }
    }

    public void startPreview() {
        OpenCamera openCamera = this.camera;
        if (openCamera == null || this.previewing) {
            return;
        }
        openCamera.getCamera().startPreview();
        this.previewing = true;
        this.autoFocusManager = new AutoFocusManager(this.context, openCamera.getCamera());
    }

    public void stopPreview() {
        AutoFocusManager autoFocusManager = this.autoFocusManager;
        if (autoFocusManager != null) {
            autoFocusManager.stop();
            this.autoFocusManager = null;
        }
        OpenCamera openCamera = this.camera;
        if (openCamera == null || !this.previewing) {
            return;
        }
        openCamera.getCamera().stopPreview();
        this.previewCallback.setHandler(null, 0);
        this.previewing = false;
    }

    public synchronized void setTorch(boolean z) {
        OpenCamera openCamera = this.camera;
        if (openCamera != null && z != this.configManager.getTorchState(openCamera.getCamera())) {
            boolean z2 = this.autoFocusManager != null;
            if (z2) {
                this.autoFocusManager.stop();
                this.autoFocusManager = null;
            }
            this.isTorch = z;
            this.configManager.setTorch(openCamera.getCamera(), z);
            if (z2) {
                this.autoFocusManager = new AutoFocusManager(this.context, openCamera.getCamera());
                this.autoFocusManager.start();
            }
            if (this.onTorchListener != null) {
                this.onTorchListener.onTorchChanged(z);
            }
        }
    }

    public synchronized void requestPreviewFrame(Handler handler, int i) {
        OpenCamera openCamera = this.camera;
        if (openCamera != null && this.previewing) {
            this.previewCallback.setHandler(handler, i);
            openCamera.getCamera().setOneShotPreviewCallback(this.previewCallback);
        }
    }

    public synchronized Rect getFramingRect() {
        if (this.framingRect == null) {
            if (this.camera == null) {
                return null;
            }
            Point cameraResolution = this.configManager.getCameraResolution();
            if (cameraResolution == null) {
                return null;
            }
            int i = cameraResolution.x;
            int i2 = cameraResolution.y;
            if (this.isFullScreenScan) {
                this.framingRect = new Rect(0, 0, i, i2);
            } else {
                int min = (int) (Math.min(i, i2) * this.framingRectRatio);
                int i3 = ((i - min) / 2) + this.framingRectHorizontalOffset;
                int i4 = ((i2 - min) / 2) + this.framingRectVerticalOffset;
                this.framingRect = new Rect(i3, i4, i3 + min, min + i4);
            }
        }
        return this.framingRect;
    }

    public synchronized Rect getFramingRectInPreview() {
        if (this.framingRectInPreview == null) {
            Rect framingRect = getFramingRect();
            if (framingRect == null) {
                return null;
            }
            Rect rect = new Rect(framingRect);
            Point cameraResolution = this.configManager.getCameraResolution();
            Point screenResolution = this.configManager.getScreenResolution();
            if (cameraResolution != null && screenResolution != null) {
                rect.left = (rect.left * cameraResolution.y) / screenResolution.x;
                rect.right = (rect.right * cameraResolution.y) / screenResolution.x;
                rect.top = (rect.top * cameraResolution.x) / screenResolution.y;
                rect.bottom = (rect.bottom * cameraResolution.x) / screenResolution.y;
                this.framingRectInPreview = rect;
            }
            return null;
        }
        return this.framingRectInPreview;
    }

    public void setFullScreenScan(boolean z) {
        this.isFullScreenScan = z;
    }

    public void setFramingRectRatio(@FloatRange(from = 0.0d, m5592to = 1.0d) float f) {
        this.framingRectRatio = f;
    }

    public void setFramingRectVerticalOffset(int i) {
        this.framingRectVerticalOffset = i;
    }

    public void setFramingRectHorizontalOffset(int i) {
        this.framingRectHorizontalOffset = i;
    }

    public Point getCameraResolution() {
        return this.configManager.getCameraResolution();
    }

    public Point getScreenResolution() {
        return this.configManager.getScreenResolution();
    }

    public synchronized void setManualFramingRect(int i, int i2) {
        if (this.initialized) {
            Point screenResolution = this.configManager.getScreenResolution();
            if (i > screenResolution.x) {
                i = screenResolution.x;
            }
            if (i2 > screenResolution.y) {
                i2 = screenResolution.y;
            }
            int i3 = (screenResolution.x - i) / 2;
            int i4 = (screenResolution.y - i2) / 2;
            this.framingRect = new Rect(i3, i4, i + i3, i2 + i4);
            LogUtils.m3904d("Calculated manual framing rect: " + this.framingRect);
            this.framingRectInPreview = null;
        } else {
            this.requestedFramingRectWidth = i;
            this.requestedFramingRectHeight = i2;
        }
    }

    public PlanarYUVLuminanceSource buildLuminanceSource(byte[] bArr, int i, int i2) {
        if (getFramingRectInPreview() == null) {
            return null;
        }
        if (this.isFullScreenScan) {
            return new PlanarYUVLuminanceSource(bArr, i, i2, 0, 0, i, i2, false);
        }
        int min = (int) (Math.min(i, i2) * this.framingRectRatio);
        return new PlanarYUVLuminanceSource(bArr, i, i2, ((i - min) / 2) + this.framingRectHorizontalOffset, ((i2 - min) / 2) + this.framingRectVerticalOffset, min, min, false);
    }

    public void setOnTorchListener(OnTorchListener onTorchListener) {
        this.onTorchListener = onTorchListener;
    }

    public void setOnSensorListener(OnSensorListener onSensorListener) {
        this.onSensorListener = onSensorListener;
    }

    public void sensorChanged(boolean z, float f) {
        OnSensorListener onSensorListener = this.onSensorListener;
        if (onSensorListener != null) {
            onSensorListener.onSensorChanged(this.isTorch, z, f);
        }
    }
}
