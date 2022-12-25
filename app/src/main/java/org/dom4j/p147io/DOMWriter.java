package org.dom4j.p147io;

import java.io.PrintStream;
import java.util.List;
import org.dom4j.Attribute;
import org.dom4j.CDATA;
import org.dom4j.Comment;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Entity;
import org.dom4j.Namespace;
import org.dom4j.ProcessingInstruction;
import org.dom4j.Text;
import org.dom4j.tree.NamespaceStack;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/* renamed from: org.dom4j.io.DOMWriter */
/* loaded from: classes4.dex */
public class DOMWriter {
    private static final String[] DEFAULT_DOM_DOCUMENT_CLASSES = {"org.apache.xerces.dom.DocumentImpl", "gnu.xml.dom.DomDocument", "org.apache.crimson.tree.XmlDocument", "com.sun.xml.tree.XmlDocument", "oracle.xml.parser.v2.XMLDocument", "oracle.xml.parser.XMLDocument", "org.dom4j.dom.DOMDocument"};
    private static boolean loggedWarning = false;
    private Class<?> domDocumentClass;
    private NamespaceStack namespaceStack = new NamespaceStack();

    public DOMWriter() {
    }

    public DOMWriter(Class<?> cls) {
        this.domDocumentClass = cls;
    }

    public Class<?> getDomDocumentClass() throws DocumentException {
        Class<?> cls = this.domDocumentClass;
        if (cls == null) {
            String[] strArr = DEFAULT_DOM_DOCUMENT_CLASSES;
            int length = strArr.length;
            for (String str : strArr) {
                try {
                    cls = Class.forName(str, true, DOMWriter.class.getClassLoader());
                } catch (Exception unused) {
                }
                if (cls != null) {
                    break;
                }
            }
        }
        return cls;
    }

    public void setDomDocumentClass(Class<?> cls) {
        this.domDocumentClass = cls;
    }

    public void setDomDocumentClassName(String str) throws DocumentException {
        try {
            this.domDocumentClass = Class.forName(str, true, DOMWriter.class.getClassLoader());
        } catch (Exception e) {
            throw new DocumentException("Could not load the DOM Document class: " + str, e);
        }
    }

    public Document write(org.dom4j.Document document) throws DocumentException {
        if (document instanceof Document) {
            return (Document) document;
        }
        resetNamespaceStack();
        Document createDomDocument = createDomDocument(document);
        appendDOMTree(createDomDocument, createDomDocument, document.content());
        this.namespaceStack.clear();
        return createDomDocument;
    }

    public Document write(org.dom4j.Document document, DOMImplementation dOMImplementation) throws DocumentException {
        if (document instanceof Document) {
            return (Document) document;
        }
        resetNamespaceStack();
        Document createDomDocument = createDomDocument(document, dOMImplementation);
        appendDOMTree(createDomDocument, createDomDocument, document.content());
        this.namespaceStack.clear();
        return createDomDocument;
    }

    protected void appendDOMTree(Document document, Node node, List<org.dom4j.Node> list) {
        for (org.dom4j.Node node2 : list) {
            if (node2 instanceof Element) {
                appendDOMTree(document, node, (Element) node2);
            } else if (node2 instanceof Text) {
                appendDOMTree(document, node, ((Text) node2).getText());
            } else if (node2 instanceof CDATA) {
                appendDOMTree(document, node, (CDATA) node2);
            } else if (node2 instanceof Comment) {
                appendDOMTree(document, node, (Comment) node2);
            } else if (node2 instanceof Entity) {
                appendDOMTree(document, node, (Entity) node2);
            } else if (node2 instanceof ProcessingInstruction) {
                appendDOMTree(document, node, (ProcessingInstruction) node2);
            }
        }
    }

