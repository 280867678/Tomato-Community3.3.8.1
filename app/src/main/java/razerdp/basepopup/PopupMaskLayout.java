package razerdp.basepopup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import razerdp.blur.BlurImageView;
import razerdp.library.R$anim;
import razerdp.util.PopupUtils;
import razerdp.util.log.PopupLog;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class PopupMaskLayout extends FrameLayout {
    private BackgroundViewHolder mBackgroundViewHolder;
    private BlurImageView mBlurImageView;

    private PopupMaskLayout(Context context) {
        this(context, null);
    }

    private PopupMaskLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    private PopupMaskLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public static PopupMaskLayout create(Context context, BasePopupHelper basePopupHelper) {
        PopupMaskLayout popupMaskLayout = new PopupMaskLayout(context);
        popupMaskLayout.init(context, basePopupHelper);
        return popupMaskLayout;
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        boolean dispatchKeyEvent = super.dispatchKeyEvent(keyEvent);
        PopupLog.m26i("dispatch  >> " + dispatchKeyEvent);
        return dispatchKeyEvent;
    }

    private void init(Context context, BasePopupHelper basePopupHelper) {
        setLayoutAnimation(null);
        if (basePopupHelper == null) {
            setBackgroundColor(0);
            return;
        }
        if (basePopupHelper.isAllowToBlur()) {
            this.mBlurImageView = new BlurImageView(context);
            this.mBlurImageView.applyBlurOption(basePopupHelper.getBlurOption());
            addViewInLayout(this.mBlurImageView, -1, generateDefaultLayoutParams());
        }
        if (basePopupHelper.getBackgroundView() != null) {
            this.mBackgroundViewHolder = new BackgroundViewHolder(basePopupHelper.getBackgroundView(), basePopupHelper);
        } else if (!PopupUtils.isBackgroundInvalidated(basePopupHelper.getPopupBackground())) {
            this.mBackgroundViewHolder = new BackgroundViewHolder(PopupBackgroundView.creaete(context, basePopupHelper), basePopupHelper);
        }
        BackgroundViewHolder backgroundViewHolder = this.mBackgroundViewHolder;
        if (backgroundViewHolder != null) {
            backgroundViewHolder.addInLayout();
        }
        basePopupHelper.registerActionListener(new PopupWindowActionListener() { // from class: razerdp.basepopup.PopupMaskLayout.1
            @Override // razerdp.basepopup.PopupWindowActionListener
            public void onShow(boolean z) {
            }

            @Override // razerdp.basepopup.PopupWindowActionListener
            public void onDismiss(boolean z) {
                PopupMaskLayout.this.handleDismiss(z ? -2L : 0L);
            }
        });
    }

    /* JADX WARN: Code restructure failed: missing block: B:8:0x0020, code lost:
        if (r4 != 8388613) goto L9;
     */
    /* JADX WARN: Removed duplicated region for block: B:11:0x002c  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0038  */
    /* JADX WARN: Removed duplicated region for block: B:19:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0033  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void handleAlignBackground(int i, int i2, int i3, int i4, int i5) {
        int i6;
        BackgroundViewHolder backgroundViewHolder;
        int left = getLeft();
        int top2 = getTop();
        int right = getRight();
        int bottom = getBottom();
        int i7 = i & 7;
        if (i7 != 3) {
            if (i7 != 5) {
                if (i7 != 8388611) {
                }
            }
            right = i4;
            i6 = i & 112;
            if (i6 != 48) {
                top2 = i3;
            } else if (i6 == 80) {
                bottom = i5;
            }
            backgroundViewHolder = this.mBackgroundViewHolder;
            if (backgroundViewHolder != null) {
                return;
            }
            backgroundViewHolder.handleAlignBackground(left, top2, right, bottom);
            return;
        }
        left = i2;
        i6 = i & 112;
        if (i6 != 48) {
        }
        backgroundViewHolder = this.mBackgroundViewHolder;
        if (backgroundViewHolder != null) {
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.widget.FrameLayout, android.view.ViewGroup
    public FrameLayout.LayoutParams generateDefaultLayoutParams() {
        return new FrameLayout.LayoutParams(-1, -1);
    }

    public void update() {
        BlurImageView blurImageView = this.mBlurImageView;
        if (blurImageView != null) {
            blurImageView.update();
        }
        BackgroundViewHolder backgroundViewHolder = this.mBackgroundViewHolder;
        if (backgroundViewHolder != null) {
            backgroundViewHolder.update();
        }
    }

    public void handleStart(long j) {
        BlurImageView blurImageView = this.mBlurImageView;
        if (blurImageView != null) {
            blurImageView.start(j);
        }
    }

    public void handleDismiss(long j) {
        BlurImageView blurImageView = this.mBlurImageView;
        if (blurImageView != null) {
            blurImageView.dismiss(j);
        }
        BackgroundViewHolder backgroundViewHolder = this.mBackgroundViewHolder;
        if (backgroundViewHolder != null) {
            backgroundViewHolder.dismiss();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeAllViews();
        BackgroundViewHolder backgroundViewHolder = this.mBackgroundViewHolder;
        if (backgroundViewHolder != null) {
            backgroundViewHolder.destroy();
            this.mBackgroundViewHolder = null;
        }
        BlurImageView blurImageView = this.mBlurImageView;
        if (blurImageView != null) {
            blurImageView.destroy();
            this.mBlurImageView = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public final class BackgroundViewHolder {
        View mBackgroundView;
        BasePopupHelper mHelper;

        BackgroundViewHolder(View view, BasePopupHelper basePopupHelper) {
            Animation loadAnimation;
            this.mBackgroundView = view;
            this.mHelper = basePopupHelper;
            if ((this.mBackgroundView instanceof PopupBackgroundView) || !this.mHelper.isPopupFadeEnable() || (loadAnimation = AnimationUtils.loadAnimation(PopupMaskLayout.this.getContext(), R$anim.basepopup_fade_in)) == null) {
                return;
            }
            loadAnimation.setDuration(Math.max(loadAnimation.getDuration(), this.mHelper.getShowAnimationDuration() - 200));
            loadAnimation.setFillAfter(true);
            this.mBackgroundView.startAnimation(loadAnimation);
        }

        void addInLayout() {
            View view = this.mBackgroundView;
            if (view != null) {
                PopupMaskLayout popupMaskLayout = PopupMaskLayout.this;
                popupMaskLayout.addViewInLayout(view, -1, popupMaskLayout.generateDefaultLayoutParams());
            }
        }

        void handleAlignBackground(int i, int i2, int i3, int i4) {
            View view = this.mBackgroundView;
            if (view == null) {
                return;
            }
            view.layout(i, i2, i3, i4);
        }

        void update() {
            View view = this.mBackgroundView;
            if (view instanceof PopupBackgroundView) {
                ((PopupBackgroundView) view).update();
            }
        }

        void dismiss() {
            BasePopupHelper basePopupHelper;
            Animation loadAnimation;
            View view = this.mBackgroundView;
            if (view instanceof PopupBackgroundView) {
                ((PopupBackgroundView) view).handleAnimateDismiss();
            } else if (view == null || (basePopupHelper = this.mHelper) == null || !basePopupHelper.isPopupFadeEnable() || (loadAnimation = AnimationUtils.loadAnimation(PopupMaskLayout.this.getContext(), R$anim.basepopup_fade_out)) == null) {
            } else {
                loadAnimation.setDuration(Math.max(loadAnimation.getDuration(), this.mHelper.getDismissAnimationDuration() - 200));
                loadAnimation.setFillAfter(true);
                this.mBackgroundView.startAnimation(loadAnimation);
            }
        }

        void destroy() {
            View view = this.mBackgroundView;
            if (view instanceof PopupBackgroundView) {
                ((PopupBackgroundView) view).destroy();
                this.mBackgroundView = null;
                return;
            }
            this.mBackgroundView = null;
        }
    }
}
