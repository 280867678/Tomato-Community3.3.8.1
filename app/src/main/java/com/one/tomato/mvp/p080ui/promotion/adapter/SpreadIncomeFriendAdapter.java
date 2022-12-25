package com.one.tomato.mvp.p080ui.promotion.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.p005v7.widget.RecyclerView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.entity.PromotionFanyong;
import com.one.tomato.entity.PromotionFriend;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.one.tomato.utils.AppUtil;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SpreadIncomeFriendAdapter.kt */
/* renamed from: com.one.tomato.mvp.ui.promotion.adapter.SpreadIncomeFriendAdapter */
/* loaded from: classes3.dex */
public final class SpreadIncomeFriendAdapter extends BaseRecyclerViewAdapter<PromotionFriend> {
    private PromotionFanyong promotionFanyong;

    public SpreadIncomeFriendAdapter(Context context, RecyclerView recyclerView) {
        super(context, R.layout.item_spread_income, recyclerView);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder holder, PromotionFriend promotionFriend) {
        Intrinsics.checkParameterIsNotNull(holder, "holder");
        super.convert(holder, (BaseViewHolder) promotionFriend);
        int indexOf = this.mData.indexOf(promotionFriend);
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
        Integer num = null;
        objArr[0] = String.valueOf(promotionFriend != null ? Integer.valueOf(promotionFriend.getLevel1()) : null);
        textView.setText(AppUtil.getString(R.string.spread_income_level, objArr));
        textView2.setText(promotionFriend != null ? promotionFriend.getAccount() : null);
        Integer valueOf = promotionFriend != null ? Integer.valueOf(promotionFriend.getLevel1()) : null;
        if (valueOf != null && valueOf.intValue() == 1) {
            PromotionFanyong promotionFanyong = this.promotionFanyong;
            if (promotionFanyong != null) {
                num = Integer.valueOf(promotionFanyong.levelRateOne);
            }
            textView3.setText(String.valueOf(num));
        } else if (valueOf != null && valueOf.intValue() == 2) {
            PromotionFanyong promotionFanyong2 = this.promotionFanyong;
            if (promotionFanyong2 != null) {
                num = Integer.valueOf(promotionFanyong2.levelRateTwo);
            }
            textView3.setText(String.valueOf(num));
        } else if (valueOf != null && valueOf.intValue() == 3) {
            PromotionFanyong promotionFanyong3 = this.promotionFanyong;
            if (promotionFanyong3 != null) {
                num = Integer.valueOf(promotionFanyong3.levelRateThree);
            }
            textView3.setText(String.valueOf(num));
        } else if (valueOf != null && valueOf.intValue() == 4) {
            PromotionFanyong promotionFanyong4 = this.promotionFanyong;
            if (promotionFanyong4 != null) {
                num = Integer.valueOf(promotionFanyong4.levelRateFour);
            }
            textView3.setText(String.valueOf(num));
        } else if (valueOf == null || valueOf.intValue() != 5) {
        } else {
            PromotionFanyong promotionFanyong5 = this.promotionFanyong;
            if (promotionFanyong5 != null) {
                num = Integer.valueOf(promotionFanyong5.levelRateFive);
            }
            textView3.setText(String.valueOf(num));
        }
    }

    public final void setPromotionFanyong(PromotionFanyong promotionFanyong) {
        this.promotionFanyong = promotionFanyong;
    }
}
