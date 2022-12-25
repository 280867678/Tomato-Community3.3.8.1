package com.amazonaws.services.p054s3.model;

import com.amazonaws.services.p054s3.model.analytics.AnalyticsConfiguration;
import java.io.Serializable;

/* renamed from: com.amazonaws.services.s3.model.GetBucketAnalyticsConfigurationResult */
/* loaded from: classes2.dex */
public class GetBucketAnalyticsConfigurationResult implements Serializable {
    private AnalyticsConfiguration analyticsConfiguration;

    public AnalyticsConfiguration getAnalyticsConfiguration() {
        return this.analyticsConfiguration;
    }

    public void setAnalyticsConfiguration(AnalyticsConfiguration analyticsConfiguration) {
        this.analyticsConfiguration = analyticsConfiguration;
    }

    public GetBucketAnalyticsConfigurationResult withAnalyticsConfiguration(AnalyticsConfiguration analyticsConfiguration) {
        setAnalyticsConfiguration(analyticsConfiguration);
        return this;
    }
}
