package android.support.design.expandable;

import android.support.annotation.IdRes;

/* loaded from: classes2.dex */
public interface ExpandableTransformationWidget extends ExpandableWidget {
    @IdRes
    int getExpandedComponentIdHint();

    void setExpandedComponentIdHint(@IdRes int i);
}
