package com.gen.p059mh.webapps.utils;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.gen.mh.webapps.utils.FileUtils */
/* loaded from: classes2.dex */
public class FileUtils {
    private static final int BUFFER_SIZE = 8192;
    private static final String TAG = "FileUtils";
    private static final boolean VERBOSE = false;

    private FileUtils() {
    }

    public static boolean fileExists(String str) {
        if (str == null) {
            return false;
        }
        return new File(str).exists();
    }

    public static boolean fileExists(String[] strArr) {
        for (String str : strArr) {
            if (!fileExists(str)) {
                return false;
            }
        }
        return true;
    }

    public static String extractFileName(String str) {
        int lastIndexOf = str.lastIndexOf("/");
        return lastIndexOf < 0 ? str : str.substring(lastIndexOf + 1, str.length());
    }

    public static String extractFileFolder(String str) {
        int length = str.length();
        int lastIndexOf = str.lastIndexOf(47);
        if (lastIndexOf == -1 || str.charAt(length - 1) == '/') {
            return str;
        }
        if (str.indexOf(47) == lastIndexOf && str.charAt(0) == '/') {
            return str.substring(0, lastIndexOf + 1);
        }
        return str.substring(0, lastIndexOf);
    }

    public static String extractFileSuffix(String str) {
        int lastIndexOf;
        return (str != null && (lastIndexOf = str.lastIndexOf(46)) > -1) ? str.substring(lastIndexOf + 1) : "";
    }

