package com.tomatolive.library.p136ui.view.custom;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.blankj.utilcode.util.SPUtils;
import com.j256.ormlite.stmt.query.SimpleComparison;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.model.BannerEntity;
import com.tomatolive.library.model.QMInteractTaskEntity;
import com.tomatolive.library.model.SocketMessageEvent;
import com.tomatolive.library.p136ui.interfaces.OnLiveAdBannerClickListener;
import com.tomatolive.library.p136ui.view.custom.TaskBoxView;
import com.tomatolive.library.p136ui.view.task.TaskBoxUtils;
import com.tomatolive.library.p136ui.view.widget.BannerWebView;
import com.tomatolive.library.p136ui.view.widget.bgabanner.BGABanner;
import com.tomatolive.library.p136ui.view.widget.bgabanner.BGAViewPager;
import com.tomatolive.library.p136ui.view.widget.bgabanner.TopBannerUtils;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.GlideUtils;
import com.tomatolive.library.utils.LogConstants;
import com.tomatolive.library.utils.NumberUtils;
import com.tomatolive.library.utils.UserInfoManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.custom.LiveAdBannerBottomView */
/* loaded from: classes3.dex */
public class LiveAdBannerBottomView extends LinearLayout {
    private BGABanner bannerView;
    private ImageView ivBannerArrow;
    private ImageView ivBannerClose;
    private ImageView ivBannerTopClose;
    private Context mContext;
    private TaskBoxView mTaskBoxView;
    private OnLiveAdBannerClickListener onAdBannerClickListener;
    private RelativeLayout rlBannerBottomBg;
    private RelativeLayout rlBannerTopBg;
    private List<QMInteractTaskEntity> taskEntityList = new ArrayList();
    private TopBannerUtils topBannerUtils;
    private BGAViewPager vpTop;

