package com.one.tomato.entity.p079db;

import java.util.Objects;
import org.litepal.crud.LitePalSupport;

/* renamed from: com.one.tomato.entity.db.ChannelInfo */
/* loaded from: classes3.dex */
public class ChannelInfo extends LitePalSupport {
    private String channelId;
    private String subChannelId;

    public void setSubChannelId(String str) {
        this.subChannelId = str;
    }

    public void setChannelId(String str) {
        this.channelId = str;
    }

    public String getSubChannelId() {
        return this.subChannelId;
    }

    public String getChannelId() {
        return this.channelId;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && ChannelInfo.class == obj.getClass() && this.channelId == ((ChannelInfo) obj).channelId;
    }

    public int hashCode() {
        return Objects.hashCode(this.channelId);
    }
}
