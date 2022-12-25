package com.tomatolive.library.p136ui.view.dialog.alert;

import android.os.Bundle;
import android.support.p002v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.TomatoLiveSDK;
import com.tomatolive.library.model.event.LogoutEvent;
import com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment;
import com.tomatolive.library.utils.UserInfoManager;
import org.greenrobot.eventbus.EventBus;

/* renamed from: com.tomatolive.library.ui.view.dialog.alert.TokenInvalidDialog */
/* loaded from: classes3.dex */
public class TokenInvalidDialog extends BaseDialogFragment {
    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public boolean getCancelOutside() {
        return false;
    }

    public static TokenInvalidDialog newInstance() {
        Bundle bundle = new Bundle();
        TokenInvalidDialog tokenInvalidDialog = new TokenInvalidDialog();
        UserInfoManager.getInstance().clearTokenInfo();
        EventBus.getDefault().post(new LogoutEvent());
        tokenInvalidDialog.setArguments(bundle);
        return tokenInvalidDialog;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public int getLayoutRes() {
        return R$layout.fq_chat_tip_dialog;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public void initView(View view) {
        TextView textView = (TextView) view.findViewById(R$id.tv_tips);
        TextView textView2 = (TextView) view.findViewById(R$id.tv_sure);
        ((TextView) view.findViewById(R$id.tv_title)).setText(R$string.fq_tip);
        textView.setText(R$string.fq_token_invalid_tip);
        textView2.setText(R$string.fq_btn_sure);
        textView.setTextColor(ContextCompat.getColor(this.mContext, R$color.fq_colorPrimary));
        textView2.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.alert.-$$Lambda$TokenInvalidDialog$NsZ1VF6huJhX9TTX_ofYxz2S92I
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                TokenInvalidDialog.this.lambda$initView$0$TokenInvalidDialog(view2);
            }
        });
    }

    public /* synthetic */ void lambda$initView$0$TokenInvalidDialog(View view) {
        if (TomatoLiveSDK.getSingleton().sdkCallbackListener != null) {
            TomatoLiveSDK.getSingleton().sdkCallbackListener.onUserOfflineListener(this.mContext);
        }
        dismiss();
    }
}
