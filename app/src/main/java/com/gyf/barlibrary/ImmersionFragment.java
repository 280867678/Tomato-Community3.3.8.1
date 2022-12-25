package com.gyf.barlibrary;

import android.support.p002v4.app.Fragment;

@Deprecated
/* loaded from: classes3.dex */
public abstract class ImmersionFragment extends Fragment {
    @Deprecated
    protected boolean immersionEnabled() {
        return true;
    }

    @Deprecated
    protected abstract void immersionInit();

    @Override // android.support.p002v4.app.Fragment
    public void setUserVisibleHint(boolean z) {
        super.setUserVisibleHint(z);
        if (!z || !isResumed()) {
            return;
        }
        onResume();
    }

    @Override // android.support.p002v4.app.Fragment
    public void onResume() {
        super.onResume();
        if (!getUserVisibleHint() || !immersionEnabled()) {
            return;
        }
        immersionInit();
    }
}
