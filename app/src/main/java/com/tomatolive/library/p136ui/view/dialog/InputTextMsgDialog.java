package com.tomatolive.library.p136ui.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.p002v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.R$style;
import com.tomatolive.library.utils.EmojiFilter;
import com.tomatolive.library.utils.SystemUtils;

/* renamed from: com.tomatolive.library.ui.view.dialog.InputTextMsgDialog */
/* loaded from: classes3.dex */
public class InputTextMsgDialog extends Dialog {
    private EditText etMsg;
    private Context mContext;
    private int mNowh = -1;
    private OnTextSendListener mOnTextSendListener;

    /* renamed from: com.tomatolive.library.ui.view.dialog.InputTextMsgDialog$OnTextSendListener */
    /* loaded from: classes3.dex */
    public interface OnTextSendListener {
        void onTextSend(String str);
    }

    public InputTextMsgDialog(Context context, OnTextSendListener onTextSendListener) {
        super(context, R$style.fq_InputDialog);
        this.mContext = context;
        this.mOnTextSendListener = onTextSendListener;
        setContentView(R$layout.fq_dialog_input_send_msg);
        initView();
        initListener();
        initGlobalListener();
        Window window = getWindow();
        window.setLayout(-1, -1);
        window.setSoftInputMode(20);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
    }

    private void initView() {
        this.etMsg = (EditText) findViewById(R$id.et_input_message);
        this.etMsg.setFocusable(true);
        this.etMsg.setFocusableInTouchMode(true);
        this.etMsg.requestFocus();
        findViewById(R$id.out_side).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$InputTextMsgDialog$c2TQ3SG9sRUVL-PX6BFKEn4-3hg
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                InputTextMsgDialog.this.lambda$initView$0$InputTextMsgDialog(view);
            }
        });
    }

    public /* synthetic */ void lambda$initView$0$InputTextMsgDialog(View view) {
        dismiss();
    }

    private void initGlobalListener() {
        this.mNowh = SystemUtils.getContentViewInvisibleHeight(this.mNowh, (Activity) this.mContext);
        ((FrameLayout) ((Activity) this.mContext).findViewById(16908290)).getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$InputTextMsgDialog$cHxjRRfUGkXOAbtywEAFsk6-a5Q
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public final void onGlobalLayout() {
                InputTextMsgDialog.this.lambda$initGlobalListener$1$InputTextMsgDialog();
            }
        });
    }

    public /* synthetic */ void lambda$initGlobalListener$1$InputTextMsgDialog() {
        int contentViewInvisibleHeight = SystemUtils.getContentViewInvisibleHeight(this.mNowh, (Activity) this.mContext);
        if (this.mNowh != contentViewInvisibleHeight) {
            this.mNowh = contentViewInvisibleHeight;
            if (this.mNowh > 0) {
                return;
            }
            dismiss();
        }
    }

    private void initListener() {
        this.etMsg.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$InputTextMsgDialog$gyI-sVyiCnXnbsKjB1V1vNdV7oM
            @Override // android.widget.TextView.OnEditorActionListener
            public final boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                return InputTextMsgDialog.this.lambda$initListener$2$InputTextMsgDialog(textView, i, keyEvent);
            }
        });
        this.etMsg.addTextChangedListener(new TextWatcher() { // from class: com.tomatolive.library.ui.view.dialog.InputTextMsgDialog.1
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                if (EmojiFilter.containsEmoji(editable.toString())) {
                    InputTextMsgDialog.this.etMsg.setText(EmojiFilter.filterEmoji(editable.toString()));
                    InputTextMsgDialog.this.etMsg.setSelection(InputTextMsgDialog.this.etMsg.getText().length());
                }
            }
        });
    }

    public /* synthetic */ boolean lambda$initListener$2$InputTextMsgDialog(TextView textView, int i, KeyEvent keyEvent) {
        OnTextSendListener onTextSendListener;
        if (i == 4) {
            String trim = this.etMsg.getText().toString().trim();
            if (!TextUtils.isEmpty(trim) && (onTextSendListener = this.mOnTextSendListener) != null) {
                onTextSendListener.onTextSend(trim);
                this.etMsg.setText("");
                dismiss();
            }
            return true;
        }
        return false;
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public void dismiss() {
        ((InputMethodManager) getContext().getSystemService("input_method")).hideSoftInputFromWindow(this.etMsg.getWindowToken(), 2);
        super.dismiss();
    }

    public void onDestroy() {
        if (this.mOnTextSendListener != null) {
            this.mOnTextSendListener = null;
        }
        EditText editText = this.etMsg;
        if (editText != null) {
            editText.clearFocus();
            this.etMsg.setOnEditorActionListener(null);
        }
    }

    public void cancelBandPost() {
        EditText editText = this.etMsg;
        if (editText != null) {
            editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(53)});
            this.etMsg.setEnabled(true);
            this.etMsg.setText("");
            this.etMsg.setTextColor(ContextCompat.getColor(this.mContext, R$color.fq_colorBlack));
        }
    }

    public void setBandPostBySuperManager() {
        setBanedAllPost(this.mContext.getString(R$string.fq_super_manager));
    }

    public void setBanedAllPost(String str) {
        EditText editText = this.etMsg;
        if (editText != null) {
            editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(53)});
            this.etMsg.setText(str);
            this.etMsg.setTextColor(ContextCompat.getColor(this.mContext, R$color.fq_colorPrimary));
            this.etMsg.setEnabled(false);
        }
    }
}
