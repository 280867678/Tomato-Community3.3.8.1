package kotlin.text;

import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.collections.Maps;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Lambda;
import kotlin.ranges._Ranges;

/* compiled from: CharDirectionality.kt */
/* loaded from: classes4.dex */
final class CharDirectionality$Companion$directionalityMap$2 extends Lambda implements Functions<Map<Integer, ? extends CharDirectionality>> {
    public static final CharDirectionality$Companion$directionalityMap$2 INSTANCE = new CharDirectionality$Companion$directionalityMap$2();

    CharDirectionality$Companion$directionalityMap$2() {
        super(0);
    }

    @Override // kotlin.jvm.functions.Functions
    /* renamed from: invoke  reason: collision with other method in class */
    public final Map<Integer, ? extends CharDirectionality> mo6822invoke() {
        int mapCapacity;
        int coerceAtLeast;
        CharDirectionality[] values = CharDirectionality.values();
        mapCapacity = Maps.mapCapacity(values.length);
        coerceAtLeast = _Ranges.coerceAtLeast(mapCapacity, 16);
        LinkedHashMap linkedHashMap = new LinkedHashMap(coerceAtLeast);
        for (CharDirectionality charDirectionality : values) {
            linkedHashMap.put(Integer.valueOf(charDirectionality.getValue()), charDirectionality);
        }
        return linkedHashMap;
    }
}
