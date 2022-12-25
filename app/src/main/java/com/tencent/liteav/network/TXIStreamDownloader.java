package com.tencent.liteav.network;

import android.content.Context;
import com.tencent.liteav.basic.p105a.TXEAudioTypeDef;
import com.tencent.liteav.basic.p108d.TXINotifyListener;
import com.tencent.liteav.basic.p111g.TXSAudioPacket;
import com.tencent.liteav.basic.p111g.TXSNALPacket;
import com.tencent.liteav.network.TXCStreamDownloader;
import java.util.Map;
import java.util.Vector;

/* loaded from: classes3.dex */
public abstract class TXIStreamDownloader {
    protected Context mApplicationContext;
    protected Map<String, String> mHeaders;
    protected TXIStreamDownloaderListener mListener = null;
    protected TXINotifyListener mNotifyListener = null;
    protected AbstractC3570a mRestartListener = null;
    protected boolean mIsRunning = false;
    protected String mOriginUrl = "";
    public int connectRetryTimes = 0;
    public int connectRetryLimit = 3;
    public int connectRetryInterval = 3;
    protected boolean mEnableMessage = false;

    /* renamed from: com.tencent.liteav.network.TXIStreamDownloader$a */
    /* loaded from: classes3.dex */
    public interface AbstractC3570a {
        void onRestartDownloader();
    }

    public int getConnectCountQuic() {
        return 0;
    }

    public int getConnectCountTcp() {
        return 0;
    }

    public String getCurrentStreamUrl() {
        return null;
    }

    public TXCStreamDownloader.DownloadStats getDownloadStats() {
        return null;
    }

    public String getRealStreamUrl() {
        return null;
    }

    public boolean isQuicChannel() {
        return false;
    }

    public abstract void startDownload(Vector<TXCStreamPlayUrl> vector, boolean z, boolean z2, boolean z3);

    public abstract void stopDownload();

    public TXIStreamDownloader(Context context) {
        this.mApplicationContext = null;
        this.mApplicationContext = context;
    }

    public void setListener(TXIStreamDownloaderListener tXIStreamDownloaderListener) {
        this.mListener = tXIStreamDownloaderListener;
    }

    public void setHeaders(Map<String, String> map) {
        this.mHeaders = map;
    }

    public void setNotifyListener(TXINotifyListener tXINotifyListener) {
        this.mNotifyListener = tXINotifyListener;
    }

    public void setRestartListener(AbstractC3570a abstractC3570a) {
        this.mRestartListener = abstractC3570a;
    }

    public void sendNotifyEvent(int i) {
        TXINotifyListener tXINotifyListener = this.mNotifyListener;
        if (tXINotifyListener != null) {
            tXINotifyListener.onNotifyEvent(i, null);
        }
    }

    public void onRecvVideoData(byte[] bArr, int i, long j, long j2, int i2) {
        if (this.mListener != null) {
            TXSNALPacket tXSNALPacket = new TXSNALPacket();
            tXSNALPacket.nalData = bArr;
            tXSNALPacket.nalType = i;
            tXSNALPacket.dts = j;
            tXSNALPacket.pts = j2;
            tXSNALPacket.codecId = i2;
            this.mListener.onPullNAL(tXSNALPacket);
        }
    }

    public void onRecvAudioData(byte[] bArr, int i, int i2, int i3) {
        if (this.mListener != null) {
            TXSAudioPacket tXSAudioPacket = new TXSAudioPacket();
            tXSAudioPacket.audioData = bArr;
            tXSAudioPacket.timestamp = i;
            if (i2 == 10) {
                if (i3 == 1) {
                    tXSAudioPacket.packetType = TXEAudioTypeDef.f2324k;
                } else {
                    tXSAudioPacket.packetType = TXEAudioTypeDef.f2325l;
                }
                if (tXSAudioPacket.packetType == TXEAudioTypeDef.f2324k) {
                    tXSAudioPacket.bitsPerChannel = TXEAudioTypeDef.f2321h;
                }
            }
            if (i2 == 2) {
                tXSAudioPacket.packetType = TXEAudioTypeDef.f2326m;
            }
            this.mListener.onPullAudio(tXSAudioPacket);
        }
    }

    public void setOriginUrl(String str) {
        this.mOriginUrl = str;
    }
}
