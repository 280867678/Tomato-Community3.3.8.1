package com.one.tomato.mvp.p080ui.vip.adapter;

import android.content.Context;
import android.support.p005v7.widget.RecyclerView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.entity.VipHistory;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.one.tomato.utils.AppUtil;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: VipHistoryAdapter.kt */
/* renamed from: com.one.tomato.mvp.ui.vip.adapter.VipHistoryAdapter */
/* loaded from: classes3.dex */
public final class VipHistoryAdapter extends BaseRecyclerViewAdapter<VipHistory> {
    public VipHistoryAdapter(Context context, RecyclerView recyclerView) {
        super(context, R.layout.item_vip_history, recyclerView);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder holder, VipHistory vipHistory) {
        String string;
        Intrinsics.checkParameterIsNotNull(holder, "holder");
        super.convert(holder, (BaseViewHolder) vipHistory);
        TextView textView = (TextView) holder.getView(R.id.tv_title);
        TextView textView2 = (TextView) holder.getView(R.id.tv_content);
        TextView textView3 = (TextView) holder.getView(R.id.tv_cur_price);
        Integer num = null;
        Integer valueOf = vipHistory != null ? Integer.valueOf(vipHistory.operationType) : null;
        if (valueOf != null && valueOf.intValue() == 1) {
            string = AppUtil.getString(R.string.vip_package_agent);
            Intrinsics.checkExpressionValueIsNotNull(string, "AppUtil.getString(R.string.vip_package_agent)");
        } else {
            string = AppUtil.getString(R.string.vip_open_y);
            Intrinsics.checkExpressionValueIsNotNull(string, "AppUtil.getString(R.string.vip_open_y)");
        }
        textView.setText(string);
        if (vipHistory != null && vipHistory.operationType == 1) {
            textView2.setText(R.string.vip_status_expire_forever);
        } else {
            Object[] objArr = new Object[1];
            objArr[0] = vipHistory != null ? vipHistory.expireTimeAfter : null;
            textView2.setText(AppUtil.getString(R.string.vip_status_expire_time, objArr));
        }
        Object[] objArr2 = new Object[1];
        if (vipHistory != null) {
            num = Integer.valueOf(vipHistory.amount / 100);
        }
        objArr2[0] = num;
        textView3.setText(AppUtil.getString(R.string.vip_money_cur, objArr2));
    }
}
