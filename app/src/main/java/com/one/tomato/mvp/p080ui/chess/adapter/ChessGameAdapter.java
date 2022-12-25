package com.one.tomato.mvp.p080ui.chess.adapter;

import android.content.Context;
import android.support.p002v4.content.ContextCompat;
import android.support.p005v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.entity.ImageBean;
import com.one.tomato.entity.p079db.SubGamesBean;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.one.tomato.utils.image.ImageLoaderUtil;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ChessGameAdapter.kt */
/* renamed from: com.one.tomato.mvp.ui.chess.adapter.ChessGameAdapter */
/* loaded from: classes3.dex */
public final class ChessGameAdapter extends BaseRecyclerViewAdapter<SubGamesBean> {
    public ChessGameAdapter(Context context, RecyclerView recyclerView) {
        super(context, R.layout.chess_game_type_item, recyclerView);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, SubGamesBean subGamesBean) {
        super.convert(baseViewHolder, (BaseViewHolder) subGamesBean);
        RelativeLayout relativeLayout = baseViewHolder != null ? (RelativeLayout) baseViewHolder.getView(R.id.relate_image) : null;
        ImageView imageView = baseViewHolder != null ? (ImageView) baseViewHolder.getView(R.id.home_iv_cover) : null;
        TextView textView = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.home_tv_title) : null;
        TextView textView2 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_brand_name) : null;
        if (subGamesBean == null || !subGamesBean.isSelector()) {
            if (relativeLayout != null) {
                relativeLayout.setBackground(null);
            }
            if (textView != null) {
                textView.setTextColor(ContextCompat.getColor(this.mContext, R.color.chess_text_light));
            }
            if (textView2 != null) {
                textView2.setVisibility(8);
            }
        } else {
            if (relativeLayout != null) {
                relativeLayout.setBackground(ContextCompat.getDrawable(this.mContext, R.drawable.chess_stroke_game_item_bg));
            }
            if (textView != null) {
                textView.setTextColor(ContextCompat.getColor(this.mContext, R.color.chess_bright_bg));
            }
            if (textView2 != null) {
                String adBrandName = subGamesBean.getAdBrandName();
                if (adBrandName == null) {
                    adBrandName = "";
                }
                textView2.setText(adBrandName);
            }
            if (textView2 != null) {
                textView2.setVisibility(0);
            }
        }
        if (imageView != null) {
            imageView.setImageBitmap(null);
        }
        loadCoverImg(imageView, subGamesBean);
        loadTitleStr(textView, subGamesBean);
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
        ImageLoaderUtil.loadRecyclerThumbImage(context, imageView, new ImageBean(str));
    }

    private final void loadTitleStr(TextView textView, SubGamesBean subGamesBean) {
        if (textView != null) {
            textView.setText(subGamesBean != null ? subGamesBean.getAdName() : null);
        }
    }
}
