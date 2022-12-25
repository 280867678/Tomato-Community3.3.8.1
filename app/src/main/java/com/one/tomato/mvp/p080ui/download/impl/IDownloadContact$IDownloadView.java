package com.one.tomato.mvp.p080ui.download.impl;

import com.one.tomato.entity.p079db.VideoDownload;
import com.one.tomato.mvp.base.view.IBaseView;
import java.util.ArrayList;

/* compiled from: IDownloadContact.kt */
/* renamed from: com.one.tomato.mvp.ui.download.impl.IDownloadContact$IDownloadView */
/* loaded from: classes3.dex */
public interface IDownloadContact$IDownloadView extends IBaseView {
    void setCachedSize(String[] strArr);

    void setDataFromDB(ArrayList<VideoDownload> arrayList);
}
