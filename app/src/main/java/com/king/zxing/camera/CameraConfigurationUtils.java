package com.king.zxing.camera;

import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import com.king.zxing.util.LogUtils;
import com.tomatolive.library.p136ui.view.dialog.LotteryDialog;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/* loaded from: classes3.dex */
public final class CameraConfigurationUtils {
    static {
        Pattern.compile(";");
    }

    public static void setFocus(Camera.Parameters parameters, boolean z, boolean z2, boolean z3) {
        String str;
        List<String> supportedFocusModes = parameters.getSupportedFocusModes();
        if (!z) {
            str = null;
        } else if (z3 || z2) {
            str = findSettableValue("focus mode", supportedFocusModes, "auto");
        } else {
            str = findSettableValue("focus mode", supportedFocusModes, "continuous-picture", "continuous-video", "auto");
        }
        if (!z3 && str == null) {
            str = findSettableValue("focus mode", supportedFocusModes, "macro", "edof");
        }
        if (str != null) {
            if (str.equals(parameters.getFocusMode())) {
                LogUtils.m3904d("Focus mode already set to " + str);
                return;
            }
            parameters.setFocusMode(str);
        }
    }

    public static void setTorch(Camera.Parameters parameters, boolean z) {
        String findSettableValue;
        List<String> supportedFlashModes = parameters.getSupportedFlashModes();
        if (z) {
            findSettableValue = findSettableValue("flash mode", supportedFlashModes, "torch", "on");
        } else {
            findSettableValue = findSettableValue("flash mode", supportedFlashModes, "off");
        }
        if (findSettableValue != null) {
            if (findSettableValue.equals(parameters.getFlashMode())) {
                LogUtils.m3904d("Flash mode already set to " + findSettableValue);
                return;
            }
            LogUtils.m3904d("Setting flash mode to " + findSettableValue);
            parameters.setFlashMode(findSettableValue);
        }
    }

    public static void setBestExposure(Camera.Parameters parameters, boolean z) {
        int minExposureCompensation = parameters.getMinExposureCompensation();
        int maxExposureCompensation = parameters.getMaxExposureCompensation();
        float exposureCompensationStep = parameters.getExposureCompensationStep();
        if (minExposureCompensation != 0 || maxExposureCompensation != 0) {
            float f = 0.0f;
            if (exposureCompensationStep > 0.0f) {
                if (!z) {
                    f = 1.5f;
                }
                int round = Math.round(f / exposureCompensationStep);
                float f2 = exposureCompensationStep * round;
                int max = Math.max(Math.min(round, maxExposureCompensation), minExposureCompensation);
                if (parameters.getExposureCompensation() == max) {
                    LogUtils.m3904d("Exposure compensation already set to " + max + " / " + f2);
                    return;
                }
                LogUtils.m3904d("Setting exposure compensation to " + max + " / " + f2);
                parameters.setExposureCompensation(max);
                return;
            }
        }
        LogUtils.m3904d("Camera does not support exposure compensation");
    }

    public static void setFocusArea(Camera.Parameters parameters) {
        if (parameters.getMaxNumFocusAreas() > 0) {
            LogUtils.m3904d("Old focus areas: " + toString(parameters.getFocusAreas()));
            List<Camera.Area> buildMiddleArea = buildMiddleArea(LotteryDialog.MAX_VALUE);
            LogUtils.m3904d("Setting focus area to : " + toString(buildMiddleArea));
            parameters.setFocusAreas(buildMiddleArea);
            return;
        }
        LogUtils.m3904d("Device does not support focus areas");
    }

    public static void setMetering(Camera.Parameters parameters) {
        if (parameters.getMaxNumMeteringAreas() > 0) {
            LogUtils.m3904d("Old metering areas: " + parameters.getMeteringAreas());
            List<Camera.Area> buildMiddleArea = buildMiddleArea(LotteryDialog.MAX_VALUE);
            LogUtils.m3904d("Setting metering area to : " + toString(buildMiddleArea));
            parameters.setMeteringAreas(buildMiddleArea);
            return;
        }
        LogUtils.m3904d("Device does not support metering areas");
    }

    private static List<Camera.Area> buildMiddleArea(int i) {
        int i2 = -i;
        return Collections.singletonList(new Camera.Area(new Rect(i2, i2, i, i), 1));
    }

    public static void setVideoStabilization(Camera.Parameters parameters) {
        if (parameters.isVideoStabilizationSupported()) {
            if (parameters.getVideoStabilization()) {
                LogUtils.m3904d("Video stabilization already enabled");
                return;
            }
            LogUtils.m3904d("Enabling video stabilization...");
            parameters.setVideoStabilization(true);
            return;
        }
        LogUtils.m3904d("This device does not support video stabilization");
    }

