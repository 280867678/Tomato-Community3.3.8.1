package cn.bertsir.zbar;

import android.content.Context;
import android.hardware.Camera;
import android.os.Build;
import android.util.Log;
import android.view.SurfaceHolder;
import cn.bertsir.zbar.utils.QRUtils;
import java.io.IOException;

/* loaded from: classes2.dex */
public final class CameraManager {
    private static final String TAG = "CameraManager";
    private Context context;
    private Camera mCamera;
    private final CameraConfiguration mConfiguration;

    public CameraManager(Context context) {
        this.context = context;
        this.mConfiguration = new CameraConfiguration(context);
    }

    public synchronized void openDriver() throws Exception {
        if (this.mCamera != null) {
            return;
        }
        this.mCamera = Camera.open();
        if (this.mCamera == null) {
            throw new IOException("The camera is occupied.");
        }
        this.mConfiguration.initFromCameraParameters(this.mCamera);
        Camera.Parameters parameters = this.mCamera.getParameters();
        String flatten = parameters == null ? null : parameters.flatten();
        try {
            this.mConfiguration.setDesiredCameraParameters(this.mCamera, false);
        } catch (RuntimeException unused) {
            if (flatten != null) {
                Camera.Parameters parameters2 = this.mCamera.getParameters();
                parameters2.unflatten(flatten);
                try {
                    this.mCamera.setParameters(parameters2);
                    this.mConfiguration.setDesiredCameraParameters(this.mCamera, true);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public synchronized void closeDriver() {
        if (this.mCamera != null) {
            this.mCamera.setPreviewCallback(null);
            this.mCamera.release();
            this.mCamera = null;
        }
    }

    public boolean isOpen() {
        return this.mCamera != null;
    }

    public CameraConfiguration getConfiguration() {
        return this.mConfiguration;
    }

    public void startPreview(SurfaceHolder surfaceHolder, Camera.PreviewCallback previewCallback) throws IOException {
        if (this.mCamera != null) {
            if (Build.MANUFACTURER.equals("LGE") && Build.MODEL.equals("Nexus 5X")) {
                this.mCamera.setDisplayOrientation(QRUtils.getInstance().isScreenOriatationPortrait(this.context) ? 270 : 180);
            } else {
                this.mCamera.setDisplayOrientation(QRUtils.getInstance().isScreenOriatationPortrait(this.context) ? 90 : 0);
            }
            this.mCamera.setPreviewDisplay(surfaceHolder);
            this.mCamera.setPreviewCallback(previewCallback);
            this.mCamera.startPreview();
        }
    }

    public void stopPreview() {
        Camera camera = this.mCamera;
        if (camera != null) {
            try {
                camera.stopPreview();
            } catch (Exception unused) {
            }
            try {
                this.mCamera.setPreviewDisplay(null);
            } catch (IOException unused2) {
            }
        }
    }

    public void autoFocus(Camera.AutoFocusCallback autoFocusCallback) {
        Camera camera = this.mCamera;
        if (camera != null) {
            try {
                camera.autoFocus(autoFocusCallback);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setFlash() {
        Camera camera = this.mCamera;
        if (camera != null) {
            Camera.Parameters parameters = camera.getParameters();
            if (parameters.getFlashMode() == null) {
                return;
            }
            if (parameters.getFlashMode().endsWith("torch")) {
                parameters.setFlashMode("off");
            } else {
                parameters.setFlashMode("torch");
            }
            this.mCamera.setParameters(parameters);
        }
    }

    public void setFlash(boolean z) {
        Camera camera = this.mCamera;
        if (camera != null) {
            Camera.Parameters parameters = camera.getParameters();
            if (parameters.getFlashMode() == null) {
                return;
            }
            if (!z) {
                if (parameters.getFlashMode().endsWith("torch")) {
                    parameters.setFlashMode("off");
                }
            } else if (parameters.getFlashMode().endsWith("off")) {
                parameters.setFlashMode("torch");
            }
            this.mCamera.setParameters(parameters);
        }
    }

    public void setCameraZoom(float f) {
        int maxZoom;
        Camera camera = this.mCamera;
        if (camera != null) {
            Camera.Parameters parameters = camera.getParameters();
            if (!parameters.isZoomSupported() || (maxZoom = parameters.getMaxZoom()) == 0) {
                return;
            }
            parameters.setZoom((int) (maxZoom * f));
            this.mCamera.setParameters(parameters);
        }
    }

    public void handleZoom(boolean z) {
        Camera camera = this.mCamera;
        if (camera != null) {
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
                this.mCamera.setParameters(parameters);
                return;
            }
            Log.i(TAG, "zoom not supported");
        }
    }
}
