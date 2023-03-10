package com.amazonaws.services.p054s3.internal;

import com.amazonaws.Request;
import com.amazonaws.util.StringUtils;
import com.j256.ormlite.stmt.query.SimpleComparison;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/* renamed from: com.amazonaws.services.s3.internal.RestUtils */
/* loaded from: classes2.dex */
public class RestUtils {
    private static final List<String> SIGNED_PARAMETERS = Arrays.asList("acl", "torrent", "logging", "location", "policy", "requestPayment", "versioning", "versions", "versionId", "notification", "uploadId", "uploads", "partNumber", "website", "delete", "lifecycle", "tagging", "cors", "restore", "replication", "accelerate", "inventory", "analytics", "metrics", "response-cache-control", "response-content-disposition", "response-content-encoding", "response-content-language", "response-content-type", "response-expires");

    public static <T> String makeS3CanonicalString(String str, String str2, Request<T> request, String str3, Collection<String> collection) {
        StringBuilder sb = new StringBuilder();
        sb.append(str + "\n");
        Map<String, String> headers = request.getHeaders();
        TreeMap treeMap = new TreeMap();
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (key != null) {
                    String lowerCase = StringUtils.lowerCase(key);
                    if ("content-type".equals(lowerCase) || "content-md5".equals(lowerCase) || "date".equals(lowerCase) || lowerCase.startsWith("x-amz-")) {
                        treeMap.put(lowerCase, value);
                    }
                }
            }
        }
        if (treeMap.containsKey("x-amz-date")) {
            treeMap.put("date", "");
        }
        if (str3 != null) {
            treeMap.put("date", str3);
        }
        if (!treeMap.containsKey("content-type")) {
            treeMap.put("content-type", "");
        }
        if (!treeMap.containsKey("content-md5")) {
            treeMap.put("content-md5", "");
        }
        for (Map.Entry<String, String> entry2 : request.getParameters().entrySet()) {
            if (entry2.getKey().startsWith("x-amz-")) {
                treeMap.put(entry2.getKey(), entry2.getValue());
            }
        }
        for (Map.Entry entry3 : treeMap.entrySet()) {
            String str4 = (String) entry3.getKey();
            String str5 = (String) entry3.getValue();
            if (str4.startsWith("x-amz-")) {
                sb.append(str4);
                sb.append(':');
                if (str5 != null) {
                    sb.append(str5);
                }
            } else if (str5 != null) {
                sb.append(str5);
            }
            sb.append("\n");
        }
        sb.append(str2);
        String[] strArr = (String[]) request.getParameters().keySet().toArray(new String[request.getParameters().size()]);
        Arrays.sort(strArr);
        char c = '?';
        for (String str6 : strArr) {
            if (SIGNED_PARAMETERS.contains(str6) || (collection != null && collection.contains(str6))) {
                if (sb.length() == 0) {
                    sb.append(c);
                }
                sb.append(str6);
                String str7 = request.getParameters().get(str6);
                if (str7 != null) {
                    sb.append(SimpleComparison.EQUAL_TO_OPERATION);
                    sb.append(str7);
                }
                c = '&';
            }
        }
        return sb.toString();
    }
}
