package com.one.tomato.mvp.p080ui.game.view;

import android.os.Bundle;
import android.view.View;
import com.broccoli.p150bh.R;
import com.one.tomato.entity.GameBGLoginBean;
import com.one.tomato.entity.p079db.AdPage;
import com.one.tomato.entity.p079db.GameTypeData;
import com.one.tomato.entity.p079db.SubGamesBean;
import com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment;
import com.one.tomato.mvp.p080ui.game.adapter.GameCenterAdapter;
import com.one.tomato.mvp.p080ui.game.impl.IGameContact$IGameView;
import com.one.tomato.mvp.p080ui.game.presenter.GamePresenter;
import com.one.tomato.utils.DBUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.tomatolive.library.http.RequestParams;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import kotlin.Standard;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: GameCenterFragment.kt */
/* renamed from: com.one.tomato.mvp.ui.game.view.GameCenterFragment */
/* loaded from: classes3.dex */
public final class GameCenterFragment extends MvpBaseRecyclerViewFragment<IGameContact$IGameView, GamePresenter, GameCenterAdapter, AdPage> implements IGameContact$IGameView {
    public static final Companion Companion = new Companion(null);
    private HashMap _$_findViewCache;
    public String businessType;
    public ArrayList<AdPage> dbList;

    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment, com.one.tomato.mvp.base.view.MvpBaseFragment
    public void _$_clearFindViewByIdCache() {
        HashMap hashMap = this._$_findViewCache;
        if (hashMap != null) {
            hashMap.clear();
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public int createLayoutID() {
        return R.layout.fragment_game_center;
    }

    @Override // com.one.tomato.mvp.p080ui.game.impl.IGameContact$IGameView
    public void handlerGameType(ArrayList<GameTypeData> list) {
        Intrinsics.checkParameterIsNotNull(list, "list");
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment, com.one.tomato.mvp.base.view.MvpBaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public /* synthetic */ void onDestroyView() {
        super.onDestroyView();
        _$_clearFindViewByIdCache();
    }

    @Override // com.one.tomato.mvp.p080ui.game.impl.IGameContact$IGameView
    public void handlerBGLogin(GameBGLoginBean gameBGLoginBean, SubGamesBean subGamesBean) {
        Intrinsics.checkParameterIsNotNull(subGamesBean, "subGamesBean");
        throw new Standard("An operation is not implemented: not implemented");
    }

    /* compiled from: GameCenterFragment.kt */
    /* renamed from: com.one.tomato.mvp.ui.game.view.GameCenterFragment$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final GameCenterFragment getInstance(String businessType) {
            Intrinsics.checkParameterIsNotNull(businessType, "businessType");
            GameCenterFragment gameCenterFragment = new GameCenterFragment();
            Bundle bundle = new Bundle();
            bundle.putString("type", businessType);
            gameCenterFragment.setArguments(bundle);
            return gameCenterFragment;
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Bundle arguments = getArguments();
        if (arguments != null) {
            String string = arguments.getString("type");
            Intrinsics.checkExpressionValueIsNotNull(string, "bundle.getString(TYPE)");
            this.businessType = string;
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    /* renamed from: createPresenter  reason: collision with other method in class */
    public GamePresenter mo6441createPresenter() {
        return new GamePresenter();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment
    public void initRecyclerView() {
        super.initRecyclerView();
        String str = this.businessType;
        if (str == null) {
            Intrinsics.throwUninitializedPropertyAccessException("businessType");
            throw null;
        } else if (!"tab".equals(str)) {
        } else {
            configGridLayoutManager(getRecyclerView(), 2);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment
    /* renamed from: createAdapter */
    public GameCenterAdapter mo6434createAdapter() {
        String str = this.businessType;
        if (str != null) {
            return new GameCenterAdapter(str, getMContext(), getRecyclerView());
        }
        Intrinsics.throwUninitializedPropertyAccessException("businessType");
        throw null;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void inintData() {
        String str = this.businessType;
        if (str == null) {
            Intrinsics.throwUninitializedPropertyAccessException("businessType");
            throw null;
        }
        int hashCode = str.hashCode();
        if (hashCode == -1364013995) {
            if (!str.equals("center")) {
                return;
            }
            setUserVisibleHint(true);
        } else if (hashCode != 114581 || !str.equals("tab")) {
        } else {
            setUserVisibleHint(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void onLazyLoad() {
        super.onLazyLoad();
        String str = this.businessType;
        if (str == null) {
            Intrinsics.throwUninitializedPropertyAccessException("businessType");
            throw null;
        }
        ArrayList<AdPage> adPage = DBUtil.getAdPage(str);
        Intrinsics.checkExpressionValueIsNotNull(adPage, "DBUtil.getAdPage(businessType)");
        this.dbList = adPage;
        ArrayList<AdPage> arrayList = this.dbList;
        if (arrayList == null) {
            Intrinsics.throwUninitializedPropertyAccessException("dbList");
            throw null;
        }
        if (!arrayList.isEmpty()) {
            GameCenterAdapter adapter = getAdapter();
            if (adapter != null) {
                ArrayList<AdPage> arrayList2 = this.dbList;
                if (arrayList2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("dbList");
                    throw null;
                }
                adapter.setNewData(arrayList2);
            }
            GameCenterAdapter adapter2 = getAdapter();
            if (adapter2 != null) {
                adapter2.setEnableLoadMore(false);
            }
        }
        SmartRefreshLayout refreshLayout = getRefreshLayout();
        if (refreshLayout == null) {
            return;
        }
        refreshLayout.autoRefresh();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment
    public void refresh() {
        setState(getGET_LIST_REFRESH());
        setPageNo(1);
        requestList();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment
    public void loadMore() {
        setState(getGET_LIST_LOAD());
        requestList();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment
    public void onEmptyRefresh(View view, int i) {
        Intrinsics.checkParameterIsNotNull(view, "view");
        refresh();
    }

    private final void requestList() {
        int i;
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("pageNo", Integer.valueOf(getPageNo()));
        hashMap.put(RequestParams.PAGE_SIZE, Integer.valueOf(getPageSize()));
        String str = this.businessType;
        if (str == null) {
            Intrinsics.throwUninitializedPropertyAccessException("businessType");
            throw null;
        }
        int hashCode = str.hashCode();
        if (hashCode == -1364013995) {
            if (str.equals("center")) {
                i = 8;
            }
            i = 0;
        } else if (hashCode != 114581) {
            if (hashCode == 3208415 && str.equals("home")) {
                i = 6;
            }
            i = 0;
        } else {
            if (str.equals("tab")) {
                i = 7;
            }
            i = 0;
        }
        hashMap.put("type", Integer.valueOf(i));
        GamePresenter gamePresenter = (GamePresenter) getMPresenter();
        if (gamePresenter == null) {
            return;
        }
        gamePresenter.requestList(hashMap);
    }

    @Override // com.one.tomato.mvp.base.view.IBaseView
    public void onError(String str) {
        updateDataFail();
    }

    @Override // com.one.tomato.mvp.p080ui.game.impl.IGameContact$IGameView
    public void handleList(ArrayList<AdPage> list) {
        Intrinsics.checkParameterIsNotNull(list, "list");
        String str = this.businessType;
        if (str == null) {
            Intrinsics.throwUninitializedPropertyAccessException("businessType");
            throw null;
        }
        ArrayList<AdPage> adPage = DBUtil.getAdPage(str);
        Intrinsics.checkExpressionValueIsNotNull(adPage, "DBUtil.getAdPage(businessType)");
        this.dbList = adPage;
        ArrayList<AdPage> arrayList = this.dbList;
        if (arrayList == null) {
            Intrinsics.throwUninitializedPropertyAccessException("dbList");
            throw null;
        }
        if (!arrayList.isEmpty()) {
            Iterator<AdPage> it2 = list.iterator();
            while (it2.hasNext()) {
                AdPage item = it2.next();
                ArrayList<AdPage> arrayList2 = this.dbList;
                if (arrayList2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("dbList");
                    throw null;
                } else if (arrayList2.contains(item)) {
                    ArrayList<AdPage> arrayList3 = this.dbList;
                    if (arrayList3 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("dbList");
                        throw null;
                    }
                    int indexOf = arrayList3.indexOf(item);
                    ArrayList<AdPage> arrayList4 = this.dbList;
                    if (arrayList4 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("dbList");
                        throw null;
                    }
                    AdPage adPage2 = arrayList4.get(indexOf);
                    Intrinsics.checkExpressionValueIsNotNull(adPage2, "dbList.get(position)");
                    Intrinsics.checkExpressionValueIsNotNull(item, "item");
                    item.setWebAppNew(adPage2.isWebAppNew());
                } else {
                    Intrinsics.checkExpressionValueIsNotNull(item, "item");
                    item.setWebAppNew(true);
                }
            }
        }
        updateData(list);
        if (getState() != getGET_LIST_REFRESH()) {
            return;
        }
        String str2 = this.businessType;
        if (str2 != null) {
            DBUtil.saveAdPage(list, str2);
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("businessType");
            throw null;
        }
    }
}
