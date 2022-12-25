package com.king.zxing;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import com.king.zxing.util.LogUtils;
import java.lang.ref.WeakReference;
import java.util.concurrent.RejectedExecutionException;

/* loaded from: classes3.dex */
final class InactivityTimer {
    private final Activity activity;
    private AsyncTask<Object, Object, Object> inactivityTask;
    private final BroadcastReceiver powerStatusReceiver = new PowerStatusReceiver(this);
    private boolean registered = false;

    /* JADX INFO: Access modifiers changed from: package-private */
    public InactivityTimer(Activity activity) {
        this.activity = activity;
        onActivity();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void onActivity() {
        cancel();
        this.inactivityTask = new InactivityAsyncTask(this.activity);
        try {
            this.inactivityTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Object[0]);
        } catch (RejectedExecutionException unused) {
            LogUtils.m3902w("Couldn't schedule inactivity task; ignoring");
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void onPause() {
        cancel();
        if (this.registered) {
            this.activity.unregisterReceiver(this.powerStatusReceiver);
            this.registered = false;
            return;
        }
        LogUtils.m3902w("PowerStatusReceiver was never registered?");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void onResume() {
        if (this.registered) {
            LogUtils.m3902w("PowerStatusReceiver was already registered?");
        } else {
            this.activity.registerReceiver(this.powerStatusReceiver, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
            this.registered = true;
        }
        onActivity();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancel() {
        AsyncTask<Object, Object, Object> asyncTask = this.inactivityTask;
        if (asyncTask != null) {
            asyncTask.cancel(true);
            this.inactivityTask = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void shutdown() {
        cancel();
    }

    /* loaded from: classes3.dex */
    private static class PowerStatusReceiver extends BroadcastReceiver {
        private WeakReference<InactivityTimer> weakReference;

        public PowerStatusReceiver(InactivityTimer inactivityTimer) {
            this.weakReference = new WeakReference<>(inactivityTimer);
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            InactivityTimer inactivityTimer;
            if (!"android.intent.action.BATTERY_CHANGED".equals(intent.getAction()) || (inactivityTimer = this.weakReference.get()) == null) {
                return;
            }
            if (!(intent.getIntExtra("plugged", -1) <= 0)) {
                inactivityTimer.cancel();
            } else {
                inactivityTimer.onActivity();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class InactivityAsyncTask extends AsyncTask<Object, Object, Object> {
        private WeakReference<Activity> weakReference;

        public InactivityAsyncTask(Activity activity) {
            this.weakReference = new WeakReference<>(activity);
        }

        @Override // android.os.AsyncTask
        protected Object doInBackground(Object... objArr) {
            try {
                Thread.sleep(300000L);
                LogUtils.m3903i("Finishing activity due to inactivity");
                Activity activity = this.weakReference.get();
                if (activity == null) {
                    return null;
                }
                activity.finish();
                return null;
            } catch (InterruptedException unused) {
                return null;
            }
        }
    }
}
