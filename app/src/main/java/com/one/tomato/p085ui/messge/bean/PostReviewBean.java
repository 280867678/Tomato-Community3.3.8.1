package com.one.tomato.p085ui.messge.bean;

import java.io.Serializable;
import java.util.List;

/* renamed from: com.one.tomato.ui.messge.bean.PostReviewBean */
/* loaded from: classes3.dex */
public class PostReviewBean implements Serializable {
    private int code;
    private List<DataBean> data;
    private String message;
    private boolean success;

    public int getCode() {
        return this.code;
    }

    public void setCode(int i) {
        this.code = i;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean z) {
        this.success = z;
    }

    public List<DataBean> getData() {
        return this.data;
    }

    public void setData(List<DataBean> list) {
        this.data = list;
    }

    /* renamed from: com.one.tomato.ui.messge.bean.PostReviewBean$DataBean */
    /* loaded from: classes3.dex */
    public static class DataBean implements Serializable {
        private String articleCover;
        private String articleDes;
        private int articleId;
        private int articleType;
        private String createTime;
        private String ext1;
        private String ext2;
        private String ext3;
        private String feedbackId;
        private String feedbackMsg;
        private String feedbackMsgReply;
        private String fromMemberAvatar;
        private String fromMemberId;
        private String fromMemberName;

        /* renamed from: id */
        private int f1753id;
        private String msgContent;
        private int msgGroup;
        private String msgReason;
        private String msgTitle;
        private int msgType;
        private String myComment;
        private String myCommentId;
        private String myReply;
        private String myReplyId;
        private String myReplyToMember;
        private String myReplyToMemberId;
        private String otherSideComment;
        private String otherSideReply;
        private String otherSideReplyId;
        private String replaceMsgContent;
        private String replaceMsgTitle;
        private String rootCommentId;
        private int status;
        private int toMemberId;

        public String getExt1() {
            return this.ext1;
        }

        public void setExt1(String str) {
            this.ext1 = str;
        }

        public String getExt2() {
            return this.ext2;
        }

        public void setExt2(String str) {
            this.ext2 = str;
        }

        public String getExt3() {
            return this.ext3;
        }

        public void setExt3(String str) {
            this.ext3 = str;
        }

        public String getMyComment() {
            return this.myComment;
        }

        public void setMyComment(String str) {
            this.myComment = str;
        }

        public String getMyCommentId() {
            return this.myCommentId;
        }

        public void setMyCommentId(String str) {
            this.myCommentId = str;
        }

        public String getOtherSideComment() {
            return this.otherSideComment;
        }

        public void setOtherSideComment(String str) {
            this.otherSideComment = str;
        }

        public String getFromMemberAvatar() {
            return this.fromMemberAvatar;
        }

        public void setFromMemberAvatar(String str) {
            this.fromMemberAvatar = str;
        }

        public String getFromMemberId() {
            return this.fromMemberId;
        }

        public void setFromMemberId(String str) {
            this.fromMemberId = str;
        }

        public String getFromMemberName() {
            return this.fromMemberName;
        }

        public void setFromMemberName(String str) {
            this.fromMemberName = str;
        }

        public String getMyReply() {
            return this.myReply;
        }

        public void setMyReply(String str) {
            this.myReply = str;
        }

        public String getMyReplyId() {
            return this.myReplyId;
        }

        public void setMyReplyId(String str) {
            this.myReplyId = str;
        }

        public String getMyReplyToMember() {
            return this.myReplyToMember;
        }

        public void setMyReplyToMember(String str) {
            this.myReplyToMember = str;
        }

        public String getMyReplyToMemberId() {
            return this.myReplyToMemberId;
        }

        public void setMyReplyToMemberId(String str) {
            this.myReplyToMemberId = str;
        }

        public String getOtherSideReply() {
            return this.otherSideReply;
        }

        public void setOtherSideReply(String str) {
            this.otherSideReply = str;
        }

        public String getOtherSideReplyId() {
            return this.otherSideReplyId;
        }

        public void setOtherSideReplyId(String str) {
            this.otherSideReplyId = str;
        }

        public String getRootCommentId() {
            return this.rootCommentId;
        }

        public void setRootCommentId(String str) {
            this.rootCommentId = str;
        }

        public String getFeedbackId() {
            return this.feedbackId;
        }

        public void setFeedbackId(String str) {
            this.feedbackId = str;
        }

        public String getFeedbackMsg() {
            return this.feedbackMsg;
        }

        public void setFeedbackMsg(String str) {
            this.feedbackMsg = str;
        }

        public String getFeedbackMsgReply() {
            return this.feedbackMsgReply;
        }

        public void setFeedbackMsgReply(String str) {
            this.feedbackMsgReply = str;
        }

        public String getReplaceMsgTitle() {
            return this.replaceMsgTitle;
        }

        public void setReplaceMsgTitle(String str) {
            this.replaceMsgTitle = str;
        }

        public int getArticleType() {
            return this.articleType;
        }

        public void setArticleType(int i) {
            this.articleType = i;
        }

        public String getReplaceMsgContent() {
            return this.replaceMsgContent;
        }

        public void setReplaceMsgContent(String str) {
            this.replaceMsgContent = str;
        }

        public String getMsgContent() {
            return this.msgContent;
        }

        public void setMsgContent(String str) {
            this.msgContent = str;
        }

        public String getArticleCover() {
            return this.articleCover;
        }

        public void setArticleCover(String str) {
            this.articleCover = str;
        }

        public String getArticleDes() {
            return this.articleDes;
        }

        public void setArticleDes(String str) {
            this.articleDes = str;
        }

        public int getArticleId() {
            return this.articleId;
        }

        public void setArticleId(int i) {
            this.articleId = i;
        }

        public String getCreateTime() {
            return this.createTime;
        }

        public void setCreateTime(String str) {
            this.createTime = str;
        }

        public int getId() {
            return this.f1753id;
        }

        public void setId(int i) {
            this.f1753id = i;
        }

        public int getMsgGroup() {
            return this.msgGroup;
        }

        public void setMsgGroup(int i) {
            this.msgGroup = i;
        }

        public String getMsgReason() {
            return this.msgReason;
        }

        public void setMsgReason(String str) {
            this.msgReason = str;
        }

        public String getMsgTitle() {
            return this.msgTitle;
        }

        public void setMsgTitle(String str) {
            this.msgTitle = str;
        }

        public int getMsgType() {
            return this.msgType;
        }

        public void setMsgType(int i) {
            this.msgType = i;
        }

        public int getStatus() {
            return this.status;
        }

        public void setStatus(int i) {
            this.status = i;
        }

        public int getToMemberId() {
            return this.toMemberId;
        }

        public void setToMemberId(int i) {
            this.toMemberId = i;
        }
    }
}
