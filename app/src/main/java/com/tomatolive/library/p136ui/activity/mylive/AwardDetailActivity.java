package com.tomatolive.library.p136ui.activity.mylive;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.gyf.barlibrary.ImmersionBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.base.BaseActivity;
import com.tomatolive.library.http.ResultCallBack;
import com.tomatolive.library.model.AppealInfoEntity;
import com.tomatolive.library.model.AwardDetailEntity;
import com.tomatolive.library.model.MessageDetailEntity;
import com.tomatolive.library.model.event.AwardDetailEvent;
import com.tomatolive.library.p136ui.adapter.LeaveMessageAdapter;
import com.tomatolive.library.p136ui.presenter.AwardDetailPresenter;
import com.tomatolive.library.p136ui.view.dialog.AddAddressDialog;
import com.tomatolive.library.p136ui.view.dialog.confirm.SureCancelDialog;
import com.tomatolive.library.p136ui.view.divider.RVDividerListMsg;
import com.tomatolive.library.p136ui.view.headview.LeaveMsgHeadView;
import com.tomatolive.library.p136ui.view.iview.IAwardDetailView;
import com.tomatolive.library.p136ui.view.widget.StateView;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.DateUtils;
import com.tomatolive.library.utils.NumberUtils;
import com.tomatolive.library.utils.SoftKeyboardUtils;
import com.tomatolive.library.utils.live.SimpleRxObserver;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.greenrobot.eventbus.EventBus;

/* renamed from: com.tomatolive.library.ui.activity.mylive.AwardDetailActivity */
/* loaded from: classes3.dex */
public class AwardDetailActivity extends BaseActivity<AwardDetailPresenter> implements IAwardDetailView {
    private LeaveMessageAdapter leaveMessageAdapter;
    private LeaveMsgHeadView leaveMsgHeadView;
    private AwardDetailEntity mAwardDetail;
    private EditText mEtContent;
    private String mRecordId;
    private SmartRefreshLayout mSmartRefreshLayout;
    private TextView mTvSendBtn;
    private RecyclerView rvMsgDetailList;
    private List<MessageDetailEntity> msgList = new ArrayList();
    private boolean isAwarded = true;

