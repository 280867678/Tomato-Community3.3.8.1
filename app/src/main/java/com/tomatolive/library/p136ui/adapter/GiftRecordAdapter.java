package com.tomatolive.library.p136ui.adapter;

import android.support.p002v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import com.blankj.utilcode.util.TimeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$id;
import com.tomatolive.library.model.ReceiveGiftRecordEntity;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.DateUtils;
import com.tomatolive.library.utils.NumberUtils;
import java.text.SimpleDateFormat;

/* renamed from: com.tomatolive.library.ui.adapter.GiftRecordAdapter */
/* loaded from: classes3.dex */
public class GiftRecordAdapter extends BaseQuickAdapter<ReceiveGiftRecordEntity, BaseViewHolder> {
    public GiftRecordAdapter(int i) {
        super(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, ReceiveGiftRecordEntity receiveGiftRecordEntity) {
        String str = receiveGiftRecordEntity.name + ConstantUtils.PLACEHOLDER_STR_TWO + receiveGiftRecordEntity.description;
        SpannableString spannableString = new SpannableString(str);
        spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this.mContext, R$color.fq_colorPrimary)), 0, receiveGiftRecordEntity.name.length(), 33);
        spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this.mContext, R$color.fq_colorWhite)), receiveGiftRecordEntity.name.length() + 1, str.length(), 33);
        baseViewHolder.setText(R$id.tv_gift_time, getOpenTime(receiveGiftRecordEntity.createTime, DateUtils.C_TIME_PATTON_DEFAULT_2)).setText(R$id.tv_gift_desc, spannableString);
    }

    private String getOpenTime(String str, String str2) {
        return TimeUtils.millis2String(NumberUtils.string2long(str) * 1000, new SimpleDateFormat(str2));
    }
}
