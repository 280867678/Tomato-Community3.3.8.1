package kotlin.text;

import java.util.Map;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Lambda;

/* compiled from: CharCategory.kt */
/* loaded from: classes4.dex */
final class CharCategory$Companion$categoryMap$2 extends Lambda implements Functions<Map<Integer, ? extends CharCategory>> {
    public static final CharCategory$Companion$categoryMap$2 INSTANCE = new CharCategory$Companion$categoryMap$2();

    CharCategory$Companion$categoryMap$2() {
        super(0);
    }

    /*  JADX ERROR: JadxRuntimeException in pass: InlineMethods
        jadx.core.utils.exceptions.JadxRuntimeException: Failed to process method for inline: kotlin.collections.MapsKt.mapCapacity(int):int
        	at jadx.core.dex.visitors.InlineMethods.processInvokeInsn(InlineMethods.java:76)
        	at jadx.core.dex.visitors.InlineMethods.visit(InlineMethods.java:51)
        Caused by: java.lang.ArrayIndexOutOfBoundsException: 3
        	at java.util.ArrayList.add(ArrayList.java:463)
        	at jadx.core.utils.ListUtils.safeReplace(ListUtils.java:82)
        	at jadx.core.dex.visitors.InlineMethods.lambda$updateUsageInfo$0(InlineMethods.java:157)
        	at jadx.core.dex.nodes.InsnNode.visitInsns(InsnNode.java:295)
        	at jadx.core.dex.visitors.InlineMethods.updateUsageInfo(InlineMethods.java:149)
        	at jadx.core.dex.visitors.InlineMethods.inlineMethod(InlineMethods.java:122)
        	at jadx.core.dex.visitors.InlineMethods.processInvokeInsn(InlineMethods.java:74)
        	... 1 more
        */
    @Override // kotlin.jvm.functions.Functions
    /* renamed from: invoke  reason: collision with other method in class */
    public final java.util.Map<java.lang.Integer, ? extends kotlin.text.CharCategory> mo6822invoke() {
        /*
            r6 = this;
            kotlin.text.CharCategory[] r0 = kotlin.text.CharCategory.values()
            int r1 = r0.length
            int r1 = kotlin.collections.MapsKt.mapCapacity(r1)
            r2 = 16
            int r1 = kotlin.ranges.RangesKt.coerceAtLeast(r1, r2)
            java.util.LinkedHashMap r2 = new java.util.LinkedHashMap
            r2.<init>(r1)
            int r1 = r0.length
            r3 = 0
        L16:
            if (r3 >= r1) goto L28
            r4 = r0[r3]
            int r5 = r4.getValue()
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            r2.put(r5, r4)
            int r3 = r3 + 1
            goto L16
        L28:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.text.CharCategory$Companion$categoryMap$2.mo6822invoke():java.util.Map");
    }
}
