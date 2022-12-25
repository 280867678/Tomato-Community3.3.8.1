package org.dom4j.swing;

import java.io.PrintStream;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.XPath;

/* loaded from: classes4.dex */
public class XMLTableModel extends AbstractTableModel {
    private XMLTableDefinition definition;
    private List<Node> rows;
    private Object source;

    public XMLTableModel(Element element, Object obj) {
        this(XMLTableDefinition.load(element), obj);
    }

    public XMLTableModel(Document document, Object obj) {
        this(XMLTableDefinition.load(document), obj);
    }

    public XMLTableModel(XMLTableDefinition xMLTableDefinition, Object obj) {
        this.definition = xMLTableDefinition;
        this.source = obj;
    }

    public Object getRowValue(int i) {
        return getRows().get(i);
    }

    public List<Node> getRows() {
        if (this.rows == null) {
            this.rows = this.definition.getRowXPath().selectNodes(this.source);
        }
        return this.rows;
    }

    public Class<?> getColumnClass(int i) {
        return this.definition.getColumnClass(i);
    }

    public int getColumnCount() {
        return this.definition.getColumnCount();
    }

    public String getColumnName(int i) {
        XPath columnNameXPath = this.definition.getColumnNameXPath(i);
        if (columnNameXPath != null) {
            PrintStream printStream = System.out;
            printStream.println("Evaluating column xpath: " + columnNameXPath + " value: " + columnNameXPath.valueOf(this.source));
            return columnNameXPath.valueOf(this.source);
        }
        return this.definition.getColumnName(i);
    }

    public Object getValueAt(int i, int i2) {
        try {
            return this.definition.getValueAt(getRowValue(i), i2);
        } catch (Exception e) {
            handleException(e);
            return null;
        }
    }

    public int getRowCount() {
        return getRows().size();
    }

    public XMLTableDefinition getDefinition() {
        return this.definition;
    }

    public void setDefinition(XMLTableDefinition xMLTableDefinition) {
        this.definition = xMLTableDefinition;
    }

    public Object getSource() {
        return this.source;
    }

    public void setSource(Object obj) {
        this.source = obj;
        this.rows = null;
    }

    protected void handleException(Exception exc) {
        PrintStream printStream = System.out;
        printStream.println("Caught: " + exc);
    }
}
