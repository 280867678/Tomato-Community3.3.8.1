package razerdp.basepopup;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import java.lang.reflect.Field;
import razerdp.util.PopupUtils;
import razerdp.util.log.PopupLog;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public abstract class BasePopupWindowProxy extends PopupWindow {
    private boolean isHandledFullScreen;
    private BasePopupHelper mHelper;
    private WindowManagerProxy mWindowManagerProxy;
    private boolean oldFocusable = true;

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract boolean callSuperIsShowing();

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void callSuperShowAtLocation(View view, int i, int i2, int i3);

    public BasePopupWindowProxy(View view, int i, int i2, BasePopupHelper basePopupHelper) {
        super(view, i, i2);
        this.mHelper = basePopupHelper;
        init(view.getContext());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void attachPopupHelper(BasePopupHelper basePopupHelper) {
        if (this.mWindowManagerProxy == null) {
            tryToProxyWindowManagerMethod(this);
        }
        this.mWindowManagerProxy.attachPopupHelper(basePopupHelper);
    }

    private void init(Context context) {
        setFocusable(true);
        setOutsideTouchable(true);
        setBackgroundDrawable(new ColorDrawable());
        tryToProxyWindowManagerMethod(this);
    }

    @Override // android.widget.PopupWindow
    public void setContentView(View view) {
        super.setContentView(view);
        tryToProxyWindowManagerMethod(this);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Activity scanForActivity(Context context) {
        return PopupUtils.scanForActivity(context, 15);
    }

    @Override // android.widget.PopupWindow
    public void dismiss() {
        BasePopupHelper basePopupHelper = this.mHelper;
        if (basePopupHelper != null && basePopupHelper.onBeforeDismiss() && this.mHelper.callDismissAtOnce()) {
            callSuperDismiss();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void callSuperDismiss() {
        try {
            try {
                super.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } finally {
            clear();
        }
    }

    private void clear() {
        WindowManagerProxy windowManagerProxy = this.mWindowManagerProxy;
        if (windowManagerProxy != null) {
            windowManagerProxy.clear();
        }
        PopupUtils.clearViewFromParent(getContentView());
        PopupCompatManager.clear(this);
    }

    private void tryToProxyWindowManagerMethod(PopupWindow popupWindow) {
        if (this.mHelper == null || this.mWindowManagerProxy != null) {
            return;
        }
        PopupLog.m26i("cur api >> " + Build.VERSION.SDK_INT);
        troToProxyWindowManagerMethodBeforeP(popupWindow);
    }

    private void troToProxyWindowManagerMethodOverP(PopupWindow popupWindow) {
        try {
            WindowManager popupWindowManager = PopupReflectionHelper.getInstance().getPopupWindowManager(popupWindow);
            if (popupWindowManager == null) {
                return;
            }
            this.mWindowManagerProxy = new WindowManagerProxy(popupWindowManager);
            PopupReflectionHelper.getInstance().preInject(popupWindow, this.mWindowManagerProxy);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void troToProxyWindowManagerMethodBeforeP(PopupWindow popupWindow) {
        try {
            Field declaredField = PopupWindow.class.getDeclaredField("mWindowManager");
            declaredField.setAccessible(true);
            WindowManager windowManager = (WindowManager) declaredField.get(popupWindow);
            if (windowManager == null) {
                return;
            }
            this.mWindowManagerProxy = new WindowManagerProxy(windowManager);
            declaredField.set(popupWindow, this.mWindowManagerProxy);
            PopupLog.m25i("BasePopupWindowProxy", "尝试代理WindowManager成功");
            Field declaredField2 = PopupWindow.class.getDeclaredField("mOnScrollChangedListener");
            declaredField2.setAccessible(true);
            declaredField2.set(popupWindow, null);
        } catch (NoSuchFieldException e) {
            if (Build.VERSION.SDK_INT >= 27) {
                troToProxyWindowManagerMethodOverP(popupWindow);
            } else {
                e.printStackTrace();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // android.widget.PopupWindow
    public void update() {
        try {
            if (this.mHelper != null) {
                if (this.mHelper.isOutSideTouchable()) {
                    super.update(this.mHelper.getAnchorX(), this.mHelper.getAnchorY() + this.mHelper.getAnchorHeight(), this.mHelper.getPopupViewWidth(), this.mHelper.getPopupViewHeight(), true);
                }
                if (this.mWindowManagerProxy == null) {
                    return;
                }
                this.mWindowManagerProxy.update();
                return;
            }
            super.update();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean isHandledFullScreen() {
        return this.isHandledFullScreen;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void handleFullScreenFocusable() {
        this.oldFocusable = isFocusable();
        setFocusable(false);
        this.isHandledFullScreen = true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void restoreFocusable() {
        WindowManagerProxy windowManagerProxy = this.mWindowManagerProxy;
        if (windowManagerProxy != null) {
            windowManagerProxy.updateFocus(this.oldFocusable);
        }
        this.isHandledFullScreen = false;
    }
}
