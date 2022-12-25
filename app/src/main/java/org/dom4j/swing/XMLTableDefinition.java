package org.dom4j.swing;

import java.io.PrintStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.jaxen.VariableContext;

/* loaded from: classes4.dex */
public class XMLTableDefinition implements Serializable, VariableContext {
    private XMLTableColumnDefinition[] columnArray;
    private Map<String, XMLTableColumnDefinition> columnNameIndex;
    private List<XMLTableColumnDefinition> columns = new ArrayList();
    private Object rowValue;
    private XPath rowXPath;
    private VariableContext variableContext;

    public static XMLTableDefinition load(Document document) {
        return load(document.getRootElement());
    }

    public static XMLTableDefinition load(Element element) {
        XMLTableDefinition xMLTableDefinition = new XMLTableDefinition();
        xMLTableDefinition.setRowExpression(element.attributeValue("select"));
        Iterator<Element> elementIterator = element.elementIterator("column");
        while (elementIterator.hasNext()) {
            Element next = elementIterator.next();
            String attributeValue = next.attributeValue("select");
            String text = next.getText();
            String attributeValue2 = next.attributeValue("type", "string");
            String attributeValue3 = next.attributeValue("columnNameXPath");
            int parseType = XMLTableColumnDefinition.parseType(attributeValue2);
            if (attributeValue3 != null) {
                xMLTableDefinition.addColumnWithXPathName(attributeValue3, attributeValue, parseType);
            } else {
                xMLTableDefinition.addColumn(text, attributeValue, parseType);
            }
        }
        return xMLTableDefinition;
    }

    public Class<?> getColumnClass(int i) {
        return getColumn(i).getColumnClass();
    }

    public int getColumnCount() {
        return this.columns.size();
    }

    public String getColumnName(int i) {
        return getColumn(i).getName();
    }

    public XPath getColumnXPath(int i) {
        return getColumn(i).getXPath();
    }

    public XPath getColumnNameXPath(int i) {
        return getColumn(i).getColumnNameXPath();
    }

    public synchronized Object getValueAt(Object obj, int i) {
        Object value;
        XMLTableColumnDefinition column = getColumn(i);
        synchronized (this) {
            this.rowValue = obj;
            value = column.getValue(obj);
            this.rowValue = null;
        }
        return value;
        return value;
    }

    public void addColumn(String str, String str2) {
        addColumn(str, str2, 0);
    }

    public void addColumn(String str, String str2, int i) {
        addColumn(new XMLTableColumnDefinition(str, createColumnXPath(str2), i));
    }

    public void addColumnWithXPathName(String str, String str2, int i) {
        addColumn(new XMLTableColumnDefinition(createColumnXPath(str), createColumnXPath(str2), i));
    }

    public void addStringColumn(String str, String str2) {
        addColumn(str, str2, 1);
    }

    public void addNumberColumn(String str, String str2) {
        addColumn(str, str2, 2);
    }

    public void addColumn(XMLTableColumnDefinition xMLTableColumnDefinition) {
        clearCaches();
        this.columns.add(xMLTableColumnDefinition);
    }

    public void removeColumn(XMLTableColumnDefinition xMLTableColumnDefinition) {
        clearCaches();
        this.columns.remove(xMLTableColumnDefinition);
    }

    public void clear() {
        clearCaches();
        this.columns.clear();
    }

    public XMLTableColumnDefinition getColumn(int i) {
        if (this.columnArray == null) {
            this.columnArray = new XMLTableColumnDefinition[this.columns.size()];
            this.columns.toArray(this.columnArray);
        }
        return this.columnArray[i];
    }

    public XMLTableColumnDefinition getColumn(String str) {
        if (this.columnNameIndex == null) {
            this.columnNameIndex = new HashMap();
            for (XMLTableColumnDefinition xMLTableColumnDefinition : this.columns) {
                this.columnNameIndex.put(xMLTableColumnDefinition.getName(), xMLTableColumnDefinition);
            }
        }
        return this.columnNameIndex.get(str);
    }

    public XPath getRowXPath() {
        return this.rowXPath;
    }

    public void setRowXPath(XPath xPath) {
        this.rowXPath = xPath;
    }

    public void setRowExpression(String str) {
        setRowXPath(createXPath(str));
    }

    public Object getVariableValue(String str, String str2, String str3) {
        XMLTableColumnDefinition column = getColumn(str3);
        if (column != null) {
            return column.getValue(this.rowValue);
        }
        return null;
    }

    protected XPath createXPath(String str) {
        return DocumentHelper.createXPath(str);
    }

    protected XPath createColumnXPath(String str) {
        XPath createXPath = createXPath(str);
        createXPath.setVariableContext(this);
        return createXPath;
    }

    protected void clearCaches() {
        this.columnArray = null;
        this.columnNameIndex = null;
    }

    protected void handleException(Exception exc) {
        PrintStream printStream = System.out;
        printStream.println("Caught: " + exc);
    }
}
