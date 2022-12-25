package com.scwang.smartrefresh.layout.footer;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.scwang.smartrefresh.layout.R$string;
import com.scwang.smartrefresh.layout.R$styleable;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.internal.ArrowDrawable;
import com.scwang.smartrefresh.layout.internal.InternalClassics;
import com.scwang.smartrefresh.layout.internal.ProgressDrawable;
import com.scwang.smartrefresh.layout.util.DensityUtil;

/* loaded from: classes3.dex */
public class ClassicsFooter extends InternalClassics<ClassicsFooter> implements RefreshFooter {
    public static String REFRESH_FOOTER_FAILED;
    public static String REFRESH_FOOTER_FINISH;
    public static String REFRESH_FOOTER_LOADING;
    public static String REFRESH_FOOTER_NOTHING;
    public static String REFRESH_FOOTER_PULLING;
    public static String REFRESH_FOOTER_REFRESHING;
    public static String REFRESH_FOOTER_RELEASE;
    protected boolean mNoMoreData;
    protected String mTextFailed;
    protected String mTextFinish;
    protected String mTextLoading;
    protected String mTextNothing;
    protected String mTextPulling;
    protected String mTextRefreshing;
    protected String mTextRelease;

    public ClassicsFooter(Context context) {
        this(context, null);
    }

