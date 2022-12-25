package com.gen.p059mh.webapp_extensions.views.player.custom;

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
import com.gen.p059mh.webapp_extensions.utils.DeviceUtils;
import com.gen.p059mh.webapp_extensions.views.player.BasePlayerDialogView;

/* renamed from: com.gen.mh.webapp_extensions.views.player.custom.CustomPlayerDialogFrameLayout */
/* loaded from: classes2.dex */
public class CustomPlayerDialogFrameLayout extends FrameLayout {
    private Callback callback;
    private BasePlayerDialogView contentView;
    boolean isFull;
    private boolean isShowing;
    private LinearLayout llHalfContainer;
    private View rootView;
    public boolean screenOrientation;
    private TextView tvTitle;
    private View viewHalfLeft;
    private LinearLayout viewHalfRight;
    private View viewTitleLine;
    private int animDuration = 500;
    private int dialogType = 0;

    /* renamed from: com.gen.mh.webapp_extensions.views.player.custom.CustomPlayerDialogFrameLayout$Callback */
    /* loaded from: classes2.dex */
    public interface Callback {
        void showControlWidget();
    }

    public CustomPlayerDialogFrameLayout(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.rootView = LayoutInflater.from(context).inflate(R$layout.custom_view_base_player_dialog_container, (ViewGroup) null);
        addView(this.rootView);
        initRootView();
    }

    private void initRootView() {
        this.tvTitle = (TextView) this.rootView.findViewById(R$id.tv_player_dialog_title);
        this.viewTitleLine = this.rootView.findViewById(R$id.view_player_dialog_title_line);
        this.llHalfContainer = (LinearLayout) this.rootView.findViewById(R$id.ll_player_dialog_half_container);
        this.viewHalfLeft = this.rootView.findViewById(R$id.view_player_dialog_half_left);
        this.viewHalfRight = (LinearLayout) this.rootView.findViewById(R$id.view_player_dialog_half_right);
    }

    private void calHalfView() {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.viewHalfRight.getLayoutParams();
        layoutParams.width = (int) DeviceUtils.dpToPixel(getContext(), this.isFull ? 214.0f : 120.0f);
        this.viewHalfRight.setLayoutParams(layoutParams);
    }

    public CustomPlayerDialogFrameLayout addContentView(BasePlayerDialogView basePlayerDialogView) {
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

    private CustomPlayerDialogFrameLayout addChildView(BasePlayerDialogView basePlayerDialogView) {
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

    public BasePlayerDialogView getContentView() {
        return this.contentView;
    }

    public CustomPlayerDialogFrameLayout hasNoTitle() {
        this.tvTitle.setVisibility(8);
        this.viewTitleLine.setVisibility(8);
        return this;
    }

    public CustomPlayerDialogFrameLayout gravityCenter() {
        this.llHalfContainer.setGravity(17);
        return this;
    }

    public CustomPlayerDialogFrameLayout showHalf() {
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
        setAnimation(AnimationUtil.moveLeft2Self(this.animDuration));
    }

    public TranslateAnimation hiddenAnim() {
        return AnimationUtil.moveSelf2Left(this.animDuration);
    }

    public void dismissView(boolean z) {
        this.isShowing = false;
        if (this.contentView == null) {
            return;
        }
        setVisibility(8);
        if (z) {
            TranslateAnimation hiddenAnim = hiddenAnim();
            hiddenAnim.setAnimationListener(new Animation.AnimationListener() { // from class: com.gen.mh.webapp_extensions.views.player.custom.CustomPlayerDialogFrameLayout.1
                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationRepeat(Animation animation) {
                }

                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationStart(Animation animation) {
                }

                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationEnd(Animation animation) {
                    CustomPlayerDialogFrameLayout.this.contentView.destroyView();
                    CustomPlayerDialogFrameLayout.this.llHalfContainer.removeAllViewsInLayout();
                }
            });
            setAnimation(hiddenAnim);
        } else {
            this.contentView.destroyView();
            this.llHalfContainer.removeAllViewsInLayout();
        }
        this.dialogType = 0;
    }

    public void dismissView() {
        dismissView(true);
    }

    public boolean isShowing() {
        return this.isShowing;
    }

    public CustomPlayerDialogFrameLayout setCanceledOnTouchOutside(boolean z) {
        if (!z) {
            return this;
        }
        setOnTouchListener(new View.OnTouchListener() { // from class: com.gen.mh.webapp_extensions.views.player.custom.CustomPlayerDialogFrameLayout.2
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == 1) {
                    CustomPlayerDialogFrameLayout.this.dismissView();
                    if (CustomPlayerDialogFrameLayout.this.callback != null) {
                        CustomPlayerDialogFrameLayout.this.callback.showControlWidget();
                    }
                }
                return true;
            }
        });
        return this;
    }

    public void displayInScreenOrientation(boolean z) {
        this.isFull = z;
        if (!this.screenOrientation) {
            dismissView(false);
            return;
        }
        BasePlayerDialogView basePlayerDialogView = this.contentView;
        if (basePlayerDialogView == null) {
            return;
        }
        basePlayerDialogView.setHorizontal(z);
    }

    public CustomPlayerDialogFrameLayout setScreenOrientation(boolean z) {
        this.screenOrientation = z;
        return this;
    }

    public int getDialogType() {
        return this.dialogType;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }
}
