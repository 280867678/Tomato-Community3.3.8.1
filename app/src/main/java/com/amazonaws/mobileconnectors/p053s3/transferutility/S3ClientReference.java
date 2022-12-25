package com.amazonaws.mobileconnectors.p053s3.transferutility;

import com.amazonaws.services.p054s3.AmazonS3;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* renamed from: com.amazonaws.mobileconnectors.s3.transferutility.S3ClientReference */
/* loaded from: classes2.dex */
class S3ClientReference {
    private static Map<Integer, AmazonS3> map = new ConcurrentHashMap();

    public static AmazonS3 get(Integer num) {
        return map.get(num);
    }

    public static void remove(Integer num) {
        map.remove(num);
    }
}
