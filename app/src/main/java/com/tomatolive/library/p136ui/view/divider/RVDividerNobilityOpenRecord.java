package com.tomatolive.library.p136ui.view.divider;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.p002v4.content.ContextCompat;
import com.tomatolive.library.p136ui.view.divider.decoration.Y_Divider;
import com.tomatolive.library.p136ui.view.divider.decoration.Y_DividerBuilder;
import com.tomatolive.library.p136ui.view.divider.decoration.Y_DividerItemDecoration;

/* renamed from: com.tomatolive.library.ui.view.divider.RVDividerNobilityOpenRecord */
/* loaded from: classes3.dex */
public class RVDividerNobilityOpenRecord extends Y_DividerItemDecoration {
    private int colorRes;
    private Context context;
    private float widthDp = 15.0f;

    private float getPaddingWidthDp() {
        return 0.0f;
    }

    public RVDividerNobilityOpenRecord(Context context, @ColorRes int i) {
        super(context);
        this.context = context;
        this.colorRes = i;
    }

    @Override // com.tomatolive.library.p136ui.view.divider.decoration.Y_DividerItemDecoration
    public Y_Divider getDivider(int i) {
        return new Y_DividerBuilder().setTopSideLine(true, ContextCompat.getColor(this.context, this.colorRes), this.widthDp, getPaddingWidthDp(), getPaddingWidthDp()).create();
    }
}
