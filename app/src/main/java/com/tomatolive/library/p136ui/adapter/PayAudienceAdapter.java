package com.tomatolive.library.p136ui.adapter;

import android.text.TextUtils;
import android.widget.ImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$string;
import com.tomatolive.library.model.UserEntity;
import com.tomatolive.library.utils.DateUtils;
import com.tomatolive.library.utils.GlideUtils;
import com.tomatolive.library.utils.NumberUtils;

/* renamed from: com.tomatolive.library.ui.adapter.PayAudienceAdapter */
/* loaded from: classes3.dex */
public class PayAudienceAdapter extends BaseQuickAdapter<UserEntity, BaseViewHolder> {
    public PayAudienceAdapter(int i) {
        super(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, UserEntity userEntity) {
        baseViewHolder.setText(R$id.tv_nick_name, userEntity.name).setText(R$id.tv_watch_time, this.mContext.getString(R$string.fq_pay_watched_time, formatTime(userEntity.getLiveStaySeconds())));
        GlideUtils.loadAvatar(this.mContext, (ImageView) baseViewHolder.getView(R$id.iv_avatar), userEntity.avatar);
    }

    public String formatTime(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        long string2long = ((NumberUtils.string2long(str) * 1000) % DateUtils.ONE_HOUR_MILLIONS) / DateUtils.ONE_MINUTE_MILLIONS;
        return string2long <= 1 ? this.mContext.getString(R$string.fq_live_time_minute, "≤1") : String.valueOf(string2long);
    }
}
