package okhttp3;

import okio.ByteString;

/* loaded from: classes4.dex */
public interface WebSocket {

    /* loaded from: classes4.dex */
    public interface Factory {
    }

    boolean close(int i, String str);

    boolean send(String str);

    boolean send(ByteString byteString);
}
