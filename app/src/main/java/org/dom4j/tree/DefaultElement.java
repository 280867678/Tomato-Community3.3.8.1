package org.dom4j.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.dom4j.Attribute;
import org.dom4j.Branch;
import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.IllegalAddException;
import org.dom4j.Namespace;
import org.dom4j.Node;
import org.dom4j.ProcessingInstruction;
import org.dom4j.QName;

/* loaded from: classes4.dex */
public class DefaultElement extends AbstractElement {
    private static final transient DocumentFactory DOCUMENT_FACTORY = DocumentFactory.getInstance();
    private Object attributes;
    private Object content;
    private Branch parentBranch;
    private QName qname;

    @Override // org.dom4j.tree.AbstractNode, org.dom4j.Node
    public boolean supportsParent() {
        return true;
    }

    public DefaultElement(String str) {
        this.qname = DOCUMENT_FACTORY.createQName(str);
    }

    public DefaultElement(QName qName) {
        this.qname = qName;
    }

    public DefaultElement(QName qName, int i) {
        this.qname = qName;
        if (i > 1) {
            this.attributes = new ArrayList(i);
        }
    }

    public DefaultElement(String str, Namespace namespace) {
        this.qname = DOCUMENT_FACTORY.createQName(str, namespace);
    }

    @Override // org.dom4j.tree.AbstractNode, org.dom4j.Node
    public Element getParent() {
        Branch branch = this.parentBranch;
        if (branch instanceof Element) {
            return (Element) branch;
        }
        return null;
    }

    @Override // org.dom4j.tree.AbstractNode, org.dom4j.Node
    public void setParent(Element element) {
        if ((this.parentBranch instanceof Element) || element != null) {
            this.parentBranch = element;
        }
    }

    @Override // org.dom4j.tree.AbstractNode, org.dom4j.Node
    public Document getDocument() {
        Branch branch = this.parentBranch;
        if (branch instanceof Document) {
            return (Document) branch;
        }
        if (!(branch instanceof Element)) {
            return null;
        }
        return ((Element) branch).getDocument();
    }

    @Override // org.dom4j.tree.AbstractNode, org.dom4j.Node
    public void setDocument(Document document) {
        if ((this.parentBranch instanceof Document) || document != null) {
            this.parentBranch = document;
        }
    }

    @Override // org.dom4j.Element
    public QName getQName() {
        return this.qname;
    }

    @Override // org.dom4j.Element
    public void setQName(QName qName) {
        this.qname = qName;
    }

    @Override // org.dom4j.tree.AbstractBranch, org.dom4j.tree.AbstractNode, org.dom4j.Node
    public String getText() {
        Object obj = this.content;
        if (obj instanceof List) {
            return super.getText();
        }
        return obj != null ? getContentAsText(obj) : "";
    }

    @Override // org.dom4j.tree.AbstractElement, org.dom4j.tree.AbstractNode, org.dom4j.Node
    public String getStringValue() {
        Object obj = this.content;
        if (!(obj instanceof List)) {
            return obj != null ? getContentAsStringValue(obj) : "";
        }
        List<Node> list = (List) obj;
        int size = list.size();
        if (size <= 0) {
            return "";
        }
        if (size == 1) {
            return getContentAsStringValue(list.get(0));
        }
        StringBuilder sb = new StringBuilder();
        for (Node node : list) {
            String contentAsStringValue = getContentAsStringValue(node);
            if (contentAsStringValue.length() > 0) {
                sb.append(contentAsStringValue);
            }
        }
        return sb.toString();
    }

    @Override // org.dom4j.tree.AbstractNode, org.dom4j.Node
    public Object clone() {
        DefaultElement defaultElement = (DefaultElement) super.clone();
        if (defaultElement != this) {
            defaultElement.content = null;
            defaultElement.attributes = null;
            defaultElement.appendAttributes(this);
            defaultElement.appendContent(this);
        }
        return defaultElement;
    }

