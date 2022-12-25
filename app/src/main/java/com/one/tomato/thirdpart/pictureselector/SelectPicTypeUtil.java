package com.one.tomato.thirdpart.pictureselector;

import android.app.Activity;
import com.luck.picture.lib.PictureSelectionModel;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.one.tomato.utils.FileUtil;
import java.util.List;

/* loaded from: classes3.dex */
public class SelectPicTypeUtil {
    private Activity activity;

    public SelectPicTypeUtil(Activity activity) {
        this.activity = activity;
    }

    public void selectCommonPhoto(int i, boolean z, boolean z2, boolean z3, List<LocalMedia> list) {
        PictureSelectionModel openGallery = PictureSelector.create(this.activity).openGallery(PictureMimeType.ofImage());
        openGallery.theme(2131821197);
        openGallery.maxSelectNum(i);
        openGallery.minSelectNum(1);
        openGallery.imageSpanCount(4);
        openGallery.selectionMode(2);
        openGallery.previewImage(true);
        openGallery.isCamera(true);
        openGallery.isZoomAnim(true);
        openGallery.enableCrop(z);
        openGallery.compress(z2);
        openGallery.synOrAsy(true);
        openGallery.compressSavePath(FileUtil.getImageCacheDir().getPath());
        openGallery.glideOverride(160, 160);
        openGallery.withAspectRatio(0, 0);
        openGallery.hideBottomControls(false);
        openGallery.isGif(true);
        openGallery.freeStyleCropEnabled(true);
        openGallery.circleDimmedLayer(z3);
        openGallery.showCropFrame(false);
        openGallery.showCropGrid(false);
        openGallery.openClickSound(false);
        openGallery.selectionMedia(list);
        openGallery.cropCompressQuality(80);
        openGallery.minimumCompressSize(1024);
        openGallery.forResult(188);
    }

    public void selectCommonVideo(int i, List<LocalMedia> list) {
        PictureSelectionModel openGallery = PictureSelector.create(this.activity).openGallery(PictureMimeType.ofVideo());
        openGallery.theme(2131821197);
        openGallery.maxSelectNum(i);
        openGallery.minSelectNum(1);
        openGallery.imageSpanCount(4);
        openGallery.selectionMode(2);
        openGallery.previewVideo(true);
        openGallery.isCamera(true);
        openGallery.openClickSound(false);
        openGallery.selectionMedia(list);
        openGallery.forResult(188);
    }

    public void selectLimitSecondVideo(int i, int i2, int i3, int i4, List<LocalMedia> list) {
        PictureSelectionModel openGallery = PictureSelector.create(this.activity).openGallery(PictureMimeType.ofVideo());
        openGallery.theme(2131821197);
        openGallery.maxSelectNum(i);
        openGallery.minSelectNum(1);
        openGallery.imageSpanCount(4);
        openGallery.selectionMode(2);
        openGallery.previewVideo(true);
        openGallery.isCamera(false);
        openGallery.openClickSound(false);
        openGallery.selectionMedia(list);
        openGallery.videoMaxSecond(i2);
        openGallery.videoMinSecond(i3);
        openGallery.videoQuality(1);
        openGallery.recordVideoSecond(i4);
        openGallery.forResult(188);
    }
}
