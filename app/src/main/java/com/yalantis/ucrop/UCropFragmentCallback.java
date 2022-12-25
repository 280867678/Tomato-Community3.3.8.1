package com.yalantis.ucrop;

import com.yalantis.ucrop.UCropFragment;

/* loaded from: classes4.dex */
public interface UCropFragmentCallback {
    void loadingProgress(boolean z);

    void onCropFinish(UCropFragment.UCropResult uCropResult);
}
