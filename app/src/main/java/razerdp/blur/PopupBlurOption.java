package razerdp.blur;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewParent;
import java.lang.ref.WeakReference;

/* loaded from: classes4.dex */
public class PopupBlurOption {
    private WeakReference<View> mBlurView;
    private float mBlurRadius = 10.0f;
    private float mBlurPreScaleRatio = 0.15f;
    private long mBlurInDuration = 500;
    private long mBlurOutDuration = 500;
    private boolean mBlurAsync = true;
    private boolean mFullScreen = true;

    public View getBlurView() {
        WeakReference<View> weakReference = this.mBlurView;
        if (weakReference == null) {
            return null;
        }
        return weakReference.get();
    }

    public PopupBlurOption setBlurView(View view) {
        this.mBlurView = new WeakReference<>(view);
        boolean z = false;
        if (view != null) {
            ViewParent parent = view.getParent();
            boolean equals = parent != null ? TextUtils.equals(parent.getClass().getName(), "com.android.internal.policy.DecorView") : false;
            if (equals) {
                z = equals;
            } else if (view.getId() == 16908290) {
                z = true;
            }
            if (!z) {
                z = TextUtils.equals(view.getClass().getName(), "com.android.internal.policy.DecorView");
            }
        }
        setFullScreen(z);
        return this;
    }

    public float getBlurRadius() {
        return this.mBlurRadius;
    }

    public float getBlurPreScaleRatio() {
        return this.mBlurPreScaleRatio;
    }

    public long getBlurInDuration() {
        long j = this.mBlurInDuration;
        if (j < 0) {
            return 500L;
        }
        return j;
    }

    public PopupBlurOption setBlurInDuration(long j) {
        this.mBlurInDuration = j;
        return this;
    }

    public long getBlurOutDuration() {
        long j = this.mBlurOutDuration;
        if (j < 0) {
            return 500L;
        }
        return j;
    }

    public PopupBlurOption setBlurOutDuration(long j) {
        this.mBlurOutDuration = j;
        return this;
    }

    public boolean isBlurAsync() {
        return this.mBlurAsync;
    }

    public boolean isFullScreen() {
        return this.mFullScreen;
    }

    public PopupBlurOption setFullScreen(boolean z) {
        this.mFullScreen = z;
        return this;
    }

    public boolean isAllowToBlur() {
        return getBlurView() != null;
    }
}
