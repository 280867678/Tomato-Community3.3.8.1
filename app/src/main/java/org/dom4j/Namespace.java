package org.dom4j;

import org.dom4j.tree.AbstractNode;
import org.dom4j.tree.DefaultNamespace;
import org.dom4j.tree.NamespaceCache;

/* loaded from: classes4.dex */
public class Namespace extends AbstractNode {
    private int hashCode;
    private String prefix;
    private String uri;
    protected static final NamespaceCache CACHE = new NamespaceCache();
    public static final Namespace XML_NAMESPACE = CACHE.get("xml", "http://www.w3.org/XML/1998/namespace");
    public static final Namespace NO_NAMESPACE = CACHE.get("", "");

    @Override // org.dom4j.tree.AbstractNode, org.dom4j.Node
    public short getNodeType() {
        return (short) 13;
    }

    public Namespace(String str, String str2) {
        this.prefix = str == null ? "" : str;
        this.uri = str2 == null ? "" : str2;
        if (!this.prefix.isEmpty()) {
            QName.validateNCName(this.prefix);
        }
    }

    public static Namespace get(String str, String str2) {
        return CACHE.get(str, str2);
    }

    public static Namespace get(String str) {
        return CACHE.get(str);
    }

    public int hashCode() {
        if (this.hashCode == 0) {
            this.hashCode = createHashCode();
        }
        return this.hashCode;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int createHashCode() {
        int hashCode = this.uri.hashCode() ^ this.prefix.hashCode();
        if (hashCode == 0) {
            return 47806;
        }
        return hashCode;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Namespace) {
            Namespace namespace = (Namespace) obj;
            return hashCode() == namespace.hashCode() && this.uri.equals(namespace.getURI()) && this.prefix.equals(namespace.getPrefix());
        }
        return false;
    }

    @Override // org.dom4j.tree.AbstractNode, org.dom4j.Node
    public String getText() {
        return this.uri;
    }

    @Override // org.dom4j.tree.AbstractNode, org.dom4j.Node
    public String getStringValue() {
        return this.uri;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public String getURI() {
        return this.uri;
    }

    public String getXPathNameStep() {
        String str = this.prefix;
        if (str == null || "".equals(str)) {
            return "namespace::*[name()='']";
        }
        return "namespace::" + this.prefix;
    }

    @Override // org.dom4j.Node
    public String getPath(Element element) {
        StringBuffer stringBuffer = new StringBuffer(10);
        Element parent = getParent();
        if (parent != null && parent != element) {
            stringBuffer.append(parent.getPath(element));
            stringBuffer.append('/');
        }
        stringBuffer.append(getXPathNameStep());
        return stringBuffer.toString();
    }

    @Override // org.dom4j.Node
    public String getUniquePath(Element element) {
        StringBuffer stringBuffer = new StringBuffer(10);
        Element parent = getParent();
        if (parent != null && parent != element) {
            stringBuffer.append(parent.getUniquePath(element));
            stringBuffer.append('/');
        }
        stringBuffer.append(getXPathNameStep());
        return stringBuffer.toString();
    }

    public String toString() {
        return super.toString() + " [Namespace: prefix " + getPrefix() + " mapped to URI \"" + getURI() + "\"]";
    }

    @Override // org.dom4j.Node
    public String asXML() {
        StringBuffer stringBuffer = new StringBuffer(10);
        String prefix = getPrefix();
        if (prefix != null && prefix.length() > 0) {
            stringBuffer.append("xmlns:");
            stringBuffer.append(prefix);
            stringBuffer.append("=\"");
        } else {
            stringBuffer.append("xmlns=\"");
        }
        stringBuffer.append(getURI());
        stringBuffer.append("\"");
        return stringBuffer.toString();
    }

    @Override // org.dom4j.Node
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override // org.dom4j.tree.AbstractNode
    protected Node createXPathResult(Element element) {
        return new DefaultNamespace(element, getPrefix(), getURI());
    }
}
