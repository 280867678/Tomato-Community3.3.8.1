package com.tomatolive.library.p136ui.view.dialog;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.http.ApiRetrofit;
import com.tomatolive.library.http.HttpRxObserver;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.ResultCallBack;
import com.tomatolive.library.http.function.HttpResultFunction;
import com.tomatolive.library.http.function.ServerResultFunction;
import com.tomatolive.library.model.GuardItemEntity;
import com.tomatolive.library.model.MyAccountEntity;
import com.tomatolive.library.p136ui.activity.home.WebViewActivity;
import com.tomatolive.library.p136ui.view.custom.GuardChangeTitleView;
import com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment;
import com.tomatolive.library.p136ui.view.dialog.base.BaseRxDialogFragment;
import com.tomatolive.library.p136ui.view.widget.StateView;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.GlideUtils;
import com.tomatolive.library.utils.LogEventUtils;
import com.tomatolive.library.utils.NumberUtils;
import com.tomatolive.library.utils.live.SimpleRxObserver;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import razerdp.basepopup.QuickPopupBuilder;
import razerdp.basepopup.QuickPopupConfig;

/* renamed from: com.tomatolive.library.ui.view.dialog.GuardOpenContentDialog */
/* loaded from: classes3.dex */
public class GuardOpenContentDialog extends BaseBottomDialogFragment {
    private static final String ANCHORID_KEY = "anchorId_key";
    private static final String SER_ITEM = "serItem";
    private QuickPopupBuilder chatBgPop;
    private String curType;
    private GuardItemEntity currentSelectGuardItem;
    private FrameLayout flAvatarBg;
    private QuickPopupBuilder giftPop;
    private GuardItemEntity guardItem;
    private QuickPopupBuilder identityPop;
    private boolean isOnOpenWeekGuard;
    private ImageView ivCover;
    private ImageView ivGuardType;
    private QuickPopupBuilder joinPop;
    private String liveCount;
    private StateView mStateView;
    private OnOpenGuardCallbackListener openGuardCallbackListener;
    private QuickPopupBuilder privilegePop;
    private TextView tvChatBg;
    private TextView tvGift;
    private TextView tvIdentity;
    private TextView tvJoin;
    private TextView tvMoney;
    private GuardChangeTitleView tvMonth;
    private TextView tvOpen;
    private TextView tvPrivilege;
    private GuardChangeTitleView tvWeek;
    private GuardChangeTitleView tvYear;
    private String anchorId = "";
    private String userOver = "0";

    /* renamed from: com.tomatolive.library.ui.view.dialog.GuardOpenContentDialog$OnOpenGuardCallbackListener */
    /* loaded from: classes3.dex */
    public interface OnOpenGuardCallbackListener {
        void OnOpenGuardFail();

        void OnOpenGuardSuccess(GuardItemEntity guardItemEntity);
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    public double getHeightScale() {
        return -2.0d;
    }

