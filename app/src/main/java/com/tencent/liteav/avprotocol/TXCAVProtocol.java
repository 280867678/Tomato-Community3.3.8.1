package com.tencent.liteav.avprotocol;

import android.support.p002v4.app.NotificationCompat;
import android.util.Log;
import com.tencent.liteav.basic.p105a.TXEAudioTypeDef;
import com.tencent.liteav.basic.p110f.TXCConfigCenter;
import com.tencent.liteav.basic.p111g.TXSAudioPacket;
import com.tencent.liteav.basic.p111g.TXSNALPacket;
import com.tencent.liteav.basic.util.TXCSystemUtil;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public class TXCAVProtocol {
    public static byte AV_STATE_ENTER_AUDIO = 1;
    public static byte AV_STATE_ENTER_VIDEO = 3;
    public static byte AV_STATE_EXIT_AUDIO = 2;
    public static byte AV_STATE_EXIT_VIDEO = 4;
    public static byte AV_STATE_NONE = 0;
    private static final String TAG = "TXCAVProtocol";
    public static final int TXE_AVPROTO_CONNECT_ACC_FAIL = 6605;
    public static final int TXE_AVPROTO_CONNECT_ACC_SUCCESS = 6604;
    public static final int TXE_AVPROTO_ENTER_ROOM_FAIL = 6607;
    public static final int TXE_AVPROTO_ENTER_ROOM_SUCC = 6606;
    public static final int TXE_AVPROTO_EXIT_ROOM_FAIL = 6609;
    public static final int TXE_AVPROTO_EXIT_ROOM_SUCC = 6608;
    public static final int TXE_AVPROTO_GET_ACC_IP_FAIL = 6603;
    public static final int TXE_AVPROTO_GET_ACC_IP_SUCCESS = 6602;
    public static final int TXE_AVPROTO_HEARTBEAT_FAIL = 6601;
    public static final int TXE_AVPROTO_REQUEST_AVSEAT_FAIL = 6611;
    public static final int TXE_AVPROTO_REQUEST_AVSEAT_SUCC = 6610;
    private long mInstance;
    private TXIAVListener mListener = null;

    /* loaded from: classes3.dex */
    public interface TXIAVCompletionCallback {
        void onComplete(int i);
    }

    /* loaded from: classes3.dex */
    public interface TXIAVListener {
        void onMemberChange(long j, boolean z);

        void onPullAudio(TXSAVProtoAudioPacket tXSAVProtoAudioPacket);

        void onPullNAL(TXSAVProtoNALPacket tXSAVProtoNALPacket);

        void onVideoStateChange(long j, boolean z);

        void sendNotifyEvent(int i, String str);
    }

    private native void nativeChangeAVState(long j, TXIAVCompletionCallback tXIAVCompletionCallback, byte b);

    private native void nativeEnterRoom(long j, TXIAVCompletionCallback tXIAVCompletionCallback, long j2, long j3, long j4, long j5, byte[] bArr, long j6, int i);

    private native void nativeExitRoom(long j, TXIAVCompletionCallback tXIAVCompletionCallback);

    private native DownloadStats nativeGetDownloadStats(long j);

    private native long[] nativeGetRoomMemberList(long j);

    private native long[] nativeGetRoomVideoList(long j);

    private native UploadStats nativeGetUploadStats(long j);

    private native long nativeInitAVProtocol();

    private native void nativePushAAC(long j, byte[] bArr, int i, int i2, int i3, int i4, long j2);

    private native void nativePushNAL(long j, byte[] bArr, int i, long j2, long j3, long j4, long j5, long j6, long j7);

    private native void nativeRequestViews(long j, TXIAVCompletionCallback tXIAVCompletionCallback, long[] jArr, int[] iArr, int[] iArr2);

    private native void nativeUninitAVProtocol(long j);

    public native String nativeNAT64Compatable(String str, short s);

    /* loaded from: classes3.dex */
    public class UploadStats {
        public long audioCacheLen;
        public long audioDropCount;
        public long channelType;
        public long connTS;
        public long dnsTS;
        public long inAudioBytes;
        public long inVideoBytes;
        public long outAudioBytes;
        public long outVideoBytes;
        public String serverIP;
        public long startTS;
        public long videoCacheLen;
        public long videoDropCount;

        public UploadStats() {
        }
    }

    /* loaded from: classes3.dex */
    public class DownloadStats {
        public long afterParseAudioBytes;
        public long afterParseVideoBytes;
        public long beforeParseAudioBytes;
        public long beforeParseVideoBytes;
        public long connTS;
        public long dnsTS;
        public long firstAudioTS;
        public long firstVideoTS;
        public String serverIP;
        public long startTS;

        public DownloadStats() {
        }
    }

    static {
        TXCSystemUtil.m2873e();
    }

    /* loaded from: classes3.dex */
    public class TXSAVProtoAudioPacket extends TXSAudioPacket {
        public int roomID;
        public long tinyID;

        public TXSAVProtoAudioPacket() {
        }
    }

    /* loaded from: classes3.dex */
    public class TXSAVProtoNALPacket extends TXSNALPacket {
        public int roomID;
        public long tinyID;

        public TXSAVProtoNALPacket() {
        }
    }

    /* loaded from: classes3.dex */
    public class TXCAVProtoParam {
        public int authBits;
        public byte[] authBuffer;
        public long roomID;
        public int sdkAppid;
        public int sdkVersion;
        public long userID;

        public TXCAVProtoParam() {
        }
    }

    /* loaded from: classes3.dex */
    public class TXSAVRoomView {
        public int height;
        public long tinyID;
        public int width;

        public TXSAVRoomView() {
        }
    }

    public TXCAVProtocol() {
        this.mInstance = 0L;
        this.mInstance = nativeInitAVProtocol();
    }

    public void destory() {
        long j = this.mInstance;
        if (j == 0) {
            return;
        }
        nativeUninitAVProtocol(j);
        this.mInstance = 0L;
    }

    public void setListener(TXIAVListener tXIAVListener) {
        this.mListener = tXIAVListener;
    }

    public void enterRoom(TXCAVProtoParam tXCAVProtoParam, TXIAVCompletionCallback tXIAVCompletionCallback) {
        if (this.mInstance == 0) {
            return;
        }
        nativeEnterRoom(this.mInstance, tXIAVCompletionCallback, tXCAVProtoParam.sdkAppid, tXCAVProtoParam.sdkVersion, tXCAVProtoParam.roomID, tXCAVProtoParam.authBits, tXCAVProtoParam.authBuffer, tXCAVProtoParam.userID, (int) TXCConfigCenter.m2988a().m2979a("QUICMode", "AVRoom"));
    }

    public void exitRoom(final TXIAVCompletionCallback tXIAVCompletionCallback) {
        long j = this.mInstance;
        if (j == 0) {
            return;
        }
        nativeExitRoom(j, new TXIAVCompletionCallback() { // from class: com.tencent.liteav.avprotocol.TXCAVProtocol.1
            @Override // com.tencent.liteav.avprotocol.TXCAVProtocol.TXIAVCompletionCallback
            public void onComplete(int i) {
                tXIAVCompletionCallback.onComplete(i);
            }
        });
    }

    public void changeAVState(byte b, TXIAVCompletionCallback tXIAVCompletionCallback) {
        long j = this.mInstance;
        if (j == 0) {
            return;
        }
        nativeChangeAVState(j, tXIAVCompletionCallback, b);
    }

    public void requestViews(ArrayList<TXSAVRoomView> arrayList, TXIAVCompletionCallback tXIAVCompletionCallback) {
        if (this.mInstance == 0) {
            return;
        }
        long[] jArr = new long[arrayList.size()];
        int[] iArr = new int[arrayList.size()];
        int[] iArr2 = new int[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            jArr[i] = arrayList.get(i).tinyID;
            iArr[i] = arrayList.get(i).width;
            iArr2[i] = arrayList.get(i).height;
        }
        nativeRequestViews(this.mInstance, tXIAVCompletionCallback, jArr, iArr, iArr2);
    }

    public void pushAAC(byte[] bArr, long j, int i, int i2) {
        long j2 = this.mInstance;
        if (j2 == 0) {
            return;
        }
        nativePushAAC(j2, bArr, i, i2, TXEAudioTypeDef.f2321h, TXEAudioTypeDef.f2325l, j);
    }

    public void pushNAL(TXSNALPacket tXSNALPacket) {
        long j = this.mInstance;
        if (j == 0) {
            return;
        }
        nativePushNAL(j, tXSNALPacket.nalData, tXSNALPacket.nalType, tXSNALPacket.gopIndex, tXSNALPacket.gopFrameIndex, tXSNALPacket.frameIndex, tXSNALPacket.refFremeIndex, tXSNALPacket.pts, tXSNALPacket.dts);
    }

    private void onPullAudio(int i, long j, byte[] bArr, long j2, int i2, int i3, int i4, int i5) {
        if (this.mListener != null) {
            TXSAVProtoAudioPacket tXSAVProtoAudioPacket = new TXSAVProtoAudioPacket();
            tXSAVProtoAudioPacket.roomID = i;
            tXSAVProtoAudioPacket.tinyID = j;
            tXSAVProtoAudioPacket.audioData = bArr;
            tXSAVProtoAudioPacket.timestamp = j2;
            tXSAVProtoAudioPacket.sampleRate = i2;
            tXSAVProtoAudioPacket.channelsPerSample = i3;
            tXSAVProtoAudioPacket.bitsPerChannel = i4;
            tXSAVProtoAudioPacket.packetType = i5;
            this.mListener.onPullAudio(tXSAVProtoAudioPacket);
        }
    }

    private void onPullVideo(int i, long j, byte[] bArr, int i2, long j2, long j3, int i3, int i4, int i5, int i6) {
        if (this.mListener != null) {
            TXSAVProtoNALPacket tXSAVProtoNALPacket = new TXSAVProtoNALPacket();
            tXSAVProtoNALPacket.roomID = i;
            tXSAVProtoNALPacket.tinyID = j;
            tXSAVProtoNALPacket.nalType = i2;
            tXSAVProtoNALPacket.nalData = bArr;
            tXSAVProtoNALPacket.pts = j2;
            tXSAVProtoNALPacket.dts = j3;
            tXSAVProtoNALPacket.gopIndex = i3;
            tXSAVProtoNALPacket.gopFrameIndex = i4;
            tXSAVProtoNALPacket.frameIndex = i5;
            tXSAVProtoNALPacket.refFremeIndex = i6;
            this.mListener.onPullNAL(tXSAVProtoNALPacket);
        }
    }

    private void onMemberChange(long j, boolean z) {
        this.mListener.onMemberChange(j, z);
    }

    private void onVideoStateChange(long j, boolean z) {
        this.mListener.onVideoStateChange(j, z);
    }

    private void sendNotifyEvent(int i, String str) {
        String str2 = TAG;
        Log.i(str2, NotificationCompat.CATEGORY_EVENT + i);
        this.mListener.sendNotifyEvent(i, str);
    }

    public long[] getRoomMemberList() {
        long j = this.mInstance;
        if (j == 0) {
            return null;
        }
        return nativeGetRoomMemberList(j);
    }

    public long[] getRoomVideoList() {
        long j = this.mInstance;
        if (j == 0) {
            return null;
        }
        return nativeGetRoomVideoList(j);
    }

    public UploadStats getUploadStats() {
        long j = this.mInstance;
        if (j != 0) {
            return nativeGetUploadStats(j);
        }
        return null;
    }

    public DownloadStats getDownloadStats() {
        long j = this.mInstance;
        if (j != 0) {
            return nativeGetDownloadStats(j);
        }
        return null;
    }
}
