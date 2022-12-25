package org.dom4j.tree;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.dom4j.Attribute;
import org.dom4j.CDATA;
import org.dom4j.CharacterData;
import org.dom4j.Comment;
import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.Entity;
import org.dom4j.IllegalAddException;
import org.dom4j.Namespace;
import org.dom4j.Node;
import org.dom4j.ProcessingInstruction;
import org.dom4j.QName;
import org.dom4j.Text;
import org.dom4j.Visitor;
import org.dom4j.p147io.OutputFormat;
import org.dom4j.p147io.XMLWriter;
import org.xml.sax.Attributes;

/* loaded from: classes4.dex */
public abstract class AbstractElement extends AbstractBranch implements Element {
    private static final DocumentFactory DOCUMENT_FACTORY = DocumentFactory.getInstance();
    protected static final boolean USE_STRINGVALUE_SEPARATOR = false;
    protected static final boolean VERBOSE_TOSTRING = false;

    protected abstract List<Attribute> attributeList();

    protected abstract List<Attribute> attributeList(int i);

    @Override // org.dom4j.tree.AbstractNode, org.dom4j.Node
    public short getNodeType() {
        return (short) 1;
    }

    public void setData(Object obj) {
    }

    @Override // org.dom4j.Element
    public boolean isRootElement() {
        Document document = getDocument();
        return document != null && document.getRootElement() == this;
    }

    @Override // org.dom4j.tree.AbstractNode, org.dom4j.Node
    public void setName(String str) {
        setQName(getDocumentFactory().createQName(str));
    }

    public void setNamespace(Namespace namespace) {
        setQName(getDocumentFactory().createQName(getName(), namespace));
    }

    public String getXPathNameStep() {
        String namespaceURI = getNamespaceURI();
        if (namespaceURI == null || namespaceURI.length() == 0) {
            return getName();
        }
        String namespacePrefix = getNamespacePrefix();
        if (namespacePrefix == null || namespacePrefix.length() == 0) {
            return "*[name()='" + getName() + "']";
        }
        return getQualifiedName();
    }

    @Override // org.dom4j.Node
    public String getPath(Element element) {
        if (this == element) {
            return ".";
        }
        Element parent = getParent();
        if (parent == null) {
            return "/" + getXPathNameStep();
        } else if (parent == element) {
            return getXPathNameStep();
        } else {
            return parent.getPath(element) + "/" + getXPathNameStep();
        }
    }

    @Override // org.dom4j.Node
    public String getUniquePath(Element element) {
        int indexOf;
        Element parent = getParent();
        if (parent == null) {
            return "/" + getXPathNameStep();
        }
        StringBuilder sb = new StringBuilder();
        if (parent != element) {
            sb.append(parent.getUniquePath(element));
            sb.append("/");
        }
        sb.append(getXPathNameStep());
        List<Element> elements = parent.elements(getQName());
        if (elements.size() > 1 && (indexOf = elements.indexOf(this)) >= 0) {
            sb.append("[");
            sb.append(Integer.toString(indexOf + 1));
            sb.append("]");
        }
        return sb.toString();
    }

    @Override // org.dom4j.Node
    public String asXML() {
        try {
            StringWriter stringWriter = new StringWriter();
            XMLWriter xMLWriter = new XMLWriter(stringWriter, new OutputFormat());
            xMLWriter.write((Element) this);
            xMLWriter.flush();
            return stringWriter.toString();
        } catch (IOException e) {
            throw new RuntimeException("IOException while generating textual representation: " + e.getMessage());
        }
    }

    @Override // org.dom4j.tree.AbstractNode, org.dom4j.Node
    public void write(Writer writer) throws IOException {
        new XMLWriter(writer, new OutputFormat()).write((Element) this);
    }

    @Override // org.dom4j.Node
    public void accept(Visitor visitor) {
        visitor.visit(this);
        int attributeCount = attributeCount();
        for (int i = 0; i < attributeCount; i++) {
            visitor.visit(attribute(i));
        }
        int nodeCount = nodeCount();
        for (int i2 = 0; i2 < nodeCount; i2++) {
            node(i2).accept(visitor);
        }
    }

