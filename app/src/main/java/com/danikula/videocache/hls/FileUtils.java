package com.danikula.videocache.hls;

import com.danikula.videocache.entity.HlsPlayConstant;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/* loaded from: classes2.dex */
public class FileUtils {
    public static void readM3U8Buf(StringBuffer stringBuffer, String str) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(str);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
        parseM3U8Buf(bufferedReader, stringBuffer);
        bufferedReader.close();
        fileInputStream.close();
    }

    public static void readM3U8Buf(StringBuffer stringBuffer, byte[] bArr) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(bArr)));
        parseM3U8Buf(bufferedReader, stringBuffer);
        bufferedReader.close();
    }

    public static void parseM3U8Buf(BufferedReader bufferedReader, StringBuffer stringBuffer) throws IOException {
        String readLine = bufferedReader.readLine();
        while (readLine != null) {
            if (readLine != null && readLine.trim().toLowerCase().endsWith(".ts")) {
                stringBuffer.append(readLine);
                stringBuffer.append(System.getProperty("line.separator"));
            } else if (readLine != null && readLine.trim().toUpperCase().startsWith("#EXTINF")) {
                stringBuffer.append(readLine);
                stringBuffer.append(System.getProperty("line.separator"));
            } else {
                if (readLine.startsWith("#EXT-SECRET-KEY:")) {
                    HlsPlayConstant.putHlsKey(readLine.substring(16));
                } else if (readLine.startsWith("#EXT-SECRET-KEY-INDEX:")) {
                    HlsPlayConstant.putHlsKeyIdx(Integer.parseInt(readLine.substring(22)));
                }
                stringBuffer.append(readLine);
                stringBuffer.append(System.getProperty("line.separator"));
            }
            readLine = bufferedReader.readLine();
        }
    }
}
