package com.tomatolive.library.p136ui.view.widget.progress;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.p005v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import com.tomatolive.library.R$string;
import com.tomatolive.library.R$styleable;
import com.tomatolive.library.utils.SystemUtils;

/* renamed from: com.tomatolive.library.ui.view.widget.progress.AnimDownCountProgressButton */
/* loaded from: classes4.dex */
public class AnimDownCountProgressButton extends AppCompatTextView {
    public static final int DOWNLOADING = 1;
    public static final int NORMAL = 0;
    private boolean flag;
    private boolean isFull;
    private DownCountListener listner;
    private float mAboveTextSize;
    private RectF mBackgroundBounds;
    private int[] mBackgroundColor;
    private Paint mBackgroundPaint;
    private int mBackgroundSecondColor;
    private float mButtonRadius;
    private Context mContext;
    private CharSequence mCurrentText;
    private ButtonController mCustomerController;
    private ButtonController mDefaultController;
    private LinearGradient mFillBgGradient;
    private int mMaxProgress;
    private int mMinProgress;
    private int[] mOriginBackgroundColor;
    private float mProgress;
    private ValueAnimator mProgressAnimation;
    private LinearGradient mProgressBgGradient;
    private float mProgressPercent;
    private LinearGradient mProgressTextGradient;
    private int mState;
    private int mTextColor;
    private int mTextCoverColor;
    private volatile Paint mTextPaint;
    private int mToProgress;

    /* renamed from: com.tomatolive.library.ui.view.widget.progress.AnimDownCountProgressButton$DownCountListener */
    /* loaded from: classes4.dex */
    public interface DownCountListener {
        void onFinish();

        void onStart();
    }

    public AnimDownCountProgressButton(Context context) {
        this(context, null);
    }

