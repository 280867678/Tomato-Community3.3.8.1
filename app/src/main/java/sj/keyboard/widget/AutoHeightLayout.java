package sj.keyboard.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.keyboard.view.R$id;
import sj.keyboard.utils.EmoticonsKeyboardUtils;
import sj.keyboard.widget.SoftKeyboardSizeWatchLayout;

/* loaded from: classes4.dex */
public abstract class AutoHeightLayout extends SoftKeyboardSizeWatchLayout implements SoftKeyboardSizeWatchLayout.OnResizeListener {
    private static final int ID_CHILD = R$id.id_autolayout;
    protected boolean mConfigurationChangedFlag = false;
    protected Context mContext;
    protected int mMaxParentHeight;
    protected int mSoftKeyboardHeight;
    private OnMaxParentHeightChangeListener maxParentHeightChangeListener;

    /* loaded from: classes4.dex */
    public interface OnMaxParentHeightChangeListener {
        void onMaxParentHeightChange(int i);
    }

    public void OnSoftClose() {
    }

    public abstract void onSoftKeyboardHeightChanged(int i);

    public AutoHeightLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        this.mSoftKeyboardHeight = EmoticonsKeyboardUtils.getDefKeyboardHeight(this.mContext);
        addOnResizeListener(this);
    }

    @Override // android.view.ViewGroup
    public void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        int childCount = getChildCount();
        if (childCount > 1) {
            throw new IllegalStateException("can host only one direct child");
        }
        super.addView(view, i, layoutParams);
        if (childCount == 0) {
            if (view.getId() < 0) {
                view.setId(ID_CHILD);
            }
            RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) view.getLayoutParams();
            layoutParams2.addRule(12);
            view.setLayoutParams(layoutParams2);
        } else if (childCount != 1) {
        } else {
            RelativeLayout.LayoutParams layoutParams3 = (RelativeLayout.LayoutParams) view.getLayoutParams();
            layoutParams3.addRule(2, ID_CHILD);
            view.setLayoutParams(layoutParams3);
        }
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        onSoftKeyboardHeightChanged(this.mSoftKeyboardHeight);
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        if (this.mMaxParentHeight == 0) {
            this.mMaxParentHeight = i2;
        }
    }

    public void updateMaxParentHeight(int i) {
        this.mMaxParentHeight = i;
        OnMaxParentHeightChangeListener onMaxParentHeightChangeListener = this.maxParentHeightChangeListener;
        if (onMaxParentHeightChangeListener != null) {
            onMaxParentHeightChangeListener.onMaxParentHeightChange(i);
        }
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mConfigurationChangedFlag = true;
        this.mScreenHeight = 0;
    }

    @Override // android.widget.RelativeLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        if (this.mConfigurationChangedFlag) {
            this.mConfigurationChangedFlag = false;
            Rect rect = new Rect();
            ((Activity) this.mContext).getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
            if (this.mScreenHeight == 0) {
                this.mScreenHeight = rect.bottom;
            }
            this.mMaxParentHeight = this.mScreenHeight - rect.bottom;
        }
        if (this.mMaxParentHeight != 0) {
            super.onMeasure(i, View.MeasureSpec.makeMeasureSpec(this.mMaxParentHeight, View.MeasureSpec.getMode(i2)));
            return;
        }
        super.onMeasure(i, i2);
    }

    public void OnSoftPop(int i) {
        if (this.mSoftKeyboardHeight != i) {
            this.mSoftKeyboardHeight = i;
            EmoticonsKeyboardUtils.setDefKeyboardHeight(this.mContext, this.mSoftKeyboardHeight);
            onSoftKeyboardHeightChanged(this.mSoftKeyboardHeight);
        }
    }

    public void setOnMaxParentHeightChangeListener(OnMaxParentHeightChangeListener onMaxParentHeightChangeListener) {
        this.maxParentHeightChangeListener = onMaxParentHeightChangeListener;
    }
}
