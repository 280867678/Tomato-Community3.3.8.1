package com.tencent.rtmp.sharp.jni;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.SystemClock;
import android.support.media.ExifInterface;
import android.text.TextUtils;
import com.gen.p059mh.webapp_extensions.fragments.MainFragment;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.rtmp.sharp.jni.TraeMediaPlayer;
import com.tomatolive.library.utils.ConstantUtils;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

@SuppressLint({"NewApi"})
/* loaded from: classes3.dex */
public class TraeAudioManager extends BroadcastReceiver {
    public static final String ACTION_TRAEAUDIOMANAGER_NOTIFY = "com.tencent.sharp.ACTION_TRAEAUDIOMANAGER_NOTIFY";
    public static final String ACTION_TRAEAUDIOMANAGER_REQUEST = "com.tencent.sharp.ACTION_TRAEAUDIOMANAGER_REQUEST";
    public static final String ACTION_TRAEAUDIOMANAGER_RES = "com.tencent.sharp.ACTION_TRAEAUDIOMANAGER_RES";
    static final int AUDIO_DEVICE_OUT_BLUETOOTH_A2DP = 128;
    static final int AUDIO_DEVICE_OUT_BLUETOOTH_A2DP_HEADPHONES = 256;
    static final int AUDIO_DEVICE_OUT_BLUETOOTH_A2DP_SPEAKER = 512;
    static final int AUDIO_DEVICE_OUT_BLUETOOTH_SCO = 16;
    static final int AUDIO_DEVICE_OUT_BLUETOOTH_SCO_CARKIT = 64;
    static final int AUDIO_DEVICE_OUT_BLUETOOTH_SCO_HEADSET = 32;
    static final int AUDIO_DEVICE_OUT_EARPIECE = 1;
    static final int AUDIO_DEVICE_OUT_SPEAKER = 2;
    static final int AUDIO_DEVICE_OUT_WIRED_HEADPHONE = 8;
    static final int AUDIO_DEVICE_OUT_WIRED_HEADSET = 4;
    public static final int AUDIO_MANAGER_ACTIVE_NONE = 0;
    public static final int AUDIO_MANAGER_ACTIVE_RING = 2;
    public static final int AUDIO_MANAGER_ACTIVE_VOICECALL = 1;
    static final String AUDIO_PARAMETER_STREAM_ROUTING = "routing";
    public static final String CONNECTDEVICE_DEVICENAME = "CONNECTDEVICE_DEVICENAME";
    public static final String CONNECTDEVICE_RESULT_DEVICENAME = "CONNECTDEVICE_RESULT_DEVICENAME";
    public static final String DEVICE_BLUETOOTHHEADSET = "DEVICE_BLUETOOTHHEADSET";
    public static final String DEVICE_EARPHONE = "DEVICE_EARPHONE";
    public static final String DEVICE_NONE = "DEVICE_NONE";
    public static final String DEVICE_SPEAKERPHONE = "DEVICE_SPEAKERPHONE";
    public static final int DEVICE_STATUS_CONNECTED = 2;
    public static final int DEVICE_STATUS_CONNECTING = 1;
    public static final int DEVICE_STATUS_DISCONNECTED = 0;
    public static final int DEVICE_STATUS_DISCONNECTING = 3;
    public static final int DEVICE_STATUS_ERROR = -1;
    public static final int DEVICE_STATUS_UNCHANGEABLE = 4;
    public static final String DEVICE_WIREDHEADSET = "DEVICE_WIREDHEADSET";
    public static final int EARACTION_AWAY = 0;
    public static final int EARACTION_CLOSE = 1;
    public static final String EXTRA_DATA_AVAILABLEDEVICE_LIST = "EXTRA_DATA_AVAILABLEDEVICE_LIST";
    public static final String EXTRA_DATA_CONNECTEDDEVICE = "EXTRA_DATA_CONNECTEDDEVICE";
    public static final String EXTRA_DATA_DEVICECONFIG = "EXTRA_DATA_DEVICECONFIG";
    public static final String EXTRA_DATA_IF_HAS_BLUETOOTH_THIS_IS_NAME = "EXTRA_DATA_IF_HAS_BLUETOOTH_THIS_IS_NAME";
    public static final String EXTRA_DATA_PREV_CONNECTEDDEVICE = "EXTRA_DATA_PREV_CONNECTEDDEVICE";
    public static final String EXTRA_DATA_ROUTESWITCHEND_DEV = "EXTRA_DATA_ROUTESWITCHEND_DEV";
    public static final String EXTRA_DATA_ROUTESWITCHEND_TIME = "EXTRA_DATA_ROUTESWITCHEND_TIME";
    public static final String EXTRA_DATA_ROUTESWITCHSTART_FROM = "EXTRA_DATA_ROUTESWITCHSTART_FROM";
    public static final String EXTRA_DATA_ROUTESWITCHSTART_TO = "EXTRA_DATA_ROUTESWITCHSTART_TO";
    public static final String EXTRA_DATA_STREAMTYPE = "EXTRA_DATA_STREAMTYPE";
    public static final String EXTRA_EARACTION = "EXTRA_EARACTION";
    public static final int FORCE_ANALOG_DOCK = 8;
    public static final int FORCE_BT_A2DP = 4;
    public static final int FORCE_BT_CAR_DOCK = 6;
    public static final int FORCE_BT_DESK_DOCK = 7;
    public static final int FORCE_BT_SCO = 3;
    public static final int FORCE_DEFAULT = 0;
    public static final int FORCE_DIGITAL_DOCK = 9;
    public static final int FORCE_HEADPHONES = 2;
    public static final int FORCE_NONE = 0;
    public static final int FORCE_NO_BT_A2DP = 10;
    public static final int FORCE_SPEAKER = 1;
    public static final int FORCE_WIRED_ACCESSORY = 5;
    public static final int FOR_COMMUNICATION = 0;
    public static final int FOR_DOCK = 3;
    public static final int FOR_MEDIA = 1;
    public static final int FOR_RECORD = 2;
    public static final String GETCONNECTEDDEVICE_RESULT_LIST = "GETCONNECTEDDEVICE_REULT_LIST";
    public static final String GETCONNECTINGDEVICE_RESULT_LIST = "GETCONNECTINGDEVICE_REULT_LIST";
    public static final String ISDEVICECHANGABLED_RESULT_ISCHANGABLED = "ISDEVICECHANGABLED_REULT_ISCHANGABLED";
    public static boolean IsMusicScene = false;
    public static boolean IsUpdateSceneFlag = false;
    public static final int MODE_MUSIC_PLAYBACK = 2;
    public static final int MODE_MUSIC_PLAY_RECORD = 1;
    public static final int MODE_MUSIC_PLAY_RECORD_HIGH_QUALITY = 3;
    public static final int MODE_VOICE_CHAT = 0;
    public static final String MUSIC_CONFIG = "DEVICE_SPEAKERPHONE;DEVICE_WIREDHEADSET;DEVICE_BLUETOOTHHEADSET;";
    public static final String NOTIFY_DEVICECHANGABLE_UPDATE = "NOTIFY_DEVICECHANGABLE_UPDATE";
    public static final String NOTIFY_DEVICECHANGABLE_UPDATE_DATE = "NOTIFY_DEVICECHANGABLE_UPDATE_DATE";
    public static final String NOTIFY_DEVICELIST_UPDATE = "NOTIFY_DEVICELISTUPDATE";
    public static final String NOTIFY_RING_COMPLETION = "NOTIFY_RING_COMPLETION";
    public static final String NOTIFY_ROUTESWITCHEND = "NOTIFY_ROUTESWITCHEND";
    public static final String NOTIFY_ROUTESWITCHSTART = "NOTIFY_ROUTESWITCHSTART";
    public static final String NOTIFY_SERVICE_STATE = "NOTIFY_SERVICE_STATE";
    public static final String NOTIFY_SERVICE_STATE_DATE = "NOTIFY_SERVICE_STATE_DATE";
    public static final String NOTIFY_STREAMTYPE_UPDATE = "NOTIFY_STREAMTYPE_UPDATE";
    private static final int NUM_FORCE_CONFIG = 11;
    private static final int NUM_FORCE_USE = 4;
    public static final String OPERATION_CONNECTDEVICE = "OPERATION_CONNECTDEVICE";
    public static final String OPERATION_CONNECT_HIGHEST_PRIORITY_DEVICE = "OPERATION_CONNECT_HIGHEST_PRIORITY_DEVICE";
    public static final String OPERATION_EARACTION = "OPERATION_EARACTION";
    public static final String OPERATION_GETCONNECTEDDEVICE = "OPERATION_GETCONNECTEDDEVICE";
    public static final String OPERATION_GETCONNECTINGDEVICE = "OPERATION_GETCONNECTINGDEVICE";
    public static final String OPERATION_GETDEVICELIST = "OPERATION_GETDEVICELIST";
    public static final String OPERATION_GETSTREAMTYPE = "OPERATION_GETSTREAMTYPE";
    public static final String OPERATION_ISDEVICECHANGABLED = "OPERATION_ISDEVICECHANGABLED";
    public static final String OPERATION_RECOVER_AUDIO_FOCUS = "OPERATION_RECOVER_AUDIO_FOCUS";
    public static final String OPERATION_REGISTERAUDIOSESSION = "OPERATION_REGISTERAUDIOSESSION";
    public static final String OPERATION_REQUEST_RELEASE_AUDIO_FOCUS = "OPERATION_REQUEST_RELEASE_AUDIO_FOCUS";
    public static final String OPERATION_STARTRING = "OPERATION_STARTRING";
    public static final String OPERATION_STARTSERVICE = "OPERATION_STARTSERVICE";
    public static final String OPERATION_STOPRING = "OPERATION_STOPRING";
    public static final String OPERATION_STOPSERVICE = "OPERATION_STOPSERVICE";
    public static final String OPERATION_VOICECALL_AUDIOPARAM_CHANGED = "OPERATION_VOICECALL_AUDIOPARAM_CHANGED";
    public static final String OPERATION_VOICECALL_POSTPROCESS = "OPERATION_VOICECALL_POSTROCESS";
    public static final String OPERATION_VOICECALL_PREPROCESS = "OPERATION_VOICECALL_PREPROCESS";
    public static final String PARAM_DEVICE = "PARAM_DEVICE";
    public static final String PARAM_ERROR = "PARAM_ERROR";
    public static final String PARAM_ISHOSTSIDE = "PARAM_ISHOSTSIDE";
    public static final String PARAM_MODEPOLICY = "PARAM_MODEPOLICY";
    public static final String PARAM_OPERATION = "PARAM_OPERATION";
    public static final String PARAM_RES_ERRCODE = "PARAM_RES_ERRCODE";
    public static final String PARAM_RING_DATASOURCE = "PARAM_RING_DATASOURCE";
    public static final String PARAM_RING_FILEPATH = "PARAM_RING_FILEPATH";
    public static final String PARAM_RING_LOOP = "PARAM_RING_LOOP";
    public static final String PARAM_RING_LOOPCOUNT = "PARAM_RING_LOOPCOUNT";
    public static final String PARAM_RING_MODE = "PARAM_RING_MODE";
    public static final String PARAM_RING_RSID = "PARAM_RING_RSID";
    public static final String PARAM_RING_URI = "PARAM_RING_URI";
    public static final String PARAM_RING_USERDATA_STRING = "PARAM_RING_USERDATA_STRING";
    public static final String PARAM_SESSIONID = "PARAM_SESSIONID";
    public static final String PARAM_STATUS = "PARAM_STATUS";
    public static final String PARAM_STREAMTYPE = "PARAM_STREAMTYPE";
    public static final String REGISTERAUDIOSESSION_ISREGISTER = "REGISTERAUDIOSESSION_ISREGISTER";
    public static final int RES_ERRCODE_DEVICE_BTCONNCECTED_TIMEOUT = 10;
    public static final int RES_ERRCODE_DEVICE_NOT_VISIABLE = 8;
    public static final int RES_ERRCODE_DEVICE_UNCHANGEABLE = 9;
    public static final int RES_ERRCODE_DEVICE_UNKOWN = 7;
    public static final int RES_ERRCODE_NONE = 0;
    public static final int RES_ERRCODE_RING_NOT_EXIST = 5;
    public static final int RES_ERRCODE_SERVICE_OFF = 1;
    public static final int RES_ERRCODE_STOPRING_INTERRUPT = 4;
    public static final int RES_ERRCODE_VOICECALLPOST_INTERRUPT = 6;
    public static final int RES_ERRCODE_VOICECALL_EXIST = 2;
    public static final int RES_ERRCODE_VOICECALL_NOT_EXIST = 3;
    public static final String VIDEO_CONFIG = "DEVICE_EARPHONE;DEVICE_SPEAKERPHONE;DEVICE_BLUETOOTHHEADSET;DEVICE_WIREDHEADSET;";
    public static final String VOICECALL_CONFIG = "DEVICE_SPEAKERPHONE;DEVICE_EARPHONE;DEVICE_BLUETOOTHHEADSET;DEVICE_WIREDHEADSET;";
    Context _context;
    C3721f mTraeAudioManagerLooper;
    static ReentrantLock _glock = new ReentrantLock();
    static TraeAudioManager _ginstance = null;
    static int _gHostProcessId = -1;
    static final String[] forceName = {"FORCE_NONE", "FORCE_SPEAKER", "FORCE_HEADPHONES", "FORCE_BT_SCO", "FORCE_BT_A2DP", "FORCE_WIRED_ACCESSORY", "FORCE_BT_CAR_DOCK", "FORCE_BT_DESK_DOCK", "FORCE_ANALOG_DOCK", "FORCE_NO_BT_A2DP", "FORCE_DIGITAL_DOCK"};
    AudioManager _am = null;
    int _activeMode = 0;
    int _prevMode = 0;
    int _streamType = 0;
    int _modePolicy = -1;
    boolean IsBluetoothA2dpExisted = true;
    TraeAudioSessionHost _audioSessionHost = null;
    C3719e _deviceConfigManager = null;
    AbstractC3718d _bluetoothCheck = null;
    String sessionConnectedDev = DEVICE_NONE;
    ReentrantLock _lock = new ReentrantLock();
    AbstractC3730k _switchThread = null;

    int InternalSessionEarAction(HashMap<String, Object> hashMap) {
        return 0;
    }

    public static boolean checkDevName(String str) {
        if (str == null) {
            return false;
        }
        return DEVICE_SPEAKERPHONE.equals(str) || DEVICE_EARPHONE.equals(str) || DEVICE_WIREDHEADSET.equals(str) || DEVICE_BLUETOOTHHEADSET.equals(str);
    }

