package com.gen.p059mh.webapp_extensions.views.camera;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.io.IOException;

/* renamed from: com.gen.mh.webapp_extensions.views.camera.CameraPreview */
/* loaded from: classes2.dex */
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    public Camera mCamera;
    private SurfaceHolder mHolder = getHolder();

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
    }

    public CameraPreview(Context context, Camera camera) {
        super(context);
        this.mCamera = camera;
        this.mHolder.addCallback(this);
        this.mHolder.setType(3);
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        try {
            this.mCamera.setPreviewDisplay(surfaceHolder);
            this.mCamera.startPreview();
        } catch (IOException e) {
            Log.d("CameraView", "Error setting camera preview: " + e.getMessage());
        }
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        if (this.mHolder.getSurface() == null) {
            return;
        }
        try {
            this.mCamera.stopPreview();
        } catch (Exception unused) {
        }
        try {
            this.mCamera.setPreviewDisplay(this.mHolder);
            this.mCamera.startPreview();
        } catch (Exception e) {
            Log.d("CameraView", "Error starting camera preview: " + e.getMessage());
        }
    }
}
