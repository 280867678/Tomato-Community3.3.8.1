package org.dom4j.datatype;

import com.sun.msv.datatype.xsd.DatatypeFactory;
import com.sun.msv.datatype.xsd.TypeIncubator;
import com.sun.msv.datatype.xsd.XSDatatype;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.QName;
import org.dom4j.p147io.SAXReader;
import org.dom4j.util.AttributeHelper;
import org.relaxng.datatype.DatatypeException;
import org.relaxng.datatype.ValidationContext;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

/* loaded from: classes4.dex */
public class SchemaParser {
    private Map<String, XSDatatype> dataTypeCache;
    private DatatypeDocumentFactory documentFactory;
    private NamedTypeResolver namedTypeResolver;
    private Namespace targetNamespace;
    private static final Namespace XSD_NAMESPACE = Namespace.get("xsd", "http://www.w3.org/2001/XMLSchema");
    private static final QName XSD_ELEMENT = QName.get("element", XSD_NAMESPACE);
    private static final QName XSD_ATTRIBUTE = QName.get("attribute", XSD_NAMESPACE);
    private static final QName XSD_SIMPLETYPE = QName.get("simpleType", XSD_NAMESPACE);
    private static final QName XSD_COMPLEXTYPE = QName.get("complexType", XSD_NAMESPACE);
    private static final QName XSD_RESTRICTION = QName.get("restriction", XSD_NAMESPACE);
    private static final QName XSD_SEQUENCE = QName.get("sequence", XSD_NAMESPACE);
    private static final QName XSD_CHOICE = QName.get("choice", XSD_NAMESPACE);
    private static final QName XSD_ALL = QName.get("all", XSD_NAMESPACE);
    private static final QName XSD_INCLUDE = QName.get("include", XSD_NAMESPACE);

    public SchemaParser() {
        this(DatatypeDocumentFactory.singleton);
    }

    public SchemaParser(DatatypeDocumentFactory datatypeDocumentFactory) {
        this.dataTypeCache = new HashMap();
        this.documentFactory = datatypeDocumentFactory;
        this.namedTypeResolver = new NamedTypeResolver(datatypeDocumentFactory);
    }

    public void build(Document document) {
        this.targetNamespace = null;
        internalBuild(document);
    }

    public void build(Document document, Namespace namespace) {
        this.targetNamespace = namespace;
        internalBuild(document);
    }

    private synchronized void internalBuild(Document document) {
        Element rootElement = document.getRootElement();
        if (rootElement != null) {
            for (Element element : rootElement.elements(XSD_INCLUDE)) {
                String attributeValue = element.attributeValue("schemaLocation");
                EntityResolver entityResolver = document.getEntityResolver();
                if (entityResolver == null) {
                    throw new InvalidSchemaException("No EntityResolver available");
                }
                try {
                    InputSource resolveEntity = entityResolver.resolveEntity(null, attributeValue);
                    if (resolveEntity == null) {
                        throw new InvalidSchemaException("Could not resolve the schema URI: " + attributeValue);
                    }
                    build(new SAXReader().read(resolveEntity));
                } catch (Exception e) {
                    PrintStream printStream = System.out;
                    printStream.println("Failed to load schema: " + attributeValue);
                    PrintStream printStream2 = System.out;
                    printStream2.println("Caught: " + e);
                    e.printStackTrace();
                    throw new InvalidSchemaException("Failed to load schema: " + attributeValue);
                }
            }
            for (Element element2 : rootElement.elements(XSD_ELEMENT)) {
                onDatatypeElement(element2, this.documentFactory);
            }
            for (Element element3 : rootElement.elements(XSD_SIMPLETYPE)) {
                onNamedSchemaSimpleType(element3);
            }
            for (Element element4 : rootElement.elements(XSD_COMPLEXTYPE)) {
                onNamedSchemaComplexType(element4);
            }
            this.namedTypeResolver.resolveNamedTypes();
        }
    }

