package com.one.tomato.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.p085ui.income.VeritifyIdentityActivity;
import com.one.tomato.widget.SeparatedEditText;
import sj.keyboard.utils.EmoticonsKeyboardUtils;

/* loaded from: classes3.dex */
public class TradePwdEntryDialog extends Dialog {
    private OnTradePwdListener mListener;
    private ImageView iv_cancel = (ImageView) findViewById(R.id.iv_cancel);
    private SeparatedEditText et_trade_pwd = (SeparatedEditText) findViewById(R.id.et_trade_pwd);
    private TextView tv_trade_pwd_forget = (TextView) findViewById(R.id.tv_trade_pwd_forget);

    /* loaded from: classes3.dex */
    public interface OnTradePwdListener {
        void onCompleted(String str);

        void onTextChanged(String str);
    }

    public TradePwdEntryDialog(@NonNull final Context context) {
        super(context, R.style.TradePwdEntryDialog);
        setContentView(LayoutInflater.from(context).inflate(R.layout.dialog_entry_trade_pwd, (ViewGroup) null));
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.width = -1;
        attributes.height = -2;
        getWindow().setAttributes(attributes);
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        this.et_trade_pwd.setTextChangedListener(new SeparatedEditText.TextChangedListener() { // from class: com.one.tomato.dialog.TradePwdEntryDialog.1
            @Override // com.one.tomato.widget.SeparatedEditText.TextChangedListener
            public void textChanged(CharSequence charSequence) {
                if (TradePwdEntryDialog.this.mListener != null) {
                    TradePwdEntryDialog.this.mListener.onTextChanged(charSequence.toString());
                }
            }

            @Override // com.one.tomato.widget.SeparatedEditText.TextChangedListener
            public void textCompleted(CharSequence charSequence) {
                if (TradePwdEntryDialog.this.mListener != null) {
                    TradePwdEntryDialog.this.mListener.onCompleted(charSequence.toString());
                }
            }
        });
        this.tv_trade_pwd_forget.setOnClickListener(new View.OnClickListener(this) { // from class: com.one.tomato.dialog.TradePwdEntryDialog.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                VeritifyIdentityActivity.startActivity(context, 1003);
            }
        });
        this.iv_cancel.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.dialog.TradePwdEntryDialog.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                TradePwdEntryDialog.this.dismiss();
            }
        });
        this.et_trade_pwd.setOnTouchListener(new View.OnTouchListener() { // from class: com.one.tomato.dialog.TradePwdEntryDialog.4
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (!TradePwdEntryDialog.this.et_trade_pwd.isFocused()) {
                    TradePwdEntryDialog.this.et_trade_pwd.setFocusable(true);
                    TradePwdEntryDialog.this.et_trade_pwd.setFocusableInTouchMode(true);
                    return false;
                }
                return false;
            }
        });
    }

    @Override // android.app.Dialog
    public void show() {
        super.show();
        this.et_trade_pwd.clearText();
        this.et_trade_pwd.setFocusable(true);
        this.et_trade_pwd.requestFocusFromTouch();
        this.et_trade_pwd.requestFocus();
        EmoticonsKeyboardUtils.openSoftKeyboard(this.et_trade_pwd);
    }

    public void setOnTradePwdListener(OnTradePwdListener onTradePwdListener) {
        this.mListener = onTradePwdListener;
    }
}
