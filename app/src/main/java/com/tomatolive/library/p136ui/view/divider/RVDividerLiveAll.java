package com.tomatolive.library.p136ui.view.divider;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.p002v4.content.ContextCompat;
import com.tomatolive.library.p136ui.view.divider.decoration.Y_Divider;
import com.tomatolive.library.p136ui.view.divider.decoration.Y_DividerBuilder;
import com.tomatolive.library.p136ui.view.divider.decoration.Y_DividerItemDecoration;

/* renamed from: com.tomatolive.library.ui.view.divider.RVDividerLiveAll */
/* loaded from: classes3.dex */
public class RVDividerLiveAll extends Y_DividerItemDecoration {
    private int bannerSpanPosition;
    private int colorRes;
    private Context context;
    private boolean isHasBanner;
    private boolean isHeadView;
    private boolean isHeadViewWidth;
    private final float widthDp;

    public RVDividerLiveAll(Context context, @ColorRes int i) {
        super(context);
        this.isHeadView = false;
        this.isHeadViewWidth = true;
        this.widthDp = 10.0f;
        this.isHasBanner = false;
        this.context = context;
        this.colorRes = i;
    }

    public RVDividerLiveAll(Context context, @ColorRes int i, boolean z, int i2) {
        super(context);
        this.isHeadView = false;
        this.isHeadViewWidth = true;
        this.widthDp = 10.0f;
        this.isHasBanner = false;
        this.context = context;
        this.colorRes = i;
        this.bannerSpanPosition = i2;
        this.isHasBanner = z;
    }

