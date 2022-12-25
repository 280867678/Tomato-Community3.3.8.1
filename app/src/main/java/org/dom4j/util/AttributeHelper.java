package org.dom4j.util;

import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.QName;

/* loaded from: classes4.dex */
public class AttributeHelper {
    protected AttributeHelper() {
    }

    public static boolean booleanValue(Element element, String str) {
        return booleanValue(element.mo6817attribute(str));
    }

    public static boolean booleanValue(Element element, QName qName) {
        return booleanValue(element.mo6818attribute(qName));
    }

    protected static boolean booleanValue(Attribute attribute) {
        Object data;
        if (attribute == null || (data = attribute.getData()) == null) {
            return false;
        }
        if (data instanceof Boolean) {
            return ((Boolean) data).booleanValue();
        }
        return "true".equalsIgnoreCase(data.toString());
    }
}
