package com.opensource.svgaplayer;

import kotlin.Unit;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Ref$BooleanRef;

/* compiled from: SVGAParser.kt */
/* loaded from: classes3.dex */
final class SVGAParser$FileDownloader$resume$cancelBlock$1 extends Lambda implements Functions<Unit> {
    final /* synthetic */ Ref$BooleanRef $cancelled;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SVGAParser$FileDownloader$resume$cancelBlock$1(Ref$BooleanRef ref$BooleanRef) {
        super(0);
        this.$cancelled = ref$BooleanRef;
    }

    @Override // kotlin.jvm.functions.Functions
    /* renamed from: invoke */
    public /* bridge */ /* synthetic */ Unit mo6822invoke() {
        mo6822invoke();
        return Unit.INSTANCE;
    }

    @Override // kotlin.jvm.functions.Functions
    /* renamed from: invoke  reason: collision with other method in class */
    public final void mo6822invoke() {
        this.$cancelled.element = true;
    }
}
