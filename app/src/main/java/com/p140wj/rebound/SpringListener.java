package com.p140wj.rebound;

/* renamed from: com.wj.rebound.SpringListener */
/* loaded from: classes4.dex */
public interface SpringListener {
    void onSpringActivate(Spring spring);

    void onSpringAtRest(Spring spring);

    void onSpringEndStateChange(Spring spring);

    void onSpringUpdate(Spring spring);
}
