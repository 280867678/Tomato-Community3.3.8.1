package com.one.tomato.mvp.p080ui.level;

import android.content.Context;
import android.content.Intent;
import android.support.p005v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.R$id;
import com.one.tomato.entity.CreditRecord;
import com.one.tomato.entity.p079db.LevelBean;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.base.view.MvpBaseActivity;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.thirdpart.recyclerview.BaseLinearLayoutManager;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.RxUtils;
import com.tomatolive.library.http.RequestParams;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: CreditActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.level.CreditActivity */
/* loaded from: classes3.dex */
public final class CreditActivity extends MvpBaseActivity<IBaseView, MvpBasePresenter<IBaseView>> {
    public static final Companion Companion = new Companion(null);
    private HashMap _$_findViewCache;
    private BaseRecyclerViewAdapter<CreditRecord> adapter;
    private LevelBean levelBean;
    private int pageNo = 1;
    private int pageSize = 10;

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
        return R.layout.activity_my_credit;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    /* renamed from: createPresenter */
    public MvpBasePresenter<IBaseView> mo6439createPresenter() {
        return null;
    }

    /* compiled from: CreditActivity.kt */
    /* renamed from: com.one.tomato.mvp.ui.level.CreditActivity$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void startActivity(Context context) {
            Intrinsics.checkParameterIsNotNull(context, "context");
            context.startActivity(new Intent(context, CreditActivity.class));
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initView() {
        LevelBean levelBean = DBUtil.getLevelBean();
        Intrinsics.checkExpressionValueIsNotNull(levelBean, "DBUtil.getLevelBean()");
        this.levelBean = levelBean;
        LevelBean levelBean2 = this.levelBean;
        if (levelBean2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("levelBean");
            throw null;
        }
        int noAuditCount = levelBean2.getNoAuditCount();
        LevelBean levelBean3 = this.levelBean;
        if (levelBean3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("levelBean");
            throw null;
        }
        int pubCount = levelBean3.getPubCount();
        LevelBean levelBean4 = this.levelBean;
        if (levelBean4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("levelBean");
            throw null;
        }
        int commentCount = levelBean4.getCommentCount();
        String string = AppUtil.getString(R.string.credit_right_times_no_limit);
        Object[] objArr = new Object[1];
        objArr[0] = noAuditCount == -1 ? string : String.valueOf(noAuditCount);
        String string2 = AppUtil.getString(R.string.credit_right_times, objArr);
        Object[] objArr2 = new Object[1];
        objArr2[0] = pubCount == -1 ? string : String.valueOf(pubCount);
        String string3 = AppUtil.getString(R.string.credit_right_times, objArr2);
        Object[] objArr3 = new Object[1];
        if (commentCount != -1) {
            string = String.valueOf(commentCount);
        }
        objArr3[0] = string;
        String string4 = AppUtil.getString(R.string.credit_right_times, objArr3);
        TextView textView = (TextView) _$_findCachedViewById(R$id.tv_credit_value);
        LevelBean levelBean5 = this.levelBean;
        if (levelBean5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("levelBean");
            throw null;
        }
        textView.setText(String.valueOf(levelBean5.getPrestigeBalance()));
        ((TextView) _$_findCachedViewById(R$id.tv_credit_review_time)).setText(string2);
        ((TextView) _$_findCachedViewById(R$id.tv_credit_publish_time)).setText(string3);
        ((TextView) _$_findCachedViewById(R$id.tv_credit_comment_time)).setText(string4);
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initData() {
        addListener();
        initAdapter();
        requestData(true);
    }

    private final void addListener() {
        ((ImageView) _$_findCachedViewById(R$id.iv_back)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.level.CreditActivity$addListener$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CreditActivity.this.onBackPressed();
            }
        });
        ((TextView) _$_findCachedViewById(R$id.tv_sub_title)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.level.CreditActivity$addListener$2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CreditRuleActivity.Companion.startActivity(CreditActivity.this);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void requestData(final boolean z) {
        if (z) {
            showWaitingDialog();
        }
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("memberId", String.valueOf(DBUtil.getMemberId()));
        hashMap.put("pageNo", String.valueOf(this.pageNo));
        hashMap.put(RequestParams.PAGE_SIZE, String.valueOf(this.pageSize));
        ApiImplService.Companion.getApiImplService().requestCreditRecord(hashMap).compose(RxUtils.bindToLifecycler((RxAppCompatActivity) this)).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<ArrayList<CreditRecord>>() { // from class: com.one.tomato.mvp.ui.level.CreditActivity$requestData$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(ArrayList<CreditRecord> arrayList) {
                if (z) {
                    CreditActivity.this.hideWaitingDialog();
                }
                CreditActivity.this.updateData(arrayList);
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                if (z) {
                    CreditActivity.this.hideWaitingDialog();
                }
                CreditActivity.this.updateDataFail();
            }
        });
    }

    private final void initAdapter() {
        final RecyclerView recyclerView = (RecyclerView) _$_findCachedViewById(R$id.recyclerView);
        this.adapter = new BaseRecyclerViewAdapter<CreditRecord>(this, R.layout.item_credit_record, recyclerView) { // from class: com.one.tomato.mvp.ui.level.CreditActivity$initAdapter$1
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder holder, CreditRecord itemData) {
                Intrinsics.checkParameterIsNotNull(holder, "holder");
                Intrinsics.checkParameterIsNotNull(itemData, "itemData");
                super.convert(holder, (BaseViewHolder) itemData);
                ((TextView) holder.getView(R.id.tv_title)).setText(itemData.getTypeDes());
                ((TextView) holder.getView(R.id.tv_time)).setText(itemData.getCreateTime());
                ((TextView) holder.getView(R.id.tv_value)).setText(itemData.getAmount());
            }

            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
            public void onEmptyRefresh(int i) {
                CreditActivity.this.requestData(true);
            }

            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
            public void onLoadMore() {
                CreditActivity.this.requestData(false);
            }
        };
        ((RecyclerView) _$_findCachedViewById(R$id.recyclerView)).setLayoutManager(new BaseLinearLayoutManager(getMContext()));
        RecyclerView recyclerView2 = (RecyclerView) _$_findCachedViewById(R$id.recyclerView);
        BaseRecyclerViewAdapter<CreditRecord> baseRecyclerViewAdapter = this.adapter;
        if (baseRecyclerViewAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
            throw null;
        }
        recyclerView2.setAdapter(baseRecyclerViewAdapter);
        BaseRecyclerViewAdapter<CreditRecord> baseRecyclerViewAdapter2 = this.adapter;
        if (baseRecyclerViewAdapter2 != null) {
            baseRecyclerViewAdapter2.setEnableLoadMore(false);
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
            throw null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateData(ArrayList<CreditRecord> arrayList) {
        List<CreditRecord> data;
        if (arrayList == null || arrayList.isEmpty()) {
            BaseRecyclerViewAdapter<CreditRecord> baseRecyclerViewAdapter = this.adapter;
            if (baseRecyclerViewAdapter == null) {
                Intrinsics.throwUninitializedPropertyAccessException("adapter");
                throw null;
            }
            if (((baseRecyclerViewAdapter == null || (data = baseRecyclerViewAdapter.getData()) == null) ? null : Integer.valueOf(data.size())).intValue() != 0) {
                return;
            }
            BaseRecyclerViewAdapter<CreditRecord> baseRecyclerViewAdapter2 = this.adapter;
            if (baseRecyclerViewAdapter2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("adapter");
                throw null;
            } else if (baseRecyclerViewAdapter2 == null) {
                return;
            } else {
                baseRecyclerViewAdapter2.setEmptyViewState(2, null);
                return;
            }
        }
        boolean z = true;
        this.pageNo++;
        BaseRecyclerViewAdapter<CreditRecord> baseRecyclerViewAdapter3 = this.adapter;
        if (baseRecyclerViewAdapter3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
            throw null;
        }
        if (baseRecyclerViewAdapter3 != null) {
            baseRecyclerViewAdapter3.addData(arrayList);
        }
        if (arrayList.size() < this.pageSize) {
            z = false;
        }
        if (z) {
            BaseRecyclerViewAdapter<CreditRecord> baseRecyclerViewAdapter4 = this.adapter;
            if (baseRecyclerViewAdapter4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("adapter");
                throw null;
            } else if (baseRecyclerViewAdapter4 == null) {
                return;
            } else {
                baseRecyclerViewAdapter4.loadMoreComplete();
                return;
            }
        }
        BaseRecyclerViewAdapter<CreditRecord> baseRecyclerViewAdapter5 = this.adapter;
        if (baseRecyclerViewAdapter5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
            throw null;
        } else if (baseRecyclerViewAdapter5 == null) {
        } else {
            baseRecyclerViewAdapter5.loadMoreEnd();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateDataFail() {
        List<CreditRecord> data;
        BaseRecyclerViewAdapter<CreditRecord> baseRecyclerViewAdapter = this.adapter;
        if (baseRecyclerViewAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
            throw null;
        }
        if (((baseRecyclerViewAdapter == null || (data = baseRecyclerViewAdapter.getData()) == null) ? null : Integer.valueOf(data.size())).intValue() == 0) {
            BaseRecyclerViewAdapter<CreditRecord> baseRecyclerViewAdapter2 = this.adapter;
            if (baseRecyclerViewAdapter2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("adapter");
                throw null;
            } else if (baseRecyclerViewAdapter2 == null) {
                return;
            } else {
                baseRecyclerViewAdapter2.setEmptyViewState(1, null);
                return;
            }
        }
        BaseRecyclerViewAdapter<CreditRecord> baseRecyclerViewAdapter3 = this.adapter;
        if (baseRecyclerViewAdapter3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("adapter");
            throw null;
        } else if (baseRecyclerViewAdapter3 == null) {
        } else {
            baseRecyclerViewAdapter3.loadMoreFail();
        }
    }
}
