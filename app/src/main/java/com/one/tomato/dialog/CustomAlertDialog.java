package com.one.tomato.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.text.method.ScrollingMovementMethod;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;
import com.broccoli.p150bh.R;

/* loaded from: classes3.dex */
public class CustomAlertDialog {
    LinearLayout bottomView;
    protected TextView cancelButton;
    protected TextView confirmButton;
    LinearLayout contentView;
    protected Context context;
    Dialog dialog;
    protected LottieAnimationView image_loading;
    boolean isMiddleNeedPadding = true;
    View line_bottom_horizontal;
    View line_bottom_vertical;
    View line_title;
    LinearLayout ll_root;
    LinearLayout ll_title;
    protected RelativeLayout relate_post_need_pay;
    TextView titleView;
    protected TextView tv_post_currency_play;
    Window window;

    public CustomAlertDialog(Context context) {
        this.context = context;
        this.dialog = new Dialog(context, R.style.BankListDialog);
        this.window = this.dialog.getWindow();
        if (!(context instanceof Activity) || ((Activity) context).isDestroyed()) {
            return;
        }
        this.dialog.show();
        this.window.setContentView(R.layout.dialog_custom);
        setWidth();
        this.ll_root = (LinearLayout) this.window.findViewById(R.id.ll_root);
        this.ll_title = (LinearLayout) this.window.findViewById(R.id.ll_title);
        this.ll_title.setVisibility(8);
        this.titleView = (TextView) this.window.findViewById(R.id.title);
        this.line_title = this.window.findViewById(R.id.line_title);
        this.contentView = (LinearLayout) this.window.findViewById(R.id.content);
        this.bottomView = (LinearLayout) this.window.findViewById(R.id.bottom_layout);
        this.cancelButton = (TextView) this.window.findViewById(R.id.cancel);
        this.confirmButton = (TextView) this.window.findViewById(R.id.confirm);
        this.line_bottom_horizontal = this.window.findViewById(R.id.line_bottom_horizontal);
        this.line_bottom_vertical = this.window.findViewById(R.id.line_bottom_vertical);
        this.relate_post_need_pay = (RelativeLayout) this.window.findViewById(R.id.relate_post_need_pay);
        this.tv_post_currency_play = (TextView) this.window.findViewById(R.id.tv_post_currency_play);
        this.image_loading = (LottieAnimationView) this.window.findViewById(R.id.image_loading);
        bottomButtonVisiblity(0);
        this.dialog.setCanceledOnTouchOutside(false);
    }

    public CustomAlertDialog(Context context, boolean z) {
        this.context = context;
        this.dialog = new Dialog(context, R.style.BankListDialog);
        this.window = this.dialog.getWindow();
        if (!(context instanceof Activity) || ((Activity) context).isDestroyed()) {
            return;
        }
        if (z) {
            show();
        }
        this.window.setContentView(R.layout.dialog_custom);
        setWidth();
        this.ll_root = (LinearLayout) this.window.findViewById(R.id.ll_root);
        this.ll_title = (LinearLayout) this.window.findViewById(R.id.ll_title);
        this.ll_title.setVisibility(8);
        this.titleView = (TextView) this.window.findViewById(R.id.title);
        this.line_title = this.window.findViewById(R.id.line_title);
        this.contentView = (LinearLayout) this.window.findViewById(R.id.content);
        this.bottomView = (LinearLayout) this.window.findViewById(R.id.bottom_layout);
        this.cancelButton = (TextView) this.window.findViewById(R.id.cancel);
        this.confirmButton = (TextView) this.window.findViewById(R.id.confirm);
        this.line_bottom_horizontal = this.window.findViewById(R.id.line_bottom_horizontal);
        this.line_bottom_vertical = this.window.findViewById(R.id.line_bottom_vertical);
        this.relate_post_need_pay = (RelativeLayout) this.window.findViewById(R.id.relate_post_need_pay);
        this.tv_post_currency_play = (TextView) this.window.findViewById(R.id.tv_post_currency_play);
        this.image_loading = (LottieAnimationView) this.window.findViewById(R.id.image_loading);
        bottomButtonVisiblity(0);
        this.dialog.setCanceledOnTouchOutside(false);
    }

    private void setWidth() {
        Display defaultDisplay = ((WindowManager) this.context.getSystemService("window")).getDefaultDisplay();
        WindowManager.LayoutParams attributes = this.window.getAttributes();
        attributes.gravity = 17;
        attributes.width = (int) (defaultDisplay.getWidth() * 0.8d);
        attributes.alpha = 1.0f;
        this.window.setAttributes(attributes);
    }

