package com.one.tomato.mvp.p080ui.game.view;

import android.content.Context;
import android.os.Bundle;
import android.support.p002v4.widget.NestedScrollView;
import android.support.p005v7.widget.GridLayoutManager;
import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.view.View;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.R$id;
import com.one.tomato.entity.p079db.SubGamesBean;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment;
import com.one.tomato.mvp.p080ui.game.adapter.GameTypeAllSubAdapter;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.GameUtils;
import com.one.tomato.utils.ToastUtil;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: GameSinglePageFragment.kt */
/* renamed from: com.one.tomato.mvp.ui.game.view.GameSinglePageFragment */
/* loaded from: classes3.dex */
public final class GameSinglePageFragment extends MvpBaseRecyclerViewFragment<IBaseView, MvpBasePresenter<IBaseView>, GameTypeAllSubAdapter, SubGamesBean> {
    public static final Companion Companion = new Companion(null);
    private HashMap _$_findViewCache;

    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment, com.one.tomato.mvp.base.view.MvpBaseFragment
    public void _$_clearFindViewByIdCache() {
        HashMap hashMap = this._$_findViewCache;
        if (hashMap != null) {
            hashMap.clear();
        }
    }

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

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public int createLayoutID() {
        return R.layout.game_single_page_fragment;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    /* renamed from: createPresenter */
    public MvpBasePresenter<IBaseView> mo6441createPresenter() {
        return null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment
    public void loadMore() {
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment, com.one.tomato.mvp.base.view.MvpBaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public /* synthetic */ void onDestroyView() {
        super.onDestroyView();
        _$_clearFindViewByIdCache();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment
    public void onEmptyRefresh(View view, int i) {
        Intrinsics.checkParameterIsNotNull(view, "view");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment
    public void refresh() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment
    /* renamed from: createAdapter */
    public GameTypeAllSubAdapter mo6434createAdapter() {
        return new GameTypeAllSubAdapter(getMContext(), getRecyclerView());
    }

    /* compiled from: GameSinglePageFragment.kt */
    /* renamed from: com.one.tomato.mvp.ui.game.view.GameSinglePageFragment$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final GameSinglePageFragment getInstance(ArrayList<SubGamesBean> list) {
            Intrinsics.checkParameterIsNotNull(list, "list");
            GameSinglePageFragment gameSinglePageFragment = new GameSinglePageFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable(AopConstants.APP_PROPERTIES_KEY, list);
            gameSinglePageFragment.setArguments(bundle);
            return gameSinglePageFragment;
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment, com.one.tomato.mvp.base.view.MvpBaseFragment
    public void initView() {
        super.initView();
        setUserVisibleHint(true);
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void inintData() {
        GameTypeAllSubAdapter adapter = getAdapter();
        if (adapter != null) {
            adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.one.tomato.mvp.ui.game.view.GameSinglePageFragment$inintData$1
                @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter<Object, BaseViewHolder> adapter2, View view, int i) {
                    Context mContext;
                    Integer valueOf = view != null ? Integer.valueOf(view.getId()) : null;
                    if (valueOf == null) {
                        Intrinsics.throwNpe();
                        throw null;
                    } else if (AppUtil.isFastClick(valueOf.intValue(), 1000)) {
                        ToastUtil.showCenterToast((int) R.string.post_fast_click_tip);
                    } else {
                        Intrinsics.checkExpressionValueIsNotNull(adapter2, "adapter");
                        Object obj = adapter2.getData().get(i);
                        if (obj == null) {
                            throw new TypeCastException("null cannot be cast to non-null type com.one.tomato.entity.db.SubGamesBean");
                        }
                        SubGamesBean subGamesBean = (SubGamesBean) obj;
                        GameUtils gameUtils = GameUtils.INSTANCE;
                        mContext = GameSinglePageFragment.this.getMContext();
                        if (mContext != null) {
                            gameUtils.clickGame(mContext, subGamesBean);
                        } else {
                            Intrinsics.throwNpe();
                            throw null;
                        }
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void onLazyLoad() {
        super.onLazyLoad();
        Bundle arguments = getArguments();
        String str = null;
        Serializable serializable = arguments != null ? arguments.getSerializable(AopConstants.APP_PROPERTIES_KEY) : null;
        if (serializable == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.ArrayList<com.one.tomato.entity.db.SubGamesBean> /* = java.util.ArrayList<com.one.tomato.entity.db.SubGamesBean> */");
        }
        ArrayList arrayList = (ArrayList) serializable;
        if (!(arrayList == null || arrayList.isEmpty())) {
            RecyclerView recyclerView = getRecyclerView();
            if (recyclerView != null) {
                recyclerView.setVisibility(0);
            }
            NestedScrollView nestedScrollView = (NestedScrollView) _$_findCachedViewById(R$id.nestedScrollView);
            if (nestedScrollView != null) {
                nestedScrollView.setVisibility(8);
            }
            Object obj = arrayList.get(0);
            Intrinsics.checkExpressionValueIsNotNull(obj, "parcelable[0]");
            SubGamesBean subGamesBean = (SubGamesBean) obj;
            if (subGamesBean != null) {
                str = subGamesBean.getAdLogoType();
            }
            if (Intrinsics.areEqual(str, "1")) {
                RecyclerView recyclerView2 = getRecyclerView();
                if (recyclerView2 != null) {
                    recyclerView2.setLayoutManager(new GridLayoutManager(getMContext(), 4));
                }
            } else {
                RecyclerView recyclerView3 = getRecyclerView();
                if (recyclerView3 != null) {
                    recyclerView3.setLayoutManager(new LinearLayoutManager(getMContext(), 1, false));
                }
            }
            GameTypeAllSubAdapter adapter = getAdapter();
            if (adapter != null) {
                adapter.addData((Collection) arrayList);
            }
            GameTypeAllSubAdapter adapter2 = getAdapter();
            if (adapter2 == null) {
                return;
            }
            adapter2.setEnableLoadMore(false);
            return;
        }
        RecyclerView recyclerView4 = getRecyclerView();
        if (recyclerView4 != null) {
            recyclerView4.setVisibility(8);
        }
        NestedScrollView nestedScrollView2 = (NestedScrollView) _$_findCachedViewById(R$id.nestedScrollView);
        if (nestedScrollView2 == null) {
            return;
        }
        nestedScrollView2.setVisibility(0);
    }
}
