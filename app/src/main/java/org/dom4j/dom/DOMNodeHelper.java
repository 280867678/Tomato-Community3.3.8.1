package org.dom4j.dom;

import java.io.PrintStream;
import java.util.List;
import org.dom4j.Branch;
import org.dom4j.CharacterData;
import org.dom4j.Element;
import org.slf4j.Marker;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

/* loaded from: classes4.dex */
public class DOMNodeHelper {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final NodeList EMPTY_NODE_LIST = new EmptyNodeList();

    /* loaded from: classes4.dex */
    public static class EmptyNodeList implements NodeList {
        @Override // org.w3c.dom.NodeList
        public int getLength() {
            return 0;
        }

        @Override // org.w3c.dom.NodeList
        public Node item(int i) {
            return null;
        }
    }

    public static NamedNodeMap getAttributes(org.dom4j.Node node) {
        return null;
    }

    public static Node getFirstChild(org.dom4j.Node node) {
        return null;
    }

    public static Node getLastChild(org.dom4j.Node node) {
        return null;
    }

    public static String getLocalName(org.dom4j.Node node) {
        return null;
    }

    public static String getNamespaceURI(org.dom4j.Node node) {
        return null;
    }

    public static String getPrefix(org.dom4j.Node node) {
        return null;
    }

    public static boolean hasChildNodes(org.dom4j.Node node) {
        return false;
    }

    public static boolean isNodeSame(Node node, Node node2) {
        return node == node2;
    }

    public static boolean isSupported(org.dom4j.Node node, String str, String str2) {
        return false;
    }

    public static boolean supports(org.dom4j.Node node, String str, String str2) {
        return false;
    }

    protected DOMNodeHelper() {
    }

    public static void setPrefix(org.dom4j.Node node, String str) throws DOMException {
        notSupported();
    }

    public static String getNodeValue(org.dom4j.Node node) throws DOMException {
        return node.getText();
    }

    public static void setNodeValue(org.dom4j.Node node, String str) throws DOMException {
        node.setText(str);
    }

    public static Node getParentNode(org.dom4j.Node node) {
        return asDOMNode(node.getParent());
    }

    public static NodeList getChildNodes(org.dom4j.Node node) {
        return EMPTY_NODE_LIST;
    }

    public static Node getPreviousSibling(org.dom4j.Node node) {
        int indexOf;
        Element parent = node.getParent();
        if (parent == null || (indexOf = parent.indexOf(node)) <= 0) {
            return null;
        }
        return asDOMNode(parent.node(indexOf - 1));
    }

    public static Node getNextSibling(org.dom4j.Node node) {
        int indexOf;
        int i;
        Element parent = node.getParent();
        if (parent == null || (indexOf = parent.indexOf(node)) < 0 || (i = indexOf + 1) >= parent.nodeCount()) {
            return null;
        }
        return asDOMNode(parent.node(i));
    }

    public static Document getOwnerDocument(org.dom4j.Node node) {
        return asDOMDocument(node.getDocument());
    }

    public static Node insertBefore(org.dom4j.Node node, Node node2, Node node3) throws DOMException {
        if (node instanceof Branch) {
            Branch branch = (Branch) node;
            List<org.dom4j.Node> content = branch.content();
            int indexOf = content.indexOf(node3);
            if (indexOf < 0) {
                branch.add((org.dom4j.Node) node2);
            } else {
                content.add(indexOf, (org.dom4j.Node) node2);
            }
            return node2;
        }
        throw new DOMException((short) 3, "Children not allowed for this node: " + node);
    }

    public static Node replaceChild(org.dom4j.Node node, Node node2, Node node3) throws DOMException {
        if (node instanceof Branch) {
            List<org.dom4j.Node> content = ((Branch) node).content();
            int indexOf = content.indexOf(node3);
            if (indexOf < 0) {
                throw new DOMException((short) 8, "Tried to replace a non existing child for node: " + node);
            }
            content.set(indexOf, (org.dom4j.Node) node2);
            return node3;
        }
        throw new DOMException((short) 3, "Children not allowed for this node: " + node);
    }