    public void setWidth(int i, int i2, float f) {
        ((WindowManager) this.context.getSystemService("window")).getDefaultDisplay();
        WindowManager.LayoutParams attributes = this.window.getAttributes();
        attributes.gravity = i;
        attributes.width = i2;
        attributes.alpha = f;
        this.window.setAttributes(attributes);
    }

    public void setCanceledOnTouchOutside(boolean z) {
        this.dialog.setCanceledOnTouchOutside(z);
    }

    public void setBackgroundColor(int i) {
        this.ll_root.setBackgroundColor(i);
    }

    public void setBackgroundResource(int i) {
        this.ll_root.setBackgroundResource(i);
    }

    public void setBackground(Drawable drawable) {
        this.ll_root.setBackground(drawable);
    }

    public void setTitle(int i) {
        this.titleView.setText(i);
        this.ll_title.setVisibility(0);
    }

    public void setTitleBackgroundDrawable(Drawable drawable) {
        this.ll_title.setBackground(drawable);
    }

    public void setTitle(String str) {
        this.titleView.setText(str);
        this.ll_title.setVisibility(0);
    }

    public void setTitleLineVisible(boolean z) {
        if (z) {
            this.line_title.setVisibility(0);
        } else {
            this.line_title.setVisibility(8);
        }
    }

    public void setMessage(int i) {
        setMessage(this.context.getResources().getString(i));
    }

    public void setMessage(String str) {
        setMessage(str, 17);
    }

    public void setMessage(String str, int i) {
        LinearLayout linearLayout = this.contentView;
        if (linearLayout != null) {
            linearLayout.removeAllViews();
        }
        LinearLayout linearLayout2 = (LinearLayout) LayoutInflater.from(this.context).inflate(R.layout.dialog_tip_textview, (ViewGroup) null);
        TextView textView = (TextView) linearLayout2.findViewById(R.id.text_view);
        textView.setText(str);
        textView.setScrollBarStyle(16777216);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        textView.setSingleLine(false);
        textView.setMaxLines(10);
        textView.setLayoutParams(layoutParams);
        textView.setHorizontallyScrolling(false);
        textView.setVerticalScrollBarEnabled(true);
        textView.setGravity(i);
        textView.setMovementMethod(new ScrollingMovementMethod());
        this.contentView.addView(linearLayout2);
    }

    public void setMiddleNeedPadding(boolean z) {
        this.isMiddleNeedPadding = z;
    }

    public void setContentView(View view) {
        if (!this.isMiddleNeedPadding) {
            this.contentView.setPadding(0, 0, 0, 0);
        }
        LinearLayout linearLayout = this.contentView;
        if (linearLayout == null) {
            dismiss();
        } else {
            linearLayout.addView(view, new LinearLayout.LayoutParams(-1, -2));
        }
    }

    public void bottomLayoutGone() {
        this.bottomView.setVisibility(8);
    }

    public void bottomButtonVisiblity(int i) {
        if (i == 0) {
            this.line_bottom_horizontal.setVisibility(0);
            this.cancelButton.setVisibility(0);
            this.confirmButton.setVisibility(0);
        } else if (i == 1) {
            this.line_bottom_horizontal.setVisibility(0);
            this.confirmButton.setVisibility(8);
            this.cancelButton.setVisibility(0);
        } else if (i == 2) {
            this.line_bottom_horizontal.setVisibility(0);
            this.cancelButton.setVisibility(8);
            this.confirmButton.setVisibility(0);
        } else if (i != 3) {
        } else {
            this.bottomView.setVisibility(8);
            this.isMiddleNeedPadding = false;
        }
    }

    public void setBottomVerticalLineVisible(boolean z) {
        if (z) {
            this.line_bottom_vertical.setVisibility(0);
        } else {
            this.line_bottom_vertical.setVisibility(8);
        }
    }

    public void setBottomHorizontalLineVisible(boolean z) {
        if (z) {
            this.line_bottom_horizontal.setVisibility(0);
        } else {
            this.line_bottom_horizontal.setVisibility(8);
        }
    }

    public void setCancelButtonBackgroundColor(int i) {
        this.cancelButton.setBackgroundColor(i);
    }

    public void setCancelButtonBackgroundRes(int i) {
        this.cancelButton.setBackgroundResource(i);
        if (i == R.drawable.dialog_custom_btn_bg) {
            this.cancelButton.setTextColor(this.context.getResources().getColor(R.color.colorPrimary));
        }
    }

    public void setCancleButtonBackgroundDrable(Drawable drawable) {
        this.cancelButton.setBackground(drawable);
    }

    public void setConfirmButtonBackgroundDrable(Drawable drawable) {
        this.confirmButton.setBackground(drawable);
    }

