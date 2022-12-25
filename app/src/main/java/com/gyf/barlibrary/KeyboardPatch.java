package com.gyf.barlibrary;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.FrameLayout;

/* loaded from: classes3.dex */
public class KeyboardPatch {
    private int actionBarHeight;
    private int keyboardHeightPrevious;
    private Activity mActivity;
    private BarParams mBarParams;
    private View mChildView;
    private View mContentView;
    private View mDecorView;
    private Window mWindow;
    private boolean navigationAtBottom;
    private int navigationBarHeight;
    private ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.gyf.barlibrary.KeyboardPatch.1
        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public void onGlobalLayout() {
            int i;
            int i2;
            int height;
            int i3;
            if (!KeyboardPatch.this.navigationAtBottom) {
                return;
            }
            Rect rect = new Rect();
            KeyboardPatch.this.mDecorView.getWindowVisibleDisplayFrame(rect);
            boolean z = true;
            if (KeyboardPatch.this.mBarParams.systemWindows) {
                int height2 = (KeyboardPatch.this.mContentView.getHeight() - rect.bottom) - KeyboardPatch.this.navigationBarHeight;
                if (KeyboardPatch.this.mBarParams.onKeyboardListener == null) {
                    return;
                }
                if (height2 <= KeyboardPatch.this.navigationBarHeight) {
                    z = false;
                }
                KeyboardPatch.this.mBarParams.onKeyboardListener.onKeyboardChange(z, height2);
            } else if (KeyboardPatch.this.mChildView != null) {
                if (KeyboardPatch.this.mBarParams.isSupportActionBar) {
                    height = KeyboardPatch.this.mContentView.getHeight() + KeyboardPatch.this.statusBarHeight + KeyboardPatch.this.actionBarHeight;
                    i3 = rect.bottom;
                } else if (KeyboardPatch.this.mBarParams.fits) {
                    height = KeyboardPatch.this.mContentView.getHeight() + KeyboardPatch.this.statusBarHeight;
                    i3 = rect.bottom;
                } else {
                    height = KeyboardPatch.this.mContentView.getHeight();
                    i3 = rect.bottom;
                }
                int i4 = height - i3;
                int i5 = KeyboardPatch.this.mBarParams.fullScreen ? i4 - KeyboardPatch.this.navigationBarHeight : i4;
                if (KeyboardPatch.this.mBarParams.fullScreen && i4 == KeyboardPatch.this.navigationBarHeight) {
                    i4 -= KeyboardPatch.this.navigationBarHeight;
                }
                if (i5 == KeyboardPatch.this.keyboardHeightPrevious) {
                    return;
                }
                KeyboardPatch.this.mContentView.setPadding(KeyboardPatch.this.paddingLeft, KeyboardPatch.this.paddingTop, KeyboardPatch.this.paddingRight, i4 + KeyboardPatch.this.paddingBottom);
                KeyboardPatch.this.keyboardHeightPrevious = i5;
                if (KeyboardPatch.this.mBarParams.onKeyboardListener == null) {
                    return;
                }
                if (i5 <= KeyboardPatch.this.navigationBarHeight) {
                    z = false;
                }
                KeyboardPatch.this.mBarParams.onKeyboardListener.onKeyboardChange(z, i5);
            } else {
                int height3 = KeyboardPatch.this.mContentView.getHeight() - rect.bottom;
                if (!KeyboardPatch.this.mBarParams.navigationBarEnable || !KeyboardPatch.this.mBarParams.navigationBarWithKitkatEnable) {
                    i = height3;
                } else {
                    if (Build.VERSION.SDK_INT == 19 || OSUtils.isEMUI3_1()) {
                        i2 = KeyboardPatch.this.navigationBarHeight;
                    } else if (!KeyboardPatch.this.mBarParams.fullScreen) {
                        i = height3;
                        if (KeyboardPatch.this.mBarParams.fullScreen && height3 == KeyboardPatch.this.navigationBarHeight) {
                            height3 -= KeyboardPatch.this.navigationBarHeight;
                        }
                    } else {
                        i2 = KeyboardPatch.this.navigationBarHeight;
                    }
                    i = height3 - i2;
                    if (KeyboardPatch.this.mBarParams.fullScreen) {
                        height3 -= KeyboardPatch.this.navigationBarHeight;
                    }
                }
                if (i == KeyboardPatch.this.keyboardHeightPrevious) {
                    return;
                }
                if (KeyboardPatch.this.mBarParams.isSupportActionBar) {
                    KeyboardPatch.this.mContentView.setPadding(0, KeyboardPatch.this.statusBarHeight + KeyboardPatch.this.actionBarHeight, 0, height3);
                } else if (KeyboardPatch.this.mBarParams.fits) {
                    KeyboardPatch.this.mContentView.setPadding(0, KeyboardPatch.this.statusBarHeight, 0, height3);
                } else {
                    KeyboardPatch.this.mContentView.setPadding(0, 0, 0, height3);
                }
                KeyboardPatch.this.keyboardHeightPrevious = i;
                if (KeyboardPatch.this.mBarParams.onKeyboardListener == null) {
                    return;
                }
                if (i <= KeyboardPatch.this.navigationBarHeight) {
                    z = false;
                }
                KeyboardPatch.this.mBarParams.onKeyboardListener.onKeyboardChange(z, i);
            }
        }
    };
    private int paddingBottom;
    private int paddingLeft;
    private int paddingRight;
    private int paddingTop;
    private int statusBarHeight;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v4, types: [android.view.View] */
    private KeyboardPatch(Activity activity, Window window) {
        this.mActivity = activity;
        this.mWindow = window;
        this.mDecorView = this.mWindow.getDecorView();
        FrameLayout frameLayout = (FrameLayout) this.mDecorView.findViewById(16908290);
        this.mChildView = frameLayout.getChildAt(0);
        ?? r3 = this.mChildView;
        this.mContentView = r3 != 0 ? r3 : frameLayout;
        this.paddingLeft = this.mContentView.getPaddingLeft();
        this.paddingTop = this.mContentView.getPaddingTop();
        this.paddingRight = this.mContentView.getPaddingRight();
        this.paddingBottom = this.mContentView.getPaddingBottom();
        BarConfig barConfig = new BarConfig(this.mActivity);
        this.statusBarHeight = barConfig.getStatusBarHeight();
        this.navigationBarHeight = barConfig.getNavigationBarHeight();
        this.actionBarHeight = barConfig.getActionBarHeight();
        this.navigationAtBottom = barConfig.isNavigationAtBottom();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static KeyboardPatch patch(Activity activity, Window window) {
        return new KeyboardPatch(activity, window);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setBarParams(BarParams barParams) {
        this.mBarParams = barParams;
    }

    public void enable(int i) {
        if (Build.VERSION.SDK_INT >= 19) {
            this.mWindow.setSoftInputMode(i);
            this.mDecorView.getViewTreeObserver().addOnGlobalLayoutListener(this.onGlobalLayoutListener);
        }
    }

    public void disable(int i) {
        if (Build.VERSION.SDK_INT >= 19) {
            this.mWindow.setSoftInputMode(i);
            this.mDecorView.getViewTreeObserver().removeOnGlobalLayoutListener(this.onGlobalLayoutListener);
        }
    }
}
