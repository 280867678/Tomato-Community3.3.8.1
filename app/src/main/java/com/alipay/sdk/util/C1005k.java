package com.alipay.sdk.util;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import com.alipay.sdk.encrypt.C0967a;
import java.io.ByteArrayInputStream;
import java.io.Closeable;

/* renamed from: com.alipay.sdk.util.k */
/* loaded from: classes2.dex */
public class C1005k {
    /* renamed from: a */
    public static Drawable m4405a(String str, Context context) {
        return m4404a(str, context, 480);
    }

    /* renamed from: a */
    public static Drawable m4404a(String str, Context context, int i) {
        ByteArrayInputStream byteArrayInputStream;
        try {
            byteArrayInputStream = new ByteArrayInputStream(C0967a.m4557a(str));
        } catch (Throwable unused) {
            byteArrayInputStream = null;
        }
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            if (i <= 0) {
                i = 240;
            }
            options.inDensity = i;
            options.inTargetDensity = context.getResources().getDisplayMetrics().densityDpi;
            BitmapDrawable bitmapDrawable = new BitmapDrawable(context.getResources(), BitmapFactory.decodeStream(byteArrayInputStream, null, options));
            m4406a(byteArrayInputStream);
            return bitmapDrawable;
        } catch (Throwable unused2) {
            m4406a(byteArrayInputStream);
            return null;
        }
    }

    /* renamed from: a */
    private static void m4406a(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Throwable unused) {
            }
        }
    }
}
