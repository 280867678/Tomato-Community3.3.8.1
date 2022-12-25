package com.tomatolive.library.p136ui.view.iview;

import com.tomatolive.library.base.BaseView;
import com.tomatolive.library.model.CountryCodeEntity;
import com.tomatolive.library.model.UploadFileEntity;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.iview.IAnchorAuthView */
/* loaded from: classes3.dex */
public interface IAnchorAuthView extends BaseView {
    void onAuthSuccess();

    void onCountryPhoneCodeSuccess(List<CountryCodeEntity> list);

    void onSendPhoneCodeFail();

    void onSendPhoneCodeSuccess();

    void onUploadFail(boolean z);

    void onUploadSuc(UploadFileEntity uploadFileEntity, boolean z);
}
