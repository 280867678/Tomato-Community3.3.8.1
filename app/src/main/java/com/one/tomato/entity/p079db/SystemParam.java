package com.one.tomato.entity.p079db;

import android.text.TextUtils;
import org.litepal.crud.LitePalSupport;

/* renamed from: com.one.tomato.entity.db.SystemParam */
/* loaded from: classes3.dex */
public class SystemParam extends LitePalSupport {
    private String IPFSDomain;
    private String IPFSToken;
    private int IPFS_Switch;
    private String JCParameter;
    private String JavDbParameter;
    private String SpreadParameter;
    private String activityJson;
    private String agent_domain;
    private String agent_merchant;
    private String agent_token;
    private String creator_center_url;
    private String keyApp;
    private int loginSms;
    private int multipleDevice;
    private int pinglunPic;
    private String potatoDownUrl;
    private String potatoUrl;
    private String publishRuleUrl;
    private int reViewFlag;
    private String spreadQrBtnDes;
    private String spreadQrDes;
    private String topListCfg;
    private String upCfgPicUrl;
    private String upHostPtUrl;
    private int ADFrequencyOnList = 10;
    private int paPaFrequencyOnList = 10;
    private int loginCaptcha = 1;

    public String getPotatoUrl() {
        if (TextUtils.isEmpty(this.potatoUrl)) {
            this.potatoUrl = "https://potato.im/fanqie666";
        }
        return this.potatoUrl;
    }

    public String getPotatoDownUrl() {
        if (TextUtils.isEmpty(this.potatoDownUrl)) {
            this.potatoDownUrl = "https://lynnconway.me/apps";
        }
        return this.potatoDownUrl;
    }

    public int getPinglunPic() {
        return this.pinglunPic;
    }

    public int getADFrequencyOnList() {
        return this.ADFrequencyOnList;
    }

    public int getPaPaFrequencyOnList() {
        return this.paPaFrequencyOnList;
    }

    public String getSpreadQrBtnDes() {
        return this.spreadQrBtnDes;
    }

    public String getSpreadQrDes() {
        return this.spreadQrDes;
    }

    public String getAgent_token() {
        if (TextUtils.isEmpty(this.agent_token)) {
            this.agent_token = "0OBBG9ZAS4EROMYIU7VV";
        }
        return this.agent_token;
    }

    public String getAgent_domain() {
        if (TextUtils.isEmpty(this.agent_domain)) {
            this.agent_domain = "http://api.aishangtan.com";
        }
        return this.agent_domain;
    }

    public String getAgent_merchant() {
        if (TextUtils.isEmpty(this.agent_merchant)) {
            this.agent_merchant = "FQ1";
        }
        return this.agent_merchant;
    }

    public String getTopListCfg() {
        return this.topListCfg;
    }

    public void setTopListCfg(String str) {
        this.topListCfg = str;
    }

    public String getJCParameter() {
        return this.JCParameter;
    }

    public String getSpreadParameter() {
        return this.SpreadParameter;
    }

    public String getKeyApp() {
        return this.keyApp;
    }

    public void setKeyApp(String str) {
        this.keyApp = str;
    }

    public int getLoginCaptcha() {
        return this.loginCaptcha;
    }

    public void setLoginCaptcha(int i) {
        this.loginCaptcha = i;
    }

    public int getLoginSms() {
        return this.loginSms;
    }

    public void setLoginSms(int i) {
        this.loginSms = i;
    }

    public int getReViewFlag() {
        return this.reViewFlag;
    }

    public void setReViewFlag(int i) {
        this.reViewFlag = i;
    }

    public int getMultipleDevice() {
        return this.multipleDevice;
    }

    public void setMultipleDevice(int i) {
        this.multipleDevice = i;
    }

    public int getIPFS_Switch() {
        return this.IPFS_Switch;
    }

    public void setIPFS_Switch(int i) {
        this.IPFS_Switch = i;
    }

    public String getIPFSDomain() {
        return this.IPFSDomain;
    }

    public String getIPFSToken() {
        return this.IPFSToken;
    }

    public String getCreator_center_url() {
        return this.creator_center_url;
    }

    public void setCreator_center_url(String str) {
        this.creator_center_url = str;
    }

    public String getJavDbParameter() {
        return this.JavDbParameter;
    }

    public void setJavDbParameter(String str) {
        this.JavDbParameter = str;
    }

    public String getUpHostPtUrl() {
        return this.upHostPtUrl;
    }

    public void setUpHostPtUrl(String str) {
        this.upHostPtUrl = str;
    }

    public String getUpCfgPicUrl() {
        return this.upCfgPicUrl;
    }

    public void setUpCfgPicUrl(String str) {
        this.upCfgPicUrl = str;
    }

    public String getActivityJson() {
        return this.activityJson;
    }

    public String getPublishRuleUrl() {
        return this.publishRuleUrl;
    }

    public void setPublishRuleUrl(String str) {
        this.publishRuleUrl = str;
    }
}
