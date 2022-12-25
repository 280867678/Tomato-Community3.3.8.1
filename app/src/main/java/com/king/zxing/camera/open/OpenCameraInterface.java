package com.king.zxing.camera.open;

import android.hardware.Camera;
import com.king.zxing.util.LogUtils;

/* loaded from: classes3.dex */
public final class OpenCameraInterface {
    public static OpenCamera open(int i) {
        int numberOfCameras = Camera.getNumberOfCameras();
        if (numberOfCameras == 0) {
            LogUtils.m3902w("No cameras!");
            return null;
        } else if (i >= numberOfCameras) {
            LogUtils.m3902w("Requested camera does not exist: " + i);
            return null;
        } else {
            if (i <= -1) {
                i = 0;
                while (i < numberOfCameras) {
                    Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
                    Camera.getCameraInfo(i, cameraInfo);
                    if (CameraFacing.values()[cameraInfo.facing] == CameraFacing.BACK) {
                        break;
                    }
                    i++;
                }
                if (i == numberOfCameras) {
                    LogUtils.m3903i("No camera facing " + CameraFacing.BACK + "; returning camera #0");
                    i = 0;
                }
            }
            LogUtils.m3903i("Opening camera #" + i);
            Camera.CameraInfo cameraInfo2 = new Camera.CameraInfo();
            Camera.getCameraInfo(i, cameraInfo2);
            Camera open = Camera.open(i);
            if (open != null) {
                return new OpenCamera(i, open, CameraFacing.values()[cameraInfo2.facing], cameraInfo2.orientation);
            }
            return null;
        }
    }
}
