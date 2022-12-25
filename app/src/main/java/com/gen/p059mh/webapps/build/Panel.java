package com.gen.p059mh.webapps.build;

import android.animation.Animator;
import android.support.p002v4.widget.SwipeRefreshLayout;
import android.view.View;
import com.gen.p059mh.webapps.build.toolbar.ToolBar;
import com.gen.p059mh.webapps.webEngine.WebEngine;

/* renamed from: com.gen.mh.webapps.build.Panel */
/* loaded from: classes2.dex */
public interface Panel {
    void animIn(boolean z, Animator.AnimatorListener animatorListener);

    void animOut(boolean z, Animator.AnimatorListener animatorListener);

    void build(int i);

    ToolBar getNavigationBar();

    View getPanelView();

    SwipeRefreshLayout getRefreshView();

    WebEngine getWebView();

    void release();

    void setNavigationBar(ToolBar toolBar);

    void setRefreshView(SwipeRefreshLayout swipeRefreshLayout);
}
