package com.one.tomato.mvp.p080ui.post.item;

import android.content.Context;
import android.content.Intent;
import android.support.p005v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.R$id;
import com.one.tomato.entity.p079db.PostHotMessageBean;
import com.one.tomato.mvp.p080ui.post.adapter.PostListItemHotMessageAdapter;
import com.one.tomato.mvp.p080ui.post.view.PostHotMessageActivity;
import com.one.tomato.mvp.p080ui.post.view.PostHotMessageDetailActivity;
import com.one.tomato.thirdpart.recyclerview.BetterHorScrollRecyclerView;
import java.util.ArrayList;
import java.util.HashMap;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PostHotMessageItem.kt */
/* renamed from: com.one.tomato.mvp.ui.post.item.PostHotMessageItem */
/* loaded from: classes3.dex */
public final class PostHotMessageItem extends RelativeLayout {
    private HashMap _$_findViewCache;
    private PostListItemHotMessageAdapter adapter;

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
    public PostHotMessageItem(final Context context) {
        super(context);
        Intrinsics.checkParameterIsNotNull(context, "context");
        LayoutInflater.from(context).inflate(R.layout.item_post_list_hot_message, this);
        BetterHorScrollRecyclerView betterHorScrollRecyclerView = (BetterHorScrollRecyclerView) _$_findCachedViewById(R$id.recycler_view);
        if (betterHorScrollRecyclerView != null) {
            betterHorScrollRecyclerView.setLayoutManager(new LinearLayoutManager(context, 0, false));
        }
        this.adapter = new PostListItemHotMessageAdapter();
        BetterHorScrollRecyclerView betterHorScrollRecyclerView2 = (BetterHorScrollRecyclerView) _$_findCachedViewById(R$id.recycler_view);
        if (betterHorScrollRecyclerView2 != null) {
            betterHorScrollRecyclerView2.setAdapter(this.adapter);
        }
        TextView textView = (TextView) _$_findCachedViewById(R$id.text_more);
        if (textView != null) {
            textView.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.item.PostHotMessageItem.1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    context.startActivity(new Intent(context, PostHotMessageActivity.class));
                }
            });
        }
        PostListItemHotMessageAdapter postListItemHotMessageAdapter = this.adapter;
        if (postListItemHotMessageAdapter != null) {
            postListItemHotMessageAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.one.tomato.mvp.ui.post.item.PostHotMessageItem.2
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter<Object, BaseViewHolder> baseQuickAdapter, View view, int i) {
                    Object item = baseQuickAdapter != null ? baseQuickAdapter.getItem(i) : null;
                    if (item == null) {
                        throw new TypeCastException("null cannot be cast to non-null type com.one.tomato.entity.db.PostHotMessageBean");
                    }
                    PostHotMessageDetailActivity.Companion.startAct(context, (PostHotMessageBean) item);
                }
            });
        }
    }

    public final void setData(ArrayList<PostHotMessageBean> arrayList) {
        PostListItemHotMessageAdapter postListItemHotMessageAdapter = this.adapter;
        if (postListItemHotMessageAdapter != null) {
            postListItemHotMessageAdapter.setNewData(arrayList);
        }
        PostListItemHotMessageAdapter postListItemHotMessageAdapter2 = this.adapter;
        if (postListItemHotMessageAdapter2 != null) {
            postListItemHotMessageAdapter2.setEnableLoadMore(false);
        }
    }
}
