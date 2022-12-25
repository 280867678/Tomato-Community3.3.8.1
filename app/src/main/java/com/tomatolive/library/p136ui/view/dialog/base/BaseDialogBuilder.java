package com.tomatolive.library.p136ui.view.dialog.base;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.p136ui.view.dialog.base.BaseDialogBuilder;

/* renamed from: com.tomatolive.library.ui.view.dialog.base.BaseDialogBuilder */
/* loaded from: classes3.dex */
public class BaseDialogBuilder {
    private static String CONTENT_RES = "contentRes";
    private static String POSITIVE_BTN_RES = "positiveBtnRes";
    private static String TITLE_RES = "titleRes";
    private int contentResource;
    private View.OnClickListener positiveBtnClickListener;
    private int positiveBtnResource;
    private int titleResource;

    public BaseDialog build() {
        BaseDialog baseDialog = new BaseDialog();
        Bundle bundle = new Bundle();
        bundle.putInt(TITLE_RES, this.titleResource);
        bundle.putInt(CONTENT_RES, this.contentResource);
        bundle.putInt(POSITIVE_BTN_RES, this.positiveBtnResource);
        baseDialog.setArguments(bundle);
        baseDialog.setPositiveBtnClickListener(this.positiveBtnClickListener);
        return baseDialog;
    }

    public BaseDialogBuilder setTitleRes(int i) {
        this.titleResource = i;
        return this;
    }

    public BaseDialogBuilder setContentRes(int i) {
        this.contentResource = i;
        return this;
    }

    public BaseDialogBuilder setPositiveBtnRes(int i) {
        this.positiveBtnResource = i;
        return this;
    }

    public BaseDialogBuilder setPositiveBtnClickListener(View.OnClickListener onClickListener) {
        this.positiveBtnClickListener = onClickListener;
        return this;
    }

    /* renamed from: com.tomatolive.library.ui.view.dialog.base.BaseDialogBuilder$BaseDialog */
    /* loaded from: classes3.dex */
    public static class BaseDialog extends BaseDialogFragment {
        private View.OnClickListener positiveBtnClickListener;

        @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
        public int getLayoutRes() {
            return R$layout.fq_dialog_base;
        }

        @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
        public void initView(View view) {
            TextView textView = (TextView) view.findViewById(R$id.tv_sure);
            Bundle arguments = getArguments();
            if (arguments != null) {
                int i = arguments.getInt(BaseDialogBuilder.TITLE_RES, 0);
                int i2 = arguments.getInt(BaseDialogBuilder.CONTENT_RES, 0);
                int i3 = arguments.getInt(BaseDialogBuilder.POSITIVE_BTN_RES, 0);
                if (i != 0) {
                    ((TextView) view.findViewById(R$id.tv_title)).setText(i);
                }
                if (i2 != 0) {
                    ((TextView) view.findViewById(R$id.tv_content)).setText(i2);
                }
                if (i3 != 0) {
                    textView.setText(i3);
                }
            }
            textView.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.base.-$$Lambda$BaseDialogBuilder$BaseDialog$91deH4Vf7jmBqErwPb-Zu-tog9Y
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    BaseDialogBuilder.BaseDialog.this.lambda$initView$0$BaseDialogBuilder$BaseDialog(view2);
                }
            });
            view.findViewById(R$id.tv_cancel).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.base.-$$Lambda$BaseDialogBuilder$BaseDialog$V4zhOO9uL-VGkhczOsKQOmw2RTo
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    BaseDialogBuilder.BaseDialog.this.lambda$initView$1$BaseDialogBuilder$BaseDialog(view2);
                }
            });
        }

        public /* synthetic */ void lambda$initView$0$BaseDialogBuilder$BaseDialog(View view) {
            View.OnClickListener onClickListener = this.positiveBtnClickListener;
            if (onClickListener != null) {
                onClickListener.onClick(view);
            }
            dismiss();
        }

        public /* synthetic */ void lambda$initView$1$BaseDialogBuilder$BaseDialog(View view) {
            dismiss();
        }

        public void setPositiveBtnClickListener(View.OnClickListener onClickListener) {
            this.positiveBtnClickListener = onClickListener;
        }
    }
}
