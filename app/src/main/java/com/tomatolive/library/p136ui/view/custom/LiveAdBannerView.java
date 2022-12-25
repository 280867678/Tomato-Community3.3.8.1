package com.tomatolive.library.p136ui.view.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.j256.ormlite.stmt.query.SimpleComparison;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.model.BannerEntity;
import com.tomatolive.library.p136ui.interfaces.OnLiveAdBannerClickListener;
import com.tomatolive.library.p136ui.view.widget.BannerWebView;
import com.tomatolive.library.p136ui.view.widget.bgabanner.BGABanner;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.GlideUtils;
import com.tomatolive.library.utils.LogConstants;
import com.tomatolive.library.utils.RxViewUtils;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.custom.LiveAdBannerView */
/* loaded from: classes3.dex */
public class LiveAdBannerView extends LinearLayout {
    private BGABanner bannerView;
    private ImageView ivAdClose;
    private ImageView ivAdImg;
    private ImageView ivBannerClose;
    private Context mContext;
    private OnLiveAdBannerClickListener onAdBannerClickListener;
    private RelativeLayout rlAdBg;
    private RelativeLayout rlBannerBg;
    private BannerWebView wvVerticalAd;

    public LiveAdBannerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        initView();
    }

    private void initView() {
        LinearLayout.inflate(this.mContext, R$layout.fq_layout_live_banner_view, this);
        this.bannerView = (BGABanner) findViewById(R$id.banner_top);
        this.ivAdImg = (ImageView) findViewById(R$id.iv_ad);
        this.ivBannerClose = (ImageView) findViewById(R$id.iv_banner_close);
        this.ivAdClose = (ImageView) findViewById(R$id.iv_ad_close);
        this.rlAdBg = (RelativeLayout) findViewById(R$id.rl_ad_bg);
        this.rlBannerBg = (RelativeLayout) findViewById(R$id.rl_banner_bg);
        this.wvVerticalAd = (BannerWebView) findViewById(R$id.wv_vertical_ad);
        this.ivAdClose.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.custom.-$$Lambda$LiveAdBannerView$v0Yw9GbAi-EbJSQHKUycVIwE7rk
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                LiveAdBannerView.this.lambda$initView$0$LiveAdBannerView(view);
            }
        });
        this.ivBannerClose.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.custom.-$$Lambda$LiveAdBannerView$AAN0piByH9FLjon7wHWm-pZWXaw
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                LiveAdBannerView.this.lambda$initView$1$LiveAdBannerView(view);
            }
        });
    }

    public /* synthetic */ void lambda$initView$0$LiveAdBannerView(View view) {
        this.rlAdBg.setVisibility(8);
    }

    public /* synthetic */ void lambda$initView$1$LiveAdBannerView(View view) {
        this.rlBannerBg.setVisibility(8);
    }

    public void initAdBannerImages(final String str, final String str2, List<BannerEntity> list) {
        if (this.bannerView == null) {
            return;
        }
        List<BannerEntity> imgBannerItem = AppUtils.getImgBannerItem(list);
        if (imgBannerItem == null || imgBannerItem.isEmpty()) {
            this.rlBannerBg.setVisibility(8);
            return;
        }
        this.rlBannerBg.setVisibility(0);
        this.ivBannerClose.setVisibility(isAllowClose(list) ? 0 : 4);
        this.bannerView.setAdapter(new BGABanner.Adapter() { // from class: com.tomatolive.library.ui.view.custom.-$$Lambda$LiveAdBannerView$HpWgBAr-VIelmlfU-ddGtrOJw48
            @Override // com.tomatolive.library.p136ui.view.widget.bgabanner.BGABanner.Adapter
            public final void fillBannerItem(BGABanner bGABanner, View view, Object obj, int i) {
                LiveAdBannerView.this.lambda$initAdBannerImages$2$LiveAdBannerView(str, str2, bGABanner, view, (BannerEntity) obj, i);
            }
        });
        BGABanner bGABanner = this.bannerView;
        boolean z = true;
        if (imgBannerItem.size() <= 1) {
            z = false;
        }
        bGABanner.setAutoPlayAble(z);
        this.bannerView.setDataWithWebView(getBannerView(imgBannerItem), imgBannerItem);
        this.bannerView.setDelegate(new BGABanner.Delegate() { // from class: com.tomatolive.library.ui.view.custom.-$$Lambda$LiveAdBannerView$_dnXH8coVxWtIyPVOC6Uv36ozc4
            @Override // com.tomatolive.library.p136ui.view.widget.bgabanner.BGABanner.Delegate
            public final void onBannerItemClick(BGABanner bGABanner2, View view, Object obj, int i) {
                LiveAdBannerView.this.lambda$initAdBannerImages$3$LiveAdBannerView(bGABanner2, view, (BannerEntity) obj, i);
            }
        });
    }

    public /* synthetic */ void lambda$initAdBannerImages$2$LiveAdBannerView(String str, String str2, BGABanner bGABanner, View view, BannerEntity bannerEntity, int i) {
        if (view instanceof ImageView) {
            loadImg(bannerEntity.img, (ImageView) view);
        }
        if (view instanceof BannerWebView) {
            BannerWebView bannerWebView = (BannerWebView) view;
            if (bannerWebView.isLoadBoolean()) {
                return;
            }
            bannerWebView.loadUrl(getWebH5Url(bannerEntity.img, str, str2));
        }
    }

    public /* synthetic */ void lambda$initAdBannerImages$3$LiveAdBannerView(BGABanner bGABanner, View view, BannerEntity bannerEntity, int i) {
        onAdBannerClick(bannerEntity);
    }

    public void initVerticalAdImage(String str, String str2, List<BannerEntity> list) {
        if (list == null || list.isEmpty()) {
            this.rlAdBg.setVisibility(4);
            return;
        }
        List<BannerEntity> imgBannerItem = AppUtils.getImgBannerItem(list);
        if (imgBannerItem.size() <= 0) {
            return;
        }
        final BannerEntity bannerEntity = imgBannerItem.get(0);
        this.rlAdBg.setVisibility(0);
        this.ivAdClose.setVisibility(bannerEntity.isAllowClose() ? 4 : 0);
        if (bannerEntity.isWebH5View()) {
            this.ivAdImg.setVisibility(4);
            this.wvVerticalAd.setVisibility(0);
            this.wvVerticalAd.loadUrl(getWebH5Url(bannerEntity.img, str, str2));
        } else {
            this.wvVerticalAd.setVisibility(4);
            this.ivAdImg.setVisibility(0);
            loadImg(bannerEntity.img, this.ivAdImg);
        }
        RxViewUtils.getInstance().throttleFirst(this.rlAdBg, 500, new RxViewUtils.RxViewAction() { // from class: com.tomatolive.library.ui.view.custom.-$$Lambda$LiveAdBannerView$A5m8j5u36oeSIAunu-VSx-sxSDI
            @Override // com.tomatolive.library.utils.RxViewUtils.RxViewAction
            public final void action(Object obj) {
                LiveAdBannerView.this.lambda$initVerticalAdImage$4$LiveAdBannerView(bannerEntity, obj);
            }
        });
    }

    public /* synthetic */ void lambda$initVerticalAdImage$4$LiveAdBannerView(BannerEntity bannerEntity, Object obj) {
        onAdBannerClick(bannerEntity);
    }

    private List<View> getBannerView(List<BannerEntity> list) {
        ArrayList arrayList = new ArrayList();
        for (BannerEntity bannerEntity : list) {
            if (bannerEntity.isWebH5View()) {
                arrayList.add(new BannerWebView(getContext()));
            } else {
                ImageView imageView = new ImageView(getContext());
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                arrayList.add(imageView);
            }
        }
        return arrayList;
    }

    private void onAdBannerClick(BannerEntity bannerEntity) {
        AppUtils.onAdvChannelHitsListener(this.mContext, bannerEntity.f5830id, "2");
        OnLiveAdBannerClickListener onLiveAdBannerClickListener = this.onAdBannerClickListener;
        if (onLiveAdBannerClickListener != null) {
            onLiveAdBannerClickListener.onAdBannerClick(bannerEntity);
        }
    }

    private void loadImg(String str, ImageView imageView) {
        GlideUtils.loadAdBannerImageForRoundView(this.mContext, imageView, str, R$drawable.fq_shape_default_banner_cover_bg);
    }

    public void setOnAdBannerClickListener(OnLiveAdBannerClickListener onLiveAdBannerClickListener) {
        this.onAdBannerClickListener = onLiveAdBannerClickListener;
    }

    private boolean isAllowClose(List<BannerEntity> list) {
        boolean z = true;
        for (BannerEntity bannerEntity : list) {
            if (bannerEntity.isAllowClose()) {
                z = false;
            }
        }
        return z;
    }

    private String getWebH5Url(String str, String str2, String str3) {
        return str + "?" + LogConstants.APP_ID + SimpleComparison.EQUAL_TO_OPERATION + str2 + "&" + LogConstants.OPEN_ID + SimpleComparison.EQUAL_TO_OPERATION + str3;
    }

    public void releaseWebView() {
        BGABanner bGABanner = this.bannerView;
        if (bGABanner != null) {
            bGABanner.onDestroyWebView();
        }
        BannerWebView bannerWebView = this.wvVerticalAd;
        if (bannerWebView != null) {
            bannerWebView.onDestroyWebView();
        }
    }
}
