package com.one.tomato.mvp.p080ui.promotion.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.p005v7.widget.RecyclerView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.entity.PromotionTop50;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SpreadIncomeTop50Adapter.kt */
/* renamed from: com.one.tomato.mvp.ui.promotion.adapter.SpreadIncomeTop50Adapter */
/* loaded from: classes3.dex */
public final class SpreadIncomeTop50Adapter extends BaseRecyclerViewAdapter<PromotionTop50.C2525Record> {
    public SpreadIncomeTop50Adapter(Context context, RecyclerView recyclerView) {
        super(context, R.layout.item_spread_income, recyclerView);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder holder, PromotionTop50.C2525Record c2525Record) {
        Intrinsics.checkParameterIsNotNull(holder, "holder");
        super.convert(holder, (BaseViewHolder) c2525Record);
        int indexOf = this.mData.indexOf(c2525Record);
        ConstraintLayout constraintLayout = (ConstraintLayout) holder.getView(R.id.cl_root);
        TextView textView = (TextView) holder.getView(R.id.tv_title);
        TextView textView2 = (TextView) holder.getView(R.id.tv_name);
        TextView textView3 = (TextView) holder.getView(R.id.tv_money);
        if (indexOf % 2 == 0) {
            Context mContext = this.mContext;
            Intrinsics.checkExpressionValueIsNotNull(mContext, "mContext");
            constraintLayout.setBackgroundColor(mContext.getResources().getColor(R.color.app_bg_grey));
        } else {
            Context mContext2 = this.mContext;
            Intrinsics.checkExpressionValueIsNotNull(mContext2, "mContext");
            constraintLayout.setBackgroundColor(mContext2.getResources().getColor(R.color.white));
        }
        String str = null;
        textView.setText(c2525Record != null ? c2525Record.f1723id : null);
        textView2.setText(c2525Record != null ? c2525Record.telephone : null);
        if (c2525Record != null) {
            str = c2525Record.money;
        }
        textView3.setText(str);
    }
}
