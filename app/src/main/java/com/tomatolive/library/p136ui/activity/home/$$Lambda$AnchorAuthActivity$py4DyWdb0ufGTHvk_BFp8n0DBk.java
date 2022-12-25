package com.tomatolive.library.p136ui.activity.home;

import android.text.TextUtils;
import io.reactivex.functions.Function;

/* compiled from: lambda */
/* renamed from: com.tomatolive.library.ui.activity.home.-$$Lambda$AnchorAuthActivity$py4DyWdb0ufGTHvk_BFp8n0DB-k  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$AnchorAuthActivity$py4DyWdb0ufGTHvk_BFp8n0DBk implements Function {
    public static final /* synthetic */ $$Lambda$AnchorAuthActivity$py4DyWdb0ufGTHvk_BFp8n0DBk INSTANCE = new $$Lambda$AnchorAuthActivity$py4DyWdb0ufGTHvk_BFp8n0DBk();

    private /* synthetic */ $$Lambda$AnchorAuthActivity$py4DyWdb0ufGTHvk_BFp8n0DBk() {
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
