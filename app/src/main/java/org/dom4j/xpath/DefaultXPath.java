package org.dom4j.xpath;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.dom4j.InvalidXPathException;
import org.dom4j.Node;
import org.dom4j.NodeFilter;
import org.dom4j.XPath;
import org.dom4j.XPathException;
import org.jaxen.FunctionContext;
import org.jaxen.JaxenException;
import org.jaxen.NamespaceContext;
import org.jaxen.SimpleNamespaceContext;
import org.jaxen.VariableContext;
import org.jaxen.dom4j.Dom4jXPath;

/* loaded from: classes4.dex */
public class DefaultXPath implements XPath, NodeFilter, Serializable {
    private NamespaceContext namespaceContext;
    private String text;
    private org.jaxen.XPath xpath;

    public DefaultXPath(String str) throws InvalidXPathException {
        this.text = str;
        this.xpath = parse(str);
    }

    public String toString() {
        return "[XPath: " + this.xpath + "]";
    }

    @Override // org.dom4j.XPath
    public String getText() {
        return this.text;
    }

    @Override // org.dom4j.XPath
    public FunctionContext getFunctionContext() {
        return this.xpath.getFunctionContext();
    }

    @Override // org.dom4j.XPath
    public void setFunctionContext(FunctionContext functionContext) {
        this.xpath.setFunctionContext(functionContext);
    }

    @Override // org.dom4j.XPath
    public NamespaceContext getNamespaceContext() {
        return this.namespaceContext;
    }

    @Override // org.dom4j.XPath
    public void setNamespaceURIs(Map<String, String> map) {
        setNamespaceContext(new SimpleNamespaceContext(map));
    }

    @Override // org.dom4j.XPath
    public void setNamespaceContext(NamespaceContext namespaceContext) {
        this.namespaceContext = namespaceContext;
        this.xpath.setNamespaceContext(namespaceContext);
    }

    @Override // org.dom4j.XPath
    public VariableContext getVariableContext() {
        return this.xpath.getVariableContext();
    }

    @Override // org.dom4j.XPath
    public void setVariableContext(VariableContext variableContext) {
        this.xpath.setVariableContext(variableContext);
    }

    @Override // org.dom4j.XPath
    public Object evaluate(Object obj) {
        try {
            setNSContext(obj);
            List selectNodes = this.xpath.selectNodes(obj);
            return (selectNodes == null || selectNodes.size() != 1) ? selectNodes : selectNodes.get(0);
        } catch (JaxenException e) {
            handleJaxenException(e);
            return null;
        }
    }

    @Override // org.dom4j.XPath
    public Object selectObject(Object obj) {
        return evaluate(obj);
    }

    @Override // org.dom4j.XPath
    public List<Node> selectNodes(Object obj) {
        try {
            setNSContext(obj);
            return this.xpath.selectNodes(obj);
        } catch (JaxenException e) {
            handleJaxenException(e);
            return Collections.emptyList();
        }
    }

    @Override // org.dom4j.XPath
    public List<Node> selectNodes(Object obj, XPath xPath) {
        List<Node> selectNodes = selectNodes(obj);
        xPath.sort(selectNodes);
        return selectNodes;
    }

    @Override // org.dom4j.XPath
    public List<Node> selectNodes(Object obj, XPath xPath, boolean z) {
        List<Node> selectNodes = selectNodes(obj);
        xPath.sort(selectNodes, z);
        return selectNodes;
    }

    @Override // org.dom4j.XPath
    public Node selectSingleNode(Object obj) {
        try {
            setNSContext(obj);
            Object selectSingleNode = this.xpath.selectSingleNode(obj);
            if (selectSingleNode instanceof Node) {
                return (Node) selectSingleNode;
            }
            if (selectSingleNode == null) {
                return null;
            }
            throw new XPathException("The result of the XPath expression is not a Node. It was: " + selectSingleNode + " of type: " + selectSingleNode.getClass().getName());
        } catch (JaxenException e) {
            handleJaxenException(e);
            return null;
        }
    }

    @Override // org.dom4j.XPath
    public String valueOf(Object obj) {
        try {
            setNSContext(obj);
            return this.xpath.stringValueOf(obj);
        } catch (JaxenException e) {
            handleJaxenException(e);
            return "";
        }
    }

    @Override // org.dom4j.XPath
    public Number numberValueOf(Object obj) {
        try {
            setNSContext(obj);
            return this.xpath.numberValueOf(obj);
        } catch (JaxenException e) {
            handleJaxenException(e);
            return null;
        }
    }

    @Override // org.dom4j.XPath
    public boolean booleanValueOf(Object obj) {
        try {
            setNSContext(obj);
            return this.xpath.booleanValueOf(obj);
        } catch (JaxenException e) {
            handleJaxenException(e);
            return false;
        }
    }

    @Override // org.dom4j.XPath
    public void sort(List<Node> list) {
        sort(list, false);
    }

    @Override // org.dom4j.XPath
    public void sort(List<Node> list, boolean z) {
        if (list == null || list.isEmpty()) {
            return;
        }
        HashMap hashMap = new HashMap(list.size());
        for (Node node : list) {
            hashMap.put(node, getCompareValue(node));
        }
        sort(list, hashMap);
        if (!z) {
            return;
        }
        removeDuplicates(list, hashMap);
    }

    @Override // org.dom4j.XPath, org.dom4j.NodeFilter
    public boolean matches(Node node) {
        try {
            setNSContext(node);
            List selectNodes = this.xpath.selectNodes(node);
            if (selectNodes == null || selectNodes.size() <= 0) {
                return false;
            }
            Object obj = selectNodes.get(0);
            if (obj instanceof Boolean) {
                return ((Boolean) obj).booleanValue();
            }
            return selectNodes.contains(node);
        } catch (JaxenException e) {
            handleJaxenException(e);
            return false;
        }
    }

    protected void sort(List<Node> list, final Map<Node, Object> map) {
        Collections.sort(list, new Comparator<Node>() { // from class: org.dom4j.xpath.DefaultXPath.1
            @Override // java.util.Comparator
            public int compare(Node node, Node node2) {
                Object obj = map.get(node);
                Object obj2 = map.get(node2);
                if (obj == obj2) {
                    return 0;
                }
                if (obj instanceof Comparable) {
                    return ((Comparable) obj).compareTo(obj2);
                }
                if (obj == null) {
                    return 1;
                }
                return (obj2 != null && obj.equals(obj2)) ? 0 : -1;
            }
        });
    }

    protected void removeDuplicates(List<Node> list, Map<Node, Object> map) {
        HashSet hashSet = new HashSet();
        Iterator<Node> it2 = list.iterator();
        while (it2.hasNext()) {
            Object obj = map.get(it2.next());
            if (hashSet.contains(obj)) {
                it2.remove();
            } else {
                hashSet.add(obj);
            }
        }
    }

    protected Object getCompareValue(Node node) {
        return valueOf(node);
    }

    protected static org.jaxen.XPath parse(String str) {
        try {
            return new Dom4jXPath(str);
        } catch (RuntimeException unused) {
            throw new InvalidXPathException(str);
        } catch (JaxenException e) {
            throw new InvalidXPathException(str, e.getMessage());
        }
    }

    protected void setNSContext(Object obj) {
        if (this.namespaceContext == null) {
            this.xpath.setNamespaceContext(DefaultNamespaceContext.create(obj));
        }
    }

    protected void handleJaxenException(JaxenException jaxenException) throws XPathException {
        throw new XPathException(this.text, (Exception) jaxenException);
    }
}
