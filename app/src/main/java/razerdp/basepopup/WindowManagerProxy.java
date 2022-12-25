package razerdp.basepopup;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import com.eclipsesource.p056v8.Platform;
import java.lang.ref.WeakReference;
import razerdp.util.log.PopupLog;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public final class WindowManagerProxy implements WindowManager {
    private static int statusBarHeight;
    private WeakReference<PopupDecorViewProxy> mPopupDecorViewProxy;
    private WeakReference<BasePopupHelper> mPopupHelper;
    private WindowManager mWindowManager;

    /* JADX INFO: Access modifiers changed from: package-private */
    public WindowManagerProxy(WindowManager windowManager) {
        this.mWindowManager = windowManager;
    }

    @Override // android.view.WindowManager
    public Display getDefaultDisplay() {
        WindowManager windowManager = this.mWindowManager;
        if (windowManager == null) {
            return null;
        }
        return windowManager.getDefaultDisplay();
    }

    @Override // android.view.WindowManager
    public void removeViewImmediate(View view) {
        Object[] objArr = new Object[1];
        StringBuilder sb = new StringBuilder();
        sb.append("WindowManager.removeViewImmediate  >>>  ");
        sb.append(view == null ? null : view.getClass().getSimpleName());
        objArr[0] = sb.toString();
        PopupLog.m25i("WindowManagerProxy", objArr);
        if (this.mWindowManager == null || view == null) {
            return;
        }
        checkStatusBarHeight(view.getContext());
        if (isPopupInnerDecorView(view) && getPopupDecorViewProxy() != null) {
            PopupDecorViewProxy popupDecorViewProxy = getPopupDecorViewProxy();
            if (Build.VERSION.SDK_INT >= 19 && !popupDecorViewProxy.isAttachedToWindow()) {
                return;
            }
            this.mWindowManager.removeViewImmediate(popupDecorViewProxy);
            this.mPopupDecorViewProxy.clear();
            this.mPopupDecorViewProxy = null;
            return;
        }
        this.mWindowManager.removeViewImmediate(view);
    }

    @Override // android.view.ViewManager
    public void addView(View view, ViewGroup.LayoutParams layoutParams) {
        Object[] objArr = new Object[1];
        StringBuilder sb = new StringBuilder();
        sb.append("WindowManager.addView  >>>  ");
        sb.append(view == null ? null : view.getClass().getSimpleName());
        objArr[0] = sb.toString();
        PopupLog.m25i("WindowManagerProxy", objArr);
        if (this.mWindowManager == null || view == null) {
            return;
        }
        checkStatusBarHeight(view.getContext());
        if (isPopupInnerDecorView(view)) {
            BasePopupHelper basePopupHelper = getBasePopupHelper();
            applyHelper(layoutParams, basePopupHelper);
            PopupDecorViewProxy create = PopupDecorViewProxy.create(view.getContext(), this, basePopupHelper);
            create.addPopupDecorView(view, (WindowManager.LayoutParams) layoutParams);
            this.mPopupDecorViewProxy = new WeakReference<>(create);
            WindowManager windowManager = this.mWindowManager;
            fitLayoutParamsPosition(create, layoutParams);
            windowManager.addView(create, layoutParams);
            return;
        }
        this.mWindowManager.addView(view, layoutParams);
    }

    private void applyHelper(ViewGroup.LayoutParams layoutParams, BasePopupHelper basePopupHelper) {
        if (!(layoutParams instanceof WindowManager.LayoutParams) || basePopupHelper == null) {
            return;
        }
        WindowManager.LayoutParams layoutParams2 = (WindowManager.LayoutParams) layoutParams;
        if (Build.VERSION.SDK_INT >= 28) {
            layoutParams2.layoutInDisplayCutoutMode = 0;
        }
        if (basePopupHelper.isOutSideTouchable()) {
            PopupLog.m25i("WindowManagerProxy", "applyHelper  >>>  不拦截事件");
            layoutParams2.flags |= 32;
            layoutParams2.flags |= 262144;
            if (!basePopupHelper.isClipToScreen()) {
                layoutParams2.flags |= 512;
            }
        }
        if (!basePopupHelper.isFullScreen()) {
            return;
        }
        PopupLog.m25i("WindowManagerProxy", "applyHelper  >>>  全屏");
        layoutParams2.flags |= 256;
        if (Build.VERSION.SDK_INT >= 28) {
            layoutParams2.layoutInDisplayCutoutMode = 1;
        }
        if (basePopupHelper.isOutSideTouchable()) {
            return;
        }
        layoutParams2.flags |= 512;
    }

    private ViewGroup.LayoutParams fitLayoutParamsPosition(PopupDecorViewProxy popupDecorViewProxy, ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof WindowManager.LayoutParams) {
            WindowManager.LayoutParams layoutParams2 = (WindowManager.LayoutParams) layoutParams;
            BasePopupHelper basePopupHelper = getBasePopupHelper();
            if (basePopupHelper != null) {
                if (basePopupHelper.getShowCount() > 1) {
                    layoutParams2.type = 1002;
                }
                if (!basePopupHelper.isOutSideTouchable()) {
                    layoutParams2.y = 0;
                    layoutParams2.x = 0;
                } else {
                    popupDecorViewProxy.fitWindowParams(layoutParams2);
                }
            }
            applyHelper(layoutParams2, basePopupHelper);
        }
        return layoutParams;
    }

    @Override // android.view.ViewManager
    public void updateViewLayout(View view, ViewGroup.LayoutParams layoutParams) {
        Object[] objArr = new Object[1];
        StringBuilder sb = new StringBuilder();
        sb.append("WindowManager.updateViewLayout  >>>  ");
        sb.append(view == null ? null : view.getClass().getSimpleName());
        objArr[0] = sb.toString();
        PopupLog.m25i("WindowManagerProxy", objArr);
        if (this.mWindowManager == null || view == null) {
            return;
        }
        checkStatusBarHeight(view.getContext());
        if ((isPopupInnerDecorView(view) && getPopupDecorViewProxy() != null) || view == getPopupDecorViewProxy()) {
            PopupDecorViewProxy popupDecorViewProxy = getPopupDecorViewProxy();
            WindowManager windowManager = this.mWindowManager;
            fitLayoutParamsPosition(popupDecorViewProxy, layoutParams);
            windowManager.updateViewLayout(popupDecorViewProxy, layoutParams);
            return;
        }
        this.mWindowManager.updateViewLayout(view, layoutParams);
    }

    public void updateViewLayoutOriginal(View view, ViewGroup.LayoutParams layoutParams) {
        this.mWindowManager.updateViewLayout(view, layoutParams);
    }

    public void updateFocus(boolean z) {
        if (this.mWindowManager == null || getPopupDecorViewProxy() == null) {
            return;
        }
        PopupDecorViewProxy popupDecorViewProxy = getPopupDecorViewProxy();
        ViewGroup.LayoutParams layoutParams = popupDecorViewProxy.getLayoutParams();
        if (layoutParams instanceof WindowManager.LayoutParams) {
            if (z) {
                ((WindowManager.LayoutParams) layoutParams).flags &= -9;
            } else {
                ((WindowManager.LayoutParams) layoutParams).flags |= 8;
            }
        }
        this.mWindowManager.updateViewLayout(popupDecorViewProxy, layoutParams);
    }

    public void update() {
        if (this.mWindowManager == null || getPopupDecorViewProxy() == null) {
            return;
        }
        getPopupDecorViewProxy().updateLayout();
    }

    @Override // android.view.ViewManager
    public void removeView(View view) {
        Object[] objArr = new Object[1];
        StringBuilder sb = new StringBuilder();
        sb.append("WindowManager.removeView  >>>  ");
        sb.append(view == null ? null : view.getClass().getSimpleName());
        objArr[0] = sb.toString();
        PopupLog.m25i("WindowManagerProxy", objArr);
        if (this.mWindowManager == null || view == null) {
            return;
        }
        checkStatusBarHeight(view.getContext());
        if (isPopupInnerDecorView(view) && getPopupDecorViewProxy() != null) {
            this.mWindowManager.removeView(getPopupDecorViewProxy());
            this.mPopupDecorViewProxy.clear();
            this.mPopupDecorViewProxy = null;
            return;
        }
        this.mWindowManager.removeView(view);
    }

    public void clear() {
        try {
            removeViewImmediate(this.mPopupDecorViewProxy.get());
            this.mPopupDecorViewProxy.clear();
        } catch (Exception unused) {
        }
    }

    private boolean isPopupInnerDecorView(View view) {
        if (view == null) {
            return false;
        }
        String simpleName = view.getClass().getSimpleName();
        return TextUtils.equals(simpleName, "PopupDecorView") || TextUtils.equals(simpleName, "PopupViewContainer");
    }

    private PopupDecorViewProxy getPopupDecorViewProxy() {
        WeakReference<PopupDecorViewProxy> weakReference = this.mPopupDecorViewProxy;
        if (weakReference == null) {
            return null;
        }
        return weakReference.get();
    }

    private BasePopupHelper getBasePopupHelper() {
        WeakReference<BasePopupHelper> weakReference = this.mPopupHelper;
        if (weakReference == null) {
            return null;
        }
        return weakReference.get();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void attachPopupHelper(BasePopupHelper basePopupHelper) {
        this.mPopupHelper = new WeakReference<>(basePopupHelper);
    }

    private void checkStatusBarHeight(Context context) {
        if (statusBarHeight != 0 || context == null) {
            return;
        }
        int i = 0;
        int identifier = context.getResources().getIdentifier("status_bar_height", "dimen", Platform.ANDROID);
        if (identifier > 0) {
            i = context.getResources().getDimensionPixelSize(identifier);
        }
        statusBarHeight = i;
    }
}
