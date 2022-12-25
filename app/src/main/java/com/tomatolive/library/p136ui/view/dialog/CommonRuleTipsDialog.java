package com.tomatolive.library.p136ui.view.dialog;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.http.ApiRetrofit;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.function.HttpResultFunction;
import com.tomatolive.library.http.function.ServerResultFunction;
import com.tomatolive.library.model.PopularCardEntity;
import com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment;
import com.tomatolive.library.p136ui.view.dialog.base.BaseRxDialogFragment;
import com.tomatolive.library.p136ui.view.widget.Html5WebView;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.live.SimpleRxObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/* renamed from: com.tomatolive.library.ui.view.dialog.CommonRuleTipsDialog */
/* loaded from: classes3.dex */
public class CommonRuleTipsDialog extends BaseDialogFragment {
    public static final String CODE_KEY = "CODE_KEY";
    public static final String HEIGHT_SCALE = "HEIGHT_SCALE";
    public static final String IS_KNOW_KEY = "IS_KNOW_KEY";
    public static final String TITLE_KEY = "TITLE_KEY";
    private double heightScale = 0.23d;

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public double getWidthScale() {
        return 0.75d;
    }

    public static CommonRuleTipsDialog newInstance(String str, String str2) {
        Bundle bundle = new Bundle();
        CommonRuleTipsDialog commonRuleTipsDialog = new CommonRuleTipsDialog();
        bundle.putString(CODE_KEY, str);
        bundle.putString(TITLE_KEY, str2);
        commonRuleTipsDialog.setArguments(bundle);
        return commonRuleTipsDialog;
    }

    public static CommonRuleTipsDialog newInstance(String str, String str2, boolean z, double d) {
        Bundle bundle = new Bundle();
        CommonRuleTipsDialog commonRuleTipsDialog = new CommonRuleTipsDialog();
        bundle.putString(CODE_KEY, str);
        bundle.putString(TITLE_KEY, str2);
        bundle.putBoolean(IS_KNOW_KEY, z);
        bundle.putDouble(HEIGHT_SCALE, d);
        commonRuleTipsDialog.setArguments(bundle);
        return commonRuleTipsDialog;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseRxDialogFragment
    public void getBundle(Bundle bundle) {
        super.getBundle(bundle);
        this.heightScale = bundle.getDouble(HEIGHT_SCALE, 0.23d);
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public int getLayoutRes() {
        return R$layout.fq_dialog_common_rule_tips;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public void initView(View view) {
        ImageView imageView = (ImageView) view.findViewById(R$id.iv_close);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R$id.ll_know_bg);
        final Html5WebView html5WebView = (Html5WebView) view.findViewById(R$id.tv_content);
        boolean argumentsBoolean = getArgumentsBoolean(IS_KNOW_KEY, false);
        ((TextView) view.findViewById(R$id.tv_title)).setText(getArgumentsString(TITLE_KEY));
        imageView.setVisibility(argumentsBoolean ? 4 : 0);
        linearLayout.setVisibility(argumentsBoolean ? 0 : 8);
        html5WebView.setWebViewClient(new Html5WebViewClient());
        html5WebView.getSettings().setLoadWithOverviewMode(false);
        html5WebView.getSettings().setUseWideViewPort(false);
        html5WebView.setHorizontalScrollBarEnabled(false);
        html5WebView.setVerticalScrollBarEnabled(false);
        ApiRetrofit.getInstance().getApiService().getAppParamConfigService(new RequestParams().getCodeParams(getArgumentsString(CODE_KEY))).map(new ServerResultFunction<PopularCardEntity>() { // from class: com.tomatolive.library.ui.view.dialog.CommonRuleTipsDialog.2
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new SimpleRxObserver<PopularCardEntity>() { // from class: com.tomatolive.library.ui.view.dialog.CommonRuleTipsDialog.1
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(PopularCardEntity popularCardEntity) {
                html5WebView.loadDataWithBaseURL(null, popularCardEntity.value, "text/html", "UTF-8", null);
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$CommonRuleTipsDialog$TLIrys7mdsoEOjgn0vu5_ey9lL0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                CommonRuleTipsDialog.this.lambda$initView$0$CommonRuleTipsDialog(view2);
            }
        });
        linearLayout.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$CommonRuleTipsDialog$vYqPpuKAwkqB1a13dJo3d91qpOk
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                CommonRuleTipsDialog.this.lambda$initView$1$CommonRuleTipsDialog(view2);
            }
        });
    }

    public /* synthetic */ void lambda$initView$0$CommonRuleTipsDialog(View view) {
        dismiss();
    }

    public /* synthetic */ void lambda$initView$1$CommonRuleTipsDialog(View view) {
        dismiss();
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public double getHeightScale() {
        return this.heightScale;
    }

    /* renamed from: com.tomatolive.library.ui.view.dialog.CommonRuleTipsDialog$Html5WebViewClient */
    /* loaded from: classes3.dex */
    private class Html5WebViewClient extends Html5WebView.BaseWebViewClient {
        private Html5WebViewClient() {
        }

        @Override // com.tomatolive.library.p136ui.view.widget.Html5WebView.BaseWebViewClient, android.webkit.WebViewClient
        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            if (str.startsWith("http") && str.startsWith("https")) {
                AppUtils.onSysWebView(((BaseRxDialogFragment) CommonRuleTipsDialog.this).mContext, str);
            }
            return true;
        }
    }
}
