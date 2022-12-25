package org.dom4j.p147io;

import org.dom4j.Element;
import org.dom4j.ElementHandler;
import org.dom4j.ElementPath;

/* renamed from: org.dom4j.io.SAXModifyElementHandler */
/* loaded from: classes4.dex */
class SAXModifyElementHandler implements ElementHandler {
    private ElementModifier elemModifier;
    private Element modifiedElement;

    public SAXModifyElementHandler(ElementModifier elementModifier) {
        this.elemModifier = elementModifier;
    }

    @Override // org.dom4j.ElementHandler
    public void onStart(ElementPath elementPath) {
        this.modifiedElement = elementPath.getCurrent();
    }

    @Override // org.dom4j.ElementHandler
    public void onEnd(ElementPath elementPath) {
        try {
            Element current = elementPath.getCurrent();
            Element parent = current.getParent();
            if (parent != null) {
                this.modifiedElement = this.elemModifier.modifyElement((Element) current.clone());
                if (this.modifiedElement != null) {
                    this.modifiedElement.setParent(current.getParent());
                    this.modifiedElement.setDocument(current.getDocument());
                    parent.content().set(parent.indexOf(current), this.modifiedElement);
                }
                current.detach();
            } else if (current.isRootElement()) {
                this.modifiedElement = this.elemModifier.modifyElement((Element) current.clone());
                if (this.modifiedElement != null) {
                    this.modifiedElement.setDocument(current.getDocument());
                    current.getDocument().setRootElement(this.modifiedElement);
                }
                current.detach();
            }
            if (!(elementPath instanceof ElementStack)) {
                return;
            }
            ElementStack elementStack = (ElementStack) elementPath;
            elementStack.popElement();
            elementStack.pushElement(this.modifiedElement);
        } catch (Exception e) {
            throw new SAXModifyException(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public Element getModifiedElement() {
        return this.modifiedElement;
    }
}
