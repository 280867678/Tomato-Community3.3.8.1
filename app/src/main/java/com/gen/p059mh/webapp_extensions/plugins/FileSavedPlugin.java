package com.gen.p059mh.webapp_extensions.plugins;

import android.os.Build;
import android.support.p002v4.app.NotificationCompat;
import android.text.TextUtils;
import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.listener.UploadListener;
import com.gen.p059mh.webapps.utils.Logger;
import com.gen.p059mh.webapps.utils.UploadUtils;
import com.google.gson.Gson;
import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import com.one.tomato.entity.C2516Ad;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import com.tomatolive.library.utils.LogConstants;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import okhttp3.Response;

/* renamed from: com.gen.mh.webapp_extensions.plugins.FileSavedPlugin */
/* loaded from: classes2.dex */
public class FileSavedPlugin extends Plugin {
    File rootFile;
    String rootPath = "app:///save_files";
    Gson gson = new Gson();

    public FileSavedPlugin() {
        super("saved.file");
    }

    @Override // com.gen.p059mh.webapps.Plugin
    public void ready() {
        super.ready();
    }

    @Override // com.gen.p059mh.webapps.Plugin
    public void process(String str, Plugin.PluginCallback pluginCallback) {
        Logger.m4112i("save.file", str);
        if (this.rootFile == null) {
            this.rootFile = new File(transferPath(this.rootPath));
            if (!this.rootFile.exists()) {
                this.rootFile.mkdir();
            }
        }
        Map<String, Object> map = (Map) this.gson.fromJson(str, (Class<Object>) Map.class);
        if (map != null) {
            String obj = map.get(LogConstants.FOLLOW_OPERATION_TYPE).toString();
            char c = 65535;
            switch (obj.hashCode()) {
                case -934610812:
                    if (obj.equals("remove")) {
                        c = 1;
                        break;
                    }
                    break;
                case -838595071:
                    if (obj.equals("upload")) {
                        c = 7;
                        break;
                    }
                    break;
                case -244213580:
                    if (obj.equals("upload.ext")) {
                        c = '\b';
                        break;
                    }
                    break;
                case 100589:
                    if (obj.equals("env")) {
                        c = 6;
                        break;
                    }
                    break;
                case 3237038:
                    if (obj.equals("info")) {
                        c = 3;
                        break;
                    }
                    break;
                case 3322014:
                    if (obj.equals(C2516Ad.TYPE_LIST)) {
                        c = 2;
                        break;
                    }
                    break;
                case 3522941:
                    if (obj.equals("save")) {
                        c = 0;
                        break;
                    }
                    break;
                case 111449576:
                    if (obj.equals("unzip")) {
                        c = 4;
                        break;
                    }
                    break;
                case 1033411199:
                    if (obj.equals("abort.upload")) {
                        c = 5;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    actionSave(map, pluginCallback);
                    return;
                case 1:
                    actionRemove(map, pluginCallback);
                    return;
                case 2:
                    actionList(map, pluginCallback);
                    return;
                case 3:
                    actionInfo(map, pluginCallback);
                    return;
                case 4:
                    actionUnzip(map, pluginCallback);
                    return;
                case 5:
                    actionAbortUpload(map, pluginCallback);
                    return;
                case 6:
                    actionEnv(map, pluginCallback);
                    return;
                case 7:
                    actionUpload(map, pluginCallback);
                    return;
                case '\b':
                    actionUploadExt(map, pluginCallback);
                    return;
                default:
                    pluginCallback.response(null);
                    return;
            }
        }
        pluginCallback.response(null);
    }

    void actionSave(Map<String, Object> map, Plugin.PluginCallback pluginCallback) {
        File file;
        boolean z;
        String str = null;
        String transferPath = map.get("filePath") != null ? transferPath(map.get("filePath").toString()) : null;
        if (map.get("tempFilePath") != null) {
            str = transferPath(map.get("tempFilePath").toString());
        }
        boolean z2 = true;
        if (!TextUtils.isEmpty(transferPath)) {
            file = new File(transferPath);
            z = false;
        } else if (!TextUtils.isEmpty(str)) {
            file = new File(str);
            z = true;
        } else {
            backSuccess(false, "file path is null", pluginCallback);
            return;
        }
        if (!file.exists()) {
            backSuccess(false, pluginCallback);
            return;
        }
        String createFileName = createFileName(file.getName());
        File file2 = new File(this.rootFile, createFileName);
        if (z) {
            z2 = file.renameTo(file2);
        } else {
            try {
                copyFile(file, file2);
            } catch (IOException e) {
                e.printStackTrace();
                z2 = false;
            }
        }
        HashMap hashMap = new HashMap();
        hashMap.put("success", Boolean.valueOf(z2));
        if (z2) {
            hashMap.put("savedFilePath", this.rootPath + "/" + createFileName);
        } else {
            hashMap.put(NotificationCompat.CATEGORY_MESSAGE, "save fail");
        }
        pluginCallback.response(hashMap);
    }

    void actionRemove(Map<String, Object> map, Plugin.PluginCallback pluginCallback) {
        if (map.get("filePath") == null) {
            backSuccess(false, pluginCallback);
            return;
        }
        File file = new File(transferPath(map.get("filePath").toString()));
        if (file.exists()) {
            backSuccess(file.delete(), pluginCallback);
        } else {
            backSuccess(false, pluginCallback);
        }
    }

    void actionList(Map<String, Object> map, Plugin.PluginCallback pluginCallback) {
        File file = new File(transferPath(this.rootPath));
        if (file.exists() && file.isDirectory()) {
            File[] listFiles = file.listFiles();
            ArrayList arrayList = new ArrayList();
            for (File file2 : listFiles) {
                arrayList.add(this.rootPath + "/" + file2.getName());
            }
            HashMap hashMap = new HashMap();
            hashMap.put("success", true);
            hashMap.put("fileList", arrayList);
            pluginCallback.response(hashMap);
            return;
        }
        backSuccess(false, pluginCallback);
    }

    void actionInfo(Map<String, Object> map, Plugin.PluginCallback pluginCallback) {
        long createTimeFromName;
        if (map.get("filePath") == null) {
            backSuccess(false, pluginCallback);
            return;
        }
        File file = new File(transferPath(map.get("filePath").toString()));
        if (file.exists()) {
            try {
                if (Build.VERSION.SDK_INT >= 26) {
                    createTimeFromName = Files.readAttributes(file.toPath(), BasicFileAttributes.class, new LinkOption[0]).creationTime().toMillis();
                } else {
                    createTimeFromName = getCreateTimeFromName(file.getName());
                }
            } catch (Exception unused) {
                createTimeFromName = getCreateTimeFromName(file.getName());
            }
            long length = file.length();
            String str = null;
            if (map.get("digestAlgorithm") != null) {
                try {
                    str = createFileHash(map.get("digestAlgorithm").toString(), new FileInputStream(file));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            HashMap hashMap = new HashMap();
            hashMap.put("size", Long.valueOf(length));
            hashMap.put("createTime", Long.valueOf(createTimeFromName));
            if (str != null) {
                hashMap.put("digest", str);
            }
            hashMap.put("success", true);
            pluginCallback.response(hashMap);
            return;
        }
        backSuccess(false, pluginCallback);
    }

    void actionUnzip(Map<String, Object> map, Plugin.PluginCallback pluginCallback) {
        String transferPath = transferPath(map.get("zipFilePath").toString());
        String transferPath2 = transferPath(map.get("targetPath").toString());
        if (transferPath.endsWith(".zip")) {
            File file = new File(transferPath);
            if (!file.exists()) {
                backSuccess(false, pluginCallback);
                return;
            }
            try {
                backSuccess(unZip(file, transferPath2), pluginCallback);
                return;
            } catch (RuntimeException e) {
                backSuccess(false, e.getMessage(), pluginCallback);
                return;
            }
        }
        backSuccess(false, "the file not zip file", pluginCallback);
    }

    void actionAbortUpload(Map<String, Object> map, Plugin.PluginCallback pluginCallback) {
        if (map.get(DatabaseFieldConfigLoader.FIELD_NAME_ID) == null) {
            backSuccess(false, "no task id", pluginCallback);
            return;
        }
        backSuccess(UploadUtils.getInstance().cancelRequest(map.get(DatabaseFieldConfigLoader.FIELD_NAME_ID).toString()), "no task can abort", pluginCallback);
    }

    void actionEnv(Map<String, Object> map, Plugin.PluginCallback pluginCallback) {
        HashMap hashMap = new HashMap();
        hashMap.put("success", true);
        hashMap.put("path", this.rootPath);
        pluginCallback.response(hashMap);
    }

    void actionUpload(final Map<String, Object> map, final Plugin.PluginCallback pluginCallback) {
        try {
            final String obj = map.get("url").toString();
            final File file = new File(transferPath(map.get("filePath").toString()));
            if (!file.exists()) {
                backSuccess(false, pluginCallback);
                return;
            }
            final String obj2 = map.get("name").toString();
            final String obj3 = map.get(DatabaseFieldConfigLoader.FIELD_NAME_ID).toString();
            new Thread(new Runnable() { // from class: com.gen.mh.webapp_extensions.plugins.-$$Lambda$FileSavedPlugin$KPnrN3LXA-w-kyTAB2iCxKUfcp8
                @Override // java.lang.Runnable
                public final void run() {
                    FileSavedPlugin.this.lambda$actionUpload$0$FileSavedPlugin(obj, file, obj2, map, obj3, pluginCallback);
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
            backSuccess(false, e.getMessage(), pluginCallback);
        }
    }

    public /* synthetic */ void lambda$actionUpload$0$FileSavedPlugin(String str, File file, String str2, Map map, final String str3, final Plugin.PluginCallback pluginCallback) {
        UploadUtils.getInstance().upload(str, file, str2, map.get("header"), map.get("formData"), map.get("timeout"), str3, new UploadListener() { // from class: com.gen.mh.webapp_extensions.plugins.FileSavedPlugin.1
            @Override // com.gen.p059mh.webapps.listener.UploadListener
            public void onProgress(long j, long j2) {
                Logger.m4113i("upload  task " + str3 + " progress" + j + " / " + j2);
            }

            @Override // com.gen.p059mh.webapps.listener.UploadListener
            public void onResponse(Response response) {
                try {
                    int code = response.code();
                    String string = response.body().string();
                    Logger.m4115e("data:" + string);
                    HashMap hashMap = new HashMap();
                    hashMap.put("success", true);
                    hashMap.put(AopConstants.APP_PROPERTIES_KEY, (Map) FileSavedPlugin.this.gson.fromJson(string, (Class<Object>) Map.class));
                    hashMap.put("statusCode", Integer.valueOf(code));
                    pluginCallback.response(hashMap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    void actionUploadExt(final Map<String, Object> map, final Plugin.PluginCallback pluginCallback) {
        try {
            final String obj = map.get("url").toString();
            final File file = new File(transferPath(map.get("filePath").toString()));
            if (!file.exists()) {
                backSuccess(false, pluginCallback);
                return;
            }
            final String obj2 = map.get(DatabaseFieldConfigLoader.FIELD_NAME_ID).toString();
            final String obj3 = map.get("method").toString();
            final String obj4 = map.get("range").toString();
            new Thread(new Runnable() { // from class: com.gen.mh.webapp_extensions.plugins.-$$Lambda$FileSavedPlugin$UIRmtvlyP2aZsGNvv-asGJuRQ-8
                @Override // java.lang.Runnable
                public final void run() {
                    FileSavedPlugin.this.lambda$actionUploadExt$1$FileSavedPlugin(obj, file, map, obj3, obj4, obj2, pluginCallback);
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
            backSuccess(false, e.getMessage(), pluginCallback);
        }
    }

    public /* synthetic */ void lambda$actionUploadExt$1$FileSavedPlugin(String str, File file, Map map, String str2, String str3, final String str4, final Plugin.PluginCallback pluginCallback) {
        UploadUtils.getInstance().upload2(str, file, map.get("header"), map.get("timeout"), str2, str3, str4, new UploadListener() { // from class: com.gen.mh.webapp_extensions.plugins.FileSavedPlugin.2
            @Override // com.gen.p059mh.webapps.listener.UploadListener
            public void onProgress(long j, long j2) {
                Logger.m4113i("upload  task " + str4 + " progress" + j + " / " + j2);
            }

            @Override // com.gen.p059mh.webapps.listener.UploadListener
            public void onResponse(Response response) {
                try {
                    int code = response.code();
                    String string = response.body().string();
                    Logger.m4113i("data:" + string);
                    HashMap hashMap = new HashMap();
                    hashMap.put("success", true);
                    hashMap.put(AopConstants.APP_PROPERTIES_KEY, (Map) FileSavedPlugin.this.gson.fromJson(string, (Class<Object>) Map.class));
                    hashMap.put("statusCode", Integer.valueOf(code));
                    pluginCallback.response(hashMap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    void backSuccess(boolean z, Plugin.PluginCallback pluginCallback) {
        backSuccess(z, "no file exist", pluginCallback);
    }

    void backSuccess(boolean z, String str, Plugin.PluginCallback pluginCallback) {
        HashMap hashMap = new HashMap();
        hashMap.put("success", Boolean.valueOf(z));
        if (!z) {
            hashMap.put(NotificationCompat.CATEGORY_MESSAGE, str);
        }
        pluginCallback.response(hashMap);
    }

    public boolean unZip(File file, String str) throws RuntimeException {
        ZipFile zipFile;
        if (!file.exists()) {
            throw new RuntimeException("the source file not exist");
        }
        try {
            try {
                zipFile = new ZipFile(file);
            } catch (Throwable th) {
                th = th;
                zipFile = null;
            }
        } catch (Exception e) {
            e = e;
        }
        try {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            boolean z = false;
            while (entries.hasMoreElements()) {
                ZipEntry nextElement = entries.nextElement();
                if (nextElement.isDirectory()) {
                    new File(str + "/" + nextElement.getName()).mkdirs();
                } else {
                    File file2 = new File(str + "/" + nextElement.getName());
                    if (!file2.getParentFile().exists()) {
                        file2.getParentFile().mkdirs();
                    }
                    file2.createNewFile();
                    InputStream inputStream = zipFile.getInputStream(nextElement);
                    FileOutputStream fileOutputStream = new FileOutputStream(file2);
                    byte[] bArr = new byte[4096];
                    while (true) {
                        int read = inputStream.read(bArr);
                        if (read == -1) {
                            break;
                        }
                        fileOutputStream.write(bArr, 0, read);
                    }
                    fileOutputStream.close();
                    inputStream.close();
                }
                z = true;
            }
            try {
                zipFile.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            return z;
        } catch (Exception e3) {
            e = e3;
            throw new RuntimeException("unzip error from ZipUtils", e);
        } catch (Throwable th2) {
            th = th2;
            if (zipFile != null) {
                try {
                    zipFile.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
            }
            throw th;
        }
    }

    private void copyFile(File file, File file2) throws IOException {
        FileChannel fileChannel;
        FileChannel fileChannel2 = null;
        try {
            FileChannel channel = new FileInputStream(file).getChannel();
            try {
                fileChannel = new FileOutputStream(file2).getChannel();
                try {
                    fileChannel.transferFrom(channel, 0L, channel.size());
                    channel.close();
                } catch (IOException e) {
                    fileChannel2 = channel;
                    e = e;
                    try {
                        e.printStackTrace();
                        fileChannel2.close();
                        fileChannel.close();
                    } catch (Throwable th) {
                        th = th;
                        fileChannel2.close();
                        fileChannel.close();
                        throw th;
                    }
                } catch (Throwable th2) {
                    fileChannel2 = channel;
                    th = th2;
                    fileChannel2.close();
                    fileChannel.close();
                    throw th;
                }
            } catch (IOException e2) {
                fileChannel2 = channel;
                e = e2;
                fileChannel = null;
            } catch (Throwable th3) {
                fileChannel2 = channel;
                th = th3;
                fileChannel = null;
            }
        } catch (IOException e3) {
            e = e3;
            fileChannel = null;
        } catch (Throwable th4) {
            th = th4;
            fileChannel = null;
        }
        fileChannel.close();
    }

    private String createFileHash(String str, InputStream inputStream) {
        MessageDigest messageDigest;
        DigestInputStream digestInputStream;
        byte[] bArr = new byte[0];
        DigestInputStream digestInputStream2 = null;
        try {
            try {
                try {
                    messageDigest = MessageDigest.getInstance(str);
                    digestInputStream = new DigestInputStream(inputStream, messageDigest);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (NoSuchAlgorithmException e2) {
                e = e2;
            }
        } catch (Throwable th) {
            th = th;
        }
        try {
            bArr = messageDigest.digest();
            if (inputStream != null) {
                inputStream.close();
            }
            digestInputStream.close();
        } catch (NoSuchAlgorithmException e3) {
            e = e3;
            digestInputStream2 = digestInputStream;
            e.printStackTrace();
            if (inputStream != null) {
                inputStream.close();
            }
            if (digestInputStream2 != null) {
                digestInputStream2.close();
            }
            return byteToHexString(bArr);
        } catch (Throwable th2) {
            th = th2;
            digestInputStream2 = digestInputStream;
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                    throw th;
                }
            }
            if (digestInputStream2 != null) {
                digestInputStream2.close();
            }
            throw th;
        }
        return byteToHexString(bArr);
    }

    private String byteToHexString(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bArr) {
            sb.append(Integer.toHexString(b & 255));
        }
        return sb.toString();
    }

    private String createFileName(String str) {
        return "file_" + new Date().getTime() + (new Random().nextInt(900) + 100) + "_" + str;
    }

    private long getCreateTimeFromName(String str) {
        String str2 = str.split("_")[1];
        return Long.valueOf(str2.substring(0, str2.length() - 3)).longValue();
    }

    private String transferPath(String str) {
        return this.webViewFragment.realPath(str);
    }
}
