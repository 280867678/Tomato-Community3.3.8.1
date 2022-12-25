package org.dom4j.datatype;

import com.sun.msv.datatype.xsd.XSDatatype;
import java.util.HashMap;
import java.util.Map;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.QName;

/* loaded from: classes4.dex */
class NamedTypeResolver {
    protected DocumentFactory documentFactory;
    protected Map<QName, DocumentFactory> complexTypeMap = new HashMap();
    protected Map<QName, XSDatatype> simpleTypeMap = new HashMap();
    protected Map<Element, QName> typedElementMap = new HashMap();
    protected Map<Element, DocumentFactory> elementFactoryMap = new HashMap();

    /* JADX INFO: Access modifiers changed from: package-private */
    public NamedTypeResolver(DocumentFactory documentFactory) {
        this.documentFactory = documentFactory;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void registerComplexType(QName qName, DocumentFactory documentFactory) {
        this.complexTypeMap.put(qName, documentFactory);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void registerSimpleType(QName qName, XSDatatype xSDatatype) {
        this.simpleTypeMap.put(qName, xSDatatype);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void registerTypedElement(Element element, QName qName, DocumentFactory documentFactory) {
        this.typedElementMap.put(element, qName);
        this.elementFactoryMap.put(element, documentFactory);
    }

    void resolveElementTypes() {
        for (Element element : this.typedElementMap.keySet()) {
            QName qNameOfSchemaElement = getQNameOfSchemaElement(element);
            QName qName = this.typedElementMap.get(element);
            if (this.complexTypeMap.containsKey(qName)) {
                qNameOfSchemaElement.setDocumentFactory(this.complexTypeMap.get(qName));
            } else if (this.simpleTypeMap.containsKey(qName)) {
                XSDatatype xSDatatype = this.simpleTypeMap.get(qName);
                DocumentFactory documentFactory = this.elementFactoryMap.get(element);
                if (documentFactory instanceof DatatypeElementFactory) {
                    ((DatatypeElementFactory) documentFactory).setChildElementXSDatatype(qNameOfSchemaElement, xSDatatype);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void resolveNamedTypes() {
        resolveElementTypes();
    }

    private QName getQNameOfSchemaElement(Element element) {
        return getQName(element.attributeValue("name"));
    }

    private QName getQName(String str) {
        return this.documentFactory.createQName(str);
    }
}
