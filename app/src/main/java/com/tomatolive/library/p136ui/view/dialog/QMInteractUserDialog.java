package com.tomatolive.library.p136ui.view.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.p002v4.content.ContextCompat;
import android.support.p005v7.widget.DefaultItemAnimator;
import android.support.p005v7.widget.GridLayoutManager;
import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.blankj.utilcode.util.ConvertUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.flexbox.FlexboxItemDecoration;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.tomatolive.library.R$array;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.download.GiftDownLoadManager;
import com.tomatolive.library.http.ApiRetrofit;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.function.HttpResultFunction;
import com.tomatolive.library.http.function.ServerResultFunction;
import com.tomatolive.library.model.GiftDownloadItemEntity;
import com.tomatolive.library.model.PopularCardEntity;
import com.tomatolive.library.model.QMInteractTaskConfigEntity;
import com.tomatolive.library.model.QMInteractTaskEntity;
import com.tomatolive.library.p136ui.adapter.HdGiftSelectAdapter;
import com.tomatolive.library.p136ui.adapter.QMInteractRecordAdapter;
import com.tomatolive.library.p136ui.adapter.QMTagAdapter;
import com.tomatolive.library.p136ui.interfaces.OnQMInteractCallback;
import com.tomatolive.library.p136ui.view.dialog.SpinnerDialog;
import com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment;
import com.tomatolive.library.p136ui.view.dialog.base.BaseRxDialogFragment;
import com.tomatolive.library.p136ui.view.divider.RVDividerGiftAdapter;
import com.tomatolive.library.p136ui.view.divider.RVDividerLinear;
import com.tomatolive.library.p136ui.view.emptyview.QMTaskListEmptyView;
import com.tomatolive.library.p136ui.view.headview.QMHdRecordHeadView;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.EmojiFilter;
import com.tomatolive.library.utils.GlideUtils;
import com.tomatolive.library.utils.NumberUtils;
import com.tomatolive.library.utils.StringUtils;
import com.tomatolive.library.utils.live.SimpleRxObserver;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/* renamed from: com.tomatolive.library.ui.view.dialog.QMInteractUserDialog */
/* loaded from: classes3.dex */
public class QMInteractUserDialog extends BaseDialogFragment {
    private String anchorId;
    private EditText etGiftNum;
    private EditText etTaskName;
    private View flTaskTagEmpty;
    private String giftMarkId;
    private String giftNum;
    private String giftPrice;
    private HdGiftSelectAdapter giftSelectAdapter;
    private ImageView ivBack;
    private ImageView ivDialogBg;
    private ImageView ivGiftRight;
    private ImageView ivTaskTagEmpty;
    private String liveId;
    private View llTabRecordBg;
    private LinearLayout llTagSelectBg;
    private View llTaskSendBg;
    private SpinnerDialog moreSpinnerDialog;
    private OnQMInteractCallback onQMInteractCallback;
    private QMInteractRecordAdapter qmInteractRecordAdapter;
    private QMTagAdapter qmRecommendTagAdapter;
    private View rlTaskTagBg;
    private RecyclerView rvGiftList;
    private RecyclerView rvRecordList;
    private RecyclerView rvTaskTagList;
    private String taskName;
    private TextView tvGiftName;
    private TextView tvGiftPriceTips;
    private TextView tvNowPractice;
    private TextView tvSendInvitation;
    private TextView tvTabTitle;
    private TextView tvTaskChange;
    private TextView tvTaskName;
    private WebView webView;
    private final int CONTENT_TYPE_TASK = 1;
    private final int CONTENT_TYPE_GIFT = 2;
    private final int CONTENT_TYPE_RECORD = 3;
    private final int CONTENT_TYPE_DESC = 4;
    private int contentType = 1;
    private long lowestGiftPrice = 600;

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public double getWidthScale() {
        return 0.8d;
    }

