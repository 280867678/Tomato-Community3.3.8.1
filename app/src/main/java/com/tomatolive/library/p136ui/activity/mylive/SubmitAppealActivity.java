package com.tomatolive.library.p136ui.activity.mylive;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.gyf.barlibrary.ImmersionBar;
import com.tomatolive.library.R$array;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.base.BaseActivity;
import com.tomatolive.library.model.MenuEntity;
import com.tomatolive.library.model.UploadFileEntity;
import com.tomatolive.library.p136ui.activity.mylive.SubmitAppealActivity;
import com.tomatolive.library.p136ui.interfaces.impl.UploadImageActionListener;
import com.tomatolive.library.p136ui.presenter.SubmitAppealPresenter;
import com.tomatolive.library.p136ui.view.dialog.LoadingDialog;
import com.tomatolive.library.p136ui.view.dialog.base.BaseDialogBuilder;
import com.tomatolive.library.p136ui.view.iview.ISubmitAppealView;
import com.tomatolive.library.p136ui.view.widget.ActionSheetView;
import com.tomatolive.library.p136ui.view.widget.matisse.Matisse;
import com.tomatolive.library.p136ui.view.widget.matisse.internal.entity.CaptureStrategy;
import com.tomatolive.library.p136ui.view.widget.matisse.internal.utils.MediaStoreCompat;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.DateUtils;
import com.tomatolive.library.utils.GlideUtils;
import com.tomatolive.library.utils.RxViewUtils;
import com.yalantis.ucrop.UCrop;
import java.io.File;
import java.util.ArrayList;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/* renamed from: com.tomatolive.library.ui.activity.mylive.SubmitAppealActivity */
/* loaded from: classes3.dex */
public class SubmitAppealActivity extends BaseActivity<SubmitAppealPresenter> implements ISubmitAppealView {
    private String liveDrawRecordId;
    private LoadingDialog loadingDialog;
    private EditText mEtAppealContent;
    private TextView mTextLength;
    private View mTvSubmit;
    private MediaStoreCompat mediaStoreCompat;
    private String userWinningRecordId;
    private String appealDesc = "";
    private File[] tempFile = new File[3];
    private File[] upFile = new File[3];
    private String[] imgUrl = {"", "", ""};
    private int currentIndex = -1;
    private String parentImgPath = "";
    private ImageView[] mImageView = new ImageView[3];

    /* JADX INFO: Access modifiers changed from: private */
    public void setSubmitAble() {
    }

