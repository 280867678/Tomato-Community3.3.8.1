package com.tomatolive.library.p136ui.view.widget;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.p002v4.content.ContextCompat;
import android.support.p002v4.view.GravityCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;
import com.blankj.utilcode.util.ConvertUtils;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.NumberUtils;

/* renamed from: com.tomatolive.library.ui.view.widget.ScrollTextViewForGrade */
/* loaded from: classes4.dex */
public class ScrollTextViewForGrade extends TextSwitcher implements ViewSwitcher.ViewFactory {
    private Context context;
    private ScrollAnimation inAnimation;
    private ScrollAnimation outAnimation;
    private CharSequence temp;
    private float textSize;

    public ScrollTextViewForGrade(Context context) {
        this(context, null);
    }

    public ScrollTextViewForGrade(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.textSize = 10.0f;
        this.context = context;
        init();
    }

    private void init() {
        setFactory(this);
        this.inAnimation = createAnim(-90.0f, 0.0f, true);
        this.outAnimation = createAnim(0.0f, 90.0f, false);
        setInAnimation(this.inAnimation);
        setOutAnimation(this.outAnimation);
    }

    private ScrollAnimation createAnim(float f, float f2, boolean z) {
        ScrollAnimation scrollAnimation = new ScrollAnimation(f, f2, z, true);
        scrollAnimation.setDuration(250L);
        scrollAnimation.setFillAfter(false);
        scrollAnimation.setInterpolator(new AccelerateInterpolator());
        return scrollAnimation;
    }

    public void updateSize(float f) {
        removeAllViews();
        this.textSize = f;
        setFactory(this);
        if (!TextUtils.isEmpty(this.temp)) {
            setText(this.temp);
        }
    }

    @Override // android.widget.TextSwitcher
    public void setText(CharSequence charSequence) {
        this.temp = charSequence;
        TextView textView = (TextView) getNextView();
        initUserGrade(NumberUtils.string2int(charSequence.toString()), textView);
        if (textView == null) {
            return;
        }
        super.setText(charSequence);
    }

    @Override // android.widget.ViewSwitcher.ViewFactory
    public View makeView() {
        TextView textView = new TextView(this.context);
        textView.setGravity(GravityCompat.START);
        textView.setCompoundDrawablePadding(ConvertUtils.dp2px(3.0f));
        textView.setPadding(ConvertUtils.dp2px(2.0f), ConvertUtils.dp2px(0.6f), ConvertUtils.dp2px(4.0f), ConvertUtils.dp2px(0.6f));
        textView.setTextSize(2, this.textSize);
        textView.setMinWidth(ConvertUtils.dp2px(40.0f));
        textView.setGravity(16);
        textView.setSingleLine(true);
        textView.setTextColor(-1);
        textView.setEllipsize(TextUtils.TruncateAt.END);
        AppUtils.formatTvNumTypeface(getContext(), textView, "");
        return textView;
    }

    public void next() {
        Animation inAnimation = getInAnimation();
        ScrollAnimation scrollAnimation = this.inAnimation;
        if (inAnimation != scrollAnimation) {
            setInAnimation(scrollAnimation);
        }
        Animation outAnimation = getOutAnimation();
        ScrollAnimation scrollAnimation2 = this.outAnimation;
        if (outAnimation != scrollAnimation2) {
            setOutAnimation(scrollAnimation2);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.tomatolive.library.ui.view.widget.ScrollTextViewForGrade$ScrollAnimation */
    /* loaded from: classes4.dex */
    public class ScrollAnimation extends Animation {
        private Camera mCamera;
        private float mCenterX;
        private float mCenterY;
        private final float mFromDegrees;
        private final float mToDegrees;
        private final boolean mTurnIn;
        private final boolean mTurnUp;

        private ScrollAnimation(float f, float f2, boolean z, boolean z2) {
            this.mFromDegrees = f;
            this.mToDegrees = f2;
            this.mTurnIn = z;
            this.mTurnUp = z2;
        }

        @Override // android.view.animation.Animation
        public void initialize(int i, int i2, int i3, int i4) {
            super.initialize(i, i2, i3, i4);
            this.mCamera = new Camera();
            this.mCenterY = ScrollTextViewForGrade.this.getHeight() / 2.0f;
            this.mCenterX = ScrollTextViewForGrade.this.getWidth() / 2.0f;
        }

        @Override // android.view.animation.Animation
        protected void applyTransformation(float f, Transformation transformation) {
            float f2 = this.mCenterX;
            float f3 = this.mCenterY;
            Camera camera = this.mCamera;
            int i = this.mTurnUp ? 1 : -1;
            Matrix matrix = transformation.getMatrix();
            camera.save();
            if (this.mTurnIn) {
                camera.translate(0.0f, i * this.mCenterY * (f - 1.0f), 0.0f);
            } else {
                camera.translate(0.0f, i * this.mCenterY * f, 0.0f);
            }
            camera.getMatrix(matrix);
            camera.restore();
            matrix.preTranslate(-f2, -f3);
            matrix.postTranslate(f2, f3);
        }
    }

    private void initUserGrade(int i, TextView textView) {
        textView.setBackgroundResource(AppUtils.getUserGradeBackgroundResource(true, i));
        setTextViewDrawable(AppUtils.getUserGradeIconResource(false, i), textView);
    }

    private void setTextViewDrawable(@DrawableRes int i, TextView textView) {
        textView.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(this.context, i), (Drawable) null, (Drawable) null, (Drawable) null);
    }
}
