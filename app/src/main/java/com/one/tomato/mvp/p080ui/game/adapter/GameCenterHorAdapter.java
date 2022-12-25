package com.one.tomato.mvp.p080ui.game.adapter;

import android.support.p002v4.content.ContextCompat;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.entity.ImageBean;
import com.one.tomato.entity.p079db.SubGamesBean;
import com.one.tomato.thirdpart.roundimage.RoundedImageView;
import com.one.tomato.utils.image.ImageLoaderUtil;

/* compiled from: GameCenterHorAdapter.kt */
/* renamed from: com.one.tomato.mvp.ui.game.adapter.GameCenterHorAdapter */
/* loaded from: classes3.dex */
public final class GameCenterHorAdapter extends BaseQuickAdapter<SubGamesBean, BaseViewHolder> {
    public GameCenterHorAdapter() {
        super((int) R.layout.game_center_hor_item);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, SubGamesBean subGamesBean) {
        String str;
        RoundedImageView roundedImageView = baseViewHolder != null ? (RoundedImageView) baseViewHolder.getView(R.id.image_game) : null;
        TextView textView = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_game) : null;
        RelativeLayout relativeLayout = baseViewHolder != null ? (RelativeLayout) baseViewHolder.getView(R.id.relate_background) : null;
        TextView textView2 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_brand_name) : null;
        ImageLoaderUtil.loadRecyclerThumbImage(this.mContext, roundedImageView, new ImageBean(subGamesBean != null ? subGamesBean.getAdArticleAvatarSec() : null));
        if (textView != null) {
            if (subGamesBean == null || (str = subGamesBean.getAdName()) == null) {
                str = "";
            }
            textView.setText(str);
        }
        if (textView != null) {
            textView.setVisibility(0);
        }
        if (subGamesBean == null || !subGamesBean.isSelector()) {
            if (relativeLayout != null) {
                relativeLayout.setBackground(null);
            }
            if (textView2 == null) {
                return;
            }
            textView2.setVisibility(8);
            return;
        }
        if (relativeLayout != null) {
            relativeLayout.setBackground(ContextCompat.getDrawable(this.mContext, R.drawable.common_shape_stroke_corner_ovl_fc4c7b));
        }
        if (textView2 != null) {
            textView2.setText(subGamesBean.getAdBrandName());
        }
        if (textView2 == null) {
            return;
        }
        textView2.setVisibility(0);
    }
}
