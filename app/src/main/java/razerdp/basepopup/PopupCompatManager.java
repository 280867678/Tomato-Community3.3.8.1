package razerdp.basepopup;

import android.app.Activity;
import android.util.Log;
import android.view.View;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public final class PopupCompatManager {
    private static final PopupWindowImpl IMPL = new Impl();

    /* loaded from: classes4.dex */
    interface PopupWindowImpl {
        void clear(BasePopupWindowProxy basePopupWindowProxy);

        void showAsDropDown(BasePopupWindowProxy basePopupWindowProxy, View view, int i, int i2, int i3);

        void showAtLocation(BasePopupWindowProxy basePopupWindowProxy, View view, int i, int i2, int i3);
    }

    public static void showAsDropDown(BasePopupWindowProxy basePopupWindowProxy, View view, int i, int i2, int i3) {
        PopupWindowImpl popupWindowImpl = IMPL;
        if (popupWindowImpl != null) {
            popupWindowImpl.showAsDropDown(basePopupWindowProxy, view, i, i2, i3);
        }
    }

    public static void showAtLocation(BasePopupWindowProxy basePopupWindowProxy, View view, int i, int i2, int i3) {
        PopupWindowImpl popupWindowImpl = IMPL;
        if (popupWindowImpl != null) {
            popupWindowImpl.showAtLocation(basePopupWindowProxy, view, i, i2, i3);
        }
    }

    public static void clear(BasePopupWindowProxy basePopupWindowProxy) {
        PopupWindowImpl popupWindowImpl = IMPL;
        if (popupWindowImpl != null) {
            popupWindowImpl.clear(basePopupWindowProxy);
        }
    }

    /* loaded from: classes4.dex */
    static abstract class BaseImpl implements PopupWindowImpl {
        @Override // razerdp.basepopup.PopupCompatManager.PopupWindowImpl
        public void clear(BasePopupWindowProxy basePopupWindowProxy) {
        }

        abstract void showAsDropDownImpl(Activity activity, BasePopupWindowProxy basePopupWindowProxy, View view, int i, int i2, int i3);

        abstract void showAtLocationImpl(Activity activity, BasePopupWindowProxy basePopupWindowProxy, View view, int i, int i2, int i3);

        BaseImpl() {
        }

        @Override // razerdp.basepopup.PopupCompatManager.PopupWindowImpl
        public void showAsDropDown(BasePopupWindowProxy basePopupWindowProxy, View view, int i, int i2, int i3) {
            if (isPopupShowing(basePopupWindowProxy)) {
                return;
            }
            Activity scanForActivity = basePopupWindowProxy.scanForActivity(view.getContext());
            if (scanForActivity == null) {
                Log.e("PopupCompatManager", "please make sure that context is instance of activity");
                return;
            }
            onBeforeShowExec(basePopupWindowProxy, scanForActivity);
            showAsDropDownImpl(scanForActivity, basePopupWindowProxy, view, i, i2, i3);
            onAfterShowExec(basePopupWindowProxy, scanForActivity);
        }

        @Override // razerdp.basepopup.PopupCompatManager.PopupWindowImpl
        public void showAtLocation(BasePopupWindowProxy basePopupWindowProxy, View view, int i, int i2, int i3) {
            if (isPopupShowing(basePopupWindowProxy)) {
                return;
            }
            Activity scanForActivity = basePopupWindowProxy.scanForActivity(view.getContext());
            if (scanForActivity == null) {
                Log.e("PopupCompatManager", "please make sure that context is instance of activity");
                return;
            }
            onBeforeShowExec(basePopupWindowProxy, scanForActivity);
            showAtLocationImpl(scanForActivity, basePopupWindowProxy, view, i, i2, i3);
            onAfterShowExec(basePopupWindowProxy, scanForActivity);
        }

        protected void onBeforeShowExec(BasePopupWindowProxy basePopupWindowProxy, Activity activity) {
            if (PopupCompatManager.needListenUiVisibilityChange(activity)) {
                basePopupWindowProxy.handleFullScreenFocusable();
            }
        }

        protected void onAfterShowExec(BasePopupWindowProxy basePopupWindowProxy, Activity activity) {
            if (basePopupWindowProxy.isHandledFullScreen()) {
                basePopupWindowProxy.getContentView().setSystemUiVisibility(5894);
                basePopupWindowProxy.restoreFocusable();
            }
        }

        boolean isPopupShowing(BasePopupWindowProxy basePopupWindowProxy) {
            return basePopupWindowProxy != null && basePopupWindowProxy.callSuperIsShowing();
        }
    }

    /* loaded from: classes4.dex */
    static class Impl extends BaseImpl {
        int[] anchorLocation = new int[2];

        Impl() {
        }

        @Override // razerdp.basepopup.PopupCompatManager.BaseImpl
        void showAsDropDownImpl(Activity activity, BasePopupWindowProxy basePopupWindowProxy, View view, int i, int i2, int i3) {
            if (view != null) {
                view.getLocationInWindow(this.anchorLocation);
                int[] iArr = this.anchorLocation;
                int i4 = iArr[0];
                i2 = iArr[1] + view.getHeight();
                i = i4;
            }
            basePopupWindowProxy.callSuperShowAtLocation(view, 0, i, i2);
        }

        @Override // razerdp.basepopup.PopupCompatManager.BaseImpl
        void showAtLocationImpl(Activity activity, BasePopupWindowProxy basePopupWindowProxy, View view, int i, int i2, int i3) {
            basePopupWindowProxy.callSuperShowAtLocation(view, i, i2, i3);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean needListenUiVisibilityChange(Activity activity) {
        if (activity == null) {
            return false;
        }
        try {
            View decorView = activity.getWindow().getDecorView();
            int i = activity.getWindow().getAttributes().flags;
            int windowSystemUiVisibility = decorView.getWindowSystemUiVisibility();
            if ((i & 1024) == 0) {
                return false;
            }
            return ((windowSystemUiVisibility & 2) == 0 && (windowSystemUiVisibility & 512) == 0) ? false : true;
        } catch (Exception unused) {
            return false;
        }
    }
}