    public static boolean isHandfree(String str) {
        return checkDevName(str) && DEVICE_SPEAKERPHONE.equals(str);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.tencent.rtmp.sharp.jni.TraeAudioManager$e */
    /* loaded from: classes3.dex */
    public class C3719e {

        /* renamed from: a */
        HashMap<String, C3720a> f5700a = new HashMap<>();

        /* renamed from: b */
        String f5701b = TraeAudioManager.DEVICE_NONE;

        /* renamed from: c */
        String f5702c = TraeAudioManager.DEVICE_NONE;

        /* renamed from: d */
        String f5703d = TraeAudioManager.DEVICE_NONE;

        /* renamed from: e */
        ReentrantLock f5704e = new ReentrantLock();

        /* renamed from: f */
        boolean f5705f = false;

        /* renamed from: g */
        String f5706g = "unknow";

        /* renamed from: com.tencent.rtmp.sharp.jni.TraeAudioManager$e$a */
        /* loaded from: classes3.dex */
        public class C3720a {

            /* renamed from: a */
            String f5708a = TraeAudioManager.DEVICE_NONE;

            /* renamed from: b */
            boolean f5709b = false;

            /* renamed from: c */
            int f5710c = 0;

            public C3720a() {
            }

            /* renamed from: a */
            public boolean m333a(String str, int i) {
                if (str == null || str.length() <= 0 || !TraeAudioManager.checkDevName(str)) {
                    return false;
                }
                this.f5708a = str;
                this.f5710c = i;
                return true;
            }

            /* renamed from: a */
            public String m334a() {
                return this.f5708a;
            }

            /* renamed from: b */
            public boolean m331b() {
                return this.f5709b;
            }

            /* renamed from: c */
            public int m330c() {
                return this.f5710c;
            }

            /* renamed from: a */
            public void m332a(boolean z) {
                this.f5709b = z;
            }
        }

        public C3719e() {
        }

        /* renamed from: a */
        public boolean m356a(String str) {
            String replace;
            AudioDeviceInterface.LogTraceEntry(" strConfigs:" + str);
            if (str != null && str.length() > 0 && (replace = str.replace("\n", "").replace("\r", "")) != null && replace.length() > 0) {
                if (replace.indexOf(";") < 0) {
                    replace = replace + ";";
                }
                String[] split = replace.split(";");
                if (split != null && 1 <= split.length) {
                    this.f5704e.lock();
                    for (int i = 0; i < split.length; i++) {
                        m355a(split[i], i);
                    }
                    this.f5704e.unlock();
                    TraeAudioManager.this.printDevices();
                    return true;
                }
            }
            return false;
        }

        /* renamed from: a */
        boolean m355a(String str, int i) {
            AudioDeviceInterface.LogTraceEntry(" devName:" + str + " priority:" + i);
            C3720a c3720a = new C3720a();
            if (c3720a.m333a(str, i)) {
                if (this.f5700a.containsKey(str)) {
                    if (QLog.isColorLevel()) {
                        QLog.m376e("TRAE", 2, "err dev exist!");
                    }
                    return false;
                }
                this.f5700a.put(str, c3720a);
                this.f5705f = true;
                if (QLog.isColorLevel()) {
                    QLog.m371w("TRAE", 2, " n" + m347e() + " 0:" + m357a(0));
                }
                AudioDeviceInterface.LogTraceExit();
                return true;
            }
            if (QLog.isColorLevel()) {
                QLog.m376e("TRAE", 2, " err dev init!");
            }
            return false;
        }

        /* renamed from: a */
        public void m358a() {
            this.f5704e.lock();
            this.f5700a.clear();
            this.f5701b = TraeAudioManager.DEVICE_NONE;
            this.f5702c = TraeAudioManager.DEVICE_NONE;
            this.f5703d = TraeAudioManager.DEVICE_NONE;
            this.f5704e.unlock();
        }

        /* renamed from: b */
        public boolean m353b() {
            this.f5704e.lock();
            boolean z = this.f5705f;
            this.f5704e.unlock();
            return z;
        }

        /* renamed from: c */
        public void m351c() {
            this.f5704e.lock();
            this.f5705f = false;
            this.f5704e.unlock();
        }

        /* renamed from: a */
        public boolean m354a(String str, boolean z) {
            this.f5704e.lock();
            C3720a c3720a = this.f5700a.get(str);
            boolean z2 = true;
            if (c3720a == null || c3720a.m331b() == z) {
                z2 = false;
            } else {
                c3720a.m332a(z);
                this.f5705f = true;
                if (QLog.isColorLevel()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(" ++setVisible:");
                    sb.append(str);
                    sb.append(z ? " Y" : " N");
                    QLog.m371w("TRAE", 2, sb.toString());
                }
            }
            this.f5704e.unlock();
            return z2;
        }

        /* renamed from: b */
        public void m352b(String str) {
            if (str == null) {
                this.f5706g = "unknow";
            } else if (str.isEmpty()) {
                this.f5706g = "unknow";
            } else {
                this.f5706g = str;
            }
        }

        /* renamed from: d */
        public String m349d() {
            return this.f5706g;
        }

        /* renamed from: c */
        public boolean m350c(String str) {
            this.f5704e.lock();
            C3720a c3720a = this.f5700a.get(str);
            boolean m331b = c3720a != null ? c3720a.m331b() : false;
            this.f5704e.unlock();
            return m331b;
        }

        /* renamed from: d */
        public int m348d(String str) {
            this.f5704e.lock();
            C3720a c3720a = this.f5700a.get(str);
            int m330c = c3720a != null ? c3720a.m330c() : -1;
            this.f5704e.unlock();
            return m330c;
        }

        /* renamed from: e */
        public int m347e() {
            this.f5704e.lock();
            int size = this.f5700a.size();
            this.f5704e.unlock();
            return size;
        }

        /* renamed from: a */
        public String m357a(int i) {
            C3720a c3720a;
            this.f5704e.lock();
            Iterator<Map.Entry<String, C3720a>> it2 = this.f5700a.entrySet().iterator();
            int i2 = 0;
            while (true) {
                if (!it2.hasNext()) {
                    c3720a = null;
                    break;
                }
                Map.Entry<String, C3720a> next = it2.next();
                if (i2 == i) {
                    c3720a = next.getValue();
                    break;
                }
                i2++;
            }
            String m334a = c3720a != null ? c3720a.m334a() : TraeAudioManager.DEVICE_NONE;
            this.f5704e.unlock();
            return m334a;
        }

        /* renamed from: e */
        public String m346e(String str) {
            this.f5704e.lock();
            C3720a c3720a = null;
            for (Map.Entry<String, C3720a> entry : this.f5700a.entrySet()) {
                entry.getKey();
                entry.getValue();
                C3720a value = entry.getValue();
                if (value != null && value.m331b() && !value.m334a().equals(str) && (c3720a == null || value.m330c() >= c3720a.m330c())) {
                    c3720a = value;
                }
            }
            this.f5704e.unlock();
            return c3720a != null ? c3720a.m334a() : TraeAudioManager.DEVICE_SPEAKERPHONE;
        }

        /* renamed from: f */
        public String m345f() {
            this.f5704e.lock();
            C3720a c3720a = null;
            for (Map.Entry<String, C3720a> entry : this.f5700a.entrySet()) {
                entry.getKey();
                entry.getValue();
                C3720a value = entry.getValue();
                if (value != null && value.m331b() && (c3720a == null || value.m330c() >= c3720a.m330c())) {
                    c3720a = value;
                }
            }
            this.f5704e.unlock();
            return c3720a != null ? c3720a.m334a() : TraeAudioManager.DEVICE_SPEAKERPHONE;
        }

        /* renamed from: g */
        public String m343g() {
            this.f5704e.lock();
            C3720a c3720a = this.f5700a.get(this.f5703d);
            String str = (c3720a == null || !c3720a.m331b()) ? null : this.f5703d;
            this.f5704e.unlock();
            return str;
        }

        /* renamed from: h */
        public String m341h() {
            this.f5704e.lock();
            String m336m = m336m();
            this.f5704e.unlock();
            return m336m;
        }

        /* renamed from: i */
        public String m340i() {
            this.f5704e.lock();
            String m335n = m335n();
            this.f5704e.unlock();
            return m335n;
        }

        /* renamed from: f */
        public boolean m344f(String str) {
            boolean z;
            this.f5704e.lock();
            C3720a c3720a = this.f5700a.get(str);
            if (c3720a == null || !c3720a.m331b()) {
                z = false;
            } else {
                this.f5703d = str;
                z = true;
            }
            this.f5704e.unlock();
            return z;
        }

        /* renamed from: g */
        public boolean m342g(String str) {
            boolean z;
            this.f5704e.lock();
            C3720a c3720a = this.f5700a.get(str);
            if (c3720a == null || !c3720a.m331b()) {
                z = false;
            } else {
                String str2 = this.f5702c;
                if (str2 != null && !str2.equals(str)) {
                    this.f5701b = this.f5702c;
                }
                this.f5702c = str;
                this.f5703d = "";
                z = true;
            }
            this.f5704e.unlock();
            return z;
        }

        /* renamed from: j */
        public HashMap<String, Object> m339j() {
            HashMap<String, Object> hashMap = new HashMap<>();
            this.f5704e.lock();
            hashMap.put(TraeAudioManager.EXTRA_DATA_AVAILABLEDEVICE_LIST, m337l());
            hashMap.put(TraeAudioManager.EXTRA_DATA_CONNECTEDDEVICE, m336m());
            hashMap.put(TraeAudioManager.EXTRA_DATA_PREV_CONNECTEDDEVICE, m335n());
            this.f5704e.unlock();
            return hashMap;
        }

        /* renamed from: k */
        public ArrayList<String> m338k() {
            new ArrayList();
            this.f5704e.lock();
            ArrayList<String> m337l = m337l();
            this.f5704e.unlock();
            return m337l;
        }

        /* renamed from: l */
        ArrayList<String> m337l() {
            ArrayList<String> arrayList = new ArrayList<>();
            for (Map.Entry<String, C3720a> entry : this.f5700a.entrySet()) {
                C3720a value = entry.getValue();
                if (value != null && value.m331b()) {
                    arrayList.add(value.m334a());
                }
            }
            return arrayList;
        }

        /* renamed from: m */
        String m336m() {
            C3720a c3720a = this.f5700a.get(this.f5702c);
            return (c3720a == null || !c3720a.m331b()) ? TraeAudioManager.DEVICE_NONE : this.f5702c;
        }

        /* renamed from: n */
        String m335n() {
            C3720a c3720a = this.f5700a.get(this.f5701b);
            return (c3720a == null || !c3720a.m331b()) ? TraeAudioManager.DEVICE_NONE : this.f5701b;
        }
    }

    void printDevices() {
        AudioDeviceInterface.LogTraceEntry("");
        int m347e = this._deviceConfigManager.m347e();
        if (QLog.isColorLevel()) {
            QLog.m371w("TRAE", 2, "   ConnectedDevice:" + this._deviceConfigManager.m341h());
        }
        if (QLog.isColorLevel()) {
            QLog.m371w("TRAE", 2, "   ConnectingDevice:" + this._deviceConfigManager.m343g());
        }
        if (QLog.isColorLevel()) {
            QLog.m371w("TRAE", 2, "   prevConnectedDevice:" + this._deviceConfigManager.m340i());
        }
        if (QLog.isColorLevel()) {
            QLog.m371w("TRAE", 2, "   AHPDevice:" + this._deviceConfigManager.m345f());
        }
        if (QLog.isColorLevel()) {
            QLog.m371w("TRAE", 2, "   deviceNamber:" + m347e);
        }
        for (int i = 0; i < m347e; i++) {
            String m357a = this._deviceConfigManager.m357a(i);
            if (QLog.isColorLevel()) {
                QLog.m371w("TRAE", 2, "      " + i + " devName:" + m357a + " Visible:" + this._deviceConfigManager.m350c(m357a) + " Priority:" + this._deviceConfigManager.m348d(m357a));
            }
        }
        String[] strArr = (String[]) this._deviceConfigManager.m338k().toArray(new String[0]);
        if (QLog.isColorLevel()) {
            QLog.m371w("TRAE", 2, "   AvailableNamber:" + strArr.length);
        }
        for (int i2 = 0; i2 < strArr.length; i2++) {
            String str = strArr[i2];
            if (QLog.isColorLevel()) {
                QLog.m371w("TRAE", 2, "      " + i2 + " devName:" + str + " Visible:" + this._deviceConfigManager.m350c(str) + " Priority:" + this._deviceConfigManager.m348d(str));
            }
        }
        AudioDeviceInterface.LogTraceExit();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean isCloseSystemAPM(int i) {
        if (i != -1) {
            return false;
        }
        if (Build.MANUFACTURER.equals("Xiaomi")) {
            if (Build.MODEL.equals("MI 2") || Build.MODEL.equals("MI 2A") || Build.MODEL.equals("MI 2S") || Build.MODEL.equals("MI 2SC")) {
                return true;
            }
        } else if (Build.MANUFACTURER.equals("samsung") && Build.MODEL.equals("SCH-I959")) {
            return true;
        }
        return false;
    }

    public static boolean IsEabiLowVersionByAbi(String str) {
        if (str == null) {
            return true;
        }
        if (str.contains("x86") || str.contains("mips")) {
            return false;
        }
        return str.equalsIgnoreCase("armeabi") || !str.equalsIgnoreCase("armeabi-v7a");
    }

    static boolean IsEabiLowVersion() {
        String str;
        String str2 = Build.CPU_ABI;
        if (Build.VERSION.SDK_INT >= 8) {
            try {
                str = (String) Build.class.getDeclaredField("CPU_ABI2").get(null);
            } catch (Exception unused) {
                return IsEabiLowVersionByAbi(str2);
            }
        } else {
            str = "unknown";
        }
        if (QLog.isColorLevel()) {
            QLog.m371w("TRAE", 2, "IsEabiVersion CPU_ABI:" + str2 + " CPU_ABI2:" + str);
        }
        return IsEabiLowVersionByAbi(str2) && IsEabiLowVersionByAbi(str);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int getAudioSource(int i) {
        int i2 = 0;
        if (IsMusicScene) {
            return 0;
        }
        if (IsEabiLowVersion()) {
            if (QLog.isColorLevel()) {
                QLog.m371w("TRAE", 2, "[Config] armeabi low Version, getAudioSource _audioSourcePolicy:" + i + " source:0");
            }
            return 0;
        }
        int i3 = Build.VERSION.SDK_INT;
        if (i >= 0) {
            if (QLog.isColorLevel()) {
                QLog.m371w("TRAE", 2, "[Config] getAudioSource _audioSourcePolicy:" + i + " source:" + i);
            }
            return i;
        }
        if (i3 >= 11) {
            i2 = 7;
        }
        if (QLog.isColorLevel()) {
            QLog.m371w("TRAE", 2, "[Config] getAudioSource _audioSourcePolicy:" + i + " source:" + i2);
        }
        return i2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int getAudioStreamType(int i) {
        if (IsMusicScene) {
            return 3;
        }
        if (IsEabiLowVersion()) {
            if (QLog.isColorLevel()) {
                QLog.m371w("TRAE", 2, "[Config] armeabi low Version, getAudioStreamType audioStreamTypePolicy:" + i + " streamType:3");
            }
            return 3;
        }
        int i2 = i >= 0 ? i : Build.VERSION.SDK_INT >= 9 ? 0 : 3;
        if (QLog.isColorLevel()) {
            QLog.m371w("TRAE", 2, "[Config] getAudioStreamType audioStreamTypePolicy:" + i + " streamType:" + i2);
        }
        return i2;
    }

    static int getCallAudioMode(int i) {
        int i2 = 0;
        if (IsMusicScene) {
            return 0;
        }
        if (IsEabiLowVersion()) {
            if (QLog.isColorLevel()) {
                QLog.m371w("TRAE", 2, "[Config] armeabi low Version, getCallAudioMode modePolicy:" + i + " mode:0");
            }
            return 0;
        }
        int i3 = Build.VERSION.SDK_INT;
        if (i >= 0) {
            if (QLog.isColorLevel()) {
                QLog.m371w("TRAE", 2, "[Config] getCallAudioMode modePolicy:" + i + " mode:" + i);
            }
            return i;
        }
        if (i3 >= 11) {
            i2 = 3;
        }
        if (QLog.isColorLevel()) {
            QLog.m371w("TRAE", 2, "[Config] getCallAudioMode _modePolicy:" + i + " mode:" + i2 + "facturer:" + Build.MANUFACTURER + " model:" + Build.MODEL);
        }
        return i2;
    }

    void updateDeviceStatus() {
        boolean z;
        int m347e = this._deviceConfigManager.m347e();
        for (int i = 0; i < m347e; i++) {
            String m357a = this._deviceConfigManager.m357a(i);
            if (m357a != null) {
                if (m357a.equals(DEVICE_BLUETOOTHHEADSET)) {
                    AbstractC3718d abstractC3718d = this._bluetoothCheck;
                    if (abstractC3718d == null) {
                        z = this._deviceConfigManager.m354a(m357a, false);
                    } else {
                        z = this._deviceConfigManager.m354a(m357a, abstractC3718d.mo363b());
                    }
                } else if (m357a.equals(DEVICE_WIREDHEADSET)) {
                    z = this._deviceConfigManager.m354a(m357a, this._am.isWiredHeadsetOn());
                } else if (m357a.equals(DEVICE_SPEAKERPHONE)) {
                    this._deviceConfigManager.m354a(m357a, true);
                }
                if (z && QLog.isColorLevel()) {
                    QLog.m371w("TRAE", 2, "pollUpdateDevice dev:" + m357a + " Visible:" + this._deviceConfigManager.m350c(m357a));
                }
            }
            z = false;
            if (z) {
                QLog.m371w("TRAE", 2, "pollUpdateDevice dev:" + m357a + " Visible:" + this._deviceConfigManager.m350c(m357a));
            }
        }
        checkAutoDeviceListUpdate();
    }

    void _updateEarphoneVisable() {
        if (this._deviceConfigManager.m350c(DEVICE_WIREDHEADSET)) {
            if (QLog.isColorLevel()) {
                QLog.m371w("TRAE", 2, " detected headset plugin,so disable earphone");
            }
            this._deviceConfigManager.m354a(DEVICE_EARPHONE, false);
            return;
        }
        if (QLog.isColorLevel()) {
            QLog.m371w("TRAE", 2, " detected headset plugout,so enable earphone");
        }
        this._deviceConfigManager.m354a(DEVICE_EARPHONE, true);
    }

    void checkAutoDeviceListUpdate() {
        if (this._deviceConfigManager.m353b()) {
            if (QLog.isColorLevel()) {
                QLog.m371w("TRAE", 2, "checkAutoDeviceListUpdate got update!");
            }
            _updateEarphoneVisable();
            this._deviceConfigManager.m351c();
            internalSendMessage(32785, new HashMap<>());
        }
    }

    void checkDevicePlug(String str, boolean z) {
        if (this._deviceConfigManager.m353b()) {
            if (QLog.isColorLevel()) {
                StringBuilder sb = new StringBuilder();
                sb.append("checkDevicePlug got update dev:");
                sb.append(str);
                sb.append(z ? " piugin" : " plugout");
                sb.append(" connectedDev:");
                sb.append(this._deviceConfigManager.m341h());
                QLog.m371w("TRAE", 2, sb.toString());
            }
            _updateEarphoneVisable();
            this._deviceConfigManager.m351c();
            if (z) {
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put(PARAM_DEVICE, str);
                internalSendMessage(32786, hashMap);
                return;
            }
            String m341h = this._deviceConfigManager.m341h();
            if (m341h.equals(str) || m341h.equals(DEVICE_NONE)) {
                HashMap<String, Object> hashMap2 = new HashMap<>();
                hashMap2.put(PARAM_DEVICE, str);
                internalSendMessage(32787, hashMap2);
                return;
            }
            if (QLog.isColorLevel()) {
                QLog.m371w("TRAE", 2, " ---No switch,plugout:" + str + " connectedDev:" + m341h);
            }
            internalSendMessage(32785, new HashMap<>());
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.tencent.rtmp.sharp.jni.TraeAudioManager$f */
    /* loaded from: classes3.dex */
    public class C3721f extends Thread {

        /* renamed from: h */
        TraeAudioManager f5719h;

        /* renamed from: a */
        Handler f5712a = null;

        /* renamed from: b */
        TraeMediaPlayer f5713b = null;

        /* renamed from: c */
        long f5714c = -1;

        /* renamed from: d */
        String f5715d = "";

        /* renamed from: e */
        String f5716e = "";

        /* renamed from: f */
        final boolean[] f5717f = {false};

        /* renamed from: g */
        boolean f5718g = false;

        /* renamed from: i */
        String f5720i = "";

        /* renamed from: j */
        int f5721j = 0;

        /* renamed from: k */
        int f5722k = 0;

        /* renamed from: l */
        long f5723l = -1;

        /* renamed from: m */
        String f5724m = "";

        /* renamed from: n */
        AudioManager.OnAudioFocusChangeListener f5725n = null;

        /* renamed from: o */
        int f5726o = 0;

        public C3721f(TraeAudioManager traeAudioManager) {
            this.f5719h = null;
            this.f5719h = traeAudioManager;
            long elapsedRealtime = SystemClock.elapsedRealtime();
            if (QLog.isColorLevel()) {
                QLog.m376e("TRAE", 2, "TraeAudioManagerLooper start...");
            }
            start();
            synchronized (this.f5717f) {
                if (!this.f5717f[0]) {
                    try {
                        this.f5717f.wait();
                    } catch (InterruptedException unused) {
                    }
                }
            }
            if (QLog.isColorLevel()) {
                QLog.m376e("TRAE", 2, "  start used:" + (SystemClock.elapsedRealtime() - elapsedRealtime) + "ms");
            }
        }

        /* renamed from: a */
        public void m329a() {
            AudioDeviceInterface.LogTraceEntry("");
            if (this.f5712a == null) {
                return;
            }
            long elapsedRealtime = SystemClock.elapsedRealtime();
            this.f5712a.getLooper().quit();
            synchronized (this.f5717f) {
                if (this.f5717f[0]) {
                    try {
                        this.f5717f.wait(10000L);
                    } catch (InterruptedException unused) {
                    }
                }
            }
            if (QLog.isColorLevel()) {
                QLog.m376e("TRAE", 2, "  quit used:" + (SystemClock.elapsedRealtime() - elapsedRealtime) + "ms");
            }
            this.f5712a = null;
            AudioDeviceInterface.LogTraceExit();
        }

        /* renamed from: a */
        public int m327a(int i, HashMap<String, Object> hashMap) {
            Handler handler = this.f5712a;
            if (handler != null) {
                return this.f5712a.sendMessage(Message.obtain(handler, i, hashMap)) ? 0 : -1;
            }
            StringBuilder sb = new StringBuilder();
            sb.append(" fail mMsgHandler==null _enabled:");
            sb.append(this.f5718g ? "Y" : "N");
            sb.append(" activeMode:");
            sb.append(TraeAudioManager.this._activeMode);
            sb.append(" msg:");
            sb.append(i);
            AudioDeviceInterface.LogTraceEntry(sb.toString());
            return -1;
        }

        /* renamed from: a */
        void m326a(HashMap<String, Object> hashMap) {
            String str = (String) hashMap.get(TraeAudioManager.EXTRA_DATA_DEVICECONFIG);
            TXCLog.m2911w("TRAE", "startService cfg:" + str);
            StringBuilder sb = new StringBuilder();
            sb.append(" _enabled:");
            sb.append(this.f5718g ? "Y" : "N");
            sb.append(" activeMode:");
            sb.append(TraeAudioManager.this._activeMode);
            sb.append(" cfg:");
            sb.append(str);
            AudioDeviceInterface.LogTraceEntry(sb.toString());
            if (TraeAudioManager.this._context == null) {
                return;
            }
            QLog.m371w("TRAE", 2, "   startService:" + str);
            if ((this.f5718g && this.f5720i.equals(str)) || TraeAudioManager.this._activeMode != 0) {
                return;
            }
            if (this.f5718g) {
                m324b();
            }
            m319d();
            AudioManager audioManager = (AudioManager) TraeAudioManager.this._context.getSystemService("audio");
            TraeAudioManager.this._deviceConfigManager.m358a();
            TraeAudioManager.this._deviceConfigManager.m356a(str);
            this.f5720i = str;
            AudioManager audioManager2 = TraeAudioManager.this._am;
            if (audioManager2 != null) {
                this.f5721j = audioManager2.getMode();
            }
            this.f5718g = true;
            if (this.f5713b == null) {
                this.f5713b = new TraeMediaPlayer(TraeAudioManager.this._context, new TraeMediaPlayer.AbstractC3734a() { // from class: com.tencent.rtmp.sharp.jni.TraeAudioManager.f.1
                    @Override // com.tencent.rtmp.sharp.jni.TraeMediaPlayer.AbstractC3734a
                    /* renamed from: a */
                    public void mo286a() {
                        if (QLog.isColorLevel()) {
                            QLog.m371w("TRAE", 2, "_ringPlayer onCompletion _activeMode:" + TraeAudioManager.this._activeMode + " _preRingMode:" + C3721f.this.f5722k);
                        }
                        HashMap<String, Object> hashMap2 = new HashMap<>();
                        hashMap2.put(TraeAudioManager.PARAM_ISHOSTSIDE, true);
                        C3721f.this.m327a(32783, hashMap2);
                        C3721f.this.m309j();
                    }
                });
            }
            m325a(this.f5718g);
            TraeAudioManager.this.updateDeviceStatus();
            AudioDeviceInterface.LogTraceExit();
        }

        /* renamed from: b */
        void m324b() {
            StringBuilder sb = new StringBuilder();
            sb.append(" _enabled:");
            sb.append(this.f5718g ? "Y" : "N");
            sb.append(" activeMode:");
            sb.append(TraeAudioManager.this._activeMode);
            AudioDeviceInterface.LogTraceEntry(sb.toString());
            if (!this.f5718g) {
                return;
            }
            int i = TraeAudioManager.this._activeMode;
            if (i == 1) {
                m311h();
            } else if (i == 2) {
                m310i();
            }
            if (TraeAudioManager.this._switchThread != null) {
                if (QLog.isColorLevel()) {
                    QLog.m371w("TRAE", 2, "_switchThread:" + TraeAudioManager.this._switchThread.mo304b());
                }
                TraeAudioManager.this._switchThread.m301f();
                TraeAudioManager.this._switchThread = null;
            }
            TraeMediaPlayer traeMediaPlayer = this.f5713b;
            if (traeMediaPlayer != null) {
                traeMediaPlayer.stopRing();
            }
            this.f5713b = null;
            this.f5718g = false;
            m325a(this.f5718g);
            TraeAudioManager traeAudioManager = TraeAudioManager.this;
            if (traeAudioManager._am != null && traeAudioManager._context != null) {
                try {
                    traeAudioManager.InternalSetMode(0);
                } catch (Exception unused) {
                }
            }
            m317e();
            AudioDeviceInterface.LogTraceExit();
        }

        /* renamed from: a */
        int m325a(boolean z) {
            if (TraeAudioManager.this._context == null) {
                return -1;
            }
            Intent intent = new Intent();
            intent.setAction(TraeAudioManager.ACTION_TRAEAUDIOMANAGER_NOTIFY);
            intent.putExtra(TraeAudioManager.PARAM_OPERATION, TraeAudioManager.NOTIFY_SERVICE_STATE);
            intent.putExtra(TraeAudioManager.NOTIFY_SERVICE_STATE_DATE, z);
            Context context = TraeAudioManager.this._context;
            if (context == null) {
                return 0;
            }
            context.sendBroadcast(intent);
            return 0;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            AudioDeviceInterface.LogTraceEntry("");
            Looper.prepare();
            this.f5712a = new Handler() { // from class: com.tencent.rtmp.sharp.jni.TraeAudioManager.f.2
                /* JADX WARN: Multi-variable type inference failed */
                @Override // android.os.Handler
                public void handleMessage(Message message) {
                    HashMap hashMap;
                    try {
                        hashMap = (HashMap) message.obj;
                    } catch (Exception unused) {
                        hashMap = null;
                    }
                    if (QLog.isColorLevel()) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("TraeAudioManagerLooper msg:");
                        sb.append(message.what);
                        sb.append(" _enabled:");
                        sb.append(C3721f.this.f5718g ? "Y" : "N");
                        QLog.m371w("TRAE", 2, sb.toString());
                    }
                    int i = message.what;
                    if (i == 32772) {
                        C3721f.this.m326a(hashMap);
                        return;
                    }
                    C3721f c3721f = C3721f.this;
                    if (!c3721f.f5718g) {
                        if (QLog.isColorLevel()) {
                            QLog.m371w("TRAE", 2, "******* disabled ,skip msg******");
                        }
                        TraeAudioManager.this.sendResBroadcast(new Intent(), hashMap, 1);
                        return;
                    }
                    switch (i) {
                        case ExifInterface.DATA_PACK_BITS_COMPRESSED /* 32773 */:
                            c3721f.m324b();
                            return;
                        case 32774:
                            c3721f.m322b(hashMap);
                            return;
                        case 32775:
                            TraeAudioManager.this.InternalSessionConnectDevice(hashMap);
                            return;
                        case 32776:
                            TraeAudioManager.this.InternalSessionEarAction(hashMap);
                            return;
                        case 32777:
                            TraeAudioManager.this.InternalSessionIsDeviceChangabled(hashMap);
                            return;
                        case 32778:
                            TraeAudioManager.this.InternalSessionGetConnectedDevice(hashMap);
                            return;
                        case 32779:
                            TraeAudioManager.this.InternalSessionGetConnectingDevice(hashMap);
                            return;
                        case 32780:
                            c3721f.m320c(hashMap);
                            return;
                        case 32781:
                            c3721f.m318d(hashMap);
                            return;
                        case 32782:
                            c3721f.m316e(hashMap);
                            return;
                        case 32783:
                            c3721f.m314f(hashMap);
                            return;
                        case 32784:
                            c3721f.m312g(hashMap);
                            return;
                        case 32785:
                        case 32789:
                            String m345f = TraeAudioManager.this._deviceConfigManager.m345f();
                            String m341h = TraeAudioManager.this._deviceConfigManager.m341h();
                            if (QLog.isColorLevel()) {
                                QLog.m371w("TRAE", 2, "MESSAGE_AUTO_DEVICELIST_UPDATE  connectedDev:" + m341h + " highestDev" + m345f);
                            }
                            if (TraeAudioManager.IsUpdateSceneFlag) {
                                if (TraeAudioManager.IsMusicScene) {
                                    TraeAudioManager traeAudioManager = TraeAudioManager.this;
                                    if (!traeAudioManager.IsBluetoothA2dpExisted) {
                                        traeAudioManager.InternalConnectDevice(traeAudioManager._deviceConfigManager.m346e(TraeAudioManager.DEVICE_BLUETOOTHHEADSET), null, true);
                                        return;
                                    }
                                }
                                TraeAudioManager.this.InternalConnectDevice(m345f, null, true);
                                return;
                            } else if (!m345f.equals(m341h)) {
                                TraeAudioManager.this.InternalConnectDevice(m345f, null, false);
                                return;
                            } else {
                                TraeAudioManager.this.InternalNotifyDeviceListUpdate();
                                return;
                            }
                        case 32786:
                            String str = (String) hashMap.get(TraeAudioManager.PARAM_DEVICE);
                            if (TraeAudioManager.this.InternalConnectDevice(str, null, false) == 0) {
                                return;
                            }
                            if (QLog.isColorLevel()) {
                                QLog.m371w("TRAE", 2, " plugin dev:" + str + " sessionConnectedDev:" + TraeAudioManager.this.sessionConnectedDev + " connected fail,auto switch!");
                            }
                            TraeAudioManager traeAudioManager2 = TraeAudioManager.this;
                            traeAudioManager2.InternalConnectDevice(traeAudioManager2._deviceConfigManager.m345f(), null, false);
                            return;
                        case 32787:
                            TraeAudioManager traeAudioManager3 = TraeAudioManager.this;
                            if (traeAudioManager3.InternalConnectDevice(traeAudioManager3.sessionConnectedDev, null, false) == 0) {
                                return;
                            }
                            String str2 = (String) hashMap.get(TraeAudioManager.PARAM_DEVICE);
                            if (QLog.isColorLevel()) {
                                QLog.m371w("TRAE", 2, " plugout dev:" + str2 + " sessionConnectedDev:" + TraeAudioManager.this.sessionConnectedDev + " connected fail,auto switch!");
                            }
                            TraeAudioManager traeAudioManager4 = TraeAudioManager.this;
                            traeAudioManager4.InternalConnectDevice(traeAudioManager4._deviceConfigManager.m345f(), null, false);
                            return;
                        case 32788:
                            Integer num = (Integer) hashMap.get(TraeAudioManager.PARAM_STREAMTYPE);
                            if (num == null) {
                                if (!QLog.isColorLevel()) {
                                    return;
                                }
                                QLog.m376e("TRAE", 2, " MESSAGE_VOICECALL_AUIDOPARAM_CHANGED params.get(PARAM_STREAMTYPE)==null!!");
                                return;
                            }
                            TraeAudioManager.this._streamType = num.intValue();
                            C3721f.this.m323b(num.intValue());
                            return;
                        case 32790:
                            c3721f.m313g();
                            return;
                        case 32791:
                            c3721f.m328a(TraeAudioManager.this._streamType);
                            return;
                        default:
                            return;
                    }
                }
            };
            m321c();
            synchronized (this.f5717f) {
                this.f5717f[0] = true;
                this.f5717f.notify();
            }
            Looper.loop();
            m315f();
            synchronized (this.f5717f) {
                this.f5717f[0] = false;
                this.f5717f.notify();
            }
            AudioDeviceInterface.LogTraceExit();
        }

        /* renamed from: c */
        void m321c() {
            AudioDeviceInterface.LogTraceEntry("");
            try {
                TraeAudioManager.this._audioSessionHost = new TraeAudioSessionHost();
                TraeAudioManager.this._deviceConfigManager = new C3719e();
                TraeAudioManager._gHostProcessId = Process.myPid();
                TraeAudioManager.this._am = (AudioManager) TraeAudioManager.this._context.getSystemService("audio");
                TraeAudioManager.this._bluetoothCheck = TraeAudioManager.this.CreateBluetoothCheck(TraeAudioManager.this._context, TraeAudioManager.this._deviceConfigManager);
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("android.intent.action.HEADSET_PLUG");
                intentFilter.addAction("android.media.AUDIO_BECOMING_NOISY");
                TraeAudioManager.this._bluetoothCheck.m361b(intentFilter);
                intentFilter.addAction(TraeAudioManager.ACTION_TRAEAUDIOMANAGER_REQUEST);
                TraeAudioManager.this._context.registerReceiver(this.f5719h, intentFilter);
            } catch (Exception unused) {
                if (QLog.isColorLevel()) {
                    QLog.m371w("TRAE", 2, "======7");
                }
            }
            AudioDeviceInterface.LogTraceExit();
        }

        /* renamed from: d */
        void m319d() {
            try {
                TraeAudioManager.this._am = (AudioManager) TraeAudioManager.this._context.getSystemService("audio");
                if (TraeAudioManager.this._bluetoothCheck == null) {
                    TraeAudioManager.this._bluetoothCheck = TraeAudioManager.this.CreateBluetoothCheck(TraeAudioManager.this._context, TraeAudioManager.this._deviceConfigManager);
                }
                TraeAudioManager.this._context.unregisterReceiver(this.f5719h);
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("android.intent.action.HEADSET_PLUG");
                intentFilter.addAction("android.media.AUDIO_BECOMING_NOISY");
                TraeAudioManager.this._bluetoothCheck.m361b(intentFilter);
                intentFilter.addAction(TraeAudioManager.ACTION_TRAEAUDIOMANAGER_REQUEST);
                TraeAudioManager.this._context.registerReceiver(this.f5719h, intentFilter);
            } catch (Exception unused) {
            }
        }

        /* renamed from: e */
        void m317e() {
            try {
                if (TraeAudioManager.this._bluetoothCheck != null) {
                    TraeAudioManager.this._bluetoothCheck.mo369a();
                }
                TraeAudioManager.this._bluetoothCheck = null;
                if (TraeAudioManager.this._context == null) {
                    return;
                }
                TraeAudioManager.this._context.unregisterReceiver(this.f5719h);
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction(TraeAudioManager.ACTION_TRAEAUDIOMANAGER_REQUEST);
                TraeAudioManager.this._context.registerReceiver(this.f5719h, intentFilter);
            } catch (Exception unused) {
            }
        }

        /* renamed from: f */
        void m315f() {
            AudioDeviceInterface.LogTraceEntry("");
            try {
                m324b();
                if (TraeAudioManager.this._bluetoothCheck != null) {
                    TraeAudioManager.this._bluetoothCheck.mo369a();
                }
                TraeAudioManager.this._bluetoothCheck = null;
                if (TraeAudioManager.this._context != null) {
                    TraeAudioManager.this._context.unregisterReceiver(this.f5719h);
                    TraeAudioManager.this._context = null;
                }
                if (TraeAudioManager.this._deviceConfigManager != null) {
                    TraeAudioManager.this._deviceConfigManager.m358a();
                }
                TraeAudioManager.this._deviceConfigManager = null;
            } catch (Exception unused) {
            }
            AudioDeviceInterface.LogTraceExit();
        }

        /* renamed from: b */
        int m322b(HashMap<String, Object> hashMap) {
            Intent intent = new Intent();
            HashMap<String, Object> m339j = TraeAudioManager.this._deviceConfigManager.m339j();
            intent.putExtra(TraeAudioManager.EXTRA_DATA_AVAILABLEDEVICE_LIST, (String[]) ((ArrayList) m339j.get(TraeAudioManager.EXTRA_DATA_AVAILABLEDEVICE_LIST)).toArray(new String[0]));
            intent.putExtra(TraeAudioManager.EXTRA_DATA_CONNECTEDDEVICE, (String) m339j.get(TraeAudioManager.EXTRA_DATA_CONNECTEDDEVICE));
            intent.putExtra(TraeAudioManager.EXTRA_DATA_PREV_CONNECTEDDEVICE, (String) m339j.get(TraeAudioManager.EXTRA_DATA_PREV_CONNECTEDDEVICE));
            intent.putExtra(TraeAudioManager.EXTRA_DATA_IF_HAS_BLUETOOTH_THIS_IS_NAME, TraeAudioManager.this._deviceConfigManager.m349d());
            TraeAudioManager.this.sendResBroadcast(intent, hashMap, 0);
            return 0;
        }

        @TargetApi(8)
        /* renamed from: a */
        void m328a(int i) {
            if (Build.VERSION.SDK_INT > 8 && this.f5725n == null) {
                this.f5725n = new AudioManager.OnAudioFocusChangeListener() { // from class: com.tencent.rtmp.sharp.jni.TraeAudioManager.f.3
                    @Override // android.media.AudioManager.OnAudioFocusChangeListener
                    @TargetApi(8)
                    public void onAudioFocusChange(int i2) {
                        if (QLog.isColorLevel()) {
                            QLog.m371w("TRAE", 2, "focusChange:" + i2 + " _focusSteamType:" + C3721f.this.f5726o + " currMode:" + TraeAudioManager.this._am.getMode() + " _activeMode:" + TraeAudioManager.this._activeMode);
                        }
                        if (i2 != -1 && i2 == -2) {
                        }
                    }
                };
                AudioManager audioManager = TraeAudioManager.this._am;
                if (audioManager == null) {
                    return;
                }
                int requestAudioFocus = audioManager.requestAudioFocus(this.f5725n, i, 1);
                if (requestAudioFocus != 1 && QLog.isColorLevel()) {
                    QLog.m376e("TRAE", 2, "request audio focus fail. " + requestAudioFocus + " mode:" + TraeAudioManager.this._am.getMode());
                }
                this.f5726o = i;
                if (!QLog.isColorLevel()) {
                    return;
                }
                QLog.m371w("TRAE", 2, "-------requestAudioFocus _focusSteamType:" + this.f5726o);
            }
        }

        @TargetApi(8)
        /* renamed from: g */
        void m313g() {
            if (Build.VERSION.SDK_INT <= 8 || TraeAudioManager.this._am == null || this.f5725n == null) {
                return;
            }
            if (QLog.isColorLevel()) {
                QLog.m371w("TRAE", 2, "-------abandonAudioFocus _focusSteamType:" + this.f5726o);
            }
            TraeAudioManager.this._am.abandonAudioFocus(this.f5725n);
            this.f5725n = null;
        }

        /* renamed from: c */
        int m320c(HashMap<String, Object> hashMap) {
            C3719e c3719e;
            AudioDeviceInterface.LogTraceEntry(" activeMode:" + TraeAudioManager.this._activeMode);
            if (hashMap == null) {
                return -1;
            }
            TraeAudioManager traeAudioManager = TraeAudioManager.this;
            if (traeAudioManager._am == null) {
                if (QLog.isColorLevel()) {
                    QLog.m376e("TRAE", 2, " InternalVoicecallPreprocess am==null!!");
                }
                return -1;
            } else if (traeAudioManager._activeMode == 1) {
                TraeAudioManager.this.sendResBroadcast(new Intent(), hashMap, 2);
                return -1;
            } else {
                this.f5723l = ((Long) hashMap.get(TraeAudioManager.PARAM_SESSIONID)).longValue();
                this.f5724m = (String) hashMap.get(TraeAudioManager.PARAM_OPERATION);
                TraeAudioManager traeAudioManager2 = TraeAudioManager.this;
                traeAudioManager2._activeMode = 1;
                traeAudioManager2._prevMode = traeAudioManager2._am.getMode();
                Integer.valueOf(-1);
                Integer.valueOf(0);
                Integer num = (Integer) hashMap.get(TraeAudioManager.PARAM_MODEPOLICY);
                if (num == null) {
                    if (QLog.isColorLevel()) {
                        QLog.m376e("TRAE", 2, " params.get(PARAM_MODEPOLICY)==null!!");
                    }
                    TraeAudioManager.this._modePolicy = -1;
                } else {
                    TraeAudioManager.this._modePolicy = num.intValue();
                }
                if (QLog.isColorLevel()) {
                    QLog.m376e("TRAE", 2, "  _modePolicy:" + TraeAudioManager.this._modePolicy);
                }
                Integer num2 = (Integer) hashMap.get(TraeAudioManager.PARAM_STREAMTYPE);
                if (num2 == null) {
                    if (QLog.isColorLevel()) {
                        QLog.m376e("TRAE", 2, " params.get(PARAM_STREAMTYPE)==null!!");
                    }
                    TraeAudioManager.this._streamType = 0;
                } else {
                    TraeAudioManager.this._streamType = num2.intValue();
                }
                if (TraeAudioManager.isCloseSystemAPM(TraeAudioManager.this._modePolicy)) {
                    TraeAudioManager traeAudioManager3 = TraeAudioManager.this;
                    if (traeAudioManager3._activeMode != 2 && (c3719e = traeAudioManager3._deviceConfigManager) != null) {
                        if (c3719e.m341h().equals(TraeAudioManager.DEVICE_SPEAKERPHONE)) {
                            TraeAudioManager.this.InternalSetMode(0);
                            m328a(3);
                        } else {
                            TraeAudioManager.this.InternalSetMode(3);
                            m328a(0);
                        }
                        TraeAudioManager.this.sendResBroadcast(new Intent(), hashMap, 0);
                        AudioDeviceInterface.LogTraceExit();
                        return 0;
                    }
                }
                TraeAudioManager traeAudioManager4 = TraeAudioManager.this;
                traeAudioManager4.InternalSetMode(TraeAudioManager.getCallAudioMode(traeAudioManager4._modePolicy));
                m328a(TraeAudioManager.this._streamType);
                TraeAudioManager.this.sendResBroadcast(new Intent(), hashMap, 0);
                AudioDeviceInterface.LogTraceExit();
                return 0;
            }
        }

        /* renamed from: d */
        int m318d(HashMap<String, Object> hashMap) {
            AudioDeviceInterface.LogTraceEntry(" activeMode:" + TraeAudioManager.this._activeMode);
            TraeAudioManager traeAudioManager = TraeAudioManager.this;
            if (traeAudioManager._am == null) {
                if (QLog.isColorLevel()) {
                    QLog.m376e("TRAE", 2, " InternalVoicecallPostprocess am==null!!");
                }
                return -1;
            } else if (traeAudioManager._activeMode != 1) {
                if (QLog.isColorLevel()) {
                    QLog.m376e("TRAE", 2, " not ACTIVE_VOICECALL!!");
                }
                TraeAudioManager.this.sendResBroadcast(new Intent(), hashMap, 3);
                return -1;
            } else {
                traeAudioManager._activeMode = 0;
                m313g();
                AudioDeviceInterface.LogTraceExit();
                return 0;
            }
        }

        /* renamed from: h */
        int m311h() {
            AudioDeviceInterface.LogTraceEntry(" activeMode:" + TraeAudioManager.this._activeMode);
            TraeAudioManager traeAudioManager = TraeAudioManager.this;
            if (traeAudioManager._am == null) {
                if (QLog.isColorLevel()) {
                    QLog.m376e("TRAE", 2, " am==null!!");
                }
                return -1;
            } else if (traeAudioManager._activeMode != 1) {
                if (QLog.isColorLevel()) {
                    QLog.m376e("TRAE", 2, " not ACTIVE_RING!!");
                }
                return -1;
            } else {
                traeAudioManager._activeMode = 0;
                int i = traeAudioManager._prevMode;
                if (i != -1) {
                    traeAudioManager.InternalSetMode(i);
                }
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put(TraeAudioManager.PARAM_SESSIONID, Long.valueOf(this.f5723l));
                hashMap.put(TraeAudioManager.PARAM_OPERATION, this.f5724m);
                TraeAudioManager.this.sendResBroadcast(new Intent(), hashMap, 6);
                AudioDeviceInterface.LogTraceExit();
                return 0;
            }
        }

        /* renamed from: e */
        int m316e(HashMap<String, Object> hashMap) {
            AudioDeviceInterface.LogTraceEntry(" activeMode:" + TraeAudioManager.this._activeMode);
            TraeAudioManager traeAudioManager = TraeAudioManager.this;
            if (traeAudioManager._am == null) {
                if (QLog.isColorLevel()) {
                    QLog.m376e("TRAE", 2, " InternalStartRing am==null!!");
                }
                return -1;
            }
            if (traeAudioManager._activeMode == 2) {
                m310i();
            }
            try {
                this.f5714c = ((Long) hashMap.get(TraeAudioManager.PARAM_SESSIONID)).longValue();
                this.f5715d = (String) hashMap.get(TraeAudioManager.PARAM_OPERATION);
                this.f5716e = (String) hashMap.get(TraeAudioManager.PARAM_RING_USERDATA_STRING);
                int intValue = ((Integer) hashMap.get(TraeAudioManager.PARAM_RING_DATASOURCE)).intValue();
                if (QLog.isColorLevel()) {
                    QLog.m371w("TRAE", 2, "  dataSource:" + intValue);
                }
                int intValue2 = ((Integer) hashMap.get(TraeAudioManager.PARAM_RING_RSID)).intValue();
                Uri uri = (Uri) hashMap.get(TraeAudioManager.PARAM_RING_URI);
                String str = (String) hashMap.get(TraeAudioManager.PARAM_RING_FILEPATH);
                boolean booleanValue = ((Boolean) hashMap.get(TraeAudioManager.PARAM_RING_LOOP)).booleanValue();
                int intValue3 = ((Integer) hashMap.get(TraeAudioManager.PARAM_RING_LOOPCOUNT)).intValue();
                boolean booleanValue2 = ((Boolean) hashMap.get(TraeAudioManager.PARAM_RING_MODE)).booleanValue();
                TraeAudioManager traeAudioManager2 = TraeAudioManager.this;
                if (traeAudioManager2._activeMode != 1) {
                    traeAudioManager2._activeMode = 2;
                }
                Intent intent = new Intent();
                intent.putExtra(TraeAudioManager.PARAM_RING_USERDATA_STRING, this.f5716e);
                TraeAudioManager.this.sendResBroadcast(intent, hashMap, 0);
                this.f5722k = TraeAudioManager.this._am.getMode();
                this.f5713b.playRing(intValue, intValue2, uri, str, booleanValue, intValue3, booleanValue2, TraeAudioManager.this._activeMode == 1, TraeAudioManager.this._streamType);
                if (QLog.isColorLevel()) {
                    QLog.m371w("TRAE", 2, " _ringUserdata:" + this.f5716e + " DurationMS:" + this.f5713b.getDuration());
                }
                if (!this.f5713b.hasCall()) {
                    m328a(this.f5713b.getStreamType());
                }
                m323b(this.f5713b.getStreamType());
                AudioDeviceInterface.LogTraceExit();
                return 0;
            } catch (Exception unused) {
                if (QLog.isColorLevel()) {
                    QLog.m376e("TRAE", 2, " startRing err params");
                }
                return -1;
            }
        }

        /* renamed from: f */
        int m314f(HashMap<String, Object> hashMap) {
            TraeMediaPlayer traeMediaPlayer;
            AudioDeviceInterface.LogTraceEntry(" activeMode:" + TraeAudioManager.this._activeMode + " _preRingMode:" + this.f5722k);
            if (TraeAudioManager.this._am == null || (traeMediaPlayer = this.f5713b) == null) {
                if (!QLog.isColorLevel()) {
                    return -1;
                }
                QLog.m376e("TRAE", 2, " InternalStopRing am==null!!");
                return -1;
            }
            traeMediaPlayer.stopRing();
            if (!this.f5713b.hasCall() && TraeAudioManager.this._activeMode == 2) {
                m313g();
                TraeAudioManager.this._activeMode = 0;
            }
            Intent intent = new Intent();
            intent.putExtra(TraeAudioManager.PARAM_RING_USERDATA_STRING, this.f5716e);
            TraeAudioManager.this.sendResBroadcast(intent, hashMap, 0);
            AudioDeviceInterface.LogTraceExit();
            return 0;
        }

        /* renamed from: g */
        int m312g(HashMap<String, Object> hashMap) {
            int i;
            AudioDeviceInterface.LogTraceEntry(" activeMode:" + TraeAudioManager.this._activeMode + " _preRingMode:" + this.f5722k);
            TraeAudioManager traeAudioManager = TraeAudioManager.this;
            if (traeAudioManager._am == null) {
                if (!QLog.isColorLevel()) {
                    return -1;
                }
                QLog.m376e("TRAE", 2, " InternalStopRing am==null!!");
                return -1;
            }
            if (traeAudioManager._activeMode == 2) {
                i = this.f5713b.getStreamType();
            } else {
                i = traeAudioManager._streamType;
            }
            Intent intent = new Intent();
            intent.putExtra(TraeAudioManager.EXTRA_DATA_STREAMTYPE, i);
            TraeAudioManager.this.sendResBroadcast(intent, hashMap, 0);
            AudioDeviceInterface.LogTraceExit();
            return 0;
        }

        /* renamed from: b */
        int m323b(final int i) {
            if (TraeAudioManager.this._context == null) {
                return -1;
            }
            new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.tencent.rtmp.sharp.jni.TraeAudioManager.f.4
                @Override // java.lang.Runnable
                public void run() {
                    Intent intent = new Intent();
                    intent.setAction(TraeAudioManager.ACTION_TRAEAUDIOMANAGER_NOTIFY);
                    intent.putExtra(TraeAudioManager.PARAM_OPERATION, TraeAudioManager.NOTIFY_STREAMTYPE_UPDATE);
                    intent.putExtra(TraeAudioManager.EXTRA_DATA_STREAMTYPE, i);
                    Context context = TraeAudioManager.this._context;
                    if (context != null) {
                        context.sendBroadcast(intent);
                    }
                }
            });
            return 0;
        }

        /* renamed from: i */
        int m310i() {
            AudioDeviceInterface.LogTraceEntry(" activeMode:" + TraeAudioManager.this._activeMode + " _preRingMode:" + this.f5722k);
            TraeAudioManager traeAudioManager = TraeAudioManager.this;
            if (traeAudioManager._am == null) {
                if (QLog.isColorLevel()) {
                    QLog.m376e("TRAE", 2, " interruptRing am==null!!");
                }
                return -1;
            } else if (traeAudioManager._activeMode != 2) {
                if (QLog.isColorLevel()) {
                    QLog.m376e("TRAE", 2, " not ACTIVE_RING!!");
                }
                return -1;
            } else {
                this.f5713b.stopRing();
                m313g();
                TraeAudioManager.this._activeMode = 0;
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put(TraeAudioManager.PARAM_SESSIONID, Long.valueOf(this.f5714c));
                hashMap.put(TraeAudioManager.PARAM_OPERATION, this.f5715d);
                Intent intent = new Intent();
                intent.putExtra(TraeAudioManager.PARAM_RING_USERDATA_STRING, this.f5716e);
                TraeAudioManager.this.sendResBroadcast(intent, hashMap, 4);
                AudioDeviceInterface.LogTraceExit();
                return 0;
            }
        }

        /* renamed from: j */
        void m309j() {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put(TraeAudioManager.PARAM_SESSIONID, Long.valueOf(this.f5714c));
            hashMap.put(TraeAudioManager.PARAM_OPERATION, TraeAudioManager.NOTIFY_RING_COMPLETION);
            Intent intent = new Intent();
            intent.putExtra(TraeAudioManager.PARAM_RING_USERDATA_STRING, this.f5716e);
            TraeAudioManager.this.sendResBroadcast(intent, hashMap, 0);
        }
    }

    public static int SetSpeakerForTest(Context context, boolean z) {
        int i;
        _glock.lock();
        TraeAudioManager traeAudioManager = _ginstance;
        if (traeAudioManager != null) {
            i = traeAudioManager.InternalSetSpeaker(context, z);
        } else {
            if (QLog.isColorLevel()) {
                QLog.m371w("TRAE", 2, "TraeAudioManager|static SetSpeakerForTest|null == _ginstance");
            }
            i = -1;
        }
        _glock.unlock();
        return i;
    }

    int InternalSetSpeaker(Context context, boolean z) {
        int i = -1;
        if (context == null) {
            if (QLog.isColorLevel()) {
                QLog.m376e("TRAE", 2, "Could not InternalSetSpeaker - no context");
            }
            return -1;
        }
        AudioManager audioManager = (AudioManager) context.getSystemService("audio");
        if (audioManager == null) {
            if (QLog.isColorLevel()) {
                QLog.m376e("TRAE", 2, "Could not InternalSetSpeaker - no audio manager");
            }
            return -1;
        }
        if (QLog.isColorLevel()) {
            StringBuilder sb = new StringBuilder();
            sb.append("InternalSetSpeaker entry:speaker:");
            String str = "Y";
            sb.append(audioManager.isSpeakerphoneOn() ? str : "N");
            sb.append("-->:");
            if (!z) {
                str = "N";
            }
            sb.append(str);
            QLog.m371w("TRAE", 2, sb.toString());
        }
        if (isCloseSystemAPM(this._modePolicy) && this._activeMode != 2) {
            return InternalSetSpeakerSpe(audioManager, z);
        }
        if (audioManager.isSpeakerphoneOn() != z) {
            audioManager.setSpeakerphoneOn(z);
        }
        if (audioManager.isSpeakerphoneOn() == z) {
            i = 0;
        }
        if (QLog.isColorLevel()) {
            QLog.m371w("TRAE", 2, "InternalSetSpeaker exit:" + z + " res:" + i + " mode:" + audioManager.getMode());
        }
        return i;
    }

    int InternalSetSpeakerSpe(AudioManager audioManager, boolean z) {
        if (QLog.isColorLevel()) {
            QLog.m371w("TRAE", 2, "InternalSetSpeakerSpe fac:" + Build.MANUFACTURER + " model:" + Build.MODEL + " st:" + this._streamType + " media_force_use:" + getForceUse(1));
        }
        int i = 0;
        if (z) {
            InternalSetMode(0);
            audioManager.setSpeakerphoneOn(true);
            setForceUse(1, 1);
        } else {
            InternalSetMode(3);
            audioManager.setSpeakerphoneOn(false);
            setForceUse(1, 0);
        }
        if (audioManager.isSpeakerphoneOn() != z) {
            i = -1;
        }
        if (QLog.isColorLevel()) {
            QLog.m371w("TRAE", 2, "InternalSetSpeakerSpe exit:" + z + " res:" + i + " mode:" + audioManager.getMode());
        }
        return i;
    }

    void InternalSetMode(int i) {
        if (QLog.isColorLevel()) {
            QLog.m371w("TRAE", 2, "SetMode entry:" + i);
        }
        AudioManager audioManager = this._am;
        if (audioManager == null) {
            if (!QLog.isColorLevel()) {
                return;
            }
            QLog.m371w("TRAE", 2, "setMode:" + i + " fail am=null");
            return;
        }
        audioManager.setMode(i);
        if (!QLog.isColorLevel()) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("setMode:");
        sb.append(i);
        sb.append(this._am.getMode() != i ? "fail" : "success");
        QLog.m371w("TRAE", 2, sb.toString());
    }

    public static int registerAudioSession(boolean z, long j, Context context) {
        int i;
        _glock.lock();
        TraeAudioManager traeAudioManager = _ginstance;
        if (traeAudioManager != null) {
            if (z) {
                traeAudioManager._audioSessionHost.add(j, context);
            } else {
                traeAudioManager._audioSessionHost.remove(j);
            }
            i = 0;
        } else {
            i = -1;
        }
        _glock.unlock();
        return i;
    }

    public static int sendMessage(int i, HashMap<String, Object> hashMap) {
        _glock.lock();
        TraeAudioManager traeAudioManager = _ginstance;
        int internalSendMessage = traeAudioManager != null ? traeAudioManager.internalSendMessage(i, hashMap) : -1;
        _glock.unlock();
        return internalSendMessage;
    }

    public static int init(Context context) {
        TXCLog.m2911w("TRAE", "TraeAudioManager init _ginstance:" + _ginstance);
        AudioDeviceInterface.LogTraceEntry(" _ginstance:" + _ginstance);
        _glock.lock();
        if (_ginstance == null) {
            _ginstance = new TraeAudioManager(context);
        }
        _glock.unlock();
        AudioDeviceInterface.LogTraceExit();
        return 0;
    }

    public static void uninit() {
        TXCLog.m2911w("TRAE", "TraeAudioManager uninit _ginstance:" + _ginstance);
        AudioDeviceInterface.LogTraceEntry(" _ginstance:" + _ginstance);
        _glock.lock();
        TraeAudioManager traeAudioManager = _ginstance;
        if (traeAudioManager != null) {
            traeAudioManager.release();
            _ginstance = null;
        }
        _glock.unlock();
        AudioDeviceInterface.LogTraceExit();
    }

    TraeAudioManager(Context context) {
        this._context = null;
        this.mTraeAudioManagerLooper = null;
        AudioDeviceInterface.LogTraceEntry(" context:" + context);
        if (context == null) {
            return;
        }
        this._context = context;
        this.mTraeAudioManagerLooper = new C3721f(this);
        AudioDeviceInterface.LogTraceExit();
    }

    public void release() {
        AudioDeviceInterface.LogTraceEntry("");
        C3721f c3721f = this.mTraeAudioManagerLooper;
        if (c3721f != null) {
            c3721f.m329a();
            this.mTraeAudioManagerLooper = null;
        }
        AudioDeviceInterface.LogTraceExit();
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (intent == null || context == null) {
            if (!QLog.isColorLevel()) {
                return;
            }
            QLog.m378d("TRAE", 2, "onReceive intent or context is null!");
            return;
        }
        try {
            String action = intent.getAction();
            String stringExtra = intent.getStringExtra(PARAM_OPERATION);
            if (QLog.isColorLevel()) {
                QLog.m371w("TRAE", 2, "TraeAudioManager|onReceive::Action:" + intent.getAction());
            }
            if (this._deviceConfigManager == null) {
                if (!QLog.isColorLevel()) {
                    return;
                }
                QLog.m378d("TRAE", 2, "_deviceConfigManager null!");
                return;
            }
            boolean m350c = this._deviceConfigManager.m350c(DEVICE_WIREDHEADSET);
            boolean m350c2 = this._deviceConfigManager.m350c(DEVICE_BLUETOOTHHEADSET);
            if ("android.intent.action.HEADSET_PLUG".equals(intent.getAction())) {
                onHeadsetPlug(context, intent);
                if (!m350c && this._deviceConfigManager.m350c(DEVICE_WIREDHEADSET)) {
                    checkDevicePlug(DEVICE_WIREDHEADSET, true);
                }
                if (!m350c || this._deviceConfigManager.m350c(DEVICE_WIREDHEADSET)) {
                    return;
                }
                checkDevicePlug(DEVICE_WIREDHEADSET, false);
            } else if ("android.media.AUDIO_BECOMING_NOISY".equals(intent.getAction())) {
            } else {
                if (ACTION_TRAEAUDIOMANAGER_REQUEST.equals(action)) {
                    if (QLog.isColorLevel()) {
                        QLog.m371w("TRAE", 2, "   OPERATION:" + stringExtra);
                    }
                    if (OPERATION_REGISTERAUDIOSESSION.equals(stringExtra)) {
                        registerAudioSession(intent.getBooleanExtra(REGISTERAUDIOSESSION_ISREGISTER, false), intent.getLongExtra(PARAM_SESSIONID, Long.MIN_VALUE), context);
                    } else if (OPERATION_STARTSERVICE.equals(stringExtra)) {
                        startService(stringExtra, intent.getLongExtra(PARAM_SESSIONID, Long.MIN_VALUE), false, intent.getStringExtra(EXTRA_DATA_DEVICECONFIG));
                    } else if (OPERATION_STOPSERVICE.equals(stringExtra)) {
                        stopService(stringExtra, intent.getLongExtra(PARAM_SESSIONID, Long.MIN_VALUE), false);
                    } else if (OPERATION_GETDEVICELIST.equals(stringExtra)) {
                        getDeviceList(stringExtra, intent.getLongExtra(PARAM_SESSIONID, Long.MIN_VALUE), false);
                    } else if (OPERATION_GETSTREAMTYPE.equals(stringExtra)) {
                        getStreamType(stringExtra, intent.getLongExtra(PARAM_SESSIONID, Long.MIN_VALUE), false);
                    } else if (OPERATION_CONNECTDEVICE.equals(stringExtra)) {
                        connectDevice(stringExtra, intent.getLongExtra(PARAM_SESSIONID, Long.MIN_VALUE), false, intent.getStringExtra(CONNECTDEVICE_DEVICENAME));
                    } else if (OPERATION_CONNECT_HIGHEST_PRIORITY_DEVICE.equals(stringExtra)) {
                        connectHighestPriorityDevice(stringExtra, intent.getLongExtra(PARAM_SESSIONID, Long.MIN_VALUE), false);
                    } else if (OPERATION_EARACTION.equals(stringExtra)) {
                        earAction(stringExtra, intent.getLongExtra(PARAM_SESSIONID, Long.MIN_VALUE), false, intent.getIntExtra(EXTRA_EARACTION, -1));
                    } else if (OPERATION_ISDEVICECHANGABLED.equals(stringExtra)) {
                        isDeviceChangabled(stringExtra, intent.getLongExtra(PARAM_SESSIONID, Long.MIN_VALUE), false);
                    } else if (OPERATION_GETCONNECTEDDEVICE.equals(stringExtra)) {
                        getConnectedDevice(stringExtra, intent.getLongExtra(PARAM_SESSIONID, Long.MIN_VALUE), false);
                    } else if (OPERATION_GETCONNECTINGDEVICE.equals(stringExtra)) {
                        getConnectingDevice(stringExtra, intent.getLongExtra(PARAM_SESSIONID, Long.MIN_VALUE), false);
                    } else if (OPERATION_VOICECALL_PREPROCESS.equals(stringExtra)) {
                        voicecallPreprocess(stringExtra, intent.getLongExtra(PARAM_SESSIONID, Long.MIN_VALUE), false, intent.getIntExtra(PARAM_MODEPOLICY, -1), intent.getIntExtra(PARAM_STREAMTYPE, -1));
                    } else if (OPERATION_VOICECALL_POSTPROCESS.equals(stringExtra)) {
                        voicecallPostprocess(stringExtra, intent.getLongExtra(PARAM_SESSIONID, Long.MIN_VALUE), false);
                    } else if (OPERATION_VOICECALL_AUDIOPARAM_CHANGED.equals(stringExtra)) {
                        voiceCallAudioParamChanged(stringExtra, intent.getLongExtra(PARAM_SESSIONID, Long.MIN_VALUE), false, intent.getIntExtra(PARAM_MODEPOLICY, -1), intent.getIntExtra(PARAM_STREAMTYPE, -1));
                    } else if (OPERATION_STARTRING.equals(stringExtra)) {
                        String stringExtra2 = intent.getStringExtra(PARAM_RING_FILEPATH);
                        boolean booleanExtra = intent.getBooleanExtra(PARAM_RING_LOOP, false);
                        String stringExtra3 = intent.getStringExtra(PARAM_RING_USERDATA_STRING);
                        int intExtra = intent.getIntExtra(PARAM_RING_LOOPCOUNT, 1);
                        boolean booleanExtra2 = intent.getBooleanExtra(PARAM_RING_MODE, false);
                        startRing(stringExtra, intent.getLongExtra(PARAM_SESSIONID, Long.MIN_VALUE), false, intent.getIntExtra(PARAM_RING_DATASOURCE, -1), intent.getIntExtra(PARAM_RING_RSID, -1), (Uri) intent.getParcelableExtra(PARAM_RING_URI), stringExtra2, booleanExtra, intExtra, stringExtra3, booleanExtra2);
                    } else if (!OPERATION_STOPRING.equals(stringExtra)) {
                    } else {
                        stopRing(stringExtra, intent.getLongExtra(PARAM_SESSIONID, Long.MIN_VALUE), false);
                    }
                } else if (this._deviceConfigManager == null) {
                } else {
                    if (this._bluetoothCheck != null) {
                        this._bluetoothCheck.m366a(context, intent, this._deviceConfigManager);
                    }
                    if (!m350c2 && this._deviceConfigManager.m350c(DEVICE_BLUETOOTHHEADSET)) {
                        checkDevicePlug(DEVICE_BLUETOOTHHEADSET, true);
                    }
                    if (!m350c2 || this._deviceConfigManager.m350c(DEVICE_BLUETOOTHHEADSET)) {
                        return;
                    }
                    checkDevicePlug(DEVICE_BLUETOOTHHEADSET, false);
                }
            }
        } catch (Exception unused) {
        }
    }

    void onHeadsetPlug(Context context, Intent intent) {
        String stringExtra = intent.getStringExtra("name");
        if (stringExtra == null) {
            stringExtra = "unkonw";
        }
        String str = " [" + stringExtra + "] ";
        int intExtra = intent.getIntExtra("state", -1);
        if (intExtra != -1) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(intExtra == 0 ? "unplugged" : "plugged");
            str = sb.toString();
        }
        String str2 = str + " mic:";
        int intExtra2 = intent.getIntExtra("microphone", -1);
        boolean z = true;
        if (intExtra2 != -1) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str2);
            sb2.append(intExtra2 == 1 ? "Y" : "unkown");
            str2 = sb2.toString();
        }
        if (QLog.isColorLevel()) {
            QLog.m371w("TRAE", 2, "onHeadsetPlug:: " + str2);
        }
        C3719e c3719e = this._deviceConfigManager;
        if (1 != intExtra) {
            z = false;
        }
        c3719e.m354a(DEVICE_WIREDHEADSET, z);
        if (QLog.isColorLevel()) {
            QLog.m371w("TRAE", 2, "onHeadsetPlug exit");
        }
    }

