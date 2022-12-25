package com.tomatolive.library.p136ui.view.sticker.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;
import com.tomatolive.library.p136ui.view.sticker.IMGTextEditDialog;
import com.tomatolive.library.p136ui.view.sticker.core.IMGText;

/* renamed from: com.tomatolive.library.ui.view.sticker.view.IMGStickerTextView */
/* loaded from: classes3.dex */
public class IMGStickerTextView extends IMGStickerView implements IMGTextEditDialog.Callback {
    private static final int PADDING = 20;
    private static final String TAG = "IMGStickerTextView";
    private static final float TEXT_SIZE_SP = 7.0f;
    private static float mBaseTextSize = -1.0f;
    private IMGTextEditDialog mDialog;
    private IMGText mText;
    private TextView mTextView;

    public IMGStickerTextView(Context context) {
        this(context, null, 0);
    }

    public IMGStickerTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public IMGStickerTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // com.tomatolive.library.p136ui.view.sticker.view.IMGStickerView
    public void onInitialize(Context context) {
        if (mBaseTextSize <= 0.0f) {
            mBaseTextSize = TypedValue.applyDimension(2, TEXT_SIZE_SP, context.getResources().getDisplayMetrics());
        }
        super.onInitialize(context);
    }

    @Override // com.tomatolive.library.p136ui.view.sticker.view.IMGStickerView
    public View onCreateContentView(Context context) {
        this.mTextView = new TextView(context);
        this.mTextView.setTextSize(mBaseTextSize);
        this.mTextView.setPadding(20, 20, 20, 20);
        this.mTextView.setTextColor(-1);
        this.mTextView.setSingleLine();
        this.mTextView.setEllipsize(TextUtils.TruncateAt.END);
        return this.mTextView;
    }

    public void setText(IMGText iMGText) {
        TextView textView;
        this.mText = iMGText;
        IMGText iMGText2 = this.mText;
        if (iMGText2 == null || (textView = this.mTextView) == null) {
            return;
        }
        textView.setText(iMGText2.getText());
        this.mTextView.setTextColor(this.mText.getColor());
    }

    public IMGText getText() {
        return this.mText;
    }

    @Override // com.tomatolive.library.p136ui.view.sticker.view.IMGStickerView
    public void onContentTap() {
        IMGTextEditDialog dialog = getDialog();
        dialog.setText(this.mText);
        dialog.show();
    }

    private IMGTextEditDialog getDialog() {
        if (this.mDialog == null) {
            this.mDialog = new IMGTextEditDialog(getContext(), this);
        }
        return this.mDialog;
    }

    @Override // com.tomatolive.library.p136ui.view.sticker.IMGTextEditDialog.Callback
    public void onText(IMGText iMGText) {
        TextView textView;
        this.mText = iMGText;
        IMGText iMGText2 = this.mText;
        if (iMGText2 == null || (textView = this.mTextView) == null) {
            return;
        }
        textView.setText(iMGText2.getText());
        this.mTextView.setTextColor(this.mText.getColor());
    }
}