    public String toString() {
        String namespaceURI = getNamespaceURI();
        if (namespaceURI != null && namespaceURI.length() > 0) {
            return super.toString() + " [Element: <" + getQualifiedName() + " uri: " + namespaceURI + " attributes: " + attributeList() + "/>]";
        }
        return super.toString() + " [Element: <" + getQualifiedName() + " attributes: " + attributeList() + "/>]";
    }

    @Override // org.dom4j.Element
    public Namespace getNamespace() {
        return getQName().getNamespace();
    }

    @Override // org.dom4j.tree.AbstractNode, org.dom4j.Node
    public String getName() {
        return getQName().getName();
    }

    @Override // org.dom4j.Element
    public String getNamespacePrefix() {
        return getQName().getNamespacePrefix();
    }

    public String getNamespaceURI() {
        return getQName().getNamespaceURI();
    }

    @Override // org.dom4j.Element
    public String getQualifiedName() {
        return getQName().getQualifiedName();
    }

    public Object getData() {
        return getText();
    }

    @Override // org.dom4j.tree.AbstractBranch, org.dom4j.Branch
    public Node node(int i) {
        Node node;
        if (i >= 0) {
            List<Node> contentList = contentList();
            if (i < contentList.size() && (node = contentList.get(i)) != null) {
                return node;
            }
        }
        return null;
    }

    @Override // org.dom4j.tree.AbstractBranch, org.dom4j.Branch
    public int indexOf(Node node) {
        return contentList().indexOf(node);
    }

    @Override // org.dom4j.tree.AbstractBranch, org.dom4j.Branch
    public int nodeCount() {
        return contentList().size();
    }

    @Override // org.dom4j.tree.AbstractBranch, org.dom4j.Branch
    public Iterator<Node> nodeIterator() {
        return contentList().iterator();
    }

    @Override // org.dom4j.Element
    public Element element(String str) {
        for (Node node : contentList()) {
            if (node instanceof Element) {
                Element element = (Element) node;
                if (str.equals(element.getName())) {
                    return element;
                }
            }
        }
        return null;
    }

    @Override // org.dom4j.Element
    public Element element(QName qName) {
        for (Node node : contentList()) {
            if (node instanceof Element) {
                Element element = (Element) node;
                if (qName.equals(element.getQName())) {
                    return element;
                }
            }
        }
        return null;
    }

    public Element element(String str, Namespace namespace) {
        return element(getDocumentFactory().createQName(str, namespace));
    }

    @Override // org.dom4j.Element
    public List<Element> elements() {
        BackedList createResultList = createResultList();
        for (Node node : contentList()) {
            if (node instanceof Element) {
                createResultList.addLocal((Element) node);
            }
        }
        return createResultList;
    }

    @Override // org.dom4j.Element
    public List<Element> elements(String str) {
        BackedList createResultList = createResultList();
        for (Node node : contentList()) {
            if (node instanceof Element) {
                Element element = (Element) node;
                if (str.equals(element.getName())) {
                    createResultList.addLocal(element);
                }
            }
        }
        return createResultList;
    }

    @Override // org.dom4j.Element
    public List<Element> elements(QName qName) {
        BackedList createResultList = createResultList();
        for (Node node : contentList()) {
            if (node instanceof Element) {
                Element element = (Element) node;
                if (qName.equals(element.getQName())) {
                    createResultList.addLocal(element);
                }
            }
        }
        return createResultList;
    }

    public List<Element> elements(String str, Namespace namespace) {
        return elements(getDocumentFactory().createQName(str, namespace));
    }

    @Override // org.dom4j.Element
    public Iterator<Element> elementIterator() {
        return elements().iterator();
    }

    @Override // org.dom4j.Element
    public Iterator<Element> elementIterator(String str) {
        return elements(str).iterator();
    }

    @Override // org.dom4j.Element
    public Iterator<Element> elementIterator(QName qName) {
        return elements(qName).iterator();
    }

    public Iterator<Element> elementIterator(String str, Namespace namespace) {
        return elementIterator(getDocumentFactory().createQName(str, namespace));
    }

