package org.dom4j.p147io;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Iterator;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.DTD;
import javax.xml.stream.events.EndDocument;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.EntityDeclaration;
import javax.xml.stream.events.EntityReference;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.util.XMLEventConsumer;
import org.dom4j.Attribute;
import org.dom4j.Branch;
import org.dom4j.CDATA;
import org.dom4j.Comment;
import org.dom4j.Document;
import org.dom4j.DocumentType;
import org.dom4j.Element;
import org.dom4j.Entity;
import org.dom4j.Namespace;
import org.dom4j.Node;
import org.dom4j.ProcessingInstruction;
import org.dom4j.Text;

/* renamed from: org.dom4j.io.STAXEventWriter */
/* loaded from: classes4.dex */
public class STAXEventWriter {
    private XMLEventConsumer consumer;
    private XMLEventFactory factory = XMLEventFactory.newInstance();
    private XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();

    public STAXEventWriter() {
    }

    public STAXEventWriter(File file) throws XMLStreamException, IOException {
        this.consumer = this.outputFactory.createXMLEventWriter(new FileWriter(file));
    }

    public STAXEventWriter(Writer writer) throws XMLStreamException {
        this.consumer = this.outputFactory.createXMLEventWriter(writer);
    }

    public STAXEventWriter(OutputStream outputStream) throws XMLStreamException {
        this.consumer = this.outputFactory.createXMLEventWriter(outputStream);
    }

    public STAXEventWriter(XMLEventConsumer xMLEventConsumer) {
        this.consumer = xMLEventConsumer;
    }

    public XMLEventConsumer getConsumer() {
        return this.consumer;
    }

    public void setConsumer(XMLEventConsumer xMLEventConsumer) {
        this.consumer = xMLEventConsumer;
    }

    public XMLEventFactory getEventFactory() {
        return this.factory;
    }

    public void setEventFactory(XMLEventFactory xMLEventFactory) {
        this.factory = xMLEventFactory;
    }

    public void writeNode(Node node) throws XMLStreamException {
        switch (node.getNodeType()) {
            case 1:
                writeElement((Element) node);
                return;
            case 2:
                writeAttribute((Attribute) node);
                return;
            case 3:
                writeText((Text) node);
                return;
            case 4:
                writeCDATA((CDATA) node);
                return;
            case 5:
                writeEntity((Entity) node);
                return;
            case 6:
            case 11:
            case 12:
            default:
                throw new XMLStreamException("Unsupported DOM4J Node: " + node);
            case 7:
                writeProcessingInstruction((ProcessingInstruction) node);
                return;
            case 8:
                writeComment((Comment) node);
                return;
            case 9:
                writeDocument((Document) node);
                return;
            case 10:
                writeDocumentType((DocumentType) node);
                return;
            case 13:
                writeNamespace((Namespace) node);
                return;
        }
    }

    public void writeChildNodes(Branch branch) throws XMLStreamException {
        int nodeCount = branch.nodeCount();
        for (int i = 0; i < nodeCount; i++) {
            writeNode(branch.node(i));
        }
    }

    public void writeElement(Element element) throws XMLStreamException {
        this.consumer.add(createStartElement(element));
        writeChildNodes(element);
        this.consumer.add(createEndElement(element));
    }

    public StartElement createStartElement(Element element) {
        return this.factory.createStartElement(createQName(element.getQName()), new AttributeIterator(element.attributeIterator()), new NamespaceIterator(element.declaredNamespaces().iterator()));
    }

    public EndElement createEndElement(Element element) {
        return this.factory.createEndElement(createQName(element.getQName()), new NamespaceIterator(element.declaredNamespaces().iterator()));
    }

    public void writeAttribute(Attribute attribute) throws XMLStreamException {
        this.consumer.add(createAttribute(attribute));
    }

    public javax.xml.stream.events.Attribute createAttribute(Attribute attribute) {
        return this.factory.createAttribute(createQName(attribute.getQName()), attribute.getValue());
    }

