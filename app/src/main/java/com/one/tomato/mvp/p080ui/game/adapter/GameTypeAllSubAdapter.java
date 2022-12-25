package com.one.tomato.mvp.p080ui.game.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.p005v7.widget.CardView;
import android.support.p005v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.entity.ImageBean;
import com.one.tomato.entity.p079db.SubGamesBean;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.one.tomato.thirdpart.roundimage.RoundedImageView;
import com.one.tomato.utils.image.ImageLoaderUtil;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: GameTypeAllSubAdapter.kt */
/* renamed from: com.one.tomato.mvp.ui.game.adapter.GameTypeAllSubAdapter */
/* loaded from: classes3.dex */
public final class GameTypeAllSubAdapter extends BaseRecyclerViewAdapter<SubGamesBean> {
    private final void loadHeadImg(RoundedImageView roundedImageView, SubGamesBean subGamesBean) {
    }

    public GameTypeAllSubAdapter(Context context, RecyclerView recyclerView) {
        super(context, R.layout.game_type_sub_item, recyclerView);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, SubGamesBean subGamesBean) {
        super.convert(baseViewHolder, (BaseViewHolder) subGamesBean);
        TextView textView = null;
        ConstraintLayout constraintLayout = null;
        TextView textView2 = null;
        String adLogoType = subGamesBean != null ? subGamesBean.getAdLogoType() : null;
        if (adLogoType == null) {
            return;
        }
        int hashCode = adLogoType.hashCode();
        if (hashCode == 49) {
            if (!adLogoType.equals("1")) {
                return;
            }
            RelativeLayout relativeLayout = baseViewHolder != null ? (RelativeLayout) baseViewHolder.getView(R.id.home_cl_root) : null;
            ImageView imageView = baseViewHolder != null ? (ImageView) baseViewHolder.getView(R.id.home_iv_cover) : null;
            if (baseViewHolder != null) {
                textView = (TextView) baseViewHolder.getView(R.id.home_tv_title);
            }
            if (baseViewHolder != null) {
                TextView textView3 = (TextView) baseViewHolder.getView(R.id.home_tv_new);
            }
            if (relativeLayout != null) {
                relativeLayout.setVisibility(0);
            }
            loadTitleStr(textView, subGamesBean);
            loadCoverImg(imageView, subGamesBean);
        } else if (hashCode != 50) {
            if (hashCode != 114581 || !adLogoType.equals("tab")) {
                return;
            }
            if (baseViewHolder != null) {
                constraintLayout = (ConstraintLayout) baseViewHolder.getView(R.id.tab_cl_root);
            }
            if (baseViewHolder != null) {
                RoundedImageView roundedImageView = (RoundedImageView) baseViewHolder.getView(R.id.tab_iv_cover);
            }
            if (baseViewHolder != null) {
                TextView textView4 = (TextView) baseViewHolder.getView(R.id.tab_tv_title);
            }
            if (baseViewHolder != null) {
                TextView textView5 = (TextView) baseViewHolder.getView(R.id.tab_tv_desc);
            }
            if (baseViewHolder != null) {
                TextView textView6 = (TextView) baseViewHolder.getView(R.id.tab_tv_new);
            }
            if (constraintLayout == null) {
                return;
            }
            constraintLayout.setVisibility(0);
        } else if (!adLogoType.equals("2")) {
        } else {
            CardView cardView = baseViewHolder != null ? (CardView) baseViewHolder.getView(R.id.center_cl_root) : null;
            ImageView imageView2 = baseViewHolder != null ? (ImageView) baseViewHolder.getView(R.id.center_iv_cover) : null;
            RoundedImageView roundedImageView2 = baseViewHolder != null ? (RoundedImageView) baseViewHolder.getView(R.id.center_iv_head) : null;
            TextView textView7 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.center_tv_title) : null;
            if (baseViewHolder != null) {
                textView2 = (TextView) baseViewHolder.getView(R.id.center_tv_desc);
            }
            if (baseViewHolder != null) {
                TextView textView8 = (TextView) baseViewHolder.getView(R.id.center_tv_new);
            }
            if (cardView != null) {
                cardView.setVisibility(0);
            }
            loadCoverImg(imageView2, subGamesBean);
            loadHeadImg(roundedImageView2, subGamesBean);
            loadTitleStr(textView7, subGamesBean);
            loadDescStr(textView2, subGamesBean);
        }
    }

    private final void loadCoverImg(ImageView imageView, SubGamesBean subGamesBean) {
        Context context = this.mContext;
        String str = null;
        if (context == null) {
            Intrinsics.throwNpe();
            throw null;
        }
        if (subGamesBean != null) {
            str = subGamesBean.getAdArticleAvatarSec();
        }
        ImageLoaderUtil.loadRecyclerThumbImageNoCrop(context, imageView, new ImageBean(str));
    }

    private final void loadTitleStr(TextView textView, SubGamesBean subGamesBean) {
        if (textView != null) {
            textView.setText(subGamesBean != null ? subGamesBean.getAdName() : null);
        }
    }

    private final void loadDescStr(TextView textView, SubGamesBean subGamesBean) {
        if (textView != null) {
            textView.setText(subGamesBean != null ? subGamesBean.getAdDesc() : null);
        }
    }
}
