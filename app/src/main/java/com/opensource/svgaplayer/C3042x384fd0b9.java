package com.opensource.svgaplayer;

import kotlin.Unit;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Lambda;

/* compiled from: SVGAParser.kt */
/* renamed from: com.opensource.svgaplayer.SVGAParser$decodeFromInputStream$1$$special$$inlined$let$lambda$2 */
/* loaded from: classes3.dex */
final class C3042x384fd0b9 extends Lambda implements Functions<Unit> {
    final /* synthetic */ SVGAVideoEntity $videoItem;
    final /* synthetic */ SVGAParser$decodeFromInputStream$1 this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C3042x384fd0b9(SVGAVideoEntity sVGAVideoEntity, SVGAParser$decodeFromInputStream$1 sVGAParser$decodeFromInputStream$1) {
        super(0);
        this.$videoItem = sVGAVideoEntity;
        this.this$0 = sVGAParser$decodeFromInputStream$1;
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
        SVGAParser$decodeFromInputStream$1 sVGAParser$decodeFromInputStream$1 = this.this$0;
        sVGAParser$decodeFromInputStream$1.this$0.invokeCompleteCallback(this.$videoItem, sVGAParser$decodeFromInputStream$1.$callback);
    }
}
