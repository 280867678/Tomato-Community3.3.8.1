package org.dom4j.rule;

import java.util.ArrayList;
import java.util.Collections;
import org.dom4j.Node;

/* loaded from: classes4.dex */
public class RuleSet {
    private Rule[] ruleArray;
    private ArrayList<Rule> rules = new ArrayList<>();

    public String toString() {
        return super.toString() + " [RuleSet: " + this.rules + " ]";
    }

    public Rule getMatchingRule(Node node) {
        Rule[] ruleArray = getRuleArray();
        for (int length = ruleArray.length - 1; length >= 0; length--) {
            Rule rule = ruleArray[length];
            if (rule.matches(node)) {
                return rule;
            }
        }
        return null;
    }

    public void addRule(Rule rule) {
        this.rules.add(rule);
        this.ruleArray = null;
    }

    public void removeRule(Rule rule) {
        this.rules.remove(rule);
        this.ruleArray = null;
    }

    public void addAll(RuleSet ruleSet) {
        this.rules.addAll(ruleSet.rules);
        this.ruleArray = null;
    }

    protected Rule[] getRuleArray() {
        if (this.ruleArray == null) {
            Collections.sort(this.rules);
            this.ruleArray = new Rule[this.rules.size()];
            this.rules.toArray(this.ruleArray);
        }
        return this.ruleArray;
    }
}
