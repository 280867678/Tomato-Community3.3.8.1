package com.tomatolive.library.p136ui.view.custom;

import android.animation.AnimatorSet;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import com.tomatolive.library.model.LeftAnimEntity;
import com.tomatolive.library.p136ui.interfaces.OnAnimPlayListener;

/* renamed from: com.tomatolive.library.ui.view.custom.LeftAnimView */
/* loaded from: classes3.dex */
public abstract class LeftAnimView extends RelativeLayout {
    public AnimatorSet animatorSet;
    public boolean isShowing;
    public LeftAnimEntity leftAnimEntity;
    public OnAnimPlayListener listener;
    public Context mContext;
    public int animType = -1;
    public String animPropertyName = "translationX";

    public abstract void addItemInfo(LeftAnimEntity leftAnimEntity);

    public abstract void initView(Context context);

    public LeftAnimView(Context context) {
        super(context);
        initContext(context);
    }

    public LeftAnimView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initContext(context);
    }

    public LeftAnimView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initContext(context);
    }

    public void setOnAnimPlayListener(OnAnimPlayListener onAnimPlayListener) {
        this.listener = onAnimPlayListener;
    }

    public void startAnim() {
        if (this.listener != null) {
            setRootViewVisibility(0);
            this.listener.onStart();
        }
    }

    public void endAnim() {
        this.isShowing = false;
        OnAnimPlayListener onAnimPlayListener = this.listener;
        if (onAnimPlayListener != null) {
            onAnimPlayListener.onEnd(this);
        }
    }

    public void setRootViewVisibility(int i) {
        setVisibility(i);
    }

    public void onRelease() {
        AnimatorSet animatorSet = this.animatorSet;
        if (animatorSet != null) {
            animatorSet.cancel();
            this.animatorSet.removeAllListeners();
        }
    }

    private void initContext(Context context) {
        this.mContext = context;
        this.animatorSet = new AnimatorSet();
        initView(context);
    }
}
