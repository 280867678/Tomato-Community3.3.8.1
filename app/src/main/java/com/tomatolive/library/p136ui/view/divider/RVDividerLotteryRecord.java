package com.tomatolive.library.p136ui.view.divider;

import android.content.Context;
import android.support.p002v4.content.ContextCompat;
import com.tomatolive.library.R$color;
import com.tomatolive.library.p136ui.view.divider.decoration.Y_Divider;
import com.tomatolive.library.p136ui.view.divider.decoration.Y_DividerBuilder;
import com.tomatolive.library.p136ui.view.divider.decoration.Y_DividerItemDecoration;

/* renamed from: com.tomatolive.library.ui.view.divider.RVDividerLotteryRecord */
/* loaded from: classes3.dex */
public class RVDividerLotteryRecord extends Y_DividerItemDecoration {
    private Context context;
    private float widthDp = 15.0f;
    private int colorRes = R$color.fq_color_transparent;

    private float getPaddingWidthDp() {
        return 0.0f;
    }

    public RVDividerLotteryRecord(Context context) {
        super(context);
        this.context = context;
    }

    @Override // com.tomatolive.library.p136ui.view.divider.decoration.Y_DividerItemDecoration
    public Y_Divider getDivider(int i) {
        if (i == 0) {
            return new Y_DividerBuilder().setTopSideLine(true, ContextCompat.getColor(this.context, this.colorRes), 10.0f, getPaddingWidthDp(), getPaddingWidthDp()).setBottomSideLine(true, ContextCompat.getColor(this.context, this.colorRes), this.widthDp, getPaddingWidthDp(), getPaddingWidthDp()).create();
        }
        return new Y_DividerBuilder().setBottomSideLine(true, ContextCompat.getColor(this.context, this.colorRes), this.widthDp, getPaddingWidthDp(), getPaddingWidthDp()).create();
    }
}
