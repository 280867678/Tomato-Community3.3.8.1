package com.tomatolive.library.p136ui.view.dialog;

import android.os.Bundle;
import android.support.p005v7.widget.DefaultItemAnimator;
import android.support.p005v7.widget.GridLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.blankj.utilcode.util.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.tomatolive.library.R$array;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.http.ApiRetrofit;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.function.HttpResultFunction;
import com.tomatolive.library.http.function.ServerResultFunction;
import com.tomatolive.library.model.LiveEvaluationEntity;
import com.tomatolive.library.model.MenuEntity;
import com.tomatolive.library.model.PopularCardEntity;
import com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment;
import com.tomatolive.library.p136ui.view.dialog.base.BaseRxDialogFragment;
import com.tomatolive.library.p136ui.view.divider.RVDividerGridItem4;
import com.tomatolive.library.p136ui.view.widget.Html5WebView;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.DateUtils;
import com.tomatolive.library.utils.NumberUtils;
import com.tomatolive.library.utils.live.SimpleRxObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.dialog.LiveEndEvaluationDialog */
/* loaded from: classes3.dex */
public class LiveEndEvaluationDialog extends BaseDialogFragment {
    public static final String CHARGE_TYPE = "chargeType";
    public static final String LIVE_COUNT = "liveCount";
    public static final String LIVE_ID = "liveId";
    public static final String TICKET_PRICE = "ticketPrice";
    public static final String WATCH_DURATION = "watchDuration";
    private String chargeType;
    private EditText etContent;
    private ImageView[] imgs;
    private ImageView ivBack;
    private ImageView ivClose;
    private String liveCount;
    private String liveId;
    private LinearLayout llContentBg;
    private RecyclerAdapter mAdapter;
    private ImageView mImgStar0;
    private ImageView mImgStar1;
    private ImageView mImgStar2;
    private ImageView mImgStar3;
    private ImageView mImgStar4;
    private View.OnClickListener mImgStarClickListener;
    private RecyclerView mRecycleView;
    private TextView mTvScoreDescription;
    private TextView tvDialogTitle;
    private TextView tvOtherComment;
    private TextView tvRuleDesc;
    private TextView tvScoreResult;
    private TextView tvSubmit;
    private TextView tvTicketPrice;
    private TextView tvWatchTime;
    private long watchStartTimes;
    private Html5WebView webView;
    private String ticketPrice = "0";
    private String score = "5";
    private long watchDuration = 0;
    private long submitWatchDuration = 0;
    private int maxTextLen = 150;
    private final int CONTENT_TYPE_SUBMIT = 1;
    private final int CONTENT_TYPE_RULE = 2;
    private int contentType = 1;

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public boolean getCancelOutside() {
        return true;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public double getWidthScale() {
        return 0.8d;
    }

    public static LiveEndEvaluationDialog newInstance(String str, String str2, String str3, long j, String str4) {
        Bundle bundle = new Bundle();
        LiveEndEvaluationDialog liveEndEvaluationDialog = new LiveEndEvaluationDialog();
        bundle.putString("liveId", str);
        bundle.putString("liveCount", str2);
        bundle.putString(CHARGE_TYPE, str3);
        bundle.putLong(WATCH_DURATION, j);
        bundle.putString(TICKET_PRICE, str4);
        liveEndEvaluationDialog.setArguments(bundle);
        return liveEndEvaluationDialog;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseRxDialogFragment
    public void getBundle(Bundle bundle) {
        super.getBundle(bundle);
        this.liveId = bundle.getString("liveId");
        this.liveCount = bundle.getString("liveCount");
        this.chargeType = bundle.getString(CHARGE_TYPE);
        this.ticketPrice = bundle.getString(TICKET_PRICE);
        this.watchStartTimes = bundle.getLong(WATCH_DURATION, 0L);
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public int getLayoutRes() {
        return R$layout.fq_dialog_live_end_evaluation;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public void initView(View view) {
        this.llContentBg = (LinearLayout) view.findViewById(R$id.ll_content_bg);
        this.mTvScoreDescription = (TextView) view.findViewById(R$id.fq_text_score_description);
        this.tvWatchTime = (TextView) view.findViewById(R$id.tv_watch_time);
        this.tvTicketPrice = (TextView) view.findViewById(R$id.tv_ticket_price);
        this.tvOtherComment = (TextView) view.findViewById(R$id.tv_other_comment);
        this.mRecycleView = (RecyclerView) view.findViewById(R$id.fq_rv_comment);
        this.tvScoreResult = (TextView) view.findViewById(R$id.tv_score_result);
        this.tvSubmit = (TextView) view.findViewById(R$id.tv_submit);
        this.tvDialogTitle = (TextView) view.findViewById(R$id.tv_dialog_title);
        this.mImgStar0 = (ImageView) view.findViewById(R$id.fq_img_star_0);
        this.mImgStar1 = (ImageView) view.findViewById(R$id.fq_img_star_1);
        this.mImgStar2 = (ImageView) view.findViewById(R$id.fq_img_star_2);
        this.mImgStar3 = (ImageView) view.findViewById(R$id.fq_img_star_3);
        this.mImgStar4 = (ImageView) view.findViewById(R$id.fq_img_star_4);
        this.ivBack = (ImageView) view.findViewById(R$id.iv_back);
        this.ivClose = (ImageView) view.findViewById(R$id.iv_close);
        this.etContent = (EditText) view.findViewById(R$id.et_content);
        this.tvRuleDesc = (TextView) view.findViewById(R$id.tv_rule_desc);
        this.webView = (Html5WebView) view.findViewById(R$id.web_view);
        this.imgs = new ImageView[]{this.mImgStar0, this.mImgStar1, this.mImgStar2, this.mImgStar3, this.mImgStar4};
        this.mImgStarClickListener = new ImgStarClickListener();
        int i = 0;
        while (true) {
            ImageView[] imageViewArr = this.imgs;
            if (i < imageViewArr.length) {
                ImageView imageView = imageViewArr[i];
                imageView.setTag(Integer.valueOf(i));
                imageView.setOnClickListener(this.mImgStarClickListener);
                i++;
            } else {
                initAdapter();
                this.webView.getSettings().setLoadWithOverviewMode(false);
                this.webView.getSettings().setUseWideViewPort(false);
                this.llContentBg.post(new Runnable() { // from class: com.tomatolive.library.ui.view.dialog.LiveEndEvaluationDialog.1
                    @Override // java.lang.Runnable
                    public void run() {
                        int measuredHeight = LiveEndEvaluationDialog.this.llContentBg.getMeasuredHeight();
                        if (measuredHeight > 0) {
                            ViewGroup.LayoutParams layoutParams = LiveEndEvaluationDialog.this.webView.getLayoutParams();
                            layoutParams.height = measuredHeight;
                            LiveEndEvaluationDialog.this.webView.setLayoutParams(layoutParams);
                        }
                    }
                });
                showContentView(1);
                sendLiveEvaluationRequest();
                sendDescRequest();
                return;
            }
        }
    }

    @Override // com.trello.rxlifecycle2.components.support.RxDialogFragment, android.support.p002v4.app.Fragment
    public void onResume() {
        super.onResume();
        this.tvWatchTime.setText(formatWatchTime());
        this.tvTicketPrice.setText(AppUtils.formatMoneyUnitStr(this.mContext, this.ticketPrice, true));
        this.tvOtherComment.setText(this.mContext.getString(R$string.fq_text_other_comment, String.valueOf(this.maxTextLen)));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public void initListener(View view) {
        super.initListener(view);
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.tomatolive.library.ui.view.dialog.LiveEndEvaluationDialog.2
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
                MenuEntity menuEntity = (MenuEntity) baseQuickAdapter.getItem(i);
                if (menuEntity == null) {
                    return;
                }
                LiveEndEvaluationDialog.this.mAdapter.switchSelectPosition(String.valueOf(i), menuEntity);
            }
        });
        this.ivClose.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.LiveEndEvaluationDialog.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                LiveEndEvaluationDialog.this.dismiss();
            }
        });
        this.tvSubmit.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.LiveEndEvaluationDialog.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                LiveEndEvaluationDialog.this.sendRequest();
            }
        });
        RxTextView.textChanges(this.etContent).map(new Function<CharSequence, Integer>() { // from class: com.tomatolive.library.ui.view.dialog.LiveEndEvaluationDialog.6
            @Override // io.reactivex.functions.Function
            /* renamed from: apply  reason: avoid collision after fix types in other method */
            public Integer mo6755apply(CharSequence charSequence) throws Exception {
                return Integer.valueOf(charSequence.length());
            }
        }).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<Integer>() { // from class: com.tomatolive.library.ui.view.dialog.LiveEndEvaluationDialog.5
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(Integer num) {
                LiveEndEvaluationDialog.this.tvOtherComment.setText(((BaseRxDialogFragment) LiveEndEvaluationDialog.this).mContext.getString(R$string.fq_text_other_comment, String.valueOf(LiveEndEvaluationDialog.this.maxTextLen - num.intValue())));
            }
        });
        this.tvRuleDesc.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.LiveEndEvaluationDialog.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                LiveEndEvaluationDialog.this.showContentView(2);
            }
        });
        this.ivBack.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.LiveEndEvaluationDialog.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                LiveEndEvaluationDialog.this.showContentView(1);
            }
        });
    }

    private void initAdapter() {
        ((DefaultItemAnimator) this.mRecycleView.getItemAnimator()).setSupportsChangeAnimations(false);
        this.mRecycleView.setLayoutManager(new GridLayoutManager(this.mContext, 4));
        this.mAdapter = new RecyclerAdapter(R$layout.fq_item_comment, false);
        this.mRecycleView.addItemDecoration(new RVDividerGridItem4(this.mContext, 17170445));
        this.mRecycleView.setAdapter(this.mAdapter);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showContentView(int i) {
        int i2 = 0;
        this.llContentBg.setVisibility(i == 1 ? 0 : 4);
        this.tvRuleDesc.setVisibility(i == 1 ? 0 : 4);
        this.ivClose.setVisibility(i == 1 ? 0 : 4);
        this.webView.setVisibility(i == 2 ? 0 : 4);
        ImageView imageView = this.ivBack;
        if (i != 2) {
            i2 = 4;
        }
        imageView.setVisibility(i2);
        this.tvDialogTitle.setText(i == 1 ? R$string.fq_pay_live_score_title : R$string.fq_week_star_rule_desc);
    }

    private String formatWatchTime() {
        long currentTimeMillis = System.currentTimeMillis();
        long j = this.watchStartTimes;
        this.watchDuration = currentTimeMillis - j;
        if (j > 0) {
            long j2 = this.watchDuration;
            if (j2 <= 0) {
                return "00'00'";
            }
            this.submitWatchDuration = j2 / 1000;
            return DateUtils.millisecond2TimeMinute(j2);
        }
        return "00'00'";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<MenuEntity> getDescList(int i) {
        if (i != 0) {
            if (i == 1) {
                return formatList(this.mContext.getResources().getStringArray(R$array.fq_live_end_score_2_tips));
            }
            if (i == 2) {
                return formatList(this.mContext.getResources().getStringArray(R$array.fq_live_end_score_3_tips));
            }
            if (i == 3) {
                return formatList(this.mContext.getResources().getStringArray(R$array.fq_live_end_score_4_tips));
            }
            if (i == 4) {
                return formatList(this.mContext.getResources().getStringArray(R$array.fq_live_end_score_5_tips));
            }
            return formatList(this.mContext.getResources().getStringArray(R$array.fq_live_end_score_5_tips));
        }
        return formatList(this.mContext.getResources().getStringArray(R$array.fq_live_end_score_1_tips));
    }

    private List<MenuEntity> formatList(String[] strArr) {
        ArrayList arrayList = new ArrayList();
        for (String str : strArr) {
            MenuEntity menuEntity = new MenuEntity();
            menuEntity.menuTitle = str;
            menuEntity.isSelected = false;
            arrayList.add(menuEntity);
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<MenuEntity> formatSelectDescList(int i, String str) {
        if (i != 0) {
            if (i == 1) {
                return formatList(this.mContext.getResources().getStringArray(R$array.fq_live_end_score_2_tips), str);
            }
            if (i == 2) {
                return formatList(this.mContext.getResources().getStringArray(R$array.fq_live_end_score_3_tips), str);
            }
            if (i == 3) {
                return formatList(this.mContext.getResources().getStringArray(R$array.fq_live_end_score_4_tips), str);
            }
            if (i == 4) {
                return formatList(this.mContext.getResources().getStringArray(R$array.fq_live_end_score_5_tips), str);
            }
            return formatList(this.mContext.getResources().getStringArray(R$array.fq_live_end_score_5_tips), str);
        }
        return formatList(this.mContext.getResources().getStringArray(R$array.fq_live_end_score_1_tips), str);
    }

    private List<MenuEntity> formatList(String[] strArr, String str) {
        ArrayList arrayList = new ArrayList();
        for (String str2 : strArr) {
            MenuEntity menuEntity = new MenuEntity();
            menuEntity.menuTitle = str2;
            menuEntity.isSelected = !TextUtils.isEmpty(str) && str.contains(str2);
            arrayList.add(menuEntity);
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getScoreDesc(int i) {
        return this.mContext.getResources().getStringArray(R$array.fq_live_end_score_menu)[i];
    }

    private List<String> getTagList() {
        return this.mAdapter.getSelectTagList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendRequest() {
        String trim = this.etContent.getText().toString().trim();
        List<String> tagList = getTagList();
        LogUtils.json(tagList);
        ApiRetrofit.getInstance().getApiService().getLiveEndEvaluationService(new RequestParams().getLiveEndEvaluationParams(this.liveId, this.liveCount, this.chargeType, this.submitWatchDuration, this.score, trim, tagList)).map(new ServerResultFunction<Object>() { // from class: com.tomatolive.library.ui.view.dialog.LiveEndEvaluationDialog.10
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new SimpleRxObserver<Object>() { // from class: com.tomatolive.library.ui.view.dialog.LiveEndEvaluationDialog.9
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(Object obj) {
                LiveEndEvaluationDialog.this.showToast(R$string.fq_submit_suc);
                LiveEndEvaluationDialog.this.onSubmitInfo(true);
                LiveEndEvaluationDialog.this.dismiss();
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onError(Throwable th) {
                super.onError(th);
                LiveEndEvaluationDialog.this.dismiss();
            }
        });
    }

    private void sendLiveEvaluationRequest() {
        ApiRetrofit.getInstance().getApiService().getLiveEvaluationService(new RequestParams().getTicketRoomBaseInfoParams(this.liveId, this.liveCount)).map(new ServerResultFunction<LiveEvaluationEntity>() { // from class: com.tomatolive.library.ui.view.dialog.LiveEndEvaluationDialog.12
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new SimpleRxObserver<LiveEvaluationEntity>() { // from class: com.tomatolive.library.ui.view.dialog.LiveEndEvaluationDialog.11
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(LiveEvaluationEntity liveEvaluationEntity) {
                if (liveEvaluationEntity == null || TextUtils.isEmpty(liveEvaluationEntity.score)) {
                    LiveEndEvaluationDialog.this.mAdapter.setNewData(LiveEndEvaluationDialog.this.getDescList(4));
                    LiveEndEvaluationDialog.this.mTvScoreDescription.setText(LiveEndEvaluationDialog.this.getScoreDesc(4));
                    return;
                }
                int string2int = NumberUtils.string2int(liveEvaluationEntity.score, 5) - 1;
                LiveEndEvaluationDialog.this.mAdapter.setNewDataList(LiveEndEvaluationDialog.this.formatSelectDescList(string2int, liveEvaluationEntity.tags));
                LiveEndEvaluationDialog.this.mTvScoreDescription.setText(LiveEndEvaluationDialog.this.getScoreDesc(string2int));
                for (int i = 0; i < LiveEndEvaluationDialog.this.imgs.length; i++) {
                    ImageView imageView = LiveEndEvaluationDialog.this.imgs[i];
                    if (i <= string2int) {
                        imageView.setImageResource(R$drawable.fq_ic_pay_score_on);
                    } else {
                        imageView.setImageResource(R$drawable.fq_ic_pay_score_off);
                    }
                }
                LiveEndEvaluationDialog.this.etContent.setText(liveEvaluationEntity.evaluationContent);
                LiveEndEvaluationDialog.this.onSubmitInfo(true);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onError(Throwable th) {
                super.onError(th);
                LiveEndEvaluationDialog.this.mAdapter.setNewData(LiveEndEvaluationDialog.this.getDescList(4));
                LiveEndEvaluationDialog.this.mTvScoreDescription.setText(LiveEndEvaluationDialog.this.getScoreDesc(4));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSubmitInfo(boolean z) {
        float f;
        this.tvScoreResult.setText(z ? R$string.fq_pay_score_result : R$string.fq_text_rate_this_live);
        int i = 0;
        int i2 = 0;
        while (true) {
            ImageView[] imageViewArr = this.imgs;
            f = 1.0f;
            if (i2 >= imageViewArr.length) {
                break;
            }
            ImageView imageView = imageViewArr[i2];
            if (z) {
                f = 0.4f;
            }
            imageView.setAlpha(f);
            imageView.setEnabled(!z);
            i2++;
        }
        this.mTvScoreDescription.setAlpha(z ? 0.4f : 1.0f);
        this.tvSubmit.setEnabled(!z);
        this.tvSubmit.setText(z ? R$string.fq_pay_submit_yes : R$string.fq_pay_anonymous_submit);
        EditText editText = this.etContent;
        if (!z) {
            i = R$drawable.fq_shape_anchor_pay_et_bg;
        }
        editText.setBackgroundResource(i);
        this.etContent.setEnabled(!z);
        RecyclerView recyclerView = this.mRecycleView;
        if (z) {
            f = 0.4f;
        }
        recyclerView.setAlpha(f);
        this.mRecycleView.setEnabled(!z);
        this.mAdapter.setTagEnable(z);
    }

    private void sendDescRequest() {
        ApiRetrofit.getInstance().getApiService().getAppParamConfigService(new RequestParams().getCodeParams(ConstantUtils.APP_PARAM_PAY_LIVE)).map(new ServerResultFunction<PopularCardEntity>() { // from class: com.tomatolive.library.ui.view.dialog.LiveEndEvaluationDialog.14
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<PopularCardEntity>() { // from class: com.tomatolive.library.ui.view.dialog.LiveEndEvaluationDialog.13
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                super.onSubscribe(disposable);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(PopularCardEntity popularCardEntity) {
                if (popularCardEntity == null) {
                    return;
                }
                LiveEndEvaluationDialog.this.webView.loadDataWithBaseURL(null, popularCardEntity.value, "text/html", "UTF-8", null);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onError(Throwable th) {
                super.onError(th);
            }
        });
    }

    /* renamed from: com.tomatolive.library.ui.view.dialog.LiveEndEvaluationDialog$ImgStarClickListener */
    /* loaded from: classes3.dex */
    private class ImgStarClickListener implements View.OnClickListener {
        private ImgStarClickListener() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            int intValue = ((Integer) view.getTag()).intValue();
            for (int i = 0; i < LiveEndEvaluationDialog.this.imgs.length; i++) {
                ImageView imageView = LiveEndEvaluationDialog.this.imgs[i];
                if (i <= intValue) {
                    imageView.setImageResource(R$drawable.fq_ic_pay_score_on);
                } else {
                    imageView.setImageResource(R$drawable.fq_ic_pay_score_off);
                }
            }
            LiveEndEvaluationDialog.this.mAdapter.clearTagList();
            LiveEndEvaluationDialog.this.mAdapter.setNewData(LiveEndEvaluationDialog.this.getDescList(intValue));
            LiveEndEvaluationDialog.this.mTvScoreDescription.setText(LiveEndEvaluationDialog.this.getScoreDesc(intValue));
            LiveEndEvaluationDialog.this.score = String.valueOf(intValue + 1);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: com.tomatolive.library.ui.view.dialog.LiveEndEvaluationDialog$RecyclerAdapter */
    /* loaded from: classes3.dex */
    public class RecyclerAdapter extends BaseQuickAdapter<MenuEntity, BaseViewHolder> {
        private boolean isTagEnable;
        private List<String> checkPosList = new ArrayList();
        private List<String> selectTagList = new ArrayList();

        public RecyclerAdapter(int i, boolean z) {
            super(i);
            this.isTagEnable = z;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(BaseViewHolder baseViewHolder, MenuEntity menuEntity) {
            boolean z = this.checkPosList.contains(String.valueOf(baseViewHolder.getAdapterPosition())) && menuEntity.isSelected;
            TextView textView = (TextView) baseViewHolder.getView(R$id.fq_tv_comment);
            textView.setText(menuEntity.menuTitle);
            textView.setSelected(z);
            textView.setEnabled(!this.isTagEnable);
        }

        public boolean isTagEnable() {
            return this.isTagEnable;
        }

        public void setTagEnable(boolean z) {
            this.isTagEnable = z;
            notifyDataSetChanged();
        }

        public void switchSelectPosition(String str, MenuEntity menuEntity) {
            if (this.checkPosList.contains(str)) {
                this.checkPosList.remove(str);
                this.selectTagList.remove(menuEntity.menuTitle);
                menuEntity.setSelected(false);
            } else {
                if (this.checkPosList.size() == 3) {
                    this.checkPosList.remove(0);
                    this.selectTagList.remove(0);
                    menuEntity.setSelected(false);
                }
                this.checkPosList.add(str);
                this.selectTagList.add(menuEntity.menuTitle);
                menuEntity.setSelected(true);
            }
            notifyDataSetChanged();
        }

        public List<String> getSelectTagList() {
            return this.selectTagList;
        }

        public void clearTagList() {
            this.checkPosList.clear();
            this.selectTagList.clear();
        }

        public void setNewDataList(List<MenuEntity> list) {
            if (list == null || list.isEmpty()) {
                return;
            }
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).isSelected) {
                    this.checkPosList.add(String.valueOf(i));
                    this.selectTagList.add(list.get(i).menuTitle);
                }
            }
            setNewData(list);
        }
    }
}
