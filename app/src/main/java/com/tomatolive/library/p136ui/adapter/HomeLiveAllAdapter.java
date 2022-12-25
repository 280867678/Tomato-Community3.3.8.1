package com.tomatolive.library.p136ui.adapter;

import android.content.Context;
import android.support.p002v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.model.BannerEntity;
import com.tomatolive.library.model.LiveEntity;
import com.tomatolive.library.p136ui.view.widget.bgabanner.BGABanner;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.DateUtils;
import com.tomatolive.library.utils.GlideUtils;
import com.tomatolive.library.utils.NumberUtils;
import com.tomatolive.library.utils.StringUtils;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.adapter.HomeLiveAllAdapter */
/* loaded from: classes3.dex */
public class HomeLiveAllAdapter extends BaseMultiItemQuickAdapter<LiveEntity, BaseViewHolder> {
    public static final int IMG_BANNER = 2;
    public static final int IMG_TEXT = 1;
    private Fragment fragment;

    public HomeLiveAllAdapter(List<LiveEntity> list) {
        super(list);
        this.fragment = null;
        addItemType();
    }

    public HomeLiveAllAdapter(Fragment fragment, List<LiveEntity> list) {
        super(list);
        this.fragment = null;
        this.fragment = fragment;
        addItemType();
    }

    private void addItemType() {
        addItemType(1, R$layout.fq_item_list_live_view_new);
        addItemType(2, R$layout.fq_layout_home_all_banner_view);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, LiveEntity liveEntity) {
        int itemViewType = baseViewHolder.getItemViewType();
        boolean z = false;
        if (itemViewType != 1) {
            if (itemViewType != 2) {
                return;
            }
            BGABanner bGABanner = (BGABanner) baseViewHolder.getView(R$id.iv_banner);
            List<BannerEntity> imgBannerItem = AppUtils.getImgBannerItem(liveEntity.bannerList);
            if (imgBannerItem == null || imgBannerItem.isEmpty()) {
                return;
            }
            bGABanner.setAdapter(new BGABanner.Adapter() { // from class: com.tomatolive.library.ui.adapter.-$$Lambda$HomeLiveAllAdapter$KC3F1ZQKmMZDoFKu6VU8Eqdeffk
                @Override // com.tomatolive.library.p136ui.view.widget.bgabanner.BGABanner.Adapter
                public final void fillBannerItem(BGABanner bGABanner2, View view, Object obj, int i) {
                    HomeLiveAllAdapter.this.lambda$convert$0$HomeLiveAllAdapter(bGABanner2, (ImageView) view, (BannerEntity) obj, i);
                }
            });
            bGABanner.setData(imgBannerItem, null);
            if (imgBannerItem.size() > 1) {
                z = true;
            }
            bGABanner.setAutoPlayAble(z);
            bGABanner.setDelegate(new BGABanner.Delegate() { // from class: com.tomatolive.library.ui.adapter.-$$Lambda$HomeLiveAllAdapter$C6n3sBbrF_XI7IwQ2sQE8KMC6jw
                @Override // com.tomatolive.library.p136ui.view.widget.bgabanner.BGABanner.Delegate
                public final void onBannerItemClick(BGABanner bGABanner2, View view, Object obj, int i) {
                    HomeLiveAllAdapter.this.lambda$convert$1$HomeLiveAllAdapter(bGABanner2, (ImageView) view, (BannerEntity) obj, i);
                }
            });
            return;
        }
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
        Fragment fragment = this.fragment;
        if (fragment != null) {
            GlideUtils.loadAdBannerImageForRoundView(fragment, imageView4, str, R$drawable.fq_ic_placeholder_corners);
        } else {
            GlideUtils.loadAdBannerImageForRoundView(this.mContext, imageView4, str, R$drawable.fq_ic_placeholder_corners);
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

    public /* synthetic */ void lambda$convert$0$HomeLiveAllAdapter(BGABanner bGABanner, ImageView imageView, BannerEntity bannerEntity, int i) {
        GlideUtils.loadAdBannerImageForRoundView(this.mContext, imageView, bannerEntity.img, R$drawable.fq_shape_default_banner_cover_bg);
    }

    public /* synthetic */ void lambda$convert$1$HomeLiveAllAdapter(BGABanner bGABanner, ImageView imageView, BannerEntity bannerEntity, int i) {
        AppUtils.clickBannerEvent(this.mContext, bannerEntity);
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
