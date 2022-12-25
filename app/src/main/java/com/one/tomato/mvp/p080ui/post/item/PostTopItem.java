package com.one.tomato.mvp.p080ui.post.item;

import android.content.Context;
import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.R$id;
import com.one.tomato.entity.PostList;
import com.one.tomato.mvp.p080ui.post.adapter.NewPostTopAdapter;
import com.one.tomato.mvp.p080ui.post.impl.NewPostItemOnClickCallBack;
import java.util.ArrayList;
import java.util.HashMap;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PostTopItem.kt */
/* renamed from: com.one.tomato.mvp.ui.post.item.PostTopItem */
/* loaded from: classes3.dex */
public final class PostTopItem extends RelativeLayout {
    private HashMap _$_findViewCache;
    private NewPostTopAdapter adapter;

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

    public PostTopItem(Context context, final NewPostItemOnClickCallBack newPostItemOnClickCallBack, ArrayList<PostList> arrayList) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.item_new_post_top, this);
        RecyclerView recyclerView = (RecyclerView) _$_findCachedViewById(R$id.recycler_view);
        if (recyclerView != null) {
            recyclerView.setItemViewCacheSize(5);
        }
        RecyclerView recyclerView2 = (RecyclerView) _$_findCachedViewById(R$id.recycler_view);
        if (recyclerView2 != null) {
            recyclerView2.setLayoutManager(new LinearLayoutManager(context, context, 0, false) { // from class: com.one.tomato.mvp.ui.post.item.PostTopItem.1
                @Override // android.support.p005v7.widget.LinearLayoutManager
                protected int getExtraLayoutSpace(RecyclerView.State state) {
                    return 300;
                }

                {
                    super(context, r3, r4);
                }
            });
        }
        this.adapter = new NewPostTopAdapter(context, arrayList, (RecyclerView) _$_findCachedViewById(R$id.recycler_view), newPostItemOnClickCallBack);
        RecyclerView recyclerView3 = (RecyclerView) _$_findCachedViewById(R$id.recycler_view);
        if (recyclerView3 != null) {
            recyclerView3.setAdapter(this.adapter);
        }
        NewPostTopAdapter newPostTopAdapter = this.adapter;
        if (newPostTopAdapter != null) {
            newPostTopAdapter.setEnableLoadMore(false);
        }
        NewPostTopAdapter newPostTopAdapter2 = this.adapter;
        if (newPostTopAdapter2 != null) {
            newPostTopAdapter2.loadMoreEnd();
        }
        NewPostTopAdapter newPostTopAdapter3 = this.adapter;
        if (newPostTopAdapter3 != null) {
            newPostTopAdapter3.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.one.tomato.mvp.ui.post.item.PostTopItem.2
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter<Object, BaseViewHolder> adapter, View view, int i) {
                    Intrinsics.checkExpressionValueIsNotNull(adapter, "adapter");
                    Object obj = adapter.getData().get(i);
                    if (obj == null) {
                        throw new TypeCastException("null cannot be cast to non-null type com.one.tomato.entity.PostList");
                    }
                    PostList postList = (PostList) obj;
                    NewPostItemOnClickCallBack newPostItemOnClickCallBack2 = NewPostItemOnClickCallBack.this;
                    if (newPostItemOnClickCallBack2 == null) {
                        return;
                    }
                    newPostItemOnClickCallBack2.itemClick(postList, view, i);
                }
            });
        }
    }
}
