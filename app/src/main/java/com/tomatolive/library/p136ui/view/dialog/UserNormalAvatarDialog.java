package com.tomatolive.library.p136ui.view.dialog;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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

/* renamed from: com.tomatolive.library.ui.view.dialog.UserNormalAvatarDialog */
/* loaded from: classes3.dex */
public class UserNormalAvatarDialog extends BaseDialogFragment {
    private AnchorEntity anchorEntity;
    private String appId;
    private String avatar;
    private String expGrade;
    private FrameLayout flManageBg;
    private String gender;
    private boolean isManager;
    private ImageView ivAvatar;
    private ImageView ivGender;
    private ImageView ivMore;
    private LinearLayout llBottomBg;
    private LinearLayout llRoomNumFansBg;
    private LinearLayout llUserIconBg;
    private String nickName;
    private OnUserCardCallback onUserCardCallback;
    private String openId;
    private String role;
    private String tips;
    private TextView tvAttention;
    private TextView tvFansNum;
    private TextView tvHomepage;
    private TextView tvMarkContent;
    private TextView tvNickname;
    private TextView tvReport;
    private TextView tvRoomNumber;
    private UserCardFunctionView userCardFunctionView;
    private UserGradeView userGradeView;
    private String userId;
    private String userRole;
    private int liveType = 2;
    private String totalAchieveCount = "0";
    private boolean isShowImpression = false;
    private boolean isReport = false;
    private int fansCount = 0;

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public double getWidthScale() {
        return 0.73d;
    }

    public static UserNormalAvatarDialog newInstance(UserEntity userEntity, boolean z, OnUserCardCallback onUserCardCallback) {
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
        UserNormalAvatarDialog userNormalAvatarDialog = new UserNormalAvatarDialog();
        userNormalAvatarDialog.setArguments(bundle);
        userNormalAvatarDialog.setOnUserCardCallback(onUserCardCallback);
        return userNormalAvatarDialog;
    }

    public static UserNormalAvatarDialog newInstance(AnchorEntity anchorEntity, OnUserCardCallback onUserCardCallback) {
        return newInstance(anchorEntity, 2, onUserCardCallback);
    }

    public static UserNormalAvatarDialog newInstance(AnchorEntity anchorEntity, int i, OnUserCardCallback onUserCardCallback) {
        return newInstance(anchorEntity, i, true, true, true, onUserCardCallback);
    }

