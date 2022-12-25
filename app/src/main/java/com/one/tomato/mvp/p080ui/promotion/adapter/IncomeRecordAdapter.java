package com.one.tomato.mvp.p080ui.promotion.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.p005v7.widget.RecyclerView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.entity.PromotionIncomeRecord;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.FormatUtil;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: IncomeRecordAdapter.kt */
/* renamed from: com.one.tomato.mvp.ui.promotion.adapter.IncomeRecordAdapter */
/* loaded from: classes3.dex */
public final class IncomeRecordAdapter extends BaseRecyclerViewAdapter<PromotionIncomeRecord.C2523Record> {
    public IncomeRecordAdapter(Context context, RecyclerView recyclerView) {
        super(context, R.layout.item_spread_income_record, recyclerView);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder holder, PromotionIncomeRecord.C2523Record c2523Record) {
        String str;
        Intrinsics.checkParameterIsNotNull(holder, "holder");
        super.convert(holder, (BaseViewHolder) c2523Record);
        int indexOf = this.mData.indexOf(c2523Record);
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
        Object[] objArr = new Object[1];
        Double d = null;
        objArr[0] = String.valueOf(c2523Record != null ? Integer.valueOf(c2523Record.level) : null);
        textView.setText(AppUtil.getString(R.string.spread_income_level, objArr));
        textView2.setText(c2523Record != null ? c2523Record.telephone : null);
        if (c2523Record != null && (str = c2523Record.totalSettlement) != null) {
            d = Double.valueOf(Double.parseDouble(str) / 100);
        }
        textView3.setText(FormatUtil.formatTwo(d));
    }
}
