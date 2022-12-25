package com.one.tomato.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;
import com.broccoli.p150bh.R;
import com.one.tomato.utils.AppUtil;

/* loaded from: classes3.dex */
public class LoadingDialog extends ProgressDialog implements DialogInterface.OnShowListener, DialogInterface.OnDismissListener {
    private Context context;
    private LottieAnimationView iv_loading;
    private TextView tv_hint;

    public LoadingDialog(Context context) {
        super(context, R.style.Loading_Dialog);
        this.context = context;
    }

    public LoadingDialog(Context context, int i) {
        super(context, i);
        this.context = context;
    }

    @Override // android.app.ProgressDialog, android.app.AlertDialog, android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.dialog_loading);
        setCancelable(true);
        setCanceledOnTouchOutside(false);
        setOnShowListener(this);
        setOnDismissListener(this);
        this.iv_loading = (LottieAnimationView) findViewById(R.id.iv_loading);
        this.tv_hint = (TextView) findViewById(R.id.tv_hint);
    }

    public void setTipText(int i) {
        setTipText(AppUtil.getString(i));
    }

    public void setTipText(String str) {
        this.tv_hint.setVisibility(0);
        this.tv_hint.setText(str);
    }

    @Override // android.content.DialogInterface.OnDismissListener
    public void onDismiss(DialogInterface dialogInterface) {
        if (this.iv_loading.getAnimation() == null || !this.iv_loading.isAnimating()) {
            return;
        }
        this.iv_loading.cancelAnimation();
    }

    @Override // android.content.DialogInterface.OnShowListener
    public void onShow(DialogInterface dialogInterface) {
        if (this.iv_loading.getAnimation() != null && this.iv_loading.isAnimating()) {
            this.iv_loading.cancelAnimation();
        }
        this.iv_loading.setAnimation("loading_refresh.json");
    }
}
