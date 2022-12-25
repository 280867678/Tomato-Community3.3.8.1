package com.one.tomato.mvp.p080ui.post.view;

import android.os.Bundle;
import android.view.View;
import com.one.tomato.entity.PostList;
import com.one.tomato.entity.p079db.UpRecommentBean;
import com.one.tomato.mvp.p080ui.post.adapter.NewPostTabListAdapter;
import com.one.tomato.mvp.p080ui.post.item.PostUpItem;
import com.one.tomato.utils.DBUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MyHomePagePostFragment.kt */
/* renamed from: com.one.tomato.mvp.ui.post.view.MyHomePagePostFragment */
/* loaded from: classes3.dex */
public final class MyHomePagePostFragment extends NewPostTabListFragment {
    public static final Companion Companion = new Companion(null);
    private HashMap _$_findViewCache;
    private int attentionFlag;
    private int attentionMemberId;
    private int groupId;
    private int personMemberId;
    private int tagPostId;

    @Override // com.one.tomato.mvp.p080ui.post.view.NewPostTabListFragment, com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment, com.one.tomato.mvp.base.view.MvpBaseFragment
    public void _$_clearFindViewByIdCache() {
        HashMap hashMap = this._$_findViewCache;
        if (hashMap != null) {
            hashMap.clear();
        }
    }

    @Override // com.one.tomato.mvp.p080ui.post.view.NewPostTabListFragment
    public View _$_findCachedViewById(int i) {
        if (this._$_findViewCache == null) {
            this._$_findViewCache = new HashMap();
        }
        View view = (View) this._$_findViewCache.get(Integer.valueOf(i));
        if (view == null) {
            View view2 = getView();
            if (view2 == null) {
                return null;
            }
            View findViewById = view2.findViewById(i);
            this._$_findViewCache.put(Integer.valueOf(i), findViewById);
            return findViewById;
        }
        return view;
    }

    @Override // com.one.tomato.mvp.p080ui.post.view.NewPostTabListFragment, com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment, com.one.tomato.mvp.base.view.MvpBaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public /* synthetic */ void onDestroyView() {
        super.onDestroyView();
        _$_clearFindViewByIdCache();
    }

    /* compiled from: MyHomePagePostFragment.kt */
    /* renamed from: com.one.tomato.mvp.ui.post.view.MyHomePagePostFragment$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final synchronized MyHomePagePostFragment getInstance(int i, String businessType, boolean z) {
            MyHomePagePostFragment myHomePagePostFragment;
            Intrinsics.checkParameterIsNotNull(businessType, "businessType");
            myHomePagePostFragment = new MyHomePagePostFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("category", i);
            bundle.putString("business", businessType);
            bundle.putBoolean("need_refresh", z);
            myHomePagePostFragment.setArguments(bundle);
            return myHomePagePostFragment;
        }
    }

    @Override // com.one.tomato.mvp.p080ui.post.view.NewPostTabListFragment, com.one.tomato.mvp.base.view.MvpBaseFragment
    public void inintData() {
        Bundle arguments = getArguments();
        setCategory(arguments != null ? arguments.getInt("category") : -1);
        Boolean bool = null;
        setBusinessType(arguments != null ? arguments.getString("business") : null);
        if (arguments != null) {
            bool = Boolean.valueOf(arguments.getBoolean("need_refresh"));
        }
        setNeedFresh(bool);
        setChannelId(arguments != null ? arguments.getInt("channel_id") : 0);
        if (Intrinsics.areEqual(isNeedFresh(), true)) {
            super.inintData();
            return;
        }
        NewPostTabListAdapter adapter = getAdapter();
        if (adapter != null) {
            adapter.setBusinessType(getBusinessType());
        }
        autoRecycylerViewPlay();
        registerRxBus();
    }

    public final void fragmentIsVisible(boolean z) {
        if (z) {
            onVisibleLoad();
        } else {
            onInvisibleLoad();
        }
    }

    @Override // com.one.tomato.mvp.p080ui.post.view.NewPostTabListFragment, com.one.tomato.mvp.base.view.MvpBaseFragment
    protected void onLazyLoad() {
        super.onLazyLoad();
    }

    public final void setPersonMemberId(int i) {
        this.personMemberId = i;
    }

    public final void onRefresh(int i) {
        List<PostList> data;
        if (this.personMemberId == i) {
            return;
        }
        this.personMemberId = i;
        NewPostTabListAdapter adapter = getAdapter();
        if (adapter != null && (data = adapter.getData()) != null) {
            data.clear();
        }
        NewPostTabListAdapter adapter2 = getAdapter();
        if (adapter2 != null) {
            adapter2.notifyDataSetChanged();
        }
        NewPostTabListAdapter adapter3 = getAdapter();
        if (adapter3 != null) {
            adapter3.setEmptyViewState(0, getRefreshLayout());
        }
        SmartRefreshLayout refreshLayout = getRefreshLayout();
        if (refreshLayout == null) {
            return;
        }
        refreshLayout.postDelayed(new Runnable() { // from class: com.one.tomato.mvp.ui.post.view.MyHomePagePostFragment$onRefresh$1
            @Override // java.lang.Runnable
            public final void run() {
                MyHomePagePostFragment.this.onRefresh();
            }
        }, 100L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onRefresh() {
        super.refresh();
    }

    @Override // com.one.tomato.mvp.p080ui.post.view.NewPostTabListFragment, com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment
    protected void refresh() {
        if (Intrinsics.areEqual(isNeedFresh(), false)) {
            cancleLoading();
            SmartRefreshLayout refreshLayout = getRefreshLayout();
            if (refreshLayout != null) {
                refreshLayout.mo6481finishRefresh();
            }
            SmartRefreshLayout refreshLayout2 = getRefreshLayout();
            if (refreshLayout2 == null) {
                return;
            }
            refreshLayout2.setEnableRefresh(false);
            return;
        }
        super.refresh();
    }

    @Override // com.one.tomato.mvp.p080ui.post.view.NewPostTabListFragment
    public int getPersonMemberId() {
        return this.personMemberId;
    }

    public final void setGroupId(int i) {
        this.groupId = i;
    }

    @Override // com.one.tomato.mvp.p080ui.post.view.NewPostTabListFragment
    public int getGroundId() {
        return this.groupId;
    }

    public final void setAttentionMemberId(int i, int i2) {
        this.attentionMemberId = i;
        this.attentionFlag = i2;
    }

    @Override // com.one.tomato.mvp.p080ui.post.view.NewPostTabListFragment
    public int getAttentionFlag() {
        return this.attentionFlag;
    }

    @Override // com.one.tomato.mvp.p080ui.post.view.NewPostTabListFragment
    public int getAttentionMemberId() {
        return this.attentionMemberId;
    }

    public final void setTagId(int i) {
        this.tagPostId = i;
    }

    @Override // com.one.tomato.mvp.p080ui.post.view.NewPostTabListFragment
    public int getTagId() {
        return this.tagPostId;
    }

    @Override // com.one.tomato.mvp.p080ui.post.view.NewPostTabListFragment
    public void addHeaderView() {
        if (getMContext() != null) {
            PostUpItem postUpItem = new PostUpItem(getMContext());
            List<UpRecommentBean> recommentUpBean = DBUtil.getRecommentUpBean();
            if (!(recommentUpBean == null || recommentUpBean.isEmpty())) {
                postUpItem.setData((ArrayList) recommentUpBean, true);
            }
            NewPostTabListAdapter adapter = getAdapter();
            if (adapter == null) {
                return;
            }
            adapter.addHeaderView(postUpItem);
        }
    }
}
