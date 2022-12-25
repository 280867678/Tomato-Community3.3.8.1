package com.tomatolive.library.p136ui.activity.live;

import android.text.TextUtils;
import io.reactivex.functions.Function;

/* compiled from: lambda */
/* renamed from: com.tomatolive.library.ui.activity.live.-$$Lambda$ReportLiveActivity$WhgroP6Ut9RP6eWoLFhFB85U1go  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$ReportLiveActivity$WhgroP6Ut9RP6eWoLFhFB85U1go implements Function {
    public static final /* synthetic */ $$Lambda$ReportLiveActivity$WhgroP6Ut9RP6eWoLFhFB85U1go INSTANCE = new $$Lambda$ReportLiveActivity$WhgroP6Ut9RP6eWoLFhFB85U1go();

    private /* synthetic */ $$Lambda$ReportLiveActivity$WhgroP6Ut9RP6eWoLFhFB85U1go() {
    }

    @Override // io.reactivex.functions.Function
    /* renamed from: apply */
    public final Object mo6755apply(Object obj) {
        Boolean valueOf;
        CharSequence charSequence = (CharSequence) obj;
        valueOf = Boolean.valueOf(!TextUtils.isEmpty(charSequence));
        return valueOf;
    }
}
