package com.gyf.immersionbar.components;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.p002v4.app.Fragment;

/* loaded from: classes3.dex */
public class ImmersionProxy {
    private Fragment mFragment;
    private ImmersionOwner mImmersionOwner;
    private boolean mIsActivityCreated;
    private boolean mIsLazyAfterView;
    private boolean mIsLazyBeforeView;

    public ImmersionProxy(Fragment fragment) {
        this.mFragment = fragment;
        if (fragment instanceof ImmersionOwner) {
            this.mImmersionOwner = (ImmersionOwner) fragment;
            return;
        }
        throw new IllegalArgumentException("Fragment请实现ImmersionOwner接口");
    }

    public void setUserVisibleHint(boolean z) {
        Fragment fragment = this.mFragment;
        if (fragment != null) {
            if (fragment.getUserVisibleHint()) {
                if (!this.mIsLazyBeforeView) {
                    this.mImmersionOwner.onLazyBeforeView();
                    this.mIsLazyBeforeView = true;
                }
                if (!this.mIsActivityCreated || !this.mFragment.getUserVisibleHint()) {
                    return;
                }
                if (this.mImmersionOwner.immersionBarEnabled()) {
                    this.mImmersionOwner.initImmersionBar();
                }
                if (!this.mIsLazyAfterView) {
                    this.mImmersionOwner.onLazyAfterView();
                    this.mIsLazyAfterView = true;
                }
                this.mImmersionOwner.onVisible();
            } else if (!this.mIsActivityCreated) {
            } else {
                this.mImmersionOwner.onInvisible();
            }
        }
    }

    public void onCreate(@Nullable Bundle bundle) {
        Fragment fragment = this.mFragment;
        if (fragment == null || !fragment.getUserVisibleHint() || this.mIsLazyBeforeView) {
            return;
        }
        this.mImmersionOwner.onLazyBeforeView();
        this.mIsLazyBeforeView = true;
    }

    public void onActivityCreated(@Nullable Bundle bundle) {
        this.mIsActivityCreated = true;
        Fragment fragment = this.mFragment;
        if (fragment == null || !fragment.getUserVisibleHint()) {
            return;
        }
        if (this.mImmersionOwner.immersionBarEnabled()) {
            this.mImmersionOwner.initImmersionBar();
        }
        if (this.mIsLazyAfterView) {
            return;
        }
        this.mImmersionOwner.onLazyAfterView();
        this.mIsLazyAfterView = true;
    }

    public void onResume() {
        Fragment fragment = this.mFragment;
        if (fragment == null || !fragment.getUserVisibleHint()) {
            return;
        }
        this.mImmersionOwner.onVisible();
    }

    public void onPause() {
        if (this.mFragment != null) {
            this.mImmersionOwner.onInvisible();
        }
    }

    public void onDestroy() {
        this.mFragment = null;
        this.mImmersionOwner = null;
    }

    public void onConfigurationChanged(Configuration configuration) {
        Fragment fragment = this.mFragment;
        if (fragment == null || !fragment.getUserVisibleHint()) {
            return;
        }
        if (this.mImmersionOwner.immersionBarEnabled()) {
            this.mImmersionOwner.initImmersionBar();
        }
        this.mImmersionOwner.onVisible();
    }

    public void onHiddenChanged(boolean z) {
        Fragment fragment = this.mFragment;
        if (fragment != null) {
            fragment.setUserVisibleHint(!z);
        }
    }
}
