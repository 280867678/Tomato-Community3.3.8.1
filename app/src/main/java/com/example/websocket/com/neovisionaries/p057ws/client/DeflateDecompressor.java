package com.example.websocket.com.neovisionaries.p057ws.client;

/* renamed from: com.example.websocket.com.neovisionaries.ws.client.DeflateDecompressor */
/* loaded from: classes2.dex */
class DeflateDecompressor {
    DeflateDecompressor() {
    }

    public static void decompress(ByteArray byteArray, ByteArray byteArray2) throws FormatException {
        decompress(byteArray, 0, byteArray2);
    }

    private static void decompress(ByteArray byteArray, int i, ByteArray byteArray2) throws FormatException {
        do {
        } while (inflateBlock(byteArray, new int[]{i * 8}, byteArray2));
    }

    private static boolean inflateBlock(ByteArray byteArray, int[] iArr, ByteArray byteArray2) throws FormatException {
        boolean readBit = byteArray.readBit(iArr);
        int readBits = byteArray.readBits(iArr, 2);
        if (readBits == 0) {
            inflatePlainBlock(byteArray, iArr, byteArray2);
        } else if (readBits == 1) {
            inflateFixedBlock(byteArray, iArr, byteArray2);
        } else if (readBits == 2) {
            inflateDynamicBlock(byteArray, iArr, byteArray2);
        } else {
            throw new FormatException(String.format("[%s] Bad compression type '11' at the bit index '%d'.", DeflateDecompressor.class.getSimpleName(), Integer.valueOf(iArr[0])));
        }
        if (byteArray.length() <= iArr[0] / 8) {
            readBit = true;
        }
        return !readBit;
    }

    private static void inflatePlainBlock(ByteArray byteArray, int[] iArr, ByteArray byteArray2) {
        int i = ((iArr[0] + 7) & (-8)) / 8;
        int i2 = (byteArray.get(i) & 255) + ((byteArray.get(i + 1) & 255) * 256);
        int i3 = i + 4;
        byteArray2.put(byteArray, i3, i2);
        iArr[0] = (i3 + i2) * 8;
    }

    private static void inflateFixedBlock(ByteArray byteArray, int[] iArr, ByteArray byteArray2) throws FormatException {
        inflateData(byteArray, iArr, byteArray2, FixedLiteralLengthHuffman.getInstance(), FixedDistanceHuffman.getInstance());
    }

    private static void inflateDynamicBlock(ByteArray byteArray, int[] iArr, ByteArray byteArray2) throws FormatException {
        Huffman[] huffmanArr = new Huffman[2];
        DeflateUtil.readDynamicTables(byteArray, iArr, huffmanArr);
        inflateData(byteArray, iArr, byteArray2, huffmanArr[0], huffmanArr[1]);
    }

    private static void inflateData(ByteArray byteArray, int[] iArr, ByteArray byteArray2, Huffman huffman, Huffman huffman2) throws FormatException {
        while (true) {
            int readSym = huffman.readSym(byteArray, iArr);
            if (readSym == 256) {
                return;
            }
            if (readSym >= 0 && readSym <= 255) {
                byteArray2.put(readSym);
            } else {
                duplicate(DeflateUtil.readLength(byteArray, iArr, readSym), DeflateUtil.readDistance(byteArray, iArr, huffman2), byteArray2);
            }
        }
    }

    private static void duplicate(int i, int i2, ByteArray byteArray) {
        int length = byteArray.length();
        byte[] bArr = new byte[i];
        int i3 = length - i2;
        int i4 = 0;
        int i5 = i3;
        while (i4 < i) {
            if (length <= i5) {
                i5 = i3;
            }
            bArr[i4] = byteArray.get(i5);
            i4++;
            i5++;
        }
        byteArray.put(bArr);
    }
}
