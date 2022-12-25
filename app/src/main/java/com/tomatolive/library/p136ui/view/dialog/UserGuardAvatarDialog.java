package com.tomatolive.library.p136ui.view.dialog;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.http.ApiRetrofit;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.function.HttpResultFunction;
import com.tomatolive.library.http.function.ServerResultFunction;
import com.tomatolive.library.model.AnchorEntity;
import com.tomatolive.library.model.UserCardEntity;
import com.tomatolive.library.model.UserEntity;
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
import com.tomatolive.library.utils.live.SimpleRxObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.dialog.UserGuardAvatarDialog */
/* loaded from: classes3.dex */
public class UserGuardAvatarDialog extends BaseDialogFragment {
    private AnchorEntity anchorEntity;
    private String appId;
    private String avatar;
    private String expGrade;
    private String gender;
    private int guardType;
    private boolean isManager;
    private ImageView ivAvatar;
    private ImageView ivAvatarBg;
    private ImageView ivGender;
    private ImageView ivManager;
    private String nickName;
    private OnUserCardCallback onUserCardCallback;
    private String openId;
    private RelativeLayout rlDialogBg;
    private String role;
    private String tips;
    private String totalAchieveCount = "0";
    private TextView tvHomepage;
    private TextView tvMarkContent;
    private TextView tvNickname;
    private UserCardFunctionView userCardFunctionView;
    private UserGradeView userGradeView;
    private String userId;
    private String userRole;

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public double getWidthScale() {
        return 0.73d;
    }

