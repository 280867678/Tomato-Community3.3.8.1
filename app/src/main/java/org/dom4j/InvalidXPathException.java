package org.dom4j;

import com.tomatolive.library.utils.ConstantUtils;

/* loaded from: classes4.dex */
public class InvalidXPathException extends IllegalArgumentException {
    public InvalidXPathException(String str) {
        super("Invalid XPath expression: " + str);
    }

    public InvalidXPathException(String str, String str2) {
        super("Invalid XPath expression: " + str + ConstantUtils.PLACEHOLDER_STR_ONE + str2);
    }
}
