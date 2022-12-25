package org.dom4j;

import java.util.List;
import java.util.Map;
import org.jaxen.FunctionContext;
import org.jaxen.NamespaceContext;
import org.jaxen.VariableContext;

/* loaded from: classes4.dex */
public interface XPath extends NodeFilter {
    boolean booleanValueOf(Object obj);

    Object evaluate(Object obj);

    FunctionContext getFunctionContext();

    NamespaceContext getNamespaceContext();

    String getText();

    VariableContext getVariableContext();

    @Override // org.dom4j.NodeFilter
    boolean matches(Node node);

    Number numberValueOf(Object obj);

    List<Node> selectNodes(Object obj);

    List<Node> selectNodes(Object obj, XPath xPath);

    List<Node> selectNodes(Object obj, XPath xPath, boolean z);

    Object selectObject(Object obj);

    Node selectSingleNode(Object obj);

    void setFunctionContext(FunctionContext functionContext);

    void setNamespaceContext(NamespaceContext namespaceContext);

    void setNamespaceURIs(Map<String, String> map);

    void setVariableContext(VariableContext variableContext);

    void sort(List<Node> list);

    void sort(List<Node> list, boolean z);

    String valueOf(Object obj);
}