    public static Node removeChild(org.dom4j.Node node, Node node2) throws DOMException {
        if (node instanceof Branch) {
            ((Branch) node).remove((org.dom4j.Node) node2);
            return node2;
        }
        throw new DOMException((short) 3, "Children not allowed for this node: " + node);
    }

    public static Node appendChild(org.dom4j.Node node, Node node2) throws DOMException {
        if (node instanceof Branch) {
            Branch branch = (Branch) node;
            Node parentNode = node2.getParentNode();
            if (parentNode != null) {
                parentNode.removeChild(node2);
            }
            branch.add((org.dom4j.Node) node2);
            return node2;
        }
        throw new DOMException((short) 3, "Children not allowed for this node: " + node);
    }

    public static Node cloneNode(org.dom4j.Node node, boolean z) {
        return asDOMNode((org.dom4j.Node) node.clone());
    }

    public static void normalize(org.dom4j.Node node) {
        notSupported();
    }

    public static boolean hasAttributes(org.dom4j.Node node) {
        return node != null && (node instanceof Element) && ((Element) node).attributeCount() > 0;
    }

    public static String getData(CharacterData characterData) throws DOMException {
        return characterData.getText();
    }

    public static void setData(CharacterData characterData, String str) throws DOMException {
        characterData.setText(str);
    }

    public static int getLength(CharacterData characterData) {
        String text = characterData.getText();
        if (text != null) {
            return text.length();
        }
        return 0;
    }

    public static String substringData(CharacterData characterData, int i, int i2) throws DOMException {
        if (i2 < 0) {
            throw new DOMException((short) 1, "Illegal value for count: " + i2);
        }
        String text = characterData.getText();
        int length = text != null ? text.length() : 0;
        if (i < 0 || i >= length) {
            throw new DOMException((short) 1, "No text at offset: " + i);
        }
        int i3 = i2 + i;
        if (i3 > length) {
            return text.substring(i);
        }
        return text.substring(i, i3);
    }

    public static void appendData(CharacterData characterData, String str) throws DOMException {
        if (characterData.isReadOnly()) {
            throw new DOMException((short) 7, "CharacterData node is read only: " + characterData);
        }
        String text = characterData.getText();
        if (text == null) {
            characterData.setText(str);
            return;
        }
        characterData.setText(text + str);
    }

    public static void insertData(CharacterData characterData, int i, String str) throws DOMException {
        if (characterData.isReadOnly()) {
            throw new DOMException((short) 7, "CharacterData node is read only: " + characterData);
        }
        String text = characterData.getText();
        if (text == null) {
            characterData.setText(str);
            return;
        }
        int length = text.length();
        if (i < 0 || i > length) {
            throw new DOMException((short) 1, "No text at offset: " + i);
        }
        StringBuilder sb = new StringBuilder(text);
        sb.insert(i, str);
        characterData.setText(sb.toString());
    }

    public static void deleteData(CharacterData characterData, int i, int i2) throws DOMException {
        if (characterData.isReadOnly()) {
            throw new DOMException((short) 7, "CharacterData node is read only: " + characterData);
        } else if (i2 < 0) {
            throw new DOMException((short) 1, "Illegal value for count: " + i2);
        } else {
            String text = characterData.getText();
            if (text == null) {
                return;
            }
            int length = text.length();
            if (i < 0 || i >= length) {
                throw new DOMException((short) 1, "No text at offset: " + i);
            }
            StringBuilder sb = new StringBuilder(text);
            sb.delete(i, i2 + i);
            characterData.setText(sb.toString());
        }
    }

    public static void replaceData(CharacterData characterData, int i, int i2, String str) throws DOMException {
        if (characterData.isReadOnly()) {
            throw new DOMException((short) 7, "CharacterData node is read only: " + characterData);
        } else if (i2 < 0) {
            throw new DOMException((short) 1, "Illegal value for count: " + i2);
        } else {
            String text = characterData.getText();
            if (text == null) {
                return;
            }
            int length = text.length();
            if (i < 0 || i >= length) {
                throw new DOMException((short) 1, "No text at offset: " + i);
            }
            StringBuilder sb = new StringBuilder(text);
            sb.replace(i, i2 + i, str);
            characterData.setText(sb.toString());
        }
    }

