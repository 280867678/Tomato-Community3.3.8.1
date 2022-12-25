package com.tomatolive.library.p136ui.view.dialog;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.blankj.utilcode.util.ConvertUtils;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.http.ApiRetrofit;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.function.HttpResultFunction;
import com.tomatolive.library.http.function.ServerResultFunction;
import com.tomatolive.library.model.AnchorEntity;
import com.tomatolive.library.model.UserCardEntity;
import com.tomatolive.library.model.UserEntity;
import com.tomatolive.library.p136ui.activity.live.AnchorImpressionActivity;
import com.tomatolive.library.p136ui.interfaces.OnUserCardCallback;
import com.tomatolive.library.p136ui.view.custom.UserCardFunctionView;
import com.tomatolive.library.p136ui.view.custom.UserGradeView;
import com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment;
import com.tomatolive.library.p136ui.view.dialog.base.BaseRxDialogFragment;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.GlideUtils;
import com.tomatolive.library.utils.NumberUtils;
import com.tomatolive.library.utils.SysConfigInfoManager;
import com.tomatolive.library.utils.UserInfoManager;
import com.tomatolive.library.utils.live.SimpleRxObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.dialog.UserNobilityAvatarDialog */
/* loaded from: classes3.dex */
public class UserNobilityAvatarDialog extends BaseDialogFragment {
    private AnchorEntity anchorEntity;
    private String appId;
    private String avatar;
    private String expGrade;
    private FrameLayout flAvatarBg;
    private String gender;
    private int guardType;
    private boolean isManager;
    private ImageView ivAvatar;
    private ImageView ivBadge;
    private ImageView ivCardBg;
    private ImageView ivCardFrame;
    private ImageView ivClose;
    private ImageView ivGender;
    private LinearLayout llAttentionBg;
    private LinearLayout llRoomNumFansBg;
    private LinearLayout llUserIconBg;
    private String nickName;
    private int nobleGrade;
    private OnUserCardCallback onAnchorCardCallback;
    private String openId;
    private RelativeLayout rlContentBg;
    private String role;
    private String tips;
    private TextView tvAttention;
    private TextView tvFansNum;
    private TextView tvHomepage;
    private TextView tvManage;
    private TextView tvMarkContent;
    private TextView tvNickname;
    private TextView tvRoomNumber;
    private TextView tvToNobility;
    private UserCardFunctionView userCardFunctionView;
    private UserGradeView userGradeView;
    private String userId;
    private String userRole;
    private boolean isShowImpression = false;
    private boolean isReport = false;
    private int fansCount = 0;
    private String totalAchieveCount = "0";
    private int liveType = 2;

    private int getCardBgTopMargin(int i) {
        switch (i) {
            case 1:
                return 12;
            case 2:
            case 3:
                return 25;
            case 4:
                return 20;
            case 5:
                return 25;
            case 6:
                return 35;
            case 7:
                return 30;
            default:
                return 12;
        }
    }

    private int getIVCloseTopMargin(int i) {
        if (i == 1 || i == 2) {
            return 10;
        }
        return (i == 4 || i == 5 || i == 6 || i == 7) ? 12 : 15;
    }

