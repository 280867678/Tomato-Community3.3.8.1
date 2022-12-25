package com.luck.picture.lib;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.p002v4.app.FragmentActivity;
import android.text.TextUtils;
import com.j256.ormlite.field.FieldType;
import com.luck.picture.lib.compress.Luban;
import com.luck.picture.lib.compress.OnCompressListener;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.config.PictureSelectionConfig;
import com.luck.picture.lib.dialog.PictureDialog;
import com.luck.picture.lib.entity.EventEntity;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.entity.LocalMediaFolder;
import com.luck.picture.lib.immersive.ImmersiveManage;
import com.luck.picture.lib.rxbus2.RxBus;
import com.luck.picture.lib.tools.AttrsUtils;
import com.luck.picture.lib.tools.DateUtils;
import com.luck.picture.lib.tools.DoubleUtils;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.tomato.ucrop.UCrop;
import com.tomato.ucrop.UCropMulti;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class PictureBaseActivity extends FragmentActivity {
    protected String cameraPath;
    protected int colorPrimary;
    protected int colorPrimaryDark;
    protected PictureDialog compressDialog;
    protected PictureSelectionConfig config;
    protected PictureDialog dialog;
    protected Context mContext;
    protected boolean numComplete;
    protected boolean openWhiteStatusBar;
    protected String originalPath;
    protected String outputCameraPath;
    protected List<LocalMedia> selectionMedias;

    @Override // android.app.Activity
    public boolean isImmersive() {
        return true;
    }

    public void immersive() {
        ImmersiveManage.immersiveAboveAPI23(this, this.colorPrimaryDark, this.colorPrimary, this.openWhiteStatusBar);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        if (bundle != null) {
            this.config = (PictureSelectionConfig) bundle.getParcelable("PictureSelectorConfig");
            this.cameraPath = bundle.getString("CameraPath");
            this.originalPath = bundle.getString("OriginalPath");
        } else {
            this.config = PictureSelectionConfig.getInstance();
        }
        setTheme(this.config.themeStyleId);
        super.onCreate(bundle);
        this.mContext = this;
        initConfig();
        if (isImmersive()) {
            immersive();
        }
    }

    private void initConfig() {
        this.outputCameraPath = this.config.outputCameraPath;
        this.openWhiteStatusBar = AttrsUtils.getTypeValueBoolean(this, R$attr.picture_statusFontColor);
        this.numComplete = AttrsUtils.getTypeValueBoolean(this, R$attr.picture_style_numComplete);
        this.config.checkNumMode = AttrsUtils.getTypeValueBoolean(this, R$attr.picture_style_checkNumMode);
        this.colorPrimary = AttrsUtils.getTypeValueColor(this, R$attr.colorPrimary);
        this.colorPrimaryDark = AttrsUtils.getTypeValueColor(this, R$attr.colorPrimaryDark);
        this.selectionMedias = this.config.selectionMedias;
        if (this.selectionMedias == null) {
            this.selectionMedias = new ArrayList();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putString("CameraPath", this.cameraPath);
        bundle.putString("OriginalPath", this.originalPath);
        bundle.putParcelable("PictureSelectorConfig", this.config);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void startActivity(Class cls, Bundle bundle) {
        if (!DoubleUtils.isFastDoubleClick()) {
            Intent intent = new Intent();
            intent.setClass(this, cls);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void startActivity(Class cls, Bundle bundle, int i) {
        if (!DoubleUtils.isFastDoubleClick()) {
            Intent intent = new Intent();
            intent.setClass(this, cls);
            intent.putExtras(bundle);
            startActivityForResult(intent, i);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void showPleaseDialog() {
        if (!isFinishing()) {
            dismissDialog();
            this.dialog = new PictureDialog(this);
            this.dialog.show();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void dismissDialog() {
        try {
            if (this.dialog == null || !this.dialog.isShowing()) {
                return;
            }
            this.dialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void showCompressDialog() {
        if (!isFinishing()) {
            dismissCompressDialog();
            this.compressDialog = new PictureDialog(this);
            this.compressDialog.show();
        }
    }

    protected void dismissCompressDialog() {
        try {
            if (isFinishing() || this.compressDialog == null || !this.compressDialog.isShowing()) {
                return;
            }
            this.compressDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void compressImage(final List<LocalMedia> list) {
        showCompressDialog();
        if (this.config.synOrAsy) {
            Flowable.just(list).observeOn(Schedulers.m90io()).map(new Function<List<LocalMedia>, List<File>>() { // from class: com.luck.picture.lib.PictureBaseActivity.2
                @Override // io.reactivex.functions.Function
                /* renamed from: apply  reason: avoid collision after fix types in other method */
                public List<File> mo6755apply(@NonNull List<LocalMedia> list2) throws Exception {
                    Luban.Builder with = Luban.with(PictureBaseActivity.this.mContext);
                    with.setTargetDir(PictureBaseActivity.this.config.compressSavePath);
                    with.ignoreBy(PictureBaseActivity.this.config.minimumCompressSize);
                    with.loadLocalMedia(list2);
                    List<File> list3 = with.get();
                    return list3 == null ? new ArrayList() : list3;
                }
            }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<File>>() { // from class: com.luck.picture.lib.PictureBaseActivity.1
                @Override // io.reactivex.functions.Consumer
                public void accept(@NonNull List<File> list2) throws Exception {
                    PictureBaseActivity.this.handleCompressCallBack(list, list2);
                }
            });
            return;
        }
        Luban.Builder with = Luban.with(this);
        with.loadLocalMedia(list);
        with.ignoreBy(this.config.minimumCompressSize);
        with.setTargetDir(this.config.compressSavePath);
        with.setCompressListener(new OnCompressListener() { // from class: com.luck.picture.lib.PictureBaseActivity.3
            @Override // com.luck.picture.lib.compress.OnCompressListener
            public void onStart() {
            }

            @Override // com.luck.picture.lib.compress.OnCompressListener
            public void onSuccess(List<LocalMedia> list2) {
                RxBus.getDefault().post(new EventEntity(2770));
                PictureBaseActivity.this.onResult(list2);
            }

            @Override // com.luck.picture.lib.compress.OnCompressListener
            public void onError(Throwable th) {
                RxBus.getDefault().post(new EventEntity(2770));
                PictureBaseActivity.this.onResult(list);
            }
        });
        with.launch();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleCompressCallBack(List<LocalMedia> list, List<File> list2) {
        if (list2.size() == list.size()) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                String path = list2.get(i).getPath();
                LocalMedia localMedia = list.get(i);
                boolean z = !TextUtils.isEmpty(path) && PictureMimeType.isHttp(path);
                localMedia.setCompressed(!z);
                if (z) {
                    path = "";
                }
                localMedia.setCompressPath(path);
                if (localMedia.getPath().endsWith("GIF") || localMedia.getPath().endsWith("gif")) {
                    localMedia.setCompressPath(localMedia.getPath());
                }
            }
        }
        RxBus.getDefault().post(new EventEntity(2770));
        onResult(list);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void startCrop(String str) {
        UCrop.Options options = new UCrop.Options();
        int typeValueColor = AttrsUtils.getTypeValueColor(this, R$attr.picture_crop_toolbar_bg);
        int typeValueColor2 = AttrsUtils.getTypeValueColor(this, R$attr.picture_crop_status_color);
        int typeValueColor3 = AttrsUtils.getTypeValueColor(this, R$attr.picture_crop_title_color);
        options.setToolbarColor(typeValueColor);
        options.setStatusBarColor(typeValueColor2);
        options.setToolbarWidgetColor(typeValueColor3);
        options.setCircleDimmedLayer(this.config.circleDimmedLayer);
        options.setShowCropFrame(this.config.showCropFrame);
        options.setShowCropGrid(this.config.showCropGrid);
        options.setDragFrameEnabled(this.config.isDragFrame);
        options.setScaleEnabled(this.config.scaleEnabled);
        options.setRotateEnabled(this.config.rotateEnabled);
        options.setCompressionQuality(this.config.cropCompressQuality);
        options.setHideBottomControls(this.config.hideBottomControls);
        options.setFreeStyleCropEnabled(this.config.freeStyleCropEnabled);
        boolean isHttp = PictureMimeType.isHttp(str);
        String lastImgType = PictureMimeType.getLastImgType(str);
        Uri parse = isHttp ? Uri.parse(str) : Uri.fromFile(new File(str));
        String diskCacheDir = PictureFileUtils.getDiskCacheDir(this);
        UCrop m244of = UCrop.m244of(parse, Uri.fromFile(new File(diskCacheDir, System.currentTimeMillis() + lastImgType)));
        PictureSelectionConfig pictureSelectionConfig = this.config;
        m244of.withAspectRatio((float) pictureSelectionConfig.aspect_ratio_x, (float) pictureSelectionConfig.aspect_ratio_y);
        PictureSelectionConfig pictureSelectionConfig2 = this.config;
        m244of.withMaxResultSize(pictureSelectionConfig2.cropWidth, pictureSelectionConfig2.cropHeight);
        m244of.withOptions(options);
        m244of.start(this);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void startCrop(ArrayList<String> arrayList) {
        UCropMulti.Options options = new UCropMulti.Options();
        int typeValueColor = AttrsUtils.getTypeValueColor(this, R$attr.picture_crop_toolbar_bg);
        int typeValueColor2 = AttrsUtils.getTypeValueColor(this, R$attr.picture_crop_status_color);
        int typeValueColor3 = AttrsUtils.getTypeValueColor(this, R$attr.picture_crop_title_color);
        options.setToolbarColor(typeValueColor);
        options.setStatusBarColor(typeValueColor2);
        options.setToolbarWidgetColor(typeValueColor3);
        options.setCircleDimmedLayer(this.config.circleDimmedLayer);
        options.setShowCropFrame(this.config.showCropFrame);
        options.setDragFrameEnabled(this.config.isDragFrame);
        options.setShowCropGrid(this.config.showCropGrid);
        options.setScaleEnabled(this.config.scaleEnabled);
        options.setRotateEnabled(this.config.rotateEnabled);
        options.setHideBottomControls(true);
        options.setCompressionQuality(this.config.cropCompressQuality);
        options.setCutListData(arrayList);
        options.setFreeStyleCropEnabled(this.config.freeStyleCropEnabled);
        String str = arrayList.size() > 0 ? arrayList.get(0) : "";
        boolean isHttp = PictureMimeType.isHttp(str);
        String lastImgType = PictureMimeType.getLastImgType(str);
        Uri parse = isHttp ? Uri.parse(str) : Uri.fromFile(new File(str));
        String diskCacheDir = PictureFileUtils.getDiskCacheDir(this);
        UCropMulti m243of = UCropMulti.m243of(parse, Uri.fromFile(new File(diskCacheDir, System.currentTimeMillis() + lastImgType)));
        PictureSelectionConfig pictureSelectionConfig = this.config;
        m243of.withAspectRatio((float) pictureSelectionConfig.aspect_ratio_x, (float) pictureSelectionConfig.aspect_ratio_y);
        PictureSelectionConfig pictureSelectionConfig2 = this.config;
        m243of.withMaxResultSize(pictureSelectionConfig2.cropWidth, pictureSelectionConfig2.cropHeight);
        m243of.withOptions(options);
        m243of.start(this);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void rotateImage(int i, File file) {
        if (i > 0) {
            try {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 2;
                PictureFileUtils.saveBitmapFile(PictureFileUtils.rotaingImageView(i, BitmapFactory.decodeFile(file.getAbsolutePath(), options)), file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void handlerResult(List<LocalMedia> list) {
        if (this.config.isCompress) {
            compressImage(list);
        } else {
            onResult(list);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void createNewFolder(List<LocalMediaFolder> list) {
        if (list.size() == 0) {
            LocalMediaFolder localMediaFolder = new LocalMediaFolder();
            localMediaFolder.setName(getString(this.config.mimeType == PictureMimeType.ofAudio() ? R$string.picture_all_audio : R$string.picture_camera_roll));
            localMediaFolder.setPath("");
            localMediaFolder.setFirstImagePath("");
            list.add(localMediaFolder);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public LocalMediaFolder getImageFolder(String str, List<LocalMediaFolder> list) {
        File parentFile = new File(str).getParentFile();
        for (LocalMediaFolder localMediaFolder : list) {
            if (localMediaFolder.getName().equals(parentFile.getName())) {
                return localMediaFolder;
            }
        }
        LocalMediaFolder localMediaFolder2 = new LocalMediaFolder();
        localMediaFolder2.setName(parentFile.getName());
        localMediaFolder2.setPath(parentFile.getAbsolutePath());
        localMediaFolder2.setFirstImagePath(str);
        list.add(localMediaFolder2);
        return localMediaFolder2;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onResult(List<LocalMedia> list) {
        dismissCompressDialog();
        PictureSelectionConfig pictureSelectionConfig = this.config;
        if (pictureSelectionConfig.camera && pictureSelectionConfig.selectionMode == 2 && this.selectionMedias != null) {
            list.addAll(list.size() > 0 ? list.size() - 1 : 0, this.selectionMedias);
        }
        setResult(-1, PictureSelector.putIntentResult(list));
        closeActivity();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void closeActivity() {
        finish();
        if (this.config.camera) {
            overridePendingTransition(0, R$anim.fade_out);
        } else {
            overridePendingTransition(0, R$anim.f1555a3);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        dismissCompressDialog();
        dismissDialog();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getLastImageId(boolean z) {
        int columnIndex;
        int columnIndex2;
        try {
            String dCIMCameraPath = PictureFileUtils.getDCIMCameraPath();
            Cursor query = getContentResolver().query(z ? MediaStore.Video.Media.EXTERNAL_CONTENT_URI : MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, "_data like ?", new String[]{dCIMCameraPath + "%"}, "_id DESC");
            if (!query.moveToFirst()) {
                return -1;
            }
            if (z) {
                columnIndex = query.getColumnIndex(FieldType.FOREIGN_ID_FIELD_SUFFIX);
            } else {
                columnIndex = query.getColumnIndex(FieldType.FOREIGN_ID_FIELD_SUFFIX);
            }
            int i = query.getInt(columnIndex);
            if (z) {
                columnIndex2 = query.getColumnIndex("duration");
            } else {
                columnIndex2 = query.getColumnIndex("date_added");
            }
            int dateDiffer = DateUtils.dateDiffer(query.getLong(columnIndex2));
            query.close();
            if (dateDiffer > 30) {
                return -1;
            }
            return i;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void removeImage(int i, boolean z) {
        try {
            getContentResolver().delete(z ? MediaStore.Video.Media.EXTERNAL_CONTENT_URI : MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "_id=?", new String[]{Long.toString(i)});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public String getAudioPath(Intent intent) {
        boolean z = Build.VERSION.SDK_INT <= 19;
        if (intent == null || this.config.mimeType != PictureMimeType.ofAudio()) {
            return "";
        }
        try {
            Uri data = intent.getData();
            if (z) {
                return data.getPath();
            }
            return getAudioFilePathFromUri(data);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    protected String getAudioFilePathFromUri(Uri uri) {
        try {
            Cursor query = getContentResolver().query(uri, null, null, null, null);
            query.moveToFirst();
            return query.getString(query.getColumnIndex("_data"));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
