package org.dom4j.tree;

import java.util.Iterator;
import org.dom4j.Element;
import org.dom4j.Node;

/* loaded from: classes4.dex */
public class ElementIterator extends FilterIterator<Node> {
    public ElementIterator(Iterator<Node> it2) {
        super(it2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.dom4j.tree.FilterIterator
    public boolean matches(Node node) {
        return node instanceof Element;
    }
}
