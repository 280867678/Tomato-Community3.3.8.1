package com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.gen.p059mh.webapp_extensions.R$id;
import com.gen.p059mh.webapp_extensions.R$layout;
import com.p076mh.webappStart.android_plugin_impl.beans.ShowLoadingParamsBean;

/* renamed from: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.view.LoadingView */
/* loaded from: classes3.dex */
public class LoadingView extends FrameLayout {
    private ShowLoadingParamsBean params;

    private void initData() {
    }

    public LoadingView(@NonNull Context context) {
        this(context, null);
    }

    public LoadingView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, -1);
    }

    public LoadingView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void init(ShowLoadingParamsBean showLoadingParamsBean) {
        this.params = showLoadingParamsBean;
        initView();
        initData();
    }

    private void initView() {
        View inflate = LayoutInflater.from(getContext()).inflate(R$layout.web_sdk_wx_loading, (ViewGroup) this, true);
        ((TextView) inflate.findViewById(R$id.tv_title)).setText(this.params.getTitle());
        inflate.findViewById(R$id.mask).setVisibility(this.params.isMask() ? 0 : 8);
        inflate.setClickable(this.params.isMask());
    }
}
