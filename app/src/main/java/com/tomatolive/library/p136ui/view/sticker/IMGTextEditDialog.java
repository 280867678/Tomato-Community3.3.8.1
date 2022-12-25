package com.tomatolive.library.p136ui.view.sticker;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.RadioGroup;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$style;
import com.tomatolive.library.p136ui.view.sticker.core.IMGText;
import com.tomatolive.library.p136ui.view.sticker.view.IMGColorGroup;
import com.tomatolive.library.utils.SoftKeyboardUtils;

/* renamed from: com.tomatolive.library.ui.view.sticker.IMGTextEditDialog */
/* loaded from: classes3.dex */
public class IMGTextEditDialog extends Dialog implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    private static final String TAG = "IMGTextEditDialog";
    private Callback mCallback;
    private IMGColorGroup mColorGroup;
    private IMGText mDefaultText;
    private EditText mEditText;

    /* renamed from: com.tomatolive.library.ui.view.sticker.IMGTextEditDialog$Callback */
    /* loaded from: classes3.dex */
    public interface Callback {
        void onText(IMGText iMGText);
    }

    public IMGTextEditDialog(Context context, Callback callback) {
        super(context, R$style.ImageTextDialog);
        setContentView(R$layout.fq_dialog_sticker_text_color);
        this.mCallback = callback;
        Window window = getWindow();
        if (window != null) {
            window.setLayout(-1, -1);
        }
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mColorGroup = (IMGColorGroup) findViewById(R$id.cg_colors);
        this.mColorGroup.setOnCheckedChangeListener(this);
        this.mEditText = (EditText) findViewById(R$id.et_text);
        findViewById(R$id.tv_cancel).setOnClickListener(this);
        findViewById(R$id.tv_done).setOnClickListener(this);
    }

    @Override // android.app.Dialog
    protected void onStart() {
        super.onStart();
        IMGText iMGText = this.mDefaultText;
        if (iMGText != null) {
            this.mEditText.setText(iMGText.getText());
            this.mEditText.setTextColor(this.mDefaultText.getColor());
            if (!this.mDefaultText.isEmpty()) {
                EditText editText = this.mEditText;
                editText.setSelection(editText.length());
            }
            this.mDefaultText = null;
        } else {
            this.mEditText.setText("");
        }
        this.mColorGroup.setCheckColor(this.mEditText.getCurrentTextColor());
    }

    public void setText(IMGText iMGText) {
        this.mDefaultText = iMGText;
    }

    public void reset() {
        setText(new IMGText(null, -1));
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R$id.tv_done) {
            onDone();
        } else if (id != R$id.tv_cancel) {
        } else {
            dismiss();
        }
    }

    private void onDone() {
        Callback callback;
        String obj = this.mEditText.getText().toString();
        if (!TextUtils.isEmpty(obj) && (callback = this.mCallback) != null) {
            callback.onText(new IMGText(obj, this.mEditText.getCurrentTextColor()));
        }
        dismiss();
    }

    @Override // android.widget.RadioGroup.OnCheckedChangeListener
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        this.mEditText.setTextColor(this.mColorGroup.getCheckColor());
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public void dismiss() {
        SoftKeyboardUtils.hideDialogSoftKeyboard(this);
        super.dismiss();
    }
}
