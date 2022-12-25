package com.tomatolive.library.p136ui.view.custom;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.p002v4.app.FragmentActivity;
import android.support.p005v7.widget.AppCompatCheckBox;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.PathUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jakewharton.rxbinding2.view.RxView;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tomatolive.library.R$array;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.http.ApiRetrofit;
import com.tomatolive.library.http.HttpRxObserver;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.ResultCallBack;
import com.tomatolive.library.http.function.HttpResultFunction;
import com.tomatolive.library.http.function.ServerResultFunction;
import com.tomatolive.library.model.LabelEntity;
import com.tomatolive.library.model.MenuEntity;
import com.tomatolive.library.model.UploadFileEntity;
import com.tomatolive.library.p136ui.activity.home.WebViewActivity;
import com.tomatolive.library.p136ui.adapter.LabelMenuAdapter;
import com.tomatolive.library.p136ui.view.dialog.LabelDialog;
import com.tomatolive.library.p136ui.view.dialog.LoadingDialog;
import com.tomatolive.library.p136ui.view.widget.ActionSheetView;
import com.tomatolive.library.p136ui.view.widget.matisse.Matisse;
import com.tomatolive.library.p136ui.view.widget.matisse.MimeType;
import com.tomatolive.library.p136ui.view.widget.matisse.engine.impl.GlideEngine;
import com.tomatolive.library.p136ui.view.widget.matisse.internal.entity.CaptureStrategy;
import com.tomatolive.library.p136ui.view.widget.matisse.internal.utils.MediaStoreCompat;
import com.tomatolive.library.utils.AnimUtils;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.EmojiFilter;
import com.tomatolive.library.utils.GlideUtils;
import com.tomatolive.library.utils.RxViewUtils;
import com.tomatolive.library.utils.emoji.EmojiParser;
import com.tomatolive.library.utils.live.SimpleRxObserver;
import com.yalantis.ucrop.UCrop;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/* renamed from: com.tomatolive.library.ui.view.custom.PreStartLiveView */
/* loaded from: classes3.dex */
public class PreStartLiveView extends RelativeLayout implements View.OnClickListener {
    private AppCompatCheckBox cbProtocol;
    private EditText etTitle;
    private ImageView ivCover;
    private LabelDialog labelDialog;
    private LabelMenuAdapter labelMenuAdapter;
    private boolean lastMirrorOpen;
    private LoadingDialog loadingDialog;
    private Activity mActivity;
    private Context mContext;
    private MediaStoreCompat mediaStoreCompat;
    private OnPreStartLiveCallback onPreStartLiveCallback;
    private File tempCoverImgFile;
    private TextView tvBeauty;
    private TextView tvCamera;
    private TextView tvChargeType;
    private TextView tvLabel;
    private TextView tvMirror;
    private TextView tvStartLive;

    /* renamed from: com.tomatolive.library.ui.view.custom.PreStartLiveView$OnPreStartLiveCallback */
    /* loaded from: classes3.dex */
    public interface OnPreStartLiveCallback {
        void onCancelUpload();

        void onClickBeautyListener();

        void onClickCameraListener();

        void onClickClosedListener();

        void onClickMirrorListener();

        void onClickPaySetupListener();

        void onClickStartLiveListener(String str, String str2);
    }

