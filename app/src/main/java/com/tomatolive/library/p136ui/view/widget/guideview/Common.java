package com.tomatolive.library.p136ui.view.widget.guideview;

import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import com.tomatolive.library.p136ui.view.widget.guideview.MaskView;

/* renamed from: com.tomatolive.library.ui.view.widget.guideview.Common */
/* loaded from: classes4.dex */
public class Common {
    /* JADX INFO: Access modifiers changed from: package-private */
    public static View componentToView(LayoutInflater layoutInflater, Component component) {
        View view = component.getView(layoutInflater);
        MaskView.LayoutParams layoutParams = new MaskView.LayoutParams(-2, -2);
        layoutParams.offsetX = component.getXOffset();
        layoutParams.offsetY = component.getYOffset();
        layoutParams.targetAnchor = component.getAnchor();
        layoutParams.targetParentPosition = component.getFitPosition();
        view.setLayoutParams(layoutParams);
        return view;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Rect getViewAbsRect(View view, int i, int i2) {
        int[] iArr = new int[2];
        view.getLocationInWindow(iArr);
        Rect rect = new Rect();
        rect.set(iArr[0], iArr[1], iArr[0] + view.getMeasuredWidth(), iArr[1] + view.getMeasuredHeight());
        rect.offset(-i, -i2);
        return rect;
    }
}
