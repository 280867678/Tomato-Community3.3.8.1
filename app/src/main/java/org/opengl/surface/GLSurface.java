package org.opengl.surface;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import com.eclipsesource.p056v8.Platform;

/* loaded from: classes4.dex */
public class GLSurface extends SurfaceView implements SurfaceHolder.Callback {
    boolean firstCreate;
    GLRender glRender;
    GLSurfaceCallBack glSurfaceCallBack;
    float scale;
    int screenHeight;
    int screenWidth;
    int statusBarHeight;
    int surfaceHeight;
    int surfaceWidth;
    public String TAG = GLSurface.class.getSimpleName();
    boolean isScale = false;

    /* loaded from: classes4.dex */
    public interface GLSurfaceCallBack {
        void createSuccess();
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
    }

    public GLSurface(Context context) {
        super(context);
        init();
    }

    public GLSurface(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public GLSurface(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    public void init() {
        getHolder().addCallback(this);
        this.scale = getContext().getResources().getDisplayMetrics().density;
        this.glRender = new GLRender();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) getContext().getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
        this.screenWidth = displayMetrics.widthPixels;
        this.screenHeight = displayMetrics.heightPixels;
        this.statusBarHeight = getStatusBarHeight(getContext());
    }

    public static int dip2px(Context context, float f) {
        return (int) ((f * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static float px2dip(Context context, int i) {
        return i / context.getResources().getDisplayMetrics().density;
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        Log.e(this.TAG, "surfaceCreated");
        this.glRender.nativeSetSurface(surfaceHolder.getSurface());
    }

    public static int getStatusBarHeight(Context context) {
        int identifier = context.getResources().getIdentifier("status_bar_height", "dimen", Platform.ANDROID);
        if (identifier > 0) {
            return context.getResources().getDimensionPixelSize(identifier);
        }
        return 0;
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        Log.e(this.TAG, "surfaceChanged width:" + i2 + " height:" + i3);
        Log.e("sss", "scale from x" + this.screenWidth + ":yy" + this.screenHeight);
        if (i3 > this.screenHeight || i2 > this.screenWidth) {
            float f = i2 / this.screenWidth;
            float f2 = i3 / this.screenHeight;
            setPivotX(0.0f);
            setPivotY(this.statusBarHeight);
            float f3 = 1.0f;
            setScaleX(f == 0.0f ? 1.0f : 1.0f / f);
            if (f2 != 0.0f) {
                f3 = 1.0f / f2;
            }
            setScaleY(f3);
            this.isScale = true;
        }
        onCreateCanvas(i2, i3, this.scale);
        GLSurfaceCallBack gLSurfaceCallBack = this.glSurfaceCallBack;
        if (gLSurfaceCallBack == null || this.firstCreate) {
            return;
        }
        gLSurfaceCallBack.createSuccess();
        this.firstCreate = true;
    }

    public void onCreateCanvas(int i, int i2, float f) {
        this.surfaceWidth = i;
        this.surfaceHeight = i2;
        this.glRender.createCanvas(i, i2, f);
    }

    public void onPause() {
        this.glRender.nativeOnPause();
    }

    public void onStop() {
        this.glRender.nativeOnStop();
    }

    public void setInitCallBack(GLSurfaceCallBack gLSurfaceCallBack) {
        this.glSurfaceCallBack = gLSurfaceCallBack;
    }

    public void onDestroy() {
        onPause();
        onStop();
    }

    public void input(String str) {
        this.glRender.nativeInput(str);
    }

    public Object nativeDraw() {
        return Boolean.valueOf(this.glRender.draw());
    }

    public Object nativeGetResult(String str) {
        return this.glRender.nativeGetResult(str);
    }

    public byte[] getImageData(float[] fArr) {
        return this.glRender.getImageData(fArr);
    }

    public String nativeSetup(String str, Object obj, String str2) {
        return this.glRender.nativeSetup(str, obj, str2);
    }

    public void nativeSetWidth(int i) {
        this.surfaceWidth = i;
        this.glRender.nativeSetSize(i, this.surfaceHeight);
    }

    public void nativeSetHeight(int i) {
        this.surfaceHeight = i;
        this.glRender.nativeSetSize(this.surfaceWidth, i);
    }
}