    public static QMInteractUserDialog newInstance(String str, String str2, OnQMInteractCallback onQMInteractCallback) {
        Bundle bundle = new Bundle();
        bundle.putString(ConstantUtils.RESULT_ID, str);
        bundle.putString(ConstantUtils.RESULT_ITEM, str2);
        QMInteractUserDialog qMInteractUserDialog = new QMInteractUserDialog();
        qMInteractUserDialog.setArguments(bundle);
        qMInteractUserDialog.setOnQMInteractCallback(onQMInteractCallback);
        return qMInteractUserDialog;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseRxDialogFragment
    public void getBundle(Bundle bundle) {
        super.getBundle(bundle);
        this.liveId = bundle.getString(ConstantUtils.RESULT_ID);
        this.anchorId = bundle.getString(ConstantUtils.RESULT_ITEM);
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    protected int getLayoutRes() {
        return R$layout.fq_dialog_qm_interact_user;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    protected void initView(View view) {
        this.ivDialogBg = (ImageView) view.findViewById(R$id.iv_dialog_bg);
        this.llTaskSendBg = view.findViewById(R$id.ll_task_send_bg);
        this.llTabRecordBg = view.findViewById(R$id.ll_tab_record_bg);
        this.etTaskName = (EditText) view.findViewById(R$id.et_task_name);
        this.llTagSelectBg = (LinearLayout) view.findViewById(R$id.ll_tag_select_bg);
        this.tvTaskName = (TextView) view.findViewById(R$id.tv_task_name);
        this.rlTaskTagBg = view.findViewById(R$id.rl_task_tag_bg);
        this.tvTaskChange = (TextView) view.findViewById(R$id.tv_task_change);
        this.rvTaskTagList = (RecyclerView) view.findViewById(R$id.rv_task_tag_list);
        this.flTaskTagEmpty = view.findViewById(R$id.fl_task_tag_empty);
        this.ivTaskTagEmpty = (ImageView) view.findViewById(R$id.iv_task_tag_empty);
        this.tvGiftName = (TextView) view.findViewById(R$id.tv_gift_name);
        this.etGiftNum = (EditText) view.findViewById(R$id.et_gift_num);
        this.ivGiftRight = (ImageView) view.findViewById(R$id.iv_gift_right);
        this.tvGiftPriceTips = (TextView) view.findViewById(R$id.tv_gift_price_tips);
        this.tvSendInvitation = (TextView) view.findViewById(R$id.tv_send_invitation);
        this.tvTabTitle = (TextView) view.findViewById(R$id.tv_tab_title);
        this.rvGiftList = (RecyclerView) view.findViewById(R$id.rv_gift_list);
        this.rvRecordList = (RecyclerView) view.findViewById(R$id.rv_record_list);
        this.tvNowPractice = (TextView) view.findViewById(R$id.tv_now_practice);
        this.webView = (WebView) view.findViewById(R$id.web_view);
        this.ivBack = (ImageView) view.findViewById(R$id.iv_back);
        this.webView.getSettings().setLoadWithOverviewMode(false);
        this.webView.getSettings().setUseWideViewPort(false);
        GlideUtils.loadRoundCornersImage(this.mContext, this.ivDialogBg, R$drawable.fq_ic_qm_dialog_bg, 10, RoundedCornersTransformation.CornerType.ALL);
        GlideUtils.loadRoundCornersImage(this.mContext, this.ivTaskTagEmpty, R$drawable.fq_ic_qm_task_tag_empty, 10, RoundedCornersTransformation.CornerType.ALL);
        showContentView(1);
        initGiftSelectedAdapter();
        initRecommendTagAdapter();
        initRecordListAdapter();
        initMorePopDialog();
        sendGetTaskConfigRequest();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public void initListener(View view) {
        super.initListener(view);
        this.ivBack.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.QMInteractUserDialog.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                QMInteractUserDialog.this.showContentView(1);
            }
        });
        this.tvNowPractice.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.QMInteractUserDialog.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                QMInteractUserDialog.this.showContentView(1);
            }
        });
        view.findViewById(R$id.tv_more).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.QMInteractUserDialog.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (QMInteractUserDialog.this.moreSpinnerDialog != null) {
                    QMInteractUserDialog.this.moreSpinnerDialog.showPopupWindow(view2);
                }
            }
        });
        view.findViewById(R$id.iv_dialog_close).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.QMInteractUserDialog.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                QMInteractUserDialog.this.dismiss();
            }
        });
        this.tvGiftName.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.QMInteractUserDialog.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                QMInteractUserDialog.this.showContentView(2);
            }
        });
        this.giftSelectAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.tomatolive.library.ui.view.dialog.QMInteractUserDialog.6
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
                GiftDownloadItemEntity giftDownloadItemEntity = (GiftDownloadItemEntity) baseQuickAdapter.getItem(i);
                if (giftDownloadItemEntity == null) {
                    return;
                }
                QMInteractUserDialog.this.giftSelectAdapter.setCheckItem(i);
                QMInteractUserDialog.this.giftPrice = giftDownloadItemEntity.price;
                QMInteractUserDialog.this.giftMarkId = giftDownloadItemEntity.markId;
                QMInteractUserDialog.this.tvGiftName.setText(QMInteractUserDialog.this.giftSelectAdapter.getGiftTips(giftDownloadItemEntity));
                QMInteractUserDialog.this.showContentView(1);
                QMInteractUserDialog.this.ivGiftRight.setVisibility(NumberUtils.mul(NumberUtils.string2Double(QMInteractUserDialog.this.giftPrice), NumberUtils.string2Double(QMInteractUserDialog.this.etGiftNum.getText().toString().trim())) >= ((double) QMInteractUserDialog.this.lowestGiftPrice) ? 0 : 4);
            }
        });
        RxTextView.textChanges(this.etGiftNum).map(new Function<CharSequence, Boolean>() { // from class: com.tomatolive.library.ui.view.dialog.QMInteractUserDialog.8
            @Override // io.reactivex.functions.Function
            /* renamed from: apply  reason: avoid collision after fix types in other method */
            public Boolean mo6755apply(CharSequence charSequence) throws Exception {
                boolean z = false;
                if (!TextUtils.isEmpty(QMInteractUserDialog.this.giftMarkId) && !TextUtils.isEmpty(QMInteractUserDialog.this.giftPrice)) {
                    if (NumberUtils.mul(NumberUtils.string2Double(QMInteractUserDialog.this.giftPrice), NumberUtils.string2Double(charSequence.toString())) >= QMInteractUserDialog.this.lowestGiftPrice) {
                        z = true;
                    }
                    return Boolean.valueOf(z);
                }
                return false;
            }
        }).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new Consumer<Boolean>() { // from class: com.tomatolive.library.ui.view.dialog.QMInteractUserDialog.7
            @Override // io.reactivex.functions.Consumer
            public void accept(Boolean bool) throws Exception {
                QMInteractUserDialog.this.ivGiftRight.setVisibility(bool.booleanValue() ? 0 : 4);
            }
        });
        this.tvSendInvitation.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.QMInteractUserDialog.9
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                QMInteractUserDialog qMInteractUserDialog = QMInteractUserDialog.this;
                qMInteractUserDialog.taskName = qMInteractUserDialog.etTaskName.getText().toString().trim();
                QMInteractUserDialog qMInteractUserDialog2 = QMInteractUserDialog.this;
                qMInteractUserDialog2.giftNum = qMInteractUserDialog2.etGiftNum.getText().toString().trim();
                if (!TextUtils.isEmpty(QMInteractUserDialog.this.taskName) && QMInteractUserDialog.this.taskName.length() >= 2 && QMInteractUserDialog.this.taskName.length() <= 15) {
                    if (!EmojiFilter.containsEmoji(QMInteractUserDialog.this.taskName)) {
                        if (!TextUtils.isEmpty(QMInteractUserDialog.this.giftMarkId)) {
                            if (!TextUtils.isEmpty(QMInteractUserDialog.this.giftNum)) {
                                if (NumberUtils.string2long(QMInteractUserDialog.this.giftNum) <= 9999) {
                                    if (NumberUtils.mul(NumberUtils.string2Double(QMInteractUserDialog.this.giftPrice), NumberUtils.string2Double(QMInteractUserDialog.this.etGiftNum.getText().toString().trim())) >= QMInteractUserDialog.this.lowestGiftPrice) {
                                        if (QMInteractUserDialog.this.onQMInteractCallback == null) {
                                            return;
                                        }
                                        QMInteractUserDialog.this.onQMInteractCallback.onSendInvitationListener(QMInteractUserDialog.this.taskName, QMInteractUserDialog.this.giftMarkId, QMInteractUserDialog.this.giftPrice, QMInteractUserDialog.this.giftNum);
                                        return;
                                    }
                                    QMInteractUserDialog.this.showToast(R$string.fq_qm_conform_lowest_gift_price_tips);
                                    return;
                                }
                                QMInteractUserDialog.this.showToast(R$string.fq_qm_count_empty_tips);
                                return;
                            }
                            QMInteractUserDialog.this.showToast(R$string.fq_qm_input_gift_num_tips);
                            return;
                        }
                        QMInteractUserDialog.this.showToast(R$string.fq_qm_select_gift_tips);
                        return;
                    }
                    QMInteractUserDialog.this.showToast(R$string.fq_qm_input_emoji_tips);
                    return;
                }
                QMInteractUserDialog.this.showToast(R$string.fq_qm_task_empty_tips_2_hint);
            }
        });
        this.qmRecommendTagAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.tomatolive.library.ui.view.dialog.QMInteractUserDialog.10
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
                QMInteractTaskEntity qMInteractTaskEntity = (QMInteractTaskEntity) baseQuickAdapter.getItem(i);
                if (qMInteractTaskEntity == null) {
                    return;
                }
                QMInteractUserDialog.this.qmRecommendTagAdapter.setCheckItem(i);
                QMInteractUserDialog.this.changeSelectTaskTag(qMInteractTaskEntity.taskName);
            }
        });
        view.findViewById(R$id.iv_tag_close).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.QMInteractUserDialog.11
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                QMInteractUserDialog.this.changeSelectTaskTag(null);
            }
        });
    }

    public OnQMInteractCallback getOnQMInteractCallback() {
        return this.onQMInteractCallback;
    }

    public void setOnQMInteractCallback(OnQMInteractCallback onQMInteractCallback) {
        this.onQMInteractCallback = onQMInteractCallback;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showContentView(int i) {
        SpinnerDialog spinnerDialog;
        this.contentType = i;
        int i2 = 0;
        this.llTaskSendBg.setVisibility(i == 1 ? 0 : 4);
        this.llTabRecordBg.setVisibility((i == 2 || i == 3 || i == 4) ? 0 : 4);
        this.rvGiftList.setVisibility(i == 2 ? 0 : 4);
        this.rvRecordList.setVisibility(i == 3 ? 0 : 4);
        this.webView.setVisibility(i == 4 ? 0 : 4);
        TextView textView = this.tvNowPractice;
        if (i != 4 && i != 3) {
            i2 = 4;
        }
        textView.setVisibility(i2);
        if (this.llTabRecordBg.getVisibility() == 0) {
            this.tvTabTitle.setText(getTabTitle());
        }
        if (i != 1 || (spinnerDialog = this.moreSpinnerDialog) == null) {
            return;
        }
        spinnerDialog.setSpinnerItemSelected(-1);
    }

    private String getTabTitle() {
        int i = this.contentType;
        if (i == 2) {
            this.ivBack.setImageResource(R$drawable.fq_ic_qm_back_red);
            this.tvTabTitle.setTextColor(ContextCompat.getColor(this.mContext, R$color.fq_qm_primary));
            return this.mContext.getString(R$string.fq_qm_add_gift_title);
        } else if (i == 3) {
            this.ivBack.setImageResource(R$drawable.fq_ic_achieve_back_black);
            this.tvTabTitle.setTextColor(ContextCompat.getColor(this.mContext, R$color.fq_text_black));
            return this.mContext.getString(R$string.fq_qm_hd_record_title);
        } else if (i == 4) {
            this.ivBack.setImageResource(R$drawable.fq_ic_achieve_back_black);
            this.tvTabTitle.setTextColor(ContextCompat.getColor(this.mContext, R$color.fq_text_black));
            return this.mContext.getString(R$string.fq_qm_play_desc_title);
        } else {
            this.ivBack.setImageResource(R$drawable.fq_ic_achieve_back_black);
            this.tvTabTitle.setTextColor(ContextCompat.getColor(this.mContext, R$color.fq_text_black));
            return "";
        }
    }

    private void initMorePopDialog() {
        this.moreSpinnerDialog = new SpinnerDialog(this.mContext, Arrays.asList(this.mContext.getResources().getStringArray(R$array.fq_qm_more_menu)));
        this.moreSpinnerDialog.setBackground(0);
        this.moreSpinnerDialog.setWidth(ConvertUtils.dp2px(86.0f));
        this.moreSpinnerDialog.setOnSpinnerItemClickListener(new SpinnerDialog.OnSpinnerItemClickListener() { // from class: com.tomatolive.library.ui.view.dialog.QMInteractUserDialog.12
            @Override // com.tomatolive.library.p136ui.view.dialog.SpinnerDialog.OnSpinnerItemClickListener
            public void onItemClick(String str, int i) {
                if (i == 0) {
                    QMInteractUserDialog.this.showContentView(4);
                    QMInteractUserDialog.this.sendDescRequest();
                } else if (i != 1) {
                } else {
                    QMInteractUserDialog.this.showContentView(3);
                    QMInteractUserDialog.this.sendHDRecordRequest();
                }
            }
        });
    }

    private void initGiftSelectedAdapter() {
        this.giftSelectAdapter = new HdGiftSelectAdapter(R$layout.fq_item_grid_hd_gift_select, true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.mContext, 4);
        RVDividerGiftAdapter rVDividerGiftAdapter = new RVDividerGiftAdapter(this.mContext, R$color.fq_qm_primary_light);
        this.rvGiftList.setAdapter(this.giftSelectAdapter);
        this.rvGiftList.setLayoutManager(gridLayoutManager);
        this.rvGiftList.addItemDecoration(rVDividerGiftAdapter);
        this.giftSelectAdapter.bindToRecyclerView(this.rvGiftList);
        sendGiftRequest(false);
    }

    private void initRecordListAdapter() {
        ((DefaultItemAnimator) this.rvRecordList.getItemAnimator()).setSupportsChangeAnimations(false);
        this.rvRecordList.setLayoutManager(new LinearLayoutManager(this.mContext));
        this.rvRecordList.addItemDecoration(new RVDividerLinear(this.mContext, R$color.fq_colorWhite, 15.0f));
        this.qmInteractRecordAdapter = new QMInteractRecordAdapter(R$layout.fq_item_list_qm_hd_record);
        this.rvRecordList.setAdapter(this.qmInteractRecordAdapter);
        this.qmInteractRecordAdapter.bindToRecyclerView(this.rvRecordList);
        this.qmInteractRecordAdapter.addHeaderView(new QMHdRecordHeadView(this.mContext));
        this.qmInteractRecordAdapter.setEmptyView(new QMTaskListEmptyView(this.mContext, 58));
    }

    private void initRecommendTagAdapter() {
        ((DefaultItemAnimator) this.rvTaskTagList.getItemAnimator()).setSupportsChangeAnimations(false);
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(this.mContext);
        flexboxLayoutManager.setFlexDirection(0);
        flexboxLayoutManager.setFlexWrap(1);
        flexboxLayoutManager.setJustifyContent(0);
        flexboxLayoutManager.setAlignItems(4);
        new FlexboxItemDecoration(this.mContext).setDrawable(ContextCompat.getDrawable(this.mContext, R$drawable.fq_qm_list_item_divider));
        this.rvTaskTagList.setLayoutManager(flexboxLayoutManager);
        this.qmRecommendTagAdapter = new QMTagAdapter(R$layout.fq_item_list_qm_recommend_tag);
        this.rvTaskTagList.setAdapter(this.qmRecommendTagAdapter);
        this.qmRecommendTagAdapter.bindToRecyclerView(this.rvTaskTagList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeSelectTaskTag(String str) {
        this.taskName = str;
        if (TextUtils.isEmpty(str)) {
            this.llTagSelectBg.setVisibility(8);
            this.etTaskName.setVisibility(0);
            this.etTaskName.setText("");
            this.etTaskName.setEnabled(true);
            this.tvTaskName.setText("");
            this.qmRecommendTagAdapter.setCheckItem(-1);
            return;
        }
        this.llTagSelectBg.setVisibility(0);
        this.etTaskName.setVisibility(8);
        this.etTaskName.setEnabled(false);
        this.etTaskName.setText(str);
        this.tvTaskName.setText(str);
    }

    public void resetTask() {
        this.taskName = "";
        this.giftNum = "";
        this.etTaskName.setText("");
        this.etGiftNum.setText("");
        this.qmRecommendTagAdapter.setCheckItem(-1);
        this.ivGiftRight.setVisibility(4);
        this.llTagSelectBg.setVisibility(8);
        this.etTaskName.setVisibility(0);
        setDefGiftInfo();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setDefGiftInfo() {
        List<GiftDownloadItemEntity> data = this.giftSelectAdapter.getData();
        if (data != null && !data.isEmpty()) {
            GiftDownloadItemEntity giftDownloadItemEntity = data.get(0);
            this.giftSelectAdapter.setCheckItem(0);
            this.giftPrice = giftDownloadItemEntity.price;
            this.giftMarkId = giftDownloadItemEntity.markId;
            TextView textView = this.tvGiftName;
            textView.setText(StringUtils.formatStrLen(giftDownloadItemEntity.name, 5) + "   " + AppUtils.getLiveMoneyUnitStr(this.mContext, AppUtils.formatDisplayPrice(giftDownloadItemEntity.price, false)));
            return;
        }
        this.giftSelectAdapter.setCheckItem(-1);
        this.giftMarkId = "";
        this.giftPrice = "";
        this.tvGiftName.setText("");
    }

    private void sendGetTaskConfigRequest() {
        ApiRetrofit.getInstance().getApiService().getTaskConfigService(new RequestParams().getAnchorIdParams(this.anchorId)).map(new ServerResultFunction<QMInteractTaskConfigEntity>() { // from class: com.tomatolive.library.ui.view.dialog.QMInteractUserDialog.14
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new SimpleRxObserver<QMInteractTaskConfigEntity>() { // from class: com.tomatolive.library.ui.view.dialog.QMInteractUserDialog.13
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                super.onSubscribe(disposable);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(QMInteractTaskConfigEntity qMInteractTaskConfigEntity) {
                List<QMInteractTaskEntity> list;
                if (qMInteractTaskConfigEntity == null || (list = qMInteractTaskConfigEntity.intimateTaskAnchorList) == null) {
                    return;
                }
                QMInteractUserDialog.this.lowestGiftPrice = NumberUtils.string2long(qMInteractTaskConfigEntity.threshold) * 100;
                TextView textView = QMInteractUserDialog.this.tvGiftPriceTips;
                Context context = ((BaseRxDialogFragment) QMInteractUserDialog.this).mContext;
                int i = R$string.fq_qm_target_gift_price_tips;
                textView.setText(Html.fromHtml(context.getString(i, AppUtils.formatDisplayPrice(String.valueOf(QMInteractUserDialog.this.lowestGiftPrice), false) + ((BaseRxDialogFragment) QMInteractUserDialog.this).mContext.getString(R$string.fq_qm_gift_price_unit))));
                QMInteractUserDialog.this.qmRecommendTagAdapter.setNewData(list);
                QMInteractUserDialog.this.setTaskTagEmptyView();
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onError(Throwable th) {
                super.onError(th);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendHDRecordRequest() {
        ApiRetrofit.getInstance().getApiService().userRecordListService(new RequestParams().getDefaultParams()).map(new ServerResultFunction<List<QMInteractTaskEntity>>() { // from class: com.tomatolive.library.ui.view.dialog.QMInteractUserDialog.16
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new SimpleRxObserver<List<QMInteractTaskEntity>>() { // from class: com.tomatolive.library.ui.view.dialog.QMInteractUserDialog.15
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                super.onSubscribe(disposable);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(List<QMInteractTaskEntity> list) {
                if (list == null) {
                    return;
                }
                QMInteractUserDialog.this.qmInteractRecordAdapter.setNewData(list);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onError(Throwable th) {
                super.onError(th);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendDescRequest() {
        ApiRetrofit.getInstance().getApiService().getAppParamConfigService(new RequestParams().getCodeParams(ConstantUtils.APP_PARAM_QM_RULE)).map(new ServerResultFunction<PopularCardEntity>() { // from class: com.tomatolive.library.ui.view.dialog.QMInteractUserDialog.18
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new SimpleRxObserver<PopularCardEntity>() { // from class: com.tomatolive.library.ui.view.dialog.QMInteractUserDialog.17
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                super.onSubscribe(disposable);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(PopularCardEntity popularCardEntity) {
                if (popularCardEntity == null) {
                    return;
                }
                QMInteractUserDialog.this.webView.loadDataWithBaseURL(null, popularCardEntity.value, "text/html", "UTF-8", null);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onError(Throwable th) {
                super.onError(th);
            }
        });
    }

    public void sendGiftRequest(boolean z) {
        List<GiftDownloadItemEntity> localDownloadListFilterLuckyScoreGift = GiftDownLoadManager.getInstance().getLocalDownloadListFilterLuckyScoreGift();
        if (z || localDownloadListFilterLuckyScoreGift == null || localDownloadListFilterLuckyScoreGift.isEmpty()) {
            ApiRetrofit.getInstance().getApiService().giftListV2(new RequestParams().getDefaultParams()).map(new ServerResultFunction<List<GiftDownloadItemEntity>>() { // from class: com.tomatolive.library.ui.view.dialog.QMInteractUserDialog.22
            }).flatMap(new Function<List<GiftDownloadItemEntity>, ObservableSource<List<GiftDownloadItemEntity>>>() { // from class: com.tomatolive.library.ui.view.dialog.QMInteractUserDialog.21
                @Override // io.reactivex.functions.Function
                /* renamed from: apply  reason: avoid collision after fix types in other method */
                public ObservableSource<List<GiftDownloadItemEntity>> mo6755apply(List<GiftDownloadItemEntity> list) throws Exception {
                    GiftDownLoadManager.getInstance().setLocalDownloadList(list);
                    return Observable.just(QMInteractUserDialog.this.formatListFilterLuckyScoreGift(list));
                }
            }).onErrorResumeNext(new HttpResultFunction<List<GiftDownloadItemEntity>>() { // from class: com.tomatolive.library.ui.view.dialog.QMInteractUserDialog.20
            }).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<List<GiftDownloadItemEntity>>() { // from class: com.tomatolive.library.ui.view.dialog.QMInteractUserDialog.19
                @Override // com.tomatolive.library.utils.live.SimpleRxObserver
                public void accept(List<GiftDownloadItemEntity> list) {
                    if (list == null) {
                        return;
                    }
                    QMInteractUserDialog.this.giftSelectAdapter.setNewData(list);
                    QMInteractUserDialog.this.setDefGiftInfo();
                }
            });
        } else {
            Observable.just(GiftDownLoadManager.getInstance().getLocalDownloadListFilterLuckyScoreGift()).flatMap(new Function<List<GiftDownloadItemEntity>, ObservableSource<List<GiftDownloadItemEntity>>>() { // from class: com.tomatolive.library.ui.view.dialog.QMInteractUserDialog.24
                @Override // io.reactivex.functions.Function
                /* renamed from: apply  reason: avoid collision after fix types in other method */
                public ObservableSource<List<GiftDownloadItemEntity>> mo6755apply(List<GiftDownloadItemEntity> list) throws Exception {
                    return Observable.just(QMInteractUserDialog.this.formatGiftList(list));
                }
            }).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<GiftDownloadItemEntity>>() { // from class: com.tomatolive.library.ui.view.dialog.QMInteractUserDialog.23
                @Override // io.reactivex.functions.Consumer
                public void accept(List<GiftDownloadItemEntity> list) throws Exception {
                    if (list == null) {
                        return;
                    }
                    QMInteractUserDialog.this.giftSelectAdapter.setNewData(list);
                    QMInteractUserDialog.this.setDefGiftInfo();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<GiftDownloadItemEntity> formatListFilterLuckyScoreGift(List<GiftDownloadItemEntity> list) {
        try {
            ArrayList arrayList = new ArrayList();
            for (GiftDownloadItemEntity giftDownloadItemEntity : list) {
                if (!giftDownloadItemEntity.isLuckyGift() && !giftDownloadItemEntity.isScoreGift()) {
                    arrayList.add(giftDownloadItemEntity);
                }
            }
            return formatGiftList(arrayList);
        } catch (Exception unused) {
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<GiftDownloadItemEntity> formatGiftList(List<GiftDownloadItemEntity> list) {
        int size;
        ArrayList arrayList = new ArrayList(list);
        if (!arrayList.isEmpty() && (size = arrayList.size() % 4) > 0) {
            int i = 4 - size;
            for (int i2 = 0; i2 < i; i2++) {
                arrayList.add(null);
            }
            return arrayList;
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setTaskTagEmptyView() {
        int i = 4;
        this.rlTaskTagBg.setVisibility(this.qmRecommendTagAdapter.getData().isEmpty() ? 4 : 0);
        View view = this.flTaskTagEmpty;
        if (this.qmRecommendTagAdapter.getData().isEmpty()) {
            i = 0;
        }
        view.setVisibility(i);
    }
}
