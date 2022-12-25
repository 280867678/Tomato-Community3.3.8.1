package com.one.tomato.mvp.p080ui.chess.item;

import android.content.Context;
import android.content.Intent;
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
import com.one.tomato.entity.ChessHomePostFeatruesBean;
import com.one.tomato.entity.ImageBean;
import com.one.tomato.entity.PostList;
import com.one.tomato.entity.p079db.ChessTypeBean;
import com.one.tomato.entity.p079db.ChessTypeSubBean;
import com.one.tomato.mvp.p080ui.chess.view.ChessPaPaHomeActivity;
import com.one.tomato.mvp.p080ui.papa.view.NewPaPaListVideoActivity;
import com.one.tomato.mvp.p080ui.papa.view.PaPaHotListHomeActivity;
import com.one.tomato.mvp.p080ui.papa.view.PaPaTumbeActivity;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.one.tomato.thirdpart.recyclerview.GridItemDecoration;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.image.ImageLoaderUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ChessHomePaPaItem.kt */
/* renamed from: com.one.tomato.mvp.ui.chess.item.ChessHomePaPaItem */
/* loaded from: classes3.dex */
public final class ChessHomePaPaItem extends RelativeLayout {
    private HashMap _$_findViewCache;
    private BaseRecyclerViewAdapter<ChessTypeSubBean> postAdapter;

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
    public ChessHomePaPaItem(Context context) {
        super(context);
        Intrinsics.checkParameterIsNotNull(context, "context");
        LayoutInflater.from(context).inflate(R.layout.chess_home_papa_item, this);
        initAdapter();
    }

