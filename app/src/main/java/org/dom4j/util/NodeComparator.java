package org.dom4j.util;

import java.util.Comparator;
import org.dom4j.Attribute;
import org.dom4j.Branch;
import org.dom4j.CDATA;
import org.dom4j.CharacterData;
import org.dom4j.Comment;
import org.dom4j.Document;
import org.dom4j.DocumentType;
import org.dom4j.Element;
import org.dom4j.Entity;
import org.dom4j.Namespace;
import org.dom4j.Node;
import org.dom4j.ProcessingInstruction;
import org.dom4j.QName;
import org.dom4j.Text;

/* loaded from: classes4.dex */
public class NodeComparator implements Comparator<Node> {
    @Override // java.util.Comparator
    public int compare(Node node, Node node2) {
        short nodeType = node.getNodeType();
        int nodeType2 = nodeType - node2.getNodeType();
        if (nodeType2 != 0) {
            return nodeType2;
        }
        switch (nodeType) {
            case 1:
                return compare((Element) node, (Element) node2);
            case 2:
                return compare((Attribute) node, (Attribute) node2);
            case 3:
                return compare((CharacterData) ((Text) node), (CharacterData) ((Text) node2));
            case 4:
                return compare((CharacterData) ((CDATA) node), (CharacterData) ((CDATA) node2));
            case 5:
                return compare((Entity) node, (Entity) node2);
            case 6:
            case 11:
            case 12:
            default:
                throw new RuntimeException("Invalid node types. node1: " + node + " and node2: " + node2);
            case 7:
                return compare((ProcessingInstruction) node, (ProcessingInstruction) node2);
            case 8:
                return compare((CharacterData) ((Comment) node), (CharacterData) ((Comment) node2));
            case 9:
                return compare((Document) node, (Document) node2);
            case 10:
                return compare((DocumentType) node, (DocumentType) node2);
            case 13:
                return compare((Namespace) node, (Namespace) node2);
        }
    }

    public int compare(Document document, Document document2) {
        int compare = compare(document.getDocType(), document2.getDocType());
        return compare == 0 ? compareContent(document, document2) : compare;
    }

    public int compare(Element element, Element element2) {
        int compare = compare(element.getQName(), element2.getQName());
        if (compare == 0) {
            int attributeCount = element.attributeCount();
            int attributeCount2 = attributeCount - element2.attributeCount();
            if (attributeCount2 != 0) {
                return attributeCount2;
            }
            for (int i = 0; i < attributeCount; i++) {
                Attribute attribute = element.attribute(i);
                int compare2 = compare(attribute, element2.mo6818attribute(attribute.getQName()));
                if (compare2 != 0) {
                    return compare2;
                }
            }
            return compareContent(element, element2);
        }
        return compare;
    }

    public int compare(Attribute attribute, Attribute attribute2) {
        int compare = compare(attribute.getQName(), attribute2.getQName());
        return compare == 0 ? compare(attribute.getValue(), attribute2.getValue()) : compare;
    }

    public int compare(QName qName, QName qName2) {
        int compare = compare(qName.getNamespaceURI(), qName2.getNamespaceURI());
        return compare == 0 ? compare(qName.getQualifiedName(), qName2.getQualifiedName()) : compare;
    }

    public int compare(Namespace namespace, Namespace namespace2) {
        int compare = compare(namespace.getURI(), namespace2.getURI());
        return compare == 0 ? compare(namespace.getPrefix(), namespace2.getPrefix()) : compare;
    }

    public int compare(CharacterData characterData, CharacterData characterData2) {
        return compare(characterData.getText(), characterData2.getText());
    }

    public int compare(DocumentType documentType, DocumentType documentType2) {
        if (documentType == documentType2) {
            return 0;
        }
        if (documentType == null) {
            return -1;
        }
        if (documentType2 == null) {
            return 1;
        }
        int compare = compare(documentType.getPublicID(), documentType2.getPublicID());
        if (compare != 0) {
            return compare;
        }
        int compare2 = compare(documentType.getSystemID(), documentType2.getSystemID());
        return compare2 == 0 ? compare(documentType.getName(), documentType2.getName()) : compare2;
    }

    public int compare(Entity entity, Entity entity2) {
        int compare = compare(entity.getName(), entity2.getName());
        return compare == 0 ? compare(entity.getText(), entity2.getText()) : compare;
    }

    public int compare(ProcessingInstruction processingInstruction, ProcessingInstruction processingInstruction2) {
        int compare = compare(processingInstruction.getTarget(), processingInstruction2.getTarget());
        return compare == 0 ? compare(processingInstruction.getText(), processingInstruction2.getText()) : compare;
    }

    public int compareContent(Branch branch, Branch branch2) {
        int nodeCount = branch.nodeCount();
        int nodeCount2 = nodeCount - branch2.nodeCount();
        if (nodeCount2 == 0) {
            for (int i = 0; i < nodeCount; i++) {
                nodeCount2 = compare(branch.node(i), branch2.node(i));
                if (nodeCount2 != 0) {
                    break;
                }
            }
        }
        return nodeCount2;
    }

    public int compare(String str, String str2) {
        if (str == str2) {
            return 0;
        }
        if (str == null) {
            return -1;
        }
        if (str2 != null) {
            return str.compareTo(str2);
        }
        return 1;
    }
}
