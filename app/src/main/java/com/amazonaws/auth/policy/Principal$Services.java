package com.amazonaws.auth.policy;

import org.slf4j.Marker;

/* loaded from: classes2.dex */
public enum Principal$Services {
    AWSDataPipeline("datapipeline.amazonaws.com"),
    AmazonElasticTranscoder("elastictranscoder.amazonaws.com"),
    AmazonEC2("ec2.amazonaws.com"),
    AWSOpsWorks("opsworks.amazonaws.com"),
    AWSCloudHSM("cloudhsm.amazonaws.com"),
    AllServices(Marker.ANY_MARKER);
    
    private String serviceId;

    Principal$Services(String str) {
        this.serviceId = str;
    }

    public String getServiceId() {
        return this.serviceId;
    }

    public static Principal$Services fromString(String str) {
        Principal$Services[] values;
        if (str != null) {
            for (Principal$Services principal$Services : values()) {
                if (principal$Services.getServiceId().equalsIgnoreCase(str)) {
                    return principal$Services;
                }
            }
            return null;
        }
        return null;
    }
}
