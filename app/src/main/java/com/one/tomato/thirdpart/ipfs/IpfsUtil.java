package com.one.tomato.thirdpart.ipfs;

import android.text.TextUtils;
import android.util.Pair;
import com.one.tomato.entity.p079db.SystemParam;
import com.one.tomato.mvp.base.BaseApplication;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.LogUtil;
import com.zzz.ipfssdk.IpfsSDK;
import com.zzz.ipfssdk.callback.OnStateChangeListenner;
import com.zzz.ipfssdk.callback.exception.CodeState;

/* loaded from: classes3.dex */
public class IpfsUtil {
    public static SystemParam systemParam = DBUtil.getSystemParam();

    public static void init() {
        if (systemParam.getIPFS_Switch() == 0) {
            LogUtil.m3786e("IPFS未开启");
            return;
        }
        com.zzz.ipfssdk.LogUtil.setLogLevel(1);
        com.zzz.ipfssdk.LogUtil.setSaveFlag(false);
        String version = IpfsSDK.getInstance().getVersion();
        LogUtil.m3784i("version = " + version);
        String iPFSToken = DBUtil.getSystemParam().getIPFSToken();
        String iPFSDomain = DBUtil.getSystemParam().getIPFSDomain();
        LogUtil.m3784i("token = " + iPFSToken + "\ndomain = " + iPFSDomain);
        IpfsSDK.getInstance().init(BaseApplication.getApplication(), iPFSToken, iPFSDomain);
        IpfsSDK.getInstance().setCdnAutoEnable(true);
        IpfsSDK.getInstance().setOnStateChangeListenner(new OnStateChangeListenner() { // from class: com.one.tomato.thirdpart.ipfs.IpfsUtil.1
            @Override // com.zzz.ipfssdk.callback.OnStateChangeListenner
            public void onIniting() {
                LogUtil.m3784i("初始化 ： onIniting");
            }

            @Override // com.zzz.ipfssdk.callback.OnStateChangeListenner
            public void onInitted() {
                LogUtil.m3784i("初始化 ： onInitted");
            }

            @Override // com.zzz.ipfssdk.callback.OnStateChangeListenner
            public void onException(CodeState codeState) {
                LogUtil.m3784i("初始化 ：" + codeState.toString());
            }
        });
    }

    public static String getSyncProxyUrl(String str, String str2) {
        if (systemParam.getIPFS_Switch() == 0) {
            return str2;
        }
        try {
            LogUtil.m3784i("urlName = " + str + "\ncdnUrl = " + str2);
            Pair<String, CodeState> vodResourceUrl = IpfsSDK.getInstance().getVodResourceUrl(str, str2);
            String str3 = (String) vodResourceUrl.first;
            CodeState codeState = (CodeState) vodResourceUrl.second;
            if (TextUtils.isEmpty(str3)) {
                LogUtil.m3786e("获取节点代理地址失败, codeState = " + codeState.toString());
                str3 = str2;
            } else {
                LogUtil.m3784i("获取节点代理地址成功, codeState = " + codeState.toString());
            }
            int workMode = IpfsSDK.getInstance().getWorkMode(str3);
            if (workMode == 0) {
                LogUtil.m3784i("当前工作模式为 ： CDN模式");
            } else if (workMode == 1) {
                LogUtil.m3784i("当前工作模式为 ： P2P模式");
            } else {
                LogUtil.m3784i("当前工作模式为 ： 未知");
            }
            LogUtil.m3784i("proxyUrl = " + str3);
            return str3;
        } catch (Exception e) {
            e.printStackTrace();
            return str2;
        }
    }

    public static void syncStopAll() {
        if (systemParam.getIPFS_Switch() == 0) {
            return;
        }
        IpfsSDK.getInstance().stopAll();
    }

    public static void release() {
        if (systemParam.getIPFS_Switch() == 0) {
            return;
        }
        IpfsSDK.getInstance().release();
    }
}
