package com.tomatolive.library.p136ui.activity.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.p002v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.gyf.barlibrary.ImmersionBar;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.tomatolive.library.R$array;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.base.BaseActivity;
import com.tomatolive.library.model.CountryCodeEntity;
import com.tomatolive.library.model.MenuEntity;
import com.tomatolive.library.model.UploadFileEntity;
import com.tomatolive.library.p136ui.interfaces.impl.UploadImageActionListener;
import com.tomatolive.library.p136ui.presenter.AnchorAuthPresenter;
import com.tomatolive.library.p136ui.view.dialog.BottomDialogUtils;
import com.tomatolive.library.p136ui.view.dialog.CommonRuleTipsDialog;
import com.tomatolive.library.p136ui.view.dialog.LoadingDialog;
import com.tomatolive.library.p136ui.view.iview.IAnchorAuthView;
import com.tomatolive.library.p136ui.view.widget.ActionSheetView;
import com.tomatolive.library.p136ui.view.widget.matisse.Matisse;
import com.tomatolive.library.p136ui.view.widget.matisse.internal.entity.CaptureStrategy;
import com.tomatolive.library.p136ui.view.widget.matisse.internal.utils.MediaStoreCompat;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.FileUtils;
import com.tomatolive.library.utils.GlideUtils;
import com.tomatolive.library.utils.LogEventUtils;
import com.tomatolive.library.utils.StringUtils;
import com.tomatolive.library.utils.emoji.EmojiParser;
import com.tomatolive.library.utils.live.SimpleRxObserver;
import com.yalantis.ucrop.UCrop;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/* renamed from: com.tomatolive.library.ui.activity.home.AnchorAuthActivity */
/* loaded from: classes3.dex */
public class AnchorAuthActivity extends BaseActivity<AnchorAuthPresenter> implements IAnchorAuthView, View.OnClickListener {
    private List<CountryCodeEntity> countryCodeList;
    private EditText etCode;
    private EditText etIdCard;
    private EditText etName;
    private EditText etPhone;
    private FrameLayout flIdCardBack;
    private FrameLayout flIdCardFront;
    private LoadingDialog loadingDialog;
    private ImageView mIdCardBack;
    private ImageView mIdCardFront;
    private MediaStoreCompat mediaStoreCompat;
    private File tempBackImgFile;
    private File tempFrontImgFile;
    private TextView tvCountryCode;
    private TextView tvSendCode;
    private TextView tvSubmit;
    private File upBackFile;
    private File upFrontFile;
    private final int timeCount = 60;
    private boolean isFront = true;
    private String idCardFront = "";
    private String parentImgPath = "";
    private String countryCode = "86";