    public static String convertToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        while (true) {
            String readLine = bufferedReader.readLine();
            if (readLine != null) {
                sb.append(readLine);
                sb.append("\n");
            } else {
                return sb.toString();
            }
        }
    }

    public static void writeToFile(String str, String str2, List<String> list) throws IOException {
        BufferedOutputStream bufferedOutputStream = null;
        try {
            try {
                File createFile = createFile(str, str2);
                if (createFile == null) {
                    throw new Exception("create file failed");
                }
                BufferedOutputStream bufferedOutputStream2 = new BufferedOutputStream(new FileOutputStream(createFile));
                for (int i = 0; i < list.size(); i++) {
                    try {
                        bufferedOutputStream2.write(list.get(i).getBytes());
                        bufferedOutputStream2.write("\n".getBytes());
                    } catch (Exception e) {
                        e = e;
                        bufferedOutputStream = bufferedOutputStream2;
                        Log.e(TAG, "writeLinesToFile failed!", e);
                        safetyClose(bufferedOutputStream);
                        return;
                    } catch (Throwable th) {
                        th = th;
                        bufferedOutputStream = bufferedOutputStream2;
                        safetyClose(bufferedOutputStream);
                        throw th;
                    }
                }
                safetyClose(bufferedOutputStream2);
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception e2) {
            e = e2;
        }
    }

    public static List<String> readLinesFromFile(String str) throws IOException {
        ArrayList arrayList = new ArrayList();
        File file = new File(str);
        if (!file.exists()) {
            return arrayList;
        }
        BufferedReader bufferedReader = null;
        try {
            try {
                BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                while (true) {
                    try {
                        String readLine = bufferedReader2.readLine();
                        if (readLine == null) {
                            break;
                        }
                        arrayList.add(readLine);
                    } catch (Exception e) {
                        e = e;
                        bufferedReader = bufferedReader2;
                        Log.e(TAG, "readLinesFromFile failed!", e);
                        safetyClose(bufferedReader);
                        return arrayList;
                    } catch (Throwable th) {
                        th = th;
                        bufferedReader = bufferedReader2;
                        safetyClose(bufferedReader);
                        throw th;
                    }
                }
                safetyClose(bufferedReader2);
            } catch (Exception e2) {
                e = e2;
            }
            return arrayList;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static boolean safetyClose(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
                return true;
            } catch (IOException unused) {
                return false;
            }
        }
        return true;
    }

    public static void copyFileOrFolder(String str, String str2) {
        File file = new File(str);
        if (file.isFile()) {
            copyFile(str, str2);
        } else if (!file.isDirectory()) {
        } else {
            copyFolder(str, str2);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:49:0x0072 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0068 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void copyFile(String str, String str2) {
        FileOutputStream fileOutputStream;
        FileInputStream fileInputStream = null;
        try {
            try {
                if (new File(str).exists()) {
                    FileInputStream fileInputStream2 = new FileInputStream(str);
                    try {
                        fileOutputStream = new FileOutputStream(str2);
                    } catch (IOException e) {
                        e = e;
                        fileOutputStream = null;
                    } catch (Throwable th) {
                        th = th;
                        fileOutputStream = null;
                    }
                    try {
                        byte[] bArr = new byte[8192];
                        while (true) {
                            int read = fileInputStream2.read(bArr);
                            if (read == -1) {
                                break;
                            }
                            fileOutputStream.write(bArr, 0, read);
                        }
                        fileOutputStream.flush();
                        fileInputStream = fileInputStream2;
                    } catch (IOException e2) {
                        e = e2;
                        fileInputStream = fileInputStream2;
                        try {
                            e.printStackTrace();
                            if (fileInputStream != null) {
                                try {
                                    fileInputStream.close();
                                } catch (IOException e3) {
                                    e3.printStackTrace();
                                }
                            }
                            if (fileOutputStream == null) {
                                return;
                            }
                            fileOutputStream.close();
                        } catch (Throwable th2) {
                            th = th2;
                            if (fileInputStream != null) {
                                try {
                                    fileInputStream.close();
                                } catch (IOException e4) {
                                    e4.printStackTrace();
                                }
                            }
                            if (fileOutputStream != null) {
                                try {
                                    fileOutputStream.close();
                                } catch (IOException e5) {
                                    e5.printStackTrace();
                                }
                            }
                            throw th;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        fileInputStream = fileInputStream2;
                        if (fileInputStream != null) {
                        }
                        if (fileOutputStream != null) {
                        }
                        throw th;
                    }
                } else {
                    fileOutputStream = null;
                }
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException e6) {
                        e6.printStackTrace();
                    }
                }
            } catch (IOException e7) {
                e7.printStackTrace();
                return;
            }
        } catch (IOException e8) {
            e = e8;
            fileOutputStream = null;
        } catch (Throwable th4) {
            th = th4;
            fileOutputStream = null;
        }
        if (fileOutputStream != null) {
            fileOutputStream.close();
        }
    }

    public static void createNoMediaFile(String str) {
        File createFile = createFile(str, ".nomedia");
        if (createFile != null) {
            try {
                createFile.createNewFile();
            } catch (IOException unused) {
                Log.e(TAG, "createNoMediaFile:  failed to create nomedia file");
            }
        }
    }

    public static File createFile(String str, String str2) {
        if (str == null || str2 == null) {
            return null;
        }
        if (!makeDirectory(str)) {
            Log.e(TAG, "create parent directory failed, " + str);
            return null;
        }
        return new File(str + "/" + str2);
    }

    public static boolean makeDirectory(String str) {
        File file = new File(str);
        return file.exists() ? file.isDirectory() : file.mkdirs();
    }

    public static boolean deleteFile(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return deleteFile(new File(str));
    }

    public static boolean deleteFile(File file) {
        if (file != null) {
            return file.delete();
        }
        return true;
    }

    public static boolean deleteDir(File file) {
        File[] listFiles;
        if (file == null || !file.exists() || !file.isDirectory()) {
            return false;
        }
        for (File file2 : file.listFiles()) {
            if (file2.isDirectory()) {
                deleteDir(file2);
            }
            file2.delete();
        }
        return file.delete();
    }

    public static boolean deleteDir(String str) {
        if (str == null || str.length() <= 0) {
            return false;
        }
        return deleteDir(new File(str));
    }

    /* JADX WARN: Removed duplicated region for block: B:53:0x0114 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:58:0x010a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void copyFolder(String str, String str2) {
        FileInputStream fileInputStream;
        FileOutputStream fileOutputStream;
        File file;
        FileInputStream fileInputStream2 = null;
        try {
            try {
                new File(str2).mkdirs();
                String[] list = new File(str).list();
                fileInputStream = null;
                fileOutputStream = null;
                for (int i = 0; i < list.length; i++) {
                    try {
                        if (str.endsWith(File.separator)) {
                            file = new File(str + list[i]);
                        } else {
                            file = new File(str + File.separator + list[i]);
                        }
                        if (file.isFile()) {
                            FileInputStream fileInputStream3 = new FileInputStream(file);
                            try {
                                FileOutputStream fileOutputStream2 = new FileOutputStream(str2 + "/" + file.getName());
                                try {
                                    byte[] bArr = new byte[8192];
                                    while (true) {
                                        int read = fileInputStream3.read(bArr);
                                        if (read == -1) {
                                            break;
                                        }
                                        fileOutputStream2.write(bArr, 0, read);
                                    }
                                    fileOutputStream2.flush();
                                    fileOutputStream = fileOutputStream2;
                                    fileInputStream = fileInputStream3;
                                } catch (IOException e) {
                                    e = e;
                                    fileOutputStream = fileOutputStream2;
                                    fileInputStream2 = fileInputStream3;
                                    try {
                                        e.printStackTrace();
                                        if (fileInputStream2 != null) {
                                            try {
                                                fileInputStream2.close();
                                            } catch (IOException e2) {
                                                e2.printStackTrace();
                                            }
                                        }
                                        if (fileOutputStream == null) {
                                            return;
                                        }
                                        fileOutputStream.close();
                                    } catch (Throwable th) {
                                        th = th;
                                        fileInputStream = fileInputStream2;
                                        if (fileInputStream != null) {
                                            try {
                                                fileInputStream.close();
                                            } catch (IOException e3) {
                                                e3.printStackTrace();
                                            }
                                        }
                                        if (fileOutputStream != null) {
                                            try {
                                                fileOutputStream.close();
                                            } catch (IOException e4) {
                                                e4.printStackTrace();
                                            }
                                        }
                                        throw th;
                                    }
                                } catch (Throwable th2) {
                                    th = th2;
                                    fileOutputStream = fileOutputStream2;
                                    fileInputStream = fileInputStream3;
                                    if (fileInputStream != null) {
                                    }
                                    if (fileOutputStream != null) {
                                    }
                                    throw th;
                                }
                            } catch (IOException e5) {
                                e = e5;
                            } catch (Throwable th3) {
                                th = th3;
                            }
                        } else if (file.isDirectory()) {
                            copyFolder(str + "/" + list[i], str2 + "/" + list[i]);
                        }
                    } catch (IOException e6) {
                        e = e6;
                        fileInputStream2 = fileInputStream;
                    } catch (Throwable th4) {
                        th = th4;
                    }
                }
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException e7) {
                        e7.printStackTrace();
                    }
                }
            } catch (IOException e8) {
                e8.printStackTrace();
                return;
            }
        } catch (IOException e9) {
            e = e9;
            fileOutputStream = null;
        } catch (Throwable th5) {
            th = th5;
            fileInputStream = null;
            fileOutputStream = null;
        }
        if (fileOutputStream != null) {
            fileOutputStream.close();
        }
    }

    public static List<String> listFolder(String str) {
        ArrayList arrayList = new ArrayList();
        File[] listFiles = new File(str).listFiles();
        for (int i = 0; i < listFiles.length; i++) {
            if (!listFiles[i].isDirectory()) {
                arrayList.add(listFiles[i].getAbsolutePath());
            } else {
                arrayList.addAll(listFolder(listFiles[i].getAbsolutePath()));
            }
        }
        return arrayList;
    }

    public static String getFileNameFromAbsolutePath(String str) {
        int lastIndexOf = str.lastIndexOf("/");
        int lastIndexOf2 = str.lastIndexOf(".");
        if (lastIndexOf != -1 && lastIndexOf2 != -1) {
            return str.substring(lastIndexOf + 1, lastIndexOf2);
        }
        if (lastIndexOf == -1 && !str.contains("/") && lastIndexOf2 != -1) {
            return str.substring(0, lastIndexOf2);
        }
        return null;
    }

    public static void recursionDeleteFile(File file) {
        if (file.isDirectory()) {
            File[] listFiles = file.listFiles();
            if (listFiles == null || listFiles.length == 0) {
                file.delete();
                return;
            }
            for (File file2 : listFiles) {
                recursionDeleteFile(file2);
            }
            file.delete();
            return;
        }
        file.delete();
    }

    public static List<String> getAbsolutePathlist(String str) {
        ArrayList arrayList = new ArrayList();
        File file = new File(str);
        if (file.isDirectory()) {
            File[] listFiles = file.listFiles();
            for (File file2 : listFiles) {
                arrayList.addAll(getAbsolutePathlist(file2.getAbsolutePath()));
            }
        } else {
            arrayList.add(file.getAbsolutePath());
        }
        return arrayList;
    }

    public static String readTextFromFile(File file) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String str = "";
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine != null) {
                    str = str + readLine;
                } else {
                    bufferedReader.close();
                    return str;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static boolean writeTextToFile(File file, String str) {
        try {
            new FileWriter(file).write(str);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void writeFile(String str, String str2, boolean z) {
        try {
            FileWriter fileWriter = new FileWriter(str, z);
            fileWriter.write(str2);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getUriPath(Context context, Uri uri) {
        Uri uri2 = null;
        if ((Build.VERSION.SDK_INT >= 19) && DocumentsContract.isDocumentUri(context, uri)) {
            if (isExternalStorageDocument(uri)) {
                String[] split = DocumentsContract.getDocumentId(uri).split(":");
                if ("primary".equalsIgnoreCase(split[0])) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(uri)) {
                return getDataColumn(context, ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(DocumentsContract.getDocumentId(uri)).longValue()), null, null);
            } else {
                if (isMediaDocument(uri)) {
                    String[] split2 = DocumentsContract.getDocumentId(uri).split(":");
                    String str = split2[0];
                    if ("image".equals(str)) {
                        uri2 = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(str)) {
                        uri2 = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(str)) {
                        uri2 = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }
                    return getDataColumn(context, uri2, "_id=?", new String[]{split2[1]});
                }
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            if (isGooglePhotosUri(uri)) {
                return uri.getLastPathSegment();
            }
            return getDataColumn(context, uri, null, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String str, String[] strArr) {
        Cursor cursor;
        try {
            cursor = context.getContentResolver().query(uri, new String[]{"_data"}, str, strArr, null);
            if (cursor != null) {
                try {
                    if (cursor.moveToFirst()) {
                        String string = cursor.getString(cursor.getColumnIndexOrThrow("_data"));
                        if (cursor != null) {
                            cursor.close();
                        }
                        return string;
                    }
                } catch (Throwable th) {
                    th = th;
                    if (cursor != null) {
                        cursor.close();
                    }
                    throw th;
                }
            }
            if (cursor != null) {
                cursor.close();
            }
            return null;
        } catch (Throwable th2) {
            th = th2;
            cursor = null;
        }
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }
}
