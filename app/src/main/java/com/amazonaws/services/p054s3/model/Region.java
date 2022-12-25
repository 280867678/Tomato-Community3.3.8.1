package com.amazonaws.services.p054s3.model;

import com.amazonaws.regions.RegionUtils;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/* renamed from: com.amazonaws.services.s3.model.Region */
/* loaded from: classes2.dex */
public enum Region {
    US_Standard(null),
    US_East_2("us-east-2"),
    US_West("us-west-1"),
    US_West_2("us-west-2"),
    US_GovCloud("s3-us-gov-west-1"),
    US_Gov_East_1("s3-us-gov-east-1"),
    EU_Ireland("eu-west-1", "EU"),
    EU_London("eu-west-2"),
    EU_Paris("eu-west-3"),
    EU_Frankfurt("eu-central-1"),
    EU_Stockholm("eu-north-1"),
    AP_Mumbai("ap-south-1"),
    AP_Singapore("ap-southeast-1"),
    AP_Sydney("ap-southeast-2"),
    AP_Tokyo("ap-northeast-1"),
    AP_Seoul("ap-northeast-2"),
    SA_SaoPaulo("sa-east-1"),
    CA_Montreal("ca-central-1"),
    CN_Beijing("cn-north-1"),
    CN_Ningxia("cn-northwest-1");
    
    public static final Pattern S3_REGIONAL_ENDPOINT_PATTERN = Pattern.compile("s3[-.]([^.]+)\\.amazonaws\\.com(\\.[^.]*)?");
    private final List<String> regionIds;

    Region(String... strArr) {
        this.regionIds = strArr != null ? Arrays.asList(strArr) : null;
    }

    @Override // java.lang.Enum
    public String toString() {
        return getFirstRegionId0();
    }

    public String getFirstRegionId() {
        return getFirstRegionId0();
    }

    private String getFirstRegionId0() {
        List<String> list = this.regionIds;
        if (list == null || list.size() == 0) {
            return null;
        }
        return this.regionIds.get(0);
    }

    public static Region fromValue(String str) throws IllegalArgumentException {
        Region[] values;
        if (str == null || str.equals("US") || str.equals("us-east-1")) {
            return US_Standard;
        }
        for (Region region : values()) {
            List<String> list = region.regionIds;
            if (list != null && list.contains(str)) {
                return region;
            }
        }
        throw new IllegalArgumentException("Cannot create enum from " + str + " value!");
    }

    public com.amazonaws.regions.Region toAWSRegion() {
        String firstRegionId = getFirstRegionId();
        if (firstRegionId == null) {
            return RegionUtils.getRegionByEndpoint("s3.amazonaws.com");
        }
        return RegionUtils.getRegion(firstRegionId);
    }
}
