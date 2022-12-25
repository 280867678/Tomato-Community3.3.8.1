package com.one.tomato.mvp.p080ui.chess.adapter;

import android.content.Context;
import android.support.p005v7.widget.RecyclerView;
import android.widget.RelativeLayout;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.entity.p079db.ChessTypeBean;
import com.one.tomato.mvp.p080ui.chess.item.ChessHomeGameItem;
import com.one.tomato.mvp.p080ui.chess.item.ChessHomeLiveItem;
import com.one.tomato.mvp.p080ui.chess.item.ChessHomePaPaItem;
import com.one.tomato.mvp.p080ui.chess.item.ChessHomePostItem;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ChessHomeTypeSubAdapter.kt */
/* renamed from: com.one.tomato.mvp.ui.chess.adapter.ChessHomeTypeSubAdapter */
/* loaded from: classes3.dex */
public final class ChessHomeTypeSubAdapter extends BaseRecyclerViewAdapter<ChessTypeBean> {
    public ChessHomeTypeSubAdapter(Context context, RecyclerView recyclerView) {
        super(context, R.layout.chess_home_sub_type_item, recyclerView);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, ChessTypeBean chessTypeBean) {
        super.convert(baseViewHolder, (BaseViewHolder) chessTypeBean);
        RelativeLayout relativeLayout = baseViewHolder != null ? (RelativeLayout) baseViewHolder.getView(R.id.relate_content) : null;
        Integer valueOf = chessTypeBean != null ? Integer.valueOf(chessTypeBean.getChessId()) : null;
        if (valueOf != null && valueOf.intValue() == 1) {
            if (relativeLayout != null) {
                relativeLayout.removeAllViews();
            }
            Context mContext = this.mContext;
            Intrinsics.checkExpressionValueIsNotNull(mContext, "mContext");
            ChessHomeLiveItem chessHomeLiveItem = new ChessHomeLiveItem(mContext);
            chessHomeLiveItem.addData();
            if (relativeLayout == null) {
                return;
            }
            relativeLayout.addView(chessHomeLiveItem);
        } else if (valueOf != null && valueOf.intValue() == 2) {
            if (relativeLayout != null) {
                relativeLayout.removeAllViews();
            }
            Context mContext2 = this.mContext;
            Intrinsics.checkExpressionValueIsNotNull(mContext2, "mContext");
            ChessHomePostItem chessHomePostItem = new ChessHomePostItem(mContext2);
            chessHomePostItem.addData(chessTypeBean);
            if (relativeLayout == null) {
                return;
            }
            relativeLayout.addView(chessHomePostItem);
        } else if (valueOf != null && valueOf.intValue() == 3) {
            if (relativeLayout != null) {
                relativeLayout.removeAllViews();
            }
            Context mContext3 = this.mContext;
            Intrinsics.checkExpressionValueIsNotNull(mContext3, "mContext");
            ChessHomePaPaItem chessHomePaPaItem = new ChessHomePaPaItem(mContext3);
            chessHomePaPaItem.addData(chessTypeBean);
            if (relativeLayout == null) {
                return;
            }
            relativeLayout.addView(chessHomePaPaItem);
        } else {
            if (relativeLayout != null) {
                relativeLayout.removeAllViews();
            }
            Context mContext4 = this.mContext;
            Intrinsics.checkExpressionValueIsNotNull(mContext4, "mContext");
            ChessHomeGameItem chessHomeGameItem = new ChessHomeGameItem(mContext4);
            if (chessTypeBean != null) {
                chessHomeGameItem.addData(chessTypeBean);
                if (relativeLayout == null) {
                    return;
                }
                relativeLayout.addView(chessHomeGameItem);
                return;
            }
            Intrinsics.throwNpe();
            throw null;
        }
    }
}
