package com.tomatolive.library.p136ui.view.dialog;

import android.os.Bundle;
import android.support.p005v7.widget.GridLayoutManager;
import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.http.ApiRetrofit;
import com.tomatolive.library.http.ApiService;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.function.HttpResultFunction;
import com.tomatolive.library.http.function.ServerResultFunction;
import com.tomatolive.library.model.AnchorEntity;
import com.tomatolive.library.model.GiftWallEntity;
import com.tomatolive.library.model.PopularCardEntity;
import com.tomatolive.library.p136ui.adapter.GiftWallAdapter;
import com.tomatolive.library.p136ui.adapter.GiftWallAnchorRankingAdapter;
import com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment;
import com.tomatolive.library.p136ui.view.dialog.base.BaseRxDialogFragment;
import com.tomatolive.library.p136ui.view.divider.RVDividerLinear;
import com.tomatolive.library.p136ui.view.emptyview.RecyclerIncomeEmptyView;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.GlideUtils;
import com.tomatolive.library.utils.live.SimpleRxObserver;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.dialog.GiftWallDialog */
/* loaded from: classes3.dex */
public class GiftWallDialog extends BaseBottomDialogFragment {
    private String anchorAppId;
    private AnchorEntity anchorEntity;
    private String anchorId;
    private GiftWallAnchorRankingAdapter anchorRankingAdapter;
    private GiftWallAdapter giftWallAdapter;
    private ImageView ivAvatar;
    private ImageView ivClose;
    private ImageView ivHelp;
    private LinearLayout llGiftWallBg;
    private RecyclerView recyclerViewGift;
    private RecyclerView recyclerViewRanking;
    private TextView tvAnchorNickname;
    private TextView tvDialogTitle;
    private TextView tvLightCount;
    private TextView tvLoading;
    private TextView tvLoadingFail;
    private TextView tvRanking;
    private TextView tvRankingTop;
    private WebView webViewDesc;
    private final int CONTENT_TYPE_1 = 1;
    private final int CONTENT_TYPE_2 = 2;
    private final int CONTENT_TYPE_3 = 3;
    private int contentType = 1;
    private boolean isDimAmount = false;
    private List<GiftWallEntity.GiftWallGiftItemEntity> giftList = null;

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    protected double getHeightScale() {
        return 0.6d;
    }

