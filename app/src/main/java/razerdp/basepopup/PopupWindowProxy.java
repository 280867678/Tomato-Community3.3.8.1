package razerdp.basepopup;

import android.view.View;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class PopupWindowProxy extends BasePopupWindowProxy {
    public PopupWindowProxy(View view, int i, int i2, BasePopupHelper basePopupHelper) {
        super(view, i, i2, basePopupHelper);
    }

    public void showAsDropDownProxy(View view, int i, int i2, int i3) {
        PopupCompatManager.showAsDropDown(this, view, i, i2, i3);
    }

    public void showAtLocationProxy(View view, int i, int i2, int i3) {
        PopupCompatManager.showAtLocation(this, view, i, i2, i3);
    }

    @Override // razerdp.basepopup.BasePopupWindowProxy
    public void callSuperShowAtLocation(View view, int i, int i2, int i3) {
        super.showAtLocation(view, i, i2, i3);
    }

    @Override // razerdp.basepopup.BasePopupWindowProxy
    public boolean callSuperIsShowing() {
        return super.isShowing();
    }
}
