package com.king.zxing;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Display;
import android.view.WindowManager;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.GlobalHistogramBinarizer;
import com.google.zxing.common.HybridBinarizer;
import com.king.zxing.camera.CameraManager;
import com.king.zxing.util.LogUtils;
import java.io.ByteArrayOutputStream;
import java.util.Map;

/* loaded from: classes3.dex */
final class DecodeHandler extends Handler {
    private final CameraManager cameraManager;
    private final Context context;
    private final CaptureHandler handler;
    private long lastZoomTime;
    private boolean running = true;
    private final MultiFormatReader multiFormatReader = new MultiFormatReader();

    /* JADX INFO: Access modifiers changed from: package-private */
    public DecodeHandler(Context context, CameraManager cameraManager, CaptureHandler captureHandler, Map<DecodeHintType, Object> map) {
        this.multiFormatReader.setHints(map);
        this.context = context;
        this.cameraManager = cameraManager;
        this.handler = captureHandler;
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        if (message == null || !this.running) {
            return;
        }
        int i = message.what;
        if (i == R$id.decode) {
            decode((byte[]) message.obj, message.arg1, message.arg2, isScreenPortrait(), this.handler.isSupportVerticalCode());
        } else if (i != R$id.quit) {
        } else {
            this.running = false;
            Looper.myLooper().quit();
        }
    }

    private boolean isScreenPortrait() {
        Display defaultDisplay = ((WindowManager) this.context.getSystemService("window")).getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        return point.x < point.y;
    }

    private void decode(byte[] bArr, int i, int i2, boolean z, boolean z2) {
        Result result;
        boolean z3;
        long currentTimeMillis = System.currentTimeMillis();
        PlanarYUVLuminanceSource buildPlanarYUVLuminanceSource = buildPlanarYUVLuminanceSource(bArr, i, i2, z);
        Result result2 = null;
        if (buildPlanarYUVLuminanceSource != null) {
            try {
                result = this.multiFormatReader.decodeWithState(new BinaryBitmap(new HybridBinarizer(buildPlanarYUVLuminanceSource)));
                z3 = false;
            } catch (Exception unused) {
                result = null;
                z3 = true;
            }
            if (z3 && this.handler.isSupportLuminanceInvert()) {
                try {
                    result = this.multiFormatReader.decodeWithState(new BinaryBitmap(new HybridBinarizer(buildPlanarYUVLuminanceSource.invert())));
                    z3 = false;
                } catch (Exception unused2) {
                    z3 = true;
                }
            }
            if (z3) {
                try {
                    result = this.multiFormatReader.decodeWithState(new BinaryBitmap(new GlobalHistogramBinarizer(buildPlanarYUVLuminanceSource)));
                    z3 = false;
                } catch (Exception unused3) {
                    z3 = true;
                }
            }
            if (z3 && z2) {
                PlanarYUVLuminanceSource buildPlanarYUVLuminanceSource2 = buildPlanarYUVLuminanceSource(bArr, i, i2, !z);
                if (buildPlanarYUVLuminanceSource2 != null) {
                    try {
                        buildPlanarYUVLuminanceSource = buildPlanarYUVLuminanceSource2;
                        result2 = this.multiFormatReader.decodeWithState(new BinaryBitmap(new HybridBinarizer(buildPlanarYUVLuminanceSource2)));
                    } catch (Exception unused4) {
                    }
                    this.multiFormatReader.reset();
                }
                buildPlanarYUVLuminanceSource = buildPlanarYUVLuminanceSource2;
            }
            result2 = result;
            this.multiFormatReader.reset();
        }
        if (result2 != null) {
            long currentTimeMillis2 = System.currentTimeMillis();
            LogUtils.m3904d("Found barcode in " + (currentTimeMillis2 - currentTimeMillis) + " ms");
            BarcodeFormat barcodeFormat = result2.getBarcodeFormat();
            CaptureHandler captureHandler = this.handler;
            if (captureHandler != null && captureHandler.isSupportAutoZoom() && barcodeFormat == BarcodeFormat.QR_CODE) {
                ResultPoint[] resultPoints = result2.getResultPoints();
                if (resultPoints.length >= 3) {
                    if (handleAutoZoom((int) Math.max(Math.max(ResultPoint.distance(resultPoints[0], resultPoints[1]), ResultPoint.distance(resultPoints[1], resultPoints[2])), ResultPoint.distance(resultPoints[0], resultPoints[2])), i)) {
                        Message obtain = Message.obtain();
                        obtain.what = R$id.decode_succeeded;
                        obtain.obj = result2;
                        if (this.handler.isReturnBitmap()) {
                            Bundle bundle = new Bundle();
                            bundleThumbnail(buildPlanarYUVLuminanceSource, bundle);
                            obtain.setData(bundle);
                        }
                        this.handler.sendMessageDelayed(obtain, 300L);
                        return;
                    }
                }
            }
            CaptureHandler captureHandler2 = this.handler;
            if (captureHandler2 == null) {
                return;
            }
            Message obtain2 = Message.obtain(captureHandler2, R$id.decode_succeeded, result2);
            if (this.handler.isReturnBitmap()) {
                Bundle bundle2 = new Bundle();
                bundleThumbnail(buildPlanarYUVLuminanceSource, bundle2);
                obtain2.setData(bundle2);
            }
            obtain2.sendToTarget();
            return;
        }
        CaptureHandler captureHandler3 = this.handler;
        if (captureHandler3 == null) {
            return;
        }
        Message.obtain(captureHandler3, R$id.decode_failed).sendToTarget();
    }

