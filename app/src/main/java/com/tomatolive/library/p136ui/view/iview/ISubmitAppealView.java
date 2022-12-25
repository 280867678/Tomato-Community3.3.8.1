package com.tomatolive.library.p136ui.view.iview;

import com.tomatolive.library.base.BaseView;
import com.tomatolive.library.model.UploadFileEntity;

/* renamed from: com.tomatolive.library.ui.view.iview.ISubmitAppealView */
/* loaded from: classes3.dex */
public interface ISubmitAppealView extends BaseView {
    void onSubmitAppealFailure(int i, String str);

    void onSubmitAppealSuccess();

    void onUploadFail();

    void onUploadSuc(UploadFileEntity uploadFileEntity, int i);
}
