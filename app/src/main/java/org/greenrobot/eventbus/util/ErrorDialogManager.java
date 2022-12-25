package org.greenrobot.eventbus.util;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.p002v4.app.DialogFragment;
import android.support.p002v4.app.Fragment;
import android.support.p002v4.app.FragmentManager;
import android.util.Log;
import org.greenrobot.eventbus.EventBus;

/* loaded from: classes4.dex */
public class ErrorDialogManager {
    public static ErrorDialogFragmentFactory<?> factory;

    /* loaded from: classes4.dex */
    public static class SupportManagerFragment extends Fragment {
        protected Bundle argumentsForErrorDialog;
        private EventBus eventBus;
        private Object executionScope;
        protected boolean finishAfterDialog;
        private boolean skipRegisterOnNextResume;

        @Override // android.support.p002v4.app.Fragment
        public void onCreate(Bundle bundle) {
            super.onCreate(bundle);
            ErrorDialogManager.factory.config.getEventBus();
            throw null;
        }

        @Override // android.support.p002v4.app.Fragment
        public void onResume() {
            super.onResume();
            if (this.skipRegisterOnNextResume) {
                this.skipRegisterOnNextResume = false;
            } else {
                ErrorDialogManager.factory.config.getEventBus();
                throw null;
            }
        }

        @Override // android.support.p002v4.app.Fragment
        public void onPause() {
            this.eventBus.unregister(this);
            super.onPause();
        }

        public void onEventMainThread(ThrowableFailureEvent throwableFailureEvent) {
            if (!ErrorDialogManager.isInExecutionScope(this.executionScope, throwableFailureEvent)) {
                return;
            }
            ErrorDialogManager.checkLogException(throwableFailureEvent);
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.executePendingTransactions();
            DialogFragment dialogFragment = (DialogFragment) fragmentManager.findFragmentByTag("de.greenrobot.eventbus.error_dialog");
            if (dialogFragment != null) {
                dialogFragment.dismiss();
            }
            DialogFragment dialogFragment2 = (DialogFragment) ErrorDialogManager.factory.prepareErrorFragment(throwableFailureEvent, this.finishAfterDialog, this.argumentsForErrorDialog);
            if (dialogFragment2 == null) {
                return;
            }
            dialogFragment2.show(fragmentManager, "de.greenrobot.eventbus.error_dialog");
        }
    }

    @TargetApi(11)
    /* loaded from: classes4.dex */
    public static class HoneycombManagerFragment extends android.app.Fragment {
        private EventBus eventBus;

        @Override // android.app.Fragment
        public void onResume() {
            super.onResume();
            ErrorDialogManager.factory.config.getEventBus();
            throw null;
        }

        @Override // android.app.Fragment
        public void onPause() {
            this.eventBus.unregister(this);
            super.onPause();
        }
    }

    protected static void checkLogException(ThrowableFailureEvent throwableFailureEvent) {
        ErrorDialogConfig errorDialogConfig = factory.config;
        if (errorDialogConfig.logExceptions) {
            String str = errorDialogConfig.tagForLoggingExceptions;
            if (str == null) {
                str = EventBus.TAG;
            }
            Log.i(str, "Error dialog manager received exception", throwableFailureEvent.throwable);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isInExecutionScope(Object obj, ThrowableFailureEvent throwableFailureEvent) {
        if (throwableFailureEvent == null) {
            return true;
        }
        throwableFailureEvent.getExecutionScope();
        throw null;
    }
}
