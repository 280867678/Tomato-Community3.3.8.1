package com.gen.p059mh.webapp_extensions.views.player;

import android.content.Context;
import android.util.AttributeSet;
import com.github.ybq.android.spinkit.SpinKitView;

/* renamed from: com.gen.mh.webapp_extensions.views.player.PlayerLoadingView */
/* loaded from: classes2.dex */
public class PlayerLoadingView extends SpinKitView {
    ViewVisibilityChangeListener viewVisibilityChangeListener;

    /* renamed from: com.gen.mh.webapp_extensions.views.player.PlayerLoadingView$ViewVisibilityChangeListener */
    /* loaded from: classes2.dex */
    public interface ViewVisibilityChangeListener {
        void onVisibilityChange(int i);
    }

    public void setViewVisibilityChangeListener(ViewVisibilityChangeListener viewVisibilityChangeListener) {
        this.viewVisibilityChangeListener = viewVisibilityChangeListener;
    }

    public PlayerLoadingView(Context context) {
        super(context);
    }

    public PlayerLoadingView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public PlayerLoadingView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public PlayerLoadingView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    @Override // android.view.View
    public void setVisibility(int i) {
        super.setVisibility(i);
        ViewVisibilityChangeListener viewVisibilityChangeListener = this.viewVisibilityChangeListener;
        if (viewVisibilityChangeListener != null) {
            viewVisibilityChangeListener.onVisibilityChange(i);
        }
    }
}
