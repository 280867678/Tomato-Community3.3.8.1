package com.alipay.sdk.widget;

import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import com.alipay.sdk.widget.C1028p;

/* JADX INFO: Access modifiers changed from: package-private */
/* renamed from: com.alipay.sdk.widget.q */
/* loaded from: classes2.dex */
public class View$OnClickListenerC1032q implements View.OnClickListener {

    /* renamed from: a */
    final /* synthetic */ C1028p f1114a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public View$OnClickListenerC1032q(C1028p c1028p) {
        this.f1114a = c1028p;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        C1028p.AbstractC1031c abstractC1031c;
        Handler handler;
        ImageView imageView;
        ImageView imageView2;
        abstractC1031c = this.f1114a.f1110i;
        if (abstractC1031c != null) {
            view.setEnabled(false);
            handler = C1028p.f1102f;
            handler.postDelayed(new RunnableC1033r(this, view), 256L);
            imageView = this.f1114a.f1103a;
            if (view != imageView) {
                imageView2 = this.f1114a.f1105c;
                if (view != imageView2) {
                    return;
                }
                abstractC1031c.mo4309b(this.f1114a);
                return;
            }
            abstractC1031c.mo4310a(this.f1114a);
        }
    }
}