    public static void appendElementsByTagName(List<? super Element> list, Branch branch, String str) {
        boolean equals = Marker.ANY_MARKER.equals(str);
        int nodeCount = branch.nodeCount();
        for (int i = 0; i < nodeCount; i++) {
            org.dom4j.Node node = branch.node(i);
            if (node instanceof Element) {
                Element element = (Element) node;
                if (equals || str.equals(element.getName())) {
                    list.add(element);
                }
                appendElementsByTagName(list, element, str);
            }
        }
    }

    public static void appendElementsByTagNameNS(List<? super Element> list, Branch branch, String str, String str2) {
        boolean equals = Marker.ANY_MARKER.equals(str);
        boolean equals2 = Marker.ANY_MARKER.equals(str2);
        int nodeCount = branch.nodeCount();
        for (int i = 0; i < nodeCount; i++) {
            org.dom4j.Node node = branch.node(i);
            if (node instanceof Element) {
                Element element = (Element) node;
                if ((equals || (((str == null || str.length() == 0) && (element.getNamespaceURI() == null || element.getNamespaceURI().length() == 0)) || (str != null && str.equals(element.getNamespaceURI())))) && (equals2 || str2.equals(element.getName()))) {
                    list.add(element);
                }
                appendElementsByTagNameNS(list, element, str, str2);
            }
        }
    }

    public static NodeList createNodeList(final List<org.dom4j.Node> list) {
        return new NodeList() { // from class: org.dom4j.dom.DOMNodeHelper.1
            @Override // org.w3c.dom.NodeList
            public Node item(int i) {
                if (i >= getLength()) {
                    return null;
                }
                return DOMNodeHelper.asDOMNode((org.dom4j.Node) list.get(i));
            }

            @Override // org.w3c.dom.NodeList
            public int getLength() {
                return list.size();
            }
        };
    }

    public static Node asDOMNode(org.dom4j.Node node) {
        if (node == null) {
            return null;
        }
        if (node instanceof Node) {
            return (Node) node;
        }
        PrintStream printStream = System.out;
        printStream.println("Cannot convert: " + node + " into a W3C DOM Node");
        notSupported();
        return null;
    }

    public static Document asDOMDocument(org.dom4j.Document document) {
        if (document == null) {
            return null;
        }
        if (document instanceof Document) {
            return (Document) document;
        }
        notSupported();
        return null;
    }

    public static DocumentType asDOMDocumentType(org.dom4j.DocumentType documentType) {
        if (documentType == null) {
            return null;
        }
        if (documentType instanceof DocumentType) {
            return (DocumentType) documentType;
        }
        notSupported();
        return null;
    }

    public static Text asDOMText(CharacterData characterData) {
        if (characterData == null) {
            return null;
        }
        if (characterData instanceof Text) {
            return (Text) characterData;
        }
        notSupported();
        return null;
    }

    public static org.w3c.dom.Element asDOMElement(org.dom4j.Node node) {
        if (node == null) {
            return null;
        }
        if (node instanceof org.w3c.dom.Element) {
            return (org.w3c.dom.Element) node;
        }
        notSupported();
        return null;
    }

    public static Attr asDOMAttr(org.dom4j.Node node) {
        if (node == null) {
            return null;
        }
        if (node instanceof Attr) {
            return (Attr) node;
        }
        notSupported();
        return null;
    }

    public static void notSupported() {
        throw new DOMException((short) 9, "Not supported yet");
    }

    public static boolean isStringEquals(String str, String str2) {
        if (str == null && str2 == null) {
            return true;
        }
        if (str != null && str2 != null) {
            return str.equals(str2);
        }
        return false;
    }

    public static boolean isNodeEquals(Node node, Node node2) {
        if (node == null && node2 == null) {
            return true;
        }
        return node != null && node2 != null && node.getNodeType() == node2.getNodeType() && isStringEquals(node.getNodeName(), node2.getNodeName()) && isStringEquals(node.getLocalName(), node2.getLocalName()) && isStringEquals(node.getNamespaceURI(), node2.getNamespaceURI()) && isStringEquals(node.getPrefix(), node2.getPrefix()) && isStringEquals(node.getNodeValue(), node2.getNodeValue());
    }
}
