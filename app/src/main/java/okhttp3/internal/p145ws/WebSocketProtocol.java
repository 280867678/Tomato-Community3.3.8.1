package okhttp3.internal.p145ws;

import okio.Buffer;

/* renamed from: okhttp3.internal.ws.WebSocketProtocol */
/* loaded from: classes4.dex */
public final class WebSocketProtocol {
    /* JADX INFO: Access modifiers changed from: package-private */
    public static void toggleMask(Buffer.UnsafeCursor unsafeCursor, byte[] bArr) {
        int length = bArr.length;
        int i = 0;
        do {
            byte[] bArr2 = unsafeCursor.data;
            int i2 = unsafeCursor.start;
            int i3 = unsafeCursor.end;
            while (i2 < i3) {
                int i4 = i % length;
                bArr2[i2] = (byte) (bArr2[i2] ^ bArr[i4]);
                i2++;
                i = i4 + 1;
            }
        } while (unsafeCursor.next() != -1);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String closeCodeExceptionMessage(int i) {
        if (i < 1000 || i >= 5000) {
            return "Code must be in range [1000,5000): " + i;
        } else if ((i < 1004 || i > 1006) && (i < 1012 || i > 2999)) {
            return null;
        } else {
            return "Code " + i + " is reserved and may not be used.";
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void validateCloseCode(int i) {
        String closeCodeExceptionMessage = closeCodeExceptionMessage(i);
        if (closeCodeExceptionMessage == null) {
            return;
        }
        throw new IllegalArgumentException(closeCodeExceptionMessage);
    }
}
