package org.dom4j.xpath;

import java.util.Collections;
import org.dom4j.InvalidXPathException;
import org.dom4j.Node;
import org.dom4j.XPathException;
import org.dom4j.rule.Pattern;
import org.jaxen.Context;
import org.jaxen.ContextSupport;
import org.jaxen.JaxenException;
import org.jaxen.SimpleNamespaceContext;
import org.jaxen.SimpleVariableContext;
import org.jaxen.VariableContext;
import org.jaxen.XPathFunctionContext;
import org.jaxen.dom4j.DocumentNavigator;
import org.jaxen.pattern.PatternParser;
import org.jaxen.saxpath.SAXPathException;

/* loaded from: classes4.dex */
public class XPathPattern implements Pattern {
    private Context context = new Context(getContextSupport());
    private org.jaxen.pattern.Pattern pattern;
    private String text;

    public XPathPattern(org.jaxen.pattern.Pattern pattern) {
        this.pattern = pattern;
        this.text = pattern.getText();
    }

    public XPathPattern(String str) {
        this.text = str;
        try {
            this.pattern = PatternParser.parse(str);
        } catch (RuntimeException unused) {
            throw new InvalidXPathException(str);
        } catch (SAXPathException e) {
            throw new InvalidXPathException(str, e.getMessage());
        }
    }

    @Override // org.dom4j.rule.Pattern, org.dom4j.NodeFilter
    public boolean matches(Node node) {
        try {
            this.context.setNodeSet(Collections.singletonList(node));
            return this.pattern.matches(node, this.context);
        } catch (JaxenException e) {
            handleJaxenException(e);
            return false;
        }
    }

    public String getText() {
        return this.text;
    }

    @Override // org.dom4j.rule.Pattern
    public double getPriority() {
        return this.pattern.getPriority();
    }

    @Override // org.dom4j.rule.Pattern
    public Pattern[] getUnionPatterns() {
        org.jaxen.pattern.Pattern[] unionPatterns = this.pattern.getUnionPatterns();
        if (unionPatterns != null) {
            int length = unionPatterns.length;
            XPathPattern[] xPathPatternArr = new XPathPattern[length];
            for (int i = 0; i < length; i++) {
                xPathPatternArr[i] = new XPathPattern(unionPatterns[i]);
            }
            return xPathPatternArr;
        }
        return null;
    }

    @Override // org.dom4j.rule.Pattern
    public short getMatchType() {
        return this.pattern.getMatchType();
    }

    @Override // org.dom4j.rule.Pattern
    public String getMatchesNodeName() {
        return this.pattern.getMatchesNodeName();
    }

    public void setVariableContext(VariableContext variableContext) {
        this.context.getContextSupport().setVariableContext(variableContext);
    }

    public String toString() {
        return "[XPathPattern: text: " + this.text + " Pattern: " + this.pattern + "]";
    }

    protected ContextSupport getContextSupport() {
        return new ContextSupport(new SimpleNamespaceContext(), XPathFunctionContext.getInstance(), new SimpleVariableContext(), DocumentNavigator.getInstance());
    }

    protected void handleJaxenException(JaxenException jaxenException) throws XPathException {
        throw new XPathException(this.text, (Exception) jaxenException);
    }
}
