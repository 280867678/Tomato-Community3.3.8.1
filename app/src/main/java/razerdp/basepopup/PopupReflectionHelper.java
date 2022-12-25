package razerdp.basepopup;

import android.view.WindowManager;
import android.widget.PopupWindow;
import java.lang.reflect.Field;
import razerdp.util.UnsafeHelper;
import razerdp.util.log.PopupLog;

/* loaded from: classes4.dex */
final class PopupReflectionHelper {

    /* loaded from: classes4.dex */
    private static class PopupReflectionHelperHolder {
        private static PopupReflectionHelper instance = new PopupReflectionHelper();
    }

    private PopupReflectionHelper() {
        try {
            long objectFieldOffset = UnsafeHelper.objectFieldOffset(new InnerHackClazz().getClass().getDeclaredField("classLoader"));
            UnsafeHelper.putObject(getClass(), objectFieldOffset, null);
            PopupLog.m26i("绕开android p success,inject offset >>> " + objectFieldOffset);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static PopupReflectionHelper getInstance() {
        return PopupReflectionHelperHolder.instance;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public WindowManager getPopupWindowManager(PopupWindow popupWindow) throws Exception {
        if (popupWindow == null) {
            return null;
        }
        Field declaredField = PopupWindow.class.getDeclaredField("mWindowManager");
        declaredField.setAccessible(true);
        return (WindowManager) declaredField.get(popupWindow);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void preInject(PopupWindow popupWindow, WindowManager windowManager) throws Exception {
        if (popupWindow == null || windowManager == null) {
            return;
        }
        Field declaredField = PopupWindow.class.getDeclaredField("mWindowManager");
        declaredField.setAccessible(true);
        declaredField.set(popupWindow, windowManager);
        Field declaredField2 = PopupWindow.class.getDeclaredField("mOnScrollChangedListener");
        declaredField2.setAccessible(true);
        declaredField2.set(popupWindow, null);
    }

    /* loaded from: classes4.dex */
    private static class InnerHackClazz {
        private InnerHackClazz() {
        }
    }
}
