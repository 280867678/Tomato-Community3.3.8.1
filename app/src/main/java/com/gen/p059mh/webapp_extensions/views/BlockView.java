package com.gen.p059mh.webapp_extensions.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.gen.p059mh.webapp_extensions.matisse.internal.entity.SelectionSpec;
import com.gen.p059mh.webapps.pugins.NativeViewPlugin;
import com.gen.p059mh.webapps.utils.Utils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* renamed from: com.gen.mh.webapp_extensions.views.BlockView */
/* loaded from: classes2.dex */
public class BlockView extends NativeViewPlugin.NativeView {
    ImageView imageView;
    private NativeViewPlugin.NativeView.MethodHandler setBackgroundColor = new NativeViewPlugin.NativeView.MethodHandler() { // from class: com.gen.mh.webapp_extensions.views.BlockView.4
        @Override // com.gen.p059mh.webapps.pugins.NativeViewPlugin.NativeView.MethodHandler
        public void run(List list, NativeViewPlugin.NativeView.MethodCallback methodCallback) {
            if (list.size() > 0 && list.get(0) != null) {
                BlockView.this.doBackgroundColor(String.valueOf(list.get(0)));
            }
            HashMap hashMap = new HashMap();
            hashMap.put("success", true);
            methodCallback.run(hashMap);
        }
    };
    private NativeViewPlugin.NativeView.MethodHandler setSrc = new NativeViewPlugin.NativeView.MethodHandler() { // from class: com.gen.mh.webapp_extensions.views.BlockView.5
        @Override // com.gen.p059mh.webapps.pugins.NativeViewPlugin.NativeView.MethodHandler
        public void run(List list, NativeViewPlugin.NativeView.MethodCallback methodCallback) {
            if (list.size() > 0 && list.get(0) != null) {
                BlockView.this.doSrc(list.get(0).toString());
            }
            HashMap hashMap = new HashMap();
            hashMap.put("success", true);
            methodCallback.run(hashMap);
        }
    };
    private NativeViewPlugin.NativeView.MethodHandler setTouchEnable = new NativeViewPlugin.NativeView.MethodHandler() { // from class: com.gen.mh.webapp_extensions.views.BlockView.6
        @Override // com.gen.p059mh.webapps.pugins.NativeViewPlugin.NativeView.MethodHandler
        public void run(List list, NativeViewPlugin.NativeView.MethodCallback methodCallback) {
            if (list.size() <= 0 || list.get(0) == null) {
                return;
            }
            BlockView blockView = BlockView.this;
            if (blockView.imageView == null) {
                return;
            }
            blockView.doTouchEnable(((Boolean) list.get(0)).booleanValue());
            HashMap hashMap = new HashMap();
            hashMap.put("success", true);
            methodCallback.run(hashMap);
        }
    };
    private NativeViewPlugin.NativeView.MethodHandler setScaleType = new NativeViewPlugin.NativeView.MethodHandler() { // from class: com.gen.mh.webapp_extensions.views.BlockView.7
        @Override // com.gen.p059mh.webapps.pugins.NativeViewPlugin.NativeView.MethodHandler
        public void run(List list, NativeViewPlugin.NativeView.MethodCallback methodCallback) {
            if (list.size() > 0 && list.get(0) != null) {
                BlockView.this.doScaleType((String) list.get(0));
            }
            HashMap hashMap = new HashMap();
            hashMap.put("success", true);
            methodCallback.run(hashMap);
        }
    };

    public BlockView(Context context) {
        super(context);
        registerMethod("setBackgroundColor", this.setBackgroundColor);
        registerMethod("setSrc", this.setSrc);
        registerMethod("setScaleType", this.setScaleType);
        registerMethod("setTouchEnable", this.setTouchEnable);
    }

    @Override // com.gen.p059mh.webapps.pugins.NativeViewPlugin.NativeView
    public void onInitialize(Object obj) {
        super.onInitialize(obj);
        this.imageView = new ImageView(getContext());
        this.imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        addView(this.imageView);
        this.imageView.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
        setFixed(true);
        if (obj != null) {
            initParams((Map) obj);
        }
    }

    private void initParams(Map map) {
        if (map.get("backgroundColor") != null) {
            doBackgroundColor(map.get("backgroundColor").toString());
        }
        if (map.get("src") != null) {
            doSrc(map.get("src").toString());
        }
        if (map.get("touchEnable") != null) {
            doTouchEnable(Boolean.valueOf(map.get("touchEnable").toString()).booleanValue());
        }
        if (map.get("scaleType") != null) {
            doScaleType(map.get("scaleType").toString());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doBackgroundColor(final String str) {
        ImageView imageView = this.imageView;
        if (imageView != null) {
            imageView.post(new Runnable() { // from class: com.gen.mh.webapp_extensions.views.BlockView.1
                @Override // java.lang.Runnable
                public void run() {
                    BlockView.this.setBackgroundColor(Utils.colorFromCSS(str));
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doSrc(final String str) {
        ImageView imageView = this.imageView;
        if (imageView != null) {
            imageView.post(new Runnable() { // from class: com.gen.mh.webapp_extensions.views.BlockView.2
                @Override // java.lang.Runnable
                public void run() {
                    SelectionSpec.getInstance().imageEngine.load(BlockView.this.imageView.getContext(), BlockView.this.imageView, Uri.parse(str));
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @SuppressLint({"ClickableViewAccessibility"})
    public void doTouchEnable(boolean z) {
        if (z) {
            setOnTouchListener(new View.OnTouchListener() { // from class: com.gen.mh.webapp_extensions.views.BlockView.3
                @Override // android.view.View.OnTouchListener
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (motionEvent.getAction() == 1) {
                        HashMap hashMap = new HashMap();
                        HashMap hashMap2 = new HashMap();
                        HashMap hashMap3 = new HashMap();
                        HashMap hashMap4 = new HashMap();
                        hashMap3.put("x", Float.valueOf(motionEvent.getX()));
                        hashMap3.put("y", Float.valueOf(motionEvent.getY()));
                        hashMap4.put("x", Float.valueOf(motionEvent.getRawX()));
                        hashMap4.put("y", Float.valueOf(motionEvent.getRawY()));
                        hashMap2.put("local", hashMap3);
                        hashMap2.put("global", hashMap4);
                        hashMap.put("type", "click");
                        hashMap.put("value", hashMap2);
                        BlockView.this.sendEvent(hashMap, null);
                    }
                    return true;
                }
            });
        } else {
            setOnTouchListener(null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doScaleType(String str) {
        ImageView.ScaleType scaleType;
        if (this.imageView != null) {
            char c = 65535;
            int hashCode = str.hashCode();
            if (hashCode != 101393) {
                if (hashCode != 3143043) {
                    if (hashCode == 951526612 && str.equals("contain")) {
                        c = 2;
                    }
                } else if (str.equals("fill")) {
                    c = 1;
                }
            } else if (str.equals("fit")) {
                c = 0;
            }
            if (c == 0) {
                scaleType = ImageView.ScaleType.CENTER_INSIDE;
            } else if (c == 1) {
                scaleType = ImageView.ScaleType.FIT_XY;
            } else {
                scaleType = c != 2 ? null : ImageView.ScaleType.CENTER_CROP;
            }
            if (scaleType == null) {
                return;
            }
            this.imageView.setScaleType(scaleType);
        }
    }
}
