package com.tencent.avroom;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.tencent.liteav.TXCCaptureAndEnc;
import com.tencent.liteav.TXCLivePushConfig;
import com.tencent.liteav.TXCPlayerConfig;
import com.tencent.liteav.TXCRenderAndDec;
import com.tencent.liteav.avprotocol.TXCAVProtocol;
import com.tencent.liteav.basic.datareport.TXCDRApi;
import com.tencent.liteav.basic.datareport.TXCDRDef;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.p105a.TXEAudioTypeDef;
import com.tencent.liteav.basic.p108d.TXINotifyListener;
import com.tencent.liteav.basic.p111g.TXSNALPacket;
import com.tencent.liteav.basic.util.TXCTimeUtil;
import com.tencent.liteav.qos.TXCQoS;
import com.tencent.liteav.qos.TXIQoSListener;
import com.tencent.liteav.renderer.TXCGLRender;
import com.tencent.liteav.renderer.TXCTextureViewRender;
import com.tencent.liteav.renderer.TXCVideoRender;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.p134ui.TXCloudVideoView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes3.dex */
public class TXCAVRoom {
    private static final int AVROOM_ENTERED = 2;
    private static final int AVROOM_ENTERING = 1;
    private static final int AVROOM_EXITING = 3;
    private static final int AVROOM_IDLE = 0;
    private static final String TAG = "TXCAVRoom";
    private static final int VIDEO_RESOLUTION_TYPE_1280_720 = 5;
    private static final int VIDEO_RESOLUTION_TYPE_160_160 = 19;
    private static final int VIDEO_RESOLUTION_TYPE_180_320 = 7;
    private static final int VIDEO_RESOLUTION_TYPE_240_320 = 11;
    private static final int VIDEO_RESOLUTION_TYPE_270_270 = 18;
    private static final int VIDEO_RESOLUTION_TYPE_270_480 = 8;
    private static final int VIDEO_RESOLUTION_TYPE_320_180 = 9;
    private static final int VIDEO_RESOLUTION_TYPE_320_240 = 14;
    private static final int VIDEO_RESOLUTION_TYPE_320_480 = 6;
    private static final int VIDEO_RESOLUTION_TYPE_360_480 = 12;
    private static final int VIDEO_RESOLUTION_TYPE_360_640 = 0;
    private static final int VIDEO_RESOLUTION_TYPE_480_270 = 10;
    private static final int VIDEO_RESOLUTION_TYPE_480_360 = 15;
    private static final int VIDEO_RESOLUTION_TYPE_480_480 = 17;
    private static final int VIDEO_RESOLUTION_TYPE_480_640 = 13;
    private static final int VIDEO_RESOLUTION_TYPE_540_960 = 1;
    private static final int VIDEO_RESOLUTION_TYPE_640_360 = 3;
    private static final int VIDEO_RESOLUTION_TYPE_640_480 = 16;
    private static final int VIDEO_RESOLUTION_TYPE_720_1280 = 2;
    private static final int VIDEO_RESOLUTION_TYPE_960_540 = 4;
    private static int videoMemNum;
    private int appid;
    private TXCAVRoomCallback enterRoomCB;
    private TXCloudVideoView localView;
    private TXCAVRoomLisenter mAvRoomLisenter;
    private DataCollectionPusher mCaptureDataCollection;
    private Context mContext;
    private TXCLivePushConfig mLivePushConfig;
    private TXCCaptureAndEnc mPusher;
    private TXCAVRoomConfig mRoomConfig;
    private long myid;
    private boolean allowedPush = false;
    private HashMap<Long, TXCAVRoomPlayer> playerList = new HashMap<>();
    private ArrayList<Long> videoMemList = new ArrayList<>();
    private TXCAVProtocol.TXCAVProtoParam protoparam = new TXCAVProtocol.TXCAVProtoParam();
    private TXCQoS mQos = null;
    private int maxReconnectCount = 10;
    private int reconnectCount = 0;
    private int sdkVersion = 26215104;
    private Handler mainHandler = new Handler(Looper.getMainLooper());
    private int roomStatus = 0;
    private int videoResolution = 1;
    private TXCAVProtocol mTXCAVProtocol = new TXCAVProtocol();

    static /* synthetic */ int access$1208(TXCAVRoom tXCAVRoom) {
        int i = tXCAVRoom.reconnectCount;
        tXCAVRoom.reconnectCount = i + 1;
        return i;
    }

    public boolean isPushing() {
        return this.mPusher.m2522i();
    }

    public boolean isInRoom() {
        return this.roomStatus == 2;
    }

