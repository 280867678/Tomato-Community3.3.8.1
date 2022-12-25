package com.one.tomato.mvp.p080ui.game.adapter;

import android.content.Context;
import android.support.p005v7.widget.GridLayoutManager;
import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.entity.p079db.GameTypeData;
import com.one.tomato.entity.p079db.SubGamesBean;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.GameUtils;
import com.one.tomato.utils.ToastUtil;
import java.util.ArrayList;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: GameTypeAllAdapter.kt */
/* renamed from: com.one.tomato.mvp.ui.game.adapter.GameTypeAllAdapter */
/* loaded from: classes3.dex */
public final class GameTypeAllAdapter extends BaseRecyclerViewAdapter<GameTypeData> {
    private Functions<Unit> callDeleteItem;

    public final Functions<Unit> getCallDeleteItem() {
        return this.callDeleteItem;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, final GameTypeData gameTypeData) {
        String str;
        ArrayList<SubGamesBean> arrayList = null;
        RecyclerView recyclerView = baseViewHolder != null ? (RecyclerView) baseViewHolder.getView(R.id.recycler_view) : null;
        TextView textView = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_title) : null;
        ImageView imageView = baseViewHolder != null ? (ImageView) baseViewHolder.getView(R.id.image_delete) : null;
        if (gameTypeData == null || gameTypeData.getGameId() != -10) {
            if (imageView != null) {
                imageView.setVisibility(8);
            }
        } else if (imageView != null) {
            imageView.setVisibility(0);
        }
        if (textView != null) {
            if (gameTypeData == null || (str = gameTypeData.getAdName()) == null) {
                str = "";
            }
            textView.setText(str);
        }
        GameTypeAllSubAdapter gameTypeAllSubAdapter = new GameTypeAllSubAdapter(this.mContext, recyclerView);
        if (Intrinsics.areEqual(gameTypeData != null ? gameTypeData.getAdLogoType() : null, "1")) {
            if (recyclerView != null) {
                recyclerView.setLayoutManager(new GridLayoutManager(this.mContext, 3));
            }
        } else if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this.mContext, 1, false));
        }
        if (recyclerView != null) {
            recyclerView.setAdapter(gameTypeAllSubAdapter);
        }
        if (gameTypeData != null) {
            arrayList = gameTypeData.getSubGames();
        }
        gameTypeAllSubAdapter.setNewData(arrayList);
        gameTypeAllSubAdapter.setEnableLoadMore(false);
        gameTypeAllSubAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.one.tomato.mvp.ui.game.adapter.GameTypeAllAdapter$convert$1
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter<Object, BaseViewHolder> adapter, View view, int i) {
                Context mContext;
                Integer valueOf = view != null ? Integer.valueOf(view.getId()) : null;
                if (valueOf == null) {
                    Intrinsics.throwNpe();
                    throw null;
                } else if (AppUtil.isFastClick(valueOf.intValue(), 1000)) {
                    ToastUtil.showCenterToast((int) R.string.post_fast_click_tip);
                } else {
                    Intrinsics.checkExpressionValueIsNotNull(adapter, "adapter");
                    Object obj = adapter.getData().get(i);
                    if (obj == null) {
                        throw new TypeCastException("null cannot be cast to non-null type com.one.tomato.entity.db.SubGamesBean");
                    }
                    GameUtils gameUtils = GameUtils.INSTANCE;
                    mContext = ((BaseQuickAdapter) GameTypeAllAdapter.this).mContext;
                    Intrinsics.checkExpressionValueIsNotNull(mContext, "mContext");
                    gameUtils.clickGame(mContext, (SubGamesBean) obj);
                }
            }
        });
        if (imageView != null) {
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.game.adapter.GameTypeAllAdapter$convert$2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    GameTypeAllAdapter gameTypeAllAdapter = GameTypeAllAdapter.this;
                    gameTypeAllAdapter.notifyItemRemoved(gameTypeAllAdapter.getData().indexOf(gameTypeData));
                    Functions<Unit> callDeleteItem = GameTypeAllAdapter.this.getCallDeleteItem();
                    if (callDeleteItem != null) {
                        callDeleteItem.mo6822invoke();
                    }
                }
            });
        }
    }
}
