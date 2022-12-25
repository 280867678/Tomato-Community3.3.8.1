package org.dom4j.rule;

import java.util.List;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.XPath;

/* loaded from: classes4.dex */
public class Stylesheet {
    private String modeName;
    private RuleManager ruleManager = new RuleManager();

    public void addRule(Rule rule) {
        this.ruleManager.addRule(rule);
    }

    public void removeRule(Rule rule) {
        this.ruleManager.removeRule(rule);
    }

    public void run(List<Node> list) throws Exception {
        run(list, this.modeName);
    }

    public void run(List<Node> list, String str) throws Exception {
        for (Node node : list) {
            run(node, str);
        }
    }

    public void run(Node node) throws Exception {
        run(node, this.modeName);
    }

    public void run(Node node, String str) throws Exception {
        this.ruleManager.getMode(str).fireRule(node);
    }

    public void applyTemplates(Object obj, XPath xPath) throws Exception {
        applyTemplates(obj, xPath, this.modeName);
    }

    public void applyTemplates(Object obj, XPath xPath, String str) throws Exception {
        Mode mode = this.ruleManager.getMode(str);
        for (Node node : xPath.selectNodes(obj)) {
            mode.fireRule(node);
        }
    }

    public void applyTemplates(Node node) throws Exception {
        applyTemplates(node, this.modeName);
    }

    public void applyTemplates(Element element) throws Exception {
        applyTemplates(element, this.modeName);
    }

    public void applyTemplates(Document document) throws Exception {
        applyTemplates(document, this.modeName);
    }

    public void applyTemplates(List<Node> list) throws Exception {
        applyTemplates(list, this.modeName);
    }

    public void applyTemplates(Node node, String str) throws Exception {
        if (node instanceof Element) {
            applyTemplates((Element) node, str);
        } else if (!(node instanceof Document)) {
        } else {
            applyTemplates((Document) node, str);
        }
    }

    public void applyTemplates(Element element, String str) throws Exception {
        Mode mode = this.ruleManager.getMode(str);
        int nodeCount = element.nodeCount();
        for (int i = 0; i < nodeCount; i++) {
            mode.fireRule(element.node(i));
        }
    }

    public void applyTemplates(Document document, String str) throws Exception {
        Mode mode = this.ruleManager.getMode(str);
        int nodeCount = document.nodeCount();
        for (int i = 0; i < nodeCount; i++) {
            mode.fireRule(document.node(i));
        }
    }

    public void applyTemplates(List<? extends Node> list, String str) throws Exception {
        for (Node node : list) {
            if (node instanceof Element) {
                applyTemplates((Element) node, str);
            } else if (node instanceof Document) {
                applyTemplates((Document) node, str);
            }
        }
    }

    public void clear() {
        this.ruleManager.clear();
    }

    public String getModeName() {
        return this.modeName;
    }

    public void setModeName(String str) {
        this.modeName = str;
    }

    public Action getValueOfAction() {
        return this.ruleManager.getValueOfAction();
    }

    public void setValueOfAction(Action action) {
        this.ruleManager.setValueOfAction(action);
    }
}
