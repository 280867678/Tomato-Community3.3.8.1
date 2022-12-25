package com.gen.p059mh.webapps.build;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.support.p002v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.LinearLayout;
import com.gen.p059mh.webapps.build.toolbar.ToolBar;
import com.gen.p059mh.webapps.webEngine.WebEngine;
import com.gen.p059mh.webapps.webEngine.WebEngineManager;

/* renamed from: com.gen.mh.webapps.build.PagePanel */
/* loaded from: classes2.dex */
class PagePanel extends LinearLayout implements Panel {
    Context context;
    ToolBar navigationBar;
    SwipeRefreshLayout refreshView;
    View tabBar;
    WebEngine webView;
    int width;

    @Override // com.gen.p059mh.webapps.build.Panel
    public View getPanelView() {
        return this;
    }

    public PagePanel(Context context) {
        super(context);
        this.context = context;
    }

    @Override // com.gen.p059mh.webapps.build.Panel
    public ToolBar getNavigationBar() {
        return this.navigationBar;
    }

    @Override // com.gen.p059mh.webapps.build.Panel
    public void setNavigationBar(ToolBar toolBar) {
        this.navigationBar = toolBar;
    }

    @Override // com.gen.p059mh.webapps.build.Panel
    public SwipeRefreshLayout getRefreshView() {
        return this.refreshView;
    }

    @Override // com.gen.p059mh.webapps.build.Panel
    public void setRefreshView(SwipeRefreshLayout swipeRefreshLayout) {
        this.refreshView = swipeRefreshLayout;
    }

    @Override // com.gen.p059mh.webapps.build.Panel
    public WebEngine getWebView() {
        return this.webView;
    }

    public void setupWebView() {
        this.webView = WebEngineManager.getInstance().initWebEngine().init(this.context);
        this.webView.setLayoutParams(new LinearLayout.LayoutParams(-1, -1, 1.0f));
        this.webView.setBackgroundColor(Color.parseColor("#ffffffff"));
    }

    @Override // com.gen.p059mh.webapps.build.Panel
    public void animIn(boolean z, Animator.AnimatorListener animatorListener) {
        ObjectAnimator ofFloat = z ? ObjectAnimator.ofFloat(this, "alpha", 0.0f, 1.0f) : ObjectAnimator.ofFloat(this, "translationX", this.width, 0.0f);
        if (animatorListener != null) {
            ofFloat.addListener(animatorListener);
        }
        ofFloat.start();
    }

    @Override // com.gen.p059mh.webapps.build.Panel
    public void animOut(boolean z, Animator.AnimatorListener animatorListener) {
        ObjectAnimator ofFloat = z ? ObjectAnimator.ofFloat(this, "alpha", 1.0f, 0.0f) : ObjectAnimator.ofFloat(this, "translationX", 0.0f, this.width);
        if (animatorListener != null) {
            ofFloat.addListener(animatorListener);
        }
        ofFloat.start();
    }

    @Override // com.gen.p059mh.webapps.build.Panel
    public void build(int i) {
        setOrientation(1);
        this.width = i;
        setupWebView();
        ToolBar toolBar = this.navigationBar;
        if (toolBar != null) {
            addView(toolBar.getView());
        }
        SwipeRefreshLayout swipeRefreshLayout = this.refreshView;
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.addView(this.webView.provideView());
            addView(this.refreshView);
        }
        View view = this.tabBar;
        if (view != null) {
            addView(view);
        }
    }

    @Override // com.gen.p059mh.webapps.build.Panel
    public void release() {
        ToolBar toolBar = this.navigationBar;
        if (toolBar != null) {
            toolBar.release();
            this.navigationBar = null;
        }
        if (this.context != null) {
            this.context = null;
        }
        WebEngine webEngine = this.webView;
        if (webEngine != null) {
            webEngine.destroy();
            this.webView = null;
        }
        if (this.refreshView != null) {
            this.refreshView = null;
        }
    }
}
