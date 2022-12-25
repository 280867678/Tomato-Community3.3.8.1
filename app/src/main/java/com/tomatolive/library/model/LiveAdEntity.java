package com.tomatolive.library.model;

import java.io.Serializable;

/* loaded from: classes3.dex */
public class LiveAdEntity implements Serializable {
    public AdEntity adv;
    public LiveEntity live;
    public int type;

    public LiveEntity getLiveEntity() {
        if (this.type == 2 && this.adv != null) {
            LiveEntity liveEntity = new LiveEntity();
            liveEntity.isAd = true;
            AdEntity adEntity = this.adv;
            liveEntity.f5840id = adEntity.f5826id;
            liveEntity.topic = adEntity.profiles;
            liveEntity.nickname = adEntity.name;
            liveEntity.liveCoverUrl = adEntity.img;
            liveEntity.label = adEntity.url;
            liveEntity.format = adEntity.method;
            liveEntity.role = adEntity.forwardScope;
            return liveEntity;
        }
        return this.live;
    }
}
