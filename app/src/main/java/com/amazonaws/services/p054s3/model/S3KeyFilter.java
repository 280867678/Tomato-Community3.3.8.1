package com.amazonaws.services.p054s3.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* renamed from: com.amazonaws.services.s3.model.S3KeyFilter */
/* loaded from: classes2.dex */
public class S3KeyFilter implements Serializable {
    private List<FilterRule> filterRules = new ArrayList();

    /* renamed from: com.amazonaws.services.s3.model.S3KeyFilter$FilterRuleName */
    /* loaded from: classes2.dex */
    public enum FilterRuleName {
        Prefix,
        Suffix;

        public FilterRule newRule() {
            return new FilterRule().withName(toString());
        }

        public FilterRule newRule(String str) {
            return newRule().withValue(str);
        }
    }

    public List<FilterRule> getFilterRules() {
        return Collections.unmodifiableList(this.filterRules);
    }

    public void setFilterRules(List<FilterRule> list) {
        this.filterRules = new ArrayList(list);
    }

    public S3KeyFilter withFilterRules(List<FilterRule> list) {
        setFilterRules(list);
        return this;
    }

    public S3KeyFilter withFilterRules(FilterRule... filterRuleArr) {
        setFilterRules(Arrays.asList(filterRuleArr));
        return this;
    }

    public void addFilterRule(FilterRule filterRule) {
        this.filterRules.add(filterRule);
    }
}
