package com.one.tomato.mvp.p080ui.game.view;

import android.support.design.widget.CoordinatorLayout;
import android.support.p002v4.app.Fragment;
import android.support.p002v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import com.broccoli.p150bh.R;
import com.one.tomato.R$id;
import com.one.tomato.entity.p079db.GameTypeData;
import com.one.tomato.entity.p079db.SubGamesBean;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.base.view.MvpBaseFragment;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.mvp.p080ui.game.view.GameSinglePageFragment;
import com.one.tomato.mvp.p080ui.papa.adapter.PaPaTabAdapter;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.GameUtils;
import com.one.tomato.utils.PagerSlidingTabUtil;
import com.one.tomato.utils.RxUtils;
import com.one.tomato.widget.NoHorScrollViewPager;
import com.one.tomato.widget.PagerSlidingTabStrip;
import com.one.tomato.widget.ViewPagerBottomSheetBehavior;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: GameBottomSheetFragment.kt */
/* renamed from: com.one.tomato.mvp.ui.game.view.GameBottomSheetFragment */
/* loaded from: classes3.dex */
public final class GameBottomSheetFragment extends MvpBaseFragment<IBaseView, MvpBasePresenter<IBaseView>> {
    private HashMap _$_findViewCache;
    private PaPaTabAdapter adapter;
    private ViewPagerBottomSheetBehavior<View> behavior;
    private List<Fragment> tabFragment = new ArrayList();
    private final List<String> tabString = new ArrayList();
    private int peekHeight = 750;

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
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
        return R.layout.game_bottom_dialog_fragment;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    /* renamed from: createPresenter */
    public MvpBasePresenter<IBaseView> mo6441createPresenter() {
        return null;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public /* synthetic */ void onDestroyView() {
        super.onDestroyView();
        _$_clearFindViewByIdCache();
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void initView() {
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) _$_findCachedViewById(R$id.coordinator);
        if (coordinatorLayout != null) {
            coordinatorLayout.setVisibility(8);
        }
        View view = getView();
        final View findViewById = view != null ? view.findViewById(R.id.design_bottom_sheet) : null;
        if (findViewById != null) {
            findViewById.post(new Runnable() { // from class: com.one.tomato.mvp.ui.game.view.GameBottomSheetFragment$initView$$inlined$let$lambda$1
                @Override // java.lang.Runnable
                public final void run() {
                    ViewPagerBottomSheetBehavior viewPagerBottomSheetBehavior;
                    ViewPagerBottomSheetBehavior viewPagerBottomSheetBehavior2;
                    ViewPagerBottomSheetBehavior viewPagerBottomSheetBehavior3;
                    int i;
                    this.behavior = ViewPagerBottomSheetBehavior.from(findViewById);
                    viewPagerBottomSheetBehavior = this.behavior;
                    if (viewPagerBottomSheetBehavior != null) {
                        i = this.peekHeight;
                        viewPagerBottomSheetBehavior.setPeekHeight(i);
                    }
                    viewPagerBottomSheetBehavior2 = this.behavior;
                    if (viewPagerBottomSheetBehavior2 != null) {
                        viewPagerBottomSheetBehavior2.setState(4);
                    }
                    viewPagerBottomSheetBehavior3 = this.behavior;
                    if (viewPagerBottomSheetBehavior3 != null) {
                        viewPagerBottomSheetBehavior3.setBottomSheetCallback(new ViewPagerBottomSheetBehavior.BottomSheetCallback() { // from class: com.one.tomato.mvp.ui.game.view.GameBottomSheetFragment$initView$$inlined$let$lambda$1.1
                            @Override // com.one.tomato.widget.ViewPagerBottomSheetBehavior.BottomSheetCallback
                            public void onSlide(View p0, float f) {
                                Intrinsics.checkParameterIsNotNull(p0, "p0");
                            }

                            @Override // com.one.tomato.widget.ViewPagerBottomSheetBehavior.BottomSheetCallback
                            public void onStateChanged(View p0, int i2) {
                                Intrinsics.checkParameterIsNotNull(p0, "p0");
                                if (i2 == 3) {
                                    ImageView imageView = (ImageView) this._$_findCachedViewById(R$id.image_fold);
                                    if (imageView != null) {
                                        imageView.setSelected(true);
                                    }
                                    ImageView imageView2 = (ImageView) this._$_findCachedViewById(R$id.image_fold);
                                    if (imageView2 == null) {
                                        return;
                                    }
                                    imageView2.setImageResource(R.drawable.game_fold_down);
                                } else if (i2 != 4) {
                                } else {
                                    ImageView imageView3 = (ImageView) this._$_findCachedViewById(R$id.image_fold);
                                    if (imageView3 != null) {
                                        imageView3.setSelected(false);
                                    }
                                    ImageView imageView4 = (ImageView) this._$_findCachedViewById(R$id.image_fold);
                                    if (imageView4 == null) {
                                        return;
                                    }
                                    imageView4.setImageResource(R.drawable.game_fold_above);
                                }
                            }
                        });
                    }
                }
            });
        }
    }

    @Override // com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public void onStart() {
        super.onStart();
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void inintData() {
        requestList();
    }

    private final void requestList() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("memberId", Integer.valueOf(DBUtil.getMemberId()));
        requestGameTypeList(hashMap);
    }

