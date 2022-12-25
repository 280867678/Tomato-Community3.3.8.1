package org.dom4j.p147io;

import org.dom4j.Element;
import org.dom4j.ElementHandler;
import org.dom4j.ElementPath;

/* renamed from: org.dom4j.io.ElementStack */
/* loaded from: classes4.dex */
class ElementStack implements ElementPath {
    private DispatchHandler handler;
    protected int lastElementIndex;
    protected Element[] stack;

    public ElementStack() {
        this(50);
    }

    public ElementStack(int i) {
        this.lastElementIndex = -1;
        this.handler = null;
        this.stack = new Element[i];
    }

    public void setDispatchHandler(DispatchHandler dispatchHandler) {
        this.handler = dispatchHandler;
    }

    public DispatchHandler getDispatchHandler() {
        return this.handler;
    }

    public void clear() {
        this.lastElementIndex = -1;
    }

    public Element peekElement() {
        int i = this.lastElementIndex;
        if (i < 0) {
            return null;
        }
        return this.stack[i];
    }

    public Element popElement() {
        int i = this.lastElementIndex;
        if (i < 0) {
            return null;
        }
        Element[] elementArr = this.stack;
        this.lastElementIndex = i - 1;
        return elementArr[i];
    }

    public void pushElement(Element element) {
        int length = this.stack.length;
        int i = this.lastElementIndex + 1;
        this.lastElementIndex = i;
        if (i >= length) {
            reallocate(length * 2);
        }
        this.stack[this.lastElementIndex] = element;
    }

    protected void reallocate(int i) {
        Element[] elementArr = this.stack;
        this.stack = new Element[i];
        System.arraycopy(elementArr, 0, this.stack, 0, elementArr.length);
    }

    @Override // org.dom4j.ElementPath
    public int size() {
        return this.lastElementIndex + 1;
    }

    @Override // org.dom4j.ElementPath
    public Element getElement(int i) {
        try {
            return this.stack[i];
        } catch (ArrayIndexOutOfBoundsException unused) {
            return null;
        }
    }

    @Override // org.dom4j.ElementPath
    public String getPath() {
        if (this.handler == null) {
            setDispatchHandler(new DispatchHandler());
        }
        return this.handler.getPath();
    }

    @Override // org.dom4j.ElementPath
    public Element getCurrent() {
        return peekElement();
    }

    @Override // org.dom4j.ElementPath
    public void addHandler(String str, ElementHandler elementHandler) {
        this.handler.addHandler(getHandlerPath(str), elementHandler);
    }

    @Override // org.dom4j.ElementPath
    public void removeHandler(String str) {
        this.handler.removeHandler(getHandlerPath(str));
    }

    public boolean containsHandler(String str) {
        return this.handler.containsHandler(str);
    }

    private String getHandlerPath(String str) {
        if (this.handler == null) {
            setDispatchHandler(new DispatchHandler());
        }
        if (str.startsWith("/")) {
            return str;
        }
        if (getPath().equals("/")) {
            return getPath() + str;
        }
        return getPath() + "/" + str;
    }
}
