package com.tomatolive.library.p136ui.interfaces;

import android.view.MotionEvent;
import android.view.View;

/* renamed from: com.tomatolive.library.ui.interfaces.OnClickWebViewListener */
/* loaded from: classes3.dex */
public class OnClickWebViewListener implements View.OnTouchListener {
    public static final int FINGER_DRAGGING = 2;
    public static final int FINGER_RELEASED = 0;
    public static final int FINGER_TOUCHED = 1;
    public static final int FINGER_UNDEFINED = 3;
    private int fingerState = 0;

    public void onClick(View view) {
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action != 0) {
            if (action == 1) {
                int i = this.fingerState;
                if (i != 2) {
                    this.fingerState = 0;
                    onClick(view);
                } else if (i == 2) {
                    this.fingerState = 0;
                } else {
                    this.fingerState = 3;
                }
            } else if (action == 2) {
                int i2 = this.fingerState;
                if (i2 == 1 || i2 == 2) {
                    this.fingerState = 2;
                } else {
                    this.fingerState = 3;
                }
            } else {
                this.fingerState = 3;
            }
        } else if (this.fingerState == 0) {
            this.fingerState = 1;
        } else {
            this.fingerState = 3;
        }
        return false;
    }
}
