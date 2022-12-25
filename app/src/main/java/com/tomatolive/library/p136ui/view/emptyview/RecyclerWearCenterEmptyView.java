package com.tomatolive.library.p136ui.view.emptyview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.p002v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;

/* renamed from: com.tomatolive.library.ui.view.emptyview.RecyclerWearCenterEmptyView */
/* loaded from: classes3.dex */
public class RecyclerWearCenterEmptyView extends LinearLayout {
    private int emptyType;
    private Context mContext;
    private TextView tvText;

    public RecyclerWearCenterEmptyView(Context context) {
        this(context, null, 0);
    }

    public RecyclerWearCenterEmptyView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0);
    }

    public RecyclerWearCenterEmptyView(Context context, int i) {
        this(context, null, i);
    }

    public RecyclerWearCenterEmptyView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, 0, i);
    }

    public RecyclerWearCenterEmptyView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i);
        this.mContext = context;
        this.emptyType = i2;
        initView();
    }

    private void initView() {
        LinearLayout.inflate(getContext(), R$layout.fq_achieve_layout_empty_view_wear_center, this);
        this.tvText = (TextView) findViewById(R$id.tv_empty_text);
        this.tvText.setText(getEmptyText());
        this.tvText.setTextColor(ContextCompat.getColor(this.mContext, R$color.fq_achieve_gray_878787));
        setDrawableTop();
    }

    private String getEmptyText() {
        int i = this.emptyType;
        if (i != 47) {
            return i != 48 ? "" : this.mContext.getString(R$string.fq_achieve_no_speak_prefix);
        }
        return this.mContext.getString(R$string.fq_achieve_no_speak_medal);
    }

    private void setDrawableTop() {
        Drawable drawable;
        int i = this.emptyType;
        if (i == 47) {
            drawable = ContextCompat.getDrawable(this.mContext, R$drawable.fq_ic_achieve_empty_speak_icon);
        } else {
            drawable = i != 48 ? null : ContextCompat.getDrawable(this.mContext, R$drawable.fq_ic_achieve_empty_speak_prefix);
        }
        this.tvText.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, drawable, (Drawable) null, (Drawable) null);
    }
}
