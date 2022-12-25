package com.king.zxing;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.ResultPointCallback;
import com.king.zxing.camera.CameraManager;
import com.king.zxing.util.LogUtils;
import java.util.Collection;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes3.dex */
public final class DecodeThread extends Thread {
    private final CameraManager cameraManager;
    private CaptureHandler captureHandler;
    private final Context context;
    private Handler handler;
    private final CountDownLatch handlerInitLatch = new CountDownLatch(1);
    private final Map<DecodeHintType, Object> hints = new EnumMap(DecodeHintType.class);

    /* JADX INFO: Access modifiers changed from: package-private */
    public DecodeThread(Context context, CameraManager cameraManager, CaptureHandler captureHandler, Collection<BarcodeFormat> collection, Map<DecodeHintType, Object> map, String str, ResultPointCallback resultPointCallback) {
        this.context = context;
        this.cameraManager = cameraManager;
        this.captureHandler = captureHandler;
        if (map != null) {
            this.hints.putAll(map);
        }
        if (collection == null || collection.isEmpty()) {
            SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            collection = EnumSet.noneOf(BarcodeFormat.class);
            if (defaultSharedPreferences.getBoolean("preferences_decode_1D_product", true)) {
                collection.addAll(DecodeFormatManager.PRODUCT_FORMATS);
            }
            if (defaultSharedPreferences.getBoolean("preferences_decode_1D_industrial", true)) {
                collection.addAll(DecodeFormatManager.INDUSTRIAL_FORMATS);
            }
            if (defaultSharedPreferences.getBoolean("preferences_decode_QR", true)) {
                collection.addAll(DecodeFormatManager.QR_CODE_FORMATS);
            }
            if (defaultSharedPreferences.getBoolean("preferences_decode_Data_Matrix", true)) {
                collection.addAll(DecodeFormatManager.DATA_MATRIX_FORMATS);
            }
            if (defaultSharedPreferences.getBoolean("preferences_decode_Aztec", false)) {
                collection.addAll(DecodeFormatManager.AZTEC_FORMATS);
            }
            if (defaultSharedPreferences.getBoolean("preferences_decode_PDF417", false)) {
                collection.addAll(DecodeFormatManager.PDF417_FORMATS);
            }
        }
        this.hints.put(DecodeHintType.POSSIBLE_FORMATS, collection);
        if (str != null) {
            this.hints.put(DecodeHintType.CHARACTER_SET, str);
        }
        this.hints.put(DecodeHintType.NEED_RESULT_POINT_CALLBACK, resultPointCallback);
        LogUtils.m3903i("Hints: " + this.hints);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Handler getHandler() {
        try {
            this.handlerInitLatch.await();
        } catch (InterruptedException unused) {
        }
        return this.handler;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        Looper.prepare();
        this.handler = new DecodeHandler(this.context, this.cameraManager, this.captureHandler, this.hints);
        this.handlerInitLatch.countDown();
        Looper.loop();
    }
}
