package com.alipay.sdk.widget;

import android.view.animation.Animation;
import com.alipay.sdk.widget.C1021j;

/* JADX INFO: Access modifiers changed from: package-private */
/* renamed from: com.alipay.sdk.widget.m */
/* loaded from: classes2.dex */
public class C1025m extends C1021j.AbstractanimationAnimation$AnimationListenerC1022a {

    /* renamed from: a */
    final /* synthetic */ C1028p f1096a;

    /* renamed from: b */
    final /* synthetic */ String f1097b;

    /* renamed from: c */
    final /* synthetic */ C1021j f1098c;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C1025m(C1021j c1021j, C1028p c1028p, String str) {
        super(c1021j, null);
        this.f1098c = c1021j;
        this.f1096a = c1028p;
        this.f1097b = str;
    }

    @Override // android.view.animation.Animation.AnimationListener
    public void onAnimationEnd(Animation animation) {
        C1028p c1028p;
        this.f1098c.removeView(this.f1096a);
        c1028p = this.f1098c.f1091x;
        c1028p.m4326a(this.f1097b);
        this.f1098c.f1089v = false;
    }
}
