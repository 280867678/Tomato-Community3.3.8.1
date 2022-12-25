package org.dom4j.tree;

import com.j256.ormlite.stmt.query.SimpleComparison;
import com.tomatolive.library.utils.ConstantUtils;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import org.dom4j.Element;
import org.dom4j.ProcessingInstruction;
import org.dom4j.Visitor;

/* loaded from: classes4.dex */
public abstract class AbstractProcessingInstruction extends AbstractNode implements ProcessingInstruction {
    @Override // org.dom4j.tree.AbstractNode, org.dom4j.Node
    public short getNodeType() {
        return (short) 7;
    }

    @Override // org.dom4j.ProcessingInstruction
    public boolean removeValue(String str) {
        return false;
    }

    @Override // org.dom4j.Node
    public String getPath(Element element) {
        Element parent = getParent();
        if (parent == null || parent == element) {
            return "processing-instruction()";
        }
        return parent.getPath(element) + "/processing-instruction()";
    }

    @Override // org.dom4j.Node
    public String getUniquePath(Element element) {
        Element parent = getParent();
        if (parent == null || parent == element) {
            return "processing-instruction()";
        }
        return parent.getUniquePath(element) + "/processing-instruction()";
    }

    public String toString() {
        return super.toString() + " [ProcessingInstruction: &" + getName() + ";]";
    }

    @Override // org.dom4j.Node
    public String asXML() {
        return "<?" + getName() + ConstantUtils.PLACEHOLDER_STR_ONE + getText() + "?>";
    }

    @Override // org.dom4j.tree.AbstractNode, org.dom4j.Node
    public void write(Writer writer) throws IOException {
        writer.write("<?");
        writer.write(getName());
        writer.write(ConstantUtils.PLACEHOLDER_STR_ONE);
        writer.write(getText());
        writer.write("?>");
    }

    @Override // org.dom4j.Node
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override // org.dom4j.ProcessingInstruction
    public void setValue(String str, String str2) {
        throw new UnsupportedOperationException("This PI is read-only and cannot be modified");
    }

    @Override // org.dom4j.ProcessingInstruction
    public void setValues(Map<String, String> map) {
        throw new UnsupportedOperationException("This PI is read-only and cannot be modified");
    }

    @Override // org.dom4j.tree.AbstractNode, org.dom4j.Node
    public String getName() {
        return getTarget();
    }

    @Override // org.dom4j.tree.AbstractNode, org.dom4j.Node
    public void setName(String str) {
        setTarget(str);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public String toString(Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            sb.append(entry.getKey());
            sb.append("=\"");
            sb.append(entry.getValue());
            sb.append("\" ");
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public Map<String, String> parseValues(String str) {
        HashMap hashMap = new HashMap();
        StringTokenizer stringTokenizer = new StringTokenizer(str, " ='\"", true);
        while (stringTokenizer.hasMoreTokens()) {
            String name = getName(stringTokenizer);
            if (stringTokenizer.hasMoreTokens()) {
                hashMap.put(name, getValue(stringTokenizer));
            }
        }
        return hashMap;
    }

    private String getName(StringTokenizer stringTokenizer) {
        StringBuilder sb = new StringBuilder(stringTokenizer.nextToken());
        while (stringTokenizer.hasMoreTokens()) {
            String nextToken = stringTokenizer.nextToken();
            if (nextToken.equals(SimpleComparison.EQUAL_TO_OPERATION)) {
                break;
            }
            sb.append(nextToken);
        }
        return sb.toString().trim();
    }

    private String getValue(StringTokenizer stringTokenizer) {
        String nextToken = stringTokenizer.nextToken();
        StringBuilder sb = new StringBuilder();
        while (stringTokenizer.hasMoreTokens() && !nextToken.equals("'") && !nextToken.equals("\"")) {
            nextToken = stringTokenizer.nextToken();
        }
        while (stringTokenizer.hasMoreTokens()) {
            String nextToken2 = stringTokenizer.nextToken();
            if (nextToken.equals(nextToken2)) {
                break;
            }
            sb.append(nextToken2);
        }
        return sb.toString();
    }
}
