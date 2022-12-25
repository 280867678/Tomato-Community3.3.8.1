package org.dom4j.rule;

import org.dom4j.Node;

/* loaded from: classes4.dex */
public class Rule implements Comparable<Rule> {
    private Action action;
    private int appearenceCount;
    private int importPrecedence;
    private String mode;
    private Pattern pattern;
    private double priority;

    private static int compareInt(int i, int i2) {
        if (i < i2) {
            return -1;
        }
        return i == i2 ? 0 : 1;
    }

    public Rule() {
        this.priority = 0.5d;
    }

    public Rule(Pattern pattern) {
        this.pattern = pattern;
        this.priority = pattern.getPriority();
    }

    public Rule(Pattern pattern, Action action) {
        this(pattern);
        this.action = action;
    }

    public Rule(Rule rule, Pattern pattern) {
        this.mode = rule.mode;
        this.importPrecedence = rule.importPrecedence;
        this.priority = rule.priority;
        this.appearenceCount = rule.appearenceCount;
        this.action = rule.action;
        this.pattern = pattern;
    }

    public boolean equals(Object obj) {
        return (obj instanceof Rule) && compareTo((Rule) obj) == 0;
    }

    public int hashCode() {
        return this.importPrecedence + this.appearenceCount;
    }

    @Override // java.lang.Comparable
    public int compareTo(Rule rule) {
        int compareInt = compareInt(this.importPrecedence, rule.importPrecedence);
        if (compareInt == 0) {
            int compare = Double.compare(this.priority, rule.priority);
            return compare == 0 ? compareInt(this.appearenceCount, rule.appearenceCount) : compare;
        }
        return compareInt;
    }

    public String toString() {
        return super.toString() + "[ pattern: " + getPattern() + " action: " + getAction() + " ]";
    }

    public final boolean matches(Node node) {
        return this.pattern.matches(node);
    }

    public Rule[] getUnionRules() {
        Pattern[] unionPatterns = this.pattern.getUnionPatterns();
        if (unionPatterns == null) {
            return null;
        }
        int length = unionPatterns.length;
        Rule[] ruleArr = new Rule[length];
        for (int i = 0; i < length; i++) {
            ruleArr[i] = new Rule(this, unionPatterns[i]);
        }
        return ruleArr;
    }

    public final short getMatchType() {
        return this.pattern.getMatchType();
    }

    public final String getMatchesNodeName() {
        return this.pattern.getMatchesNodeName();
    }

    public String getMode() {
        return this.mode;
    }

    public void setMode(String str) {
        this.mode = str;
    }

    public int getImportPrecedence() {
        return this.importPrecedence;
    }

    public void setImportPrecedence(int i) {
        this.importPrecedence = i;
    }

    public double getPriority() {
        return this.priority;
    }

    public void setPriority(double d) {
        this.priority = d;
    }

    public int getAppearenceCount() {
        return this.appearenceCount;
    }

    public void setAppearenceCount(int i) {
        this.appearenceCount = i;
    }

    public Pattern getPattern() {
        return this.pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    public Action getAction() {
        return this.action;
    }

    public void setAction(Action action) {
        this.action = action;
    }
}