    public PreStartLiveView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
        this.mActivity = (Activity) context;
        this.mediaStoreCompat = new MediaStoreCompat(this.mActivity);
        this.mediaStoreCompat.setCaptureStrategy(new CaptureStrategy(true, getAuthority()));
        RelativeLayout.inflate(context, R$layout.fq_layout_pre_start_live_view, this);
        this.ivCover = (ImageView) findViewById(R$id.iv_upload_cover);
        this.etTitle = (EditText) findViewById(R$id.et_live_title);
        this.tvLabel = (TextView) findViewById(R$id.tv_label_sort);
        this.tvCamera = (TextView) findViewById(R$id.tv_camera_icon);
        this.tvBeauty = (TextView) findViewById(R$id.tv_beauty_icon);
        this.tvMirror = (TextView) findViewById(R$id.tv_mirror_icon);
        this.tvStartLive = (TextView) findViewById(R$id.tv_start_live);
        this.tvChargeType = (TextView) findViewById(R$id.tv_pay_type);
        this.cbProtocol = (AppCompatCheckBox) findViewById(R$id.cb_protocol);
        setChargeTypeTips("0");
        createTempFile();
        createLoadingDialog();
        initListener();
    }

    private void createLoadingDialog() {
        if (this.loadingDialog == null) {
            Context context = this.mContext;
            this.loadingDialog = new LoadingDialog(context, context.getString(R$string.fq_uploading_cover));
        }
    }

    private String getAuthority() {
        return this.mContext.getPackageName() + ".provider";
    }

    private void initListener() {
        findViewById(R$id.iv_closed).setOnClickListener(this);
        RxViewUtils.getInstance().throttleFirst(this.ivCover, 500, new RxViewUtils.RxViewAction() { // from class: com.tomatolive.library.ui.view.custom.PreStartLiveView.1
            @Override // com.tomatolive.library.utils.RxViewUtils.RxViewAction
            public void action(Object obj) {
                PreStartLiveView.this.goToUploadCover();
            }
        });
        RxViewUtils.getInstance().throttleFirst(findViewById(R$id.rl_upload_cover), 500, new RxViewUtils.RxViewAction() { // from class: com.tomatolive.library.ui.view.custom.PreStartLiveView.2
            @Override // com.tomatolive.library.utils.RxViewUtils.RxViewAction
            public void action(Object obj) {
                PreStartLiveView.this.goToUploadCover();
            }
        });
        RxViewUtils.getInstance().throttleFirst(this.tvLabel, 500, new RxViewUtils.RxViewAction() { // from class: com.tomatolive.library.ui.view.custom.PreStartLiveView.3
            @Override // com.tomatolive.library.utils.RxViewUtils.RxViewAction
            public void action(Object obj) {
                PreStartLiveView.this.showLabel();
            }
        });
        RxViewUtils.getInstance().throttleFirst(this.tvCamera, 500, new RxViewUtils.RxViewAction() { // from class: com.tomatolive.library.ui.view.custom.PreStartLiveView.4
            @Override // com.tomatolive.library.utils.RxViewUtils.RxViewAction
            public void action(Object obj) {
                if (PreStartLiveView.this.onPreStartLiveCallback != null) {
                    PreStartLiveView.this.onPreStartLiveCallback.onClickCameraListener();
                }
            }
        });
        RxViewUtils.getInstance().throttleFirst(this.tvBeauty, 500, new RxViewUtils.RxViewAction() { // from class: com.tomatolive.library.ui.view.custom.PreStartLiveView.5
            @Override // com.tomatolive.library.utils.RxViewUtils.RxViewAction
            public void action(Object obj) {
                if (PreStartLiveView.this.onPreStartLiveCallback != null) {
                    PreStartLiveView.this.onPreStartLiveCallback.onClickBeautyListener();
                }
            }
        });
        RxViewUtils.getInstance().throttleFirst(this.tvMirror, 500, new RxViewUtils.RxViewAction() { // from class: com.tomatolive.library.ui.view.custom.PreStartLiveView.6
            @Override // com.tomatolive.library.utils.RxViewUtils.RxViewAction
            public void action(Object obj) {
                if (PreStartLiveView.this.onPreStartLiveCallback != null) {
                    PreStartLiveView.this.onPreStartLiveCallback.onClickMirrorListener();
                }
            }
        });
        RxViewUtils.getInstance().throttleFirst(findViewById(R$id.tv_protocol), 500, new RxViewUtils.RxViewAction() { // from class: com.tomatolive.library.ui.view.custom.PreStartLiveView.7
            @Override // com.tomatolive.library.utils.RxViewUtils.RxViewAction
            public void action(Object obj) {
                Intent intent = new Intent(PreStartLiveView.this.mContext, WebViewActivity.class);
                intent.putExtra(ConstantUtils.WEB_VIEW_FROM_SERVICE, true);
                intent.putExtra("url", ConstantUtils.APP_PARAM_LIVE_USER_PROTOCOL);
                intent.putExtra("title", PreStartLiveView.this.mContext.getString(R$string.fq_user_protocol));
                PreStartLiveView.this.mContext.startActivity(intent);
            }
        });
        RxViewUtils.getInstance().throttleFirst(findViewById(R$id.tv_pay_setup), 500, new RxViewUtils.RxViewAction() { // from class: com.tomatolive.library.ui.view.custom.PreStartLiveView.8
            @Override // com.tomatolive.library.utils.RxViewUtils.RxViewAction
            public void action(Object obj) {
                if (PreStartLiveView.this.onPreStartLiveCallback != null) {
                    PreStartLiveView.this.onPreStartLiveCallback.onClickPaySetupListener();
                }
            }
        });
        RxView.clicks(this.tvStartLive).throttleFirst(2L, TimeUnit.SECONDS).subscribe(new SimpleRxObserver<Object>() { // from class: com.tomatolive.library.ui.view.custom.PreStartLiveView.9
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(Object obj) {
                if (PreStartLiveView.this.onPreStartLiveCallback != null) {
                    AnimUtils.playScaleAnim(PreStartLiveView.this.tvStartLive);
                    if (!PreStartLiveView.this.cbProtocol.isChecked()) {
                        PreStartLiveView.this.showToast(R$string.fq_check_law);
                        return;
                    }
                    String trim = PreStartLiveView.this.etTitle.getText().toString().trim();
                    String trim2 = PreStartLiveView.this.tvLabel.getText().toString().trim();
                    if (EmojiFilter.containsEmoji(trim)) {
                        PreStartLiveView.this.showToast(R$string.fq_live_title_no_emoji);
                    } else if (TextUtils.isEmpty(EmojiParser.removeAllEmojis(trim))) {
                        PreStartLiveView.this.showToast(R$string.fq_input_title);
                    } else if (TextUtils.isEmpty(trim2)) {
                        PreStartLiveView.this.showToast(R$string.fq_input_label);
                    } else {
                        SPUtils.getInstance().put(ConstantUtils.LIVE_LAST_TOPIC, trim);
                        PreStartLiveView.this.onPreStartLiveCallback.onClickStartLiveListener(trim, trim2);
                    }
                }
            }
        });
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        OnPreStartLiveCallback onPreStartLiveCallback;
        int id = view.getId();
        if (id == R$id.rl_upload_cover || id == R$id.iv_upload_cover) {
            goToUploadCover();
        } else if (id == R$id.tv_label_sort) {
            showLabel();
        } else if (id == R$id.tv_camera_icon) {
            OnPreStartLiveCallback onPreStartLiveCallback2 = this.onPreStartLiveCallback;
            if (onPreStartLiveCallback2 == null) {
                return;
            }
            onPreStartLiveCallback2.onClickCameraListener();
        } else if (id == R$id.tv_beauty_icon) {
            OnPreStartLiveCallback onPreStartLiveCallback3 = this.onPreStartLiveCallback;
            if (onPreStartLiveCallback3 == null) {
                return;
            }
            onPreStartLiveCallback3.onClickBeautyListener();
        } else if (id == R$id.tv_mirror_icon) {
            OnPreStartLiveCallback onPreStartLiveCallback4 = this.onPreStartLiveCallback;
            if (onPreStartLiveCallback4 == null) {
                return;
            }
            onPreStartLiveCallback4.onClickMirrorListener();
        } else if (id != R$id.iv_closed || (onPreStartLiveCallback = this.onPreStartLiveCallback) == null) {
        } else {
            onPreStartLiveCallback.onClickClosedListener();
        }
    }

    public void setOnPreStartLiveCallback(OnPreStartLiveCallback onPreStartLiveCallback) {
        this.onPreStartLiveCallback = onPreStartLiveCallback;
    }

    public String getLiveTitle() {
        return this.etTitle.getText().toString().trim();
    }

    public String getLiveTag() {
        return this.tvLabel.getText().toString().trim();
    }

    public void setChargeTypeTips(String str) {
        char c;
        String[] stringArray = this.mContext.getResources().getStringArray(R$array.fq_charge_type_menu);
        int hashCode = str.hashCode();
        if (hashCode != 48) {
            if (hashCode == 49 && str.equals("1")) {
                c = 1;
            }
            c = 65535;
        } else {
            if (str.equals("0")) {
                c = 0;
            }
            c = 65535;
        }
        if (c == 0) {
            this.tvChargeType.setText(stringArray[0]);
        } else if (c != 1) {
        } else {
            this.tvChargeType.setText(stringArray[1]);
        }
    }

    public void onTagListSuccess(List<LabelEntity> list) {
        this.labelMenuAdapter = new LabelMenuAdapter(R$layout.fq_item_live_label, list);
        this.labelDialog = LabelDialog.newInstance(this.labelMenuAdapter);
        this.labelMenuAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.tomatolive.library.ui.view.custom.PreStartLiveView.10
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                PreStartLiveView.this.dismissLabelDialog();
                LabelEntity labelEntity = (LabelEntity) baseQuickAdapter.getItem(i);
                PreStartLiveView.this.tvLabel.setText(labelEntity.name);
                PreStartLiveView.this.labelMenuAdapter.setCheckItem(i);
                SPUtils.getInstance().put(ConstantUtils.LIVE_LAST_LABEL, labelEntity.name);
            }
        });
    }

    public void updatePreStartLiveInfo(String str) {
        GlideUtils.loadRoundCornersImage(this.mContext, this.ivCover, str, 6);
        String string = SPUtils.getInstance().getString(ConstantUtils.LIVE_LAST_TOPIC);
        this.etTitle.setText(TextUtils.isEmpty(string) ? "" : string);
        this.etTitle.setSelection(TextUtils.isEmpty(string) ? 0 : string.length());
        this.tvLabel.setText(SPUtils.getInstance().getString(ConstantUtils.LIVE_LAST_LABEL, ""));
    }

    public void onCameraActivityResult() {
        Uri currentPhotoUri = this.mediaStoreCompat.getCurrentPhotoUri();
        if (currentPhotoUri == null) {
            showToast(R$string.fq_pic_take_fail);
        } else {
            picCrop(currentPhotoUri);
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

    private void picCrop(@NonNull Uri uri) {
        UCrop.m232of(uri, Uri.fromFile(this.tempCoverImgFile)).withAspectRatio(1.0f, 1.0f).withMaxResultSize(1080, 1080).start(this.mActivity);
    }

    public void picCompression() {
        File file = this.tempCoverImgFile;
        if (file == null || !file.exists()) {
            showToast(R$string.fq_pic_inexistence);
            return;
        }
        Luban.Builder with = Luban.with(this.mContext);
        with.load(this.tempCoverImgFile);
        with.ignoreBy(50);
        with.setTargetDir(this.tempCoverImgFile.getParent());
        with.setCompressListener(new OnCompressListener() { // from class: com.tomatolive.library.ui.view.custom.PreStartLiveView.11
            @Override // top.zibin.luban.OnCompressListener
            public void onStart() {
            }

            @Override // top.zibin.luban.OnCompressListener
            public void onSuccess(File file2) {
                GlideUtils.loadRoundCornersImage(PreStartLiveView.this.mContext, PreStartLiveView.this.ivCover, file2, 6);
                PreStartLiveView.this.uploadCoverImg(file2);
            }

            @Override // top.zibin.luban.OnCompressListener
            public void onError(Throwable th) {
                PreStartLiveView.this.showToast(R$string.fq_pic_compress_fail);
            }
        });
        with.launch();
    }

    public void setTMirrorDrawableTop(int i) {
        Drawable drawable = getResources().getDrawable(i);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        this.tvMirror.setCompoundDrawables(drawable, null, null, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showToast(@StringRes int i) {
        ToastUtils.showShort(this.mContext.getString(i));
    }

    private void showTakePhotoDialog(ActionSheetView.ActionSheetOperateListener actionSheetOperateListener) {
        String[] stringArray = this.mContext.getResources().getStringArray(R$array.take_photo_text);
        ArrayList arrayList = new ArrayList();
        for (String str : stringArray) {
            arrayList.add(new MenuEntity(str));
        }
        ActionSheetView.showOperateCancelDialog(this.mContext, arrayList, actionSheetOperateListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showLabel() {
        LabelDialog labelDialog = this.labelDialog;
        if (labelDialog == null || labelDialog.isAdded()) {
            return;
        }
        Context context = this.mContext;
        if (!(context instanceof FragmentActivity)) {
            return;
        }
        this.labelDialog.show(((FragmentActivity) context).getSupportFragmentManager());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dismissLabelDialog() {
        LabelDialog labelDialog = this.labelDialog;
        if (labelDialog == null || !labelDialog.isAdded()) {
            return;
        }
        this.labelDialog.dismiss();
    }

    private void createTempFile() {
        this.tempCoverImgFile = new File(PathUtils.getExternalAppCachePath() + File.separator + "imgCache", "tempCoverImg.jpg");
        FileUtils.createFileByDeleteOldFile(this.tempCoverImgFile);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void uploadCoverImg(File file) {
        showLoadingDialog();
        ApiRetrofit.getInstance().getApiService().uploadFile(AppUtils.getImgUploadUrl(), AppUtils.getImgUploadRequestBody(file)).map(new ServerResultFunction<UploadFileEntity>() { // from class: com.tomatolive.library.ui.view.custom.PreStartLiveView.12
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new HttpRxObserver(this.mContext, new ResultCallBack<UploadFileEntity>() { // from class: com.tomatolive.library.ui.view.custom.PreStartLiveView.13
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(UploadFileEntity uploadFileEntity) {
                if (uploadFileEntity == null) {
                    PreStartLiveView.this.showToast(R$string.fq_up_cover_fail);
                    PreStartLiveView.this.dismissLoadingDialog();
                    return;
                }
                PreStartLiveView.this.uploadCoverImg(uploadFileEntity.getFilename());
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str) {
                PreStartLiveView.this.showToast(R$string.fq_up_cover_fail);
                PreStartLiveView.this.dismissLoadingDialog();
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void uploadCoverImg(String str) {
        ApiRetrofit.getInstance().getApiService().getUploadLiveCoverService(new RequestParams().getUploadLiveCoverParams(str)).map(new ServerResultFunction<Object>() { // from class: com.tomatolive.library.ui.view.custom.PreStartLiveView.14
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new HttpRxObserver(this.mContext, new ResultCallBack<Object>() { // from class: com.tomatolive.library.ui.view.custom.PreStartLiveView.15
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(Object obj) {
                PreStartLiveView.this.showToast(R$string.fq_up_cover_succ);
                PreStartLiveView.this.dismissLoadingDialog();
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str2) {
                PreStartLiveView.this.showToast(R$string.fq_up_cover_fail);
                PreStartLiveView.this.dismissLoadingDialog();
            }
        }));
    }

    private void showLoadingDialog() {
        LoadingDialog loadingDialog = this.loadingDialog;
        if (loadingDialog != null) {
            loadingDialog.show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dismissLoadingDialog() {
        LoadingDialog loadingDialog = this.loadingDialog;
        if (loadingDialog == null || !loadingDialog.isShowing()) {
            return;
        }
        this.loadingDialog.dismiss();
    }

    private boolean isPicUseAge(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        String[] split = str.split("\\.");
        String lowerCase = split[split.length - 1].toLowerCase();
        return TextUtils.equals(lowerCase, "jpg") || TextUtils.equals(lowerCase, "jpeg") || TextUtils.equals(lowerCase, "png");
    }

    public void goToUploadCover() {
        Activity activity = this.mActivity;
        FragmentActivity fragmentActivity = activity instanceof FragmentActivity ? (FragmentActivity) activity : null;
        if (fragmentActivity == null) {
            return;
        }
        final RxPermissions rxPermissions = new RxPermissions(fragmentActivity);
        showTakePhotoDialog(new ActionSheetView.ActionSheetOperateListener() { // from class: com.tomatolive.library.ui.view.custom.PreStartLiveView.16
            @Override // com.tomatolive.library.p136ui.view.widget.ActionSheetView.ActionSheetOperateListener
            public void onCancel() {
                if (PreStartLiveView.this.onPreStartLiveCallback != null) {
                    PreStartLiveView.this.onPreStartLiveCallback.onCancelUpload();
                }
            }

            @Override // com.tomatolive.library.p136ui.view.widget.ActionSheetView.ActionSheetOperateListener
            public void onOperateListener(MenuEntity menuEntity, int i) {
                if (i == 0) {
                    rxPermissions.request("android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE").subscribe(new Observer<Boolean>() { // from class: com.tomatolive.library.ui.view.custom.PreStartLiveView.16.1
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
                                if (PreStartLiveView.this.mediaStoreCompat == null) {
                                    return;
                                }
                                PreStartLiveView.this.mediaStoreCompat.dispatchCaptureIntent(PreStartLiveView.this.mContext, ConstantUtils.REQUEST_CAMERA);
                                return;
                            }
                            PreStartLiveView.this.showToast(R$string.fq_no_permission);
                        }
                    });
                } else if (i != 1) {
                } else {
                    rxPermissions.request("android.permission.WRITE_EXTERNAL_STORAGE").subscribe(new Observer<Boolean>() { // from class: com.tomatolive.library.ui.view.custom.PreStartLiveView.16.2
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
                            if (!bool.booleanValue()) {
                                PreStartLiveView.this.showToast(R$string.fq_no_permission_write);
                            } else {
                                Matisse.from(PreStartLiveView.this.mActivity).choose(MimeType.m241of(MimeType.JPEG, MimeType.PNG)).showSingleMediaType(true).capture(false).thumbnailScale(0.85f).imageEngine(new GlideEngine()).forResult(ConstantUtils.REQUEST_ALBUM);
                            }
                        }
                    });
                }
            }
        });
    }
}
