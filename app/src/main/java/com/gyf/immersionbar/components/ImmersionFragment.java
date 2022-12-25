package com.gyf.immersionbar.components;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.p002v4.app.Fragment;

/* loaded from: classes3.dex */
public abstract class ImmersionFragment extends Fragment implements ImmersionOwner {
    private ImmersionProxy mImmersionProxy = new ImmersionProxy(this);

    @Override // com.gyf.immersionbar.components.ImmersionOwner
    public boolean immersionBarEnabled() {
        return true;
    }

    @Override // com.gyf.immersionbar.components.ImmersionOwner
    public void onInvisible() {
    }

    @Override // com.gyf.immersionbar.components.ImmersionOwner
    public void onLazyAfterView() {
    }

    @Override // com.gyf.immersionbar.components.ImmersionOwner
    public void onLazyBeforeView() {
    }

    @Override // com.gyf.immersionbar.components.ImmersionOwner
    public void onVisible() {
    }

    @Override // android.support.p002v4.app.Fragment
    public void setUserVisibleHint(boolean z) {
        super.setUserVisibleHint(z);
        this.mImmersionProxy.setUserVisibleHint(z);
    }

    @Override // android.support.p002v4.app.Fragment
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        this.mImmersionProxy.onCreate(bundle);
    }

    @Override // android.support.p002v4.app.Fragment
    public void onActivityCreated(@Nullable Bundle bundle) {
        super.onActivityCreated(bundle);
        this.mImmersionProxy.onActivityCreated(bundle);
    }

    @Override // android.support.p002v4.app.Fragment
    public void onResume() {
        super.onResume();
        this.mImmersionProxy.onResume();
    }

    @Override // android.support.p002v4.app.Fragment
    public void onPause() {
        super.onPause();
        this.mImmersionProxy.onPause();
    }

    @Override // android.support.p002v4.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        this.mImmersionProxy.onDestroy();
    }

    @Override // android.support.p002v4.app.Fragment
    public void onHiddenChanged(boolean z) {
        super.onHiddenChanged(z);
        this.mImmersionProxy.onHiddenChanged(z);
    }

    @Override // android.support.p002v4.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mImmersionProxy.onConfigurationChanged(configuration);
    }
}
