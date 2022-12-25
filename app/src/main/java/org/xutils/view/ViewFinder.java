package org.xutils.view;

import android.app.Activity;
import android.view.View;

/* loaded from: classes4.dex */
final class ViewFinder {
    private Activity activity;
    private View view;

    public ViewFinder(View view) {
        this.view = view;
    }

    public ViewFinder(Activity activity) {
        this.activity = activity;
    }

    public View findViewById(int i) {
        View view = this.view;
        if (view != null) {
            return view.findViewById(i);
        }
        Activity activity = this.activity;
        if (activity == null) {
            return null;
        }
        return activity.findViewById(i);
    }

    public View findViewByInfo(ViewInfo viewInfo) {
        return findViewById(viewInfo.value, viewInfo.parentId);
    }

    public View findViewById(int i, int i2) {
        View findViewById = i2 > 0 ? findViewById(i2) : null;
        if (findViewById != null) {
            return findViewById.findViewById(i);
        }
        return findViewById(i);
    }
}
