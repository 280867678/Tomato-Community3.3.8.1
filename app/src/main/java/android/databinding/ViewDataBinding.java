package android.databinding;

import android.annotation.TargetApi;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.databinding.CallbackRegistry;
import android.os.Build;
import android.os.Handler;
import android.view.Choreographer;
import android.view.View;
import com.android.databinding.library.R$id;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/* loaded from: classes2.dex */
public abstract class ViewDataBinding extends BaseObservable {
    static int SDK_INT = Build.VERSION.SDK_INT;
    private static final boolean USE_CHOREOGRAPHER;
    private Choreographer mChoreographer;
    private ViewDataBinding mContainingBinding;
    private final Choreographer.FrameCallback mFrameCallback;
    private boolean mIsExecutingPendingBindings;
    private LifecycleOwner mLifecycleOwner;
    private boolean mPendingRebind;
    private CallbackRegistry<OnRebindCallback, ViewDataBinding, Void> mRebindCallbacks;
    private boolean mRebindHalted;
    private final Runnable mRebindRunnable;
    private Handler mUIThreadHandler;

    /* loaded from: classes.dex */
    private interface CreateWeakListener {
    }

    protected abstract void executeBindings();

    public abstract boolean hasPendingBindings();

    static {
        USE_CHOREOGRAPHER = SDK_INT >= 16;
        new CreateWeakListener() { // from class: android.databinding.ViewDataBinding.1
        };
        new CreateWeakListener() { // from class: android.databinding.ViewDataBinding.2
        };
        new CreateWeakListener() { // from class: android.databinding.ViewDataBinding.3
        };
        new CreateWeakListener() { // from class: android.databinding.ViewDataBinding.4
        };
        new CallbackRegistry.NotifierCallback<OnRebindCallback, ViewDataBinding, Void>() { // from class: android.databinding.ViewDataBinding.5
            @Override // android.databinding.CallbackRegistry.NotifierCallback
            public void onNotifyCallback(OnRebindCallback onRebindCallback, ViewDataBinding viewDataBinding, int i, Void r4) {
                if (i == 1) {
                    if (onRebindCallback.onPreBind(viewDataBinding)) {
                        return;
                    }
                    viewDataBinding.mRebindHalted = true;
                } else if (i == 2) {
                    onRebindCallback.onCanceled(viewDataBinding);
                } else if (i != 3) {
                } else {
                    onRebindCallback.onBound(viewDataBinding);
                }
            }
        };
        new ReferenceQueue();
        if (Build.VERSION.SDK_INT < 19) {
            return;
        }
        new View.OnAttachStateChangeListener() { // from class: android.databinding.ViewDataBinding.6
            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewDetachedFromWindow(View view) {
            }

            @Override // android.view.View.OnAttachStateChangeListener
            @TargetApi(19)
            public void onViewAttachedToWindow(View view) {
                ViewDataBinding.getBinding(view).mRebindRunnable.run();
                view.removeOnAttachStateChangeListener(this);
            }
        };
    }

    public void executePendingBindings() {
        ViewDataBinding viewDataBinding = this.mContainingBinding;
        if (viewDataBinding == null) {
            executeBindingsInternal();
        } else {
            viewDataBinding.executePendingBindings();
        }
    }

    private void executeBindingsInternal() {
        if (this.mIsExecutingPendingBindings) {
            requestRebind();
        } else if (!hasPendingBindings()) {
        } else {
            this.mIsExecutingPendingBindings = true;
            this.mRebindHalted = false;
            CallbackRegistry<OnRebindCallback, ViewDataBinding, Void> callbackRegistry = this.mRebindCallbacks;
            if (callbackRegistry != null) {
                callbackRegistry.notifyCallbacks(this, 1, null);
                if (this.mRebindHalted) {
                    this.mRebindCallbacks.notifyCallbacks(this, 2, null);
                }
            }
            if (!this.mRebindHalted) {
                executeBindings();
                CallbackRegistry<OnRebindCallback, ViewDataBinding, Void> callbackRegistry2 = this.mRebindCallbacks;
                if (callbackRegistry2 != null) {
                    callbackRegistry2.notifyCallbacks(this, 3, null);
                }
            }
            this.mIsExecutingPendingBindings = false;
        }
    }

    static ViewDataBinding getBinding(View view) {
        if (view != null) {
            return (ViewDataBinding) view.getTag(R$id.dataBinding);
        }
        return null;
    }

    protected void requestRebind() {
        ViewDataBinding viewDataBinding = this.mContainingBinding;
        if (viewDataBinding != null) {
            viewDataBinding.requestRebind();
            return;
        }
        LifecycleOwner lifecycleOwner = this.mLifecycleOwner;
        if (lifecycleOwner != null && !lifecycleOwner.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            return;
        }
        synchronized (this) {
            if (this.mPendingRebind) {
                return;
            }
            this.mPendingRebind = true;
            if (USE_CHOREOGRAPHER) {
                this.mChoreographer.postFrameCallback(this.mFrameCallback);
            } else {
                this.mUIThreadHandler.post(this.mRebindRunnable);
            }
        }
    }

    /* loaded from: classes2.dex */
    static class OnStartListener implements LifecycleObserver {
        final WeakReference<ViewDataBinding> mBinding;

        @OnLifecycleEvent(Lifecycle.Event.ON_START)
        public void onStart() {
            ViewDataBinding viewDataBinding = this.mBinding.get();
            if (viewDataBinding != null) {
                viewDataBinding.executePendingBindings();
            }
        }
    }
}
