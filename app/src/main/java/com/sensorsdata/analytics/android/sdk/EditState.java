package com.sensorsdata.analytics.android.sdk;

import android.app.Activity;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewTreeObserver;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes3.dex */
public class EditState extends UIThreadSet<Activity> {
    private static final String LOGTAG = "SA.EditState";
    private final Handler mUiThreadHandler = new Handler(Looper.getMainLooper());
    private final Map<String, List<ViewVisitor>> mIntendedEdits = new HashMap();
    private final Map<Activity, Set<EditBinding>> mCurrentEdits = new HashMap();

    @Override // com.sensorsdata.analytics.android.sdk.UIThreadSet
    public void add(Activity activity) {
        super.add((EditState) activity);
        applyEditsOnActivity(activity);
    }

    @Override // com.sensorsdata.analytics.android.sdk.UIThreadSet
    public void remove(Activity activity) {
        super.remove((EditState) activity);
        removeChangesOnActivity(activity);
    }

    private void applyEditsOnActivity(Activity activity) {
        List<ViewVisitor> list;
        List<ViewVisitor> list2;
        String canonicalName = activity.getClass().getCanonicalName();
        View rootView = activity.getWindow().getDecorView().getRootView();
        synchronized (this.mIntendedEdits) {
            list = this.mIntendedEdits.get(canonicalName);
            list2 = this.mIntendedEdits.get(null);
        }
        if (list != null) {
            applyChangesFromList(activity, rootView, list);
        }
        if (list2 != null) {
            applyChangesFromList(activity, rootView, list2);
        }
    }

    private void applyChangesFromList(Activity activity, View view, List<ViewVisitor> list) {
        synchronized (this.mCurrentEdits) {
            if (!this.mCurrentEdits.containsKey(activity)) {
                this.mCurrentEdits.put(activity, new HashSet());
            }
            int size = list.size();
            for (int i = 0; i < size; i++) {
                this.mCurrentEdits.get(activity).add(new EditBinding(view, list.get(i), this.mUiThreadHandler));
            }
        }
    }

    private void removeChangesOnActivity(Activity activity) {
        synchronized (this.mCurrentEdits) {
            Set<EditBinding> set = this.mCurrentEdits.get(activity);
            if (set == null) {
                return;
            }
            for (EditBinding editBinding : set) {
                editBinding.kill();
            }
            this.mCurrentEdits.remove(activity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class EditBinding implements ViewTreeObserver.OnGlobalLayoutListener, Runnable {
        private boolean mAlive = true;
        private volatile boolean mDying = false;
        private final ViewVisitor mEdit;
        private final Handler mHandler;
        private final WeakReference<View> mViewRoot;

        public EditBinding(View view, ViewVisitor viewVisitor, Handler handler) {
            this.mEdit = viewVisitor;
            this.mViewRoot = new WeakReference<>(view);
            this.mHandler = handler;
            ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(this);
            }
            run();
        }

        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public void onGlobalLayout() {
            run();
        }

        @Override // java.lang.Runnable
        public void run() {
            if (!this.mAlive) {
                return;
            }
            View view = this.mViewRoot.get();
            if (view == null || this.mDying) {
                cleanUp();
                return;
            }
            this.mEdit.visit(view);
            this.mHandler.removeCallbacks(this);
            this.mHandler.postDelayed(this, 5000L);
        }

        public void kill() {
            this.mDying = true;
            this.mHandler.post(this);
        }

        private void cleanUp() {
            if (this.mAlive) {
                View view = this.mViewRoot.get();
                if (view != null) {
                    ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();
                    if (viewTreeObserver.isAlive()) {
                        if (Build.VERSION.SDK_INT < 16) {
                            viewTreeObserver.removeGlobalOnLayoutListener(this);
                        } else {
                            viewTreeObserver.removeOnGlobalLayoutListener(this);
                        }
                    }
                }
                this.mEdit.cleanup();
            }
            this.mAlive = false;
        }
    }
}
