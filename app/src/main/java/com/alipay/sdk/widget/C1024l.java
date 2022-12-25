package com.alipay.sdk.widget;

import android.view.animation.Animation;
import com.alipay.sdk.widget.C1021j;

/* JADX INFO: Access modifiers changed from: package-private */
/* renamed from: com.alipay.sdk.widget.l */
/* loaded from: classes2.dex */
public class C1024l extends C1021j.AbstractanimationAnimation$AnimationListenerC1022a {

    /* renamed from: a */
    final /* synthetic */ C1028p f1094a;

    /* renamed from: b */
    final /* synthetic */ C1021j f1095b;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C1024l(C1021j c1021j, C1028p c1028p) {
        super(c1021j, null);
        this.f1095b = c1021j;
        this.f1094a = c1028p;
    }

    @Override // android.view.animation.Animation.AnimationListener
    public void onAnimationEnd(Animation animation) {
        this.f1094a.m4331a();
        this.f1095b.f1089v = false;
    }
}
