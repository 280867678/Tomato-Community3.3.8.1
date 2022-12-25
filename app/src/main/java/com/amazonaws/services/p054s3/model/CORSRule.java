package com.amazonaws.services.p054s3.model;

import java.util.List;

/* renamed from: com.amazonaws.services.s3.model.CORSRule */
/* loaded from: classes2.dex */
public class CORSRule {
    public void setAllowedHeaders(List<String> list) {
    }

    public void setAllowedMethods(List<AllowedMethods> list) {
    }

    public void setAllowedOrigins(List<String> list) {
    }

    public void setExposedHeaders(List<String> list) {
    }

    public void setId(String str) {
    }

    public void setMaxAgeSeconds(int i) {
    }

    /* renamed from: com.amazonaws.services.s3.model.CORSRule$AllowedMethods */
    /* loaded from: classes2.dex */
    public enum AllowedMethods {
        GET("GET"),
        PUT("PUT"),
        HEAD("HEAD"),
        POST("POST"),
        DELETE("DELETE");
        
        private final String AllowedMethod;

        AllowedMethods(String str) {
            this.AllowedMethod = str;
        }

        @Override // java.lang.Enum
        public String toString() {
            return this.AllowedMethod;
        }

        public static AllowedMethods fromValue(String str) throws IllegalArgumentException {
            AllowedMethods[] values;
            for (AllowedMethods allowedMethods : values()) {
                String allowedMethods2 = allowedMethods.toString();
                if (allowedMethods2 == null && str == null) {
                    return allowedMethods;
                }
                if (allowedMethods2 != null && allowedMethods2.equals(str)) {
                    return allowedMethods;
                }
            }
            throw new IllegalArgumentException("Cannot create enum from " + str + " value!");
        }
    }
}