    private final void initAdapter() {
        final Context context = getContext();
        final RecyclerView recyclerView = (RecyclerView) _$_findCachedViewById(R$id.recycler_papa);
        this.postAdapter = new BaseRecyclerViewAdapter<ChessTypeSubBean>(this, context, R.layout.item_chess_home_papa_item, recyclerView) { // from class: com.one.tomato.mvp.ui.chess.item.ChessHomePaPaItem$initAdapter$1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder baseViewHolder, ChessTypeSubBean chessTypeSubBean) {
                super.convert(baseViewHolder, (BaseViewHolder) chessTypeSubBean);
                String str = null;
                ImageView imageView = baseViewHolder != null ? (ImageView) baseViewHolder.getView(R.id.image) : null;
                Context context2 = this.mContext;
                if (chessTypeSubBean != null) {
                    str = chessTypeSubBean.getSecVideoCover();
                }
                ImageLoaderUtil.loadRecyclerThumbImage(context2, imageView, new ImageBean(str));
            }
        };
        GridItemDecoration.Builder builder = new GridItemDecoration.Builder(getContext());
        builder.setColorResource(R.color.transparent);
        builder.setHorizontalSpan(R.dimen.dimen_12);
        builder.setVerticalSpan(R.dimen.dimen_12);
        GridItemDecoration build = builder.build();
        RecyclerView recyclerView2 = (RecyclerView) _$_findCachedViewById(R$id.recycler_papa);
        if (recyclerView2 != null) {
            recyclerView2.addItemDecoration(build);
        }
        RecyclerView recyclerView3 = (RecyclerView) _$_findCachedViewById(R$id.recycler_papa);
        if (recyclerView3 != null) {
            recyclerView3.setLayoutManager(new GridLayoutManager(getContext(), 3));
        }
        RecyclerView recyclerView4 = (RecyclerView) _$_findCachedViewById(R$id.recycler_papa);
        if (recyclerView4 != null) {
            recyclerView4.setAdapter(this.postAdapter);
        }
        final Context context2 = getContext();
        final RecyclerView recyclerView5 = (RecyclerView) _$_findCachedViewById(R$id.recycler_features);
        BaseRecyclerViewAdapter<ChessHomePostFeatruesBean> baseRecyclerViewAdapter = new BaseRecyclerViewAdapter<ChessHomePostFeatruesBean>(this, context2, R.layout.item_chess_home_post_featrues_item, recyclerView5) { // from class: com.one.tomato.mvp.ui.chess.item.ChessHomePaPaItem$initAdapter$featuresAdapter$1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder baseViewHolder, ChessHomePostFeatruesBean chessHomePostFeatruesBean) {
                String str;
                super.convert(baseViewHolder, (BaseViewHolder) chessHomePostFeatruesBean);
                TextView textView = null;
                ImageView imageView = baseViewHolder != null ? (ImageView) baseViewHolder.getView(R.id.image) : null;
                if (baseViewHolder != null) {
                    textView = (TextView) baseViewHolder.getView(R.id.text);
                }
                if (imageView != null) {
                    imageView.setImageResource(chessHomePostFeatruesBean != null ? chessHomePostFeatruesBean.getResId() : 0);
                }
                if (textView != null) {
                    if (chessHomePostFeatruesBean == null || (str = chessHomePostFeatruesBean.getText()) == null) {
                        str = "";
                    }
                    textView.setText(str);
                }
            }
        };
        RecyclerView recyclerView6 = (RecyclerView) _$_findCachedViewById(R$id.recycler_features);
        if (recyclerView6 != null) {
            recyclerView6.addItemDecoration(build);
        }
        RecyclerView recyclerView7 = (RecyclerView) _$_findCachedViewById(R$id.recycler_features);
        if (recyclerView7 != null) {
            recyclerView7.setLayoutManager(new GridLayoutManager(getContext(), 3));
        }
        RecyclerView recyclerView8 = (RecyclerView) _$_findCachedViewById(R$id.recycler_features);
        if (recyclerView8 != null) {
            recyclerView8.setAdapter(baseRecyclerViewAdapter);
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(new ChessHomePostFeatruesBean(R.drawable.chess_home_papa_most_play, AppUtil.getString(R.string.post_most_paly_text), 1));
        arrayList.add(new ChessHomePostFeatruesBean(R.drawable.chess_home_papa_most_zan, AppUtil.getString(R.string.post_most_zan), 2));
        arrayList.add(new ChessHomePostFeatruesBean(R.drawable.chess_home_papa_most_collect, AppUtil.getString(R.string.post_most_collect), 3));
        baseRecyclerViewAdapter.setNewData(arrayList);
        baseRecyclerViewAdapter.setEnableLoadMore(false);
        baseRecyclerViewAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.one.tomato.mvp.ui.chess.item.ChessHomePaPaItem$initAdapter$2
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter<Object, BaseViewHolder> adapter, View view, int i) {
                Intrinsics.checkExpressionValueIsNotNull(adapter, "adapter");
                Object obj = adapter.getData().get(i);
                if (obj == null) {
                    throw new TypeCastException("null cannot be cast to non-null type com.one.tomato.entity.ChessHomePostFeatruesBean");
                }
                int itemId = ((ChessHomePostFeatruesBean) obj).getItemId();
                if (itemId == 1) {
                    PaPaTumbeActivity.Companion companion = PaPaTumbeActivity.Companion;
                    Context context3 = ChessHomePaPaItem.this.getContext();
                    Intrinsics.checkExpressionValueIsNotNull(context3, "context");
                    companion.startAct(1, context3);
                } else if (itemId == 2) {
                    PaPaTumbeActivity.Companion companion2 = PaPaTumbeActivity.Companion;
                    Context context4 = ChessHomePaPaItem.this.getContext();
                    Intrinsics.checkExpressionValueIsNotNull(context4, "context");
                    companion2.startAct(2, context4);
                } else if (itemId != 3) {
                } else {
                    PaPaTumbeActivity.Companion companion3 = PaPaTumbeActivity.Companion;
                    Context context5 = ChessHomePaPaItem.this.getContext();
                    Intrinsics.checkExpressionValueIsNotNull(context5, "context");
                    companion3.startAct(3, context5);
                }
            }
        });
        BaseRecyclerViewAdapter<ChessTypeSubBean> baseRecyclerViewAdapter2 = this.postAdapter;
        if (baseRecyclerViewAdapter2 != null) {
            baseRecyclerViewAdapter2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.one.tomato.mvp.ui.chess.item.ChessHomePaPaItem$initAdapter$3
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter<Object, BaseViewHolder> baseQuickAdapter, View view, int i) {
                    BaseRecyclerViewAdapter baseRecyclerViewAdapter3;
                    ArrayList<PostList> arrayListOf;
                    List<T> data;
                    baseRecyclerViewAdapter3 = ChessHomePaPaItem.this.postAdapter;
                    ChessTypeSubBean chessTypeSubBean = (baseRecyclerViewAdapter3 == null || (data = baseRecyclerViewAdapter3.getData()) == 0) ? null : (ChessTypeSubBean) data.get(i);
                    if (chessTypeSubBean != null) {
                        PostList postList = new PostList();
                        postList.setPostType(chessTypeSubBean.getPostType());
                        postList.setTagList(chessTypeSubBean.getTagList());
                        postList.setAlreadyPaid(chessTypeSubBean.isAlreadyPaid());
                        postList.setSerialGroupId(chessTypeSubBean.getSerialGroupId());
                        postList.setId(chessTypeSubBean.getId());
                        postList.setVideoTime(chessTypeSubBean.getVideoTime());
                        postList.setName(chessTypeSubBean.getName());
                        postList.setFavorTimes(chessTypeSubBean.getFavorTimes());
                        postList.setIsFavor(chessTypeSubBean.getIsFavor());
                        postList.setSize(chessTypeSubBean.getSize());
                        postList.setIsView(chessTypeSubBean.getIsView());
                        postList.setPrice(chessTypeSubBean.getPrice());
                        postList.setAvatar(chessTypeSubBean.getAvatar());
                        postList.setCanDownload(chessTypeSubBean.getCanDownload());
                        postList.setCommentTimes(chessTypeSubBean.getCommentTimes());
                        postList.setDescription(chessTypeSubBean.getDescription());
                        postList.setDownPrice(chessTypeSubBean.getDownPrice());
                        postList.setGoodNum(chessTypeSubBean.getGoodNum());
                        String memberId = chessTypeSubBean.getMemberId();
                        postList.setMemberId(memberId != null ? Integer.parseInt(memberId) : 0);
                        postList.setIsAttention(chessTypeSubBean.getIsAttention());
                        postList.setSecImageUrl(chessTypeSubBean.getSecImageUrl());
                        postList.setSecVideoCover(chessTypeSubBean.getSecVideoCover());
                        postList.setSecVideoUrl(chessTypeSubBean.getSecVideoUrl());
                        postList.setShareTimes(chessTypeSubBean.getShareTimes());
                        postList.setIsThumbUp(chessTypeSubBean.getIsThumbUp());
                        postList.setVideoView(chessTypeSubBean.getVideoView());
                        postList.setMemberIsAnchor(chessTypeSubBean.isMemberIsAnchor());
                        postList.setMemberIsUp(chessTypeSubBean.isMemberIsUp());
                        postList.setTitle(chessTypeSubBean.getTitle());
                        postList.setVipType(chessTypeSubBean.getVipType());
                        NewPaPaListVideoActivity.Companion companion = NewPaPaListVideoActivity.Companion;
                        Context context3 = ChessHomePaPaItem.this.getContext();
                        Intrinsics.checkExpressionValueIsNotNull(context3, "context");
                        arrayListOf = CollectionsKt__CollectionsKt.arrayListOf(postList);
                        companion.startAct(context3, arrayListOf, postList.getMemberId());
                    }
                }
            });
        }
        RelativeLayout relativeLayout = (RelativeLayout) _$_findCachedViewById(R$id.relate_top);
        if (relativeLayout != null) {
            relativeLayout.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.chess.item.ChessHomePaPaItem$initAdapter$4
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PaPaHotListHomeActivity.Companion companion = PaPaHotListHomeActivity.Companion;
                    Context context3 = ChessHomePaPaItem.this.getContext();
                    Intrinsics.checkExpressionValueIsNotNull(context3, "context");
                    companion.startAct(context3);
                }
            });
        }
        Button button = (Button) _$_findCachedViewById(R$id.button_goin);
        if (button != null) {
            button.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.chess.item.ChessHomePaPaItem$initAdapter$5
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    Intent intent = new Intent(ChessHomePaPaItem.this.getContext(), ChessPaPaHomeActivity.class);
                    Context context3 = ChessHomePaPaItem.this.getContext();
                    if (context3 != null) {
                        context3.startActivity(intent);
                    }
                }
            });
        }
    }

    public final void addData(ChessTypeBean chessTypeBean) {
        Intrinsics.checkParameterIsNotNull(chessTypeBean, "chessTypeBean");
        ArrayList<ChessTypeSubBean> data = chessTypeBean.getData();
        if (!(data == null || data.isEmpty())) {
            BaseRecyclerViewAdapter<ChessTypeSubBean> baseRecyclerViewAdapter = this.postAdapter;
            if (baseRecyclerViewAdapter != null) {
                baseRecyclerViewAdapter.setNewData(data);
            }
            BaseRecyclerViewAdapter<ChessTypeSubBean> baseRecyclerViewAdapter2 = this.postAdapter;
            if (baseRecyclerViewAdapter2 == null) {
                return;
            }
            baseRecyclerViewAdapter2.setEnableLoadMore(false);
        }
    }
}
