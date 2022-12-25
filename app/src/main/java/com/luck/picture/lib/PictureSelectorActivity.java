package com.luck.picture.lib;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.p002v4.content.FileProvider;
import android.support.p005v7.widget.GridLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.support.p005v7.widget.SimpleItemAnimator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import com.luck.picture.lib.adapter.PictureAlbumDirectoryAdapter;
import com.luck.picture.lib.adapter.PictureImageGridAdapter;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.config.PictureSelectionConfig;
import com.luck.picture.lib.decoration.GridSpacingItemDecoration;
import com.luck.picture.lib.dialog.CustomDialog;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.entity.LocalMediaFolder;
import com.luck.picture.lib.model.LocalMediaLoader;
import com.luck.picture.lib.observable.ImagesObservable;
import com.luck.picture.lib.permissions.RxPermissions;
import com.luck.picture.lib.rxbus2.RxBus;
import com.luck.picture.lib.tools.DateUtils;
import com.luck.picture.lib.tools.DoubleUtils;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.luck.picture.lib.tools.ScreenUtils;
import com.luck.picture.lib.tools.StringUtils;
import com.luck.picture.lib.tools.ToastManage;
import com.luck.picture.lib.widget.FolderPopWindow;
import com.luck.picture.lib.widget.PhotoPopupWindow;
import com.tomato.ucrop.UCrop;
import com.tomato.ucrop.UCropMulti;
import com.tomato.ucrop.model.CutInfo;
import com.tomatolive.library.utils.ConstantUtils;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class PictureSelectorActivity extends PictureBaseActivity implements View.OnClickListener, PictureAlbumDirectoryAdapter.OnItemClickListener, PictureImageGridAdapter.OnPhotoSelectChangedListener, PhotoPopupWindow.OnItemClickListener {
    private PictureImageGridAdapter adapter;
    private CustomDialog audioDialog;
    private int audioH;
    private FolderPopWindow folderWindow;
    private LinearLayout id_ll_ok;
    private LocalMediaLoader mediaLoader;
    private MediaPlayer mediaPlayer;
    private SeekBar musicSeekBar;
    private TextView picture_id_preview;
    private ImageView picture_left_back;
    private RecyclerView picture_recycler;
    private TextView picture_right;
    private TextView picture_title;
    private TextView picture_tv_img_num;
    private TextView picture_tv_ok;
    private PhotoPopupWindow popupWindow;
    private RelativeLayout rl_picture_title;
    private RxPermissions rxPermissions;
    private TextView tv_PlayPause;
    private TextView tv_Quit;
    private TextView tv_Stop;
    private TextView tv_empty;
    private TextView tv_musicStatus;
    private TextView tv_musicTime;
    private TextView tv_musicTotal;
    private List<LocalMedia> images = new ArrayList();
    private List<LocalMediaFolder> foldersList = new ArrayList();
    private Animation animation = null;
    private boolean anim = false;
    private boolean isPlayAudio = false;
    private Handler mHandler = new Handler() { // from class: com.luck.picture.lib.PictureSelectorActivity.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            int i = message.what;
            if (i == 0) {
                PictureSelectorActivity.this.showPleaseDialog();
            } else if (i != 1) {
            } else {
                PictureSelectorActivity.this.dismissDialog();
            }
        }
    };
    public Handler handler = new Handler();
    public Runnable runnable = new Runnable() { // from class: com.luck.picture.lib.PictureSelectorActivity.9
        @Override // java.lang.Runnable
        public void run() {
            try {
                if (PictureSelectorActivity.this.mediaPlayer == null) {
                    return;
                }
                PictureSelectorActivity.this.tv_musicTime.setText(DateUtils.timeParse(PictureSelectorActivity.this.mediaPlayer.getCurrentPosition()));
                PictureSelectorActivity.this.musicSeekBar.setProgress(PictureSelectorActivity.this.mediaPlayer.getCurrentPosition());
                PictureSelectorActivity.this.musicSeekBar.setMax(PictureSelectorActivity.this.mediaPlayer.getDuration());
                PictureSelectorActivity.this.tv_musicTotal.setText(DateUtils.timeParse(PictureSelectorActivity.this.mediaPlayer.getDuration()));
                PictureSelectorActivity.this.handler.postDelayed(PictureSelectorActivity.this.runnable, 200L);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override // com.luck.picture.lib.PictureBaseActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (!RxBus.getDefault().isRegistered(this)) {
            RxBus.getDefault().register(this);
        }
        this.rxPermissions = new RxPermissions(this);
        if (this.config.camera) {
            if (bundle == null) {
                this.rxPermissions.request("android.permission.READ_EXTERNAL_STORAGE").subscribe(new Observer<Boolean>() { // from class: com.luck.picture.lib.PictureSelectorActivity.2
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
                            PictureSelectorActivity.this.onTakePhoto();
                            return;
                        }
                        PictureSelectorActivity pictureSelectorActivity = PictureSelectorActivity.this;
                        ToastManage.m3846s(pictureSelectorActivity.mContext, pictureSelectorActivity.getString(R$string.picture_camera));
                        PictureSelectorActivity.this.closeActivity();
                    }
                });
            }
            getWindow().setFlags(1024, 1024);
            setContentView(R$layout.picture_empty);
            return;
        }
        setContentView(R$layout.picture_selector);
        initView(bundle);
    }

    private void initView(Bundle bundle) {
        String string;
        String string2;
        this.rl_picture_title = (RelativeLayout) findViewById(R$id.rl_picture_title);
        this.picture_left_back = (ImageView) findViewById(R$id.picture_left_back);
        this.picture_title = (TextView) findViewById(R$id.picture_title);
        this.picture_right = (TextView) findViewById(R$id.picture_right);
        this.picture_tv_ok = (TextView) findViewById(R$id.picture_tv_ok);
        this.picture_id_preview = (TextView) findViewById(R$id.picture_id_preview);
        this.picture_tv_img_num = (TextView) findViewById(R$id.picture_tv_img_num);
        this.picture_recycler = (RecyclerView) findViewById(R$id.picture_recycler);
        this.id_ll_ok = (LinearLayout) findViewById(R$id.id_ll_ok);
        this.tv_empty = (TextView) findViewById(R$id.tv_empty);
        isNumComplete(this.numComplete);
        if (this.config.mimeType == PictureMimeType.ofAll()) {
            this.popupWindow = new PhotoPopupWindow(this);
            this.popupWindow.setOnItemClickListener(this);
        }
        this.picture_id_preview.setOnClickListener(this);
        int i = 8;
        if (this.config.mimeType == PictureMimeType.ofAudio()) {
            this.picture_id_preview.setVisibility(8);
            this.audioH = ScreenUtils.getScreenHeight(this.mContext) + ScreenUtils.getStatusBarHeight(this.mContext);
        } else {
            TextView textView = this.picture_id_preview;
            if (this.config.mimeType != 2) {
                i = 0;
            }
            textView.setVisibility(i);
        }
        this.picture_left_back.setOnClickListener(this);
        this.picture_right.setOnClickListener(this);
        this.id_ll_ok.setOnClickListener(this);
        this.picture_title.setOnClickListener(this);
        if (this.config.mimeType == PictureMimeType.ofAudio()) {
            string = getString(R$string.picture_all_audio);
        } else {
            string = getString(R$string.picture_camera_roll);
        }
        this.picture_title.setText(string);
        this.folderWindow = new FolderPopWindow(this, this.config.mimeType);
        this.folderWindow.setPictureTitleView(this.picture_title);
        this.folderWindow.setOnItemClickListener(this);
        this.picture_recycler.setHasFixedSize(true);
        this.picture_recycler.addItemDecoration(new GridSpacingItemDecoration(this.config.imageSpanCount, ScreenUtils.dip2px(this, 2.0f), false));
        this.picture_recycler.setLayoutManager(new GridLayoutManager(this, this.config.imageSpanCount));
        ((SimpleItemAnimator) this.picture_recycler.getItemAnimator()).setSupportsChangeAnimations(false);
        PictureSelectionConfig pictureSelectionConfig = this.config;
        this.mediaLoader = new LocalMediaLoader(this, pictureSelectionConfig.mimeType, pictureSelectionConfig.isGif, pictureSelectionConfig.videoMaxSecond, pictureSelectionConfig.videoMinSecond);
        this.rxPermissions.request("android.permission.READ_EXTERNAL_STORAGE").subscribe(new Observer<Boolean>() { // from class: com.luck.picture.lib.PictureSelectorActivity.3
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
                    PictureSelectorActivity.this.mHandler.sendEmptyMessage(0);
                    PictureSelectorActivity.this.readLocalMedia();
                    return;
                }
                PictureSelectorActivity pictureSelectorActivity = PictureSelectorActivity.this;
                ToastManage.m3846s(pictureSelectorActivity.mContext, pictureSelectorActivity.getString(R$string.picture_jurisdiction));
            }
        });
        TextView textView2 = this.tv_empty;
        if (this.config.mimeType == PictureMimeType.ofAudio()) {
            string2 = getString(R$string.picture_audio_empty);
        } else {
            string2 = getString(R$string.picture_empty);
        }
        textView2.setText(string2);
        StringUtils.tempTextFont(this.tv_empty, this.config.mimeType);
        if (bundle != null) {
            this.selectionMedias = PictureSelector.obtainSelectorList(bundle);
        }
        this.adapter = new PictureImageGridAdapter(this.mContext, this.config);
        this.adapter.setOnPhotoSelectChangedListener(this);
        this.adapter.bindSelectImages(this.selectionMedias);
        this.picture_recycler.setAdapter(this.adapter);
        String trim = this.picture_title.getText().toString().trim();
        PictureSelectionConfig pictureSelectionConfig2 = this.config;
        if (pictureSelectionConfig2.isCamera) {
            pictureSelectionConfig2.isCamera = StringUtils.isCamera(trim);
        }
    }

    @Override // com.luck.picture.lib.PictureBaseActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        PictureImageGridAdapter pictureImageGridAdapter = this.adapter;
        if (pictureImageGridAdapter != null) {
            PictureSelector.saveSelectorList(bundle, pictureImageGridAdapter.getSelectedImages());
        }
    }

    private void isNumComplete(boolean z) {
        String string;
        TextView textView = this.picture_tv_ok;
        if (z) {
            int i = R$string.picture_done_front_num;
            Object[] objArr = new Object[2];
            objArr[0] = 0;
            PictureSelectionConfig pictureSelectionConfig = this.config;
            objArr[1] = Integer.valueOf(pictureSelectionConfig.selectionMode == 1 ? 1 : pictureSelectionConfig.maxSelectNum);
            string = getString(i, objArr);
        } else {
            string = getString(R$string.picture_please_select);
        }
        textView.setText(string);
        if (!z) {
            this.animation = AnimationUtils.loadAnimation(this, R$anim.modal_in);
        }
        this.animation = z ? null : AnimationUtils.loadAnimation(this, R$anim.modal_in);
    }

    protected void readLocalMedia() {
        this.mediaLoader.loadAllMedia(new LocalMediaLoader.LocalMediaLoadListener() { // from class: com.luck.picture.lib.PictureSelectorActivity.4
            @Override // com.luck.picture.lib.model.LocalMediaLoader.LocalMediaLoadListener
            public void loadComplete(List<LocalMediaFolder> list) {
                int i = 0;
                if (list.size() > 0) {
                    PictureSelectorActivity.this.foldersList = list;
                    LocalMediaFolder localMediaFolder = list.get(0);
                    localMediaFolder.setChecked(true);
                    List<LocalMedia> images = localMediaFolder.getImages();
                    if (images.size() >= PictureSelectorActivity.this.images.size()) {
                        PictureSelectorActivity.this.images = images;
                        PictureSelectorActivity.this.folderWindow.bindFolder(list);
                    }
                }
                if (PictureSelectorActivity.this.adapter != null) {
                    if (PictureSelectorActivity.this.images == null) {
                        PictureSelectorActivity.this.images = new ArrayList();
                    }
                    PictureSelectorActivity.this.adapter.bindImagesData(PictureSelectorActivity.this.images);
                    TextView textView = PictureSelectorActivity.this.tv_empty;
                    if (PictureSelectorActivity.this.images.size() > 0) {
                        i = 4;
                    }
                    textView.setVisibility(i);
                }
                PictureSelectorActivity.this.mHandler.sendEmptyMessage(1);
            }
        });
    }

    public void startCamera() {
        if (!DoubleUtils.isFastDoubleClick() || this.config.camera) {
            int i = this.config.mimeType;
            if (i == 0) {
                PhotoPopupWindow photoPopupWindow = this.popupWindow;
                if (photoPopupWindow != null) {
                    if (photoPopupWindow.isShowing()) {
                        this.popupWindow.dismiss();
                    }
                    this.popupWindow.showAsDropDown(this.rl_picture_title);
                    return;
                }
                startOpenCamera();
            } else if (i == 1) {
                startOpenCamera();
            } else if (i == 2) {
                startOpenCameraVideo();
            } else if (i != 3) {
            } else {
                startOpenCameraAudio();
            }
        }
    }

    public void startOpenCamera() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        if (intent.resolveActivity(getPackageManager()) != null) {
            int i = this.config.mimeType;
            if (i == 0) {
                i = 1;
            }
            File createCameraFile = PictureFileUtils.createCameraFile(this, i, this.outputCameraPath, this.config.suffixType);
            this.cameraPath = createCameraFile.getAbsolutePath();
            intent.putExtra("output", parUri(createCameraFile));
            startActivityForResult(intent, ConstantUtils.REQUEST_ALBUM);
        }
    }

    public void startOpenCameraVideo() {
        Intent intent = new Intent("android.media.action.VIDEO_CAPTURE");
        if (intent.resolveActivity(getPackageManager()) != null) {
            int i = this.config.mimeType;
            if (i == 0) {
                i = 2;
            }
            File createCameraFile = PictureFileUtils.createCameraFile(this, i, this.outputCameraPath, this.config.suffixType);
            this.cameraPath = createCameraFile.getAbsolutePath();
            intent.putExtra("output", parUri(createCameraFile));
            intent.putExtra("android.intent.extra.durationLimit", this.config.recordVideoSecond);
            intent.putExtra("android.intent.extra.videoQuality", this.config.videoQuality);
            startActivityForResult(intent, ConstantUtils.REQUEST_ALBUM);
        }
    }

    public void startOpenCameraAudio() {
        this.rxPermissions.request("android.permission.RECORD_AUDIO").subscribe(new Observer<Boolean>() { // from class: com.luck.picture.lib.PictureSelectorActivity.5
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
                    Intent intent = new Intent("android.provider.MediaStore.RECORD_SOUND");
                    if (intent.resolveActivity(PictureSelectorActivity.this.getPackageManager()) == null) {
                        return;
                    }
                    PictureSelectorActivity.this.startActivityForResult(intent, ConstantUtils.REQUEST_ALBUM);
                    return;
                }
                PictureSelectorActivity pictureSelectorActivity = PictureSelectorActivity.this;
                ToastManage.m3846s(pictureSelectorActivity.mContext, pictureSelectorActivity.getString(R$string.picture_audio));
            }
        });
    }

    private Uri parUri(File file) {
        String str = getPackageName() + ".provider";
        if (Build.VERSION.SDK_INT > 23) {
            return FileProvider.getUriForFile(this.mContext, str, file);
        }
        return Uri.fromFile(file);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R$id.picture_left_back || id == R$id.picture_right) {
            if (this.folderWindow.isShowing()) {
                this.folderWindow.dismiss();
            } else {
                closeActivity();
            }
        }
        if (id == R$id.picture_title) {
            if (this.folderWindow.isShowing()) {
                this.folderWindow.dismiss();
            } else {
                List<LocalMedia> list = this.images;
                if (list != null && list.size() > 0) {
                    this.folderWindow.showAsDropDown(this.rl_picture_title);
                    this.folderWindow.notifyDataCheckedStatus(this.adapter.getSelectedImages());
                }
            }
        }
        if (id == R$id.picture_id_preview) {
            List<LocalMedia> selectedImages = this.adapter.getSelectedImages();
            ArrayList arrayList = new ArrayList();
            for (LocalMedia localMedia : selectedImages) {
                arrayList.add(localMedia);
            }
            Bundle bundle = new Bundle();
            bundle.putSerializable("previewSelectList", arrayList);
            bundle.putSerializable("selectList", (Serializable) selectedImages);
            bundle.putBoolean("bottom_preview", true);
            startActivity(PicturePreviewActivity.class, bundle, this.config.selectionMode == 1 ? 69 : 609);
            overridePendingTransition(R$anim.f1556a5, 0);
        }
        if (id == R$id.id_ll_ok) {
            List<LocalMedia> selectedImages2 = this.adapter.getSelectedImages();
            LocalMedia localMedia2 = selectedImages2.size() > 0 ? selectedImages2.get(0) : null;
            String pictureType = localMedia2 != null ? localMedia2.getPictureType() : "";
            int size = selectedImages2.size();
            boolean startsWith = pictureType.startsWith("image");
            PictureSelectionConfig pictureSelectionConfig = this.config;
            int i = pictureSelectionConfig.minSelectNum;
            if (i > 0 && pictureSelectionConfig.selectionMode == 2 && size < i) {
                ToastManage.m3846s(this.mContext, startsWith ? getString(R$string.picture_min_img_num, new Object[]{Integer.valueOf(i)}) : getString(R$string.picture_min_video_num, new Object[]{Integer.valueOf(i)}));
                return;
            }
            PictureSelectionConfig pictureSelectionConfig2 = this.config;
            if (pictureSelectionConfig2.enableCrop && startsWith) {
                if (pictureSelectionConfig2.selectionMode == 1) {
                    this.originalPath = localMedia2.getPath();
                    startCrop(this.originalPath);
                    return;
                }
                ArrayList<String> arrayList2 = new ArrayList<>();
                for (LocalMedia localMedia3 : selectedImages2) {
                    arrayList2.add(localMedia3.getPath());
                }
                startCrop(arrayList2);
            } else if (this.config.isCompress && startsWith) {
                compressImage(selectedImages2);
            } else {
                onResult(selectedImages2);
            }
        }
    }

    private void audioDialog(final String str) {
        this.audioDialog = new CustomDialog(this.mContext, -1, this.audioH, R$layout.picture_audio_dialog, R$style.Theme_dialog);
        this.audioDialog.getWindow().setWindowAnimations(R$style.Dialog_Audio_StyleAnim);
        this.tv_musicStatus = (TextView) this.audioDialog.findViewById(R$id.tv_musicStatus);
        this.tv_musicTime = (TextView) this.audioDialog.findViewById(R$id.tv_musicTime);
        this.musicSeekBar = (SeekBar) this.audioDialog.findViewById(R$id.musicSeekBar);
        this.tv_musicTotal = (TextView) this.audioDialog.findViewById(R$id.tv_musicTotal);
        this.tv_PlayPause = (TextView) this.audioDialog.findViewById(R$id.tv_PlayPause);
        this.tv_Stop = (TextView) this.audioDialog.findViewById(R$id.tv_Stop);
        this.tv_Quit = (TextView) this.audioDialog.findViewById(R$id.tv_Quit);
        this.handler.postDelayed(new Runnable() { // from class: com.luck.picture.lib.PictureSelectorActivity.6
            @Override // java.lang.Runnable
            public void run() {
                PictureSelectorActivity.this.initPlayer(str);
            }
        }, 30L);
        this.tv_PlayPause.setOnClickListener(new audioOnClick(str));
        this.tv_Stop.setOnClickListener(new audioOnClick(str));
        this.tv_Quit.setOnClickListener(new audioOnClick(str));
        this.musicSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { // from class: com.luck.picture.lib.PictureSelectorActivity.7
            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                if (z) {
                    PictureSelectorActivity.this.mediaPlayer.seekTo(i);
                }
            }
        });
        this.audioDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.luck.picture.lib.PictureSelectorActivity.8
            @Override // android.content.DialogInterface.OnDismissListener
            public void onDismiss(DialogInterface dialogInterface) {
                PictureSelectorActivity pictureSelectorActivity = PictureSelectorActivity.this;
                pictureSelectorActivity.handler.removeCallbacks(pictureSelectorActivity.runnable);
                new Handler().postDelayed(new Runnable() { // from class: com.luck.picture.lib.PictureSelectorActivity.8.1
                    @Override // java.lang.Runnable
                    public void run() {
                        DialogInterface$OnDismissListenerC22358 dialogInterface$OnDismissListenerC22358 = DialogInterface$OnDismissListenerC22358.this;
                        PictureSelectorActivity.this.stop(str);
                    }
                }, 30L);
                try {
                    if (PictureSelectorActivity.this.audioDialog == null || !PictureSelectorActivity.this.audioDialog.isShowing()) {
                        return;
                    }
                    PictureSelectorActivity.this.audioDialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        this.handler.post(this.runnable);
        this.audioDialog.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initPlayer(String str) {
        this.mediaPlayer = new MediaPlayer();
        try {
            this.mediaPlayer.setDataSource(str);
            this.mediaPlayer.prepare();
            this.mediaPlayer.setLooping(true);
            playAudio();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* loaded from: classes3.dex */
    public class audioOnClick implements View.OnClickListener {
        private String path;

        public audioOnClick(String str) {
            this.path = str;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            int id = view.getId();
            if (id == R$id.tv_PlayPause) {
                PictureSelectorActivity.this.playAudio();
            }
            if (id == R$id.tv_Stop) {
                PictureSelectorActivity.this.tv_musicStatus.setText(PictureSelectorActivity.this.getString(R$string.picture_stop_audio));
                PictureSelectorActivity.this.tv_PlayPause.setText(PictureSelectorActivity.this.getString(R$string.picture_play_audio));
                PictureSelectorActivity.this.stop(this.path);
            }
            if (id == R$id.tv_Quit) {
                PictureSelectorActivity pictureSelectorActivity = PictureSelectorActivity.this;
                pictureSelectorActivity.handler.removeCallbacks(pictureSelectorActivity.runnable);
                new Handler().postDelayed(new Runnable() { // from class: com.luck.picture.lib.PictureSelectorActivity.audioOnClick.1
                    @Override // java.lang.Runnable
                    public void run() {
                        audioOnClick audioonclick = audioOnClick.this;
                        PictureSelectorActivity.this.stop(audioonclick.path);
                    }
                }, 30L);
                try {
                    if (PictureSelectorActivity.this.audioDialog == null || !PictureSelectorActivity.this.audioDialog.isShowing()) {
                        return;
                    }
                    PictureSelectorActivity.this.audioDialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void playAudio() {
        MediaPlayer mediaPlayer = this.mediaPlayer;
        if (mediaPlayer != null) {
            this.musicSeekBar.setProgress(mediaPlayer.getCurrentPosition());
            this.musicSeekBar.setMax(this.mediaPlayer.getDuration());
        }
        if (this.tv_PlayPause.getText().toString().equals(getString(R$string.picture_play_audio))) {
            this.tv_PlayPause.setText(getString(R$string.picture_pause_audio));
            this.tv_musicStatus.setText(getString(R$string.picture_play_audio));
            playOrPause();
        } else {
            this.tv_PlayPause.setText(getString(R$string.picture_play_audio));
            this.tv_musicStatus.setText(getString(R$string.picture_pause_audio));
            playOrPause();
        }
        if (!this.isPlayAudio) {
            this.handler.post(this.runnable);
            this.isPlayAudio = true;
        }
    }

    public void stop(String str) {
        MediaPlayer mediaPlayer = this.mediaPlayer;
        if (mediaPlayer != null) {
            try {
                mediaPlayer.stop();
                this.mediaPlayer.reset();
                this.mediaPlayer.setDataSource(str);
                this.mediaPlayer.prepare();
                this.mediaPlayer.seekTo(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void playOrPause() {
        try {
            if (this.mediaPlayer != null) {
                if (this.mediaPlayer.isPlaying()) {
                    this.mediaPlayer.pause();
                } else {
                    this.mediaPlayer.start();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // com.luck.picture.lib.adapter.PictureAlbumDirectoryAdapter.OnItemClickListener
    public void onItemClick(String str, List<LocalMedia> list) {
        boolean isCamera = StringUtils.isCamera(str);
        if (!this.config.isCamera) {
            isCamera = false;
        }
        this.adapter.setShowCamera(isCamera);
        this.picture_title.setText(str);
        this.adapter.bindImagesData(list);
        this.folderWindow.dismiss();
    }

    @Override // com.luck.picture.lib.adapter.PictureImageGridAdapter.OnPhotoSelectChangedListener
    public void onTakePhoto() {
        this.rxPermissions.request("android.permission.CAMERA").subscribe(new Observer<Boolean>() { // from class: com.luck.picture.lib.PictureSelectorActivity.10
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
                    PictureSelectorActivity.this.startCamera();
                    return;
                }
                PictureSelectorActivity pictureSelectorActivity = PictureSelectorActivity.this;
                ToastManage.m3846s(pictureSelectorActivity.mContext, pictureSelectorActivity.getString(R$string.picture_camera));
                PictureSelectorActivity pictureSelectorActivity2 = PictureSelectorActivity.this;
                if (!pictureSelectorActivity2.config.camera) {
                    return;
                }
                pictureSelectorActivity2.closeActivity();
            }
        });
    }

    @Override // com.luck.picture.lib.adapter.PictureImageGridAdapter.OnPhotoSelectChangedListener
    public void onChange(List<LocalMedia> list) {
        changeImageNumber(list);
    }

    @Override // com.luck.picture.lib.adapter.PictureImageGridAdapter.OnPhotoSelectChangedListener
    public void onPictureClick(LocalMedia localMedia, int i) {
        startPreview(this.adapter.getImages(), i);
    }

    public void startPreview(List<LocalMedia> list, int i) {
        LocalMedia localMedia = list.get(i);
        String pictureType = localMedia.getPictureType();
        Bundle bundle = new Bundle();
        ArrayList arrayList = new ArrayList();
        int isPictureType = PictureMimeType.isPictureType(pictureType);
        if (isPictureType == 1) {
            List<LocalMedia> selectedImages = this.adapter.getSelectedImages();
            ImagesObservable.getInstance().saveLocalMedia(list);
            bundle.putSerializable("selectList", (Serializable) selectedImages);
            bundle.putInt("position", i);
            PictureSelectionConfig pictureSelectionConfig = this.config;
            if (pictureSelectionConfig.enableCrop) {
                startActivity(PicturePreviewActivity.class, bundle, pictureSelectionConfig.selectionMode == 1 ? 69 : 609);
            } else {
                startActivity(PicturePreviewActivity.class, bundle, 9010);
            }
            overridePendingTransition(R$anim.f1556a5, 0);
        } else if (isPictureType == 2) {
            if (this.config.selectionMode == 1) {
                arrayList.add(localMedia);
                onResult(arrayList);
                return;
            }
            bundle.putString("video_path", localMedia.getPath());
            startActivity(PictureVideoPlayActivity.class, bundle);
        } else if (isPictureType != 3) {
        } else {
            if (this.config.selectionMode == 1) {
                arrayList.add(localMedia);
                onResult(arrayList);
                return;
            }
            audioDialog(localMedia.getPath());
        }
    }

    public void changeImageNumber(List<LocalMedia> list) {
        String pictureType = list.size() > 0 ? list.get(0).getPictureType() : "";
        int i = 8;
        if (this.config.mimeType == PictureMimeType.ofAudio()) {
            this.picture_id_preview.setVisibility(8);
        } else {
            boolean isVideo = PictureMimeType.isVideo(pictureType);
            boolean z = this.config.mimeType == 2;
            TextView textView = this.picture_id_preview;
            if (!isVideo && !z) {
                i = 0;
            }
            textView.setVisibility(i);
        }
        if (list.size() != 0) {
            this.id_ll_ok.setEnabled(true);
            this.picture_id_preview.setEnabled(true);
            this.picture_id_preview.setSelected(true);
            this.picture_tv_ok.setSelected(true);
            if (this.numComplete) {
                TextView textView2 = this.picture_tv_ok;
                int i2 = R$string.picture_done_front_num;
                Object[] objArr = new Object[2];
                objArr[0] = Integer.valueOf(list.size());
                PictureSelectionConfig pictureSelectionConfig = this.config;
                objArr[1] = Integer.valueOf(pictureSelectionConfig.selectionMode == 1 ? 1 : pictureSelectionConfig.maxSelectNum);
                textView2.setText(getString(i2, objArr));
                return;
            }
            if (!this.anim) {
                this.picture_tv_img_num.startAnimation(this.animation);
            }
            this.picture_tv_img_num.setVisibility(0);
            this.picture_tv_img_num.setText(String.valueOf(list.size()));
            this.picture_tv_ok.setText(getString(R$string.picture_completed));
            this.anim = false;
            return;
        }
        this.id_ll_ok.setEnabled(false);
        this.picture_id_preview.setEnabled(false);
        this.picture_id_preview.setSelected(false);
        this.picture_tv_ok.setSelected(false);
        if (this.numComplete) {
            TextView textView3 = this.picture_tv_ok;
            int i3 = R$string.picture_done_front_num;
            Object[] objArr2 = new Object[2];
            objArr2[0] = 0;
            PictureSelectionConfig pictureSelectionConfig2 = this.config;
            objArr2[1] = Integer.valueOf(pictureSelectionConfig2.selectionMode == 1 ? 1 : pictureSelectionConfig2.maxSelectNum);
            textView3.setText(getString(i3, objArr2));
            return;
        }
        this.picture_tv_img_num.setVisibility(4);
        this.picture_tv_ok.setText(getString(R$string.picture_please_select));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        String createVideoType;
        int lastImageId;
        if (i2 != -1) {
            if (i2 == 0) {
                if (!this.config.camera) {
                    return;
                }
                closeActivity();
                return;
            } else if (i2 != 96) {
                return;
            } else {
                ToastManage.m3846s(this.mContext, ((Throwable) intent.getSerializableExtra("com.one.tomato.ucrop.Error")).getMessage());
                return;
            }
        }
        List<LocalMedia> arrayList = new ArrayList<>();
        int i3 = 0;
        if (i == 69) {
            String path = UCrop.getOutput(intent).getPath();
            PictureImageGridAdapter pictureImageGridAdapter = this.adapter;
            if (pictureImageGridAdapter != null) {
                List<LocalMedia> selectedImages = pictureImageGridAdapter.getSelectedImages();
                LocalMedia localMedia = (selectedImages == null || selectedImages.size() <= 0) ? null : selectedImages.get(0);
                if (localMedia == null) {
                    return;
                }
                this.originalPath = localMedia.getPath();
                LocalMedia localMedia2 = new LocalMedia(this.originalPath, localMedia.getDuration(), false, localMedia.getPosition(), localMedia.getNum(), this.config.mimeType);
                localMedia2.setCutPath(path);
                localMedia2.setCut(true);
                localMedia2.setPictureType(PictureMimeType.createImageType(path));
                arrayList.add(localMedia2);
                handlerResult(arrayList);
                return;
            }
            PictureSelectionConfig pictureSelectionConfig = this.config;
            if (!pictureSelectionConfig.camera) {
                return;
            }
            LocalMedia localMedia3 = new LocalMedia(this.cameraPath, 0L, false, pictureSelectionConfig.isCamera ? 1 : 0, 0, pictureSelectionConfig.mimeType);
            localMedia3.setCut(true);
            localMedia3.setCutPath(path);
            localMedia3.setPictureType(PictureMimeType.createImageType(path));
            arrayList.add(localMedia3);
            handlerResult(arrayList);
        } else if (i == 609) {
            for (CutInfo cutInfo : UCropMulti.getOutput(intent)) {
                LocalMedia localMedia4 = new LocalMedia();
                String createImageType = PictureMimeType.createImageType(cutInfo.getPath());
                localMedia4.setCut(true);
                localMedia4.setPath(cutInfo.getPath());
                localMedia4.setCutPath(cutInfo.getCutPath());
                localMedia4.setPictureType(createImageType);
                localMedia4.setMimeType(this.config.mimeType);
                arrayList.add(localMedia4);
            }
            handlerResult(arrayList);
        } else if (i != 909) {
            if (i != 9010) {
                return;
            }
            try {
                ArrayList arrayList2 = (ArrayList) intent.getSerializableExtra("extra_result_media");
                if (arrayList2 == null || arrayList2.size() <= 0) {
                    return;
                }
                arrayList.addAll(arrayList2);
                handlerResult(arrayList);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (this.config.mimeType == PictureMimeType.ofAudio()) {
                this.cameraPath = getAudioPath(intent);
            }
            File file = new File(this.cameraPath);
            sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(file)));
            String fileToType = PictureMimeType.fileToType(file);
            if (this.config.mimeType != PictureMimeType.ofAudio()) {
                rotateImage(PictureFileUtils.readPictureDegree(file.getAbsolutePath()), file);
            }
            LocalMedia localMedia5 = new LocalMedia();
            localMedia5.setPath(this.cameraPath);
            boolean startsWith = fileToType.startsWith("video");
            int localVideoDuration = startsWith ? PictureMimeType.getLocalVideoDuration(this.cameraPath) : 0;
            if (this.config.mimeType == PictureMimeType.ofAudio()) {
                localVideoDuration = PictureMimeType.getLocalVideoDuration(this.cameraPath);
                createVideoType = "audio/mpeg";
            } else {
                String str = this.cameraPath;
                createVideoType = startsWith ? PictureMimeType.createVideoType(str) : PictureMimeType.createImageType(str);
            }
            localMedia5.setPictureType(createVideoType);
            localMedia5.setDuration(localVideoDuration);
            localMedia5.setMimeType(this.config.mimeType);
            if (this.config.camera) {
                boolean startsWith2 = fileToType.startsWith("image");
                if (this.config.enableCrop && startsWith2) {
                    String str2 = this.cameraPath;
                    this.originalPath = str2;
                    startCrop(str2);
                } else if (this.config.isCompress && startsWith2) {
                    arrayList.add(localMedia5);
                    compressImage(arrayList);
                    if (this.adapter != null) {
                        this.images.add(0, localMedia5);
                        this.adapter.notifyDataSetChanged();
                    }
                } else {
                    arrayList.add(localMedia5);
                    onResult(arrayList);
                }
            } else {
                this.images.add(0, localMedia5);
                PictureImageGridAdapter pictureImageGridAdapter2 = this.adapter;
                if (pictureImageGridAdapter2 != null) {
                    List<LocalMedia> selectedImages2 = pictureImageGridAdapter2.getSelectedImages();
                    if (selectedImages2.size() < this.config.maxSelectNum) {
                        if (PictureMimeType.mimeToEqual(selectedImages2.size() > 0 ? selectedImages2.get(0).getPictureType() : "", localMedia5.getPictureType()) || selectedImages2.size() == 0) {
                            int size = selectedImages2.size();
                            PictureSelectionConfig pictureSelectionConfig2 = this.config;
                            if (size < pictureSelectionConfig2.maxSelectNum) {
                                if (pictureSelectionConfig2.selectionMode == 1) {
                                    singleRadioMediaImage();
                                }
                                selectedImages2.add(localMedia5);
                                this.adapter.bindSelectImages(selectedImages2);
                            }
                        }
                    }
                    this.adapter.notifyDataSetChanged();
                }
            }
            if (this.adapter != null) {
                manualSaveFolder(localMedia5);
                TextView textView = this.tv_empty;
                if (this.images.size() > 0) {
                    i3 = 4;
                }
                textView.setVisibility(i3);
            }
            if (this.config.mimeType == PictureMimeType.ofAudio() || (lastImageId = getLastImageId(startsWith)) == -1) {
                return;
            }
            removeImage(lastImageId, startsWith);
        }
    }

    private void singleRadioMediaImage() {
        List<LocalMedia> selectedImages;
        PictureImageGridAdapter pictureImageGridAdapter = this.adapter;
        if (pictureImageGridAdapter == null || (selectedImages = pictureImageGridAdapter.getSelectedImages()) == null || selectedImages.size() <= 0) {
            return;
        }
        selectedImages.clear();
    }

    private void manualSaveFolder(LocalMedia localMedia) {
        try {
            createNewFolder(this.foldersList);
            LocalMediaFolder imageFolder = getImageFolder(localMedia.getPath(), this.foldersList);
            LocalMediaFolder localMediaFolder = this.foldersList.size() > 0 ? this.foldersList.get(0) : null;
            if (localMediaFolder == null || imageFolder == null) {
                return;
            }
            localMediaFolder.setFirstImagePath(localMedia.getPath());
            localMediaFolder.setImages(this.images);
            localMediaFolder.setImageNum(localMediaFolder.getImageNum() + 1);
            imageFolder.setImageNum(imageFolder.getImageNum() + 1);
            imageFolder.getImages().add(0, localMedia);
            imageFolder.setFirstImagePath(this.cameraPath);
            this.folderWindow.bindFolder(this.foldersList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onBackPressed() {
        super.onBackPressed();
        closeActivity();
    }

    @Override // com.luck.picture.lib.PictureBaseActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        Handler handler;
        super.onDestroy();
        if (RxBus.getDefault().isRegistered(this)) {
            RxBus.getDefault().unregister(this);
        }
        ImagesObservable.getInstance().clearLocalMedia();
        Animation animation = this.animation;
        if (animation != null) {
            animation.cancel();
            this.animation = null;
        }
        if (this.mediaPlayer == null || (handler = this.handler) == null) {
            return;
        }
        handler.removeCallbacks(this.runnable);
        this.mediaPlayer.release();
        this.mediaPlayer = null;
    }

    @Override // com.luck.picture.lib.widget.PhotoPopupWindow.OnItemClickListener
    public void onItemClick(int i) {
        if (i == 0) {
            startOpenCamera();
        } else if (i != 1) {
        } else {
            startOpenCameraVideo();
        }
    }
}
