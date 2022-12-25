package com.squareup.okhttp;

import com.squareup.okhttp.internal.http.HeaderParser;

/* loaded from: classes3.dex */
public final class CacheControl {
    private final boolean isPublic;
    private final int maxAgeSeconds;
    private final int maxStaleSeconds;
    private final int minFreshSeconds;
    private final boolean mustRevalidate;
    private final boolean noCache;
    private final boolean noStore;
    private final boolean onlyIfCached;
    private final int sMaxAgeSeconds;

    private CacheControl(boolean z, boolean z2, int i, int i2, boolean z3, boolean z4, int i3, int i4, boolean z5) {
        this.noCache = z;
        this.noStore = z2;
        this.maxAgeSeconds = i;
        this.sMaxAgeSeconds = i2;
        this.isPublic = z3;
        this.mustRevalidate = z4;
        this.maxStaleSeconds = i3;
        this.minFreshSeconds = i4;
        this.onlyIfCached = z5;
    }

    public boolean noCache() {
        return this.noCache;
    }

    public boolean noStore() {
        return this.noStore;
    }

    public int maxAgeSeconds() {
        return this.maxAgeSeconds;
    }

    public int sMaxAgeSeconds() {
        return this.sMaxAgeSeconds;
    }

    public boolean isPublic() {
        return this.isPublic;
    }

    public boolean mustRevalidate() {
        return this.mustRevalidate;
    }

    public int maxStaleSeconds() {
        return this.maxStaleSeconds;
    }

    public int minFreshSeconds() {
        return this.minFreshSeconds;
    }

    public boolean onlyIfCached() {
        return this.onlyIfCached;
    }

    public static CacheControl parse(Headers headers) {
        int i;
        String str;
        boolean z = false;
        boolean z2 = false;
        int i2 = -1;
        int i3 = -1;
        boolean z3 = false;
        boolean z4 = false;
        int i4 = -1;
        int i5 = -1;
        boolean z5 = false;
        for (int i6 = 0; i6 < headers.size(); i6++) {
            if (headers.name(i6).equalsIgnoreCase("Cache-Control") || headers.name(i6).equalsIgnoreCase("Pragma")) {
                String value = headers.value(i6);
                boolean z6 = z5;
                int i7 = i5;
                int i8 = i4;
                boolean z7 = z4;
                boolean z8 = z3;
                int i9 = i3;
                int i10 = i2;
                boolean z9 = z2;
                boolean z10 = z;
                for (int i11 = 0; i11 < value.length(); i11 = i) {
                    int skipUntil = HeaderParser.skipUntil(value, i11, "=,;");
                    String trim = value.substring(i11, skipUntil).trim();
                    if (skipUntil == value.length() || value.charAt(skipUntil) == ',' || value.charAt(skipUntil) == ';') {
                        i = skipUntil + 1;
                        str = null;
                    } else {
                        int skipWhitespace = HeaderParser.skipWhitespace(value, skipUntil + 1);
                        if (skipWhitespace < value.length() && value.charAt(skipWhitespace) == '\"') {
                            int i12 = skipWhitespace + 1;
                            int skipUntil2 = HeaderParser.skipUntil(value, i12, "\"");
                            str = value.substring(i12, skipUntil2);
                            i = skipUntil2 + 1;
                        } else {
                            i = HeaderParser.skipUntil(value, skipWhitespace, ",;");
                            str = value.substring(skipWhitespace, i).trim();
                        }
                    }
                    if ("no-cache".equalsIgnoreCase(trim)) {
                        z10 = true;
                    } else if ("no-store".equalsIgnoreCase(trim)) {
                        z9 = true;
                    } else if ("max-age".equalsIgnoreCase(trim)) {
                        i10 = HeaderParser.parseSeconds(str);
                    } else if ("s-maxage".equalsIgnoreCase(trim)) {
                        i9 = HeaderParser.parseSeconds(str);
                    } else if ("public".equalsIgnoreCase(trim)) {
                        z8 = true;
                    } else if ("must-revalidate".equalsIgnoreCase(trim)) {
                        z7 = true;
                    } else if ("max-stale".equalsIgnoreCase(trim)) {
                        i8 = HeaderParser.parseSeconds(str);
                    } else if ("min-fresh".equalsIgnoreCase(trim)) {
                        i7 = HeaderParser.parseSeconds(str);
                    } else if ("only-if-cached".equalsIgnoreCase(trim)) {
                        z6 = true;
                    }
                }
                z = z10;
                z2 = z9;
                i2 = i10;
                i3 = i9;
                z3 = z8;
                z4 = z7;
                i4 = i8;
                i5 = i7;
                z5 = z6;
            }
        }
        return new CacheControl(z, z2, i2, i3, z3, z4, i4, i5, z5);
    }
}
