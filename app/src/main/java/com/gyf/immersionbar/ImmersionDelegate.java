package com.gyf.immersionbar;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Build;
import android.support.p002v4.app.DialogFragment;
import android.support.p002v4.app.Fragment;

/* loaded from: classes3.dex */
class ImmersionDelegate {
    private ImmersionBar mImmersionBar;

    /* JADX INFO: Access modifiers changed from: package-private */
    public ImmersionDelegate(Object obj) {
        if (obj instanceof Activity) {
            if (this.mImmersionBar != null) {
                return;
            }
            this.mImmersionBar = new ImmersionBar((Activity) obj);
        } else if (obj instanceof Fragment) {
            if (this.mImmersionBar != null) {
                return;
            }
            if (obj instanceof DialogFragment) {
                this.mImmersionBar = new ImmersionBar((DialogFragment) obj);
            } else {
                this.mImmersionBar = new ImmersionBar((Fragment) obj);
            }
        } else if (!(obj instanceof android.app.Fragment) || this.mImmersionBar != null) {
        } else {
            if (obj instanceof android.app.DialogFragment) {
                this.mImmersionBar = new ImmersionBar((android.app.DialogFragment) obj);
            } else {
                this.mImmersionBar = new ImmersionBar((android.app.Fragment) obj);
            }
        }
    }

    public ImmersionBar get() {
        return this.mImmersionBar;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void onResume() {
        if (this.mImmersionBar == null || !OSUtils.isEMUI3_x() || !this.mImmersionBar.initialized() || this.mImmersionBar.isFragment() || !this.mImmersionBar.getBarParams().navigationBarWithEMUI3Enable) {
            return;
        }
        this.mImmersionBar.init();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void onDestroy() {
        ImmersionBar immersionBar = this.mImmersionBar;
        if (immersionBar != null) {
            immersionBar.destroy();
            this.mImmersionBar = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void onConfigurationChanged(Configuration configuration) {
        if (this.mImmersionBar != null) {
            if ((!OSUtils.isEMUI3_x() && Build.VERSION.SDK_INT != 19) || !this.mImmersionBar.initialized() || this.mImmersionBar.isFragment() || !this.mImmersionBar.getBarParams().navigationBarWithKitkatEnable) {
                return;
            }
            this.mImmersionBar.init();
        }
    }
}
