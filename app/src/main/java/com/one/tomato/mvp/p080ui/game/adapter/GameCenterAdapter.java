package com.one.tomato.mvp.p080ui.game.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.p005v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.entity.ImageBean;
import com.one.tomato.entity.p079db.AdPage;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.one.tomato.thirdpart.roundimage.RoundedImageView;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.ToastUtil;
import com.one.tomato.utils.image.ImageLoaderUtil;
import com.one.tomato.utils.p087ad.AdUtil;
import java.util.ArrayList;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import org.litepal.LitePal;

/* compiled from: GameCenterAdapter.kt */
/* renamed from: com.one.tomato.mvp.ui.game.adapter.GameCenterAdapter */
/* loaded from: classes3.dex */
public final class GameCenterAdapter extends BaseRecyclerViewAdapter<AdPage> {
    private String businessType;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GameCenterAdapter(String type, Context context, RecyclerView recyclerView) {
        super(context, R.layout.item_game, recyclerView);
        Intrinsics.checkParameterIsNotNull(type, "type");
        this.businessType = type;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, AdPage adPage) {
        super.convert(baseViewHolder, (BaseViewHolder) adPage);
        String str = this.businessType;
        int hashCode = str.hashCode();
        if (hashCode == -1364013995) {
            if (!str.equals("center")) {
                return;
            }
            ConstraintLayout constraintLayout = baseViewHolder != null ? (ConstraintLayout) baseViewHolder.getView(R.id.center_cl_root) : null;
            RoundedImageView roundedImageView = baseViewHolder != null ? (RoundedImageView) baseViewHolder.getView(R.id.center_iv_cover) : null;
            RoundedImageView roundedImageView2 = baseViewHolder != null ? (RoundedImageView) baseViewHolder.getView(R.id.center_iv_head) : null;
            TextView textView = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.center_tv_title) : null;
            TextView textView2 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.center_tv_desc) : null;
            TextView textView3 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.center_tv_new) : null;
            if (constraintLayout != null) {
                constraintLayout.setVisibility(0);
            }
            loadCoverImg(roundedImageView, adPage);
            loadHeadImg(roundedImageView2, adPage);
            loadTitleStr(textView, adPage);
            loadDescStr(textView2, adPage);
            Boolean valueOf = adPage != null ? Boolean.valueOf(adPage.isWebAppNew()) : null;
            if (valueOf != null) {
                updateNewWebapp(textView3, valueOf.booleanValue());
            } else {
                Intrinsics.throwNpe();
                throw null;
            }
        } else if (hashCode == 114581) {
            if (!str.equals("tab")) {
                return;
            }
            ConstraintLayout constraintLayout2 = baseViewHolder != null ? (ConstraintLayout) baseViewHolder.getView(R.id.tab_cl_root) : null;
            RoundedImageView roundedImageView3 = baseViewHolder != null ? (RoundedImageView) baseViewHolder.getView(R.id.tab_iv_cover) : null;
            TextView textView4 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.tab_tv_title) : null;
            TextView textView5 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.tab_tv_desc) : null;
            TextView textView6 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.tab_tv_new) : null;
            if (constraintLayout2 != null) {
                constraintLayout2.setVisibility(0);
            }
            loadCoverImg(roundedImageView3, adPage);
            loadTitleStr(textView4, adPage);
            loadDescStr(textView5, adPage);
            Boolean valueOf2 = adPage != null ? Boolean.valueOf(adPage.isWebAppNew()) : null;
            if (valueOf2 != null) {
                updateNewWebapp(textView6, valueOf2.booleanValue());
            } else {
                Intrinsics.throwNpe();
                throw null;
            }
        } else if (hashCode != 3208415 || !str.equals("home")) {
        } else {
            ConstraintLayout constraintLayout3 = baseViewHolder != null ? (ConstraintLayout) baseViewHolder.getView(R.id.home_cl_root) : null;
            RoundedImageView roundedImageView4 = baseViewHolder != null ? (RoundedImageView) baseViewHolder.getView(R.id.home_iv_cover) : null;
            TextView textView7 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.home_tv_title) : null;
            TextView textView8 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.home_tv_desc) : null;
            TextView textView9 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.home_tv_new) : null;
            if (constraintLayout3 != null) {
                constraintLayout3.setVisibility(0);
            }
            loadCoverImg(roundedImageView4, adPage);
            loadTitleStr(textView7, adPage);
            loadDescStr(textView8, adPage);
            Boolean valueOf3 = adPage != null ? Boolean.valueOf(adPage.isWebAppNew()) : null;
            if (valueOf3 != null) {
                updateNewWebapp(textView9, valueOf3.booleanValue());
            } else {
                Intrinsics.throwNpe();
                throw null;
            }
        }
    }

    private final void loadCoverImg(RoundedImageView roundedImageView, AdPage adPage) {
        Context context = this.mContext;
        String str = null;
        if (context == null) {
            Intrinsics.throwNpe();
            throw null;
        }
        if (adPage != null) {
            str = adPage.getImageUrlSec();
        }
        ImageLoaderUtil.loadRecyclerThumbImage(context, roundedImageView, new ImageBean(str));
    }

    private final void loadHeadImg(RoundedImageView roundedImageView, AdPage adPage) {
        Context context = this.mContext;
        String str = null;
        if (context == null) {
            Intrinsics.throwNpe();
            throw null;
        }
        if (adPage != null) {
            str = adPage.getAdArticleAvatarSec();
        }
        ImageLoaderUtil.loadRecyclerThumbImage(context, roundedImageView, new ImageBean(str));
    }

    private final void loadTitleStr(TextView textView, AdPage adPage) {
        if (textView != null) {
            textView.setText(adPage != null ? adPage.getAdArticleName() : null);
        }
    }

    private final void loadDescStr(TextView textView, AdPage adPage) {
        if (textView != null) {
            textView.setText(adPage != null ? adPage.getAdArticleContent() : null);
        }
    }

    private final void updateNewWebapp(TextView textView, boolean z) {
        if (textView != null) {
            textView.setVisibility(z ? 0 : 8);
        }
    }

    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
    public void onRecyclerItemClick(BaseQuickAdapter<?, ?> baseQuickAdapter, View view, int i) {
        super.onRecyclerItemClick(baseQuickAdapter, view, i);
        Integer valueOf = view != null ? Integer.valueOf(view.getId()) : null;
        if (valueOf == null) {
            Intrinsics.throwNpe();
            throw null;
        } else if (AppUtil.isFastClick(valueOf.intValue(), 3000)) {
            ToastUtil.showCenterToast((int) R.string.post_fast_click_tip);
        } else {
            AdPage adPage = (AdPage) this.mData.get(i);
            Context context = this.mContext;
            if (context != null) {
                AdUtil adUtil = new AdUtil((Activity) context);
                Intrinsics.checkExpressionValueIsNotNull(adPage, "adPage");
                adUtil.clickAd(adPage.getAdId(), adPage.getAdType(), adPage.getAdJumpModule(), adPage.getAdJumpDetail(), adPage.getOpenType(), adPage.getAdLink());
                if (!adPage.isWebAppNew()) {
                    return;
                }
                adPage.setWebAppNew(false);
                refreshNotifyItemChanged(i);
                ArrayList arrayList = new ArrayList();
                arrayList.addAll(this.mData);
                LitePal.saveAll(arrayList);
                return;
            }
            throw new TypeCastException("null cannot be cast to non-null type android.app.Activity");
        }
    }
}
