package com.tomatolive.library.p136ui.view.dialog;

import android.graphics.Color;
import android.os.Bundle;
import android.support.p002v4.content.ContextCompat;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.model.GuardItemEntity;
import com.tomatolive.library.p136ui.view.dialog.GuardOpenContentDialog;
import com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment;
import com.tomatolive.library.utils.AppUtils;

/* renamed from: com.tomatolive.library.ui.view.dialog.GuardOpenTipsDialog */
/* loaded from: classes3.dex */
public class GuardOpenTipsDialog extends BaseDialogFragment {
    private static final String SER_ITEM = "serItem";
    private static final String TIP_KEY = "TIP_KEY";
    public static final int TYPE_CONTINUE_OPEN = 13;
    public static final int TYPE_COVER_OPEN = 15;
    public static final int TYPE_NOW_OPEN = 12;
    public static final int TYPE_NO_OPEN = 14;
    public static final int TYPE_ROOM_OPEN = 11;
    private GuardItemEntity guardItem;
    private View.OnClickListener openClickListener;
    private GuardOpenContentDialog.OnOpenGuardCallbackListener openGuardCallbackListener;

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public double getWidthScale() {
        return 0.7d;
    }

    private void setOpenClickListener(View.OnClickListener onClickListener) {
        this.openClickListener = onClickListener;
    }

    public static GuardOpenTipsDialog newInstance(int i, GuardItemEntity guardItemEntity, GuardOpenContentDialog.OnOpenGuardCallbackListener onOpenGuardCallbackListener) {
        GuardOpenTipsDialog guardOpenTipsDialog = new GuardOpenTipsDialog();
        Bundle bundle = new Bundle();
        bundle.putInt(TIP_KEY, i);
        bundle.putParcelable(SER_ITEM, guardItemEntity);
        guardOpenTipsDialog.setArguments(bundle);
        guardOpenTipsDialog.setOpenGuardCallbackListener(onOpenGuardCallbackListener);
        return guardOpenTipsDialog;
    }

    public static GuardOpenTipsDialog newInstance(int i, GuardItemEntity guardItemEntity, View.OnClickListener onClickListener) {
        GuardOpenTipsDialog guardOpenTipsDialog = new GuardOpenTipsDialog();
        Bundle bundle = new Bundle();
        bundle.putInt(TIP_KEY, i);
        bundle.putParcelable(SER_ITEM, guardItemEntity);
        guardOpenTipsDialog.setOpenClickListener(onClickListener);
        guardOpenTipsDialog.setArguments(bundle);
        return guardOpenTipsDialog;
    }

