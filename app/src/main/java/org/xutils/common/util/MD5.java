package org.xutils.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* loaded from: classes4.dex */
public final class MD5 {
    private static final char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private MD5() {
    }

    public static String toHexString(byte[] bArr) {
        if (bArr == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder(bArr.length * 2);
        for (byte b : bArr) {
            sb.append(hexDigits[(b >> 4) & 15]);
            sb.append(hexDigits[b & 15]);
        }
        return sb.toString();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static String md5(File file) throws IOException {
        FileChannel fileChannel;
        FileChannel fileChannel2 = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            FileInputStream fileInputStream = new FileInputStream(file);
            try {
                fileChannel2 = fileInputStream.getChannel();
                messageDigest.update(fileChannel2.map(FileChannel.MapMode.READ_ONLY, 0L, file.length()));
                byte[] digest = messageDigest.digest();
                IOUtil.closeQuietly(fileInputStream);
                IOUtil.closeQuietly(fileChannel2);
                return toHexString(digest);
            } catch (NoSuchAlgorithmException e) {
                e = e;
                fileChannel = fileChannel2;
                fileChannel2 = fileInputStream;
                try {
                    throw new RuntimeException(e);
                } catch (Throwable th) {
                    th = th;
                    IOUtil.closeQuietly(fileChannel2);
                    IOUtil.closeQuietly(fileChannel);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                fileChannel = fileChannel2;
                fileChannel2 = fileInputStream;
                IOUtil.closeQuietly(fileChannel2);
                IOUtil.closeQuietly(fileChannel);
                throw th;
            }
        } catch (NoSuchAlgorithmException e2) {
            e = e2;
            fileChannel = null;
        } catch (Throwable th3) {
            th = th3;
            fileChannel = null;
        }
    }

    public static String md5(String str) {
        try {
            return toHexString(MessageDigest.getInstance("MD5").digest(str.getBytes("UTF-8")));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e2) {
            throw new RuntimeException(e2);
        }
    }
}
