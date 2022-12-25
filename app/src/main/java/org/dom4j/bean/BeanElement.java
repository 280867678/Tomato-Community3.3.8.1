package org.dom4j.bean;

import java.util.List;
import org.dom4j.Attribute;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.QName;
import org.dom4j.tree.DefaultElement;
import org.dom4j.tree.NamespaceStack;
import org.xml.sax.Attributes;

/* loaded from: classes4.dex */
public class BeanElement extends DefaultElement {
    private static final DocumentFactory DOCUMENT_FACTORY = BeanDocumentFactory.getInstance();
    private Object bean;

    public BeanElement(String str, Object obj) {
        this(DOCUMENT_FACTORY.createQName(str), obj);
    }

    public BeanElement(String str, Namespace namespace, Object obj) {
        this(DOCUMENT_FACTORY.createQName(str, namespace), obj);
    }

    public BeanElement(QName qName, Object obj) {
        super(qName);
        this.bean = obj;
    }

    public BeanElement(QName qName) {
        super(qName);
    }

    @Override // org.dom4j.tree.AbstractElement, org.dom4j.Element
    public Object getData() {
        return this.bean;
    }

    @Override // org.dom4j.tree.AbstractElement, org.dom4j.Element
    public void setData(Object obj) {
        this.bean = obj;
        setAttributeList(null);
    }

    @Override // org.dom4j.tree.DefaultElement, org.dom4j.tree.AbstractElement, org.dom4j.Element
    /* renamed from: attribute  reason: collision with other method in class */
    public BeanAttribute mo6817attribute(String str) {
        return getBeanAttributeList().attribute(str);
    }

    @Override // org.dom4j.tree.DefaultElement, org.dom4j.tree.AbstractElement, org.dom4j.Element
    /* renamed from: attribute  reason: collision with other method in class */
    public BeanAttribute mo6818attribute(QName qName) {
        return getBeanAttributeList().attribute(qName);
    }

    @Override // org.dom4j.tree.AbstractElement, org.dom4j.Element
    public Element addAttribute(String str, String str2) {
        BeanAttribute mo6817attribute = mo6817attribute(str);
        if (mo6817attribute != null) {
            mo6817attribute.setValue(str2);
        }
        return this;
    }

    @Override // org.dom4j.tree.AbstractElement, org.dom4j.Element
    public Element addAttribute(QName qName, String str) {
        BeanAttribute mo6818attribute = mo6818attribute(qName);
        if (mo6818attribute != null) {
            mo6818attribute.setValue(str);
        }
        return this;
    }

    @Override // org.dom4j.tree.DefaultElement, org.dom4j.Element
    public void setAttributes(List<Attribute> list) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override // org.dom4j.tree.AbstractElement
    public void setAttributes(Attributes attributes, NamespaceStack namespaceStack, boolean z) {
        String value = attributes.getValue("class");
        if (value != null) {
            try {
                setData(Class.forName(value, true, BeanElement.class.getClassLoader()).newInstance());
                for (int i = 0; i < attributes.getLength(); i++) {
                    String localName = attributes.getLocalName(i);
                    if (!"class".equalsIgnoreCase(localName)) {
                        addAttribute(localName, attributes.getValue(i));
                    }
                }
                return;
            } catch (Exception e) {
                ((BeanDocumentFactory) getDocumentFactory()).handleException(e);
                return;
            }
        }
        super.setAttributes(attributes, namespaceStack, z);
    }

    @Override // org.dom4j.tree.DefaultElement, org.dom4j.tree.AbstractElement, org.dom4j.tree.AbstractNode
    protected DocumentFactory getDocumentFactory() {
        return DOCUMENT_FACTORY;
    }

    protected BeanAttributeList getBeanAttributeList() {
        return (BeanAttributeList) attributeList();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.dom4j.tree.AbstractElement
    public List<Attribute> createAttributeList() {
        return new BeanAttributeList(this);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.dom4j.tree.AbstractElement
    public List<Attribute> createAttributeList(int i) {
        return new BeanAttributeList(this);
    }
}
