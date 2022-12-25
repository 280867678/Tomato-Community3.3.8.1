package com.tomatolive.library.utils.emoji;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.List;

/* loaded from: classes4.dex */
public class Emoji {
    private final List<String> aliases;
    private final String description;
    private final String htmlDec;
    private final String htmlHex;
    private final boolean supportsFitzpatrick;
    private final List<String> tags;
    private final String unicode;

    /* JADX INFO: Access modifiers changed from: protected */
    public Emoji(String str, boolean z, List<String> list, List<String> list2, byte... bArr) {
        this.description = str;
        this.supportsFitzpatrick = z;
        this.aliases = Collections.unmodifiableList(list);
        this.tags = Collections.unmodifiableList(list2);
        try {
            this.unicode = new String(bArr, "UTF-8");
            int length = getUnicode().length();
            String[] strArr = new String[length];
            String[] strArr2 = new String[length];
            int i = 0;
            int i2 = 0;
            while (i < length) {
                int codePointAt = getUnicode().codePointAt(i);
                strArr[i2] = String.format("&#%d;", Integer.valueOf(codePointAt));
                strArr2[i2] = String.format("&#x%x;", Integer.valueOf(codePointAt));
                i += Character.charCount(codePointAt);
                i2++;
            }
            this.htmlDec = stringJoin(strArr, i2);
            this.htmlHex = stringJoin(strArr2, i2);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private String stringJoin(String[] strArr, int i) {
        String str = "";
        for (int i2 = 0; i2 < i; i2++) {
            str = str + strArr[i2];
        }
        return str;
    }

    public String getDescription() {
        return this.description;
    }

    public boolean supportsFitzpatrick() {
        return this.supportsFitzpatrick;
    }

    public List<String> getAliases() {
        return this.aliases;
    }

    public List<String> getTags() {
        return this.tags;
    }

    public String getUnicode() {
        return this.unicode;
    }

    public String getUnicode(Fitzpatrick fitzpatrick) {
        if (supportsFitzpatrick()) {
            if (fitzpatrick == null) {
                return getUnicode();
            }
            return getUnicode() + fitzpatrick.unicode;
        }
        throw new UnsupportedOperationException("Cannot get the unicode with a fitzpatrick modifier, the emoji doesn't support fitzpatrick.");
    }

    public String getHtmlDecimal() {
        return this.htmlDec;
    }

    public String getHtmlHexidecimal() {
        return getHtmlHexadecimal();
    }

    public String getHtmlHexadecimal() {
        return this.htmlHex;
    }

    public boolean equals(Object obj) {
        return obj != null && (obj instanceof Emoji) && ((Emoji) obj).getUnicode().equals(getUnicode());
    }

    public int hashCode() {
        return this.unicode.hashCode();
    }

    public String toString() {
        return "Emoji{description='" + this.description + "', supportsFitzpatrick=" + this.supportsFitzpatrick + ", aliases=" + this.aliases + ", tags=" + this.tags + ", unicode='" + this.unicode + "', htmlDec='" + this.htmlDec + "', htmlHex='" + this.htmlHex + "'}";
    }
}
