package com.tomatolive.library.p136ui.view.dialog;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.RxTimerUtils;
import java.util.concurrent.TimeUnit;

/* renamed from: com.tomatolive.library.ui.view.dialog.NobilityHdRecommendDialog */
/* loaded from: classes3.dex */
public class NobilityHdRecommendDialog extends BaseDialogFragment {
    private static final String IS_ANONYMOUS = "isAnonymous";
    private static final String RECOMMEND_TIME = "recommendTime";
    private static final String USER_NAME = "userName";

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public double getWidthScale() {
        return 1.0d;
    }

    public static NobilityHdRecommendDialog newInstance(boolean z, String str, String str2) {
        Bundle bundle = new Bundle();
        NobilityHdRecommendDialog nobilityHdRecommendDialog = new NobilityHdRecommendDialog();
        bundle.putBoolean(IS_ANONYMOUS, z);
        bundle.putString(USER_NAME, str);
        bundle.putString(RECOMMEND_TIME, str2);
        nobilityHdRecommendDialog.setArguments(bundle);
        return nobilityHdRecommendDialog;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    protected int getLayoutRes() {
        return R$layout.fq_dialog_hd_recommend;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    protected void initView(View view) {
        TextView textView = (TextView) view.findViewById(R$id.tv_tips);
        String argumentsString = getArgumentsString(USER_NAME);
        String argumentsString2 = getArgumentsString(RECOMMEND_TIME);
        String formatUserNickName = AppUtils.formatUserNickName(argumentsString);
        if (getArgumentsBoolean(IS_ANONYMOUS)) {
            textView.setText(Html.fromHtml(getString(R$string.fq_nobility_hd_recommend_tips_1, argumentsString2)));
        } else {
            textView.setText(Html.fromHtml(getString(R$string.fq_nobility_hd_recommend_tips_2, formatUserNickName, argumentsString2)));
        }
        RxTimerUtils.getInstance().timer(this.mContext, 6L, TimeUnit.SECONDS, new RxTimerUtils.RxTimerAction() { // from class: com.tomatolive.library.ui.view.dialog.NobilityHdRecommendDialog.1
            @Override // com.tomatolive.library.utils.RxTimerUtils.RxTimerAction
            public void action(long j) {
                NobilityHdRecommendDialog.this.dismiss();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public void initListener(View view) {
        super.initListener(view);
        view.findViewById(R$id.iv_close).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$NobilityHdRecommendDialog$k3gXkXZ4AMo80e8f0TuKpNU7iUc
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                NobilityHdRecommendDialog.this.lambda$initListener$0$NobilityHdRecommendDialog(view2);
            }
        });
    }

    public /* synthetic */ void lambda$initListener$0$NobilityHdRecommendDialog(View view) {
        dismiss();
    }
}