    protected void appendDOMTree(Document document, Node node, Element element) {
        org.w3c.dom.Element createElementNS = document.createElementNS(element.getNamespaceURI(), element.getQualifiedName());
        int size = this.namespaceStack.size();
        Namespace namespace = element.getNamespace();
        if (isNamespaceDeclaration(namespace)) {
            this.namespaceStack.push(namespace);
            writeNamespace(createElementNS, namespace);
        }
        List<Namespace> declaredNamespaces = element.declaredNamespaces();
        int size2 = declaredNamespaces.size();
        for (int i = 0; i < size2; i++) {
            Namespace namespace2 = declaredNamespaces.get(i);
            if (isNamespaceDeclaration(namespace2)) {
                this.namespaceStack.push(namespace2);
                writeNamespace(createElementNS, namespace2);
            }
        }
        for (Attribute attribute : element.attributes()) {
            createElementNS.setAttributeNS(attribute.getNamespaceURI(), attribute.getQualifiedName(), attribute.getValue());
        }
        appendDOMTree(document, createElementNS, element.content());
        node.appendChild(createElementNS);
        while (this.namespaceStack.size() > size) {
            this.namespaceStack.pop();
        }
    }

    protected void appendDOMTree(Document document, Node node, CDATA cdata) {
        node.appendChild(document.createCDATASection(cdata.getText()));
    }

    protected void appendDOMTree(Document document, Node node, Comment comment) {
        node.appendChild(document.createComment(comment.getText()));
    }

    protected void appendDOMTree(Document document, Node node, String str) {
        node.appendChild(document.createTextNode(str));
    }

    protected void appendDOMTree(Document document, Node node, Entity entity) {
        node.appendChild(document.createEntityReference(entity.getName()));
    }

    protected void appendDOMTree(Document document, Node node, ProcessingInstruction processingInstruction) {
        node.appendChild(document.createProcessingInstruction(processingInstruction.getTarget(), processingInstruction.getText()));
    }

    protected void writeNamespace(org.w3c.dom.Element element, Namespace namespace) {
        element.setAttribute(attributeNameForNamespace(namespace), namespace.getURI());
    }

    protected String attributeNameForNamespace(Namespace namespace) {
        String prefix = namespace.getPrefix();
        if (prefix.length() > 0) {
            return "xmlns:" + prefix;
        }
        return "xmlns";
    }

    protected Document createDomDocument(org.dom4j.Document document) throws DocumentException {
        Class<?> cls = this.domDocumentClass;
        if (cls != null) {
            try {
                return (Document) cls.newInstance();
            } catch (Exception e) {
                throw new DocumentException("Could not instantiate an instance of DOM Document with class: " + this.domDocumentClass.getName(), e);
            }
        }
        Document createDomDocumentViaJAXP = createDomDocumentViaJAXP();
        if (createDomDocumentViaJAXP != null) {
            return createDomDocumentViaJAXP;
        }
        Class<?> domDocumentClass = getDomDocumentClass();
        try {
            return (Document) domDocumentClass.newInstance();
        } catch (Exception e2) {
            throw new DocumentException("Could not instantiate an instance of DOM Document with class: " + domDocumentClass.getName(), e2);
        }
    }

    protected Document createDomDocumentViaJAXP() throws DocumentException {
        try {
            return JAXPHelper.createDocument(false, true);
        } catch (Throwable th) {
            if (loggedWarning) {
                return null;
            }
            loggedWarning = true;
            if (SAXHelper.isVerboseErrorReporting()) {
                System.out.println("Warning: Caught exception attempting to use JAXP to create a W3C DOM document");
                PrintStream printStream = System.out;
                printStream.println("Warning: Exception was: " + th);
                th.printStackTrace();
                return null;
            }
            System.out.println("Warning: Error occurred using JAXP to create a DOM document.");
            return null;
        }
    }

    protected Document createDomDocument(org.dom4j.Document document, DOMImplementation dOMImplementation) throws DocumentException {
        return dOMImplementation.createDocument(null, null, null);
    }

    protected boolean isNamespaceDeclaration(Namespace namespace) {
        String uri;
        return (namespace == null || namespace == Namespace.NO_NAMESPACE || namespace == Namespace.XML_NAMESPACE || (uri = namespace.getURI()) == null || uri.length() <= 0 || this.namespaceStack.contains(namespace)) ? false : true;
    }

    protected void resetNamespaceStack() {
        this.namespaceStack.clear();
        this.namespaceStack.push(Namespace.XML_NAMESPACE);
    }
}
