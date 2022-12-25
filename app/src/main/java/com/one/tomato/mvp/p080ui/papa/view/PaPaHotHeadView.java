package com.one.tomato.mvp.p080ui.papa.view;

import android.app.Activity;
import android.content.Context;
import android.support.p005v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.R$id;
import com.one.tomato.entity.C2516Ad;
import com.one.tomato.entity.PostList;
import com.one.tomato.entity.p079db.AdPage;
import com.one.tomato.mvp.p080ui.papa.adapter.PaPaHotHomeVideoAdapter;
import com.one.tomato.thirdpart.recyclerview.BetterHorScrollRecyclerView;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.p087ad.AdUtil;
import com.one.tomato.widget.mzbanner.MZBannerView;
import java.util.ArrayList;
import java.util.HashMap;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PaPaHotHeadView.kt */
/* renamed from: com.one.tomato.mvp.ui.papa.view.PaPaHotHeadView */
/* loaded from: classes3.dex */
public final class PaPaHotHeadView extends LinearLayout {
    private HashMap _$_findViewCache;
    private AdUtil adUtil;
    private MZBannerView<AdPage> banner;
    private PaPaHotHomeVideoAdapter paPaHotHomeVideoAdapter;

    public View _$_findCachedViewById(int i) {
        if (this._$_findViewCache == null) {
            this._$_findViewCache = new HashMap();
        }
        View view = (View) this._$_findViewCache.get(Integer.valueOf(i));
        if (view == null) {
            View findViewById = findViewById(i);
            this._$_findViewCache.put(Integer.valueOf(i), findViewById);
            return findViewById;
        }
        return view;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PaPaHotHeadView(final Context context) {
        super(context);
        Intrinsics.checkParameterIsNotNull(context, "context");
        LayoutInflater.from(context).inflate(R.layout.papa_hot_home_head_view, this);
        initBanner();
        BetterHorScrollRecyclerView hot_recycler = (BetterHorScrollRecyclerView) _$_findCachedViewById(R$id.hot_recycler);
        Intrinsics.checkExpressionValueIsNotNull(hot_recycler, "hot_recycler");
        this.paPaHotHomeVideoAdapter = new PaPaHotHomeVideoAdapter(context, hot_recycler);
        BetterHorScrollRecyclerView betterHorScrollRecyclerView = (BetterHorScrollRecyclerView) _$_findCachedViewById(R$id.hot_recycler);
        if (betterHorScrollRecyclerView != null) {
            betterHorScrollRecyclerView.setLayoutManager(new LinearLayoutManager(context, 0, false));
        }
        BetterHorScrollRecyclerView betterHorScrollRecyclerView2 = (BetterHorScrollRecyclerView) _$_findCachedViewById(R$id.hot_recycler);
        if (betterHorScrollRecyclerView2 != null) {
            betterHorScrollRecyclerView2.setAdapter(this.paPaHotHomeVideoAdapter);
        }
        PaPaHotHomeVideoAdapter paPaHotHomeVideoAdapter = this.paPaHotHomeVideoAdapter;
        if (paPaHotHomeVideoAdapter != null) {
            paPaHotHomeVideoAdapter.setEnableLoadMore(false);
        }
        TextView textView = (TextView) _$_findCachedViewById(R$id.compete_hot_text);
        if (textView != null) {
            textView.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.papa.view.PaPaHotHeadView.1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PaPaHotListHomeActivity.Companion.startAct(context);
                }
            });
        }
        RelativeLayout relativeLayout = (RelativeLayout) _$_findCachedViewById(R$id.most_play_text);
        if (relativeLayout != null) {
            relativeLayout.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.papa.view.PaPaHotHeadView.2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PaPaTumbeActivity.Companion.startAct(1, context);
                }
            });
        }
        RelativeLayout relativeLayout2 = (RelativeLayout) _$_findCachedViewById(R$id.most_zan_text);
        if (relativeLayout2 != null) {
            relativeLayout2.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.papa.view.PaPaHotHeadView.3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PaPaTumbeActivity.Companion.startAct(2, context);
                }
            });
        }
        RelativeLayout relativeLayout3 = (RelativeLayout) _$_findCachedViewById(R$id.most_collect_text);
        if (relativeLayout3 != null) {
            relativeLayout3.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.papa.view.PaPaHotHeadView.4
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PaPaTumbeActivity.Companion.startAct(3, context);
                }
            });
        }
    }

    public final void initBanner() {
        this.banner = (MZBannerView) findViewById(R.id.mzbanner);
        this.adUtil = new AdUtil((Activity) getContext());
        final ArrayList<AdPage> adPage = DBUtil.getAdPage(C2516Ad.TYPE_PAPA_BANNER);
        if (!(adPage instanceof ArrayList) || adPage.isEmpty()) {
            return;
        }
        MZBannerView<AdPage> mZBannerView = this.banner;
        if (mZBannerView != null) {
            mZBannerView.setVisibility(0);
        }
        MZBannerView<AdPage> mZBannerView2 = this.banner;
        if (mZBannerView2 != null) {
            mZBannerView2.setBannerPageClickListener(new MZBannerView.BannerPageClickListener() { // from class: com.one.tomato.mvp.ui.papa.view.PaPaHotHeadView$initBanner$1
                @Override // com.one.tomato.widget.mzbanner.MZBannerView.BannerPageClickListener
                public final void onPageClick(View view, int i) {
                    AdUtil adUtil;
                    Object obj = adPage.get(i);
                    Intrinsics.checkExpressionValueIsNotNull(obj, "adPage[position]");
                    AdPage adPage2 = (AdPage) obj;
                    adUtil = PaPaHotHeadView.this.adUtil;
                    if (adUtil != null) {
                        adUtil.clickAd(adPage2.getAdId(), adPage2.getAdType(), adPage2.getAdJumpModule(), adPage2.getAdJumpDetail(), adPage2.getOpenType(), adPage2.getAdLink());
                    }
                }
            });
        }
        MZBannerView<AdPage> mZBannerView3 = this.banner;
        if (mZBannerView3 == null) {
            return;
        }
        mZBannerView3.setPages(adPage, PaPaHotHeadView$initBanner$2.INSTANCE);
    }

    public final void setData(ArrayList<PostList> arrayList) {
        if (arrayList != null) {
            PaPaHotHomeVideoAdapter paPaHotHomeVideoAdapter = this.paPaHotHomeVideoAdapter;
            if (paPaHotHomeVideoAdapter != null) {
                paPaHotHomeVideoAdapter.setNewData(arrayList);
            }
            PaPaHotHomeVideoAdapter paPaHotHomeVideoAdapter2 = this.paPaHotHomeVideoAdapter;
            if (paPaHotHomeVideoAdapter2 == null) {
                return;
            }
            paPaHotHomeVideoAdapter2.setEnableLoadMore(false);
        }
    }
}
