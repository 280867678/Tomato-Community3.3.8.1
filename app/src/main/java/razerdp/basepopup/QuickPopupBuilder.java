package razerdp.basepopup;

import android.content.Context;
import android.view.View;
import java.lang.ref.WeakReference;
import razerdp.widget.QuickPopup;

/* loaded from: classes4.dex */
public class QuickPopupBuilder {
    private WeakReference<Context> mContextWeakReference;
    private OnConfigApplyListener mOnConfigApplyListener;
    int width = -2;
    int height = -2;
    private QuickPopupConfig mConfig = QuickPopupConfig.generateDefault();

    /* loaded from: classes4.dex */
    public interface OnConfigApplyListener {
        void onConfigApply(QuickPopup quickPopup, QuickPopupConfig quickPopupConfig);
    }

    private QuickPopupBuilder(Context context) {
        this.mContextWeakReference = new WeakReference<>(context);
    }

    public static QuickPopupBuilder with(Context context) {
        return new QuickPopupBuilder(context);
    }

    public QuickPopupBuilder contentView(int i) {
        this.mConfig.contentViewLayoutid(i);
        return this;
    }

    public <C extends QuickPopupConfig> QuickPopupBuilder config(C c) {
        if (c == null) {
            return this;
        }
        QuickPopupConfig quickPopupConfig = this.mConfig;
        if (c != quickPopupConfig) {
            c.contentViewLayoutid(quickPopupConfig.contentViewLayoutid);
        }
        this.mConfig = c;
        return this;
    }

    public QuickPopup build() {
        return new QuickPopup(getContext(), this.mConfig, this.mOnConfigApplyListener, this.width, this.height);
    }

    public QuickPopup show(View view) {
        QuickPopup build = build();
        build.showPopupWindow(view);
        return build;
    }

    private Context getContext() {
        WeakReference<Context> weakReference = this.mContextWeakReference;
        if (weakReference == null) {
            return null;
        }
        return weakReference.get();
    }
}
