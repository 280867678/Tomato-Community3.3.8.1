package org.dom4j.tree;

import java.util.Iterator;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.DocumentType;
import org.dom4j.Element;
import org.dom4j.IllegalAddException;
import org.dom4j.Node;
import org.dom4j.ProcessingInstruction;
import org.xml.sax.EntityResolver;

/* loaded from: classes4.dex */
public class DefaultDocument extends AbstractDocument {
    private List<Node> content;
    private DocumentType docType;
    private DocumentFactory documentFactory = DocumentFactory.getInstance();
    private transient EntityResolver entityResolver;
    private String name;
    private Element rootElement;

    public DefaultDocument() {
    }

    public DefaultDocument(String str) {
        this.name = str;
    }

    public DefaultDocument(Element element) {
        this.rootElement = element;
    }

    public DefaultDocument(DocumentType documentType) {
        this.docType = documentType;
    }

    public DefaultDocument(Element element, DocumentType documentType) {
        this.rootElement = element;
        this.docType = documentType;
    }

    public DefaultDocument(String str, Element element, DocumentType documentType) {
        this.name = str;
        this.rootElement = element;
        this.docType = documentType;
    }

    @Override // org.dom4j.tree.AbstractNode, org.dom4j.Node
    public String getName() {
        return this.name;
    }

    @Override // org.dom4j.tree.AbstractNode, org.dom4j.Node
    public void setName(String str) {
        this.name = str;
    }

    @Override // org.dom4j.Document
    public Element getRootElement() {
        return this.rootElement;
    }

    @Override // org.dom4j.Document
    public DocumentType getDocType() {
        return this.docType;
    }

    @Override // org.dom4j.Document
    public void setDocType(DocumentType documentType) {
        this.docType = documentType;
    }

    @Override // org.dom4j.Document
    public Document addDocType(String str, String str2, String str3) {
        setDocType(getDocumentFactory().createDocType(str, str2, str3));
        return this;
    }

    @Override // org.dom4j.tree.AbstractDocument, org.dom4j.Document
    public String getXMLEncoding() {
        return this.encoding;
    }

    @Override // org.dom4j.Document
    public EntityResolver getEntityResolver() {
        return this.entityResolver;
    }

    @Override // org.dom4j.Document
    public void setEntityResolver(EntityResolver entityResolver) {
        this.entityResolver = entityResolver;
    }

    @Override // org.dom4j.tree.AbstractNode, org.dom4j.Node
    public Object clone() {
        DefaultDocument defaultDocument = (DefaultDocument) super.clone();
        defaultDocument.rootElement = null;
        defaultDocument.content = null;
        defaultDocument.appendContent(this);
        return defaultDocument;
    }

    @Override // org.dom4j.Branch
    public List<ProcessingInstruction> processingInstructions() {
        BackedList createResultList = createResultList();
        for (Node node : contentList()) {
            if (node instanceof ProcessingInstruction) {
                createResultList.add((BackedList) ((ProcessingInstruction) node));
            }
        }
        return createResultList;
    }

    @Override // org.dom4j.Branch
    public List<ProcessingInstruction> processingInstructions(String str) {
        BackedList createResultList = createResultList();
        for (Node node : contentList()) {
            if (node instanceof ProcessingInstruction) {
                ProcessingInstruction processingInstruction = (ProcessingInstruction) node;
                if (str.equals(processingInstruction.getName())) {
                    createResultList.add((BackedList) processingInstruction);
                }
            }
        }
        return createResultList;
    }

    @Override // org.dom4j.Branch
    public ProcessingInstruction processingInstruction(String str) {
        for (Node node : contentList()) {
            if (node instanceof ProcessingInstruction) {
                ProcessingInstruction processingInstruction = (ProcessingInstruction) node;
                if (str.equals(processingInstruction.getName())) {
                    return processingInstruction;
                }
            }
        }
        return null;
    }

    @Override // org.dom4j.Branch
    public boolean removeProcessingInstruction(String str) {
        Iterator<Node> it2 = contentList().iterator();
        while (it2.hasNext()) {
            Node next = it2.next();
            if ((next instanceof ProcessingInstruction) && str.equals(((ProcessingInstruction) next).getName())) {
                it2.remove();
                return true;
            }
        }
        return false;
    }

    @Override // org.dom4j.Branch
    public void setContent(List<Node> list) {
        this.rootElement = null;
        contentRemoved();
        if (list instanceof ContentListFacade) {
            list = ((ContentListFacade) list).getBackingList();
        }
        if (list == null) {
            this.content = null;
            return;
        }
        List<Node> createContentList = createContentList(list.size());
        Iterator<Node> it2 = list.iterator();
        while (it2.hasNext()) {
            Node next = it2.next();
            Document document = next.getDocument();
            if (document != null && document != this) {
                next = (Node) next.clone();
            }
            if (next instanceof Element) {
                if (this.rootElement == null) {
                    this.rootElement = (Element) next;
                } else {
                    throw new IllegalAddException("A document may only contain one root element: " + list);
                }
            }
            createContentList.add(next);
            childAdded(next);
        }
        this.content = createContentList;
    }

    @Override // org.dom4j.Branch
    public void clearContent() {
        contentRemoved();
        this.content = null;
        this.rootElement = null;
    }

    public void setDocumentFactory(DocumentFactory documentFactory) {
        this.documentFactory = documentFactory;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.dom4j.tree.AbstractBranch
    public List<Node> contentList() {
        if (this.content == null) {
            this.content = createContentList();
            Element element = this.rootElement;
            if (element != null) {
                this.content.add(element);
            }
        }
        return this.content;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.dom4j.tree.AbstractBranch
    public void addNode(Node node) {
        if (node != null) {
            Document document = node.getDocument();
            if (document != null && document != this) {
                throw new IllegalAddException(this, node, "The Node already has an existing document: " + document);
            }
            contentList().add(node);
            childAdded(node);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.dom4j.tree.AbstractBranch
    public void addNode(int i, Node node) {
        if (node != null) {
            Document document = node.getDocument();
            if (document != null && document != this) {
                throw new IllegalAddException(this, node, "The Node already has an existing document: " + document);
            }
            contentList().add(i, node);
            childAdded(node);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.dom4j.tree.AbstractBranch
    public boolean removeNode(Node node) {
        if (node == this.rootElement) {
            this.rootElement = null;
        }
        if (contentList().remove(node)) {
            childRemoved(node);
            return true;
        }
        return false;
    }

    @Override // org.dom4j.tree.AbstractDocument
    protected void rootElementAdded(Element element) {
        this.rootElement = element;
        element.setDocument(this);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.dom4j.tree.AbstractNode
    public DocumentFactory getDocumentFactory() {
        return this.documentFactory;
    }
}
