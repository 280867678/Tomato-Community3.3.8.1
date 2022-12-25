package razerdp.basepopup;

import android.app.Activity;
import android.view.View;

/* loaded from: classes4.dex */
public interface BasePopupSupporter {
    BasePopupWindow attachLifeCycle(BasePopupWindow basePopupWindow, Object obj);

    View findDecorView(BasePopupWindow basePopupWindow, Activity activity);

    BasePopupWindow removeLifeCycle(BasePopupWindow basePopupWindow, Object obj);
}
