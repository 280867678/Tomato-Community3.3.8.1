package com.gen.p059mh.webapp_extensions.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import com.gen.p059mh.webapp_extensions.manager.WACacheManager;
import com.gen.p059mh.webapp_extensions.task.CacheTask;
import com.gen.p059mh.webapp_extensions.task.Task;
import com.gen.p059mh.webapps.listener.NativeMethod;
import com.gen.p059mh.webapps.pugins.NativeViewPlugin;
import com.gen.p059mh.webapps.utils.Logger;
import java.util.HashMap;
import java.util.List;
import org.opengl.surface.GLSurface;

/* renamed from: com.gen.mh.webapp_extensions.views.WAEFGCanvas */
/* loaded from: classes2.dex */
public class WAEFGCanvas extends NativeViewPlugin.NativeView {
    GLSurface surface;

    @NativeMethod("input")
    public void input(List list, NativeViewPlugin.NativeView.MethodCallback methodCallback) {
    }

    public WAEFGCanvas(Context context) {
        super(context);
    }

    @Override // com.gen.p059mh.webapps.pugins.NativeViewPlugin.NativeView
    public void onInitialize(Object obj) {
        super.onInitialize(obj);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
        this.surface = new GLSurface(getContext());
        setInitNeedWait(true);
        this.surface.setInitCallBack(new GLSurface.GLSurfaceCallBack() { // from class: com.gen.mh.webapp_extensions.views.-$$Lambda$WAEFGCanvas$Sh4AiSX75fMVwxJwYOuB1DfjpXQ
            @Override // org.opengl.surface.GLSurface.GLSurfaceCallBack
            public final void createSuccess() {
                WAEFGCanvas.this.lambda$onInitialize$0$WAEFGCanvas();
            }
        });
        this.surface.setLayoutParams(layoutParams);
        addView(this.surface);
    }

    public /* synthetic */ void lambda$onInitialize$0$WAEFGCanvas() {
        if (getViewInitCallBack() != null) {
            getWebViewFragment().loadComplete(2);
            getViewInitCallBack().onInitSuccess();
        }
    }

