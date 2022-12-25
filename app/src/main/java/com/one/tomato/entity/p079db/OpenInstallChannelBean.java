package com.one.tomato.entity.p079db;

import android.support.annotation.RequiresApi;
import java.util.Objects;
import org.litepal.crud.LitePalSupport;

/* renamed from: com.one.tomato.entity.db.OpenInstallChannelBean */
/* loaded from: classes3.dex */
public class OpenInstallChannelBean extends LitePalSupport {
    private String openInstallChannelId;
    private String openInstallSubChannelId;
    private String openInstallVcode;

    public String getOpenInstallChannelId() {
        return this.openInstallChannelId;
    }

    public String getOpenInstallSubChannelId() {
        return this.openInstallSubChannelId;
    }

    public String getOpenInstallVcode() {
        return this.openInstallVcode;
    }

    public void setOpenInstallChannelId(String str) {
        this.openInstallChannelId = str;
    }

    public void setOpenInstallSubChannelId(String str) {
        this.openInstallSubChannelId = str;
    }

    public void setOpenInstallVcode(String str) {
        this.openInstallVcode = str;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && OpenInstallChannelBean.class == obj.getClass()) {
            return this.openInstallChannelId.equals(((OpenInstallChannelBean) obj).openInstallChannelId);
        }
        return false;
    }

    @RequiresApi(api = 19)
    public int hashCode() {
        return Objects.hashCode(this.openInstallChannelId);
    }
}