    public TXCAVRoom(Context context, TXCAVRoomConfig tXCAVRoomConfig, long j, int i) {
        this.myid = -1L;
        this.appid = -1;
        this.mContext = context;
        this.mRoomConfig = tXCAVRoomConfig;
        this.myid = j;
        this.appid = i;
        this.mPusher = new TXCCaptureAndEnc(context);
        this.mPusher.m2577a(new TXCCaptureAndEnc.AbstractC3410a() { // from class: com.tencent.avroom.TXCAVRoom.1
            @Override // com.tencent.liteav.TXCCaptureAndEnc.AbstractC3410a
            public void onEncVideoFormat(MediaFormat mediaFormat) {
            }

            @Override // com.tencent.liteav.TXCCaptureAndEnc.AbstractC3410a
            public void onRecordPcm(byte[] bArr, long j2, int i2, int i3, int i4) {
            }

            @Override // com.tencent.liteav.TXCCaptureAndEnc.AbstractC3410a
            public void onRecordRawPcm(byte[] bArr, long j2, int i2, int i3, int i4, boolean z) {
            }

            @Override // com.tencent.liteav.TXCCaptureAndEnc.AbstractC3410a
            public void onEncAudio(byte[] bArr, long j2, int i2, int i3) {
                if (TXCAVRoom.this.allowedPush) {
                    TXCAVRoom.this.mTXCAVProtocol.pushAAC(bArr, j2, i2, i3);
                }
            }

            @Override // com.tencent.liteav.TXCCaptureAndEnc.AbstractC3410a
            public void onEncVideo(TXSNALPacket tXSNALPacket) {
                if (TXCAVRoom.this.allowedPush) {
                    TXCAVRoom.this.mTXCAVProtocol.pushNAL(tXSNALPacket);
                }
            }
        });
        this.mPusher.m2586a(this.myid);
        this.mPusher.m2582a(new TXINotifyListener() { // from class: com.tencent.avroom.TXCAVRoom.2
            @Override // com.tencent.liteav.basic.p108d.TXINotifyListener
            public void onNotifyEvent(int i2, Bundle bundle) {
                TXCAVRoom.this.onBothNotifyEvent(i2, bundle);
            }
        });
        applyCaptureConfig();
        this.mTXCAVProtocol.setListener(new TXCAVProtocol.TXIAVListener() { // from class: com.tencent.avroom.TXCAVRoom.3
            @Override // com.tencent.liteav.avprotocol.TXCAVProtocol.TXIAVListener
            public void onPullAudio(TXCAVProtocol.TXSAVProtoAudioPacket tXSAVProtoAudioPacket) {
                synchronized (TXCAVRoom.this) {
                    synchronized (TXCAVRoom.this.playerList) {
                        if (TXCAVRoom.this.playerList.get(Long.valueOf(tXSAVProtoAudioPacket.tinyID)) != null) {
                            if (!((TXCAVRoomPlayer) TXCAVRoom.this.playerList.get(Long.valueOf(tXSAVProtoAudioPacket.tinyID))).m1405b() && ((TXCAVRoomPlayer) TXCAVRoom.this.playerList.get(Long.valueOf(tXSAVProtoAudioPacket.tinyID))).m1383p() != null && ((TXCAVRoomPlayer) TXCAVRoom.this.playerList.get(Long.valueOf(tXSAVProtoAudioPacket.tinyID))).m1383p().m3459g()) {
                                tXSAVProtoAudioPacket.audioData = new byte[2];
                                tXSAVProtoAudioPacket.packetType = 2;
                                TXCAVRoomConstants.makeAACCodecSpecificData(tXSAVProtoAudioPacket.audioData, 2, tXSAVProtoAudioPacket.sampleRate, tXSAVProtoAudioPacket.channelsPerSample);
                                String str = TXCAVRoom.TAG;
                                TXCLog.m2913i(str, "audioKey: makeAACCodecSpecificData id " + tXSAVProtoAudioPacket.tinyID);
                                ((TXCAVRoomPlayer) TXCAVRoom.this.playerList.get(Long.valueOf(tXSAVProtoAudioPacket.tinyID))).m1407a(true);
                            }
                            ((TXCAVRoomPlayer) TXCAVRoom.this.playerList.get(Long.valueOf(tXSAVProtoAudioPacket.tinyID))).m1413a(tXSAVProtoAudioPacket);
                            if (TXCAVRoom.this.playerList.get(Long.valueOf(tXSAVProtoAudioPacket.tinyID)) != null && ((TXCAVRoomPlayer) TXCAVRoom.this.playerList.get(Long.valueOf(tXSAVProtoAudioPacket.tinyID))).m3505a() != null && tXSAVProtoAudioPacket.audioData != null) {
                                ((TXCAVRoomPlayer) TXCAVRoom.this.playerList.get(Long.valueOf(tXSAVProtoAudioPacket.tinyID))).m3505a().m3541b(tXSAVProtoAudioPacket.audioData.length);
                            }
                        }
                    }
                }
            }

            @Override // com.tencent.liteav.avprotocol.TXCAVProtocol.TXIAVListener
            public void onPullNAL(TXCAVProtocol.TXSAVProtoNALPacket tXSAVProtoNALPacket) {
                synchronized (TXCAVRoom.this.playerList) {
                    if (TXCAVRoom.this.playerList.get(Long.valueOf(tXSAVProtoNALPacket.tinyID)) != null) {
                        if (((TXCAVRoomPlayer) TXCAVRoom.this.playerList.get(Long.valueOf(tXSAVProtoNALPacket.tinyID))).m3505a() != null) {
                            ((TXCAVRoomPlayer) TXCAVRoom.this.playerList.get(Long.valueOf(tXSAVProtoNALPacket.tinyID))).m3505a().m3559a(tXSAVProtoNALPacket.nalData.length);
                        }
                        ((TXCAVRoomPlayer) TXCAVRoom.this.playerList.get(Long.valueOf(tXSAVProtoNALPacket.tinyID))).m1412a(tXSAVProtoNALPacket);
                    }
                }
            }

            @Override // com.tencent.liteav.avprotocol.TXCAVProtocol.TXIAVListener
            public void sendNotifyEvent(final int i2, String str) {
                switch (i2) {
                    case TXCAVProtocol.TXE_AVPROTO_GET_ACC_IP_SUCCESS /* 6602 */:
                        i2 = TXCAVRoomConstants.AVROOM_EVT_REQUEST_IP_SUCC;
                        break;
                    case TXCAVProtocol.TXE_AVPROTO_GET_ACC_IP_FAIL /* 6603 */:
                        i2 = TXCAVRoomConstants.AVROOM_ERR_REQUEST_IP_FAIL;
                        break;
                    case TXCAVProtocol.TXE_AVPROTO_CONNECT_ACC_SUCCESS /* 6604 */:
                        i2 = TXCAVRoomConstants.AVROOM_EVT_CONNECT_SUCC;
                        break;
                    case TXCAVProtocol.TXE_AVPROTO_CONNECT_ACC_FAIL /* 6605 */:
                        i2 = TXCAVRoomConstants.AVROOM_ERR_CONNECT_FAILE;
                        break;
                    case TXCAVProtocol.TXE_AVPROTO_ENTER_ROOM_SUCC /* 6606 */:
                        i2 = TXCAVRoomConstants.AVROOM_EVT_ENTER_ROOM_SUCC;
                        break;
                    case TXCAVProtocol.TXE_AVPROTO_ENTER_ROOM_FAIL /* 6607 */:
                        i2 = TXCAVRoomConstants.AVROOM_ERR_ENTER_ROOM_FAIL;
                        break;
                    case TXCAVProtocol.TXE_AVPROTO_EXIT_ROOM_SUCC /* 6608 */:
                        i2 = TXCAVRoomConstants.AVROOM_EVT_EXIT_ROOM_SUCC;
                        break;
                    case TXCAVProtocol.TXE_AVPROTO_EXIT_ROOM_FAIL /* 6609 */:
                        i2 = TXCAVRoomConstants.AVROOM_ERR_EXIT_ROOM_FAIL;
                        break;
                    case TXCAVProtocol.TXE_AVPROTO_REQUEST_AVSEAT_SUCC /* 6610 */:
                        i2 = TXCAVRoomConstants.AVROOM_EVT_REQUEST_AVSEAT_SUCC;
                        break;
                    case TXCAVProtocol.TXE_AVPROTO_REQUEST_AVSEAT_FAIL /* 6611 */:
                        i2 = TXCAVRoomConstants.AVROOM_ERR_REQUEST_AVSEAT_FAIL;
                        break;
                }
                final Bundle bundle = new Bundle();
                bundle.putInt(TXCAVRoomConstants.EVT_ID, i2);
                bundle.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
                bundle.putString("EVT_MSG", str);
                ((Activity) TXCAVRoom.this.mContext).runOnUiThread(new Runnable() { // from class: com.tencent.avroom.TXCAVRoom.3.1
                    @Override // java.lang.Runnable
                    public void run() {
                        TXCAVRoom.this.mAvRoomLisenter.onAVRoomEvent(TXCAVRoom.this.myid, i2, bundle);
                    }
                });
                if (i2 == 6601) {
                    TXCAVRoom.this.reconnectRoom();
                }
            }

            @Override // com.tencent.liteav.avprotocol.TXCAVProtocol.TXIAVListener
            public void onMemberChange(final long j2, final boolean z) {
                String str = TXCAVRoom.TAG;
                TXCLog.m2915d(str, "onMemberChange: " + j2 + " flag:" + z);
                TXCAVRoom.this.mainHandler.post(new Runnable() { // from class: com.tencent.avroom.TXCAVRoom.3.2
                    @Override // java.lang.Runnable
                    public void run() {
                        if (z) {
                            TXCAVRoom.this.addRender(j2);
                        } else {
                            TXCAVRoom.this.removeRender(Long.valueOf(j2));
                        }
                        TXCAVRoom.this.mAvRoomLisenter.onMemberChange(j2, z);
                    }
                });
            }

            @Override // com.tencent.liteav.avprotocol.TXCAVProtocol.TXIAVListener
            public void onVideoStateChange(final long j2, final boolean z) {
                String str = TXCAVRoom.TAG;
                TXCLog.m2915d(str, "onVideoStateChange: " + j2 + " flag:" + z);
                TXCAVRoom.this.mainHandler.post(new Runnable() { // from class: com.tencent.avroom.TXCAVRoom.3.3
                    @Override // java.lang.Runnable
                    public void run() {
                        TXCAVRoom.this.mAvRoomLisenter.onVideoStateChange(j2, z);
                    }
                });
            }
        });
    }

