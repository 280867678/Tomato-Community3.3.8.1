package com.gen.p059mh.webapp_extensions.views.camera.smartCamera;

import android.content.Context;
import android.util.SparseIntArray;
import android.view.Display;
import android.view.OrientationEventListener;

/* renamed from: com.gen.mh.webapp_extensions.views.camera.smartCamera.DisplayOrientationDetector */
/* loaded from: classes2.dex */
abstract class DisplayOrientationDetector {
    static final SparseIntArray DISPLAY_ORIENTATIONS = new SparseIntArray();
    Display mDisplay;
    private int mLastKnownDisplayOrientation = 0;
    private final OrientationEventListener mOrientationEventListener;

    public abstract void onDisplayOrientationChanged(int i);

    static {
        DISPLAY_ORIENTATIONS.put(0, 0);
        DISPLAY_ORIENTATIONS.put(1, 90);
        DISPLAY_ORIENTATIONS.put(2, 180);
        DISPLAY_ORIENTATIONS.put(3, 270);
    }

    public DisplayOrientationDetector(Context context) {
        this.mOrientationEventListener = new OrientationEventListener(context) { // from class: com.gen.mh.webapp_extensions.views.camera.smartCamera.DisplayOrientationDetector.1
            private int mLastKnownRotation = -1;

            @Override // android.view.OrientationEventListener
            public void onOrientationChanged(int i) {
                Display display;
                int rotation;
                if (i == -1 || (display = DisplayOrientationDetector.this.mDisplay) == null || this.mLastKnownRotation == (rotation = display.getRotation())) {
                    return;
                }
                this.mLastKnownRotation = rotation;
                DisplayOrientationDetector.this.dispatchOnDisplayOrientationChanged(DisplayOrientationDetector.DISPLAY_ORIENTATIONS.get(rotation));
            }
        };
    }

    public void enable(Display display) {
        this.mDisplay = display;
        this.mOrientationEventListener.enable();
        dispatchOnDisplayOrientationChanged(DISPLAY_ORIENTATIONS.get(display.getRotation()));
    }

    public void disable() {
        this.mOrientationEventListener.disable();
        this.mDisplay = null;
    }

    public int getLastKnownDisplayOrientation() {
        return this.mLastKnownDisplayOrientation;
    }

    void dispatchOnDisplayOrientationChanged(int i) {
        this.mLastKnownDisplayOrientation = i;
        onDisplayOrientationChanged(i);
    }
}
