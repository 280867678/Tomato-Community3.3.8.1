package android.support.design.widget;

import android.app.Dialog;
import android.os.Bundle;
import android.support.p005v7.app.AppCompatDialogFragment;

/* loaded from: classes2.dex */
public class BottomSheetDialogFragment extends AppCompatDialogFragment {
    @Override // android.support.p005v7.app.AppCompatDialogFragment, android.support.p002v4.app.DialogFragment
    public Dialog onCreateDialog(Bundle bundle) {
        return new BottomSheetDialog(getContext(), getTheme());
    }
}