    @Override // org.dom4j.Element
    public List<Attribute> attributes() {
        return new ContentListFacade(this, attributeList());
    }

    @Override // org.dom4j.Element
    public Iterator<Attribute> attributeIterator() {
        return attributeList().iterator();
    }

    @Override // org.dom4j.Element
    public Attribute attribute(int i) {
        return attributeList().get(i);
    }

    @Override // org.dom4j.Element
    public int attributeCount() {
        return attributeList().size();
    }

    /* renamed from: attribute */
    public Attribute mo6817attribute(String str) {
        for (Attribute attribute : attributeList()) {
            if (str.equals(attribute.getName())) {
                return attribute;
            }
        }
        return null;
    }

    /* renamed from: attribute */
    public Attribute mo6818attribute(QName qName) {
        for (Attribute attribute : attributeList()) {
            if (qName.equals(attribute.getQName())) {
                return attribute;
            }
        }
        return null;
    }

    public Attribute attribute(String str, Namespace namespace) {
        return mo6818attribute(getDocumentFactory().createQName(str, namespace));
    }

    public void setAttributes(Attributes attributes, NamespaceStack namespaceStack, boolean z) {
        int length = attributes.getLength();
        if (length > 0) {
            DocumentFactory documentFactory = getDocumentFactory();
            if (length == 1) {
                String qName = attributes.getQName(0);
                if (!z && qName.startsWith("xmlns")) {
                    return;
                }
                String uri = attributes.getURI(0);
                String localName = attributes.getLocalName(0);
                add(documentFactory.createAttribute(this, namespaceStack.getAttributeQName(uri, localName, qName), attributes.getValue(0)));
                return;
            }
            List<Attribute> attributeList = attributeList(length);
            attributeList.clear();
            for (int i = 0; i < length; i++) {
                String qName2 = attributes.getQName(i);
                if (z || !qName2.startsWith("xmlns")) {
                    String uri2 = attributes.getURI(i);
                    String localName2 = attributes.getLocalName(i);
                    Attribute createAttribute = documentFactory.createAttribute(this, namespaceStack.getAttributeQName(uri2, localName2, qName2), attributes.getValue(i));
                    attributeList.add(createAttribute);
                    childAdded(createAttribute);
                }
            }
        }
    }

    @Override // org.dom4j.Element
    public String attributeValue(String str) {
        Attribute mo6817attribute = mo6817attribute(str);
        if (mo6817attribute == null) {
            return null;
        }
        return mo6817attribute.getValue();
    }

    @Override // org.dom4j.Element
    public String attributeValue(QName qName) {
        Attribute mo6818attribute = mo6818attribute(qName);
        if (mo6818attribute == null) {
            return null;
        }
        return mo6818attribute.getValue();
    }

    @Override // org.dom4j.Element
    public String attributeValue(String str, String str2) {
        String attributeValue = attributeValue(str);
        return attributeValue != null ? attributeValue : str2;
    }

    @Override // org.dom4j.Element
    public String attributeValue(QName qName, String str) {
        String attributeValue = attributeValue(qName);
        return attributeValue != null ? attributeValue : str;
    }

    @Override // org.dom4j.Element
    public void setAttributeValue(String str, String str2) {
        addAttribute(str, str2);
    }

    @Override // org.dom4j.Element
    public void setAttributeValue(QName qName, String str) {
        addAttribute(qName, str);
    }

