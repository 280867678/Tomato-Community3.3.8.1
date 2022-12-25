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
import com.one.tomato.mvp.p080ui.chess.view.ChessGameActivity;
import com.one.tomato.thirdpart.recyclerview.GridItemDecoration;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.GameUtils;
import com.one.tomato.utils.ToastUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Functions;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: GameTypeScrollAdapter.kt */
/* renamed from: com.one.tomato.mvp.ui.game.adapter.GameTypeScrollAdapter */
/* loaded from: classes3.dex */
public final class GameTypeScrollAdapter extends BaseQuickAdapter<GameTypeData, BaseViewHolder> {
    private Function1<? super SubGamesBean, Unit> callBackItem;
    private Functions<Unit> callDeleteItem;

    public GameTypeScrollAdapter() {
        super((int) R.layout.game_type_all_item);
    }

    public final void addCallBackItem(Function1<? super SubGamesBean, Unit> function1) {
        this.callBackItem = function1;
    }

    public final void addCallDeleteBackItem(Functions<Unit> functions) {
        this.callDeleteItem = functions;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, final GameTypeData gameTypeData) {
        String str;
        ArrayList<SubGamesBean> arrayList = null;
        RecyclerView recyclerView = baseViewHolder != null ? (RecyclerView) baseViewHolder.getView(R.id.recycler_view) : null;
        TextView textView = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_title) : null;
        ImageView imageView = baseViewHolder != null ? (ImageView) baseViewHolder.getView(R.id.image_delete) : null;
        if (textView != null) {
            if (gameTypeData == null || (str = gameTypeData.getAdName()) == null) {
                str = "";
            }
            textView.setText(str);
        }
        if (gameTypeData == null || gameTypeData.getGameId() != -10) {
            if (imageView != null) {
                imageView.setVisibility(8);
            }
            if (textView != null) {
                textView.setVisibility(0);
            }
        } else {
            if (imageView != null) {
                imageView.setVisibility(0);
            }
            if (textView != null) {
                textView.setVisibility(8);
            }
        }
        GridItemDecoration.Builder builder = new GridItemDecoration.Builder(this.mContext);
        builder.setColorResource(R.color.transparent);
        builder.setVerticalSpan(R.dimen.dimen_16);
        GridItemDecoration build = builder.build();
        if ((recyclerView != null ? recyclerView.getItemDecorationCount() : 0) <= 0 && recyclerView != null) {
            recyclerView.addItemDecoration(build);
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
        gameTypeAllSubAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.one.tomato.mvp.ui.game.adapter.GameTypeScrollAdapter$convert$1
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter<Object, BaseViewHolder> adapter, View view, int i) {
                Context mContext;
                Context context;
                Function1 function1;
                Integer valueOf = view != null ? Integer.valueOf(view.getId()) : null;
                if (valueOf == null) {
                    Intrinsics.throwNpe();
                    throw null;
                } else if (AppUtil.isFastClick(valueOf.intValue(), 3000)) {
                    ToastUtil.showCenterToast((int) R.string.post_fast_click_tip);
                } else {
                    Intrinsics.checkExpressionValueIsNotNull(adapter, "adapter");
                    Object obj = adapter.getData().get(i);
                    if (obj == null) {
                        throw new TypeCastException("null cannot be cast to non-null type com.one.tomato.entity.db.SubGamesBean");
                    }
                    SubGamesBean subGamesBean = (SubGamesBean) obj;
                    String adType = subGamesBean.getAdType();
                    if (adType != null) {
                        int hashCode = adType.hashCode();
                        if (hashCode != 55) {
                            if (hashCode == 57 && adType.equals("9")) {
                                function1 = GameTypeScrollAdapter.this.callBackItem;
                                if (function1 == null) {
                                    return;
                                }
                                Unit unit = (Unit) function1.mo6794invoke(subGamesBean);
                                return;
                            }
                        } else if (adType.equals("7")) {
                            ArrayList<SubGamesBean> arrayList2 = new ArrayList<>();
                            List<Object> data = adapter.getData();
                            if (data == null) {
                                throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.ArrayList<com.one.tomato.entity.db.SubGamesBean> /* = java.util.ArrayList<com.one.tomato.entity.db.SubGamesBean> */");
                            }
                            Iterator it2 = ((ArrayList) data).iterator();
                            while (it2.hasNext()) {
                                SubGamesBean its = (SubGamesBean) it2.next();
                                Intrinsics.checkExpressionValueIsNotNull(its, "its");
                                its.setSelector(false);
                                if (Intrinsics.areEqual(its.getAdType(), "7")) {
                                    if (Intrinsics.areEqual(its.getAdGameId(), subGamesBean.getAdGameId())) {
                                        its.setSelector(true);
                                    }
                                    arrayList2.add(its);
                                }
                            }
                            ChessGameActivity.Companion companion = ChessGameActivity.Companion;
                            context = ((BaseQuickAdapter) GameTypeScrollAdapter.this).mContext;
                            if (context != null) {
                                companion.startAct(context, arrayList2);
                                return;
                            } else {
                                Intrinsics.throwNpe();
                                throw null;
                            }
                        }
                    }
                    GameUtils gameUtils = GameUtils.INSTANCE;
                    mContext = ((BaseQuickAdapter) GameTypeScrollAdapter.this).mContext;
                    Intrinsics.checkExpressionValueIsNotNull(mContext, "mContext");
                    gameUtils.clickGame(mContext, subGamesBean);
                }
            }
        });
        if (imageView != null) {
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.game.adapter.GameTypeScrollAdapter$convert$2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    Functions functions;
                    GameTypeScrollAdapter gameTypeScrollAdapter = GameTypeScrollAdapter.this;
                    gameTypeScrollAdapter.notifyItemRemoved(gameTypeScrollAdapter.getData().indexOf(gameTypeData));
                    functions = GameTypeScrollAdapter.this.callDeleteItem;
                    if (functions != null) {
                        Unit unit = (Unit) functions.mo6822invoke();
                    }
                }
            });
        }
    }
}