    @Override // org.dom4j.tree.AbstractElement, org.dom4j.Element
    public Namespace getNamespaceForPrefix(String str) {
        Namespace namespaceForPrefix;
        if (str == null) {
            str = "";
        }
        if (str.equals(getNamespacePrefix())) {
            return getNamespace();
        }
        if (str.equals("xml")) {
            return Namespace.XML_NAMESPACE;
        }
        Object obj = this.content;
        if (obj instanceof List) {
            for (Node node : (List) obj) {
                if (node instanceof Namespace) {
                    Namespace namespace = (Namespace) node;
                    if (str.equals(namespace.getPrefix())) {
                        return namespace;
                    }
                }
            }
        } else if (obj instanceof Namespace) {
            Namespace namespace2 = (Namespace) obj;
            if (str.equals(namespace2.getPrefix())) {
                return namespace2;
            }
        }
        Element parent = getParent();
        if (parent != null && (namespaceForPrefix = parent.getNamespaceForPrefix(str)) != null) {
            return namespaceForPrefix;
        }
        if (str.length() > 0) {
            return null;
        }
        return Namespace.NO_NAMESPACE;
    }

    @Override // org.dom4j.tree.AbstractElement, org.dom4j.Element
    public Namespace getNamespaceForURI(String str) {
        if (str == null || str.length() <= 0) {
            return Namespace.NO_NAMESPACE;
        }
        if (str.equals(getNamespaceURI())) {
            return getNamespace();
        }
        Object obj = this.content;
        if (obj instanceof List) {
            for (Node node : (List) obj) {
                if (node instanceof Namespace) {
                    Namespace namespace = (Namespace) node;
                    if (str.equals(namespace.getURI())) {
                        return namespace;
                    }
                }
            }
        } else if (obj instanceof Namespace) {
            Namespace namespace2 = (Namespace) obj;
            if (str.equals(namespace2.getURI())) {
                return namespace2;
            }
        }
        Element parent = getParent();
        if (parent == null) {
            return null;
        }
        return parent.getNamespaceForURI(str);
    }

    @Override // org.dom4j.tree.AbstractElement, org.dom4j.Element
    public List<Namespace> declaredNamespaces() {
        BackedList createResultList = createResultList();
        Object obj = this.content;
        if (obj instanceof List) {
            for (Node node : (List) obj) {
                if (node instanceof Namespace) {
                    createResultList.addLocal((Namespace) node);
                }
            }
        } else if (obj instanceof Namespace) {
            createResultList.addLocal((Namespace) obj);
        }
        return createResultList;
    }

    @Override // org.dom4j.tree.AbstractElement, org.dom4j.Element
    public List<Namespace> additionalNamespaces() {
        Object obj = this.content;
        if (obj instanceof List) {
            BackedList createResultList = createResultList();
            for (Node node : (List) obj) {
                if (node instanceof Namespace) {
                    Namespace namespace = (Namespace) node;
                    if (!namespace.equals(getNamespace())) {
                        createResultList.addLocal(namespace);
                    }
                }
            }
            return createResultList;
        } else if (obj instanceof Namespace) {
            Namespace namespace2 = (Namespace) obj;
            if (namespace2.equals(getNamespace())) {
                return createEmptyList();
            }
            return createSingleResultList(namespace2);
        } else {
            return createEmptyList();
        }
    }

    @Override // org.dom4j.tree.AbstractElement
    public List<Namespace> additionalNamespaces(String str) {
        Object obj = this.content;
        if (obj instanceof List) {
            BackedList createResultList = createResultList();
            for (Node node : (List) obj) {
                if (node instanceof Namespace) {
                    Namespace namespace = (Namespace) node;
                    if (!str.equals(namespace.getURI())) {
                        createResultList.addLocal(namespace);
                    }
                }
            }
            return createResultList;
        }
        if (obj instanceof Namespace) {
            Namespace namespace2 = (Namespace) obj;
            if (!str.equals(namespace2.getURI())) {
                return createSingleResultList(namespace2);
            }
        }
        return createEmptyList();
    }

    @Override // org.dom4j.tree.AbstractElement, org.dom4j.Branch
    public List<ProcessingInstruction> processingInstructions() {
        Object obj = this.content;
        if (obj instanceof List) {
            BackedList createResultList = createResultList();
            for (Node node : (List) obj) {
                if (node instanceof ProcessingInstruction) {
                    createResultList.addLocal((ProcessingInstruction) node);
                }
            }
            return createResultList;
        } else if (obj instanceof ProcessingInstruction) {
            return createSingleResultList((ProcessingInstruction) obj);
        } else {
            return createEmptyList();
        }
    }

