package com.one.tomato.widget.scrolltextview;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.support.p002v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;
import com.broccoli.p150bh.R;
import com.one.tomato.utils.DisplayMetricsUtils;

/* loaded from: classes3.dex */
public class AutoVerticalScrollTextView extends TextSwitcher implements ViewSwitcher.ViewFactory {
    private Context mContext;
    private Rotate3dAnimation mInUp;
    private Rotate3dAnimation mOutUp;

    public AutoVerticalScrollTextView(Context context) {
        this(context, null);
    }

    public AutoVerticalScrollTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        init();
    }

    private void init() {
        setFactory(this);
        this.mInUp = createAnim(true, true);
        this.mOutUp = createAnim(false, true);
        setInAnimation(this.mInUp);
        setOutAnimation(this.mOutUp);
    }

    private Rotate3dAnimation createAnim(boolean z, boolean z2) {
        Rotate3dAnimation rotate3dAnimation = new Rotate3dAnimation(z, z2);
        rotate3dAnimation.setDuration(1000L);
        rotate3dAnimation.setFillAfter(false);
        rotate3dAnimation.setInterpolator(new AccelerateInterpolator());
        return rotate3dAnimation;
    }

    @Override // android.widget.ViewSwitcher.ViewFactory
    public View makeView() {
        TextView textView = new TextView(this.mContext);
        textView.setTextSize(16.0f);
        textView.setSingleLine(true);
        textView.setGravity(8388627);
        textView.setEllipsize(TextUtils.TruncateAt.END);
        textView.setCompoundDrawablesRelativeWithIntrinsicBounds(ContextCompat.getDrawable(this.mContext, R.drawable.post_hot_message), (Drawable) null, (Drawable) null, (Drawable) null);
        textView.setCompoundDrawablePadding((int) DisplayMetricsUtils.dp2px(2.0f));
        textView.setTextColor(Color.parseColor("#292935"));
        return textView;
    }

    public void next() {
        Animation inAnimation = getInAnimation();
        Rotate3dAnimation rotate3dAnimation = this.mInUp;
        if (inAnimation != rotate3dAnimation) {
            setInAnimation(rotate3dAnimation);
        }
        Animation outAnimation = getOutAnimation();
        Rotate3dAnimation rotate3dAnimation2 = this.mOutUp;
        if (outAnimation != rotate3dAnimation2) {
            setOutAnimation(rotate3dAnimation2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public class Rotate3dAnimation extends Animation {
        private Camera mCamera;
        private float mCenterX;
        private float mCenterY;
        private final boolean mTurnIn;
        private final boolean mTurnUp;

        Rotate3dAnimation(boolean z, boolean z2) {
            this.mTurnIn = z;
            this.mTurnUp = z2;
        }

        @Override // android.view.animation.Animation
        public void initialize(int i, int i2, int i3, int i4) {
            super.initialize(i, i2, i3, i4);
            this.mCamera = new Camera();
            this.mCenterY = AutoVerticalScrollTextView.this.getHeight();
            this.mCenterX = AutoVerticalScrollTextView.this.getWidth();
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
}
