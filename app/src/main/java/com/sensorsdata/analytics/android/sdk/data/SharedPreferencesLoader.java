package com.sensorsdata.analytics.android.sdk.data;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/* loaded from: classes3.dex */
class SharedPreferencesLoader {
    private final Executor mExecutor = Executors.newSingleThreadExecutor();

    /* JADX INFO: Access modifiers changed from: package-private */
    public Future<SharedPreferences> loadPreferences(Context context, String str) {
        FutureTask futureTask = new FutureTask(new LoadSharedPreferences(context, str));
        this.mExecutor.execute(futureTask);
        return futureTask;
    }

    /* loaded from: classes3.dex */
    private static class LoadSharedPreferences implements Callable<SharedPreferences> {
        private final Context mContext;
        private final String mPrefsName;

        LoadSharedPreferences(Context context, String str) {
            this.mContext = context;
            this.mPrefsName = str;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.util.concurrent.Callable
        /* renamed from: call */
        public SharedPreferences mo6490call() {
            return this.mContext.getSharedPreferences(this.mPrefsName, 0);
        }
    }
}
