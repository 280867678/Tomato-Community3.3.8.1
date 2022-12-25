package com.one.tomato.mvp.p080ui.post.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.p002v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.View;
import com.one.tomato.utils.LogUtil;
import com.tomatolive.library.utils.ConstantUtils;

/* renamed from: com.one.tomato.mvp.ui.post.view.MyNestScrollView */
/* loaded from: classes3.dex */
public class MyNestScrollView extends NestedScrollView {
    int mParentScrollHeight;
    int mScrollY;

    public MyNestScrollView(@NonNull Context context) {
        super(context);
    }

    public MyNestScrollView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public MyNestScrollView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void setMyScrollHeight(int i) {
        this.mParentScrollHeight = i;
    }

    @Override // android.support.p002v4.widget.NestedScrollView, android.view.ViewGroup, android.view.ViewParent, android.support.p002v4.view.NestedScrollingParent
    public void onNestedPreScroll(View view, int i, int i2, int[] iArr) {
        super.onNestedPreScroll(view, i, i2, iArr);
        if (this.mScrollY < this.mParentScrollHeight) {
            iArr[0] = i;
            iArr[1] = i2;
            scrollBy(0, i2);
        }
        LogUtil.m3787d("yan5", "dx " + i + " dy " + i2 + ConstantUtils.PLACEHOLDER_STR_ONE + iArr[0] + ConstantUtils.PLACEHOLDER_STR_ONE + iArr[1] + " scrollY " + this.mScrollY);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.p002v4.widget.NestedScrollView, android.view.View
    public void onScrollChanged(int i, int i2, int i3, int i4) {
        super.onScrollChanged(i, i2, i3, i4);
        this.mScrollY = i2;
    }
}
