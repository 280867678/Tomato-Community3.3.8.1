package com.tomatolive.library.p136ui.view.widget.drawabletext;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.LinearGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.support.p002v4.view.ViewCompat;
import android.support.p005v7.widget.AppCompatTextView;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.tomatolive.library.R$styleable;
import java.util.HashMap;

/* renamed from: com.tomatolive.library.ui.view.widget.drawabletext.DrawableTextView */
/* loaded from: classes4.dex */
public class DrawableTextView extends AppCompatTextView {
    private static final String TAG = "GradientTextView";
    boolean inflated;
    private SparseArray<LinearGradient> mDrawableStateShader;
    private RectF mRect = new RectF();
    private HashMap<String, ViewState> mShaderHashMap = new HashMap<>();
    private Runnable refreshRunnable = new Runnable() { // from class: com.tomatolive.library.ui.view.widget.drawabletext.DrawableTextView.1
        @Override // java.lang.Runnable
        public void run() {
            if (DrawableTextView.this.textColorDrawable != null) {
                if (!(DrawableTextView.this.textColorDrawable instanceof GradientDrawable) && !(DrawableTextView.this.textColorDrawable instanceof StateListDrawable)) {
                    return;
                }
                DrawableTextView.this.resetColorState();
            }
        }
    };
    private Drawable textColorDrawable;

    public DrawableTextView(Context context) {
        super(context);
        setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
    }

    public DrawableTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.DrawableTextView);
            this.textColorDrawable = obtainStyledAttributes.getDrawable(R$styleable.DrawableTextView_textDrawable);
            obtainStyledAttributes.recycle();
        }
    }

    @Override // android.widget.TextView
    public void setText(CharSequence charSequence, TextView.BufferType bufferType) {
        super.setText(charSequence, bufferType);
        post(this.refreshRunnable);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.p005v7.widget.AppCompatTextView, android.widget.TextView, android.view.View
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (getMeasuredWidth() == 0 || this.inflated || this.textColorDrawable == null) {
            return;
        }
        resetColorState();
        this.inflated = true;
    }

    public void setTextDrawable(Drawable drawable) {
        this.textColorDrawable = drawable;
        this.mShaderHashMap.clear();
        setTextDrawableInner(drawable);
    }

    private void setTextDrawableInner(Drawable drawable) {
        this.textColorDrawable = drawable;
        Drawable drawable2 = this.textColorDrawable;
        if (drawable2 instanceof StateListDrawable) {
            drawable2.setState(getDrawableState());
            Drawable current = this.textColorDrawable.getCurrent();
            Shader cachedShader = getCachedShader();
            if (cachedShader != null) {
                setTextColor(ViewCompat.MEASURED_STATE_MASK);
                getPaint().setShader(cachedShader);
            } else if (current instanceof ColorDrawable) {
                getPaint().setShader(null);
                setTextColor(((ColorDrawable) current).getColor());
            } else if (current instanceof GradientDrawable) {
                setTextColor(ViewCompat.MEASURED_STATE_MASK);
                setGradientColor((GradientDrawable) current);
            } else if (!(current instanceof BitmapDrawable)) {
            } else {
                setTextColor(ViewCompat.MEASURED_STATE_MASK);
                TextPaint paint = getPaint();
                Shader bitmapDrawable = setBitmapDrawable((BitmapDrawable) current);
                paint.setShader(bitmapDrawable);
                this.mShaderHashMap.put(current.getClass().getName(), new ViewState(bitmapDrawable, getMeasuredWidth()));
            }
        } else if (drawable2 instanceof GradientDrawable) {
            setTextColor(ViewCompat.MEASURED_STATE_MASK);
            Shader cachedShader2 = getCachedShader();
            if (cachedShader2 != null) {
                getPaint().setShader(cachedShader2);
            } else {
                setGradientColor((GradientDrawable) this.textColorDrawable);
            }
        } else if (!(drawable2 instanceof BitmapDrawable)) {
        } else {
            this.mShaderHashMap.put(this.textColorDrawable.getClass().getName(), new ViewState(setBitmapDrawable((BitmapDrawable) drawable2), getMeasuredWidth()));
        }
    }

    private Shader getCachedShader() {
        ViewState viewState = this.mShaderHashMap.get(this.textColorDrawable.getClass().getName());
        if (this.textColorDrawable == null || viewState == null) {
            return null;
        }
        return viewState.getShader(getMeasuredWidth());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetColorState() {
        setTextDrawableInner(this.textColorDrawable);
    }

    private Shader setBitmapDrawable(BitmapDrawable bitmapDrawable) {
        setTextColor(ViewCompat.MEASURED_STATE_MASK);
        TextPaint paint = getPaint();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        Shader.TileMode tileMode = Shader.TileMode.CLAMP;
        BitmapShader bitmapShader = new BitmapShader(bitmap, tileMode, tileMode);
        paint.setShader(bitmapShader);
        return bitmapShader;
    }

    private void setGradientColor(GradientDrawable gradientDrawable) {
        int[] colors = DrawableCompatHelper.getColors(gradientDrawable);
        boolean z = false;
        if (getMeasuredWidth() == 0) {
            int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
            measure(makeMeasureSpec, makeMeasureSpec);
        }
        if (getMeasuredWidth() == 0 || colors == null || colors.length <= 0) {
            return;
        }
        int[] iArr = colors.length == 1 ? new int[]{colors[0], colors[0]} : colors;
        TextPaint paint = getPaint();
        float measuredWidth = getMeasuredWidth();
        if (iArr.length == 3) {
            z = true;
        }
        LinearGradient linearGradient = new LinearGradient(0.0f, 0.0f, measuredWidth, 0.0f, iArr, DrawableCompatHelper.getPositions(gradientDrawable, z), Shader.TileMode.CLAMP);
        paint.setShader(linearGradient);
        this.mShaderHashMap.put(gradientDrawable.getClass().getName(), new ViewState(linearGradient, getMeasuredWidth()));
    }

    @Override // android.view.View
    public void setPressed(boolean z) {
        boolean isPressed = isPressed();
        super.setPressed(z);
        if (isPressed != z) {
            resetColorState();
        }
    }

    @Override // android.widget.TextView, android.view.View
    public void setSelected(boolean z) {
        boolean isSelected = isSelected();
        super.setSelected(z);
        if (isSelected != z) {
            resetColorState();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: com.tomatolive.library.ui.view.widget.drawabletext.DrawableTextView$ViewState */
    /* loaded from: classes4.dex */
    public final class ViewState {
        int initWidth;
        Shader shader;

        public Shader getShader(int i) {
            if (i != this.initWidth) {
                return null;
            }
            return this.shader;
        }

        public ViewState(Shader shader, int i) {
            this.shader = shader;
            this.initWidth = i;
        }
    }
}