    private int getManageTopMargin(int i) {
        if (i == 1 || i == 2) {
            return 8;
        }
        if (i == 4) {
            return 10;
        }
        return (i == 5 || i == 7) ? 12 : 15;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public double getWidthScale() {
        return 0.83d;
    }

    public static UserNobilityAvatarDialog newInstance(UserEntity userEntity, boolean z, OnUserCardCallback onUserCardCallback) {
        AnchorEntity formatAnchorEntity = AppUtils.formatAnchorEntity(userEntity);
        if (AppUtils.isAnchor(userEntity.getUserRole())) {
            return newInstance(formatAnchorEntity, 2, z, false, false, onUserCardCallback);
        }
        Bundle bundle = new Bundle();
        bundle.putBoolean(ConstantUtils.BUNDLE_VALUE_MANAGER, z);
        bundle.putBoolean(ConstantUtils.BUNDLE_VALUE_IMPRESSION, false);
        bundle.putBoolean(ConstantUtils.BUNDLE_VALUE_REPORT, false);
        bundle.putInt("liveType", 2);
        bundle.putParcelable(ConstantUtils.RESULT_ITEM, formatAnchorEntity);
        UserNobilityAvatarDialog userNobilityAvatarDialog = new UserNobilityAvatarDialog();
        userNobilityAvatarDialog.setArguments(bundle);
        userNobilityAvatarDialog.setOnUserCardCallback(onUserCardCallback);
        return userNobilityAvatarDialog;
    }

    public static UserNobilityAvatarDialog newInstance(AnchorEntity anchorEntity, OnUserCardCallback onUserCardCallback) {
        return newInstance(anchorEntity, 2, onUserCardCallback);
    }

    public static UserNobilityAvatarDialog newInstance(AnchorEntity anchorEntity, int i, OnUserCardCallback onUserCardCallback) {
        return newInstance(anchorEntity, i, true, true, true, onUserCardCallback);
    }

    public static UserNobilityAvatarDialog newInstance(AnchorEntity anchorEntity, int i, boolean z, boolean z2, boolean z3, OnUserCardCallback onUserCardCallback) {
        Bundle bundle = new Bundle();
        if (AppUtils.isAnchorLiveType(i)) {
            String str = "1";
            anchorEntity.userRole = str;
            if (!TextUtils.isEmpty(anchorEntity.role)) {
                str = anchorEntity.role;
            }
            anchorEntity.role = str;
        }
        bundle.putBoolean(ConstantUtils.BUNDLE_VALUE_MANAGER, z);
        bundle.putBoolean(ConstantUtils.BUNDLE_VALUE_IMPRESSION, z2);
        bundle.putBoolean(ConstantUtils.BUNDLE_VALUE_REPORT, z3);
        bundle.putInt("liveType", i);
        bundle.putParcelable(ConstantUtils.RESULT_ITEM, anchorEntity);
        UserNobilityAvatarDialog userNobilityAvatarDialog = new UserNobilityAvatarDialog();
        userNobilityAvatarDialog.setArguments(bundle);
        userNobilityAvatarDialog.setOnUserCardCallback(onUserCardCallback);
        return userNobilityAvatarDialog;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseRxDialogFragment
    public void getBundle(Bundle bundle) {
        super.getBundle(bundle);
        this.anchorEntity = (AnchorEntity) bundle.getParcelable(ConstantUtils.RESULT_ITEM);
        this.isManager = getArgumentsBoolean(ConstantUtils.BUNDLE_VALUE_MANAGER, true);
        this.isReport = getArgumentsBoolean(ConstantUtils.BUNDLE_VALUE_REPORT, false);
        this.isShowImpression = getArgumentsBoolean(ConstantUtils.BUNDLE_VALUE_IMPRESSION, false);
        this.liveType = bundle.getInt("liveType", 2);
        AnchorEntity anchorEntity = this.anchorEntity;
        if (anchorEntity != null) {
            this.userId = anchorEntity.userId;
            this.nickName = anchorEntity.nickname;
            this.avatar = anchorEntity.avatar;
            this.gender = anchorEntity.sex;
            this.tips = anchorEntity.signature;
            this.role = anchorEntity.role;
            this.userRole = anchorEntity.userRole;
            this.expGrade = anchorEntity.expGrade;
            this.appId = anchorEntity.appId;
            this.openId = anchorEntity.openId;
            this.guardType = NumberUtils.string2int(anchorEntity.guardType);
            this.nobleGrade = this.anchorEntity.nobilityType;
        }
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    protected int getLayoutRes() {
        return R$layout.fq_dialog_live_nobility_avatar;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    protected void initView(View view) {
        this.rlContentBg = (RelativeLayout) view.findViewById(R$id.rl_content_bg);
        this.flAvatarBg = (FrameLayout) view.findViewById(R$id.fl_avatar_bg);
        this.ivCardBg = (ImageView) view.findViewById(R$id.iv_card_bg);
        this.ivCardFrame = (ImageView) view.findViewById(R$id.iv_card_frame);
        this.ivAvatar = (ImageView) view.findViewById(R$id.iv_avatar);
        this.ivBadge = (ImageView) view.findViewById(R$id.iv_badge);
        this.ivGender = (ImageView) view.findViewById(R$id.iv_gender);
        this.ivClose = (ImageView) view.findViewById(R$id.iv_close);
        this.tvNickname = (TextView) view.findViewById(R$id.tv_nick_name);
        this.tvRoomNumber = (TextView) view.findViewById(R$id.tv_room_number);
        this.llAttentionBg = (LinearLayout) view.findViewById(R$id.ll_attention_bg);
        this.llRoomNumFansBg = (LinearLayout) view.findViewById(R$id.ll_attention_fans_bg);
        this.llUserIconBg = (LinearLayout) view.findViewById(R$id.ll_user_icon_bg);
        this.tvManage = (TextView) view.findViewById(R$id.tv_manage);
        this.tvToNobility = (TextView) view.findViewById(R$id.tv_know);
        this.tvAttention = (TextView) view.findViewById(R$id.tv_attention);
        this.tvHomepage = (TextView) view.findViewById(R$id.tv_homepage);
        this.tvFansNum = (TextView) view.findViewById(R$id.tv_fans_num);
        this.userCardFunctionView = (UserCardFunctionView) view.findViewById(R$id.corner_user_grade);
        this.tvMarkContent = (TextView) view.findViewById(R$id.tv_mark_content);
        this.userGradeView = (UserGradeView) view.findViewById(R$id.user_grade_view);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public void initListener(View view) {
        super.initListener(view);
        this.tvManage.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$UserNobilityAvatarDialog$v4UWchGE2fBn5Y0JAw7S7UoK8ag
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                UserNobilityAvatarDialog.this.lambda$initListener$0$UserNobilityAvatarDialog(view2);
            }
        });
        this.tvToNobility.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$UserNobilityAvatarDialog$b03fxjWwl4KcuGRmDksZ5u9KIfM
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                UserNobilityAvatarDialog.this.lambda$initListener$1$UserNobilityAvatarDialog(view2);
            }
        });
        view.findViewById(R$id.iv_close).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$UserNobilityAvatarDialog$epjkdwUQCqD018VIlszdlCGqcI8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                UserNobilityAvatarDialog.this.lambda$initListener$2$UserNobilityAvatarDialog(view2);
            }
        });
        this.tvAttention.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$UserNobilityAvatarDialog$z6atPPTGjynvCM0QqgwJPs-Ugpg
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                UserNobilityAvatarDialog.this.lambda$initListener$3$UserNobilityAvatarDialog(view2);
            }
        });
        this.tvHomepage.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$UserNobilityAvatarDialog$Fapvs8N_sfarzqgG_9VPDVm6gts
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                UserNobilityAvatarDialog.this.lambda$initListener$4$UserNobilityAvatarDialog(view2);
            }
        });
        this.userCardFunctionView.setOnFunctionViewListener(new UserCardFunctionView.UserCardFunctionViewListener() { // from class: com.tomatolive.library.ui.view.dialog.UserNobilityAvatarDialog.1
            @Override // com.tomatolive.library.p136ui.view.custom.UserCardFunctionView.UserCardFunctionViewListener
            public void onGuardClickListener() {
                if (UserNobilityAvatarDialog.this.onAnchorCardCallback != null) {
                    UserNobilityAvatarDialog.this.onAnchorCardCallback.onClickGuardListener();
                }
            }

            @Override // com.tomatolive.library.p136ui.view.custom.UserCardFunctionView.UserCardFunctionViewListener
            public void onAchieveClickListener() {
                if (UserNobilityAvatarDialog.this.onAnchorCardCallback != null) {
                    UserNobilityAvatarDialog.this.dismiss();
                    UserNobilityAvatarDialog.this.onAnchorCardCallback.onUserAchieveListener(UserNobilityAvatarDialog.this.getUserAchieveUser(), UserNobilityAvatarDialog.this.totalAchieveCount);
                }
            }

            @Override // com.tomatolive.library.p136ui.view.custom.UserCardFunctionView.UserCardFunctionViewListener
            public void onGiftWallClickListener() {
                if (UserNobilityAvatarDialog.this.onAnchorCardCallback != null) {
                    UserNobilityAvatarDialog.this.dismiss();
                    UserNobilityAvatarDialog.this.onAnchorCardCallback.onGiftWallClickListener(UserNobilityAvatarDialog.this.anchorEntity);
                }
            }

            @Override // com.tomatolive.library.p136ui.view.custom.UserCardFunctionView.UserCardFunctionViewListener
            public void onImpressionClickListener() {
                if (UserNobilityAvatarDialog.this.isRestrictionUser() && !AppUtils.isAnchorLiveType(UserNobilityAvatarDialog.this.liveType)) {
                    UserNobilityAvatarDialog.this.dismiss();
                    Intent intent = new Intent(((BaseRxDialogFragment) UserNobilityAvatarDialog.this).mContext, AnchorImpressionActivity.class);
                    intent.putExtra(ConstantUtils.RESULT_ITEM, AppUtils.getAnchorImpressionEntity(UserNobilityAvatarDialog.this.anchorEntity));
                    ((BaseRxDialogFragment) UserNobilityAvatarDialog.this).mContext.startActivity(intent);
                }
            }
        });
    }

    public /* synthetic */ void lambda$initListener$0$UserNobilityAvatarDialog(View view) {
        if (this.onAnchorCardCallback == null || !isRestrictionUser()) {
            return;
        }
        this.onAnchorCardCallback.onClickManageListener(view);
    }

    public /* synthetic */ void lambda$initListener$1$UserNobilityAvatarDialog(View view) {
        if (isRestrictionUser() && !AppUtils.isAnchorLiveType(this.liveType)) {
            dismiss();
            OnUserCardCallback onUserCardCallback = this.onAnchorCardCallback;
            if (onUserCardCallback == null) {
                return;
            }
            onUserCardCallback.onClickNobilityListener(view);
        }
    }

    public /* synthetic */ void lambda$initListener$2$UserNobilityAvatarDialog(View view) {
        dismiss();
    }

    public /* synthetic */ void lambda$initListener$3$UserNobilityAvatarDialog(View view) {
        if (this.onAnchorCardCallback == null || !isRestrictionUser() || this.anchorEntity == null || !AppUtils.isAttentionUser(getContext(), this.anchorEntity.userId)) {
            return;
        }
        this.onAnchorCardCallback.onClickAttentionListener(view);
        boolean isSelected = view.isSelected();
        int i = this.fansCount;
        this.fansCount = isSelected ? i + 1 : i - 1;
        if (this.fansCount < 0) {
            this.fansCount = 0;
        }
        this.tvAttention.setSelected(isSelected);
        this.tvAttention.setText(!isSelected ? R$string.fq_home_btn_attention : R$string.fq_home_btn_attention_yes);
        this.tvFansNum.setText(getFansStr());
    }

    public /* synthetic */ void lambda$initListener$4$UserNobilityAvatarDialog(View view) {
        if (!isRestrictionUser()) {
            return;
        }
        dismiss();
        if (AppUtils.isAnchor(this.userRole)) {
            AppUtils.onUserHomepageListener(this.mContext, this.anchorEntity);
            return;
        }
        AppUtils.onUserHomepageListener(this.mContext, AppUtils.formatUserEntity(this.anchorEntity));
    }

    @Override // com.trello.rxlifecycle2.components.support.RxDialogFragment, android.support.p002v4.app.Fragment
    public void onResume() {
        super.onResume();
        updateData();
        sendRequest();
    }

    private void updateData() {
        boolean isAnchor = AppUtils.isAnchor(this.userRole);
        boolean z = SysConfigInfoManager.getInstance().isEnableUserHomepage() && !TextUtils.isEmpty(this.openId) && !TextUtils.isEmpty(this.appId);
        int i = 4;
        this.tvToNobility.setVisibility(AppUtils.isEnableNobility() ? 0 : 4);
        this.tvHomepage.setVisibility(SysConfigInfoManager.getInstance().isEnableAnchorHomepage() ? 0 : 8);
        initContentBgViewMarginLayoutParams(this.rlContentBg, this.nobleGrade);
        initViewTopMarginLayoutParams(this.ivCardBg, ConvertUtils.dp2px(getCardBgTopMargin(this.nobleGrade)));
        initViewTopMarginLayoutParams(this.tvManage, ConvertUtils.dp2px(getManageTopMargin(this.nobleGrade)));
        initViewTopMarginLayoutParams(this.ivClose, ConvertUtils.dp2px(getIVCloseTopMargin(this.nobleGrade)));
        this.flAvatarBg.setBackgroundResource(AppUtils.getNobilityAvatarBgDrawableRes(this.nobleGrade));
        this.ivCardBg.setImageResource(AppUtils.getNobilityCardBgDrawableRes(this.nobleGrade));
        this.ivBadge.setImageResource(AppUtils.getNobilityBadgeDrawableRes(this.nobleGrade));
        this.ivCardFrame.setImageResource(getCardFrameDrawableRes(this.nobleGrade));
        GlideUtils.loadAvatar(getActivity(), this.ivAvatar, this.avatar, R$drawable.fq_ic_placeholder_avatar);
        TextView textView = this.tvManage;
        if (this.isManager) {
            i = 0;
        }
        textView.setVisibility(i);
        this.tvNickname.setText(this.nickName);
        if (AppUtils.getGenderRes(this.gender) != -1) {
            this.ivGender.setImageResource(AppUtils.getGenderRes(this.gender));
        }
        AnchorEntity anchorEntity = this.anchorEntity;
        if (anchorEntity != null && isAnchor) {
            this.nickName = anchorEntity.nickname;
            this.gender = anchorEntity.sex;
            this.expGrade = anchorEntity.expGrade;
            this.llUserIconBg.setVisibility(8);
            this.llAttentionBg.setVisibility(0);
            this.llRoomNumFansBg.setVisibility(0);
            this.tvRoomNumber.setText(getString(R$string.fq_room_id, this.anchorEntity.liveId));
            this.tvManage.setText(this.isReport ? R$string.fq_report : R$string.fq_manage);
            if (this.isReport) {
                this.tvManage.setVisibility((TextUtils.equals(this.anchorEntity.userId, UserInfoManager.getInstance().getUserId()) || !SysConfigInfoManager.getInstance().isEnableReport()) ? 8 : 0);
            } else {
                this.tvManage.setVisibility((!this.isManager || TextUtils.equals(this.anchorEntity.userId, UserInfoManager.getInstance().getUserId())) ? 8 : 0);
            }
            this.fansCount = NumberUtils.string2int(this.anchorEntity.followerCount, 0);
            this.tvFansNum.setText(getFansStr());
            this.tvAttention.setText(this.anchorEntity.isAttention() ? R$string.fq_home_btn_attention_yes : R$string.fq_home_btn_attention);
            this.tvAttention.setVisibility(TextUtils.equals(this.anchorEntity.userId, UserInfoManager.getInstance().getUserId()) ? 8 : 0);
            this.tvAttention.setSelected(this.anchorEntity.isAttention());
            this.tvHomepage.setVisibility(z ? 0 : 8);
            if (AppUtils.isAnchorLiveType(this.liveType)) {
                this.tvManage.setVisibility(8);
                this.llAttentionBg.setVisibility(8);
            } else {
                if (TextUtils.isEmpty(this.anchorEntity.liveId)) {
                    this.llUserIconBg.setVisibility(0);
                    this.llRoomNumFansBg.setVisibility(8);
                }
                if (z) {
                    this.llAttentionBg.setVisibility(0);
                    this.tvAttention.setVisibility(8);
                    this.tvHomepage.setVisibility(0);
                } else {
                    this.llAttentionBg.setVisibility(8);
                }
            }
        } else {
            this.llUserIconBg.setVisibility(0);
            this.llRoomNumFansBg.setVisibility(8);
            if (z) {
                this.llAttentionBg.setVisibility(0);
                this.tvAttention.setVisibility(8);
                this.tvHomepage.setVisibility(0);
            } else {
                this.llAttentionBg.setVisibility(8);
            }
        }
        if (this.llUserIconBg.getVisibility() == 0) {
            this.tvMarkContent.setText("");
            this.userGradeView.initUserGradeMsg(this.expGrade, true);
        }
    }

    private void sendRequest() {
        ApiRetrofit.getInstance().getApiService().getUserCardService(new RequestParams().getUserCardParams(this.userId)).map(new ServerResultFunction<UserCardEntity>() { // from class: com.tomatolive.library.ui.view.dialog.UserNobilityAvatarDialog.3
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new SimpleRxObserver<UserCardEntity>() { // from class: com.tomatolive.library.ui.view.dialog.UserNobilityAvatarDialog.2
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                super.onSubscribe(disposable);
                UserNobilityAvatarDialog.this.userCardFunctionView.setVisibility(4);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(UserCardEntity userCardEntity) {
                boolean z;
                String str;
                UserNobilityAvatarDialog.this.userCardFunctionView.setVisibility(0);
                boolean isAnchor = AppUtils.isAnchor(UserNobilityAvatarDialog.this.userRole);
                boolean z2 = isAnchor && UserNobilityAvatarDialog.this.isShowImpression;
                UserNobilityAvatarDialog.this.totalAchieveCount = userCardEntity == null ? "0" : userCardEntity.getAchievementTotalNum();
                String str2 = UserNobilityAvatarDialog.this.expGrade;
                if (userCardEntity == null || TextUtils.isEmpty(userCardEntity.getAnchorGrade())) {
                    z = isAnchor;
                    str = str2;
                } else {
                    str = userCardEntity.getAnchorGrade();
                    z = true;
                }
                UserNobilityAvatarDialog.this.userCardFunctionView.initData(z, z2, str, UserNobilityAvatarDialog.this.nobleGrade, userCardEntity);
                if (userCardEntity != null) {
                    AppUtils.initUserCardDynamicMark(((BaseRxDialogFragment) UserNobilityAvatarDialog.this).mContext, UserNobilityAvatarDialog.this.tvMarkContent, UserNobilityAvatarDialog.this.userGradeView, UserNobilityAvatarDialog.this.role, UserNobilityAvatarDialog.this.expGrade, UserNobilityAvatarDialog.this.guardType, UserNobilityAvatarDialog.this.getMarks(userCardEntity.userMark), true);
                }
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onError(Throwable th) {
                super.onError(th);
                UserNobilityAvatarDialog.this.userCardFunctionView.setVisibility(4);
                UserNobilityAvatarDialog.this.totalAchieveCount = "0";
                AppUtils.initUserCardDynamicMark(((BaseRxDialogFragment) UserNobilityAvatarDialog.this).mContext, UserNobilityAvatarDialog.this.tvMarkContent, UserNobilityAvatarDialog.this.userGradeView, UserNobilityAvatarDialog.this.role, UserNobilityAvatarDialog.this.expGrade, UserNobilityAvatarDialog.this.guardType, UserNobilityAvatarDialog.this.getMarks(null), true);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<String> getMarks(List<String> list) {
        ArrayList arrayList = new ArrayList();
        if (AppUtils.isAnchor(this.role) || AppUtils.isHouseManager(this.role)) {
            arrayList.add(ConstantUtils.ROLE_ICON_KEY);
        }
        if (AppUtils.isGuardUser(this.guardType)) {
            arrayList.add(ConstantUtils.GUARD_TYPE_ICON_KEY);
        }
        if (list != null && !list.isEmpty()) {
            arrayList.addAll(list);
        }
        arrayList.add(ConstantUtils.EXP_GRADE_ICON_KEY);
        return arrayList;
    }

    public OnUserCardCallback getOnAnchorCardCallback() {
        return this.onAnchorCardCallback;
    }

    public void setOnUserCardCallback(OnUserCardCallback onUserCardCallback) {
        this.onAnchorCardCallback = onUserCardCallback;
    }

    public String getTargetId() {
        return this.userId;
    }

    private void initContentBgViewMarginLayoutParams(View view, int i) {
        int dp2px = ConvertUtils.dp2px((i == 1 || i == 2) ? 10 : 13);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
        layoutParams.leftMargin = dp2px;
        layoutParams.rightMargin = dp2px;
        view.setLayoutParams(layoutParams);
    }

    private void initViewTopMarginLayoutParams(View view, int i) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
        layoutParams.topMargin = i;
        view.setLayoutParams(layoutParams);
    }

    @DrawableRes
    private int getCardFrameDrawableRes(int i) {
        switch (i) {
            case 1:
                return R$drawable.fq_ic_nobility_card_frame_1;
            case 2:
                return R$drawable.fq_ic_nobility_card_frame_2;
            case 3:
                return R$drawable.fq_ic_nobility_card_frame_3;
            case 4:
                return R$drawable.fq_ic_nobility_card_frame_4;
            case 5:
                return R$drawable.fq_ic_nobility_card_frame_5;
            case 6:
                return R$drawable.fq_ic_nobility_card_frame_6;
            case 7:
                return R$drawable.fq_ic_nobility_card_frame_7;
            default:
                return R$drawable.fq_ic_nobility_card_frame_1;
        }
    }

    private String getFansStr() {
        return getString(R$string.fq_fans) + ConstantUtils.PLACEHOLDER_STR_ONE + AppUtils.formatTenThousandUnit(String.valueOf(this.fansCount));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public UserEntity getUserAchieveUser() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(this.userId);
        userEntity.setAvatar(this.avatar);
        userEntity.setName(this.nickName);
        userEntity.setRole(this.role);
        userEntity.setUserRole(this.userRole);
        userEntity.setAppId(this.appId);
        userEntity.setOpenId(this.openId);
        return userEntity;
    }
}
