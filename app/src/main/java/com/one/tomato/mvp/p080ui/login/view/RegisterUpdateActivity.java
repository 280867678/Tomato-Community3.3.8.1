package com.one.tomato.mvp.p080ui.login.view;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.permissions.RxPermissions;
import com.one.tomato.R$id;
import com.one.tomato.entity.p079db.UserInfo;
import com.one.tomato.mvp.base.view.MvpBaseActivity;
import com.one.tomato.mvp.p080ui.login.impl.IRegisterUpdateContact$IRegisterUpdateView;
import com.one.tomato.mvp.p080ui.login.presenter.RegisterUpdatePresenter;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.thirdpart.pictureselector.SelectPicTypeUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.TTUtil;
import com.one.tomato.utils.ToastUtil;
import com.one.tomato.utils.image.ImageLoaderUtil;
import com.one.tomato.widget.ClearEditText;
import com.tomatolive.library.TomatoLiveSDK;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: RegisterUpdateActivity.kt */
/* renamed from: com.one.tomato.mvp.ui.login.view.RegisterUpdateActivity */
/* loaded from: classes3.dex */
public final class RegisterUpdateActivity extends MvpBaseActivity<IRegisterUpdateContact$IRegisterUpdateView, RegisterUpdatePresenter> implements IRegisterUpdateContact$IRegisterUpdateView {
    public static final Companion Companion = new Companion(null);
    private HashMap _$_findViewCache;
    private List<? extends LocalMedia> selectList = new ArrayList();
    private SelectPicTypeUtil selectPicTypeUtil;
    private String tempLocalImg;
    private TTUtil ttUtil;
    public UserInfo userInfo;

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
        return R.layout.activity_register_update;
    }

    /* compiled from: RegisterUpdateActivity.kt */
    /* renamed from: com.one.tomato.mvp.ui.login.view.RegisterUpdateActivity$Companion */
    /* loaded from: classes3.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void startActivity(Context context) {
            Intrinsics.checkParameterIsNotNull(context, "context");
            Intent intent = new Intent();
            intent.setClass(context, RegisterUpdateActivity.class);
            context.startActivity(intent);
        }
    }

    public final UserInfo getUserInfo() {
        UserInfo userInfo = this.userInfo;
        if (userInfo != null) {
            return userInfo;
        }
        Intrinsics.throwUninitializedPropertyAccessException("userInfo");
        throw null;
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    /* renamed from: createPresenter  reason: collision with other method in class */
    public RegisterUpdatePresenter mo6439createPresenter() {
        return new RegisterUpdatePresenter();
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initView() {
        addListener();
    }

    @Override // com.one.tomato.mvp.base.view.MvpBaseActivity
    public void initData() {
        UserInfo userInfo = DBUtil.getUserInfo();
        Intrinsics.checkExpressionValueIsNotNull(userInfo, "DBUtil.getUserInfo()");
        this.userInfo = userInfo;
        this.selectPicTypeUtil = new SelectPicTypeUtil(this);
    }

    private final void addListener() {
        ((ImageView) _$_findCachedViewById(R$id.iv_back)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.login.view.RegisterUpdateActivity$addListener$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                RegisterUpdateActivity.this.onBackPressed();
            }
        });
        ((ConstraintLayout) _$_findCachedViewById(R$id.cl_male)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.login.view.RegisterUpdateActivity$addListener$2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                RegisterUpdateActivity.this.changeUi("1");
            }
        });
        ((ConstraintLayout) _$_findCachedViewById(R$id.cl_female)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.login.view.RegisterUpdateActivity$addListener$3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                RegisterUpdateActivity.this.changeUi("2");
            }
        });
        ((ImageView) _$_findCachedViewById(R$id.iv_head)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.login.view.RegisterUpdateActivity$addListener$4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                RegisterUpdateActivity.this.openPhoto();
            }
        });
        ((TextView) _$_findCachedViewById(R$id.tv_finish)).setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.mvp.ui.login.view.RegisterUpdateActivity$addListener$5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                RegisterUpdateActivity.this.updateUserInfo();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void changeUi(String str) {
        ConstraintLayout cl_sex = (ConstraintLayout) _$_findCachedViewById(R$id.cl_sex);
        Intrinsics.checkExpressionValueIsNotNull(cl_sex, "cl_sex");
        cl_sex.setVisibility(8);
        ConstraintLayout cl_head = (ConstraintLayout) _$_findCachedViewById(R$id.cl_head);
        Intrinsics.checkExpressionValueIsNotNull(cl_head, "cl_head");
        cl_head.setVisibility(0);
        UserInfo userInfo = this.userInfo;
        if (userInfo == null) {
            Intrinsics.throwUninitializedPropertyAccessException("userInfo");
            throw null;
        }
        userInfo.setSex(str);
        int hashCode = str.hashCode();
        if (hashCode != 49) {
            if (hashCode == 50 && str.equals("2")) {
                ((ImageView) _$_findCachedViewById(R$id.iv_head)).setImageResource(R.drawable.default_head_female);
            }
        } else if (str.equals("1")) {
            ((ImageView) _$_findCachedViewById(R$id.iv_head)).setImageResource(R.drawable.default_head_male);
        }
        ClearEditText clearEditText = (ClearEditText) _$_findCachedViewById(R$id.et_nick);
        UserInfo userInfo2 = this.userInfo;
        if (userInfo2 != null) {
            clearEditText.setHint(userInfo2.getName());
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("userInfo");
            throw null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void openPhoto() {
        new RxPermissions(this).request("android.permission.CAMERA").subscribe(new Observer<Boolean>() { // from class: com.one.tomato.mvp.ui.login.view.RegisterUpdateActivity$openPhoto$1
            @Override // io.reactivex.Observer
            public void onComplete() {
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable e) {
                Intrinsics.checkParameterIsNotNull(e, "e");
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable d) {
                Intrinsics.checkParameterIsNotNull(d, "d");
            }

            @Override // io.reactivex.Observer
            public /* bridge */ /* synthetic */ void onNext(Boolean bool) {
                onNext(bool.booleanValue());
            }

            public void onNext(boolean z) {
                SelectPicTypeUtil selectPicTypeUtil;
                List<LocalMedia> list;
                if (z) {
                    selectPicTypeUtil = RegisterUpdateActivity.this.selectPicTypeUtil;
                    if (selectPicTypeUtil == null) {
                        return;
                    }
                    list = RegisterUpdateActivity.this.selectList;
                    selectPicTypeUtil.selectCommonPhoto(1, true, false, true, list);
                    return;
                }
                ToastUtil.showCenterToast((int) R.string.permission_camera);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateUserInfo() {
        CharSequence trim;
        showWaitingDialog();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put(DatabaseFieldConfigLoader.FIELD_NAME_ID, Integer.valueOf(DBUtil.getMemberId()));
        UserInfo userInfo = this.userInfo;
        if (userInfo == null) {
            Intrinsics.throwUninitializedPropertyAccessException("userInfo");
            throw null;
        }
        linkedHashMap.put("sex", userInfo.getSex());
        if (!TextUtils.isEmpty(this.tempLocalImg)) {
            UserInfo userInfo2 = this.userInfo;
            if (userInfo2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("userInfo");
                throw null;
            }
            linkedHashMap.put("avatar", userInfo2.getAvatar());
        }
        ClearEditText et_nick = (ClearEditText) _$_findCachedViewById(R$id.et_nick);
        Intrinsics.checkExpressionValueIsNotNull(et_nick, "et_nick");
        String obj = et_nick.getText().toString();
        if (obj == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
        }
        trim = StringsKt__StringsKt.trim(obj);
        String obj2 = trim.toString();
        if (!TextUtils.isEmpty(obj2)) {
            UserInfo userInfo3 = this.userInfo;
            if (userInfo3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("userInfo");
                throw null;
            } else {
                userInfo3.setName(obj2);
                linkedHashMap.put("name", obj2);
            }
        }
        RegisterUpdatePresenter mPresenter = getMPresenter();
        if (mPresenter == null) {
            return;
        }
        mPresenter.requestUpdateUserInfo(linkedHashMap);
    }

    @Override // com.one.tomato.mvp.p080ui.login.impl.IRegisterUpdateContact$IRegisterUpdateView
    public void handleUpdateUserInfo() {
        hideWaitingDialog();
        UserInfo userInfo = this.userInfo;
        if (userInfo == null) {
            Intrinsics.throwUninitializedPropertyAccessException("userInfo");
            throw null;
        }
        DBUtil.saveUserInfo(userInfo);
        StringBuilder sb = new StringBuilder();
        DomainServer domainServer = DomainServer.getInstance();
        Intrinsics.checkExpressionValueIsNotNull(domainServer, "DomainServer.getInstance()");
        sb.append(domainServer.getTtViewPicture());
        UserInfo userInfo2 = this.userInfo;
        if (userInfo2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("userInfo");
            throw null;
        }
        sb.append(userInfo2.getAvatar());
        TomatoLiveSDK.getSingleton().onUpdateUserAvatar(this, sb.toString());
        TomatoLiveSDK singleton = TomatoLiveSDK.getSingleton();
        UserInfo userInfo3 = this.userInfo;
        if (userInfo3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("userInfo");
            throw null;
        }
        singleton.onUpdateUserNickName(this, userInfo3.getName());
        TomatoLiveSDK singleton2 = TomatoLiveSDK.getSingleton();
        UserInfo userInfo4 = this.userInfo;
        if (userInfo4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("userInfo");
            throw null;
        }
        singleton2.onUpdateUserSex(this, userInfo4.getSex());
        finish();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == -1 && i == 188) {
            List<LocalMedia> obtainMultipleResult = PictureSelector.obtainMultipleResult(intent);
            Intrinsics.checkExpressionValueIsNotNull(obtainMultipleResult, "PictureSelector.obtainMultipleResult(data)");
            this.selectList = obtainMultipleResult;
            for (LocalMedia localMedia : this.selectList) {
                Log.i("图片-----》", localMedia.getPath());
            }
            uploadAvatar();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void uploadAvatar() {
        this.ttUtil = new TTUtil(2, new TTUtil.UploadFileToTTListener() { // from class: com.one.tomato.mvp.ui.login.view.RegisterUpdateActivity$uploadAvatar$1
            @Override // com.one.tomato.utils.TTUtil.UploadFileToTTListener
            public void start() {
                RegisterUpdateActivity.this.showWaitingDialog();
                RegisterUpdateActivity.this.tempLocalImg = null;
            }

            @Override // com.one.tomato.utils.TTUtil.UploadFileToTTListener
            public void uploadSuccess(LocalMedia media) {
                TTUtil tTUtil;
                TTUtil tTUtil2;
                List list;
                String str;
                Intrinsics.checkParameterIsNotNull(media, "media");
                if (RegisterUpdateActivity.this.isDestroyed()) {
                    return;
                }
                RegisterUpdateActivity.this.hideWaitingDialog();
                StringBuilder sb = new StringBuilder();
                sb.append("/");
                tTUtil = RegisterUpdateActivity.this.ttUtil;
                if (tTUtil == null) {
                    Intrinsics.throwNpe();
                    throw null;
                }
                sb.append(tTUtil.getBucketName());
                sb.append("/");
                tTUtil2 = RegisterUpdateActivity.this.ttUtil;
                if (tTUtil2 == null) {
                    Intrinsics.throwNpe();
                    throw null;
                }
                sb.append(tTUtil2.getCeph(media));
                RegisterUpdateActivity.this.getUserInfo().setAvatar(sb.toString());
                RegisterUpdateActivity registerUpdateActivity = RegisterUpdateActivity.this;
                list = registerUpdateActivity.selectList;
                registerUpdateActivity.tempLocalImg = ((LocalMedia) list.get(0)).getPath();
                RegisterUpdateActivity registerUpdateActivity2 = RegisterUpdateActivity.this;
                str = RegisterUpdateActivity.this.tempLocalImg;
                ImageLoaderUtil.loadNormalLocalImage(registerUpdateActivity2, (ImageView) registerUpdateActivity2._$_findCachedViewById(R$id.iv_head), str, ImageLoaderUtil.getHeadImageOption((ImageView) RegisterUpdateActivity.this._$_findCachedViewById(R$id.iv_head)));
            }

            @Override // com.one.tomato.utils.TTUtil.UploadFileToTTListener
            public void uploadFail() {
                if (RegisterUpdateActivity.this.isDestroyed()) {
                    return;
                }
                RegisterUpdateActivity.this.hideWaitingDialog();
                RegisterUpdateActivity.this.tempLocalImg = null;
                ToastUtil.showCenterToast((int) R.string.common_upload_img_fail);
            }
        });
        TTUtil tTUtil = this.ttUtil;
        if (tTUtil != 0) {
            tTUtil.getStsToken(this.selectList);
        } else {
            Intrinsics.throwNpe();
            throw null;
        }
    }
}