    public static GuardOpenTipsDialog newInstance(int i) {
        GuardOpenTipsDialog guardOpenTipsDialog = new GuardOpenTipsDialog();
        Bundle bundle = new Bundle();
        bundle.putInt(TIP_KEY, i);
        guardOpenTipsDialog.setArguments(bundle);
        return guardOpenTipsDialog;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public int getLayoutRes() {
        return R$layout.fq_dialog_open_guard_tips;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public void initView(View view) {
        this.guardItem = (GuardItemEntity) getArguments().getParcelable(SER_ITEM);
        TextView textView = (TextView) view.findViewById(R$id.tv_cancel);
        TextView textView2 = (TextView) view.findViewById(R$id.tv_sure);
        TextView textView3 = (TextView) view.findViewById(R$id.tv_content);
        TextView textView4 = (TextView) view.findViewById(R$id.tv_tips);
        TextView textView5 = (TextView) view.findViewById(R$id.tv_open_room);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R$id.ll_btn_bg);
        ImageView imageView = (ImageView) view.findViewById(R$id.iv_bg);
        RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R$id.rl_root);
        View findViewById = view.findViewById(R$id.ib_close);
        textView3.setTextColor(Color.parseColor("#494949"));
        switch (getArgumentsInt(TIP_KEY)) {
            case 11:
                findViewById.setVisibility(0);
                imageView.setBackground(ContextCompat.getDrawable(this.mContext, R$drawable.fq_ic_nobility_dialog_bg_ktsh));
                relativeLayout.setBackground(ContextCompat.getDrawable(this.mContext, R$drawable.fq_shape_bottom_corners_white_bg_2));
                imageView.setVisibility(0);
                textView4.setVisibility(8);
                textView3.setTextColor(Color.parseColor("#559CF1"));
                textView3.setText(R$string.fq_guard_open_room_tips2);
                textView3.setVisibility(0);
                textView5.setVisibility(0);
                linearLayout.setVisibility(4);
                break;
            case 12:
                textView4.setVisibility(8);
                textView.setVisibility(0);
                textView5.setVisibility(8);
                linearLayout.setVisibility(0);
                GuardItemEntity guardItemEntity = (GuardItemEntity) getArguments().getParcelable(SER_ITEM);
                if (guardItemEntity != null) {
                    textView3.setText(Html.fromHtml(this.mContext.getString(R$string.fq_guard_open_dialog_tips, guardItemEntity.anchorName, AppUtils.formatDisplayPrice(guardItemEntity.price, false), guardItemEntity.name)));
                    break;
                }
                break;
            case 13:
                textView4.setVisibility(0);
                textView.setVisibility(0);
                textView5.setVisibility(4);
                linearLayout.setVisibility(0);
                textView4.setText(R$string.fq_guard_continue_open_tips);
                GuardItemEntity guardItemEntity2 = (GuardItemEntity) getArguments().getParcelable(SER_ITEM);
                if (guardItemEntity2 != null) {
                    textView3.setText(Html.fromHtml(this.mContext.getString(R$string.fq_guard_open_dialog_tips, guardItemEntity2.anchorName, AppUtils.formatDisplayPrice(guardItemEntity2.price, false), guardItemEntity2.name)));
                    break;
                }
                break;
            case 14:
                textView4.setVisibility(4);
                textView.setVisibility(8);
                linearLayout.setVisibility(0);
                textView5.setVisibility(4);
                textView2.setText(R$string.fq_know);
                textView3.setText(R$string.fq_guard_no_open_tips);
                break;
            case 15:
                textView4.setVisibility(0);
                textView.setVisibility(0);
                textView5.setVisibility(4);
                linearLayout.setVisibility(0);
                textView4.setText(R$string.fq_guard_cover_open_tips);
                GuardItemEntity guardItemEntity3 = (GuardItemEntity) getArguments().getParcelable(SER_ITEM);
                if (guardItemEntity3 != null) {
                    textView3.setText(Html.fromHtml(this.mContext.getString(R$string.fq_guard_open_dialog_tips, guardItemEntity3.anchorName, AppUtils.formatDisplayPrice(guardItemEntity3.price, false), guardItemEntity3.name)));
                    break;
                }
                break;
            default:
                return;
        }
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$GuardOpenTipsDialog$rcah6i_IP4z72LM418peb8whloU
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                GuardOpenTipsDialog.this.lambda$initView$0$GuardOpenTipsDialog(view2);
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$GuardOpenTipsDialog$mT4qvjYhuafjAHTKBcecKrcPoLM
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                GuardOpenTipsDialog.this.lambda$initView$1$GuardOpenTipsDialog(view2);
            }
        });
        textView5.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$GuardOpenTipsDialog$EEe_5rugUmbxtKkccpmpb1uKe2Y
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                GuardOpenTipsDialog.this.lambda$initView$2$GuardOpenTipsDialog(view2);
            }
        });
        findViewById.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$GuardOpenTipsDialog$2gKDZsZYznwMtFllUkCKXkeGDBQ
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                GuardOpenTipsDialog.this.lambda$initView$3$GuardOpenTipsDialog(view2);
            }
        });
    }

    public /* synthetic */ void lambda$initView$0$GuardOpenTipsDialog(View view) {
        dismiss();
    }

    public /* synthetic */ void lambda$initView$1$GuardOpenTipsDialog(View view) {
        dismiss();
        View.OnClickListener onClickListener = this.openClickListener;
        if (onClickListener != null) {
            onClickListener.onClick(view);
        }
    }

    public /* synthetic */ void lambda$initView$2$GuardOpenTipsDialog(View view) {
        dismiss();
        if (!AppUtils.isEnableGuard()) {
            showToast(getString(R$string.fq_guard_model_close));
        } else {
            GuardOpenContentDialog.newInstance(this.guardItem, this.openGuardCallbackListener).show(getFragmentManager());
        }
    }

    public /* synthetic */ void lambda$initView$3$GuardOpenTipsDialog(View view) {
        dismiss();
    }

    private void setOpenGuardCallbackListener(GuardOpenContentDialog.OnOpenGuardCallbackListener onOpenGuardCallbackListener) {
        this.openGuardCallbackListener = onOpenGuardCallbackListener;
    }
}
