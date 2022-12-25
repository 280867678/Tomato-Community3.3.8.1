package com.one.tomato.entity;

import android.text.TextUtils;
import com.one.tomato.utils.FormatUtil;
import com.tomatolive.library.utils.DateUtils;
import java.util.ArrayList;
import java.util.Date;

/* loaded from: classes3.dex */
public class AgentChatMessage {
    private String cmd;
    private ArrayList<MessageBean> message;
    private int status;

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int i) {
        this.status = i;
    }

    public String getCmd() {
        return this.cmd;
    }

    public void setCmd(String str) {
        this.cmd = str;
    }

    public ArrayList<MessageBean> getMessage() {
        return this.message;
    }

    public void setMessage(ArrayList<MessageBean> arrayList) {
        this.message = arrayList;
    }

    /* loaded from: classes3.dex */
    public static class MessageBean {
        private String cmd;
        private int index;
        private boolean isLocalPic;
        private MessageDetail msg;
        private String reader;
        private String session_id;
        private int status;
        private String time;
        private int type;
        private String uname;
        private int userid;

        public boolean isLocalPic() {
            return this.isLocalPic;
        }

        public void setLocalPic(boolean z) {
            this.isLocalPic = z;
        }

        public String getReader() {
            return this.reader;
        }

        public void setReader(String str) {
            this.reader = str;
        }

        public String getCmd() {
            return this.cmd;
        }

        public void setCmd(String str) {
            this.cmd = str;
        }

        public int getStatus() {
            return this.status;
        }

        public void setStatus(int i) {
            this.status = i;
        }

        public int getIndex() {
            return this.index;
        }

        public void setIndex(int i) {
            this.index = i;
        }

        public int getUserid() {
            return this.userid;
        }

        public void setUserid(int i) {
            this.userid = i;
        }

        public String getSession_id() {
            return this.session_id;
        }

        public void setSession_id(String str) {
            this.session_id = str;
        }

        public String getUname() {
            return this.uname;
        }

        public void setUname(String str) {
            this.uname = str;
        }

        public String getTime() {
            return this.time;
        }

        public long getTimeMillis() {
            if (!TextUtils.isEmpty(this.time)) {
                if (this.time.contains(".")) {
                    this.time = this.time.substring(0, this.time.indexOf("."));
                }
                return Long.parseLong(this.time) * 1000;
            }
            return System.currentTimeMillis();
        }

        public String getParseTime() {
            long currentTimeMillis;
            if (!TextUtils.isEmpty(this.time)) {
                if (this.time.contains(".")) {
                    this.time = this.time.substring(0, this.time.indexOf("."));
                }
                currentTimeMillis = Long.parseLong(this.time) * 1000;
            } else {
                currentTimeMillis = System.currentTimeMillis();
            }
            return FormatUtil.formatTime(DateUtils.C_TIME_PATTON_DEFAULT, new Date(currentTimeMillis));
        }

        public void setTime(String str) {
            this.time = str;
        }

        public int getType() {
            return this.type;
        }

        public void setType(int i) {
            this.type = i;
        }

        public MessageDetail getMsg() {
            return this.msg;
        }

        public void setMsg(MessageDetail messageDetail) {
            this.msg = messageDetail;
        }

        /* loaded from: classes3.dex */
        public static class MessageDetail {
            private String account;
            private String bankname;
            private Object content;
            private int height;
            private ArrayList<PayMoBanBean> list;
            private String mobilephone;
            private String money;
            private String nickname;
            private String qrcode;
            private String style;
            private String target;
            private String title;
            private String tplname;
            private String uname;
            private int width;

            public ArrayList<PayMoBanBean> getList() {
                return this.list;
            }

            public void setList(ArrayList<PayMoBanBean> arrayList) {
                this.list = arrayList;
            }

            public String getTitle() {
                return this.title;
            }

            public void setTitle(String str) {
                this.title = str;
            }

            public String getStyle() {
                return this.style;
            }

            public void setStyle(String str) {
                this.style = str;
            }

            public String getTplname() {
                return this.tplname;
            }

            public void setTplname(String str) {
                this.tplname = str;
            }

            public Object getContent() {
                return this.content;
            }

            public void setContent(Object obj) {
                this.content = obj;
            }

            public String getMoney() {
                return this.money;
            }

            public void setMoney(String str) {
                this.money = str;
            }

            public String getUname() {
                return this.uname;
            }

            public void setUname(String str) {
                this.uname = str;
            }

            public String getQrcode() {
                return this.qrcode;
            }

            public void setQrcode(String str) {
                this.qrcode = str;
            }

            public String getNickname() {
                return this.nickname;
            }

            public void setNickname(String str) {
                this.nickname = str;
            }

            public String getAccount() {
                return this.account;
            }

            public void setAccount(String str) {
                this.account = str;
            }

            public String getBankname() {
                return this.bankname;
            }

            public void setBankname(String str) {
                this.bankname = str;
            }

            public String getMobilephone() {
                return this.mobilephone;
            }

            public void setMobilephone(String str) {
                this.mobilephone = str;
            }

            public int getWidth() {
                return this.width;
            }

            public void setWidth(int i) {
                this.width = i;
            }

            public int getHeight() {
                return this.height;
            }

            public void setHeight(int i) {
                this.height = i;
            }

            public String getTarget() {
                return this.target;
            }

            public void setTarget(String str) {
                this.target = str;
            }
        }
    }

    /* loaded from: classes3.dex */
    public static class PayMoBanBean {

        /* renamed from: id */
        private int f1691id;
        private String tplname;
        private String type;

        public String getType() {
            return this.type;
        }

        public void setType(String str) {
            this.type = str;
        }

        public int getId() {
            return this.f1691id;
        }

        public void setId(int i) {
            this.f1691id = i;
        }

        public String getTplname() {
            return this.tplname;
        }

        public void setTplname(String str) {
            this.tplname = str;
        }
    }
}
