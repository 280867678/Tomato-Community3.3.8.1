package com.tomatolive.library.model;

import java.util.List;

/* loaded from: classes3.dex */
public class LiveHelperAppConfigEntity {
    public String androidPackageName;
    public List<ChannelConfigEntity> customerChannelConfigs;
    public String startLiveAppDownloadUrl;

    /* loaded from: classes3.dex */
    public static class ChannelConfigEntity {
        public String channelName;
        public String channelType;
        public String channelUrl;
    }
}
