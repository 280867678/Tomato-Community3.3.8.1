package com.zzz.ipfssdk.callback.exception;

import com.zzz.ipfssdk.LogUtil;

/* loaded from: classes4.dex */
public class CodeState extends Exception {
    public int code;
    public String desc;

    /* loaded from: classes4.dex */
    public static final class CODES {
        public static final int CODE_CDN_PROBE = 6000;
        public static final int CODE_IP_NOT_INIT = 2001;
        public static final int CODE_P2P_INIT = 500;
        public static final int CODE_PACKAGE_ERROR = 8000;
        public static final int CODE_PLAYER_PLAY_ERROR = 4000;
        public static final int CODE_QUERY_DATA_BROKEN = 3014;
        public static final int CODE_QUERY_IP_ADDR_INFO = 2000;
        public static final int CODE_QUERY_RELAY_SRV = 1000;
        public static final int CODE_QUERY_RESOURCE_BACK2SRC_NOT_COMPLETED = 3005;
        public static final int CODE_QUERY_RESOURCE_HTTP_REQUEST_ERR = 3009;
        public static final int CODE_QUERY_RESOURCE_ILLEGAL_MALFORMED_URL_ERR = 3013;
        public static final int CODE_QUERY_RESOURCE_MSG_ERR = 3000;
        public static final int CODE_QUERY_RESOURCE_NODE_NOT_EXIST = 3004;
        public static final int CODE_QUERY_RESOURCE_NORMAL = 3008;
        public static final int CODE_QUERY_RESOURCE_NO_HANDLER = 3010;
        public static final int CODE_QUERY_RESOURCE_NO_THIS_RESOURCE = 3002;
        public static final int CODE_QUERY_RESOURCE_P2P_DISABLE = 3006;
        public static final int CODE_QUERY_RESOURCE_PARAM_ERROR = 3007;
        public static final int CODE_QUERY_RESOURCE_ROOTNODE_NOT_EXIST = 3003;
        public static final int CODE_QUERY_RESOURCE_TIME_OUT = 3011;
        public static final int CODE_QUERY_RESOURCE_TOKEN_ERROR = 3001;
        public static final int CODE_QUERY_RESOURCE_UNEXPECTED_CODE_ERR = 3012;
        public static final int CODE_READ_BUF = 5000;
        public static final int CODE_URL_NOT_EXSITS = 7000;
    }

    /* loaded from: classes4.dex */
    public static final class MSGS {
        public static final String MSG_BACK2SRC_NOT_COMPLETED = "back2src not completed";
        public static final String MSG_CDN_PROBE = "p2p网络不好,cdn介入";
        public static final String MSG_HLS_DECRET_ERROR = "HLS解密key获取失败";
        public static final String MSG_HTTP_REQUEST_ERROR = "http request error";
        public static final String MSG_HTTP_RESPONSE_500 = "http response 500";
        public static final String MSG_HTTP_RESPONSE_EMPTY = "http response empty";
        public static final String MSG_JSON_PARSE_ERROR = "json parse error";
        public static final String MSG_NATIVE_RESPONSE_TIMEOUT = "native response timeout";
        public static final String MSG_NODE_NOT_EXIST = "node not exist";
        public static final String MSG_NO_THIS_RESOURCE = "no this resource";
        public static final String MSG_P2P_DISABLE = "p2p disable";
        public static final String MSG_PACKAGE_ERROR = "the data package is not continuous,some play error may happen";
        public static final String MSG_QUERY_DATA_BROKEN = "data broken";
        public static final String MSG_QUERY_RESOURCE_HTTP_REQUEST_ERR = "query resource http request err";
        public static final String MSG_QUERY_RESOURCE_ILLEGAL_MALFORMED_URL_ERR = "query resource 非法 url";
        public static final String MSG_QUERY_RESOURCE_NORMAL = "query resource normal";
        public static final String MSG_QUERY_RESOURCE_NO_HANDLER_ERR = "query resource find no handler";
        public static final String MSG_QUERY_RESOURCE_PARAM_ERR = "query resource param error";
        public static final String MSG_QUERY_RESOURCE_TIME_OUT_ERR = "query resource time out";
        public static final String MSG_QUERY_RESOURCE_UNEXPECTED_CODE_ERR = "query resource unexpected code";
        public static final String MSG_READ_BUF_EMPTY = "read buf len = 0";
        public static final String MSG_ROOTNODE_NOT_EXIST = "rootnode not exist";
        public static final String MSG_SDK_IP_NOT_INIT = "ip not init";
        public static final String MSG_SDK_P2P_NOT_INIT = "sdk not init";
        public static final String MSG_TOKEN_ERROR = "wrong token";
        public static final String MSG_URL_NOT_EXSITS = "页面不存在404";
    }

    public CodeState(int i, String str, Throwable th) {
        super(th);
        this.code = i;
        this.desc = str;
    }

    public boolean equal(CodeState codeState) {
        return getDetailInfo() != null ? getCode() == codeState.getCode() && getDesc().equals(codeState.getDesc()) && getDetailInfo().equals(codeState.getDetailInfo()) : getCode() == codeState.getCode() && getDesc().equals(codeState.getDesc()) && codeState == null;
    }

    public int getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }

    public String getDetailInfo() {
        return getMessage();
    }

    @Override // java.lang.Throwable
    public void printStackTrace() {
        LogUtil.m120e(LogUtil.TAG_IPFS_SDK_CZ, toString());
        super.printStackTrace();
    }

    public void setCode(int i) {
        this.code = i;
    }

    public void setDesc(String str) {
        this.desc = str;
    }

    @Override // java.lang.Throwable
    public String toString() {
        return "CodeException{code=" + this.code + ", desc='" + this.desc + "'}";
    }
}
