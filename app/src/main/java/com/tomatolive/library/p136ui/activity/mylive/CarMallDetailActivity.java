package com.tomatolive.library.p136ui.activity.mylive;

import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.opensource.svgaplayer.SVGAImageView;
import com.opensource.svgaplayer.SVGAParser;
import com.opensource.svgaplayer.SVGAVideoEntity;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.TomatoLiveSDK;
import com.tomatolive.library.base.BaseActivity;
import com.tomatolive.library.download.CarDownLoadManager;
import com.tomatolive.library.model.CarDownloadEntity;
import com.tomatolive.library.model.CarEntity;
import com.tomatolive.library.model.event.BaseEvent;
import com.tomatolive.library.model.event.UpdateBalanceEvent;
import com.tomatolive.library.p136ui.presenter.CarMallDetailPresenter;
import com.tomatolive.library.p136ui.view.dialog.confirm.SureCancelDialog;
import com.tomatolive.library.p136ui.view.iview.ICarMallDetailView;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.LogEventUtils;
import com.tomatolive.library.utils.NumberUtils;
import com.tomatolive.library.utils.UserInfoManager;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/* renamed from: com.tomatolive.library.ui.activity.mylive.CarMallDetailActivity */
/* loaded from: classes3.dex */
public class CarMallDetailActivity extends BaseActivity<CarMallDetailPresenter> implements ICarMallDetailView {
    private CarEntity carItemEntity;
    private SVGAImageView ivAnim;
    private LinearLayout llDayTabBg;
    private RelativeLayout llWeekStarBg;
    private SVGAParser svgaParser;
    private TextView tvCurrentGold;
    private TextView tvDay30;
    private TextView tvDay7;
    private TextView tvNameGold;
    private TextView tvNowBuy;
    private TextView tvWeekStar7Day;
    private TextView tvWeekStarGold;
    private String userOver = "0";
    private String currentCarGold = "0";

    @Override // com.tomatolive.library.p136ui.view.iview.ICarMallDetailView
    public void onBuyCarFail(int i) {
    }