    @Override // com.tomatolive.library.base.BaseActivity
    /* renamed from: createPresenter */
    public AnchorAuthPresenter mo6636createPresenter() {
        return new AnchorAuthPresenter(this.mContext, this);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    protected int getLayoutId() {
        return R$layout.fq_activity_anchor_identy;
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar immersionBar = this.mImmersionBar;
        immersionBar.keyboardEnable(true);
        immersionBar.init();
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initView(Bundle bundle) {
        setActivityTitle(R$string.fq_anchor_identy);
        this.mIdCardBack = (ImageView) findViewById(R$id.iv_up_idcrad_back);
        this.mIdCardFront = (ImageView) findViewById(R$id.iv_up_idcrad_front);
        this.etName = (EditText) findViewById(R$id.edit_name);
        this.etIdCard = (EditText) findViewById(R$id.edit_idcard);
        this.etPhone = (EditText) findViewById(R$id.edit_phone);
        this.etCode = (EditText) findViewById(R$id.edit_code);
        this.tvSendCode = (TextView) findViewById(R$id.tv_send_code);
        this.tvSubmit = (TextView) findViewById(R$id.tv_commit_info);
        this.tvCountryCode = (TextView) findViewById(R$id.tv_country_code);
        this.flIdCardFront = (FrameLayout) findViewById(R$id.fl_id_card_front);
        this.flIdCardBack = (FrameLayout) findViewById(R$id.fl_id_card_back);
        initFile();
        if (this.loadingDialog == null) {
            this.loadingDialog = new LoadingDialog(this.mContext, getString(R$string.hint_text_dialog_loading_data));
        }
        CommonRuleTipsDialog.newInstance(ConstantUtils.APP_PARAM_AUTH_PROMPT, getString(R$string.fq_contact_warm_tips), true, 0.32d).show(getSupportFragmentManager());
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initListener() {
        super.initListener();
        this.mIdCardFront.setOnClickListener(this);
        this.mIdCardBack.setOnClickListener(this);
        this.tvSendCode.setOnClickListener(this);
        this.flIdCardFront.setOnClickListener(this);
        this.flIdCardBack.setOnClickListener(this);
        RxView.clicks(this.tvSubmit).throttleFirst(3L, TimeUnit.SECONDS).subscribe(new SimpleRxObserver<Object>() { // from class: com.tomatolive.library.ui.activity.home.AnchorAuthActivity.1
            {
                AnchorAuthActivity.this = this;
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(Object obj) {
                AnchorAuthActivity.this.commitInfo();
            }
        });
        Observable.combineLatest(RxTextView.textChanges(this.etName), RxTextView.textChanges(this.etIdCard), RxTextView.textChanges(this.etPhone), RxTextView.textChanges(this.etCode), $$Lambda$AnchorAuthActivity$Wz5L6b07Fg7Nsn4R61bapoWel3s.INSTANCE).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new SimpleRxObserver<Boolean>() { // from class: com.tomatolive.library.ui.activity.home.AnchorAuthActivity.2
            {
                AnchorAuthActivity.this = this;
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(Boolean bool) {
                if (AnchorAuthActivity.this.upFrontFile == null || !AnchorAuthActivity.this.upFrontFile.exists() || AnchorAuthActivity.this.upBackFile == null || !AnchorAuthActivity.this.upBackFile.exists()) {
                    return;
                }
                AnchorAuthActivity.this.tvSubmit.setEnabled(bool.booleanValue());
            }
        });
        RxTextView.textChanges(this.etPhone).map($$Lambda$AnchorAuthActivity$py4DyWdb0ufGTHvk_BFp8n0DBk.INSTANCE).compose(bindToLifecycle()).subscribe(new Observer<Boolean>() { // from class: com.tomatolive.library.ui.activity.home.AnchorAuthActivity.3
            @Override // io.reactivex.Observer
            public void onComplete() {
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable th) {
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
            }

            {
                AnchorAuthActivity.this = this;
            }

            @Override // io.reactivex.Observer
            public void onNext(Boolean bool) {
                AnchorAuthActivity.this.tvSendCode.setEnabled(bool.booleanValue());
            }
        });
        this.tvCountryCode.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.home.-$$Lambda$AnchorAuthActivity$1SHbz4QwEQeRd8NVqogQmxsMZB0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AnchorAuthActivity.this.lambda$initListener$2$AnchorAuthActivity(view);
            }
        });
    }

    public /* synthetic */ void lambda$initListener$2$AnchorAuthActivity(View view) {
        initTvCountryCodeDrawable(true);
        List<CountryCodeEntity> list = this.countryCodeList;
        if (list != null && list.size() > 0) {
            showPhoneCountryCodeDialog(this.countryCodeList);
        } else {
            ((AnchorAuthPresenter) this.mPresenter).onCountryCode();
        }
    }

    private void sendPhoneCode() {
        Observable.interval(0L, 1L, TimeUnit.SECONDS).take(61L).map(new Function() { // from class: com.tomatolive.library.ui.activity.home.-$$Lambda$AnchorAuthActivity$3Kgu7S-4H2vhcrgbA9T2oGBEz64
            @Override // io.reactivex.functions.Function
            /* renamed from: apply */
            public final Object mo6755apply(Object obj) {
                return AnchorAuthActivity.this.lambda$sendPhoneCode$3$AnchorAuthActivity((Long) obj);
            }
        }).observeOn(AndroidSchedulers.mainThread()).doOnSubscribe(new Consumer() { // from class: com.tomatolive.library.ui.activity.home.-$$Lambda$AnchorAuthActivity$HhwDuEE52NK8_hCF2vVq5UAyQSQ
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                AnchorAuthActivity.this.lambda$sendPhoneCode$4$AnchorAuthActivity((Disposable) obj);
            }
        }).compose(bindToLifecycle()).subscribe(new Observer<Long>() { // from class: com.tomatolive.library.ui.activity.home.AnchorAuthActivity.4
            @Override // io.reactivex.Observer
            public void onError(Throwable th) {
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
            }

            {
                AnchorAuthActivity.this = this;
            }

            @Override // io.reactivex.Observer
            public void onNext(Long l) {
                AnchorAuthActivity.this.tvSendCode.setText(AnchorAuthActivity.this.getString(R$string.fq_second, new Object[]{String.valueOf(l)}));
            }

            @Override // io.reactivex.Observer
            public void onComplete() {
                AnchorAuthActivity.this.tvSendCode.setText(R$string.fq_get_code);
                AnchorAuthActivity.this.tvSendCode.setEnabled(true);
            }
        });
    }

