package com.one.tomato.mvp.p080ui.chess.adapter;

import android.content.Context;
import android.support.p002v4.content.ContextCompat;
import android.support.p005v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.entity.ImageBean;
import com.one.tomato.entity.p079db.ChessTypeBean;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.one.tomato.utils.PreferencesUtil;
import com.one.tomato.utils.image.ImageLoaderUtil;

/* compiled from: ChessHomeTypeAdapter.kt */
/* renamed from: com.one.tomato.mvp.ui.chess.adapter.ChessHomeTypeAdapter */
/* loaded from: classes3.dex */
public final class ChessHomeTypeAdapter extends BaseRecyclerViewAdapter<ChessTypeBean> {
    public ChessHomeTypeAdapter(Context context, RecyclerView recyclerView) {
        super(context, R.layout.chess_home_type_item, recyclerView);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, ChessTypeBean chessTypeBean) {
        String str;
        String str2;
        String str3;
        super.convert(baseViewHolder, (BaseViewHolder) chessTypeBean);
        String str4 = null;
        RelativeLayout relativeLayout = baseViewHolder != null ? (RelativeLayout) baseViewHolder.getView(R.id.relate_background) : null;
        ImageView imageView = baseViewHolder != null ? (ImageView) baseViewHolder.getView(R.id.image) : null;
        TextView textView = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_name) : null;
        String string = PreferencesUtil.getInstance().getString("language_country");
        if (!TextUtils.isEmpty(string) && string != null) {
            int hashCode = string.hashCode();
            if (hashCode != 2155) {
                if (hashCode != 2691) {
                    if (hashCode == 2718 && string.equals("US") && textView != null) {
                        if (chessTypeBean == null || (str3 = chessTypeBean.getTitleEn()) == null) {
                            str3 = "";
                        }
                        textView.setText(str3);
                    }
                } else if (string.equals("TW") && textView != null) {
                    if (chessTypeBean == null || (str2 = chessTypeBean.getTitleTw()) == null) {
                        str2 = "";
                    }
                    textView.setText(str2);
                }
            } else if (string.equals("CN") && textView != null) {
                if (chessTypeBean == null || (str = chessTypeBean.getTitle()) == null) {
                    str = "";
                }
                textView.setText(str);
            }
        }
        if (imageView != null) {
            imageView.setImageBitmap(null);
        }
        Context context = this.mContext;
        if (chessTypeBean != null) {
            str4 = chessTypeBean.getIcon();
        }
        ImageLoaderUtil.loadRecyclerThumbImage(context, imageView, new ImageBean(str4));
        if (chessTypeBean == null || !chessTypeBean.isSelect()) {
            if (relativeLayout != null) {
                relativeLayout.setBackground(ContextCompat.getDrawable(this.mContext, R.drawable.chess_home_type_no_select_bg));
            }
            if (textView == null) {
                return;
            }
            textView.setTextColor(ContextCompat.getColor(this.mContext, R.color.chess_main_tab_text));
            return;
        }
        if (relativeLayout != null) {
            relativeLayout.setBackground(ContextCompat.getDrawable(this.mContext, R.drawable.chess_home_type_yes_select_bg));
        }
        if (textView == null) {
            return;
        }
        textView.setTextColor(ContextCompat.getColor(this.mContext, R.color.white));
    }
}
