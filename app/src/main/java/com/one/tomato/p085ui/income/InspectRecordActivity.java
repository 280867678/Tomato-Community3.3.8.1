package com.one.tomato.p085ui.income;

import android.content.Context;
import android.content.Intent;
import android.support.p005v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.R$id;
import com.one.tomato.dialog.CustomAlertDialog;
import com.one.tomato.entity.InspectRecord;
import com.one.tomato.entity.RechargeAccount;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.base.view.MvpBaseActivity;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.thirdpart.recyclerview.BaseGridLayoutManager;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.one.tomato.thirdpart.recyclerview.GridItemDecoration;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.FormatUtil;
import com.one.tomato.utils.ImmersionBarUtil;
import com.one.tomato.utils.RxUtils;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import kotlin.collections._ArraysJvm;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;

/* compiled from: InspectRecordActivity.kt */
/* renamed from: com.one.tomato.ui.income.InspectRecordActivity */
/* loaded from: classes3.dex */
public final class InspectRecordActivity extends MvpBaseActivity<IBaseView, MvpBasePresenter<IBaseView>> {
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
        return R.layout.activity_inspect_record;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    /* renamed from: createPresenter */
    public MvpBasePresenter<IBaseView> mo6439createPresenter() {
        return null;
    }

    /* compiled from: InspectRecordActivity.kt */
    /* renamed from: com.one.tomato.ui.income.InspectRecordActivity$Companion */
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
            intent.setClass(context, InspectRecordActivity.class);
            context.startActivity(intent);
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initView() {
        ImmersionBarUtil.init(this, (int) R.id.ll_title);
        setListener();
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initData() {
        getIncomeAccountBalance();
        requestInspectList();
    }

    private final void setListener() {
        ((ImageView) _$_findCachedViewById(R$id.iv_back)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.income.InspectRecordActivity$setListener$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                InspectRecordActivity.this.onBackPressed();
            }
        });
        TextView textView = (TextView) _$_findCachedViewById(R$id.right_txt);
        if (textView != null) {
            textView.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.ui.income.InspectRecordActivity$setListener$2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    Context mContext;
                    Context mContext2;
                    mContext = InspectRecordActivity.this.getMContext();
                    View inflate = LayoutInflater.from(mContext).inflate(R.layout.dialog_inspect_rule, (ViewGroup) null);
                    mContext2 = InspectRecordActivity.this.getMContext();
                    CustomAlertDialog customAlertDialog = new CustomAlertDialog(mContext2);
                    customAlertDialog.setTitle(R.string.inspect_desc_title);
                    customAlertDialog.setContentView(inflate);
                    customAlertDialog.bottomButtonVisiblity(2);
                    customAlertDialog.setConfirmButtonListener();
                }
            });
        }
    }

    private final void getIncomeAccountBalance() {
        ApiImplService.Companion.getApiImplService().requestRechargeAccount(DBUtil.getMemberId()).compose(RxUtils.schedulersTransformer()).compose(RxUtils.bindToLifecycler((RxAppCompatActivity) this)).subscribe(new ApiDisposableObserver<RechargeAccount>() { // from class: com.one.tomato.ui.income.InspectRecordActivity$getIncomeAccountBalance$1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable e) {
                Intrinsics.checkParameterIsNotNull(e, "e");
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(RechargeAccount bean) {
                Intrinsics.checkParameterIsNotNull(bean, "bean");
                ((TextView) InspectRecordActivity.this._$_findCachedViewById(R$id.tv_account_avail)).setText(FormatUtil.formatTomato2RMB(bean.withdrawPassBalance));
                ((TextView) InspectRecordActivity.this._$_findCachedViewById(R$id.tv_account_un_avail)).setText(FormatUtil.formatTomato2RMB(bean.withdrawNotPassBalance));
            }
        });
    }

    private final void requestInspectList() {
        ApiImplService.Companion.getApiImplService().requestInspectList(DBUtil.getMemberId()).compose(RxUtils.schedulersTransformer()).doOnSubscribe(new Consumer<Disposable>() { // from class: com.one.tomato.ui.income.InspectRecordActivity$requestInspectList$1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Disposable disposable) {
                InspectRecordActivity.this.showWaitingDialog();
            }
        }).subscribe(new ApiDisposableObserver<ArrayList<InspectRecord>>() { // from class: com.one.tomato.ui.income.InspectRecordActivity$requestInspectList$2
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(ArrayList<InspectRecord> list) {
                Intrinsics.checkParameterIsNotNull(list, "list");
                InspectRecordActivity.this.hideWaitingDialog();
                InspectRecordActivity.this.initRecordAdapter(list);
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable e) {
                Intrinsics.checkParameterIsNotNull(e, "e");
                InspectRecordActivity.this.hideWaitingDialog();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Type inference failed for: r0v0, types: [T, java.util.ArrayList] */
    public final void initRecordAdapter(ArrayList<InspectRecord> arrayList) {
        List asList;
        final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
        ref$ObjectRef.element = new ArrayList();
        String[] stringArray = getResources().getStringArray(R.array.inspect_adapter_titles);
        Intrinsics.checkExpressionValueIsNotNull(stringArray, "resources.getStringArray…y.inspect_adapter_titles)");
        asList = _ArraysJvm.asList(stringArray);
        ((ArrayList) ref$ObjectRef.element).addAll(asList);
        if (arrayList.isEmpty()) {
            ((ArrayList) ref$ObjectRef.element).add("--");
            ((ArrayList) ref$ObjectRef.element).add("--");
            ((ArrayList) ref$ObjectRef.element).add("--");
            ((ArrayList) ref$ObjectRef.element).add("--");
        } else {
            Iterator<InspectRecord> it2 = arrayList.iterator();
            while (it2.hasNext()) {
                InspectRecord next = it2.next();
                double d = 100;
                ((ArrayList) ref$ObjectRef.element).add(FormatUtil.formatTwo(Double.valueOf(next.amount / d)));
                ((ArrayList) ref$ObjectRef.element).add(FormatUtil.formatTwo(Double.valueOf(next.amount / d)));
                ((ArrayList) ref$ObjectRef.element).add(FormatUtil.formatTwo(Double.valueOf(next.auditAmount / d)));
                ((ArrayList) ref$ObjectRef.element).add(AppUtil.getString(next.status == 1 ? R.string.inspect_status_pass : R.string.inspect_status_un_pass));
            }
        }
        final Context mContext = getMContext();
        final ArrayList arrayList2 = (ArrayList) ref$ObjectRef.element;
        final RecyclerView recyclerView = (RecyclerView) _$_findCachedViewById(R$id.recyclerView);
        BaseRecyclerViewAdapter<String> baseRecyclerViewAdapter = new BaseRecyclerViewAdapter<String>(this, ref$ObjectRef, mContext, R.layout.item_credit_rule, arrayList2, recyclerView) { // from class: com.one.tomato.ui.income.InspectRecordActivity$initRecordAdapter$adapter$1
            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
            public void onEmptyRefresh(int i) {
            }

            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
            public void onLoadMore() {
            }

            /* JADX INFO: Access modifiers changed from: package-private */
            {
                super(mContext, r4, arrayList2, recyclerView);
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
            public void convert(BaseViewHolder holder, String itemData) {
                Intrinsics.checkParameterIsNotNull(holder, "holder");
                Intrinsics.checkParameterIsNotNull(itemData, "itemData");
                super.convert(holder, (BaseViewHolder) itemData);
                TextView tv_title = (TextView) holder.getView(R.id.tv_title);
                Intrinsics.checkExpressionValueIsNotNull(tv_title, "tv_title");
                tv_title.setText(itemData);
            }
        };
        GridItemDecoration.Builder builder = new GridItemDecoration.Builder(getMContext());
        builder.setVerticalSpan(R.dimen.dimen_1);
        builder.setHorizontalSpan(R.dimen.dimen_1);
        builder.setColor(getResources().getColor(R.color.disable));
        builder.setShowLastLine(false);
        ((RecyclerView) _$_findCachedViewById(R$id.recyclerView)).addItemDecoration(builder.build());
        ((RecyclerView) _$_findCachedViewById(R$id.recyclerView)).setLayoutManager(new BaseGridLayoutManager(getMContext(), 4));
        ((RecyclerView) _$_findCachedViewById(R$id.recyclerView)).setAdapter(baseRecyclerViewAdapter);
        baseRecyclerViewAdapter.setEnableLoadMore(false);
    }
}