    @Override // com.tomatolive.library.p136ui.view.divider.decoration.Y_DividerItemDecoration
    public Y_Divider getDivider(int i) {
        if (this.isHeadView) {
            if (i == 0) {
                return new Y_DividerBuilder().setRightSideLine(true, ContextCompat.getColor(this.context, this.colorRes), getHeadViewWidthDp(), 0.0f, 0.0f).setLeftSideLine(true, ContextCompat.getColor(this.context, this.colorRes), getHeadViewWidthDp(), 0.0f, 0.0f).setTopSideLine(true, ContextCompat.getColor(this.context, this.colorRes), getHeadViewWidthDp(), 0.0f, 0.0f).setBottomSideLine(false, ContextCompat.getColor(this.context, this.colorRes), 0.0f, 0.0f, 0.0f).create();
            }
            int i2 = i % 2;
            if (i2 == 0) {
                return new Y_DividerBuilder().setLeftSideLine(true, ContextCompat.getColor(this.context, this.colorRes), 5.0f, 0.0f, 0.0f).setRightSideLine(true, ContextCompat.getColor(this.context, this.colorRes), 10.0f, 0.0f, 0.0f).setBottomSideLine(true, ContextCompat.getColor(this.context, this.colorRes), 10.0f, 0.0f, 0.0f).setTopSideLine(true, ContextCompat.getColor(this.context, this.colorRes), getTopDp(i), 0.0f, 0.0f).create();
            }
            if (i2 == 1) {
                return new Y_DividerBuilder().setRightSideLine(true, ContextCompat.getColor(this.context, this.colorRes), 5.0f, 0.0f, 0.0f).setLeftSideLine(true, ContextCompat.getColor(this.context, this.colorRes), 10.0f, 0.0f, 0.0f).setBottomSideLine(true, ContextCompat.getColor(this.context, this.colorRes), 10.0f, 0.0f, 0.0f).setTopSideLine(true, ContextCompat.getColor(this.context, this.colorRes), getTopDp(i), 0.0f, 0.0f).create();
            }
        } else if (!this.isHasBanner) {
            int i3 = i % 2;
            if (i3 == 0) {
                return new Y_DividerBuilder().setRightSideLine(true, ContextCompat.getColor(this.context, this.colorRes), 5.0f, 0.0f, 0.0f).setLeftSideLine(true, ContextCompat.getColor(this.context, this.colorRes), 10.0f, 0.0f, 0.0f).setBottomSideLine(true, ContextCompat.getColor(this.context, this.colorRes), 10.0f, 0.0f, 0.0f).setTopSideLine(true, ContextCompat.getColor(this.context, this.colorRes), getTopDp(i), 0.0f, 0.0f).create();
            }
            if (i3 == 1) {
                return new Y_DividerBuilder().setLeftSideLine(true, ContextCompat.getColor(this.context, this.colorRes), 5.0f, 0.0f, 0.0f).setRightSideLine(true, ContextCompat.getColor(this.context, this.colorRes), 10.0f, 0.0f, 0.0f).setBottomSideLine(true, ContextCompat.getColor(this.context, this.colorRes), 10.0f, 0.0f, 0.0f).setTopSideLine(true, ContextCompat.getColor(this.context, this.colorRes), getTopDp(i), 0.0f, 0.0f).create();
            }
        } else {
            int i4 = this.bannerSpanPosition;
            if (i < i4) {
                int i5 = i % 2;
                if (i5 == 0) {
                    return new Y_DividerBuilder().setRightSideLine(true, ContextCompat.getColor(this.context, this.colorRes), 5.0f, 0.0f, 0.0f).setLeftSideLine(true, ContextCompat.getColor(this.context, this.colorRes), 10.0f, 0.0f, 0.0f).setBottomSideLine(true, ContextCompat.getColor(this.context, this.colorRes), 10.0f, 0.0f, 0.0f).setTopSideLine(true, ContextCompat.getColor(this.context, this.colorRes), getTopDp(i), 0.0f, 0.0f).create();
                }
                if (i5 == 1) {
                    return new Y_DividerBuilder().setLeftSideLine(true, ContextCompat.getColor(this.context, this.colorRes), 5.0f, 0.0f, 0.0f).setRightSideLine(true, ContextCompat.getColor(this.context, this.colorRes), 10.0f, 0.0f, 0.0f).setBottomSideLine(true, ContextCompat.getColor(this.context, this.colorRes), 10.0f, 0.0f, 0.0f).setTopSideLine(true, ContextCompat.getColor(this.context, this.colorRes), getTopDp(i), 0.0f, 0.0f).create();
                }
            } else if (i > i4) {
                int i6 = i % 2;
                if (i6 == 0) {
                    return new Y_DividerBuilder().setLeftSideLine(true, ContextCompat.getColor(this.context, this.colorRes), 5.0f, 0.0f, 0.0f).setRightSideLine(true, ContextCompat.getColor(this.context, this.colorRes), 10.0f, 0.0f, 0.0f).setBottomSideLine(true, ContextCompat.getColor(this.context, this.colorRes), 10.0f, 0.0f, 0.0f).setTopSideLine(true, ContextCompat.getColor(this.context, this.colorRes), getTopDp(i), 0.0f, 0.0f).create();
                }
                if (i6 == 1) {
                    return new Y_DividerBuilder().setRightSideLine(true, ContextCompat.getColor(this.context, this.colorRes), 5.0f, 0.0f, 0.0f).setLeftSideLine(true, ContextCompat.getColor(this.context, this.colorRes), 10.0f, 0.0f, 0.0f).setBottomSideLine(true, ContextCompat.getColor(this.context, this.colorRes), 10.0f, 0.0f, 0.0f).setTopSideLine(true, ContextCompat.getColor(this.context, this.colorRes), getTopDp(i), 0.0f, 0.0f).create();
                }
            } else {
                return new Y_DividerBuilder().setRightSideLine(true, ContextCompat.getColor(this.context, this.colorRes), 10.0f, 0.0f, 0.0f).setLeftSideLine(true, ContextCompat.getColor(this.context, this.colorRes), 10.0f, 0.0f, 0.0f).setBottomSideLine(true, ContextCompat.getColor(this.context, this.colorRes), 10.0f, 0.0f, 0.0f).setTopSideLine(true, ContextCompat.getColor(this.context, this.colorRes), getTopDp(i), 0.0f, 0.0f).create();
            }
        }
        return null;
    }

    private float getTopDp(int i) {
        return this.isHeadView ? (i == 1 || i == 2) ? 10.0f : 0.0f : (i == 0 || i == 1) ? 10.0f : 0.0f;
    }

    private float getHeadViewWidthDp() {
        return this.isHeadViewWidth ? 10.0f : 0.0f;
    }

    public int getBannerSpanPosition() {
        return this.bannerSpanPosition;
    }

    public void setBannerSpanPosition(int i) {
        this.bannerSpanPosition = i;
    }

    public boolean isHasBanner() {
        return this.isHasBanner;
    }

    public void setHasBanner(boolean z) {
        this.isHasBanner = z;
    }
}