    @Override // com.tomatolive.library.base.BaseView
    public void onResultError(int i) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.base.BaseActivity
    /* renamed from: createPresenter  reason: collision with other method in class */
    public CarMallDetailPresenter mo6636createPresenter() {
        return new CarMallDetailPresenter(this.mContext, this);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    protected int getLayoutId() {
        return R$layout.fq_activity_car_mall_detail;
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initView(Bundle bundle) {
        setActivityTitle(R$string.fq_my_live_car_mall_detail);
        this.carItemEntity = (CarEntity) getIntent().getSerializableExtra(ConstantUtils.RESULT_ITEM);
        this.ivAnim = (SVGAImageView) findViewById(R$id.iv_anim);
        this.tvNameGold = (TextView) findViewById(R$id.tv_name_gold);
        this.tvDay7 = (TextView) findViewById(R$id.tv_day_7);
        this.tvDay30 = (TextView) findViewById(R$id.tv_day_30);
        TextView textView = (TextView) findViewById(R$id.tv_desc);
        this.tvCurrentGold = (TextView) findViewById(R$id.tv_buy_gold);
        this.tvNowBuy = (TextView) findViewById(R$id.tv_now_buy);
        this.llWeekStarBg = (RelativeLayout) findViewById(R$id.ll_week_star_bg);
        this.tvWeekStarGold = (TextView) findViewById(R$id.tv_week_star_gold);
        this.tvWeekStar7Day = (TextView) findViewById(R$id.tv_week_star_7_day);
        this.llDayTabBg = (LinearLayout) findViewById(R$id.ll_day_tab_bg);
        textView.setMovementMethod(ScrollingMovementMethod.getInstance());
        sendUserOverRequest();
        CarEntity carEntity = this.carItemEntity;
        if (carEntity != null) {
            textView.setText(carEntity.description);
            startAnim();
            if (this.carItemEntity.isWeekStarCar()) {
                this.tvNameGold.setText(this.carItemEntity.name);
                this.tvNowBuy.setVisibility(4);
                this.llDayTabBg.setVisibility(4);
                this.tvWeekStar7Day.setVisibility(0);
                this.llWeekStarBg.setVisibility(0);
                this.tvWeekStarGold.setText(Html.fromHtml(getString(R$string.fq_week_star_car_gold_tips, new Object[]{formatGold(this.carItemEntity.getWeekPrice())})));
                return;
            }
            initPriceSelectedView(true);
            this.tvDay7.setText(getString(R$string.fq_count_day, new Object[]{"7"}));
            this.tvDay30.setText(getString(R$string.fq_count_day, new Object[]{ConstantUtils.CAR_TIMES_30}));
        }
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initListener() {
        super.initListener();
        this.tvDay7.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$CarMallDetailActivity$gle1QekB-UbRe4ak7iSHaqTALJc
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CarMallDetailActivity.this.lambda$initListener$0$CarMallDetailActivity(view);
            }
        });
        this.tvDay30.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$CarMallDetailActivity$P_lzAJHJW7XBXYH460nkDoIe9ds
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CarMallDetailActivity.this.lambda$initListener$1$CarMallDetailActivity(view);
            }
        });
        this.tvCurrentGold.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$CarMallDetailActivity$xgPGLINP6Y9LDlz2QNbflxe0z7U
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CarMallDetailActivity.this.lambda$initListener$2$CarMallDetailActivity(view);
            }
        });
        this.tvNowBuy.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$CarMallDetailActivity$iI3r_wCNAOM0z2fZhpB0P3es7YU
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CarMallDetailActivity.this.lambda$initListener$3$CarMallDetailActivity(view);
            }
        });
    }

    public /* synthetic */ void lambda$initListener$0$CarMallDetailActivity(View view) {
        initPriceSelectedView(true);
    }

    public /* synthetic */ void lambda$initListener$1$CarMallDetailActivity(View view) {
        initPriceSelectedView(false);
    }

    public /* synthetic */ void lambda$initListener$2$CarMallDetailActivity(View view) {
        sendUserOverRequest();
    }

    public /* synthetic */ void lambda$initListener$3$CarMallDetailActivity(View view) {
        CarEntity carEntity = this.carItemEntity;
        if (carEntity == null) {
            return;
        }
        if (!carEntity.isPublicCar()) {
            showToast(R$string.fq_private_car_buy_tips);
        } else if (NumberUtils.string2long(this.userOver) == 0 || NumberUtils.string2long(this.userOver) < NumberUtils.string2long(this.currentCarGold)) {
            goToRecharge();
        } else {
            ((CarMallDetailPresenter) this.mPresenter).buyCar(this.carItemEntity.f5836id, getCarType(), this.currentCarGold);
        }
    }

    private void sendUserOverRequest() {
        this.tvCurrentGold.setText(getString(R$string.fq_userover_loading));
        ((CarMallDetailPresenter) this.mPresenter).getUserOver();
    }

    private void initPriceSelectedView(boolean z) {
        if (this.carItemEntity == null) {
            return;
        }
        this.tvDay7.setSelected(z);
        this.tvDay30.setSelected(!z);
        CarEntity carEntity = this.carItemEntity;
        initNameGoldStr(z ? carEntity.getWeekPrice() : carEntity.getMonthPrice());
        this.currentCarGold = z ? this.carItemEntity.getWeekPrice() : this.carItemEntity.getMonthPrice();
    }

    private void initNameGoldStr(String str) {
        this.tvNameGold.setText(Html.fromHtml(getString(R$string.fq_car_buy_gold_tips, new Object[]{this.carItemEntity.name, getString(R$string.fq_tomato_money_str) + formatGold(str)})));
    }

    private String formatGold(String str) {
        return AppUtils.formatDisplayPrice(str, false);
    }

    @Override // com.tomatolive.library.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        SVGAImageView sVGAImageView = this.ivAnim;
        if (sVGAImageView != null) {
            sVGAImageView.stopAnimation(true);
        }
    }

    @Override // com.tomatolive.library.p136ui.view.iview.ICarMallDetailView
    public void onUserOverSuccess(String str) {
        this.userOver = str;
        this.tvCurrentGold.setText(AppUtils.formatDisplayPrice(str, true));
    }

    @Override // com.tomatolive.library.p136ui.view.iview.ICarMallDetailView
    public void onUserOverFail() {
        this.tvCurrentGold.setText(R$string.fq_userover_loading_fail);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.ICarMallDetailView
    public void onBuyCarSuccess(String str, String str2) {
        showToast(R$string.fq_purchase_succeeded);
        String string = getString(this.carItemEntity.isPrivatePermission() ? R$string.fq_private_car : R$string.fq_public_car);
        CarEntity carEntity = this.carItemEntity;
        LogEventUtils.uploadBuyCar(carEntity.f5836id, string, str, carEntity.name, str2, UserInfoManager.getInstance().getExpGrade());
        finish();
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void onEventMainThread(BaseEvent baseEvent) {
        super.onEventMainThread(baseEvent);
        if (baseEvent instanceof UpdateBalanceEvent) {
            sendUserOverRequest();
        }
    }

    private String getCarType() {
        return this.tvDay7.isSelected() ? "7" : ConstantUtils.CAR_TIMES_30;
    }

    private void goToRecharge() {
        if (TomatoLiveSDK.getSingleton().sdkCallbackListener != null) {
            SureCancelDialog.newInstance(getString(R$string.fq_over_insufficient), new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$CarMallDetailActivity$VPs8Z3HO9vxQ6C49_JAlPSm7kqg
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    CarMallDetailActivity.this.lambda$goToRecharge$4$CarMallDetailActivity(view);
                }
            }).show(getSupportFragmentManager());
        }
    }

    public /* synthetic */ void lambda$goToRecharge$4$CarMallDetailActivity(View view) {
        AppUtils.onRechargeListener(this.mContext);
        LogEventUtils.uploadRechargeClick(getString(R$string.fq_car_mall_detail_recharge_entrance));
    }

    private void startAnim() {
        try {
            Observable.just(true).map(new Function() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$CarMallDetailActivity$BLdNlEeeQESVEvk98AqyY3NQWUw
                @Override // io.reactivex.functions.Function
                /* renamed from: apply */
                public final Object mo6755apply(Object obj) {
                    return CarMallDetailActivity.this.lambda$startAnim$5$CarMallDetailActivity((Boolean) obj);
                }
            }).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<InputStream>() { // from class: com.tomatolive.library.ui.activity.mylive.CarMallDetailActivity.1
                @Override // io.reactivex.Observer
                public void onComplete() {
                }

                @Override // io.reactivex.Observer
                public void onSubscribe(Disposable disposable) {
                }

                @Override // io.reactivex.Observer
                public void onNext(InputStream inputStream) {
                    if (CarMallDetailActivity.this.svgaParser == null) {
                        CarMallDetailActivity carMallDetailActivity = CarMallDetailActivity.this;
                        carMallDetailActivity.svgaParser = new SVGAParser(((BaseActivity) carMallDetailActivity).mContext);
                    }
                    if (inputStream == null) {
                        CarMallDetailActivity.this.loadNetAnimation();
                    } else {
                        CarMallDetailActivity.this.svgaParser.decodeFromInputStream(inputStream, CarMallDetailActivity.this.carItemEntity.f5836id, new SVGAParser.ParseCompletion() { // from class: com.tomatolive.library.ui.activity.mylive.CarMallDetailActivity.1.1
                            @Override // com.opensource.svgaplayer.SVGAParser.ParseCompletion
                            public void onError() {
                            }

                            @Override // com.opensource.svgaplayer.SVGAParser.ParseCompletion
                            public void onComplete(SVGAVideoEntity sVGAVideoEntity) {
                                CarMallDetailActivity.this.ivAnim.setVisibility(0);
                                CarMallDetailActivity.this.ivAnim.setVideoItem(sVGAVideoEntity);
                                CarMallDetailActivity.this.ivAnim.startAnimation();
                            }
                        }, true);
                    }
                }

                @Override // io.reactivex.Observer
                public void onError(Throwable th) {
                    CarMallDetailActivity.this.loadNetAnimation();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public /* synthetic */ InputStream lambda$startAnim$5$CarMallDetailActivity(Boolean bool) throws Exception {
        CarEntity carEntity = this.carItemEntity;
        if (carEntity == null) {
            return null;
        }
        return AppUtils.getCarSVGAFileInputStream(carEntity.f5836id, carEntity.versionCode);
    }

    private CarDownloadEntity formatCarDownloadEntity() {
        if (this.carItemEntity == null) {
            return null;
        }
        CarDownloadEntity carDownloadEntity = new CarDownloadEntity();
        CarEntity carEntity = this.carItemEntity;
        carDownloadEntity.f5835id = carEntity.f5836id;
        carDownloadEntity.imgUrl = carEntity.imgUrl;
        carDownloadEntity.name = carEntity.name;
        carDownloadEntity.animalUrl = carEntity.animalUrl;
        return carDownloadEntity;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadNetAnimation() {
        CarDownloadEntity formatCarDownloadEntity = formatCarDownloadEntity();
        if (formatCarDownloadEntity != null) {
            CarDownLoadManager.getInstance().updateAnimOnlineSingleRes(formatCarDownloadEntity);
            if (this.svgaParser == null) {
                this.svgaParser = new SVGAParser(this.mContext);
            }
            try {
                this.svgaParser.decodeFromURL(new URL(formatCarDownloadEntity.getAnimalUrl()), new SVGAParser.ParseCompletion() { // from class: com.tomatolive.library.ui.activity.mylive.CarMallDetailActivity.2
                    @Override // com.opensource.svgaplayer.SVGAParser.ParseCompletion
                    public void onError() {
                    }

                    @Override // com.opensource.svgaplayer.SVGAParser.ParseCompletion
                    public void onComplete(SVGAVideoEntity sVGAVideoEntity) {
                        CarMallDetailActivity.this.ivAnim.setVideoItem(sVGAVideoEntity);
                        CarMallDetailActivity.this.ivAnim.startAnimation();
                    }
                });
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }
}
