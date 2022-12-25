package com.gen.p059mh.webapps.build.toolbar;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.p002v4.view.ViewCompat;
import android.text.InputFilter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.gen.p059mh.webapps.R$drawable;
import com.gen.p059mh.webapps.utils.Utils;

/* renamed from: com.gen.mh.webapps.build.toolbar.ToolBarImpl */
/* loaded from: classes2.dex */
public class ToolBarImpl implements ToolBar {
    private Context context;
    ImageView ivBack;
    LinearLayout linearLayout;
    TextView tvTitle;

    public ToolBarImpl(Context context) {
        this.context = context;
        this.linearLayout = new LinearLayout(context);
        this.linearLayout.setOrientation(1);
        start();
    }

    public void start() {
        View view = new View(this.context);
        view.setLayoutParams(new ViewGroup.LayoutParams(-1, Utils.getStatusBarHeight(this.context)));
        this.linearLayout.addView(view);
        RelativeLayout relativeLayout = new RelativeLayout(this.context);
        relativeLayout.setLayoutParams(new ViewGroup.LayoutParams(-1, (int) Utils.d2p(this.context, 46)));
        relativeLayout.setPadding(40, 0, 40, 0);
        this.linearLayout.addView(relativeLayout);
        this.ivBack = new ImageView(this.context);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(15);
        this.ivBack.setLayoutParams(layoutParams);
        this.ivBack.setImageResource(R$drawable.webapp_toolbar_back_black);
        relativeLayout.addView(this.ivBack);
        this.tvTitle = new TextView(this.context);
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams2.addRule(13);
        this.tvTitle.setLayoutParams(layoutParams2);
        this.tvTitle.getPaint().setTypeface(Typeface.DEFAULT_BOLD);
        this.tvTitle.setFilters(new InputFilter[]{new InputFilter.LengthFilter(12)});
        this.tvTitle.setTextColor(ViewCompat.MEASURED_STATE_MASK);
        this.tvTitle.setTextSize(14.0f);
        relativeLayout.addView(this.tvTitle);
        ImageView imageView = new ImageView(this.context);
        RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams3.addRule(11);
        layoutParams3.addRule(15);
        imageView.setLayoutParams(layoutParams3);
        relativeLayout.addView(imageView);
        imageView.setOnClickListener(new View.OnClickListener(this) { // from class: com.gen.mh.webapps.build.toolbar.ToolBarImpl.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
            }
        });
        imageView.setVisibility(4);
        View view2 = new View(this.context);
        view2.setLayoutParams(new ViewGroup.LayoutParams(-1, (int) Utils.d2p(this.context, 1)));
        view2.setBackgroundColor(Color.parseColor("#f1f1f1"));
        this.linearLayout.addView(view2);
    }

    @Override // com.gen.p059mh.webapps.build.toolbar.ToolBar
    public void setNavigationBarTitle(String str) {
        this.tvTitle.setText(str);
    }

    @Override // com.gen.p059mh.webapps.build.toolbar.ToolBar
    public View getView() {
        return this.linearLayout;
    }

    @Override // com.gen.p059mh.webapps.build.toolbar.ToolBar
    public void setNavigationBarColor(int i) {
        this.linearLayout.setBackgroundColor(i);
    }

    @Override // com.gen.p059mh.webapps.build.toolbar.ToolBar
    public void setTextColor(int i) {
        this.tvTitle.setTextColor(i);
    }

    @Override // com.gen.p059mh.webapps.build.toolbar.ToolBar
    public void hideHomeButton(boolean z) {
        this.ivBack.setVisibility(z ? 4 : 0);
    }

    @Override // com.gen.p059mh.webapps.build.toolbar.ToolBar
    public void setBackClickListener(View.OnClickListener onClickListener) {
        this.ivBack.setOnClickListener(onClickListener);
    }

    @Override // com.gen.p059mh.webapps.build.toolbar.ToolBar
    public void release() {
        if (this.context != null) {
            this.context = null;
        }
        LinearLayout linearLayout = this.linearLayout;
        if (linearLayout != null) {
            linearLayout.removeAllViews();
            this.linearLayout = null;
        }
        if (this.tvTitle != null) {
            this.tvTitle = null;
        }
        if (this.ivBack != null) {
            this.ivBack = null;
        }
    }
}
