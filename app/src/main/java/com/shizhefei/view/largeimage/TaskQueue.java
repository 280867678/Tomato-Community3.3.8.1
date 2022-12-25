package com.shizhefei.view.largeimage;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* loaded from: classes3.dex */
class TaskQueue {
    private static final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public void addTask(Task task) {
        if (task == null) {
            return;
        }
        task.executeOnExecutor(executorService, new Void[0]);
    }

    public void cancelTask(Task task) {
        if (task == null) {
            return;
        }
        task.cancel(true);
    }

    /* loaded from: classes3.dex */
    static abstract class Task extends AsyncTask<Void, Void, Void> {
        protected abstract void doInBackground();

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onCancelled() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public void onPostExecute() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        @TargetApi(11)
        public final void onCancelled(Void r1) {
            super.onCancelled((Task) r1);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public final Void doInBackground(Void... voidArr) {
            doInBackground();
            return null;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public final void onPostExecute(Void r1) {
            super.onPostExecute((Task) r1);
            onPostExecute();
        }
    }
}
