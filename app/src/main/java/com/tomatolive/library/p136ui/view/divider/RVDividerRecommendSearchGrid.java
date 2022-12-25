package com.tomatolive.library.p136ui.view.divider;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.p002v4.content.ContextCompat;
import com.tomatolive.library.p136ui.view.divider.decoration.Y_Divider;
import com.tomatolive.library.p136ui.view.divider.decoration.Y_DividerBuilder;
import com.tomatolive.library.p136ui.view.divider.decoration.Y_DividerItemDecoration;

/* renamed from: com.tomatolive.library.ui.view.divider.RVDividerRecommendSearchGrid */
/* loaded from: classes3.dex */
public class RVDividerRecommendSearchGrid extends Y_DividerItemDecoration {
    private int colorRes;
    private Context context;
    private final float widthDp = 10.0f;

    public RVDividerRecommendSearchGrid(Context context, @ColorRes int i) {
        super(context);
        this.context = context;
        this.colorRes = i;
    }

    @Override // com.tomatolive.library.p136ui.view.divider.decoration.Y_DividerItemDecoration
    public Y_Divider getDivider(int i) {
        int i2 = i % 3;
        if (i2 != 0) {
            if (i2 == 1) {
                return new Y_DividerBuilder().setRightSideLine(true, ContextCompat.getColor(this.context, this.colorRes), 10.0f, 0.0f, 0.0f).setBottomSideLine(true, ContextCompat.getColor(this.context, this.colorRes), 10.0f, 0.0f, 0.0f).create();
            }
            if (i2 == 2) {
                return new Y_DividerBuilder().setBottomSideLine(true, ContextCompat.getColor(this.context, this.colorRes), 10.0f, 0.0f, 0.0f).create();
            }
            return null;
        }
        return new Y_DividerBuilder().setRightSideLine(true, ContextCompat.getColor(this.context, this.colorRes), 10.0f, 0.0f, 0.0f).setBottomSideLine(true, ContextCompat.getColor(this.context, this.colorRes), 10.0f, 0.0f, 0.0f).create();
    }
}
