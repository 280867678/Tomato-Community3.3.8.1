package com.tomatolive.library.p136ui.view.dialog;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.tomatolive.library.R$array;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment;

/* renamed from: com.tomatolive.library.ui.view.dialog.NobilityGoldUseDescDialog */
/* loaded from: classes3.dex */
public class NobilityGoldUseDescDialog extends BaseDialogFragment {
    public static final String TYPE_GOLD = "typeGold";
    public static final String TYPE_RECOMMEND = "typeRecommend";
    private static final String TYPE_TIPS = "typeTips";

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public double getWidthScale() {
        return 0.65d;
    }

    public static NobilityGoldUseDescDialog newInstance(String str) {
        Bundle bundle = new Bundle();
        NobilityGoldUseDescDialog nobilityGoldUseDescDialog = new NobilityGoldUseDescDialog();
        bundle.putString(TYPE_TIPS, str);
        nobilityGoldUseDescDialog.setArguments(bundle);
        return nobilityGoldUseDescDialog;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    protected int getLayoutRes() {
        return R$layout.fq_dialog_nobility_gold_use_desc;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    protected void initView(View view) {
        char c;
        TextView textView = (TextView) view.findViewById(R$id.tv_title);
        TextView textView2 = (TextView) view.findViewById(R$id.tv_desc);
        String argumentsString = getArgumentsString(TYPE_TIPS);
        int hashCode = argumentsString.hashCode();
        if (hashCode != -676702534) {
            if (hashCode == -305782110 && argumentsString.equals(TYPE_RECOMMEND)) {
                c = 1;
            }
            c = 65535;
        } else {
            if (argumentsString.equals(TYPE_GOLD)) {
                c = 0;
            }
            c = 65535;
        }
        if (c != 0) {
            if (c != 1) {
                return;
            }
            textView.setText(R$string.fq_text_nobility_recommend_hot_count_title);
            textView2.setText(R$string.fq_text_nobility_recommend_hot_count_tips);
            return;
        }
        String[] stringArray = this.mContext.getResources().getStringArray(R$array.fq_nobility_money_use_tips);
        textView.setText(stringArray[0]);
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < stringArray.length; i++) {
            sb.append(stringArray[i]);
        }
        textView2.setText(sb.toString());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public void initListener(View view) {
        super.initListener(view);
        view.findViewById(R$id.iv_close).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$NobilityGoldUseDescDialog$mDTFXQF0mpqTBKtFAAZvFgU0N4k
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                NobilityGoldUseDescDialog.this.lambda$initListener$0$NobilityGoldUseDescDialog(view2);
            }
        });
    }

    public /* synthetic */ void lambda$initListener$0$NobilityGoldUseDescDialog(View view) {
        dismiss();
    }
}