    @Override // android.view.Window.Callback
    public void onPointerCaptureChanged(boolean z) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.base.BaseActivity
    /* renamed from: createPresenter  reason: collision with other method in class */
    public SubmitAppealPresenter mo6636createPresenter() {
        return new SubmitAppealPresenter(this.mContext, this);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    protected int getLayoutId() {
        return R$layout.fq_activity_appeal_detail;
    }

    @Override // com.tomatolive.library.base.BaseActivity
    protected View injectStateView() {
        return findViewById(R$id.fl_content_view);
    }

    private String getDrawTypeText(String str) {
        if ("1".equals(str)) {
            return getString(R$string.fq_hd_gift_lottery);
        }
        return getString(R$string.fq_hd_gift_lottery);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initView(Bundle bundle) {
        setActivityTitle(R$string.fq_hd_appeal_center);
        Intent intent = getIntent();
        String stringExtra = intent.getStringExtra(ConstantUtils.KEY_ANCHOR_NAME);
        String stringExtra2 = intent.getStringExtra(ConstantUtils.KEY_ANCHOR_ID);
        String stringExtra3 = intent.getStringExtra(ConstantUtils.KEY_WINNING_TIME);
        String stringExtra4 = intent.getStringExtra(ConstantUtils.KEY_DRAW_TYPE);
        String stringExtra5 = intent.getStringExtra(ConstantUtils.KEY_LIVE_ID);
        String stringExtra6 = intent.getStringExtra(ConstantUtils.KEY_PRIZE_NAME);
        this.userWinningRecordId = intent.getStringExtra(ConstantUtils.KEY_RECORD_ID);
        this.liveDrawRecordId = intent.getStringExtra(ConstantUtils.KEY_DRAW_ID);
        ((TextView) findViewById(R$id.tv_prize_name)).setText(stringExtra6);
        ((TextView) findViewById(R$id.tv_live_name)).setText(stringExtra5);
        ((TextView) findViewById(R$id.tv_winning_time)).setText(DateUtils.getTimeStrFromLongSecond(stringExtra3, DateUtils.C_TIME_PATTON_DEFAULT_2));
        ((TextView) findViewById(R$id.tv_live_draw_type)).setText(getDrawTypeText(stringExtra4));
        ((TextView) findViewById(R$id.tv_user_name)).setText(getString(R$string.fq_hd_name_id, new Object[]{stringExtra, stringExtra2}));
        findViewById(R$id.ll_appeal_status).setVisibility(8);
        findViewById(R$id.tv_appeal_desc).setVisibility(8);
        this.mEtAppealContent = (EditText) findViewById(R$id.et_appeal_content);
        this.mTextLength = (TextView) findViewById(R$id.tv_current_max_length);
        this.mTextLength.setText(getString(R$string.fq_hd_current_max_length, new Object[]{Integer.valueOf(this.mEtAppealContent.length())}));
        ((TextView) findViewById(R$id.tv_upload_label)).setText(R$string.fq_hd_appeal_image2);
        this.mTvSubmit = findViewById(R$id.tv_submit_appeal);
        this.mImageView[0] = (ImageView) findViewById(R$id.iv_appeal_img0);
        this.mImageView[1] = (ImageView) findViewById(R$id.iv_appeal_img1);
        this.mImageView[2] = (ImageView) findViewById(R$id.iv_appeal_img2);
        this.mImageView[0].setVisibility(0);
        this.mImageView[0].setImageResource(R$drawable.fq_ic_hd_add);
        this.mImageView[1].setVisibility(8);
        this.mImageView[1].setImageResource(R$drawable.fq_ic_hd_add);
        this.mImageView[2].setVisibility(8);
        this.mImageView[2].setImageResource(R$drawable.fq_ic_hd_add);
        initFile();
        if (this.loadingDialog == null) {
            this.loadingDialog = new LoadingDialog(this.mContext, getString(R$string.hint_text_dialog_loading_data));
        }
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initListener() {
        super.initListener();
        RxViewUtils.getInstance().throttleFirst(this.mImageView[0], 800, new RxViewUtils.RxViewAction() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$SubmitAppealActivity$kwv7YYZ7jXCxnPl-sBf4cRijUYY
            @Override // com.tomatolive.library.utils.RxViewUtils.RxViewAction
            public final void action(Object obj) {
                SubmitAppealActivity.this.lambda$initListener$0$SubmitAppealActivity(obj);
            }
        });
        RxViewUtils.getInstance().throttleFirst(this.mTvSubmit, 800, new RxViewUtils.RxViewAction() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$SubmitAppealActivity$tjoDOzL23JiL3tkMoltL5-TavPw
            @Override // com.tomatolive.library.utils.RxViewUtils.RxViewAction
            public final void action(Object obj) {
                SubmitAppealActivity.this.lambda$initListener$2$SubmitAppealActivity(obj);
            }
        });
        this.mEtAppealContent.addTextChangedListener(new TextWatcher() { // from class: com.tomatolive.library.ui.activity.mylive.SubmitAppealActivity.1
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                TextView textView = SubmitAppealActivity.this.mTextLength;
                SubmitAppealActivity submitAppealActivity = SubmitAppealActivity.this;
                textView.setText(submitAppealActivity.getString(R$string.fq_hd_current_max_length, new Object[]{Integer.valueOf(submitAppealActivity.mEtAppealContent.length())}));
            }
        });
    }

    public /* synthetic */ void lambda$initListener$0$SubmitAppealActivity(Object obj) {
        this.currentIndex = 0;
        showTakePhotoDialog(new UploadImageActionListener(this, this.mediaStoreCompat));
    }

