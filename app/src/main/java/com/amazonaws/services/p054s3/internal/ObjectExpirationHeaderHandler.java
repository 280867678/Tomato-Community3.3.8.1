package com.amazonaws.services.p054s3.internal;

import com.amazonaws.http.HttpResponse;
import com.amazonaws.logging.Log;
import com.amazonaws.logging.LogFactory;
import com.amazonaws.services.p054s3.internal.ObjectExpirationResult;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* renamed from: com.amazonaws.services.s3.internal.ObjectExpirationHeaderHandler */
/* loaded from: classes2.dex */
public class ObjectExpirationHeaderHandler<T extends ObjectExpirationResult> implements HeaderHandler<T> {
    private static final Pattern DATE_PATTERN = Pattern.compile("expiry-date=\"(.*?)\"");
    private static final Pattern RULE_PATTERN = Pattern.compile("rule-id=\"(.*?)\"");
    private static final Log log = LogFactory.getLog(ObjectExpirationHeaderHandler.class);

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.amazonaws.services.p054s3.internal.HeaderHandler
    public /* bridge */ /* synthetic */ void handle(Object obj, HttpResponse httpResponse) {
        handle((ObjectExpirationHeaderHandler<T>) ((ObjectExpirationResult) obj), httpResponse);
    }

    public void handle(T t, HttpResponse httpResponse) {
        String str = httpResponse.getHeaders().get("x-amz-expiration");
        if (str != null) {
            t.setExpirationTime(parseDate(str));
            t.setExpirationTimeRuleId(parseRuleId(str));
        }
    }

    private String parseRuleId(String str) {
        Matcher matcher = RULE_PATTERN.matcher(str);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    private Date parseDate(String str) {
        Matcher matcher = DATE_PATTERN.matcher(str);
        if (matcher.find()) {
            try {
                return ServiceUtils.parseRfc822Date(matcher.group(1));
            } catch (Exception e) {
                log.warn("Error parsing expiry-date from x-amz-expiration header.", e);
                return null;
            }
        }
        return null;
    }
}
