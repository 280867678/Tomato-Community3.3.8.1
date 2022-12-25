package com.opensource.svgaplayer;

import kotlin.Unit;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Lambda;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: SVGAVideoEntity.kt */
/* loaded from: classes3.dex */
public final class SVGAVideoEntity$prepare$$inlined$let$lambda$1 extends Lambda implements Functions<Unit> {
    final /* synthetic */ Functions $callback$inlined;
    final /* synthetic */ SVGAVideoEntity this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SVGAVideoEntity$prepare$$inlined$let$lambda$1(SVGAVideoEntity sVGAVideoEntity, Functions functions) {
        super(0);
        this.this$0 = sVGAVideoEntity;
        this.$callback$inlined = functions;
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
        this.$callback$inlined.mo6822invoke();
    }
}
