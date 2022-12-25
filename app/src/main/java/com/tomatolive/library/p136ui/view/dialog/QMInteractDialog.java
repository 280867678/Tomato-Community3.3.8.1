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
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.flexbox.FlexboxItemDecoration;
import com.google.android.flexbox.FlexboxLayoutManager;
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
import com.tomatolive.library.model.QMInteractTaskConfigEntity;
import com.tomatolive.library.model.QMInteractTaskEntity;
import com.tomatolive.library.p136ui.adapter.HdGiftSelectAdapter;
import com.tomatolive.library.p136ui.adapter.QMInteractTaskAdapter;
import com.tomatolive.library.p136ui.adapter.QMTagAdapter;
import com.tomatolive.library.p136ui.adapter.QMTaskTagAdapter;
import com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment;
import com.tomatolive.library.p136ui.view.divider.RVDividerGiftAdapter;
import com.tomatolive.library.p136ui.view.emptyview.QMTaskListEmptyView;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.EmojiFilter;
import com.tomatolive.library.utils.GlideUtils;
import com.tomatolive.library.utils.NumberUtils;
import com.tomatolive.library.utils.SoftKeyboardUtils;
import com.tomatolive.library.utils.StringUtils;
import com.tomatolive.library.utils.UserInfoManager;
import com.tomatolive.library.utils.live.SimpleRxObserver;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/* renamed from: com.tomatolive.library.ui.view.dialog.QMInteractDialog */
/* loaded from: classes3.dex */
public class QMInteractDialog extends BaseBottomDialogFragment {
    private EditText etCountName;
    private EditText etCustomTaskName;
    private EditText etLowestGiftPrice;
    private EditText etTaskName;
    private FrameLayout flHdTaskContentBg;
    private View flTaskTagEmpty;
    private String giftMarkId;
    private HdGiftSelectAdapter giftSelectAdapter;
    private String giftUrl;
    private QMInteractTaskEntity interactTaskEditEntity;
    private ImageView ivCustomTaskAdd;
    private ImageView ivDialogBg;
    private ImageView ivTaskTagEmpty;
    private LinearLayout llAddEditTaskBg;
    private LinearLayout llPublishBg;
    private LinearLayout llQmTaskContentBg;
    private LinearLayout llTagSelectBg;
    private LinearLayout llTaskEditBg;
    private QMInteractTaskAdapter qmHDTaskListAdapter;
    private QMTagAdapter qmRecommendTagAdapter;
    private QMTaskListEmptyView qmTaskEmptyView;
    private QMTaskTagAdapter qmTaskTagAdapter;
    private RelativeLayout rlTaskListBg;
    private RecyclerView rvGift;
    private RecyclerView rvRecommendTag;
    private RecyclerView rvTaskList;
    private RecyclerView rvTaskTagList;
    private String taskId;
    private String taskName;
    private TextView tvAddTask;
    private TextView tvGiftName;
    private TextView tvHdTask;
    private TextView tvLowestGiftPrice;
    private TextView tvLowestGiftPriceTips;
    private TextView tvQmTask;
    private TextView tvSubmit;
    private TextView tvTagEdit;
    private TextView tvTaskName;
    private final int CONTENT_TYPE_HD_TASK_LIST = 1;
    private final int CONTENT_TYPE_HD_TASK_LIST_EMPTY = 2;
    private final int CONTENT_TYPE_HD_TASK_ADD = 3;
    private final int CONTENT_TYPE_HD_TASK_EDIT = 4;
    private final int CONTENT_TYPE_HD_TASK_GIFT = 5;
    private final int CONTENT_TYPE_HD_TASK = 7;
    private final int CONTENT_TYPE_QM_TASK = 8;
    private int contentType = 1;
    private LoadingDialog loadingDialog = null;
    private String giftNum = "";
    private String thresholdGiftPrice = "6";
    private List<String> taskDeleteIds = new ArrayList();
    private int interactTaskEditPosition = -1;

