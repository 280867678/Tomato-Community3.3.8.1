package com.one.tomato.entity;

import java.util.ArrayList;
import org.xutils.p148db.annotation.Column;
import org.xutils.p148db.annotation.Table;

/* loaded from: classes3.dex */
public class InteractiveBean {
    private ArrayList<MessageNoticeListBean> messageNoticeList;

    public ArrayList<MessageNoticeListBean> getMessageNoticeList() {
        return this.messageNoticeList;
    }

    public void setMessageNoticeList(ArrayList<MessageNoticeListBean> arrayList) {
        this.messageNoticeList = arrayList;
    }

    @Table(name = "MessageNoticeListBean")
    /* loaded from: classes3.dex */
    public static class MessageNoticeListBean {
        @Column(name = "articleId")
        private int articleId;
        @Column(name = "commentId")
        private int commentId;
        @Column(name = "createTime")
        private String createTime;
        @Column(name = "createTimeStr")
        private String createTimeStr;
        @Column(autoGen = false, isId = true, name = "ID")

        /* renamed from: id */
        private int f1711id;
        @Column(name = "landlordMemberId")
        private int landlordMemberId;
        @Column(name = "memberAvatar")
        private String memberAvatar;
        @Column(name = "memberId")
        private int memberId;
        @Column(name = "memberName")
        private String memberName;
        @Column(name = "memeberId")
        private int memeberId;
        @Column(name = "myReplyContent")
        private String myReplyContent;
        @Column(name = "replyContent")
        private String replyContent;
        @Column(name = "replyId")
        private int replyId;
        @Column(name = "terminal")
        private int terminal;
        @Column(name = "type")
        private String type;
        @Column(name = "userNmae")
        private String userNmae;

        public String getUserNmae() {
            return this.userNmae;
        }

        public void setUserNmae(String str) {
            this.userNmae = str;
        }

        public String getCreateTimeStr() {
            return this.createTimeStr;
        }

        public void setCreateTimeStr(String str) {
            this.createTimeStr = str;
        }

        public String getCreateTime() {
            return this.createTime;
        }

        public void setCreateTime(String str) {
            this.createTime = str;
        }

        public int getId() {
            return this.f1711id;
        }

        public void setId(int i) {
            this.f1711id = i;
        }

        public int getArticleId() {
            return this.articleId;
        }

        public void setArticleId(int i) {
            this.articleId = i;
        }

        public int getReplyId() {
            return this.replyId;
        }

        public void setReplyId(int i) {
            this.replyId = i;
        }

        public String getReplyContent() {
            return this.replyContent;
        }

        public void setReplyContent(String str) {
            this.replyContent = str;
        }

        public int getMemberId() {
            return this.memberId;
        }

        public void setMemberId(int i) {
            this.memberId = i;
        }

        public String getMemberName() {
            return this.memberName;
        }

        public void setMemberName(String str) {
            this.memberName = str;
        }

        public String getMemberAvatar() {
            return this.memberAvatar;
        }

        public void setMemberAvatar(String str) {
            this.memberAvatar = str;
        }

        public String getType() {
            return this.type;
        }

        public void setType(String str) {
            this.type = str;
        }

        public int getCommentId() {
            return this.commentId;
        }

        public void setCommentId(int i) {
            this.commentId = i;
        }

        public int getTerminal() {
            return this.terminal;
        }

        public void setTerminal(int i) {
            this.terminal = i;
        }

        public String getMyReplyContent() {
            return this.myReplyContent;
        }

        public void setMyReplyContent(String str) {
            this.myReplyContent = str;
        }

        public int getLandlordMemberId() {
            return this.landlordMemberId;
        }

        public void setLandlordMemberId(int i) {
            this.landlordMemberId = i;
        }

        public int getMemeberId() {
            return this.memeberId;
        }

        public void setMemeberId(int i) {
            this.memeberId = i;
        }

        public String toString() {
            return "MessageNoticeListBean{id=" + this.f1711id + ", articleId=" + this.articleId + ", replyId=" + this.replyId + ", replyContent='" + this.replyContent + "', memberId=" + this.memberId + ", memberName='" + this.memberName + "', memberAvatar='" + this.memberAvatar + "', type='" + this.type + "', commentId=" + this.commentId + ", terminal=" + this.terminal + ", myReplyContent='" + this.myReplyContent + "', landlordMemberId=" + this.landlordMemberId + ", memeberId=" + this.memeberId + ", createTimeStr='" + this.createTimeStr + "', createTime='" + this.createTime + "', userNmae='" + this.userNmae + "'}";
        }
    }
}
