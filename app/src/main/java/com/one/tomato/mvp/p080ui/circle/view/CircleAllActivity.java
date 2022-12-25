package com.one.tomato.mvp.p080ui.circle.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.R$id;
import com.one.tomato.entity.CircleCategory;
import com.one.tomato.entity.CircleDetail;
import com.one.tomato.entity.CircleList;
import com.one.tomato.entity.p079db.CircleAllBean;
import com.one.tomato.mvp.base.view.MvpBaseActivity;
import com.one.tomato.mvp.p080ui.circle.adapter.CircleAllAdater;
import com.one.tomato.mvp.p080ui.circle.impl.ICircleAllContact$ICircleAllView;
import com.one.tomato.mvp.p080ui.circle.impl.ICircleAllContact$ItemClick;
import com.one.tomato.mvp.p080ui.circle.presenter.CircleAllPresenter;
import com.one.tomato.thirdpart.recyclerview.BaseLinearLayoutManager;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.ToastUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CircleAllActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.circle.view.CircleAllActivity */
/* loaded from: classes3.dex */
public final class CircleAllActivity extends MvpBaseActivity<ICircleAllContact$ICircleAllView, CircleAllPresenter> implements ICircleAllContact$ICircleAllView, ICircleAllContact$ItemClick {
    public static final Companion Companion = new Companion(null);
    private HashMap _$_findViewCache;
    private CircleAllAdater allAdapter;
    private BaseRecyclerViewAdapter<CircleCategory> circleCategoryAdapter;
    private int curPosition;
    private boolean isClick;
    private boolean isSelectCircle;

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
        return R.layout.activity_circle_search;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    /* renamed from: createPresenter  reason: avoid collision after fix types in other method */
    public CircleAllPresenter mo6439createPresenter() {
        return new CircleAllPresenter();
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initView() {
        initTitleBar();
        TextView titleTV = getTitleTV();
        if (titleTV != null) {
            titleTV.setText(getString(R.string.circle_all));
        }
        ImageView backImg = getBackImg();
        if (backImg != null) {
            backImg.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.circle.view.CircleAllActivity$initView$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    CircleAllActivity.this.onBackPressed();
                }
            });
        }
        initRecyclerView();
        this.isSelectCircle = getIntent().getBooleanExtra("isSelect", false);
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initData() {
        requestCategoryAll();
    }

    /* compiled from: CircleAllActivity.kt */
    /* renamed from: com.one.tomato.mvp.ui.circle.view.CircleAllActivity$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void startAct(Activity activity, boolean z) {
            Intent intent = new Intent(activity, CircleAllActivity.class);
            if (!z) {
                if (activity == null) {
                    return;
                }
                activity.startActivity(intent);
                return;
            }
            intent.putExtra("isSelect", z);
            if (activity == null) {
                return;
            }
            activity.startActivityForResult(intent, 20);
        }
    }

    private final void initRecyclerView() {
        ((RecyclerView) _$_findCachedViewById(R$id.recyclerView)).setHasFixedSize(true);
        BaseLinearLayoutManager baseLinearLayoutManager = new BaseLinearLayoutManager(this, 1, false);
        RecyclerView recyclerView = (RecyclerView) _$_findCachedViewById(R$id.recyclerView);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(baseLinearLayoutManager);
        }
        final RecyclerView recyclerView2 = (RecyclerView) _$_findCachedViewById(R$id.recyclerView);
        this.circleCategoryAdapter = new BaseRecyclerViewAdapter<CircleCategory>(this, R.layout.item_category, recyclerView2) { // from class: com.one.tomato.mvp.ui.circle.view.CircleAllActivity$initRecyclerView$1
            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
            public void onEmptyRefresh(int i) {
            }

            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
            public void onLoadMore() {
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder holder, CircleCategory itemData) {
                BaseRecyclerViewAdapter baseRecyclerViewAdapter;
                int i;
                List<T> data;
                Intrinsics.checkParameterIsNotNull(holder, "holder");
                Intrinsics.checkParameterIsNotNull(itemData, "itemData");
                super.convert(holder, (BaseViewHolder) itemData);
                baseRecyclerViewAdapter = CircleAllActivity.this.circleCategoryAdapter;
                Integer valueOf = (baseRecyclerViewAdapter == null || (data = baseRecyclerViewAdapter.getData()) == 0) ? null : Integer.valueOf(data.indexOf(itemData));
                View view = holder.getView(R.id.tv_name);
                if (view == null) {
                    throw new TypeCastException("null cannot be cast to non-null type android.widget.TextView");
                }
                TextView textView = (TextView) view;
                textView.setText(itemData.getName());
                i = CircleAllActivity.this.curPosition;
                if (valueOf != null && i == valueOf.intValue()) {
                    Context mContext = this.mContext;
                    Intrinsics.checkExpressionValueIsNotNull(mContext, "mContext");
                    textView.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
                    Context mContext2 = this.mContext;
                    Intrinsics.checkExpressionValueIsNotNull(mContext2, "mContext");
                    textView.setTextSize(0, mContext2.getResources().getDimensionPixelSize(R.dimen.text_title));
                    Context mContext3 = this.mContext;
                    Intrinsics.checkExpressionValueIsNotNull(mContext3, "mContext");
                    textView.setTextColor(mContext3.getResources().getColor(R.color.colorAccent));
                    return;
                }
                Context mContext4 = this.mContext;
                Intrinsics.checkExpressionValueIsNotNull(mContext4, "mContext");
                textView.setBackgroundColor(mContext4.getResources().getColor(R.color.color_FFF7F7F7));
                Context mContext5 = this.mContext;
                Intrinsics.checkExpressionValueIsNotNull(mContext5, "mContext");
                textView.setTextSize(0, mContext5.getResources().getDimensionPixelSize(R.dimen.text_content));
                Context mContext6 = this.mContext;
                Intrinsics.checkExpressionValueIsNotNull(mContext6, "mContext");
                textView.setTextColor(mContext6.getResources().getColor(R.color.text_light));
            }

            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
            public void onRecyclerItemClick(BaseQuickAdapter<?, ?> adapter, View view, int i) {
                BaseRecyclerViewAdapter baseRecyclerViewAdapter;
                CircleCategory circleCategory;
                Intrinsics.checkParameterIsNotNull(adapter, "adapter");
                Intrinsics.checkParameterIsNotNull(view, "view");
                super.onRecyclerItemClick(adapter, view, i);
                CircleAllActivity.this.curPosition = i;
                notifyDataSetChanged();
                CircleAllActivity.this.isClick = true;
                CircleAllActivity circleAllActivity = CircleAllActivity.this;
                baseRecyclerViewAdapter = circleAllActivity.circleCategoryAdapter;
                circleAllActivity.scorllCircleAll((baseRecyclerViewAdapter == null || (circleCategory = (CircleCategory) baseRecyclerViewAdapter.getItem(i)) == null) ? null : Integer.valueOf(circleCategory.getId()));
            }
        };
        BaseRecyclerViewAdapter<CircleCategory> baseRecyclerViewAdapter = this.circleCategoryAdapter;
        if (baseRecyclerViewAdapter != null) {
            baseRecyclerViewAdapter.setEnableLoadMore(false);
        }
        RecyclerView recyclerView3 = (RecyclerView) _$_findCachedViewById(R$id.recyclerView);
        if (recyclerView3 != null) {
            recyclerView3.setAdapter(this.circleCategoryAdapter);
        }
        RecyclerView recyclerView4 = (RecyclerView) _$_findCachedViewById(R$id.circle_recycler);
        if (recyclerView4 != null) {
            recyclerView4.setLayoutManager(new LinearLayoutManager(getMContext(), 1, false));
        }
        Context mContext = getMContext();
        if (mContext != null) {
            RecyclerView circle_recycler = (RecyclerView) _$_findCachedViewById(R$id.circle_recycler);
            Intrinsics.checkExpressionValueIsNotNull(circle_recycler, "circle_recycler");
            this.allAdapter = new CircleAllAdater(mContext, circle_recycler, this);
            RecyclerView recyclerView5 = (RecyclerView) _$_findCachedViewById(R$id.circle_recycler);
            if (recyclerView5 != null) {
                recyclerView5.setAdapter(this.allAdapter);
            }
            CircleAllAdater circleAllAdater = this.allAdapter;
            if (circleAllAdater != null) {
                circleAllAdater.setEnableLoadMore(false);
            }
            CircleAllAdater circleAllAdater2 = this.allAdapter;
            if (circleAllAdater2 != null) {
                circleAllAdater2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.one.tomato.mvp.ui.circle.view.CircleAllActivity$initRecyclerView$2
                    @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
                    public final void onItemClick(BaseQuickAdapter<Object, BaseViewHolder> baseQuickAdapter, View view, int i) {
                        boolean z;
                        Context mContext2;
                        CircleDetail circleDetail = new CircleDetail();
                        CircleList circleList = new CircleList();
                        Object item = baseQuickAdapter != null ? baseQuickAdapter.getItem(i) : null;
                        if (item instanceof CircleAllBean) {
                            CircleAllBean circleAllBean = (CircleAllBean) item;
                            circleDetail.setLogo(circleAllBean.getLogo());
                            circleList.setLogo(circleAllBean.getLogo());
                            circleDetail.setBrief(circleAllBean.getBrief());
                            circleList.setBrief(circleAllBean.getBrief());
                            circleDetail.setCategoryName(circleAllBean.getCategoryName());
                            circleList.setFollowFlag(circleAllBean.getFollowFlag());
                            circleDetail.setFollowCount((int) circleAllBean.getFollowCount());
                            circleDetail.setFollowFlag(circleAllBean.getFollowFlag());
                            circleDetail.setId(circleAllBean.getGroupId());
                            circleDetail.setName(circleAllBean.getCategoryName());
                            circleDetail.setOfficial(circleAllBean.getOfficial());
                            circleList.setId(circleAllBean.getGroupId());
                            circleList.setName(circleAllBean.getName());
                            circleList.setOfficial(circleAllBean.getOfficial());
                        }
                        z = CircleAllActivity.this.isSelectCircle;
                        if (!z) {
                            mContext2 = CircleAllActivity.this.getMContext();
                            CircleSingleActivity.startActivity(mContext2, circleDetail);
                            return;
                        }
                        CircleAllActivity.this.getIntent().putExtra("select_data", circleList);
                        CircleAllActivity circleAllActivity = CircleAllActivity.this;
                        circleAllActivity.setResult(-1, circleAllActivity.getIntent());
                        CircleAllActivity.this.finish();
                    }
                });
            }
            RecyclerView recyclerView6 = (RecyclerView) _$_findCachedViewById(R$id.circle_recycler);
            if (recyclerView6 != null) {
                recyclerView6.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.one.tomato.mvp.ui.circle.view.CircleAllActivity$initRecyclerView$3
                    @Override // android.support.p005v7.widget.RecyclerView.OnScrollListener
                    public void onScrolled(RecyclerView recyclerView7, int i, int i2) {
                        CircleAllPresenter mPresenter;
                        boolean z;
                        CircleAllAdater circleAllAdater3;
                        CircleAllPresenter mPresenter2;
                        CircleAllPresenter mPresenter3;
                        CircleAllPresenter mPresenter4;
                        Intrinsics.checkParameterIsNotNull(recyclerView7, "recyclerView");
                        super.onScrolled(recyclerView7, i, i2);
                        mPresenter = CircleAllActivity.this.getMPresenter();
                        boolean z2 = false;
                        CircleAllBean circleAllBean = null;
                        if (mPresenter != null && mPresenter.getMShouldScroll()) {
                            mPresenter2 = CircleAllActivity.this.getMPresenter();
                            Integer valueOf = mPresenter2 != null ? Integer.valueOf(mPresenter2.getIndex()) : null;
                            if (valueOf != null) {
                                mPresenter3 = CircleAllActivity.this.getMPresenter();
                                if (mPresenter3 != null) {
                                    mPresenter3.setMShouldScroll(false);
                                }
                                mPresenter4 = CircleAllActivity.this.getMPresenter();
                                if (mPresenter4 != null) {
                                    mPresenter4.smoothMoveToPosition(recyclerView7, valueOf.intValue());
                                }
                            }
                        }
                        z = CircleAllActivity.this.isClick;
                        if (z) {
                            return;
                        }
                        RecyclerView.LayoutManager layoutManager = recyclerView7.getLayoutManager();
                        if (layoutManager == null) {
                            throw new TypeCastException("null cannot be cast to non-null type android.support.v7.widget.LinearLayoutManager");
                        }
                        int findFirstCompletelyVisibleItemPosition = ((LinearLayoutManager) layoutManager).findFirstCompletelyVisibleItemPosition();
                        circleAllAdater3 = CircleAllActivity.this.allAdapter;
                        if (circleAllAdater3 != null) {
                            circleAllBean = circleAllAdater3.getItem(findFirstCompletelyVisibleItemPosition);
                        }
                        if (circleAllBean == null) {
                            return;
                        }
                        CircleAllActivity circleAllActivity = CircleAllActivity.this;
                        if (circleAllBean.getFollowFlag() == 1) {
                            z2 = true;
                        }
                        circleAllActivity.scorllCircleType(z2, circleAllBean.getCategoryId());
                    }

                    @Override // android.support.p005v7.widget.RecyclerView.OnScrollListener
                    public void onScrollStateChanged(RecyclerView recyclerView7, int i) {
                        boolean z;
                        Intrinsics.checkParameterIsNotNull(recyclerView7, "recyclerView");
                        super.onScrollStateChanged(recyclerView7, i);
                        if (i == 0) {
                            z = CircleAllActivity.this.isClick;
                            if (!z) {
                                return;
                            }
                            CircleAllActivity.this.isClick = false;
                        }
                    }
                });
            }
            RecyclerView recyclerView7 = (RecyclerView) _$_findCachedViewById(R$id.recyclerView);
            if (recyclerView7 == null) {
                return;
            }
            recyclerView7.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.one.tomato.mvp.ui.circle.view.CircleAllActivity$initRecyclerView$4
                @Override // android.support.p005v7.widget.RecyclerView.OnScrollListener
                public void onScrolled(RecyclerView recyclerView8, int i, int i2) {
                    CircleAllPresenter mPresenter;
                    CircleAllPresenter mPresenter2;
                    CircleAllPresenter mPresenter3;
                    CircleAllPresenter mPresenter4;
                    Intrinsics.checkParameterIsNotNull(recyclerView8, "recyclerView");
                    super.onScrolled(recyclerView8, i, i2);
                    mPresenter = CircleAllActivity.this.getMPresenter();
                    if (mPresenter == null || !mPresenter.getMShouldScroll()) {
                        return;
                    }
                    mPresenter2 = CircleAllActivity.this.getMPresenter();
                    Integer valueOf = mPresenter2 != null ? Integer.valueOf(mPresenter2.getIndex()) : null;
                    if (valueOf == null) {
                        return;
                    }
                    mPresenter3 = CircleAllActivity.this.getMPresenter();
                    if (mPresenter3 != null) {
                        mPresenter3.setMShouldScroll(false);
                    }
                    mPresenter4 = CircleAllActivity.this.getMPresenter();
                    if (mPresenter4 == null) {
                        return;
                    }
                    mPresenter4.smoothMoveToPosition(recyclerView8, valueOf.intValue());
                }
            });
            return;
        }
        Intrinsics.throwNpe();
        throw null;
    }

    public final void scorllCircleType(boolean z, int i) {
        int i2 = 0;
        if (z) {
            this.curPosition = 0;
            BaseRecyclerViewAdapter<CircleCategory> baseRecyclerViewAdapter = this.circleCategoryAdapter;
            if (baseRecyclerViewAdapter != null) {
                baseRecyclerViewAdapter.notifyDataSetChanged();
            }
            RecyclerView recyclerView = (RecyclerView) _$_findCachedViewById(R$id.recyclerView);
            if (recyclerView == null) {
                return;
            }
            recyclerView.smoothScrollToPosition(0);
            return;
        }
        BaseRecyclerViewAdapter<CircleCategory> baseRecyclerViewAdapter2 = this.circleCategoryAdapter;
        List<CircleCategory> data = baseRecyclerViewAdapter2 != null ? baseRecyclerViewAdapter2.getData() : null;
        if (data == null) {
            return;
        }
        for (Object obj : data) {
            int i3 = i2 + 1;
            if (i2 >= 0) {
                CircleCategory circleCategory = (CircleCategory) obj;
                Intrinsics.checkExpressionValueIsNotNull(circleCategory, "circleCategory");
                if (circleCategory.getId() == i) {
                    this.curPosition = i2;
                    BaseRecyclerViewAdapter<CircleCategory> baseRecyclerViewAdapter3 = this.circleCategoryAdapter;
                    if (baseRecyclerViewAdapter3 != null) {
                        baseRecyclerViewAdapter3.notifyDataSetChanged();
                    }
                    RecyclerView recyclerView2 = (RecyclerView) _$_findCachedViewById(R$id.recyclerView);
                    if (recyclerView2 == null) {
                        return;
                    }
                    recyclerView2.smoothScrollToPosition(this.curPosition);
                    return;
                }
                i2 = i3;
            } else {
                CollectionsKt.throwIndexOverflow();
                throw null;
            }
        }
    }

    public final void scorllCircleAll(Integer num) {
        if (num == null) {
            return;
        }
        CircleAllAdater circleAllAdater = this.allAdapter;
        List<CircleAllBean> data = circleAllAdater != null ? circleAllAdater.getData() : null;
        if (data == null) {
            return;
        }
        int i = 0;
        for (Object obj : data) {
            int i2 = i + 1;
            if (i >= 0) {
                CircleAllBean circleAllBean = (CircleAllBean) obj;
                if (num != null && num.intValue() == -10) {
                    Intrinsics.checkExpressionValueIsNotNull(circleAllBean, "circleAllBean");
                    if (circleAllBean.getFollowFlag() == 1) {
                        CircleAllPresenter mPresenter = getMPresenter();
                        if (mPresenter == null) {
                            return;
                        }
                        RecyclerView circle_recycler = (RecyclerView) _$_findCachedViewById(R$id.circle_recycler);
                        Intrinsics.checkExpressionValueIsNotNull(circle_recycler, "circle_recycler");
                        mPresenter.smoothMoveToPosition(circle_recycler, i);
                        return;
                    }
                }
                Intrinsics.checkExpressionValueIsNotNull(circleAllBean, "circleAllBean");
                int categoryId = circleAllBean.getCategoryId();
                if (num != null && num.intValue() == categoryId) {
                    CircleAllPresenter mPresenter2 = getMPresenter();
                    if (mPresenter2 == null) {
                        return;
                    }
                    RecyclerView circle_recycler2 = (RecyclerView) _$_findCachedViewById(R$id.circle_recycler);
                    Intrinsics.checkExpressionValueIsNotNull(circle_recycler2, "circle_recycler");
                    mPresenter2.smoothMoveToPosition(circle_recycler2, i);
                    return;
                }
                i = i2;
            } else {
                CollectionsKt.throwIndexOverflow();
                throw null;
            }
        }
    }

    public final void requestCategoryAll() {
        CircleAllPresenter mPresenter = getMPresenter();
        if (mPresenter != null) {
            mPresenter.requestCategoryCircleAll(1, 100);
        }
    }

    @Override // com.one.tomato.mvp.p080ui.circle.impl.ICircleAllContact$ICircleAllView
    public void handlerCircleFllowSuccess(int i) {
        Integer num;
        CircleAllAdater circleAllAdater = this.allAdapter;
        List<CircleAllBean> list = null;
        CircleAllBean item = circleAllAdater != null ? circleAllAdater.getItem(i) : null;
        Integer valueOf = item != null ? Integer.valueOf(item.getFollowFlag()) : null;
        if (valueOf != null) {
            valueOf.intValue();
            if (valueOf != null && valueOf.intValue() == 1) {
                num = 0;
                ToastUtil.showCenterToast((int) R.string.common_cancel_focus_success);
            } else {
                num = 1;
                ToastUtil.showCenterToast((int) R.string.common_focus_success);
            }
            if (item != null) {
                item.setFollowFlag(num.intValue());
            }
        }
        CircleAllAdater circleAllAdater2 = this.allAdapter;
        if (circleAllAdater2 != null) {
            if (item == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            circleAllAdater2.setData(i, item);
        }
        ArrayList<CircleAllBean> arrayList = new ArrayList<>();
        CircleAllAdater circleAllAdater3 = this.allAdapter;
        if (circleAllAdater3 != null) {
            list = circleAllAdater3.getData();
        }
        if (list != null) {
            arrayList.addAll(list);
            CircleAllPresenter mPresenter = getMPresenter();
            if (mPresenter == null) {
                return;
            }
            mPresenter.circleSort(arrayList);
        }
    }

    @Override // com.one.tomato.mvp.p080ui.circle.impl.ICircleAllContact$ICircleAllView
    public void handlerCategoryCircleAllSucess(ArrayList<CircleCategory> arrayList) {
        if (arrayList instanceof ArrayList) {
            BaseRecyclerViewAdapter<CircleCategory> baseRecyclerViewAdapter = this.circleCategoryAdapter;
            if (baseRecyclerViewAdapter != null) {
                baseRecyclerViewAdapter.addData(arrayList);
            }
            CircleAllAdater circleAllAdater = this.allAdapter;
            if (circleAllAdater != null) {
                circleAllAdater.setEnableLoadMore(false);
            }
            CircleAllPresenter mPresenter = getMPresenter();
            if (mPresenter == null) {
                return;
            }
            mPresenter.requestCircleAll(DBUtil.getMemberId(), 1);
        }
    }

    @Override // com.one.tomato.mvp.p080ui.circle.impl.ICircleAllContact$ItemClick
    public void clickItemFollow(int i, int i2) {
        CircleAllAdater circleAllAdater = this.allAdapter;
        CircleAllBean item = circleAllAdater != null ? circleAllAdater.getItem(i2) : null;
        String str = (item == null || item.getFollowFlag() != 0) ? "/app/follow/delete" : "/app/follow/save";
        CircleAllPresenter mPresenter = getMPresenter();
        if (mPresenter != null) {
            mPresenter.requestCircleFllow(i, i2, str);
        }
    }

    @Override // com.one.tomato.mvp.p080ui.circle.impl.ICircleAllContact$ICircleAllView
    public void handlerCategoryCircleAllError() {
        LinearLayout linearLayout = (LinearLayout) _$_findCachedViewById(R$id.ll_empty);
        if (linearLayout != null) {
            linearLayout.setVisibility(0);
        }
        LinearLayout linearLayout2 = (LinearLayout) _$_findCachedViewById(R$id.ll_content);
        if (linearLayout2 != null) {
            linearLayout2.setVisibility(8);
        }
    }

    @Override // com.one.tomato.mvp.p080ui.circle.impl.ICircleAllContact$ICircleAllView
    public void handlerCircleAll(ArrayList<CircleAllBean> arrayList) {
        if (arrayList != null) {
            if (arrayList.size() > 0) {
                CircleAllBean circleAllBean = arrayList.get(0);
                Intrinsics.checkExpressionValueIsNotNull(circleAllBean, "circleBean[0]");
                if (circleAllBean.getFollowFlag() == 1) {
                    this.curPosition = 0;
                    BaseRecyclerViewAdapter<CircleCategory> baseRecyclerViewAdapter = this.circleCategoryAdapter;
                    if (baseRecyclerViewAdapter != null) {
                        baseRecyclerViewAdapter.notifyDataSetChanged();
                    }
                }
            }
            CircleAllAdater circleAllAdater = this.allAdapter;
            if (circleAllAdater != null) {
                circleAllAdater.setNewData(arrayList);
            }
            CircleAllAdater circleAllAdater2 = this.allAdapter;
            if (circleAllAdater2 == null) {
                return;
            }
            circleAllAdater2.setEnableLoadMore(false);
        }
    }
}
