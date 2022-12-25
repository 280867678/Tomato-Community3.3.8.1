package com.luck.picture.lib;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.IntRange;
import android.support.annotation.StyleRes;
import android.support.p002v4.app.Fragment;
import com.luck.picture.lib.config.PictureSelectionConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.DoubleUtils;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class PictureSelectionModel {
    private PictureSelectionConfig selectionConfig = PictureSelectionConfig.getCleanInstance();
    private PictureSelector selector;

    public PictureSelectionModel(PictureSelector pictureSelector, int i) {
        this.selector = pictureSelector;
        this.selectionConfig.mimeType = i;
    }

    public PictureSelectionModel theme(@StyleRes int i) {
        this.selectionConfig.themeStyleId = i;
        return this;
    }

    public PictureSelectionModel selectionMode(int i) {
        this.selectionConfig.selectionMode = i;
        return this;
    }

    public PictureSelectionModel enableCrop(boolean z) {
        this.selectionConfig.enableCrop = z;
        return this;
    }

    public PictureSelectionModel freeStyleCropEnabled(boolean z) {
        this.selectionConfig.freeStyleCropEnabled = z;
        return this;
    }

    public PictureSelectionModel circleDimmedLayer(boolean z) {
        this.selectionConfig.circleDimmedLayer = z;
        return this;
    }

    public PictureSelectionModel showCropFrame(boolean z) {
        this.selectionConfig.showCropFrame = z;
        return this;
    }

    public PictureSelectionModel showCropGrid(boolean z) {
        this.selectionConfig.showCropGrid = z;
        return this;
    }

    public PictureSelectionModel hideBottomControls(boolean z) {
        this.selectionConfig.hideBottomControls = z;
        return this;
    }

    public PictureSelectionModel withAspectRatio(int i, int i2) {
        PictureSelectionConfig pictureSelectionConfig = this.selectionConfig;
        pictureSelectionConfig.aspect_ratio_x = i;
        pictureSelectionConfig.aspect_ratio_y = i2;
        return this;
    }

    public PictureSelectionModel maxSelectNum(int i) {
        this.selectionConfig.maxSelectNum = i;
        return this;
    }

    public PictureSelectionModel minSelectNum(int i) {
        this.selectionConfig.minSelectNum = i;
        return this;
    }

    public PictureSelectionModel videoQuality(int i) {
        this.selectionConfig.videoQuality = i;
        return this;
    }

    public PictureSelectionModel videoMaxSecond(int i) {
        this.selectionConfig.videoMaxSecond = i * 1000;
        return this;
    }

    public PictureSelectionModel videoMinSecond(int i) {
        this.selectionConfig.videoMinSecond = i * 1000;
        return this;
    }

    public PictureSelectionModel recordVideoSecond(int i) {
        this.selectionConfig.recordVideoSecond = i;
        return this;
    }

    public PictureSelectionModel glideOverride(@IntRange(from = 100) int i, @IntRange(from = 100) int i2) {
        PictureSelectionConfig pictureSelectionConfig = this.selectionConfig;
        pictureSelectionConfig.overrideWidth = i;
        pictureSelectionConfig.overrideHeight = i2;
        return this;
    }

    public PictureSelectionModel imageSpanCount(int i) {
        this.selectionConfig.imageSpanCount = i;
        return this;
    }

    public PictureSelectionModel minimumCompressSize(int i) {
        this.selectionConfig.minimumCompressSize = i;
        return this;
    }

    public PictureSelectionModel cropCompressQuality(int i) {
        this.selectionConfig.cropCompressQuality = i;
        return this;
    }

    public PictureSelectionModel compress(boolean z) {
        this.selectionConfig.isCompress = z;
        return this;
    }

    public PictureSelectionModel synOrAsy(boolean z) {
        this.selectionConfig.synOrAsy = z;
        return this;
    }

    public PictureSelectionModel compressSavePath(String str) {
        this.selectionConfig.compressSavePath = str;
        return this;
    }

    public PictureSelectionModel isZoomAnim(boolean z) {
        this.selectionConfig.zoomAnim = z;
        return this;
    }

    public PictureSelectionModel isCamera(boolean z) {
        this.selectionConfig.isCamera = z;
        return this;
    }

    public PictureSelectionModel isGif(boolean z) {
        this.selectionConfig.isGif = z;
        return this;
    }

    public PictureSelectionModel previewImage(boolean z) {
        this.selectionConfig.enablePreview = z;
        return this;
    }

    public PictureSelectionModel previewVideo(boolean z) {
        this.selectionConfig.enPreviewVideo = z;
        return this;
    }

    public PictureSelectionModel openClickSound(boolean z) {
        this.selectionConfig.openClickSound = z;
        return this;
    }

    public PictureSelectionModel selectionMedia(List<LocalMedia> list) {
        if (list == null) {
            list = new ArrayList<>();
        }
        this.selectionConfig.selectionMedias = list;
        return this;
    }

    public void forResult(int i) {
        Activity activity;
        if (DoubleUtils.isFastDoubleClick() || (activity = this.selector.getActivity()) == null) {
            return;
        }
        Intent intent = new Intent(activity, PictureSelectorActivity.class);
        Fragment fragment = this.selector.getFragment();
        if (fragment != null) {
            fragment.startActivityForResult(intent, i);
        } else {
            activity.startActivityForResult(intent, i);
        }
        activity.overridePendingTransition(R$anim.f1556a5, 0);
    }

    public void openExternalPreview(int i, List<LocalMedia> list) {
        PictureSelector pictureSelector = this.selector;
        if (pictureSelector != null) {
            pictureSelector.externalPicturePreview(i, list);
            return;
        }
        throw new NullPointerException("This PictureSelector is Null");
    }
}
