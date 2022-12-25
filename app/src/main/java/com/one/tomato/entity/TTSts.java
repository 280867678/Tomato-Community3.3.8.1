package com.one.tomato.entity;

/* loaded from: classes3.dex */
public class TTSts {
    public static String KEY_ONLINE = "t2dTQu6P6B2jFEv8";
    public static String KEY_TEST = "935wpOsnDGrlpd6W";
    private String bucketName;
    private String secJson;

    public String getKey() {
        return KEY_ONLINE;
    }

    public String getBucketName() {
        return this.bucketName;
    }

    public void setBucketName(String str) {
        this.bucketName = str;
    }

    public String getSecJson() {
        return this.secJson;
    }

    public void setSecJson(String str) {
        this.secJson = str;
    }

    /* loaded from: classes3.dex */
    public static class TTKeys {
        private String accessKey;
        private String secretKey;
        private String token;
        private String userId;

        public String getSecretKey() {
            return this.secretKey;
        }

        public void setSecretKey(String str) {
            this.secretKey = str;
        }

        public String getAccessKey() {
            return this.accessKey;
        }

        public void setAccessKey(String str) {
            this.accessKey = str;
        }

        public String getUserId() {
            return this.userId;
        }

        public void setUserId(String str) {
            this.userId = str;
        }

        public String getToken() {
            return this.token;
        }

        public void setToken(String str) {
            this.token = str;
        }
    }
}
