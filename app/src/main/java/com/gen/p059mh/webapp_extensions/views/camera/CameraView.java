package com.gen.p059mh.webapp_extensions.views.camera;

import android.content.Context;
import android.hardware.Camera;
import android.support.p002v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.gen.p059mh.webapp_extensions.plugins.PickImagePlugin;
import com.gen.p059mh.webapp_extensions.rxpermission.RxPermissions;
import com.gen.p059mh.webapps.pugins.NativeViewPlugin;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* renamed from: com.gen.mh.webapp_extensions.views.camera.CameraView */
/* loaded from: classes2.dex */
public class CameraView extends NativeViewPlugin.NativeView {
    CameraPreview cameraPreview;
    CoverView coverView;
    boolean scanCode = false;
    private NativeViewPlugin.NativeView.MethodHandler takePhoto = new NativeViewPlugin.NativeView.MethodHandler() { // from class: com.gen.mh.webapp_extensions.views.camera.CameraView.1
        @Override // com.gen.p059mh.webapps.pugins.NativeViewPlugin.NativeView.MethodHandler
        public void run(List list, final NativeViewPlugin.NativeView.MethodCallback methodCallback) {
            CameraPreview cameraPreview = CameraView.this.cameraPreview;
            if (cameraPreview != null) {
                cameraPreview.mCamera.takePicture(null, null, new Camera.PictureCallback() { // from class: com.gen.mh.webapp_extensions.views.camera.CameraView.1.1
                    @Override // android.hardware.Camera.PictureCallback
                    public void onPictureTaken(byte[] bArr, Camera camera) {
                        File file = new File(CameraView.this.getWebViewFragment().getTempDir().getAbsolutePath() + "/photo_" + new Date().getTime() + ".jpg");
                        try {
                            FileOutputStream fileOutputStream = new FileOutputStream(file);
                            fileOutputStream.write(bArr);
                            fileOutputStream.close();
                            HashMap hashMap = new HashMap();
                            hashMap.put("success", true);
                            hashMap.put("tempImagePath", file.getAbsolutePath());
                            methodCallback.run(hashMap);
                        } catch (IOException e) {
                            e.printStackTrace();
                            HashMap hashMap2 = new HashMap();
                            hashMap2.put("success", false);
                            hashMap2.put(NotificationCompat.CATEGORY_MESSAGE, "Write failed!");
                            methodCallback.run(hashMap2);
                        }
                        CameraView.this.cameraPreview.mCamera.startPreview();
                    }
                });
            }
        }
    };
    String oldResult = null;
    Camera.PreviewCallback previewCallback = new Camera.PreviewCallback() { // from class: com.gen.mh.webapp_extensions.views.camera.CameraView.2
        @Override // android.hardware.Camera.PreviewCallback
        public void onPreviewFrame(byte[] bArr, Camera camera) {
            Camera.Size previewSize = camera.getParameters().getPreviewSize();
            String scanImage = CameraView.this.scanImage(previewSize.width, previewSize.height, bArr);
            if (scanImage == null || scanImage.equals(CameraView.this.oldResult)) {
                return;
            }
            CameraView.this.oldResult = scanImage;
            HashMap hashMap = new HashMap();
            hashMap.put("type", "scancode");
            hashMap.put("value", scanImage);
            CameraView.this.sendEvent(hashMap, null);
        }
    };

    /* JADX INFO: Access modifiers changed from: private */
    public native String scanImage(int i, int i2, byte[] bArr);

    /* JADX INFO: Access modifiers changed from: private */
    public native void startScan();

    private native void stopScan();

    public CameraView(Context context) {
        super(context);
        registerMethod("takePhoto", this.takePhoto);
    }

    @Override // com.gen.p059mh.webapps.pugins.NativeViewPlugin.NativeView
    public void onInitialize(final Object obj) {
        super.onInitialize(obj);
        new RxPermissions(getWebViewFragment().getActivity()).request(PickImagePlugin.CAMERA).subscribe(new Observer<Boolean>() { // from class: com.gen.mh.webapp_extensions.views.camera.CameraView.3
            @Override // io.reactivex.Observer
            public void onComplete() {
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable th) {
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
            }

            @Override // io.reactivex.Observer
            public void onNext(Boolean bool) {
                int i = 0;
                if (bool.booleanValue()) {
                    if (CameraView.this.getContext() == null) {
                        return;
                    }
                    Object obj2 = obj;
                    Map hashMap = obj2 == null ? new HashMap() : (Map) obj2;
                    CameraView.this.scanCode = "scanCode".equals((String) hashMap.get("mode"));
                    String str = (String) hashMap.get("flash");
                    boolean equals = "front".equals((String) hashMap.get("device-position"));
                    int numberOfCameras = Camera.getNumberOfCameras();
                    Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
                    Camera camera = null;
                    while (true) {
                        if (i >= numberOfCameras) {
                            break;
                        }
                        Camera.getCameraInfo(i, cameraInfo);
                        if (cameraInfo.facing == equals) {
                            camera = Camera.open(i);
                            break;
                        }
                        i++;
                    }
                    if (camera == null) {
                        Log.i("CameraView", "No camera found!");
                        return;
                    }
                    Camera.Parameters parameters = camera.getParameters();
                    parameters.setFocusMode("continuous-video");
                    camera.setParameters(parameters);
                    camera.setDisplayOrientation(90);
                    CameraView cameraView = CameraView.this;
                    cameraView.cameraPreview = new CameraPreview(cameraView.getContext(), camera);
                    CameraView.this.cameraPreview.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
                    CameraView cameraView2 = CameraView.this;
                    cameraView2.addView(cameraView2.cameraPreview);
                    CameraView cameraView3 = CameraView.this;
                    if (!cameraView3.scanCode) {
                        return;
                    }
                    cameraView3.startScan();
                    CameraView cameraView4 = CameraView.this;
                    cameraView4.cameraPreview.mCamera.setPreviewCallback(cameraView4.previewCallback);
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
                    CameraView cameraView5 = CameraView.this;
                    cameraView5.coverView = new CoverView(cameraView5.getContext());
                    CameraView.this.coverView.setLayoutParams(layoutParams);
                    CameraView cameraView6 = CameraView.this;
                    cameraView6.addView(cameraView6.coverView);
                    return;
                }
                Toast.makeText(CameraView.this.getContext(), "请打开相机权限", 0).show();
            }
        });
    }

    @Override // com.gen.p059mh.webapps.pugins.NativeViewPlugin.NativeView
    public void onDestroy() {
        super.onDestroy();
        CameraPreview cameraPreview = this.cameraPreview;
        if (cameraPreview != null) {
            cameraPreview.mCamera.stopPreview();
        }
        if (this.scanCode) {
            stopScan();
        }
    }
}
