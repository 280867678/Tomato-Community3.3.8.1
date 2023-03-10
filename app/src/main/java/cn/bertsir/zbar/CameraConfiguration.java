package cn.bertsir.zbar;

import android.content.Context;
import android.graphics.Point;
import android.hardware.Camera;
import android.os.Build;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public final class CameraConfiguration {
    private static final double MAX_ASPECT_DISTORTION = 0.15d;
    private static final int MIN_PREVIEW_PIXELS = 153600;
    private static final String TAG = "CameraConfiguration";
    private Point cameraResolution;
    private final Context context;
    private Point screenResolution;

    public CameraConfiguration(Context context) {
        this.context = context;
    }

    public void initFromCameraParameters(Camera camera) {
        Camera.Parameters parameters = camera.getParameters();
        this.screenResolution = getDisplaySize(((WindowManager) this.context.getSystemService("window")).getDefaultDisplay());
        Point point = new Point();
        Point point2 = this.screenResolution;
        point.x = point2.x;
        point.y = point2.y;
        int i = point2.x;
        int i2 = point2.y;
        if (i < i2) {
            point.x = i2;
            point.y = point2.x;
        }
        this.cameraResolution = findBestPreviewSizeValue(parameters, point);
    }

    private Point getDisplaySize(Display display) {
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= 13) {
            display.getSize(point);
        } else {
            point.set(display.getWidth(), display.getHeight());
        }
        return point;
    }

    public void setDesiredCameraParameters(Camera camera, boolean z) {
        Camera.Parameters parameters = camera.getParameters();
        if (parameters == null) {
            Log.w(TAG, "Device error: no camera parameters are available. Proceeding without configuration.");
            return;
        }
        Point point = this.cameraResolution;
        parameters.setPreviewSize(point.x, point.y);
        camera.setParameters(parameters);
        Camera.Size previewSize = camera.getParameters().getPreviewSize();
        if (previewSize != null) {
            Point point2 = this.cameraResolution;
            if (point2.x != previewSize.width || point2.y != previewSize.height) {
                Point point3 = this.cameraResolution;
                point3.x = previewSize.width;
                point3.y = previewSize.height;
            }
        }
        camera.setDisplayOrientation(90);
    }

    public Point getCameraResolution() {
        return this.cameraResolution;
    }

    public Point getScreenResolution() {
        return this.screenResolution;
    }

    private Point findBestPreviewSizeValue(Camera.Parameters parameters, Point point) {
        List<Camera.Size> supportedPreviewSizes = parameters.getSupportedPreviewSizes();
        if (supportedPreviewSizes == null) {
            Log.w(TAG, "Device returned no supported preview sizes; using default");
            Camera.Size previewSize = parameters.getPreviewSize();
            return new Point(previewSize.width, previewSize.height);
        }
        ArrayList<Camera.Size> arrayList = new ArrayList(supportedPreviewSizes);
        Collections.sort(arrayList, new Comparator<Camera.Size>() { // from class: cn.bertsir.zbar.CameraConfiguration.1
            @Override // java.util.Comparator
            public int compare(Camera.Size size, Camera.Size size2) {
                int i = size.height * size.width;
                int i2 = size2.height * size2.width;
                if (i2 < i) {
                    return -1;
                }
                return i2 > i ? 1 : 0;
            }
        });
        if (Log.isLoggable(TAG, 4)) {
            StringBuilder sb = new StringBuilder();
            for (Camera.Size size : arrayList) {
                sb.append(size.width);
                sb.append('x');
                sb.append(size.height);
                sb.append(' ');
            }
            Log.i(TAG, "Supported preview sizes: " + ((Object) sb));
        }
        double d = point.x / point.y;
        Iterator it2 = arrayList.iterator();
        while (true) {
            boolean z = false;
            if (it2.hasNext()) {
                Camera.Size size2 = (Camera.Size) it2.next();
                int i = size2.width;
                int i2 = size2.height;
                if (i * i2 < MIN_PREVIEW_PIXELS) {
                    it2.remove();
                } else {
                    if (i < i2) {
                        z = true;
                    }
                    int i3 = z ? i2 : i;
                    int i4 = z ? i : i2;
                    if (Math.abs((i3 / i4) - d) > MAX_ASPECT_DISTORTION) {
                        it2.remove();
                    } else if (i3 == point.x && i4 == point.y) {
                        Point point2 = new Point(i, i2);
                        Log.i(TAG, "Found preview size exactly matching screen size: " + point2);
                        return point2;
                    }
                }
            } else if (!arrayList.isEmpty()) {
                Camera.Size size3 = (Camera.Size) arrayList.get(0);
                Point point3 = new Point(size3.width, size3.height);
                Log.i(TAG, "Using largest suitable preview size: " + point3);
                return point3;
            } else {
                Camera.Size previewSize2 = parameters.getPreviewSize();
                Point point4 = new Point(previewSize2.width, previewSize2.height);
                Log.i(TAG, "No suitable preview sizes, using default: " + point4);
                return point4;
            }
        }
    }
}