    private final void requestGameTypeList(HashMap<String, Object> hashMap) {
        ApiImplService.Companion.getApiImplService().requestGameCenterList(hashMap).compose(RxUtils.schedulersTransformer()).doOnSubscribe(GameBottomSheetFragment$requestGameTypeList$1.INSTANCE).subscribe(new ApiDisposableObserver<ArrayList<GameTypeData>>() { // from class: com.one.tomato.mvp.ui.game.view.GameBottomSheetFragment$requestGameTypeList$2
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(ArrayList<GameTypeData> t) {
                Intrinsics.checkParameterIsNotNull(t, "t");
                GameBottomSheetFragment.this.handlerGameType(t);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void handlerGameType(ArrayList<GameTypeData> arrayList) {
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) _$_findCachedViewById(R$id.coordinator);
        if (coordinatorLayout != null) {
            coordinatorLayout.setVisibility(0);
        }
        ArrayList<GameTypeData> arrayList2 = new ArrayList<>();
        if (!(arrayList == null || arrayList.isEmpty())) {
            arrayList2.addAll(arrayList);
        }
        ArrayList<SubGamesBean> gameBean = GameUtils.INSTANCE.getGameBean();
        if (gameBean.size() > 0) {
            GameTypeData gameTypeData = new GameTypeData();
            gameTypeData.setGameId(-10);
            gameTypeData.setAdName(AppUtil.getString(R.string.game_recent_play));
            gameTypeData.setAdLogoType("1");
            GameUtils.INSTANCE.deleteGameBean();
            ArrayList<SubGamesBean> arrayList3 = new ArrayList<>();
            for (GameTypeData gameTypeData2 : arrayList2) {
                ArrayList<SubGamesBean> subGames = gameTypeData2.getSubGames();
                if (!(subGames == null || subGames.isEmpty()) && gameTypeData2.getSubGames().size() > 0) {
                    ArrayList<SubGamesBean> subGames2 = gameTypeData2.getSubGames();
                    Intrinsics.checkExpressionValueIsNotNull(subGames2, "it.subGames");
                    for (SubGamesBean subGame : subGames2) {
                        for (SubGamesBean subGamesBean : gameBean) {
                            int gameId = subGamesBean.getGameId();
                            Intrinsics.checkExpressionValueIsNotNull(subGame, "subGame");
                            if (gameId == subGame.getGameId()) {
                                arrayList3.add(subGame);
                                GameUtils.INSTANCE.saveGameBean(subGame);
                            }
                        }
                    }
                }
            }
            gameTypeData.setSubGames(arrayList3);
            arrayList2.add(gameTypeData);
        }
        initTab(arrayList2);
    }

    private final void initTab(ArrayList<GameTypeData> arrayList) {
        if (arrayList == null || arrayList.isEmpty()) {
            return;
        }
        Iterator<GameTypeData> it2 = arrayList.iterator();
        while (it2.hasNext()) {
            GameTypeData it3 = it2.next();
            List<String> list = this.tabString;
            Intrinsics.checkExpressionValueIsNotNull(it3, "it");
            String adName = it3.getAdName();
            Intrinsics.checkExpressionValueIsNotNull(adName, "it.adName");
            list.add(adName);
            List<Fragment> list2 = this.tabFragment;
            GameSinglePageFragment.Companion companion = GameSinglePageFragment.Companion;
            ArrayList<SubGamesBean> subGames = it3.getSubGames();
            Intrinsics.checkExpressionValueIsNotNull(subGames, "it.subGames");
            list2.add(companion.getInstance(subGames));
        }
        initGameTypeAdapter();
    }

    public final void setPeekHeight(int i, int i2) {
        this.peekHeight = i;
    }

    private final void initGameTypeAdapter() {
        this.adapter = new PaPaTabAdapter(getChildFragmentManager(), this.tabFragment, this.tabString);
        NoHorScrollViewPager noHorScrollViewPager = (NoHorScrollViewPager) _$_findCachedViewById(R$id.view_pager);
        if (noHorScrollViewPager != null) {
            noHorScrollViewPager.setAdapter(this.adapter);
        }
        PagerSlidingTabStrip pagerSlidingTabStrip = (PagerSlidingTabStrip) _$_findCachedViewById(R$id.tab_strip);
        if (pagerSlidingTabStrip != null) {
            pagerSlidingTabStrip.setViewPager((NoHorScrollViewPager) _$_findCachedViewById(R$id.view_pager));
        }
        PagerSlidingTabUtil.setGameTabValue(getContext(), (PagerSlidingTabStrip) _$_findCachedViewById(R$id.tab_strip));
        NoHorScrollViewPager view_pager = (NoHorScrollViewPager) _$_findCachedViewById(R$id.view_pager);
        Intrinsics.checkExpressionValueIsNotNull(view_pager, "view_pager");
        view_pager.setOffscreenPageLimit(this.tabFragment.size());
        NoHorScrollViewPager noHorScrollViewPager2 = (NoHorScrollViewPager) _$_findCachedViewById(R$id.view_pager);
        if (noHorScrollViewPager2 != null) {
            noHorScrollViewPager2.setCurrentItem(0);
        }
        NoHorScrollViewPager noHorScrollViewPager3 = (NoHorScrollViewPager) _$_findCachedViewById(R$id.view_pager);
        if (noHorScrollViewPager3 != null) {
            noHorScrollViewPager3.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.one.tomato.mvp.ui.game.view.GameBottomSheetFragment$initGameTypeAdapter$1
                @Override // android.support.p002v4.view.ViewPager.OnPageChangeListener
                public void onPageScrollStateChanged(int i) {
                }

                @Override // android.support.p002v4.view.ViewPager.OnPageChangeListener
                public void onPageScrolled(int i, float f, int i2) {
                }

                @Override // android.support.p002v4.view.ViewPager.OnPageChangeListener
                public void onPageSelected(int i) {
                    ViewPagerBottomSheetBehavior<View> from;
                    View view = GameBottomSheetFragment.this.getView();
                    View findViewById = view != null ? view.findViewById(R.id.design_bottom_sheet) : null;
                    if (findViewById == null || (from = ViewPagerBottomSheetBehavior.from(findViewById)) == null) {
                        return;
                    }
                    from.updateScrollingChild();
                }
            });
        }
        ImageView imageView = (ImageView) _$_findCachedViewById(R$id.image_fold);
        if (imageView != null) {
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.game.view.GameBottomSheetFragment$initGameTypeAdapter$2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ViewPagerBottomSheetBehavior viewPagerBottomSheetBehavior;
                    ViewPagerBottomSheetBehavior viewPagerBottomSheetBehavior2;
                    ImageView imageView2 = (ImageView) GameBottomSheetFragment.this._$_findCachedViewById(R$id.image_fold);
                    if (imageView2 != null && !imageView2.isSelected()) {
                        ImageView imageView3 = (ImageView) GameBottomSheetFragment.this._$_findCachedViewById(R$id.image_fold);
                        if (imageView3 != null) {
                            imageView3.setSelected(true);
                        }
                        viewPagerBottomSheetBehavior2 = GameBottomSheetFragment.this.behavior;
                        if (viewPagerBottomSheetBehavior2 != null) {
                            viewPagerBottomSheetBehavior2.setState(3);
                        }
                        ImageView imageView4 = (ImageView) GameBottomSheetFragment.this._$_findCachedViewById(R$id.image_fold);
                        if (imageView4 == null) {
                            return;
                        }
                        imageView4.setImageResource(R.drawable.game_fold_down);
                        return;
                    }
                    ImageView imageView5 = (ImageView) GameBottomSheetFragment.this._$_findCachedViewById(R$id.image_fold);
                    if (imageView5 != null) {
                        imageView5.setSelected(false);
                    }
                    viewPagerBottomSheetBehavior = GameBottomSheetFragment.this.behavior;
                    if (viewPagerBottomSheetBehavior != null) {
                        viewPagerBottomSheetBehavior.setState(4);
                    }
                    ImageView imageView6 = (ImageView) GameBottomSheetFragment.this._$_findCachedViewById(R$id.image_fold);
                    if (imageView6 == null) {
                        return;
                    }
                    imageView6.setImageResource(R.drawable.game_fold_above);
                }
            });
        }
        ViewPagerBottomSheetBehavior<View> viewPagerBottomSheetBehavior = this.behavior;
        if (viewPagerBottomSheetBehavior != null) {
            if (viewPagerBottomSheetBehavior != null) {
                viewPagerBottomSheetBehavior.setPeekHeight(this.peekHeight);
            }
            ViewPagerBottomSheetBehavior<View> viewPagerBottomSheetBehavior2 = this.behavior;
            if (viewPagerBottomSheetBehavior2 == null) {
                return;
            }
            viewPagerBottomSheetBehavior2.setState(4);
        }
    }
}
