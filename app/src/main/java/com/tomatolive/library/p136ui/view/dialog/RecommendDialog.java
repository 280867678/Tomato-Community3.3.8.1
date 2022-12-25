package com.tomatolive.library.p136ui.view.dialog;

import android.os.Bundle;
import android.support.p002v4.app.FragmentManager;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment;

/* renamed from: com.tomatolive.library.ui.view.dialog.RecommendDialog */
/* loaded from: classes3.dex */
public class RecommendDialog extends BaseDialogFragment {
    private static final String KEY_ANCHOR_NAME = "KEY_ANCHOR_NAME";
    private static final String KEY_CONTENT = "KEY_CONTENT";
    private static final String KEY_DIALOG_TYPE = "KEY_DIALOG_TYPE";
    private static final String KEY_TITLE = "KEY_TITLE";
    public static final String RECOMMEND_ANONYMOUS = "RECOMMEND_ANONYMOUS";
    public static final String RECOMMEND_FAIL = "RECOMMEND_FAIL";
    public static final String RECOMMEND_FAIL_2 = "RECOMMEND_FAIL_2";
    public static final String RECOMMEND_FAIL_3 = "RECOMMEND_FAIL_3";
    public static final String RECOMMEND_PUBLIC = "RECOMMEND_PUBLIC";
    private OnRecommendListener listener;

    /* renamed from: com.tomatolive.library.ui.view.dialog.RecommendDialog$OnRecommendListener */
    /* loaded from: classes3.dex */
    public interface OnRecommendListener {
        void onRecommend();
    }

    public static RecommendDialog newInstance(String str, String str2) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_DIALOG_TYPE, str);
        bundle.putString(KEY_ANCHOR_NAME, str2);
        RecommendDialog recommendDialog = new RecommendDialog();
        recommendDialog.setArguments(bundle);
        return recommendDialog;
    }

    public static RecommendDialog newInstance(String str, String str2, String str3) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_DIALOG_TYPE, str);
        bundle.putString(KEY_TITLE, str2);
        bundle.putString(KEY_CONTENT, str3);
        RecommendDialog recommendDialog = new RecommendDialog();
        recommendDialog.setArguments(bundle);
        return recommendDialog;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public int getLayoutRes() {
        return R$layout.fq_dialog_recommend;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public void initView(View view) {
        String string;
        String str;
        TextView textView = (TextView) view.findViewById(R$id.fq_recommend_anchor_name);
        String argumentsString = getArgumentsString(KEY_ANCHOR_NAME);
        if (!TextUtils.isEmpty(argumentsString)) {
            textView.setVisibility(0);
            textView.setText(argumentsString);
        } else {
            textView.setVisibility(8);
        }
        TextView textView2 = (TextView) view.findViewById(R$id.fq_dialog_recommend_content);
        TextView textView3 = (TextView) view.findViewById(R$id.fq_btn_recommend_sure);
        TextView textView4 = (TextView) view.findViewById(R$id.fq_btn_recommend_cancel);
        TextView textView5 = (TextView) view.findViewById(R$id.fq_recommend_title);
        View findViewById = view.findViewById(R$id.v_divider);
        String argumentsString2 = getArgumentsString(KEY_DIALOG_TYPE);
        char c = 65535;
        switch (argumentsString2.hashCode()) {
            case -1984918422:
                if (argumentsString2.equals(RECOMMEND_ANONYMOUS)) {
                    c = 0;
                    break;
                }
                break;
            case 43958356:
                if (argumentsString2.equals(RECOMMEND_FAIL_2)) {
                    c = 3;
                    break;
                }
                break;
            case 43958357:
                if (argumentsString2.equals(RECOMMEND_FAIL_3)) {
                    c = 4;
                    break;
                }
                break;
            case 348511084:
                if (argumentsString2.equals(RECOMMEND_PUBLIC)) {
                    c = 1;
                    break;
                }
                break;
            case 1112893665:
                if (argumentsString2.equals(RECOMMEND_FAIL)) {
                    c = 2;
                    break;
                }
                break;
        }
        if (c == 0) {
            string = getString(R$string.fq_text_dialog_recommend_title_anonymous);
            textView4.setVisibility(0);
            findViewById.setVisibility(0);
        } else if (c == 1) {
            string = getString(R$string.fq_text_dialog_recommend_title_public);
            textView4.setVisibility(0);
            findViewById.setVisibility(0);
        } else {
            if (c == 2) {
                textView4.setVisibility(8);
                findViewById.setVisibility(8);
                str = getArgumentsString(KEY_TITLE);
                textView2.setText(getArgumentsString(KEY_CONTENT));
                textView3.setText(getString(R$string.fq_btn_sure));
            } else if (c == 3) {
                String argumentsString3 = getArgumentsString(KEY_TITLE);
                textView4.setVisibility(0);
                findViewById.setVisibility(0);
                textView2.setText(getArgumentsString(KEY_CONTENT));
                textView3.setText(getString(R$string.fq_text_renewal));
                str = argumentsString3;
            } else if (c != 4) {
                return;
            } else {
                textView4.setVisibility(8);
                findViewById.setVisibility(8);
                str = getArgumentsString(KEY_TITLE);
                textView2.setText(Html.fromHtml(this.mContext.getString(R$string.fq_text_recommend_fail_tips_time_end, getArgumentsString(KEY_CONTENT))));
                textView3.setText(getString(R$string.fq_btn_sure));
            }
            textView5.setText(str);
            textView3.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$RecommendDialog$kzZiM4xvFrzCI7xVCvWQJFscA4g
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    RecommendDialog.this.lambda$initView$0$RecommendDialog(view2);
                }
            });
            textView4.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$RecommendDialog$q1c5cE2N7H0-VmjDhZg4FnBKNpc
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    RecommendDialog.this.lambda$initView$1$RecommendDialog(view2);
                }
            });
        }
        str = string;
        textView5.setText(str);
        textView3.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$RecommendDialog$kzZiM4xvFrzCI7xVCvWQJFscA4g
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                RecommendDialog.this.lambda$initView$0$RecommendDialog(view2);
            }
        });
        textView4.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$RecommendDialog$q1c5cE2N7H0-VmjDhZg4FnBKNpc
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                RecommendDialog.this.lambda$initView$1$RecommendDialog(view2);
            }
        });
    }

    public /* synthetic */ void lambda$initView$0$RecommendDialog(View view) {
        dismiss();
        OnRecommendListener onRecommendListener = this.listener;
        if (onRecommendListener != null) {
            onRecommendListener.onRecommend();
        }
    }

    public /* synthetic */ void lambda$initView$1$RecommendDialog(View view) {
        dismiss();
    }

    public RecommendDialog setOnSureListener(OnRecommendListener onRecommendListener) {
        this.listener = onRecommendListener;
        return this;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseRxDialogFragment
    public void show(FragmentManager fragmentManager) {
        super.show(fragmentManager, getArgumentsString(KEY_DIALOG_TYPE));
    }
}
