package io.requery.android.database.sqlite;

import android.util.Log;

/* loaded from: classes4.dex */
public final class CloseGuard {
    private Throwable allocationSite;
    private static final CloseGuard NOOP = new CloseGuard();
    private static volatile boolean ENABLED = true;
    private static volatile Reporter REPORTER = new DefaultReporter();

    /* loaded from: classes4.dex */
    public interface Reporter {
        void report(String str, Throwable th);
    }

    public static CloseGuard get() {
        if (!ENABLED) {
            return NOOP;
        }
        return new CloseGuard();
    }

    private CloseGuard() {
    }

    public void open(String str) {
        if (str == null) {
            throw new NullPointerException("closer == null");
        }
        if (this == NOOP || !ENABLED) {
            return;
        }
        this.allocationSite = new Throwable("Explicit termination method '" + str + "' not called");
    }

    public void close() {
        this.allocationSite = null;
    }

    public void warnIfOpen() {
        if (this.allocationSite == null || !ENABLED) {
            return;
        }
        REPORTER.report("A resource was acquired at attached stack trace but never released. See java.io.Closeable for information on avoiding resource leaks.", this.allocationSite);
    }

    /* loaded from: classes4.dex */
    private static final class DefaultReporter implements Reporter {
        private DefaultReporter() {
        }

        @Override // io.requery.android.database.sqlite.CloseGuard.Reporter
        public void report(String str, Throwable th) {
            Log.w("SQLite", str, th);
        }
    }
}