    public void setCancelButtonTextColor(int i) {
        this.cancelButton.setTextColor(this.context.getResources().getColor(i));
    }

    public void setConfirmButtonBackgroundColor(int i) {
        this.confirmButton.setBackgroundColor(i);
    }

    public void setConfirmButtonBackgroundRes(int i) {
        this.confirmButton.setBackgroundResource(i);
        if (i == R.drawable.dialog_custom_btn_bg) {
            this.confirmButton.setTextColor(this.context.getResources().getColor(R.color.colorPrimary));
        }
    }

    public void setConfirmButtonTextColor(int i) {
        this.confirmButton.setTextColor(this.context.getResources().getColor(i));
    }

    public TextView getCancelButton() {
        return this.cancelButton;
    }

    public TextView getConfirmButton() {
        return this.confirmButton;
    }

    public CustomAlertDialog setCancelButton(int i, View.OnClickListener onClickListener) {
        this.cancelButton.setText(i);
        this.cancelButton.setOnClickListener(onClickListener);
        return this;
    }

    public CustomAlertDialog setCancelButton(String str, View.OnClickListener onClickListener) {
        this.cancelButton.setText(str);
        this.cancelButton.setOnClickListener(onClickListener);
        return this;
    }

    public CustomAlertDialog setCancelButton(int i) {
        return setCancelButton(this.context.getResources().getString(i));
    }

    public CustomAlertDialog setCancelButton(String str) {
        this.cancelButton.setText(str);
        this.cancelButton.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.dialog.CustomAlertDialog.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CustomAlertDialog.this.dismiss();
            }
        });
        return this;
    }

    public CustomAlertDialog setCancelButtonListener() {
        this.cancelButton.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.dialog.CustomAlertDialog.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CustomAlertDialog.this.dismiss();
            }
        });
        return this;
    }

    public CustomAlertDialog setConfirmButton(int i, View.OnClickListener onClickListener) {
        this.confirmButton.setText(i);
        this.confirmButton.setOnClickListener(onClickListener);
        return this;
    }

    public CustomAlertDialog setConfirmButton(String str, View.OnClickListener onClickListener) {
        this.confirmButton.setText(str);
        this.confirmButton.setOnClickListener(onClickListener);
        return this;
    }

    public CustomAlertDialog setConfirmButton(int i) {
        return setConfirmButton(this.context.getResources().getString(i));
    }

    public CustomAlertDialog setConfirmButton(String str) {
        this.confirmButton.setText(str);
        this.confirmButton.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.dialog.CustomAlertDialog.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CustomAlertDialog.this.dismiss();
            }
        });
        return this;
    }

    public CustomAlertDialog setConfirmButtonListener() {
        this.confirmButton.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.dialog.CustomAlertDialog.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CustomAlertDialog.this.dismiss();
            }
        });
        return this;
    }

    public CustomAlertDialog setConfirmButtonListener(View.OnClickListener onClickListener) {
        this.confirmButton.setOnClickListener(onClickListener);
        return this;
    }

    public void setOnKeyListener(DialogInterface.OnKeyListener onKeyListener) {
        this.dialog.setOnKeyListener(onKeyListener);
    }

    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.dialog.setOnDismissListener(onDismissListener);
    }

    public void setLoadingVisiby(boolean z) {
        if (z) {
            this.confirmButton.setVisibility(8);
            this.relate_post_need_pay.setVisibility(0);
            showAnimation(this.image_loading);
            return;
        }
        this.confirmButton.setVisibility(0);
        this.relate_post_need_pay.setVisibility(8);
        cancelAnimation(this.image_loading);
    }

    public void cancelAnimation(LottieAnimationView lottieAnimationView) {
        if (lottieAnimationView.getAnimation() == null || !lottieAnimationView.isAnimating()) {
            return;
        }
        lottieAnimationView.cancelAnimation();
    }

    public void showAnimation(LottieAnimationView lottieAnimationView) {
        if (lottieAnimationView.getAnimation() != null && lottieAnimationView.isAnimating()) {
            lottieAnimationView.cancelAnimation();
        }
        lottieAnimationView.setAnimation("loading_more.json");
    }

    public RelativeLayout getRelate_post_need_pay() {
        return this.relate_post_need_pay;
    }

    public TextView getTv_post_currency_play() {
        return this.tv_post_currency_play;
    }

    public void show() {
        this.dialog.show();
    }

    public boolean isShow() {
        Dialog dialog = this.dialog;
        return dialog != null && dialog.isShowing();
    }

    public void dismiss() {
        Dialog dialog = this.dialog;
        if (dialog == null || !dialog.isShowing()) {
            return;
        }
        this.dialog.dismiss();
    }

    public void cancel() {
        this.dialog.cancel();
    }
}
