package org.dom4j.bean;

import java.util.AbstractList;
import org.dom4j.Attribute;
import org.dom4j.QName;

/* loaded from: classes4.dex */
public class BeanAttributeList extends AbstractList<Attribute> {
    private BeanAttribute[] attributes;
    private BeanMetaData beanMetaData;
    private BeanElement parent;

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean remove(Object obj) {
        return false;
    }

    public BeanAttributeList(BeanElement beanElement, BeanMetaData beanMetaData) {
        this.parent = beanElement;
        this.beanMetaData = beanMetaData;
        this.attributes = new BeanAttribute[beanMetaData.attributeCount()];
    }

    public BeanAttributeList(BeanElement beanElement) {
        this.parent = beanElement;
        Object data = beanElement.getData();
        this.beanMetaData = BeanMetaData.get(data != null ? data.getClass() : null);
        this.attributes = new BeanAttribute[this.beanMetaData.attributeCount()];
    }

    public BeanAttribute attribute(String str) {
        return attribute(this.beanMetaData.getIndex(str));
    }

    public BeanAttribute attribute(QName qName) {
        return attribute(this.beanMetaData.getIndex(qName));
    }

    public BeanAttribute attribute(int i) {
        if (i >= 0) {
            BeanAttribute[] beanAttributeArr = this.attributes;
            if (i > beanAttributeArr.length) {
                return null;
            }
            BeanAttribute beanAttribute = beanAttributeArr[i];
            if (beanAttribute != null) {
                return beanAttribute;
            }
            BeanAttribute createAttribute = createAttribute(this.parent, i);
            this.attributes[i] = createAttribute;
            return createAttribute;
        }
        return null;
    }

    public BeanElement getParent() {
        return this.parent;
    }

    public QName getQName(int i) {
        return this.beanMetaData.getQName(i);
    }

    public Object getData(int i) {
        return this.beanMetaData.getData(i, this.parent.getData());
    }

    public void setData(int i, Object obj) {
        this.beanMetaData.setData(i, this.parent.getData(), obj);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public int size() {
        return this.attributes.length;
    }

    @Override // java.util.AbstractList, java.util.List
    public BeanAttribute get(int i) {
        BeanAttribute beanAttribute = this.attributes[i];
        if (beanAttribute == null) {
            BeanAttribute createAttribute = createAttribute(this.parent, i);
            this.attributes[i] = createAttribute;
            return createAttribute;
        }
        return beanAttribute;
    }

    public boolean add(BeanAttribute beanAttribute) {
        throw new UnsupportedOperationException("add(Object) unsupported");
    }

    public void add(int i, BeanAttribute beanAttribute) {
        throw new UnsupportedOperationException("add(int,Object) unsupported");
    }

    public BeanAttribute set(int i, BeanAttribute beanAttribute) {
        throw new UnsupportedOperationException("set(int,Object) unsupported");
    }

    @Override // java.util.AbstractList, java.util.List
    public BeanAttribute remove(int i) {
        BeanAttribute beanAttribute = get(i);
        beanAttribute.setValue(null);
        return beanAttribute;
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public void clear() {
        BeanAttribute[] beanAttributeArr;
        for (BeanAttribute beanAttribute : this.attributes) {
            if (beanAttribute != null) {
                beanAttribute.setValue(null);
            }
        }
    }

    protected BeanAttribute createAttribute(BeanElement beanElement, int i) {
        return new BeanAttribute(this, i);
    }
}
