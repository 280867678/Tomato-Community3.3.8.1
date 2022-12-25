package com.tomatolive.library.http.utils;

import com.tomatolive.library.TomatoLiveSDK;
import java.io.Serializable;

/* loaded from: classes3.dex */
public class EncryptHttpResultModel implements Serializable {
    private int code = 0;
    private EncryptMode data = null;
    private String msg;
    private int status;

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int i) {
        this.status = i;
    }

    public boolean isSuccess() {
        return this.code == 100001 || this.status == 1;
    }

    public String getMessage() {
        return this.msg;
    }

    public void setMessage(String str) {
        this.msg = str;
    }

    public int getCode() {
        return this.code;
    }

    public String getJsonData() {
        try {
            return this.data.getJsonData();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /* loaded from: classes3.dex */
    public static class EncryptMode implements Serializable {
        public String data;
        public String key;

        public String getJsonData() throws Exception {
            return EncryptUtil.DESDecrypt(EncryptUtil.RSADecrypt(TomatoLiveSDK.getSingleton().ENCRYPT_API_KEY, this.key), this.data);
        }
    }
}
