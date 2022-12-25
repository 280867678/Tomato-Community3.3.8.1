package com.tomatolive.library.utils;

import android.database.Cursor;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;

/* loaded from: classes4.dex */
public class IOUtils {
    static final int DEFAULT_BUFFER_SIZE = 8192;

    private IOUtils() {
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    public static void closeQuietly(Cursor cursor) {
        if (cursor != null) {
            try {
                cursor.close();
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    public static void copyStream(InputStream inputStream, OutputStream outputStream) throws IOException {
        copyStream(inputStream, outputStream, 8192);
    }

    public static void copyStream(InputStream inputStream, OutputStream outputStream, int i) throws IOException {
        byte[] bArr = new byte[i];
        while (true) {
            int read = inputStream.read(bArr);
            if (read != -1) {
                outputStream.write(bArr, 0, read);
            } else {
                outputStream.flush();
                return;
            }
        }
    }

    public static byte[] toByteArray(InputStream inputStream) throws Exception {
        ByteArrayOutputStream byteArrayOutputStream;
        Throwable th;
        if (inputStream == null) {
            return null;
        }
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                byte[] bArr = new byte[1024];
                while (true) {
                    int read = inputStream.read(bArr);
                    if (-1 != read) {
                        byteArrayOutputStream.write(bArr, 0, read);
                    } else {
                        byte[] byteArray = byteArrayOutputStream.toByteArray();
                        closeQuietly(inputStream);
                        closeQuietly(byteArrayOutputStream);
                        return byteArray;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                closeQuietly(inputStream);
                closeQuietly(byteArrayOutputStream);
                throw th;
            }
        } catch (Throwable th3) {
            byteArrayOutputStream = null;
            th = th3;
        }
    }

    /* JADX WARN: Not initialized variable reg: 3, insn: 0x0031: MOVE  (r0 I:??[OBJECT, ARRAY]) = (r3 I:??[OBJECT, ARRAY]), block:B:20:0x0031 */
    public static String toString(InputStream inputStream) {
        InputStreamReader inputStreamReader;
        Closeable closeable;
        Closeable closeable2 = null;
        try {
            try {
                StringWriter stringWriter = new StringWriter();
                char[] cArr = new char[1024];
                inputStreamReader = new InputStreamReader(inputStream);
                while (true) {
                    try {
                        int read = inputStreamReader.read(cArr);
                        if (read != -1) {
                            stringWriter.write(cArr, 0, read);
                        } else {
                            String stringWriter2 = stringWriter.toString();
                            closeQuietly(inputStreamReader);
                            return stringWriter2;
                        }
                    } catch (IOException e) {
                        e = e;
                        e.printStackTrace();
                        closeQuietly(inputStreamReader);
                        return null;
                    }
                }
            } catch (Throwable th) {
                th = th;
                closeable2 = closeable;
                closeQuietly(closeable2);
                throw th;
            }
        } catch (IOException e2) {
            e = e2;
            inputStreamReader = null;
        } catch (Throwable th2) {
            th = th2;
            closeQuietly(closeable2);
            throw th;
        }
    }
}
