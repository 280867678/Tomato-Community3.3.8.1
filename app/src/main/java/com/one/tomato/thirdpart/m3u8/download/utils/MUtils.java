package com.one.tomato.thirdpart.m3u8.download.utils;

import android.text.TextUtils;
import com.one.tomato.thirdpart.m3u8.download.entity.M3U8;
import com.one.tomato.thirdpart.m3u8.download.entity.M3U8Ts;
import com.one.tomato.utils.FileUtil;
import com.one.tomato.utils.LogUtil;
import com.one.tomato.utils.encrypt.MD5Util;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes3.dex */
public class MUtils {
    public static M3U8 parseIndex(M3U8 m3u8) throws Exception {
        BufferedWriter bufferedWriter;
        BufferedReader bufferedReader;
        String readLine;
        String url = m3u8.getUrl();
        m3u8.setBaseUrl(generateM3U8BaseUrl(url));
        String generateM3U8Folder = generateM3U8Folder(url, !m3u8.isPreDownload());
        File file = new File(generateM3U8Folder);
        if (!file.exists()) {
            file.mkdirs();
        }
        m3u8.setDirFilePath(generateM3U8Folder);
        String generateM3U8FileName = generateM3U8FileName(url);
        File file2 = new File(generateM3U8Folder, generateM3U8FileName);
        boolean z = file2.exists() && file2.length() > 0;
        File file3 = null;
        if (z) {
            LogUtil.m3785e("Download", "本地已存在m3u8文件");
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file2)));
            bufferedWriter = null;
        } else {
            LogUtil.m3783i("Download", "本地没有m3u8文件，网络读流");
            file3 = new File(generateM3U8Folder, generateM3U8FileName + ".download");
            bufferedWriter = new BufferedWriter(new FileWriter(file3));
            bufferedReader = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
        }
        loop0: while (true) {
            float f = 0.0f;
            while (true) {
                readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break loop0;
                }
                if (!z) {
                    bufferedWriter.write(readLine);
                    bufferedWriter.write(System.getProperty("line.separator"));
                }
                if (readLine.startsWith("#")) {
                    if (readLine.startsWith("#EXTINF:")) {
                        String substring = readLine.substring(8);
                        if (substring.endsWith(",")) {
                            substring = substring.substring(0, substring.length() - 1);
                        }
                        f = Float.parseFloat(substring);
                    } else if (readLine.startsWith("#EXT-SECRET-KEY:")) {
                        m3u8.setM3u8Key(readLine.substring(16));
                    } else if (readLine.startsWith("#EXT-SECRET-KEY-INDEX:")) {
                        m3u8.setM3u8KeyIndex(Integer.parseInt(readLine.substring(22)));
                    }
                }
            }
            m3u8.addTs(new M3U8Ts(readLine, f));
        }
        if (!z) {
            if (!file3.renameTo(file2)) {
                throw new Exception("替换m3u8文件失败");
            }
            LogUtil.m3783i("Download", "网络下载m3u8文件到本地成功");
            bufferedWriter.flush();
            bufferedWriter.close();
        }
        bufferedReader.close();
        return m3u8;
    }

    private static String generateM3U8FileName(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        if (str.contains("?")) {
            str = str.substring(0, str.lastIndexOf(63));
        }
        return str.substring(str.lastIndexOf(47) + 1, str.length());
    }

    private static String generateM3U8BaseUrl(String str) {
        return str.substring(0, str.lastIndexOf(47));
    }

    public static String generatePrimaryKeyUrl(String str) {
        if (str.contains("_s3")) {
            List asList = Arrays.asList(str.split("/"));
            int indexOf = asList.indexOf("_s3");
            return "_s3/" + ((String) asList.get(indexOf + 1)) + "/" + ((String) asList.get(indexOf + 2)) + "/" + ((String) asList.get(indexOf + 3));
        }
        return getUrl(str);
    }

    private static String getUrl(String str) {
        int lastIndexOf = str.lastIndexOf(63);
        return lastIndexOf == -1 ? str : str.substring(0, lastIndexOf);
    }

    public static String generateM3U8Folder(String str, boolean z) {
        return FileUtil.getVideoDownDir(z).getPath() + File.separator + MD5Util.md5(generatePrimaryKeyUrl(str));
    }

    public static boolean checkM3U8IsExist(String str, boolean z) {
        String generateM3U8Folder = generateM3U8Folder(str, z);
        File file = new File(generateM3U8Folder, generateM3U8FileName(str));
        if (file.exists() && file.length() != 0) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                while (true) {
                    String readLine = bufferedReader.readLine();
                    if (readLine != null) {
                        if (!readLine.startsWith("#") && !new File(generateM3U8Folder, readLine).exists()) {
                            return false;
                        }
                    } else {
                        bufferedReader.close();
                        return true;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
