package com.one.tomato.entity;

import android.text.TextUtils;
import com.one.tomato.utils.FormatUtil;
import com.tomatolive.library.utils.DateUtils;
import java.util.Date;
import java.util.List;

/* loaded from: classes3.dex */
public class AgentOrderList {
    private List<DataBean> data;
    private String message;
    private String status;

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String str) {
        this.status = str;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public List<DataBean> getData() {
        return this.data;
    }

    public void setData(List<DataBean> list) {
        this.data = list;
    }

    /* loaded from: classes3.dex */
    public static class DataBean {
        private String EvaluationState;
        private String ReadStatus;
        private String agent_nickname;
        private String chat_url;
        private String comment;
        private String create_time;
        private String money;
        private String orderid;
        private String status;
        private String upload_url;
        private String userid;

        public String getOrderid() {
            return this.orderid;
        }

        public void setOrderid(String str) {
            this.orderid = str;
        }

        public String getMoney() {
            return this.money;
        }

        public void setMoney(String str) {
            this.money = str;
        }

        public String getStatus() {
            return this.status;
        }

        public void setStatus(String str) {
            this.status = str;
        }

        public String getCreate_time() {
            return this.create_time;
        }

        public String getParseCreate_time() {
            long currentTimeMillis;
            if (!TextUtils.isEmpty(this.create_time)) {
                if (this.create_time.contains(".")) {
                    this.create_time = this.create_time.substring(0, this.create_time.indexOf("."));
                }
                currentTimeMillis = Long.parseLong(this.create_time) * 1000;
            } else {
                currentTimeMillis = System.currentTimeMillis();
            }
            return FormatUtil.formatTime(DateUtils.C_TIME_PATTON_DEFAULT, new Date(currentTimeMillis));
        }

        public void setCreate_time(String str) {
            this.create_time = str;
        }

        public String getUserid() {
            return this.userid;
        }

        public void setUserid(String str) {
            this.userid = str;
        }

        public String getAgent_nickname() {
            return this.agent_nickname;
        }

        public void setAgent_nickname(String str) {
            this.agent_nickname = str;
        }

        public String getChat_url() {
            return this.chat_url;
        }

        public void setChat_url(String str) {
            this.chat_url = str;
        }

        public String getUpload_url() {
            return this.upload_url;
        }

        public void setUpload_url(String str) {
            this.upload_url = str;
        }

        public String getEvaluationState() {
            return this.EvaluationState;
        }

        public void setEvaluationState(String str) {
            this.EvaluationState = str;
        }

        public String getReadStatus() {
            return this.ReadStatus;
        }

        public void setReadStatus(String str) {
            this.ReadStatus = str;
        }

        public String getComment() {
            return this.comment;
        }

        public void setComment(String str) {
            this.comment = str;
        }
    }
}