    public static GiftWallDialog newInstance(AnchorEntity anchorEntity) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ConstantUtils.RESULT_ITEM, anchorEntity);
        GiftWallDialog giftWallDialog = new GiftWallDialog();
        giftWallDialog.setArguments(bundle);
        return giftWallDialog;
    }

    public static GiftWallDialog newInstance(AnchorEntity anchorEntity, boolean z) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ConstantUtils.RESULT_ITEM, anchorEntity);
        bundle.putBoolean(ConstantUtils.RESULT_FLAG, z);
        GiftWallDialog giftWallDialog = new GiftWallDialog();
        giftWallDialog.setArguments(bundle);
        return giftWallDialog;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    protected int getLayoutRes() {
        return R$layout.fq_dialog_achieve_gift_wall_view;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseRxDialogFragment
    public void getBundle(Bundle bundle) {
        super.getBundle(bundle);
        this.anchorEntity = (AnchorEntity) bundle.getParcelable(ConstantUtils.RESULT_ITEM);
        this.isDimAmount = bundle.getBoolean(ConstantUtils.RESULT_FLAG, false);
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    protected void initView(View view) {
        this.ivClose = (ImageView) view.findViewById(R$id.iv_close);
        this.ivHelp = (ImageView) view.findViewById(R$id.iv_help);
        this.ivAvatar = (ImageView) view.findViewById(R$id.iv_avatar);
        this.tvLoading = (TextView) view.findViewById(R$id.tv_loading);
        this.tvLoadingFail = (TextView) view.findViewById(R$id.tv_loading_fail);
        this.tvDialogTitle = (TextView) view.findViewById(R$id.tv_dialog_title);
        this.tvAnchorNickname = (TextView) view.findViewById(R$id.tv_anchor_nickname);
        this.tvLightCount = (TextView) view.findViewById(R$id.tv_light_count);
        this.tvRanking = (TextView) view.findViewById(R$id.tv_ranking);
        this.tvRankingTop = (TextView) view.findViewById(R$id.tv_ranking_top);
        this.llGiftWallBg = (LinearLayout) view.findViewById(R$id.ll_gift_wall_bg);
        this.recyclerViewGift = (RecyclerView) view.findViewById(R$id.recycler_view_gift);
        this.recyclerViewRanking = (RecyclerView) view.findViewById(R$id.recycler_view_ranking);
        this.webViewDesc = (WebView) view.findViewById(R$id.web_view);
        this.webViewDesc.getSettings().setLoadWithOverviewMode(false);
        this.webViewDesc.getSettings().setUseWideViewPort(false);
        initGiftAdapter();
        initRankingAdapter();
    }

    @Override // com.trello.rxlifecycle2.components.support.RxDialogFragment, android.support.p002v4.app.Fragment
    public void onResume() {
        super.onResume();
        sendGiftRequest();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    public void initListener(View view) {
        super.initListener(view);
        this.ivClose.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$GiftWallDialog$ZokswkcUrggiDsaPz-mdJ4wEgIY
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                GiftWallDialog.this.lambda$initListener$0$GiftWallDialog(view2);
            }
        });
        this.tvRankingTop.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$GiftWallDialog$JQWxdjPKqUY4Ddh5bSHlVPAG7Z8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                GiftWallDialog.this.lambda$initListener$1$GiftWallDialog(view2);
            }
        });
        this.ivHelp.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$GiftWallDialog$37gq2yzg195R3yLAfkxWD1Ls8TQ
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                GiftWallDialog.this.lambda$initListener$2$GiftWallDialog(view2);
            }
        });
        this.tvLoadingFail.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$GiftWallDialog$8p_d1uWW3sqybYABjmg39P8fgBE
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                GiftWallDialog.this.lambda$initListener$3$GiftWallDialog(view2);
            }
        });
    }

    public /* synthetic */ void lambda$initListener$0$GiftWallDialog(View view) {
        if (this.contentType == 1) {
            dismiss();
            return;
        }
        this.contentType = 1;
        showContentView();
    }

    public /* synthetic */ void lambda$initListener$1$GiftWallDialog(View view) {
        sendAnchorRankingRequest();
    }

    public /* synthetic */ void lambda$initListener$2$GiftWallDialog(View view) {
        sendDescRequest();
    }

    public /* synthetic */ void lambda$initListener$3$GiftWallDialog(View view) {
        int i = this.contentType;
        if (i == 1) {
            sendGiftRequest();
        } else if (i == 2) {
            sendAnchorRankingRequest();
        } else if (i != 3) {
        } else {
            sendDescRequest();
        }
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseRxDialogFragment
    public float getDimAmount() {
        if (this.isDimAmount) {
            return super.getDimAmount();
        }
        return 0.0f;
    }

    private void initGiftAdapter() {
        this.giftWallAdapter = new GiftWallAdapter(R$layout.fq_item_grid_achieve_gift_wall_view);
        this.recyclerViewGift.setLayoutManager(new GridLayoutManager(this.mContext, 4));
        this.recyclerViewGift.setHasFixedSize(true);
        this.recyclerViewGift.setAdapter(this.giftWallAdapter);
        this.giftWallAdapter.bindToRecyclerView(this.recyclerViewGift);
    }

    private void initRankingAdapter() {
        this.anchorRankingAdapter = new GiftWallAnchorRankingAdapter(R$layout.fq_item_list_achieve_anchor_ranking_view);
        this.recyclerViewRanking.setLayoutManager(new LinearLayoutManager(this.mContext));
        this.recyclerViewRanking.addItemDecoration(new RVDividerLinear(this.mContext, R$color.fq_color_transparent, 16.0f));
        this.recyclerViewRanking.setHasFixedSize(true);
        this.recyclerViewRanking.setAdapter(this.anchorRankingAdapter);
        this.anchorRankingAdapter.bindToRecyclerView(this.recyclerViewRanking);
        this.giftWallAdapter.setEmptyView(new RecyclerIncomeEmptyView(this.mContext, 100));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showContentView() {
        int i = 4;
        this.tvLoading.setVisibility(4);
        this.tvLoadingFail.setVisibility(4);
        this.llGiftWallBg.setVisibility(this.contentType == 1 ? 0 : 4);
        this.recyclerViewRanking.setVisibility(this.contentType == 2 ? 0 : 4);
        this.webViewDesc.setVisibility(this.contentType == 3 ? 0 : 4);
        this.ivClose.setImageResource(this.contentType == 1 ? R$drawable.fq_ic_achieve_close_black : R$drawable.fq_ic_achieve_back_black);
        this.tvDialogTitle.setText(getDialogTitle(this.contentType));
        ImageView imageView = this.ivHelp;
        if (this.contentType == 1) {
            i = 0;
        }
        imageView.setVisibility(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showLoading(int i) {
        this.contentType = i;
        int i2 = 0;
        this.tvLoading.setVisibility(0);
        this.tvLoadingFail.setVisibility(4);
        this.llGiftWallBg.setVisibility(4);
        this.recyclerViewRanking.setVisibility(4);
        this.webViewDesc.setVisibility(4);
        this.ivClose.setImageResource(i == 1 ? R$drawable.fq_ic_achieve_close_black : R$drawable.fq_ic_achieve_back_black);
        this.tvDialogTitle.setText(getDialogTitle(i));
        ImageView imageView = this.ivHelp;
        if (i != 1) {
            i2 = 4;
        }
        imageView.setVisibility(i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showFailView() {
        this.tvLoadingFail.setVisibility(0);
        this.tvLoading.setVisibility(4);
        this.llGiftWallBg.setVisibility(4);
        this.recyclerViewRanking.setVisibility(4);
        this.webViewDesc.setVisibility(4);
    }

    private void sendGiftRequest() {
        Observable just;
        AnchorEntity anchorEntity = this.anchorEntity;
        String str = "";
        this.anchorId = anchorEntity == null ? str : anchorEntity.userId;
        AnchorEntity anchorEntity2 = this.anchorEntity;
        if (anchorEntity2 != null) {
            str = anchorEntity2.appId;
        }
        this.anchorAppId = str;
        ApiService apiService = ApiRetrofit.getInstance().getApiService();
        List<GiftWallEntity.GiftWallGiftItemEntity> list = this.giftList;
        if (list == null) {
            just = apiService.getGiftWallListService(new RequestParams().getAppIdParams()).map(new ServerResultFunction<List<GiftWallEntity.GiftWallGiftItemEntity>>() { // from class: com.tomatolive.library.ui.view.dialog.GiftWallDialog.1
            }).onErrorResumeNext(new HttpResultFunction());
        } else {
            just = Observable.just(list);
        }
        Observable.zip(just, apiService.getGiftWallService(new RequestParams().getAnchorIdParams(this.anchorId)).map(new ServerResultFunction<GiftWallEntity>() { // from class: com.tomatolive.library.ui.view.dialog.GiftWallDialog.2
        }).onErrorResumeNext(new HttpResultFunction()), new BiFunction<List<GiftWallEntity.GiftWallGiftItemEntity>, GiftWallEntity, GiftWallEntity>() { // from class: com.tomatolive.library.ui.view.dialog.GiftWallDialog.4
            @Override // io.reactivex.functions.BiFunction
            /* renamed from: apply  reason: avoid collision after fix types in other method */
            public GiftWallEntity mo6745apply(List<GiftWallEntity.GiftWallGiftItemEntity> list2, GiftWallEntity giftWallEntity) throws Exception {
                return GiftWallDialog.this.getGiftWallEntity(list2, giftWallEntity);
            }
        }).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new SimpleRxObserver<GiftWallEntity>() { // from class: com.tomatolive.library.ui.view.dialog.GiftWallDialog.3
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                super.onSubscribe(disposable);
                GiftWallDialog.this.showLoading(1);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(GiftWallEntity giftWallEntity) {
                if (giftWallEntity == null) {
                    return;
                }
                GlideUtils.loadAvatar(((BaseRxDialogFragment) GiftWallDialog.this).mContext, GiftWallDialog.this.ivAvatar, giftWallEntity.avatar);
                GiftWallDialog.this.tvAnchorNickname.setText(giftWallEntity.name);
                GiftWallDialog.this.tvRanking.setText(GiftWallDialog.this.getString(R$string.fq_achieve_ranking_num, giftWallEntity.getRankStr()));
                GiftWallDialog.this.tvLightCount.setText(Html.fromHtml(GiftWallDialog.this.getString(R$string.fq_achieve_gift_wall_light_color, String.valueOf(giftWallEntity.getGiftLightCount()))));
                GiftWallDialog.this.giftWallAdapter.setNewData(giftWallEntity.giftList);
                GiftWallDialog.this.showContentView();
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onError(Throwable th) {
                super.onError(th);
                GiftWallDialog.this.showFailView();
            }
        });
    }

    private void sendAnchorRankingRequest() {
        ApiRetrofit.getInstance().getApiService().getGiftWallAnchorListService(new RequestParams().getAppIdParams(this.anchorAppId)).map(new ServerResultFunction<List<GiftWallEntity>>() { // from class: com.tomatolive.library.ui.view.dialog.GiftWallDialog.6
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new SimpleRxObserver<List<GiftWallEntity>>() { // from class: com.tomatolive.library.ui.view.dialog.GiftWallDialog.5
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                super.onSubscribe(disposable);
                GiftWallDialog.this.showLoading(2);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(List<GiftWallEntity> list) {
                if (list == null || list.isEmpty()) {
                    return;
                }
                GiftWallDialog.this.anchorRankingAdapter.setNewData(list);
                GiftWallDialog.this.showContentView();
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onError(Throwable th) {
                super.onError(th);
                GiftWallDialog.this.showFailView();
            }
        });
    }

    private void sendDescRequest() {
        ApiRetrofit.getInstance().getApiService().getAppParamConfigService(new RequestParams().getCodeParams(ConstantUtils.APP_PARAM_GIFT_WALL_RULE)).map(new ServerResultFunction<PopularCardEntity>() { // from class: com.tomatolive.library.ui.view.dialog.GiftWallDialog.8
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new SimpleRxObserver<PopularCardEntity>() { // from class: com.tomatolive.library.ui.view.dialog.GiftWallDialog.7
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                super.onSubscribe(disposable);
                GiftWallDialog.this.showLoading(3);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(PopularCardEntity popularCardEntity) {
                if (popularCardEntity == null) {
                    return;
                }
                GiftWallDialog.this.showContentView();
                GiftWallDialog.this.webViewDesc.loadDataWithBaseURL(null, popularCardEntity.value, "text/html", "UTF-8", null);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onError(Throwable th) {
                super.onError(th);
                GiftWallDialog.this.showFailView();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public GiftWallEntity getGiftWallEntity(List<GiftWallEntity.GiftWallGiftItemEntity> list, GiftWallEntity giftWallEntity) {
        this.giftList = list;
        if (giftWallEntity == null) {
            GiftWallEntity giftWallEntity2 = new GiftWallEntity();
            AnchorEntity anchorEntity = this.anchorEntity;
            giftWallEntity2.avatar = anchorEntity == null ? "" : anchorEntity.avatar;
            AnchorEntity anchorEntity2 = this.anchorEntity;
            giftWallEntity2.name = anchorEntity2 == null ? "" : anchorEntity2.nickname;
            giftWallEntity2.markIds = "";
            giftWallEntity2.rank = 0;
            giftWallEntity2.num = "0";
            giftWallEntity2.giftList = formatGiftWallListLight(list);
            return giftWallEntity2;
        } else if (TextUtils.isEmpty(giftWallEntity.markIds)) {
            GiftWallEntity giftWallEntity3 = new GiftWallEntity();
            AnchorEntity anchorEntity3 = this.anchorEntity;
            giftWallEntity3.avatar = anchorEntity3 == null ? "" : anchorEntity3.avatar;
            AnchorEntity anchorEntity4 = this.anchorEntity;
            giftWallEntity3.name = anchorEntity4 == null ? "" : anchorEntity4.nickname;
            giftWallEntity3.markIds = "";
            giftWallEntity3.num = "0";
            giftWallEntity3.rank = giftWallEntity.rank;
            giftWallEntity3.giftList = formatGiftWallListLight(list);
            return giftWallEntity3;
        } else {
            GiftWallEntity giftWallEntity4 = new GiftWallEntity();
            AnchorEntity anchorEntity5 = this.anchorEntity;
            giftWallEntity4.avatar = anchorEntity5 == null ? giftWallEntity.avatar : anchorEntity5.avatar;
            AnchorEntity anchorEntity6 = this.anchorEntity;
            giftWallEntity4.name = anchorEntity6 == null ? giftWallEntity.name : anchorEntity6.nickname;
            giftWallEntity4.markIds = giftWallEntity.markIds;
            giftWallEntity4.rank = giftWallEntity.rank;
            giftWallEntity4.num = giftWallEntity.num;
            if (list == null || list.isEmpty()) {
                giftWallEntity4.giftList = new ArrayList();
                return giftWallEntity4;
            }
            String str = giftWallEntity4.markIds;
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            for (GiftWallEntity.GiftWallGiftItemEntity giftWallGiftItemEntity : list) {
                String str2 = giftWallGiftItemEntity.markId;
                boolean z = true;
                if (str.contains(giftWallEntity4.getUnitCommaStr())) {
                    boolean z2 = false;
                    for (String str3 : TextUtils.split(str, giftWallEntity4.getUnitCommaStr())) {
                        if (TextUtils.equals(str3, str2)) {
                            z2 = true;
                        }
                    }
                    z = z2;
                } else if (!TextUtils.equals(str, str2)) {
                    z = false;
                }
                giftWallGiftItemEntity.isLight = z;
            }
            for (GiftWallEntity.GiftWallGiftItemEntity giftWallGiftItemEntity2 : list) {
                if (giftWallGiftItemEntity2.isLight) {
                    arrayList.add(giftWallGiftItemEntity2);
                } else {
                    arrayList2.add(giftWallGiftItemEntity2);
                }
            }
            ArrayList arrayList3 = new ArrayList();
            arrayList3.addAll(arrayList);
            arrayList3.addAll(arrayList2);
            giftWallEntity4.giftList = arrayList3;
            return giftWallEntity4;
        }
    }

    private List<GiftWallEntity.GiftWallGiftItemEntity> formatGiftWallListLight(List<GiftWallEntity.GiftWallGiftItemEntity> list) {
        if (list == null) {
            return new ArrayList();
        }
        for (GiftWallEntity.GiftWallGiftItemEntity giftWallGiftItemEntity : list) {
            giftWallGiftItemEntity.isLight = false;
        }
        return list;
    }

    private String getDialogTitle(int i) {
        if (i != 2) {
            if (i == 3) {
                return getString(R$string.fq_achieve_gift_wall_desc);
            }
            return getString(R$string.fq_achieve_he_gift_wall);
        }
        return getString(R$string.fq_achieve_top_50);
    }

    public void setAnchorEntity(AnchorEntity anchorEntity) {
        this.anchorEntity = anchorEntity;
    }
}
