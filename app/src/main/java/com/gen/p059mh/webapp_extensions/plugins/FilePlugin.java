package com.gen.p059mh.webapp_extensions.plugins;

import android.support.p002v4.app.NotificationCompat;
import android.util.Base64;
import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.utils.Logger;
import com.gen.p059mh.webapps.utils.Utils;
import com.google.gson.Gson;
import com.tomatolive.library.utils.LogConstants;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* renamed from: com.gen.mh.webapp_extensions.plugins.FilePlugin */
/* loaded from: classes2.dex */
public class FilePlugin extends Plugin {
    Map<String, FileProcess> processers = new HashMap();
    FileProcess access = new FileProcess() { // from class: com.gen.mh.webapp_extensions.plugins.FilePlugin.1
        @Override // com.gen.p059mh.webapp_extensions.plugins.FilePlugin.FileProcess
        public int process(List list, Map map) {
            ((Number) list.get(1)).intValue();
            if (new File(FilePlugin.this.getWebViewFragment().realPath((String) list.get(0))).exists()) {
                return 0;
            }
            map.put(NotificationCompat.CATEGORY_MESSAGE, "NoTargetFile");
            return 301;
        }
    };
    FileProcess appendFile = new FileProcess() { // from class: com.gen.mh.webapp_extensions.plugins.FilePlugin.2
        @Override // com.gen.p059mh.webapp_extensions.plugins.FilePlugin.FileProcess
        public int process(List list, Map map) {
            Object obj = list.get(1);
            Map map2 = (Map) list.get(2);
            String realPath = FilePlugin.this.getWebViewFragment().realPath((String) list.get(0));
            byte[] dataFrom = Utils.dataFrom(obj);
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(new File(realPath), true);
                fileOutputStream.write(dataFrom);
                fileOutputStream.close();
                return 0;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                map.put(NotificationCompat.CATEGORY_MESSAGE, "FileNotFound");
                return 305;
            } catch (IOException e2) {
                e2.printStackTrace();
                map.put(NotificationCompat.CATEGORY_MESSAGE, "FailToAdd");
                return 304;
            }
        }
    };
    FileProcess copyFile = new FileProcess() { // from class: com.gen.mh.webapp_extensions.plugins.FilePlugin.3
        @Override // com.gen.p059mh.webapp_extensions.plugins.FilePlugin.FileProcess
        public int process(List list, Map map) {
            String realPath = FilePlugin.this.getWebViewFragment().realPath((String) list.get(0));
            String realPath2 = FilePlugin.this.getWebViewFragment().realPath((String) list.get(1));
            try {
                FileInputStream fileInputStream = new FileInputStream(realPath);
                FileOutputStream fileOutputStream = new FileOutputStream(realPath2);
                Utils.pipe(fileInputStream, fileOutputStream);
                fileInputStream.close();
                fileOutputStream.close();
                return 0;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                map.put(NotificationCompat.CATEGORY_MESSAGE, "No Source File");
                return 312;
            } catch (IOException e2) {
                e2.printStackTrace();
                map.put(NotificationCompat.CATEGORY_MESSAGE, "Fail When Copy");
                return 313;
            }
        }
    };
    FileProcess mkdir = new FileProcess() { // from class: com.gen.mh.webapp_extensions.plugins.FilePlugin.4
        @Override // com.gen.p059mh.webapp_extensions.plugins.FilePlugin.FileProcess
        public int process(List list, Map map) {
            Map map2 = (Map) list.get(1);
            String realPath = FilePlugin.this.getWebViewFragment().realPath((String) list.get(0));
            boolean booleanValue = map2.containsKey("recursive") ? ((Boolean) map2.get("recursive")).booleanValue() : false;
            File file = new File(realPath);
            if (!file.exists()) {
                if (booleanValue) {
                    file.mkdirs();
                } else {
                    file.mkdir();
                }
            }
            return 0;
        }
    };
    FileProcess readdir = new FileProcess() { // from class: com.gen.mh.webapp_extensions.plugins.FilePlugin.5
        @Override // com.gen.p059mh.webapp_extensions.plugins.FilePlugin.FileProcess
        public int process(List list, Map map) {
            String str = (String) list.get(0);
            Map map2 = (Map) list.get(1);
            boolean booleanValue = map2.containsKey("withFileTypes") ? ((Boolean) map2.get("withFileTypes")).booleanValue() : false;
            String realPath = FilePlugin.this.getWebViewFragment().realPath(str);
            if (new File(realPath).exists()) {
                map.put("result", FilePlugin.this.readdir(realPath, booleanValue));
                return 0;
            }
            map.put(NotificationCompat.CATEGORY_MESSAGE, "No Dir found");
            return 331;
        }
    };
    FileProcess readFile = new FileProcess() { // from class: com.gen.mh.webapp_extensions.plugins.FilePlugin.6
        @Override // com.gen.p059mh.webapp_extensions.plugins.FilePlugin.FileProcess
        public int process(List list, Map map) {
            Map map2 = (Map) list.get(1);
            String realPath = FilePlugin.this.getWebViewFragment().realPath((String) list.get(0));
            try {
                if (!new File(realPath).exists()) {
                    map.put(NotificationCompat.CATEGORY_MESSAGE, "No Target File");
                    return 341;
                }
                FileInputStream fileInputStream = new FileInputStream(realPath);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                Utils.pipe(fileInputStream, byteArrayOutputStream);
                fileInputStream.close();
                map.put("result", Base64.encodeToString(byteArrayOutputStream.toByteArray(), 0));
                byteArrayOutputStream.close();
                return 0;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                map.put(NotificationCompat.CATEGORY_MESSAGE, "No Target File");
                return 341;
            } catch (IOException e2) {
                e2.printStackTrace();
                map.put(NotificationCompat.CATEGORY_MESSAGE, "No Target File");
                return 341;
            }
        }
    };
    FileProcess rename = new FileProcess() { // from class: com.gen.mh.webapp_extensions.plugins.FilePlugin.7
        @Override // com.gen.p059mh.webapp_extensions.plugins.FilePlugin.FileProcess
        public int process(List list, Map map) {
            if (new File(FilePlugin.this.getWebViewFragment().realPath((String) list.get(0))).renameTo(new File(FilePlugin.this.getWebViewFragment().realPath((String) list.get(1))))) {
                return 0;
            }
            map.put(NotificationCompat.CATEGORY_MESSAGE, "Fail To Rename");
            return 451;
        }
    };
    FileProcess rmdir = new FileProcess() { // from class: com.gen.mh.webapp_extensions.plugins.FilePlugin.8
        @Override // com.gen.p059mh.webapp_extensions.plugins.FilePlugin.FileProcess
        public int process(List list, Map map) {
            File file = new File(FilePlugin.this.getWebViewFragment().realPath((String) list.get(0)));
            if (file.exists()) {
                if (file.isDirectory()) {
                    return file.delete() ? 0 : -1;
                }
                map.put(NotificationCompat.CATEGORY_MESSAGE, "Target File is not dir");
                return 452;
            }
            map.put(NotificationCompat.CATEGORY_MESSAGE, "No Target File");
            return 451;
        }
    };
    FileProcess rmdir2 = new FileProcess() { // from class: com.gen.mh.webapp_extensions.plugins.FilePlugin.9
        @Override // com.gen.p059mh.webapp_extensions.plugins.FilePlugin.FileProcess
        public int process(List list, Map map) {
            Map map2 = (Map) list.get(1);
            String realPath = FilePlugin.this.getWebViewFragment().realPath((String) list.get(0));
            boolean booleanValue = map2.containsKey("recursive") ? ((Boolean) map2.get("recursive")).booleanValue() : false;
            File file = new File(FilePlugin.this.getWebViewFragment().realPath(realPath));
            if (file.exists()) {
                if (file.isDirectory()) {
                    return booleanValue ? Utils.deleteDirWithFile(file) ? 0 : -1 : file.delete() ? 0 : -1;
                }
                map.put(NotificationCompat.CATEGORY_MESSAGE, "Target File is not dir");
                return 452;
            }
            map.put(NotificationCompat.CATEGORY_MESSAGE, "No Target File");
            return 451;
        }
    };
    FileProcess stat = new FileProcess() { // from class: com.gen.mh.webapp_extensions.plugins.FilePlugin.10
        @Override // com.gen.p059mh.webapp_extensions.plugins.FilePlugin.FileProcess
        public int process(List list, Map map) {
            String str = (String) list.get(0);
            Logger.m4113i("path：" + str);
            String realPath = FilePlugin.this.getWebViewFragment().realPath(str);
            if (new File(realPath).exists()) {
                HashMap hashMap = new HashMap();
                if (FilePlugin.this.getStat(realPath, hashMap) == 0) {
                    HashMap hashMap2 = new HashMap();
                    hashMap2.put("dev", Integer.valueOf((String) hashMap.get("dev")));
                    hashMap2.put("ino", Integer.valueOf((String) hashMap.get("ino")));
                    hashMap2.put("mode", Integer.valueOf((String) hashMap.get("mode")));
                    hashMap2.put("nlink", Integer.valueOf((String) hashMap.get("nlink")));
                    hashMap2.put("uid", Integer.valueOf((String) hashMap.get("uid")));
                    hashMap2.put("gid", Integer.valueOf((String) hashMap.get("gid")));
                    hashMap2.put("rdev", Integer.valueOf((String) hashMap.get("rdev")));
                    hashMap2.put("size", Long.valueOf((String) hashMap.get("size")));
                    hashMap2.put("blocks", Integer.valueOf((String) hashMap.get("blocks")));
                    hashMap2.put("blksize", Integer.valueOf((String) hashMap.get("blksize")));
                    hashMap2.put("atimeMs", Float.valueOf(Float.valueOf((String) hashMap.get("atime")).floatValue() * 1000.0f));
                    hashMap2.put("mtimeMs", Float.valueOf(Float.valueOf((String) hashMap.get("mtime")).floatValue() * 1000.0f));
                    String str2 = (String) hashMap.get("ctime");
                    hashMap2.put("ctimeMs", Float.valueOf(Float.valueOf(str2).floatValue() * 1000.0f));
                    hashMap2.put("birthtimeMs", Float.valueOf(Float.valueOf(str2).floatValue() * 1000.0f));
                    map.put("result", hashMap2);
                    return 0;
                }
                map.put(NotificationCompat.CATEGORY_MESSAGE, map.get(NotificationCompat.CATEGORY_MESSAGE));
                return 462;
            }
            map.put(NotificationCompat.CATEGORY_MESSAGE, "No target File");
            return 461;
        }
    };
    FileProcess unlink = new FileProcess() { // from class: com.gen.mh.webapp_extensions.plugins.FilePlugin.11
        @Override // com.gen.p059mh.webapp_extensions.plugins.FilePlugin.FileProcess
        public int process(List list, Map map) {
            if (new File(FilePlugin.this.getWebViewFragment().realPath((String) list.get(0))).delete()) {
                return 0;
            }
            map.put(NotificationCompat.CATEGORY_MESSAGE, "Fail when unlink");
            return 471;
        }
    };
    FileProcess writeFile = new FileProcess() { // from class: com.gen.mh.webapp_extensions.plugins.FilePlugin.12
        @Override // com.gen.p059mh.webapp_extensions.plugins.FilePlugin.FileProcess
        public int process(List list, Map map) {
            Object obj = list.get(1);
            Map map2 = (Map) list.get(2);
            String realPath = FilePlugin.this.getWebViewFragment().realPath((String) list.get(0));
            byte[] dataFrom = Utils.dataFrom(obj);
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(new File(realPath));
                fileOutputStream.write(dataFrom);
                fileOutputStream.close();
                return 0;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                map.put(NotificationCompat.CATEGORY_MESSAGE, "FileNotFound");
                return 481;
            } catch (IOException e2) {
                e2.printStackTrace();
                map.put(NotificationCompat.CATEGORY_MESSAGE, "FailToAdd");
                return 481;
            }
        }
    };
    FileProcess absolutePath = new FileProcess() { // from class: com.gen.mh.webapp_extensions.plugins.FilePlugin.13
        @Override // com.gen.p059mh.webapp_extensions.plugins.FilePlugin.FileProcess
        public int process(List list, Map map) {
            map.put("result", FilePlugin.this.getWebViewFragment().realPath((String) list.get(0)));
            return 0;
        }
    };

    /* renamed from: com.gen.mh.webapp_extensions.plugins.FilePlugin$FileProcess */
    /* loaded from: classes2.dex */
    private interface FileProcess {
        int process(List list, Map map);
    }

    native int getStat(String str, Map map);

    public native List<Map> readdir(String str, boolean z);

    static {
        System.loadLibrary("filesystem");
    }

    public FilePlugin() {
        super("filesystem");
        this.processers.put("access", this.access);
        this.processers.put("appendFile", this.appendFile);
        this.processers.put("copyFile", this.copyFile);
        this.processers.put("mkdir", this.mkdir);
        this.processers.put("readdir", this.readdir);
        this.processers.put("readFile", this.readFile);
        this.processers.put("rename", this.rename);
        this.processers.put("rmdir", this.rmdir);
        this.processers.put("stat", this.stat);
        this.processers.put("unlink", this.unlink);
        this.processers.put("writeFile", this.writeFile);
        this.processers.put("absolutePath", this.absolutePath);
        this.processers.put("rmdir2", this.rmdir2);
        new Integer(1);
    }

    @Override // com.gen.p059mh.webapps.Plugin
    public void process(String str, Plugin.PluginCallback pluginCallback) {
        Map map = (Map) new Gson().fromJson(str, (Class<Object>) Map.class);
        String str2 = (String) map.get(LogConstants.FOLLOW_OPERATION_TYPE);
        List list = (List) map.get("params");
        if (this.processers.containsKey(str2)) {
            HashMap hashMap = new HashMap();
            hashMap.put("code", Integer.valueOf(this.processers.get(str2).process(list, hashMap)));
            pluginCallback.response(hashMap);
            return;
        }
        HashMap hashMap2 = new HashMap();
        hashMap2.put("code", 404);
        hashMap2.put(NotificationCompat.CATEGORY_MESSAGE, "No Method");
        pluginCallback.response(hashMap2);
    }
}
