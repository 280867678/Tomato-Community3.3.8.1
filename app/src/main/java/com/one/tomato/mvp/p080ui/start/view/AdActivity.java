package com.one.tomato.mvp.p080ui.start.view;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.dueeeke.videoplayer.util.PlayerUtils;
import com.one.tomato.R$id;
import com.one.tomato.entity.C2516Ad;
import com.one.tomato.entity.ImageBean;
import com.one.tomato.entity.p079db.AdPage;
import com.one.tomato.mvp.base.AppManager;
import com.one.tomato.mvp.base.BaseApplication;
import com.one.tomato.mvp.base.presenter.MvpBasePresenter;
import com.one.tomato.mvp.base.view.IBaseView;
import com.one.tomato.mvp.base.view.MvpBaseActivity;
import com.one.tomato.mvp.p080ui.chess.view.ChessMainTabActivity;
import com.one.tomato.p085ui.MainTabActivity;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.thirdpart.video.controller.StartUpVideoController;
import com.one.tomato.thirdpart.video.player.StartUpVideoView;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.DataUploadUtil;
import com.one.tomato.utils.DisplayMetricsUtils;
import com.one.tomato.utils.PreferencesUtil;
import com.one.tomato.utils.TimerTaskUtil;
import com.one.tomato.utils.image.ImageLoaderUtil;
import com.one.tomato.utils.p087ad.AdUtil;
import java.util.ArrayList;
import java.util.HashMap;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: AdActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.start.view.AdActivity */
/* loaded from: classes3.dex */
public final class AdActivity extends MvpBaseActivity<IBaseView, MvpBasePresenter<IBaseView>> {
    public static final Companion Companion = new Companion(null);
    private HashMap _$_findViewCache;
    private AdPage page = new AdPage();
    private AdUtil adUtil = new AdUtil(this);

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
        return R.layout.activity_ad;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    /* renamed from: createPresenter */
    public MvpBasePresenter<IBaseView> mo6439createPresenter() {
        return null;
    }

    public final AdPage getPage() {
        return this.page;
    }

