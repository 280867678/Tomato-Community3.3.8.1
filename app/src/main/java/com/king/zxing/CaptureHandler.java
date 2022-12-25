package com.king.zxing;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;
import com.king.zxing.camera.CameraManager;
import java.util.Collection;
import java.util.Map;

/* loaded from: classes3.dex */
public class CaptureHandler extends Handler implements ResultPointCallback {
    private final CameraManager cameraManager;
    private final DecodeThread decodeThread;
    private boolean isReturnBitmap;
    private boolean isSupportAutoZoom;
    private boolean isSupportLuminanceInvert;
    private boolean isSupportVerticalCode;
    private final OnCaptureListener onCaptureListener;
    private State state = State.SUCCESS;
    private final ViewfinderView viewfinderView;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public enum State {
        PREVIEW,
        SUCCESS,
        DONE
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public CaptureHandler(Activity activity, ViewfinderView viewfinderView, OnCaptureListener onCaptureListener, Collection<BarcodeFormat> collection, Map<DecodeHintType, Object> map, String str, CameraManager cameraManager) {
        this.viewfinderView = viewfinderView;
        this.onCaptureListener = onCaptureListener;
        this.decodeThread = new DecodeThread(activity, cameraManager, this, collection, map, str, this);
        this.decodeThread.start();
        this.cameraManager = cameraManager;
        cameraManager.startPreview();
        restartPreviewAndDecode();
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        int i = message.what;
        if (i == R$id.restart_preview) {
            restartPreviewAndDecode();
        } else if (i == R$id.decode_succeeded) {
            this.state = State.SUCCESS;
            Bundle data = message.getData();
            float f = 1.0f;
            Bitmap bitmap = null;
            if (data != null) {
                byte[] byteArray = data.getByteArray("barcode_bitmap");
                if (byteArray != null) {
                    bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length, null).copy(Bitmap.Config.ARGB_8888, true);
                }
                f = data.getFloat("barcode_scaled_factor");
            }
            this.onCaptureListener.onHandleDecode((Result) message.obj, bitmap, f);
        } else if (i != R$id.decode_failed) {
        } else {
            this.state = State.PREVIEW;
            this.cameraManager.requestPreviewFrame(this.decodeThread.getHandler(), R$id.decode);
        }
    }

    public void quitSynchronously() {
        this.state = State.DONE;
        this.cameraManager.stopPreview();
        Message.obtain(this.decodeThread.getHandler(), R$id.quit).sendToTarget();
        try {
            this.decodeThread.join(100L);
        } catch (InterruptedException unused) {
        }
        removeMessages(R$id.decode_succeeded);
        removeMessages(R$id.decode_failed);
    }

    public void restartPreviewAndDecode() {
        if (this.state == State.SUCCESS) {
            this.state = State.PREVIEW;
            this.cameraManager.requestPreviewFrame(this.decodeThread.getHandler(), R$id.decode);
            ViewfinderView viewfinderView = this.viewfinderView;
            if (viewfinderView == null) {
                return;
            }
            viewfinderView.drawViewfinder();
        }
    }

    @Override // com.google.zxing.ResultPointCallback
    public void foundPossibleResultPoint(ResultPoint resultPoint) {
        if (this.viewfinderView != null) {
            this.viewfinderView.addPossibleResultPoint(transform(resultPoint));
        }
    }

    private ResultPoint transform(ResultPoint resultPoint) {
        float x;
        float y;
        int max;
        Point screenResolution = this.cameraManager.getScreenResolution();
        Point cameraResolution = this.cameraManager.getCameraResolution();
        int i = screenResolution.x;
        int i2 = screenResolution.y;
        if (i < i2) {
            float f = (i2 * 1.0f) / cameraResolution.x;
            x = (resultPoint.getX() * ((i * 1.0f) / cameraResolution.y)) - (Math.max(screenResolution.x, cameraResolution.y) / 2);
            y = resultPoint.getY() * f;
            max = Math.min(screenResolution.y, cameraResolution.x) / 2;
        } else {
            float f2 = (i2 * 1.0f) / cameraResolution.y;
            x = (resultPoint.getX() * ((i * 1.0f) / cameraResolution.x)) - (Math.min(screenResolution.y, cameraResolution.y) / 2);
            y = resultPoint.getY() * f2;
            max = Math.max(screenResolution.x, cameraResolution.x) / 2;
        }
        return new ResultPoint(x, y - max);
    }

    public boolean isSupportVerticalCode() {
        return this.isSupportVerticalCode;
    }

    public void setSupportVerticalCode(boolean z) {
        this.isSupportVerticalCode = z;
    }

    public boolean isReturnBitmap() {
        return this.isReturnBitmap;
    }

    public void setReturnBitmap(boolean z) {
        this.isReturnBitmap = z;
    }

    public boolean isSupportAutoZoom() {
        return this.isSupportAutoZoom;
    }

    public void setSupportAutoZoom(boolean z) {
        this.isSupportAutoZoom = z;
    }

    public boolean isSupportLuminanceInvert() {
        return this.isSupportLuminanceInvert;
    }

    public void setSupportLuminanceInvert(boolean z) {
        this.isSupportLuminanceInvert = z;
    }
}
