package com.tomatolive.library.p136ui.view.divider.decoration;

import android.support.annotation.ColorInt;

/* renamed from: com.tomatolive.library.ui.view.divider.decoration.Y_DividerBuilder */
/* loaded from: classes3.dex */
public class Y_DividerBuilder {
    private Y_SideLine bottomSideLine;
    private Y_SideLine leftSideLine;
    private Y_SideLine rightSideLine;
    private Y_SideLine topSideLine;

    public Y_DividerBuilder setLeftSideLine(boolean z, @ColorInt int i, float f, float f2, float f3) {
        this.leftSideLine = new Y_SideLine(z, i, f, f2, f3);
        return this;
    }

    public Y_DividerBuilder setTopSideLine(boolean z, @ColorInt int i, float f, float f2, float f3) {
        this.topSideLine = new Y_SideLine(z, i, f, f2, f3);
        return this;
    }

    public Y_DividerBuilder setRightSideLine(boolean z, @ColorInt int i, float f, float f2, float f3) {
        this.rightSideLine = new Y_SideLine(z, i, f, f2, f3);
        return this;
    }

    public Y_DividerBuilder setBottomSideLine(boolean z, @ColorInt int i, float f, float f2, float f3) {
        this.bottomSideLine = new Y_SideLine(z, i, f, f2, f3);
        return this;
    }

    public Y_Divider create() {
        Y_SideLine y_SideLine = new Y_SideLine(false, -10066330, 0.0f, 0.0f, 0.0f);
        Y_SideLine y_SideLine2 = this.leftSideLine;
        if (y_SideLine2 == null) {
            y_SideLine2 = y_SideLine;
        }
        this.leftSideLine = y_SideLine2;
        Y_SideLine y_SideLine3 = this.topSideLine;
        if (y_SideLine3 == null) {
            y_SideLine3 = y_SideLine;
        }
        this.topSideLine = y_SideLine3;
        Y_SideLine y_SideLine4 = this.rightSideLine;
        if (y_SideLine4 == null) {
            y_SideLine4 = y_SideLine;
        }
        this.rightSideLine = y_SideLine4;
        Y_SideLine y_SideLine5 = this.bottomSideLine;
        if (y_SideLine5 == null) {
            y_SideLine5 = y_SideLine;
        }
        this.bottomSideLine = y_SideLine5;
        return new Y_Divider(this.leftSideLine, this.topSideLine, this.rightSideLine, this.bottomSideLine);
    }
}
