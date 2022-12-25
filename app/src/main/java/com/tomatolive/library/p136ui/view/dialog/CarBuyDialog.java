package com.tomatolive.library.p136ui.view.dialog;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.opensource.svgaplayer.SVGAImageView;
import com.opensource.svgaplayer.SVGAParser;
import com.opensource.svgaplayer.SVGAVideoEntity;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.TomatoLiveSDK;
import com.tomatolive.library.download.CarDownLoadManager;
import com.tomatolive.library.http.ApiRetrofit;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.function.HttpResultFunction;
import com.tomatolive.library.http.function.ServerResultFunction;
import com.tomatolive.library.model.CarDownloadEntity;
import com.tomatolive.library.model.CarEntity;
import com.tomatolive.library.model.MyAccountEntity;
import com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment;
import com.tomatolive.library.p136ui.view.dialog.base.BaseRxDialogFragment;
import com.tomatolive.library.p136ui.view.dialog.confirm.SureCancelDialog;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.LogEventUtils;
import com.tomatolive.library.utils.NumberUtils;
import com.tomatolive.library.utils.live.SimpleRxObserver;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/* renamed from: com.tomatolive.library.ui.view.dialog.CarBuyDialog */
/* loaded from: classes3.dex */
public class CarBuyDialog extends BaseDialogFragment {
    private static final String ITEM = "ITEM";
    private CarEntity carItemEntity;
    private SVGAImageView ivAnim;
    private LinearLayout llPrice30Bg;
    private LinearLayout llPrice7Bg;
    private OnBuyListener onBuyListener;
    private SVGAParser svgaParser;
    private TextView tvCurrentGold;
    private TextView tvDay30;
    private TextView tvDay7;
    private TextView tvPrice30;
    private TextView tvPrice7;
    private String userOver = "0";

    /* renamed from: com.tomatolive.library.ui.view.dialog.CarBuyDialog$OnBuyListener */
    /* loaded from: classes3.dex */
    public interface OnBuyListener {
        void onBuy(String str, String str2);
    }