    private void scrollToPosition() {
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IAwardDetailView
    public void onAddMessageFailure(int i, String str) {
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IAwardDetailView
    public void onGetAwardDetailFailure(int i, String str) {
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IAwardDetailView
    public void onGetMessageDetailFailure() {
    }

    @Override // com.tomatolive.library.base.BaseView
    public void onResultError(int i) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.base.BaseActivity
    /* renamed from: createPresenter  reason: collision with other method in class */
    public AwardDetailPresenter mo6636createPresenter() {
        return new AwardDetailPresenter(this.mContext, this);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    protected int getLayoutId() {
        return R$layout.fq_activity_award_detail;
    }

    @Override // com.tomatolive.library.base.BaseActivity
    protected View injectStateView() {
        return findViewById(R$id.fl_content_view);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initView(Bundle bundle) {
        Intent intent = getIntent();
        final String stringExtra = intent.getStringExtra(ConstantUtils.RESULT_ITEM);
        this.mRecordId = intent.getStringExtra(ConstantUtils.RESULT_ID);
        this.isAwarded = intent.getBooleanExtra(ConstantUtils.RESULT_FLAG, true);
        if (this.isAwarded) {
            setActivityRightTitle(stringExtra, getString(R$string.fq_hd_appeal), new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$AwardDetailActivity$X7pyTHyBM-abCPobYyKBCCNVKps
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    AwardDetailActivity.this.lambda$initView$0$AwardDetailActivity(stringExtra, view);
                }
            });
        } else {
            setActivityTitle(stringExtra);
        }
        this.mEtContent = (EditText) findViewById(R$id.et_input_message);
        this.mTvSendBtn = (TextView) findViewById(R$id.tv_send_btn);
        this.rvMsgDetailList = (RecyclerView) findViewById(R$id.rv_msg_detail_list);
        this.mSmartRefreshLayout = (SmartRefreshLayout) findViewById(R$id.refreshLayout);
        initLeaveMessageAdapter();
        sendRequest(true);
    }

    public /* synthetic */ void lambda$initView$0$AwardDetailActivity(final String str, View view) {
        AwardDetailEntity awardDetailEntity = this.mAwardDetail;
        if (awardDetailEntity == null) {
            return;
        }
        ((AwardDetailPresenter) this.mPresenter).getAppealInfo(awardDetailEntity.getWinningRecordId(), new ResultCallBack<AppealInfoEntity>() { // from class: com.tomatolive.library.ui.activity.mylive.AwardDetailActivity.1
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str2) {
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(AppealInfoEntity appealInfoEntity) {
                if (appealInfoEntity == null || TextUtils.isEmpty(appealInfoEntity.getPrizeName())) {
                    if (!AwardDetailActivity.this.isSubmitAppeal()) {
                        AwardDetailActivity.this.showToast(R$string.fq_hd_winning_days_appeal_tips);
                        return;
                    }
                    Intent intent = new Intent(AwardDetailActivity.this, SubmitAppealActivity.class);
                    intent.putExtra(ConstantUtils.KEY_ANCHOR_NAME, str);
                    intent.putExtra(ConstantUtils.KEY_ANCHOR_ID, AwardDetailActivity.this.mAwardDetail.getAnchorOpenId());
                    intent.putExtra(ConstantUtils.KEY_WINNING_TIME, AwardDetailActivity.this.mAwardDetail.getWinningTime());
                    intent.putExtra(ConstantUtils.KEY_DRAW_TYPE, AwardDetailActivity.this.mAwardDetail.getDrawType());
                    intent.putExtra(ConstantUtils.KEY_LIVE_ID, AwardDetailActivity.this.mAwardDetail.getLiveId());
                    intent.putExtra(ConstantUtils.KEY_PRIZE_NAME, AwardDetailActivity.this.mAwardDetail.getPrizeName());
                    intent.putExtra(ConstantUtils.KEY_RECORD_ID, AwardDetailActivity.this.mAwardDetail.getWinningRecordId());
                    intent.putExtra(ConstantUtils.KEY_DRAW_ID, AwardDetailActivity.this.mAwardDetail.getDrawId());
                    AwardDetailActivity.this.startActivity(intent);
                    return;
                }
                Intent intent2 = new Intent(((BaseActivity) AwardDetailActivity.this).mContext, AppealDetailActivity.class);
                intent2.putExtra(ConstantUtils.RESULT_ID, appealInfoEntity.getId());
                intent2.putExtra(ConstantUtils.RESULT_FLAG, appealInfoEntity.getAppealStatus());
                AwardDetailActivity.this.startActivity(intent2);
            }
        });
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initListener() {
        super.initListener();
        this.mTvSendBtn.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$AwardDetailActivity$oxR1KsBFSUBFF7SWVUfY-60XfuY
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AwardDetailActivity.this.lambda$initListener$1$AwardDetailActivity(view);
            }
        });
        this.leaveMsgHeadView.setAddressOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.AwardDetailActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (!AwardDetailActivity.this.isAwarded) {
                    if (AwardDetailActivity.this.mAwardDetail == null) {
                        return;
                    }
                    String winningStatus = AwardDetailActivity.this.mAwardDetail.getWinningStatus();
                    char c = 65535;
                    int hashCode = winningStatus.hashCode();
                    if (hashCode != 48) {
                        if (hashCode == 49 && winningStatus.equals("1")) {
                            c = 1;
                        }
                    } else if (winningStatus.equals("0")) {
                        c = 0;
                    }
                    if (c == 0) {
                        AwardDetailActivity awardDetailActivity = AwardDetailActivity.this;
                        awardDetailActivity.sendLeaveMessageRequest(((BaseActivity) awardDetailActivity).mContext.getString(R$string.fq_hd_pls_add_address));
                        return;
                    } else if (c != 1) {
                        return;
                    } else {
                        AwardDetailActivity.this.showSaveGivenPrizeInfoDialog();
                        return;
                    }
                }
                AddAddressDialog.newInstance(new AddAddressDialog.OnAddAddressListener() { // from class: com.tomatolive.library.ui.activity.mylive.AwardDetailActivity.2.1
                    @Override // com.tomatolive.library.p136ui.view.dialog.AddAddressDialog.OnAddAddressListener
                    public void OnAddAddress(String str) {
                        ((AwardDetailPresenter) ((BaseActivity) AwardDetailActivity.this).mPresenter).addAddress(AwardDetailActivity.this.mRecordId, str);
                    }
                }).show(AwardDetailActivity.this.getSupportFragmentManager());
            }
        });
        this.mSmartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() { // from class: com.tomatolive.library.ui.activity.mylive.AwardDetailActivity.3
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                AwardDetailActivity awardDetailActivity = AwardDetailActivity.this;
                awardDetailActivity.pageNum++;
                ((AwardDetailPresenter) ((BaseActivity) awardDetailActivity).mPresenter).getMessageDetail(AwardDetailActivity.this.mRecordId, AwardDetailActivity.this.pageNum, false);
            }

            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.resetNoMoreData();
                AwardDetailActivity awardDetailActivity = AwardDetailActivity.this;
                awardDetailActivity.pageNum = 1;
                awardDetailActivity.sendRequest(false);
                refreshLayout.mo6481finishRefresh();
            }
        });
        this.mStateView.setOnRetryClickListener(new StateView.OnRetryClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.AwardDetailActivity.4
            @Override // com.tomatolive.library.p136ui.view.widget.StateView.OnRetryClickListener
            public void onRetryClick() {
                AwardDetailActivity awardDetailActivity = AwardDetailActivity.this;
                awardDetailActivity.pageNum = 1;
                awardDetailActivity.sendRequest(true);
            }
        });
        this.leaveMessageAdapter.setOnGivenPrizeClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.AwardDetailActivity.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                AwardDetailActivity.this.showSaveGivenPrizeInfoDialog();
            }
        });
        this.mEtContent.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.tomatolive.library.ui.activity.mylive.AwardDetailActivity.6
            @Override // android.widget.TextView.OnEditorActionListener
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == 4) {
                    AwardDetailActivity.this.sendLeaveMessageRequest(AwardDetailActivity.this.mEtContent.getText().toString().trim());
                    return true;
                }
                return true;
            }
        });
    }

    public /* synthetic */ void lambda$initListener$1$AwardDetailActivity(View view) {
        sendLeaveMessageRequest(this.mEtContent.getText().toString().trim());
    }

    private void initLeaveMessageAdapter() {
        this.leaveMsgHeadView = new LeaveMsgHeadView(this.mContext);
        this.leaveMessageAdapter = new LeaveMessageAdapter(this.msgList);
        this.rvMsgDetailList.setLayoutManager(new LinearLayoutManager(this.mContext));
        this.rvMsgDetailList.setHasFixedSize(true);
        this.rvMsgDetailList.addItemDecoration(new RVDividerListMsg(this.mContext, 17170445));
        this.leaveMessageAdapter.bindToRecyclerView(this.rvMsgDetailList);
        this.rvMsgDetailList.setAdapter(this.leaveMessageAdapter);
        this.leaveMessageAdapter.addHeaderView(this.leaveMsgHeadView);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendLeaveMessageRequest(String str) {
        AwardDetailEntity awardDetailEntity;
        if (!TextUtils.isEmpty(str) && (awardDetailEntity = this.mAwardDetail) != null) {
            ((AwardDetailPresenter) this.mPresenter).sendLeaveMessage(awardDetailEntity.getWinningRecordId(), str, this.isAwarded ? this.mAwardDetail.getAnchorId() : this.mAwardDetail.getUserId());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendRequest(boolean z) {
        ((AwardDetailPresenter) this.mPresenter).getAwardDetail(this.mStateView, this.mRecordId, z);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IAwardDetailView
    public void onAddAddressSuccess(MessageDetailEntity messageDetailEntity) {
        showToast(R$string.fq_hd_add_address_success);
        this.leaveMessageAdapter.addData((LeaveMessageAdapter) messageDetailEntity);
        this.leaveMsgHeadView.updateWinningStatus(this.isAwarded, "1");
        sendPostEvent();
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IAwardDetailView
    public void onAddAddressFailure(int i, String str) {
        showToast(R$string.fq_hd_add_address_failure);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IAwardDetailView
    public void onGetMessageDetailSuccess(List<MessageDetailEntity> list, boolean z, boolean z2) {
        if (z2) {
            this.leaveMessageAdapter.setNewData(list);
            if (!TextUtils.isEmpty(this.mAwardDetail.getAddress()) && isShowAddressType()) {
                this.leaveMessageAdapter.addData(0, (int) ((AwardDetailPresenter) this.mPresenter).getAddAddressDetailEntity(this.mAwardDetail.getUserId(), this.mAwardDetail.getAddress(), this.mAwardDetail.getSaveAddressTime(), TextUtils.equals(this.mAwardDetail.getWinningStatus(), "2"), this.isAwarded));
            }
            scrollToPosition();
        } else {
            this.leaveMessageAdapter.addData((Collection) list);
        }
        AppUtils.updateRefreshLayoutFinishStatus(this.mSmartRefreshLayout, z, z2);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IAwardDetailView
    public void onGetAwardDetailSuccess(AwardDetailEntity awardDetailEntity) {
        this.mAwardDetail = awardDetailEntity;
        this.leaveMsgHeadView.initData(this.isAwarded, awardDetailEntity);
        ((AwardDetailPresenter) this.mPresenter).anchorAvatar = this.isAwarded ? this.mAwardDetail.getAnchorAvatar() : awardDetailEntity.getUserAvatar();
        boolean z = true;
        this.pageNum = 1;
        ((AwardDetailPresenter) this.mPresenter).getMessageDetail(this.mRecordId, this.pageNum, true);
        EditText editText = this.mEtContent;
        if (!TextUtils.equals("0", this.mAwardDetail.getWinningStatus()) && !TextUtils.equals("1", this.mAwardDetail.getWinningStatus())) {
            z = false;
        }
        editText.setEnabled(z);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IAwardDetailView
    public void onAddMessageSuccess(MessageDetailEntity messageDetailEntity) {
        this.mEtContent.setText("");
        this.leaveMessageAdapter.addData((LeaveMessageAdapter) messageDetailEntity);
        SoftKeyboardUtils.hideSoftKeyboard(this.mActivity);
        scrollToPosition();
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar immersionBar = this.mImmersionBar;
        immersionBar.keyboardEnable(true);
        immersionBar.init();
    }

    private boolean isShowAddressType() {
        AwardDetailEntity awardDetailEntity = this.mAwardDetail;
        if (awardDetailEntity == null) {
            return false;
        }
        return TextUtils.equals(awardDetailEntity.getWinningStatus(), "1") || TextUtils.equals(this.mAwardDetail.getWinningStatus(), "2");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyDataSetChanged() {
        Observable.just(this.leaveMessageAdapter.getData()).map($$Lambda$AwardDetailActivity$KWtBubDMyx7oREM1MiNyMXUono.INSTANCE).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<Boolean>() { // from class: com.tomatolive.library.ui.activity.mylive.AwardDetailActivity.7
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(Boolean bool) {
                AwardDetailActivity.this.leaveMessageAdapter.notifyDataSetChanged();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ Boolean lambda$notifyDataSetChanged$2(List list) throws Exception {
        Iterator it2 = list.iterator();
        while (it2.hasNext()) {
            MessageDetailEntity messageDetailEntity = (MessageDetailEntity) it2.next();
            if (messageDetailEntity.getType() == 290) {
                messageDetailEntity.setSelected(true);
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showSaveGivenPrizeInfoDialog() {
        SureCancelDialog.newInstance(this.mContext.getString(R$string.fq_hd_ensure_give_prize), this.mContext.getString(R$string.fq_hd_ensure_give_prize_content), this.mContext.getString(R$string.fq_btn_cancel), this.mContext.getString(R$string.fq_hd_has_given_award), R$color.fq_text_black, new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.AwardDetailActivity.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ((AwardDetailPresenter) ((BaseActivity) AwardDetailActivity.this).mPresenter).saveGivenPrizeInfo(AwardDetailActivity.this.mRecordId, new ResultCallBack<Object>() { // from class: com.tomatolive.library.ui.activity.mylive.AwardDetailActivity.8.1
                    @Override // com.tomatolive.library.http.ResultCallBack
                    public void onError(int i, String str) {
                    }

                    @Override // com.tomatolive.library.http.ResultCallBack
                    public void onSuccess(Object obj) {
                        AwardDetailActivity awardDetailActivity = AwardDetailActivity.this;
                        awardDetailActivity.sendLeaveMessageRequest(((BaseActivity) awardDetailActivity).mContext.getString(R$string.fq_hd_prize_deliver_tips));
                        AwardDetailActivity.this.notifyDataSetChanged();
                        AwardDetailActivity.this.leaveMsgHeadView.updateWinningStatus(AwardDetailActivity.this.isAwarded, "2");
                        AwardDetailActivity.this.sendPostEvent();
                    }
                });
            }
        }).show(getSupportFragmentManager());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendPostEvent() {
        EventBus.getDefault().post(new AwardDetailEvent());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isSubmitAppeal() {
        AwardDetailEntity awardDetailEntity = this.mAwardDetail;
        if (awardDetailEntity == null) {
            return true;
        }
        return DateUtils.getDayByMinusDate(new Date(NumberUtils.string2long(awardDetailEntity.getWinningTime()) * 1000), new Date()) > 7;
    }
}
