package com.luck.picture.lib;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.p002v4.app.Fragment;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.DoubleUtils;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public final class PictureSelector {
    private final WeakReference<Activity> mActivity;
    private final WeakReference<Fragment> mFragment;

    private PictureSelector(Activity activity) {
        this(activity, null);
    }

    private PictureSelector(Activity activity, Fragment fragment) {
        this.mActivity = new WeakReference<>(activity);
        this.mFragment = new WeakReference<>(fragment);
    }

    public static PictureSelector create(Activity activity) {
        return new PictureSelector(activity);
    }

    public PictureSelectionModel openGallery(int i) {
        return new PictureSelectionModel(this, i);
    }

    public PictureSelectionModel themeStyle(int i) {
        PictureSelectionModel pictureSelectionModel = new PictureSelectionModel(this, PictureMimeType.ofImage());
        pictureSelectionModel.theme(i);
        return pictureSelectionModel;
    }

    public static List<LocalMedia> obtainMultipleResult(Intent intent) {
        ArrayList arrayList = new ArrayList();
        if (intent != null) {
            List<LocalMedia> list = (List) intent.getSerializableExtra("extra_result_media");
            return list == null ? new ArrayList() : list;
        }
        return arrayList;
    }

    public static Intent putIntentResult(List<LocalMedia> list) {
        return new Intent().putExtra("extra_result_media", (Serializable) list);
    }

    public static List<LocalMedia> obtainSelectorList(Bundle bundle) {
        if (bundle != null) {
            return (List) bundle.getSerializable("selectList");
        }
        return new ArrayList();
    }

    public static void saveSelectorList(Bundle bundle, List<LocalMedia> list) {
        bundle.putSerializable("selectList", (Serializable) list);
    }

    public void externalPicturePreview(int i, List<LocalMedia> list) {
        if (!DoubleUtils.isFastDoubleClick()) {
            Intent intent = new Intent(getActivity(), PictureExternalPreviewActivity.class);
            intent.putExtra("previewSelectList", (Serializable) list);
            intent.putExtra("position", i);
            getActivity().startActivity(intent);
            getActivity().overridePendingTransition(R$anim.f1556a5, 0);
        }
    }

    public void externalPictureVideo(String str) {
        if (!DoubleUtils.isFastDoubleClick()) {
            Intent intent = new Intent(getActivity(), PictureVideoPlayActivity.class);
            intent.putExtra("video_path", str);
            getActivity().startActivity(intent);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Nullable
    public Activity getActivity() {
        return this.mActivity.get();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Nullable
    public Fragment getFragment() {
        WeakReference<Fragment> weakReference = this.mFragment;
        if (weakReference != null) {
            return weakReference.get();
        }
        return null;
    }
}
