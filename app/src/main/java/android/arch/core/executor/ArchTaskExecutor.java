package android.arch.core.executor;

import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import java.util.concurrent.Executor;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes2.dex */
public class ArchTaskExecutor extends TaskExecutor {
    private static volatile ArchTaskExecutor sInstance;
    @NonNull
    private TaskExecutor mDefaultTaskExecutor = new DefaultTaskExecutor();
    @NonNull
    private TaskExecutor mDelegate = this.mDefaultTaskExecutor;

    static {
        new Executor() { // from class: android.arch.core.executor.ArchTaskExecutor.1
            @Override // java.util.concurrent.Executor
            public void execute(Runnable runnable) {
                ArchTaskExecutor.getInstance().postToMainThread(runnable);
            }
        };
        new Executor() { // from class: android.arch.core.executor.ArchTaskExecutor.2
            @Override // java.util.concurrent.Executor
            public void execute(Runnable runnable) {
                ArchTaskExecutor.getInstance().executeOnDiskIO(runnable);
            }
        };
    }

    private ArchTaskExecutor() {
    }

    @NonNull
    public static ArchTaskExecutor getInstance() {
        if (sInstance != null) {
            return sInstance;
        }
        synchronized (ArchTaskExecutor.class) {
            if (sInstance == null) {
                sInstance = new ArchTaskExecutor();
            }
        }
        return sInstance;
    }

    @Override // android.arch.core.executor.TaskExecutor
    public void executeOnDiskIO(Runnable runnable) {
        this.mDelegate.executeOnDiskIO(runnable);
    }

    @Override // android.arch.core.executor.TaskExecutor
    public void postToMainThread(Runnable runnable) {
        this.mDelegate.postToMainThread(runnable);
    }

    @Override // android.arch.core.executor.TaskExecutor
    public boolean isMainThread() {
        return this.mDelegate.isMainThread();
    }
}
