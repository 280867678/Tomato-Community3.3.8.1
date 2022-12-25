package com.tomatolive.library.p136ui.view.iview;

import com.tomatolive.library.base.BaseView;
import com.tomatolive.library.model.ImpressionEntity;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.iview.IAnchorImpressionView */
/* loaded from: classes3.dex */
public interface IAnchorImpressionView extends BaseView {
    void onImpressionListFail();

    void onImpressionListSuccess(List<ImpressionEntity> list);

    void onImpressionListUpdateSuccess(List<ImpressionEntity> list, String str);
}
