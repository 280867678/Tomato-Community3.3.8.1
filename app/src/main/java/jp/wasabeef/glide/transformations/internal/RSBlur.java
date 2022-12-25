package jp.wasabeef.glide.transformations.internal;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RSRuntimeException;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

@Deprecated
/* loaded from: classes4.dex */
public class RSBlur {
    /* JADX WARN: Removed duplicated region for block: B:27:0x0060  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x006d  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0077  */
    @TargetApi(18)
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static Bitmap blur(Context context, Bitmap bitmap, int i) throws RSRuntimeException {
        RenderScript renderScript;
        ScriptIntrinsicBlur scriptIntrinsicBlur;
        Allocation allocation;
        Allocation allocation2 = null;
        ScriptIntrinsicBlur scriptIntrinsicBlur2 = null;
        allocation2 = null;
        try {
            renderScript = RenderScript.create(context);
            try {
                renderScript.setMessageHandler(new RenderScript.RSMessageHandler());
                allocation = Allocation.createFromBitmap(renderScript, bitmap, Allocation.MipmapControl.MIPMAP_NONE, 1);
                try {
                    Allocation createTyped = Allocation.createTyped(renderScript, allocation.getType());
                    try {
                        scriptIntrinsicBlur2 = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
                        scriptIntrinsicBlur2.setInput(allocation);
                        scriptIntrinsicBlur2.setRadius(i);
                        scriptIntrinsicBlur2.forEach(createTyped);
                        createTyped.copyTo(bitmap);
                        if (renderScript != null) {
                            if (Build.VERSION.SDK_INT >= 23) {
                                RenderScript.releaseAllContexts();
                            } else {
                                renderScript.destroy();
                            }
                        }
                        if (allocation != null) {
                            allocation.destroy();
                        }
                        if (createTyped != null) {
                            createTyped.destroy();
                        }
                        if (scriptIntrinsicBlur2 != null) {
                            scriptIntrinsicBlur2.destroy();
                        }
                        return bitmap;
                    } catch (Throwable th) {
                        th = th;
                        scriptIntrinsicBlur = scriptIntrinsicBlur2;
                        allocation2 = createTyped;
                        if (renderScript != null) {
                            if (Build.VERSION.SDK_INT >= 23) {
                                RenderScript.releaseAllContexts();
                            } else {
                                renderScript.destroy();
                            }
                        }
                        if (allocation != null) {
                            allocation.destroy();
                        }
                        if (allocation2 != null) {
                            allocation2.destroy();
                        }
                        if (scriptIntrinsicBlur != null) {
                            scriptIntrinsicBlur.destroy();
                        }
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    scriptIntrinsicBlur = null;
                }
            } catch (Throwable th3) {
                th = th3;
                scriptIntrinsicBlur = null;
                allocation = scriptIntrinsicBlur;
                if (renderScript != null) {
                }
                if (allocation != null) {
                }
                if (allocation2 != null) {
                }
                if (scriptIntrinsicBlur != null) {
                }
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            renderScript = null;
            scriptIntrinsicBlur = null;
        }
    }
}
