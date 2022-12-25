package com.github.promeg.pinyinhelper;

import com.zzz.ipfssdk.callback.exception.CodeState;

/* loaded from: classes2.dex */
public final class Pinyin {
    public static String toPinyin(char c) {
        if (isChinese(c)) {
            return c == 12295 ? "LING" : PinyinData.PINYIN_TABLE[getPinyinCode(c)];
        }
        return String.valueOf(c);
    }

    public static boolean isChinese(char c) {
        return (19968 <= c && c <= 40869 && getPinyinCode(c) > 0) || 12295 == c;
    }

    private static int getPinyinCode(char c) {
        int i = c - 19968;
        if (i < 0 || i >= 7000) {
            if (7000 <= i && i < 14000) {
                return decodeIndex(PinyinCode2.PINYIN_CODE_PADDING, PinyinCode2.PINYIN_CODE, i - CodeState.CODES.CODE_URL_NOT_EXSITS);
            }
            return decodeIndex(PinyinCode3.PINYIN_CODE_PADDING, PinyinCode3.PINYIN_CODE, i - 14000);
        }
        return decodeIndex(PinyinCode1.PINYIN_CODE_PADDING, PinyinCode1.PINYIN_CODE, i);
    }

    private static short decodeIndex(byte[] bArr, byte[] bArr2, int i) {
        int i2 = i % 8;
        short s = (short) (bArr2[i] & 255);
        return (bArr[i / 8] & PinyinData.BIT_MASKS[i2]) != 0 ? (short) (s | 256) : s;
    }
}
