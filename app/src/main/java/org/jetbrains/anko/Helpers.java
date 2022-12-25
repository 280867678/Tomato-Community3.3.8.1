package org.jetbrains.anko;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* renamed from: org.jetbrains.anko.AnkoException */
/* loaded from: classes4.dex */
public class Helpers extends RuntimeException {
    public Helpers() {
        this(null, 1, null);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Helpers(String message) {
        super(message);
        Intrinsics.checkParameterIsNotNull(message, "message");
    }

    public /* synthetic */ Helpers(String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? "" : str);
    }
}
