package com.king.zxing;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.support.p002v4.app.Fragment;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.Result;
import com.king.zxing.camera.CameraManager;
import com.king.zxing.util.LogUtils;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

/* loaded from: classes3.dex */
public class CaptureHelper implements CaptureLifecycle, CaptureTouchEvent, CaptureManager, SurfaceHolder.Callback {
    private Activity activity;
    private AmbientLightManager ambientLightManager;
    private BeepManager beepManager;
    private float brightEnoughLux;
    private CameraManager cameraManager;
    private CaptureHandler captureHandler;
    private String characterSet;
    private Collection<BarcodeFormat> decodeFormats;
    private Map<DecodeHintType, Object> decodeHints;
    private int framingRectHorizontalOffset;
    private float framingRectRatio;
    private int framingRectVerticalOffset;
    private boolean hasCameraFlash;
    private boolean hasSurface;
    private InactivityTimer inactivityTimer;
    private boolean isAutoRestartPreviewAndDecode;
    private boolean isContinuousScan;
    private boolean isFullScreenScan;
    private boolean isPlayBeep;
    private boolean isReturnBitmap;
    private boolean isSupportAutoZoom;
    private boolean isSupportLuminanceInvert;
    private boolean isSupportVerticalCode;
    private boolean isSupportZoom;
    private boolean isVibrate;
    private View ivTorch;
    private float oldDistance;
    private OnCaptureCallback onCaptureCallback;
    private OnCaptureListener onCaptureListener;
    private SurfaceHolder surfaceHolder;
    private SurfaceView surfaceView;
    private float tooDarkLux;
    private ViewfinderView viewfinderView;

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
    }

    public CaptureHelper(Fragment fragment, SurfaceView surfaceView, ViewfinderView viewfinderView, View view) {
        this(fragment.getActivity(), surfaceView, viewfinderView, view);
    }

    public CaptureHelper(Activity activity, SurfaceView surfaceView, ViewfinderView viewfinderView, View view) {
        this.isSupportZoom = true;
        this.isSupportAutoZoom = true;
        this.isSupportLuminanceInvert = false;
        this.isContinuousScan = false;
        this.isAutoRestartPreviewAndDecode = true;
        this.framingRectRatio = 0.9f;
        this.tooDarkLux = 45.0f;
        this.brightEnoughLux = 100.0f;
        this.activity = activity;
        this.surfaceView = surfaceView;
        this.viewfinderView = viewfinderView;
        this.ivTorch = view;
    }

    public void onCreate() {
        this.surfaceHolder = this.surfaceView.getHolder();
        this.hasSurface = false;
        this.inactivityTimer = new InactivityTimer(this.activity);
        this.beepManager = new BeepManager(this.activity);
        this.ambientLightManager = new AmbientLightManager(this.activity);
        this.hasCameraFlash = this.activity.getPackageManager().hasSystemFeature("android.hardware.camera.flash");
        initCameraManager();
        this.onCaptureListener = new OnCaptureListener() { // from class: com.king.zxing.-$$Lambda$CaptureHelper$M1LKX0hZL5VGLrV8hfodXcHppF8
            @Override // com.king.zxing.OnCaptureListener
            public final void onHandleDecode(Result result, Bitmap bitmap, float f) {
                CaptureHelper.this.lambda$onCreate$0$CaptureHelper(result, bitmap, f);
            }
        };
        this.beepManager.setPlayBeep(this.isPlayBeep);
        this.beepManager.setVibrate(this.isVibrate);
        this.ambientLightManager.setTooDarkLux(this.tooDarkLux);
        this.ambientLightManager.setBrightEnoughLux(this.brightEnoughLux);
    }

    public /* synthetic */ void lambda$onCreate$0$CaptureHelper(Result result, Bitmap bitmap, float f) {
        this.inactivityTimer.onActivity();
        this.beepManager.playBeepSoundAndVibrate();
        onResult(result, bitmap, f);
    }

    public void onResume() {
        this.beepManager.updatePrefs();
        this.inactivityTimer.onResume();
        if (this.hasSurface) {
            initCamera(this.surfaceHolder);
        } else {
            this.surfaceHolder.addCallback(this);
        }
        this.ambientLightManager.start(this.cameraManager);
    }

    public void onPause() {
        CaptureHandler captureHandler = this.captureHandler;
        if (captureHandler != null) {
            captureHandler.quitSynchronously();
            this.captureHandler = null;
        }
        this.inactivityTimer.onPause();
        this.ambientLightManager.stop();
        this.beepManager.close();
        this.cameraManager.closeDriver();
        if (!this.hasSurface) {
            this.surfaceHolder.removeCallback(this);
        }
        View view = this.ivTorch;
        if (view == null || view.getVisibility() != 0) {
            return;
        }
        this.ivTorch.setSelected(false);
        this.ivTorch.setVisibility(4);
    }

    public void onDestroy() {
        this.inactivityTimer.shutdown();
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        Camera camera;
        if (!this.isSupportZoom || !this.cameraManager.isOpen() || (camera = this.cameraManager.getOpenCamera().getCamera()) == null || motionEvent.getPointerCount() <= 1) {
            return false;
        }
        int action = motionEvent.getAction() & 255;
        if (action == 2) {
            float calcFingerSpacing = calcFingerSpacing(motionEvent);
            float f = this.oldDistance;
            if (calcFingerSpacing > f + 6.0f) {
                handleZoom(true, camera);
            } else if (calcFingerSpacing < f - 6.0f) {
                handleZoom(false, camera);
            }
            this.oldDistance = calcFingerSpacing;
        } else if (action == 5) {
            this.oldDistance = calcFingerSpacing(motionEvent);
        }
        return true;
    }

    private void initCameraManager() {
        this.cameraManager = new CameraManager(this.activity);
        this.cameraManager.setFullScreenScan(this.isFullScreenScan);
        this.cameraManager.setFramingRectRatio(this.framingRectRatio);
        this.cameraManager.setFramingRectVerticalOffset(this.framingRectVerticalOffset);
        this.cameraManager.setFramingRectHorizontalOffset(this.framingRectHorizontalOffset);
        View view = this.ivTorch;
        if (view == null || !this.hasCameraFlash) {
            return;
        }
        view.setOnClickListener(new View.OnClickListener() { // from class: com.king.zxing.-$$Lambda$CaptureHelper$i8cvNEWL6OlZjVbzRDtr3lazGZc
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                CaptureHelper.this.lambda$initCameraManager$1$CaptureHelper(view2);
            }
        });
        this.cameraManager.setOnSensorListener(new CameraManager.OnSensorListener() { // from class: com.king.zxing.-$$Lambda$CaptureHelper$qZVOqVqKk6calUaIsNPo5S2Svww
            @Override // com.king.zxing.camera.CameraManager.OnSensorListener
            public final void onSensorChanged(boolean z, boolean z2, float f) {
                CaptureHelper.this.lambda$initCameraManager$2$CaptureHelper(z, z2, f);
            }
        });
        this.cameraManager.setOnTorchListener(new CameraManager.OnTorchListener() { // from class: com.king.zxing.-$$Lambda$CaptureHelper$rpoAt43hjil8ox87CgThrHqtLBQ
            @Override // com.king.zxing.camera.CameraManager.OnTorchListener
            public final void onTorchChanged(boolean z) {
                CaptureHelper.this.lambda$initCameraManager$3$CaptureHelper(z);
            }
        });
    }

    public /* synthetic */ void lambda$initCameraManager$1$CaptureHelper(View view) {
        CameraManager cameraManager = this.cameraManager;
        if (cameraManager != null) {
            cameraManager.setTorch(!this.ivTorch.isSelected());
        }
    }

    public /* synthetic */ void lambda$initCameraManager$2$CaptureHelper(boolean z, boolean z2, float f) {
        if (z2) {
            if (this.ivTorch.getVisibility() == 0) {
                return;
            }
            this.ivTorch.setVisibility(0);
        } else if (z || this.ivTorch.getVisibility() != 0) {
        } else {
            this.ivTorch.setVisibility(4);
        }
    }

    public /* synthetic */ void lambda$initCameraManager$3$CaptureHelper(boolean z) {
        this.ivTorch.setSelected(z);
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        if (surfaceHolder == null) {
            throw new IllegalStateException("No SurfaceHolder provided");
        }
        if (this.cameraManager.isOpen()) {
            LogUtils.m3902w("initCamera() while already open -- late SurfaceView callback?");
            return;
        }
        try {
            this.cameraManager.openDriver(surfaceHolder);
            if (this.captureHandler != null) {
                return;
            }
            this.captureHandler = new CaptureHandler(this.activity, this.viewfinderView, this.onCaptureListener, this.decodeFormats, this.decodeHints, this.characterSet, this.cameraManager);
            this.captureHandler.setSupportVerticalCode(this.isSupportVerticalCode);
            this.captureHandler.setReturnBitmap(this.isReturnBitmap);
            this.captureHandler.setSupportAutoZoom(this.isSupportAutoZoom);
            this.captureHandler.setSupportLuminanceInvert(this.isSupportLuminanceInvert);
        } catch (IOException e) {
            LogUtils.m3900w(e);
        } catch (RuntimeException e2) {
            LogUtils.m3901w("Unexpected error initializing camera", e2);
        }
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        if (surfaceHolder == null) {
            LogUtils.m3902w("*** WARNING *** surfaceCreated() gave us a null surface!");
        }
        if (!this.hasSurface) {
            this.hasSurface = true;
            initCamera(surfaceHolder);
        }
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        this.hasSurface = false;
    }

    private void handleZoom(boolean z, Camera camera) {
        Camera.Parameters parameters = camera.getParameters();
        if (parameters.isZoomSupported()) {
            int maxZoom = parameters.getMaxZoom();
            int zoom = parameters.getZoom();
            if (z && zoom < maxZoom) {
                zoom++;
            } else if (zoom > 0) {
                zoom--;
            }
            parameters.setZoom(zoom);
            camera.setParameters(parameters);
            return;
        }
        LogUtils.m3903i("zoom not supported");
    }

    private float calcFingerSpacing(MotionEvent motionEvent) {
        float x = motionEvent.getX(0) - motionEvent.getX(1);
        float y = motionEvent.getY(0) - motionEvent.getY(1);
        return (float) Math.sqrt((x * x) + (y * y));
    }

    public void restartPreviewAndDecode() {
        CaptureHandler captureHandler = this.captureHandler;
        if (captureHandler != null) {
            captureHandler.restartPreviewAndDecode();
        }
    }

    public void onResult(Result result, Bitmap bitmap, float f) {
        onResult(result);
    }

    public void onResult(Result result) {
        CaptureHandler captureHandler;
        final String text = result.getText();
        if (this.isContinuousScan) {
            OnCaptureCallback onCaptureCallback = this.onCaptureCallback;
            if (onCaptureCallback != null) {
                onCaptureCallback.onResultCallback(text);
            }
            if (!this.isAutoRestartPreviewAndDecode) {
                return;
            }
            restartPreviewAndDecode();
        } else if (this.isPlayBeep && (captureHandler = this.captureHandler) != null) {
            captureHandler.postDelayed(new Runnable() { // from class: com.king.zxing.-$$Lambda$CaptureHelper$qeCs8VHWSPAGjlauoPkYu9qs5NM
                @Override // java.lang.Runnable
                public final void run() {
                    CaptureHelper.this.lambda$onResult$5$CaptureHelper(text);
                }
            }, 100L);
        } else {
            OnCaptureCallback onCaptureCallback2 = this.onCaptureCallback;
            if (onCaptureCallback2 != null && onCaptureCallback2.onResultCallback(text)) {
                return;
            }
            Intent intent = new Intent();
            intent.putExtra("SCAN_RESULT", text);
            this.activity.setResult(-1, intent);
            this.activity.finish();
        }
    }

    public /* synthetic */ void lambda$onResult$5$CaptureHelper(String str) {
        OnCaptureCallback onCaptureCallback = this.onCaptureCallback;
        if (onCaptureCallback == null || !onCaptureCallback.onResultCallback(str)) {
            Intent intent = new Intent();
            intent.putExtra("SCAN_RESULT", str);
            this.activity.setResult(-1, intent);
            this.activity.finish();
        }
    }

    public CaptureHelper setOnCaptureCallback(OnCaptureCallback onCaptureCallback) {
        this.onCaptureCallback = onCaptureCallback;
        return this;
    }
}
