package bolts;

import com.eclipsesource.p056v8.Platform;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* loaded from: classes2.dex */
final class BoltsExecutors {
    private static final BoltsExecutors INSTANCE = new BoltsExecutors();
    private final ExecutorService background;
    private final Executor immediate;

    private static boolean isAndroidRuntime() {
        String property = System.getProperty("java.runtime.name");
        if (property == null) {
            return false;
        }
        return property.toLowerCase(Locale.US).contains(Platform.ANDROID);
    }

    private BoltsExecutors() {
        this.background = !isAndroidRuntime() ? Executors.newCachedThreadPool() : AndroidExecutors.newCachedThreadPool();
        Executors.newSingleThreadScheduledExecutor();
        this.immediate = new ImmediateExecutor();
    }

    public static ExecutorService background() {
        return INSTANCE.background;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Executor immediate() {
        return INSTANCE.immediate;
    }

    /* loaded from: classes2.dex */
    private static class ImmediateExecutor implements Executor {
        private ThreadLocal<Integer> executionDepth;

        private ImmediateExecutor() {
            this.executionDepth = new ThreadLocal<>();
        }

        private int incrementDepth() {
            Integer num = this.executionDepth.get();
            if (num == null) {
                num = 0;
            }
            int intValue = num.intValue() + 1;
            this.executionDepth.set(Integer.valueOf(intValue));
            return intValue;
        }

        private int decrementDepth() {
            Integer num = this.executionDepth.get();
            if (num == null) {
                num = 0;
            }
            int intValue = num.intValue() - 1;
            if (intValue == 0) {
                this.executionDepth.remove();
            } else {
                this.executionDepth.set(Integer.valueOf(intValue));
            }
            return intValue;
        }

        @Override // java.util.concurrent.Executor
        public void execute(Runnable runnable) {
            try {
                if (incrementDepth() <= 15) {
                    runnable.run();
                } else {
                    BoltsExecutors.background().execute(runnable);
                }
            } finally {
                decrementDepth();
            }
        }
    }
}
