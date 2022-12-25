package org.dom4j.rule;

import java.io.PrintStream;
import java.util.HashMap;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.rule.pattern.NodeTypePattern;

/* loaded from: classes4.dex */
public class RuleManager {
    private int appearenceCount;
    private HashMap<String, Mode> modes = new HashMap<>();
    private Action valueOfAction;

    public Mode getMode(String str) {
        Mode mode = this.modes.get(str);
        if (mode == null) {
            Mode createMode = createMode();
            this.modes.put(str, createMode);
            return createMode;
        }
        return mode;
    }

    public void addRule(Rule rule) {
        int i = this.appearenceCount + 1;
        this.appearenceCount = i;
        rule.setAppearenceCount(i);
        Mode mode = getMode(rule.getMode());
        Rule[] unionRules = rule.getUnionRules();
        if (unionRules != null) {
            for (Rule rule2 : unionRules) {
                mode.addRule(rule2);
            }
            return;
        }
        mode.addRule(rule);
    }

    public void removeRule(Rule rule) {
        Mode mode = getMode(rule.getMode());
        Rule[] unionRules = rule.getUnionRules();
        if (unionRules != null) {
            for (Rule rule2 : unionRules) {
                mode.removeRule(rule2);
            }
            return;
        }
        mode.removeRule(rule);
    }

    public Rule getMatchingRule(String str, Node node) {
        Mode mode = this.modes.get(str);
        if (mode != null) {
            return mode.getMatchingRule(node);
        }
        PrintStream printStream = System.out;
        printStream.println("Warning: No Mode for mode: " + mode);
        return null;
    }

    public void clear() {
        this.modes.clear();
        this.appearenceCount = 0;
    }

    public Action getValueOfAction() {
        return this.valueOfAction;
    }

    public void setValueOfAction(Action action) {
        this.valueOfAction = action;
    }

    protected Mode createMode() {
        Mode mode = new Mode();
        addDefaultRules(mode);
        return mode;
    }

    protected void addDefaultRules(final Mode mode) {
        Action action = new Action() { // from class: org.dom4j.rule.RuleManager.1
            @Override // org.dom4j.rule.Action
            public void run(Node node) throws Exception {
                if (node instanceof Element) {
                    mode.applyTemplates((Element) node);
                } else if (!(node instanceof Document)) {
                } else {
                    mode.applyTemplates((Document) node);
                }
            }
        };
        Action valueOfAction = getValueOfAction();
        addDefaultRule(mode, NodeTypePattern.ANY_DOCUMENT, action);
        addDefaultRule(mode, NodeTypePattern.ANY_ELEMENT, action);
        if (valueOfAction != null) {
            addDefaultRule(mode, NodeTypePattern.ANY_ATTRIBUTE, valueOfAction);
            addDefaultRule(mode, NodeTypePattern.ANY_TEXT, valueOfAction);
        }
    }

    protected void addDefaultRule(Mode mode, Pattern pattern, Action action) {
        mode.addRule(createDefaultRule(pattern, action));
    }

    protected Rule createDefaultRule(Pattern pattern, Action action) {
        Rule rule = new Rule(pattern, action);
        rule.setImportPrecedence(-1);
        return rule;
    }
}
