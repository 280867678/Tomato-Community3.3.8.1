package com.one.tomato.utils;

import android.text.TextUtils;
import com.one.tomato.utils.encrypt.AESUtil;
import com.one.tomato.utils.encrypt.Base64Util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/* loaded from: classes3.dex */
public class AppSecretUtil {
    public static final String[] apiPaths = {"/order", "/pay", "/query"};

    public static String decodeResponse(String str) {
        char[] charArray = str.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            charArray[i] = (char) (charArray[i] ^ 12029);
        }
        return String.valueOf(charArray);
    }

    public static boolean severApiFilter(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        for (String str2 : apiPaths) {
            if (str.equals(str2)) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:56:0x0083 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0079 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:66:0x0071 -> B:22:0x0074). Please submit an issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void decodeS3Image(File file, File file2) {
        FileOutputStream fileOutputStream;
        FileInputStream fileInputStream;
        FileInputStream fileInputStream2 = null;
        try {
            try {
                fileInputStream = new FileInputStream(file);
                try {
                    fileOutputStream = new FileOutputStream(file2);
                } catch (Exception e) {
                    e = e;
                    fileOutputStream = null;
                } catch (Throwable th) {
                    th = th;
                    fileOutputStream = null;
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } catch (Exception e3) {
            e = e3;
            fileOutputStream = null;
        } catch (Throwable th2) {
            th = th2;
            fileOutputStream = null;
            fileInputStream = null;
        }
        try {
            byte[] bArr = new byte[1];
            byte b = -1;
            while (true) {
                int read = fileInputStream.read(bArr);
                if (read == -1) {
                    break;
                }
                if (b == -1) {
                    b = bArr[0];
                    bArr = new byte[4096];
                } else {
                    byte[] bArr2 = new byte[read];
                    for (int i = 0; i < read; i++) {
                        bArr2[i] = (byte) (bArr[i] ^ b);
                    }
                    fileOutputStream.write(bArr2);
                }
            }
            fileInputStream.close();
            fileOutputStream.flush();
            fileOutputStream.close();
            try {
                fileInputStream.close();
            } catch (Exception e4) {
                e4.printStackTrace();
            }
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e5) {
            e = e5;
            fileInputStream2 = fileInputStream;
            try {
                e.printStackTrace();
                if (fileInputStream2 != null) {
                    try {
                        fileInputStream2.close();
                    } catch (Exception e6) {
                        e6.printStackTrace();
                    }
                }
                if (fileOutputStream == null) {
                    return;
                }
                fileOutputStream.flush();
                fileOutputStream.close();
            } catch (Throwable th3) {
                th = th3;
                fileInputStream = fileInputStream2;
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (Exception e7) {
                        e7.printStackTrace();
                    }
                }
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.flush();
                        fileOutputStream.close();
                    } catch (Exception e8) {
                        e8.printStackTrace();
                    }
                }
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            if (fileInputStream != null) {
            }
            if (fileOutputStream != null) {
            }
            throw th;
        }
    }

    public static String encodePath(String str) {
        String str2;
        try {
            return new String(Base64Util.base64EncodeStr((AESUtil.encryptAES(str + (char) 1 + RandomUtil.getRandom(1), System.currentTimeMillis() + RandomUtil.getRandom(3)) + (char) 1 + Base64Util.base64EncodeStr(str2, 8)).replace("\n", ""), 8)).replace("\n", "") + ".do";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        } catch (InvalidAlgorithmParameterException e2) {
            e2.printStackTrace();
            return "";
        } catch (InvalidKeyException e3) {
            e3.printStackTrace();
            return "";
        } catch (NoSuchAlgorithmException e4) {
            e4.printStackTrace();
            return "";
        } catch (BadPaddingException e5) {
            e5.printStackTrace();
            return "";
        } catch (IllegalBlockSizeException e6) {
            e6.printStackTrace();
            return "";
        } catch (NoSuchPaddingException e7) {
            e7.printStackTrace();
            return "";
        }
    }
}
