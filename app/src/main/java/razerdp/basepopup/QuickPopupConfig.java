package razerdp.basepopup;

import android.animation.Animator;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import razerdp.basepopup.BasePopupWindow;
import razerdp.blur.PopupBlurOption;
import razerdp.util.SimpleAnimationUtils;

/* loaded from: classes4.dex */
public class QuickPopupConfig implements BasePopupFlag {
    protected int contentViewLayoutid;
    protected Animation mDismissAnimation;
    protected Animator mDismissAnimator;
    protected BasePopupWindow.OnDismissListener mDismissListener;
    protected View mLinkedView;
    HashMap<Integer, Pair<View.OnClickListener, Boolean>> mListenersHolderMap;
    protected WeakReference<BasePopupWindow.OnBlurOptionInitListener> mOnBlurOptionInitListener;
    protected PopupBlurOption mPopupBlurOption;
    protected Animation mShowAnimation;
    protected Animator mShowAnimator;
    protected int maxHeight;
    protected int maxWidth;
    protected int minHeight;
    protected int minWidth;
    protected int offsetX;
    protected int offsetY;
    public int flag = 125;
    protected int gravity = 17;
    protected int alignBackgroundGravity = 48;
    protected Drawable background = new ColorDrawable(BasePopupWindow.DEFAULT_BACKGROUND_COLOR);

    public QuickPopupConfig() {
        if (Build.VERSION.SDK_INT == 23) {
            this.flag &= -65;
        }
    }

    public static QuickPopupConfig generateDefault() {
        QuickPopupConfig quickPopupConfig = new QuickPopupConfig();
        boolean z = true;
        quickPopupConfig.withShowAnimation(SimpleAnimationUtils.getDefaultScaleAnimation(true));
        quickPopupConfig.withDismissAnimation(SimpleAnimationUtils.getDefaultScaleAnimation(false));
        if (Build.VERSION.SDK_INT == 23) {
            z = false;
        }
        quickPopupConfig.fadeInAndOut(z);
        return quickPopupConfig;
    }

    public QuickPopupConfig withShowAnimation(Animation animation) {
        this.mShowAnimation = animation;
        return this;
    }

    public QuickPopupConfig withDismissAnimation(Animation animation) {
        this.mDismissAnimation = animation;
        return this;
    }

    public QuickPopupConfig fadeInAndOut(boolean z) {
        setFlag(64, z);
        return this;
    }

    public QuickPopupConfig gravity(int i) {
        this.gravity = i;
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public QuickPopupConfig contentViewLayoutid(int i) {
        this.contentViewLayoutid = i;
        return this;
    }

    public Animation getShowAnimation() {
        return this.mShowAnimation;
    }

    public Animation getDismissAnimation() {
        return this.mDismissAnimation;
    }

    public Animator getShowAnimator() {
        return this.mShowAnimator;
    }

    public Animator getDismissAnimator() {
        return this.mDismissAnimator;
    }

    public PopupBlurOption getPopupBlurOption() {
        return this.mPopupBlurOption;
    }

    public int getOffsetX() {
        return this.offsetX;
    }

    public int getOffsetY() {
        return this.offsetY;
    }

    public HashMap<Integer, Pair<View.OnClickListener, Boolean>> getListenersHolderMap() {
        return this.mListenersHolderMap;
    }

    public BasePopupWindow.OnBlurOptionInitListener getOnBlurOptionInitListener() {
        WeakReference<BasePopupWindow.OnBlurOptionInitListener> weakReference = this.mOnBlurOptionInitListener;
        if (weakReference == null) {
            return null;
        }
        return weakReference.get();
    }

    public int getAlignBackgroundGravity() {
        return this.alignBackgroundGravity;
    }

    public BasePopupWindow.OnDismissListener getDismissListener() {
        return this.mDismissListener;
    }

    public Drawable getBackground() {
        return this.background;
    }

    public int getGravity() {
        return this.gravity;
    }

    public int getContentViewLayoutid() {
        return this.contentViewLayoutid;
    }

    public View getLinkedView() {
        return this.mLinkedView;
    }

    public int getMinWidth() {
        return this.minWidth;
    }

    public int getMaxWidth() {
        return this.maxWidth;
    }

    public int getMinHeight() {
        return this.minHeight;
    }

    public int getMaxHeight() {
        return this.maxHeight;
    }

    private void setFlag(int i, boolean z) {
        if (!z) {
            this.flag = (~i) & this.flag;
            return;
        }
        this.flag = i | this.flag;
    }
}
