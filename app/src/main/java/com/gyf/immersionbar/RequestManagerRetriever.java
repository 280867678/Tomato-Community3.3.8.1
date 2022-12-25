package com.gyf.immersionbar;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.p002v4.app.FragmentActivity;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes3.dex */
public class RequestManagerRetriever implements Handler.Callback {
    private Handler mHandler;
    private final Map<FragmentManager, RequestManagerFragment> mPendingFragments;
    private final Map<android.support.p002v4.app.FragmentManager, SupportRequestManagerFragment> mPendingSupportFragments;
    private String mTag;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class Holder {
        private static final RequestManagerRetriever INSTANCE = new RequestManagerRetriever();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static RequestManagerRetriever getInstance() {
        return Holder.INSTANCE;
    }

    private RequestManagerRetriever() {
        this.mTag = ImmersionBar.class.getName();
        this.mPendingFragments = new HashMap();
        this.mPendingSupportFragments = new HashMap();
        this.mHandler = new Handler(Looper.getMainLooper(), this);
    }

    public ImmersionBar get(Activity activity) {
        checkNotNull(activity, "activity is null");
        if (activity instanceof FragmentActivity) {
            android.support.p002v4.app.FragmentManager supportFragmentManager = ((FragmentActivity) activity).getSupportFragmentManager();
            return getSupportFragment(supportFragmentManager, this.mTag + activity.toString()).get(activity);
        }
        FragmentManager fragmentManager = activity.getFragmentManager();
        return getFragment(fragmentManager, this.mTag + activity.toString()).get(activity);
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(Message message) {
        int i = message.what;
        if (i == 1) {
            this.mPendingFragments.remove((FragmentManager) message.obj);
            return true;
        } else if (i != 2) {
            return false;
        } else {
            this.mPendingSupportFragments.remove((android.support.p002v4.app.FragmentManager) message.obj);
            return true;
        }
    }

    private RequestManagerFragment getFragment(FragmentManager fragmentManager, String str) {
        return getFragment(fragmentManager, str, false);
    }

    private RequestManagerFragment getFragment(FragmentManager fragmentManager, String str, boolean z) {
        RequestManagerFragment requestManagerFragment = (RequestManagerFragment) fragmentManager.findFragmentByTag(str);
        if (requestManagerFragment == null && (requestManagerFragment = this.mPendingFragments.get(fragmentManager)) == null) {
            if (z) {
                return null;
            }
            requestManagerFragment = new RequestManagerFragment();
            this.mPendingFragments.put(fragmentManager, requestManagerFragment);
            fragmentManager.beginTransaction().add(requestManagerFragment, str).commitAllowingStateLoss();
            this.mHandler.obtainMessage(1, fragmentManager).sendToTarget();
        }
        if (z) {
            fragmentManager.beginTransaction().remove(requestManagerFragment).commit();
            return null;
        }
        return requestManagerFragment;
    }

    private SupportRequestManagerFragment getSupportFragment(android.support.p002v4.app.FragmentManager fragmentManager, String str) {
        return getSupportFragment(fragmentManager, str, false);
    }

    private SupportRequestManagerFragment getSupportFragment(android.support.p002v4.app.FragmentManager fragmentManager, String str, boolean z) {
        SupportRequestManagerFragment supportRequestManagerFragment = (SupportRequestManagerFragment) fragmentManager.findFragmentByTag(str);
        if (supportRequestManagerFragment == null && (supportRequestManagerFragment = this.mPendingSupportFragments.get(fragmentManager)) == null) {
            if (z) {
                return null;
            }
            supportRequestManagerFragment = new SupportRequestManagerFragment();
            this.mPendingSupportFragments.put(fragmentManager, supportRequestManagerFragment);
            fragmentManager.beginTransaction().add(supportRequestManagerFragment, str).commitAllowingStateLoss();
            this.mHandler.obtainMessage(2, fragmentManager).sendToTarget();
        }
        if (z) {
            fragmentManager.beginTransaction().remove(supportRequestManagerFragment).commit();
            return null;
        }
        return supportRequestManagerFragment;
    }

    private static <T> void checkNotNull(@Nullable T t, @NonNull String str) {
        if (t != null) {
            return;
        }
        throw new NullPointerException(str);
    }
}
