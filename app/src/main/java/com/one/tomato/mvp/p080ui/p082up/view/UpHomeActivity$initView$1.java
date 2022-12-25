package com.one.tomato.mvp.p080ui.p082up.view;

import android.view.MotionEvent;
import android.view.View;

/* compiled from: UpHomeActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.up.view.UpHomeActivity$initView$1 */
/* loaded from: classes3.dex */
final class UpHomeActivity$initView$1 implements View.OnTouchListener {
    public static final UpHomeActivity$initView$1 INSTANCE = new UpHomeActivity$initView$1();

    UpHomeActivity$initView$1() {
    }

    @Override // android.view.View.OnTouchListener
    public final boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0) {
            view.getParent().requestDisallowInterceptTouchEvent(true);
        }
        if (motionEvent.getAction() == 2) {
            view.getParent().requestDisallowInterceptTouchEvent(true);
        }
        if (motionEvent.getAction() == 1) {
            view.getParent().requestDisallowInterceptTouchEvent(false);
        }
        return false;
    }
}
