package com.tomatolive.library.p136ui.view.dialog;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.http.ApiRetrofit;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.function.HttpResultFunction;
import com.tomatolive.library.http.function.ServerResultFunction;
import com.tomatolive.library.model.AnchorEntity;
import com.tomatolive.library.p136ui.adapter.DedicateTopAdapter;
import com.tomatolive.library.p136ui.interfaces.OnLivePusherInfoCallback;
import com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment;
import com.tomatolive.library.p136ui.view.emptyview.RecyclerIncomeEmptyView;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.SysConfigInfoManager;
import com.tomatolive.library.utils.UserInfoManager;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.dialog.DedicateTopDialog */
/* loaded from: classes3.dex */
public class DedicateTopDialog extends BaseBottomDialogFragment {
    private static final String ANCHORID_KEY = "anchorId_key";
    private static final int DAY_TOP_VALUE = 1;
    private static final String LIVE_TYPE = "liveType";
    private static final int MONTH_TOP_VALUE = 2;
    private static final int WEEK_TOP_VALUE = 3;
    private String anchorId;
    private AnchorEntity anchorInfoItem;
    private LinearLayout llContentBg;
    private DedicateTopAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private OnLivePusherInfoCallback onLivePusherInfoCallback;
    private ProgressBar progressBar;
    private TextView tvAllTop;
    private TextView tvDayTop;
    private TextView tvMonthTop;
    private TextView tvWeekTop;
    private int dayTagValue = 1;
    private SparseArray<List<AnchorEntity>> listMap = new SparseArray<>();
    private final int CHARM_DAY_KEY = 11;
    private final int CHARM_WEEK_KEY = 12;
    private final int CHARM_MONTH_KEY = 13;
    private int liveType = 2;

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseRxDialogFragment
    public float getDimAmount() {
        return 0.0f;
    }