    public static QMInteractDialog newInstance() {
        Bundle bundle = new Bundle();
        QMInteractDialog qMInteractDialog = new QMInteractDialog();
        qMInteractDialog.setArguments(bundle);
        return qMInteractDialog;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    protected int getLayoutRes() {
        return R$layout.fq_dialog_qm_interact;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    protected void initView(View view) {
        this.ivDialogBg = (ImageView) view.findViewById(R$id.iv_dialog_bg);
        this.flHdTaskContentBg = (FrameLayout) view.findViewById(R$id.fl_hd_task_content_bg);
        this.llQmTaskContentBg = (LinearLayout) view.findViewById(R$id.ll_qm_task_content_bg);
        this.rlTaskListBg = (RelativeLayout) view.findViewById(R$id.rl_task_list_bg);
        this.rvTaskList = (RecyclerView) view.findViewById(R$id.rv_task_list);
        this.llPublishBg = (LinearLayout) view.findViewById(R$id.ll_publish_bg);
        this.llAddEditTaskBg = (LinearLayout) view.findViewById(R$id.ll_add_edit_task_bg);
        this.tvAddTask = (TextView) view.findViewById(R$id.tv_add_task);
        this.llTaskEditBg = (LinearLayout) view.findViewById(R$id.ll_task_edit_bg);
        this.etTaskName = (EditText) view.findViewById(R$id.et_task_name);
        this.rvRecommendTag = (RecyclerView) view.findViewById(R$id.rv_recommend_tag);
        this.tvGiftName = (TextView) view.findViewById(R$id.tv_gift_name);
        this.etCountName = (EditText) view.findViewById(R$id.et_count_name);
        this.tvSubmit = (TextView) view.findViewById(R$id.tv_submit);
        this.rvGift = (RecyclerView) view.findViewById(R$id.rv_gift);
        this.tvHdTask = (TextView) view.findViewById(R$id.tv_hd_task);
        this.tvQmTask = (TextView) view.findViewById(R$id.tv_qm_task);
        this.llTagSelectBg = (LinearLayout) view.findViewById(R$id.ll_tag_select_bg);
        this.tvTaskName = (TextView) view.findViewById(R$id.tv_task_name);
        this.tvTagEdit = (TextView) view.findViewById(R$id.tv_tag_edit);
        this.rvTaskTagList = (RecyclerView) view.findViewById(R$id.rv_task_tag_list);
        this.etCustomTaskName = (EditText) view.findViewById(R$id.et_custom_task_name);
        this.ivCustomTaskAdd = (ImageView) view.findViewById(R$id.iv_custom_task_add);
        this.tvLowestGiftPrice = (TextView) view.findViewById(R$id.tv_lowest_gift_price);
        this.etLowestGiftPrice = (EditText) view.findViewById(R$id.et_lowest_gift_price);
        this.tvLowestGiftPriceTips = (TextView) view.findViewById(R$id.tv_lowest_gift_price_tips);
        this.flTaskTagEmpty = view.findViewById(R$id.fl_task_tag_empty);
        this.ivTaskTagEmpty = (ImageView) view.findViewById(R$id.iv_task_tag_empty);
        this.tvLowestGiftPrice.setText(this.mContext.getString(R$string.fq_qm_lowest_gift_price));
        TextView textView = this.tvLowestGiftPriceTips;
        Context context = this.mContext;
        int i = R$string.fq_qm_lowest_gift_price_tips;
        textView.setText(Html.fromHtml(context.getString(i, "1 - 3000")));
        GlideUtils.loadRoundCornersImage(this.mContext, this.ivDialogBg, R$drawable.fq_ic_qm_dialog_bg, 10, RoundedCornersTransformation.CornerType.TOP);
        GlideUtils.loadRoundCornersImage(this.mContext, this.ivTaskTagEmpty, R$drawable.fq_ic_qm_task_tag_empty, 10, RoundedCornersTransformation.CornerType.ALL);
        showContentVie(1);
        initHDTaskListAdapter();
        initGiftSelectedAdapter();
        initRecommendTagAdapter();
        initTaskTagAdapter();
        changeTaskTabView(7);
        sendChargeTaskListRequest();
        sendGetTaskConfigRequest();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    public void initListener(View view) {
        super.initListener(view);
        this.qmTaskEmptyView.setTaskAddListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.QMInteractDialog.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                QMInteractDialog.this.showContentVie(3);
            }
        });
        view.findViewById(R$id.tv_add_new_task).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.QMInteractDialog.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                QMInteractDialog.this.showContentVie(3);
            }
        });
        this.tvHdTask.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.QMInteractDialog.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                QMInteractDialog.this.changeTaskTabView(7);
            }
        });
        this.tvQmTask.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.QMInteractDialog.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                QMInteractDialog.this.changeTaskTabView(8);
            }
        });
        view.findViewById(R$id.tv_publish).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.QMInteractDialog.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                List publishTaskId = QMInteractDialog.this.getPublishTaskId();
                if (publishTaskId.isEmpty()) {
                    QMInteractDialog.this.showToast(R$string.fq_qm_interact_task_empty_tips);
                } else if (publishTaskId.size() <= 3) {
                    QMInteractDialog.this.sendChargeTaskPublishRequest();
                } else {
                    QMInteractDialog.this.showToast(R$string.fq_qm_interact_task_max_tips);
                }
            }
        });
        view.findViewById(R$id.iv_back).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.QMInteractDialog.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                SoftKeyboardUtils.hideDialogSoftKeyboard(QMInteractDialog.this.getDialog());
                if (QMInteractDialog.this.rvGift.getVisibility() == 0) {
                    QMInteractDialog.this.tvAddTask.setText(QMInteractDialog.this.contentType == 4 ? R$string.fq_qm_edit_interact_task : R$string.fq_qm_add_interact_task);
                    QMInteractDialog.this.llTaskEditBg.setVisibility(0);
                    QMInteractDialog.this.rvGift.setVisibility(4);
                    return;
                }
                QMInteractDialog.this.showContentVie(1);
            }
        });
        this.tvGiftName.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.QMInteractDialog.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                QMInteractDialog.this.tvAddTask.setText(R$string.fq_qm_add_gift_title);
                QMInteractDialog.this.llTaskEditBg.setVisibility(4);
                QMInteractDialog.this.rvGift.setVisibility(0);
            }
        });
        this.giftSelectAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.tomatolive.library.ui.view.dialog.QMInteractDialog.8
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
                GiftDownloadItemEntity giftDownloadItemEntity = (GiftDownloadItemEntity) baseQuickAdapter.getItem(i);
                if (giftDownloadItemEntity == null) {
                    return;
                }
                QMInteractDialog.this.giftSelectAdapter.setCheckItem(i);
                QMInteractDialog.this.giftUrl = giftDownloadItemEntity.imgurl;
                QMInteractDialog.this.giftMarkId = giftDownloadItemEntity.markId;
                QMInteractDialog.this.tvGiftName.setText(QMInteractDialog.this.giftSelectAdapter.getGiftTips(giftDownloadItemEntity));
                QMInteractDialog.this.tvAddTask.setText(QMInteractDialog.this.contentType == 4 ? R$string.fq_qm_edit_interact_task : R$string.fq_qm_add_interact_task);
                QMInteractDialog.this.llTaskEditBg.setVisibility(0);
                QMInteractDialog.this.rvGift.setVisibility(4);
            }
        });
        this.tvSubmit.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.QMInteractDialog.9
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                QMInteractDialog qMInteractDialog = QMInteractDialog.this;
                qMInteractDialog.taskName = qMInteractDialog.etTaskName.getText().toString().trim();
                QMInteractDialog qMInteractDialog2 = QMInteractDialog.this;
                qMInteractDialog2.giftNum = qMInteractDialog2.etCountName.getText().toString().trim();
                if (!TextUtils.isEmpty(QMInteractDialog.this.taskName) && QMInteractDialog.this.taskName.length() >= 2 && QMInteractDialog.this.taskName.length() <= 10) {
                    if (!EmojiFilter.containsEmoji(QMInteractDialog.this.taskName)) {
                        if (!TextUtils.isEmpty(QMInteractDialog.this.giftMarkId)) {
                            if (!TextUtils.isEmpty(QMInteractDialog.this.giftNum)) {
                                if (NumberUtils.string2long(QMInteractDialog.this.giftNum) > 9999) {
                                    QMInteractDialog.this.showToast(R$string.fq_qm_count_empty_tips);
                                    return;
                                }
                                QMInteractDialog qMInteractDialog3 = QMInteractDialog.this;
                                qMInteractDialog3.sendChargeTaskUpdateRequest(qMInteractDialog3.taskId);
                                return;
                            }
                            QMInteractDialog.this.showToast(R$string.fq_qm_input_gift_num_tips);
                            return;
                        }
                        QMInteractDialog.this.showToast(R$string.fq_qm_select_gift_tips);
                        return;
                    }
                    QMInteractDialog.this.showToast(R$string.fq_qm_input_emoji_tips);
                    return;
                }
                QMInteractDialog.this.showToast(R$string.fq_qm_task_input_tips);
            }
        });
        this.qmHDTaskListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() { // from class: com.tomatolive.library.ui.view.dialog.QMInteractDialog.10
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
                QMInteractTaskEntity qMInteractTaskEntity = (QMInteractTaskEntity) baseQuickAdapter.getItem(i);
                if (qMInteractTaskEntity == null) {
                    return;
                }
                int id = view2.getId();
                if (id == R$id.iv_more) {
                    qMInteractTaskEntity.isEdit = true;
                    baseQuickAdapter.setData(i, qMInteractTaskEntity);
                } else if (id == R$id.iv_edit) {
                    qMInteractTaskEntity.isEdit = false;
                    baseQuickAdapter.setData(i, qMInteractTaskEntity);
                    QMInteractDialog.this.interactTaskEditPosition = i;
                    QMInteractDialog.this.showContentVie(4);
                    QMInteractDialog.this.editHdTaskDetail(qMInteractTaskEntity);
                } else if (id == R$id.iv_check) {
                    qMInteractTaskEntity.isSelected = !qMInteractTaskEntity.isSelected;
                    baseQuickAdapter.setData(i, qMInteractTaskEntity);
                } else if (id != R$id.iv_delete) {
                } else {
                    qMInteractTaskEntity.isEdit = false;
                    baseQuickAdapter.setData(i, qMInteractTaskEntity);
                    QMInteractDialog.this.sendChargeTaskDelRequest(qMInteractTaskEntity.taskId, i);
                }
            }
        });
        this.qmHDTaskListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.tomatolive.library.ui.view.dialog.QMInteractDialog.11
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
                QMInteractTaskEntity qMInteractTaskEntity = (QMInteractTaskEntity) baseQuickAdapter.getItem(i);
                if (qMInteractTaskEntity == null) {
                    return;
                }
                qMInteractTaskEntity.isSelected = !qMInteractTaskEntity.isSelected;
                baseQuickAdapter.setData(i, qMInteractTaskEntity);
            }
        });
        this.qmRecommendTagAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.tomatolive.library.ui.view.dialog.QMInteractDialog.12
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
                QMInteractTaskEntity qMInteractTaskEntity = (QMInteractTaskEntity) baseQuickAdapter.getItem(i);
                if (qMInteractTaskEntity == null) {
                    return;
                }
                QMInteractDialog.this.qmRecommendTagAdapter.setCheckItem(i);
                QMInteractDialog.this.changeSelectTaskTag(qMInteractTaskEntity.taskName);
            }
        });
        view.findViewById(R$id.iv_tag_close).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.QMInteractDialog.13
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                QMInteractDialog.this.changeSelectTaskTag(null);
            }
        });
        this.tvTagEdit.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.QMInteractDialog.14
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (QMInteractDialog.this.qmTaskTagAdapter.isEdit()) {
                    QMInteractDialog.this.tvTagEdit.setText(R$string.fq_qm_edit);
                    QMInteractDialog.this.qmTaskTagAdapter.setEdit(false);
                    QMInteractDialog.this.sendTaskConfigDelRequest();
                    return;
                }
                QMInteractDialog.this.tvTagEdit.setText(R$string.fq_qm_done);
                QMInteractDialog.this.qmTaskTagAdapter.setEdit(true);
                if (QMInteractDialog.this.taskDeleteIds == null) {
                    return;
                }
                QMInteractDialog.this.taskDeleteIds.clear();
            }
        });
        this.qmTaskTagAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() { // from class: com.tomatolive.library.ui.view.dialog.QMInteractDialog.15
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
                QMInteractTaskEntity qMInteractTaskEntity = (QMInteractTaskEntity) baseQuickAdapter.getItem(i);
                if (qMInteractTaskEntity != null && view2.getId() == R$id.iv_task_delete) {
                    QMInteractDialog.this.taskDeleteIds.add(qMInteractTaskEntity.taskId);
                    baseQuickAdapter.remove(i);
                    QMInteractDialog.this.setTaskTagEmptyView();
                }
            }
        });
        this.ivCustomTaskAdd.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.QMInteractDialog.16
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                QMInteractDialog.this.sendTaskConfigAddRequest();
            }
        });
        this.etLowestGiftPrice.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: com.tomatolive.library.ui.view.dialog.QMInteractDialog.17
            @Override // android.view.View.OnFocusChangeListener
            public void onFocusChange(View view2, boolean z) {
                if (!z) {
                    QMInteractDialog.this.sendConfigGiftThresholdRequest();
                }
            }
        });
    }

    @Override // com.trello.rxlifecycle2.components.support.RxDialogFragment, android.support.p002v4.app.Fragment
    public void onResume() {
        super.onResume();
        QMTaskTagAdapter qMTaskTagAdapter = this.qmTaskTagAdapter;
        if (qMTaskTagAdapter == null || !qMTaskTagAdapter.isEdit()) {
            return;
        }
        this.tvTagEdit.setText(R$string.fq_qm_edit);
        this.qmTaskTagAdapter.setEdit(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showContentVie(int i) {
        this.contentType = i;
        int i2 = 0;
        this.rlTaskListBg.setVisibility(i == 1 ? 0 : 4);
        this.llAddEditTaskBg.setVisibility((i == 3 || i == 4 || i == 5) ? 0 : 4);
        this.llTaskEditBg.setVisibility((i == 3 || i == 4) ? 0 : 4);
        RecyclerView recyclerView = this.rvGift;
        if (i != 5) {
            i2 = 4;
        }
        recyclerView.setVisibility(i2);
        this.tvAddTask.setText(i == 4 ? R$string.fq_qm_edit_interact_task : R$string.fq_qm_add_interact_task);
        if (i == 3) {
            resetHdTask();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeTaskTabView(int i) {
        int i2 = 0;
        boolean z = i == 7;
        this.tvHdTask.setTextColor(ContextCompat.getColor(this.mContext, z ? R$color.fq_qm_primary : R$color.fq_qm_gray));
        this.tvHdTask.setBackgroundResource(z ? R$drawable.fq_qm_tab_white_bg : 0);
        this.tvQmTask.setTextColor(ContextCompat.getColor(this.mContext, !z ? R$color.fq_qm_primary : R$color.fq_qm_gray));
        this.tvQmTask.setBackgroundResource(!z ? R$drawable.fq_qm_tab_white_bg : 0);
        this.flHdTaskContentBg.setVisibility(z ? 0 : 4);
        LinearLayout linearLayout = this.llQmTaskContentBg;
        if (z) {
            i2 = 4;
        }
        linearLayout.setVisibility(i2);
        SoftKeyboardUtils.hideDialogSoftKeyboard(getDialog());
    }

    private void initHDTaskListAdapter() {
        this.qmTaskEmptyView = new QMTaskListEmptyView(this.mContext, 57);
        ((DefaultItemAnimator) this.rvTaskList.getItemAnimator()).setSupportsChangeAnimations(false);
        this.rvTaskList.setLayoutManager(new LinearLayoutManager(this.mContext));
        this.qmHDTaskListAdapter = new QMInteractTaskAdapter(R$layout.fq_item_list_qm_hd_task);
        this.rvTaskList.setAdapter(this.qmHDTaskListAdapter);
        this.qmHDTaskListAdapter.bindToRecyclerView(this.rvTaskList);
        this.qmHDTaskListAdapter.setEmptyView(this.qmTaskEmptyView);
    }

    private void initRecommendTagAdapter() {
        ((DefaultItemAnimator) this.rvRecommendTag.getItemAnimator()).setSupportsChangeAnimations(false);
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(this.mContext);
        flexboxLayoutManager.setFlexDirection(0);
        flexboxLayoutManager.setFlexWrap(1);
        flexboxLayoutManager.setJustifyContent(0);
        flexboxLayoutManager.setAlignItems(4);
        new FlexboxItemDecoration(this.mContext).setDrawable(ContextCompat.getDrawable(this.mContext, R$drawable.fq_qm_list_item_divider));
        this.rvRecommendTag.setLayoutManager(flexboxLayoutManager);
        this.qmRecommendTagAdapter = new QMTagAdapter(R$layout.fq_item_list_qm_recommend_tag);
        this.rvRecommendTag.setAdapter(this.qmRecommendTagAdapter);
        this.qmRecommendTagAdapter.bindToRecyclerView(this.rvRecommendTag);
    }

    private void initTaskTagAdapter() {
        ((DefaultItemAnimator) this.rvTaskTagList.getItemAnimator()).setSupportsChangeAnimations(false);
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(this.mContext);
        flexboxLayoutManager.setFlexDirection(0);
        flexboxLayoutManager.setFlexWrap(1);
        flexboxLayoutManager.setJustifyContent(0);
        flexboxLayoutManager.setAlignItems(4);
        new FlexboxItemDecoration(this.mContext).setDrawable(ContextCompat.getDrawable(this.mContext, R$drawable.fq_qm_list_item_divider));
        this.rvTaskTagList.setLayoutManager(flexboxLayoutManager);
        this.qmTaskTagAdapter = new QMTaskTagAdapter(R$layout.fq_item_list_qm_status_tag);
        this.rvTaskTagList.setAdapter(this.qmTaskTagAdapter);
        this.qmTaskTagAdapter.bindToRecyclerView(this.rvTaskTagList);
    }

    private void initGiftSelectedAdapter() {
        this.giftSelectAdapter = new HdGiftSelectAdapter(R$layout.fq_item_grid_hd_gift_select, true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.mContext, 4);
        RVDividerGiftAdapter rVDividerGiftAdapter = new RVDividerGiftAdapter(this.mContext, R$color.fq_qm_primary_light, true);
        this.rvGift.setAdapter(this.giftSelectAdapter);
        this.rvGift.setLayoutManager(gridLayoutManager);
        this.rvGift.addItemDecoration(rVDividerGiftAdapter);
        this.giftSelectAdapter.bindToRecyclerView(this.rvGift);
        sendGiftRequest(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendChargeTaskListRequest() {
        ApiRetrofit.getInstance().getApiService().chargeTaskListService(new RequestParams().getDefaultParams()).map(new ServerResultFunction<List<QMInteractTaskEntity>>() { // from class: com.tomatolive.library.ui.view.dialog.QMInteractDialog.19
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new SimpleRxObserver<List<QMInteractTaskEntity>>() { // from class: com.tomatolive.library.ui.view.dialog.QMInteractDialog.18
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                super.onSubscribe(disposable);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(List<QMInteractTaskEntity> list) {
                QMInteractDialog.this.qmHDTaskListAdapter.setNewData(list);
                QMInteractDialog.this.llPublishBg.setVisibility(QMInteractDialog.this.qmHDTaskListAdapter.getData().isEmpty() ? 4 : 0);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onError(Throwable th) {
                super.onError(th);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendChargeTaskUpdateRequest(final String str) {
        ApiRetrofit.getInstance().getApiService().chargeTaskUpdateService(new RequestParams().getInteractTaskUpdateParams(str, this.taskName, this.giftMarkId, this.giftUrl, this.giftNum)).map(new ServerResultFunction<QMInteractTaskEntity>() { // from class: com.tomatolive.library.ui.view.dialog.QMInteractDialog.21
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new SimpleRxObserver<QMInteractTaskEntity>() { // from class: com.tomatolive.library.ui.view.dialog.QMInteractDialog.20
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                super.onSubscribe(disposable);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(QMInteractTaskEntity qMInteractTaskEntity) {
                SoftKeyboardUtils.hideDialogSoftKeyboard(QMInteractDialog.this.getDialog());
                QMInteractDialog.this.showContentVie(1);
                if (TextUtils.isEmpty(str)) {
                    QMInteractDialog.this.showToast(R$string.fq_qm_add_interact_task_success);
                    if (qMInteractTaskEntity != null) {
                        QMInteractDialog.this.llPublishBg.setVisibility(0);
                        QMInteractTaskEntity qMInteractTaskEntity2 = new QMInteractTaskEntity();
                        qMInteractTaskEntity2.taskId = qMInteractTaskEntity.taskId;
                        qMInteractTaskEntity2.giftNum = QMInteractDialog.this.giftNum;
                        qMInteractTaskEntity2.giftMarkId = QMInteractDialog.this.giftMarkId;
                        qMInteractTaskEntity2.taskName = QMInteractDialog.this.taskName;
                        qMInteractTaskEntity2.giftUrl = QMInteractDialog.this.giftUrl;
                        QMInteractDialog.this.qmHDTaskListAdapter.addData(0, (int) qMInteractTaskEntity2);
                        return;
                    }
                    QMInteractDialog.this.sendChargeTaskListRequest();
                    return;
                }
                QMInteractDialog.this.showToast(R$string.fq_qm_edit_interact_task_success);
                if (QMInteractDialog.this.interactTaskEditEntity != null) {
                    QMInteractDialog.this.interactTaskEditEntity.giftNum = QMInteractDialog.this.giftNum;
                    QMInteractDialog.this.interactTaskEditEntity.giftMarkId = QMInteractDialog.this.giftMarkId;
                    QMInteractDialog.this.interactTaskEditEntity.taskName = QMInteractDialog.this.taskName;
                    QMInteractDialog.this.interactTaskEditEntity.giftUrl = QMInteractDialog.this.giftUrl;
                    if (QMInteractDialog.this.interactTaskEditPosition < 0) {
                        return;
                    }
                    QMInteractDialog.this.qmHDTaskListAdapter.setData(QMInteractDialog.this.interactTaskEditPosition, QMInteractDialog.this.interactTaskEditEntity);
                    return;
                }
                QMInteractDialog.this.sendChargeTaskListRequest();
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onError(Throwable th) {
                super.onError(th);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendChargeTaskDelRequest(String str, final int i) {
        ApiRetrofit.getInstance().getApiService().chargeTaskDelService(new RequestParams().getTaskIdParams(str)).map(new ServerResultFunction<Object>() { // from class: com.tomatolive.library.ui.view.dialog.QMInteractDialog.23
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new SimpleRxObserver<Object>() { // from class: com.tomatolive.library.ui.view.dialog.QMInteractDialog.22
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                super.onSubscribe(disposable);
                QMInteractDialog.this.showLoadingDialog(true);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(Object obj) {
                QMInteractDialog.this.showToast(R$string.fq_qm_task_delete_success_tips);
                QMInteractDialog.this.dismissProgressDialog();
                QMInteractDialog.this.qmHDTaskListAdapter.remove(i);
                QMInteractDialog.this.llPublishBg.setVisibility(QMInteractDialog.this.qmHDTaskListAdapter.getData().isEmpty() ? 4 : 0);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onError(Throwable th) {
                super.onError(th);
                QMInteractDialog.this.dismissProgressDialog();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendChargeTaskPublishRequest() {
        ApiRetrofit.getInstance().getApiService().chargeTaskPublishService(new RequestParams().getTaskIdParams(getTaskPublishIDs())).map(new ServerResultFunction<Object>() { // from class: com.tomatolive.library.ui.view.dialog.QMInteractDialog.25
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new SimpleRxObserver<Object>() { // from class: com.tomatolive.library.ui.view.dialog.QMInteractDialog.24
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                super.onSubscribe(disposable);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(Object obj) {
                QMInteractDialog.this.showToast(R$string.fq_qm_interact_task_publish_success);
                QMInteractDialog.this.resetHDTaskListAdapter();
                QMInteractDialog.this.dismiss();
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void onError(int i, String str) {
                super.onError(i, str);
                if (TextUtils.equals(String.valueOf(i), ConstantUtils.GIFT_NEED_UPDATE)) {
                    QMInteractDialog.this.dismiss();
                    QMInteractDialog.this.sendGiftRequest(true);
                }
            }
        });
    }

    private void sendGetTaskConfigRequest() {
        ApiRetrofit.getInstance().getApiService().getTaskConfigService(new RequestParams().getAnchorIdParams(UserInfoManager.getInstance().getUserId())).map(new ServerResultFunction<QMInteractTaskConfigEntity>() { // from class: com.tomatolive.library.ui.view.dialog.QMInteractDialog.27
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new SimpleRxObserver<QMInteractTaskConfigEntity>() { // from class: com.tomatolive.library.ui.view.dialog.QMInteractDialog.26
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
                QMInteractDialog.this.thresholdGiftPrice = qMInteractTaskConfigEntity.threshold;
                QMInteractDialog.this.etLowestGiftPrice.setText(QMInteractDialog.this.thresholdGiftPrice);
                QMInteractDialog.this.etLowestGiftPrice.setHint(QMInteractDialog.this.thresholdGiftPrice);
                QMInteractDialog.this.qmTaskTagAdapter.setNewData(list);
                QMInteractDialog.this.qmRecommendTagAdapter.setNewData(list);
                QMInteractDialog.this.setTaskTagEmptyView();
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onError(Throwable th) {
                super.onError(th);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendTaskConfigAddRequest() {
        if (this.qmTaskTagAdapter.getData().size() >= 9) {
            showToast(R$string.fq_qm_custom_task_max_add);
            return;
        }
        final String trim = this.etCustomTaskName.getText().toString().trim();
        if (TextUtils.isEmpty(trim) || trim.length() < 2 || trim.length() > 6) {
            showToast(R$string.fq_qm_custom_task_max_word_hint);
        } else if (EmojiFilter.containsEmoji(trim)) {
            showToast(R$string.fq_qm_input_emoji_tips);
        } else if (isRepeatCustomTask(trim)) {
            showToast(R$string.fq_qm_custom_repeat_task_tips);
        } else {
            ApiRetrofit.getInstance().getApiService().taskConfigAddService(new RequestParams().getTaskNameTagParams(trim)).map(new ServerResultFunction<QMInteractTaskEntity>() { // from class: com.tomatolive.library.ui.view.dialog.QMInteractDialog.29
            }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new SimpleRxObserver<QMInteractTaskEntity>() { // from class: com.tomatolive.library.ui.view.dialog.QMInteractDialog.28
                @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
                public void onSubscribe(Disposable disposable) {
                    super.onSubscribe(disposable);
                }

                @Override // com.tomatolive.library.utils.live.SimpleRxObserver
                public void accept(QMInteractTaskEntity qMInteractTaskEntity) {
                    if (qMInteractTaskEntity == null) {
                        return;
                    }
                    SoftKeyboardUtils.hideDialogSoftKeyboard(QMInteractDialog.this.getDialog());
                    QMInteractTaskEntity qMInteractTaskEntity2 = new QMInteractTaskEntity();
                    qMInteractTaskEntity2.taskId = qMInteractTaskEntity.taskId;
                    qMInteractTaskEntity2.taskName = trim;
                    QMInteractDialog.this.qmTaskTagAdapter.addData(0, (int) qMInteractTaskEntity2);
                    QMInteractDialog.this.etCustomTaskName.setText("");
                    QMInteractDialog.this.setTaskTagEmptyView();
                }

                @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
                public void onError(Throwable th) {
                    super.onError(th);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendTaskConfigDelRequest() {
        List<String> list = this.taskDeleteIds;
        if (list == null || list.isEmpty()) {
            return;
        }
        ApiRetrofit.getInstance().getApiService().taskConfigDelService(new RequestParams().getTaskTagDelParams(this.taskDeleteIds)).map(new ServerResultFunction<Object>() { // from class: com.tomatolive.library.ui.view.dialog.QMInteractDialog.31
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new SimpleRxObserver<Object>() { // from class: com.tomatolive.library.ui.view.dialog.QMInteractDialog.30
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                super.onSubscribe(disposable);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(Object obj) {
                QMInteractDialog.this.updateRecommendTagAdapter();
                if (QMInteractDialog.this.taskDeleteIds != null) {
                    QMInteractDialog.this.taskDeleteIds.clear();
                }
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onError(Throwable th) {
                super.onError(th);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendConfigGiftThresholdRequest() {
        String trim = this.etLowestGiftPrice.getText().toString().trim();
        if (TextUtils.isEmpty(trim)) {
            return;
        }
        ApiRetrofit.getInstance().getApiService().configGiftThresholdService(new RequestParams().getConfigGiftThresholdParams(trim)).map(new ServerResultFunction<Object>() { // from class: com.tomatolive.library.ui.view.dialog.QMInteractDialog.33
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new SimpleRxObserver<Object>() { // from class: com.tomatolive.library.ui.view.dialog.QMInteractDialog.32
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                super.onSubscribe(disposable);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(Object obj) {
                QMInteractDialog.this.showToast(R$string.fq_qm_lowest_gift_price_set_success);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onError(Throwable th) {
                super.onError(th);
                QMInteractDialog.this.showToast(R$string.fq_qm_lowest_gift_price_set_fail);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateRecommendTagAdapter() {
        List<QMInteractTaskEntity> data = this.qmRecommendTagAdapter.getData();
        for (QMInteractTaskEntity qMInteractTaskEntity : data) {
            for (String str : this.taskDeleteIds) {
                if (TextUtils.equals(qMInteractTaskEntity.taskId, str)) {
                    data.remove(qMInteractTaskEntity);
                }
            }
        }
        this.qmRecommendTagAdapter.setNewData(data);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setTaskTagEmptyView() {
        int i = 0;
        this.rvTaskTagList.setVisibility(!this.qmTaskTagAdapter.getData().isEmpty() ? 0 : 4);
        View view = this.flTaskTagEmpty;
        if (!this.qmTaskTagAdapter.getData().isEmpty()) {
            i = 4;
        }
        view.setVisibility(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendGiftRequest(boolean z) {
        List<GiftDownloadItemEntity> localDownloadListFilterLuckyScoreGift = GiftDownLoadManager.getInstance().getLocalDownloadListFilterLuckyScoreGift();
        if (z || localDownloadListFilterLuckyScoreGift == null || localDownloadListFilterLuckyScoreGift.isEmpty()) {
            ApiRetrofit.getInstance().getApiService().giftListV2(new RequestParams().getDefaultParams()).map(new ServerResultFunction<List<GiftDownloadItemEntity>>() { // from class: com.tomatolive.library.ui.view.dialog.QMInteractDialog.37
            }).flatMap(new Function<List<GiftDownloadItemEntity>, ObservableSource<List<GiftDownloadItemEntity>>>() { // from class: com.tomatolive.library.ui.view.dialog.QMInteractDialog.36
                @Override // io.reactivex.functions.Function
                /* renamed from: apply  reason: avoid collision after fix types in other method */
                public ObservableSource<List<GiftDownloadItemEntity>> mo6755apply(List<GiftDownloadItemEntity> list) throws Exception {
                    GiftDownLoadManager.getInstance().setLocalDownloadList(list);
                    return Observable.just(QMInteractDialog.this.formatListFilterLuckyScoreGift(list));
                }
            }).onErrorResumeNext(new HttpResultFunction<List<GiftDownloadItemEntity>>() { // from class: com.tomatolive.library.ui.view.dialog.QMInteractDialog.35
            }).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<List<GiftDownloadItemEntity>>() { // from class: com.tomatolive.library.ui.view.dialog.QMInteractDialog.34
                @Override // com.tomatolive.library.utils.live.SimpleRxObserver
                public void accept(List<GiftDownloadItemEntity> list) {
                    if (list == null) {
                        return;
                    }
                    QMInteractDialog.this.giftSelectAdapter.setNewData(list);
                    QMInteractDialog.this.giftSelectAdapter.setCheckItem(-1);
                    QMInteractDialog.this.giftUrl = "";
                    QMInteractDialog.this.giftMarkId = "";
                    QMInteractDialog.this.tvGiftName.setText("");
                }
            });
        } else {
            Observable.just(GiftDownLoadManager.getInstance().getLocalDownloadListFilterLuckyScoreGift()).flatMap(new Function<List<GiftDownloadItemEntity>, ObservableSource<List<GiftDownloadItemEntity>>>() { // from class: com.tomatolive.library.ui.view.dialog.QMInteractDialog.39
                @Override // io.reactivex.functions.Function
                /* renamed from: apply  reason: avoid collision after fix types in other method */
                public ObservableSource<List<GiftDownloadItemEntity>> mo6755apply(List<GiftDownloadItemEntity> list) throws Exception {
                    return Observable.just(QMInteractDialog.this.formatGiftList(list));
                }
            }).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<GiftDownloadItemEntity>>() { // from class: com.tomatolive.library.ui.view.dialog.QMInteractDialog.38
                @Override // io.reactivex.functions.Consumer
                public void accept(List<GiftDownloadItemEntity> list) throws Exception {
                    if (list == null) {
                        return;
                    }
                    QMInteractDialog.this.giftSelectAdapter.setNewData(list);
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

    /* JADX INFO: Access modifiers changed from: private */
    public void editHdTaskDetail(QMInteractTaskEntity qMInteractTaskEntity) {
        List<QMInteractTaskEntity> data;
        if (qMInteractTaskEntity == null) {
            return;
        }
        changeSelectTaskTag(qMInteractTaskEntity.taskName);
        this.interactTaskEditEntity = qMInteractTaskEntity;
        this.taskId = qMInteractTaskEntity.taskId;
        this.giftUrl = qMInteractTaskEntity.giftUrl;
        this.giftNum = qMInteractTaskEntity.giftNum;
        this.giftMarkId = qMInteractTaskEntity.giftMarkId;
        String str = "";
        int i = 0;
        if (!TextUtils.isEmpty(this.giftMarkId)) {
            List<GiftDownloadItemEntity> data2 = this.giftSelectAdapter.getData();
            GiftDownloadItemEntity giftDownloadItemEntity = null;
            int i2 = 0;
            while (true) {
                if (i2 >= data2.size()) {
                    i2 = -1;
                    break;
                }
                giftDownloadItemEntity = data2.get(i2);
                if (giftDownloadItemEntity != null && TextUtils.equals(this.giftMarkId, giftDownloadItemEntity.markId)) {
                    break;
                }
                i2++;
            }
            if (giftDownloadItemEntity != null && i2 > -1) {
                this.giftSelectAdapter.setCheckItem(i2);
                this.tvGiftName.setText(this.giftSelectAdapter.getGiftTips(giftDownloadItemEntity));
            } else {
                this.giftSelectAdapter.setCheckItem(-1);
                this.tvGiftName.setText(str);
            }
        }
        if (!TextUtils.isEmpty(this.taskName) && (data = this.qmRecommendTagAdapter.getData()) != null && !data.isEmpty()) {
            while (true) {
                if (i >= data.size()) {
                    i = -1;
                    break;
                } else if (TextUtils.equals(this.taskName, data.get(i).taskName)) {
                    break;
                } else {
                    i++;
                }
            }
            this.qmRecommendTagAdapter.setCheckItem(i);
        }
        EditText editText = this.etCountName;
        if (!TextUtils.isEmpty(this.giftNum)) {
            str = this.giftNum;
        }
        editText.setText(str);
    }

    private void resetHdTask() {
        this.taskId = "";
        this.giftMarkId = "";
        this.giftNum = "";
        this.giftUrl = "";
        this.taskName = "";
        this.etTaskName.setText("");
        this.etCountName.setText("");
        this.tvGiftName.setText("");
        this.interactTaskEditEntity = null;
        this.interactTaskEditPosition = -1;
        this.qmRecommendTagAdapter.setCheckItem(-1);
        this.giftSelectAdapter.setCheckItem(-1);
        changeSelectTaskTag(null);
        List<String> list = this.taskDeleteIds;
        if (list != null) {
            list.clear();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetHDTaskListAdapter() {
        for (QMInteractTaskEntity qMInteractTaskEntity : this.qmHDTaskListAdapter.getData()) {
            qMInteractTaskEntity.isSelected = false;
        }
        this.qmHDTaskListAdapter.notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<String> getPublishTaskId() {
        ArrayList arrayList = new ArrayList();
        for (QMInteractTaskEntity qMInteractTaskEntity : this.qmHDTaskListAdapter.getData()) {
            if (qMInteractTaskEntity.isSelected) {
                arrayList.add(qMInteractTaskEntity.taskId);
            }
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showLoadingDialog(boolean z) {
        LoadingDialog loadingDialog;
        if (this.loadingDialog == null) {
            this.loadingDialog = new LoadingDialog(getContext());
        }
        if (!z || (loadingDialog = this.loadingDialog) == null || loadingDialog.isShowing()) {
            return;
        }
        this.loadingDialog.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dismissProgressDialog() {
        try {
            if (this.loadingDialog == null || !this.loadingDialog.isShowing()) {
                return;
            }
            this.loadingDialog.dismiss();
        } catch (Exception unused) {
        }
    }

    private boolean isRepeatCustomTask(String str) {
        List<QMInteractTaskEntity> data = this.qmTaskTagAdapter.getData();
        if (data == null || data.isEmpty()) {
            return false;
        }
        for (QMInteractTaskEntity qMInteractTaskEntity : data) {
            if (TextUtils.equals(str, qMInteractTaskEntity.taskName)) {
                return true;
            }
        }
        return false;
    }

    private String getTaskPublishIDs() {
        ArrayList arrayList = new ArrayList();
        for (QMInteractTaskEntity qMInteractTaskEntity : this.qmHDTaskListAdapter.getData()) {
            if (qMInteractTaskEntity.isSelected) {
                arrayList.add(qMInteractTaskEntity.taskId);
            }
        }
        return StringUtils.getCommaSpliceStrByList(arrayList);
    }
}
