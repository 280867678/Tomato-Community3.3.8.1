package org.dom4j.tree;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import org.dom4j.Document;
import org.dom4j.DocumentType;
import org.dom4j.Element;
import org.dom4j.IllegalAddException;
import org.dom4j.Node;
import org.dom4j.QName;
import org.dom4j.Visitor;
import org.dom4j.p147io.OutputFormat;
import org.dom4j.p147io.XMLWriter;

/* loaded from: classes4.dex */
public abstract class AbstractDocument extends AbstractBranch implements Document {
    protected String encoding;

    @Override // org.dom4j.tree.AbstractNode, org.dom4j.Node
    public Node asXPathResult(Element element) {
        return this;
    }

    @Override // org.dom4j.tree.AbstractNode, org.dom4j.Node
    public Document getDocument() {
        return this;
    }

    @Override // org.dom4j.tree.AbstractNode, org.dom4j.Node
    public short getNodeType() {
        return (short) 9;
    }

    @Override // org.dom4j.Node
    public String getPath(Element element) {
        return "/";
    }

    @Override // org.dom4j.Node
    public String getUniquePath(Element element) {
        return "/";
    }

    @Override // org.dom4j.Document
    public String getXMLEncoding() {
        return null;
    }

    protected abstract void rootElementAdded(Element element);

    @Override // org.dom4j.tree.AbstractNode, org.dom4j.Node
    public String getStringValue() {
        Element rootElement = getRootElement();
        return rootElement != null ? rootElement.getStringValue() : "";
    }

    @Override // org.dom4j.Node
    public String asXML() {
        OutputFormat outputFormat = new OutputFormat();
        outputFormat.setEncoding(this.encoding);
        try {
            StringWriter stringWriter = new StringWriter();
            XMLWriter xMLWriter = new XMLWriter(stringWriter, outputFormat);
            xMLWriter.write((Document) this);
            xMLWriter.flush();
            return stringWriter.toString();
        } catch (IOException e) {
            throw new RuntimeException("IOException while generating textual representation: " + e.getMessage());
        }
    }

    @Override // org.dom4j.tree.AbstractNode, org.dom4j.Node
    public void write(Writer writer) throws IOException {
        OutputFormat outputFormat = new OutputFormat();
        outputFormat.setEncoding(this.encoding);
        new XMLWriter(writer, outputFormat).write((Document) this);
    }

    @Override // org.dom4j.Node
    public void accept(Visitor visitor) {
        visitor.visit(this);
        DocumentType docType = getDocType();
        if (docType != null) {
            visitor.visit(docType);
        }
        List<Node> content = content();
        if (content != null) {
            for (Node node : content) {
                node.accept(visitor);
            }
        }
    }

    public String toString() {
        return super.toString() + " [Document: name " + getName() + "]";
    }

    @Override // org.dom4j.Branch
    public void normalize() {
        Element rootElement = getRootElement();
        if (rootElement != null) {
            rootElement.normalize();
        }
    }

    @Override // org.dom4j.Document
    public Document addComment(String str) {
        add(getDocumentFactory().createComment(str));
        return this;
    }

    @Override // org.dom4j.Document
    public Document addProcessingInstruction(String str, String str2) {
        add(getDocumentFactory().createProcessingInstruction(str, str2));
        return this;
    }

    @Override // org.dom4j.Document
    public Document addProcessingInstruction(String str, Map<String, String> map) {
        add(getDocumentFactory().createProcessingInstruction(str, map));
        return this;
    }

    @Override // org.dom4j.tree.AbstractBranch, org.dom4j.Branch
    public Element addElement(String str) {
        Element createElement = getDocumentFactory().createElement(str);
        add(createElement);
        return createElement;
    }

    @Override // org.dom4j.tree.AbstractBranch, org.dom4j.Branch
    public Element addElement(String str, String str2) {
        Element createElement = getDocumentFactory().createElement(str, str2);
        add(createElement);
        return createElement;
    }

    @Override // org.dom4j.tree.AbstractBranch, org.dom4j.Branch
    public Element addElement(QName qName) {
        Element createElement = getDocumentFactory().createElement(qName);
        add(createElement);
        return createElement;
    }

    @Override // org.dom4j.Document
    public void setRootElement(Element element) {
        clearContent();
        if (element != null) {
            super.add(element);
            rootElementAdded(element);
        }
    }

    @Override // org.dom4j.tree.AbstractBranch, org.dom4j.Branch
    public void add(Element element) {
        checkAddElementAllowed(element);
        super.add(element);
        rootElementAdded(element);
    }

    @Override // org.dom4j.tree.AbstractBranch, org.dom4j.Branch
    public boolean remove(Element element) {
        boolean remove = super.remove(element);
        if (getRootElement() != null && remove) {
            setRootElement(null);
        }
        element.setDocument(null);
        return remove;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.dom4j.tree.AbstractBranch
    public void childAdded(Node node) {
        if (node != null) {
            node.setDocument(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.dom4j.tree.AbstractBranch
    public void childRemoved(Node node) {
        if (node != null) {
            node.setDocument(null);
        }
    }

    protected void checkAddElementAllowed(Element element) {
        Element rootElement = getRootElement();
        if (rootElement == null) {
            return;
        }
        throw new IllegalAddException(this, element, "Cannot add another element to this Document as it already has a root element of: " + rootElement.getQualifiedName());
    }

    @Override // org.dom4j.Document
    public void setXMLEncoding(String str) {
        this.encoding = str;
    }
}
