package com.amazonaws.auth.policy;

import org.slf4j.Marker;

/* loaded from: classes2.dex */
public enum Principal$WebIdentityProviders {
    Facebook("graph.facebook.com"),
    Google("accounts.google.com"),
    Amazon("www.amazon.com"),
    AllProviders(Marker.ANY_MARKER);
    
    private String webIdentityProvider;

    Principal$WebIdentityProviders(String str) {
        this.webIdentityProvider = str;
    }

    public String getWebIdentityProvider() {
        return this.webIdentityProvider;
    }

    public static Principal$WebIdentityProviders fromString(String str) {
        Principal$WebIdentityProviders[] values;
        if (str != null) {
            for (Principal$WebIdentityProviders principal$WebIdentityProviders : values()) {
                if (principal$WebIdentityProviders.getWebIdentityProvider().equalsIgnoreCase(str)) {
                    return principal$WebIdentityProviders;
                }
            }
            return null;
        }
        return null;
    }
}