    public ClassicsFooter(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ClassicsFooter(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mTextPulling = null;
        this.mTextRelease = null;
        this.mTextLoading = null;
        this.mTextRefreshing = null;
        this.mTextFinish = null;
        this.mTextFailed = null;
        this.mTextNothing = null;
        this.mNoMoreData = false;
        if (REFRESH_FOOTER_PULLING == null) {
            REFRESH_FOOTER_PULLING = context.getString(R$string.srl_footer_pulling);
        }
        if (REFRESH_FOOTER_RELEASE == null) {
            REFRESH_FOOTER_RELEASE = context.getString(R$string.srl_footer_release);
        }
        if (REFRESH_FOOTER_LOADING == null) {
            REFRESH_FOOTER_LOADING = context.getString(R$string.srl_footer_loading);
        }
        if (REFRESH_FOOTER_REFRESHING == null) {
            REFRESH_FOOTER_REFRESHING = context.getString(R$string.srl_footer_refreshing);
        }
        if (REFRESH_FOOTER_FINISH == null) {
            REFRESH_FOOTER_FINISH = context.getString(R$string.srl_footer_finish);
        }
        if (REFRESH_FOOTER_FAILED == null) {
            REFRESH_FOOTER_FAILED = context.getString(R$string.srl_footer_failed);
        }
        if (REFRESH_FOOTER_NOTHING == null) {
            REFRESH_FOOTER_NOTHING = context.getString(R$string.srl_footer_nothing);
        }
        ImageView imageView = this.mArrowView;
        ImageView imageView2 = this.mProgressView;
        DensityUtil densityUtil = new DensityUtil();
        this.mTitleText.setTextColor(-10066330);
        this.mTitleText.setText(isInEditMode() ? REFRESH_FOOTER_LOADING : REFRESH_FOOTER_PULLING);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.ClassicsFooter);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) imageView2.getLayoutParams();
        layoutParams2.rightMargin = obtainStyledAttributes.getDimensionPixelSize(R$styleable.ClassicsFooter_srlDrawableMarginRight, densityUtil.dip2px(20.0f));
        layoutParams.rightMargin = layoutParams2.rightMargin;
        layoutParams.width = obtainStyledAttributes.getLayoutDimension(R$styleable.ClassicsFooter_srlDrawableArrowSize, layoutParams.width);
        layoutParams.height = obtainStyledAttributes.getLayoutDimension(R$styleable.ClassicsFooter_srlDrawableArrowSize, layoutParams.height);
        layoutParams2.width = obtainStyledAttributes.getLayoutDimension(R$styleable.ClassicsFooter_srlDrawableProgressSize, layoutParams2.width);
        layoutParams2.height = obtainStyledAttributes.getLayoutDimension(R$styleable.ClassicsFooter_srlDrawableProgressSize, layoutParams2.height);
        layoutParams.width = obtainStyledAttributes.getLayoutDimension(R$styleable.ClassicsFooter_srlDrawableSize, layoutParams.width);
        layoutParams.height = obtainStyledAttributes.getLayoutDimension(R$styleable.ClassicsFooter_srlDrawableSize, layoutParams.height);
        layoutParams2.width = obtainStyledAttributes.getLayoutDimension(R$styleable.ClassicsFooter_srlDrawableSize, layoutParams2.width);
        layoutParams2.height = obtainStyledAttributes.getLayoutDimension(R$styleable.ClassicsFooter_srlDrawableSize, layoutParams2.height);
        this.mFinishDuration = obtainStyledAttributes.getInt(R$styleable.ClassicsFooter_srlFinishDuration, this.mFinishDuration);
        this.mSpinnerStyle = SpinnerStyle.values()[obtainStyledAttributes.getInt(R$styleable.ClassicsFooter_srlClassicsSpinnerStyle, this.mSpinnerStyle.ordinal())];
        if (obtainStyledAttributes.hasValue(R$styleable.ClassicsFooter_srlDrawableArrow)) {
            this.mArrowView.setImageDrawable(obtainStyledAttributes.getDrawable(R$styleable.ClassicsFooter_srlDrawableArrow));
        } else {
            this.mArrowDrawable = new ArrowDrawable();
            this.mArrowDrawable.setColor(-10066330);
            this.mArrowView.setImageDrawable(this.mArrowDrawable);
        }
        if (obtainStyledAttributes.hasValue(R$styleable.ClassicsFooter_srlDrawableProgress)) {
            this.mProgressView.setImageDrawable(obtainStyledAttributes.getDrawable(R$styleable.ClassicsFooter_srlDrawableProgress));
        } else {
            this.mProgressDrawable = new ProgressDrawable();
            this.mProgressDrawable.setColor(-10066330);
            this.mProgressView.setImageDrawable(this.mProgressDrawable);
        }
        if (obtainStyledAttributes.hasValue(R$styleable.ClassicsFooter_srlTextSizeTitle)) {
            this.mTitleText.setTextSize(0, obtainStyledAttributes.getDimensionPixelSize(R$styleable.ClassicsFooter_srlTextSizeTitle, DensityUtil.dp2px(16.0f)));
        } else {
            this.mTitleText.setTextSize(16.0f);
        }
        if (obtainStyledAttributes.hasValue(R$styleable.ClassicsFooter_srlPrimaryColor)) {
            super.setPrimaryColor(obtainStyledAttributes.getColor(R$styleable.ClassicsFooter_srlPrimaryColor, 0));
        }
        if (obtainStyledAttributes.hasValue(R$styleable.ClassicsFooter_srlAccentColor)) {
            super.mo6489setAccentColor(obtainStyledAttributes.getColor(R$styleable.ClassicsFooter_srlAccentColor, 0));
        }
        this.mTextPulling = REFRESH_FOOTER_PULLING;
        this.mTextRelease = REFRESH_FOOTER_RELEASE;
        this.mTextLoading = REFRESH_FOOTER_LOADING;
        this.mTextRefreshing = REFRESH_FOOTER_REFRESHING;
        this.mTextFinish = REFRESH_FOOTER_FINISH;
        this.mTextFailed = REFRESH_FOOTER_FAILED;
        this.mTextNothing = REFRESH_FOOTER_NOTHING;
        if (obtainStyledAttributes.hasValue(R$styleable.ClassicsFooter_srlTextPulling)) {
            this.mTextPulling = obtainStyledAttributes.getString(R$styleable.ClassicsFooter_srlTextPulling);
        }
        if (obtainStyledAttributes.hasValue(R$styleable.ClassicsFooter_srlTextRelease)) {
            this.mTextRelease = obtainStyledAttributes.getString(R$styleable.ClassicsFooter_srlTextRelease);
        }
        if (obtainStyledAttributes.hasValue(R$styleable.ClassicsFooter_srlTextLoading)) {
            this.mTextLoading = obtainStyledAttributes.getString(R$styleable.ClassicsFooter_srlTextLoading);
        }
        if (obtainStyledAttributes.hasValue(R$styleable.ClassicsFooter_srlTextRefreshing)) {
            this.mTextRefreshing = obtainStyledAttributes.getString(R$styleable.ClassicsFooter_srlTextRefreshing);
        }
        if (obtainStyledAttributes.hasValue(R$styleable.ClassicsFooter_srlTextFinish)) {
            this.mTextFinish = obtainStyledAttributes.getString(R$styleable.ClassicsFooter_srlTextFinish);
        }
        if (obtainStyledAttributes.hasValue(R$styleable.ClassicsFooter_srlTextFailed)) {
            this.mTextFailed = obtainStyledAttributes.getString(R$styleable.ClassicsFooter_srlTextFailed);
        }
        if (obtainStyledAttributes.hasValue(R$styleable.ClassicsFooter_srlTextNothing)) {
            this.mTextNothing = obtainStyledAttributes.getString(R$styleable.ClassicsFooter_srlTextNothing);
        }
        obtainStyledAttributes.recycle();
    }

    @Override // com.scwang.smartrefresh.layout.internal.InternalClassics, com.scwang.smartrefresh.layout.internal.InternalAbstract, com.scwang.smartrefresh.layout.api.RefreshInternal
    public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int i, int i2) {
        if (!this.mNoMoreData) {
            super.onStartAnimator(refreshLayout, i, i2);
        }
    }

    @Override // com.scwang.smartrefresh.layout.internal.InternalClassics, com.scwang.smartrefresh.layout.internal.InternalAbstract, com.scwang.smartrefresh.layout.api.RefreshInternal
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean z) {
        if (!this.mNoMoreData) {
            this.mTitleText.setText(z ? this.mTextFinish : this.mTextFailed);
            return super.onFinish(refreshLayout, z);
        }
        return 0;
    }

    @Override // com.scwang.smartrefresh.layout.internal.InternalClassics, com.scwang.smartrefresh.layout.internal.InternalAbstract, com.scwang.smartrefresh.layout.api.RefreshInternal
    @Deprecated
    public void setPrimaryColors(@ColorInt int... iArr) {
        if (this.mSpinnerStyle == SpinnerStyle.FixedBehind) {
            super.setPrimaryColors(iArr);
        }
    }

    @Override // com.scwang.smartrefresh.layout.api.RefreshFooter
    public boolean setNoMoreData(boolean z) {
        if (this.mNoMoreData != z) {
            this.mNoMoreData = z;
            ImageView imageView = this.mArrowView;
            if (z) {
                this.mTitleText.setText(this.mTextNothing);
                imageView.setVisibility(8);
                return true;
            }
            this.mTitleText.setText(this.mTextPulling);
            imageView.setVisibility(0);
            return true;
        }
        return true;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.scwang.smartrefresh.layout.internal.InternalAbstract, com.scwang.smartrefresh.layout.listener.OnStateChangedListener
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState refreshState, @NonNull RefreshState refreshState2) {
        ImageView imageView = this.mArrowView;
        if (!this.mNoMoreData) {
            switch (C30721.$SwitchMap$com$scwang$smartrefresh$layout$constant$RefreshState[refreshState2.ordinal()]) {
                case 1:
                    imageView.setVisibility(0);
                    break;
                case 2:
                    break;
                case 3:
                case 4:
                    imageView.setVisibility(8);
                    this.mTitleText.setText(this.mTextLoading);
                    return;
                case 5:
                    this.mTitleText.setText(this.mTextRelease);
                    imageView.animate().rotation(0.0f);
                    return;
                case 6:
                    this.mTitleText.setText(this.mTextRefreshing);
                    imageView.setVisibility(8);
                    return;
                default:
                    return;
            }
            this.mTitleText.setText(this.mTextPulling);
            imageView.animate().rotation(180.0f);
        }
    }

    /* renamed from: com.scwang.smartrefresh.layout.footer.ClassicsFooter$1 */
    /* loaded from: classes3.dex */
    static /* synthetic */ class C30721 {
        static final /* synthetic */ int[] $SwitchMap$com$scwang$smartrefresh$layout$constant$RefreshState = new int[RefreshState.values().length];

        static {
            try {
                $SwitchMap$com$scwang$smartrefresh$layout$constant$RefreshState[RefreshState.None.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$scwang$smartrefresh$layout$constant$RefreshState[RefreshState.PullUpToLoad.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$scwang$smartrefresh$layout$constant$RefreshState[RefreshState.Loading.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$scwang$smartrefresh$layout$constant$RefreshState[RefreshState.LoadReleased.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$scwang$smartrefresh$layout$constant$RefreshState[RefreshState.ReleaseToLoad.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$scwang$smartrefresh$layout$constant$RefreshState[RefreshState.Refreshing.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }
}
