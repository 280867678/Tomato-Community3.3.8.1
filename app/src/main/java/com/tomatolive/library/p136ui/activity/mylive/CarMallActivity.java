package com.tomatolive.library.p136ui.activity.mylive;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.p005v7.widget.DefaultItemAnimator;
import android.support.p005v7.widget.GridLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.view.View;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.base.BaseActivity;
import com.tomatolive.library.model.CarEntity;
import com.tomatolive.library.model.CarHistoryRecordEntity;
import com.tomatolive.library.p136ui.adapter.CarMallAdapter;
import com.tomatolive.library.p136ui.interfaces.impl.SimpleAnimatorListener;
import com.tomatolive.library.p136ui.presenter.CarMallPresenter;
import com.tomatolive.library.p136ui.view.dialog.CarBuyDialog;
import com.tomatolive.library.p136ui.view.divider.RVDividerCarMall;
import com.tomatolive.library.p136ui.view.emptyview.RecyclerIncomeEmptyView;
import com.tomatolive.library.p136ui.view.headview.CarMallHeadView;
import com.tomatolive.library.p136ui.view.iview.ICarMallView;
import com.tomatolive.library.p136ui.view.widget.StateView;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.HandlerUtils;
import com.tomatolive.library.utils.LogEventUtils;
import com.tomatolive.library.utils.UserInfoManager;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/* renamed from: com.tomatolive.library.ui.activity.mylive.CarMallActivity */
/* loaded from: classes3.dex */
public class CarMallActivity extends BaseActivity<CarMallPresenter> implements ICarMallView, Handler.Callback {
    private static final int CAR_NOTICE = 10004;
    private CarMallAdapter mAdapter;
    private Handler mHandler;
    private CarMallHeadView mHeadView;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mSmartRefreshLayout;
    private ArrayList<CarHistoryRecordEntity> mCarNoticeQueue = new ArrayList<>();
    private final long mPlayPeriod = 9000;
    private boolean mCanShowNotice = true;
    private boolean isRouterFlag = true;
    private AtomicInteger noticeQueuePosition = new AtomicInteger(0);

    @Override // com.tomatolive.library.base.BaseActivity
    public boolean isAutoRefreshDataEnable() {
        return true;
    }

    @Override // com.tomatolive.library.p136ui.view.iview.ICarMallView
    public void onBuyCarFail(int i) {
    }

    @Override // com.tomatolive.library.base.BaseView
    public void onResultError(int i) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.base.BaseActivity
    /* renamed from: createPresenter  reason: collision with other method in class */
    public CarMallPresenter mo6636createPresenter() {
        return new CarMallPresenter(this.mContext, this);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    protected int getLayoutId() {
        return R$layout.fq_activity_car_mall;
    }

    @Override // com.tomatolive.library.base.BaseActivity
    protected View injectStateView() {
        return findViewById(R$id.fl_content_view);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initView(Bundle bundle) {
        this.isRouterFlag = getIntent().getBooleanExtra(ConstantUtils.RESULT_FLAG, true);
        this.mRecyclerView = (RecyclerView) findViewById(R$id.recycler_view);
        this.mSmartRefreshLayout = (SmartRefreshLayout) findViewById(R$id.refreshLayout);
        this.mHandler = HandlerUtils.getInstance().startIOThread(CarMallActivity.class.getName(), this);
        if (this.isRouterFlag) {
            setActivityTitle(getString(R$string.fq_my_live_car_mall));
        } else {
            setActivityRightTitle(getString(R$string.fq_my_live_car_mall), getString(R$string.fq_my_live_my_garage), new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$CarMallActivity$7oZbCDEfzXo3_fISX7k5RD7Rm2k
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    CarMallActivity.this.lambda$initView$0$CarMallActivity(view);
                }
            });
        }
        initAdapter();
        ((CarMallPresenter) this.mPresenter).getCarPurchaseCarouselRecord();
        ((CarMallPresenter) this.mPresenter).getCarList(this.mStateView, this.pageNum, true, true);
    }

    public /* synthetic */ void lambda$initView$0$CarMallActivity(View view) {
        startActivity(MyCarActivity.class);
    }

