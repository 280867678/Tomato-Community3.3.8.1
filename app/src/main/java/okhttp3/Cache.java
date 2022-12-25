package okhttp3;

import java.io.Closeable;
import java.io.Flushable;
import okhttp3.internal.cache.InternalCache;

/* loaded from: classes4.dex */
public final class Cache implements Closeable, Flushable {
    final InternalCache internalCache;
}
