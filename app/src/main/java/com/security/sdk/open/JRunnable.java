package com.security.sdk.open;

import com.security.sdk.AbstractC3087d;

/* loaded from: classes3.dex */
public class JRunnable implements Runnable {
    AbstractC3087d runnable;

    public JRunnable(AbstractC3087d abstractC3087d) {
        this.runnable = abstractC3087d;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            this.runnable.mo3678a();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
