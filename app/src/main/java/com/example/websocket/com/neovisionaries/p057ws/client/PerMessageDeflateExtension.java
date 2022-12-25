package com.example.websocket.com.neovisionaries.p057ws.client;

import java.util.Map;

/* renamed from: com.example.websocket.com.neovisionaries.ws.client.PerMessageDeflateExtension */
/* loaded from: classes2.dex */
class PerMessageDeflateExtension extends PerMessageCompressionExtension {
    private static final String CLIENT_MAX_WINDOW_BITS = "client_max_window_bits";
    private static final String CLIENT_NO_CONTEXT_TAKEOVER = "client_no_context_takeover";
    private static final byte[] COMPRESSION_TERMINATOR = {0, 0, -1, -1};
    private static final int INCOMING_SLIDING_WINDOW_MARGIN = 1024;
    private static final int MAX_BITS = 15;
    private static final int MAX_WINDOW_SIZE = 32768;
    private static final int MIN_BITS = 8;
    private static final int MIN_WINDOW_SIZE = 256;
    private static final String SERVER_MAX_WINDOW_BITS = "server_max_window_bits";
    private static final String SERVER_NO_CONTEXT_TAKEOVER = "server_no_context_takeover";
    private boolean mClientNoContextTakeover;
    private ByteArray mIncomingSlidingWindow;
    private int mIncomingSlidingWindowBufferSize;
    private boolean mServerNoContextTakeover;
    private int mServerWindowSize = 32768;
    private int mClientWindowSize = 32768;

    public PerMessageDeflateExtension() {
        super(WebSocketExtension.PERMESSAGE_DEFLATE);
    }

    public PerMessageDeflateExtension(String str) {
        super(str);
    }

    @Override // com.example.websocket.com.neovisionaries.p057ws.client.WebSocketExtension
    void validate() throws WebSocketException {
        for (Map.Entry<String, String> entry : getParameters().entrySet()) {
            validateParameter(entry.getKey(), entry.getValue());
        }
        this.mIncomingSlidingWindowBufferSize = this.mServerWindowSize + 1024;
    }

    public boolean isServerNoContextTakeover() {
        return this.mServerNoContextTakeover;
    }

    public boolean isClientNoContextTakeover() {
        return this.mClientNoContextTakeover;
    }

    public int getServerWindowSize() {
        return this.mServerWindowSize;
    }

    public int getClientWindowSize() {
        return this.mClientWindowSize;
    }

    private void validateParameter(String str, String str2) throws WebSocketException {
        if (SERVER_NO_CONTEXT_TAKEOVER.equals(str)) {
            this.mServerNoContextTakeover = true;
        } else if (CLIENT_NO_CONTEXT_TAKEOVER.equals(str)) {
            this.mClientNoContextTakeover = true;
        } else if (SERVER_MAX_WINDOW_BITS.equals(str)) {
            this.mServerWindowSize = computeWindowSize(str, str2);
        } else if (CLIENT_MAX_WINDOW_BITS.equals(str)) {
            this.mClientWindowSize = computeWindowSize(str, str2);
        } else {
            WebSocketError webSocketError = WebSocketError.PERMESSAGE_DEFLATE_UNSUPPORTED_PARAMETER;
            throw new WebSocketException(webSocketError, "permessage-deflate extension contains an unsupported parameter: " + str);
        }
    }

    private int computeWindowSize(String str, String str2) throws WebSocketException {
        int extractMaxWindowBits = extractMaxWindowBits(str, str2);
        int i = 256;
        for (int i2 = 8; i2 < extractMaxWindowBits; i2++) {
            i *= 2;
        }
        return i;
    }

    private int extractMaxWindowBits(String str, String str2) throws WebSocketException {
        int parseMaxWindowBits = parseMaxWindowBits(str2);
        if (parseMaxWindowBits >= 0) {
            return parseMaxWindowBits;
        }
        throw new WebSocketException(WebSocketError.PERMESSAGE_DEFLATE_INVALID_MAX_WINDOW_BITS, String.format("The value of %s parameter of permessage-deflate extension is invalid: %s", str, str2));
    }

