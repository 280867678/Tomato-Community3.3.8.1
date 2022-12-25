package com.one.tomato.entity;

import java.util.List;

/* loaded from: classes3.dex */
public class CreditRule {
    private List<ListCfgBean> listCfg;
    private List<ListRuleBean> listRule;

    public List<ListCfgBean> getListCfg() {
        return this.listCfg;
    }

    public void setListCfg(List<ListCfgBean> list) {
        this.listCfg = list;
    }

    public List<ListRuleBean> getListRule() {
        return this.listRule;
    }

    public void setListRule(List<ListRuleBean> list) {
        this.listRule = list;
    }

    /* loaded from: classes3.dex */
    public static class ListCfgBean {
        private String commentCount;
        private String noAuditCount;
        private String pubCount;
        private String range;
        private String replyCount;

        public String getCommentCount() {
            return this.commentCount;
        }

        public void setCommentCount(String str) {
            this.commentCount = str;
        }

        public String getNoAuditCount() {
            return this.noAuditCount;
        }

        public void setNoAuditCount(String str) {
            this.noAuditCount = str;
        }

        public String getPubCount() {
            return this.pubCount;
        }

        public void setPubCount(String str) {
            this.pubCount = str;
        }

        public String getRange() {
            return this.range;
        }

        public void setRange(String str) {
            this.range = str;
        }

        public String getReplyCount() {
            return this.replyCount;
        }

        public void setReplyCount(String str) {
            this.replyCount = str;
        }
    }

    /* loaded from: classes3.dex */
    public static class ListRuleBean {
        private String maxValue;
        private String name;
        private String singleValue;

        public String getMaxValue() {
            return this.maxValue;
        }

        public void setMaxValue(String str) {
            this.maxValue = str;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String str) {
            this.name = str;
        }

        public String getSingleValue() {
            return this.singleValue;
        }

        public void setSingleValue(String str) {
            this.singleValue = str;
        }
    }
}
