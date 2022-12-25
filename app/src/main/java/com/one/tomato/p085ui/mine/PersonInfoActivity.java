package com.one.tomato.p085ui.mine;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bigkoo.pickerview.configure.PickerOptions;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.broccoli.p150bh.R;
import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.permissions.RxPermissions;
import com.one.tomato.base.BaseActivity;
import com.one.tomato.dialog.CustomAlertDialog;
import com.one.tomato.entity.BaseModel;
import com.one.tomato.entity.ImageBean;
import com.one.tomato.entity.PersonInfoResponse;
import com.one.tomato.entity.p079db.UserInfo;
import com.one.tomato.net.ResponseObserver;
import com.one.tomato.net.TomatoCallback;
import com.one.tomato.net.TomatoParams;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.thirdpart.pictureselector.SelectPicTypeUtil;
import com.one.tomato.thirdpart.tomatolive.TomatoLiveSDKUtils;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.TTUtil;
import com.one.tomato.utils.ToastUtil;
import com.one.tomato.utils.image.ImageLoaderUtil;
import com.one.tomato.widget.MineCustomItemLayout;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import com.tomatolive.library.TomatoLiveSDK;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_personal_info)
/* renamed from: com.one.tomato.ui.mine.PersonInfoActivity */
/* loaded from: classes3.dex */
public class PersonInfoActivity extends BaseActivity {
    private String locationCity;
    private String locationCountry;
    private String locationProvince;
    private String personInfoOptionType;
    @ViewInject(R.id.rl_head)
    private MineCustomItemLayout rl_head;
    @ViewInject(R.id.rl_location)
    private MineCustomItemLayout rl_location;
    @ViewInject(R.id.rl_nick)
    private MineCustomItemLayout rl_nick;
    @ViewInject(R.id.rl_sex)
    private MineCustomItemLayout rl_sex;
    @ViewInject(R.id.rl_signature)
    private MineCustomItemLayout rl_signature;
    private SelectPicTypeUtil selectPicTypeUtil;
    private String tempLocalImg;
    private TTUtil ttUtil;
    @ViewInject(R.id.tv_complete)
    private TextView tv_complete;
    private String type;
    private String typeFrom;
    private UserInfo userInfo;
    private Map<String, String> infoMap = new HashMap();
    private List<LocalMedia> selectList = new ArrayList();
    private List<String> optionList = new ArrayList();

    public static void startActivity(Context context, String str) {
        Intent intent = new Intent();
        intent.setClass(context, PersonInfoActivity.class);
        intent.putExtra("type_intent", str);
        context.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initView();
    }

    private void initView() {
        char c;
        this.typeFrom = getIntent().getExtras().getString("type_intent");
        String str = this.typeFrom;
        int hashCode = str.hashCode();
        if (hashCode != -690213213) {
            if (hashCode == 3351635 && str.equals("mine")) {
                c = 0;
            }
            c = 65535;
        } else {
            if (str.equals("register")) {
                c = 1;
            }
            c = 65535;
        }
        if (c == 0) {
            initTitleBar();
            this.titleTV.setText(R.string.person_info_title);
            initUserInfo(true);
        } else if (c == 1) {
            initTitleBar();
            this.rl_title_bg.setVisibility(4);
            this.tv_complete.setVisibility(0);
            initUserInfo(true);
        }
        this.selectPicTypeUtil = new SelectPicTypeUtil(this);
    }

