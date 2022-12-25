package com.one.tomato.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.SpannableString;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.R$styleable;
import com.one.tomato.utils.DisplayMetricsUtils;

/* loaded from: classes3.dex */
public class MineCustomItemLayout extends LinearLayout {

    /* renamed from: dm */
    private DisplayMetrics f1761dm;
    private ImageView leftImg;
    LinearLayout linearLayout;
    private TextView mine_left_name;
    private TextView mine_right_name;
    private ImageView mine_right_sub_img;
    private TextView mine_right_sub_name;
    View read_point;
    RelativeLayout relativeLayout;
    private ImageView rightImg;
    View viewLine;

    public MineCustomItemLayout(Context context) {
        this(context, null);
    }

    public MineCustomItemLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MineCustomItemLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        int i2;
        int i3;
        this.f1761dm = getResources().getDisplayMetrics();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.MineCustomLayout, i, 0);
        int resourceId = obtainStyledAttributes.getResourceId(12, getResources().getColor(R.color.white));
        int resourceId2 = obtainStyledAttributes.getResourceId(0, 0);
        String string = obtainStyledAttributes.getString(1);
        String string2 = obtainStyledAttributes.getString(9);
        int color = obtainStyledAttributes.getColor(10, getResources().getColor(R.color.text_middle));
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(11, (int) TypedValue.applyDimension(2, 14.0f, this.f1761dm));
        String string3 = obtainStyledAttributes.getString(6);
        int color2 = obtainStyledAttributes.getColor(7, getResources().getColor(R.color.text_middle));
        int dimensionPixelSize2 = obtainStyledAttributes.getDimensionPixelSize(8, (int) TypedValue.applyDimension(2, 14.0f, this.f1761dm));
        int resourceId3 = obtainStyledAttributes.getResourceId(4, R.drawable.icon_arrow_right_grey);
        int resourceId4 = obtainStyledAttributes.getResourceId(5, 0);
        int color3 = obtainStyledAttributes.getColor(3, getResources().getColor(R.color.text_middle));
        int dimensionPixelSize3 = obtainStyledAttributes.getDimensionPixelSize(2, (int) TypedValue.applyDimension(2, 16.0f, this.f1761dm));
        boolean z = obtainStyledAttributes.getBoolean(13, false);
        boolean z2 = obtainStyledAttributes.getBoolean(18, false);
        boolean z3 = obtainStyledAttributes.getBoolean(17, false);
        boolean z4 = obtainStyledAttributes.getBoolean(16, false);
        boolean z5 = obtainStyledAttributes.getBoolean(15, false);
        boolean z6 = obtainStyledAttributes.getBoolean(14, false);
        setClickable(true);
        View inflate = LayoutInflater.from(context).inflate(R.layout.layout_custom_item, (ViewGroup) this, true);
        this.linearLayout = (LinearLayout) inflate.findViewById(R.id.mine_linear);
        this.relativeLayout = (RelativeLayout) inflate.findViewById(R.id.mine_relative);
        this.leftImg = (ImageView) inflate.findViewById(R.id.mine_left_image);
        this.mine_left_name = (TextView) inflate.findViewById(R.id.mine_left_name);
        this.mine_right_name = (TextView) inflate.findViewById(R.id.mine_right_name);
        this.mine_right_sub_name = (TextView) inflate.findViewById(R.id.mine_right_sub_name);
        this.mine_right_sub_img = (ImageView) inflate.findViewById(R.id.mine_right_sub_img);
        this.rightImg = (ImageView) inflate.findViewById(R.id.mine_right_iv);
        this.viewLine = inflate.findViewById(R.id.mine_line_view);
        this.read_point = inflate.findViewById(R.id.rad_point);
        this.relativeLayout.setBackgroundColor(resourceId);
        this.leftImg.setImageResource(resourceId2);
        this.mine_left_name.setText(string);
        this.mine_left_name.setTextColor(color3);
        this.mine_left_name.setTextSize(0, dimensionPixelSize3);
        this.mine_right_name.setText(string2);
        this.mine_right_name.setTextColor(color);
        this.mine_right_name.setTextSize(0, dimensionPixelSize);
        this.mine_right_sub_name.setText(string3);
        this.mine_right_sub_name.setTextColor(color2);
        this.mine_right_sub_name.setTextSize(0, dimensionPixelSize2);
        this.mine_right_sub_img.setImageResource(resourceId4);
        this.rightImg.setImageResource(resourceId3);
        this.mine_right_sub_name.setMaxWidth(((int) DisplayMetricsUtils.getWidth()) / 2);
        if (z) {
            i2 = 0;
            this.leftImg.setVisibility(0);
            i3 = 8;
        } else {
            i2 = 0;
            i3 = 8;
            this.leftImg.setVisibility(8);
        }
        if (z5) {
            this.rightImg.setVisibility(i2);
        } else {
            this.rightImg.setVisibility(i3);
        }
        if (z2) {
            this.mine_right_name.setVisibility(i2);
        } else {
            this.mine_right_name.setVisibility(i3);
        }
        if (z3) {
            this.mine_right_sub_name.setVisibility(i2);
        } else {
            this.mine_right_sub_name.setVisibility(i3);
        }
        if (z4) {
            this.mine_right_sub_img.setVisibility(i2);
        } else {
            this.mine_right_sub_img.setVisibility(i3);
        }
        if (z5) {
            this.rightImg.setVisibility(i2);
        } else {
            this.rightImg.setVisibility(i3);
        }
        if (z6) {
            this.viewLine.setVisibility(i2);
        } else {
            this.viewLine.setVisibility(i3);
        }
        obtainStyledAttributes.recycle();
    }

    public void setHeight(int i) {
        this.linearLayout.setLayoutParams(new FrameLayout.LayoutParams(-1, i));
        this.relativeLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, i - 2));
        requestLayout();
    }

    public void setRootBackgroundRes(int i) {
        this.relativeLayout.setBackgroundResource(i);
    }

    public void setRootBackgroundColor(int i) {
        this.relativeLayout.setBackgroundColor(getResources().getColor(i));
    }

    public void setLeftText(SpannableString spannableString) {
        this.mine_left_name.setText(spannableString);
    }

    public void setLeftText(String str) {
        this.mine_left_name.setText(str);
    }

    public void setRightText(String str) {
        this.mine_right_name.setText(str);
    }

    public void setRightTextColor(int i) {
        this.mine_right_name.setTextColor(i);
    }

    public void setRightSubText(String str) {
        this.mine_right_sub_name.setText(str);
    }

    public TextView getRightSubText() {
        return this.mine_right_sub_name;
    }

    public void setRightSubTextColor(int i) {
        this.mine_right_sub_name.setTextColor(i);
    }

    public ImageView getRightSubImg() {
        return this.mine_right_sub_img;
    }

    public ImageView getRightImg() {
        this.mine_right_sub_img.setVisibility(0);
        return this.mine_right_sub_img;
    }

    public View getRightRedPoint() {
        this.read_point.setVisibility(0);
        return this.read_point;
    }
}