    public void writeNamespace(Namespace namespace) throws XMLStreamException {
        this.consumer.add(createNamespace(namespace));
    }

    public javax.xml.stream.events.Namespace createNamespace(Namespace namespace) {
        return this.factory.createNamespace(namespace.getPrefix(), namespace.getURI());
    }

    public void writeText(Text text) throws XMLStreamException {
        this.consumer.add(createCharacters(text));
    }

    public Characters createCharacters(Text text) {
        return this.factory.createCharacters(text.getText());
    }

    public void writeCDATA(CDATA cdata) throws XMLStreamException {
        this.consumer.add(createCharacters(cdata));
    }

    public Characters createCharacters(CDATA cdata) {
        return this.factory.createCData(cdata.getText());
    }

    public void writeComment(Comment comment) throws XMLStreamException {
        this.consumer.add(createComment(comment));
    }

    public javax.xml.stream.events.Comment createComment(Comment comment) {
        return this.factory.createComment(comment.getText());
    }

    public void writeProcessingInstruction(ProcessingInstruction processingInstruction) throws XMLStreamException {
        this.consumer.add(createProcessingInstruction(processingInstruction));
    }

    public javax.xml.stream.events.ProcessingInstruction createProcessingInstruction(ProcessingInstruction processingInstruction) {
        return this.factory.createProcessingInstruction(processingInstruction.getTarget(), processingInstruction.getText());
    }

    public void writeEntity(Entity entity) throws XMLStreamException {
        this.consumer.add(createEntityReference(entity));
    }

    private EntityReference createEntityReference(Entity entity) {
        return this.factory.createEntityReference(entity.getName(), (EntityDeclaration) null);
    }

    public void writeDocumentType(DocumentType documentType) throws XMLStreamException {
        this.consumer.add(createDTD(documentType));
    }

    public DTD createDTD(DocumentType documentType) {
        StringWriter stringWriter = new StringWriter();
        try {
            documentType.write(stringWriter);
            return this.factory.createDTD(stringWriter.toString());
        } catch (IOException e) {
            throw new RuntimeException("Error writing DTD", e);
        }
    }

    public void writeDocument(Document document) throws XMLStreamException {
        this.consumer.add(createStartDocument(document));
        writeChildNodes(document);
        this.consumer.add(createEndDocument(document));
    }

    public StartDocument createStartDocument(Document document) {
        String xMLEncoding = document.getXMLEncoding();
        if (xMLEncoding != null) {
            return this.factory.createStartDocument(xMLEncoding);
        }
        return this.factory.createStartDocument();
    }

    public EndDocument createEndDocument(Document document) {
        return this.factory.createEndDocument();
    }

    public QName createQName(org.dom4j.QName qName) {
        return new QName(qName.getNamespaceURI(), qName.getName(), qName.getNamespacePrefix());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: org.dom4j.io.STAXEventWriter$AttributeIterator */
    /* loaded from: classes4.dex */
    public class AttributeIterator implements Iterator<javax.xml.stream.events.Attribute> {
        private Iterator<Attribute> iter;

        public AttributeIterator(Iterator<Attribute> it2) {
            this.iter = it2;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.iter.hasNext();
        }

        @Override // java.util.Iterator
        public javax.xml.stream.events.Attribute next() {
            Attribute next = this.iter.next();
            return STAXEventWriter.this.factory.createAttribute(STAXEventWriter.this.createQName(next.getQName()), next.getValue());
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: org.dom4j.io.STAXEventWriter$NamespaceIterator */
    /* loaded from: classes4.dex */
    public class NamespaceIterator implements Iterator<javax.xml.stream.events.Namespace> {
        private Iterator<Namespace> iter;

        public NamespaceIterator(Iterator<Namespace> it2) {
            this.iter = it2;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.iter.hasNext();
        }

        @Override // java.util.Iterator
        public javax.xml.stream.events.Namespace next() {
            Namespace next = this.iter.next();
            return STAXEventWriter.this.factory.createNamespace(next.getPrefix(), next.getURI());
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
