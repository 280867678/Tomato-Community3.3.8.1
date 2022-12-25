package com.one.tomato.mvp.p080ui.circle.view;

import android.content.Context;
import android.support.p002v4.content.ContextCompat;
import android.support.p002v4.widget.SwipeRefreshLayout;
import android.support.p005v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.R$id;
import com.one.tomato.entity.C2516Ad;
import com.one.tomato.entity.ImageBean;
import com.one.tomato.entity.p079db.AdPage;
import com.one.tomato.entity.p079db.CircleAllBean;
import com.one.tomato.entity.p079db.CircleDiscoverListBean;
import com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment;
import com.one.tomato.mvp.p080ui.circle.adapter.CircleTabAdapter;
import com.one.tomato.mvp.p080ui.circle.impl.ICircleTabContract$ICircleView;
import com.one.tomato.mvp.p080ui.circle.impl.ICircleTabContract$ItemClick;
import com.one.tomato.mvp.p080ui.circle.presenter.CircleTabPresenter;
import com.one.tomato.mvp.p080ui.circle.utils.CircleAllUtils;
import com.one.tomato.thirdpart.roundimage.RoundedImageView;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.ToastUtil;
import com.one.tomato.utils.image.ImageLoaderUtil;
import com.one.tomato.utils.p087ad.AdUtil;
import com.one.tomato.widget.mzbanner.MZBannerView;
import java.util.ArrayList;
import java.util.HashMap;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CircleTabFragment.kt */
/* renamed from: com.one.tomato.mvp.ui.circle.view.CircleTabFragment */
/* loaded from: classes3.dex */
public final class CircleTabFragment extends MvpBaseRecyclerViewFragment<ICircleTabContract$ICircleView, CircleTabPresenter, CircleTabAdapter, CircleDiscoverListBean> implements ICircleTabContract$ICircleView, ICircleTabContract$ItemClick {
    private HashMap _$_findViewCache;
    private AdUtil adUtil;
    private MZBannerView<AdPage> banner;

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
        return R.layout.circle_tab_fragment;
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

