package com.one.tomato.mvp.p080ui.post.view;

import android.content.Context;
import android.support.p005v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.R$id;
import com.one.tomato.entity.p079db.PostHotMessageBean;
import com.one.tomato.mvp.base.view.MvpBaseRecyclerViewActivity;
import com.one.tomato.mvp.p080ui.post.adapter.PostHotMessageAdapter;
import com.one.tomato.mvp.p080ui.post.impl.IPostHotMessageContact;
import com.one.tomato.mvp.p080ui.post.presenter.PostHotMessagePresenter;
import com.one.tomato.mvp.p080ui.post.view.PostHotMessageDetailActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.tomatolive.library.http.RequestParams;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PostHotMessageActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.post.view.PostHotMessageActivity */
/* loaded from: classes3.dex */
public final class PostHotMessageActivity extends MvpBaseRecyclerViewActivity<IPostHotMessageContact, PostHotMessagePresenter, PostHotMessageAdapter, PostHotMessageBean> implements IPostHotMessageContact {
    private HashMap _$_findViewCache;

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

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public int createLayoutView() {
        return R.layout.activity_post_hot_message;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewActivity
    public void onEmptyRefresh(View view, int i) {
        Intrinsics.checkParameterIsNotNull(view, "view");
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewActivity
    /* renamed from: createAdapter */
    public PostHotMessageAdapter mo6446createAdapter() {
        Context mContext = getMContext();
        if (mContext == null) {
            Intrinsics.throwNpe();
            throw null;
        }
        RecyclerView recyclerView = getRecyclerView();
        if (recyclerView != null) {
            return new PostHotMessageAdapter(mContext, recyclerView);
        }
        Intrinsics.throwNpe();
        throw null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewActivity
    public void refresh() {
        setPageNo(1);
        setState(getGET_LIST_REFRESH());
        request();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewActivity
    public void loadMore() {
        setState(getGET_LIST_LOAD());
        request();
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    /* renamed from: createPresenter  reason: collision with other method in class */
    public PostHotMessagePresenter mo6439createPresenter() {
        return new PostHotMessagePresenter();
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initData() {
        SmartRefreshLayout refreshLayout = getRefreshLayout();
        if (refreshLayout != null) {
            refreshLayout.autoRefresh();
        }
        PostHotMessageAdapter adapter = getAdapter();
        if (adapter != null) {
            adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.one.tomato.mvp.ui.post.view.PostHotMessageActivity$initData$1
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter<Object, BaseViewHolder> baseQuickAdapter, View view, int i) {
                    Context mContext;
                    Object item = baseQuickAdapter != null ? baseQuickAdapter.getItem(i) : null;
                    if (item == null) {
                        throw new TypeCastException("null cannot be cast to non-null type com.one.tomato.entity.db.PostHotMessageBean");
                    }
                    PostHotMessageBean postHotMessageBean = (PostHotMessageBean) item;
                    PostHotMessageDetailActivity.Companion companion = PostHotMessageDetailActivity.Companion;
                    mContext = PostHotMessageActivity.this.getMContext();
                    if (mContext != null) {
                        companion.startAct(mContext, postHotMessageBean);
                    } else {
                        Intrinsics.throwNpe();
                        throw null;
                    }
                }
            });
        }
        ImageView imageView = (ImageView) _$_findCachedViewById(R$id.image_back);
        if (imageView != null) {
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.post.view.PostHotMessageActivity$initData$2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PostHotMessageActivity.this.onBackPressed();
                }
            });
        }
    }

    @Override // com.one.tomato.mvp.base.view.IBaseView
    public void onError(String str) {
        List<PostHotMessageBean> data;
        PostHotMessageAdapter adapter;
        PostHotMessageAdapter adapter2;
        SmartRefreshLayout refreshLayout;
        if (getState() == getGET_LIST_REFRESH() && (refreshLayout = getRefreshLayout()) != null) {
            refreshLayout.mo6481finishRefresh();
        }
        if (getState() == getGET_LIST_LOAD() && (adapter2 = getAdapter()) != null) {
            adapter2.loadMoreFail();
        }
        PostHotMessageAdapter adapter3 = getAdapter();
        if (adapter3 == null || (data = adapter3.getData()) == null || data.size() != 0 || (adapter = getAdapter()) == null) {
            return;
        }
        adapter.setEmptyViewState(1, getRefreshLayout());
    }

    private final void request() {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("pageNo", Integer.valueOf(getPageNo()));
        linkedHashMap.put(RequestParams.PAGE_SIZE, Integer.valueOf(getPageSize()));
        PostHotMessagePresenter postHotMessagePresenter = (PostHotMessagePresenter) getMPresenter();
        if (postHotMessagePresenter != null) {
            postHotMessagePresenter.requestList(linkedHashMap);
        }
    }

    @Override // com.one.tomato.mvp.p080ui.post.impl.IPostHotMessageContact
    public void handleHotMessage(ArrayList<PostHotMessageBean> arrayList) {
        updateData(getState(), arrayList);
    }
}
