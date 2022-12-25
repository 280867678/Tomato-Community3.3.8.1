package com.one.tomato.adapter;

import android.support.p002v4.content.ContextCompat;
import android.support.p005v7.widget.CardView;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.entity.ImageBean;
import com.one.tomato.entity.p079db.SubGamesBean;
import com.one.tomato.utils.DisplayMetricsUtils;
import com.one.tomato.utils.image.ImageLoaderUtil;

/* compiled from: GameDialogAdapter.kt */
/* loaded from: classes3.dex */
public final class GameDialogAdapter extends BaseQuickAdapter<SubGamesBean, BaseViewHolder> {
    private int gameType = 1;

    public GameDialogAdapter() {
        super((int) R.layout.game_dialog_item);
    }

    public final void setGameType(int i) {
        this.gameType = i;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, SubGamesBean subGamesBean) {
        String str;
        ImageView imageView = baseViewHolder != null ? (ImageView) baseViewHolder.getView(R.id.image_game) : null;
        TextView textView = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_game) : null;
        RelativeLayout relativeLayout = baseViewHolder != null ? (RelativeLayout) baseViewHolder.getView(R.id.card_image_game) : null;
        CardView cardView = baseViewHolder != null ? (CardView) baseViewHolder.getView(R.id.card_image) : null;
        ImageLoaderUtil.loadRecyclerThumbImage(this.mContext, imageView, new ImageBean(subGamesBean != null ? subGamesBean.getAdArticleAvatarSec() : null));
        if (textView != null) {
            if (subGamesBean == null || (str = subGamesBean.getAdName()) == null) {
                str = "";
            }
            textView.setText(str);
        }
        if (this.gameType == 1) {
            if (textView != null) {
                textView.setVisibility(0);
            }
            ViewGroup.LayoutParams layoutParams = cardView != null ? cardView.getLayoutParams() : null;
            if (layoutParams != null) {
                layoutParams.width = (int) DisplayMetricsUtils.dp2px(50.0f);
            }
            if (layoutParams != null) {
                layoutParams.height = (int) DisplayMetricsUtils.dp2px(50.0f);
            }
            if (cardView != null) {
                cardView.setLayoutParams(layoutParams);
            }
            ViewGroup.LayoutParams layoutParams2 = textView != null ? textView.getLayoutParams() : null;
            if (layoutParams2 != null) {
                layoutParams2.width = (int) DisplayMetricsUtils.dp2px(50.0f);
            }
            if (textView != null) {
                textView.setLayoutParams(layoutParams2);
            }
        } else {
            if (textView != null) {
                textView.setVisibility(8);
            }
            ViewGroup.LayoutParams layoutParams3 = cardView != null ? cardView.getLayoutParams() : null;
            if (layoutParams3 != null) {
                layoutParams3.width = (int) DisplayMetricsUtils.dp2px(40.0f);
            }
            if (layoutParams3 != null) {
                layoutParams3.height = (int) DisplayMetricsUtils.dp2px(40.0f);
            }
            if (cardView != null) {
                cardView.setLayoutParams(layoutParams3);
            }
        }
        if (subGamesBean == null || !subGamesBean.isSelector()) {
            if (relativeLayout == null) {
                return;
            }
            relativeLayout.setBackground(null);
        } else if (relativeLayout == null) {
        } else {
            relativeLayout.setBackground(ContextCompat.getDrawable(this.mContext, R.drawable.common_shape_stroke_corner6_fc4c7b));
        }
    }
}
