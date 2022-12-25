package com.tencent.avroom;

import com.tencent.liteav.basic.p105a.TXEAudioTypeDef;
import com.tencent.ugc.TXRecordCommon;

/* loaded from: classes3.dex */
public class TXCAVRoomConstants {
    public static int AVROOM_AUDIO_SAMPLE_RATE = 16000;
    public static int AVROOM_AUTH_BITS_CREATE_ROOM = 1;
    public static int AVROOM_AUTH_BITS_DEFAULT = -1;
    public static int AVROOM_AUTH_BITS_JOIN_ROOM = 2;
    public static int AVROOM_AUTH_BITS_RECV_AUDIO = 8;
    public static int AVROOM_AUTH_BITS_RECV_CAMERA_VIDEO = 32;
    public static int AVROOM_AUTH_BITS_RECV_SCREEN_VIDEO = 128;
    public static int AVROOM_AUTH_BITS_SEND_AUDIO = 4;
    public static int AVROOM_AUTH_BITS_SEND_CAMERA_VIDEO = 16;
    public static int AVROOM_AUTH_BITS_SEND_SCREEN_VIDEO = 64;
    public static int AVROOM_BITRATE = 400;
    public static int AVROOM_ERR_AUDIO_ENCODE_FAIL = -3004;
    public static int AVROOM_ERR_CONNECT_FAILE = -1002;
    public static int AVROOM_ERR_ENTER_ROOM_FAIL = -1003;
    public static int AVROOM_ERR_EXIT_ROOM_FAIL = -1004;
    public static int AVROOM_ERR_OPEN_CAMERA_FAIL = -3001;
    public static int AVROOM_ERR_OPEN_MIC_FAIL = -3002;
    public static int AVROOM_ERR_REQUEST_AVSEAT_FAIL = -1005;
    public static int AVROOM_ERR_REQUEST_IP_FAIL = -1001;
    public static int AVROOM_ERR_UNSUPPORTED_RESOLUTION = -3005;
    public static int AVROOM_ERR_UNSUPPORTED_SAMPLERATE = -3006;
    public static int AVROOM_ERR_VIDEO_ENCODE_FAIL = -3003;
    public static int AVROOM_EVT_CONNECT_SUCC = 1002;
    public static int AVROOM_EVT_DOWN_CHANGE_RESOLUTION = 4005;
    public static int AVROOM_EVT_ENTER_ROOM_SUCC = 1003;
    public static int AVROOM_EVT_EXIT_ROOM_SUCC = 1004;
    public static int AVROOM_EVT_FIRST_FRAME_AVAILABLE = 3005;
    public static int AVROOM_EVT_OPEN_CAMERA_SUCC = 3001;
    public static int AVROOM_EVT_PLAY_BEGIN = 4002;
    public static int AVROOM_EVT_PLAY_LOADING = 4003;
    public static int AVROOM_EVT_RCV_FIRST_I_FRAME = 4001;
    public static int AVROOM_EVT_REQUEST_AVSEAT_SUCC = 1005;
    public static int AVROOM_EVT_REQUEST_IP_SUCC = 1001;
    public static int AVROOM_EVT_START_VIDEO_DECODER = 4004;
    public static int AVROOM_EVT_START_VIDEO_ENCODER = 3002;
    public static int AVROOM_EVT_UP_CHANGE_BITRATE = 3004;
    public static int AVROOM_EVT_UP_CHANGE_RESOLUTION = 3003;
    public static int AVROOM_HOME_ORIENTATION_DOWN = 1;
    public static int AVROOM_HOME_ORIENTATION_LEFT = 2;
    public static int AVROOM_HOME_ORIENTATION_RIGHT = 0;
    public static int AVROOM_HOME_ORIENTATION_UP = 3;
    public static int AVROOM_MAX_BITRATE = 600;
    public static int AVROOM_MIN_BITRATE = 300;
    public static int AVROOM_PUSH_PAUSETIME = Integer.MAX_VALUE;
    public static int AVROOM_QOS_INTERVAL = 2000;
    public static int AVROOM_WARNING_AUDIO_DECODE_FAIL = 4102;
    public static int AVROOM_WARNING_DISCONNECT = 2001;
    public static int AVROOM_WARNING_HW_ACCELERATION_DECODE_FAIL = 4103;
    public static int AVROOM_WARNING_HW_ACCELERATION_ENCODE_FAIL = 3101;
    public static int AVROOM_WARNING_NET_BUSY = 2003;
    public static int AVROOM_WARNING_RECONNECT = 2002;
    public static int AVROOM_WARNING_VIDEO_DECODE_FAIL = 4101;
    public static int AVROOM_WARNING_VIDEO_PLAY_LAG = 4104;
    public static final String EVT_DESCRIPTION = "EVT_MSG";
    public static final String EVT_ID = "EVT_ID";
    public static final String EVT_TIME = "EVT_TIME";
    public static final String EVT_USERID = "EVT_USERID";
    public static final String NET_STATUS_AUDIO_BITRATE = "AUDIO_BITRATE";
    public static final String NET_STATUS_CACHE_SIZE = "CACHE_SIZE";
    public static final String NET_STATUS_CODEC_CACHE = "CODEC_CACHE";
    public static final String NET_STATUS_CODEC_DROP_CNT = "CODEC_DROP_CNT";
    public static final String NET_STATUS_CPU_USAGE = "CPU_USAGE";
    public static final String NET_STATUS_DROP_SIZE = "DROP_SIZE";
    public static final String NET_STATUS_NET_JITTER = "NET_JITTER";
    public static final String NET_STATUS_NET_SPEED = "NET_SPEED";
    public static final String NET_STATUS_PLAYABLE_DURATION = "PLAYABLE_DURATION";
    public static final String NET_STATUS_SERVER_IP = "SERVER_IP";
    public static final String NET_STATUS_SET_VIDEO_BITRATE = "SET_VIDEO_BITRATE";
    public static final String NET_STATUS_USER_ID = "USER_ID";
    public static final String NET_STATUS_VIDEO_BITRATE = "VIDEO_BITRATE";
    public static final String NET_STATUS_VIDEO_FPS = "VIDEO_FPS";
    public static final String NET_STATUS_VIDEO_HEIGHT = "VIDEO_HEIGHT";
    public static final String NET_STATUS_VIDEO_WIDTH = "VIDEO_WIDTH";
    public static final int RENDER_MODE_ADJUST_RESOLUTION = 1;
    public static final int RENDER_MODE_FULL_FILL_SCREEN = 0;
    public static final int RENDER_ROTATION_LANDSCAPE = 270;
    public static final int RENDER_ROTATION_PORTRAIT = 0;
    public static int TXE_AUDIO_MODE_RECEIVER = 1;
    public static int TXE_AUDIO_MODE_SPEAKER;
    public static float AVROOM_CACHETIME = TXEAudioTypeDef.f2328o;
    public static float AVROOM_MIN_ADJUSTCACHETIME = TXEAudioTypeDef.f2329p;
    public static float AVROOM_MAX_ADJUSTCACHETIME = TXEAudioTypeDef.f2330q;
    private static int[] sampleRateArray = {96000, 88200, 64000, TXRecordCommon.AUDIO_SAMPLERATE_48000, TXRecordCommon.AUDIO_SAMPLERATE_44100, TXRecordCommon.AUDIO_SAMPLERATE_32000, 24000, 22050, TXRecordCommon.AUDIO_SAMPLERATE_16000, 12000, 11025, 8000, 7350};

    public static void makeAACCodecSpecificData(byte[] bArr, int i, int i2, int i3) {
        int i4 = 0;
        int i5 = 0;
        while (true) {
            int[] iArr = sampleRateArray;
            if (i4 < iArr.length) {
                if (i2 == iArr[i4]) {
                    i5 = i4;
                }
                i4++;
            } else {
                short s = (short) (((short) (((short) (((short) (((i << 11) & 63488) | 0)) | ((i5 << 7) & 1920))) | ((i3 << 3) & 120))) | 0);
                bArr[0] = (byte) ((s >> 8) & 255);
                bArr[1] = (byte) (s & 255);
                return;
            }
        }
    }
}
