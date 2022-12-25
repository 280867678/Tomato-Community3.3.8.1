package com.one.tomato.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.broccoli.p150bh.R;

/* loaded from: classes3.dex */
public class DeleMsgDialog extends Dialog {
    private TextView message;
    private onSetMessgeListener messgeListener;

    /* renamed from: no */
    private TextView f1690no;
    private TextView title;
    private TextView yes;
    private onYesOnclickListener yesOnclickListener;

    /* loaded from: classes3.dex */
    public interface onSetMessgeListener {
        void onsetContent(TextView textView);

        void onsetTitle(TextView textView);
    }

    /* loaded from: classes3.dex */
    public interface onYesOnclickListener {
        void onYesClick();
    }

    public void setYesOnclickListener(onYesOnclickListener onyesonclicklistener) {
        this.yesOnclickListener = onyesonclicklistener;
    }

    public void setMessgeListener(onSetMessgeListener onsetmessgelistener) {
        this.messgeListener = onsetmessgelistener;
    }

    public DeleMsgDialog(Context context) {
        super(context, R.style.MyDialog);
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.dialog_dele);
        setCanceledOnTouchOutside(false);
        initView();
        initEvent();
    }

    private void initEvent() {
        this.yes.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.dialog.DeleMsgDialog.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (DeleMsgDialog.this.yesOnclickListener != null) {
                    DeleMsgDialog.this.yesOnclickListener.onYesClick();
                }
            }
        });
        this.f1690no.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.dialog.DeleMsgDialog.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DeleMsgDialog.this.dismiss();
            }
        });
    }

    private void initView() {
        this.yes = (TextView) findViewById(R.id.yes);
        this.f1690no = (TextView) findViewById(R.id.no);
        this.title = (TextView) findViewById(R.id.title);
        this.message = (TextView) findViewById(R.id.message);
        onSetMessgeListener onsetmessgelistener = this.messgeListener;
        if (onsetmessgelistener != null) {
            onsetmessgelistener.onsetTitle(this.title);
            this.messgeListener.onsetContent(this.message);
        }
    }
}
