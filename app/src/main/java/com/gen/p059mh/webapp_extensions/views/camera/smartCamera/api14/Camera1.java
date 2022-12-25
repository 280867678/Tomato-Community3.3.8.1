package com.gen.p059mh.webapp_extensions.views.camera.smartCamera.api14;

import android.annotation.SuppressLint;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.support.p002v4.util.SparseArrayCompat;
import android.view.SurfaceHolder;
import com.gen.p059mh.webapp_extensions.views.camera.smartCamera.AspectRatio;
import com.gen.p059mh.webapp_extensions.views.camera.smartCamera.CameraViewImpl;
import com.gen.p059mh.webapp_extensions.views.camera.smartCamera.Constants;
import com.gen.p059mh.webapp_extensions.views.camera.smartCamera.PreviewImpl;
import com.gen.p059mh.webapp_extensions.views.camera.smartCamera.Size;
import com.gen.p059mh.webapp_extensions.views.camera.smartCamera.SizeMap;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.concurrent.atomic.AtomicBoolean;

/* renamed from: com.gen.mh.webapp_extensions.views.camera.smartCamera.api14.Camera1 */
/* loaded from: classes2.dex */
public class Camera1 extends CameraViewImpl {
    private static final SparseArrayCompat<String> FLASH_MODES = new SparseArrayCompat<>();
    private AspectRatio mAspectRatio;
    private boolean mAutoFocus;
    Camera mCamera;
    private int mCameraId;
    private Camera.Parameters mCameraParameters;
    private int mDisplayOrientation;
    private int mFacing;
    private int mFlash;
    private boolean mShowingPreview;
    private final AtomicBoolean isPictureCaptureInProgress = new AtomicBoolean(false);
    private final Camera.CameraInfo mCameraInfo = new Camera.CameraInfo();
    private final SizeMap mPreviewSizes = new SizeMap();
    private final SizeMap mPictureSizes = new SizeMap();

    private boolean isLandscape(int i) {
        return i == 90 || i == 270;
    }

    static {
        FLASH_MODES.put(0, "off");
        FLASH_MODES.put(1, "on");
        FLASH_MODES.put(2, "torch");
        FLASH_MODES.put(3, "auto");
        FLASH_MODES.put(4, "red-eye");
    }

