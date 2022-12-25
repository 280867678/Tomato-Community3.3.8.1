package razerdp.basepopup;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import razerdp.library.R$anim;
import razerdp.util.PopupUtils;

/* loaded from: classes4.dex */
class PopupBackgroundView extends View {
    BasePopupHelper mHelper;

    private PopupBackgroundView(Context context) {
        this(context, null);
    }

    private PopupBackgroundView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    private PopupBackgroundView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public static PopupBackgroundView creaete(Context context, BasePopupHelper basePopupHelper) {
        PopupBackgroundView popupBackgroundView = new PopupBackgroundView(context);
        popupBackgroundView.init(context, basePopupHelper);
        return popupBackgroundView;
    }

    private void init(Context context, BasePopupHelper basePopupHelper) {
        Animation loadAnimation;
        if (PopupUtils.isBackgroundInvalidated(basePopupHelper.getPopupBackground())) {
            setVisibility(8);
            return;
        }
        this.mHelper = basePopupHelper;
        setVisibility(0);
        if (Build.VERSION.SDK_INT >= 16) {
            setBackground(basePopupHelper.getPopupBackground());
        } else {
            setBackgroundDrawable(basePopupHelper.getPopupBackground());
        }
        if (!basePopupHelper.isPopupFadeEnable() || (loadAnimation = AnimationUtils.loadAnimation(getContext(), R$anim.basepopup_fade_in)) == null) {
            return;
        }
        loadAnimation.setDuration(Math.max(loadAnimation.getDuration(), basePopupHelper.getShowAnimationDuration() - 200));
        loadAnimation.setFillAfter(true);
        startAnimation(loadAnimation);
    }

    public void destroy() {
        this.mHelper = null;
    }

    public void handleAnimateDismiss() {
        Animation loadAnimation;
        BasePopupHelper basePopupHelper = this.mHelper;
        if (basePopupHelper == null || !basePopupHelper.isPopupFadeEnable() || (loadAnimation = AnimationUtils.loadAnimation(getContext(), R$anim.basepopup_fade_out)) == null) {
            return;
        }
        loadAnimation.setDuration(Math.max(loadAnimation.getDuration(), this.mHelper.getDismissAnimationDuration() - 200));
        loadAnimation.setFillAfter(true);
        startAnimation(loadAnimation);
    }

    public void update() {
        BasePopupHelper basePopupHelper = this.mHelper;
        if (basePopupHelper != null) {
            if (Build.VERSION.SDK_INT >= 16) {
                setBackground(basePopupHelper.getPopupBackground());
            } else {
                setBackgroundDrawable(basePopupHelper.getPopupBackground());
            }
        }
    }
}
