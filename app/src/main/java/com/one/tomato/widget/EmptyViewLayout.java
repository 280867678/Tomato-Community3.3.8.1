package com.one.tomato.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;
import com.broccoli.p150bh.R;
import com.one.tomato.R$styleable;
import com.one.tomato.mvp.base.BaseApplication;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;

/* loaded from: classes3.dex */
public class EmptyViewLayout extends LinearLayout {
    private String buttonStr;
    private FrameLayout ff_root;
    private FrameLayout fl_no_data;
    private ImageView iv_status_icon;
    private LinearLayout ll_content;
    private LinearLayout ll_potato_tip;
    private LinearLayout ll_wait;
    private Drawable logo_net_error;
    private Drawable logo_no_data;
    private LottieAnimationView lottieAnimationView;
    private View noDataView;
    private View rootView;
    private TextView tv_button;
    private TextView tv_content;
    private TextView tv_potato_tip;
    private int state = 0;
    ButtonClickListener buttonClickListener = null;
    RootClickListener rootClickListener = null;

    /* loaded from: classes3.dex */
    public interface ButtonClickListener {
        void buttonClickListener(View view, int i);
    }

    /* loaded from: classes3.dex */
    public interface RootClickListener {
        void rootClickListenr(View view);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        return true;
    }

    public void setLoadingLog(Drawable drawable) {
    }

    public EmptyViewLayout(Context context) {
        super(context);
        if (isInEditMode()) {
            return;
        }
        initView(context);
    }