    @Override // org.dom4j.tree.AbstractElement, org.dom4j.Branch
    public List<ProcessingInstruction> processingInstructions(String str) {
        Object obj = this.content;
        if (obj instanceof List) {
            BackedList createResultList = createResultList();
            for (Node node : (List) obj) {
                if (node instanceof ProcessingInstruction) {
                    ProcessingInstruction processingInstruction = (ProcessingInstruction) node;
                    if (str.equals(processingInstruction.getName())) {
                        createResultList.addLocal(processingInstruction);
                    }
                }
            }
            return createResultList;
        }
        if (obj instanceof ProcessingInstruction) {
            ProcessingInstruction processingInstruction2 = (ProcessingInstruction) obj;
            if (str.equals(processingInstruction2.getName())) {
                return createSingleResultList(processingInstruction2);
            }
        }
        return createEmptyList();
    }

    @Override // org.dom4j.tree.AbstractElement, org.dom4j.Branch
    public ProcessingInstruction processingInstruction(String str) {
        Object obj = this.content;
        if (obj instanceof List) {
            for (Node node : (List) obj) {
                if (node instanceof ProcessingInstruction) {
                    ProcessingInstruction processingInstruction = (ProcessingInstruction) node;
                    if (str.equals(processingInstruction.getName())) {
                        return processingInstruction;
                    }
                }
            }
            return null;
        } else if (!(obj instanceof ProcessingInstruction)) {
            return null;
        } else {
            ProcessingInstruction processingInstruction2 = (ProcessingInstruction) obj;
            if (!str.equals(processingInstruction2.getName())) {
                return null;
            }
            return processingInstruction2;
        }
    }

    @Override // org.dom4j.tree.AbstractElement, org.dom4j.Branch
    public boolean removeProcessingInstruction(String str) {
        Object obj = this.content;
        if (obj instanceof List) {
            Iterator it2 = ((List) obj).iterator();
            while (it2.hasNext()) {
                Node node = (Node) it2.next();
                if ((node instanceof ProcessingInstruction) && str.equals(((ProcessingInstruction) node).getName())) {
                    it2.remove();
                    return true;
                }
            }
            return false;
        } else if (!(obj instanceof ProcessingInstruction) || !str.equals(((ProcessingInstruction) obj).getName())) {
            return false;
        } else {
            this.content = null;
            return true;
        }
    }

    @Override // org.dom4j.tree.AbstractElement, org.dom4j.Element
    public Element element(String str) {
        Object obj = this.content;
        if (obj instanceof List) {
            for (Node node : (List) obj) {
                if (node instanceof Element) {
                    Element element = (Element) node;
                    if (str.equals(element.getName())) {
                        return element;
                    }
                }
            }
            return null;
        } else if (!(obj instanceof Element)) {
            return null;
        } else {
            Element element2 = (Element) obj;
            if (!str.equals(element2.getName())) {
                return null;
            }
            return element2;
        }
    }

    @Override // org.dom4j.tree.AbstractElement, org.dom4j.Element
    public Element element(QName qName) {
        Object obj = this.content;
        if (obj instanceof List) {
            for (Node node : (List) obj) {
                if (node instanceof Element) {
                    Element element = (Element) node;
                    if (qName.equals(element.getQName())) {
                        return element;
                    }
                }
            }
            return null;
        } else if (!(obj instanceof Element)) {
            return null;
        } else {
            Element element2 = (Element) obj;
            if (!qName.equals(element2.getQName())) {
                return null;
            }
            return element2;
        }
    }

    @Override // org.dom4j.tree.AbstractElement
    public Element element(String str, Namespace namespace) {
        return element(getDocumentFactory().createQName(str, namespace));
    }

    @Override // org.dom4j.Branch
    public void setContent(List<Node> list) {
        contentRemoved();
        if (list instanceof ContentListFacade) {
            list = ((ContentListFacade) list).getBackingList();
        }
        if (list == null) {
            this.content = null;
            return;
        }
        List<Node> createContentList = createContentList(list.size());
        for (Node node : list) {
            Element parent = node.getParent();
            if (parent != null && parent != this) {
                node = (Node) node.clone();
            }
            createContentList.add(node);
            childAdded(node);
        }
        this.content = createContentList;
    }

