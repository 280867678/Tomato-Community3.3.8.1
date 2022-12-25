package com.one.tomato.dialog;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import kotlin.Unit;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: UpNotifyDialog.kt */
/* loaded from: classes3.dex */
public final class UpNotifyDialog extends CustomAlertDialog {
    private Button button_ok;
    private Functions<Unit> clickButton;
    private ImageView image_up;
    private TextView text_message;
    private TextView text_title;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UpNotifyDialog(Context context) {
        super(context);
        Intrinsics.checkParameterIsNotNull(context, "context");
        Button button = null;
        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_up_notify, (ViewGroup) null);
        this.image_up = inflate != null ? (ImageView) inflate.findViewById(R.id.image_up) : null;
        this.text_title = inflate != null ? (TextView) inflate.findViewById(R.id.text_title) : null;
        this.text_message = inflate != null ? (TextView) inflate.findViewById(R.id.text_message) : null;
        this.button_ok = inflate != null ? (Button) inflate.findViewById(R.id.button_ok) : button;
        Button button2 = this.button_ok;
        if (button2 != null) {
            button2.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.dialog.UpNotifyDialog.1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    Functions functions = UpNotifyDialog.this.clickButton;
                    if (functions != null) {
                        Unit unit = (Unit) functions.mo6822invoke();
                    }
                }
            });
        }
        setContentView(inflate);
        setMiddleNeedPadding(false);
        bottomLayoutGone();
    }

    public final void setImageUp(Drawable imag) {
        Intrinsics.checkParameterIsNotNull(imag, "imag");
        ImageView imageView = this.image_up;
        if (imageView != null) {
            imageView.setImageDrawable(imag);
        }
    }

    public final void setTextTitle(String title) {
        Intrinsics.checkParameterIsNotNull(title, "title");
        TextView textView = this.text_title;
        if (textView != null) {
            textView.setText(title);
        }
    }

    public final void setTextMessage(String message) {
        Intrinsics.checkParameterIsNotNull(message, "message");
        TextView textView = this.text_message;
        if (textView != null) {
            textView.setText(message);
        }
    }

    public final void addButtonClick(Functions<Unit> functions) {
        this.clickButton = functions;
    }
}