    public static UserGuardAvatarDialog newInstance(UserEntity userEntity, boolean z, OnUserCardCallback onUserCardCallback) {
        AnchorEntity formatAnchorEntity = AppUtils.formatAnchorEntity(userEntity);
        Bundle bundle = new Bundle();
        bundle.putBoolean(ConstantUtils.BUNDLE_VALUE_MANAGER, z);
        bundle.putParcelable(ConstantUtils.RESULT_ITEM, formatAnchorEntity);
        UserGuardAvatarDialog userGuardAvatarDialog = new UserGuardAvatarDialog();
        userGuardAvatarDialog.setArguments(bundle);
        userGuardAvatarDialog.setOnUserCardCallback(onUserCardCallback);
        return userGuardAvatarDialog;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseRxDialogFragment
    public void getBundle(Bundle bundle) {
        super.getBundle(bundle);
        this.anchorEntity = (AnchorEntity) bundle.getParcelable(ConstantUtils.RESULT_ITEM);
        this.isManager = getArgumentsBoolean(ConstantUtils.BUNDLE_VALUE_MANAGER, true);
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
        }
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public int getLayoutRes() {
        return R$layout.fq_dialog_live_user_guard_avatar;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public void initView(View view) {
        this.rlDialogBg = (RelativeLayout) view.findViewById(R$id.rl_dialog_bg);
        this.ivAvatar = (ImageView) view.findViewById(R$id.iv_avatar);
        this.ivAvatarBg = (ImageView) view.findViewById(R$id.iv_avatar_bg);
        this.ivManager = (ImageView) view.findViewById(R$id.iv_manage);
        this.ivGender = (ImageView) view.findViewById(R$id.iv_gender);
        this.tvNickname = (TextView) view.findViewById(R$id.tv_nick_name);
        this.tvHomepage = (TextView) view.findViewById(R$id.tv_homepage);
        this.userCardFunctionView = (UserCardFunctionView) view.findViewById(R$id.corner_user_grade);
        this.tvMarkContent = (TextView) view.findViewById(R$id.tv_mark_content);
        this.userGradeView = (UserGradeView) view.findViewById(R$id.user_grade_view);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public void initListener(final View view) {
        super.initListener(view);
        this.ivManager.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$UserGuardAvatarDialog$IlB07h7OTzx6bE9SIcpNl80dOvs
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                UserGuardAvatarDialog.this.lambda$initListener$0$UserGuardAvatarDialog(view, view2);
            }
        });
        this.tvHomepage.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$UserGuardAvatarDialog$IewYQTG3wUxotD1SJU7uXrYuloE
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                UserGuardAvatarDialog.this.lambda$initListener$1$UserGuardAvatarDialog(view2);
            }
        });
        view.findViewById(R$id.iv_close).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$UserGuardAvatarDialog$Cl2XBRMIuxmR7HaAz2pE4a5ASJw
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                UserGuardAvatarDialog.this.lambda$initListener$2$UserGuardAvatarDialog(view2);
            }
        });
        this.userCardFunctionView.setOnFunctionViewListener(new UserCardFunctionView.UserCardFunctionViewListener() { // from class: com.tomatolive.library.ui.view.dialog.UserGuardAvatarDialog.1
            @Override // com.tomatolive.library.p136ui.view.custom.UserCardFunctionView.UserCardFunctionViewListener
            public void onGuardClickListener() {
            }

            @Override // com.tomatolive.library.p136ui.view.custom.UserCardFunctionView.UserCardFunctionViewListener
            public void onImpressionClickListener() {
            }

            @Override // com.tomatolive.library.p136ui.view.custom.UserCardFunctionView.UserCardFunctionViewListener
            public void onAchieveClickListener() {
                if (UserGuardAvatarDialog.this.onUserCardCallback != null) {
                    UserGuardAvatarDialog.this.dismiss();
                    UserGuardAvatarDialog.this.onUserCardCallback.onUserAchieveListener(UserGuardAvatarDialog.this.getUserAchieveUser(), UserGuardAvatarDialog.this.totalAchieveCount);
                }
            }

            @Override // com.tomatolive.library.p136ui.view.custom.UserCardFunctionView.UserCardFunctionViewListener
            public void onGiftWallClickListener() {
                if (UserGuardAvatarDialog.this.onUserCardCallback != null) {
                    UserGuardAvatarDialog.this.dismiss();
                    UserGuardAvatarDialog.this.onUserCardCallback.onGiftWallClickListener(UserGuardAvatarDialog.this.anchorEntity);
                }
            }
        });
    }

    public /* synthetic */ void lambda$initListener$0$UserGuardAvatarDialog(View view, View view2) {
        if (this.onUserCardCallback == null || !isRestrictionUser()) {
            return;
        }
        this.onUserCardCallback.onClickManageListener(view);
    }

    public /* synthetic */ void lambda$initListener$1$UserGuardAvatarDialog(View view) {
        if (!isRestrictionUser()) {
            return;
        }
        dismiss();
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(this.userId);
        userEntity.setAvatar(this.avatar);
        userEntity.setName(this.nickName);
        userEntity.setSex(this.gender);
        userEntity.setExpGrade(this.expGrade);
        userEntity.setRole(this.role);
        userEntity.setAppId(this.appId);
        userEntity.setOpenId(this.openId);
        AppUtils.onUserHomepageListener(this.mContext, userEntity);
    }

    public /* synthetic */ void lambda$initListener$2$UserGuardAvatarDialog(View view) {
        dismiss();
    }

    @Override // com.trello.rxlifecycle2.components.support.RxDialogFragment, android.support.p002v4.app.Fragment
    public void onResume() {
        super.onResume();
        updateData();
        sendRequest();
    }

    private void updateData() {
        AppUtils.isAnchor(this.userRole);
        int i = 0;
        this.tvHomepage.setVisibility(SysConfigInfoManager.getInstance().isEnableUserHomepage() && !TextUtils.isEmpty(this.openId) && !TextUtils.isEmpty(this.appId) ? 0 : 8);
        this.tvNickname.setText(this.nickName);
        if (AppUtils.getGenderRes(this.gender) != -1) {
            this.ivGender.setImageResource(AppUtils.getGenderRes(this.gender));
        }
        ImageView imageView = this.ivManager;
        if (!this.isManager) {
            i = 4;
        }
        imageView.setVisibility(i);
        this.rlDialogBg.setBackgroundResource(AppUtils.isYearGuard(this.guardType) ? R$drawable.fq_ic_dialog_bg_guard_year : R$drawable.fq_ic_dialog_bg_guard_month);
        this.ivAvatarBg.setImageResource(AppUtils.isYearGuard(this.guardType) ? R$drawable.fq_ic_guard_year_avatar_bg_big : R$drawable.fq_ic_guard_month_avatar_bg_big);
        GlideUtils.loadAvatar(getActivity(), this.ivAvatar, this.avatar, R$drawable.fq_ic_placeholder_avatar);
    }

    private void sendRequest() {
        ApiRetrofit.getInstance().getApiService().getUserCardService(new RequestParams().getUserCardParams(this.userId)).map(new ServerResultFunction<UserCardEntity>() { // from class: com.tomatolive.library.ui.view.dialog.UserGuardAvatarDialog.3
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new SimpleRxObserver<UserCardEntity>() { // from class: com.tomatolive.library.ui.view.dialog.UserGuardAvatarDialog.2
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                super.onSubscribe(disposable);
                UserGuardAvatarDialog.this.userCardFunctionView.setVisibility(4);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(UserCardEntity userCardEntity) {
                UserGuardAvatarDialog.this.userCardFunctionView.setVisibility(0);
                boolean isAnchor = AppUtils.isAnchor(UserGuardAvatarDialog.this.userRole);
                UserGuardAvatarDialog.this.totalAchieveCount = userCardEntity == null ? "0" : userCardEntity.getAchievementTotalNum();
                String str = UserGuardAvatarDialog.this.expGrade;
                if (userCardEntity != null && !TextUtils.isEmpty(userCardEntity.getAnchorGrade())) {
                    str = userCardEntity.getAnchorGrade();
                    isAnchor = true;
                }
                UserGuardAvatarDialog.this.userCardFunctionView.initData(isAnchor, false, str, userCardEntity);
                if (userCardEntity != null) {
                    AppUtils.initUserCardDynamicMark(((BaseRxDialogFragment) UserGuardAvatarDialog.this).mContext, UserGuardAvatarDialog.this.tvMarkContent, UserGuardAvatarDialog.this.userGradeView, UserGuardAvatarDialog.this.role, UserGuardAvatarDialog.this.expGrade, UserGuardAvatarDialog.this.guardType, UserGuardAvatarDialog.this.getMarks(userCardEntity.userMark), false);
                }
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onError(Throwable th) {
                super.onError(th);
                UserGuardAvatarDialog.this.userCardFunctionView.setVisibility(4);
                UserGuardAvatarDialog.this.totalAchieveCount = "0";
                AppUtils.initUserCardDynamicMark(((BaseRxDialogFragment) UserGuardAvatarDialog.this).mContext, UserGuardAvatarDialog.this.tvMarkContent, UserGuardAvatarDialog.this.userGradeView, UserGuardAvatarDialog.this.role, UserGuardAvatarDialog.this.expGrade, UserGuardAvatarDialog.this.guardType, UserGuardAvatarDialog.this.getMarks(null), false);
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

    public OnUserCardCallback getOnUserCardCallback() {
        return this.onUserCardCallback;
    }

    public void setOnUserCardCallback(OnUserCardCallback onUserCardCallback) {
        this.onUserCardCallback = onUserCardCallback;
    }

    public String getTargetId() {
        return this.userId;
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