    @Override // com.one.tomato.mvp.base.view.IBaseView
    public void onError(String str) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment
    public void refresh() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.one.tomato.mvp.base.view.MvpBaseRecyclerViewFragment
    /* renamed from: createAdapter */
    public CircleTabAdapter mo6434createAdapter() {
        Context mContext = getMContext();
        if (mContext == null) {
            Intrinsics.throwNpe();
            throw null;
        }
        RecyclerView recyclerView = getRecyclerView();
        if (recyclerView != null) {
            return new CircleTabAdapter(mContext, recyclerView, this);
        }
        Intrinsics.throwNpe();
        throw null;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    /* renamed from: createPresenter  reason: collision with other method in class */
    public CircleTabPresenter mo6441createPresenter() {
        return new CircleTabPresenter();
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void inintData() {
        SwipeRefreshLayout swipeRefreshLayout;
        TextView textView = (TextView) _$_findCachedViewById(R$id.text_click_all);
        if (textView != null) {
            textView.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.circle.view.CircleTabFragment$inintData$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    CircleAllActivity.Companion.startAct(CircleTabFragment.this.getActivity(), false);
                }
            });
        }
        Context mContext = getMContext();
        if (mContext != null && (swipeRefreshLayout = (SwipeRefreshLayout) _$_findCachedViewById(R$id.swipeRefresh)) != null) {
            swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(mContext, R.color.colorAccent), ContextCompat.getColor(mContext, R.color.colorAccent_80), ContextCompat.getColor(mContext, R.color.color_ff933a), ContextCompat.getColor(mContext, R.color.color_90ddf4), ContextCompat.getColor(mContext, R.color.color_FF2877));
        }
        SwipeRefreshLayout swipeRefreshLayout2 = (SwipeRefreshLayout) _$_findCachedViewById(R$id.swipeRefresh);
        if (swipeRefreshLayout2 != null) {
            swipeRefreshLayout2.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() { // from class: com.one.tomato.mvp.ui.circle.view.CircleTabFragment$inintData$3
                @Override // android.support.p002v4.widget.SwipeRefreshLayout.OnRefreshListener
                public final void onRefresh() {
                    CircleTabFragment.this.refreshData();
                }
            });
        }
        setUserVisibleHint(true);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseFragment
    public void onLazyLoad() {
        super.onLazyLoad();
        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) _$_findCachedViewById(R$id.swipeRefresh);
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(true);
        }
        refreshData();
    }

    public final void refreshData() {
        initBanner();
        CircleTabPresenter circleTabPresenter = (CircleTabPresenter) getMPresenter();
        if (circleTabPresenter != null) {
            circleTabPresenter.requestCircleDiscover();
        }
        ArrayList<CircleAllBean> pullHomeRecommentCircle = CircleAllUtils.INSTANCE.pullHomeRecommentCircle(3);
        if (!pullHomeRecommentCircle.isEmpty()) {
            int i = 0;
            for (Object obj : pullHomeRecommentCircle) {
                int i2 = i + 1;
                if (i < 0) {
                    CollectionsKt.throwIndexOverflow();
                    throw null;
                }
                CircleAllBean circleAllBean = (CircleAllBean) obj;
                if (i == 0) {
                    ImageLoaderUtil.loadHeadImage(getMContext(), (RoundedImageView) _$_findCachedViewById(R$id.image1), new ImageBean(circleAllBean.getLogo()));
                }
                if (i == 1) {
                    ImageLoaderUtil.loadHeadImage(getMContext(), (RoundedImageView) _$_findCachedViewById(R$id.image2), new ImageBean(circleAllBean.getLogo()));
                }
                if (i == 2) {
                    ImageLoaderUtil.loadHeadImage(getMContext(), (RoundedImageView) _$_findCachedViewById(R$id.image3), new ImageBean(circleAllBean.getLogo()));
                }
                i = i2;
            }
        }
    }

    public final void initBanner() {
        View layoutView = getLayoutView();
        this.banner = layoutView != null ? (MZBannerView) layoutView.findViewById(R.id.mzbanner) : null;
        this.adUtil = new AdUtil(getActivity());
        final ArrayList<AdPage> adPage = DBUtil.getAdPage(C2516Ad.TYPE_CIRCLE);
        if (!(adPage instanceof ArrayList) || adPage.isEmpty()) {
            return;
        }
        MZBannerView<AdPage> mZBannerView = this.banner;
        if (mZBannerView != null) {
            mZBannerView.setVisibility(0);
        }
        MZBannerView<AdPage> mZBannerView2 = this.banner;
        if (mZBannerView2 != null) {
            mZBannerView2.setBannerPageClickListener(new MZBannerView.BannerPageClickListener() { // from class: com.one.tomato.mvp.ui.circle.view.CircleTabFragment$initBanner$1
                @Override // com.one.tomato.widget.mzbanner.MZBannerView.BannerPageClickListener
                public final void onPageClick(View view, int i) {
                    AdUtil adUtil;
                    Object obj = adPage.get(i);
                    Intrinsics.checkExpressionValueIsNotNull(obj, "adPage[position]");
                    AdPage adPage2 = (AdPage) obj;
                    adUtil = CircleTabFragment.this.adUtil;
                    if (adUtil != null) {
                        adUtil.clickAd(adPage2.getAdId(), adPage2.getAdType(), adPage2.getAdJumpModule(), adPage2.getAdJumpDetail(), adPage2.getOpenType(), adPage2.getAdLink());
                    }
                }
            });
        }
        MZBannerView<AdPage> mZBannerView3 = this.banner;
        if (mZBannerView3 == null) {
            return;
        }
        mZBannerView3.setPages(adPage, CircleTabFragment$initBanner$2.INSTANCE);
    }

    @Override // com.one.tomato.mvp.p080ui.circle.impl.ICircleTabContract$ICircleView
    public void handlerCircleDiscover(ArrayList<CircleDiscoverListBean> arrayList) {
        updateData(arrayList);
        CircleTabAdapter adapter = getAdapter();
        if (adapter != null) {
            adapter.setEnableLoadMore(false);
        }
        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) _$_findCachedViewById(R$id.swipeRefresh);
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
        }
        if (arrayList != null) {
            if (arrayList == null || arrayList.isEmpty()) {
                return;
            }
            CircleDiscoverListBean circleDiscoverListBean = arrayList.get(0);
            Intrinsics.checkExpressionValueIsNotNull(circleDiscoverListBean, "list[0]");
            if (circleDiscoverListBean.followFlag != 1) {
                TextView textView = (TextView) _$_findCachedViewById(R$id.my_circle_text);
                if (textView == null) {
                    return;
                }
                textView.setText(AppUtil.getString(R.string.circle_recomment_5));
                return;
            }
            TextView textView2 = (TextView) _$_findCachedViewById(R$id.my_circle_text);
            if (textView2 == null) {
                return;
            }
            textView2.setText(AppUtil.getString(R.string.cirlce_my_circle));
        }
    }

    @Override // com.one.tomato.mvp.p080ui.circle.impl.ICircleTabContract$ICircleView
    public void handlerCircleFllowSuccess(int i) {
        CircleTabAdapter adapter;
        Integer num;
        CircleTabAdapter adapter2 = getAdapter();
        CircleDiscoverListBean item = adapter2 != null ? adapter2.getItem(i) : null;
        Integer valueOf = item != null ? Integer.valueOf(item.followFlag) : null;
        if (valueOf != null) {
            valueOf.intValue();
            if (valueOf != null && valueOf.intValue() == 1) {
                num = 0;
                ToastUtil.showCenterToast((int) R.string.common_cancel_focus_success);
            } else {
                num = 1;
                ToastUtil.showCenterToast((int) R.string.common_focus_success);
            }
            if (item == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            item.followFlag = num.intValue();
        }
        if (item == null || (adapter = getAdapter()) == null) {
            return;
        }
        adapter.setData(i, item);
    }

    @Override // com.one.tomato.mvp.p080ui.circle.impl.ICircleTabContract$ItemClick
    public void clickItemFollow(int i, int i2) {
        CircleTabAdapter adapter = getAdapter();
        CircleDiscoverListBean item = adapter != null ? adapter.getItem(i2) : null;
        String str = (item == null || item.followFlag != 0) ? "/app/follow/delete" : "/app/follow/save";
        CircleTabPresenter circleTabPresenter = (CircleTabPresenter) getMPresenter();
        if (circleTabPresenter != null) {
            circleTabPresenter.requestCircleFllow(i, i2, str);
        }
    }
}