    public Camera1(CameraViewImpl.Callback callback, PreviewImpl previewImpl) {
        super(callback, previewImpl);
        previewImpl.setCallback(new PreviewImpl.Callback() { // from class: com.gen.mh.webapp_extensions.views.camera.smartCamera.api14.Camera1.1
            @Override // com.gen.p059mh.webapp_extensions.views.camera.smartCamera.PreviewImpl.Callback
            public void onSurfaceChanged() {
                Camera1 camera1 = Camera1.this;
                if (camera1.mCamera != null) {
                    try {
                        camera1.setUpPreview();
                        Camera1.this.adjustCameraParameters();
                    } catch (RuntimeException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override // com.gen.p059mh.webapp_extensions.views.camera.smartCamera.CameraViewImpl
    public boolean start() {
        chooseCamera();
        openCamera();
        if (this.mPreview.isReady()) {
            setUpPreview();
        }
        this.mShowingPreview = true;
        this.mCamera.startPreview();
        return true;
    }

    @Override // com.gen.p059mh.webapp_extensions.views.camera.smartCamera.CameraViewImpl
    public void stop() {
        Camera camera = this.mCamera;
        if (camera != null) {
            camera.stopPreview();
        }
        this.mShowingPreview = false;
        releaseCamera();
    }

    @SuppressLint({"NewApi"})
    public void setUpPreview() throws RuntimeException {
        try {
            if (this.mPreview.getOutputClass() == SurfaceHolder.class) {
                this.mCamera.setPreviewDisplay(this.mPreview.getSurfaceHolder());
            } else {
                this.mCamera.setPreviewTexture((SurfaceTexture) this.mPreview.mo6190getSurfaceTexture());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override // com.gen.p059mh.webapp_extensions.views.camera.smartCamera.CameraViewImpl
    public boolean isCameraOpened() {
        return this.mCamera != null;
    }

    @Override // com.gen.p059mh.webapp_extensions.views.camera.smartCamera.CameraViewImpl
    public void setFacing(int i) {
        if (this.mFacing == i) {
            return;
        }
        this.mFacing = i;
        if (!isCameraOpened()) {
            return;
        }
        stop();
        start();
    }

    @Override // com.gen.p059mh.webapp_extensions.views.camera.smartCamera.CameraViewImpl
    public int getFacing() {
        return this.mFacing;
    }

    @Override // com.gen.p059mh.webapp_extensions.views.camera.smartCamera.CameraViewImpl
    public Set<AspectRatio> getSupportedAspectRatios() {
        SizeMap sizeMap = this.mPreviewSizes;
        for (AspectRatio aspectRatio : sizeMap.ratios()) {
            if (this.mPictureSizes.sizes(aspectRatio) == null) {
                sizeMap.remove(aspectRatio);
            }
        }
        return sizeMap.ratios();
    }

    @Override // com.gen.p059mh.webapp_extensions.views.camera.smartCamera.CameraViewImpl
    public boolean setAspectRatio(AspectRatio aspectRatio) {
        if (this.mAspectRatio == null || !isCameraOpened()) {
            this.mAspectRatio = aspectRatio;
            return true;
        } else if (this.mAspectRatio.equals(aspectRatio)) {
            return false;
        } else {
            if (this.mPreviewSizes.sizes(aspectRatio) == null) {
                throw new UnsupportedOperationException(aspectRatio + " is not supported");
            }
            this.mAspectRatio = aspectRatio;
            adjustCameraParameters();
            return true;
        }
    }

    @Override // com.gen.p059mh.webapp_extensions.views.camera.smartCamera.CameraViewImpl
    public AspectRatio getAspectRatio() {
        return this.mAspectRatio;
    }

    @Override // com.gen.p059mh.webapp_extensions.views.camera.smartCamera.CameraViewImpl
    public void setAutoFocus(boolean z) {
        if (this.mAutoFocus != z && setAutoFocusInternal(z)) {
            this.mCamera.setParameters(this.mCameraParameters);
        }
    }

    @Override // com.gen.p059mh.webapp_extensions.views.camera.smartCamera.CameraViewImpl
    public boolean getAutoFocus() {
        if (!isCameraOpened()) {
            return this.mAutoFocus;
        }
        String focusMode = this.mCameraParameters.getFocusMode();
        return focusMode != null && focusMode.contains("continuous");
    }

    @Override // com.gen.p059mh.webapp_extensions.views.camera.smartCamera.CameraViewImpl
    public void setFlash(int i) {
        if (i != this.mFlash && setFlashInternal(i)) {
            this.mCamera.setParameters(this.mCameraParameters);
        }
    }

    @Override // com.gen.p059mh.webapp_extensions.views.camera.smartCamera.CameraViewImpl
    public int getFlash() {
        return this.mFlash;
    }

    @Override // com.gen.p059mh.webapp_extensions.views.camera.smartCamera.CameraViewImpl
    public void takePicture() {
        if (!isCameraOpened()) {
            throw new IllegalStateException("Camera is not ready. Call start() before takePicture().");
        }
        if (getAutoFocus()) {
            this.mCamera.cancelAutoFocus();
            this.mCamera.autoFocus(new Camera.AutoFocusCallback() { // from class: com.gen.mh.webapp_extensions.views.camera.smartCamera.api14.Camera1.2
                @Override // android.hardware.Camera.AutoFocusCallback
                public void onAutoFocus(boolean z, Camera camera) {
                    Camera1.this.takePictureInternal();
                }
            });
            return;
        }
        takePictureInternal();
    }

    void takePictureInternal() {
        if (!this.isPictureCaptureInProgress.getAndSet(true)) {
            this.mCamera.takePicture(null, null, null, new Camera.PictureCallback() { // from class: com.gen.mh.webapp_extensions.views.camera.smartCamera.api14.Camera1.3
                @Override // android.hardware.Camera.PictureCallback
                public void onPictureTaken(byte[] bArr, Camera camera) {
                    Camera1.this.isPictureCaptureInProgress.set(false);
                    ((CameraViewImpl) Camera1.this).mCallback.onPictureTaken(bArr);
                    camera.cancelAutoFocus();
                    camera.startPreview();
                }
            });
        }
    }

    @Override // com.gen.p059mh.webapp_extensions.views.camera.smartCamera.CameraViewImpl
    public void setDisplayOrientation(int i) {
        if (this.mDisplayOrientation == i) {
            return;
        }
        this.mDisplayOrientation = i;
        if (!isCameraOpened()) {
            return;
        }
        this.mCameraParameters.setRotation(calcCameraRotation(i));
        this.mCamera.setParameters(this.mCameraParameters);
        this.mCamera.setDisplayOrientation(calcDisplayOrientation(i));
    }

    private void chooseCamera() {
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.getCameraInfo(i, this.mCameraInfo);
            if (this.mCameraInfo.facing == this.mFacing) {
                this.mCameraId = i;
                return;
            }
        }
        this.mCameraId = -1;
    }

    private void openCamera() {
        if (this.mCamera != null) {
            releaseCamera();
        }
        this.mCamera = Camera.open(this.mCameraId);
        this.mCameraParameters = this.mCamera.getParameters();
        this.mPreviewSizes.clear();
        for (Camera.Size size : this.mCameraParameters.getSupportedPreviewSizes()) {
            this.mPreviewSizes.add(new Size(size.width, size.height));
        }
        this.mPictureSizes.clear();
        for (Camera.Size size2 : this.mCameraParameters.getSupportedPictureSizes()) {
            this.mPictureSizes.add(new Size(size2.width, size2.height));
        }
        if (this.mAspectRatio == null) {
            this.mAspectRatio = Constants.DEFAULT_ASPECT_RATIO;
        }
        adjustCameraParameters();
        this.mCamera.setDisplayOrientation(calcDisplayOrientation(this.mDisplayOrientation));
        this.mCallback.onCameraOpened();
    }

    private AspectRatio chooseAspectRatio() {
        Iterator<AspectRatio> it2 = this.mPreviewSizes.ratios().iterator();
        AspectRatio aspectRatio = null;
        while (it2.hasNext()) {
            aspectRatio = it2.next();
            if (aspectRatio.equals(Constants.DEFAULT_ASPECT_RATIO)) {
                break;
            }
        }
        return aspectRatio;
    }

    void adjustCameraParameters() {
        SortedSet<Size> sizes = this.mPreviewSizes.sizes(this.mAspectRatio);
        if (sizes == null) {
            this.mAspectRatio = chooseAspectRatio();
            sizes = this.mPreviewSizes.sizes(this.mAspectRatio);
        }
        Size chooseOptimalSize = chooseOptimalSize(sizes);
        Size last = this.mPictureSizes.sizes(this.mAspectRatio).last();
        if (this.mShowingPreview) {
            this.mCamera.stopPreview();
        }
        this.mCameraParameters.setPreviewSize(chooseOptimalSize.getWidth(), chooseOptimalSize.getHeight());
        this.mCameraParameters.setPictureSize(last.getWidth(), last.getHeight());
        this.mCameraParameters.setRotation(calcCameraRotation(this.mDisplayOrientation));
        setAutoFocusInternal(this.mAutoFocus);
        setFlashInternal(this.mFlash);
        this.mCamera.setParameters(this.mCameraParameters);
        if (this.mShowingPreview) {
            this.mCamera.startPreview();
        }
    }

    private Size chooseOptimalSize(SortedSet<Size> sortedSet) {
        if (!this.mPreview.isReady()) {
            return sortedSet.first();
        }
        int width = this.mPreview.getWidth();
        int height = this.mPreview.getHeight();
        if (isLandscape(this.mDisplayOrientation)) {
            height = width;
            width = height;
        }
        Size size = null;
        Iterator<Size> it2 = sortedSet.iterator();
        while (it2.hasNext()) {
            size = it2.next();
            if (width <= size.getWidth() && height <= size.getHeight()) {
                break;
            }
        }
        return size;
    }

    private void releaseCamera() {
        Camera camera = this.mCamera;
        if (camera != null) {
            camera.release();
            this.mCamera = null;
            this.mCallback.onCameraClosed();
        }
    }

    private int calcDisplayOrientation(int i) {
        Camera.CameraInfo cameraInfo = this.mCameraInfo;
        if (cameraInfo.facing == 1) {
            return (360 - ((cameraInfo.orientation + i) % 360)) % 360;
        }
        return ((cameraInfo.orientation - i) + 360) % 360;
    }

    private int calcCameraRotation(int i) {
        Camera.CameraInfo cameraInfo = this.mCameraInfo;
        if (cameraInfo.facing == 1) {
            return (cameraInfo.orientation + i) % 360;
        }
        return ((this.mCameraInfo.orientation + i) + (isLandscape(i) ? 180 : 0)) % 360;
    }

    private boolean setAutoFocusInternal(boolean z) {
        this.mAutoFocus = z;
        if (isCameraOpened()) {
            List<String> supportedFocusModes = this.mCameraParameters.getSupportedFocusModes();
            if (z && supportedFocusModes.contains("continuous-picture")) {
                this.mCameraParameters.setFocusMode("continuous-picture");
                return true;
            } else if (supportedFocusModes.contains("fixed")) {
                this.mCameraParameters.setFocusMode("fixed");
                return true;
            } else if (supportedFocusModes.contains("infinity")) {
                this.mCameraParameters.setFocusMode("infinity");
                return true;
            } else {
                this.mCameraParameters.setFocusMode(supportedFocusModes.get(0));
                return true;
            }
        }
        return false;
    }

    private boolean setFlashInternal(int i) {
        if (isCameraOpened()) {
            List<String> supportedFlashModes = this.mCameraParameters.getSupportedFlashModes();
            String str = FLASH_MODES.get(i);
            if (supportedFlashModes != null && supportedFlashModes.contains(str)) {
                this.mCameraParameters.setFlashMode(str);
                this.mFlash = i;
                return true;
            }
            String str2 = FLASH_MODES.get(this.mFlash);
            if (supportedFlashModes != null && supportedFlashModes.contains(str2)) {
                return false;
            }
            this.mCameraParameters.setFlashMode("off");
            this.mFlash = 0;
            return true;
        }
        this.mFlash = i;
        return false;
    }
}
