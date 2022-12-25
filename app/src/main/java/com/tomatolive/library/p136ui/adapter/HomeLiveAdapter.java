package com.tomatolive.library.p136ui.adapter;

import android.content.Context;
import android.support.p002v4.app.Fragment;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$string;
import com.tomatolive.library.model.LiveEntity;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.DateUtils;
import com.tomatolive.library.utils.GlideUtils;
import com.tomatolive.library.utils.NumberUtils;
import com.tomatolive.library.utils.StringUtils;

/* renamed from: com.tomatolive.library.ui.adapter.HomeLiveAdapter */
/* loaded from: classes3.dex */
public class HomeLiveAdapter extends BaseQuickAdapter<LiveEntity, BaseViewHolder> {
    private Fragment fragment;

    public HomeLiveAdapter(int i) {
        super(i);
        this.fragment = null;
    }

    public HomeLiveAdapter(Fragment fragment, int i) {
        super(i);
        this.fragment = null;
        this.fragment = fragment;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, LiveEntity liveEntity) {
        baseViewHolder.setText(R$id.tv_live_title, StringUtils.formatStrLen(liveEntity.topic, 15)).setText(R$id.tv_personal, liveEntity.getLivePopularityStr()).setText(R$id.tv_nick_name, liveEntity.nickname).setText(R$id.tv_pre_notice, getPreNoticeStr(liveEntity)).setVisible(R$id.rl_live_status, !isLiving(liveEntity)).setGone(R$id.iv_live_dot, !liveEntity.isAd).setVisible(R$id.rl_pre_notice_bg, isPreNotice(liveEntity)).setVisible(R$id.tv_ad, liveEntity.isAd).setVisible(R$id.tv_personal, !liveEntity.isAd);
        ImageView imageView = (ImageView) baseViewHolder.getView(R$id.iv_pendant);
        int i = 8;
        imageView.setVisibility(TextUtils.isEmpty(liveEntity.pendantUrl) ? 8 : 0);
        if (!TextUtils.isEmpty(liveEntity.pendantUrl)) {
            GlideUtils.loadImageNormal(this.mContext, imageView, liveEntity.pendantUrl);
        }
        ImageView imageView2 = (ImageView) baseViewHolder.getView(R$id.iv_label);
        imageView2.setVisibility(TextUtils.isEmpty(liveEntity.markerUrl) ? 8 : 0);
        if (!TextUtils.isEmpty(liveEntity.markerUrl)) {
            GlideUtils.loadImageNormal(this.mContext, imageView2, liveEntity.markerUrl);
        }
        ImageView imageView3 = (ImageView) baseViewHolder.getView(R$id.iv_cover_identity);
        if (!TextUtils.isEmpty(liveEntity.getCoverIdentityUrl())) {
            i = 0;
        }
        imageView3.setVisibility(i);
        if (!TextUtils.isEmpty(liveEntity.getCoverIdentityUrl())) {
            GlideUtils.loadImageNormal(this.mContext, imageView3, liveEntity.getCoverIdentityUrl());
        }
        ImageView imageView4 = (ImageView) baseViewHolder.getView(R$id.iv_cover);
        String str = TextUtils.isEmpty(liveEntity.liveCoverUrl) ? liveEntity.avatar : liveEntity.liveCoverUrl;
        if (liveEntity.isAd) {
            GlideUtils.loadAdBannerImageForRoundView(this.mContext, imageView4, str, R$drawable.fq_ic_placeholder_corners);
            return;
        }
        Fragment fragment = this.fragment;
        if (fragment != null) {
            GlideUtils.loadRoundCornersImage(fragment, imageView4, str, 6, R$drawable.fq_ic_placeholder_corners);
        } else {
            GlideUtils.loadRoundCornersImage(this.mContext, imageView4, str, 6, R$drawable.fq_ic_placeholder_corners);
        }
        TextView textView = (TextView) baseViewHolder.getView(R$id.tv_pay_ticket);
        if (liveEntity.isPayLiveTicket() && isLiving(liveEntity)) {
            textView.setVisibility(0);
            Context context = this.mContext;
            textView.setText(context.getString(R$string.fq_pay_ticket_price, AppUtils.formatMoneyUnitStr(context, liveEntity.getPayLivePrice(), false)));
            return;
        }
        textView.setVisibility(4);
    }

    private boolean isPreNotice(LiveEntity liveEntity) {
        return !liveEntity.isAd && !TextUtils.isEmpty(liveEntity.herald) && !liveEntity.isOnLiving();
    }

    private String getPreNoticeStr(LiveEntity liveEntity) {
        if (liveEntity.isAd) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(liveEntity.herald);
        if (!TextUtils.isEmpty(liveEntity.publishTime)) {
            sb.append("\n");
            sb.append("(");
            sb.append(DateUtils.getShortTime(NumberUtils.string2long(liveEntity.publishTime) * 1000));
            sb.append(")");
        }
        return sb.toString();
    }

    private boolean isLiving(LiveEntity liveEntity) {
        if (liveEntity.isAd) {
            return true;
        }
        return liveEntity.isOnLiving();
    }
}
