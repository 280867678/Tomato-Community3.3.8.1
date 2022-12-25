package org.dom4j.dom;

import org.dom4j.Element;
import org.dom4j.tree.DefaultNamespace;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.UserDataHandler;

/* loaded from: classes4.dex */
public class DOMNamespace extends DefaultNamespace implements Node {
    public DOMNamespace(String str, String str2) {
        super(str, str2);
    }

    public DOMNamespace(Element element, String str, String str2) {
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
    public String getNodeValue() throws DOMException {
        return DOMNodeHelper.getNodeValue(this);
    }

    @Override // org.w3c.dom.Node
    public void setNodeValue(String str) throws DOMException {
        DOMNodeHelper.setNodeValue(this, str);
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
    public NamedNodeMap getAttributes() {
        return DOMNodeHelper.getAttributes(this);
    }

    @Override // org.w3c.dom.Node
    public Document getOwnerDocument() {
        return DOMNodeHelper.getOwnerDocument(this);
    }

    @Override // org.w3c.dom.Node
    public Node insertBefore(Node node, Node node2) throws DOMException {
        return DOMNodeHelper.insertBefore(this, node, node2);
    }

    @Override // org.w3c.dom.Node
    public Node replaceChild(Node node, Node node2) throws DOMException {
        return DOMNodeHelper.replaceChild(this, node, node2);
    }

    @Override // org.w3c.dom.Node
    public Node removeChild(Node node) throws DOMException {
        return DOMNodeHelper.removeChild(this, node);
    }

    @Override // org.w3c.dom.Node
    public Node appendChild(Node node) throws DOMException {
        return DOMNodeHelper.appendChild(this, node);
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
        return DOMNodeHelper.isNodeSame(this, node);
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
        return DOMNodeHelper.isNodeEquals(this, node);
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
