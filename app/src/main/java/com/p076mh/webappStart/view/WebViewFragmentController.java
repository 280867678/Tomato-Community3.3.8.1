package com.p076mh.webappStart.view;

import android.view.View;
import com.gen.p059mh.webapps.listener.IWebFragmentController;
import com.p076mh.webappStart.util.MainHandler;

/* renamed from: com.mh.webappStart.view.WebViewFragmentController */
/* loaded from: classes3.dex */
public class WebViewFragmentController {
    private boolean isRemoved;
    private View view;
    private IWebFragmentController webViewFragment;

    public WebViewFragmentController(IWebFragmentController iWebFragmentController, View view) {
        this.isRemoved = false;
        this.webViewFragment = iWebFragmentController;
        this.view = view;
        this.isRemoved = false;
    }

    public void remove() {
        if (!this.isRemoved) {
            MainHandler.getInstance().post(new Runnable() { // from class: com.mh.webappStart.view.WebViewFragmentController.1
                @Override // java.lang.Runnable
                public void run() {
                    WebViewFragmentController.this.webViewFragment.getContentView().removeView(WebViewFragmentController.this.view);
                    WebViewFragmentController.this.isRemoved = true;
                }
            });
        }
    }
}