    private void initAdapter() {
        this.mAdapter = new CarMallAdapter(R$layout.fq_item_list_car_mall);
        this.mHeadView = new CarMallHeadView(this.mContext);
        ((DefaultItemAnimator) this.mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        this.mRecyclerView.setLayoutManager(new GridLayoutManager(this.mContext, 2));
        this.mRecyclerView.addItemDecoration(new RVDividerCarMall(this.mContext, 17170445, true));
        this.mRecyclerView.setAdapter(this.mAdapter);
        this.mAdapter.setEmptyView(new RecyclerIncomeEmptyView(this.mContext, 41));
        this.mAdapter.addHeaderView(this.mHeadView);
        this.mAdapter.bindToRecyclerView(this.mRecyclerView);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initListener() {
        super.initListener();
        this.mStateView.setOnRetryClickListener(new StateView.OnRetryClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$CarMallActivity$sZw9g_qPnl7Ix8HFROafBk_3uGE
            @Override // com.tomatolive.library.p136ui.view.widget.StateView.OnRetryClickListener
            public final void onRetryClick() {
                CarMallActivity.this.lambda$initListener$1$CarMallActivity();
            }
        });
        this.mSmartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() { // from class: com.tomatolive.library.ui.activity.mylive.CarMallActivity.1
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                CarMallActivity carMallActivity = CarMallActivity.this;
                carMallActivity.pageNum++;
                carMallActivity.sendRequest(false, false);
            }

            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.resetNoMoreData();
                CarMallActivity carMallActivity = CarMallActivity.this;
                carMallActivity.pageNum = 1;
                carMallActivity.sendRequest(false, true);
                refreshLayout.mo6481finishRefresh();
            }
        });
        this.mHeadView.setCarNoticeAnimListener(new SimpleAnimatorListener() { // from class: com.tomatolive.library.ui.activity.mylive.CarMallActivity.2
            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleAnimatorListener, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                CarMallActivity.this.mCanShowNotice = false;
            }

            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleAnimatorListener, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                CarMallActivity.this.mCanShowNotice = true;
                if (CarMallActivity.this.mCarNoticeQueue.isEmpty()) {
                    CarMallActivity.this.mHeadView.setVisibility(8);
                }
            }

            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleAnimatorListener, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                CarMallActivity.this.mCanShowNotice = true;
            }
        });
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$CarMallActivity$OjfxsLVY1URXH12dK4d2efpyO_M
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                CarMallActivity.this.lambda$initListener$2$CarMallActivity(baseQuickAdapter, view, i);
            }
        });
        this.mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$CarMallActivity$m-70TtnaHA_zE-sdrP1t8BtjYxs
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
            public final void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                CarMallActivity.this.lambda$initListener$4$CarMallActivity(baseQuickAdapter, view, i);
            }
        });
    }

    public /* synthetic */ void lambda$initListener$1$CarMallActivity() {
        this.pageNum = 1;
        sendRequest(true, true);
    }

    public /* synthetic */ void lambda$initListener$2$CarMallActivity(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        CarEntity carEntity = (CarEntity) baseQuickAdapter.getItem(i);
        if (carEntity == null) {
            return;
        }
        Intent intent = new Intent(this.mContext, CarMallDetailActivity.class);
        intent.putExtra(ConstantUtils.RESULT_ITEM, carEntity);
        startActivity(intent);
    }

    public /* synthetic */ void lambda$initListener$4$CarMallActivity(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        final CarEntity carEntity = (CarEntity) baseQuickAdapter.getItem(i);
        if (view.getId() != R$id.tv_money || carEntity == null) {
            return;
        }
        if (!carEntity.isPublicCar()) {
            showToast(R$string.fq_private_car_buy_tips);
        } else {
            CarBuyDialog.newInstance(carEntity, new CarBuyDialog.OnBuyListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$CarMallActivity$Zwf8aEkPSfAuT16pMU20cMwQ_Es
                @Override // com.tomatolive.library.p136ui.view.dialog.CarBuyDialog.OnBuyListener
                public final void onBuy(String str, String str2) {
                    CarMallActivity.this.lambda$null$3$CarMallActivity(carEntity, str, str2);
                }
            }).show(getSupportFragmentManager());
        }
    }

    public /* synthetic */ void lambda$null$3$CarMallActivity(CarEntity carEntity, String str, String str2) {
        ((CarMallPresenter) this.mPresenter).buyCar(carEntity, str, str2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendRequest(boolean z, boolean z2) {
        ((CarMallPresenter) this.mPresenter).getCarList(this.mStateView, this.pageNum, z, z2);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.ICarMallView
    public void onBuyCarSuccess(CarEntity carEntity, String str, String str2) {
        showToast(R$string.fq_purchase_succeeded);
        LogEventUtils.uploadBuyCar(carEntity.f5836id, getString(carEntity.isPrivatePermission() ? R$string.fq_private_car : R$string.fq_public_car), str, carEntity.name, str2, UserInfoManager.getInstance().getExpGrade());
    }

    @Override // com.tomatolive.library.p136ui.view.iview.ICarMallView
    public void onDataListSuccess(List<CarEntity> list, boolean z, boolean z2) {
        this.mAdapter.setNewData(list);
        AppUtils.updateRefreshLayoutFinishStatus(this.mSmartRefreshLayout, z, z2);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.ICarMallView
    public void onDataListFail(boolean z) {
        if (!z) {
            this.mSmartRefreshLayout.finishLoadMore();
        }
    }

    @Override // com.tomatolive.library.p136ui.view.iview.ICarMallView
    public void onGetCarPurchaseCarouselRecordSuccess(List<CarHistoryRecordEntity> list) {
        ArrayList<CarHistoryRecordEntity> arrayList = this.mCarNoticeQueue;
        if (arrayList == null) {
            this.mCarNoticeQueue = new ArrayList<>();
        } else {
            arrayList.clear();
        }
        this.mCarNoticeQueue.addAll(list);
        if (!this.mHandler.hasMessages(10004) && !list.isEmpty()) {
            this.mHandler.sendEmptyMessage(10004);
        } else {
            this.mHeadView.setVisibility(8);
        }
    }

    @Override // com.tomatolive.library.p136ui.view.iview.ICarMallView
    public void onGetCarPurchaseCarouselRecordFail() {
        this.mHeadView.setVisibility(8);
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(Message message) {
        if (message.what != 10004) {
            return true;
        }
        dealCarNotice();
        return true;
    }

    private void dealCarNotice() {
        Handler handler;
        if (this.mCanShowNotice) {
            this.mCanShowNotice = false;
            if (this.noticeQueuePosition.get() == this.mCarNoticeQueue.size()) {
                this.noticeQueuePosition.set(0);
            }
            int i = this.noticeQueuePosition.get();
            if (i >= this.mCarNoticeQueue.size()) {
                return;
            }
            final CarHistoryRecordEntity carHistoryRecordEntity = this.mCarNoticeQueue.get(i);
            this.noticeQueuePosition.incrementAndGet();
            if (carHistoryRecordEntity != null) {
                runOnUiThread(new Runnable() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$CarMallActivity$lZi9K28yaZg5jyRP8v8AOxpoNQ4
                    @Override // java.lang.Runnable
                    public final void run() {
                        CarMallActivity.this.lambda$dealCarNotice$5$CarMallActivity(carHistoryRecordEntity);
                    }
                });
            }
        }
        ArrayList<CarHistoryRecordEntity> arrayList = this.mCarNoticeQueue;
        if (arrayList == null || arrayList.isEmpty() || (handler = this.mHandler) == null) {
            return;
        }
        handler.sendEmptyMessage(10004);
    }

    public /* synthetic */ void lambda$dealCarNotice$5$CarMallActivity(CarHistoryRecordEntity carHistoryRecordEntity) {
        this.mHeadView.setVisibility(0);
        this.mHeadView.setCarNoticeNoticeAnim(carHistoryRecordEntity.getUserName(), carHistoryRecordEntity.getPostTime(), carHistoryRecordEntity.getCarName(), 9000L);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void onAutoRefreshData() {
        super.onAutoRefreshData();
        sendRequest(false, true);
    }

    @Override // com.tomatolive.library.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        HandlerUtils.getInstance().stopIOThread();
        ArrayList<CarHistoryRecordEntity> arrayList = this.mCarNoticeQueue;
        if (arrayList != null) {
            arrayList.clear();
        }
        CarMallHeadView carMallHeadView = this.mHeadView;
        if (carMallHeadView != null) {
            carMallHeadView.onDestroy();
        }
    }
}
