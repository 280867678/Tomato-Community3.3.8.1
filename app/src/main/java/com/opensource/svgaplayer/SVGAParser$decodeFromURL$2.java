package com.opensource.svgaplayer;

import com.opensource.svgaplayer.SVGAParser;
import java.io.InputStream;
import java.net.URL;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: SVGAParser.kt */
/* loaded from: classes3.dex */
public final class SVGAParser$decodeFromURL$2 extends Lambda implements Function1<InputStream, Unit> {
    final /* synthetic */ SVGAParser.ParseCompletion $callback;
    final /* synthetic */ URL $url;
    final /* synthetic */ SVGAParser this$0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SVGAParser$decodeFromURL$2(SVGAParser sVGAParser, URL url, SVGAParser.ParseCompletion parseCompletion) {
        super(1);
        this.this$0 = sVGAParser;
        this.$url = url;
        this.$callback = parseCompletion;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public /* bridge */ /* synthetic */ Unit mo6794invoke(InputStream inputStream) {
        invoke2(inputStream);
        return Unit.INSTANCE;
    }

    /* renamed from: invoke  reason: avoid collision after fix types in other method */
    public final void invoke2(InputStream it2) {
        String buildCacheKey;
        Intrinsics.checkParameterIsNotNull(it2, "it");
        SVGAParser sVGAParser = this.this$0;
        buildCacheKey = sVGAParser.buildCacheKey(this.$url);
        SVGAParser.decodeFromInputStream$default(sVGAParser, it2, buildCacheKey, this.$callback, false, 8, null);
    }
}
