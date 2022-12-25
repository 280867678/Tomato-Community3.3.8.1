package com.example.websocket.com.neovisionaries.p057ws.client;

import android.support.p002v4.app.FragmentTransaction;
import android.support.p002v4.view.InputDeviceCompat;
import android.support.p006v8.renderscript.ScriptIntrinsicBLAS;
import com.tomatolive.library.utils.ConstantUtils;

/* JADX INFO: Access modifiers changed from: package-private */
/* renamed from: com.example.websocket.com.neovisionaries.ws.client.DeflateUtil */
/* loaded from: classes2.dex */
public class DeflateUtil {
    private static int[] INDICES_FROM_CODE_LENGTH_ORDER = {16, 17, 18, 0, 8, 7, 9, 6, 10, 5, 11, 4, 12, 3, 13, 2, 14, 1, 15};

    DeflateUtil() {
    }

    public static void readDynamicTables(ByteArray byteArray, int[] iArr, Huffman[] huffmanArr) throws FormatException {
        int readBits = byteArray.readBits(iArr, 5) + 257;
        int readBits2 = byteArray.readBits(iArr, 5) + 1;
        int readBits3 = byteArray.readBits(iArr, 4) + 4;
        int[] iArr2 = new int[19];
        for (int i = 0; i < readBits3; i++) {
            iArr2[codeLengthOrderToIndex(i)] = (byte) byteArray.readBits(iArr, 3);
        }
        Huffman huffman = new Huffman(iArr2);
        int[] iArr3 = new int[readBits];
        readCodeLengths(byteArray, iArr, iArr3, huffman);
        Huffman huffman2 = new Huffman(iArr3);
        int[] iArr4 = new int[readBits2];
        readCodeLengths(byteArray, iArr, iArr4, huffman);
        Huffman huffman3 = new Huffman(iArr4);
        huffmanArr[0] = huffman2;
        huffmanArr[1] = huffman3;
    }

    private static void readCodeLengths(ByteArray byteArray, int[] iArr, int[] iArr2, Huffman huffman) throws FormatException {
        int i;
        int readBits;
        int readBits2;
        int i2 = 0;
        while (i2 < iArr2.length) {
            int readSym = huffman.readSym(byteArray, iArr);
            if (readSym >= 0 && readSym <= 15) {
                iArr2[i2] = readSym;
            } else {
                switch (readSym) {
                    case 16:
                        i = iArr2[i2 - 1];
                        readBits = byteArray.readBits(iArr, 2) + 3;
                        break;
                    case 17:
                        readBits2 = byteArray.readBits(iArr, 3) + 3;
                        readBits = readBits2;
                        i = 0;
                        break;
                    case 18:
                        readBits2 = byteArray.readBits(iArr, 7) + 11;
                        readBits = readBits2;
                        i = 0;
                        break;
                    default:
                        throw new FormatException(String.format("[%s] Bad code length '%d' at the bit index '%d'.", DeflateUtil.class.getSimpleName(), Integer.valueOf(readSym), iArr));
                }
                for (int i3 = 0; i3 < readBits; i3++) {
                    iArr2[i2 + i3] = i;
                }
                i2 += readBits - 1;
            }
            i2++;
        }
    }

    private static int codeLengthOrderToIndex(int i) {
        return INDICES_FROM_CODE_LENGTH_ORDER[i];
    }