    public static CarBuyDialog newInstance(CarEntity carEntity, OnBuyListener onBuyListener) {
        Bundle bundle = new Bundle();
        CarBuyDialog carBuyDialog = new CarBuyDialog();
        carBuyDialog.setArguments(bundle);
        bundle.putSerializable(ITEM, carEntity);
        carBuyDialog.setBuyListener(onBuyListener);
        return carBuyDialog;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public int getLayoutRes() {
        return R$layout.fq_dialog_car_buy;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public void initView(View view) {
        if (getArguments() != null) {
            this.carItemEntity = (CarEntity) getArguments().getSerializable(ITEM);
        }
        TextView textView = (TextView) view.findViewById(R$id.tv_name);
        this.tvPrice7 = (TextView) view.findViewById(R$id.tv_price_7);
        this.tvDay7 = (TextView) view.findViewById(R$id.tv_time_7);
        this.tvPrice30 = (TextView) view.findViewById(R$id.tv_price_30);
        this.tvDay30 = (TextView) view.findViewById(R$id.tv_time_30);
        this.tvCurrentGold = (TextView) view.findViewById(R$id.tv_buy_gold);
        this.llPrice7Bg = (LinearLayout) view.findViewById(R$id.ll_price_7_bg);
        this.llPrice30Bg = (LinearLayout) view.findViewById(R$id.ll_price_30_bg);
        this.ivAnim = (SVGAImageView) view.findViewById(R$id.iv_anim);
        getUserOver();
        CarEntity carEntity = this.carItemEntity;
        if (carEntity != null) {
            textView.setText(carEntity.name);
            this.tvPrice7.setText(Html.fromHtml(this.mContext.getString(R$string.fq_car_buy_gold, getString(R$string.fq_tomato_money_str) + formatGold(this.carItemEntity.getWeekPrice()))));
            this.tvPrice30.setText(Html.fromHtml(this.mContext.getString(R$string.fq_car_buy_gold, getString(R$string.fq_tomato_money_str) + formatGold(this.carItemEntity.getMonthPrice()))));
            this.tvDay7.setText(this.mContext.getString(R$string.fq_count_day, "7"));
            this.tvDay30.setText(this.mContext.getString(R$string.fq_count_day, ConstantUtils.CAR_TIMES_30));
            initPriceSelectedView(true);
            startAnim();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public void initListener(View view) {
        super.initListener(view);
        this.llPrice7Bg.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.CarBuyDialog.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                CarBuyDialog.this.initPriceSelectedView(true);
            }
        });
        this.llPrice30Bg.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.CarBuyDialog.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                CarBuyDialog.this.initPriceSelectedView(false);
            }
        });
        this.tvCurrentGold.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.CarBuyDialog.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                CarBuyDialog.this.getUserOver();
            }
        });
        view.findViewById(R$id.tv_now_buy).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.CarBuyDialog.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                String str;
                if (CarBuyDialog.this.onBuyListener == null || CarBuyDialog.this.carItemEntity == null || !CarBuyDialog.this.carItemEntity.isPublicCar()) {
                    return;
                }
                String weekPrice = (!CarBuyDialog.this.llPrice7Bg.isSelected() || CarBuyDialog.this.llPrice30Bg.isSelected()) ? "0" : CarBuyDialog.this.carItemEntity.getWeekPrice();
                if (CarBuyDialog.this.llPrice7Bg.isSelected() || !CarBuyDialog.this.llPrice30Bg.isSelected()) {
                    str = "7";
                } else {
                    weekPrice = CarBuyDialog.this.carItemEntity.getMonthPrice();
                    str = ConstantUtils.CAR_TIMES_30;
                }
                if (NumberUtils.string2long(CarBuyDialog.this.userOver) == 0 || NumberUtils.string2long(CarBuyDialog.this.userOver) < NumberUtils.string2long(weekPrice)) {
                    CarBuyDialog.this.goToRecharge();
                    return;
                }
                CarBuyDialog.this.dismiss();
                CarBuyDialog.this.onBuyListener.onBuy(str, weekPrice);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initPriceSelectedView(boolean z) {
        this.llPrice7Bg.setSelected(z);
        this.tvPrice7.setSelected(z);
        this.tvDay7.setSelected(z);
        this.llPrice30Bg.setSelected(!z);
        this.tvPrice30.setSelected(!z);
        this.tvDay30.setSelected(!z);
    }

    private void startAnim() {
        try {
            Observable.just(true).map(new Function() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$CarBuyDialog$DD9PPUXmoH0IaA3aK8L4ySqUKhw
                @Override // io.reactivex.functions.Function
                /* renamed from: apply */
                public final Object mo6755apply(Object obj) {
                    return CarBuyDialog.this.lambda$startAnim$0$CarBuyDialog((Boolean) obj);
                }
            }).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<InputStream>() { // from class: com.tomatolive.library.ui.view.dialog.CarBuyDialog.5
                @Override // io.reactivex.Observer
                public void onComplete() {
                }

                @Override // io.reactivex.Observer
                public void onSubscribe(Disposable disposable) {
                }

                @Override // io.reactivex.Observer
                public void onNext(InputStream inputStream) {
                    if (inputStream == null) {
                        CarBuyDialog.this.loadNetAnimation();
                        return;
                    }
                    if (CarBuyDialog.this.svgaParser == null) {
                        CarBuyDialog carBuyDialog = CarBuyDialog.this;
                        carBuyDialog.svgaParser = new SVGAParser(((BaseRxDialogFragment) carBuyDialog).mContext);
                    }
                    CarBuyDialog.this.svgaParser.decodeFromInputStream(inputStream, CarBuyDialog.this.carItemEntity.f5836id, new SVGAParser.ParseCompletion() { // from class: com.tomatolive.library.ui.view.dialog.CarBuyDialog.5.1
                        @Override // com.opensource.svgaplayer.SVGAParser.ParseCompletion
                        public void onError() {
                        }

                        @Override // com.opensource.svgaplayer.SVGAParser.ParseCompletion
                        public void onComplete(SVGAVideoEntity sVGAVideoEntity) {
                            CarBuyDialog.this.ivAnim.setVisibility(0);
                            CarBuyDialog.this.ivAnim.setVideoItem(sVGAVideoEntity);
                            CarBuyDialog.this.ivAnim.startAnimation();
                        }
                    }, true);
                }

                @Override // io.reactivex.Observer
                public void onError(Throwable th) {
                    CarBuyDialog.this.loadNetAnimation();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public /* synthetic */ InputStream lambda$startAnim$0$CarBuyDialog(Boolean bool) throws Exception {
        CarEntity carEntity = this.carItemEntity;
        if (carEntity == null) {
            return null;
        }
        return AppUtils.getCarSVGAFileInputStream(carEntity.f5836id, carEntity.versionCode);
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
                this.svgaParser.decodeFromURL(new URL(formatCarDownloadEntity.getAnimalUrl()), new SVGAParser.ParseCompletion() { // from class: com.tomatolive.library.ui.view.dialog.CarBuyDialog.6
                    @Override // com.opensource.svgaplayer.SVGAParser.ParseCompletion
                    public void onError() {
                    }

                    @Override // com.opensource.svgaplayer.SVGAParser.ParseCompletion
                    public void onComplete(SVGAVideoEntity sVGAVideoEntity) {
                        CarBuyDialog.this.ivAnim.setVideoItem(sVGAVideoEntity);
                        CarBuyDialog.this.ivAnim.startAnimation();
                    }
                });
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }

    public OnBuyListener getOnBuyListener() {
        return this.onBuyListener;
    }

    public void setBuyListener(OnBuyListener onBuyListener) {
        this.onBuyListener = onBuyListener;
    }

    private String formatGold(String str) {
        return AppUtils.formatDisplayPrice(str, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getUserOver() {
        this.tvCurrentGold.setText(getString(R$string.fq_userover_loading));
        ApiRetrofit.getInstance().getApiService().getQueryBalanceService(new RequestParams().getDefaultParams()).map(new ServerResultFunction<MyAccountEntity>() { // from class: com.tomatolive.library.ui.view.dialog.CarBuyDialog.8
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new SimpleRxObserver<MyAccountEntity>() { // from class: com.tomatolive.library.ui.view.dialog.CarBuyDialog.7
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(MyAccountEntity myAccountEntity) {
                String str = "0";
                String accountBalance = myAccountEntity == null ? str : myAccountEntity.getAccountBalance();
                CarBuyDialog carBuyDialog = CarBuyDialog.this;
                if (myAccountEntity != null) {
                    str = myAccountEntity.getAccountBalance();
                }
                carBuyDialog.userOver = str;
                CarBuyDialog.this.tvCurrentGold.setText(AppUtils.formatDisplayPrice(accountBalance, true));
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onError(Throwable th) {
                super.onError(th);
                CarBuyDialog.this.tvCurrentGold.setText(R$string.fq_userover_loading_fail);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void goToRecharge() {
        if (TomatoLiveSDK.getSingleton().sdkCallbackListener != null) {
            SureCancelDialog.newInstance(getString(R$string.fq_over_insufficient), new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$CarBuyDialog$r3Mw8yhkeMcJ32L_3F147bC6aZk
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    CarBuyDialog.this.lambda$goToRecharge$1$CarBuyDialog(view);
                }
            }).show(getFragmentManager());
        }
    }

    public /* synthetic */ void lambda$goToRecharge$1$CarBuyDialog(View view) {
        dismiss();
        AppUtils.onRechargeListener(this.mContext);
        LogEventUtils.uploadRechargeClick(getString(R$string.fq_car_buy_recharge_entrance));
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

    @Override // com.trello.rxlifecycle2.components.support.RxDialogFragment, android.support.p002v4.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        SVGAImageView sVGAImageView = this.ivAnim;
        if (sVGAImageView != null) {
            sVGAImageView.stopAnimation(true);
        }
        if (this.svgaParser != null) {
            this.svgaParser = null;
        }
    }
}
