package com.gen.p059mh.webapp_extensions.views.camera.smartCamera;

import android.view.View;
import java.util.Set;

/* renamed from: com.gen.mh.webapp_extensions.views.camera.smartCamera.CameraViewImpl */
/* loaded from: classes2.dex */
public abstract class CameraViewImpl {
    protected final Callback mCallback;
    protected final PreviewImpl mPreview;

    /* renamed from: com.gen.mh.webapp_extensions.views.camera.smartCamera.CameraViewImpl$Callback */
    /* loaded from: classes2.dex */
    public interface Callback {
        void onCameraClosed();

        void onCameraOpened();

        void onPictureTaken(byte[] bArr);
    }

    public abstract AspectRatio getAspectRatio();

    public abstract boolean getAutoFocus();

    public abstract int getFacing();

    public abstract int getFlash();

    public abstract Set<AspectRatio> getSupportedAspectRatios();

    public abstract boolean isCameraOpened();

    public abstract boolean setAspectRatio(AspectRatio aspectRatio);

    public abstract void setAutoFocus(boolean z);

    public abstract void setDisplayOrientation(int i);

    public abstract void setFacing(int i);

    public abstract void setFlash(int i);

    public abstract boolean start();

    public abstract void stop();

    public abstract void takePicture();

    public CameraViewImpl(Callback callback, PreviewImpl previewImpl) {
        this.mCallback = callback;
        this.mPreview = previewImpl;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public View getView() {
        return this.mPreview.getView();
    }
}
