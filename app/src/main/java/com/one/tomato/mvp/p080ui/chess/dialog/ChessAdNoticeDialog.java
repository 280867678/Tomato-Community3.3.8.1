package com.one.tomato.mvp.p080ui.chess.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.broccoli.p150bh.R;
import com.one.tomato.dialog.CustomAlertDialog;
import com.one.tomato.mvp.p080ui.login.view.RegisterActivity;

/* renamed from: com.one.tomato.mvp.ui.chess.dialog.ChessAdNoticeDialog */
/* loaded from: classes3.dex */
public class ChessAdNoticeDialog extends CustomAlertDialog {
    private ImageView iv_close;
    private ImageView iv_notice;

    public ChessAdNoticeDialog(final Context context) {
        super(context);
        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_chess_ad_notify, (ViewGroup) null);
        setContentView(inflate);
        setMiddleNeedPadding(false);
        bottomLayoutGone();
        this.iv_notice = (ImageView) inflate.findViewById(R.id.iv_notice);
        this.iv_close = (ImageView) inflate.findViewById(R.id.iv_close);
        setBackgroundColor(context.getResources().getColor(R.color.transparent));
        this.iv_notice.setVisibility(0);
        this.iv_notice.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.chess.dialog.ChessAdNoticeDialog.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ChessAdNoticeDialog.this.dismiss();
                RegisterActivity.Companion.startActivity(context);
            }
        });
        this.iv_close.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.chess.dialog.ChessAdNoticeDialog.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ChessAdNoticeDialog.this.dismiss();
            }
        });
    }
}
