package com.tomatolive.library.p136ui.view.divider;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.p002v4.content.ContextCompat;
import com.tomatolive.library.p136ui.view.divider.decoration.Y_Divider;
import com.tomatolive.library.p136ui.view.divider.decoration.Y_DividerBuilder;
import com.tomatolive.library.p136ui.view.divider.decoration.Y_DividerItemDecoration;

/* renamed from: com.tomatolive.library.ui.view.divider.RVDividerCarMall */
/* loaded from: classes3.dex */
public class RVDividerCarMall extends Y_DividerItemDecoration {
    private int colorRes;
    private Context context;
    private boolean isHeadView;
    private final float widthDp;

    public RVDividerCarMall(Context context, @ColorRes int i) {
        super(context);
        this.isHeadView = false;
        this.widthDp = 10.0f;
        this.context = context;
        this.colorRes = i;
    }

    public RVDividerCarMall(Context context, @ColorRes int i, boolean z) {
        super(context);
        this.isHeadView = false;
        this.widthDp = 10.0f;
        this.context = context;
        this.colorRes = i;
        this.isHeadView = z;
    }

    @Override // com.tomatolive.library.p136ui.view.divider.decoration.Y_DividerItemDecoration
    public Y_Divider getDivider(int i) {
        if (!this.isHeadView) {
            int i2 = i % 2;
            if (i2 == 0) {
                return new Y_DividerBuilder().setRightSideLine(true, ContextCompat.getColor(this.context, this.colorRes), 5.0f, 0.0f, 0.0f).setLeftSideLine(true, ContextCompat.getColor(this.context, this.colorRes), 10.0f, 0.0f, 0.0f).setBottomSideLine(true, ContextCompat.getColor(this.context, this.colorRes), 10.0f, 0.0f, 0.0f).setTopSideLine(true, ContextCompat.getColor(this.context, this.colorRes), getTopDp(i), 0.0f, 0.0f).create();
            }
            if (i2 == 1) {
                return new Y_DividerBuilder().setLeftSideLine(true, ContextCompat.getColor(this.context, this.colorRes), 5.0f, 0.0f, 0.0f).setRightSideLine(true, ContextCompat.getColor(this.context, this.colorRes), 10.0f, 0.0f, 0.0f).setBottomSideLine(true, ContextCompat.getColor(this.context, this.colorRes), 10.0f, 0.0f, 0.0f).setTopSideLine(true, ContextCompat.getColor(this.context, this.colorRes), getTopDp(i), 0.0f, 0.0f).create();
            }
        } else if (i == 0) {
            return new Y_DividerBuilder().setRightSideLine(true, ContextCompat.getColor(this.context, this.colorRes), 0.0f, 0.0f, 0.0f).setLeftSideLine(true, ContextCompat.getColor(this.context, this.colorRes), 0.0f, 0.0f, 0.0f).setTopSideLine(true, ContextCompat.getColor(this.context, this.colorRes), 10.0f, 0.0f, 0.0f).setBottomSideLine(false, ContextCompat.getColor(this.context, this.colorRes), 0.0f, 0.0f, 0.0f).create();
        } else {
            int i3 = i % 2;
            if (i3 == 0) {
                return new Y_DividerBuilder().setLeftSideLine(true, ContextCompat.getColor(this.context, this.colorRes), 5.0f, 0.0f, 0.0f).setRightSideLine(true, ContextCompat.getColor(this.context, this.colorRes), 10.0f, 0.0f, 0.0f).setBottomSideLine(true, ContextCompat.getColor(this.context, this.colorRes), 10.0f, 0.0f, 0.0f).setTopSideLine(true, ContextCompat.getColor(this.context, this.colorRes), getTopDp(i), 0.0f, 0.0f).create();
            }
            if (i3 == 1) {
                return new Y_DividerBuilder().setRightSideLine(true, ContextCompat.getColor(this.context, this.colorRes), 5.0f, 0.0f, 0.0f).setLeftSideLine(true, ContextCompat.getColor(this.context, this.colorRes), 10.0f, 0.0f, 0.0f).setBottomSideLine(true, ContextCompat.getColor(this.context, this.colorRes), 10.0f, 0.0f, 0.0f).setTopSideLine(true, ContextCompat.getColor(this.context, this.colorRes), getTopDp(i), 0.0f, 0.0f).create();
            }
        }
        return null;
    }

    private float getTopDp(int i) {
        return this.isHeadView ? (i == 1 || i == 2) ? 10.0f : 0.0f : (i == 0 || i == 1) ? 10.0f : 0.0f;
    }
}
