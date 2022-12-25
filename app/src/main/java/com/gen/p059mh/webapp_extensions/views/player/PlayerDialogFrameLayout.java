package com.gen.p059mh.webapp_extensions.views.player;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.gen.p059mh.webapp_extensions.R$id;
import com.gen.p059mh.webapp_extensions.R$layout;
import com.gen.p059mh.webapp_extensions.utils.AnimationUtil;

/* renamed from: com.gen.mh.webapp_extensions.views.player.PlayerDialogFrameLayout */
/* loaded from: classes2.dex */
public class PlayerDialogFrameLayout extends FrameLayout {
    private int animDuration;
    private Callback callback;
    private BasePlayerDialogView contentView;
    private int dialogType;
    int flag;
    private boolean isShowing;
    private LinearLayout llHalfContainer;
    private View rootView;
    private View viewHalfLeft;
    private LinearLayout viewHalfRight;

    /* renamed from: com.gen.mh.webapp_extensions.views.player.PlayerDialogFrameLayout$Callback */
    /* loaded from: classes2.dex */
    public interface Callback {
        void onViewDismiss(int i);

        void onVisibleChange(int i);

        void showControlWidget();
    }

    private void calHalfView() {
    }

    public PlayerDialogFrameLayout setScreenOrientation(boolean z) {
        return this;
    }

    public PlayerDialogFrameLayout(@NonNull Context context) {
        this(context, null);
    }

    public PlayerDialogFrameLayout(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.animDuration = 300;
        this.dialogType = 0;
        this.flag = -1;
        this.rootView = LayoutInflater.from(context).inflate(R$layout.web_sdk_view_player_dialog_container, (ViewGroup) null);
        addView(this.rootView);
        initRootView();
    }

    private void initRootView() {
        TextView textView = (TextView) this.rootView.findViewById(R$id.tv_player_dialog_title);
        this.rootView.findViewById(R$id.view_player_dialog_title_line);
        this.llHalfContainer = (LinearLayout) this.rootView.findViewById(R$id.ll_player_dialog_half_container);
        this.viewHalfLeft = this.rootView.findViewById(R$id.view_player_dialog_half_left);
        this.viewHalfRight = (LinearLayout) this.rootView.findViewById(R$id.view_player_dialog_half_right);
    }

    public PlayerDialogFrameLayout addContentView(BasePlayerDialogView basePlayerDialogView) {
        if (basePlayerDialogView == null) {
            return this;
        }
        if (this.isShowing) {
            this.contentView.destroyView();
            this.llHalfContainer.removeAllViewsInLayout();
        }
        addChildView(basePlayerDialogView);
        this.dialogType = 0;
        return this;
    }

    private PlayerDialogFrameLayout addChildView(BasePlayerDialogView basePlayerDialogView) {
        if (basePlayerDialogView == null) {
            return this;
        }
        this.contentView = basePlayerDialogView;
        gravityCenter();
        setScreenOrientation(false);
        showHalf();
        this.llHalfContainer.addView(this.contentView.provideView());
        this.isShowing = false;
        return this;
    }

    public PlayerDialogFrameLayout gravityCenter() {
        this.llHalfContainer.setGravity(17);
        return this;
    }

    public PlayerDialogFrameLayout showHalf() {
        this.viewHalfLeft.setVisibility(0);
        calHalfView();
        return this;
    }

    public void show() {
        this.isShowing = true;
        setVisibility(0);
        showAnim();
    }

    public void showAnim() {
        this.viewHalfRight.startAnimation(AnimationUtil.moveBottom2Self(this.animDuration));
    }

    public TranslateAnimation hiddenAnim() {
        return AnimationUtil.moveSelf2Bottom(this.animDuration);
    }

    public void dismissView(boolean z) {
        this.isShowing = false;
        BasePlayerDialogView basePlayerDialogView = this.contentView;
        if (basePlayerDialogView == null) {
            return;
        }
        if (z) {
            TranslateAnimation hiddenAnim = hiddenAnim();
            hiddenAnim.setAnimationListener(new Animation.AnimationListener() { // from class: com.gen.mh.webapp_extensions.views.player.PlayerDialogFrameLayout.1
                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationRepeat(Animation animation) {
                }

                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationStart(Animation animation) {
                }

                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationEnd(Animation animation) {
                    PlayerDialogFrameLayout.this.setVisibility(8);
                    PlayerDialogFrameLayout.this.contentView.destroyView();
                    PlayerDialogFrameLayout.this.llHalfContainer.removeAllViewsInLayout();
                    if (PlayerDialogFrameLayout.this.callback != null) {
                        PlayerDialogFrameLayout.this.callback.onViewDismiss(PlayerDialogFrameLayout.this.flag);
                    }
                }
            });
            this.viewHalfRight.startAnimation(hiddenAnim);
        } else {
            basePlayerDialogView.destroyView();
            this.llHalfContainer.removeAllViewsInLayout();
        }
        this.dialogType = 0;
    }

    public void dismissView() {
        dismissView(true);
        this.flag = -1;
    }

    public void dismissView(int i) {
        dismissView(true);
        this.flag = i;
    }

    public boolean isShowing() {
        return this.isShowing;
    }

    public PlayerDialogFrameLayout setCanceledOnTouchOutside(boolean z) {
        if (!z) {
            return this;
        }
        setOnTouchListener(new View.OnTouchListener() { // from class: com.gen.mh.webapp_extensions.views.player.PlayerDialogFrameLayout.2
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == 1) {
                    PlayerDialogFrameLayout.this.dismissView();
                    if (PlayerDialogFrameLayout.this.callback != null) {
                        PlayerDialogFrameLayout.this.callback.showControlWidget();
                    }
                }
                return true;
            }
        });
        return this;
    }

    public BasePlayerDialogView getContentView() {
        return this.contentView;
    }

    @Override // android.view.View
    public void setVisibility(int i) {
        super.setVisibility(i);
        Callback callback = this.callback;
        if (callback != null) {
            callback.onVisibleChange(i);
        }
    }

    public int getDialogType() {
        return this.dialogType;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }
}