    @Override // org.dom4j.Branch
    public void clearContent() {
        if (this.content != null) {
            contentRemoved();
            this.content = null;
        }
    }

    @Override // org.dom4j.tree.AbstractElement, org.dom4j.tree.AbstractBranch, org.dom4j.Branch
    public Node node(int i) {
        Node node = null;
        if (i >= 0) {
            Object obj = this.content;
            if (!(obj instanceof List)) {
                if (i == 0) {
                    node = (Node) obj;
                }
                return node;
            }
            List list = (List) obj;
            if (i < list.size()) {
                return (Node) list.get(i);
            }
            return null;
        }
        return null;
    }

    @Override // org.dom4j.tree.AbstractElement, org.dom4j.tree.AbstractBranch, org.dom4j.Branch
    public int indexOf(Node node) {
        Object obj = this.content;
        if (obj instanceof List) {
            return ((List) obj).indexOf(node);
        }
        return (obj == null || !obj.equals(node)) ? -1 : 0;
    }

    @Override // org.dom4j.tree.AbstractElement, org.dom4j.tree.AbstractBranch, org.dom4j.Branch
    public int nodeCount() {
        Object obj = this.content;
        if (obj instanceof List) {
            return ((List) obj).size();
        }
        return obj != null ? 1 : 0;
    }

    @Override // org.dom4j.tree.AbstractElement, org.dom4j.tree.AbstractBranch, org.dom4j.Branch
    public Iterator<Node> nodeIterator() {
        Object obj = this.content;
        if (obj instanceof List) {
            return ((List) obj).iterator();
        }
        if (obj != null) {
            return createSingleIterator((Node) obj);
        }
        return Collections.emptyList().iterator();
    }

    @Override // org.dom4j.tree.AbstractElement, org.dom4j.Element
    public List<Attribute> attributes() {
        return new ContentListFacade(this, attributeList());
    }

    public void setAttributes(List<Attribute> list) {
        if (list instanceof ContentListFacade) {
            list = ((ContentListFacade) list).getBackingList();
        }
        this.attributes = list;
    }

    @Override // org.dom4j.tree.AbstractElement, org.dom4j.Element
    public Iterator<Attribute> attributeIterator() {
        Object obj = this.attributes;
        if (obj instanceof List) {
            return ((List) obj).iterator();
        }
        if (obj != null) {
            return createSingleIterator((Attribute) obj);
        }
        return Collections.emptyList().iterator();
    }

    @Override // org.dom4j.tree.AbstractElement, org.dom4j.Element
    public Attribute attribute(int i) {
        Object obj = this.attributes;
        if (obj instanceof List) {
            return (Attribute) ((List) obj).get(i);
        }
        if (obj != null && i == 0) {
            return (Attribute) obj;
        }
        return null;
    }

    @Override // org.dom4j.tree.AbstractElement, org.dom4j.Element
    public int attributeCount() {
        Object obj = this.attributes;
        if (obj instanceof List) {
            return ((List) obj).size();
        }
        return obj != null ? 1 : 0;
    }

    @Override // org.dom4j.tree.AbstractElement, org.dom4j.Element
    /* renamed from: attribute */
    public Attribute mo6817attribute(String str) {
        Object obj = this.attributes;
        if (obj instanceof List) {
            for (Attribute attribute : (List) obj) {
                if (str.equals(attribute.getName())) {
                    return attribute;
                }
            }
            return null;
        } else if (obj == null) {
            return null;
        } else {
            Attribute attribute2 = (Attribute) obj;
            if (!str.equals(attribute2.getName())) {
                return null;
            }
            return attribute2;
        }
    }

    @Override // org.dom4j.tree.AbstractElement, org.dom4j.Element
    /* renamed from: attribute */
    public Attribute mo6818attribute(QName qName) {
        Object obj = this.attributes;
        if (obj instanceof List) {
            for (Attribute attribute : (List) obj) {
                if (qName.equals(attribute.getQName())) {
                    return attribute;
                }
            }
            return null;
        } else if (obj == null) {
            return null;
        } else {
            Attribute attribute2 = (Attribute) obj;
            if (!qName.equals(attribute2.getQName())) {
                return null;
            }
            return attribute2;
        }
    }

    @Override // org.dom4j.tree.AbstractElement
    public Attribute attribute(String str, Namespace namespace) {
        return mo6818attribute(getDocumentFactory().createQName(str, namespace));
    }

