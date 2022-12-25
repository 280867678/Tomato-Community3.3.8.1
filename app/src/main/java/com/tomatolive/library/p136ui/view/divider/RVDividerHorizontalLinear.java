package com.tomatolive.library.p136ui.view.divider;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.p002v4.content.ContextCompat;
import com.tomatolive.library.p136ui.view.divider.decoration.Y_Divider;
import com.tomatolive.library.p136ui.view.divider.decoration.Y_DividerBuilder;
import com.tomatolive.library.p136ui.view.divider.decoration.Y_DividerItemDecoration;

/* renamed from: com.tomatolive.library.ui.view.divider.RVDividerHorizontalLinear */
/* loaded from: classes3.dex */
public class RVDividerHorizontalLinear extends Y_DividerItemDecoration {
    private int colorRes;
    private Context context;
    private boolean isFirstItemLeftSideLine;
    private float widthDp;

    private float getPaddingWidthDp() {
        return 0.0f;
    }

    public RVDividerHorizontalLinear(Context context, @ColorRes int i) {
        super(context);
        this.widthDp = 0.8f;
        this.isFirstItemLeftSideLine = true;
        this.context = context;
        this.colorRes = i;
    }

    public RVDividerHorizontalLinear(Context context, @ColorRes int i, float f) {
        super(context);
        this.widthDp = 0.8f;
        this.isFirstItemLeftSideLine = true;
        this.context = context;
        this.colorRes = i;
        this.widthDp = f;
    }

    public RVDividerHorizontalLinear(Context context, @ColorRes int i, float f, boolean z) {
        super(context);
        this.widthDp = 0.8f;
        this.isFirstItemLeftSideLine = true;
        this.context = context;
        this.colorRes = i;
        this.widthDp = f;
        this.isFirstItemLeftSideLine = z;
    }

    @Override // com.tomatolive.library.p136ui.view.divider.decoration.Y_DividerItemDecoration
    public Y_Divider getDivider(int i) {
        if (i != 0 || !this.isFirstItemLeftSideLine) {
            if (i == 1) {
                return new Y_DividerBuilder().setLeftSideLine(true, ContextCompat.getColor(this.context, this.colorRes), this.widthDp, getPaddingWidthDp(), getPaddingWidthDp()).setRightSideLine(true, ContextCompat.getColor(this.context, this.colorRes), this.widthDp, getPaddingWidthDp(), getPaddingWidthDp()).create();
            }
            return new Y_DividerBuilder().setRightSideLine(true, ContextCompat.getColor(this.context, this.colorRes), this.widthDp, getPaddingWidthDp(), getPaddingWidthDp()).create();
        }
        return new Y_DividerBuilder().setLeftSideLine(true, ContextCompat.getColor(this.context, this.colorRes), this.widthDp, getPaddingWidthDp(), getPaddingWidthDp()).create();
    }
}