    public static void setBarcodeSceneMode(Camera.Parameters parameters) {
        if ("barcode".equals(parameters.getSceneMode())) {
            LogUtils.m3904d("Barcode scene mode already set");
            return;
        }
        String findSettableValue = findSettableValue("scene mode", parameters.getSupportedSceneModes(), "barcode");
        if (findSettableValue == null) {
            return;
        }
        parameters.setSceneMode(findSettableValue);
    }

    public static void setInvertColor(Camera.Parameters parameters) {
        if ("negative".equals(parameters.getColorEffect())) {
            LogUtils.m3904d("Negative effect already set");
            return;
        }
        String findSettableValue = findSettableValue("color effect", parameters.getSupportedColorEffects(), "negative");
        if (findSettableValue == null) {
            return;
        }
        parameters.setColorEffect(findSettableValue);
    }

    public static Point findBestPreviewSizeValue(Camera.Parameters parameters, Point point) {
        double d;
        int i;
        List<Camera.Size> supportedPreviewSizes = parameters.getSupportedPreviewSizes();
        if (supportedPreviewSizes == null) {
            LogUtils.m3902w("Device returned no supported preview sizes; using default");
            Camera.Size previewSize = parameters.getPreviewSize();
            if (previewSize == null) {
                throw new IllegalStateException("Parameters contained no preview size!");
            }
            return new Point(previewSize.width, previewSize.height);
        }
        if (LogUtils.isShowLog()) {
            StringBuilder sb = new StringBuilder();
            for (Camera.Size size : supportedPreviewSizes) {
                sb.append(size.width);
                sb.append('x');
                sb.append(size.height);
                sb.append(' ');
            }
            LogUtils.m3904d("Supported preview sizes: " + ((Object) sb));
        }
        int i2 = point.x;
        int i3 = point.y;
        double d2 = i2 < i3 ? i2 / i3 : i3 / i2;
        LogUtils.m3904d("screenAspectRatio: " + d2);
        Camera.Size size2 = null;
        char c = 0;
        int i4 = 0;
        for (Camera.Size size3 : supportedPreviewSizes) {
            int i5 = size3.width;
            int i6 = size3.height;
            int i7 = i5 * i6;
            if (i7 < 153600) {
                d = d2;
                i = i4;
            } else {
                boolean z = i5 < i6;
                int i8 = z ? i5 : i6;
                int i9 = z ? i6 : i5;
                Object[] objArr = new Object[2];
                objArr[c] = Integer.valueOf(i8);
                objArr[1] = Integer.valueOf(i9);
                LogUtils.m3904d(String.format("maybeFlipped:%d * %d", objArr));
                d = d2;
                i = i4;
                double d3 = i8 / i9;
                LogUtils.m3904d("aspectRatio: " + d3);
                double abs = Math.abs(d3 - d);
                LogUtils.m3904d("distortion: " + abs);
                if (abs <= 0.05d) {
                    if (i8 == point.x && i9 == point.y) {
                        Point point2 = new Point(i5, i6);
                        LogUtils.m3904d("Found preview size exactly matching screen size: " + point2);
                        return point2;
                    } else if (i7 > i) {
                        size2 = size3;
                        i4 = i7;
                        d2 = d;
                        c = 0;
                    }
                }
            }
            i4 = i;
            d2 = d;
            c = 0;
        }
        if (size2 != null) {
            Point point3 = new Point(size2.width, size2.height);
            LogUtils.m3904d("Using largest suitable preview size: " + point3);
            return point3;
        }
        Camera.Size previewSize2 = parameters.getPreviewSize();
        if (previewSize2 == null) {
            throw new IllegalStateException("Parameters contained no preview size!");
        }
        Point point4 = new Point(previewSize2.width, previewSize2.height);
        LogUtils.m3904d("No suitable preview sizes, using default: " + point4);
        return point4;
    }

    private static String findSettableValue(String str, Collection<String> collection, String... strArr) {
        LogUtils.m3904d("Requesting " + str + " value from among: " + Arrays.toString(strArr));
        LogUtils.m3904d("Supported " + str + " values: " + collection);
        if (collection != null) {
            for (String str2 : strArr) {
                if (collection.contains(str2)) {
                    LogUtils.m3904d("Can set " + str + " to: " + str2);
                    return str2;
                }
            }
        }
        LogUtils.m3904d("No supported values match");
        return null;
    }

    private static String toString(Iterable<Camera.Area> iterable) {
        if (iterable == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (Camera.Area area : iterable) {
            sb.append(area.rect);
            sb.append(':');
            sb.append(area.weight);
            sb.append(' ');
        }
        return sb.toString();
    }
}