    @Override // org.dom4j.Element
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
            attributeList().add(attribute);
            childAdded(attribute);
        }
    }

    @Override // org.dom4j.Element
    public boolean remove(Attribute attribute) {
        List<Attribute> attributeList = attributeList();
        boolean remove = attributeList.remove(attribute);
        if (remove) {
            childRemoved(attribute);
            return remove;
        }
        Attribute mo6818attribute = mo6818attribute(attribute.getQName());
        if (mo6818attribute == null) {
            return remove;
        }
        attributeList.remove(mo6818attribute);
        return true;
    }

    @Override // org.dom4j.Branch
    public List<ProcessingInstruction> processingInstructions() {
        BackedList createResultList = createResultList();
        for (Node node : contentList()) {
            if (node instanceof ProcessingInstruction) {
                createResultList.addLocal((ProcessingInstruction) node);
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
                    createResultList.addLocal(processingInstruction);
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

    @Override // org.dom4j.Element
    public Node getXPathResult(int i) {
        Node node = node(i);
        return (node == null || node.supportsParent()) ? node : node.asXPathResult(this);
    }

    public Element addAttribute(String str, String str2) {
        Attribute mo6817attribute = mo6817attribute(str);
        if (str2 != null) {
            if (mo6817attribute == null) {
                add(getDocumentFactory().createAttribute(this, str, str2));
            } else if (mo6817attribute.isReadOnly()) {
                remove(mo6817attribute);
                add(getDocumentFactory().createAttribute(this, str, str2));
            } else {
                mo6817attribute.setValue(str2);
            }
        } else if (mo6817attribute != null) {
            remove(mo6817attribute);
        }
        return this;
    }

    public Element addAttribute(QName qName, String str) {
        Attribute mo6818attribute = mo6818attribute(qName);
        if (str != null) {
            if (mo6818attribute == null) {
                add(getDocumentFactory().createAttribute(this, qName, str));
            } else if (mo6818attribute.isReadOnly()) {
                remove(mo6818attribute);
                add(getDocumentFactory().createAttribute(this, qName, str));
            } else {
                mo6818attribute.setValue(str);
            }
        } else if (mo6818attribute != null) {
            remove(mo6818attribute);
        }
        return this;
    }

    @Override // org.dom4j.Element
    public Element addCDATA(String str) {
        addNewNode(getDocumentFactory().createCDATA(str));
        return this;
    }

    @Override // org.dom4j.Element
    public Element addComment(String str) {
        addNewNode(getDocumentFactory().createComment(str));
        return this;
    }

    @Override // org.dom4j.tree.AbstractBranch, org.dom4j.Branch
    public Element addElement(String str) {
        Namespace namespaceForPrefix;
        String str2;
        Element createElement;
        DocumentFactory documentFactory = getDocumentFactory();
        int indexOf = str.indexOf(":");
        if (indexOf > 0) {
            String substring = str.substring(0, indexOf);
            str2 = str.substring(indexOf + 1);
            namespaceForPrefix = getNamespaceForPrefix(substring);
            if (namespaceForPrefix == null) {
                throw new IllegalAddException("No such namespace prefix: " + substring + " is in scope on: " + this + " so cannot add element: " + str);
            }
        } else {
            namespaceForPrefix = getNamespaceForPrefix("");
            str2 = str;
        }
        if (namespaceForPrefix != null) {
            createElement = documentFactory.createElement(documentFactory.createQName(str2, namespaceForPrefix));
        } else {
            createElement = documentFactory.createElement(str);
        }
        addNewNode(createElement);
        return createElement;
    }

    @Override // org.dom4j.Element
    public Element addEntity(String str, String str2) {
        addNewNode(getDocumentFactory().createEntity(str, str2));
        return this;
    }

    @Override // org.dom4j.Element
    public Element addNamespace(String str, String str2) {
        addNewNode(getDocumentFactory().createNamespace(str, str2));
        return this;
    }

    @Override // org.dom4j.Element
    public Element addProcessingInstruction(String str, String str2) {
        addNewNode(getDocumentFactory().createProcessingInstruction(str, str2));
        return this;
    }

    @Override // org.dom4j.Element
    public Element addProcessingInstruction(String str, Map<String, String> map) {
        addNewNode(getDocumentFactory().createProcessingInstruction(str, map));
        return this;
    }

    public Element addText(String str) {
        addNewNode(getDocumentFactory().createText(str));
        return this;
    }

    @Override // org.dom4j.tree.AbstractBranch, org.dom4j.Branch
    public void add(Node node) {
        short nodeType = node.getNodeType();
        if (nodeType == 1) {
            add((Element) node);
        } else if (nodeType == 2) {
            add((Attribute) node);
        } else if (nodeType == 3) {
            add((Text) node);
        } else if (nodeType == 4) {
            add((CDATA) node);
        } else if (nodeType == 5) {
            add((Entity) node);
        } else if (nodeType == 7) {
            add((ProcessingInstruction) node);
        } else if (nodeType == 8) {
            add((Comment) node);
        } else if (nodeType == 13) {
            add((Namespace) node);
        } else {
            invalidNodeTypeAddException(node);
        }
    }

    @Override // org.dom4j.tree.AbstractBranch, org.dom4j.Branch
    public boolean remove(Node node) {
        short nodeType = node.getNodeType();
        if (nodeType != 1) {
            if (nodeType == 2) {
                return remove((Attribute) node);
            }
            if (nodeType == 3) {
                return remove((Text) node);
            }
            if (nodeType == 4) {
                return remove((CDATA) node);
            }
            if (nodeType == 5) {
                return remove((Entity) node);
            }
            if (nodeType == 7) {
                return remove((ProcessingInstruction) node);
            }
            if (nodeType == 8) {
                return remove((Comment) node);
            }
            if (nodeType == 13) {
                return remove((Namespace) node);
            }
            return false;
        }
        return remove((Element) node);
    }

    @Override // org.dom4j.Element
    public void add(CDATA cdata) {
        addNode(cdata);
    }

    @Override // org.dom4j.tree.AbstractBranch, org.dom4j.Branch
    public void add(Comment comment) {
        addNode(comment);
    }

    @Override // org.dom4j.tree.AbstractBranch, org.dom4j.Branch
    public void add(Element element) {
        addNode(element);
    }

    @Override // org.dom4j.Element
    public void add(Entity entity) {
        addNode(entity);
    }

    @Override // org.dom4j.Element
    public void add(Namespace namespace) {
        addNode(namespace);
    }

    @Override // org.dom4j.tree.AbstractBranch, org.dom4j.Branch
    public void add(ProcessingInstruction processingInstruction) {
        addNode(processingInstruction);
    }

    @Override // org.dom4j.Element
    public void add(Text text) {
        addNode(text);
    }

    @Override // org.dom4j.Element
    public boolean remove(CDATA cdata) {
        return removeNode(cdata);
    }

    @Override // org.dom4j.tree.AbstractBranch, org.dom4j.Branch
    public boolean remove(Comment comment) {
        return removeNode(comment);
    }

    @Override // org.dom4j.tree.AbstractBranch, org.dom4j.Branch
    public boolean remove(Element element) {
        return removeNode(element);
    }

    @Override // org.dom4j.Element
    public boolean remove(Entity entity) {
        return removeNode(entity);
    }

    @Override // org.dom4j.Element
    public boolean remove(Namespace namespace) {
        return removeNode(namespace);
    }

    @Override // org.dom4j.tree.AbstractBranch, org.dom4j.Branch
    public boolean remove(ProcessingInstruction processingInstruction) {
        return removeNode(processingInstruction);
    }

    @Override // org.dom4j.Element
    public boolean remove(Text text) {
        return removeNode(text);
    }

    @Override // org.dom4j.Element
    public boolean hasMixedContent() {
        List<Node> contentList = contentList();
        if (contentList != null && !contentList.isEmpty() && contentList.size() >= 2) {
            Class<?> cls = null;
            for (Node node : contentList) {
                Class<?> cls2 = node.getClass();
                if (cls2 != cls) {
                    if (cls != null) {
                        return true;
                    }
                    cls = cls2;
                }
            }
        }
        return false;
    }

    @Override // org.dom4j.Element
    public boolean isTextOnly() {
        List<Node> contentList = contentList();
        if (contentList != null && !contentList.isEmpty()) {
            for (Node node : contentList) {
                if (!(node instanceof CharacterData)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override // org.dom4j.tree.AbstractNode, org.dom4j.Node
    public void setText(String str) {
        List<Node> contentList = contentList();
        if (contentList != null) {
            Iterator<Node> it2 = contentList.iterator();
            while (it2.hasNext()) {
                short nodeType = it2.next().getNodeType();
                if (nodeType == 3 || nodeType == 4 || nodeType == 5) {
                    it2.remove();
                }
            }
        }
        addText(str);
    }

    @Override // org.dom4j.tree.AbstractNode, org.dom4j.Node
    public String getStringValue() {
        List<Node> contentList = contentList();
        int size = contentList.size();
        if (size > 0) {
            if (size == 1) {
                return getContentAsStringValue(contentList.get(0));
            }
            StringBuilder sb = new StringBuilder();
            for (Node node : contentList) {
                String contentAsStringValue = getContentAsStringValue(node);
                if (contentAsStringValue.length() > 0) {
                    sb.append(contentAsStringValue);
                }
            }
            return sb.toString();
        }
        return "";
    }

    @Override // org.dom4j.Branch
    public void normalize() {
        List<Node> contentList = contentList();
        int i = 0;
        while (true) {
            Text text = null;
            while (i < contentList.size()) {
                Node node = contentList.get(i);
                if (node instanceof Text) {
                    Text text2 = (Text) node;
                    if (text != null) {
                        text.appendText(text2.getText());
                        remove(text2);
                    } else {
                        String text3 = text2.getText();
                        if (text3 == null || text3.length() <= 0) {
                            remove(text2);
                        } else {
                            i++;
                            text = text2;
                        }
                    }
                } else {
                    if (node instanceof Element) {
                        ((Element) node).normalize();
                    }
                    i++;
                }
            }
            return;
        }
    }

    @Override // org.dom4j.Element
    public String elementText(String str) {
        Element element = element(str);
        if (element != null) {
            return element.getText();
        }
        return null;
    }

    @Override // org.dom4j.Element
    public String elementText(QName qName) {
        Element element = element(qName);
        if (element != null) {
            return element.getText();
        }
        return null;
    }

    @Override // org.dom4j.Element
    public String elementTextTrim(String str) {
        Element element = element(str);
        if (element != null) {
            return element.getTextTrim();
        }
        return null;
    }

    @Override // org.dom4j.Element
    public String elementTextTrim(QName qName) {
        Element element = element(qName);
        if (element != null) {
            return element.getTextTrim();
        }
        return null;
    }

    @Override // org.dom4j.Element
    public void appendAttributes(Element element) {
        int attributeCount = element.attributeCount();
        for (int i = 0; i < attributeCount; i++) {
            Attribute attribute = element.attribute(i);
            if (attribute.supportsParent()) {
                addAttribute(attribute.getQName(), attribute.getValue());
            } else {
                add(attribute);
            }
        }
    }

    @Override // org.dom4j.Element
    public Element createCopy() {
        Element createElement = createElement(getQName());
        createElement.appendAttributes(this);
        createElement.appendContent(this);
        return createElement;
    }

    @Override // org.dom4j.Element
    public Element createCopy(String str) {
        Element createElement = createElement(str);
        createElement.appendAttributes(this);
        createElement.appendContent(this);
        return createElement;
    }

    @Override // org.dom4j.Element
    public Element createCopy(QName qName) {
        Element createElement = createElement(qName);
        createElement.appendAttributes(this);
        createElement.appendContent(this);
        return createElement;
    }

    @Override // org.dom4j.Element
    public QName getQName(String str) {
        String str2;
        int indexOf = str.indexOf(":");
        if (indexOf > 0) {
            str2 = str.substring(0, indexOf);
            str = str.substring(indexOf + 1);
        } else {
            str2 = "";
        }
        Namespace namespaceForPrefix = getNamespaceForPrefix(str2);
        if (namespaceForPrefix != null) {
            return getDocumentFactory().createQName(str, namespaceForPrefix);
        }
        return getDocumentFactory().createQName(str);
    }

    @Override // org.dom4j.Element
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
        for (Node node : contentList()) {
            if (node instanceof Namespace) {
                Namespace namespace = (Namespace) node;
                if (str.equals(namespace.getPrefix())) {
                    return namespace;
                }
            }
        }
        Element parent = getParent();
        if (parent != null && (namespaceForPrefix = parent.getNamespaceForPrefix(str)) != null) {
            return namespaceForPrefix;
        }
        if (str.length() != 0) {
            return null;
        }
        return Namespace.NO_NAMESPACE;
    }

    @Override // org.dom4j.Element
    public Namespace getNamespaceForURI(String str) {
        if (str == null || str.length() <= 0) {
            return Namespace.NO_NAMESPACE;
        }
        if (str.equals(getNamespaceURI())) {
            return getNamespace();
        }
        for (Node node : contentList()) {
            if (node instanceof Namespace) {
                Namespace namespace = (Namespace) node;
                if (str.equals(namespace.getURI())) {
                    return namespace;
                }
            }
        }
        return null;
    }

    @Override // org.dom4j.Element
    public List<Namespace> getNamespacesForURI(String str) {
        BackedList createResultList = createResultList();
        for (Node node : contentList()) {
            if (node instanceof Namespace) {
                Namespace namespace = (Namespace) node;
                if (namespace.getURI().equals(str)) {
                    createResultList.addLocal(namespace);
                }
            }
        }
        return createResultList;
    }

    @Override // org.dom4j.Element
    public List<Namespace> declaredNamespaces() {
        BackedList createResultList = createResultList();
        for (Node node : contentList()) {
            if (node instanceof Namespace) {
                createResultList.addLocal((Namespace) node);
            }
        }
        return createResultList;
    }

    @Override // org.dom4j.Element
    public List<Namespace> additionalNamespaces() {
        BackedList createResultList = createResultList();
        for (Node node : contentList()) {
            if (node instanceof Namespace) {
                Namespace namespace = (Namespace) node;
                if (!namespace.equals(getNamespace())) {
                    createResultList.addLocal(namespace);
                }
            }
        }
        return createResultList;
    }

    public List<Namespace> additionalNamespaces(String str) {
        BackedList createResultList = createResultList();
        for (Node node : contentList()) {
            if (node instanceof Namespace) {
                Namespace namespace = (Namespace) node;
                if (!str.equals(namespace.getURI())) {
                    createResultList.addLocal(namespace);
                }
            }
        }
        return createResultList;
    }

    public void ensureAttributesCapacity(int i) {
        if (i > 1) {
            List<Attribute> attributeList = attributeList();
            if (!(attributeList instanceof ArrayList)) {
                return;
            }
            ((ArrayList) attributeList).ensureCapacity(i);
        }
    }

    protected Element createElement(String str) {
        return getDocumentFactory().createElement(str);
    }

    protected Element createElement(QName qName) {
        return getDocumentFactory().createElement(qName);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.dom4j.tree.AbstractBranch
    public void addNode(Node node) {
        if (node.getParent() != null) {
            throw new IllegalAddException((Element) this, node, "The Node already has an existing parent of \"" + node.getParent().getQualifiedName() + "\"");
        }
        addNewNode(node);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.dom4j.tree.AbstractBranch
    public void addNode(int i, Node node) {
        if (node.getParent() != null) {
            throw new IllegalAddException((Element) this, node, "The Node already has an existing parent of \"" + node.getParent().getQualifiedName() + "\"");
        }
        addNewNode(i, node);
    }

    protected void addNewNode(Node node) {
        contentList().add(node);
        childAdded(node);
    }

    protected void addNewNode(int i, Node node) {
        contentList().add(i, node);
        childAdded(node);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.dom4j.tree.AbstractBranch
    public boolean removeNode(Node node) {
        boolean remove = contentList().remove(node);
        if (remove) {
            childRemoved(node);
        }
        return remove;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.dom4j.tree.AbstractBranch
    public void childAdded(Node node) {
        if (node != null) {
            node.setParent(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.dom4j.tree.AbstractBranch
    public void childRemoved(Node node) {
        if (node != null) {
            node.setParent(null);
            node.setDocument(null);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.dom4j.tree.AbstractNode
    public DocumentFactory getDocumentFactory() {
        DocumentFactory documentFactory;
        QName qName = getQName();
        return (qName == null || (documentFactory = qName.getDocumentFactory()) == null) ? DOCUMENT_FACTORY : documentFactory;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public List<Attribute> createAttributeList() {
        return createAttributeList(5);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public List<Attribute> createAttributeList(int i) {
        return new ArrayList(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public <T> Iterator<T> createSingleIterator(T t) {
        return new SingleIterator(t);
    }
}
