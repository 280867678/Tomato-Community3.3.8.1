package com.one.tomato.mvp.p080ui.start.view;

import android.view.View;
import com.one.tomato.thirdpart.domain.DomainRequest;
import java.util.ArrayList;

/* compiled from: WarnActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.start.view.WarnActivity$initData$3 */
/* loaded from: classes3.dex */
final class WarnActivity$initData$3 implements View.OnClickListener {
    public static final WarnActivity$initData$3 INSTANCE = new WarnActivity$initData$3();

    WarnActivity$initData$3() {
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        DomainRequest.getInstance().decrypt("server", "6K0ife+6TC4HfWqa89TjixVMnDYRDfN/MctT1GOGOUHIwwcaf0BjBuSz4FJCH1QX5OSmHUeT3wpQZvZoMFOigHhaBhUcBR0/Segzwi8zri2s1sW2oLIOorW8rmiqDa/UX39babQDXXVw+tX75OdCEk8ie6qWt4zTaXM+JzotFsbPY8yYR8+m0jcI8lEZgxeI1q6gP0wzI5PAirLgP2kY2a6yUEDJtkZPqc5K0v1guOaP8p9GitryDE9+6SjP/8yAyA+1UgmoPoUpvatKmN86vWJWrlJMnxBPEO4XetKGzbzLlJHc43kNKEskxRTwFHAgrKcPs2YVob0rmMR9bC6bglbuiC8e9BwNIOOH9967GDrpj8jVtLd4i/DAawxsGuNy2bdNb+WYo779u3O2EhE8QpO5toS83rKlZDhoXHOh01L677QqweuZOVRpeWuOXVB7Y3Z1Tqpd28tcsYmdLUn0aA==", new ArrayList<>());
    }
}
