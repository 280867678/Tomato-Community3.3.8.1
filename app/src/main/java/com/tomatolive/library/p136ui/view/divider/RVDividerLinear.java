package com.tomatolive.library.p136ui.view.divider;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.p002v4.content.ContextCompat;
import com.tomatolive.library.p136ui.view.divider.decoration.Y_Divider;
import com.tomatolive.library.p136ui.view.divider.decoration.Y_DividerBuilder;
import com.tomatolive.library.p136ui.view.divider.decoration.Y_DividerItemDecoration;

/* renamed from: com.tomatolive.library.ui.view.divider.RVDividerLinear */
/* loaded from: classes3.dex */
public class RVDividerLinear extends Y_DividerItemDecoration {
    private int colorRes;
    private Context context;
    private float widthDp;

    private float getPaddingWidthDp() {
        return 0.0f;
    }

    public RVDividerLinear(Context context, @ColorRes int i) {
        super(context);
        this.widthDp = 0.8f;
        this.context = context;
        this.colorRes = i;
    }

    public RVDividerLinear(Context context, @ColorRes int i, float f) {
        super(context);
        this.widthDp = 0.8f;
        this.context = context;
        this.colorRes = i;
        this.widthDp = f;
    }

    @Override // com.tomatolive.library.p136ui.view.divider.decoration.Y_DividerItemDecoration
    public Y_Divider getDivider(int i) {
        if (i == 0) {
            return new Y_DividerBuilder().create();
        }
        if (i == 1) {
            return new Y_DividerBuilder().setTopSideLine(true, ContextCompat.getColor(this.context, this.colorRes), this.widthDp, getPaddingWidthDp(), getPaddingWidthDp()).setBottomSideLine(true, ContextCompat.getColor(this.context, this.colorRes), this.widthDp, getPaddingWidthDp(), getPaddingWidthDp()).create();
        }
        return new Y_DividerBuilder().setBottomSideLine(true, ContextCompat.getColor(this.context, this.colorRes), this.widthDp, getPaddingWidthDp(), getPaddingWidthDp()).create();
    }
}
