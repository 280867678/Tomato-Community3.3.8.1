package com.one.tomato.entity.p079db;

import java.util.List;

/* renamed from: com.one.tomato.entity.db.UserChannelBean */
/* loaded from: classes3.dex */
public class UserChannelBean {
    public static final String TYPE_DEFAULT = "default";
    public static final String TYPE_NO_DEFAULT = "noDefault";
    private List<DefaultChannelBean> defaultChannel;
    private int memberId;
    private List<DefaultChannelBean> notDefaultChannel;
    private String versionNo;

    public int getMemberId() {
        return this.memberId;
    }

    public void setMemberId(int i) {
        this.memberId = i;
    }

    public String getVersionNo() {
        return this.versionNo;
    }

    public void setVersionNo(String str) {
        this.versionNo = str;
    }

    public List<DefaultChannelBean> getDefaultChannel() {
        return this.defaultChannel;
    }

    public void setDefaultChannel(List<DefaultChannelBean> list) {
        this.defaultChannel = list;
    }

    public List<DefaultChannelBean> getNotDefaultChannel() {
        return this.notDefaultChannel;
    }

    public void setNotDefaultChannel(List<DefaultChannelBean> list) {
        this.notDefaultChannel = list;
    }
}
