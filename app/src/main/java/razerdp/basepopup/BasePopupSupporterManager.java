package razerdp.basepopup;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import razerdp.util.PopupUtils;
import razerdp.util.log.PopupLog;

/* loaded from: classes4.dex */
public final class BasePopupSupporterManager {
    private int account;
    private WeakReference<Activity> mTopActivity;
    BasePopupSupporterProxy proxy;

    static /* synthetic */ int access$208(BasePopupSupporterManager basePopupSupporterManager) {
        int i = basePopupSupporterManager.account;
        basePopupSupporterManager.account = i + 1;
        return i;
    }

    static /* synthetic */ int access$210(BasePopupSupporterManager basePopupSupporterManager) {
        int i = basePopupSupporterManager.account;
        basePopupSupporterManager.account = i - 1;
        return i;
    }

    /* loaded from: classes4.dex */
    class BasePopupSupporterProxy implements BasePopupSupporter {
        private List<BasePopupSupporter> IMPL = new ArrayList();

        BasePopupSupporterProxy(BasePopupSupporterManager basePopupSupporterManager, Context context) {
            try {
                if (basePopupSupporterManager.isClassExist("razerdp.basepopup.BasePopupSupporterSupport")) {
                    this.IMPL.add((BasePopupSupporter) Class.forName("razerdp.basepopup.BasePopupSupporterSupport").newInstance());
                }
                if (basePopupSupporterManager.isClassExist("razerdp.basepopup.BasePopupSupporterLifeCycle")) {
                    this.IMPL.add((BasePopupSupporter) Class.forName("razerdp.basepopup.BasePopupSupporterLifeCycle").newInstance());
                }
                if (basePopupSupporterManager.isClassExist("razerdp.basepopup.BasePopupSupporterX")) {
                    this.IMPL.add((BasePopupSupporter) Class.forName("razerdp.basepopup.BasePopupSupporterX").newInstance());
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e2) {
                e2.printStackTrace();
            } catch (InstantiationException e3) {
                e3.printStackTrace();
            }
            PopupLog.m26i(this.IMPL);
        }

        @Override // razerdp.basepopup.BasePopupSupporter
        public View findDecorView(BasePopupWindow basePopupWindow, Activity activity) {
            if (PopupUtils.isListEmpty(this.IMPL)) {
                return null;
            }
            for (BasePopupSupporter basePopupSupporter : this.IMPL) {
                View findDecorView = basePopupSupporter.findDecorView(basePopupWindow, activity);
                if (findDecorView != null) {
                    return findDecorView;
                }
            }
            return null;
        }

        @Override // razerdp.basepopup.BasePopupSupporter
        public BasePopupWindow attachLifeCycle(BasePopupWindow basePopupWindow, Object obj) {
            if (PopupUtils.isListEmpty(this.IMPL)) {
                return null;
            }
            for (BasePopupSupporter basePopupSupporter : this.IMPL) {
                if (basePopupWindow.lifeCycleObserver != null) {
                    return basePopupWindow;
                }
                basePopupSupporter.attachLifeCycle(basePopupWindow, obj);
            }
            return basePopupWindow;
        }

        @Override // razerdp.basepopup.BasePopupSupporter
        public BasePopupWindow removeLifeCycle(BasePopupWindow basePopupWindow, Object obj) {
            if (PopupUtils.isListEmpty(this.IMPL)) {
                return null;
            }
            for (BasePopupSupporter basePopupSupporter : this.IMPL) {
                if (basePopupWindow.lifeCycleObserver == null) {
                    return basePopupWindow;
                }
                basePopupSupporter.removeLifeCycle(basePopupWindow, obj);
            }
            return basePopupWindow;
        }
    }

    /* loaded from: classes4.dex */
    private static class SingleTonHolder {
        private static BasePopupSupporterManager INSTANCE = new BasePopupSupporterManager();
    }

    private BasePopupSupporterManager() {
        this.account = 0;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void init(Context context) {
        if (this.proxy != null) {
            return;
        }
        if (context instanceof Application) {
            regLifeCallback((Application) context);
        } else {
            regLifeCallback((Application) context.getApplicationContext());
        }
        this.proxy = new BasePopupSupporterProxy(this, context);
    }

    public Activity getTopActivity() {
        WeakReference<Activity> weakReference = this.mTopActivity;
        if (weakReference == null) {
            return null;
        }
        return weakReference.get();
    }

    private void regLifeCallback(Application application) {
        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() { // from class: razerdp.basepopup.BasePopupSupporterManager.1
            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityCreated(Activity activity, Bundle bundle) {
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityDestroyed(Activity activity) {
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityPaused(Activity activity) {
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityStarted(Activity activity) {
                BasePopupSupporterManager.access$208(BasePopupSupporterManager.this);
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityResumed(Activity activity) {
                BasePopupSupporterManager.this.mTopActivity = new WeakReference(activity);
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityStopped(Activity activity) {
                BasePopupSupporterManager.access$210(BasePopupSupporterManager.this);
            }
        });
    }

    public static BasePopupSupporterManager getInstance() {
        return SingleTonHolder.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isClassExist(String str) {
        try {
            Class.forName(str);
            return true;
        } catch (Exception unused) {
            return false;
        }
    }
}
