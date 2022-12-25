package org.dom4j.datatype;

import java.io.PrintStream;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.QName;
import org.dom4j.p147io.SAXReader;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

/* loaded from: classes4.dex */
public class DatatypeDocumentFactory extends DocumentFactory {
    private static final boolean DO_INTERN_QNAME = false;
    protected static transient DatatypeDocumentFactory singleton = new DatatypeDocumentFactory();
    private static final Namespace XSI_NAMESPACE = Namespace.get("xsi", "http://www.w3.org/2001/XMLSchema-instance");
    private static final QName XSI_SCHEMA_LOCATION = QName.get("schemaLocation", XSI_NAMESPACE);
    private static final QName XSI_NO_SCHEMA_LOCATION = QName.get("noNamespaceSchemaLocation", XSI_NAMESPACE);
    private SAXReader xmlSchemaReader = new SAXReader();
    private boolean autoLoadSchema = true;
    private SchemaParser schemaBuilder = new SchemaParser(this);

    public static DocumentFactory getInstance() {
        return singleton;
    }

    public void loadSchema(Document document) {
        this.schemaBuilder.build(document);
    }

    public void loadSchema(Document document, Namespace namespace) {
        this.schemaBuilder.build(document, namespace);
    }

    public DatatypeElementFactory getElementFactory(QName qName) {
        DocumentFactory documentFactory = qName.getDocumentFactory();
        if (documentFactory instanceof DatatypeElementFactory) {
            return (DatatypeElementFactory) documentFactory;
        }
        return null;
    }

    @Override // org.dom4j.DocumentFactory
    public Attribute createAttribute(Element element, QName qName, String str) {
        Document document = null;
        if (this.autoLoadSchema && qName.equals(XSI_NO_SCHEMA_LOCATION)) {
            if (element != null) {
                document = element.getDocument();
            }
            loadSchema(document, str);
        } else if (this.autoLoadSchema && qName.equals(XSI_SCHEMA_LOCATION)) {
            if (element != null) {
                document = element.getDocument();
            }
            loadSchema(document, str.substring(str.indexOf(32) + 1), element.getNamespaceForURI(str.substring(0, str.indexOf(32))));
        }
        return super.createAttribute(element, qName, str);
    }

    protected void loadSchema(Document document, String str) {
        try {
            EntityResolver entityResolver = document.getEntityResolver();
            if (entityResolver == null) {
                throw new InvalidSchemaException("No EntityResolver available for resolving URI: " + str);
            }
            InputSource resolveEntity = entityResolver.resolveEntity(null, str);
            if (entityResolver == null) {
                throw new InvalidSchemaException("Could not resolve the URI: " + str);
            }
            loadSchema(this.xmlSchemaReader.read(resolveEntity));
        } catch (Exception e) {
            PrintStream printStream = System.out;
            printStream.println("Failed to load schema: " + str);
            PrintStream printStream2 = System.out;
            printStream2.println("Caught: " + e);
            e.printStackTrace();
            throw new InvalidSchemaException("Failed to load schema: " + str);
        }
    }

    protected void loadSchema(Document document, String str, Namespace namespace) {
        try {
            EntityResolver entityResolver = document.getEntityResolver();
            if (entityResolver == null) {
                throw new InvalidSchemaException("No EntityResolver available for resolving URI: " + str);
            }
            InputSource resolveEntity = entityResolver.resolveEntity(null, str);
            if (entityResolver == null) {
                throw new InvalidSchemaException("Could not resolve the URI: " + str);
            }
            loadSchema(this.xmlSchemaReader.read(resolveEntity), namespace);
        } catch (Exception e) {
            PrintStream printStream = System.out;
            printStream.println("Failed to load schema: " + str);
            PrintStream printStream2 = System.out;
            printStream2.println("Caught: " + e);
            e.printStackTrace();
            throw new InvalidSchemaException("Failed to load schema: " + str);
        }
    }
}
