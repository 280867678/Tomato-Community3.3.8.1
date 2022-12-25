package com.one.tomato.mvp.p080ui.feedback.adapter;

import android.content.Context;
import android.support.p005v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.dialog.CustomAlertDialog;
import com.one.tomato.entity.RechargeProblemOrder;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.image.ImageLoaderUtil;
import com.tomatolive.library.utils.ConstantUtils;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;

/* compiled from: FeedbackOrderAdapter.kt */
/* renamed from: com.one.tomato.mvp.ui.feedback.adapter.FeedbackOrderAdapter */
/* loaded from: classes3.dex */
public final class FeedbackOrderAdapter extends BaseRecyclerViewAdapter<RechargeProblemOrder> {
    public FeedbackOrderAdapterListener listener;

    /* compiled from: FeedbackOrderAdapter.kt */
    /* renamed from: com.one.tomato.mvp.ui.feedback.adapter.FeedbackOrderAdapter$FeedbackOrderAdapterListener */
    /* loaded from: classes3.dex */
    public interface FeedbackOrderAdapterListener {
        void onItemClick(BaseQuickAdapter<?, ?> baseQuickAdapter, View view, int i);
    }

    public FeedbackOrderAdapter(Context context, RecyclerView recyclerView) {
        super(context, R.layout.item_feedback_problem_order, recyclerView);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, RechargeProblemOrder rechargeProblemOrder) {
        super.convert(baseViewHolder, (BaseViewHolder) rechargeProblemOrder);
        String str = null;
        if (baseViewHolder != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(AppUtil.getString(R.string.problem_order_id));
            sb.append(ConstantUtils.PLACEHOLDER_STR_ONE);
            sb.append(rechargeProblemOrder != null ? rechargeProblemOrder.getId() : null);
            baseViewHolder.setText(R.id.tv_order, sb.toString());
        }
        if (baseViewHolder != null) {
            baseViewHolder.setText(R.id.tv_money, rechargeProblemOrder != null ? rechargeProblemOrder.getMoney() : null);
        }
        if (baseViewHolder != null) {
            if (rechargeProblemOrder != null) {
                str = rechargeProblemOrder.getDate();
            }
            baseViewHolder.setText(R.id.tv_date, str);
        }
    }

    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
    public void onRecyclerItemClick(BaseQuickAdapter<?, ?> baseQuickAdapter, View view, int i) {
        super.onRecyclerItemClick(baseQuickAdapter, view, i);
        Object obj = this.mData.get(i);
        Intrinsics.checkExpressionValueIsNotNull(obj, "mData[position]");
        if (((RechargeProblemOrder) obj).getIsJinYu() == 1) {
            showJYTipDialog(baseQuickAdapter, view, i);
            return;
        }
        FeedbackOrderAdapterListener feedbackOrderAdapterListener = this.listener;
        if (feedbackOrderAdapterListener != null) {
            feedbackOrderAdapterListener.onItemClick(baseQuickAdapter, view, i);
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("listener");
            throw null;
        }
    }

    /* JADX WARN: Type inference failed for: r9v1, types: [T, com.one.tomato.dialog.CustomAlertDialog] */
    private final void showJYTipDialog(final BaseQuickAdapter<?, ?> baseQuickAdapter, View view, final int i) {
        final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
        ref$ObjectRef.element = new CustomAlertDialog(this.mContext);
        ((CustomAlertDialog) ref$ObjectRef.element).bottomLayoutGone();
        final View inflate = LayoutInflater.from(this.mContext).inflate(R.layout.dialog_recharge_feedback_pay_order_tip, (ViewGroup) null);
        ((CustomAlertDialog) ref$ObjectRef.element).setContentView(inflate);
        TextView textView = (TextView) inflate.findViewById(R.id.tv_get);
        ImageLoaderUtil.loadNormalDrawableGif(this.mContext, (ImageView) inflate.findViewById(R.id.iv_demo), R.drawable.recharge_feedback_img_pay_order);
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.feedback.adapter.FeedbackOrderAdapter$showJYTipDialog$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                ((CustomAlertDialog) ref$ObjectRef.element).dismiss();
                FeedbackOrderAdapter.this.getListener().onItemClick(baseQuickAdapter, inflate, i);
            }
        });
    }

    public final FeedbackOrderAdapterListener getListener() {
        FeedbackOrderAdapterListener feedbackOrderAdapterListener = this.listener;
        if (feedbackOrderAdapterListener != null) {
            return feedbackOrderAdapterListener;
        }
        Intrinsics.throwUninitializedPropertyAccessException("listener");
        throw null;
    }

    public final void setFeedbackOrderAdapterListener(FeedbackOrderAdapterListener listener) {
        Intrinsics.checkParameterIsNotNull(listener, "listener");
        this.listener = listener;
    }
}
