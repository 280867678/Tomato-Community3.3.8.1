package com.tomatolive.library.p136ui.view.dialog;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment;
import com.tomatolive.library.utils.live.SimpleRxObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/* renamed from: com.tomatolive.library.ui.view.dialog.AddAddressDialog */
/* loaded from: classes3.dex */
public class AddAddressDialog extends BaseDialogFragment {
    private int maxTextLen = 150;
    private OnAddAddressListener onAddAddressListener;

    /* renamed from: com.tomatolive.library.ui.view.dialog.AddAddressDialog$OnAddAddressListener */
    /* loaded from: classes3.dex */
    public interface OnAddAddressListener {
        void OnAddAddress(String str);
    }

    public static AddAddressDialog newInstance(OnAddAddressListener onAddAddressListener) {
        AddAddressDialog addAddressDialog = new AddAddressDialog();
        addAddressDialog.setListener(onAddAddressListener);
        return addAddressDialog;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public int getLayoutRes() {
        return R$layout.fq_dialog_hd_add_address;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public void initView(View view) {
        final EditText editText = (EditText) view.findViewById(R$id.et_address);
        final TextView textView = (TextView) view.findViewById(R$id.tv_num);
        view.findViewById(R$id.tv_cancel).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$AddAddressDialog$VhY0ZpsLQtC1YHcEoR0fpEGWNDs
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                AddAddressDialog.this.lambda$initView$0$AddAddressDialog(view2);
            }
        });
        view.findViewById(R$id.tv_sure).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$AddAddressDialog$0_cs4evrTvU7EnDsv-LwVlVhyPo
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                AddAddressDialog.this.lambda$initView$1$AddAddressDialog(editText, view2);
            }
        });
        RxTextView.textChanges(editText).map(new Function<CharSequence, Integer>() { // from class: com.tomatolive.library.ui.view.dialog.AddAddressDialog.2
            @Override // io.reactivex.functions.Function
            /* renamed from: apply  reason: avoid collision after fix types in other method */
            public Integer mo6755apply(CharSequence charSequence) throws Exception {
                return Integer.valueOf(charSequence.length());
            }
        }).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new SimpleRxObserver<Integer>() { // from class: com.tomatolive.library.ui.view.dialog.AddAddressDialog.1
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                super.onSubscribe(disposable);
                textView.setText(AddAddressDialog.this.setWordNumTips(0));
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(Integer num) {
                textView.setText(AddAddressDialog.this.setWordNumTips(num.intValue()));
            }
        });
    }

    public /* synthetic */ void lambda$initView$0$AddAddressDialog(View view) {
        dismiss();
    }

    public /* synthetic */ void lambda$initView$1$AddAddressDialog(EditText editText, View view) {
        String trim = editText.getText().toString().trim();
        if (TextUtils.isEmpty(trim)) {
            showToast(R$string.fq_hd_add_address_empty_tips);
        } else if (this.onAddAddressListener == null) {
        } else {
            dismiss();
            this.onAddAddressListener.OnAddAddress(trim);
        }
    }

    private void setListener(OnAddAddressListener onAddAddressListener) {
        this.onAddAddressListener = onAddAddressListener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String setWordNumTips(int i) {
        return i + "/" + this.maxTextLen;
    }
}
