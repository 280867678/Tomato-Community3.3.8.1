package com.tomatolive.library.p136ui.adapter;

import android.content.Context;
import android.support.p002v4.content.ContextCompat;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import com.blankj.utilcode.util.TimeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tomatolive.library.R$array;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$string;
import com.tomatolive.library.model.IncomeEntity;
import com.tomatolive.library.model.PaidLiveIncomeExpenseEntity;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.GlideUtils;
import com.tomatolive.library.utils.NumberUtils;
import org.slf4j.Marker;

/* renamed from: com.tomatolive.library.ui.adapter.IncomeDetailAdapter */
/* loaded from: classes3.dex */
public class IncomeDetailAdapter extends BaseQuickAdapter<IncomeEntity, BaseViewHolder> {
    private boolean isExpend;
    private int type;

    public IncomeDetailAdapter(int i, int i2) {
        super(i);
        this.isExpend = false;
        this.type = i2;
    }

    public IncomeDetailAdapter(int i, int i2, boolean z) {
        super(i);
        this.isExpend = false;
        this.type = i2;
        this.isExpend = z;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, IncomeEntity incomeEntity) {
        Context context;
        int i;
        int i2;
        BaseViewHolder text = baseViewHolder.setText(R$id.tv_reward_tips, incomeEntity.getFirstLine(this.mContext, this.isExpend)).setText(R$id.tv_reward_time, getTimeStr(incomeEntity)).setText(R$id.tv_count, formatCount(incomeEntity.getCount(this.isExpend)));
        int i3 = R$id.tv_reward_tips;
        if (this.isExpend || (i2 = this.type) == 4 || i2 == 5) {
            context = this.mContext;
            i = R$color.fq_text_black;
        } else {
            context = this.mContext;
            i = R$color.fq_colorPrimary;
        }
        text.setTextColor(i3, ContextCompat.getColor(context, i));
        ImageView imageView = (ImageView) baseViewHolder.getView(R$id.iv_logo);
        int i4 = this.type;
        if (i4 == 1 || i4 == 6) {
            GlideUtils.loadImage(this.mContext, imageView, incomeEntity.getImgUrl(), R$drawable.fq_ic_gift_default);
        } else if (i4 == 3) {
            GlideUtils.loadImage(this.mContext, imageView, incomeEntity.getImgUrl(), R$drawable.fq_ic_placeholder_avatar);
        } else if (i4 == 2) {
            imageView.setImageResource(incomeEntity.getIconImg());
        } else if (i4 == 5) {
            imageView.setImageResource(incomeEntity.getIconImg());
        } else if (i4 == 8) {
            if (this.isExpend) {
                GlideUtils.loadAvatar(this.mContext, imageView, incomeEntity.getImgUrl());
            } else {
                imageView.setImageResource(incomeEntity.getIconImg());
            }
        } else {
            imageView.setVisibility(8);
        }
        TextView textView = (TextView) baseViewHolder.getView(R$id.tv_beneficiary);
        if (incomeEntity.hasNobleExtraText(this.isExpend)) {
            textView.setVisibility(0);
            textView.setText(incomeEntity.getNobleExtraText(this.mContext, this.isExpend));
            return;
        }
        textView.setVisibility(8);
    }

    private String formatCount(String str) {
        String formatDisplayPrice;
        boolean isEmpty = TextUtils.isEmpty(str);
        String str2 = Marker.ANY_NON_NULL_MARKER;
        if (isEmpty || TextUtils.equals(str, "0")) {
            StringBuilder sb = new StringBuilder();
            if (this.isExpend) {
                sb.append(ConstantUtils.PLACEHOLDER_STR_ONE);
            } else {
                sb.append(str2);
                sb.append(0);
            }
            return sb.toString();
        }
        if (this.type == 7) {
            if (this.isExpend) {
                formatDisplayPrice = str;
            } else {
                formatDisplayPrice = str + this.mContext.getString(R$string.fq_popularity);
            }
        } else {
            formatDisplayPrice = AppUtils.formatDisplayPrice(str, false);
        }
        StringBuilder sb2 = new StringBuilder();
        if (this.isExpend) {
            str2 = "-";
        }
        sb2.append(str2);
        sb2.append(formatDisplayPrice);
        return sb2.toString();
    }

    private String getTimeStr(IncomeEntity incomeEntity) {
        if (this.type == 8 && (incomeEntity instanceof PaidLiveIncomeExpenseEntity)) {
            StringBuilder sb = new StringBuilder();
            PaidLiveIncomeExpenseEntity paidLiveIncomeExpenseEntity = (PaidLiveIncomeExpenseEntity) incomeEntity;
            if (TextUtils.equals(paidLiveIncomeExpenseEntity.getChargeType(), "1")) {
                sb.append(this.mContext.getResources().getStringArray(R$array.fq_charge_type_menu)[1]);
                sb.append(ConstantUtils.PLACEHOLDER_STR_TWO);
            }
            if (this.isExpend) {
                sb.append(TimeUtils.millis2String(NumberUtils.string2long(incomeEntity.getRecordTime()) * 1000, "MM-dd HH:mm"));
            } else {
                sb.append(TimeUtils.millis2String(NumberUtils.string2long(paidLiveIncomeExpenseEntity.getBeginTime()) * 1000, "MM-dd HH:mm"));
            }
            return sb.toString();
        }
        int i = this.type;
        if (i == 6 || i == 4) {
            return incomeEntity.formatRecordTime(this.mContext, this.isExpend);
        }
        return TimeUtils.millis2String(NumberUtils.string2long(incomeEntity.getRecordTime()) * 1000);
    }
}
