package razerdp.basepopup;

import android.app.Activity;
import android.support.p002v4.app.DialogFragment;
import android.support.p002v4.app.Fragment;
import android.support.p002v4.app.FragmentActivity;
import android.view.View;

/* loaded from: classes4.dex */
public class BasePopupSupporterSupport implements BasePopupSupporter {
    @Override // razerdp.basepopup.BasePopupSupporter
    public BasePopupWindow attachLifeCycle(BasePopupWindow basePopupWindow, Object obj) {
        return basePopupWindow;
    }

    @Override // razerdp.basepopup.BasePopupSupporter
    public BasePopupWindow removeLifeCycle(BasePopupWindow basePopupWindow, Object obj) {
        return basePopupWindow;
    }

    @Override // razerdp.basepopup.BasePopupSupporter
    public View findDecorView(BasePopupWindow basePopupWindow, Activity activity) {
        if (activity instanceof FragmentActivity) {
            try {
                for (Fragment fragment : ((FragmentActivity) activity).getSupportFragmentManager().getFragments()) {
                    if (fragment instanceof DialogFragment) {
                        DialogFragment dialogFragment = (DialogFragment) fragment;
                        if (dialogFragment.getDialog() != null && dialogFragment.getDialog().isShowing() && !dialogFragment.isRemoving()) {
                            return dialogFragment.getView();
                        }
                    }
                }
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }
}
