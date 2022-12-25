package com.faceunity.beautycontrolview;

import android.content.Context;
import android.support.annotation.IdRes;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.faceunity.beautycontrolview.BeautyBox;

/* loaded from: classes2.dex */
public class BeautyBoxGroup extends LinearLayout {
    private BeautyBox.OnCheckedChangeListener mChildOnCheckedChangeListener;
    private OnCheckedChangeListener mOnCheckedChangeListener;
    private PassThroughHierarchyChangeListener mPassThroughListener;
    private int mCheckedId = -1;
    private boolean mProtectFromCheckedChange = false;

    /* loaded from: classes2.dex */
    public interface OnCheckedChangeListener {
        void onCheckedChanged(BeautyBoxGroup beautyBoxGroup, @IdRes int i, boolean z);
    }

    public BeautyBoxGroup(Context context) {
        super(context);
        setOrientation(1);
        init();
    }

    public BeautyBoxGroup(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    private void init() {
        this.mChildOnCheckedChangeListener = new CheckedStateTracker();
        this.mPassThroughListener = new PassThroughHierarchyChangeListener();
        super.setOnHierarchyChangeListener(this.mPassThroughListener);
    }

    @Override // android.view.ViewGroup
    public void setOnHierarchyChangeListener(ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener) {
        this.mPassThroughListener.mOnHierarchyChangeListener = onHierarchyChangeListener;
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        int i = this.mCheckedId;
        if (i != -1) {
            this.mProtectFromCheckedChange = true;
            setCheckedStateForView(i, true);
            this.mProtectFromCheckedChange = false;
            setCheckedId(this.mCheckedId, true);
        }
    }

    @Override // android.view.ViewGroup
    public void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        if (view instanceof BeautyBox) {
            BeautyBox beautyBox = (BeautyBox) view;
            if (beautyBox.isChecked()) {
                this.mProtectFromCheckedChange = true;
                int i2 = this.mCheckedId;
                if (i2 != -1) {
                    setCheckedStateForView(i2, false);
                }
                this.mProtectFromCheckedChange = false;
                setCheckedId(beautyBox.getId(), false);
            }
        }
        super.addView(view, i, layoutParams);
    }

    public void check(@IdRes int i) {
        if (i == -1 || i != this.mCheckedId) {
            int i2 = this.mCheckedId;
            if (i2 != -1) {
                setCheckedStateForView(i2, false);
            }
            if (i != -1) {
                setCheckedStateForView(i, true);
            }
            setCheckedId(i, true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setCheckedId(@IdRes int i, boolean z) {
        this.mCheckedId = i;
        setCheckedStateForView(this.mCheckedId, true);
        OnCheckedChangeListener onCheckedChangeListener = this.mOnCheckedChangeListener;
        if (onCheckedChangeListener != null) {
            onCheckedChangeListener.onCheckedChanged(this, this.mCheckedId, z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setCheckedStateForView(int i, boolean z) {
        View findViewById = findViewById(i);
        if (findViewById == null || !(findViewById instanceof BeautyBox)) {
            return;
        }
        BeautyBox beautyBox = (BeautyBox) findViewById;
        if (z) {
            beautyBox.setBackgroundImg(R$drawable.control_beauty_select);
            beautyBox.setSelect(true);
            return;
        }
        beautyBox.clearBackgroundImg();
        beautyBox.setSelect(false);
    }

    @IdRes
    public int getCheckedBeautyBoxId() {
        return this.mCheckedId;
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.mOnCheckedChangeListener = onCheckedChangeListener;
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public CharSequence getAccessibilityClassName() {
        return BeautyBoxGroup.class.getName();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class CheckedStateTracker implements BeautyBox.OnCheckedChangeListener {
        private CheckedStateTracker() {
        }

        @Override // com.faceunity.beautycontrolview.BeautyBox.OnCheckedChangeListener
        public void onCheckedChanged(BeautyBox beautyBox, boolean z) {
            if (BeautyBoxGroup.this.mProtectFromCheckedChange) {
                return;
            }
            int id = beautyBox.getId();
            BeautyBoxGroup.this.mProtectFromCheckedChange = true;
            if (BeautyBoxGroup.this.mCheckedId != -1 && BeautyBoxGroup.this.mCheckedId != id) {
                BeautyBoxGroup beautyBoxGroup = BeautyBoxGroup.this;
                beautyBoxGroup.setCheckedStateForView(beautyBoxGroup.mCheckedId, false);
            }
            BeautyBoxGroup.this.mProtectFromCheckedChange = false;
            BeautyBoxGroup.this.setCheckedId(id, z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class PassThroughHierarchyChangeListener implements ViewGroup.OnHierarchyChangeListener {
        private ViewGroup.OnHierarchyChangeListener mOnHierarchyChangeListener;

        private PassThroughHierarchyChangeListener() {
        }

        @Override // android.view.ViewGroup.OnHierarchyChangeListener
        public void onChildViewAdded(View view, View view2) {
            if (view == BeautyBoxGroup.this && (view2 instanceof BeautyBox)) {
                if (view2.getId() == -1) {
                    view2.setId(View.generateViewId());
                }
                ((BeautyBox) view2).setOnCheckedChangeListener(BeautyBoxGroup.this.mChildOnCheckedChangeListener);
            }
            ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener = this.mOnHierarchyChangeListener;
            if (onHierarchyChangeListener != null) {
                onHierarchyChangeListener.onChildViewAdded(view, view2);
            }
        }

        @Override // android.view.ViewGroup.OnHierarchyChangeListener
        public void onChildViewRemoved(View view, View view2) {
            if (view == BeautyBoxGroup.this && (view2 instanceof BeautyBox)) {
                ((BeautyBox) view2).setOnCheckedChangeListener(null);
            }
            ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener = this.mOnHierarchyChangeListener;
            if (onHierarchyChangeListener != null) {
                onHierarchyChangeListener.onChildViewRemoved(view, view2);
            }
        }
    }
}
