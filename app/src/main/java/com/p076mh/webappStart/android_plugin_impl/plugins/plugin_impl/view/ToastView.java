package com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.gen.p059mh.webapp_extensions.R$id;
import com.gen.p059mh.webapp_extensions.R$layout;
import com.gen.p059mh.webapp_extensions.R$mipmap;
import com.gen.p059mh.webapp_extensions.matisse.internal.entity.SelectionSpec;
import com.gen.p059mh.webapps.listener.IWebFragmentController;
import com.p076mh.webappStart.android_plugin_impl.beans.ToastParamsBean;
import com.p076mh.webappStart.util.CZViewUtil;
import java.io.File;

/* renamed from: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.view.ToastView */
/* loaded from: classes3.dex */
public class ToastView extends FrameLayout {
    private static final String TAG = "ToastView";
    private ToastParamsBean params;
    private IWebFragmentController webViewFragment;

    private void initData() {
    }

    public ToastView(@NonNull Context context) {
        this(context, null);
    }

    public ToastView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, -1);
    }

    public ToastView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void init(IWebFragmentController iWebFragmentController, ToastParamsBean toastParamsBean) {
        this.webViewFragment = iWebFragmentController;
        this.params = toastParamsBean;
        initView();
        initData();
    }

    private void initView() {
        View inflate = LayoutInflater.from(getContext()).inflate(R$layout.web_sdk_wx_toast, (ViewGroup) this, true);
        View findViewById = findViewById(R$id.rl_wrapper);
        ProgressBar progressBar = (ProgressBar) inflate.findViewById(R$id.pb_bar);
        ImageView imageView = (ImageView) inflate.findViewById(R$id.img_icon);
        TextView textView = (TextView) inflate.findViewById(R$id.tv_title);
        textView.setText(this.params.getTitle());
        inflate.findViewById(R$id.mask).setVisibility(this.params.isMask() ? 0 : 8);
        inflate.setClickable(this.params.isMask());
        progressBar.setVisibility(8);
        imageView.setVisibility(0);
        if (!TextUtils.isEmpty(this.params.getImage())) {
            File file = new File(this.webViewFragment.realPath(this.params.getImage()));
            Log.e(TAG, "toast img1 : " + this.webViewFragment.realPath(this.params.getImage()) + ",file exist " + file.exists());
            Log.e(TAG, "toast img2 : " + file.getAbsolutePath() + ",file exist " + file.exists());
            SelectionSpec.getInstance().imageEngine.load(getContext(), imageView, this.webViewFragment.realPath(this.params.getImage()));
            return;
        }
        String icon = this.params.getIcon();
        char c = 65535;
        int hashCode = icon.hashCode();
        if (hashCode != -1867169789) {
            if (hashCode != 3387192) {
                if (hashCode == 336650556 && icon.equals("loading")) {
                    c = 1;
                }
            } else if (icon.equals("none")) {
                c = 2;
            }
        } else if (icon.equals("success")) {
            c = 0;
        }
        if (c == 0) {
            imageView.setImageResource(R$mipmap.icon_toast_success);
        } else if (c == 1) {
            imageView.setVisibility(8);
            progressBar.setVisibility(0);
        } else if (c != 2) {
        } else {
            imageView.setVisibility(8);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) textView.getLayoutParams();
            layoutParams.setMargins(10, 10, 10, 10);
            textView.setLayoutParams(layoutParams);
            CZViewUtil.measureViewWrapWrap(findViewById);
        }
    }
}
