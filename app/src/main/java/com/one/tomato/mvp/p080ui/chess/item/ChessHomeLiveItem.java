package com.one.tomato.mvp.p080ui.chess.item;

import android.content.Context;
import android.support.p005v7.widget.GridLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.R$id;
import com.one.tomato.entity.LiveRecommendData;
import com.one.tomato.mvp.p080ui.chess.view.ChessLiveActivity;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.one.tomato.thirdpart.recyclerview.GridItemDecoration;
import com.one.tomato.thirdpart.tomatolive.TomatoLiveSDKUtils;
import java.util.HashMap;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ChessHomeLiveItem.kt */
/* renamed from: com.one.tomato.mvp.ui.chess.item.ChessHomeLiveItem */
/* loaded from: classes3.dex */
public final class ChessHomeLiveItem extends RelativeLayout {
    private HashMap _$_findViewCache;
    private BaseRecyclerViewAdapter<LiveRecommendData> adapter;

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
    public ChessHomeLiveItem(Context context) {
        super(context);
        Intrinsics.checkParameterIsNotNull(context, "context");
        LayoutInflater.from(context).inflate(R.layout.chess_home_live_item, this);
        initAdapter();
    }

    private final void initAdapter() {
        final Context context = getContext();
        final RecyclerView recyclerView = (RecyclerView) _$_findCachedViewById(R$id.recycler_anchor);
        this.adapter = new BaseRecyclerViewAdapter<LiveRecommendData>(this, context, R.layout.item_chess_home_live_item, recyclerView) { // from class: com.one.tomato.mvp.ui.chess.item.ChessHomeLiveItem$initAdapter$1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder baseViewHolder, LiveRecommendData liveRecommendData) {
                String str;
                String str2;
                String str3;
                super.convert(baseViewHolder, (BaseViewHolder) liveRecommendData);
                String str4 = null;
                ImageView imageView = baseViewHolder != null ? (ImageView) baseViewHolder.getView(R.id.image) : null;
                TextView textView = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_title) : null;
                TextView textView2 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_name) : null;
                TextView textView3 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_look_num) : null;
                TomatoLiveSDKUtils singleton = TomatoLiveSDKUtils.getSingleton();
                Context context2 = this.mContext;
                if (liveRecommendData != null) {
                    str4 = liveRecommendData.liveCoverUrl;
                }
                singleton.loadRoundCornersImage(context2, imageView, str4);
                if (textView != null) {
                    if (liveRecommendData == null || (str3 = liveRecommendData.topic) == null) {
                        str3 = "";
                    }
                    textView.setText(str3);
                }
                if (textView2 != null) {
                    if (liveRecommendData == null || (str2 = liveRecommendData.nickname) == null) {
                        str2 = "";
                    }
                    textView2.setText(str2);
                }
                if (textView3 != null) {
                    if (liveRecommendData == null || (str = liveRecommendData.popularity) == null) {
                        str = "";
                    }
                    textView3.setText(str);
                }
            }
        };
        GridItemDecoration.Builder builder = new GridItemDecoration.Builder(getContext());
        builder.setColorResource(R.color.transparent);
        builder.setHorizontalSpan(R.dimen.dimen_12);
        builder.setVerticalSpan(R.dimen.dimen_12);
        GridItemDecoration build = builder.build();
        RecyclerView recyclerView2 = (RecyclerView) _$_findCachedViewById(R$id.recycler_anchor);
        if (recyclerView2 != null) {
            recyclerView2.addItemDecoration(build);
        }
        RecyclerView recyclerView3 = (RecyclerView) _$_findCachedViewById(R$id.recycler_anchor);
        if (recyclerView3 != null) {
            recyclerView3.setLayoutManager(new GridLayoutManager(getContext(), 2));
        }
        RecyclerView recyclerView4 = (RecyclerView) _$_findCachedViewById(R$id.recycler_anchor);
        if (recyclerView4 != null) {
            recyclerView4.setAdapter(this.adapter);
        }
        BaseRecyclerViewAdapter<LiveRecommendData> baseRecyclerViewAdapter = this.adapter;
        if (baseRecyclerViewAdapter != null) {
            baseRecyclerViewAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.one.tomato.mvp.ui.chess.item.ChessHomeLiveItem$initAdapter$2
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter<Object, BaseViewHolder> adapter, View view, int i) {
                    TomatoLiveSDKUtils.getSingleton().updateServerUrl();
                    TomatoLiveSDKUtils.getSingleton().initAnim();
                    TomatoLiveSDKUtils singleton = TomatoLiveSDKUtils.getSingleton();
                    Context context2 = ChessHomeLiveItem.this.getContext();
                    Intrinsics.checkExpressionValueIsNotNull(adapter, "adapter");
                    Object obj = adapter.getData().get(i);
                    if (obj != null) {
                        singleton.startLiveActivity(context2, ((LiveRecommendData) obj).liveId);
                        return;
                    }
                    throw new TypeCastException("null cannot be cast to non-null type com.one.tomato.entity.LiveRecommendData");
                }
            });
        }
        Button button = (Button) _$_findCachedViewById(R$id.button_goin);
        if (button != null) {
            button.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.chess.item.ChessHomeLiveItem$initAdapter$3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ChessLiveActivity.Companion companion = ChessLiveActivity.Companion;
                    Context context2 = ChessHomeLiveItem.this.getContext();
                    Intrinsics.checkExpressionValueIsNotNull(context2, "context");
                    companion.startActivity(context2);
                }
            });
        }
    }

    public final void addData() {
        setRecommendData();
        setRankData();
    }

    private final void setRecommendData() {
        TomatoLiveSDKUtils.getSingleton().onLiveRecommendDataCallback(new TomatoLiveSDKUtils.LiveRecommendDataListener() { // from class: com.one.tomato.mvp.ui.chess.item.ChessHomeLiveItem$setRecommendData$1
            @Override // com.one.tomato.thirdpart.tomatolive.TomatoLiveSDKUtils.LiveRecommendDataListener
            public void onFail() {
            }

            @Override // com.one.tomato.thirdpart.tomatolive.TomatoLiveSDKUtils.LiveRecommendDataListener
            public void onSuccess(List<LiveRecommendData> list) {
                BaseRecyclerViewAdapter baseRecyclerViewAdapter;
                BaseRecyclerViewAdapter baseRecyclerViewAdapter2;
                if (!(list == null || list.isEmpty())) {
                    baseRecyclerViewAdapter = ChessHomeLiveItem.this.adapter;
                    if (baseRecyclerViewAdapter != null) {
                        baseRecyclerViewAdapter.setNewData(list);
                    }
                    baseRecyclerViewAdapter2 = ChessHomeLiveItem.this.adapter;
                    if (baseRecyclerViewAdapter2 == null) {
                        return;
                    }
                    baseRecyclerViewAdapter2.setEnableLoadMore(false);
                }
            }
        });
    }

    private final void setRankData() {
        TomatoLiveSDKUtils.getSingleton().onLiveRankDataCallback(new TomatoLiveSDKUtils.LiveRankDataListener() { // from class: com.one.tomato.mvp.ui.chess.item.ChessHomeLiveItem$setRankData$1
            @Override // com.one.tomato.thirdpart.tomatolive.TomatoLiveSDKUtils.LiveRankDataListener
            public void onSuccess(List<String> charmAvatarList, List<String> strengthAvatarList) {
                Intrinsics.checkParameterIsNotNull(charmAvatarList, "charmAvatarList");
                Intrinsics.checkParameterIsNotNull(strengthAvatarList, "strengthAvatarList");
                if ((!charmAvatarList.isEmpty()) && (!strengthAvatarList.isEmpty())) {
                    RelativeLayout relate_top = (RelativeLayout) ChessHomeLiveItem.this._$_findCachedViewById(R$id.relate_top);
                    Intrinsics.checkExpressionValueIsNotNull(relate_top, "relate_top");
                    relate_top.setVisibility(0);
                    if (!charmAvatarList.isEmpty()) {
                        if (charmAvatarList.size() >= 3) {
                            TomatoLiveSDKUtils.getSingleton().loadAvatar(ChessHomeLiveItem.this.getContext(), (ImageView) ChessHomeLiveItem.this._$_findCachedViewById(R$id.anchor_img_1), charmAvatarList.get(0));
                            TomatoLiveSDKUtils.getSingleton().loadAvatar(ChessHomeLiveItem.this.getContext(), (ImageView) ChessHomeLiveItem.this._$_findCachedViewById(R$id.anchor_img_2), charmAvatarList.get(1));
                            TomatoLiveSDKUtils.getSingleton().loadAvatar(ChessHomeLiveItem.this.getContext(), (ImageView) ChessHomeLiveItem.this._$_findCachedViewById(R$id.anchor_img_3), charmAvatarList.get(2));
                        } else if (charmAvatarList.size() >= 2) {
                            TomatoLiveSDKUtils.getSingleton().loadAvatar(ChessHomeLiveItem.this.getContext(), (ImageView) ChessHomeLiveItem.this._$_findCachedViewById(R$id.anchor_img_1), charmAvatarList.get(0));
                            TomatoLiveSDKUtils.getSingleton().loadAvatar(ChessHomeLiveItem.this.getContext(), (ImageView) ChessHomeLiveItem.this._$_findCachedViewById(R$id.anchor_img_2), charmAvatarList.get(1));
                        } else if (charmAvatarList.size() >= 1) {
                            TomatoLiveSDKUtils.getSingleton().loadAvatar(ChessHomeLiveItem.this.getContext(), (ImageView) ChessHomeLiveItem.this._$_findCachedViewById(R$id.anchor_img_1), charmAvatarList.get(0));
                        }
                    }
                    if (!(!strengthAvatarList.isEmpty())) {
                        return;
                    }
                    if (strengthAvatarList.size() >= 3) {
                        TomatoLiveSDKUtils.getSingleton().loadAvatar(ChessHomeLiveItem.this.getContext(), (ImageView) ChessHomeLiveItem.this._$_findCachedViewById(R$id.tycoon_img_1), strengthAvatarList.get(0));
                        TomatoLiveSDKUtils.getSingleton().loadAvatar(ChessHomeLiveItem.this.getContext(), (ImageView) ChessHomeLiveItem.this._$_findCachedViewById(R$id.tycoon_img_2), strengthAvatarList.get(1));
                        TomatoLiveSDKUtils.getSingleton().loadAvatar(ChessHomeLiveItem.this.getContext(), (ImageView) ChessHomeLiveItem.this._$_findCachedViewById(R$id.tycoon_img_3), strengthAvatarList.get(2));
                        return;
                    } else if (strengthAvatarList.size() >= 2) {
                        TomatoLiveSDKUtils.getSingleton().loadAvatar(ChessHomeLiveItem.this.getContext(), (ImageView) ChessHomeLiveItem.this._$_findCachedViewById(R$id.tycoon_img_1), strengthAvatarList.get(0));
                        TomatoLiveSDKUtils.getSingleton().loadAvatar(ChessHomeLiveItem.this.getContext(), (ImageView) ChessHomeLiveItem.this._$_findCachedViewById(R$id.tycoon_img_2), strengthAvatarList.get(1));
                        return;
                    } else if (strengthAvatarList.size() < 1) {
                        return;
                    } else {
                        TomatoLiveSDKUtils.getSingleton().loadAvatar(ChessHomeLiveItem.this.getContext(), (ImageView) ChessHomeLiveItem.this._$_findCachedViewById(R$id.tycoon_img_1), strengthAvatarList.get(0));
                        return;
                    }
                }
                RelativeLayout relate_top2 = (RelativeLayout) ChessHomeLiveItem.this._$_findCachedViewById(R$id.relate_top);
                Intrinsics.checkExpressionValueIsNotNull(relate_top2, "relate_top");
                relate_top2.setVisibility(8);
            }

            @Override // com.one.tomato.thirdpart.tomatolive.TomatoLiveSDKUtils.LiveRankDataListener
            public void onFail() {
                RelativeLayout relate_top = (RelativeLayout) ChessHomeLiveItem.this._$_findCachedViewById(R$id.relate_top);
                Intrinsics.checkExpressionValueIsNotNull(relate_top, "relate_top");
                relate_top.setVisibility(8);
            }
        });
        ((RelativeLayout) _$_findCachedViewById(R$id.relate_anchor)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.chess.item.ChessHomeLiveItem$setRankData$2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                TomatoLiveSDKUtils.getSingleton().startRankType(ChessHomeLiveItem.this.getContext(), 0);
            }
        });
        ((RelativeLayout) _$_findCachedViewById(R$id.relate_tycoon)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.chess.item.ChessHomeLiveItem$setRankData$3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                TomatoLiveSDKUtils.getSingleton().startRankType(ChessHomeLiveItem.this.getContext(), 1);
            }
        });
    }
}
