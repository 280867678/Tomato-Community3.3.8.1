package cn.bertsir.zbar;

import android.content.Context;
import android.hardware.Camera;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.FrameLayout;
import android.widget.Toast;

/* loaded from: classes2.dex */
public class CameraPreview extends FrameLayout implements SurfaceHolder.Callback {
    private Runnable mAutoFocusTask;
    private CameraManager mCameraManager;
    private Camera.AutoFocusCallback mFocusCallback;
    private CameraScanAnalysis mPreviewCallback;
    private SurfaceView mSurfaceView;

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
    }

    public CameraPreview(Context context) {
        this(context, null);
    }

    public CameraPreview(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CameraPreview(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mFocusCallback = new Camera.AutoFocusCallback() { // from class: cn.bertsir.zbar.CameraPreview.2
            @Override // android.hardware.Camera.AutoFocusCallback
            public void onAutoFocus(boolean z, Camera camera) {
                CameraPreview cameraPreview = CameraPreview.this;
                cameraPreview.postDelayed(cameraPreview.mAutoFocusTask, 1000L);
            }
        };
        this.mAutoFocusTask = new Runnable() { // from class: cn.bertsir.zbar.CameraPreview.3
            @Override // java.lang.Runnable
            public void run() {
                CameraPreview.this.mCameraManager.autoFocus(CameraPreview.this.mFocusCallback);
            }
        };
        this.mCameraManager = new CameraManager(context);
        this.mPreviewCallback = new CameraScanAnalysis(context);
    }

    public void setScanCallback(ScanCallback scanCallback) {
        this.mPreviewCallback.setScanCallback(scanCallback);
    }

    public boolean start() {
        try {
            this.mCameraManager.openDriver();
            this.mPreviewCallback.onStart();
            if (this.mSurfaceView == null) {
                this.mSurfaceView = new SurfaceView(getContext());
                addView(this.mSurfaceView, new FrameLayout.LayoutParams(-1, -1));
                SurfaceHolder holder = this.mSurfaceView.getHolder();
                holder.addCallback(this);
                holder.setType(3);
            }
            startCameraPreview(this.mSurfaceView.getHolder());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "摄像头权限被拒绝！", 0).show();
            return false;
        }
    }

    public void stop() {
        removeCallbacks(this.mAutoFocusTask);
        this.mPreviewCallback.onStop();
        this.mCameraManager.stopPreview();
        this.mCameraManager.closeDriver();
    }

    private void startCameraPreview(SurfaceHolder surfaceHolder) {
        try {
            this.mCameraManager.startPreview(surfaceHolder, this.mPreviewCallback);
            this.mCameraManager.autoFocus(this.mFocusCallback);
        } catch (Exception e) {
            e.printStackTrace();
            new Handler().postDelayed(new Runnable() { // from class: cn.bertsir.zbar.CameraPreview.1
                @Override // java.lang.Runnable
                public void run() {
                    CameraPreview.this.mCameraManager.autoFocus(CameraPreview.this.mFocusCallback);
                }
            }, 200L);
        }
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        if (surfaceHolder.getSurface() == null) {
            return;
        }
        this.mCameraManager.stopPreview();
        startCameraPreview(surfaceHolder);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        stop();
        super.onDetachedFromWindow();
    }

    public void setFlash() {
        this.mCameraManager.setFlash();
    }

    public void setFlash(boolean z) {
        this.mCameraManager.setFlash(z);
    }

    public void setZoom(float f) {
        this.mCameraManager.setCameraZoom(f);
    }

    public void handleZoom(boolean z) {
        this.mCameraManager.handleZoom(z);
    }
}
