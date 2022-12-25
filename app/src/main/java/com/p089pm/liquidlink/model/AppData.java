package com.p089pm.liquidlink.model;

import android.text.TextUtils;
import java.io.Serializable;

/* renamed from: com.pm.liquidlink.model.AppData */
/* loaded from: classes3.dex */
public class AppData implements Serializable {
    public String channel = "";
    public String data = "";

    public String getChannel() {
        return this.channel;
    }

    public String getData() {
        return this.data;
    }

    public boolean isEmpty() {
        return TextUtils.isEmpty(getChannel()) && TextUtils.isEmpty(getData());
    }

    public void setChannel(String str) {
        this.channel = str;
    }

    public void setData(String str) {
        this.data = str;
    }

    public String toString() {
        return "{\"channel\":\"" + this.channel + "\",\"data\":\"" + this.data + "\"}";
    }
}
