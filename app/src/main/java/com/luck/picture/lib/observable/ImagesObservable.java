package com.luck.picture.lib.observable;

import com.luck.picture.lib.entity.LocalMedia;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class ImagesObservable implements SubjectListener {
    private static ImagesObservable sObserver;
    private List<LocalMedia> medias = new ArrayList();

    private ImagesObservable() {
        new ArrayList();
        new ArrayList();
        new ArrayList();
    }

    public static ImagesObservable getInstance() {
        if (sObserver == null) {
            synchronized (ImagesObservable.class) {
                if (sObserver == null) {
                    sObserver = new ImagesObservable();
                }
            }
        }
        return sObserver;
    }

    public void saveLocalMedia(List<LocalMedia> list) {
        this.medias = list;
    }

    public List<LocalMedia> readLocalMedias() {
        if (this.medias == null) {
            this.medias = new ArrayList();
        }
        return this.medias;
    }

    public void clearLocalMedia() {
        List<LocalMedia> list = this.medias;
        if (list != null) {
            list.clear();
        }
    }
}