    /* compiled from: AdActivity.kt */
    /* renamed from: com.one.tomato.mvp.ui.start.view.AdActivity$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void startActivity(Context context) {
            Intrinsics.checkParameterIsNotNull(context, "context");
            context.startActivity(new Intent(context, AdActivity.class));
        }
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initView() {
        PlayerUtils.hideActionBar(this);
        ((TextView) _$_findCachedViewById(R$id.tv_count_down)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.start.view.AdActivity$initView$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AdActivity.this.startMainTabActivity();
            }
        });
        ((ImageView) _$_findCachedViewById(R$id.iv_volume)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.start.view.AdActivity$initView$2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AdActivity.this.checkVolume();
            }
        });
        ((ImageView) _$_findCachedViewById(R$id.ad_img)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.start.view.AdActivity$initView$3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AdActivity.this.clickAd();
            }
        });
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initData() {
        showAd();
    }

    public final void startMainTabActivity() {
        BaseApplication application = BaseApplication.getApplication();
        Intrinsics.checkExpressionValueIsNotNull(application, "BaseApplication.getApplication()");
        if (application.isChess()) {
            ChessMainTabActivity.Companion companion = ChessMainTabActivity.Companion;
            companion.startActivity(this, companion.getTAB_ITEM_HOME());
        } else {
            MainTabActivity.startActivity(this, 0);
        }
        finish();
    }

    public final void checkVolume() {
        if (((StartUpVideoView) _$_findCachedViewById(R$id.videoView)) != null) {
            StartUpVideoView videoView = (StartUpVideoView) _$_findCachedViewById(R$id.videoView);
            Intrinsics.checkExpressionValueIsNotNull(videoView, "videoView");
            if (videoView.isMute()) {
                StartUpVideoView videoView2 = (StartUpVideoView) _$_findCachedViewById(R$id.videoView);
                Intrinsics.checkExpressionValueIsNotNull(videoView2, "videoView");
                videoView2.setMute(false);
                ((ImageView) _$_findCachedViewById(R$id.iv_volume)).setImageResource(R.drawable.start_up_video_volume_open);
                return;
            }
            StartUpVideoView videoView3 = (StartUpVideoView) _$_findCachedViewById(R$id.videoView);
            Intrinsics.checkExpressionValueIsNotNull(videoView3, "videoView");
            videoView3.setMute(true);
            ((ImageView) _$_findCachedViewById(R$id.iv_volume)).setImageResource(R.drawable.start_up_video_volume_close);
        }
    }

    public final void showAd() {
        ArrayList<AdPage> adPage = DBUtil.getAdPage(C2516Ad.TYPE_START);
        if (adPage == null || adPage.isEmpty()) {
            startMainTabActivity();
            return;
        }
        AdPage adPage2 = adPage.get(0);
        Intrinsics.checkExpressionValueIsNotNull(adPage2, "startAds.get(0)");
        this.page = adPage2;
        AdPage adPage3 = this.page;
        if ((adPage3 != null ? Integer.valueOf(adPage3.getWeight()) : null).intValue() > 0) {
            AdPage adPage4 = adPage.get(AdUtil.getListIndexByWeight(adPage));
            Intrinsics.checkExpressionValueIsNotNull(adPage4, "startAds.get(AdUtil.getL…tIndexByWeight(startAds))");
            this.page = adPage4;
        } else {
            int i = PreferencesUtil.getInstance().getInt("start_ad_index");
            if (i >= adPage.size()) {
                i = 0;
            }
            AdPage adPage5 = adPage.get(i);
            Intrinsics.checkExpressionValueIsNotNull(adPage5, "startAds.get(index)");
            this.page = adPage5;
            PreferencesUtil.getInstance().putInt("start_ad_index", i + 1);
        }
        int adTime = this.page.getAdTime();
        if (adTime == 0) {
            adTime = 3;
        }
        TextView tv_count_down = (TextView) _$_findCachedViewById(R$id.tv_count_down);
        Intrinsics.checkExpressionValueIsNotNull(tv_count_down, "tv_count_down");
        tv_count_down.setText(String.valueOf(adTime) + "s");
        TextView tv_count_down2 = (TextView) _$_findCachedViewById(R$id.tv_count_down);
        Intrinsics.checkExpressionValueIsNotNull(tv_count_down2, "tv_count_down");
        tv_count_down2.setVisibility(0);
        TimerTaskUtil.getInstance().onStart(adTime, new TimerTaskUtil.TimeTask() { // from class: com.one.tomato.mvp.ui.start.view.AdActivity$showAd$timerTask$1
            @Override // com.one.tomato.utils.TimerTaskUtil.TimeTask
            public void position(int i2) {
                TextView tv_count_down3 = (TextView) AdActivity.this._$_findCachedViewById(R$id.tv_count_down);
                Intrinsics.checkExpressionValueIsNotNull(tv_count_down3, "tv_count_down");
                tv_count_down3.setText(String.valueOf(i2) + "s");
                TextView tv_count_down4 = (TextView) AdActivity.this._$_findCachedViewById(R$id.tv_count_down);
                Intrinsics.checkExpressionValueIsNotNull(tv_count_down4, "tv_count_down");
                tv_count_down4.setEnabled(false);
            }

            @Override // com.one.tomato.utils.TimerTaskUtil.TimeTask
            public void stop() {
                if ("1".equals(AdActivity.this.getPage().getDirectApp())) {
                    AdActivity.this.startMainTabActivity();
                    return;
                }
                ((TextView) AdActivity.this._$_findCachedViewById(R$id.tv_count_down)).setText(R.string.common_close);
                TextView tv_count_down3 = (TextView) AdActivity.this._$_findCachedViewById(R$id.tv_count_down);
                Intrinsics.checkExpressionValueIsNotNull(tv_count_down3, "tv_count_down");
                tv_count_down3.setEnabled(true);
            }
        });
        if ("1".equals(this.page.getMaterialType())) {
            showAdImg();
        } else if ("2".equals(this.page.getMaterialType())) {
            showAdVideo();
        }
        viewAd();
    }

    public final void showAdImg() {
        String andImageUrl7201280Sec;
        ImageView ad_img = (ImageView) _$_findCachedViewById(R$id.ad_img);
        Intrinsics.checkExpressionValueIsNotNull(ad_img, "ad_img");
        ad_img.setVisibility(0);
        int densityDpi = DisplayMetricsUtils.getDensityDpi();
        if (densityDpi > 480) {
            andImageUrl7201280Sec = this.page.getAndImageUrl10802280Sec();
        } else if (densityDpi <= 480 && densityDpi >= 320) {
            andImageUrl7201280Sec = this.page.getAndImageUrl10801920Sec();
        } else {
            andImageUrl7201280Sec = this.page.getAndImageUrl7201280Sec();
        }
        ImageLoaderUtil.loadViewPagerOriginImage(this, (ImageView) _$_findCachedViewById(R$id.ad_img), null, new ImageBean(andImageUrl7201280Sec), 0);
    }

    public final void showAdVideo() {
        StartUpVideoView videoView = (StartUpVideoView) _$_findCachedViewById(R$id.videoView);
        Intrinsics.checkExpressionValueIsNotNull(videoView, "videoView");
        videoView.setVisibility(0);
        ImageView iv_volume = (ImageView) _$_findCachedViewById(R$id.iv_volume);
        Intrinsics.checkExpressionValueIsNotNull(iv_volume, "iv_volume");
        iv_volume.setVisibility(0);
        if (this.page.getLoopStatus() == 1) {
            ((StartUpVideoView) _$_findCachedViewById(R$id.videoView)).setLooping(true);
        } else {
            ((StartUpVideoView) _$_findCachedViewById(R$id.videoView)).setLooping(false);
        }
        DomainServer domainServer = DomainServer.getInstance();
        Intrinsics.checkExpressionValueIsNotNull(domainServer, "DomainServer.getInstance()");
        StartUpVideoController startUpVideoController = new StartUpVideoController(this);
        ImageLoaderUtil.loadViewPagerOriginImage(this, startUpVideoController.getThumb(), null, new ImageBean(this.page.getVideoCoverUrlSec()), 0);
        ((StartUpVideoView) _$_findCachedViewById(R$id.videoView)).setUrl(domainServer.getTtViewVideoView2() + this.page.getVideoUrlSec());
        ((StartUpVideoView) _$_findCachedViewById(R$id.videoView)).setVideoController(startUpVideoController);
        ((StartUpVideoView) _$_findCachedViewById(R$id.videoView)).setScreenScale(5);
        ((StartUpVideoView) _$_findCachedViewById(R$id.videoView)).start();
        startUpVideoController.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.start.view.AdActivity$showAdVideo$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AdActivity.this.clickAd();
            }
        });
    }

    public final void viewAd() {
        DataUploadUtil.uploadAD(this.page.getAdId(), 1);
    }

    public final void clickAd() {
        this.adUtil.clickAd(this.page.getAdId(), this.page.getAdType(), this.page.getAdJumpModule(), this.page.getAdJumpDetail(), this.page.getOpenType(), this.page.getAdLink());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        if (((StartUpVideoView) _$_findCachedViewById(R$id.videoView)) != null) {
            ((StartUpVideoView) _$_findCachedViewById(R$id.videoView)).resume();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        if (((StartUpVideoView) _$_findCachedViewById(R$id.videoView)) != null) {
            ((StartUpVideoView) _$_findCachedViewById(R$id.videoView)).pause();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        TimerTaskUtil.getInstance().onStop();
        if (((StartUpVideoView) _$_findCachedViewById(R$id.videoView)) != null) {
            ((StartUpVideoView) _$_findCachedViewById(R$id.videoView)).release();
        }
    }

    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onBackPressed() {
        super.onBackPressed();
        AppManager.getAppManager().exitApp();
    }
}
