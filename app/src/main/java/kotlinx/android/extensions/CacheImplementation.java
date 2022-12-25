package kotlinx.android.extensions;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: CacheImplementation.kt */
/* loaded from: classes4.dex */
public enum CacheImplementation {
    SPARSE_ARRAY,
    HASH_MAP,
    NO_CACHE;
    
    public static final Companion Companion = new Companion(null);
    private static final CacheImplementation DEFAULT = HASH_MAP;

    /* compiled from: CacheImplementation.kt */
    /* loaded from: classes4.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }
}