    public TXCAVRoomConfig getRoomConfig() {
        return this.mRoomConfig;
    }

    public void setRoomConfig(TXCAVRoomConfig tXCAVRoomConfig) {
        this.mRoomConfig = tXCAVRoomConfig;
        applyCaptureConfig();
    }

    public String nat64Compatable(String str, short s) {
        return this.mTXCAVProtocol.nativeNAT64Compatable(str, s);
    }

    public void enterRoom(TXCAVRoomParam tXCAVRoomParam, final TXCAVRoomCallback tXCAVRoomCallback) {
        if (this.roomStatus != 0) {
            postToUiThread(tXCAVRoomCallback, -4);
            return;
        }
        this.roomStatus = 1;
        TXCDRApi.initCrashReport(this.mContext);
        TXCDRApi.txReportDAU(this.mContext, TXCDRDef.f2501bs);
        this.protoparam.roomID = tXCAVRoomParam.getRoomID();
        this.protoparam.authBits = tXCAVRoomParam.getAuthBits();
        this.protoparam.authBuffer = tXCAVRoomParam.getAuthBuffer();
        this.enterRoomCB = tXCAVRoomCallback;
        TXCAVProtocol.TXCAVProtoParam tXCAVProtoParam = this.protoparam;
        tXCAVProtoParam.userID = this.myid;
        tXCAVProtoParam.sdkAppid = this.appid;
        tXCAVProtoParam.sdkVersion = this.sdkVersion;
        applyCaptureConfig();
        this.mTXCAVProtocol.enterRoom(this.protoparam, new TXCAVProtocol.TXIAVCompletionCallback() { // from class: com.tencent.avroom.TXCAVRoom.4
            @Override // com.tencent.liteav.avprotocol.TXCAVProtocol.TXIAVCompletionCallback
            public void onComplete(int i) {
                if (i == 0) {
                    TXCAVRoom.this.reconnectCount = 0;
                    TXCAVRoom.this.startPush();
                    TXCAVRoom.this.roomStatus = 2;
                    int unused = TXCAVRoom.videoMemNum = 1;
                    TXCAVRoom.this.postToUiThread(tXCAVRoomCallback, i);
                    if (!TXCAVRoom.this.mRoomConfig.isHasVideo()) {
                        return;
                    }
                    TXCAVRoom.this.mTXCAVProtocol.changeAVState(TXCAVProtocol.AV_STATE_ENTER_VIDEO, new TXCAVProtocol.TXIAVCompletionCallback() { // from class: com.tencent.avroom.TXCAVRoom.4.1
                        @Override // com.tencent.liteav.avprotocol.TXCAVProtocol.TXIAVCompletionCallback
                        public void onComplete(int i2) {
                            String str = TXCAVRoom.TAG;
                            TXCLog.m2913i(str, "keyway change to Video  onComplete: " + i2);
                        }
                    });
                    return;
                }
                TXCAVRoom.this.roomStatus = 0;
                TXCAVRoom.this.postToUiThread(tXCAVRoomCallback, i);
            }
        });
        this.mQos = new TXCQoS(true);
        this.mQos.setUserID(this.myid);
        this.mQos.setListener(new TXIQoSListener() { // from class: com.tencent.avroom.TXCAVRoom.5
            @Override // com.tencent.liteav.qos.TXIQoSListener
            public void onEnableDropStatusChanged(boolean z) {
            }

            @Override // com.tencent.liteav.qos.TXIQoSListener
            public int onGetVideoQueueMaxCount() {
                return 5;
            }

            @Override // com.tencent.liteav.qos.TXIQoSListener
            public int onGetEncoderRealBitrate() {
                Object statusValue = TXCAVRoom.this.mCaptureDataCollection != null ? TXCAVRoom.this.mCaptureDataCollection.getStatusValue(4002) : null;
                if (statusValue == null || !(statusValue instanceof Long)) {
                    return 0;
                }
                return ((Long) statusValue).intValue();
            }

            @Override // com.tencent.liteav.qos.TXIQoSListener
            public int onGetQueueInputSize() {
                Object obj = null;
                Object statusValue = TXCAVRoom.this.mCaptureDataCollection != null ? TXCAVRoom.this.mCaptureDataCollection.getStatusValue(7002) : null;
                int i = 0;
                if (statusValue != null && (statusValue instanceof Long)) {
                    i = ((Long) statusValue).intValue();
                }
                if (TXCAVRoom.this.mCaptureDataCollection != null) {
                    obj = TXCAVRoom.this.mCaptureDataCollection.getStatusValue(7001);
                }
                return (obj == null || !(obj instanceof Long)) ? i : i + ((Long) obj).intValue();
            }

            @Override // com.tencent.liteav.qos.TXIQoSListener
            public int onGetQueueOutputSize() {
                int i = 0;
                if (TXCAVRoom.this.mCaptureDataCollection == null) {
                    return 0;
                }
                Object statusValue = TXCAVRoom.this.mCaptureDataCollection.getStatusValue(7004);
                if (statusValue != null && (statusValue instanceof Long)) {
                    i = ((Long) statusValue).intValue();
                }
                Object statusValue2 = TXCAVRoom.this.mCaptureDataCollection.getStatusValue(7003);
                return (statusValue2 == null || !(statusValue2 instanceof Long)) ? i : i + ((Long) statusValue2).intValue();
            }

            @Override // com.tencent.liteav.qos.TXIQoSListener
            public int onGetVideoQueueCurrentCount() {
                Object statusValue = TXCAVRoom.this.mCaptureDataCollection != null ? TXCAVRoom.this.mCaptureDataCollection.getStatusValue(7005) : null;
                if (statusValue == null || !(statusValue instanceof Long)) {
                    return 0;
                }
                return ((Long) statusValue).intValue();
            }

            @Override // com.tencent.liteav.qos.TXIQoSListener
            public int onGetVideoDropCount() {
                Object statusValue = TXCAVRoom.this.mCaptureDataCollection != null ? TXCAVRoom.this.mCaptureDataCollection.getStatusValue(7007) : null;
                if (statusValue == null || !(statusValue instanceof Long)) {
                    return 0;
                }
                return ((Long) statusValue).intValue();
            }

            @Override // com.tencent.liteav.qos.TXIQoSListener
            public void onEncoderParamsChanged(int i, int i2, int i3) {
                if (i != 0) {
                    if (TXCAVRoom.this.mPusher != null) {
                        TXCAVRoom.this.mPusher.m2590a(i, i2, i3);
                    }
                    String str = TXCAVRoom.TAG;
                    TXCLog.m2913i(str, "onEncoderParamsChanged:" + i);
                }
            }
        });
        this.mQos.setNotifyListener(new TXINotifyListener() { // from class: com.tencent.avroom.TXCAVRoom.6
            @Override // com.tencent.liteav.basic.p108d.TXINotifyListener
            public void onNotifyEvent(int i, Bundle bundle) {
                TXCAVRoom.this.onBothNotifyEvent(i, bundle);
            }
        });
        this.mQos.setAutoAdjustBitrate(true);
        this.mQos.setAutoAdjustStrategy(5);
        this.mQos.setDefaultVideoResolution(0);
        this.mQos.setDefaultVideoResolution(this.videoResolution);
        this.mQos.setVideoEncBitrate(100, this.mRoomConfig.getVideoBitrate(), this.mRoomConfig.getVideoBitrate());
        this.mQos.start(TXCAVRoomConstants.AVROOM_QOS_INTERVAL);
    }

