package com.tomatolive.library.p136ui.view.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.p002v4.content.ContextCompat;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.p136ui.interfaces.OnPayLiveCallback;
import com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment;
import com.tomatolive.library.utils.NumberUtils;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.TimeUnit;

/* renamed from: com.tomatolive.library.ui.view.dialog.PayLiveTipsDialog */
/* loaded from: classes3.dex */
public class PayLiveTipsDialog extends BaseDialogFragment {
    private static final String LIVE_PRICE = "LIVE_PRICE";
    private static final String LIVE_TIME = "LIVE_TIME";
    private CompositeDisposable compositeDisposable;
    private OnPayLiveCallback onPayLiveCallback;

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public boolean getCancelOutside() {
        return true;
    }

    public static PayLiveTipsDialog newInstance(String str, String str2, OnPayLiveCallback onPayLiveCallback) {
        Bundle bundle = new Bundle();
        PayLiveTipsDialog payLiveTipsDialog = new PayLiveTipsDialog();
        payLiveTipsDialog.setArguments(bundle);
        bundle.putString(LIVE_TIME, str);
        bundle.putString(LIVE_PRICE, str2);
        payLiveTipsDialog.setOnPayLiveCallback(onPayLiveCallback);
        return payLiveTipsDialog;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    protected int getLayoutRes() {
        return R$layout.fq_dialog_auth_tips;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    protected void initView(View view) {
        if (this.compositeDisposable == null) {
            this.compositeDisposable = new CompositeDisposable();
        }
        String argumentsString = getArgumentsString(LIVE_TIME);
        String argumentsString2 = getArgumentsString(LIVE_PRICE);
        onPayLiveTimerHandle(NumberUtils.string2long(argumentsString, 60L) + 5);
        TextView textView = (TextView) view.findViewById(R$id.tv_content);
        TextView textView2 = (TextView) view.findViewById(R$id.tv_sure);
        TextView textView3 = (TextView) view.findViewById(R$id.tv_cancel);
        textView3.setText(R$string.fq_pay_deal_with_later);
        textView2.setText(R$string.fq_pay_enter_room_tips);
        textView2.setEnabled(true);
        textView.setTextColor(ContextCompat.getColor(this.mContext, R$color.fq_text_black));
        textView.setText(Html.fromHtml(this.mContext.getString(R$string.fq_pay_live_tips_dialog_title, argumentsString, argumentsString2)));
        textView3.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.PayLiveTipsDialog.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (PayLiveTipsDialog.this.onPayLiveCallback != null) {
                    PayLiveTipsDialog.this.dismiss();
                    PayLiveTipsDialog.this.onPayLiveCallback.onPayCancelListener();
                }
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.PayLiveTipsDialog.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (PayLiveTipsDialog.this.onPayLiveCallback != null) {
                    PayLiveTipsDialog.this.dismiss();
                    PayLiveTipsDialog.this.onPayLiveCallback.onPayEnterClickListener(view2);
                }
            }
        });
    }

    public void setOnPayLiveCallback(OnPayLiveCallback onPayLiveCallback) {
        this.onPayLiveCallback = onPayLiveCallback;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseRxDialogFragment, android.support.p002v4.app.DialogFragment, android.content.DialogInterface.OnDismissListener
    public void onDismiss(DialogInterface dialogInterface) {
        super.onDismiss(dialogInterface);
        OnPayLiveCallback onPayLiveCallback = this.onPayLiveCallback;
        if (onPayLiveCallback != null) {
            onPayLiveCallback.onPayCancelListener();
        }
    }

    private void onPayLiveTimerHandle(long j) {
        Disposable subscribe = Observable.timer(j, TimeUnit.SECONDS).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() { // from class: com.tomatolive.library.ui.view.dialog.PayLiveTipsDialog.3
            @Override // io.reactivex.functions.Consumer
            public void accept(Long l) throws Exception {
                if (PayLiveTipsDialog.this.onPayLiveCallback != null) {
                    PayLiveTipsDialog.this.onPayLiveCallback.onPayExitClickListener();
                }
            }
        });
        CompositeDisposable compositeDisposable = this.compositeDisposable;
        if (compositeDisposable != null) {
            compositeDisposable.add(subscribe);
        }
    }

    public void compositeDisposableClear() {
        CompositeDisposable compositeDisposable = this.compositeDisposable;
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }
}