    @Event({R.id.rl_head, R.id.rl_nick, R.id.rl_signature, R.id.rl_sex, R.id.rl_location, R.id.tv_complete})
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_head /* 2131298073 */:
                openPhoto();
                return;
            case R.id.rl_location /* 2131298090 */:
                LocationPickActivity.startActivity(this.mContext, this.userInfo.getCountry(), this.userInfo.getProvince(), this.userInfo.getCity());
                return;
            case R.id.rl_nick /* 2131298102 */:
                ModifyPersonInfoActivity.startActivity(this.mContext, 24, this.typeFrom);
                return;
            case R.id.rl_sex /* 2131298136 */:
                pickOption("sex");
                return;
            case R.id.rl_signature /* 2131298140 */:
                ModifyPersonInfoActivity.startActivity(this.mContext, 25, this.typeFrom);
                return;
            case R.id.tv_complete /* 2131298767 */:
                updateAllInfo();
                return;
            default:
                return;
        }
    }

    private void initUserInfo(boolean z) {
        this.userInfo = DBUtil.getUserInfo();
        if (z) {
            ImageLoaderUtil.loadHeadImage(this.mContext, this.rl_head.getRightSubImg(), new ImageBean(this.userInfo.getAvatar()));
        }
        this.rl_nick.setRightSubText(this.userInfo.getName());
        this.rl_signature.setRightSubText(this.userInfo.getSignatureHint());
        this.rl_sex.setRightSubText(this.userInfo.getSexDes());
        this.rl_location.setRightSubText(this.userInfo.getLocation());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public void updateInfo(String str, String str2) {
        char c;
        this.type = str;
        this.infoMap.clear();
        this.infoMap.put(str, str2);
        showWaitingDialog();
        TomatoParams tomatoParams = new TomatoParams(DomainServer.getInstance().getServerUrl(), "/app/memberInfo/modify");
        tomatoParams.addParameter(DatabaseFieldConfigLoader.FIELD_NAME_ID, Integer.valueOf(DBUtil.getMemberId()));
        switch (str.hashCode()) {
            case -1990458108:
                if (str.equals("social_love")) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case 113766:
                if (str.equals("sex")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 3198432:
                if (str.equals("head")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 3381091:
                if (str.equals("nick")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 93746367:
                if (str.equals("birth")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 103666725:
                if (str.equals("marry")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 1073584312:
                if (str.equals("signature")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 1529844651:
                if (str.equals("sex_love")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 1901043637:
                if (str.equals("location")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                tomatoParams.addParameter("avatar", str2);
                break;
            case 1:
                tomatoParams.addParameter("name", str2);
                break;
            case 2:
                tomatoParams.addParameter("signature", str2);
                break;
            case 3:
                tomatoParams.addParameter("sex", str2);
                break;
            case 4:
                tomatoParams.addParameter("birth", str2);
                break;
            case 5:
                tomatoParams.addParameter(AopConstants.COUNTRY, this.locationCountry);
                tomatoParams.addParameter(AopConstants.PROVINCE, this.locationProvince);
                tomatoParams.addParameter(AopConstants.CITY, this.locationCity);
                break;
            case 6:
                tomatoParams.addParameter("sexualOrientation", str2);
                break;
            case 7:
                tomatoParams.addParameter("marriage", str2);
                break;
            case '\b':
                tomatoParams.addParameter("friendshipIntention", str2);
                break;
        }
        tomatoParams.post(new TomatoCallback((ResponseObserver) this, 1, PersonInfoResponse.class));
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0076, code lost:
        if (r2.equals("nick") != false) goto L12;
     */
    @Override // com.one.tomato.base.BaseActivity, com.one.tomato.net.ResponseObserver
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void handleResponse(Message message) {
        super.handleResponse(message);
        if (isDestroyed()) {
            return;
        }
        PersonInfoResponse personInfoResponse = (PersonInfoResponse) ((BaseModel) message.obj).obj;
        hideWaitingDialog();
        char c = 1;
        if (message.what != 1) {
            return;
        }
        ToastUtil.showCenterToast((int) R.string.common_modify_success);
        String str = this.infoMap.get(this.type);
        String str2 = this.type;
        switch (str2.hashCode()) {
            case -1990458108:
                if (str2.equals("social_love")) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case 113766:
                if (str2.equals("sex")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 3198432:
                if (str2.equals("head")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 3381091:
                break;
            case 93746367:
                if (str2.equals("birth")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 103666725:
                if (str2.equals("marry")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 1073584312:
                if (str2.equals("signature")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 1529844651:
                if (str2.equals("sex_love")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 1901043637:
                if (str2.equals("location")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                this.userInfo.setAvatar(str);
                TomatoLiveSDKUtils.getSingleton().onUpdateUserAvatar(this, DomainServer.getInstance().getTtViewPicture() + str);
                TomatoLiveSDKUtils.getSingleton().updateIsRisk(this, personInfoResponse.isRisk);
                ImageLoaderUtil.loadNormalLocalImage(this.mContext, this.rl_head.getRightSubImg(), this.tempLocalImg, ImageLoaderUtil.getHeadImageOption(this.rl_head.getRightSubImg()));
                this.tempLocalImg = null;
                break;
            case 1:
                this.userInfo.setName(str);
                TomatoLiveSDKUtils.getSingleton().onUpdateUserNickName(this, str);
                TomatoLiveSDKUtils.getSingleton().updateIsRisk(this, personInfoResponse.isRisk);
                break;
            case 2:
                this.userInfo.setSex(str);
                TomatoLiveSDKUtils.getSingleton().onUpdateUserSex(this, str);
                TomatoLiveSDKUtils.getSingleton().updateIsRisk(this, personInfoResponse.isRisk);
                break;
            case 3:
                this.userInfo.setSignature(str);
                TomatoLiveSDKUtils.getSingleton().updateIsRisk(this, personInfoResponse.isRisk);
                break;
            case 4:
                this.userInfo.setBirth(str);
                break;
            case 5:
                this.userInfo.setCountry(this.locationCountry);
                this.userInfo.setProvince(this.locationProvince);
                this.userInfo.setCity(this.locationCity);
                this.locationCountry = null;
                this.locationProvince = null;
                this.locationCity = null;
                break;
            case 6:
                this.userInfo.setSexualOrientation(str);
                break;
            case 7:
                this.userInfo.setMarriage(str);
                break;
            case '\b':
                this.userInfo.setFriendshipIntention(str);
                break;
        }
        DBUtil.saveUserInfo(this.userInfo);
        initUserInfo(false);
    }

    @Override // com.one.tomato.base.BaseActivity, com.one.tomato.net.ResponseObserver
    public boolean handleResponseError(Message message) {
        char c = 0;
        if (isDestroyed()) {
            return false;
        }
        hideWaitingDialog();
        if (message.what != 1) {
            return false;
        }
        String str = this.type;
        if (str.hashCode() != 1901043637 || !str.equals("location")) {
            c = 65535;
        }
        if (c != 0) {
            return true;
        }
        this.locationCountry = null;
        this.locationProvince = null;
        this.locationCity = null;
        return true;
    }

    private void updateAllInfo() {
        DBUtil.saveUserInfo(this.userInfo);
        TomatoLiveSDK.getSingleton().onUpdateUserAvatar(this, DomainServer.getInstance().getTtViewPicture() + this.userInfo.getAvatar());
        TomatoLiveSDK.getSingleton().onUpdateUserNickName(this, this.userInfo.getName());
        TomatoLiveSDK.getSingleton().onUpdateUserSex(this, this.userInfo.getSex());
        finish();
    }

    private void openPhoto() {
        new RxPermissions(this).request("android.permission.CAMERA").subscribe(new Observer<Boolean>() { // from class: com.one.tomato.ui.mine.PersonInfoActivity.1
            @Override // io.reactivex.Observer
            public void onComplete() {
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable th) {
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
            }

            @Override // io.reactivex.Observer
            public void onNext(Boolean bool) {
                if (bool.booleanValue()) {
                    CustomAlertDialog customAlertDialog = new CustomAlertDialog(PersonInfoActivity.this);
                    customAlertDialog.bottomButtonVisiblity(2);
                    View inflate = LayoutInflater.from(PersonInfoActivity.this).inflate(R.layout.dialog_modify_head_tip, (ViewGroup) null);
                    customAlertDialog.setContentView(inflate);
                    customAlertDialog.setConfirmButton(R.string.common_confirm);
                    ImageLoaderUtil.loadNormalDrawableGif(PersonInfoActivity.this, (ImageView) inflate.findViewById(R.id.imageView), R.drawable.modify_head_tip);
                    customAlertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.one.tomato.ui.mine.PersonInfoActivity.1.1
                        @Override // android.content.DialogInterface.OnDismissListener
                        public void onDismiss(DialogInterface dialogInterface) {
                            PersonInfoActivity.this.selectPicTypeUtil.selectCommonPhoto(1, true, false, true, PersonInfoActivity.this.selectList);
                        }
                    });
                    return;
                }
                ToastUtil.showCenterToast((int) R.string.permission_camera);
            }
        });
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private void pickOption(final String str) {
        char c;
        String string;
        this.optionList.clear();
        switch (str.hashCode()) {
            case -1990458108:
                if (str.equals("social_love")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 113766:
                if (str.equals("sex")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 103666725:
                if (str.equals("marry")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 1529844651:
                if (str.equals("sex_love")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        if (c == 0) {
            this.optionList.add(AppUtil.getString(R.string.modify_sex_select_man));
            this.optionList.add(AppUtil.getString(R.string.modify_sex_select_woman));
            string = AppUtil.getString(R.string.modify_sex_select);
        } else if (c == 1) {
            this.optionList.add(AppUtil.getString(R.string.modify_sex_select_man));
            this.optionList.add(AppUtil.getString(R.string.modify_sex_select_woman));
            this.optionList.add(AppUtil.getString(R.string.modify_sex_love_select_all));
            string = AppUtil.getString(R.string.modify_sex_love_select);
        } else if (c == 2) {
            this.optionList.add(AppUtil.getString(R.string.modify_marry_select_single));
            this.optionList.add(AppUtil.getString(R.string.modify_marry_select_double));
            this.optionList.add(AppUtil.getString(R.string.modify_marry_select_marry));
            this.optionList.add(AppUtil.getString(R.string.modify_marry_select_marry_divide));
            this.optionList.add(AppUtil.getString(R.string.modify_marry_select_marry_dead));
            string = AppUtil.getString(R.string.modify_marry_select);
        } else if (c != 3) {
            string = "";
        } else {
            this.optionList.add(AppUtil.getString(R.string.modify_social_love_select_rao));
            this.optionList.add(AppUtil.getString(R.string.modify_social_love_select_liao));
            this.optionList.add(AppUtil.getString(R.string.modify_social_love_select_xin));
            this.optionList.add(AppUtil.getString(R.string.modify_social_love_select_shen));
            string = AppUtil.getString(R.string.modify_social_love_select);
        }
        PickerOptions pickerOptions = new PickerOptions(1);
        pickerOptions.context = this;
        pickerOptions.textContentCancel = string;
        pickerOptions.textColorCancel = getResources().getColor(R.color.text_dark);
        pickerOptions.textColorConfirm = getResources().getColor(R.color.colorAccent);
        pickerOptions.bgColorTitle = getResources().getColor(R.color.white);
        pickerOptions.textSizeSubmitCancel = 16;
        pickerOptions.textSizeContent = 24;
        pickerOptions.lineSpacingMultiplier = 2.0f;
        pickerOptions.optionsSelectListener = new OnOptionsSelectListener() { // from class: com.one.tomato.ui.mine.PersonInfoActivity.3
            /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
            @Override // com.bigkoo.pickerview.listener.OnOptionsSelectListener
            public void onOptionsSelect(int i, int i2, int i3, View view) {
                char c2;
                PersonInfoActivity personInfoActivity = PersonInfoActivity.this;
                personInfoActivity.personInfoOptionType = (i + 1) + "";
                String str2 = str;
                switch (str2.hashCode()) {
                    case -1990458108:
                        if (str2.equals("social_love")) {
                            c2 = 3;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 113766:
                        if (str2.equals("sex")) {
                            c2 = 0;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 103666725:
                        if (str2.equals("marry")) {
                            c2 = 2;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 1529844651:
                        if (str2.equals("sex_love")) {
                            c2 = 1;
                            break;
                        }
                        c2 = 65535;
                        break;
                    default:
                        c2 = 65535;
                        break;
                }
                if (PersonInfoActivity.this.personInfoOptionType.equals(c2 != 0 ? c2 != 1 ? c2 != 2 ? c2 != 3 ? "" : PersonInfoActivity.this.userInfo.getFriendshipIntention() : PersonInfoActivity.this.userInfo.getMarriage() : PersonInfoActivity.this.userInfo.getSexualOrientation() : PersonInfoActivity.this.userInfo.getSex())) {
                    return;
                }
                PersonInfoActivity personInfoActivity2 = PersonInfoActivity.this;
                String str3 = str;
                personInfoActivity2.updateInfo(str3, PersonInfoActivity.this.personInfoOptionType + "");
            }
        };
        OptionsPickerView optionsPickerView = new OptionsPickerView(pickerOptions);
        optionsPickerView.setPicker(this.optionList);
        optionsPickerView.show();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == -1) {
            if (i == 188) {
                this.selectList = PictureSelector.obtainMultipleResult(intent);
                for (LocalMedia localMedia : this.selectList) {
                    Log.i("图片-----》", localMedia.getPath());
                }
                uploadAvatar();
                return;
            }
            switch (i) {
                case 24:
                    updateInfo("nick", intent.getExtras().getString("nick"));
                    return;
                case 25:
                    updateInfo("signature", intent.getExtras().getString("signature"));
                    return;
                case 26:
                    this.locationCountry = intent.getExtras().getString(AopConstants.COUNTRY);
                    this.locationProvince = intent.getExtras().getString(AopConstants.PROVINCE);
                    this.locationCity = intent.getExtras().getString(AopConstants.CITY);
                    updateInfo("location", "");
                    return;
                default:
                    return;
            }
        }
    }

    private void uploadAvatar() {
        this.ttUtil = new TTUtil(2, new TTUtil.UploadFileToTTListener() { // from class: com.one.tomato.ui.mine.PersonInfoActivity.4
            @Override // com.one.tomato.utils.TTUtil.UploadFileToTTListener
            public void start() {
                PersonInfoActivity.this.showWaitingDialog();
                PersonInfoActivity personInfoActivity = PersonInfoActivity.this;
                personInfoActivity.tempLocalImg = ((LocalMedia) personInfoActivity.selectList.get(0)).getPath();
            }

            @Override // com.one.tomato.utils.TTUtil.UploadFileToTTListener
            public void uploadSuccess(LocalMedia localMedia) {
                if (PersonInfoActivity.this.isDestroyed()) {
                    return;
                }
                PersonInfoActivity.this.hideWaitingDialog();
                PersonInfoActivity.this.updateInfo("head", "/" + PersonInfoActivity.this.ttUtil.getBucketName() + "/" + PersonInfoActivity.this.ttUtil.getCeph(localMedia));
            }

            @Override // com.one.tomato.utils.TTUtil.UploadFileToTTListener
            public void uploadFail() {
                if (PersonInfoActivity.this.isDestroyed()) {
                    return;
                }
                PersonInfoActivity.this.hideWaitingDialog();
                PersonInfoActivity.this.tempLocalImg = null;
                ToastUtil.showCenterToast((int) R.string.common_upload_img_fail);
            }
        });
        this.ttUtil.getStsToken(this.selectList);
    }

    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onBackPressed() {
        if ("mine".equals(this.typeFrom)) {
            super.onBackPressed();
        }
    }
}
