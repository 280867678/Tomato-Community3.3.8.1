package com.youdao.sdk.common;

import android.content.Context;
import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import com.youdao.sdk.app.other.AbstractC5171t;
import com.youdao.sdk.app.other.C5165h;
import com.youdao.sdk.app.other.C5173v;
import java.util.Map;

/* loaded from: classes4.dex */
public class YDUrlGenerator extends AbstractC5171t {
    public YDUrlGenerator(Context context) {
        super(context);
    }

    public YDUrlGenerator withAppKey(String str) {
        this.appKey = str;
        return this;
    }

    public Map<String, String> generateUrlMap() {
        setAppKey(this.appKey);
        setKeywords(this.mKeywords);
        C5165h m208a = C5165h.m208a(this.mContext);
        setSdkVersion(m208a.m190o());
        setApiVersion(m208a.m189p());
        setDeviceInfo(m208a.m193l(), m208a.m192m(), m208a.m191n());
        setUdid(m208a.m196i());
        setAUid(m208a.m195j());
        setTimezone(C5173v.m175c());
        setOrientation(m208a.m209a());
        setDensity(m208a.m201e());
        String m199f = m208a.m199f();
        setMccCode(m199f);
        setMncCode(m199f);
        setImei(m208a.m205c());
        setIsoCountryCode(m208a.m198g());
        setCarrierName(m208a.m197h());
        setNetworkType(m208a.m207b());
        setDetailNetworkType(m208a.m203d());
        setAppVersion(m208a.m188q());
        setPackage(m208a.m187r());
        setOsType();
        setWifi();
        setScreen(m208a.m194k());
        return this.parameters;
    }

    protected void setOsType() {
        addParam("osType", "Android");
    }

    protected void setScreen(String str) {
        addParam("screen", str);
    }

    protected void setSdkVersion(String str) {
        addParam("sdkversion", str);
    }

    protected void setApiVersion(String str) {
        addParam(DatabaseFieldConfigLoader.FIELD_NAME_VERSION, str);
    }
}
