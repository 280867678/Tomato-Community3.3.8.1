package razerdp.widget;

import android.animation.Animator;
import android.content.Context;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import java.util.HashMap;
import java.util.Map;
import razerdp.basepopup.BasePopupWindow;
import razerdp.basepopup.QuickPopupBuilder;
import razerdp.basepopup.QuickPopupConfig;

/* loaded from: classes4.dex */
public class QuickPopup extends BasePopupWindow {
    private QuickPopupConfig mConfig;
    private QuickPopupBuilder.OnConfigApplyListener mOnConfigApplyListener;

    public QuickPopup(Context context, QuickPopupConfig quickPopupConfig, QuickPopupBuilder.OnConfigApplyListener onConfigApplyListener, int i, int i2) {
        super(context, i, i2, true);
        this.mConfig = quickPopupConfig;
        this.mOnConfigApplyListener = onConfigApplyListener;
        if (this.mConfig != null) {
            delayInit();
            applyConfigSetting(this.mConfig);
            return;
        }
        throw new NullPointerException("QuickPopupConfig must be not null!");
    }

    protected <C extends QuickPopupConfig> void applyConfigSetting(C c) {
        boolean z = false;
        if (c.getPopupBlurOption() != null) {
            setBlurOption(c.getPopupBlurOption());
        } else {
            setBlurBackgroundEnable((c.flag & 2048) != 0, c.getOnBlurOptionInitListener());
        }
        setPopupFadeEnable((c.flag & 64) != 0);
        applyClick();
        setOffsetX(c.getOffsetX());
        setOffsetY(c.getOffsetY());
        setClipChildren((c.flag & 16) != 0);
        setClipToScreen((c.flag & 32) != 0);
        setOutSideDismiss((c.flag & 1) != 0);
        setOutSideTouchable((c.flag & 2) != 0);
        setPopupGravity(c.getGravity());
        setAlignBackground((c.flag & 1024) != 0);
        setAlignBackgroundGravity(c.getAlignBackgroundGravity());
        setAutoLocatePopup((c.flag & 128) != 0);
        setPopupWindowFullScreen((c.flag & 8) != 0);
        setOnDismissListener(c.getDismissListener());
        setBackground(c.getBackground());
        linkTo(c.getLinkedView());
        setMinWidth(c.getMinWidth());
        setMaxWidth(c.getMaxWidth());
        setMinHeight(c.getMinHeight());
        setMaxHeight(c.getMaxHeight());
        if ((c.flag & 2048) != 0) {
            z = true;
        }
        setKeepSize(z);
        QuickPopupBuilder.OnConfigApplyListener onConfigApplyListener = this.mOnConfigApplyListener;
        if (onConfigApplyListener != null) {
            onConfigApplyListener.onConfigApply(this, c);
        }
    }

    private void applyClick() {
        HashMap<Integer, Pair<View.OnClickListener, Boolean>> listenersHolderMap = this.mConfig.getListenersHolderMap();
        if (listenersHolderMap == null || listenersHolderMap.isEmpty()) {
            return;
        }
        for (Map.Entry<Integer, Pair<View.OnClickListener, Boolean>> entry : listenersHolderMap.entrySet()) {
            int intValue = entry.getKey().intValue();
            final Pair<View.OnClickListener, Boolean> value = entry.getValue();
            View findViewById = findViewById(intValue);
            if (findViewById != null) {
                if (((Boolean) value.second).booleanValue()) {
                    findViewById.setOnClickListener(new View.OnClickListener() { // from class: razerdp.widget.QuickPopup.1
                        @Override // android.view.View.OnClickListener
                        public void onClick(View view) {
                            Object obj = value.first;
                            if (obj != null) {
                                if (obj instanceof OnQuickPopupClickListenerWrapper) {
                                    ((OnQuickPopupClickListenerWrapper) obj).mQuickPopup = QuickPopup.this;
                                }
                                ((View.OnClickListener) value.first).onClick(view);
                            }
                            QuickPopup.this.dismiss();
                        }
                    });
                } else {
                    findViewById.setOnClickListener((View.OnClickListener) value.first);
                }
            }
        }
    }

    @Override // razerdp.basepopup.BasePopupWindow
    protected Animation onCreateShowAnimation() {
        return this.mConfig.getShowAnimation();
    }

    @Override // razerdp.basepopup.BasePopupWindow
    protected Animation onCreateDismissAnimation() {
        return this.mConfig.getDismissAnimation();
    }

    @Override // razerdp.basepopup.BasePopupWindow
    protected Animator onCreateDismissAnimator() {
        return this.mConfig.getDismissAnimator();
    }

    @Override // razerdp.basepopup.BasePopupWindow
    protected Animator onCreateShowAnimator() {
        return this.mConfig.getShowAnimator();
    }

    @Override // razerdp.basepopup.BasePopup
    public View onCreateContentView() {
        return createPopupById(this.mConfig.getContentViewLayoutid());
    }
}