    public /* synthetic */ void lambda$initListener$2$SubmitAppealActivity(Object obj) {
        this.appealDesc = this.mEtAppealContent.getText().toString();
        if (TextUtils.isEmpty(this.appealDesc)) {
            showToast(R$string.fq_hd_appeal_content_can_not_be_empty);
        } else if (!isUploadImg()) {
            showToast(R$string.fq_hd_no_image_empty_tips);
        } else {
            new BaseDialogBuilder().setTitleRes(R$string.fq_hd_submit_appeal).setContentRes(R$string.fq_hd_submit_appeal_content).setPositiveBtnRes(R$string.fq_hd_sure_submit).setPositiveBtnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$SubmitAppealActivity$BXUT4vvhXlZ5OB_IOexRNJvQMJg
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SubmitAppealActivity.this.lambda$null$1$SubmitAppealActivity(view);
                }
            }).build().show(getSupportFragmentManager());
        }
    }

    public /* synthetic */ void lambda$null$1$SubmitAppealActivity(View view) {
        commitInfo();
    }

    private void commitInfo() {
        File[] fileArr = this.upFile;
        if (fileArr[0] != null && fileArr[0].exists()) {
            LoadingDialog loadingDialog = this.loadingDialog;
            if (loadingDialog != null) {
                loadingDialog.show();
            }
            ((SubmitAppealPresenter) this.mPresenter).onUpload(this.upFile[0].getAbsolutePath(), 0);
            return;
        }
        sendRequest();
    }

    private void sendRequest() {
        this.appealDesc = this.mEtAppealContent.getText().toString();
        if (this.appealDesc.isEmpty()) {
            dismissDialog();
            showToast(R$string.fq_hd_appeal_content_can_not_be_empty);
            return;
        }
        SubmitAppealPresenter submitAppealPresenter = (SubmitAppealPresenter) this.mPresenter;
        String str = this.liveDrawRecordId;
        String str2 = this.userWinningRecordId;
        String str3 = this.appealDesc;
        String[] strArr = this.imgUrl;
        submitAppealPresenter.submitAppeal(str, str2, str3, strArr[0], strArr[1], strArr[2], true);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.ISubmitAppealView
    public void onSubmitAppealSuccess() {
        dismissDialog();
        showToast(R$string.fq_hd_submit_appeal_success);
        finish();
    }

    @Override // com.tomatolive.library.p136ui.view.iview.ISubmitAppealView
    public void onSubmitAppealFailure(int i, String str) {
        dismissDialog();
    }

    @Override // com.tomatolive.library.base.BaseView
    public void onResultError(int i) {
        runOnUiThread(new Runnable() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$SubmitAppealActivity$5TYos6LAVxHnIAuOa9Bze1RRY24
            @Override // java.lang.Runnable
            public final void run() {
                SubmitAppealActivity.this.dismissDialog();
            }
        });
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar immersionBar = this.mImmersionBar;
        immersionBar.keyboardEnable(true);
        immersionBar.init();
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
        UCrop.m232of(uri, Uri.fromFile(this.tempFile[this.currentIndex])).withAspectRatio(1.0f, 1.0f).withMaxResultSize(1080, 1080).start(this.mActivity);
    }

    private void initFile() {
        this.mediaStoreCompat = new MediaStoreCompat(this.mActivity);
        this.mediaStoreCompat.setCaptureStrategy(new CaptureStrategy(true, getAuthority()));
        this.parentImgPath = AppUtils.getUploadPicPath();
        int i = 0;
        while (true) {
            File[] fileArr = this.tempFile;
            if (i < fileArr.length) {
                String str = this.parentImgPath;
                fileArr[i] = new File(str, "tempImg" + i + ".jpg");
                i++;
            } else {
                return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showTakePhotoDialog(ActionSheetView.ActionSheetOperateListener actionSheetOperateListener) {
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
        if (i2 != -1) {
            return;
        }
        if (i == 69) {
            picPress(this.tempFile[this.currentIndex]);
        } else if (i == 908) {
            onCameraActivityResult();
        } else if (i != 909) {
        } else {
            onAlbumActivityResult(intent);
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

    private void picPress(File file) {
        if (file == null || !file.exists()) {
            showToast(R$string.fq_pic_compress_fail);
            return;
        }
        try {
            Luban.Builder with = Luban.with(this);
            with.load(file);
            with.ignoreBy(50);
            with.setTargetDir(this.parentImgPath);
            with.setCompressListener(new C40692());
            with.launch();
        } catch (Exception e) {
            e.printStackTrace();
            showToast(R$string.fq_pic_compress_fail);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.tomatolive.library.ui.activity.mylive.SubmitAppealActivity$2 */
    /* loaded from: classes3.dex */
    public class C40692 implements OnCompressListener {
        @Override // top.zibin.luban.OnCompressListener
        public void onStart() {
        }

        C40692() {
        }

        @Override // top.zibin.luban.OnCompressListener
        public void onSuccess(File file) {
            SubmitAppealActivity.this.upFile[SubmitAppealActivity.this.currentIndex] = file;
            GlideUtils.loadRoundCornersImage(((BaseActivity) SubmitAppealActivity.this).mContext, SubmitAppealActivity.this.mImageView[SubmitAppealActivity.this.currentIndex], file, 4);
            if (SubmitAppealActivity.this.currentIndex == 0 || SubmitAppealActivity.this.currentIndex == 1) {
                final int i = SubmitAppealActivity.this.currentIndex + 1;
                ImageView imageView = SubmitAppealActivity.this.mImageView[i];
                imageView.setVisibility(0);
                RxViewUtils.getInstance().throttleFirst(imageView, 800, new RxViewUtils.RxViewAction() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$SubmitAppealActivity$2$SPntOxLkrKAlFiAlF-oeqc4PMY8
                    @Override // com.tomatolive.library.utils.RxViewUtils.RxViewAction
                    public final void action(Object obj) {
                        SubmitAppealActivity.C40692.this.lambda$onSuccess$0$SubmitAppealActivity$2(i, obj);
                    }
                });
            }
            SubmitAppealActivity.this.setSubmitAble();
        }

        public /* synthetic */ void lambda$onSuccess$0$SubmitAppealActivity$2(int i, Object obj) {
            SubmitAppealActivity.this.currentIndex = i;
            SubmitAppealActivity submitAppealActivity = SubmitAppealActivity.this;
            submitAppealActivity.showTakePhotoDialog(new UploadImageActionListener(submitAppealActivity, submitAppealActivity.mediaStoreCompat));
        }

        @Override // top.zibin.luban.OnCompressListener
        public void onError(Throwable th) {
            SubmitAppealActivity.this.showToast(R$string.fq_pic_compress_fail);
        }
    }

    @Override // com.tomatolive.library.p136ui.view.iview.ISubmitAppealView
    public void onUploadSuc(UploadFileEntity uploadFileEntity, int i) {
        this.imgUrl[i] = uploadFileEntity.getFilename();
        int i2 = i + 1;
        File[] fileArr = this.upFile;
        if (i2 >= fileArr.length || fileArr[i2] == null) {
            sendRequest();
        } else if (fileArr[i2] == null || !fileArr[i2].exists()) {
        } else {
            ((SubmitAppealPresenter) this.mPresenter).onUpload(this.upFile[i2].getAbsolutePath(), i2);
        }
    }

    @Override // com.tomatolive.library.p136ui.view.iview.ISubmitAppealView
    public void onUploadFail() {
        showToast(R$string.fq_pic_upload_fail);
        dismissDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dismissDialog() {
        LoadingDialog loadingDialog = this.loadingDialog;
        if (loadingDialog == null || !loadingDialog.isShowing()) {
            return;
        }
        this.loadingDialog.dismiss();
    }

    private boolean isUploadImg() {
        File[] fileArr = this.upFile;
        return fileArr[0] != null && fileArr[0].exists();
    }
}
