package com.one.tomato.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.R$id;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.PreferencesUtil;
import kotlin.Unit;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: PublishTipDialog.kt */
/* loaded from: classes3.dex */
public final class PublishTipDialog extends Dialog {
    private Functions<Unit> dismissCallBack;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PublishTipDialog(Context context) {
        super(context, R.style.PostRewardDialog);
        Intrinsics.checkParameterIsNotNull(context, "context");
        setContentView(LayoutInflater.from(context).inflate(R.layout.dialog_publish_tip, (ViewGroup) null));
        Window window = getWindow();
        Intrinsics.checkExpressionValueIsNotNull(window, "window");
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.gravity = 80;
        attributes.width = -1;
        attributes.alpha = 1.0f;
        window.setAttributes(attributes);
        initView();
    }

    private final void initView() {
        int indexOf$default;
        int indexOf$default2;
        ImageView imageView = (ImageView) findViewById(R$id.image_close);
        if (imageView != null) {
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.dialog.PublishTipDialog$initView$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PublishTipDialog.this.dismiss();
                }
            });
        }
        String string = AppUtil.getString(R.string.publish_post_tishi_2);
        if (string instanceof String) {
            SpannableString spannableString = new SpannableString(string);
            ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.parseColor("#5B92E1"));
            indexOf$default = StringsKt__StringsKt.indexOf$default((CharSequence) string, "《", 0, false, 6, (Object) null);
            indexOf$default2 = StringsKt__StringsKt.indexOf$default((CharSequence) string, "》", 0, false, 6, (Object) null);
            spannableString.setSpan(foregroundColorSpan, indexOf$default, indexOf$default2, 18);
            TextView text_agree = (TextView) findViewById(R$id.text_agree);
            Intrinsics.checkExpressionValueIsNotNull(text_agree, "text_agree");
            text_agree.setText(spannableString);
        }
        CheckBox checkBox = (CheckBox) findViewById(R$id.check_tip);
        if (checkBox != null) {
            checkBox.setOnCheckedChangeListener(PublishTipDialog$initView$2.INSTANCE);
        }
        boolean z = PreferencesUtil.getInstance().getBoolean("publish_tip_check");
        CheckBox check_tip = (CheckBox) findViewById(R$id.check_tip);
        Intrinsics.checkExpressionValueIsNotNull(check_tip, "check_tip");
        check_tip.setChecked(z);
    }

    public final void dismissCallBack(Functions<Unit> functions) {
        this.dismissCallBack = functions;
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public void dismiss() {
        Functions<Unit> functions = this.dismissCallBack;
        if (functions != null) {
            functions.mo6822invoke();
        }
        super.dismiss();
    }
}
