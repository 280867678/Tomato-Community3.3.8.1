package com.tomatolive.library.utils.live;

import android.text.TextUtils;
import com.tomatolive.library.model.LiveEntity;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class LiveManagerUtils {
    private String currentLiveId;
    private LiveEntity currentLiveItem;
    private List<LiveEntity> liveList;

    private LiveManagerUtils() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static class SingletonHolder {
        private static final LiveManagerUtils INSTANCE = new LiveManagerUtils();

        private SingletonHolder() {
        }
    }

    public static LiveManagerUtils getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void initCurrentLiveItemInfo(String str, LiveEntity liveEntity) {
        this.currentLiveId = str;
        this.currentLiveItem = liveEntity;
    }

    public ArrayList<LiveEntity> getLiveList() {
        List<LiveEntity> list = this.liveList;
        return list == null ? new ArrayList<>() : (ArrayList) list;
    }

    public void setLiveList(List<LiveEntity> list) {
        this.liveList = list;
    }

    public String getCurrentLiveId() {
        return this.currentLiveId;
    }

    public void setCurrentLiveId(String str) {
        this.currentLiveId = str;
    }

    public LiveEntity getCurrentLiveItem() {
        return this.currentLiveItem;
    }

    public int getCurrentLivePosition() {
        if (TextUtils.isEmpty(this.currentLiveId)) {
            return 0;
        }
        for (int i = 0; i < getLiveList().size(); i++) {
            if (TextUtils.equals(this.currentLiveId, getLiveList().get(i).liveId)) {
                return i;
            }
        }
        return 0;
    }
}