    public long[] getRoomMemberList() {
        return this.mTXCAVProtocol.getRoomMemberList();
    }

    public long[] getRoomVideoList() {
        return this.mTXCAVProtocol.getRoomVideoList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reconnectRoom() {
        int i = 2;
        if (this.roomStatus != 2) {
            return;
        }
        TXCLog.m2913i(TAG, "reconnectRoom");
        int i2 = this.reconnectCount;
        if (i2 > this.maxReconnectCount) {
            if (this.mAvRoomLisenter == null) {
                return;
            }
            final Bundle bundle = new Bundle();
            bundle.putLong(TXCAVRoomConstants.EVT_USERID, this.myid);
            bundle.putInt(TXCAVRoomConstants.EVT_ID, TXCAVRoomConstants.AVROOM_WARNING_DISCONNECT);
            bundle.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
            bundle.putString("EVT_MSG", "多次重连失败，放弃重连");
            ((Activity) this.mContext).runOnUiThread(new Runnable() { // from class: com.tencent.avroom.TXCAVRoom.7
                @Override // java.lang.Runnable
                public void run() {
                    TXCAVRoom.this.mAvRoomLisenter.onAVRoomEvent(TXCAVRoom.this.myid, TXCAVRoomConstants.AVROOM_WARNING_DISCONNECT, bundle);
                }
            });
            return;
        }
        if (i2 < 3) {
            i = 0;
        }
        this.mainHandler.postDelayed(new Runnable() { // from class: com.tencent.avroom.TXCAVRoom.8
            @Override // java.lang.Runnable
            public void run() {
                TXCAVRoom.this.mTXCAVProtocol.enterRoom(TXCAVRoom.this.protoparam, new TXCAVProtocol.TXIAVCompletionCallback() { // from class: com.tencent.avroom.TXCAVRoom.8.1
                    @Override // com.tencent.liteav.avprotocol.TXCAVProtocol.TXIAVCompletionCallback
                    public void onComplete(int i3) {
                        if (i3 == 0) {
                            TXCAVRoom.this.reconnectCount = 0;
                            if (!TXCAVRoom.this.mRoomConfig.isHasVideo()) {
                                return;
                            }
                            TXCAVRoom.this.mTXCAVProtocol.changeAVState(TXCAVProtocol.AV_STATE_ENTER_VIDEO, new TXCAVProtocol.TXIAVCompletionCallback() { // from class: com.tencent.avroom.TXCAVRoom.8.1.1
                                @Override // com.tencent.liteav.avprotocol.TXCAVProtocol.TXIAVCompletionCallback
                                public void onComplete(int i4) {
                                    String str = TXCAVRoom.TAG;
                                    TXCLog.m2913i(str, "keyway changeAVState onComplete: " + i4);
                                }
                            });
                            return;
                        }
                        TXCAVRoom.this.reconnectRoom();
                    }
                });
                TXCAVRoom.access$1208(TXCAVRoom.this);
            }
        }, i * 1000);
    }

    public void setAvRoomLisenter(TXCAVRoomLisenter tXCAVRoomLisenter) {
        this.mAvRoomLisenter = tXCAVRoomLisenter;
    }

    public void exitRoom(final TXCAVRoomCallback tXCAVRoomCallback) {
        int i = this.roomStatus;
        if (i == 3 || i == 1) {
            postToUiThread(tXCAVRoomCallback, -4);
            return;
        }
        this.roomStatus = 3;
        TXCQoS tXCQoS = this.mQos;
        if (tXCQoS != null) {
            tXCQoS.stop();
            this.mQos.setListener(null);
            this.mQos.setNotifyListener(null);
            this.mQos = null;
        }
        removeAllRender();
        stopLocalPreview();
        stopPush();
        HashMap<Long, TXCAVRoomPlayer> hashMap = this.playerList;
        if (hashMap != null) {
            hashMap.clear();
        }
        this.mTXCAVProtocol.exitRoom(new TXCAVProtocol.TXIAVCompletionCallback() { // from class: com.tencent.avroom.TXCAVRoom.9
            @Override // com.tencent.liteav.avprotocol.TXCAVProtocol.TXIAVCompletionCallback
            public void onComplete(int i2) {
                TXCAVRoom.this.roomStatus = 0;
                TXCAVRoom.this.postToUiThread(tXCAVRoomCallback, i2);
                if (i2 == 0) {
                    int unused = TXCAVRoom.videoMemNum = 0;
                    if (TXCAVRoom.this.mCaptureDataCollection == null) {
                        return;
                    }
                    TXCAVRoom.this.mCaptureDataCollection.m3515b();
                    TXCAVRoom.this.mCaptureDataCollection = null;
                }
            }
        });
    }

    public void destory() {
        TXCQoS tXCQoS = this.mQos;
        if (tXCQoS != null) {
            tXCQoS.stop();
        }
        stopLocalPreview();
        removeAllRender();
        this.mPusher.m2531f();
        this.mPusher = null;
        this.mContext = null;
        this.mTXCAVProtocol.destory();
    }

    public void startLocalPreview(TXCloudVideoView tXCloudVideoView) {
        this.localView = tXCloudVideoView;
        tXCloudVideoView.setVisibility(0);
        String str = TAG;
        TXCLog.m2913i(str, "startLocalPreview: " + tXCloudVideoView.getTag());
        applyCaptureConfig();
        this.mPusher.m2568a(tXCloudVideoView);
        if (this.mCaptureDataCollection == null) {
            this.mCaptureDataCollection = new DataCollectionPusher(this.appid, this.myid, this.mContext, this.mLivePushConfig);
            this.mCaptureDataCollection.m3519a(this.mTXCAVProtocol);
            this.mCaptureDataCollection.m3523a(this.mAvRoomLisenter);
            this.mCaptureDataCollection.m3524a();
            this.mCaptureDataCollection.m3518a(this.mPusher);
        }
        this.mRoomConfig.setHasVideo(true);
        if (this.roomStatus != 2) {
            return;
        }
        this.mTXCAVProtocol.changeAVState(TXCAVProtocol.AV_STATE_ENTER_VIDEO, new TXCAVProtocol.TXIAVCompletionCallback() { // from class: com.tencent.avroom.TXCAVRoom.10
            @Override // com.tencent.liteav.avprotocol.TXCAVProtocol.TXIAVCompletionCallback
            public void onComplete(int i) {
                String str2 = TXCAVRoom.TAG;
                Log.i(str2, "keyway change to Video onComplete: " + i);
            }
        });
    }

    public void stopLocalPreview() {
        DataCollectionPusher dataCollectionPusher = this.mCaptureDataCollection;
        if (dataCollectionPusher != null) {
            dataCollectionPusher.m3515b();
            this.mCaptureDataCollection = null;
        }
        TXCloudVideoView tXCloudVideoView = this.localView;
        if (tXCloudVideoView != null) {
            tXCloudVideoView.setVisibility(4);
            this.localView = null;
        }
        TXCCaptureAndEnc tXCCaptureAndEnc = this.mPusher;
        if (tXCCaptureAndEnc != null) {
            tXCCaptureAndEnc.m2566a(true);
        }
        this.mRoomConfig.setHasVideo(false);
        if (this.roomStatus == 2) {
            this.mTXCAVProtocol.changeAVState(TXCAVProtocol.AV_STATE_ENTER_AUDIO, new TXCAVProtocol.TXIAVCompletionCallback() { // from class: com.tencent.avroom.TXCAVRoom.11
                @Override // com.tencent.liteav.avprotocol.TXCAVProtocol.TXIAVCompletionCallback
                public void onComplete(int i) {
                    String str = TXCAVRoom.TAG;
                    Log.i(str, "keyway change to audio onComplete: " + i);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addRender(long j) {
        synchronized (this.playerList) {
            if (this.playerList.get(Long.valueOf(j)) == null) {
                TXCAVRoomPlayer tXCAVRoomPlayer = new TXCAVRoomPlayer(this.mContext, 2);
                tXCAVRoomPlayer.m1416a(j);
                tXCAVRoomPlayer.m1414a(new TXINotifyListener() { // from class: com.tencent.avroom.TXCAVRoom.12
                    @Override // com.tencent.liteav.basic.p108d.TXINotifyListener
                    public void onNotifyEvent(int i, Bundle bundle) {
                        TXCAVRoom.this.onBothNotifyEvent(i, bundle);
                    }
                });
                TXCPlayerConfig renderConfig = getRenderConfig();
                tXCAVRoomPlayer.m1411a(renderConfig);
                tXCAVRoomPlayer.m1418a(this.mRoomConfig.getRemoteRenderMode());
                tXCAVRoomPlayer.m1404b(TXCAVRoomConstants.AVROOM_HOME_ORIENTATION_RIGHT);
                if (renderConfig.f4347i) {
                    tXCAVRoomPlayer.m1409a(new TXCTextureViewRender());
                } else {
                    tXCAVRoomPlayer.m1409a(new TXCGLRender());
                }
                this.playerList.put(Long.valueOf(j), tXCAVRoomPlayer);
            }
            this.playerList.get(Long.valueOf(j)).m1402b(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeRender(Long l) {
        synchronized (this.playerList) {
            TXCAVRoomPlayer tXCAVRoomPlayer = this.playerList.get(l);
            if (tXCAVRoomPlayer == null) {
                return;
            }
            tXCAVRoomPlayer.m1401c();
            tXCAVRoomPlayer.m1409a((TXCVideoRender) null);
            tXCAVRoomPlayer.m1410a((TXCRenderAndDec.AbstractC3529a) null);
            if (tXCAVRoomPlayer.m3505a() != null) {
                tXCAVRoomPlayer.m3505a().m3542b();
                tXCAVRoomPlayer.m3504a((DataCollectionPlayer) null);
            }
            this.playerList.remove(l);
        }
    }

    public synchronized void startRemoteView(TXCloudVideoView tXCloudVideoView, long j) {
        synchronized (this.playerList) {
            TXCAVRoomPlayer tXCAVRoomPlayer = this.playerList.get(Long.valueOf(j));
            if (tXCAVRoomPlayer == null) {
                return;
            }
            if (tXCloudVideoView != null) {
                tXCAVRoomPlayer.m3503a(tXCloudVideoView);
                this.videoMemList.add(Long.valueOf(j));
                tXCAVRoomPlayer.m1418a(0);
                requestView(this.videoMemList);
            }
            DataCollectionPlayer dataCollectionPlayer = new DataCollectionPlayer(j);
            dataCollectionPlayer.m3558a(this.mAvRoomLisenter);
            dataCollectionPlayer.m3549a(this.mTXCAVProtocol);
            dataCollectionPlayer.m3547a(this.playerList.get(Long.valueOf(j)));
            dataCollectionPlayer.m3552a(this.playerList.get(Long.valueOf(j)).m1383p());
            dataCollectionPlayer.m3548a(this.playerList.get(Long.valueOf(j)).m1384o());
            dataCollectionPlayer.m3545a(this.playerList.get(Long.valueOf(j)).m1382q());
            this.playerList.get(Long.valueOf(j)).m3504a(dataCollectionPlayer);
            dataCollectionPlayer.m3560a();
            setRenderMode(this.mRoomConfig.getRemoteRenderMode());
        }
    }

    public synchronized void stopRemoteView(long j) {
        this.videoMemList.remove(Long.valueOf(j));
        requestView(this.videoMemList);
    }

    private void removeAllRender() {
        HashMap<Long, TXCAVRoomPlayer> hashMap = this.playerList;
        if (hashMap == null || hashMap.size() == 0) {
            return;
        }
        for (Long l : this.playerList.keySet()) {
            if (this.playerList.get(l) != null) {
                stopRemoteView(l.longValue());
                this.playerList.get(l).m1401c();
                if (this.playerList.get(l) != null && this.playerList.get(l).m3505a() != null) {
                    this.playerList.get(l).m3505a().m3542b();
                    this.playerList.get(l).m3504a((DataCollectionPlayer) null);
                }
            }
        }
        this.playerList.clear();
    }

    private void requestViewList(ArrayList<TXCAVProtocol.TXSAVRoomView> arrayList, final TXCAVRoomCallback tXCAVRoomCallback) {
        this.mTXCAVProtocol.requestViews(arrayList, new TXCAVProtocol.TXIAVCompletionCallback() { // from class: com.tencent.avroom.TXCAVRoom.13
            @Override // com.tencent.liteav.avprotocol.TXCAVProtocol.TXIAVCompletionCallback
            public void onComplete(int i) {
                TXCAVRoom.this.postToUiThread(tXCAVRoomCallback, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startPush() {
        TXCLog.m2913i(TAG, "keyway startPush: ");
        this.allowedPush = true;
        this.mPusher.m2536e();
    }

    private void stopPush() {
        TXCLog.m2913i(TAG, "keyway stopPush: ");
        this.allowedPush = false;
        this.mPusher.m2531f();
    }

    public void onPause() {
        TXCLog.m2913i(TAG, "keyway onPause : ");
        this.mPusher.m2528g();
    }

    public void onResume() {
        TXCLog.m2913i(TAG, "keyway onResume: ");
        this.mPusher.m2525h();
    }

    public void setVideoBitrateAndvideoAspect(int i, int i2) {
        int nativeGetProperResolutionByVideoBitrate = TXCQoS.nativeGetProperResolutionByVideoBitrate(true, i, i2);
        if (nativeGetProperResolutionByVideoBitrate < 0) {
            if (i2 == 1) {
                nativeGetProperResolutionByVideoBitrate = 0;
            } else if (i2 == 2) {
                nativeGetProperResolutionByVideoBitrate = 12;
            } else if (i2 == 3) {
                nativeGetProperResolutionByVideoBitrate = 17;
            }
        }
        String str = TAG;
        TXCLog.m2913i(str, "setVideoBitrateAndvideoAspect videoBitrate: " + i + " videoAspect:" + i2 + " videoResolution:" + nativeGetProperResolutionByVideoBitrate);
        this.videoResolution = nativeGetProperResolutionByVideoBitrate;
        this.mRoomConfig.videoBitrate(i);
        applyCaptureConfig();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void postToUiThread(final TXCAVRoomCallback tXCAVRoomCallback, final int i) {
        Context context = this.mContext;
        if (context != null) {
            ((Activity) context).runOnUiThread(new Runnable() { // from class: com.tencent.avroom.TXCAVRoom.14
                @Override // java.lang.Runnable
                public void run() {
                    tXCAVRoomCallback.onComplete(i);
                }
            });
        }
    }

    private void requestView(ArrayList<Long> arrayList) {
        String str = TAG;
        TXCLog.m2913i(str, "requestView: " + arrayList.size());
        ArrayList<TXCAVProtocol.TXSAVRoomView> arrayList2 = new ArrayList<>();
        Iterator<Long> it2 = arrayList.iterator();
        while (it2.hasNext()) {
            long longValue = it2.next().longValue();
            TXCAVProtocol.TXSAVRoomView tXSAVRoomView = new TXCAVProtocol.TXSAVRoomView();
            tXSAVRoomView.tinyID = longValue;
            tXSAVRoomView.height = 240;
            tXSAVRoomView.width = 320;
            arrayList2.add(tXSAVRoomView);
        }
        requestViewList(arrayList2, new TXCAVRoomCallback() { // from class: com.tencent.avroom.TXCAVRoom.15
            @Override // com.tencent.avroom.TXCAVRoomCallback
            public void onComplete(int i) {
                String str2 = TXCAVRoom.TAG;
                TXCLog.m2913i(str2, "keyway requestViewList onComplete: " + i);
            }
        });
    }

    public void switchCamera() {
        TXCCaptureAndEnc tXCCaptureAndEnc = this.mPusher;
        if (tXCCaptureAndEnc != null) {
            tXCCaptureAndEnc.m2520j();
        }
    }

    public void setRemoteMute(boolean z, long j) {
        HashMap<Long, TXCAVRoomPlayer> hashMap = this.playerList;
        if (hashMap == null) {
            return;
        }
        if (j == 0) {
            for (Long l : hashMap.keySet()) {
                this.playerList.get(l).m1397c(z);
            }
        }
        synchronized (this.playerList) {
            if (this.playerList.get(Long.valueOf(j)) != null) {
                this.playerList.get(Long.valueOf(j)).m1397c(z);
            }
        }
    }

    public void setMirror(boolean z) {
        TXCCaptureAndEnc tXCCaptureAndEnc = this.mPusher;
        if (tXCCaptureAndEnc != null) {
            tXCCaptureAndEnc.m2532e(z);
        }
    }

    public void setAudioMode(int i) {
        TXCRenderAndDec.m1415a(this.mContext, i);
    }

    public void setLocalMute(boolean z) {
        TXCCaptureAndEnc tXCCaptureAndEnc = this.mPusher;
        if (tXCCaptureAndEnc != null) {
            tXCCaptureAndEnc.m2537d(z);
        }
    }

    private void applyCaptureConfig() {
        if (this.mPusher != null) {
            this.mLivePushConfig = new TXCLivePushConfig();
            TXCLivePushConfig tXCLivePushConfig = this.mLivePushConfig;
            tXCLivePushConfig.f4309q = TXCAVRoomConstants.AVROOM_AUDIO_SAMPLE_RATE;
            tXCLivePushConfig.f4310r = TXEAudioTypeDef.f2319f;
            tXCLivePushConfig.f4311s = true;
            tXCLivePushConfig.f4295c = this.mRoomConfig.getVideoBitrate();
            this.mLivePushConfig.f4300h = this.mRoomConfig.getCaptureVideoFPS();
            TXCLivePushConfig tXCLivePushConfig2 = this.mLivePushConfig;
            tXCLivePushConfig2.f4301i = 1;
            tXCLivePushConfig2.f4304l = this.mRoomConfig.getHomeOrientation();
            this.mLivePushConfig.f4305m = this.mRoomConfig.isFrontCamera();
            this.mLivePushConfig.f4314v = this.mRoomConfig.getPauseFps();
            this.mLivePushConfig.f4312t = this.mRoomConfig.getPauseImg();
            TXCLivePushConfig tXCLivePushConfig3 = this.mLivePushConfig;
            tXCLivePushConfig3.f4313u = TXCAVRoomConstants.AVROOM_PUSH_PAUSETIME;
            tXCLivePushConfig3.f4315w = this.mRoomConfig.getPauseFlag();
            TXCLivePushConfig tXCLivePushConfig4 = this.mLivePushConfig;
            tXCLivePushConfig4.f4303k = this.videoResolution;
            tXCLivePushConfig4.f4302j = this.mRoomConfig.isEnableVideoHWAcceleration() ? 1 : 0;
            TXCQoS tXCQoS = this.mQos;
            if (tXCQoS != null) {
                tXCQoS.setDefaultVideoResolution(this.videoResolution);
                this.mQos.setVideoEncBitrate(100, this.mRoomConfig.getVideoBitrate(), this.mRoomConfig.getVideoBitrate());
            }
            this.mPusher.m2572a(this.mLivePushConfig);
        }
    }

    private TXCPlayerConfig getRenderConfig() {
        TXCPlayerConfig tXCPlayerConfig = new TXCPlayerConfig();
        tXCPlayerConfig.m1451a(true);
        tXCPlayerConfig.m1452a(TXCAVRoomConstants.AVROOM_CACHETIME);
        tXCPlayerConfig.m1448c(TXCAVRoomConstants.AVROOM_MIN_ADJUSTCACHETIME);
        tXCPlayerConfig.m1450b(TXCAVRoomConstants.AVROOM_MAX_ADJUSTCACHETIME);
        tXCPlayerConfig.m1449b(true);
        tXCPlayerConfig.f4347i = this.mRoomConfig.isEnableVideoHWAcceleration();
        return tXCPlayerConfig;
    }

    public void setRenderMode(int i) {
        for (Map.Entry<Long, TXCAVRoomPlayer> entry : this.playerList.entrySet()) {
            String str = TAG;
            TXCLog.m2913i(str, "Key = " + entry.getKey() + ", Value = " + entry.getValue());
            entry.getValue().m1418a(i);
        }
    }

    public void setEyeScaleLevel(int i) {
        this.mPusher.m2550c(i);
    }

    public void setFaceSlimLevel(int i) {
        this.mPusher.m2542d(i);
    }

    public void setFilter(Bitmap bitmap) {
        this.mPusher.m2585a(bitmap);
    }

    public void setGreenScreenFile(String str) {
        this.mPusher.m2554b(str);
    }

    public void setMotionTmpl(String str) {
        this.mPusher.m2567a(str);
    }

    public void setExposureCompensation(float f) {
        this.mPusher.m2551c(f);
    }

    public void setBeautyFilter(int i, int i2, int i3, int i4) {
        TXCCaptureAndEnc tXCCaptureAndEnc = this.mPusher;
        if (tXCCaptureAndEnc != null) {
            tXCCaptureAndEnc.m2560b(i);
            this.mPusher.m2558b(i2, i3, i4);
        }
    }

    public void setFaceVLevel(int i) {
        TXCCaptureAndEnc tXCCaptureAndEnc = this.mPusher;
        if (tXCCaptureAndEnc != null) {
            tXCCaptureAndEnc.m2534e(i);
        }
    }

    public void setSpecialRatio(float f) {
        TXCCaptureAndEnc tXCCaptureAndEnc = this.mPusher;
        if (tXCCaptureAndEnc != null) {
            tXCCaptureAndEnc.m2594a(f);
        }
    }

    public void setFaceShortLevel(int i) {
        TXCCaptureAndEnc tXCCaptureAndEnc = this.mPusher;
        if (tXCCaptureAndEnc != null) {
            tXCCaptureAndEnc.m2530f(i);
        }
    }

    public void setChinLevel(int i) {
        TXCCaptureAndEnc tXCCaptureAndEnc = this.mPusher;
        if (tXCCaptureAndEnc != null) {
            tXCCaptureAndEnc.m2527g(i);
        }
    }

    public void setNoseSlimLevel(int i) {
        TXCCaptureAndEnc tXCCaptureAndEnc = this.mPusher;
        if (tXCCaptureAndEnc != null) {
            tXCCaptureAndEnc.m2524h(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onBothNotifyEvent(int i, Bundle bundle) {
        final int i2;
        String str = TAG;
        TXCLog.m2913i(str, "AVROOM onNotifyEvent: " + bundle.getString("EVT_MSG"));
        if (this.mAvRoomLisenter != null) {
            final Long valueOf = Long.valueOf(bundle.getLong(TXCAVRoomConstants.EVT_USERID));
            if (i == 1003) {
                i2 = TXCAVRoomConstants.AVROOM_EVT_OPEN_CAMERA_SUCC;
            } else if (i == 1103) {
                i2 = TXCAVRoomConstants.AVROOM_WARNING_HW_ACCELERATION_ENCODE_FAIL;
            } else if (i == 2101) {
                i2 = TXCAVRoomConstants.AVROOM_WARNING_VIDEO_DECODE_FAIL;
            } else if (i == 2003) {
                i2 = TXCAVRoomConstants.AVROOM_EVT_RCV_FIRST_I_FRAME;
            } else if (i == 2004) {
                i2 = TXCAVRoomConstants.AVROOM_EVT_PLAY_BEGIN;
            } else if (i == 2007) {
                i2 = TXCAVRoomConstants.AVROOM_EVT_PLAY_LOADING;
            } else if (i == 2008) {
                i2 = TXCAVRoomConstants.AVROOM_EVT_START_VIDEO_DECODER;
            } else if (i == 2105) {
                i2 = TXCAVRoomConstants.AVROOM_WARNING_VIDEO_PLAY_LAG;
            } else if (i != 2106) {
                switch (i) {
                    case TXLiveConstants.PUSH_ERR_VIDEO_ENCODE_FAIL /* -1303 */:
                        i2 = TXCAVRoomConstants.AVROOM_ERR_VIDEO_ENCODE_FAIL;
                        break;
                    case TXLiveConstants.PUSH_ERR_OPEN_MIC_FAIL /* -1302 */:
                        i2 = TXCAVRoomConstants.AVROOM_ERR_OPEN_MIC_FAIL;
                        break;
                    case TXLiveConstants.PUSH_ERR_OPEN_CAMERA_FAIL /* -1301 */:
                        i2 = TXCAVRoomConstants.AVROOM_ERR_OPEN_CAMERA_FAIL;
                        break;
                    default:
                        switch (i) {
                            case 1005:
                                int i3 = TXCAVRoomConstants.AVROOM_EVT_UP_CHANGE_RESOLUTION;
                            case 1006:
                                i2 = TXCAVRoomConstants.AVROOM_EVT_UP_CHANGE_BITRATE;
                                break;
                            case 1007:
                                i2 = TXCAVRoomConstants.AVROOM_EVT_FIRST_FRAME_AVAILABLE;
                                break;
                            case 1008:
                                i2 = TXCAVRoomConstants.AVROOM_EVT_START_VIDEO_ENCODER;
                                break;
                            default:
                                return;
                        }
                }
            } else {
                i2 = TXCAVRoomConstants.AVROOM_WARNING_HW_ACCELERATION_DECODE_FAIL;
            }
            final Bundle bundle2 = new Bundle();
            bundle2.putLong(TXCAVRoomConstants.EVT_USERID, valueOf.longValue());
            bundle2.putInt(TXCAVRoomConstants.EVT_ID, bundle.getInt(TXCAVRoomConstants.EVT_ID, 0));
            bundle2.putLong("EVT_TIME", bundle.getLong("EVT_TIME", 0L));
            bundle2.putString("EVT_MSG", bundle.getString("EVT_MSG", ""));
            if (this.mPusher == null) {
                return;
            }
            ((Activity) this.mContext).runOnUiThread(new Runnable() { // from class: com.tencent.avroom.TXCAVRoom.16
                @Override // java.lang.Runnable
                public void run() {
                    String str2 = TXCAVRoom.TAG;
                    TXCLog.m2913i(str2, "NotifyEvent sendNotifyEvent userID: " + valueOf + "  msg " + bundle2.getString("EVT_MSG"));
                    TXCAVRoom.this.mAvRoomLisenter.onAVRoomEvent(valueOf.longValue(), i2, bundle2);
                }
            });
        }
    }
}