    private int parseMaxWindowBits(String str) {
        int parseInt;
        if (str == null) {
            return -1;
        }
        try {
            parseInt = Integer.parseInt(str);
        } catch (NumberFormatException unused) {
        }
        if (parseInt >= 8 && 15 >= parseInt) {
            return parseInt;
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.example.websocket.com.neovisionaries.p057ws.client.PerMessageCompressionExtension
    public byte[] decompress(byte[] bArr) throws WebSocketException {
        ByteArray byteArray = new ByteArray(bArr.length + COMPRESSION_TERMINATOR.length);
        byteArray.put(bArr);
        byteArray.put(COMPRESSION_TERMINATOR);
        if (this.mIncomingSlidingWindow == null) {
            this.mIncomingSlidingWindow = new ByteArray(this.mIncomingSlidingWindowBufferSize);
        }
        int length = this.mIncomingSlidingWindow.length();
        try {
            DeflateDecompressor.decompress(byteArray, this.mIncomingSlidingWindow);
            byte[] bytes = this.mIncomingSlidingWindow.toBytes(length);
            this.mIncomingSlidingWindow.shrink(this.mIncomingSlidingWindowBufferSize);
            if (this.mServerNoContextTakeover) {
                this.mIncomingSlidingWindow.clear();
            }
            return bytes;
        } catch (Exception e) {
            throw new WebSocketException(WebSocketError.DECOMPRESSION_ERROR, String.format("Failed to decompress the message: %s", e.getMessage()), e);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.example.websocket.com.neovisionaries.p057ws.client.PerMessageCompressionExtension
    public byte[] compress(byte[] bArr) throws WebSocketException {
        if (!canCompress(bArr)) {
            return bArr;
        }
        try {
            return adjustCompressedData(DeflateCompressor.compress(bArr));
        } catch (Exception e) {
            throw new WebSocketException(WebSocketError.COMPRESSION_ERROR, String.format("Failed to compress the message: %s", e.getMessage()), e);
        }
    }

    private boolean canCompress(byte[] bArr) {
        int i = this.mClientWindowSize;
        return i == 32768 || bArr.length < i;
    }

    private static byte[] adjustCompressedData(byte[] bArr) throws FormatException {
        ByteArray byteArray = new ByteArray(bArr.length + 1);
        byteArray.put(bArr);
        int[] iArr = new int[1];
        boolean[] zArr = new boolean[1];
        do {
        } while (skipBlock(byteArray, iArr, zArr));
        if (zArr[0]) {
            return byteArray.toBytes(0, (((iArr[0] - 1) / 8) + 1) - 4);
        }
        appendEmptyBlock(byteArray, iArr);
        return byteArray.toBytes(0, ((iArr[0] - 1) / 8) + 1);
    }

    private static void appendEmptyBlock(ByteArray byteArray, int[] iArr) {
        int i = iArr[0] % 8;
        if (i == 0 || i == 6 || i == 7) {
            byteArray.put(0);
        }
        iArr[0] = iArr[0] + 3;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0052  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static boolean skipBlock(ByteArray byteArray, int[] iArr, boolean[] zArr) throws FormatException {
        boolean z;
        boolean readBit = byteArray.readBit(iArr);
        if (readBit) {
            byteArray.clearBit(iArr[0] - 1);
        }
        int readBits = byteArray.readBits(iArr, 2);
        if (readBits != 0) {
            if (readBits == 1) {
                skipFixedBlock(byteArray, iArr);
            } else if (readBits == 2) {
                skipDynamicBlock(byteArray, iArr);
            } else {
                throw new FormatException(String.format("[%s] Bad compression type '11' at the bit index '%d'.", PerMessageDeflateExtension.class.getSimpleName(), Integer.valueOf(iArr[0])));
            }
        } else if (skipPlainBlock(byteArray, iArr) == 0) {
            z = true;
            if (byteArray.length() <= iArr[0] / 8) {
                readBit = true;
            }
            if (readBit && z) {
                zArr[0] = true;
            }
            return !readBit;
        }
        z = false;
        if (byteArray.length() <= iArr[0] / 8) {
        }
        if (readBit) {
            zArr[0] = true;
        }
        return !readBit;
    }

    private static int skipPlainBlock(ByteArray byteArray, int[] iArr) {
        int i = ((iArr[0] + 7) & (-8)) / 8;
        int i2 = (byteArray.get(i) & 255) + ((byteArray.get(i + 1) & 255) * 256);
        iArr[0] = (i + 4 + i2) * 8;
        return i2;
    }

    private static void skipFixedBlock(ByteArray byteArray, int[] iArr) throws FormatException {
        skipData(byteArray, iArr, FixedLiteralLengthHuffman.getInstance(), FixedDistanceHuffman.getInstance());
    }

    private static void skipDynamicBlock(ByteArray byteArray, int[] iArr) throws FormatException {
        Huffman[] huffmanArr = new Huffman[2];
        DeflateUtil.readDynamicTables(byteArray, iArr, huffmanArr);
        skipData(byteArray, iArr, huffmanArr[0], huffmanArr[1]);
    }

    private static void skipData(ByteArray byteArray, int[] iArr, Huffman huffman, Huffman huffman2) throws FormatException {
        while (true) {
            int readSym = huffman.readSym(byteArray, iArr);
            if (readSym == 256) {
                return;
            }
            if (readSym < 0 || readSym > 255) {
                DeflateUtil.readLength(byteArray, iArr, readSym);
                DeflateUtil.readDistance(byteArray, iArr, huffman2);
            }
        }
    }
}
