package com.one.tomato.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.p002v4.view.AccessibilityDelegateCompat;
import android.support.p002v4.view.ViewCompat;
import android.support.p002v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.p005v7.app.AppCompatDialog;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import com.broccoli.p150bh.R;
import com.one.tomato.widget.ViewPagerBottomSheetBehavior;

/* loaded from: classes3.dex */
public class ViewPagerBottomSheetDialog extends AppCompatDialog {
    private ViewPagerBottomSheetBehavior<View> mBehavior;
    private boolean mCanceledOnTouchOutsideSet;
    boolean mCancelable = true;
    private boolean mCanceledOnTouchOutside = true;
    private ViewPagerBottomSheetBehavior.BottomSheetCallback mBottomSheetCallback = new ViewPagerBottomSheetBehavior.BottomSheetCallback() { // from class: com.one.tomato.dialog.ViewPagerBottomSheetDialog.4
        @Override // com.one.tomato.widget.ViewPagerBottomSheetBehavior.BottomSheetCallback
        public void onSlide(@NonNull View view, float f) {
        }

        @Override // com.one.tomato.widget.ViewPagerBottomSheetBehavior.BottomSheetCallback
        public void onStateChanged(@NonNull View view, int i) {
            if (i == 5) {
                ViewPagerBottomSheetDialog.this.cancel();
            }
        }
    };

    public ViewPagerBottomSheetDialog(@NonNull Context context, @StyleRes int i) {
        super(context, getThemeResId(context, i));
        supportRequestWindowFeature(1);
    }

    @Override // android.support.p005v7.app.AppCompatDialog, android.app.Dialog
    public void setContentView(@LayoutRes int i) {
        super.setContentView(wrapInBottomSheet(i, null, null));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.p005v7.app.AppCompatDialog, android.app.Dialog
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Window window = getWindow();
        if (window != null) {
            if (Build.VERSION.SDK_INT >= 21) {
                window.clearFlags(67108864);
                window.addFlags(Integer.MIN_VALUE);
            }
            window.setLayout(-1, -1);
        }
    }

    @Override // android.support.p005v7.app.AppCompatDialog, android.app.Dialog
    public void setContentView(View view) {
        super.setContentView(wrapInBottomSheet(0, view, null));
    }

    @Override // android.support.p005v7.app.AppCompatDialog, android.app.Dialog
    public void setContentView(View view, ViewGroup.LayoutParams layoutParams) {
        super.setContentView(wrapInBottomSheet(0, view, layoutParams));
    }

    @Override // android.app.Dialog
    public void setCancelable(boolean z) {
        super.setCancelable(z);
        if (this.mCancelable != z) {
            this.mCancelable = z;
            ViewPagerBottomSheetBehavior<View> viewPagerBottomSheetBehavior = this.mBehavior;
            if (viewPagerBottomSheetBehavior == null) {
                return;
            }
            viewPagerBottomSheetBehavior.setHideable(z);
        }
    }

    @Override // android.app.Dialog
    protected void onStart() {
        super.onStart();
        ViewPagerBottomSheetBehavior<View> viewPagerBottomSheetBehavior = this.mBehavior;
        if (viewPagerBottomSheetBehavior != null) {
            viewPagerBottomSheetBehavior.setState(4);
        }
    }

    @Override // android.app.Dialog
    public void setCanceledOnTouchOutside(boolean z) {
        super.setCanceledOnTouchOutside(z);
        if (z && !this.mCancelable) {
            this.mCancelable = true;
        }
        this.mCanceledOnTouchOutside = z;
        this.mCanceledOnTouchOutsideSet = true;
    }

    @SuppressLint({"WrongConstant"})
    private View wrapInBottomSheet(int i, View view, ViewGroup.LayoutParams layoutParams) {
        FrameLayout frameLayout = (FrameLayout) View.inflate(getContext(), R.layout.design_view_pager_bottom_sheet_dialog, null);
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) frameLayout.findViewById(R.id.coordinator);
        if (i != 0 && view == null) {
            view = getLayoutInflater().inflate(i, (ViewGroup) coordinatorLayout, false);
        }
        FrameLayout frameLayout2 = (FrameLayout) coordinatorLayout.findViewById(R.id.design_bottom_sheet);
        this.mBehavior = ViewPagerBottomSheetBehavior.from(frameLayout2);
        this.mBehavior.setBottomSheetCallback(this.mBottomSheetCallback);
        this.mBehavior.setHideable(this.mCancelable);
        this.mBehavior.setState(3);
        if (layoutParams == null) {
            frameLayout2.addView(view);
        } else {
            frameLayout2.addView(view, layoutParams);
        }
        coordinatorLayout.findViewById(R.id.touch_outside).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.dialog.ViewPagerBottomSheetDialog.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                ViewPagerBottomSheetDialog viewPagerBottomSheetDialog = ViewPagerBottomSheetDialog.this;
                if (!viewPagerBottomSheetDialog.mCancelable || !viewPagerBottomSheetDialog.isShowing() || !ViewPagerBottomSheetDialog.this.shouldWindowCloseOnTouchOutside()) {
                    return;
                }
                ViewPagerBottomSheetDialog.this.cancel();
            }
        });
        ViewCompat.setAccessibilityDelegate(frameLayout2, new AccessibilityDelegateCompat() { // from class: com.one.tomato.dialog.ViewPagerBottomSheetDialog.2
            @Override // android.support.p002v4.view.AccessibilityDelegateCompat
            public void onInitializeAccessibilityNodeInfo(View view2, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                super.onInitializeAccessibilityNodeInfo(view2, accessibilityNodeInfoCompat);
                if (ViewPagerBottomSheetDialog.this.mCancelable) {
                    accessibilityNodeInfoCompat.addAction(1048576);
                    accessibilityNodeInfoCompat.setDismissable(true);
                    return;
                }
                accessibilityNodeInfoCompat.setDismissable(false);
            }

            @Override // android.support.p002v4.view.AccessibilityDelegateCompat
            public boolean performAccessibilityAction(View view2, int i2, Bundle bundle) {
                if (i2 == 1048576) {
                    ViewPagerBottomSheetDialog viewPagerBottomSheetDialog = ViewPagerBottomSheetDialog.this;
                    if (viewPagerBottomSheetDialog.mCancelable) {
                        viewPagerBottomSheetDialog.cancel();
                        return true;
                    }
                }
                return super.performAccessibilityAction(view2, i2, bundle);
            }
        });
        frameLayout2.setOnTouchListener(new View.OnTouchListener(this) { // from class: com.one.tomato.dialog.ViewPagerBottomSheetDialog.3
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view2, MotionEvent motionEvent) {
                return true;
            }
        });
        return frameLayout;
    }

    boolean shouldWindowCloseOnTouchOutside() {
        if (!this.mCanceledOnTouchOutsideSet) {
            if (Build.VERSION.SDK_INT < 11) {
                this.mCanceledOnTouchOutside = true;
            } else {
                TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(new int[]{16843611});
                this.mCanceledOnTouchOutside = obtainStyledAttributes.getBoolean(0, true);
                obtainStyledAttributes.recycle();
            }
            this.mCanceledOnTouchOutsideSet = true;
        }
        return this.mCanceledOnTouchOutside;
    }

    private static int getThemeResId(Context context, int i) {
        if (i == 0) {
            TypedValue typedValue = new TypedValue();
            if (!context.getTheme().resolveAttribute(R.attr.bottomSheetDialogTheme, typedValue, true)) {
                return 2131821002;
            }
            return typedValue.resourceId;
        }
        return i;
    }
}
