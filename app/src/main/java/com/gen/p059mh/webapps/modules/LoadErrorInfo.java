package com.gen.p059mh.webapps.modules;

import com.gen.p059mh.webapps.listener.IErrorInfo;
import com.tomatolive.library.BuildConfig;
import com.tomatolive.library.p136ui.view.dialog.LotteryDialog;

/* renamed from: com.gen.mh.webapps.modules.LoadErrorInfo */
/* loaded from: classes2.dex */
public enum LoadErrorInfo implements IErrorInfo {
    UNKNOWN_TYPE(LotteryDialog.MAX_VALUE, "不能识别小程序"),
    CHECK_FAIL(401, "验证失败"),
    LOAD_FAIL(402, "加载失败"),
    UNZIP_FAIL(300, "解压失败"),
    FILE_INFO_FAIL(301, "不能获取文件信息"),
    CREATE_DIR_FAIL(302, "增量更新,创建目录失败"),
    WRITE_FILE_FAIL(303, "增量更新,写入文件失败"),
    DOWNLOAD_FILE_FAIL(304, "增量更新,下载文件失败"),
    REQUEST_LIST_FAIL(305, "增量更新,请求列表失败"),
    NO_REQUEST_URL(306, "完整更新,无请求地址"),
    REQUEST_FINISH(307, "完整更新,请求已经结束"),
    GET_RANGE_FAIL(308, "完整更新,获得文件容量失败"),
    SAVE_INFO_FAIL(309, "完整更新,保存文件信息失败"),
    FILE_EMPTY(310, "完整更新,文件为空"),
    DOWNLOAD_ZIP_FAIL(BuildConfig.VERSION_CODE, "完整更新,下载失败"),
    NO_NETWORK(200, "无网络"),
    NO_API_HOST(201, "没有API HOST"),
    NO_AUTHORIZE_ID(202, "没有授权ID"),
    OTHER_ERROR(203, "其他异常");
    
    private String appId;
    private int code;
    private String message;

    @Override // java.lang.Enum, com.gen.p059mh.webapps.listener.IErrorInfo
    public String toString() {
        return this.appId + " | " + this.code + " | " + this.message;
    }

    LoadErrorInfo(int i, String str) {
        this.code = i;
        this.message = str;
    }

    @Override // com.gen.p059mh.webapps.listener.IErrorInfo
    public int getCode() {
        return this.code;
    }

    @Override // com.gen.p059mh.webapps.listener.IErrorInfo
    public String getMessage() {
        return this.message;
    }

    @Override // com.gen.p059mh.webapps.listener.IErrorInfo
    public String getAppId() {
        return this.appId;
    }

    @Override // com.gen.p059mh.webapps.listener.IErrorInfo
    public void setAppInfo(String str) {
        this.appId = str;
    }
}
