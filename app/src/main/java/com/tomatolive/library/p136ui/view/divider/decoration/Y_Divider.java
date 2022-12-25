package com.tomatolive.library.p136ui.view.divider.decoration;

/* renamed from: com.tomatolive.library.ui.view.divider.decoration.Y_Divider */
/* loaded from: classes3.dex */
public class Y_Divider {
    public Y_SideLine bottomSideLine;
    public Y_SideLine leftSideLine;
    public Y_SideLine rightSideLine;
    public Y_SideLine topSideLine;

    public Y_Divider(Y_SideLine y_SideLine, Y_SideLine y_SideLine2, Y_SideLine y_SideLine3, Y_SideLine y_SideLine4) {
        this.leftSideLine = y_SideLine;
        this.topSideLine = y_SideLine2;
        this.rightSideLine = y_SideLine3;
        this.bottomSideLine = y_SideLine4;
    }

    public Y_SideLine getLeftSideLine() {
        return this.leftSideLine;
    }

    public void setLeftSideLine(Y_SideLine y_SideLine) {
        this.leftSideLine = y_SideLine;
    }

    public Y_SideLine getTopSideLine() {
        return this.topSideLine;
    }

    public void setTopSideLine(Y_SideLine y_SideLine) {
        this.topSideLine = y_SideLine;
    }

    public Y_SideLine getRightSideLine() {
        return this.rightSideLine;
    }

    public void setRightSideLine(Y_SideLine y_SideLine) {
        this.rightSideLine = y_SideLine;
    }

    public Y_SideLine getBottomSideLine() {
        return this.bottomSideLine;
    }

    public void setBottomSideLine(Y_SideLine y_SideLine) {
        this.bottomSideLine = y_SideLine;
    }
}
