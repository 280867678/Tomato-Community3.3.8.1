package org.greenrobot.eventbus;

import android.os.Looper;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.greenrobot.eventbus.Logger;
import org.greenrobot.eventbus.MainThreadSupport;
import org.greenrobot.eventbus.meta.SubscriberInfoIndex;

/* loaded from: classes4.dex */
public class EventBusBuilder {
    private static final ExecutorService DEFAULT_EXECUTOR_SERVICE = Executors.newCachedThreadPool();
    boolean ignoreGeneratedIndex;
    Logger logger;
    MainThreadSupport mainThreadSupport;
    boolean strictMethodVerification;
    List<SubscriberInfoIndex> subscriberInfoIndexes;
    boolean throwSubscriberException;
    boolean logSubscriberExceptions = true;
    boolean logNoSubscriberMessages = true;
    boolean sendSubscriberExceptionEvent = true;
    boolean sendNoSubscriberEvent = true;
    boolean eventInheritance = true;
    ExecutorService executorService = DEFAULT_EXECUTOR_SERVICE;

    /* JADX INFO: Access modifiers changed from: package-private */
    public Logger getLogger() {
        Logger logger = this.logger;
        return logger != null ? logger : (!Logger.AndroidLogger.isAndroidLogAvailable() || getAndroidMainLooperOrNull() == null) ? new Logger.SystemOutLogger() : new Logger.AndroidLogger("EventBus");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public MainThreadSupport getMainThreadSupport() {
        Object androidMainLooperOrNull;
        MainThreadSupport mainThreadSupport = this.mainThreadSupport;
        if (mainThreadSupport != null) {
            return mainThreadSupport;
        }
        if (Logger.AndroidLogger.isAndroidLogAvailable() && (androidMainLooperOrNull = getAndroidMainLooperOrNull()) != null) {
            return new MainThreadSupport.AndroidHandlerMainThreadSupport((Looper) androidMainLooperOrNull);
        }
        return null;
    }

    Object getAndroidMainLooperOrNull() {
        try {
            return Looper.getMainLooper();
        } catch (RuntimeException unused) {
            return null;
        }
    }
}
