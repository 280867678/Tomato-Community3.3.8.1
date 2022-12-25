package razerdp.interceptor;

import android.view.View;
import android.widget.PopupWindow;
import razerdp.basepopup.BasePopupWindow;

/* loaded from: classes4.dex */
public interface PopupWindowEventInterceptor<P extends BasePopupWindow> {
    int onKeyboardChangeResult(int i, boolean z, int i2);

    boolean onPreMeasurePopupView(P p, View view, int i, int i2);

    boolean onTryToShowPopup(P p, PopupWindow popupWindow, View view, int i, int i2, int i3);
}