    public EmptyViewLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        if (isInEditMode()) {
            return;
        }
        initView(context);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.EmptyViewLayout);
        this.tv_content.setText(obtainStyledAttributes.getString(3));
        this.logo_net_error = context.getResources().getDrawable(R.drawable.no_data_net);
        Drawable drawable = obtainStyledAttributes.getDrawable(4);
        this.logo_net_error = drawable == null ? this.logo_net_error : drawable;
        this.logo_no_data = context.getResources().getDrawable(R.drawable.no_data_common);
        Drawable drawable2 = obtainStyledAttributes.getDrawable(5);
        this.logo_no_data = drawable2 == null ? this.logo_no_data : drawable2;
        int color = context.getResources().getColor(R.color.text_middle);
        ColorStateList colorStateList = obtainStyledAttributes.getColorStateList(6);
        this.tv_content.setTextColor(colorStateList == null ? ColorStateList.valueOf(color) : colorStateList);
        this.tv_content.setTextSize(0, obtainStyledAttributes.getDimension(7, 16.0f));
        int color2 = context.getResources().getColor(R.color.colorPrimary);
        ColorStateList colorStateList2 = obtainStyledAttributes.getColorStateList(0);
        this.tv_button.setTextColor(colorStateList2 == null ? ColorStateList.valueOf(color2) : colorStateList2);
        this.tv_button.setTextSize(0, obtainStyledAttributes.getDimension(1, 16.0f));
        intiState("");
        obtainStyledAttributes.recycle();
    }

    private void initView(Context context) {
        this.rootView = LayoutInflater.from(context).inflate(R.layout.layout_empty_view, (ViewGroup) this, true);
        this.ff_root = (FrameLayout) this.rootView.findViewById(R.id.ff_root);
        this.ll_wait = (LinearLayout) this.rootView.findViewById(R.id.ll_wait);
        this.ll_content = (LinearLayout) this.rootView.findViewById(R.id.ll_content);
        this.iv_status_icon = (ImageView) this.rootView.findViewById(R.id.iv_status_icon);
        this.tv_content = (TextView) this.rootView.findViewById(R.id.tv_content);
        this.tv_button = (TextView) this.rootView.findViewById(R.id.tv_button);
        this.ll_potato_tip = (LinearLayout) this.rootView.findViewById(R.id.ll_potato_tip);
        this.tv_potato_tip = (TextView) this.rootView.findViewById(R.id.tv_potato_tip);
        this.fl_no_data = (FrameLayout) this.rootView.findViewById(R.id.fl_no_data);
        this.lottieAnimationView = (LottieAnimationView) this.rootView.findViewById(R.id.refresh_lottie);
        this.tv_button.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.widget.EmptyViewLayout.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                EmptyViewLayout emptyViewLayout = EmptyViewLayout.this;
                ButtonClickListener buttonClickListener = emptyViewLayout.buttonClickListener;
                if (buttonClickListener != null) {
                    buttonClickListener.buttonClickListener(view, emptyViewLayout.state);
                }
            }
        });
        initPotatoTip();
    }

    private void intiState(String str) {
        this.ll_potato_tip.setVisibility(8);
        switch (this.state) {
            case 0:
                if (this.noDataView != null) {
                    this.fl_no_data.setVisibility(0);
                    this.ll_wait.setVisibility(8);
                    this.ll_content.setVisibility(8);
                    this.lottieAnimationView.setVisibility(8);
                } else {
                    this.ll_wait.setVisibility(0);
                    this.ll_content.setVisibility(8);
                    this.fl_no_data.setVisibility(8);
                    this.lottieAnimationView.setVisibility(8);
                }
                LottieAnimationView lottieAnimationView = this.lottieAnimationView;
                if (lottieAnimationView == null || lottieAnimationView.getAnimation() == null || !this.lottieAnimationView.isAnimating()) {
                    return;
                }
                this.lottieAnimationView.cancelAnimation();
                return;
            case 1:
                if (this.noDataView != null) {
                    this.fl_no_data.setVisibility(0);
                    this.ll_wait.setVisibility(8);
                    this.lottieAnimationView.setVisibility(8);
                    this.ll_content.setVisibility(8);
                } else {
                    this.ll_content.setVisibility(0);
                    this.ll_potato_tip.setVisibility(0);
                    this.ll_wait.setVisibility(8);
                    this.lottieAnimationView.setVisibility(8);
                    this.fl_no_data.setVisibility(8);
                    setDrawable(this.logo_net_error);
                    if (TextUtils.isEmpty(str)) {
                        this.tv_content.setText(R.string.loading_net_error);
                    } else {
                        this.tv_content.setText(str);
                    }
                    if (TextUtils.isEmpty(this.buttonStr)) {
                        this.tv_button.setText(R.string.loading_retry);
                    } else {
                        this.tv_button.setText(this.buttonStr);
                    }
                }
                LottieAnimationView lottieAnimationView2 = this.lottieAnimationView;
                if (lottieAnimationView2 == null || lottieAnimationView2.getAnimation() == null || !this.lottieAnimationView.isAnimating()) {
                    return;
                }
                this.lottieAnimationView.cancelAnimation();
                return;
            case 2:
                if (this.noDataView != null) {
                    this.fl_no_data.setVisibility(0);
                    this.ll_wait.setVisibility(8);
                    this.lottieAnimationView.setVisibility(8);
                    this.ll_content.setVisibility(8);
                } else {
                    this.ll_content.setVisibility(0);
                    this.ll_wait.setVisibility(8);
                    this.lottieAnimationView.setVisibility(8);
                    this.fl_no_data.setVisibility(8);
                    this.tv_button.setVisibility(8);
                    setDrawable(this.logo_no_data);
                    if (TextUtils.isEmpty(str)) {
                        this.tv_content.setText("");
                    } else {
                        this.tv_content.setText(str);
                    }
                    this.ff_root.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.widget.EmptyViewLayout.2
                        @Override // android.view.View.OnClickListener
                        public void onClick(View view) {
                            EmptyViewLayout emptyViewLayout = EmptyViewLayout.this;
                            RootClickListener rootClickListener = emptyViewLayout.rootClickListener;
                            if (rootClickListener != null) {
                                rootClickListener.rootClickListenr(emptyViewLayout.ff_root);
                            }
                        }
                    });
                }
                LottieAnimationView lottieAnimationView3 = this.lottieAnimationView;
                if (lottieAnimationView3 == null || lottieAnimationView3.getAnimation() == null || !this.lottieAnimationView.isAnimating()) {
                    return;
                }
                this.lottieAnimationView.cancelAnimation();
                return;
            case 3:
                if (this.noDataView != null) {
                    this.fl_no_data.setVisibility(0);
                    this.ll_wait.setVisibility(8);
                    this.lottieAnimationView.setVisibility(8);
                    this.ll_content.setVisibility(8);
                } else {
                    this.ll_content.setVisibility(0);
                    this.ll_wait.setVisibility(8);
                    this.lottieAnimationView.setVisibility(8);
                    this.fl_no_data.setVisibility(8);
                    this.tv_button.setVisibility(8);
                    setDrawable(null);
                    if (TextUtils.isEmpty(str)) {
                        this.tv_content.setText("");
                    } else {
                        this.tv_content.setText(str);
                    }
                }
                LottieAnimationView lottieAnimationView4 = this.lottieAnimationView;
                if (lottieAnimationView4 == null || lottieAnimationView4.getAnimation() == null || !this.lottieAnimationView.isAnimating()) {
                    return;
                }
                this.lottieAnimationView.cancelAnimation();
                return;
            case 4:
                if (this.noDataView != null) {
                    this.fl_no_data.setVisibility(0);
                    this.ll_wait.setVisibility(8);
                    this.lottieAnimationView.setVisibility(8);
                    this.ll_content.setVisibility(8);
                } else {
                    this.ll_content.setVisibility(0);
                    this.ll_wait.setVisibility(8);
                    this.lottieAnimationView.setVisibility(8);
                    this.fl_no_data.setVisibility(8);
                    this.tv_content.setVisibility(8);
                    this.tv_button.setVisibility(8);
                    setDrawable(this.logo_no_data);
                }
                LottieAnimationView lottieAnimationView5 = this.lottieAnimationView;
                if (lottieAnimationView5 == null || lottieAnimationView5.getAnimation() == null || !this.lottieAnimationView.isAnimating()) {
                    return;
                }
                this.lottieAnimationView.cancelAnimation();
                return;
            case 5:
                if (this.noDataView != null) {
                    this.fl_no_data.setVisibility(0);
                    this.ll_wait.setVisibility(8);
                    this.lottieAnimationView.setVisibility(8);
                    this.ll_content.setVisibility(8);
                } else {
                    this.ll_content.setVisibility(0);
                    this.ll_wait.setVisibility(8);
                    this.lottieAnimationView.setVisibility(8);
                    this.fl_no_data.setVisibility(8);
                    this.tv_button.setVisibility(8);
                    setDrawable(this.logo_no_data);
                    if (TextUtils.isEmpty(str)) {
                        this.tv_content.setText("");
                    } else {
                        this.tv_content.setText(str);
                    }
                }
                LottieAnimationView lottieAnimationView6 = this.lottieAnimationView;
                if (lottieAnimationView6 == null || lottieAnimationView6.getAnimation() == null || !this.lottieAnimationView.isAnimating()) {
                    return;
                }
                this.lottieAnimationView.cancelAnimation();
                return;
            case 6:
                if (this.noDataView != null) {
                    this.fl_no_data.setVisibility(0);
                    this.ll_wait.setVisibility(8);
                    this.lottieAnimationView.setVisibility(8);
                    LottieAnimationView lottieAnimationView7 = this.lottieAnimationView;
                    if (lottieAnimationView7 != null && lottieAnimationView7.getAnimation() != null && this.lottieAnimationView.isAnimating()) {
                        this.lottieAnimationView.cancelAnimation();
                    }
                    this.ll_content.setVisibility(8);
                    return;
                }
                this.lottieAnimationView.setVisibility(0);
                if (this.lottieAnimationView.getAnimation() != null && this.lottieAnimationView.isAnimating()) {
                    this.lottieAnimationView.cancelAnimation();
                }
                this.lottieAnimationView.setImageAssetsFolder("images/");
                this.lottieAnimationView.setAnimation("refresh_empty.json");
                this.lottieAnimationView.playAnimation();
                this.ll_wait.setVisibility(8);
                this.ll_content.setVisibility(8);
                this.fl_no_data.setVisibility(8);
                return;
            default:
                return;
        }
    }

    public void setEmptyViewBackgroundRes(int i) {
        this.ff_root.setBackgroundResource(i);
    }

    public void setEmptyViewBackgroundColor(int i) {
        this.ff_root.setBackgroundColor(i);
    }

    public void setNoDataView(View view) {
        this.noDataView = view;
        this.fl_no_data.removeAllViews();
        this.fl_no_data.addView(view);
    }

    public void setNetErrorLogo(Drawable drawable) {
        this.logo_net_error = drawable;
    }

    public void setNoDataLogo(Drawable drawable) {
        this.logo_no_data = drawable;
    }

    public void setButtonStr(String str) {
        this.buttonStr = str;
    }

    private void setDrawable(Drawable drawable) {
        if (drawable != null) {
            this.iv_status_icon.setVisibility(0);
            this.iv_status_icon.setImageDrawable(drawable);
            return;
        }
        this.iv_status_icon.setVisibility(8);
    }

    public void setState(int i) {
        this.state = i;
        intiState("");
    }

    public void setState(int i, String str) {
        this.state = i;
        intiState(str);
    }

    public int getState() {
        return this.state;
    }

    public void setContentTextColor(int i) {
        this.tv_content.setTextColor(i);
    }

    public void setButtonTextColor(int i) {
        this.tv_button.setTextColor(i);
    }

    public void setButtonClickListener(ButtonClickListener buttonClickListener) {
        this.buttonClickListener = buttonClickListener;
    }

    public void setRootClickListener(RootClickListener rootClickListener) {
        this.rootClickListener = rootClickListener;
    }

    private void initPotatoTip() {
        String string = AppUtil.getString(R.string.potato_tip1);
        String websiteUrl = DomainServer.getInstance().getWebsiteUrl();
        String string2 = AppUtil.getString(R.string.potato_tip3);
        String string3 = AppUtil.getString(R.string.potato_tip4);
        String string4 = AppUtil.getString(R.string.potato_tip5);
        String string5 = AppUtil.getString(R.string.potato_tip6);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        SpannableString spannableString = new SpannableString(string);
        spannableString.setSpan(new ContentSpan(), 0, spannableString.length(), 33);
        spannableStringBuilder.append((CharSequence) spannableString);
        SpannableString spannableString2 = new SpannableString(websiteUrl);
        spannableString2.setSpan(new WebSpan(this), 0, spannableString2.length(), 33);
        spannableStringBuilder.append((CharSequence) spannableString2);
        SpannableString spannableString3 = new SpannableString(string2);
        spannableString3.setSpan(new ContentSpan(), 0, spannableString3.length(), 33);
        spannableStringBuilder.append((CharSequence) spannableString3);
        SpannableString spannableString4 = new SpannableString(string3);
        spannableString4.setSpan(new ContentSpan(), 0, spannableString4.length(), 33);
        spannableStringBuilder.append((CharSequence) spannableString4);
        SpannableString spannableString5 = new SpannableString(string4);
        spannableString5.setSpan(new PotatoSpan(this), 0, spannableString5.length(), 33);
        spannableStringBuilder.append((CharSequence) spannableString5);
        SpannableString spannableString6 = new SpannableString("@" + string5);
        spannableString6.setSpan(new ContentSpan(), 0, spannableString6.length(), 33);
        spannableStringBuilder.append((CharSequence) spannableString6);
        this.tv_potato_tip.setText(spannableStringBuilder);
        this.tv_potato_tip.setMovementMethod(LinkMovementMethod.getInstance());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public class ContentSpan extends ClickableSpan {
        @Override // android.text.style.ClickableSpan
        public void onClick(@NonNull View view) {
        }

        ContentSpan() {
        }

        @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
        public void updateDrawState(@NonNull TextPaint textPaint) {
            super.updateDrawState(textPaint);
            if (BaseApplication.getApplication().isChess()) {
                textPaint.setColor(Color.parseColor("#63638B"));
            } else {
                textPaint.setColor(EmptyViewLayout.this.getResources().getColor(R.color.text_light));
            }
            textPaint.setUnderlineText(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public class WebSpan extends ClickableSpan {
        WebSpan(EmptyViewLayout emptyViewLayout) {
        }

        @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
        public void updateDrawState(@NonNull TextPaint textPaint) {
            super.updateDrawState(textPaint);
            if (BaseApplication.getApplication().isChess()) {
                textPaint.setColor(Color.parseColor("#5B92E1"));
            } else {
                textPaint.setColor(Color.parseColor("#FF149EFF"));
            }
            textPaint.setUnderlineText(true);
        }

        @Override // android.text.style.ClickableSpan
        public void onClick(@NonNull View view) {
            AppUtil.startBrowseView(DomainServer.getInstance().getWebsiteUrl());
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public class PotatoSpan extends ClickableSpan {
        PotatoSpan(EmptyViewLayout emptyViewLayout) {
        }

        @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
        public void updateDrawState(@NonNull TextPaint textPaint) {
            super.updateDrawState(textPaint);
            if (BaseApplication.getApplication().isChess()) {
                textPaint.setColor(Color.parseColor("#5B92E1"));
            } else {
                textPaint.setColor(Color.parseColor("#FF149EFF"));
            }
            textPaint.setUnderlineText(true);
        }

        @Override // android.text.style.ClickableSpan
        public void onClick(@NonNull View view) {
            AppUtil.startBrowseView(DBUtil.getSystemParam().getPotatoUrl());
        }
    }
}