    @NativeMethod("loadImage")
    public static void loadImage(List list, final NativeViewPlugin.NativeView.MethodCallback methodCallback) {
        if (list.size() >= 2) {
            final String obj = list.get(0).toString();
            final String obj2 = list.get(1).toString();
            if (WACacheManager.getInstance().hasHash(obj)) {
                Bitmap loadFromFile = WACacheManager.getInstance().loadFromFile(obj);
                if (loadFromFile != null) {
                    HashMap hashMap = new HashMap();
                    HashMap hashMap2 = new HashMap();
                    hashMap.put("success", true);
                    hashMap.put("size", hashMap2);
                    hashMap2.put("width", Integer.valueOf(loadFromFile.getWidth()));
                    hashMap2.put("height", Integer.valueOf(loadFromFile.getHeight()));
                    Log.e("loadImage exist", "hash:" + obj + " src:" + obj2 + " map:" + hashMap.toString());
                    methodCallback.run(hashMap);
                    return;
                }
                HashMap hashMap3 = new HashMap();
                hashMap3.put("success", false);
                methodCallback.run(hashMap3);
                return;
            }
            CacheTask cacheTask = new CacheTask(obj, obj2);
            cacheTask.addEvent();
            cacheTask.setStateListener(new Task.StateListener() { // from class: com.gen.mh.webapp_extensions.views.-$$Lambda$WAEFGCanvas$7g-8CfJzk5V_-CvWT56FW4DEf8o
                @Override // com.gen.p059mh.webapp_extensions.task.Task.StateListener
                public final void onComplete(int i, int i2) {
                    WAEFGCanvas.lambda$loadImage$1(obj, obj2, methodCallback, i, i2);
                }
            });
            return;
        }
        HashMap hashMap4 = new HashMap();
        hashMap4.put("success", false);
        methodCallback.run(hashMap4);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$loadImage$1(String str, String str2, NativeViewPlugin.NativeView.MethodCallback methodCallback, int i, int i2) {
        HashMap hashMap = new HashMap();
        HashMap hashMap2 = new HashMap();
        hashMap.put("success", true);
        hashMap.put("size", hashMap2);
        hashMap2.put("width", Integer.valueOf(i));
        hashMap2.put("height", Integer.valueOf(i2));
        Log.e("loadImage", "hash:" + str + " src:" + str2 + " map:" + hashMap.toString());
        methodCallback.run(hashMap);
    }

    @NativeMethod("draw")
    public void draw(List list, NativeViewPlugin.NativeView.MethodCallback methodCallback) {
        this.surface.input(list.get(0).toString());
        this.surface.nativeDraw();
        methodCallback.run(null);
    }

    @NativeMethod("getResult")
    public void getResult(List list, NativeViewPlugin.NativeView.MethodCallback methodCallback) {
        Logger.m4115e("ppp:" + list.get(0).toString());
        Object nativeGetResult = this.surface.nativeGetResult(list.get(0).toString());
        Logger.m4115e("result:" + nativeGetResult);
        methodCallback.run(nativeGetResult);
    }

    @NativeMethod("getImageData")
    public void getImageData(List list, NativeViewPlugin.NativeView.MethodCallback methodCallback) {
        this.surface.getImageData(new float[]{Float.valueOf(list.get(0).toString()).floatValue(), Float.valueOf(list.get(1).toString()).floatValue(), Float.valueOf(list.get(2).toString()).floatValue(), Float.valueOf(list.get(3).toString()).floatValue()});
        methodCallback.run("success");
    }

    @NativeMethod("setup")
    public void setup(List list, NativeViewPlugin.NativeView.MethodCallback methodCallback) {
        Logger.m4114e("setup1", list.toString());
        String nativeSetup = this.surface.nativeSetup(list.get(0).toString(), list.get(1), list.size() >= 3 ? (String) list.get(2) : "");
        Logger.m4114e("setup result", nativeSetup);
        methodCallback.run(nativeSetup);
    }

    @NativeMethod("post")
    public void post(List list, NativeViewPlugin.NativeView.MethodCallback methodCallback) {
        this.surface.input(list.get(0).toString());
        methodCallback.run(null);
    }

    @NativeMethod("setWidth")
    public void setWidth(List list, NativeViewPlugin.NativeView.MethodCallback methodCallback) {
        final int dip2px = GLSurface.dip2px(getContext(), ((Number) list.get(0)).intValue());
        this.surface.nativeSetWidth(((Number) list.get(0)).intValue());
        getWebViewFragment().getActivity().runOnUiThread(new Runnable() { // from class: com.gen.mh.webapp_extensions.views.-$$Lambda$WAEFGCanvas$d9hI25AQOdO6E1faj46ZUo3s8do
            @Override // java.lang.Runnable
            public final void run() {
                WAEFGCanvas.this.lambda$setWidth$2$WAEFGCanvas(dip2px);
            }
        });
        methodCallback.run(true);
    }

    public /* synthetic */ void lambda$setWidth$2$WAEFGCanvas(int i) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) getLayoutParams();
        layoutParams.width = i;
        setLayoutParams(layoutParams);
    }

    @NativeMethod("setHeight")
    public void setHeight(List list, NativeViewPlugin.NativeView.MethodCallback methodCallback) {
        final int dip2px = GLSurface.dip2px(getContext(), ((Number) list.get(0)).intValue());
        this.surface.nativeSetHeight(((Number) list.get(0)).intValue());
        getWebViewFragment().getActivity().runOnUiThread(new Runnable() { // from class: com.gen.mh.webapp_extensions.views.-$$Lambda$WAEFGCanvas$TFcvg91JXnm9KLw2wNCjtUq1hG8
            @Override // java.lang.Runnable
            public final void run() {
                WAEFGCanvas.this.lambda$setHeight$3$WAEFGCanvas(dip2px);
            }
        });
        methodCallback.run(true);
    }

    public /* synthetic */ void lambda$setHeight$3$WAEFGCanvas(int i) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) getLayoutParams();
        layoutParams.height = i;
        setLayoutParams(layoutParams);
    }

    @Override // com.gen.p059mh.webapps.pugins.NativeViewPlugin.NativeView
    public void onDestroy() {
        super.onDestroy();
        GLSurface gLSurface = this.surface;
        if (gLSurface != null) {
            gLSurface.onDestroy();
        }
    }
}
