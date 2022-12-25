package com.tomatolive.library.p136ui.view.dialog;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment;
import com.tomatolive.library.utils.SoftKeyboardUtils;

/* renamed from: com.tomatolive.library.ui.view.dialog.UpdateTitleDialog */
/* loaded from: classes3.dex */
public class UpdateTitleDialog extends BaseBottomDialogFragment {
    private OnUpdateLiveTitleListener liveTitleListener;
    private EditText mEditText;

    /* renamed from: com.tomatolive.library.ui.view.dialog.UpdateTitleDialog$OnUpdateLiveTitleListener */
    /* loaded from: classes3.dex */
    public interface OnUpdateLiveTitleListener {
        void onUpdateLiveTitle(String str);
    }

    public static UpdateTitleDialog newInstance(OnUpdateLiveTitleListener onUpdateLiveTitleListener) {
        Bundle bundle = new Bundle();
        UpdateTitleDialog updateTitleDialog = new UpdateTitleDialog();
        updateTitleDialog.setArguments(bundle);
        updateTitleDialog.setLiveTitleListener(onUpdateLiveTitleListener);
        return updateTitleDialog;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    public int getLayoutRes() {
        return R$layout.fq_dialog_update_input_title;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    public void initView(View view) {
        this.mEditText = (EditText) view.findViewById(R$id.et_input_message);
        this.mEditText.setFocusable(true);
        this.mEditText.setFocusableInTouchMode(true);
        this.mEditText.requestFocus();
        this.mEditText.post(new Runnable() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$UpdateTitleDialog$PPUypYXSG8Gw4FfrTxM8p3y0zbA
            @Override // java.lang.Runnable
            public final void run() {
                UpdateTitleDialog.this.lambda$initView$0$UpdateTitleDialog();
            }
        });
        this.mEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$UpdateTitleDialog$pCMOUGGgPrEHgfrN1XMTS3rt7JY
            @Override // android.widget.TextView.OnEditorActionListener
            public final boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                return UpdateTitleDialog.this.lambda$initView$1$UpdateTitleDialog(textView, i, keyEvent);
            }
        });
        view.findViewById(R$id.btn_send).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$UpdateTitleDialog$Sikqi9TWWMhKPY1HKeKYj3CcMWo
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                UpdateTitleDialog.this.lambda$initView$2$UpdateTitleDialog(view2);
            }
        });
    }

    public /* synthetic */ void lambda$initView$0$UpdateTitleDialog() {
        SoftKeyboardUtils.showSoftKeyboard(this.mEditText);
    }

    public /* synthetic */ boolean lambda$initView$1$UpdateTitleDialog(TextView textView, int i, KeyEvent keyEvent) {
        if (i == 6) {
            String trim = this.mEditText.getText().toString().trim();
            if (TextUtils.isEmpty(trim)) {
                showToast(getString(R$string.fq_live_input_title));
                return true;
            }
            dismiss();
            OnUpdateLiveTitleListener onUpdateLiveTitleListener = this.liveTitleListener;
            if (onUpdateLiveTitleListener != null) {
                onUpdateLiveTitleListener.onUpdateLiveTitle(trim);
            }
            return true;
        }
        return false;
    }

    public /* synthetic */ void lambda$initView$2$UpdateTitleDialog(View view) {
        String trim = this.mEditText.getText().toString().trim();
        if (TextUtils.isEmpty(trim)) {
            showToast(getString(R$string.fq_live_input_title));
            return;
        }
        dismiss();
        OnUpdateLiveTitleListener onUpdateLiveTitleListener = this.liveTitleListener;
        if (onUpdateLiveTitleListener == null) {
            return;
        }
        onUpdateLiveTitleListener.onUpdateLiveTitle(trim);
    }

    private void setLiveTitleListener(OnUpdateLiveTitleListener onUpdateLiveTitleListener) {
        this.liveTitleListener = onUpdateLiveTitleListener;
    }
}
