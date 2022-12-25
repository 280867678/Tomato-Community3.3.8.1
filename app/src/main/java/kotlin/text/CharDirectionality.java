package kotlin.text;

import kotlin.Lazy;
import kotlin.LazyJVM;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;

/* compiled from: CharDirectionality.kt */
/* loaded from: classes4.dex */
public enum CharDirectionality {
    UNDEFINED(-1),
    LEFT_TO_RIGHT(0),
    RIGHT_TO_LEFT(1),
    RIGHT_TO_LEFT_ARABIC(2),
    EUROPEAN_NUMBER(3),
    EUROPEAN_NUMBER_SEPARATOR(4),
    EUROPEAN_NUMBER_TERMINATOR(5),
    ARABIC_NUMBER(6),
    COMMON_NUMBER_SEPARATOR(7),
    NONSPACING_MARK(8),
    BOUNDARY_NEUTRAL(9),
    PARAGRAPH_SEPARATOR(10),
    SEGMENT_SEPARATOR(11),
    WHITESPACE(12),
    OTHER_NEUTRALS(13),
    LEFT_TO_RIGHT_EMBEDDING(14),
    LEFT_TO_RIGHT_OVERRIDE(15),
    RIGHT_TO_LEFT_EMBEDDING(16),
    RIGHT_TO_LEFT_OVERRIDE(17),
    POP_DIRECTIONAL_FORMAT(18);
    
    public static final Companion Companion = new Companion(null);
    private static final Lazy directionalityMap$delegate;
    private final int value;

    CharDirectionality(int i) {
        this.value = i;
    }

    public final int getValue() {
        return this.value;
    }

    static {
        Lazy lazy;
        lazy = LazyJVM.lazy(CharDirectionality$Companion$directionalityMap$2.INSTANCE);
        directionalityMap$delegate = lazy;
    }

    /* compiled from: CharDirectionality.kt */
    /* loaded from: classes4.dex */
    public static final class Companion {
        static {
            PropertyReference1Impl propertyReference1Impl = new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(Companion.class), "directionalityMap", "getDirectionalityMap()Ljava/util/Map;");
            Reflection.property1(propertyReference1Impl);
            new KProperty[1][0] = propertyReference1Impl;
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }
}