    public AnimDownCountProgressButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mAboveTextSize = 50.0f;
        this.mProgress = -1.0f;
        this.isFull = true;
        this.flag = true;
        if (!isInEditMode()) {
            this.mContext = context;
            initController();
            initAttrs(context, attributeSet);
            init();
            setupAnimations();
            return;
        }
        initController();
    }

    private void initController() {
        this.mDefaultController = new DefaultButtonController();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.p005v7.widget.AppCompatTextView, android.widget.TextView, android.view.View
    public void drawableStateChanged() {
        super.drawableStateChanged();
    }

    private void initAttrs(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.AnimDownloadProgressButton);
        int color = obtainStyledAttributes.getColor(R$styleable.AnimDownloadProgressButton_progressbtn_background_color, Color.parseColor("#6699ff"));
        initGradientColor(color, color);
        this.mBackgroundSecondColor = obtainStyledAttributes.getColor(R$styleable.AnimDownloadProgressButton_progressbtn_background_second_color, -3355444);
        this.mButtonRadius = obtainStyledAttributes.getFloat(R$styleable.AnimDownloadProgressButton_progressbtn_radius, getMeasuredHeight() / 2);
        this.mAboveTextSize = obtainStyledAttributes.getFloat(R$styleable.AnimDownloadProgressButton_progressbtn_text_size, 50.0f);
        this.mTextColor = obtainStyledAttributes.getColor(R$styleable.AnimDownloadProgressButton_progressbtn_text_color, color);
        this.mTextCoverColor = obtainStyledAttributes.getColor(R$styleable.AnimDownloadProgressButton_progressbtn_text_covercolor, -1);
        boolean z = obtainStyledAttributes.getBoolean(R$styleable.AnimDownloadProgressButton_progressbtn_enable_gradient, false);
        ((DefaultButtonController) this.mDefaultController).setEnableGradient(z).setEnablePress(obtainStyledAttributes.getBoolean(R$styleable.AnimDownloadProgressButton_progressbtn_enable_press, false));
        if (z) {
            initGradientColor(this.mDefaultController.getLighterColor(this.mBackgroundColor[0]), this.mBackgroundColor[0]);
        }
        obtainStyledAttributes.recycle();
    }

    private void init() {
        this.mMaxProgress = 50;
        this.mMinProgress = 0;
        this.mProgress = 0.0f;
        this.mBackgroundPaint = new Paint();
        this.mBackgroundPaint.setAntiAlias(true);
        this.mBackgroundPaint.setStyle(Paint.Style.FILL);
        this.mTextPaint = new Paint();
        this.mTextPaint.setAntiAlias(true);
        this.mTextPaint.setTextSize(SystemUtils.sp2px(this.mAboveTextSize));
        if (Build.VERSION.SDK_INT >= 11) {
            setLayerType(1, this.mTextPaint);
        }
        this.mState = 0;
        invalidate();
    }

    private int[] initGradientColor(int i, int i2) {
        this.mBackgroundColor = new int[2];
        int[] iArr = this.mBackgroundColor;
        iArr[0] = i;
        iArr[1] = i2;
        return iArr;
    }

    @Override // android.widget.TextView, android.view.View
    public void setEnabled(boolean z) {
        super.setEnabled(z);
        this.mState = 0;
        removeAllAnim();
        invalidate();
    }

    private void setupAnimations() {
        this.mProgressAnimation = ValueAnimator.ofFloat(0.0f, 1.0f).setDuration(5000L);
        this.mProgressAnimation.setInterpolator(new LinearInterpolator());
        this.mProgressAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.tomatolive.library.ui.view.widget.progress.AnimDownCountProgressButton.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                AnimDownCountProgressButton animDownCountProgressButton = AnimDownCountProgressButton.this;
                animDownCountProgressButton.mToProgress = (int) (animDownCountProgressButton.mProgress * (1.0f - floatValue));
                if (AnimDownCountProgressButton.this.mToProgress <= 0) {
                    if (AnimDownCountProgressButton.this.mToProgress != 0 || !AnimDownCountProgressButton.this.flag) {
                        return;
                    }
                    AnimDownCountProgressButton.this.flag = false;
                    AnimDownCountProgressButton.this.setCurrentText(R$string.fq_text_send);
                    if (AnimDownCountProgressButton.this.listner == null) {
                        return;
                    }
                    AnimDownCountProgressButton.this.listner.onFinish();
                    return;
                }
                AnimDownCountProgressButton animDownCountProgressButton2 = AnimDownCountProgressButton.this;
                animDownCountProgressButton2.mCurrentText = animDownCountProgressButton2.getContext().getString(R$string.fq_git_batter, String.valueOf(AnimDownCountProgressButton.this.mToProgress));
                AnimDownCountProgressButton.this.invalidate();
            }
        });
    }

    public void setDownCountListener(DownCountListener downCountListener) {
        this.listner = downCountListener;
    }

    public void setFullCorner(boolean z) {
        this.isFull = z;
        invalidate();
    }

    @Override // android.widget.TextView, android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isInEditMode()) {
            drawing(canvas);
        }
    }

    private void drawing(Canvas canvas) {
        drawBackground(canvas);
        drawTextAbove(canvas);
    }

    private void drawBackground(Canvas canvas) {
        this.mBackgroundBounds = new RectF();
        if (this.mButtonRadius == 0.0f) {
            this.mButtonRadius = getMeasuredHeight() / 2;
        }
        this.mBackgroundBounds.left = this.isFull ? 0.0f : -100.0f;
        RectF rectF = this.mBackgroundBounds;
        rectF.top = 0.0f;
        rectF.right = getMeasuredWidth();
        this.mBackgroundBounds.bottom = getMeasuredHeight();
        int i = this.mState;
        if (i == 0) {
            if (this.mBackgroundPaint.getShader() != null) {
                this.mBackgroundPaint.setShader(null);
            }
            if (isEnabled()) {
                this.mBackgroundPaint.setColor(this.mBackgroundSecondColor);
            } else {
                this.mBackgroundPaint.setColor(Color.parseColor("#ff666666"));
            }
            RectF rectF2 = this.mBackgroundBounds;
            float f = this.mButtonRadius;
            canvas.drawRoundRect(rectF2, f, f, this.mBackgroundPaint);
        } else if (i != 1) {
        } else {
            this.mProgressPercent = this.mToProgress / (this.mMaxProgress + 0.0f);
            float measuredWidth = getMeasuredWidth();
            int[] iArr = {this.mBackgroundColor[0], this.mBackgroundSecondColor};
            float f2 = this.mProgressPercent;
            this.mProgressBgGradient = new LinearGradient(0.0f, 0.0f, measuredWidth, 0.0f, iArr, new float[]{f2, f2 + 0.001f}, Shader.TileMode.CLAMP);
            this.mBackgroundPaint.setColor(this.mBackgroundColor[0]);
            this.mBackgroundPaint.setShader(this.mProgressBgGradient);
            RectF rectF3 = this.mBackgroundBounds;
            float f3 = this.mButtonRadius;
            canvas.drawRoundRect(rectF3, f3, f3, this.mBackgroundPaint);
        }
    }

    private void drawTextAbove(Canvas canvas) {
        float height = (canvas.getHeight() / 2) - ((this.mTextPaint.descent() / 2.0f) + (this.mTextPaint.ascent() / 2.0f));
        if (this.mCurrentText == null) {
            this.mCurrentText = "";
        }
        float measureText = this.mTextPaint.measureText(this.mCurrentText.toString());
        int i = this.mState;
        if (i == 0) {
            this.mTextPaint.setShader(null);
            this.mTextPaint.setColor(this.mTextCoverColor);
            canvas.drawText(this.mCurrentText.toString(), (getMeasuredWidth() - measureText) / 2.0f, height, this.mTextPaint);
        } else if (i != 1) {
        } else {
            float measuredWidth = getMeasuredWidth() * this.mProgressPercent;
            float f = measureText / 2.0f;
            float measuredWidth2 = (getMeasuredWidth() / 2) - f;
            float measuredWidth3 = (getMeasuredWidth() / 2) + f;
            float measuredWidth4 = ((f - (getMeasuredWidth() / 2)) + measuredWidth) / measureText;
            if (measuredWidth <= measuredWidth2) {
                this.mTextPaint.setShader(null);
                this.mTextPaint.setColor(this.mTextColor);
            } else if (measuredWidth2 < measuredWidth && measuredWidth <= measuredWidth3) {
                this.mProgressTextGradient = new LinearGradient((getMeasuredWidth() - measureText) / 2.0f, 0.0f, (getMeasuredWidth() + measureText) / 2.0f, 0.0f, new int[]{this.mTextCoverColor, this.mTextColor}, new float[]{measuredWidth4, measuredWidth4 + 0.001f}, Shader.TileMode.CLAMP);
                this.mTextPaint.setColor(this.mTextColor);
                this.mTextPaint.setShader(this.mProgressTextGradient);
            } else {
                this.mTextPaint.setShader(null);
                this.mTextPaint.setColor(this.mTextCoverColor);
            }
            canvas.drawText(this.mCurrentText.toString(), (getMeasuredWidth() - measureText) / 2.0f, height, this.mTextPaint);
        }
    }

    private ButtonController switchController() {
        ButtonController buttonController = this.mCustomerController;
        return buttonController != null ? buttonController : this.mDefaultController;
    }

    public int getState() {
        return this.mState;
    }

    public void setState(int i) {
        if (this.mState != i) {
            this.mState = i;
            invalidate();
        }
    }

    public void setCurrentText(CharSequence charSequence) {
        this.mCurrentText = charSequence;
        invalidate();
    }

    public void setCurrentText(int i) {
        this.mCurrentText = getContext().getResources().getText(i);
        invalidate();
    }

    public float getProgress() {
        return this.mProgress;
    }

    public void setProgress(float f) {
        this.mProgress = f;
    }

    public void removeAllAnim() {
        ValueAnimator valueAnimator = this.mProgressAnimation;
        if (valueAnimator != null) {
            valueAnimator.cancel();
            this.mProgressAnimation.removeAllListeners();
        }
    }

    public void setProgressBtnBackgroundColor(int i) {
        initGradientColor(i, i);
    }

    public void setProgressBtnBackgroundSecondColor(int i) {
        this.mBackgroundSecondColor = i;
    }

    public float getButtonRadius() {
        return this.mButtonRadius;
    }

    public void setButtonRadius(float f) {
        this.mButtonRadius = f;
    }

    public int getTextColor() {
        return this.mTextColor;
    }

    @Override // android.widget.TextView
    public void setTextColor(int i) {
        this.mTextColor = i;
    }

    public int getTextCoverColor() {
        return this.mTextCoverColor;
    }

    public void setTextCoverColor(int i) {
        this.mTextCoverColor = i;
    }

    public int getMinProgress() {
        return this.mMinProgress;
    }

    public void setMinProgress(int i) {
        this.mMinProgress = i;
    }

    public int getMaxProgress() {
        return this.mMaxProgress;
    }

    public void setMaxProgress(int i) {
        this.mMaxProgress = i;
    }

    public void enabelDefaultPress(boolean z) {
        ButtonController buttonController = this.mDefaultController;
        if (buttonController != null) {
            ((DefaultButtonController) buttonController).setEnablePress(z);
        }
    }

    public void enabelDefaultGradient(boolean z) {
        ButtonController buttonController = this.mDefaultController;
        if (buttonController != null) {
            ((DefaultButtonController) buttonController).setEnableGradient(z);
            initGradientColor(this.mDefaultController.getLighterColor(this.mBackgroundColor[0]), this.mBackgroundColor[0]);
        }
    }

    @Override // android.widget.TextView
    public void setTextSize(float f) {
        this.mAboveTextSize = f;
        this.mTextPaint.setTextSize(SystemUtils.sp2px(f));
    }

    @Override // android.widget.TextView
    public float getTextSize() {
        return this.mAboveTextSize;
    }

    public AnimDownCountProgressButton setCustomerController(ButtonController buttonController) {
        this.mCustomerController = buttonController;
        return this;
    }

    public void startDownCont(int i) {
        this.mState = 1;
        this.mProgress = i;
        this.mToProgress = 0;
        DownCountListener downCountListener = this.listner;
        if (downCountListener != null) {
            downCountListener.onStart();
        }
        this.flag = true;
        this.mProgressAnimation.start();
    }

    /* renamed from: com.tomatolive.library.ui.view.widget.progress.AnimDownCountProgressButton$SavedState */
    /* loaded from: classes4.dex */
    public static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() { // from class: com.tomatolive.library.ui.view.widget.progress.AnimDownCountProgressButton.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            /* renamed from: createFromParcel */
            public SavedState mo6740createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            /* renamed from: newArray */
            public SavedState[] mo6741newArray(int i) {
                return new SavedState[i];
            }
        };
        private String currentText;
        private int progress;
        private int state;

        public SavedState(Parcelable parcelable, int i, int i2, String str) {
            super(parcelable);
            this.progress = i;
            this.state = i2;
            this.currentText = str;
        }

        private SavedState(Parcel parcel) {
            super(parcel);
            this.progress = parcel.readInt();
            this.state = parcel.readInt();
            this.currentText = parcel.readString();
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.progress);
            parcel.writeInt(this.state);
            parcel.writeString(this.currentText);
        }
    }
}
