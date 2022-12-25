package com.tomatolive.library.p136ui.activity.mylive;

import android.text.TextUtils;
import io.reactivex.functions.Function;

/* compiled from: lambda */
/* renamed from: com.tomatolive.library.ui.activity.mylive.-$$Lambda$LivePreNoticeActivity$Ug5s0bIA-1uy6ACXt-AovkEEE-0  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$LivePreNoticeActivity$Ug5s0bIA1uy6ACXtAovkEEE0 implements Function {
    public static final /* synthetic */ $$Lambda$LivePreNoticeActivity$Ug5s0bIA1uy6ACXtAovkEEE0 INSTANCE = new $$Lambda$LivePreNoticeActivity$Ug5s0bIA1uy6ACXtAovkEEE0();

    private /* synthetic */ $$Lambda$LivePreNoticeActivity$Ug5s0bIA1uy6ACXtAovkEEE0() {
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
