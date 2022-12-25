package com.one.tomato.thirdpart.domain;

import com.one.tomato.mvp.base.BaseApplication;

/* loaded from: classes3.dex */
public class DomainServer {
    private static DomainServer domainServer;

    public static DomainServer getInstance() {
        if (domainServer == null) {
            domainServer = new DomainServer();
        }
        return domainServer;
    }

    public synchronized String getCurrentServerUrl(String str) {
        String domainUrlByType = DomainRequest.getInstance().getDomainUrlByType(str);
        if (!domainUrlByType.endsWith("/")) {
            domainUrlByType = domainUrlByType + "/";
        }
        if ("ttViewPicture".equals(str) || "ttViewVideoNew".equals(str) || "jcAgent".equals(str) || "h5Domain".equals(str) || "spreadDomain".equals(str) || "website".equals(str) || "websiteYule".equals(str) || "shareDomain".equals(str)) {
            return subStringUrlByLine(domainUrlByType);
        }
        return domainUrlByType;
    }

    public synchronized String subStringUrlByLine(String str) {
        if (str.endsWith("/")) {
            return subStringUrlByLine(str.substring(0, str.length() - 1));
        }
        return str;
    }

    public synchronized String getServerUrl() {
        String domainBaseServerUrl;
        domainBaseServerUrl = DomainRequest.getInstance().getDomainBaseServerUrl();
        if (!domainBaseServerUrl.endsWith("/")) {
            domainBaseServerUrl = domainBaseServerUrl + "/";
        }
        return domainBaseServerUrl;
    }

    public synchronized String getShareUrl() {
        return getCurrentServerUrl("shareDomain");
    }

    public synchronized String getLiveServerUrl() {
        return getCurrentServerUrl("liveServer");
    }

    public synchronized String getLivePictureUrl() {
        return getCurrentServerUrl("livePicture");
    }

    public synchronized String getLiveUploadUrl() {
        return getCurrentServerUrl("liveUpload");
    }

    public synchronized String getReportServerUrl() {
        return getCurrentServerUrl("reportServer");
    }

    public synchronized String getWebsiteUrl() {
        return getCurrentServerUrl(BaseApplication.instance.isChess() ? "websiteYule" : "website");
    }

    public synchronized String getTtUploadUrl() {
        return getCurrentServerUrl("ttUpload");
    }

    public synchronized String getTtViewPicture() {
        return getCurrentServerUrl("ttViewPicture");
    }

    public synchronized String getTtViewVideoView2() {
        return getCurrentServerUrl("ttViewVideoNew");
    }

    public synchronized String getJCUrl() {
        return getCurrentServerUrl("jcAgent");
    }

    public synchronized String getSpreadUrl() {
        return getCurrentServerUrl("spreadDomain");
    }

    public synchronized String getH5Url() {
        return getCurrentServerUrl("h5Domain");
    }
}
