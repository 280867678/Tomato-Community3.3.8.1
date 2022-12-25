package org.dom4j;

import com.tomatolive.library.utils.ConstantUtils;

/* loaded from: classes4.dex */
public class XPathException extends RuntimeException {
    private String xpath;

    public XPathException(String str) {
        super("Exception occurred evaluting XPath: " + str);
        this.xpath = str;
    }

    public XPathException(String str, String str2) {
        super("Exception occurred evaluting XPath: " + str + ConstantUtils.PLACEHOLDER_STR_ONE + str2);
        this.xpath = str;
    }

    public XPathException(String str, Exception exc) {
        super("Exception occurred evaluting XPath: " + str + ". Exception: " + exc.getMessage());
        this.xpath = str;
    }

    public String getXPath() {
        return this.xpath;
    }
}
