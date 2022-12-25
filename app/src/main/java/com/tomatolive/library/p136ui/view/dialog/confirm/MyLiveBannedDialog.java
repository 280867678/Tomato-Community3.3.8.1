package com.tomatolive.library.p136ui.view.dialog.confirm;

import android.os.Bundle;
import android.support.p002v4.content.ContextCompat;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment;

/* renamed from: com.tomatolive.library.ui.view.dialog.confirm.MyLiveBannedDialog */
/* loaded from: classes3.dex */
public class MyLiveBannedDialog extends BaseDialogFragment {
    private static final String TIPS = "tips";
    private View.OnClickListener authListener;

    public static MyLiveBannedDialog newInstance(String str, View.OnClickListener onClickListener) {
        Bundle bundle = new Bundle();
        MyLiveBannedDialog myLiveBannedDialog = new MyLiveBannedDialog();
        myLiveBannedDialog.setArguments(bundle);
        bundle.putString(TIPS, str);
        myLiveBannedDialog.setAuthListener(onClickListener);
        return myLiveBannedDialog;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public int getLayoutRes() {
        return R$layout.fq_dialog_auth_tips;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public void initView(View view) {
        TextView textView = (TextView) view.findViewById(R$id.tv_content);
        TextView textView2 = (TextView) view.findViewById(R$id.tv_sure);
        textView.setTextColor(ContextCompat.getColor(this.mContext, R$color.fq_text_black));
        textView.setText(Html.fromHtml(getString(R$string.fq_my_live_banned_dialog_tips, getArgumentsString(TIPS))));
        textView2.setText(getString(R$string.fq_btn_sure));
        view.findViewById(R$id.tv_cancel).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.confirm.-$$Lambda$MyLiveBannedDialog$oLjbTtC5UchNFMobEjVF5WhEub4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                MyLiveBannedDialog.this.lambda$initView$0$MyLiveBannedDialog(view2);
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.confirm.-$$Lambda$MyLiveBannedDialog$rizvLdng6wusYczLo-NPo5Sov5k
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                MyLiveBannedDialog.this.lambda$initView$1$MyLiveBannedDialog(view2);
            }
        });
    }

    public /* synthetic */ void lambda$initView$0$MyLiveBannedDialog(View view) {
        dismiss();
    }

    public /* synthetic */ void lambda$initView$1$MyLiveBannedDialog(View view) {
        if (this.authListener != null) {
            dismiss();
            this.authListener.onClick(view);
        }
    }

    public View.OnClickListener getAuthListener() {
        return this.authListener;
    }

    private void setAuthListener(View.OnClickListener onClickListener) {
        this.authListener = onClickListener;
    }
}
