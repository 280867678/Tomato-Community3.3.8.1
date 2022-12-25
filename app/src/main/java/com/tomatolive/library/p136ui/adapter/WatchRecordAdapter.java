package com.tomatolive.library.p136ui.adapter;

import android.widget.ImageView;
import com.blankj.utilcode.util.TimeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.model.p135db.WatchRecordEntity;
import com.tomatolive.library.utils.GlideUtils;
import com.tomatolive.library.utils.StringUtils;

/* renamed from: com.tomatolive.library.ui.adapter.WatchRecordAdapter */
/* loaded from: classes3.dex */
public class WatchRecordAdapter extends BaseQuickAdapter<WatchRecordEntity, BaseViewHolder> {
    public WatchRecordAdapter(int i) {
        super(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, WatchRecordEntity watchRecordEntity) {
        baseViewHolder.setText(R$id.tv_live_title, StringUtils.formatStrLen(watchRecordEntity.title, 15)).setText(R$id.tv_anchor_nickname, watchRecordEntity.anchorNickname).setText(R$id.tv_live_label, watchRecordEntity.label).setText(R$id.tv_live_time, TimeUtils.getFriendlyTimeSpanByNow(watchRecordEntity.liveTime)).setVisible(R$id.iv_living_label, false).addOnClickListener(R$id.rl_content).addOnClickListener(R$id.tv_delete);
        GlideUtils.loadRoundCornersImage(this.mContext, (ImageView) baseViewHolder.getView(R$id.iv_cover), watchRecordEntity.coverUrl, 6, R$drawable.fq_ic_placeholder_corners);
    }

    public void clearAll() {
        getData().clear();
        notifyDataSetChanged();
    }
}
