package com.example.websocket.com.neovisionaries.p057ws.client;

/* JADX INFO: Access modifiers changed from: package-private */
/* renamed from: com.example.websocket.com.neovisionaries.ws.client.FixedDistanceHuffman */
/* loaded from: classes2.dex */
public class FixedDistanceHuffman extends Huffman {
    private static final FixedDistanceHuffman INSTANCE = new FixedDistanceHuffman();

    private FixedDistanceHuffman() {
        super(buildCodeLensFromSym());
    }

    private static int[] buildCodeLensFromSym() {
        int[] iArr = new int[32];
        for (int i = 0; i < 32; i++) {
            iArr[i] = 5;
        }
        return iArr;
    }

    public static FixedDistanceHuffman getInstance() {
        return INSTANCE;
    }
}
