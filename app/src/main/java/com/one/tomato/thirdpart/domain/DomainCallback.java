package com.one.tomato.thirdpart.domain;

import android.text.TextUtils;
import com.one.tomato.thirdpart.tomatolive.TomatoLiveSDKUtils;
import com.one.tomato.utils.FileUtil;
import com.one.tomato.utils.LogUtil;
import com.one.tomato.utils.PreferencesUtil;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import java.io.File;

/* loaded from: classes3.dex */
public class DomainCallback implements IDomainRequest {
    private static DomainCallback domainCallback;
    private String curDomainVersion;
    private int requestDomainStatus = 0;
    private boolean updateDomain = false;

    private DomainCallback() {
    }

    public static DomainCallback getInstance() {
        if (domainCallback == null) {
            synchronized (DomainCallback.class) {
                if (domainCallback == null) {
                    domainCallback = new DomainCallback();
                }
            }
        }
        return domainCallback;
    }

    public synchronized void setDomainVersion(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.curDomainVersion = str;
            File domainDir = FileUtil.getDomainDir(DomainRequest.getInstance().getSDCardDomainDir());
            if (!domainDir.exists() || domainDir.listFiles() == null || domainDir.listFiles().length == 0) {
                LogUtil.m3784i("本地没有域名文件");
                this.updateDomain = true;
            }
            String string = PreferencesUtil.getInstance().getString("domainVersionCode");
            LogUtil.m3784i("本地domainVersion : " + string);
            if (!this.curDomainVersion.equals(string)) {
                LogUtil.m3784i("本地域名版本号小于服务端版本号");
                this.updateDomain = true;
            }
            if (this.updateDomain && this.requestDomainStatus != 1) {
                this.requestDomainStatus = 1;
                DomainRequest.getInstance().setRequestListener(this);
                DomainRequest.getInstance().requestDomainList("server", false);
            }
        }
    }

    @Override // com.one.tomato.thirdpart.domain.IDomainRequest
    public void domainRequest(int i) {
        this.requestDomainStatus = i;
        this.updateDomain = false;
        if (2 == this.requestDomainStatus) {
            PreferencesUtil.getInstance().putString("domainVersionCode", this.curDomainVersion);
            Observable.just("").observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>(this) { // from class: com.one.tomato.thirdpart.domain.DomainCallback.1
                @Override // io.reactivex.functions.Consumer
                public void accept(String str) throws Exception {
                    TomatoLiveSDKUtils.getSingleton().updateServerUrl();
                }
            });
        }
    }
}