    public static UserNormalAvatarDialog newInstance(AnchorEntity anchorEntity, int i, boolean z, boolean z2, boolean z3, OnUserCardCallback onUserCardCallback) {
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
        UserNormalAvatarDialog userNormalAvatarDialog = new UserNormalAvatarDialog();
        userNormalAvatarDialog.setArguments(bundle);
        userNormalAvatarDialog.setOnUserCardCallback(onUserCardCallback);
        return userNormalAvatarDialog;
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
        }
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public int getLayoutRes() {
        return R$layout.fq_dialog_live_user_normal_avatar;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public void initView(View view) {
        this.ivAvatar = (ImageView) view.findViewById(R$id.iv_avatar);
        this.ivGender = (ImageView) view.findViewById(R$id.iv_gender);
        this.tvNickname = (TextView) view.findViewById(R$id.tv_nick_name);
        this.tvRoomNumber = (TextView) view.findViewById(R$id.tv_room_number);
        this.llBottomBg = (LinearLayout) view.findViewById(R$id.ll_bottom_bg);
        this.llRoomNumFansBg = (LinearLayout) view.findViewById(R$id.ll_attention_fans_bg);
        this.llUserIconBg = (LinearLayout) view.findViewById(R$id.ll_user_icon_bg);
        this.ivMore = (ImageView) view.findViewById(R$id.iv_more);
        this.userCardFunctionView = (UserCardFunctionView) view.findViewById(R$id.corner_user_grade);
        this.tvReport = (TextView) view.findViewById(R$id.tv_report);
        this.tvFansNum = (TextView) view.findViewById(R$id.tv_fans_num);
        this.tvAttention = (TextView) view.findViewById(R$id.tv_manage);
        this.flManageBg = (FrameLayout) view.findViewById(R$id.rl_manage_btn);
        this.tvHomepage = (TextView) view.findViewById(R$id.tv_homepage);
        this.tvMarkContent = (TextView) view.findViewById(R$id.tv_mark_content);
        this.userGradeView = (UserGradeView) view.findViewById(R$id.user_grade_view);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public void initListener(View view) {
        super.initListener(view);
        view.findViewById(R$id.iv_close).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$UserNormalAvatarDialog$VvaA7EBeDhlmp58a0k0mPoj-G_4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                UserNormalAvatarDialog.this.lambda$initListener$0$UserNormalAvatarDialog(view2);
            }
        });
        this.tvAttention.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$UserNormalAvatarDialog$wD6H8BXQT8Z764Spt17PHEmTXQk
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                UserNormalAvatarDialog.this.lambda$initListener$1$UserNormalAvatarDialog(view2);
            }
        });
        this.flManageBg.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$UserNormalAvatarDialog$Fl7BE1LPELjwbB87oWzEEiPLXrA
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                UserNormalAvatarDialog.this.lambda$initListener$2$UserNormalAvatarDialog(view2);
            }
        });
        this.tvHomepage.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$UserNormalAvatarDialog$nAeaNPLyIOI5KJfO8Sbj6Ylh-es
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                UserNormalAvatarDialog.this.lambda$initListener$3$UserNormalAvatarDialog(view2);
            }
        });
        this.userCardFunctionView.setOnFunctionViewListener(new UserCardFunctionView.UserCardFunctionViewListener() { // from class: com.tomatolive.library.ui.view.dialog.UserNormalAvatarDialog.1
            @Override // com.tomatolive.library.p136ui.view.custom.UserCardFunctionView.UserCardFunctionViewListener
            public void onGuardClickListener() {
                if (UserNormalAvatarDialog.this.onUserCardCallback != null) {
                    UserNormalAvatarDialog.this.onUserCardCallback.onClickGuardListener();
                }
            }

            @Override // com.tomatolive.library.p136ui.view.custom.UserCardFunctionView.UserCardFunctionViewListener
            public void onAchieveClickListener() {
                if (UserNormalAvatarDialog.this.onUserCardCallback != null) {
                    UserNormalAvatarDialog.this.dismiss();
                    UserNormalAvatarDialog.this.onUserCardCallback.onUserAchieveListener(UserNormalAvatarDialog.this.getUserAchieveUser(), UserNormalAvatarDialog.this.totalAchieveCount);
                }
            }

            @Override // com.tomatolive.library.p136ui.view.custom.UserCardFunctionView.UserCardFunctionViewListener
            public void onGiftWallClickListener() {
                if (UserNormalAvatarDialog.this.onUserCardCallback != null) {
                    UserNormalAvatarDialog.this.dismiss();
                    UserNormalAvatarDialog.this.onUserCardCallback.onGiftWallClickListener(UserNormalAvatarDialog.this.anchorEntity);
                }
            }

            @Override // com.tomatolive.library.p136ui.view.custom.UserCardFunctionView.UserCardFunctionViewListener
            public void onImpressionClickListener() {
                if (UserNormalAvatarDialog.this.isRestrictionUser() && !AppUtils.isAnchorLiveType(UserNormalAvatarDialog.this.liveType)) {
                    UserNormalAvatarDialog.this.dismiss();
                    Intent intent = new Intent(((BaseRxDialogFragment) UserNormalAvatarDialog.this).mContext, AnchorImpressionActivity.class);
                    intent.putExtra(ConstantUtils.RESULT_ITEM, AppUtils.getAnchorImpressionEntity(UserNormalAvatarDialog.this.anchorEntity));
                    ((BaseRxDialogFragment) UserNormalAvatarDialog.this).mContext.startActivity(intent);
                }
            }
        });
    }

    public /* synthetic */ void lambda$initListener$0$UserNormalAvatarDialog(View view) {
        dismiss();
    }

    public /* synthetic */ void lambda$initListener$1$UserNormalAvatarDialog(View view) {
        if (this.onUserCardCallback != null) {
            if (AppUtils.isAnchorLiveType(this.liveType)) {
                this.onUserCardCallback.onClickAttentionListener(view);
            } else if (!isRestrictionUser() || this.anchorEntity == null || !AppUtils.isAttentionUser(getContext(), this.anchorEntity.userId)) {
            } else {
                this.onUserCardCallback.onClickAttentionListener(view);
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
        }
    }

    public /* synthetic */ void lambda$initListener$2$UserNormalAvatarDialog(View view) {
        if (this.onUserCardCallback == null || !isRestrictionUser()) {
            return;
        }
        this.onUserCardCallback.onClickManageListener(view);
    }

    public /* synthetic */ void lambda$initListener$3$UserNormalAvatarDialog(View view) {
        if (!isRestrictionUser()) {
            return;
        }
        dismiss();
        AppUtils.onUserHomepageListener(this.mContext, this.anchorEntity);
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
        this.flManageBg.setVisibility(this.isManager ? 0 : 4);
        this.tvHomepage.setVisibility(SysConfigInfoManager.getInstance().isEnableAnchorHomepage() ? 0 : 8);
        GlideUtils.loadAvatar(getActivity(), this.ivAvatar, this.avatar, R$drawable.fq_ic_placeholder_avatar);
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
            this.llBottomBg.setVisibility(0);
            this.llRoomNumFansBg.setVisibility(0);
            this.fansCount = NumberUtils.string2int(this.anchorEntity.followerCount, 0);
            this.tvRoomNumber.setText(getString(R$string.fq_room_id, this.anchorEntity.liveId));
            this.tvFansNum.setText(getFansStr());
            this.tvAttention.setText(this.anchorEntity.isAttention() ? R$string.fq_home_btn_attention_yes : R$string.fq_home_btn_attention);
            this.tvAttention.setVisibility(TextUtils.equals(this.anchorEntity.userId, UserInfoManager.getInstance().getUserId()) ? 8 : 0);
            this.tvAttention.setSelected(this.anchorEntity.isAttention());
            this.tvHomepage.setVisibility(z ? 0 : 8);
            if (this.isReport) {
                this.ivMore.setVisibility(4);
                this.tvReport.setVisibility((TextUtils.equals(this.anchorEntity.userId, UserInfoManager.getInstance().getUserId()) || !SysConfigInfoManager.getInstance().isEnableReport()) ? 8 : 0);
            } else {
                this.ivMore.setVisibility(0);
                this.tvReport.setVisibility(4);
            }
            if (AppUtils.isAnchorLiveType(this.liveType)) {
                this.flManageBg.setVisibility(8);
                this.llBottomBg.setVisibility(8);
            } else if (TextUtils.isEmpty(this.anchorEntity.liveId)) {
                this.llUserIconBg.setVisibility(0);
                this.llRoomNumFansBg.setVisibility(8);
                this.tvReport.setVisibility(4);
                this.ivMore.setVisibility(0);
                if (z) {
                    this.tvAttention.setVisibility(8);
                    this.tvHomepage.setVisibility(0);
                } else {
                    this.llBottomBg.setVisibility(8);
                }
            }
        } else {
            this.llUserIconBg.setVisibility(0);
            this.llRoomNumFansBg.setVisibility(8);
            this.tvReport.setVisibility(4);
            this.ivMore.setVisibility(0);
            if (z) {
                this.llBottomBg.setVisibility(0);
                this.tvAttention.setVisibility(8);
                this.tvHomepage.setVisibility(0);
            } else {
                this.llBottomBg.setVisibility(8);
            }
        }
        if (this.llUserIconBg.getVisibility() == 0) {
            this.tvMarkContent.setText("");
            this.userGradeView.initUserGrade(this.expGrade);
        }
    }

    private void sendRequest() {
        ApiRetrofit.getInstance().getApiService().getUserCardService(new RequestParams().getUserCardParams(this.userId)).map(new ServerResultFunction<UserCardEntity>() { // from class: com.tomatolive.library.ui.view.dialog.UserNormalAvatarDialog.3
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new SimpleRxObserver<UserCardEntity>() { // from class: com.tomatolive.library.ui.view.dialog.UserNormalAvatarDialog.2
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                super.onSubscribe(disposable);
                UserNormalAvatarDialog.this.userCardFunctionView.setVisibility(4);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(UserCardEntity userCardEntity) {
                boolean z = false;
                UserNormalAvatarDialog.this.userCardFunctionView.setVisibility(0);
                boolean isAnchor = AppUtils.isAnchor(UserNormalAvatarDialog.this.userRole);
                if (isAnchor && UserNormalAvatarDialog.this.isShowImpression) {
                    z = true;
                }
                UserNormalAvatarDialog.this.totalAchieveCount = userCardEntity == null ? "0" : userCardEntity.getAchievementTotalNum();
                String str = UserNormalAvatarDialog.this.expGrade;
                if (userCardEntity != null && !TextUtils.isEmpty(userCardEntity.getAnchorGrade())) {
                    str = userCardEntity.getAnchorGrade();
                    isAnchor = true;
                }
                UserNormalAvatarDialog.this.userCardFunctionView.initData(isAnchor, z, str, userCardEntity);
                if (userCardEntity != null) {
                    AppUtils.initUserCardDynamicMark(((BaseRxDialogFragment) UserNormalAvatarDialog.this).mContext, UserNormalAvatarDialog.this.tvMarkContent, UserNormalAvatarDialog.this.userGradeView, UserNormalAvatarDialog.this.role, UserNormalAvatarDialog.this.expGrade, 0, UserNormalAvatarDialog.this.getMarks(userCardEntity.userMark), false);
                }
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onError(Throwable th) {
                super.onError(th);
                th.printStackTrace();
                UserNormalAvatarDialog.this.userCardFunctionView.setVisibility(4);
                UserNormalAvatarDialog.this.totalAchieveCount = "0";
                AppUtils.initUserCardDynamicMark(((BaseRxDialogFragment) UserNormalAvatarDialog.this).mContext, UserNormalAvatarDialog.this.tvMarkContent, UserNormalAvatarDialog.this.userGradeView, UserNormalAvatarDialog.this.role, UserNormalAvatarDialog.this.expGrade, 0, UserNormalAvatarDialog.this.getMarks(null), false);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<String> getMarks(List<String> list) {
        ArrayList arrayList = new ArrayList();
        if (AppUtils.isAnchor(this.role) || AppUtils.isHouseManager(this.role)) {
            arrayList.add(ConstantUtils.ROLE_ICON_KEY);
        }
        if (list != null && !list.isEmpty()) {
            arrayList.addAll(list);
        }
        arrayList.add(ConstantUtils.EXP_GRADE_ICON_KEY);
        return arrayList;
    }

    public OnUserCardCallback getOnAnchorCardCallback() {
        return this.onUserCardCallback;
    }

    public void setOnUserCardCallback(OnUserCardCallback onUserCardCallback) {
        this.onUserCardCallback = onUserCardCallback;
    }

    public String getTargetId() {
        return this.userId;
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