    public /* synthetic */ Long lambda$sendPhoneCode$3$AnchorAuthActivity(Long l) throws Exception {
        return Long.valueOf(60 - l.longValue());
    }

    public /* synthetic */ void lambda$sendPhoneCode$4$AnchorAuthActivity(Disposable disposable) throws Exception {
        showToast(R$string.fq_code_send_suc);
        this.tvSendCode.setEnabled(false);
    }

    private String getAuthority() {
        return this.mContext.getPackageName() + ".provider";
    }

    public void onCameraActivityResult() {
        Uri currentPhotoUri = this.mediaStoreCompat.getCurrentPhotoUri();
        if (currentPhotoUri == null) {
            showToast(R$string.fq_pic_take_fail);
        } else {
            picCrop(currentPhotoUri);
        }
    }

    private void picCrop(@NonNull Uri uri) {
        UCrop.m232of(uri, Uri.fromFile(this.isFront ? this.tempFrontImgFile : this.tempBackImgFile)).withAspectRatio(1.0f, 1.0f).withMaxResultSize(1080, 1080).start(this.mActivity);
    }

    private void initFile() {
        this.mediaStoreCompat = new MediaStoreCompat(this.mActivity);
        this.mediaStoreCompat.setCaptureStrategy(new CaptureStrategy(true, getAuthority()));
        this.parentImgPath = AppUtils.getUploadPicPath();
        this.tempFrontImgFile = new File(this.parentImgPath, "tempFrontImg.jpg");
        this.tempBackImgFile = new File(this.parentImgPath, "tempBackImg.jpg");
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R$id.iv_up_idcrad_front || id == R$id.fl_id_card_front) {
            this.isFront = true;
            showTakePhotoDialog(new UploadImageActionListener(this, this.mediaStoreCompat));
        } else if (id == R$id.iv_up_idcrad_back || id == R$id.fl_id_card_back) {
            this.isFront = false;
            showTakePhotoDialog(new UploadImageActionListener(this, this.mediaStoreCompat));
        } else if (id != R$id.tv_send_code) {
        } else {
            if (TextUtils.isEmpty(this.countryCode)) {
                showToast(R$string.fq_choose_phone_encode);
                return;
            }
            ((AnchorAuthPresenter) this.mPresenter).onSendPhoneCode(StringUtils.formatPhoneRemoveSpaces(this.etPhone.getText().toString().trim()), this.countryCode);
        }
    }

    public void commitInfo() {
        File file;
        File file2 = this.upFrontFile;
        if (file2 == null || !file2.exists() || (file = this.upBackFile) == null || !file.exists()) {
            return;
        }
        LoadingDialog loadingDialog = this.loadingDialog;
        if (loadingDialog != null) {
            loadingDialog.show();
        }
        ((AnchorAuthPresenter) this.mPresenter).onUpload(this.upFrontFile.getAbsolutePath(), true);
    }

    private void showTakePhotoDialog(ActionSheetView.ActionSheetOperateListener actionSheetOperateListener) {
        String[] stringArray = this.mContext.getResources().getStringArray(R$array.take_photo_text);
        ArrayList arrayList = new ArrayList();
        for (String str : stringArray) {
            arrayList.add(new MenuEntity(str));
        }
        ActionSheetView.showOperateCancelDialog(this.mContext, arrayList, actionSheetOperateListener);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == -1) {
            if (i == 69) {
                if (i2 == -1) {
                    picPress(this.isFront ? this.tempFrontImgFile : this.tempBackImgFile, Boolean.valueOf(this.isFront));
                } else if (i2 != 96) {
                } else {
                    showToast(R$string.fq_pic_cut_fail);
                }
            } else if (i == 908) {
                if (i2 != -1) {
                    return;
                }
                onCameraActivityResult();
            } else if (i != 909 || i2 != -1) {
            } else {
                onAlbumActivityResult(intent);
            }
        }
    }

    public void onAlbumActivityResult(Intent intent) {
        if (intent == null) {
            return;
        }
        if (!isPicUseAge(Matisse.obtainPathResult(intent).get(0))) {
            showToast(R$string.fq_pic_format_error);
            return;
        }
        Uri uri = Matisse.obtainResult(intent).get(0);
        if (uri == null) {
            showToast(R$string.fq_pic_format_error);
        } else {
            picCrop(uri);
        }
    }

    private boolean isPicUseAge(String str) {
        if (str == null || TextUtils.isEmpty(str)) {
            return false;
        }
        String[] split = str.split("\\.");
        String lowerCase = split[split.length - 1].toLowerCase();
        return lowerCase.equals("jpg") || lowerCase.equals("jpeg") || lowerCase.equals("png");
    }

    private void picPress(File file, final Boolean bool) {
        if (file == null || !file.exists()) {
            showToast(R$string.fq_pic_compress_fail);
            return;
        }
        try {
            Luban.Builder with = Luban.with(this);
            with.load(file);
            with.ignoreBy(50);
            with.setTargetDir(this.parentImgPath);
            with.setCompressListener(new OnCompressListener() { // from class: com.tomatolive.library.ui.activity.home.AnchorAuthActivity.5
                @Override // top.zibin.luban.OnCompressListener
                public void onStart() {
                }

                {
                    AnchorAuthActivity.this = this;
                }

                @Override // top.zibin.luban.OnCompressListener
                public void onSuccess(File file2) {
                    if (bool.booleanValue()) {
                        AnchorAuthActivity.this.upFrontFile = file2;
                        AnchorAuthActivity.this.flIdCardFront.setVisibility(4);
                        AnchorAuthActivity.this.mIdCardFront.setVisibility(0);
                        GlideUtils.loadRoundCornersImage(((BaseActivity) AnchorAuthActivity.this).mContext, AnchorAuthActivity.this.mIdCardFront, AnchorAuthActivity.this.upFrontFile, 6);
                        AnchorAuthActivity.this.setSubmitAble();
                        return;
                    }
                    AnchorAuthActivity.this.upBackFile = file2;
                    AnchorAuthActivity.this.flIdCardBack.setVisibility(4);
                    AnchorAuthActivity.this.mIdCardBack.setVisibility(0);
                    GlideUtils.loadRoundCornersImage(((BaseActivity) AnchorAuthActivity.this).mContext, AnchorAuthActivity.this.mIdCardBack, AnchorAuthActivity.this.upBackFile, 6);
                    AnchorAuthActivity.this.setSubmitAble();
                }

                @Override // top.zibin.luban.OnCompressListener
                public void onError(Throwable th) {
                    AnchorAuthActivity.this.showToast(R$string.fq_pic_compress_fail);
                }
            });
            with.launch();
        } catch (Exception e) {
            e.printStackTrace();
            showToast(R$string.fq_pic_compress_fail);
        }
    }

    public void setSubmitAble() {
        File file;
        File file2;
        String trim = this.etName.getText().toString().trim();
        String trim2 = this.etIdCard.getText().toString().trim();
        String formatPhoneRemoveSpaces = StringUtils.formatPhoneRemoveSpaces(this.etPhone.getText().toString().trim());
        String trim3 = this.etCode.getText().toString().trim();
        String removeAllEmojis = EmojiParser.removeAllEmojis(trim);
        String removeAllEmojis2 = EmojiParser.removeAllEmojis(trim2);
        if (!TextUtils.isEmpty(removeAllEmojis) && !TextUtils.isEmpty(removeAllEmojis2) && !TextUtils.isEmpty(formatPhoneRemoveSpaces) && !TextUtils.isEmpty(trim3) && (file = this.upFrontFile) != null && file.exists() && (file2 = this.upBackFile) != null && file2.exists()) {
            this.tvSubmit.setEnabled(true);
        } else {
            this.tvSubmit.setEnabled(false);
        }
    }

    @Override // com.tomatolive.library.base.BaseView
    public void onResultError(int i) {
        LogEventUtils.uploadStartRealNameAuthentication("0");
        runOnUiThread(new $$Lambda$AnchorAuthActivity$YxqwtFwvV2p_hcotJLfiZQol9Q(this));
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IAnchorAuthView
    public void onAuthSuccess() {
        runOnUiThread(new $$Lambda$AnchorAuthActivity$YxqwtFwvV2p_hcotJLfiZQol9Q(this));
        LogEventUtils.uploadStartRealNameAuthentication("1");
        showToast(R$string.fq_submit_suc);
        FileUtils.delAllFile(this.parentImgPath);
        Intent intent = new Intent(this.mContext, AnchorAuthResultActivity.class);
        intent.putExtra(ConstantUtils.AUTH_TYPE, 0);
        startActivity(intent);
        finish();
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IAnchorAuthView
    public void onUploadSuc(UploadFileEntity uploadFileEntity, boolean z) {
        if (z) {
            this.idCardFront = uploadFileEntity.getFilename();
            ((AnchorAuthPresenter) this.mPresenter).onUpload(this.upBackFile.getAbsolutePath(), false);
            return;
        }
        String trim = this.etName.getText().toString().trim();
        String trim2 = this.etIdCard.getText().toString().trim();
        String formatPhoneRemoveSpaces = StringUtils.formatPhoneRemoveSpaces(this.etPhone.getText().toString().trim());
        String trim3 = this.etCode.getText().toString().trim();
        ((AnchorAuthPresenter) this.mPresenter).onAnchorAuth(EmojiParser.removeAllEmojis(trim), EmojiParser.removeAllEmojis(trim2), formatPhoneRemoveSpaces, trim3, this.idCardFront, uploadFileEntity.getFilename(), this.countryCode);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IAnchorAuthView
    public void onUploadFail(boolean z) {
        showToast(R$string.fq_pic_upload_fail);
        dismissDialog();
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IAnchorAuthView
    public void onSendPhoneCodeSuccess() {
        sendPhoneCode();
        LogEventUtils.uploadGetCodeResult(StringUtils.formatPhoneRemoveSpaces(this.etPhone.getText().toString().trim()), 1);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IAnchorAuthView
    public void onSendPhoneCodeFail() {
        LogEventUtils.uploadGetCodeResult(StringUtils.formatPhoneRemoveSpaces(this.etPhone.getText().toString().trim()), 0);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IAnchorAuthView
    public void onCountryPhoneCodeSuccess(List<CountryCodeEntity> list) {
        if (list == null) {
            return;
        }
        this.countryCodeList = list;
        showPhoneCountryCodeDialog(list);
    }

    private void initTvCountryCodeDrawable(boolean z) {
        this.tvCountryCode.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, ContextCompat.getDrawable(this.mContext, z ? R$drawable.fq_ic_code_arrow_up : R$drawable.fq_ic_code_arrow_down), (Drawable) null);
    }

    private void showPhoneCountryCodeDialog(List<CountryCodeEntity> list) {
        BottomDialogUtils.showPhoneCountryCodeDialog(this.mContext, list, new BottomDialogUtils.CountryCodeListener() { // from class: com.tomatolive.library.ui.activity.home.-$$Lambda$AnchorAuthActivity$AJlUFELmCTMs5MFSV0onCZ_qTEE
            @Override // com.tomatolive.library.p136ui.view.dialog.BottomDialogUtils.CountryCodeListener
            public final void onCountryCodeListener(CountryCodeEntity countryCodeEntity, int i) {
                AnchorAuthActivity.this.lambda$showPhoneCountryCodeDialog$5$AnchorAuthActivity(countryCodeEntity, i);
            }
        }).setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.tomatolive.library.ui.activity.home.-$$Lambda$AnchorAuthActivity$Kv3o16KxhmUFr7T08XdtMz4H4IA
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                AnchorAuthActivity.this.lambda$showPhoneCountryCodeDialog$6$AnchorAuthActivity(dialogInterface);
            }
        });
    }

    public /* synthetic */ void lambda$showPhoneCountryCodeDialog$5$AnchorAuthActivity(CountryCodeEntity countryCodeEntity, int i) {
        this.countryCode = countryCodeEntity.countryCode;
        this.tvCountryCode.setText(String.format(getString(R$string.fq_add), countryCodeEntity.countryCode));
    }

    public /* synthetic */ void lambda$showPhoneCountryCodeDialog$6$AnchorAuthActivity(DialogInterface dialogInterface) {
        initTvCountryCodeDrawable(false);
    }

    public void dismissDialog() {
        LoadingDialog loadingDialog = this.loadingDialog;
        if (loadingDialog == null || !loadingDialog.isShowing()) {
            return;
        }
        this.loadingDialog.dismiss();
    }
}
