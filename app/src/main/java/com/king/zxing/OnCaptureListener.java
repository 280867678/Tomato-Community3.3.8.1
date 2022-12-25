package com.king.zxing;

import android.graphics.Bitmap;
import com.google.zxing.Result;

/* loaded from: classes3.dex */
public interface OnCaptureListener {
    void onHandleDecode(Result result, Bitmap bitmap, float f);
}
