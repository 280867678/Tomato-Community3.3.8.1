package razerdp.widget;

import android.view.View;

/* loaded from: classes4.dex */
public abstract class OnQuickPopupClickListenerWrapper implements View.OnClickListener {
    QuickPopup mQuickPopup;

    public abstract void onClick(QuickPopup quickPopup, View view);

    @Override // android.view.View.OnClickListener
    @Deprecated
    public void onClick(View view) {
        onClick(this.mQuickPopup, view);
    }
}
