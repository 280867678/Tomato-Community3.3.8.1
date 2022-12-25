package com.tomatolive.library.p136ui.view.widget.guideview;

import android.graphics.Bitmap;
import android.support.annotation.AnimatorRes;
import android.support.annotation.IdRes;
import android.support.annotation.IntRange;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.widget.guideview.GuideBuilder */
/* loaded from: classes4.dex */
public class GuideBuilder {
    private boolean mBuilt;
    private List<Component> mComponents = new ArrayList();
    private Configuration mConfiguration = new Configuration();
    private OnSlideListener mOnSlideListener;
    private OnVisibilityChangedListener mOnVisibilityChangedListener;

    /* renamed from: com.tomatolive.library.ui.view.widget.guideview.GuideBuilder$OnSlideListener */
    /* loaded from: classes4.dex */
    public interface OnSlideListener {
        void onSlideListener(SlideState slideState);
    }

    /* renamed from: com.tomatolive.library.ui.view.widget.guideview.GuideBuilder$OnVisibilityChangedListener */
    /* loaded from: classes4.dex */
    public interface OnVisibilityChangedListener {
        void onDismiss();

        void onShown();
    }

    /* renamed from: com.tomatolive.library.ui.view.widget.guideview.GuideBuilder$SlideState */
    /* loaded from: classes4.dex */
    public enum SlideState {
        UP,
        DOWN
    }

    public GuideBuilder setAlpha(@IntRange(from = 0, m5591to = 255) int i) {
        if (this.mBuilt) {
            throw new BuildException("Already created. rebuild a new one.");
        }
        if (i < 0 || i > 255) {
            i = 0;
        }
        this.mConfiguration.mAlpha = i;
        return this;
    }

    public GuideBuilder setTargetView(View view) {
        if (this.mBuilt) {
            throw new BuildException("Already created. rebuild a new one.");
        }
        this.mConfiguration.mTargetView = view;
        return this;
    }

    public GuideBuilder setHighlightBitmap(Bitmap bitmap) {
        this.mConfiguration.mBitmap = bitmap;
        return this;
    }

    public GuideBuilder setTargetViewId(@IdRes int i) {
        if (this.mBuilt) {
            throw new BuildException("Already created. rebuild a new one.");
        }
        this.mConfiguration.mTargetViewId = i;
        return this;
    }

    public GuideBuilder setHighTargetCorner(int i) {
        if (this.mBuilt) {
            throw new BuildException("Already created. rebuild a new one.");
        }
        if (i < 0) {
            this.mConfiguration.mCorner = 0;
        }
        this.mConfiguration.mCorner = i;
        return this;
    }

    public GuideBuilder setHighTargetGraphStyle(int i) {
        if (this.mBuilt) {
            throw new BuildException("Already created. rebuild a new one.");
        }
        this.mConfiguration.mGraphStyle = i;
        return this;
    }

    public GuideBuilder setFullingColorId(@IdRes int i) {
        if (this.mBuilt) {
            throw new BuildException("Already created. rebuild a new one.");
        }
        this.mConfiguration.mFullingColorId = i;
        return this;
    }

    public GuideBuilder setAutoDismiss(boolean z) {
        if (this.mBuilt) {
            throw new BuildException("Already created, rebuild a new one.");
        }
        this.mConfiguration.mAutoDismiss = z;
        return this;
    }

    public GuideBuilder setOverlayTarget(boolean z) {
        if (this.mBuilt) {
            throw new BuildException("Already created, rebuild a new one.");
        }
        this.mConfiguration.mOverlayTarget = z;
        return this;
    }

    public GuideBuilder setEnterAnimationId(@AnimatorRes int i) {
        if (this.mBuilt) {
            throw new BuildException("Already created. rebuild a new one.");
        }
        this.mConfiguration.mEnterAnimationId = i;
        return this;
    }

    public GuideBuilder setExitAnimationId(@AnimatorRes int i) {
        if (this.mBuilt) {
            throw new BuildException("Already created. rebuild a new one.");
        }
        this.mConfiguration.mExitAnimationId = i;
        return this;
    }

    public GuideBuilder addComponent(Component component) {
        if (this.mBuilt) {
            throw new BuildException("Already created, rebuild a new one.");
        }
        this.mComponents.add(component);
        return this;
    }

    public GuideBuilder setOnVisibilityChangedListener(OnVisibilityChangedListener onVisibilityChangedListener) {
        if (this.mBuilt) {
            throw new BuildException("Already created, rebuild a new one.");
        }
        this.mOnVisibilityChangedListener = onVisibilityChangedListener;
        return this;
    }

    public GuideBuilder setOnSlideListener(OnSlideListener onSlideListener) {
        if (this.mBuilt) {
            throw new BuildException("Already created, rebuild a new one.");
        }
        this.mOnSlideListener = onSlideListener;
        return this;
    }

    public GuideBuilder setOutsideTouchable(boolean z) {
        this.mConfiguration.mOutsideTouchable = z;
        return this;
    }

    public GuideBuilder setHighTargetPadding(int i) {
        if (this.mBuilt) {
            throw new BuildException("Already created. rebuild a new one.");
        }
        if (i < 0) {
            this.mConfiguration.mPadding = 0;
        }
        this.mConfiguration.mPadding = i;
        return this;
    }

    public GuideBuilder setHighTargetPaddingLeft(int i) {
        if (this.mBuilt) {
            throw new BuildException("Already created. rebuild a new one.");
        }
        if (i < 0) {
            this.mConfiguration.mPaddingLeft = 0;
        }
        this.mConfiguration.mPaddingLeft = i;
        return this;
    }

    public GuideBuilder setHighTargetPaddingTop(int i) {
        if (this.mBuilt) {
            throw new BuildException("Already created. rebuild a new one.");
        }
        if (i < 0) {
            this.mConfiguration.mPaddingTop = 0;
        }
        this.mConfiguration.mPaddingTop = i;
        return this;
    }

    public GuideBuilder setHighTargetPaddingRight(int i) {
        if (this.mBuilt) {
            throw new BuildException("Already created. rebuild a new one.");
        }
        if (i < 0) {
            this.mConfiguration.mPaddingRight = 0;
        }
        this.mConfiguration.mPaddingRight = i;
        return this;
    }

    public GuideBuilder setHighTargetPaddingBottom(int i) {
        if (this.mBuilt) {
            throw new BuildException("Already created. rebuild a new one.");
        }
        if (i < 0) {
            this.mConfiguration.mPaddingBottom = 0;
        }
        this.mConfiguration.mPaddingBottom = i;
        return this;
    }

    public Guide createGuide() {
        Guide guide = new Guide();
        guide.setComponents((Component[]) this.mComponents.toArray(new Component[this.mComponents.size()]));
        guide.setConfiguration(this.mConfiguration);
        guide.setCallback(this.mOnVisibilityChangedListener);
        guide.setOnSlideListener(this.mOnSlideListener);
        this.mComponents = null;
        this.mConfiguration = null;
        this.mOnVisibilityChangedListener = null;
        this.mBuilt = true;
        return guide;
    }
}
