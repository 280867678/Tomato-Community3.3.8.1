package com.one.tomato.mvp.p080ui.mine.view;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.one.tomato.R$id;
import com.one.tomato.entity.CreatorCenterBean;
import com.one.tomato.entity.CreatorCenterHomeBean;
import com.one.tomato.entity.p079db.UserInfo;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.base.view.MvpBaseActivity;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.mvp.p080ui.mine.adapter.CreatorRankAdapter;
import com.one.tomato.mvp.p080ui.p082up.view.UpHomeActivity;
import com.one.tomato.p085ui.income.IncomeActivity;
import com.one.tomato.p085ui.mine.MyPublishActivity;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.FormatUtil;
import com.one.tomato.utils.RxUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: CreatorCenterActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.mine.view.CreatorCenterActivity */
/* loaded from: classes3.dex */
public final class CreatorCenterActivity extends MvpBaseActivity<IBaseView, MvpBasePresenter<IBaseView>> {
    public static final Companion Companion = new Companion(null);
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
        return R.layout.activity_creator_center;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    /* renamed from: createPresenter */
    public MvpBasePresenter<IBaseView> mo6439createPresenter() {
        return null;
    }

    /* compiled from: CreatorCenterActivity.kt */
    /* renamed from: com.one.tomato.mvp.ui.mine.view.CreatorCenterActivity$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void startActivity(Context context) {
            Intrinsics.checkParameterIsNotNull(context, "context");
            Intent intent = new Intent();
            intent.setClass(context, CreatorCenterActivity.class);
            context.startActivity(intent);
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initView() {
        UserInfo userInfo = DBUtil.getUserInfo();
        Intrinsics.checkExpressionValueIsNotNull(userInfo, "DBUtil.getUserInfo()");
        if (userInfo.getUpHostType().compareTo("0") > 0) {
            ConstraintLayout cl_my_profit = (ConstraintLayout) _$_findCachedViewById(R$id.cl_my_profit);
            Intrinsics.checkExpressionValueIsNotNull(cl_my_profit, "cl_my_profit");
            cl_my_profit.setVisibility(0);
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initData() {
        addListener();
        requestData();
    }

    private final void addListener() {
        ((ImageView) _$_findCachedViewById(R$id.back)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.mine.view.CreatorCenterActivity$addListener$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CreatorCenterActivity.this.onBackPressed();
            }
        });
        ((ConstraintLayout) _$_findCachedViewById(R$id.cl_my_profit)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.mine.view.CreatorCenterActivity$addListener$2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Context mContext;
                IncomeActivity.Companion companion = IncomeActivity.Companion;
                mContext = CreatorCenterActivity.this.getMContext();
                if (mContext != null) {
                    companion.startActivity(mContext, 2);
                } else {
                    Intrinsics.throwNpe();
                    throw null;
                }
            }
        });
        ((ConstraintLayout) _$_findCachedViewById(R$id.cl_work_manager)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.mine.view.CreatorCenterActivity$addListener$3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Context mContext;
                mContext = CreatorCenterActivity.this.getMContext();
                MyPublishActivity.startActivity(mContext);
            }
        });
        ((ConstraintLayout) _$_findCachedViewById(R$id.cl_up)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.mine.view.CreatorCenterActivity$addListener$4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Context mContext;
                UpHomeActivity.Companion companion = UpHomeActivity.Companion;
                mContext = CreatorCenterActivity.this.getMContext();
                companion.startAct(mContext, 1);
            }
        });
        ((ConstraintLayout) _$_findCachedViewById(R$id.cl_dashen)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.mine.view.CreatorCenterActivity$addListener$5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Context mContext;
                UpHomeActivity.Companion companion = UpHomeActivity.Companion;
                mContext = CreatorCenterActivity.this.getMContext();
                companion.startAct(mContext, 2);
            }
        });
        DomainServer domainServer = DomainServer.getInstance();
        Intrinsics.checkExpressionValueIsNotNull(domainServer, "DomainServer.getInstance()");
        final String h5Url = domainServer.getH5Url();
        ((ConstraintLayout) _$_findCachedViewById(R$id.cl_video)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.mine.view.CreatorCenterActivity$addListener$6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AppUtil.startBrowseView(h5Url + "/author/Tutorials");
            }
        });
        ((ConstraintLayout) _$_findCachedViewById(R$id.cl_tool)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.mine.view.CreatorCenterActivity$addListener$7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                String string = AppUtil.getString(R.string.creator_html_tool);
                Intrinsics.checkExpressionValueIsNotNull(string, "AppUtil.getString(R.string.creator_html_tool)");
                CreatorCenterHtmlActivity.Companion.startActivity(CreatorCenterActivity.this, h5Url + "/author/Tools", string);
            }
        });
        ((ImageView) _$_findCachedViewById(R$id.iv_new_user)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.mine.view.CreatorCenterActivity$addListener$8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                String string = AppUtil.getString(R.string.creator_raiders);
                Intrinsics.checkExpressionValueIsNotNull(string, "AppUtil.getString(R.string.creator_raiders)");
                CreatorCenterHtmlActivity.Companion.startActivity(CreatorCenterActivity.this, h5Url + "/author/ProfitDescription", string);
            }
        });
        ((ImageView) _$_findCachedViewById(R$id.iv_content_standard)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.mine.view.CreatorCenterActivity$addListener$9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                String string = AppUtil.getString(R.string.creator_raiders);
                Intrinsics.checkExpressionValueIsNotNull(string, "AppUtil.getString(R.string.creator_raiders)");
                CreatorCenterHtmlActivity.Companion.startActivity(CreatorCenterActivity.this, h5Url + "/author/Standard", string);
            }
        });
        ((ImageView) _$_findCachedViewById(R$id.iv_content_auth)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.mine.view.CreatorCenterActivity$addListener$10
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                String string = AppUtil.getString(R.string.creator_raiders);
                Intrinsics.checkExpressionValueIsNotNull(string, "AppUtil.getString(R.string.creator_raiders)");
                CreatorCenterHtmlActivity.Companion.startActivity(CreatorCenterActivity.this, h5Url + "/author/Certification", string);
            }
        });
    }

    private final void requestData() {
        showWaitingDialog();
        ApiImplService.Companion.getApiImplService().requestCreatorCenterBean(DBUtil.getMemberId()).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<CreatorCenterBean>() { // from class: com.one.tomato.mvp.ui.mine.view.CreatorCenterActivity$requestData$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(CreatorCenterBean creatorCenterBean) {
                CreatorCenterActivity.this.dismissDialog();
                if (creatorCenterBean != null) {
                    MineTabFragment.Companion.setCreatorCenterBean(creatorCenterBean);
                    CreatorCenterActivity.this.updateData(creatorCenterBean);
                }
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                CreatorCenterActivity.this.dismissDialog();
                if (MineTabFragment.Companion.getCreatorCenterBean() != null) {
                    CreatorCenterActivity creatorCenterActivity = CreatorCenterActivity.this;
                    CreatorCenterBean creatorCenterBean = MineTabFragment.Companion.getCreatorCenterBean();
                    if (creatorCenterBean != null) {
                        creatorCenterActivity.updateData(creatorCenterBean);
                    } else {
                        Intrinsics.throwNpe();
                        throw null;
                    }
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateData(CreatorCenterBean creatorCenterBean) {
        ((TextView) _$_findCachedViewById(R$id.tv_day_profit_num)).setText("¥" + FormatUtil.formatTomato2RMB(creatorCenterBean.todayIncome));
        ((TextView) _$_findCachedViewById(R$id.tv_all_profit_num)).setText("¥" + FormatUtil.formatTomato2RMB(creatorCenterBean.totalIncome));
        ((TextView) _$_findCachedViewById(R$id.tv_publish_num)).setText(FormatUtil.formatNumOverTenThousand(creatorCenterBean.countPublished));
        ((TextView) _$_findCachedViewById(R$id.tv_serialize_num)).setText(FormatUtil.formatNumOverTenThousand(creatorCenterBean.countSerialGroup));
        ((TextView) _$_findCachedViewById(R$id.tv_up_join_num)).setText(AppUtil.getString(R.string.creator_join_num, FormatUtil.formatNumOverTenThousand(creatorCenterBean.countUpHost)));
        ((TextView) _$_findCachedViewById(R$id.tv_dashen_join_num)).setText(AppUtil.getString(R.string.creator_join_num, FormatUtil.formatNumOverTenThousand(creatorCenterBean.countOriginal)));
        initRankList(creatorCenterBean);
    }

    private final void initRankList(CreatorCenterBean creatorCenterBean) {
        List split$default;
        RecyclerView recyclerView = (RecyclerView) _$_findCachedViewById(R$id.recycler_rank);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getMContext(), 0, false));
        }
        Context mContext = getMContext();
        if (mContext != null) {
            RecyclerView recycler_rank = (RecyclerView) _$_findCachedViewById(R$id.recycler_rank);
            Intrinsics.checkExpressionValueIsNotNull(recycler_rank, "recycler_rank");
            CreatorRankAdapter creatorRankAdapter = new CreatorRankAdapter(mContext, recycler_rank);
            RecyclerView recyclerView2 = (RecyclerView) _$_findCachedViewById(R$id.recycler_rank);
            if (recyclerView2 != null) {
                recyclerView2.setAdapter(creatorRankAdapter);
            }
            String topListCfg = creatorCenterBean.topListCfg;
            if (TextUtils.isEmpty(topListCfg)) {
                return;
            }
            Intrinsics.checkExpressionValueIsNotNull(topListCfg, "topListCfg");
            split$default = StringsKt__StringsKt.split$default(topListCfg, new String[]{","}, false, 0, 6, null);
            List<ArrayList<CreatorCenterBean.TodayRank>> list = creatorCenterBean.top3List;
            if (list == null || list.isEmpty()) {
                creatorCenterBean.top3List = new ArrayList();
                int size = split$default.size();
                for (int i = 0; i < size; i++) {
                    ArrayList<CreatorCenterBean.TodayRank> arrayList = new ArrayList<>();
                    for (int i2 = 0; i2 < 3; i2++) {
                        arrayList.add(new CreatorCenterBean.TodayRank("0", "虚位以待"));
                    }
                    creatorCenterBean.top3List.add(arrayList);
                }
            } else {
                List<ArrayList<CreatorCenterBean.TodayRank>> list2 = creatorCenterBean.top3List;
                int size2 = list2.size();
                for (int i3 = 0; i3 < size2; i3++) {
                    int size3 = list2.get(i3).size();
                    if (size3 == 0) {
                        list2.get(i3).add(new CreatorCenterBean.TodayRank("0", "虚位以待"));
                        list2.get(i3).add(new CreatorCenterBean.TodayRank("0", "虚位以待"));
                        list2.get(i3).add(new CreatorCenterBean.TodayRank("0", "虚位以待"));
                    } else if (size3 == 1) {
                        list2.get(i3).add(new CreatorCenterBean.TodayRank("0", "虚位以待"));
                        list2.get(i3).add(new CreatorCenterBean.TodayRank("0", "虚位以待"));
                    } else if (size3 == 2) {
                        list2.get(i3).add(new CreatorCenterBean.TodayRank("0", "虚位以待"));
                    }
                }
            }
            List<ArrayList<CreatorCenterBean.TodayRank>> list3 = creatorCenterBean.top3List;
            ArrayList arrayList2 = new ArrayList();
            int size4 = split$default.size();
            for (int i4 = 0; i4 < size4; i4++) {
                arrayList2.add(new CreatorCenterHomeBean((String) split$default.get(i4), list3.get(i4)));
            }
            creatorRankAdapter.setNewData(arrayList2);
            creatorRankAdapter.setEnableLoadMore(false);
            return;
        }
        Intrinsics.throwNpe();
        throw null;
    }
}
