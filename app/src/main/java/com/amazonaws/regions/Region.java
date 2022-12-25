package com.amazonaws.regions;

import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public final class Region {
    private final String domain;
    private final String name;
    private final Map<String, String> serviceEndpoints = new HashMap();
    private final Map<String, Boolean> httpSupport = new HashMap();
    private final Map<String, Boolean> httpsSupport = new HashMap();

    /* JADX INFO: Access modifiers changed from: package-private */
    public Region(String str, String str2) {
        this.name = str;
        if (str2 == null || str2.isEmpty()) {
            this.domain = "amazonaws.com";
        } else {
            this.domain = str2;
        }
    }

    public static Region getRegion(Regions regions) {
        return RegionUtils.getRegion(regions.getName());
    }

    public static Region getRegion(String str) {
        return RegionUtils.getRegion(str);
    }

    public String getName() {
        return this.name;
    }

    public String getDomain() {
        return this.domain;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Map<String, String> getServiceEndpoints() {
        return this.serviceEndpoints;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Map<String, Boolean> getHttpSupport() {
        return this.httpSupport;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Map<String, Boolean> getHttpsSupport() {
        return this.httpsSupport;
    }

    public String getServiceEndpoint(String str) {
        return this.serviceEndpoints.get(str);
    }

    public boolean isServiceSupported(String str) {
        return this.serviceEndpoints.containsKey(str);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Region)) {
            return false;
        }
        return getName().equals(((Region) obj).getName());
    }

    public int hashCode() {
        return getName().hashCode();
    }

    public String toString() {
        return getName();
    }
}
