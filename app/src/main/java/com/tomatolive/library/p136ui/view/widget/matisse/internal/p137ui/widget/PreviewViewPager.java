package com.tomatolive.library.p136ui.view.widget.matisse.internal.p137ui.widget;

import android.content.Context;
import android.support.p002v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import it.sephiroth.android.library.imagezoom.ImageViewTouch;

/* renamed from: com.tomatolive.library.ui.view.widget.matisse.internal.ui.widget.PreviewViewPager */
/* loaded from: classes4.dex */
public class PreviewViewPager extends ViewPager {
    public PreviewViewPager(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.p002v4.view.ViewPager
    public boolean canScroll(View view, boolean z, int i, int i2, int i3) {
        if (view instanceof ImageViewTouch) {
            return ((ImageViewTouch) view).canScroll(i) || super.canScroll(view, z, i, i2, i3);
        }
        return super.canScroll(view, z, i, i2, i3);
    }
}
