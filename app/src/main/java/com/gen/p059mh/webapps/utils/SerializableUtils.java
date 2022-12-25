package com.gen.p059mh.webapps.utils;

import com.gen.p059mh.webapps.modules.StorageLinkedHashMap;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

/* renamed from: com.gen.mh.webapps.utils.SerializableUtils */
/* loaded from: classes2.dex */
public class SerializableUtils {
    static ClearThread clearThread;

    public static byte[] serializableMap(Map map) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        new ObjectOutputStream(byteArrayOutputStream).writeObject(map);
        return byteArrayOutputStream.toByteArray();
    }

    public static StorageLinkedHashMap deSerializableMap(byte[] bArr) throws IOException, ClassNotFoundException {
        return (StorageLinkedHashMap) new ObjectInputStream(new ByteArrayInputStream(bArr)).readObject();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.gen.mh.webapps.utils.SerializableUtils$ClearThread */
    /* loaded from: classes2.dex */
    public static class ClearThread extends Thread {
        private Integer count = 0;
        private Map map;

        ClearThread() {
        }

        public void setMap(Map map) {
            this.map = map;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            byte[] serializableMap;
            super.run();
            synchronized (this.count) {
                if (this.count.intValue() == 0) {
                    return;
                }
                while (this.count.intValue() > 0) {
                    byte[] bArr = new byte[0];
                    try {
                        serializableMap = SerializableUtils.serializableMap(this.map);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (serializableMap.length / 1024 > 10240) {
                        this.map.remove(this.map.keySet().toArray()[0]);
                        SerializableUtils.clearLast(this.map);
                    }
                    this.count = Integer.valueOf(this.count.intValue() - 1);
                }
            }
        }
    }

    public static void clearLast(Map map) {
        ClearThread clearThread2 = clearThread;
        if (clearThread2 != null) {
            Integer unused = clearThread2.count;
            clearThread2.count = Integer.valueOf(clearThread2.count.intValue() + 1);
        } else {
            clearThread = new ClearThread();
            clearThread.setMap(map);
            ClearThread clearThread3 = clearThread;
            Integer unused2 = clearThread3.count;
            clearThread3.count = Integer.valueOf(clearThread3.count.intValue() + 1);
        }
        clearThread.run();
    }
}