    @Override // org.dom4j.tree.AbstractElement, org.dom4j.Element
    public void add(Attribute attribute) {
        if (attribute.getParent() != null) {
            throw new IllegalAddException((Element) this, (Node) attribute, "The Attribute already has an existing parent \"" + attribute.getParent().getQualifiedName() + "\"");
        } else if (attribute.getValue() == null) {
            Attribute mo6818attribute = mo6818attribute(attribute.getQName());
            if (mo6818attribute == null) {
                return;
            }
            remove(mo6818attribute);
        } else {
            if (this.attributes == null) {
                this.attributes = attribute;
            } else {
                attributeList().add(attribute);
            }
            childAdded(attribute);
        }
    }

    @Override // org.dom4j.tree.AbstractElement, org.dom4j.Element
    public boolean remove(Attribute attribute) {
        Attribute mo6818attribute;
        Object obj = this.attributes;
        boolean z = true;
        if (obj instanceof List) {
            List list = (List) obj;
            boolean remove = list.remove(attribute);
            if (remove || (mo6818attribute = mo6818attribute(attribute.getQName())) == null) {
                z = remove;
            } else {
                list.remove(mo6818attribute);
            }
        } else {
            if (obj != null) {
                if (attribute.equals(obj)) {
                    this.attributes = null;
                } else if (attribute.getQName().equals(((Attribute) obj).getQName())) {
                    this.attributes = null;
                }
            }
            z = false;
        }
        if (z) {
            childRemoved(attribute);
        }
        return z;
    }

    @Override // org.dom4j.tree.AbstractElement
    protected void addNewNode(Node node) {
        Object obj = this.content;
        if (obj == null) {
            this.content = node;
        } else if (obj instanceof List) {
            ((List) obj).add(node);
        } else {
            List<Node> createContentList = createContentList();
            createContentList.add((Node) obj);
            createContentList.add(node);
            this.content = createContentList;
        }
        childAdded(node);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Removed duplicated region for block: B:6:0x0019  */
    @Override // org.dom4j.tree.AbstractElement, org.dom4j.tree.AbstractBranch
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean removeNode(Node node) {
        boolean z;
        Object obj = this.content;
        if (obj != null) {
            if (obj == node) {
                this.content = null;
                z = true;
            } else if (obj instanceof List) {
                z = ((List) obj).remove(node);
            }
            if (z) {
                childRemoved(node);
            }
            return z;
        }
        z = false;
        if (z) {
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.dom4j.tree.AbstractBranch
    public List<Node> contentList() {
        Object obj = this.content;
        if (obj instanceof List) {
            return (List) obj;
        }
        List<Node> createContentList = createContentList();
        if (obj != null) {
            createContentList.add((Node) obj);
        }
        this.content = createContentList;
        return createContentList;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.dom4j.tree.AbstractElement
    public List<Attribute> attributeList() {
        Object obj = this.attributes;
        if (obj instanceof List) {
            return (List) obj;
        }
        if (obj != null) {
            List<Attribute> createAttributeList = createAttributeList();
            createAttributeList.add((Attribute) obj);
            this.attributes = createAttributeList;
            return createAttributeList;
        }
        List<Attribute> createAttributeList2 = createAttributeList();
        this.attributes = createAttributeList2;
        return createAttributeList2;
    }

    @Override // org.dom4j.tree.AbstractElement
    protected List<Attribute> attributeList(int i) {
        Object obj = this.attributes;
        if (obj instanceof List) {
            return (List) obj;
        }
        if (obj != null) {
            List<Attribute> createAttributeList = createAttributeList(i);
            createAttributeList.add((Attribute) obj);
            this.attributes = createAttributeList;
            return createAttributeList;
        }
        List<Attribute> createAttributeList2 = createAttributeList(i);
        this.attributes = createAttributeList2;
        return createAttributeList2;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setAttributeList(List<Attribute> list) {
        this.attributes = list;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.dom4j.tree.AbstractElement, org.dom4j.tree.AbstractNode
    public DocumentFactory getDocumentFactory() {
        DocumentFactory documentFactory = this.qname.getDocumentFactory();
        return documentFactory != null ? documentFactory : DOCUMENT_FACTORY;
    }
}
