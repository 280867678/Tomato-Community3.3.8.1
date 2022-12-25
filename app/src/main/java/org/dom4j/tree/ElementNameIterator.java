package org.dom4j.tree;

import java.util.Iterator;
import org.dom4j.Element;
import org.dom4j.Node;

/* loaded from: classes4.dex */
public class ElementNameIterator extends FilterIterator<Node> {
    private String name;

    public ElementNameIterator(Iterator<Node> it2, String str) {
        super(it2);
        this.name = str;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.dom4j.tree.FilterIterator
    public boolean matches(Node node) {
        if (node instanceof Element) {
            return this.name.equals(((Element) node).getName());
        }
        return false;
    }
}
