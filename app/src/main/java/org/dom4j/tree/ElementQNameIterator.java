package org.dom4j.tree;

import java.util.Iterator;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.QName;

/* loaded from: classes4.dex */
public class ElementQNameIterator extends FilterIterator<Node> {
    private QName qName;

    public ElementQNameIterator(Iterator<Node> it2, QName qName) {
        super(it2);
        this.qName = qName;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.dom4j.tree.FilterIterator
    public boolean matches(Node node) {
        if (node instanceof Element) {
            return this.qName.equals(((Element) node).getQName());
        }
        return false;
    }
}
