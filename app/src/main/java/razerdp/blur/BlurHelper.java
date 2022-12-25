package razerdp.blur;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Looper;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RSIllegalArgumentException;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.view.View;
import android.widget.Toast;
import com.eclipsesource.p056v8.Platform;
import razerdp.basepopup.BasePopupWindow;
import razerdp.util.log.PopupLog;

/* loaded from: classes4.dex */
public class BlurHelper {
    private static long startTime;
    private static int statusBarHeight;

    public static boolean renderScriptSupported() {
        return Build.VERSION.SDK_INT > 17;
    }

    public static Bitmap blur(Context context, View view, float f, float f2, boolean z) {
        return blur(context, getViewBitmap(view, f, z), view.getWidth(), view.getHeight(), f2);
    }

    public static Bitmap blur(Context context, Bitmap bitmap, int i, int i2, float f) {
        startTime = System.currentTimeMillis();
        if (renderScriptSupported()) {
            PopupLog.m25i("BlurHelper", "脚本模糊");
            return scriptBlur(context, bitmap, i, i2, f);
        }
        PopupLog.m25i("BlurHelper", "快速模糊");
        return fastBlur(context, bitmap, i, i2, f);
    }

    @TargetApi(17)
    public static Bitmap scriptBlur(Context context, Bitmap bitmap, int i, int i2, float f) {
        ScriptIntrinsicBlur scriptIntrinsicBlur = null;
        if (bitmap == null || bitmap.isRecycled()) {
            return null;
        }
        RenderScript create = RenderScript.create(context.getApplicationContext());
        Allocation createFromBitmap = Allocation.createFromBitmap(create, bitmap);
        Allocation createTyped = Allocation.createTyped(create, createFromBitmap.getType());
        try {
            scriptIntrinsicBlur = ScriptIntrinsicBlur.create(create, createFromBitmap.getElement());
        } catch (RSIllegalArgumentException e) {
            if (e.getMessage().contains("Unsuported element type")) {
                scriptIntrinsicBlur = ScriptIntrinsicBlur.create(create, Element.U8_4(create));
            }
        }
        if (scriptIntrinsicBlur == null) {
            PopupLog.m27e("BlurHelper", "脚本模糊失败，转fastBlur");
            return fastBlur(context, bitmap, i, i2, f);
        }
        scriptIntrinsicBlur.setRadius(range(f, 0.0f, 20.0f));
        scriptIntrinsicBlur.setInput(createFromBitmap);
        scriptIntrinsicBlur.forEach(createTyped);
        createTyped.copyTo(bitmap);
        create.destroy();
        createFromBitmap.destroy();
        createTyped.destroy();
        Bitmap createScaledBitmap = Bitmap.createScaledBitmap(bitmap, i, i2, true);
        bitmap.recycle();
        long currentTimeMillis = System.currentTimeMillis() - startTime;
        if (BasePopupWindow.DEBUG) {
            toast(context, "模糊用时：【" + currentTimeMillis + "ms】");
        }
        PopupLog.m25i("BlurHelper", "模糊用时：【" + currentTimeMillis + "ms】");
        return createScaledBitmap;
    }

    public static Bitmap fastBlur(Context context, Bitmap bitmap, int i, int i2, float f) {
        Bitmap doBlur;
        if (bitmap == null || bitmap.isRecycled() || (doBlur = FastBlur.doBlur(bitmap, (int) range(f, 0.0f, 20.0f), false)) == null || doBlur.isRecycled()) {
            return null;
        }
        Bitmap createScaledBitmap = Bitmap.createScaledBitmap(doBlur, i, i2, true);
        long currentTimeMillis = System.currentTimeMillis() - startTime;
        if (BasePopupWindow.DEBUG) {
            toast(context, "模糊用时：【" + currentTimeMillis + "ms】");
        }
        PopupLog.m25i("BlurHelper", "模糊用时：【" + currentTimeMillis + "ms】");
        return createScaledBitmap;
    }

    public static Bitmap getViewBitmap(View view, float f, boolean z) {
        if (view == null || view.getWidth() <= 0 || view.getHeight() <= 0) {
            PopupLog.m27e("getViewBitmap  >>  宽或者高为空", new Object[0]);
            return null;
        }
        if (statusBarHeight <= 0) {
            statusBarHeight = getStatusBarHeight(view.getContext());
        }
        PopupLog.m26i("模糊原始图像分辨率 [" + view.getWidth() + " x " + view.getHeight() + "]");
        try {
            Bitmap createBitmap = Bitmap.createBitmap((int) (view.getWidth() * f), (int) (view.getHeight() * f), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(createBitmap);
            Matrix matrix = new Matrix();
            matrix.preScale(f, f);
            canvas.setMatrix(matrix);
            Drawable background = view.getBackground();
            if (background == null) {
                canvas.drawColor(Color.parseColor("#FAFAFA"));
            } else {
                background.draw(canvas);
            }
            if (z && statusBarHeight > 0 && Build.VERSION.SDK_INT >= 21 && (view.getContext() instanceof Activity)) {
                int statusBarColor = ((Activity) view.getContext()).getWindow().getStatusBarColor();
                Paint paint = new Paint(1);
                paint.setColor(statusBarColor);
                canvas.drawRect(new Rect(0, 0, view.getWidth(), statusBarHeight), paint);
            }
            view.draw(canvas);
            PopupLog.m26i("模糊缩放图像分辨率 [" + createBitmap.getWidth() + " x " + createBitmap.getHeight() + "]");
            return createBitmap;
        } catch (OutOfMemoryError unused) {
            System.gc();
            return null;
        }
    }

    public static float range(float f, float f2, float f3) {
        return Math.max(f2, Math.min(f, f3));
    }

    private static int getStatusBarHeight(Context context) {
        int identifier;
        if (context != null && (identifier = context.getResources().getIdentifier("status_bar_height", "dimen", Platform.ANDROID)) > 0) {
            return context.getResources().getDimensionPixelSize(identifier);
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void toast(final Context context, final String str) {
        if (Looper.myLooper() == null || Looper.myLooper() != Looper.getMainLooper()) {
            if (!(context instanceof Activity)) {
                return;
            }
            ((Activity) context).runOnUiThread(new Runnable() { // from class: razerdp.blur.BlurHelper.1
                @Override // java.lang.Runnable
                public void run() {
                    BlurHelper.toast(context, str);
                }
            });
            return;
        }
        Toast.makeText(context.getApplicationContext(), str, 0).show();
    }
}