    public static GuardOpenContentDialog newInstance(GuardItemEntity guardItemEntity, OnOpenGuardCallbackListener onOpenGuardCallbackListener) {
        Bundle bundle = new Bundle();
        GuardOpenContentDialog guardOpenContentDialog = new GuardOpenContentDialog();
        bundle.putParcelable(SER_ITEM, guardItemEntity);
        guardOpenContentDialog.setArguments(bundle);
        guardOpenContentDialog.setOpenGuardCallbackListener(onOpenGuardCallbackListener);
        return guardOpenContentDialog;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    public int getLayoutRes() {
        return R$layout.fq_dialog_open_guard;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    public void initView(View view) {
        this.guardItem = (GuardItemEntity) getArguments().getParcelable(SER_ITEM);
        GuardItemEntity guardItemEntity = this.guardItem;
        if (guardItemEntity != null) {
            this.anchorId = guardItemEntity.anchorId;
            this.liveCount = guardItemEntity.liveCount;
        }
        this.ivCover = (ImageView) view.findViewById(R$id.iv_cover);
        this.tvWeek = (GuardChangeTitleView) view.findViewById(R$id.tv_week);
        this.tvMonth = (GuardChangeTitleView) view.findViewById(R$id.tv_month);
        this.tvYear = (GuardChangeTitleView) view.findViewById(R$id.tv_year);
        this.tvMoney = (TextView) view.findViewById(R$id.tv_money);
        this.tvIdentity = (TextView) view.findViewById(R$id.tv_identity);
        this.tvJoin = (TextView) view.findViewById(R$id.tv_join);
        this.tvPrivilege = (TextView) view.findViewById(R$id.tv_privilege);
        this.tvGift = (TextView) view.findViewById(R$id.tv_gift);
        this.tvChatBg = (TextView) view.findViewById(R$id.tv_guard_chat_bg);
        this.tvOpen = (TextView) view.findViewById(R$id.tv_open);
        this.ivGuardType = (ImageView) view.findViewById(R$id.iv_guard_type);
        this.flAvatarBg = (FrameLayout) view.findViewById(R$id.fl_avatar_bg);
        this.mStateView = StateView.inject((ViewGroup) view.findViewById(R$id.fl_content_view));
        setImgCover(R$drawable.fq_ic_guard_top_bg_1);
        initPop();
        getUserOver();
        lambda$initListener$10$GuardOpenContentDialog();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    public void initListener(View view) {
        super.initListener(view);
        this.tvWeek.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$GuardOpenContentDialog$0J31zt2FGmNZ8eDcIryi9krXIm4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                GuardOpenContentDialog.this.lambda$initListener$0$GuardOpenContentDialog(view2);
            }
        });
        this.tvMonth.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$GuardOpenContentDialog$fnuhCYAnsSDtHzDG9NrAZflWo-k
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                GuardOpenContentDialog.this.lambda$initListener$1$GuardOpenContentDialog(view2);
            }
        });
        this.tvYear.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$GuardOpenContentDialog$nMpK3Rsbo5MYcstM5WAZ37O319o
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                GuardOpenContentDialog.this.lambda$initListener$2$GuardOpenContentDialog(view2);
            }
        });
        this.tvIdentity.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$GuardOpenContentDialog$Rzr4BkNcVMRuXDrtgKmnpySqnd8
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view2) {
                return GuardOpenContentDialog.this.lambda$initListener$3$GuardOpenContentDialog(view2);
            }
        });
        this.tvJoin.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$GuardOpenContentDialog$tVdjPOzilEuIflJ4Dxloh2-_Poc
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view2) {
                return GuardOpenContentDialog.this.lambda$initListener$4$GuardOpenContentDialog(view2);
            }
        });
        this.tvPrivilege.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$GuardOpenContentDialog$CLiSPFPbyxvpqO72RCOrQfI9_Bk
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view2) {
                return GuardOpenContentDialog.this.lambda$initListener$5$GuardOpenContentDialog(view2);
            }
        });
        this.tvChatBg.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$GuardOpenContentDialog$W4ez6J7DfOXEdgc0nFJ6ewGk6kI
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view2) {
                return GuardOpenContentDialog.this.lambda$initListener$6$GuardOpenContentDialog(view2);
            }
        });
        this.tvGift.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$GuardOpenContentDialog$BAJ7lLOokIU7RZe_GYL4XVCO_9Y
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view2) {
                return GuardOpenContentDialog.this.lambda$initListener$7$GuardOpenContentDialog(view2);
            }
        });
        this.tvOpen.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$GuardOpenContentDialog$ZYnkv2LyghtGVeij7MVKfmLLIMs
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                GuardOpenContentDialog.this.lambda$initListener$8$GuardOpenContentDialog(view2);
            }
        });
        view.findViewById(R$id.tv_guard_rule).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.GuardOpenContentDialog.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                Intent intent = new Intent(((BaseRxDialogFragment) GuardOpenContentDialog.this).mContext, WebViewActivity.class);
                intent.putExtra(ConstantUtils.WEB_VIEW_FROM_SERVICE, true);
                intent.putExtra("url", ConstantUtils.APP_PARAM_GUARD_RULE);
                intent.putExtra("title", GuardOpenContentDialog.this.getString(R$string.fq_guard_rule));
                ((BaseRxDialogFragment) GuardOpenContentDialog.this).mContext.startActivity(intent);
            }
        });
        this.tvMoney.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$GuardOpenContentDialog$xDoZ2QcJx7mpmj3nPy5ErJe7FjI
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                GuardOpenContentDialog.this.lambda$initListener$9$GuardOpenContentDialog(view2);
            }
        });
        this.mStateView.setOnRetryClickListener(new StateView.OnRetryClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$GuardOpenContentDialog$U9nvkIIDONqTQGlchE7WT0QhzlA
            @Override // com.tomatolive.library.p136ui.view.widget.StateView.OnRetryClickListener
            public final void onRetryClick() {
                GuardOpenContentDialog.this.lambda$initListener$10$GuardOpenContentDialog();
            }
        });
    }

    public /* synthetic */ void lambda$initListener$0$GuardOpenContentDialog(View view) {
        initGuardViewInfo("1");
    }

    public /* synthetic */ void lambda$initListener$1$GuardOpenContentDialog(View view) {
        initGuardViewInfo("2");
    }

    public /* synthetic */ void lambda$initListener$2$GuardOpenContentDialog(View view) {
        initGuardViewInfo("3");
    }

    public /* synthetic */ boolean lambda$initListener$3$GuardOpenContentDialog(View view) {
        QuickPopupBuilder quickPopupBuilder = this.identityPop;
        if (quickPopupBuilder != null) {
            quickPopupBuilder.show(this.tvIdentity);
            return false;
        }
        return false;
    }

    public /* synthetic */ boolean lambda$initListener$4$GuardOpenContentDialog(View view) {
        QuickPopupBuilder quickPopupBuilder = this.joinPop;
        if (quickPopupBuilder != null) {
            quickPopupBuilder.show(this.tvJoin);
            return false;
        }
        return false;
    }

    public /* synthetic */ boolean lambda$initListener$5$GuardOpenContentDialog(View view) {
        QuickPopupBuilder quickPopupBuilder = this.privilegePop;
        if (quickPopupBuilder != null) {
            quickPopupBuilder.show(this.tvPrivilege);
            return false;
        }
        return false;
    }

    public /* synthetic */ boolean lambda$initListener$6$GuardOpenContentDialog(View view) {
        QuickPopupBuilder quickPopupBuilder = this.chatBgPop;
        if (quickPopupBuilder != null) {
            quickPopupBuilder.contentView(TextUtils.equals("3", this.curType) ? R$layout.fq_dialog_pop_guard_year_chat_bg : R$layout.fq_dialog_pop_guard_week_chat_bg);
            this.chatBgPop.show(this.tvPrivilege);
            return false;
        }
        return false;
    }

    public /* synthetic */ boolean lambda$initListener$7$GuardOpenContentDialog(View view) {
        QuickPopupBuilder quickPopupBuilder = this.giftPop;
        if (quickPopupBuilder != null) {
            quickPopupBuilder.show(this.tvGift);
            return false;
        }
        return false;
    }

    public /* synthetic */ void lambda$initListener$8$GuardOpenContentDialog(View view) {
        GuardItemEntity guardItemEntity;
        if (this.tvWeek.isSelected()) {
            this.currentSelectGuardItem = this.tvWeek.getGuardItemEntity();
        } else if (this.tvMonth.isSelected()) {
            this.currentSelectGuardItem = this.tvMonth.getGuardItemEntity();
        } else if (this.tvYear.isSelected()) {
            this.currentSelectGuardItem = this.tvYear.getGuardItemEntity();
        }
        if (this.guardItem == null || (guardItemEntity = this.currentSelectGuardItem) == null) {
            showToast(getString(R$string.fq_guard_select_type));
        } else if (NumberUtils.string2Double(guardItemEntity.price) > NumberUtils.string2Double(this.userOver)) {
            RechargeDialog.newInstance(this.mContext.getString(R$string.fq_guard_money_not_enough), new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.GuardOpenContentDialog.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    AppUtils.onRechargeListener(((BaseRxDialogFragment) GuardOpenContentDialog.this).mContext);
                    LogEventUtils.uploadRechargeClick(GuardOpenContentDialog.this.getString(R$string.fq_open_guard_recharge_entrance));
                }
            }).show(getFragmentManager());
        } else {
            GuardItemEntity guardItemEntity2 = this.guardItem;
            GuardItemEntity guardItemEntity3 = this.currentSelectGuardItem;
            guardItemEntity2.price = guardItemEntity3.price;
            guardItemEntity2.name = getGuardTypeStr(guardItemEntity3.name);
            GuardItemEntity guardItemEntity4 = this.currentSelectGuardItem;
            GuardItemEntity guardItemEntity5 = this.guardItem;
            guardItemEntity4.expGrade = guardItemEntity5.expGrade;
            int string2int = NumberUtils.string2int(guardItemEntity5.userGuardType);
            int string2int2 = NumberUtils.string2int(this.currentSelectGuardItem.type);
            if (string2int == 0) {
                GuardOpenTipsDialog.newInstance(12, this.guardItem, new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.GuardOpenContentDialog.2
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view2) {
                        GuardOpenContentDialog guardOpenContentDialog = GuardOpenContentDialog.this;
                        guardOpenContentDialog.sendOpenRequest(guardOpenContentDialog.currentSelectGuardItem);
                    }
                }).show(getFragmentManager());
            } else if (string2int == string2int2) {
                GuardOpenTipsDialog.newInstance(13, this.guardItem, new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.GuardOpenContentDialog.3
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view2) {
                        GuardOpenContentDialog guardOpenContentDialog = GuardOpenContentDialog.this;
                        guardOpenContentDialog.sendOpenRequest(guardOpenContentDialog.currentSelectGuardItem);
                    }
                }).show(getFragmentManager());
            } else if (string2int < string2int2) {
                GuardOpenTipsDialog.newInstance(15, this.guardItem, new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.GuardOpenContentDialog.4
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view2) {
                        GuardOpenContentDialog guardOpenContentDialog = GuardOpenContentDialog.this;
                        guardOpenContentDialog.sendOpenRequest(guardOpenContentDialog.currentSelectGuardItem);
                    }
                }).show(getFragmentManager());
            } else if (string2int <= string2int2) {
            } else {
                GuardOpenTipsDialog.newInstance(14).show(getFragmentManager());
            }
        }
    }

    public /* synthetic */ void lambda$initListener$9$GuardOpenContentDialog(View view) {
        getUserOver();
    }

    private void setImgCover(@DrawableRes int i) {
        GlideUtils.loadRoundCornersImage(this.mContext, this.ivCover, i, 10, RoundedCornersTransformation.CornerType.TOP);
    }

    private void initPop() {
        QuickPopupBuilder with = QuickPopupBuilder.with(getContext());
        with.contentView(R$layout.fq_dialog_pop_guard_identity);
        QuickPopupConfig quickPopupConfig = new QuickPopupConfig();
        quickPopupConfig.gravity(48);
        with.config(quickPopupConfig);
        this.identityPop = with;
        QuickPopupBuilder with2 = QuickPopupBuilder.with(getContext());
        with2.contentView(R$layout.fq_dialog_pop_guard_join);
        QuickPopupConfig quickPopupConfig2 = new QuickPopupConfig();
        quickPopupConfig2.gravity(49);
        with2.config(quickPopupConfig2);
        this.joinPop = with2;
        QuickPopupBuilder with3 = QuickPopupBuilder.with(getContext());
        QuickPopupConfig quickPopupConfig3 = new QuickPopupConfig();
        quickPopupConfig3.gravity(51);
        with3.config(quickPopupConfig3);
        this.chatBgPop = with3;
        QuickPopupBuilder with4 = QuickPopupBuilder.with(getContext());
        with4.contentView(R$layout.fq_dialog_pop_guard_privilege);
        QuickPopupConfig quickPopupConfig4 = new QuickPopupConfig();
        quickPopupConfig4.gravity(49);
        with4.config(quickPopupConfig4);
        this.privilegePop = with4;
        QuickPopupBuilder with5 = QuickPopupBuilder.with(getContext());
        with5.contentView(R$layout.fq_dialog_pop_guard_gift);
        QuickPopupConfig quickPopupConfig5 = new QuickPopupConfig();
        quickPopupConfig5.gravity(48);
        with5.config(quickPopupConfig5);
        this.giftPop = with5;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: sendRequest */
    public void lambda$initListener$10$GuardOpenContentDialog() {
        Observable.zip(ApiRetrofit.getInstance().getApiService().getIsOpenWeekGuardService(new RequestParams().getDefaultParams()).map(new ServerResultFunction<GuardItemEntity>() { // from class: com.tomatolive.library.ui.view.dialog.GuardOpenContentDialog.7
        }).onErrorResumeNext(new HttpResultFunction()), ApiRetrofit.getInstance().getApiService().getGuardListService(new RequestParams().getAppIdParams()).map(new ServerResultFunction<List<GuardItemEntity>>() { // from class: com.tomatolive.library.ui.view.dialog.GuardOpenContentDialog.6
        }).onErrorResumeNext(new HttpResultFunction()), new BiFunction() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$GuardOpenContentDialog$bKT62Xb0ir5-xyt4kzK8aAwkaBk
            @Override // io.reactivex.functions.BiFunction
            /* renamed from: apply */
            public final Object mo6745apply(Object obj, Object obj2) {
                return GuardOpenContentDialog.this.lambda$sendRequest$11$GuardOpenContentDialog((GuardItemEntity) obj, (List) obj2);
            }
        }).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new HttpRxObserver(this.mContext, (ResultCallBack) new ResultCallBack<List<GuardItemEntity>>() { // from class: com.tomatolive.library.ui.view.dialog.GuardOpenContentDialog.8
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str) {
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(List<GuardItemEntity> list) {
                if (list == null) {
                    return;
                }
                GuardOpenContentDialog.this.initGuardChangeTitleView(list);
            }
        }, this.mStateView, true));
    }

    public /* synthetic */ List lambda$sendRequest$11$GuardOpenContentDialog(GuardItemEntity guardItemEntity, List list) throws Exception {
        this.isOnOpenWeekGuard = guardItemEntity.isOpenWeekGuardBoolean();
        return list;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendOpenRequest(GuardItemEntity guardItemEntity) {
        ApiRetrofit.getInstance().getApiService().getOpenGuardService(new RequestParams().getOpenGuardParams(guardItemEntity, this.liveCount)).map(new ServerResultFunction<GuardItemEntity>() { // from class: com.tomatolive.library.ui.view.dialog.GuardOpenContentDialog.10
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new HttpRxObserver(this.mContext, new ResultCallBack<GuardItemEntity>() { // from class: com.tomatolive.library.ui.view.dialog.GuardOpenContentDialog.9
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(GuardItemEntity guardItemEntity2) {
                if (GuardOpenContentDialog.this.openGuardCallbackListener != null) {
                    GuardOpenContentDialog.this.dismiss();
                    if (guardItemEntity2 == null || TextUtils.isEmpty(guardItemEntity2.getAccountBalance())) {
                        GuardOpenContentDialog.this.openGuardCallbackListener.OnOpenGuardFail();
                    } else {
                        GuardOpenContentDialog.this.openGuardCallbackListener.OnOpenGuardSuccess(guardItemEntity2);
                    }
                }
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str) {
                if (GuardOpenContentDialog.this.openGuardCallbackListener != null) {
                    GuardOpenContentDialog.this.dismiss();
                    GuardOpenContentDialog.this.showToast(str);
                    GuardOpenContentDialog.this.openGuardCallbackListener.OnOpenGuardFail();
                }
            }
        }, true));
    }

    private void getUserOver() {
        this.tvMoney.setText(getString(R$string.fq_userover_loading));
        ApiRetrofit.getInstance().getApiService().getQueryBalanceService(new RequestParams().getDefaultParams()).map(new ServerResultFunction<MyAccountEntity>() { // from class: com.tomatolive.library.ui.view.dialog.GuardOpenContentDialog.12
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new SimpleRxObserver<MyAccountEntity>() { // from class: com.tomatolive.library.ui.view.dialog.GuardOpenContentDialog.11
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(MyAccountEntity myAccountEntity) {
                String str = "0";
                String accountBalance = myAccountEntity == null ? str : myAccountEntity.getAccountBalance();
                GuardOpenContentDialog guardOpenContentDialog = GuardOpenContentDialog.this;
                if (myAccountEntity != null) {
                    str = myAccountEntity.getAccountBalance();
                }
                guardOpenContentDialog.userOver = str;
                GuardOpenContentDialog.this.tvMoney.setText(Html.fromHtml(((BaseRxDialogFragment) GuardOpenContentDialog.this).mContext.getString(R$string.fq_my_money_str, AppUtils.formatDisplayPrice(accountBalance, true))));
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onError(Throwable th) {
                super.onError(th);
                GuardOpenContentDialog.this.tvMoney.setText(Html.fromHtml(((BaseRxDialogFragment) GuardOpenContentDialog.this).mContext.getString(R$string.fq_my_money_str, GuardOpenContentDialog.this.getString(R$string.fq_userover_loading_fail))));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initGuardChangeTitleView(List<GuardItemEntity> list) {
        for (GuardItemEntity guardItemEntity : list) {
            String str = guardItemEntity.type;
            char c = 65535;
            switch (str.hashCode()) {
                case 49:
                    if (str.equals("1")) {
                        c = 0;
                        break;
                    }
                    break;
                case 50:
                    if (str.equals("2")) {
                        c = 1;
                        break;
                    }
                    break;
                case 51:
                    if (str.equals("3")) {
                        c = 2;
                        break;
                    }
                    break;
            }
            if (c != 0) {
                if (c == 1) {
                    this.tvMonth.setVisibility(0);
                    this.tvMonth.initData(guardItemEntity, this.anchorId);
                    initGuardViewInfo(this.guardItem.userGuardType);
                } else if (c == 2) {
                    this.tvYear.setVisibility(0);
                    this.tvYear.initData(guardItemEntity, this.anchorId);
                    if (this.tvWeek.getVisibility() != 0 && this.tvMonth.getVisibility() != 0) {
                        initGuardViewInfo("3");
                    } else {
                        initGuardViewInfo(this.guardItem.userGuardType);
                    }
                }
            } else if (!this.isOnOpenWeekGuard) {
                this.tvWeek.setVisibility(0);
                this.tvWeek.initData(guardItemEntity, this.anchorId);
                initGuardViewInfo(this.guardItem.userGuardType);
            }
        }
    }

    private void initBtnOpen(GuardItemEntity guardItemEntity) {
        if (guardItemEntity == null) {
            return;
        }
        this.tvOpen.setText(NumberUtils.string2int(this.guardItem.userGuardType) == NumberUtils.string2int(guardItemEntity.type) ? R$string.fq_guard_renewal_fee : R$string.fq_guard_open);
    }

    private void initGuardViewInfo(String str) {
        this.curType = str;
        setImgCover(TextUtils.equals(str, "3") ? R$drawable.fq_ic_guard_top_bg_2 : R$drawable.fq_ic_guard_top_bg_1);
        this.flAvatarBg.setBackgroundResource(TextUtils.equals(str, "3") ? R$drawable.fq_shape_guard_avatar_bg_circle_2 : R$drawable.fq_shape_guard_avatar_bg_circle_1);
        this.ivGuardType.setImageResource(TextUtils.equals(str, "3") ? R$drawable.fq_ic_live_msg_year_guard : R$drawable.fq_ic_live_msg_mouth_guard);
        this.tvWeek.showArrow(TextUtils.equals(str, "0") || TextUtils.equals(str, "1"));
        this.tvMonth.showArrow(TextUtils.equals(str, "2"));
        this.tvYear.showArrow(TextUtils.equals(str, "3"));
        this.tvPrivilege.setSelected(TextUtils.equals(str, "3"));
        char c = 65535;
        switch (str.hashCode()) {
            case 49:
                if (str.equals("1")) {
                    c = 0;
                    break;
                }
                break;
            case 50:
                if (str.equals("2")) {
                    c = 1;
                    break;
                }
                break;
            case 51:
                if (str.equals("3")) {
                    c = 2;
                    break;
                }
                break;
        }
        if (c == 0) {
            initBtnOpen(this.tvWeek.getGuardItemEntity());
        } else if (c == 1) {
            initBtnOpen(this.tvMonth.getGuardItemEntity());
        } else if (c != 2) {
        } else {
            initBtnOpen(this.tvYear.getGuardItemEntity());
        }
    }

    private void setOpenGuardCallbackListener(OnOpenGuardCallbackListener onOpenGuardCallbackListener) {
        this.openGuardCallbackListener = onOpenGuardCallbackListener;
    }

    private String getGuardTypeStr(String str) {
        GuardItemEntity guardItemEntity = this.currentSelectGuardItem;
        if (guardItemEntity == null) {
            return str;
        }
        String str2 = guardItemEntity.type;
        char c = 65535;
        switch (str2.hashCode()) {
            case 49:
                if (str2.equals("1")) {
                    c = 0;
                    break;
                }
                break;
            case 50:
                if (str2.equals("2")) {
                    c = 1;
                    break;
                }
                break;
            case 51:
                if (str2.equals("3")) {
                    c = 2;
                    break;
                }
                break;
        }
        if (c == 0) {
            return this.mContext.getString(R$string.fq_guard_week_money);
        }
        if (c == 1) {
            return this.mContext.getString(R$string.fq_guard_month_money);
        }
        return c != 2 ? str : this.mContext.getString(R$string.fq_guard_year_money);
    }

    @Override // com.trello.rxlifecycle2.components.support.RxDialogFragment, android.support.p002v4.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        if (this.openGuardCallbackListener != null) {
            this.openGuardCallbackListener = null;
        }
    }
}
