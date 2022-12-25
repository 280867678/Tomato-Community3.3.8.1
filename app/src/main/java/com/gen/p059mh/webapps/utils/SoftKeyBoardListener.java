package com.gen.p059mh.webapps.utils;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.gen.mh.webapps.utils.SoftKeyBoardListener */
/* loaded from: classes2.dex */
public class SoftKeyBoardListener {
    private List<OnSoftKeyBoardChangeListener> listeners = new ArrayList();
    private View rootView;
    int rootViewVisibleHeight;
    int rootViewVisibleWidth;

    /* renamed from: com.gen.mh.webapps.utils.SoftKeyBoardListener$OnSoftKeyBoardChangeListener */
    /* loaded from: classes2.dex */
    public interface OnSoftKeyBoardChangeListener {
        void keyBoardHide(int i);

        void keyBoardShow(int i);
    }

    public SoftKeyBoardListener(Activity activity) {
        Logger.m4113i("SoftKeyBoardListener init");
        float f = activity.getResources().getDisplayMetrics().density;
        if (activity != null) {
            this.rootView = activity.getWindow().getDecorView();
        }
        Rect rect = new Rect();
        this.rootView.getWindowVisibleDisplayFrame(rect);
        this.rootViewVisibleHeight = rect.height();
        this.rootViewVisibleWidth = rect.width();
        this.rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.gen.mh.webapps.utils.SoftKeyBoardListener.1
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                Rect rect2 = new Rect();
                if (SoftKeyBoardListener.this.rootView != null) {
                    SoftKeyBoardListener.this.rootView.getWindowVisibleDisplayFrame(rect2);
                }
                int height = rect2.height();
                int width = rect2.width();
                int i = SoftKeyBoardListener.this.rootViewVisibleWidth;
                if (width != i && Math.abs(width - i) > 200) {
                    SoftKeyBoardListener softKeyBoardListener = SoftKeyBoardListener.this;
                    softKeyBoardListener.rootViewVisibleWidth = width;
                    softKeyBoardListener.rootViewVisibleHeight = height;
                    return;
                }
                SoftKeyBoardListener softKeyBoardListener2 = SoftKeyBoardListener.this;
                int i2 = softKeyBoardListener2.rootViewVisibleHeight;
                if (i2 == 0) {
                    softKeyBoardListener2.rootViewVisibleHeight = height;
                } else if (i2 == height) {
                } else {
                    if (i2 - height > 200) {
                        softKeyBoardListener2.keyBoardShow(i2 - height);
                        SoftKeyBoardListener.this.rootViewVisibleHeight = height;
                    } else if (height - i2 <= 200) {
                    } else {
                        softKeyBoardListener2.keyBoardHide(height - i2);
                        SoftKeyBoardListener.this.rootViewVisibleHeight = height;
                    }
                }
            }
        });
    }

    public void keyBoardShow(int i) {
        for (OnSoftKeyBoardChangeListener onSoftKeyBoardChangeListener : this.listeners) {
            onSoftKeyBoardChangeListener.keyBoardShow(i);
        }
    }

    public void keyBoardHide(int i) {
        for (OnSoftKeyBoardChangeListener onSoftKeyBoardChangeListener : this.listeners) {
            onSoftKeyBoardChangeListener.keyBoardHide(i);
        }
    }

    public void addListener(OnSoftKeyBoardChangeListener onSoftKeyBoardChangeListener) {
        this.listeners.add(onSoftKeyBoardChangeListener);
    }

    public void removeListener(OnSoftKeyBoardChangeListener onSoftKeyBoardChangeListener) {
        List<OnSoftKeyBoardChangeListener> list = this.listeners;
        if (list != null) {
            list.remove(onSoftKeyBoardChangeListener);
        }
    }

    public void release() {
        if (this.rootView != null) {
            this.rootView = null;
        }
    }
}
