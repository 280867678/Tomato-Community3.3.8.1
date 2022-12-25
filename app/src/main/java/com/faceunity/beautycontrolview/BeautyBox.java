package com.faceunity.beautycontrolview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/* loaded from: classes2.dex */
public class BeautyBox extends LinearLayout implements Checkable {
    private ImageView boxImg;
    private TextView boxText;
    private int checkedModel;
    private Drawable drawableChecked;
    private Drawable drawableNormal;
    private boolean isSelect;
    private boolean mBroadcasting;
    private boolean mChecked;
    private OnCheckedChangeListener mOnCheckedChangeListener;
    private int textCheckedColor;
    private String textCheckedStr;
    private int textNormalColor;
    private String textNormalStr;

    /* loaded from: classes2.dex */
    public interface OnCheckedChangeListener {
        void onCheckedChanged(BeautyBox beautyBox, boolean z);
    }

    public BeautyBox(Context context) {
        this(context, null);
    }

    public BeautyBox(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public BeautyBox(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        LayoutInflater.from(context).inflate(R$layout.layout_beauty_box, this);
        this.boxImg = (ImageView) findViewById(R$id.beauty_box_img);
        this.boxText = (TextView) findViewById(R$id.beauty_box_text);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.BeautyBox, i, 0);
        this.drawableNormal = obtainStyledAttributes.getDrawable(R$styleable.BeautyBox_drawable_normal);
        this.drawableChecked = obtainStyledAttributes.getDrawable(R$styleable.BeautyBox_drawable_checked);
        this.textNormalStr = obtainStyledAttributes.getString(R$styleable.BeautyBox_text_normal);
        this.textCheckedStr = obtainStyledAttributes.getString(R$styleable.BeautyBox_text_checked);
        if (TextUtils.isEmpty(this.textCheckedStr)) {
            this.textCheckedStr = this.textNormalStr;
        }
        this.textNormalColor = obtainStyledAttributes.getColor(R$styleable.BeautyBox_textColor_normal, getResources().getColor(R$color.main_color_c5c5c5));
        this.textCheckedColor = obtainStyledAttributes.getColor(R$styleable.BeautyBox_textColor_checked, getResources().getColor(R$color.main_color));
        boolean z = obtainStyledAttributes.getBoolean(R$styleable.BeautyBox_checked, false);
        this.checkedModel = obtainStyledAttributes.getInt(R$styleable.BeautyBox_checked_model, 1);
        this.boxText.setText(this.textNormalStr);
        this.boxText.setTextColor(getResources().getColor(R$color.main_color_c5c5c5));
        this.boxImg.setImageDrawable(this.drawableNormal);
        setChecked(z);
        obtainStyledAttributes.recycle();
        setOnClickListener(new View.OnClickListener(this) { // from class: com.faceunity.beautycontrolview.BeautyBox.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
            }
        });
    }

    @Override // android.view.View
    public boolean performClick() {
        toggle();
        boolean performClick = super.performClick();
        if (!performClick) {
            playSoundEffect(0);
        }
        return performClick;
    }

    @Override // android.widget.Checkable
    public void setChecked(boolean z) {
        this.mChecked = z;
        this.boxImg.setImageDrawable(this.mChecked ? this.drawableChecked : this.drawableNormal);
        this.boxText.setText(this.mChecked ? this.textCheckedStr : this.textNormalStr);
        this.boxText.setTextColor(this.mChecked ? this.textCheckedColor : this.textNormalColor);
        if (this.mBroadcasting) {
            return;
        }
        this.mBroadcasting = true;
        OnCheckedChangeListener onCheckedChangeListener = this.mOnCheckedChangeListener;
        if (onCheckedChangeListener != null) {
            onCheckedChangeListener.onCheckedChanged(this, this.mChecked);
        }
        this.mBroadcasting = false;
    }

    @Override // android.widget.Checkable
    public boolean isChecked() {
        return this.mChecked;
    }

    @Override // android.widget.Checkable
    public void toggle() {
        boolean z;
        int i = this.checkedModel;
        boolean z2 = true;
        if (i == 1) {
            if (!this.isSelect && (z = this.mChecked)) {
                z2 = z;
            } else if (this.mChecked) {
                z2 = false;
            }
            setChecked(z2);
        } else if (i == 2) {
            setChecked(!this.mChecked);
        } else if (i != 3) {
        } else {
            setChecked(this.mChecked);
        }
    }

    public void setSelect(boolean z) {
        this.isSelect = z;
    }

    public void setBackgroundImg(int i) {
        this.boxImg.setBackgroundResource(i);
    }

    public void clearBackgroundImg() {
        this.boxImg.setBackground(null);
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.mOnCheckedChangeListener = onCheckedChangeListener;
    }
}
