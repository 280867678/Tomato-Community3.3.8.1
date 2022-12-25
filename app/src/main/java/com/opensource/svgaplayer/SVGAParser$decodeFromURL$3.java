package com.opensource.svgaplayer;

import com.opensource.svgaplayer.SVGAParser;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: SVGAParser.kt */
/* loaded from: classes3.dex */
public final class SVGAParser$decodeFromURL$3 extends Lambda implements Function1<Exception, Unit> {
    final /* synthetic */ SVGAParser.ParseCompletion $callback;
    final /* synthetic */ SVGAParser this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SVGAParser$decodeFromURL$3(SVGAParser sVGAParser, SVGAParser.ParseCompletion parseCompletion) {
        super(1);
        this.this$0 = sVGAParser;
        this.$callback = parseCompletion;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public /* bridge */ /* synthetic */ Unit mo6794invoke(Exception exc) {
        invoke2(exc);
        return Unit.INSTANCE;
    }

    /* renamed from: invoke  reason: avoid collision after fix types in other method */
    public final void invoke2(Exception it2) {
        Intrinsics.checkParameterIsNotNull(it2, "it");
        this.this$0.invokeErrorCallback(it2, this.$callback);
    }
}