    int internalSendMessage(int i, HashMap<String, Object> hashMap) {
        C3721f c3721f = this.mTraeAudioManagerLooper;
        if (c3721f != null) {
            return c3721f.m327a(i, hashMap);
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int getDeviceList(String str, long j, boolean z) {
        HashMap hashMap = new HashMap();
        hashMap.put(PARAM_SESSIONID, Long.valueOf(j));
        hashMap.put(PARAM_OPERATION, str);
        hashMap.put(PARAM_ISHOSTSIDE, Boolean.valueOf(z));
        return sendMessage(32774, hashMap);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int getStreamType(String str, long j, boolean z) {
        HashMap hashMap = new HashMap();
        hashMap.put(PARAM_SESSIONID, Long.valueOf(j));
        hashMap.put(PARAM_OPERATION, str);
        hashMap.put(PARAM_ISHOSTSIDE, Boolean.valueOf(z));
        return sendMessage(32784, hashMap);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int startService(String str, long j, boolean z, String str2) {
        if (str2.length() <= 0) {
            return -1;
        }
        HashMap hashMap = new HashMap();
        hashMap.put(PARAM_SESSIONID, Long.valueOf(j));
        hashMap.put(PARAM_OPERATION, str);
        hashMap.put(PARAM_ISHOSTSIDE, Boolean.valueOf(z));
        hashMap.put(EXTRA_DATA_DEVICECONFIG, str2);
        return sendMessage(32772, hashMap);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int stopService(String str, long j, boolean z) {
        HashMap hashMap = new HashMap();
        hashMap.put(PARAM_SESSIONID, Long.valueOf(j));
        hashMap.put(PARAM_OPERATION, str);
        hashMap.put(PARAM_ISHOSTSIDE, Boolean.valueOf(z));
        return sendMessage(ExifInterface.DATA_PACK_BITS_COMPRESSED, hashMap);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int connectDevice(String str, long j, boolean z, String str2) {
        if (str2 == null) {
            return -1;
        }
        HashMap hashMap = new HashMap();
        hashMap.put(PARAM_SESSIONID, Long.valueOf(j));
        hashMap.put(PARAM_OPERATION, str);
        hashMap.put(PARAM_ISHOSTSIDE, Boolean.valueOf(z));
        hashMap.put(CONNECTDEVICE_DEVICENAME, str2);
        hashMap.put(PARAM_DEVICE, str2);
        return sendMessage(32775, hashMap);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int connectHighestPriorityDevice(String str, long j, boolean z) {
        HashMap hashMap = new HashMap();
        hashMap.put(PARAM_SESSIONID, Long.valueOf(j));
        hashMap.put(PARAM_OPERATION, str);
        hashMap.put(PARAM_ISHOSTSIDE, Boolean.valueOf(z));
        return sendMessage(32789, hashMap);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int earAction(String str, long j, boolean z, int i) {
        if (i == 0 || i == 1) {
            HashMap hashMap = new HashMap();
            hashMap.put(PARAM_SESSIONID, Long.valueOf(j));
            hashMap.put(PARAM_OPERATION, str);
            hashMap.put(PARAM_ISHOSTSIDE, Boolean.valueOf(z));
            hashMap.put(EXTRA_EARACTION, Integer.valueOf(i));
            return sendMessage(32776, hashMap);
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int isDeviceChangabled(String str, long j, boolean z) {
        HashMap hashMap = new HashMap();
        hashMap.put(PARAM_SESSIONID, Long.valueOf(j));
        hashMap.put(PARAM_OPERATION, str);
        hashMap.put(PARAM_ISHOSTSIDE, Boolean.valueOf(z));
        return sendMessage(32777, hashMap);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int getConnectedDevice(String str, long j, boolean z) {
        HashMap hashMap = new HashMap();
        hashMap.put(PARAM_SESSIONID, Long.valueOf(j));
        hashMap.put(PARAM_OPERATION, str);
        hashMap.put(PARAM_ISHOSTSIDE, Boolean.valueOf(z));
        return sendMessage(32778, hashMap);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int getConnectingDevice(String str, long j, boolean z) {
        HashMap hashMap = new HashMap();
        hashMap.put(PARAM_SESSIONID, Long.valueOf(j));
        hashMap.put(PARAM_OPERATION, str);
        hashMap.put(PARAM_ISHOSTSIDE, Boolean.valueOf(z));
        return sendMessage(32779, hashMap);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int voicecallPreprocess(String str, long j, boolean z, int i, int i2) {
        HashMap hashMap = new HashMap();
        hashMap.put(PARAM_SESSIONID, Long.valueOf(j));
        hashMap.put(PARAM_OPERATION, str);
        hashMap.put(PARAM_ISHOSTSIDE, Boolean.valueOf(z));
        hashMap.put(PARAM_MODEPOLICY, Integer.valueOf(i));
        hashMap.put(PARAM_STREAMTYPE, Integer.valueOf(i2));
        return sendMessage(32780, hashMap);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int voicecallPostprocess(String str, long j, boolean z) {
        HashMap hashMap = new HashMap();
        hashMap.put(PARAM_SESSIONID, Long.valueOf(j));
        hashMap.put(PARAM_OPERATION, str);
        hashMap.put(PARAM_ISHOSTSIDE, Boolean.valueOf(z));
        return sendMessage(32781, hashMap);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int voiceCallAudioParamChanged(String str, long j, boolean z, int i, int i2) {
        HashMap hashMap = new HashMap();
        hashMap.put(PARAM_SESSIONID, Long.valueOf(j));
        hashMap.put(PARAM_OPERATION, str);
        hashMap.put(PARAM_ISHOSTSIDE, Boolean.valueOf(z));
        hashMap.put(PARAM_MODEPOLICY, Integer.valueOf(i));
        hashMap.put(PARAM_STREAMTYPE, Integer.valueOf(i2));
        return sendMessage(32788, hashMap);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int startRing(String str, long j, boolean z, int i, int i2, Uri uri, String str2, boolean z2, int i3, String str3, boolean z3) {
        HashMap hashMap = new HashMap();
        hashMap.put(PARAM_SESSIONID, Long.valueOf(j));
        hashMap.put(PARAM_OPERATION, str);
        hashMap.put(PARAM_ISHOSTSIDE, Boolean.valueOf(z));
        hashMap.put(PARAM_RING_DATASOURCE, Integer.valueOf(i));
        hashMap.put(PARAM_RING_RSID, Integer.valueOf(i2));
        hashMap.put(PARAM_RING_URI, uri);
        hashMap.put(PARAM_RING_FILEPATH, str2);
        hashMap.put(PARAM_RING_LOOP, Boolean.valueOf(z2));
        hashMap.put(PARAM_RING_LOOPCOUNT, Integer.valueOf(i3));
        hashMap.put(PARAM_RING_MODE, Boolean.valueOf(z3));
        hashMap.put(PARAM_RING_USERDATA_STRING, str3);
        return sendMessage(32782, hashMap);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int stopRing(String str, long j, boolean z) {
        HashMap hashMap = new HashMap();
        hashMap.put(PARAM_SESSIONID, Long.valueOf(j));
        hashMap.put(PARAM_OPERATION, str);
        hashMap.put(PARAM_ISHOSTSIDE, Boolean.valueOf(z));
        return sendMessage(32783, hashMap);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int requestReleaseAudioFocus(String str, long j, boolean z) {
        HashMap hashMap = new HashMap();
        hashMap.put(PARAM_SESSIONID, Long.valueOf(j));
        hashMap.put(PARAM_OPERATION, str);
        hashMap.put(PARAM_ISHOSTSIDE, Boolean.valueOf(z));
        return sendMessage(32790, hashMap);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int recoverAudioFocus(String str, long j, boolean z) {
        HashMap hashMap = new HashMap();
        hashMap.put(PARAM_SESSIONID, Long.valueOf(j));
        hashMap.put(PARAM_OPERATION, str);
        hashMap.put(PARAM_ISHOSTSIDE, Boolean.valueOf(z));
        return sendMessage(32791, hashMap);
    }

    int InternalSessionConnectDevice(HashMap<String, Object> hashMap) {
        int i;
        AudioDeviceInterface.LogTraceEntry("");
        if (hashMap == null || this._context == null) {
            return -1;
        }
        if (IsMusicScene) {
            if (QLog.isColorLevel()) {
                QLog.m371w("TRAE", 2, "MusicScene: InternalSessionConnectDevice failed");
            }
            return -1;
        }
        String str = (String) hashMap.get(PARAM_DEVICE);
        TXCLog.m2911w("TRAE", "ConnectDevice: " + str);
        boolean InternalIsDeviceChangeable = InternalIsDeviceChangeable();
        if (!checkDevName(str)) {
            i = 7;
        } else {
            i = !this._deviceConfigManager.m350c(str) ? 8 : !InternalIsDeviceChangeable ? 9 : 0;
        }
        if (QLog.isColorLevel()) {
            StringBuilder sb = new StringBuilder();
            sb.append("sessonID:");
            sb.append((Long) hashMap.get(PARAM_SESSIONID));
            sb.append(" devName:");
            sb.append(str);
            sb.append(" bChangabled:");
            sb.append(InternalIsDeviceChangeable ? "Y" : "N");
            sb.append(" err:");
            sb.append(i);
            QLog.m371w("TRAE", 2, sb.toString());
        }
        if (i != 0) {
            Intent intent = new Intent();
            intent.putExtra(CONNECTDEVICE_RESULT_DEVICENAME, (String) hashMap.get(PARAM_DEVICE));
            sendResBroadcast(intent, hashMap, i);
            return -1;
        } else if (str.equals(this._deviceConfigManager.m341h())) {
            if (QLog.isColorLevel()) {
                QLog.m376e("TRAE", 2, " --has connected!");
            }
            Intent intent2 = new Intent();
            intent2.putExtra(CONNECTDEVICE_RESULT_DEVICENAME, (String) hashMap.get(PARAM_DEVICE));
            sendResBroadcast(intent2, hashMap, i);
            return 0;
        } else {
            if (QLog.isColorLevel()) {
                QLog.m371w("TRAE", 2, " --connecting...");
            }
            InternalConnectDevice(str, hashMap, false);
            AudioDeviceInterface.LogTraceExit();
            return 0;
        }
    }

    int InternalConnectDevice(String str, HashMap<String, Object> hashMap, boolean z) {
        AudioDeviceInterface.LogTraceEntry(" devName:" + str);
        if (str == null) {
            return -1;
        }
        if (IsMusicScene && str.equals(DEVICE_EARPHONE)) {
            if (QLog.isColorLevel()) {
                QLog.m376e("TRAE", 2, "MusicScene, Connect device:" + str + " failed");
            }
            return -1;
        } else if (!z && !this._deviceConfigManager.m341h().equals(DEVICE_NONE) && str.equals(this._deviceConfigManager.m341h())) {
            return 0;
        } else {
            if (!checkDevName(str) || !this._deviceConfigManager.m350c(str)) {
                if (QLog.isColorLevel()) {
                    QLog.m376e("TRAE", 2, " checkDevName fail");
                }
                return -1;
            } else if (!InternalIsDeviceChangeable()) {
                if (QLog.isColorLevel()) {
                    QLog.m376e("TRAE", 2, " InternalIsDeviceChangeable fail");
                }
                return -1;
            } else {
                if (this._switchThread != null) {
                    if (QLog.isColorLevel()) {
                        QLog.m371w("TRAE", 2, "_switchThread:" + this._switchThread.mo304b());
                    }
                    this._switchThread.m301f();
                    this._switchThread = null;
                }
                if (str.equals(DEVICE_EARPHONE)) {
                    this._switchThread = new C3727h();
                } else if (str.equals(DEVICE_SPEAKERPHONE)) {
                    this._switchThread = new C3729j();
                } else if (str.equals(DEVICE_WIREDHEADSET)) {
                    this._switchThread = new C3728i();
                } else if (str.equals(DEVICE_BLUETOOTHHEADSET)) {
                    this._switchThread = new C3726g();
                }
                AbstractC3730k abstractC3730k = this._switchThread;
                if (abstractC3730k != null) {
                    abstractC3730k.m305a(hashMap);
                    this._switchThread.start();
                }
                AudioDeviceInterface.LogTraceExit();
                return 0;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.tencent.rtmp.sharp.jni.TraeAudioManager$k */
    /* loaded from: classes3.dex */
    public abstract class AbstractC3730k extends Thread {

        /* renamed from: b */
        boolean f5737b = true;

        /* renamed from: c */
        boolean[] f5738c = {false};

        /* renamed from: d */
        HashMap<String, Object> f5739d = null;

        /* renamed from: e */
        long f5740e = 0;

        /* renamed from: a */
        public abstract void mo307a();

        /* renamed from: b */
        public abstract String mo304b();

        /* renamed from: c */
        public abstract void mo303c();

        AbstractC3730k() {
            if (QLog.isColorLevel()) {
                QLog.m371w("TRAE", 2, " ++switchThread:" + mo304b());
            }
        }

        /* renamed from: a */
        public void m305a(HashMap<String, Object> hashMap) {
            this.f5739d = hashMap;
        }

        /* renamed from: e */
        void m302e() {
            TraeAudioManager.this._deviceConfigManager.m342g(mo304b());
            m306a(0);
        }

        /* renamed from: a */
        void m306a(int i) {
            TraeAudioManager.this.InternalNotifyDeviceChangableUpdate();
            AudioDeviceInterface.LogTraceEntry(mo304b() + " err:" + i);
            if (this.f5739d == null) {
                TraeAudioManager.this.InternalNotifyDeviceListUpdate();
                return;
            }
            TraeAudioManager traeAudioManager = TraeAudioManager.this;
            traeAudioManager.sessionConnectedDev = traeAudioManager._deviceConfigManager.m341h();
            Long l = (Long) this.f5739d.get(TraeAudioManager.PARAM_SESSIONID);
            if (QLog.isColorLevel()) {
                QLog.m371w("TRAE", 2, " sessonID:" + l);
            }
            if (l == null || l.longValue() == Long.MIN_VALUE) {
                TraeAudioManager.this.InternalNotifyDeviceListUpdate();
                if (!QLog.isColorLevel()) {
                    return;
                }
                QLog.m371w("TRAE", 2, "processDeviceConnectRes sid null,don't send res");
                return;
            }
            Intent intent = new Intent();
            intent.putExtra(TraeAudioManager.CONNECTDEVICE_RESULT_DEVICENAME, (String) this.f5739d.get(TraeAudioManager.PARAM_DEVICE));
            if (TraeAudioManager.this.sendResBroadcast(intent, this.f5739d, i) == 0) {
                TraeAudioManager.this.InternalNotifyDeviceListUpdate();
            }
            AudioDeviceInterface.LogTraceExit();
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            AudioDeviceInterface.LogTraceEntry(mo304b());
            TraeAudioManager.this._deviceConfigManager.m344f(mo304b());
            TraeAudioManager.this.InternalNotifyDeviceChangableUpdate();
            mo307a();
            synchronized (this.f5738c) {
                this.f5738c[0] = true;
                this.f5738c.notify();
            }
            AudioDeviceInterface.LogTraceExit();
        }

        /* renamed from: f */
        public void m301f() {
            AudioDeviceInterface.LogTraceEntry(mo304b());
            this.f5737b = false;
            if (QLog.isColorLevel()) {
                QLog.m371w("TRAE", 2, " quit:" + mo304b() + " _running:" + this.f5737b);
            }
            interrupt();
            mo303c();
            synchronized (this.f5738c) {
                if (!this.f5738c[0]) {
                    try {
                        this.f5738c.wait();
                    } catch (InterruptedException unused) {
                    }
                }
            }
            AudioDeviceInterface.LogTraceExit();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.tencent.rtmp.sharp.jni.TraeAudioManager$h */
    /* loaded from: classes3.dex */
    public class C3727h extends AbstractC3730k {
        @Override // com.tencent.rtmp.sharp.jni.TraeAudioManager.AbstractC3730k
        /* renamed from: b */
        public String mo304b() {
            return TraeAudioManager.DEVICE_EARPHONE;
        }

        @Override // com.tencent.rtmp.sharp.jni.TraeAudioManager.AbstractC3730k
        /* renamed from: c */
        public void mo303c() {
        }

        C3727h() {
            super();
        }

        @Override // com.tencent.rtmp.sharp.jni.TraeAudioManager.AbstractC3730k
        /* renamed from: a */
        public void mo307a() {
            if (TraeAudioManager.IsUpdateSceneFlag) {
                m302e();
            }
            if (QLog.isColorLevel()) {
                QLog.m371w("TRAE", 2, "connect earphone: do nothing");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.tencent.rtmp.sharp.jni.TraeAudioManager$j */
    /* loaded from: classes3.dex */
    public class C3729j extends AbstractC3730k {
        @Override // com.tencent.rtmp.sharp.jni.TraeAudioManager.AbstractC3730k
        /* renamed from: b */
        public String mo304b() {
            return TraeAudioManager.DEVICE_SPEAKERPHONE;
        }

        @Override // com.tencent.rtmp.sharp.jni.TraeAudioManager.AbstractC3730k
        /* renamed from: c */
        public void mo303c() {
        }

        C3729j() {
            super();
        }

        @Override // com.tencent.rtmp.sharp.jni.TraeAudioManager.AbstractC3730k
        /* renamed from: a */
        public void mo307a() {
            if (!TraeAudioManager.IsMusicScene && TraeAudioManager.IsUpdateSceneFlag) {
                m302e();
            }
            if (QLog.isColorLevel()) {
                QLog.m371w("TRAE", 2, "connect speakerPhone: do nothing");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.tencent.rtmp.sharp.jni.TraeAudioManager$i */
    /* loaded from: classes3.dex */
    public class C3728i extends AbstractC3730k {
        @Override // com.tencent.rtmp.sharp.jni.TraeAudioManager.AbstractC3730k
        /* renamed from: b */
        public String mo304b() {
            return TraeAudioManager.DEVICE_WIREDHEADSET;
        }

        @Override // com.tencent.rtmp.sharp.jni.TraeAudioManager.AbstractC3730k
        /* renamed from: c */
        public void mo303c() {
        }

        C3728i() {
            super();
        }

        @Override // com.tencent.rtmp.sharp.jni.TraeAudioManager.AbstractC3730k
        /* renamed from: a */
        public void mo307a() {
            if (!TraeAudioManager.IsMusicScene && TraeAudioManager.IsUpdateSceneFlag) {
                TraeAudioManager.this._am.setWiredHeadsetOn(true);
            }
            m302e();
            if (QLog.isColorLevel()) {
                QLog.m371w("TRAE", 2, "connect headset: do nothing");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.tencent.rtmp.sharp.jni.TraeAudioManager$g */
    /* loaded from: classes3.dex */
    public class C3726g extends AbstractC3730k {
        @Override // com.tencent.rtmp.sharp.jni.TraeAudioManager.AbstractC3730k
        /* renamed from: b */
        public String mo304b() {
            return TraeAudioManager.DEVICE_BLUETOOTHHEADSET;
        }

        C3726g() {
            super();
        }

        @Override // com.tencent.rtmp.sharp.jni.TraeAudioManager.AbstractC3730k
        /* renamed from: a */
        public void mo307a() {
            if (QLog.isColorLevel()) {
                QLog.m371w("TRAE", 2, "connect bluetoothHeadset: do nothing");
            }
            m302e();
        }

        @Override // com.tencent.rtmp.sharp.jni.TraeAudioManager.AbstractC3730k
        @TargetApi(8)
        /* renamed from: c */
        public void mo303c() {
            if (TraeAudioManager.this._am == null) {
                return;
            }
            m308d();
        }

        @TargetApi(8)
        /* renamed from: d */
        void m308d() {
            if (Build.VERSION.SDK_INT > 8) {
                TraeAudioManager.this._am.stopBluetoothSco();
            }
            TraeAudioManager.this._am.setBluetoothScoOn(false);
        }
    }

    int InternalSessionIsDeviceChangabled(HashMap<String, Object> hashMap) {
        Intent intent = new Intent();
        intent.putExtra(ISDEVICECHANGABLED_RESULT_ISCHANGABLED, InternalIsDeviceChangeable());
        sendResBroadcast(intent, hashMap, 0);
        return 0;
    }

    boolean InternalIsDeviceChangeable() {
        String m343g = this._deviceConfigManager.m343g();
        return m343g == null || m343g.equals(DEVICE_NONE) || m343g.equals("");
    }

    int InternalSessionGetConnectedDevice(HashMap<String, Object> hashMap) {
        Intent intent = new Intent();
        intent.putExtra(GETCONNECTEDDEVICE_RESULT_LIST, this._deviceConfigManager.m341h());
        sendResBroadcast(intent, hashMap, 0);
        return 0;
    }

    int InternalSessionGetConnectingDevice(HashMap<String, Object> hashMap) {
        Intent intent = new Intent();
        intent.putExtra(GETCONNECTINGDEVICE_RESULT_LIST, this._deviceConfigManager.m343g());
        sendResBroadcast(intent, hashMap, 0);
        return 0;
    }

    int sendResBroadcast(final Intent intent, HashMap<String, Object> hashMap, final int i) {
        if (this._context == null) {
            return -1;
        }
        Long l = (Long) hashMap.get(PARAM_SESSIONID);
        if (QLog.isColorLevel()) {
            QLog.m371w("TRAE", 2, " sessonID:" + l + ConstantUtils.PLACEHOLDER_STR_ONE + ((String) hashMap.get(PARAM_OPERATION)));
        }
        if (l == null || l.longValue() == Long.MIN_VALUE) {
            InternalNotifyDeviceListUpdate();
            if (QLog.isColorLevel()) {
                QLog.m376e("TRAE", 2, "sendResBroadcast sid null,don't send res");
            }
            return -1;
        }
        final Long l2 = (Long) hashMap.get(PARAM_SESSIONID);
        final String str = (String) hashMap.get(PARAM_OPERATION);
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.tencent.rtmp.sharp.jni.TraeAudioManager.1
            @Override // java.lang.Runnable
            public void run() {
                intent.setAction(TraeAudioManager.ACTION_TRAEAUDIOMANAGER_RES);
                intent.putExtra(TraeAudioManager.PARAM_SESSIONID, l2);
                intent.putExtra(TraeAudioManager.PARAM_OPERATION, str);
                intent.putExtra(TraeAudioManager.PARAM_RES_ERRCODE, i);
                Context context = TraeAudioManager.this._context;
                if (context != null) {
                    context.sendBroadcast(intent);
                }
            }
        });
        return 0;
    }

    int InternalNotifyDeviceListUpdate() {
        AudioDeviceInterface.LogTraceEntry("");
        if (this._context == null) {
            return -1;
        }
        HashMap<String, Object> m339j = this._deviceConfigManager.m339j();
        final ArrayList arrayList = (ArrayList) m339j.get(EXTRA_DATA_AVAILABLEDEVICE_LIST);
        final String str = (String) m339j.get(EXTRA_DATA_CONNECTEDDEVICE);
        final String str2 = (String) m339j.get(EXTRA_DATA_PREV_CONNECTEDDEVICE);
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.tencent.rtmp.sharp.jni.TraeAudioManager.2
            @Override // java.lang.Runnable
            public void run() {
                Intent intent = new Intent();
                intent.setAction(TraeAudioManager.ACTION_TRAEAUDIOMANAGER_NOTIFY);
                intent.putExtra(TraeAudioManager.PARAM_OPERATION, TraeAudioManager.NOTIFY_DEVICELIST_UPDATE);
                intent.putExtra(TraeAudioManager.EXTRA_DATA_AVAILABLEDEVICE_LIST, (String[]) arrayList.toArray(new String[0]));
                intent.putExtra(TraeAudioManager.EXTRA_DATA_CONNECTEDDEVICE, str);
                intent.putExtra(TraeAudioManager.EXTRA_DATA_PREV_CONNECTEDDEVICE, str2);
                intent.putExtra(TraeAudioManager.EXTRA_DATA_IF_HAS_BLUETOOTH_THIS_IS_NAME, TraeAudioManager.this._deviceConfigManager.m349d());
                Context context = TraeAudioManager.this._context;
                if (context != null) {
                    context.sendBroadcast(intent);
                }
            }
        });
        AudioDeviceInterface.LogTraceExit();
        return 0;
    }

    int InternalNotifyDeviceChangableUpdate() {
        if (this._context == null) {
            return -1;
        }
        final boolean InternalIsDeviceChangeable = InternalIsDeviceChangeable();
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.tencent.rtmp.sharp.jni.TraeAudioManager.3
            @Override // java.lang.Runnable
            public void run() {
                Intent intent = new Intent();
                intent.setAction(TraeAudioManager.ACTION_TRAEAUDIOMANAGER_NOTIFY);
                intent.putExtra(TraeAudioManager.PARAM_OPERATION, TraeAudioManager.NOTIFY_DEVICECHANGABLE_UPDATE);
                intent.putExtra(TraeAudioManager.NOTIFY_DEVICECHANGABLE_UPDATE_DATE, InternalIsDeviceChangeable);
                Context context = TraeAudioManager.this._context;
                if (context != null) {
                    context.sendBroadcast(intent);
                }
            }
        });
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.tencent.rtmp.sharp.jni.TraeAudioManager$d */
    /* loaded from: classes3.dex */
    public abstract class AbstractC3718d {
        /* renamed from: a */
        public abstract void mo369a();

        /* renamed from: a */
        abstract void mo367a(Context context, Intent intent);

        /* renamed from: a */
        abstract void mo364a(IntentFilter intentFilter);

        /* renamed from: a */
        public abstract boolean mo365a(Context context, C3719e c3719e);

        /* renamed from: b */
        public abstract boolean mo363b();

        /* renamed from: c */
        public abstract String mo360c();

        AbstractC3718d() {
        }

        /* renamed from: b */
        public void m361b(IntentFilter intentFilter) {
            intentFilter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
            intentFilter.addAction("android.bluetooth.device.action.ACL_CONNECTED");
            intentFilter.addAction("android.bluetooth.device.action.ACL_DISCONNECTED");
            mo364a(intentFilter);
        }

        /* renamed from: a */
        public void m366a(Context context, Intent intent, C3719e c3719e) {
            if ("android.bluetooth.adapter.action.STATE_CHANGED".equals(intent.getAction())) {
                int intExtra = intent.getIntExtra("android.bluetooth.adapter.extra.STATE", -1);
                int intExtra2 = intent.getIntExtra("android.bluetooth.adapter.extra.PREVIOUS_STATE", -1);
                if (QLog.isColorLevel()) {
                    QLog.m371w("TRAE", 2, "BT ACTION_STATE_CHANGED|   EXTRA_STATE " + m368a(intExtra));
                }
                if (QLog.isColorLevel()) {
                    QLog.m371w("TRAE", 2, "BT ACTION_STATE_CHANGED|   EXTRA_PREVIOUS_STATE " + m368a(intExtra2));
                }
                if (intExtra == 10) {
                    if (QLog.isColorLevel()) {
                        QLog.m371w("TRAE", 2, "    BT off");
                    }
                    c3719e.m354a(TraeAudioManager.DEVICE_BLUETOOTHHEADSET, false);
                } else if (intExtra != 12 || !QLog.isColorLevel()) {
                } else {
                    QLog.m371w("TRAE", 2, "BT OFF-->ON,Visiable it...");
                }
            } else if ("android.bluetooth.device.action.ACL_CONNECTED".equals(intent.getAction()) && Build.VERSION.SDK_INT < 11) {
            } else {
                if ("android.bluetooth.device.action.ACL_DISCONNECTED".equals(intent.getAction()) && Build.VERSION.SDK_INT < 11) {
                    return;
                }
                mo367a(context, intent);
            }
        }

        /* renamed from: a */
        String m368a(int i) {
            String str;
            switch (i) {
                case 10:
                    str = "STATE_OFF";
                    break;
                case 11:
                    str = "STATE_TURNING_ON";
                    break;
                case 12:
                    str = "STATE_ON";
                    break;
                case 13:
                    str = "STATE_TURNING_OFF";
                    break;
                default:
                    str = "unknow";
                    break;
            }
            return str + ":" + i;
        }

        /* renamed from: b */
        String m362b(int i) {
            String str = i != -1 ? i != 0 ? i != 1 ? i != 2 ? "unknow" : "SCO_AUDIO_STATE_CONNECTING" : "SCO_AUDIO_STATE_CONNECTED" : "SCO_AUDIO_STATE_DISCONNECTED" : "SCO_AUDIO_STATE_ERROR";
            return str + ":" + i;
        }

        /* renamed from: c */
        String m359c(int i) {
            String str = i != 0 ? i != 1 ? i != 2 ? i != 3 ? "unknow" : "STATE_DISCONNECTING" : "STATE_CONNECTED" : "STATE_CONNECTING" : "STATE_DISCONNECTED";
            return str + ":" + i;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.tencent.rtmp.sharp.jni.TraeAudioManager$b */
    /* loaded from: classes3.dex */
    public class C3716b extends AbstractC3718d {
        @Override // com.tencent.rtmp.sharp.jni.TraeAudioManager.AbstractC3718d
        /* renamed from: a */
        public void mo369a() {
        }

        @Override // com.tencent.rtmp.sharp.jni.TraeAudioManager.AbstractC3718d
        /* renamed from: a */
        void mo367a(Context context, Intent intent) {
        }

        @Override // com.tencent.rtmp.sharp.jni.TraeAudioManager.AbstractC3718d
        /* renamed from: a */
        void mo364a(IntentFilter intentFilter) {
        }

        @Override // com.tencent.rtmp.sharp.jni.TraeAudioManager.AbstractC3718d
        /* renamed from: a */
        public boolean mo365a(Context context, C3719e c3719e) {
            return true;
        }

        @Override // com.tencent.rtmp.sharp.jni.TraeAudioManager.AbstractC3718d
        /* renamed from: b */
        public boolean mo363b() {
            return false;
        }

        @Override // com.tencent.rtmp.sharp.jni.TraeAudioManager.AbstractC3718d
        /* renamed from: c */
        public String mo360c() {
            return "BluetoohHeadsetCheckFake";
        }

        C3716b() {
            super();
        }
    }

    public AbstractC3718d CreateBluetoothCheck(Context context, C3719e c3719e) {
        AbstractC3718d c3716b;
        int i = Build.VERSION.SDK_INT;
        if (i >= 11) {
            c3716b = new C3715a();
        } else if (i != 18) {
            c3716b = new C3717c();
        } else {
            c3716b = new C3716b();
        }
        if (!c3716b.mo365a(context, c3719e)) {
            c3716b = new C3716b();
        }
        if (QLog.isColorLevel()) {
            StringBuilder sb = new StringBuilder();
            sb.append("CreateBluetoothCheck:");
            sb.append(c3716b.mo360c());
            sb.append(" skip android4.3:");
            sb.append(Build.VERSION.SDK_INT == 18 ? "Y" : "N");
            QLog.m371w("TRAE", 2, sb.toString());
        }
        return c3716b;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @TargetApi(11)
    /* renamed from: com.tencent.rtmp.sharp.jni.TraeAudioManager$a */
    /* loaded from: classes3.dex */
    public class C3715a extends AbstractC3718d implements BluetoothProfile.ServiceListener {

        /* renamed from: a */
        Context f5685a = null;

        /* renamed from: b */
        C3719e f5686b = null;

        /* renamed from: c */
        BluetoothAdapter f5687c = null;

        /* renamed from: d */
        BluetoothProfile f5688d = null;

        /* renamed from: f */
        private final ReentrantLock f5690f = new ReentrantLock();

        @Override // com.tencent.rtmp.sharp.jni.TraeAudioManager.AbstractC3718d
        /* renamed from: c */
        public String mo360c() {
            return "BluetoohHeadsetCheck";
        }

        C3715a() {
            super();
        }

        @Override // com.tencent.rtmp.sharp.jni.TraeAudioManager.AbstractC3718d
        @TargetApi(11)
        /* renamed from: a */
        public boolean mo365a(Context context, C3719e c3719e) {
            AudioDeviceInterface.LogTraceEntry("");
            if (context == null || c3719e == null) {
                if (QLog.isColorLevel()) {
                    QLog.m376e("TRAE", 2, " err ctx==null||_devCfg==null");
                }
                return false;
            }
            this.f5685a = context;
            this.f5686b = c3719e;
            this.f5687c = BluetoothAdapter.getDefaultAdapter();
            if (this.f5687c == null) {
                if (QLog.isColorLevel()) {
                    QLog.m376e("TRAE", 2, " err getDefaultAdapter fail!");
                }
                return false;
            }
            this.f5690f.lock();
            try {
                if (this.f5687c.isEnabled() && this.f5688d == null && !this.f5687c.getProfileProxy(this.f5685a, this, 1)) {
                    if (QLog.isColorLevel()) {
                        QLog.m376e("TRAE", 2, "BluetoohHeadsetCheck: getProfileProxy HEADSET fail!");
                    }
                    return false;
                }
                this.f5690f.unlock();
                AudioDeviceInterface.LogTraceExit();
                return true;
            } finally {
                this.f5690f.unlock();
            }
        }

        @Override // com.tencent.rtmp.sharp.jni.TraeAudioManager.AbstractC3718d
        /* renamed from: a */
        public void mo369a() {
            AudioDeviceInterface.LogTraceEntry("_profile:" + this.f5688d);
            this.f5690f.lock();
            try {
                try {
                    if (this.f5687c != null) {
                        if (this.f5688d != null) {
                            this.f5687c.closeProfileProxy(1, this.f5688d);
                        }
                        this.f5688d = null;
                    }
                } catch (Exception e) {
                    if (QLog.isColorLevel()) {
                        QLog.m371w("TRAE", 2, " closeProfileProxy:e:" + e.getMessage());
                    }
                }
                AudioDeviceInterface.LogTraceExit();
            } finally {
                this.f5690f.unlock();
            }
        }

        @Override // com.tencent.rtmp.sharp.jni.TraeAudioManager.AbstractC3718d
        /* renamed from: b */
        public boolean mo363b() {
            this.f5690f.lock();
            try {
                boolean z = false;
                if (this.f5688d != null) {
                    List<BluetoothDevice> connectedDevices = this.f5688d.getConnectedDevices();
                    if (connectedDevices == null) {
                        return false;
                    }
                    if (connectedDevices.size() > 0) {
                        z = true;
                    }
                }
                return z;
            } finally {
                this.f5690f.unlock();
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:33:0x00c4 A[Catch: all -> 0x013a, TRY_ENTER, TryCatch #0 {all -> 0x013a, blocks: (B:5:0x0031, B:8:0x003d, B:10:0x0041, B:12:0x0047, B:13:0x0064, B:14:0x006d, B:16:0x0073, B:19:0x007e, B:21:0x0082, B:23:0x0088, B:25:0x00aa, B:27:0x00b0, B:29:0x00b6, B:31:0x00ba, B:33:0x00c4, B:34:0x00cd, B:36:0x00d3), top: B:4:0x0031 }] */
        /* JADX WARN: Removed duplicated region for block: B:36:0x00d3 A[Catch: all -> 0x013a, TRY_LEAVE, TryCatch #0 {all -> 0x013a, blocks: (B:5:0x0031, B:8:0x003d, B:10:0x0041, B:12:0x0047, B:13:0x0064, B:14:0x006d, B:16:0x0073, B:19:0x007e, B:21:0x0082, B:23:0x0088, B:25:0x00aa, B:27:0x00b0, B:29:0x00b6, B:31:0x00ba, B:33:0x00c4, B:34:0x00cd, B:36:0x00d3), top: B:4:0x0031 }] */
        /* JADX WARN: Removed duplicated region for block: B:39:0x00fe A[SYNTHETIC] */
        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        @TargetApi(11)
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
            int i2;
            AudioDeviceInterface.LogTraceEntry("_profile:" + this.f5688d + " profile:" + i + " proxy:" + bluetoothProfile);
            if (i == 1) {
                this.f5690f.lock();
                try {
                    String str = null;
                    if (this.f5688d != null && this.f5688d != bluetoothProfile) {
                        if (QLog.isColorLevel()) {
                            QLog.m371w("TRAE", 2, "BluetoohHeadsetCheck: HEADSET Connected proxy:" + bluetoothProfile + " _profile:" + this.f5688d);
                        }
                        this.f5687c.closeProfileProxy(1, this.f5688d);
                        this.f5688d = null;
                    }
                    this.f5688d = bluetoothProfile;
                    List<BluetoothDevice> connectedDevices = this.f5688d != null ? this.f5688d.getConnectedDevices() : null;
                    if (connectedDevices != null && this.f5688d != null) {
                        if (QLog.isColorLevel()) {
                            QLog.m371w("TRAE", 2, "TRAEBluetoohProxy: HEADSET Connected devs:" + connectedDevices.size() + " _profile:" + this.f5688d);
                        }
                        for (int i3 = 0; i3 < connectedDevices.size(); i3++) {
                            BluetoothDevice bluetoothDevice = connectedDevices.get(i3);
                            if (this.f5688d != null) {
                                i2 = this.f5688d.getConnectionState(bluetoothDevice);
                                if (i2 == 2) {
                                    this.f5686b.m352b(bluetoothDevice.getName());
                                }
                                if (!QLog.isColorLevel()) {
                                    QLog.m371w("TRAE", 2, "   " + i3 + ConstantUtils.PLACEHOLDER_STR_ONE + bluetoothDevice.getName() + " ConnectionState:" + i2);
                                }
                            }
                            i2 = 0;
                            if (i2 == 2) {
                            }
                            if (!QLog.isColorLevel()) {
                            }
                        }
                    }
                    this.f5690f.unlock();
                    if (this.f5686b != null) {
                        C3719e c3719e = TraeAudioManager.this._deviceConfigManager;
                        if (c3719e != null) {
                            str = c3719e.m349d();
                        }
                        if (TextUtils.isEmpty(str)) {
                            this.f5686b.m354a(TraeAudioManager.DEVICE_BLUETOOTHHEADSET, false);
                        } else if (mo363b()) {
                            this.f5686b.m354a(TraeAudioManager.DEVICE_BLUETOOTHHEADSET, true);
                            TraeAudioManager.this.checkDevicePlug(TraeAudioManager.DEVICE_BLUETOOTHHEADSET, true);
                        } else {
                            this.f5686b.m354a(TraeAudioManager.DEVICE_BLUETOOTHHEADSET, false);
                        }
                    }
                } catch (Throwable th) {
                    this.f5690f.unlock();
                    throw th;
                }
            }
            AudioDeviceInterface.LogTraceExit();
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        @TargetApi(11)
        public void onServiceDisconnected(int i) {
            AudioDeviceInterface.LogTraceEntry("_profile:" + this.f5688d + " profile:" + i);
            if (i == 1) {
                if (QLog.isColorLevel()) {
                    QLog.m371w("TRAE", 2, "TRAEBluetoohProxy: HEADSET Disconnected");
                }
                if (mo363b()) {
                    TraeAudioManager.this.checkDevicePlug(TraeAudioManager.DEVICE_BLUETOOTHHEADSET, false);
                }
                this.f5690f.lock();
                try {
                    if (this.f5688d != null) {
                        this.f5687c.closeProfileProxy(1, this.f5688d);
                        this.f5688d = null;
                    }
                } finally {
                    this.f5690f.unlock();
                }
            }
            AudioDeviceInterface.LogTraceExit();
        }

        @Override // com.tencent.rtmp.sharp.jni.TraeAudioManager.AbstractC3718d
        /* renamed from: a */
        void mo364a(IntentFilter intentFilter) {
            if (QLog.isColorLevel()) {
                QLog.m371w("TRAE", 2, ConstantUtils.PLACEHOLDER_STR_ONE + mo360c() + " _addAction");
            }
            intentFilter.addAction("android.bluetooth.adapter.action.CONNECTION_STATE_CHANGED");
            intentFilter.addAction("android.media.ACTION_SCO_AUDIO_STATE_UPDATED");
            intentFilter.addAction("android.bluetooth.a2dp.profile.action.CONNECTION_STATE_CHANGED");
        }

        @Override // com.tencent.rtmp.sharp.jni.TraeAudioManager.AbstractC3718d
        /* renamed from: a */
        void mo367a(Context context, Intent intent) {
            if ("android.bluetooth.adapter.action.CONNECTION_STATE_CHANGED".equals(intent.getAction())) {
                int intExtra = intent.getIntExtra("android.bluetooth.adapter.extra.CONNECTION_STATE", -1);
                int intExtra2 = intent.getIntExtra("android.bluetooth.adapter.extra.PREVIOUS_CONNECTION_STATE", -1);
                BluetoothDevice bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                if (QLog.isColorLevel()) {
                    QLog.m371w("TRAE", 2, "BT ACTION_CONNECTION_STATE_CHANGED|   EXTRA_CONNECTION_STATE " + m359c(intExtra));
                }
                if (QLog.isColorLevel()) {
                    QLog.m371w("TRAE", 2, "    EXTRA_PREVIOUS_CONNECTION_STATE " + m359c(intExtra2));
                }
                if (QLog.isColorLevel()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("    EXTRA_DEVICE ");
                    sb.append(bluetoothDevice);
                    String str = ConstantUtils.PLACEHOLDER_STR_ONE;
                    sb.append(str);
                    if (bluetoothDevice != null) {
                        str = bluetoothDevice.getName();
                    }
                    sb.append(str);
                    QLog.m371w("TRAE", 2, sb.toString());
                }
                if (intExtra != 2) {
                    if (intExtra != 0) {
                        return;
                    }
                    this.f5686b.m354a(TraeAudioManager.DEVICE_BLUETOOTHHEADSET, false);
                    return;
                }
                if (QLog.isColorLevel()) {
                    QLog.m371w("TRAE", 2, "   dev:" + bluetoothDevice.getName() + " connected,start sco...");
                }
                this.f5686b.m354a(TraeAudioManager.DEVICE_BLUETOOTHHEADSET, true);
                this.f5686b.m352b(bluetoothDevice != null ? bluetoothDevice.getName() : "unkown");
            } else if ("android.media.ACTION_SCO_AUDIO_STATE_UPDATED".equals(intent.getAction())) {
                int intExtra3 = intent.getIntExtra("android.media.extra.SCO_AUDIO_STATE", -1);
                int intExtra4 = intent.getIntExtra("android.media.extra.SCO_AUDIO_PREVIOUS_STATE", -1);
                BluetoothDevice bluetoothDevice2 = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                if (QLog.isColorLevel()) {
                    QLog.m371w("TRAE", 2, "BT ACTION_SCO_AUDIO_STATE_UPDATED|   EXTRA_CONNECTION_STATE  dev:" + bluetoothDevice2);
                }
                if (QLog.isColorLevel()) {
                    QLog.m371w("TRAE", 2, "   EXTRA_SCO_AUDIO_STATE " + m362b(intExtra3));
                }
                if (!QLog.isColorLevel()) {
                    return;
                }
                QLog.m371w("TRAE", 2, "   EXTRA_SCO_AUDIO_PREVIOUS_STATE " + m362b(intExtra4));
            } else if (!"android.bluetooth.a2dp.profile.action.CONNECTION_STATE_CHANGED".equals(intent.getAction())) {
            } else {
                BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
                int profileConnectionState = defaultAdapter.getProfileConnectionState(2);
                if (profileConnectionState == 0) {
                    QLog.m371w("TRAE", 2, "BluetoothA2dp STATE_DISCONNECTED");
                    TraeAudioManager.this.IsBluetoothA2dpExisted = false;
                } else if (profileConnectionState == 2) {
                    QLog.m371w("TRAE", 2, "BluetoothA2dp STATE_CONNECTED");
                    TraeAudioManager.this.IsBluetoothA2dpExisted = true;
                } else {
                    QLog.m371w("TRAE", 2, "BluetoothA2dp" + defaultAdapter.getProfileConnectionState(2));
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.tencent.rtmp.sharp.jni.TraeAudioManager$c */
    /* loaded from: classes3.dex */
    public class C3717c extends AbstractC3718d {

        /* renamed from: a */
        Class<?> f5692a = null;

        /* renamed from: b */
        Class<?> f5693b = null;

        /* renamed from: c */
        Object f5694c = null;

        /* renamed from: d */
        Method f5695d = null;

        /* renamed from: e */
        Context f5696e = null;

        /* renamed from: f */
        C3719e f5697f = null;

        @Override // com.tencent.rtmp.sharp.jni.TraeAudioManager.AbstractC3718d
        /* renamed from: c */
        public String mo360c() {
            return "BluetoohHeadsetCheckFor2x";
        }

        C3717c() {
            super();
        }

        @Override // com.tencent.rtmp.sharp.jni.TraeAudioManager.AbstractC3718d
        /* renamed from: a */
        public boolean mo365a(Context context, C3719e c3719e) {
            AudioDeviceInterface.LogTraceEntry("");
            this.f5696e = context;
            this.f5697f = c3719e;
            if (this.f5696e == null || this.f5697f == null) {
                return false;
            }
            try {
                this.f5692a = Class.forName("android.bluetooth.BluetoothHeadset");
            } catch (Exception unused) {
                if (QLog.isColorLevel()) {
                    QLog.m376e("TRAE", 2, "BTLooperThread BluetoothHeadset class not found");
                }
            }
            if (this.f5692a == null) {
                return false;
            }
            try {
                this.f5693b = Class.forName("android.bluetooth.BluetoothHeadset$ServiceListener");
            } catch (Exception e) {
                if (QLog.isColorLevel()) {
                    QLog.m376e("TRAE", 2, "BTLooperThread BluetoothHeadset.ServiceListener class not found:" + e);
                }
            }
            try {
                this.f5695d = this.f5692a.getDeclaredMethod("getCurrentHeadset", new Class[0]);
            } catch (NoSuchMethodException unused2) {
                if (QLog.isColorLevel()) {
                    QLog.m376e("TRAE", 2, "BTLooperThread BluetoothHeadset method getCurrentHeadset NoSuchMethodException");
                }
            }
            if (this.f5695d == null) {
                return false;
            }
            try {
                this.f5694c = this.f5692a.getConstructor(Context.class, this.f5693b).newInstance(context, null);
            } catch (IllegalAccessException unused3) {
                if (QLog.isColorLevel()) {
                    QLog.m376e("TRAE", 2, "BTLooperThread BluetoothHeadset getConstructor IllegalAccessException");
                }
            } catch (IllegalArgumentException unused4) {
                if (QLog.isColorLevel()) {
                    QLog.m376e("TRAE", 2, "BTLooperThread BluetoothHeadset getConstructor IllegalArgumentException");
                }
            } catch (InstantiationException unused5) {
                if (QLog.isColorLevel()) {
                    QLog.m376e("TRAE", 2, "BTLooperThread BluetoothHeadset getConstructor InstantiationException");
                }
            } catch (NoSuchMethodException unused6) {
                if (QLog.isColorLevel()) {
                    QLog.m376e("TRAE", 2, "BTLooperThread BluetoothHeadset getConstructor NoSuchMethodException");
                }
            } catch (InvocationTargetException unused7) {
                if (QLog.isColorLevel()) {
                    QLog.m376e("TRAE", 2, "BTLooperThread BluetoothHeadset getConstructor InvocationTargetException");
                }
            }
            if (this.f5694c == null) {
                return false;
            }
            this.f5697f.m354a(TraeAudioManager.DEVICE_BLUETOOTHHEADSET, mo363b());
            if (mo363b()) {
                this.f5697f.m354a(TraeAudioManager.DEVICE_BLUETOOTHHEADSET, true);
                TraeAudioManager.this.checkDevicePlug(TraeAudioManager.DEVICE_BLUETOOTHHEADSET, true);
            } else {
                this.f5697f.m354a(TraeAudioManager.DEVICE_BLUETOOTHHEADSET, false);
            }
            AudioDeviceInterface.LogTraceExit();
            return true;
        }

        @Override // com.tencent.rtmp.sharp.jni.TraeAudioManager.AbstractC3718d
        /* renamed from: a */
        public void mo369a() {
            Method method;
            AudioDeviceInterface.LogTraceEntry("");
            if (this.f5694c == null) {
                return;
            }
            try {
                method = this.f5692a.getDeclaredMethod(MainFragment.CLOSE_EVENT, new Class[0]);
            } catch (NoSuchMethodException unused) {
                if (QLog.isColorLevel()) {
                    QLog.m376e("TRAE", 2, "BTLooperThread _uninitHeadsetfor2x method close NoSuchMethodException");
                }
                method = null;
            }
            if (method == null) {
                return;
            }
            try {
                method.invoke(this.f5694c, new Object[0]);
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException unused2) {
            }
            this.f5692a = null;
            this.f5693b = null;
            this.f5694c = null;
            this.f5695d = null;
            AudioDeviceInterface.LogTraceExit();
        }

        /* JADX WARN: Removed duplicated region for block: B:15:0x0065 A[ORIG_RETURN, RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:17:? A[RETURN, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:9:0x0045  */
        @Override // com.tencent.rtmp.sharp.jni.TraeAudioManager.AbstractC3718d
        /* renamed from: b */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public boolean mo363b() {
            Object obj;
            Method method = this.f5695d;
            if (method == null || method == null) {
                return false;
            }
            try {
                obj = method.invoke(this.f5694c, new Object[0]);
            } catch (IllegalAccessException unused) {
                if (QLog.isColorLevel()) {
                    QLog.m371w("TRAE", 2, "BTLooperThread BluetoothHeadset method getCurrentHeadset IllegalAccessException");
                }
                obj = null;
                if (QLog.isColorLevel()) {
                }
                if (obj == null) {
                }
            } catch (IllegalArgumentException unused2) {
                if (QLog.isColorLevel()) {
                    QLog.m371w("TRAE", 2, "BTLooperThread BluetoothHeadset method getCurrentHeadset IllegalArgumentException");
                }
                obj = null;
                if (QLog.isColorLevel()) {
                }
                if (obj == null) {
                }
            } catch (InvocationTargetException unused3) {
                if (QLog.isColorLevel()) {
                    QLog.m371w("TRAE", 2, "BTLooperThread BluetoothHeadset method getCurrentHeadset InvocationTargetException");
                }
                obj = null;
                if (QLog.isColorLevel()) {
                }
                if (obj == null) {
                }
            }
            if (QLog.isColorLevel()) {
                StringBuilder sb = new StringBuilder();
                sb.append("BTLooperThread BluetoothHeadset method getCurrentHeadset res:");
                sb.append(obj != null ? " Y" : "N");
                QLog.m371w("TRAE", 2, sb.toString());
            }
            return obj == null;
        }

        @Override // com.tencent.rtmp.sharp.jni.TraeAudioManager.AbstractC3718d
        /* renamed from: a */
        void mo364a(IntentFilter intentFilter) {
            if (QLog.isColorLevel()) {
                QLog.m371w("TRAE", 2, ConstantUtils.PLACEHOLDER_STR_ONE + mo360c() + " _addAction");
            }
            intentFilter.addAction("android.bluetooth.headset.action.AUDIO_STATE_CHANGED");
            intentFilter.addAction("android.bluetooth.headset.action.STATE_CHANGED");
        }

        @Override // com.tencent.rtmp.sharp.jni.TraeAudioManager.AbstractC3718d
        /* renamed from: a */
        void mo367a(Context context, Intent intent) {
            if ("android.bluetooth.headset.action.AUDIO_STATE_CHANGED".equals(intent.getAction())) {
                int intExtra = intent.getIntExtra("android.bluetooth.headset.extra.STATE", -2);
                int intExtra2 = intent.getIntExtra("android.bluetooth.headset.extra.PREVIOUS_STATE", -2);
                int intExtra3 = intent.getIntExtra("android.bluetooth.headset.extra.AUDIO_STATE", -2);
                if (QLog.isColorLevel()) {
                    QLog.m371w("TRAE", 2, "++ AUDIO_STATE_CHANGED|  STATE " + intExtra);
                }
                if (QLog.isColorLevel()) {
                    QLog.m371w("TRAE", 2, "       PREVIOUS_STATE " + intExtra2);
                }
                if (QLog.isColorLevel()) {
                    QLog.m371w("TRAE", 2, "       AUDIO_STATE " + intExtra3);
                }
                if (intExtra3 == 2) {
                    this.f5697f.m354a(TraeAudioManager.DEVICE_BLUETOOTHHEADSET, true);
                } else if (intExtra3 != 0) {
                } else {
                    this.f5697f.m354a(TraeAudioManager.DEVICE_BLUETOOTHHEADSET, false);
                }
            } else if (!"android.bluetooth.headset.action.STATE_CHANGED".equals(intent.getAction())) {
            } else {
                int intExtra4 = intent.getIntExtra("android.bluetooth.headset.extra.STATE", -2);
                int intExtra5 = intent.getIntExtra("android.bluetooth.headset.extra.PREVIOUS_STATE", -2);
                int intExtra6 = intent.getIntExtra("android.bluetooth.headset.extra.AUDIO_STATE", -2);
                if (QLog.isColorLevel()) {
                    QLog.m371w("TRAE", 2, "++ STATE_CHANGED|  STATE " + intExtra4);
                }
                if (QLog.isColorLevel()) {
                    QLog.m371w("TRAE", 2, "       PREVIOUS_STATE " + intExtra5);
                }
                if (QLog.isColorLevel()) {
                    QLog.m371w("TRAE", 2, "       AUDIO_STATE " + intExtra6);
                }
                if (intExtra6 == 2) {
                    this.f5697f.m354a(TraeAudioManager.DEVICE_BLUETOOTHHEADSET, true);
                } else if (intExtra6 != 0) {
                } else {
                    this.f5697f.m354a(TraeAudioManager.DEVICE_BLUETOOTHHEADSET, false);
                }
            }
        }
    }

    static String getForceConfigName(int i) {
        if (i >= 0) {
            String[] strArr = forceName;
            return i < strArr.length ? strArr[i] : "unknow";
        }
        return "unknow";
    }

    public static Object invokeMethod(Object obj, String str, Object[] objArr, Class[] clsArr) {
        try {
            return obj.getClass().getMethod(str, clsArr).invoke(obj, objArr);
        } catch (Exception e) {
            if (QLog.isColorLevel()) {
                QLog.m371w("TRAE", 2, "invokeMethod Exception:" + e.getMessage());
            }
            return null;
        }
    }

    public static Object invokeStaticMethod(String str, String str2, Object[] objArr, Class[] clsArr) {
        try {
            return Class.forName(str).getMethod(str2, clsArr).invoke(null, objArr);
        } catch (ClassNotFoundException unused) {
            if (!QLog.isColorLevel()) {
                return null;
            }
            QLog.m371w("TRAE", 2, "ClassNotFound:" + str);
            return null;
        } catch (IllegalAccessException unused2) {
            if (!QLog.isColorLevel()) {
                return null;
            }
            QLog.m371w("TRAE", 2, "IllegalAccess:" + str2);
            return null;
        } catch (IllegalArgumentException unused3) {
            if (!QLog.isColorLevel()) {
                return null;
            }
            QLog.m371w("TRAE", 2, "IllegalArgument:" + str2);
            return null;
        } catch (NoSuchMethodException unused4) {
            if (!QLog.isColorLevel()) {
                return null;
            }
            QLog.m371w("TRAE", 2, "NoSuchMethod:" + str2);
            return null;
        } catch (InvocationTargetException unused5) {
            if (!QLog.isColorLevel()) {
                return null;
            }
            QLog.m371w("TRAE", 2, "InvocationTarget:" + str2);
            return null;
        } catch (Exception e) {
            if (!QLog.isColorLevel()) {
                return null;
            }
            QLog.m371w("TRAE", 2, "invokeStaticMethod Exception:" + e.getMessage());
            return null;
        }
    }

    static void setParameters(String str) {
        Object[] objArr = {str};
        Class[] clsArr = new Class[objArr.length];
        clsArr[0] = String.class;
        if (QLog.isColorLevel()) {
            QLog.m371w("TRAE", 2, "setParameters  :" + str);
        }
        invokeStaticMethod("android.media.AudioSystem", "setParameters", objArr, clsArr);
    }

    static void setPhoneState(int i) {
        Object[] objArr = {Integer.valueOf(i)};
        Class[] clsArr = new Class[objArr.length];
        clsArr[0] = Integer.TYPE;
        invokeStaticMethod("android.media.AudioSystem", "setPhoneState", objArr, clsArr);
    }

    static void setForceUse(int i, int i2) {
        Object[] objArr = {Integer.valueOf(i), Integer.valueOf(i2)};
        Class[] clsArr = new Class[objArr.length];
        Class cls = Integer.TYPE;
        clsArr[0] = cls;
        clsArr[1] = cls;
        Object invokeStaticMethod = invokeStaticMethod("android.media.AudioSystem", "setForceUse", objArr, clsArr);
        if (QLog.isColorLevel()) {
            QLog.m371w("TRAE", 2, "setForceUse  usage:" + i + " config:" + i2 + " ->" + getForceConfigName(i2) + " res:" + invokeStaticMethod);
        }
    }

    static int getForceUse(int i) {
        Integer num = 0;
        Object[] objArr = {Integer.valueOf(i)};
        Class[] clsArr = new Class[objArr.length];
        clsArr[0] = Integer.TYPE;
        Object invokeStaticMethod = invokeStaticMethod("android.media.AudioSystem", "getForceUse", objArr, clsArr);
        if (invokeStaticMethod != null) {
            num = (Integer) invokeStaticMethod;
        }
        if (QLog.isColorLevel()) {
            QLog.m371w("TRAE", 2, "getForceUse  usage:" + i + " config:" + num + " ->" + getForceConfigName(num.intValue()));
        }
        return num.intValue();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void forceVolumeControlStream(AudioManager audioManager, int i) {
        Object[] objArr = {Integer.valueOf(i)};
        Class[] clsArr = new Class[objArr.length];
        clsArr[0] = Integer.TYPE;
        Object invokeMethod = invokeMethod(audioManager, "forceVolumeControlStream", objArr, clsArr);
        if (QLog.isColorLevel()) {
            QLog.m371w("TRAE", 2, "forceVolumeControlStream  streamType:" + i + " res:" + invokeMethod);
        }
    }
}
