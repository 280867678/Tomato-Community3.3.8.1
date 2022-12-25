package com.tomatolive.library.p136ui.view.divider;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.p002v4.content.ContextCompat;
import com.tomatolive.library.p136ui.view.divider.decoration.Y_Divider;
import com.tomatolive.library.p136ui.view.divider.decoration.Y_DividerBuilder;
import com.tomatolive.library.p136ui.view.divider.decoration.Y_DividerItemDecoration;

/* renamed from: com.tomatolive.library.ui.view.divider.RVDividerGiftAdapter */
/* loaded from: classes3.dex */
public class RVDividerGiftAdapter extends Y_DividerItemDecoration {
    private int colorRes;
    private Context context;
    private boolean isAllLine;
    private float widthDp;

    public RVDividerGiftAdapter(Context context, @ColorRes int i) {
        this(context, i, false);
    }

    public RVDividerGiftAdapter(Context context, @ColorRes int i, boolean z) {
        super(context);
        this.widthDp = 0.8f;
        this.isAllLine = false;
        this.context = context;
        this.colorRes = i;
        this.isAllLine = z;
    }

    @Override // com.tomatolive.library.p136ui.view.divider.decoration.Y_DividerItemDecoration
    public Y_Divider getDivider(int i) {
        if (!this.isAllLine) {
            if (i == 0 || i == 1 || i == 2 || i == 3) {
                if (i == 0) {
                    return new Y_DividerBuilder().setTopSideLine(true, ContextCompat.getColor(this.context, this.colorRes), this.widthDp, 0.0f, 0.0f).setBottomSideLine(true, ContextCompat.getColor(this.context, this.colorRes), this.widthDp, 0.0f, 0.0f).create();
                }
                return new Y_DividerBuilder().setTopSideLine(true, ContextCompat.getColor(this.context, this.colorRes), this.widthDp, 0.0f, 0.0f).setLeftSideLine(true, ContextCompat.getColor(this.context, this.colorRes), this.widthDp, 0.0f, 0.0f).setBottomSideLine(true, ContextCompat.getColor(this.context, this.colorRes), this.widthDp, 0.0f, 0.0f).create();
            }
            int i2 = i % 4;
            if (i2 == 0) {
                return new Y_DividerBuilder().setBottomSideLine(true, ContextCompat.getColor(this.context, this.colorRes), this.widthDp, 0.0f, 0.0f).create();
            }
            if (i2 != 1 && i2 != 2 && i2 != 3) {
                return null;
            }
            return new Y_DividerBuilder().setLeftSideLine(true, ContextCompat.getColor(this.context, this.colorRes), this.widthDp, 0.0f, 0.0f).setBottomSideLine(true, ContextCompat.getColor(this.context, this.colorRes), this.widthDp, 0.0f, 0.0f).create();
        } else if (i == 0 || i == 1 || i == 2 || i == 3) {
            if (i == 0) {
                return new Y_DividerBuilder().setLeftSideLine(true, ContextCompat.getColor(this.context, this.colorRes), this.widthDp, 0.0f, 0.0f).setTopSideLine(true, ContextCompat.getColor(this.context, this.colorRes), this.widthDp, 0.0f, 0.0f).setBottomSideLine(true, ContextCompat.getColor(this.context, this.colorRes), this.widthDp, 0.0f, 0.0f).create();
            }
            if (i == 3) {
                return new Y_DividerBuilder().setTopSideLine(true, ContextCompat.getColor(this.context, this.colorRes), this.widthDp, 0.0f, 0.0f).setLeftSideLine(true, ContextCompat.getColor(this.context, this.colorRes), this.widthDp, 0.0f, 0.0f).setBottomSideLine(true, ContextCompat.getColor(this.context, this.colorRes), this.widthDp, 0.0f, 0.0f).setRightSideLine(true, ContextCompat.getColor(this.context, this.colorRes), this.widthDp, 0.0f, 0.0f).create();
            }
            return new Y_DividerBuilder().setTopSideLine(true, ContextCompat.getColor(this.context, this.colorRes), this.widthDp, 0.0f, 0.0f).setLeftSideLine(true, ContextCompat.getColor(this.context, this.colorRes), this.widthDp, 0.0f, 0.0f).setBottomSideLine(true, ContextCompat.getColor(this.context, this.colorRes), this.widthDp, 0.0f, 0.0f).create();
        } else {
            int i3 = i % 4;
            if (i3 != 0) {
                if (i3 != 1) {
                    if (i3 == 2) {
                        return new Y_DividerBuilder().setLeftSideLine(true, ContextCompat.getColor(this.context, this.colorRes), 0.0f, 0.0f, 0.0f).setRightSideLine(true, ContextCompat.getColor(this.context, this.colorRes), 0.0f, 0.0f, 0.0f).setBottomSideLine(true, ContextCompat.getColor(this.context, this.colorRes), this.widthDp, 0.0f, 0.0f).create();
                    }
                    if (i3 != 3) {
                        return null;
                    }
                }
                return new Y_DividerBuilder().setLeftSideLine(true, ContextCompat.getColor(this.context, this.colorRes), this.widthDp, 0.0f, 0.0f).setRightSideLine(true, ContextCompat.getColor(this.context, this.colorRes), this.widthDp, 0.0f, 0.0f).setBottomSideLine(true, ContextCompat.getColor(this.context, this.colorRes), this.widthDp, 0.0f, 0.0f).create();
            }
            return new Y_DividerBuilder().setLeftSideLine(true, ContextCompat.getColor(this.context, this.colorRes), this.widthDp, 0.0f, 0.0f).setBottomSideLine(true, ContextCompat.getColor(this.context, this.colorRes), this.widthDp, 0.0f, 0.0f).create();
        }
    }
}
