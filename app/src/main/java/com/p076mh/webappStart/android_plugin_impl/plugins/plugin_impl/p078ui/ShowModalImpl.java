package com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.p078ui;

import android.graphics.Color;
import android.support.p005v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.gen.p059mh.webapp_extensions.R$id;
import com.gen.p059mh.webapp_extensions.R$layout;
import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.listener.IWebFragmentController;
import com.p076mh.webappStart.android_plugin_impl.beans.ShowDialogParamsBean;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.BasePluginImpl;
import java.util.HashMap;

/* renamed from: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.ui.ShowModalImpl */
/* loaded from: classes3.dex */
public class ShowModalImpl extends BasePluginImpl<ShowDialogParamsBean> {
    @Override // com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.base.BasePluginImpl
    public void action(IWebFragmentController iWebFragmentController, ShowDialogParamsBean showDialogParamsBean, final Plugin.PluginCallback pluginCallback) {
        final HashMap hashMap = new HashMap();
        AlertDialog.Builder builder = new AlertDialog.Builder(iWebFragmentController.getContext());
        View inflate = LayoutInflater.from(iWebFragmentController.getContext()).inflate(R$layout.web_sdk_wx_modal, (ViewGroup) null);
        builder.setView(inflate);
        final AlertDialog create = builder.create();
        int i = 0;
        create.setCanceledOnTouchOutside(false);
        create.setCancelable(false);
        TextView textView = (TextView) inflate.findViewById(R$id.btn_no);
        TextView textView2 = (TextView) inflate.findViewById(R$id.btn_yes);
        View findViewById = inflate.findViewById(R$id.line_v);
        ((TextView) inflate.findViewById(R$id.tv_title)).setText(showDialogParamsBean.getTitle());
        ((TextView) inflate.findViewById(R$id.tv_msg)).setText(showDialogParamsBean.getContent());
        textView.setText(showDialogParamsBean.getCancelText());
        textView.setTextColor(Color.parseColor(showDialogParamsBean.getCancelColor()));
        textView.setVisibility(showDialogParamsBean.getShowCancel().booleanValue() ? 0 : 8);
        if (!showDialogParamsBean.getShowCancel().booleanValue()) {
            i = 8;
        }
        findViewById.setVisibility(i);
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.ui.ShowModalImpl.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                create.dismiss();
                hashMap.put("cancel", true);
                ShowModalImpl.this.responseSuccess(pluginCallback, hashMap);
            }
        });
        textView2.setText(showDialogParamsBean.getConfirmText());
        textView2.setTextColor(Color.parseColor(showDialogParamsBean.getConfirmColor()));
        textView2.setOnClickListener(new View.OnClickListener() { // from class: com.mh.webappStart.android_plugin_impl.plugins.plugin_impl.ui.ShowModalImpl.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                create.dismiss();
                hashMap.put("confirm", true);
                ShowModalImpl.this.responseSuccess(pluginCallback, hashMap);
            }
        });
        create.show();
    }
}