    private void onDatatypeElement(Element element, DocumentFactory documentFactory) {
        DatatypeElementFactory datatypeElementFactory;
        XSDatatype loadXSDatatypeFromSimpleType;
        String attributeValue = element.attributeValue("name");
        String attributeValue2 = element.attributeValue("type");
        QName qName = null;
        if (attributeValue != null) {
            qName = getQName(attributeValue);
            datatypeElementFactory = getDatatypeElementFactory(qName);
        } else {
            datatypeElementFactory = null;
        }
        if (attributeValue2 != null) {
            XSDatatype typeByName = getTypeByName(attributeValue2);
            if (typeByName != null && datatypeElementFactory != null) {
                datatypeElementFactory.setChildElementXSDatatype(qName, typeByName);
                return;
            } else {
                this.namedTypeResolver.registerTypedElement(element, getQName(attributeValue2), documentFactory);
                return;
            }
        }
        Element element2 = element.element(XSD_SIMPLETYPE);
        if (element2 != null && (loadXSDatatypeFromSimpleType = loadXSDatatypeFromSimpleType(element2)) != null && datatypeElementFactory != null) {
            datatypeElementFactory.setChildElementXSDatatype(qName, loadXSDatatypeFromSimpleType);
        }
        Element element3 = element.element(XSD_COMPLEXTYPE);
        if (element3 != null && datatypeElementFactory != null) {
            onSchemaComplexType(element3, datatypeElementFactory);
        }
        if (datatypeElementFactory == null) {
            return;
        }
        Iterator<Element> elementIterator = element.elementIterator(XSD_ATTRIBUTE);
        if (!elementIterator.hasNext()) {
            return;
        }
        do {
            onDatatypeAttribute(element, datatypeElementFactory, elementIterator.next());
        } while (elementIterator.hasNext());
    }

    private void onNamedSchemaComplexType(Element element) {
        Attribute mo6817attribute = element.mo6817attribute("name");
        if (mo6817attribute == null) {
            return;
        }
        QName qName = getQName(mo6817attribute.getText());
        DatatypeElementFactory datatypeElementFactory = getDatatypeElementFactory(qName);
        onSchemaComplexType(element, datatypeElementFactory);
        this.namedTypeResolver.registerComplexType(qName, datatypeElementFactory);
    }

    private void onSchemaComplexType(Element element, DatatypeElementFactory datatypeElementFactory) {
        Iterator<Element> elementIterator = element.elementIterator(XSD_ATTRIBUTE);
        while (elementIterator.hasNext()) {
            Element next = elementIterator.next();
            QName qName = getQName(next.attributeValue("name"));
            XSDatatype dataTypeForXsdAttribute = dataTypeForXsdAttribute(next);
            if (dataTypeForXsdAttribute != null) {
                datatypeElementFactory.setAttributeXSDatatype(qName, dataTypeForXsdAttribute);
            }
        }
        Element element2 = element.element(XSD_SEQUENCE);
        if (element2 != null) {
            onChildElements(element2, datatypeElementFactory);
        }
        Element element3 = element.element(XSD_CHOICE);
        if (element3 != null) {
            onChildElements(element3, datatypeElementFactory);
        }
        Element element4 = element.element(XSD_ALL);
        if (element4 != null) {
            onChildElements(element4, datatypeElementFactory);
        }
    }

    private void onChildElements(Element element, DatatypeElementFactory datatypeElementFactory) {
        Iterator<Element> elementIterator = element.elementIterator(XSD_ELEMENT);
        while (elementIterator.hasNext()) {
            onDatatypeElement(elementIterator.next(), datatypeElementFactory);
        }
    }

    private void onDatatypeAttribute(Element element, DatatypeElementFactory datatypeElementFactory, Element element2) {
        String attributeValue = element2.attributeValue("name");
        QName qName = getQName(attributeValue);
        XSDatatype dataTypeForXsdAttribute = dataTypeForXsdAttribute(element2);
        if (dataTypeForXsdAttribute != null) {
            datatypeElementFactory.setAttributeXSDatatype(qName, dataTypeForXsdAttribute);
            return;
        }
        String attributeValue2 = element2.attributeValue("type");
        PrintStream printStream = System.out;
        printStream.println("Warning: Couldn't find XSDatatype for type: " + attributeValue2 + " attribute: " + attributeValue);
    }

