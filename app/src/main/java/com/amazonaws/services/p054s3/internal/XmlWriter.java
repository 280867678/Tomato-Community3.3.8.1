package com.amazonaws.services.p054s3.internal;

import com.amazonaws.util.StringUtils;
import com.j256.ormlite.stmt.query.SimpleComparison;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.amazonaws.services.s3.internal.XmlWriter */
/* loaded from: classes2.dex */
public class XmlWriter {
    List<String> tags = new ArrayList();

    /* renamed from: sb */
    StringBuilder f1177sb = new StringBuilder();

    public XmlWriter start(String str) {
        StringBuilder sb = this.f1177sb;
        sb.append(SimpleComparison.LESS_THAN_OPERATION);
        sb.append(str);
        sb.append(SimpleComparison.GREATER_THAN_OPERATION);
        this.tags.add(str);
        return this;
    }

    public XmlWriter end() {
        List<String> list = this.tags;
        StringBuilder sb = this.f1177sb;
        sb.append("</");
        sb.append(list.remove(list.size() - 1));
        sb.append(SimpleComparison.GREATER_THAN_OPERATION);
        return this;
    }

    public byte[] getBytes() {
        return toString().getBytes(StringUtils.UTF8);
    }

    public String toString() {
        return this.f1177sb.toString();
    }

    public XmlWriter value(String str) {
        appendEscapedString(str, this.f1177sb);
        return this;
    }

    private void appendEscapedString(String str, StringBuilder sb) {
        if (str == null) {
            str = "";
        }
        int length = str.length();
        int i = 0;
        int i2 = 0;
        while (i < length) {
            char charAt = str.charAt(i);
            String str2 = charAt != '\t' ? charAt != '\n' ? charAt != '\r' ? charAt != '\"' ? charAt != '&' ? charAt != '<' ? charAt != '>' ? null : "&gt;" : "&lt;" : "&amp;" : "&quot;" : "&#13;" : "&#10;" : "&#9;";
            if (str2 != null) {
                if (i2 < i) {
                    sb.append((CharSequence) str, i2, i);
                }
                this.f1177sb.append(str2);
                i2 = i + 1;
            }
            i++;
        }
        if (i2 < i) {
            this.f1177sb.append((CharSequence) str, i2, i);
        }
    }
}
