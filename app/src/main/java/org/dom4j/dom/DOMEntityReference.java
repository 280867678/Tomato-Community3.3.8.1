package org.dom4j.dom;

import org.dom4j.Element;
import org.dom4j.tree.DefaultEntity;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.EntityReference;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.UserDataHandler;

/* loaded from: classes4.dex */
public class DOMEntityReference extends DefaultEntity implements EntityReference {
    @Override // org.w3c.dom.Node
    public NamedNodeMap getAttributes() {
        return null;
    }

    @Override // org.w3c.dom.Node
    public String getNodeValue() throws DOMException {
        return null;
    }

    @Override // org.w3c.dom.Node
    public void setNodeValue(String str) throws DOMException {
    }

    public DOMEntityReference(String str) {
        super(str);
    }

    public DOMEntityReference(String str, String str2) {
        super(str, str2);
    }

    public DOMEntityReference(Element element, String str, String str2) {
        super(element, str, str2);
    }

    public boolean supports(String str, String str2) {
        return DOMNodeHelper.supports(this, str, str2);
    }

    @Override // org.w3c.dom.Node
    public String getNamespaceURI() {
        return DOMNodeHelper.getNamespaceURI(this);
    }

    @Override // org.w3c.dom.Node
    public String getPrefix() {
        return DOMNodeHelper.getPrefix(this);
    }

    @Override // org.w3c.dom.Node
    public void setPrefix(String str) throws DOMException {
        DOMNodeHelper.setPrefix(this, str);
    }

    @Override // org.w3c.dom.Node
    public String getLocalName() {
        return DOMNodeHelper.getLocalName(this);
    }

    @Override // org.w3c.dom.Node
    public String getNodeName() {
        return getName();
    }

    @Override // org.w3c.dom.Node
    public Node getParentNode() {
        return DOMNodeHelper.getParentNode(this);
    }

    @Override // org.w3c.dom.Node
    public NodeList getChildNodes() {
        return DOMNodeHelper.getChildNodes(this);
    }

    @Override // org.w3c.dom.Node
    public Node getFirstChild() {
        return DOMNodeHelper.getFirstChild(this);
    }

    @Override // org.w3c.dom.Node
    public Node getLastChild() {
        return DOMNodeHelper.getLastChild(this);
    }

    @Override // org.w3c.dom.Node
    public Node getPreviousSibling() {
        return DOMNodeHelper.getPreviousSibling(this);
    }

    @Override // org.w3c.dom.Node
    public Node getNextSibling() {
        return DOMNodeHelper.getNextSibling(this);
    }

    @Override // org.w3c.dom.Node
    public Document getOwnerDocument() {
        return DOMNodeHelper.getOwnerDocument(this);
    }

    @Override // org.w3c.dom.Node
    public Node insertBefore(Node node, Node node2) throws DOMException {
        checkNewChildNode(node);
        return DOMNodeHelper.insertBefore(this, node, node2);
    }

    @Override // org.w3c.dom.Node
    public Node replaceChild(Node node, Node node2) throws DOMException {
        checkNewChildNode(node);
        return DOMNodeHelper.replaceChild(this, node, node2);
    }

    @Override // org.w3c.dom.Node
    public Node removeChild(Node node) throws DOMException {
        return DOMNodeHelper.removeChild(this, node);
    }

    @Override // org.w3c.dom.Node
    public Node appendChild(Node node) throws DOMException {
        checkNewChildNode(node);
        return DOMNodeHelper.appendChild(this, node);
    }

    private void checkNewChildNode(Node node) throws DOMException {
        short nodeType = node.getNodeType();
        if (nodeType == 1 || nodeType == 3 || nodeType == 8 || nodeType == 7 || nodeType == 4 || nodeType == 5) {
            return;
        }
        throw new DOMException((short) 3, "Given node cannot be a child of an entity reference");
    }

    @Override // org.w3c.dom.Node
    public boolean hasChildNodes() {
        return DOMNodeHelper.hasChildNodes(this);
    }

    @Override // org.w3c.dom.Node
    public Node cloneNode(boolean z) {
        return DOMNodeHelper.cloneNode(this, z);
    }

    @Override // org.w3c.dom.Node
    public void normalize() {
        DOMNodeHelper.normalize(this);
    }

    @Override // org.w3c.dom.Node
    public boolean isSupported(String str, String str2) {
        return DOMNodeHelper.isSupported(this, str, str2);
    }

    @Override // org.w3c.dom.Node
    public boolean hasAttributes() {
        return DOMNodeHelper.hasAttributes(this);
    }

    @Override // org.w3c.dom.Node
    public String getBaseURI() {
        DOMNodeHelper.notSupported();
        return null;
    }

    @Override // org.w3c.dom.Node
    public short compareDocumentPosition(Node node) throws DOMException {
        DOMNodeHelper.notSupported();
        return (short) 0;
    }

    @Override // org.w3c.dom.Node
    public String getTextContent() throws DOMException {
        DOMNodeHelper.notSupported();
        return null;
    }

    @Override // org.w3c.dom.Node
    public void setTextContent(String str) throws DOMException {
        DOMNodeHelper.notSupported();
    }

    @Override // org.w3c.dom.Node
    public boolean isSameNode(Node node) {
        DOMNodeHelper.notSupported();
        return false;
    }

    @Override // org.w3c.dom.Node
    public String lookupPrefix(String str) {
        DOMNodeHelper.notSupported();
        return null;
    }

    @Override // org.w3c.dom.Node
    public boolean isDefaultNamespace(String str) {
        DOMNodeHelper.notSupported();
        return false;
    }

    @Override // org.w3c.dom.Node
    public String lookupNamespaceURI(String str) {
        DOMNodeHelper.notSupported();
        return null;
    }

    @Override // org.w3c.dom.Node
    public boolean isEqualNode(Node node) {
        DOMNodeHelper.notSupported();
        return false;
    }

    @Override // org.w3c.dom.Node
    public Object getFeature(String str, String str2) {
        DOMNodeHelper.notSupported();
        return null;
    }

    @Override // org.w3c.dom.Node
    public Object setUserData(String str, Object obj, UserDataHandler userDataHandler) {
        DOMNodeHelper.notSupported();
        return null;
    }

    @Override // org.w3c.dom.Node
    public Object getUserData(String str) {
        DOMNodeHelper.notSupported();
        return null;
    }
}