    private XSDatatype dataTypeForXsdAttribute(Element element) {
        String attributeValue = element.attributeValue("type");
        if (attributeValue != null) {
            return getTypeByName(attributeValue);
        }
        Element element2 = element.element(XSD_SIMPLETYPE);
        if (element2 == null) {
            String attributeValue2 = element.attributeValue("name");
            throw new InvalidSchemaException("The attribute: " + attributeValue2 + " has no type attribute and does not contain a <simpleType/> element");
        }
        return loadXSDatatypeFromSimpleType(element2);
    }

    private void onNamedSchemaSimpleType(Element element) {
        Attribute mo6817attribute = element.mo6817attribute("name");
        if (mo6817attribute == null) {
            return;
        }
        this.namedTypeResolver.registerSimpleType(getQName(mo6817attribute.getText()), loadXSDatatypeFromSimpleType(element));
    }

    private XSDatatype loadXSDatatypeFromSimpleType(Element element) {
        Element element2 = element.element(XSD_RESTRICTION);
        if (element2 != null) {
            String attributeValue = element2.attributeValue("base");
            if (attributeValue != null) {
                XSDatatype typeByName = getTypeByName(attributeValue);
                if (typeByName == null) {
                    onSchemaError("Invalid base type: " + attributeValue + " when trying to build restriction: " + element2);
                    return null;
                }
                return deriveSimpleType(typeByName, element2);
            }
            Element element3 = element.element(XSD_SIMPLETYPE);
            if (element3 == null) {
                onSchemaError("The simpleType element: " + element + " must contain a base attribute or simpleType element");
                return null;
            }
            return loadXSDatatypeFromSimpleType(element3);
        }
        onSchemaError("No <restriction>. Could not create XSDatatype for simpleType: " + element);
        return null;
    }

    private XSDatatype deriveSimpleType(XSDatatype xSDatatype, Element element) {
        TypeIncubator typeIncubator = new TypeIncubator(xSDatatype);
        try {
            Iterator<Element> elementIterator = element.elementIterator();
            while (elementIterator.hasNext()) {
                Element next = elementIterator.next();
                typeIncubator.addFacet(next.getName(), next.attributeValue("value"), AttributeHelper.booleanValue(next, "fixed"), (ValidationContext) null);
            }
            return typeIncubator.derive("", (String) null);
        } catch (DatatypeException e) {
            onSchemaError("Invalid restriction: " + e.getMessage() + " when trying to build restriction: " + element);
            return null;
        }
    }

    private DatatypeElementFactory getDatatypeElementFactory(QName qName) {
        DatatypeElementFactory elementFactory = this.documentFactory.getElementFactory(qName);
        if (elementFactory == null) {
            DatatypeElementFactory datatypeElementFactory = new DatatypeElementFactory(qName);
            qName.setDocumentFactory(datatypeElementFactory);
            return datatypeElementFactory;
        }
        return elementFactory;
    }

    private XSDatatype getTypeByName(String str) {
        XSDatatype xSDatatype = this.dataTypeCache.get(str);
        if (xSDatatype == null) {
            int indexOf = str.indexOf(58);
            if (indexOf >= 0) {
                try {
                    xSDatatype = DatatypeFactory.getTypeByName(str.substring(indexOf + 1));
                } catch (DatatypeException unused) {
                }
            }
            if (xSDatatype == null) {
                try {
                    xSDatatype = DatatypeFactory.getTypeByName(str);
                } catch (DatatypeException unused2) {
                }
            }
            if (xSDatatype == null) {
                xSDatatype = this.namedTypeResolver.simpleTypeMap.get(getQName(str));
            }
            if (xSDatatype != null) {
                this.dataTypeCache.put(str, xSDatatype);
            }
        }
        return xSDatatype;
    }

    private QName getQName(String str) {
        Namespace namespace = this.targetNamespace;
        if (namespace == null) {
            return this.documentFactory.createQName(str);
        }
        return this.documentFactory.createQName(str, namespace);
    }

    private void onSchemaError(String str) {
        throw new InvalidSchemaException(str);
    }
}