    public static DedicateTopDialog newInstance(int i, AnchorEntity anchorEntity, OnLivePusherInfoCallback onLivePusherInfoCallback) {
        Bundle bundle = new Bundle();
        DedicateTopDialog dedicateTopDialog = new DedicateTopDialog();
        bundle.putInt("liveType", i);
        bundle.putParcelable(ANCHORID_KEY, anchorEntity);
        dedicateTopDialog.setArguments(bundle);
        dedicateTopDialog.setOnLivePusherInfoCallback(onLivePusherInfoCallback);
        return dedicateTopDialog;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    public int getLayoutRes() {
        return R$layout.fq_dialog_dedicate_top;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    public void initView(View view) {
        this.tvDayTop = (TextView) view.findViewById(R$id.tv_day_top);
        this.tvWeekTop = (TextView) view.findViewById(R$id.tv_week_top);
        this.tvMonthTop = (TextView) view.findViewById(R$id.tv_month_top);
        this.tvAllTop = (TextView) view.findViewById(R$id.tv_bottom_top);
        this.mRecyclerView = (RecyclerView) view.findViewById(R$id.recycler_view);
        this.progressBar = (ProgressBar) view.findViewById(R$id.progress_wheel);
        this.llContentBg = (LinearLayout) view.findViewById(R$id.ll_content_bg);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this.mContext));
        this.mAdapter = new DedicateTopAdapter(R$layout.fq_item_list_dedicate_top_live, true);
        this.mRecyclerView.setAdapter(this.mAdapter);
        this.mAdapter.bindToRecyclerView(this.mRecyclerView);
        this.mAdapter.setEmptyView(new RecyclerIncomeEmptyView(this.mContext, 38));
        this.tvAllTop.getPaint().setFlags(8);
        this.tvAllTop.getPaint().setAntiAlias(true);
        initChangeView();
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseRxDialogFragment
    public void getBundle(Bundle bundle) {
        super.getBundle(bundle);
        this.liveType = getArgumentsInt("liveType", 2);
        this.anchorInfoItem = (AnchorEntity) bundle.getParcelable(ANCHORID_KEY);
        AnchorEntity anchorEntity = this.anchorInfoItem;
        this.anchorId = anchorEntity != null ? anchorEntity.userId : "";
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    public void initListener(View view) {
        super.initListener(view);
        this.tvDayTop.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$DedicateTopDialog$fOSp_FQIefamBhucBGCk4fvocK8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                DedicateTopDialog.this.lambda$initListener$0$DedicateTopDialog(view2);
            }
        });
        this.tvMonthTop.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$DedicateTopDialog$b09MwUuheF0jGBOk29HAKo4L0fQ
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                DedicateTopDialog.this.lambda$initListener$1$DedicateTopDialog(view2);
            }
        });
        this.tvWeekTop.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$DedicateTopDialog$JtqFFmPKT58BPtfuP_hZ0oTgG1E
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                DedicateTopDialog.this.lambda$initListener$2$DedicateTopDialog(view2);
            }
        });
        view.findViewById(R$id.tv_bottom_top).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$DedicateTopDialog$wawTaAE8c-1vAe6vBzfuewEcr6M
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                DedicateTopDialog.this.lambda$initListener$3$DedicateTopDialog(view2);
            }
        });
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$DedicateTopDialog$zN1OmxGCZcdhG503fLim_qf6kxw
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
                DedicateTopDialog.this.lambda$initListener$5$DedicateTopDialog(baseQuickAdapter, view2, i);
            }
        });
    }

    public /* synthetic */ void lambda$initListener$0$DedicateTopDialog(View view) {
        hideTopTagView(1);
    }

    public /* synthetic */ void lambda$initListener$1$DedicateTopDialog(View view) {
        hideTopTagView(2);
    }

    public /* synthetic */ void lambda$initListener$2$DedicateTopDialog(View view) {
        hideTopTagView(3);
    }

    public /* synthetic */ void lambda$initListener$3$DedicateTopDialog(View view) {
        dismiss();
        DedicateTopAllDialog.newInstance(this.liveType, this.anchorInfoItem, this.onLivePusherInfoCallback).show(getFragmentManager());
    }

    public /* synthetic */ void lambda$initListener$5$DedicateTopDialog(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        AnchorEntity anchorEntity = (AnchorEntity) baseQuickAdapter.getItem(i);
        if (anchorEntity == null) {
            return;
        }
        if (anchorEntity.isRankHideBoolean() && !TextUtils.equals(anchorEntity.userId, UserInfoManager.getInstance().getUserId())) {
            NobilityOpenTipsDialog.newInstance(13, new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$DedicateTopDialog$nx1tq8qNjklRyK9LO3M04zRAvMI
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    DedicateTopDialog.this.lambda$null$4$DedicateTopDialog(view2);
                }
            }).show(getChildFragmentManager());
            return;
        }
        OnLivePusherInfoCallback onLivePusherInfoCallback = this.onLivePusherInfoCallback;
        if (onLivePusherInfoCallback == null) {
            return;
        }
        onLivePusherInfoCallback.onClickUserAvatarListener(AppUtils.formatUserEntity(anchorEntity));
    }

    public /* synthetic */ void lambda$null$4$DedicateTopDialog(View view) {
        OnLivePusherInfoCallback onLivePusherInfoCallback;
        if (!AppUtils.isAnchorLiveType(this.liveType) && (onLivePusherInfoCallback = this.onLivePusherInfoCallback) != null) {
            onLivePusherInfoCallback.onNobilityOpenListener();
        }
    }

    private void initChangeView() {
        if (AppUtils.isAnchorLiveType(getArgumentsInt("liveType", 2))) {
            this.tvDayTop.setVisibility(0);
            this.tvWeekTop.setVisibility(0);
            this.tvMonthTop.setVisibility(0);
            this.tvAllTop.setVisibility(0);
            hideTopTagView(1);
            return;
        }
        ArrayList<String> liveRankConfig = SysConfigInfoManager.getInstance().getLiveRankConfig();
        if (liveRankConfig == null || liveRankConfig.isEmpty()) {
            this.tvDayTop.setVisibility(0);
            this.tvWeekTop.setVisibility(0);
            this.tvMonthTop.setVisibility(0);
            hideTopTagView(1);
            return;
        }
        Iterator<String> it2 = liveRankConfig.iterator();
        while (it2.hasNext()) {
            String next = it2.next();
            char c = 65535;
            switch (next.hashCode()) {
                case 96673:
                    if (next.equals("all")) {
                        c = 3;
                        break;
                    }
                    break;
                case 99228:
                    if (next.equals(ConstantUtils.TOP_DAY)) {
                        c = 0;
                        break;
                    }
                    break;
                case 3645428:
                    if (next.equals(ConstantUtils.TOP_WEEK)) {
                        c = 1;
                        break;
                    }
                    break;
                case 104080000:
                    if (next.equals(ConstantUtils.TOP_MONTH)) {
                        c = 2;
                        break;
                    }
                    break;
            }
            if (c == 0) {
                this.tvDayTop.setVisibility(0);
            } else if (c == 1) {
                this.tvWeekTop.setVisibility(0);
            } else if (c == 2) {
                this.tvMonthTop.setVisibility(0);
            } else if (c == 3) {
                this.tvAllTop.setVisibility(0);
            }
        }
        hideTopTagView(formatTopValue(liveRankConfig));
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x0044, code lost:
        if (r7.equals(com.tomatolive.library.utils.ConstantUtils.TOP_DAY) != false) goto L14;
     */
    /* JADX WARN: Removed duplicated region for block: B:15:0x004a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int formatTopValue(List<String> list) {
        if (list != null && !list.isEmpty()) {
            char c = 0;
            String str = list.get(0);
            int hashCode = str.hashCode();
            if (hashCode != 99228) {
                if (hashCode != 3645428) {
                    if (hashCode == 104080000 && str.equals(ConstantUtils.TOP_MONTH)) {
                        c = 2;
                        if (c != 0) {
                            if (c == 1) {
                                return 3;
                            }
                            return c != 2 ? 1 : 2;
                        }
                    }
                    c = 65535;
                    if (c != 0) {
                    }
                } else {
                    if (str.equals(ConstantUtils.TOP_WEEK)) {
                        c = 1;
                        if (c != 0) {
                        }
                    }
                    c = 65535;
                    if (c != 0) {
                    }
                }
            }
        }
        return 1;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    public double getHeightScale() {
        return this.maxHeightScale;
    }

    private void hideTopTagView(int i) {
        this.dayTagValue = i;
        boolean z = false;
        this.tvDayTop.setSelected(i == 1);
        this.tvMonthTop.setSelected(i == 2);
        TextView textView = this.tvWeekTop;
        if (i == 3) {
            z = true;
        }
        textView.setSelected(z);
        setTextViewDrawable(i);
        sendRequest(this.anchorId, i);
    }

    private void setTextViewDrawable(int i) {
        Drawable drawable = getResources().getDrawable(R$drawable.fq_shape_top_tag_red_divider);
        this.tvDayTop.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, i == 1 ? drawable : null);
        this.tvMonthTop.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, i == 2 ? drawable : null);
        TextView textView = this.tvWeekTop;
        if (i != 3) {
            drawable = null;
        }
        textView.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, drawable);
    }

    private String getDateType() {
        int i = this.dayTagValue;
        return i != 1 ? i != 2 ? i != 3 ? ConstantUtils.TOP_DAY : ConstantUtils.TOP_WEEK : ConstantUtils.TOP_MONTH : ConstantUtils.TOP_DAY;
    }

    private void sendRequest(String str, final int i) {
        List<AnchorEntity> charmDataList = getCharmDataList(i);
        if (charmDataList != null) {
            this.mAdapter.setNewData(charmDataList);
        } else {
            ApiRetrofit.getInstance().getApiService().getDedicateTopListService(new RequestParams().getContributionListParams(getDateType(), str)).map(new ServerResultFunction<List<AnchorEntity>>() { // from class: com.tomatolive.library.ui.view.dialog.DedicateTopDialog.2
            }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new Observer<List<AnchorEntity>>() { // from class: com.tomatolive.library.ui.view.dialog.DedicateTopDialog.1
                @Override // io.reactivex.Observer
                public void onSubscribe(Disposable disposable) {
                    DedicateTopDialog.this.showLoading(true);
                }

                @Override // io.reactivex.Observer
                public void onNext(List<AnchorEntity> list) {
                    if (list == null) {
                        return;
                    }
                    DedicateTopDialog.this.putCharmDataList(list, i);
                    DedicateTopDialog.this.mAdapter.setNewData(list);
                }

                @Override // io.reactivex.Observer
                public void onError(Throwable th) {
                    DedicateTopDialog.this.showLoading(false);
                }

                @Override // io.reactivex.Observer
                public void onComplete() {
                    DedicateTopDialog.this.showLoading(false);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showLoading(boolean z) {
        int i = 0;
        this.progressBar.setVisibility(z ? 0 : 4);
        LinearLayout linearLayout = this.llContentBg;
        if (z) {
            i = 4;
        }
        linearLayout.setVisibility(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void putCharmDataList(List<AnchorEntity> list, int i) {
        if (this.listMap == null) {
            this.listMap = new SparseArray<>();
        }
        if (i == 1) {
            this.listMap.put(11, formatList(list));
        } else if (i == 2) {
            this.listMap.put(13, formatList(list));
        } else if (i != 3) {
        } else {
            this.listMap.put(12, formatList(list));
        }
    }

    private List<AnchorEntity> formatList(List<AnchorEntity> list) {
        if (list == null) {
            return new ArrayList();
        }
        return list.isEmpty() ? new ArrayList() : list;
    }

    private List<AnchorEntity> getCharmDataList(int i) {
        SparseArray<List<AnchorEntity>> sparseArray = this.listMap;
        if (sparseArray == null) {
            return null;
        }
        if (i == 1) {
            return sparseArray.get(11);
        }
        if (i == 2) {
            return sparseArray.get(13);
        }
        if (i == 3) {
            return sparseArray.get(12);
        }
        return new ArrayList();
    }

    public OnLivePusherInfoCallback getOnLivePusherInfoCallback() {
        return this.onLivePusherInfoCallback;
    }

    public void setOnLivePusherInfoCallback(OnLivePusherInfoCallback onLivePusherInfoCallback) {
        this.onLivePusherInfoCallback = onLivePusherInfoCallback;
    }

    @Override // com.trello.rxlifecycle2.components.support.RxDialogFragment, android.support.p002v4.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        SparseArray<List<AnchorEntity>> sparseArray = this.listMap;
        if (sparseArray != null) {
            sparseArray.clear();
            this.listMap = null;
        }
    }
}