    private PlanarYUVLuminanceSource buildPlanarYUVLuminanceSource(byte[] bArr, int i, int i2, boolean z) {
        if (z) {
            byte[] bArr2 = new byte[bArr.length];
            for (int i3 = 0; i3 < i2; i3++) {
                for (int i4 = 0; i4 < i; i4++) {
                    bArr2[(((i4 * i2) + i2) - i3) - 1] = bArr[(i3 * i) + i4];
                }
            }
            return this.cameraManager.buildLuminanceSource(bArr2, i2, i);
        }
        return this.cameraManager.buildLuminanceSource(bArr, i, i2);
    }

    private static void bundleThumbnail(PlanarYUVLuminanceSource planarYUVLuminanceSource, Bundle bundle) {
        int[] renderThumbnail = planarYUVLuminanceSource.renderThumbnail();
        int thumbnailWidth = planarYUVLuminanceSource.getThumbnailWidth();
        Bitmap createBitmap = Bitmap.createBitmap(renderThumbnail, 0, thumbnailWidth, thumbnailWidth, planarYUVLuminanceSource.getThumbnailHeight(), Bitmap.Config.ARGB_8888);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        createBitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        bundle.putByteArray("barcode_bitmap", byteArrayOutputStream.toByteArray());
        bundle.putFloat("barcode_scaled_factor", thumbnailWidth / planarYUVLuminanceSource.getWidth());
    }

    private boolean handleAutoZoom(int i, int i2) {
        Camera camera;
        if (this.lastZoomTime > System.currentTimeMillis() - 1000) {
            return true;
        }
        if (i >= i2 / 5 || (camera = this.cameraManager.getOpenCamera().getCamera()) == null) {
            return false;
        }
        Camera.Parameters parameters = camera.getParameters();
        if (parameters.isZoomSupported()) {
            int maxZoom = parameters.getMaxZoom();
            parameters.setZoom(Math.min(parameters.getZoom() + (maxZoom / 5), maxZoom));
            camera.setParameters(parameters);
            this.lastZoomTime = System.currentTimeMillis();
            return true;
        }
        LogUtils.m3903i("Zoom not supported");
        return false;
    }
}
