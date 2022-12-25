package com.one.tomato.thirdpart.recyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;
import com.broccoli.p150bh.R;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import java.util.Random;

/* loaded from: classes3.dex */
public class PullToRefreshHeader extends LinearLayout implements RefreshHeader {
    protected SpinnerStyle mSpinnerStyle = SpinnerStyle.Translate;
    private Random random;
    private LottieAnimationView refresh_header_image;
    private TextView refresh_header_text;
    private String[] strings;

    @Override // com.scwang.smartrefresh.layout.api.RefreshInternal
    @NonNull
    public View getView() {
        return this;
    }

    @Override // com.scwang.smartrefresh.layout.api.RefreshInternal
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override // com.scwang.smartrefresh.layout.api.RefreshInternal
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean z) {
        return 0;
    }

    @Override // com.scwang.smartrefresh.layout.api.RefreshInternal
    public void onHorizontalDrag(float f, int i, int i2) {
    }

    @Override // com.scwang.smartrefresh.layout.api.RefreshInternal
    public void onInitialized(@NonNull RefreshKernel refreshKernel, int i, int i2) {
    }

    @Override // com.scwang.smartrefresh.layout.api.RefreshInternal
    public void onMoving(boolean z, float f, int i, int i2, int i3) {
    }

    @Override // com.scwang.smartrefresh.layout.api.RefreshInternal
    public void onReleased(RefreshLayout refreshLayout, int i, int i2) {
    }

    @Override // com.scwang.smartrefresh.layout.api.RefreshInternal
    public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int i, int i2) {
    }

    @Override // com.scwang.smartrefresh.layout.api.RefreshInternal
    public void setPrimaryColors(int... iArr) {
    }

    public PullToRefreshHeader(Context context) {
        super(context);
        initView(context);
    }

    public PullToRefreshHeader(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        initView(context);
    }

    public PullToRefreshHeader(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initView(context);
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
    }

    private void initView(Context context) {
        if (this.random == null) {
            this.random = new Random();
        }
        this.strings = context.getResources().getStringArray(R.array.pullToRefresh_head_text);
        View inflate = LayoutInflater.from(context).inflate(R.layout.recyclerview_pulltorefresh_header, this);
        this.refresh_header_text = (TextView) inflate.findViewById(R.id.refresh_header_text);
        this.refresh_header_image = (LottieAnimationView) inflate.findViewById(R.id.refresh_header_image);
        TextView textView = this.refresh_header_text;
        String[] strArr = this.strings;
        textView.setText(strArr[this.random.nextInt(strArr.length)]);
        this.refresh_header_text.setVisibility(8);
        this.refresh_header_image.setVisibility(8);
    }

    @Override // com.scwang.smartrefresh.layout.api.RefreshInternal
    @NonNull
    public SpinnerStyle getSpinnerStyle() {
        return this.mSpinnerStyle;
    }

    /* renamed from: com.one.tomato.thirdpart.recyclerview.PullToRefreshHeader$1 */
    /* loaded from: classes3.dex */
    static /* synthetic */ class C27331 {
        static final /* synthetic */ int[] $SwitchMap$com$scwang$smartrefresh$layout$constant$RefreshState = new int[RefreshState.values().length];

        static {
            try {
                $SwitchMap$com$scwang$smartrefresh$layout$constant$RefreshState[RefreshState.PullDownToRefresh.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$scwang$smartrefresh$layout$constant$RefreshState[RefreshState.PullDownCanceled.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$scwang$smartrefresh$layout$constant$RefreshState[RefreshState.ReleaseToRefresh.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$scwang$smartrefresh$layout$constant$RefreshState[RefreshState.ReleaseToTwoLevel.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$scwang$smartrefresh$layout$constant$RefreshState[RefreshState.RefreshReleased.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$scwang$smartrefresh$layout$constant$RefreshState[RefreshState.Refreshing.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$scwang$smartrefresh$layout$constant$RefreshState[RefreshState.None.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$scwang$smartrefresh$layout$constant$RefreshState[RefreshState.RefreshFinish.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
        }
    }

    @Override // com.scwang.smartrefresh.layout.listener.OnStateChangedListener
    public void onStateChanged(RefreshLayout refreshLayout, RefreshState refreshState, RefreshState refreshState2) {
        switch (C27331.$SwitchMap$com$scwang$smartrefresh$layout$constant$RefreshState[refreshState2.ordinal()]) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                this.refresh_header_text.setVisibility(0);
                this.refresh_header_image.setVisibility(0);
                if (this.refresh_header_image.getAnimation() != null && this.refresh_header_image.isAnimating()) {
                    this.refresh_header_image.cancelAnimation();
                }
                this.refresh_header_image.setAnimation("loading_refresh.json");
                return;
            case 7:
            case 8:
                this.refresh_header_text.setVisibility(8);
                this.refresh_header_image.setVisibility(8);
                if (this.refresh_header_image.getAnimation() != null && this.refresh_header_image.isAnimating()) {
                    this.refresh_header_image.cancelAnimation();
                }
                TextView textView = this.refresh_header_text;
                String[] strArr = this.strings;
                textView.setText(strArr[this.random.nextInt(strArr.length)]);
                return;
            default:
                return;
        }
    }
}