    public LiveAdBannerBottomView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        initView();
        initListener();
    }

    private void initView() {
        setOrientation(1);
        LinearLayout.inflate(this.mContext, R$layout.fq_layout_live_banner_bottom_view, this);
        this.bannerView = (BGABanner) findViewById(R$id.banner_bottom);
        this.ivBannerClose = (ImageView) findViewById(R$id.iv_banner_close);
        this.ivBannerArrow = (ImageView) findViewById(R$id.iv_banner_arrow);
        this.rlBannerBottomBg = (RelativeLayout) findViewById(R$id.rl_banner_bottom_bg);
        this.mTaskBoxView = (TaskBoxView) findViewById(R$id.task_view);
        this.rlBannerTopBg = (RelativeLayout) findViewById(R$id.rl_banner_top_bg);
        this.ivBannerTopClose = (ImageView) findViewById(R$id.iv_banner_top_close);
        this.vpTop = (BGAViewPager) findViewById(R$id.vp_top);
        this.topBannerUtils = new TopBannerUtils(this.vpTop, this.mContext);
    }

    private void initListener() {
        this.ivBannerClose.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.custom.-$$Lambda$LiveAdBannerBottomView$7y7Rxf28vIrK2jdOv0SSaEPrbeE
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                LiveAdBannerBottomView.this.lambda$initListener$0$LiveAdBannerBottomView(view);
            }
        });
        this.ivBannerTopClose.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.custom.-$$Lambda$LiveAdBannerBottomView$FvRJniuqOnmNjIwW7yQd9BT6jyc
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                LiveAdBannerBottomView.this.lambda$initListener$1$LiveAdBannerBottomView(view);
            }
        });
    }

    public /* synthetic */ void lambda$initListener$0$LiveAdBannerBottomView(View view) {
        this.rlBannerBottomBg.setVisibility(8);
        this.ivBannerClose.setVisibility(8);
        this.ivBannerArrow.setVisibility(8);
    }

    public /* synthetic */ void lambda$initListener$1$LiveAdBannerBottomView(View view) {
        SPUtils.getInstance().put(ConstantUtils.IS_CLOSE_QM_WINDOW, true);
        closeBannerTop();
    }

    private void closeBannerTop() {
        this.rlBannerTopBg.setVisibility(8);
        this.ivBannerTopClose.setVisibility(8);
        this.topBannerUtils.onReleaseData();
        this.taskEntityList.clear();
    }

    public void addHdLotteryWindowView(boolean z, String str, long j) {
        if (SPUtils.getInstance().getBoolean(ConstantUtils.IS_CLOSE_QM_WINDOW)) {
            return;
        }
        showTopBannerView(z);
        this.topBannerUtils.addHdLotteryWindowView(z, str, j);
    }

    public void onDeleteHdLotteryWindowView() {
        this.topBannerUtils.onDeleteHdLotteryWindowView();
        if (this.topBannerUtils.isEmpty()) {
            closeBannerTop();
        }
    }

    private void showTopBannerView(boolean z) {
        if (this.rlBannerTopBg.getVisibility() != 0) {
            this.rlBannerTopBg.setVisibility(0);
        }
        if (!z && this.ivBannerTopClose.getVisibility() != 0) {
            this.ivBannerTopClose.setVisibility(0);
        }
    }

    public void setOnInteractWindowClickListener(TopBannerUtils.InteractWindowListener interactWindowListener) {
        this.topBannerUtils.setListener(interactWindowListener);
    }

    public void initAdBannerImages(final String str, final String str2, List<BannerEntity> list) {
        if (this.bannerView == null) {
            return;
        }
        List<BannerEntity> imgBannerItem = AppUtils.getImgBannerItem(list);
        int i = 4;
        if (imgBannerItem == null || imgBannerItem.isEmpty()) {
            this.rlBannerBottomBg.setVisibility(4);
            this.ivBannerClose.setVisibility(4);
            this.ivBannerArrow.setVisibility(4);
            return;
        }
        this.rlBannerBottomBg.setVisibility(0);
        this.ivBannerArrow.setVisibility(0);
        ImageView imageView = this.ivBannerClose;
        if (isAllowClose(list)) {
            i = 0;
        }
        imageView.setVisibility(i);
        this.bannerView.setAdapter(new BGABanner.Adapter() { // from class: com.tomatolive.library.ui.view.custom.-$$Lambda$LiveAdBannerBottomView$tyrYntpT4r8sPubuuJJ2eurjffU
            @Override // com.tomatolive.library.p136ui.view.widget.bgabanner.BGABanner.Adapter
            public final void fillBannerItem(BGABanner bGABanner, View view, Object obj, int i2) {
                LiveAdBannerBottomView.this.lambda$initAdBannerImages$2$LiveAdBannerBottomView(str, str2, bGABanner, view, (BannerEntity) obj, i2);
            }
        });
        BGABanner bGABanner = this.bannerView;
        boolean z = true;
        if (imgBannerItem.size() <= 1) {
            z = false;
        }
        bGABanner.setAutoPlayAble(z);
        this.bannerView.setDataWithWebView(getBannerView(imgBannerItem), imgBannerItem);
        this.bannerView.setDelegate(new BGABanner.Delegate() { // from class: com.tomatolive.library.ui.view.custom.-$$Lambda$LiveAdBannerBottomView$OUV91gJyGC8_PYk2TmNNyfu1v_w
            @Override // com.tomatolive.library.p136ui.view.widget.bgabanner.BGABanner.Delegate
            public final void onBannerItemClick(BGABanner bGABanner2, View view, Object obj, int i2) {
                LiveAdBannerBottomView.this.lambda$initAdBannerImages$3$LiveAdBannerBottomView(bGABanner2, view, (BannerEntity) obj, i2);
            }
        });
    }

    public /* synthetic */ void lambda$initAdBannerImages$2$LiveAdBannerBottomView(String str, String str2, BGABanner bGABanner, View view, BannerEntity bannerEntity, int i) {
        if (view instanceof ImageView) {
            loadImg(bannerEntity.img, (ImageView) view);
        }
        if (view instanceof BannerWebView) {
            BannerWebView bannerWebView = (BannerWebView) view;
            if (bannerWebView.isLoadBoolean()) {
                return;
            }
            bannerWebView.loadUrl(getWebH5Url(bannerEntity.img, str, str2));
        }
    }

    public /* synthetic */ void lambda$initAdBannerImages$3$LiveAdBannerBottomView(BGABanner bGABanner, View view, BannerEntity bannerEntity, int i) {
        onAdBannerClick(bannerEntity);
    }

    private List<View> getBannerView(List<BannerEntity> list) {
        ArrayList arrayList = new ArrayList();
        for (BannerEntity bannerEntity : list) {
            if (bannerEntity.isWebH5View()) {
                arrayList.add(new BannerWebView(getContext()));
            } else {
                ImageView imageView = new ImageView(getContext());
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                arrayList.add(imageView);
            }
        }
        return arrayList;
    }

    private void onAdBannerClick(BannerEntity bannerEntity) {
        AppUtils.onAdvChannelHitsListener(this.mContext, bannerEntity.f5830id, "2");
        OnLiveAdBannerClickListener onLiveAdBannerClickListener = this.onAdBannerClickListener;
        if (onLiveAdBannerClickListener != null) {
            onLiveAdBannerClickListener.onAdBannerClick(bannerEntity);
        }
    }

    private void loadImg(String str, ImageView imageView) {
        GlideUtils.loadAdBannerImageForRoundView(this.mContext, imageView, str, R$drawable.fq_shape_default_banner_cover_bg);
    }

    public void setOnAdBannerClickListener(OnLiveAdBannerClickListener onLiveAdBannerClickListener) {
        this.onAdBannerClickListener = onLiveAdBannerClickListener;
    }

    private String getWebH5Url(String str, String str2, String str3) {
        return str + "?" + LogConstants.APP_ID + SimpleComparison.EQUAL_TO_OPERATION + str2 + "&" + LogConstants.OPEN_ID + SimpleComparison.EQUAL_TO_OPERATION + str3;
    }

    private boolean isAllowClose(List<BannerEntity> list) {
        boolean z = true;
        for (BannerEntity bannerEntity : list) {
            if (bannerEntity.isAllowClose()) {
                z = false;
            }
        }
        return z;
    }

    public void setTaskBoxVisibility(int i) {
        TaskBoxView taskBoxView = this.mTaskBoxView;
        if (taskBoxView != null) {
            taskBoxView.setVisibility(i);
        }
    }

    public void changeRedCountForTaskBox(boolean z) {
        TaskBoxView taskBoxView = this.mTaskBoxView;
        if (taskBoxView != null) {
            taskBoxView.changeRedCount(z);
        }
    }

    public void checkToCountdownForTaskBox() {
        TaskBoxView taskBoxView = this.mTaskBoxView;
        if (taskBoxView != null) {
            taskBoxView.checkToCountdown();
        }
    }

    public void refreshTaskButtonForTaskBox() {
        TaskBoxView taskBoxView = this.mTaskBoxView;
        if (taskBoxView != null) {
            taskBoxView.refreshTaskButton();
        }
    }

    public void releaseForTaskBox() {
        TaskBoxView taskBoxView = this.mTaskBoxView;
        if (taskBoxView != null) {
            taskBoxView.release();
        }
        TaskBoxUtils.getInstance().clear();
    }

    public void setOnRefreshTaskListener(TaskBoxView.OnRefreshTaskListener onRefreshTaskListener) {
        TaskBoxView taskBoxView = this.mTaskBoxView;
        if (taskBoxView != null) {
            taskBoxView.setOnRefreshTaskListener(onRefreshTaskListener);
        }
    }

    public void releaseWebView() {
        BGABanner bGABanner = this.bannerView;
        if (bGABanner != null) {
            bGABanner.onDestroyWebView();
        }
    }

    public void onReleaseTopBanner() {
        closeBannerTop();
    }

    public void onReleaseHdLotteryWindowView() {
        this.topBannerUtils.onReleaseLotteryWindowView();
    }

    public void onLotteryEnd() {
        this.topBannerUtils.onLotteryEnd();
    }

    public boolean hasQMTaskView() {
        return this.topBannerUtils.hasQMTaskView();
    }

    public void showRedPoint() {
        this.topBannerUtils.showRedPoint();
    }

    public void hideRedPoint() {
        this.topBannerUtils.hideRedPoint();
    }

    public QMInteractTaskEntity getCurTaskEntity() {
        return this.topBannerUtils.getCurTaskEntity();
    }

    public void initIntimateTaskList(boolean z, List<QMInteractTaskEntity> list) {
        this.taskEntityList.clear();
        this.taskEntityList.addAll(list);
        dealIntimateTaskShow(z, true, this.taskEntityList.get(0));
        if (this.taskEntityList.size() > 1) {
            for (QMInteractTaskEntity qMInteractTaskEntity : this.taskEntityList) {
                if (!TextUtils.equals(qMInteractTaskEntity.putUserId, UserInfoManager.getInstance().getUserId())) {
                    showRedPoint();
                    return;
                }
            }
        }
    }

    public void dealIntimateTaskShowFromSocket(boolean z, SocketMessageEvent.ResultData resultData) {
        QMInteractTaskEntity formatQMInteractTaskEntity = formatQMInteractTaskEntity(resultData);
        if (resultData.isDisPlayWindow()) {
            if (isAddTask(formatQMInteractTaskEntity)) {
                this.taskEntityList.add(formatQMInteractTaskEntity);
            }
            if (hasQMTaskView()) {
                return;
            }
            dealIntimateTaskShow(z, true, this.taskEntityList.get(0));
            return;
        }
        removeTaskList(formatQMInteractTaskEntity.taskId);
        dealIntimateTaskShow(z, false, formatQMInteractTaskEntity);
    }

    public void dealIntimateTaskShow(boolean z, boolean z2, QMInteractTaskEntity qMInteractTaskEntity) {
        if (!z2) {
            QMInteractTaskEntity curTaskEntity = getCurTaskEntity();
            if (curTaskEntity == null || !TextUtils.equals(qMInteractTaskEntity.taskId, curTaskEntity.taskId)) {
                return;
            }
            if (this.taskEntityList.isEmpty()) {
                onDeleteQMNoticeWindow();
                return;
            } else {
                addQMNoticeWindow(z, this.taskEntityList.get(0));
                return;
            }
        }
        addQMNoticeWindow(z, qMInteractTaskEntity);
    }

    public void dealIntimateTaskChargeFormSocket(SocketMessageEvent.ResultData resultData) {
        List<QMInteractTaskEntity> list = resultData.intimateTaskChargeList;
        synchronized (LiveAdBannerBottomView.class) {
            for (QMInteractTaskEntity qMInteractTaskEntity : this.taskEntityList) {
                for (QMInteractTaskEntity qMInteractTaskEntity2 : list) {
                    if (TextUtils.equals(qMInteractTaskEntity.taskId, qMInteractTaskEntity2.taskId)) {
                        qMInteractTaskEntity.giftMarkId = qMInteractTaskEntity2.giftMarkId;
                        qMInteractTaskEntity.giftNum = qMInteractTaskEntity2.giftNum;
                        qMInteractTaskEntity.chargeGiftNum = qMInteractTaskEntity2.chargeGiftNum;
                    }
                }
            }
        }
        QMInteractTaskEntity curTaskEntity = getCurTaskEntity();
        if (curTaskEntity != null) {
            for (QMInteractTaskEntity qMInteractTaskEntity3 : list) {
                if (TextUtils.equals(qMInteractTaskEntity3.taskId, curTaskEntity.taskId)) {
                    setChargeProgress(NumberUtils.string2int(qMInteractTaskEntity3.chargeGiftNum));
                }
            }
        }
    }

    public void dealIntimateTaskChargeCompleteFromSocket(SocketMessageEvent.ResultData resultData) {
        synchronized (LiveAdBannerBottomView.class) {
            for (QMInteractTaskEntity qMInteractTaskEntity : this.taskEntityList) {
                if (TextUtils.equals(resultData.taskId, qMInteractTaskEntity.taskId)) {
                    qMInteractTaskEntity.chargeGiftNum = qMInteractTaskEntity.giftNum;
                }
            }
        }
        QMInteractTaskEntity curTaskEntity = getCurTaskEntity();
        if (curTaskEntity == null || !TextUtils.equals(resultData.taskId, curTaskEntity.taskId)) {
            return;
        }
        setChargeProgress(NumberUtils.string2int(curTaskEntity.giftNum));
    }

    public int getTaskListSize() {
        return this.taskEntityList.size();
    }

    public boolean isShowCloseLiveTips() {
        for (QMInteractTaskEntity qMInteractTaskEntity : this.taskEntityList) {
            if (TextUtils.equals(qMInteractTaskEntity.taskType, "2")) {
                return true;
            }
        }
        return false;
    }

    public boolean isOneselfInitiateTask() {
        List<QMInteractTaskEntity> list = this.taskEntityList;
        if (list == null || list.isEmpty()) {
            return false;
        }
        for (QMInteractTaskEntity qMInteractTaskEntity : this.taskEntityList) {
            if (TextUtils.equals(qMInteractTaskEntity.putUserId, UserInfoManager.getInstance().getUserId())) {
                return true;
            }
        }
        return false;
    }

    public boolean isShowQMInviteChatMsg(SocketMessageEvent.ResultData resultData) {
        if (TextUtils.isEmpty(resultData.taskId)) {
            return false;
        }
        List<QMInteractTaskEntity> list = this.taskEntityList;
        if (list == null || list.isEmpty()) {
            return resultData.isShowQMInviteChatMsg();
        }
        for (QMInteractTaskEntity qMInteractTaskEntity : this.taskEntityList) {
            if (TextUtils.equals(resultData.taskId, qMInteractTaskEntity.taskId)) {
                return false;
            }
        }
        return true;
    }

    private void removeTaskList(String str) {
        synchronized (LiveAdBannerBottomView.class) {
            Iterator<QMInteractTaskEntity> it2 = this.taskEntityList.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    break;
                }
                QMInteractTaskEntity next = it2.next();
                if (TextUtils.equals(str, next.taskId)) {
                    this.taskEntityList.remove(next);
                    break;
                }
            }
        }
    }

    private void onDeleteQMNoticeWindow() {
        this.topBannerUtils.onReleaseQMNoticeWindow();
        if (this.topBannerUtils.isEmpty()) {
            closeBannerTop();
        }
    }

    private void addQMNoticeWindow(boolean z, QMInteractTaskEntity qMInteractTaskEntity) {
        if (SPUtils.getInstance().getBoolean(ConstantUtils.IS_CLOSE_QM_WINDOW)) {
            return;
        }
        showTopBannerView(z);
        this.topBannerUtils.addQMNoticeWindow(qMInteractTaskEntity);
    }

    private void setChargeProgress(int i) {
        this.topBannerUtils.setProgressValue(i);
    }

    private boolean isAddTask(QMInteractTaskEntity qMInteractTaskEntity) {
        for (QMInteractTaskEntity qMInteractTaskEntity2 : this.taskEntityList) {
            if (TextUtils.equals(qMInteractTaskEntity.taskId, qMInteractTaskEntity2.taskId)) {
                return false;
            }
        }
        return true;
    }

    private QMInteractTaskEntity formatQMInteractTaskEntity(SocketMessageEvent.ResultData resultData) {
        QMInteractTaskEntity qMInteractTaskEntity = new QMInteractTaskEntity();
        String str = resultData.taskId;
        qMInteractTaskEntity.f5845id = str;
        qMInteractTaskEntity.taskId = str;
        qMInteractTaskEntity.taskType = resultData.taskType;
        qMInteractTaskEntity.taskName = resultData.taskName;
        qMInteractTaskEntity.putName = resultData.putName;
        qMInteractTaskEntity.giftMarkId = resultData.giftMarkId;
        qMInteractTaskEntity.giftNum = resultData.giftNum;
        qMInteractTaskEntity.chargeGiftNum = resultData.chargeGiftNum;
        qMInteractTaskEntity.giftUrl = resultData.giftUrl;
        qMInteractTaskEntity.putUserId = resultData.putUserId;
        qMInteractTaskEntity.createTime = resultData.createTime;
        return qMInteractTaskEntity;
    }
}
