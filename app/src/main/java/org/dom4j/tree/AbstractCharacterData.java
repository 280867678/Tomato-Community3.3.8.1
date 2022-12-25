package org.dom4j.tree;

import org.dom4j.CharacterData;
import org.dom4j.Element;

/* loaded from: classes4.dex */
public abstract class AbstractCharacterData extends AbstractNode implements CharacterData {
    @Override // org.dom4j.Node
    public String getPath(Element element) {
        Element parent = getParent();
        if (parent == null || parent == element) {
            return "text()";
        }
        return parent.getPath(element) + "/text()";
    }

    @Override // org.dom4j.Node
    public String getUniquePath(Element element) {
        Element parent = getParent();
        if (parent == null || parent == element) {
            return "text()";
        }
        return parent.getUniquePath(element) + "/text()";
    }

    @Override // org.dom4j.CharacterData
    public void appendText(String str) {
        setText(getText() + str);
    }
}