    public static int readLength(ByteArray byteArray, int[] iArr, int i) throws FormatException {
        int i2;
        int i3 = 5;
        switch (i) {
            case 257:
            case 258:
            case 259:
            case 260:
            case 261:
            case 262:
            case 263:
            case 264:
                return i - 254;
            case 265:
                i2 = 11;
                i3 = 1;
                break;
            case 266:
                i2 = 13;
                i3 = 1;
                break;
            case 267:
                i2 = 15;
                i3 = 1;
                break;
            case 268:
                i2 = 17;
                i3 = 1;
                break;
            case 269:
                i2 = 19;
                i3 = 2;
                break;
            case 270:
                i2 = 23;
                i3 = 2;
                break;
            case 271:
                i2 = 27;
                i3 = 2;
                break;
            case 272:
                i2 = 31;
                i3 = 2;
                break;
            case 273:
                i2 = 35;
                i3 = 3;
                break;
            case 274:
                i2 = 43;
                i3 = 3;
                break;
            case 275:
                i2 = 51;
                i3 = 3;
                break;
            case ConstantUtils.PK_TYPE_SUCCESS_APPOINT /* 276 */:
                i2 = 59;
                i3 = 3;
                break;
            case ConstantUtils.PK_TYPE_FAIL /* 277 */:
                i2 = 67;
                i3 = 4;
                break;
            case ConstantUtils.PK_TYPE_INVITE /* 278 */:
                i2 = 83;
                i3 = 4;
                break;
            case ConstantUtils.PK_TYPE_PK_PREPARE /* 279 */:
                i2 = 99;
                i3 = 4;
                break;
            case ConstantUtils.PK_TYPE_PK_PROCESSING /* 280 */:
                i2 = 115;
                i3 = 4;
                break;
            case ConstantUtils.PK_TYPE_PK_ENDING /* 281 */:
                i2 = ScriptIntrinsicBLAS.NON_UNIT;
                break;
            case 282:
                i2 = 163;
                break;
            case 283:
                i2 = 195;
                break;
            case 284:
                i2 = 227;
                break;
            case 285:
                return 258;
            default:
                throw new FormatException(String.format("[%s] Bad literal/length code '%d' at the bit index '%d'.", DeflateUtil.class.getSimpleName(), Integer.valueOf(i), Integer.valueOf(iArr[0])));
        }
        return i2 + byteArray.readBits(iArr, i3);
    }

    public static int readDistance(ByteArray byteArray, int[] iArr, Huffman huffman) throws FormatException {
        int readSym = huffman.readSym(byteArray, iArr);
        int i = 12;
        int i2 = 13;
        switch (readSym) {
            case 0:
            case 1:
            case 2:
            case 3:
                return readSym + 1;
            case 4:
                i = 1;
                i2 = 5;
                break;
            case 5:
                i = 1;
                i2 = 7;
                break;
            case 6:
                i = 2;
                i2 = 9;
                break;
            case 7:
                i = 2;
                break;
            case 8:
                i2 = 17;
                i = 3;
                break;
            case 9:
                i2 = 25;
                i = 3;
                break;
            case 10:
                i2 = 33;
                i = 4;
                break;
            case 11:
                i2 = 49;
                i = 4;
                break;
            case 12:
                i2 = 65;
                i = 5;
                break;
            case 13:
                i2 = 97;
                i = 5;
                break;
            case 14:
                i2 = 129;
                i = 6;
                break;
            case 15:
                i2 = 193;
                i = 6;
                break;
            case 16:
                i2 = 257;
                i = 7;
                break;
            case 17:
                i2 = 385;
                i = 7;
                break;
            case 18:
                i2 = InputDeviceCompat.SOURCE_DPAD;
                i = 8;
                break;
            case 19:
                i2 = 769;
                i = 8;
                break;
            case 20:
                i2 = InputDeviceCompat.SOURCE_GAMEPAD;
                i = 9;
                break;
            case 21:
                i2 = 1537;
                i = 9;
                break;
            case 22:
                i2 = 2049;
                i = 10;
                break;
            case 23:
                i2 = 3073;
                i = 10;
                break;
            case 24:
                i2 = FragmentTransaction.TRANSIT_FRAGMENT_OPEN;
                i = 11;
                break;
            case 25:
                i2 = 6145;
                i = 11;
                break;
            case 26:
                i2 = 8193;
                break;
            case 27:
                i2 = 12289;
                break;
            case 28:
                i = 13;
                i2 = 16385;
                break;
            case 29:
                i = 13;
                i2 = 24577;
                break;
            default:
                throw new FormatException(String.format("[%s] Bad distance code '%d' at the bit index '%d'.", DeflateUtil.class.getSimpleName(), Integer.valueOf(readSym), Integer.valueOf(iArr[0])));
        }
        return i2 + byteArray.readBits(iArr, i);
    }
}
