package com.tomatolive.library.p136ui.view.headview;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.model.BannerEntity;
import com.tomatolive.library.model.IndexRankEntity;
import com.tomatolive.library.p136ui.adapter.MyRankBannerAdapter;
import com.tomatolive.library.p136ui.view.widget.bgabanner.BGABanner;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.GlideUtils;
import com.tomatolive.library.utils.LogManager;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/* renamed from: com.tomatolive.library.ui.view.headview.HomeHotHeadView */
/* loaded from: classes3.dex */
public class HomeHotHeadView extends LinearLayout {
    private List<BannerEntity> bannerList;
    private BGABanner bannerTop;
    private BGABanner bannerView;
    private ImageView ivDefaultCover;
    private Context mContext;

    public HomeHotHeadView(Context context) {
        this(context, null);
    }

    public HomeHotHeadView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public HomeHotHeadView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initView(context);
    }

    private void initView(Context context) {
        LinearLayout.inflate(getContext(), R$layout.fq_layout_head_view_home_hot, this);
        this.mContext = context;
        this.bannerView = (BGABanner) findViewById(R$id.banner);
        this.bannerTop = (BGABanner) findViewById(R$id.banner_top);
        this.ivDefaultCover = (ImageView) findViewById(R$id.iv_default_cover);
    }

    public void initBannerImages(List<BannerEntity> list) {
        boolean z = false;
        if (list == null || list.isEmpty()) {
            this.bannerView.setVisibility(4);
            this.ivDefaultCover.setVisibility(0);
            GlideUtils.loadRoundCornersImage(this.mContext, this.ivDefaultCover, R$drawable.fq_shape_default_banner_cover_bg, 6, RoundedCornersTransformation.CornerType.ALL);
            return;
        }
        this.bannerView.setVisibility(0);
        this.ivDefaultCover.setVisibility(4);
        this.bannerList = AppUtils.getImgBannerItem(list);
        List<BannerEntity> list2 = this.bannerList;
        if (list2 == null || list2.size() == 0) {
            return;
        }
        this.bannerView.setAdapter(new BGABanner.Adapter() { // from class: com.tomatolive.library.ui.view.headview.-$$Lambda$HomeHotHeadView$rPnuDmj3VSILNXokFH0KFNcOgl8
            @Override // com.tomatolive.library.p136ui.view.widget.bgabanner.BGABanner.Adapter
            public final void fillBannerItem(BGABanner bGABanner, View view, Object obj, int i) {
                HomeHotHeadView.this.lambda$initBannerImages$0$HomeHotHeadView(bGABanner, (ImageView) view, (BannerEntity) obj, i);
            }
        });
        BGABanner bGABanner = this.bannerView;
        if (this.bannerList.size() > 1) {
            z = true;
        }
        bGABanner.setAutoPlayAble(z);
        LogManager.m238i("广告个数：" + this.bannerList.size());
        this.bannerView.setData(this.bannerList, null);
        this.bannerView.setDelegate(new BGABanner.Delegate() { // from class: com.tomatolive.library.ui.view.headview.-$$Lambda$HomeHotHeadView$K-1jWfLpoD3Y2qUozM0ibgHooqQ
            @Override // com.tomatolive.library.p136ui.view.widget.bgabanner.BGABanner.Delegate
            public final void onBannerItemClick(BGABanner bGABanner2, View view, Object obj, int i) {
                HomeHotHeadView.this.lambda$initBannerImages$1$HomeHotHeadView(bGABanner2, (ImageView) view, (BannerEntity) obj, i);
            }
        });
    }

    public /* synthetic */ void lambda$initBannerImages$0$HomeHotHeadView(BGABanner bGABanner, ImageView imageView, BannerEntity bannerEntity, int i) {
        GlideUtils.loadAdBannerImageForRoundView(this.mContext, imageView, bannerEntity.img, R$drawable.fq_shape_default_banner_cover_bg);
    }

    public /* synthetic */ void lambda$initBannerImages$1$HomeHotHeadView(BGABanner bGABanner, ImageView imageView, BannerEntity bannerEntity, int i) {
        AppUtils.clickBannerEvent(this.mContext, bannerEntity);
    }

    public void initTopList(List<IndexRankEntity> list) {
        final ArrayList arrayList = new ArrayList();
        Observable.just(list).map(new Function() { // from class: com.tomatolive.library.ui.view.headview.-$$Lambda$HomeHotHeadView$CzMRYXoLdcc1Q7elnziXztNCOUA
            @Override // io.reactivex.functions.Function
            /* renamed from: apply */
            public final Object mo6755apply(Object obj) {
                return HomeHotHeadView.this.lambda$initTopList$2$HomeHotHeadView(arrayList, (List) obj);
            }
        }).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.tomatolive.library.ui.view.headview.-$$Lambda$HomeHotHeadView$yF7ZuIsk1yq2s5eT4j2zLBH5pHI
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                HomeHotHeadView.this.lambda$initTopList$3$HomeHotHeadView(arrayList, (List) obj);
            }
        });
    }

    public /* synthetic */ List lambda$initTopList$2$HomeHotHeadView(List list, List list2) throws Exception {
        int size = list2.size();
        for (int i = 0; i < size; i += 2) {
            list.add("index:" + i);
        }
        return dealAvatarList(list2);
    }

    public /* synthetic */ void lambda$initTopList$3$HomeHotHeadView(List list, List list2) throws Exception {
        BGABanner bGABanner = this.bannerTop;
        boolean z = true;
        if (list.size() <= 1) {
            z = false;
        }
        bGABanner.setAutoPlayAble(z);
        this.bannerTop.setData(R$layout.fq_layout_head_view_rank_enter, list, (List<String>) null);
        this.bannerTop.setAdapter(new MyRankBannerAdapter(list2, getContext()));
    }

    private List<IndexRankEntity> dealAvatarList(List<IndexRankEntity> list) {
        for (IndexRankEntity indexRankEntity : list) {
            if (TextUtils.equals(indexRankEntity.getType(), ConstantUtils.RANK_TYPE_WEEKSTAR)) {
                List<String> avatars = indexRankEntity.getAvatars();
                if (avatars.size() > 5) {
                    avatars = avatars.subList(0, 4);
                }
                Collections.reverse(avatars);
                indexRankEntity.setAvatars(avatars);
            } else {
                List<String> avatars2 = indexRankEntity.getAvatars();
                if (avatars2.size() > 3) {
                    avatars2 = avatars2.subList(0, 2);
                }
                Collections.reverse(avatars2);
                indexRankEntity.setAvatars(avatars2);
            }
        }
        return list;
    }
}
