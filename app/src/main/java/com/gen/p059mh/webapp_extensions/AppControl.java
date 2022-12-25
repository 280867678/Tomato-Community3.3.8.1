package com.gen.p059mh.webapp_extensions;

import android.os.Handler;
import com.gen.p059mh.webapp_extensions.fragments.WebAppFragment;
import com.gen.p059mh.webapp_extensions.listener.AppControlListener;
import com.gen.p059mh.webapp_extensions.listener.DOWNLOAD_MODE;
import com.gen.p059mh.webapp_extensions.listener.DownloadListener;
import com.gen.p059mh.webapp_extensions.modules.AppInfo;
import com.gen.p059mh.webapp_extensions.utils.MultiDownload;
import com.gen.p059mh.webapps.listener.AppResponse;
import com.gen.p059mh.webapps.listener.IErrorInfo;
import com.gen.p059mh.webapps.listener.OnAppInfoResponse;
import com.gen.p059mh.webapps.modules.ErrorInfoImpl;
import com.gen.p059mh.webapps.modules.LoadErrorInfo;
import com.gen.p059mh.webapps.utils.Logger;
import com.gen.p059mh.webapps.utils.MD5Utils;
import com.gen.p059mh.webapps.utils.Utils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/* renamed from: com.gen.mh.webapp_extensions.AppControl */
/* loaded from: classes2.dex */
public class AppControl {
    private File appDir;
    private AppInfo appInfo;
    private DownloadListener downloadListener;
    DownloadThread downloadThread;
    private List<File> filelist;
    private Handler handler;
    AppControlListener listener;
    private String onlineVersion;
    MultiDownload zipDownloader;
    OnAppInfoResponse requestInfoHandler = new OnAppInfoResponse() { // from class: com.gen.mh.webapp_extensions.AppControl.1
        @Override // com.gen.p059mh.webapps.listener.OnAppInfoResponse
        public void onComplete(AppResponse appResponse) {
            AppControl.this.onlineVersion = appResponse.version;
            StringBuilder sb = new StringBuilder();
            sb.append("isNeedUpdate ");
            sb.append(!AppControl.this.onlineVersion.equals(AppControl.this.appInfo.getVersion()));
            Logger.m4113i(sb.toString());
            if (!AppControl.this.onlineVersion.equals(AppControl.this.appInfo.getVersion())) {
                AppControl.this.appInfo.setNeedUpdate(1);
            }
            if (WebAppFragment.download_mode != DOWNLOAD_MODE.VERSION || !AppControl.this.onlineVersion.equals(AppControl.this.appInfo.getVersion())) {
                AppControl.this.appInfo.setTitle(appResponse.title);
                AppControl.this.appInfo.save();
                AppControlListener appControlListener = AppControl.this.listener;
                if (appControlListener != null) {
                    appControlListener.onReceiveInfo(appResponse);
                }
                if (AppControl.this.appInfo.getVersion() != null && AppControl.this.appInfo.getVersion().length() != 0) {
                    AppControl.this.downloadApp(appResponse.url);
                    return;
                }
                AppControl.this.listener.onUpdate();
                AppControl.this.downloadAppZip(appResponse.zipUrl);
                return;
            }
            AppControl.this.appInfo.setTitle(appResponse.title);
            AppControl.this.appInfo.save();
            AppControl.this.listener.onReady();
        }

        @Override // com.gen.p059mh.webapps.listener.OnAppInfoResponse
        public void onFail(IErrorInfo iErrorInfo) {
            AppControlListener appControlListener = AppControl.this.listener;
            if (appControlListener != null) {
                appControlListener.onFail(iErrorInfo);
            }
        }
    };
    private MultiDownload.OnRequestListener onDownloadListener = new MultiDownload.OnRequestListener() { // from class: com.gen.mh.webapp_extensions.AppControl.3
        @Override // com.gen.p059mh.webapp_extensions.utils.MultiDownload.OnRequestListener
        public void onFail(final IErrorInfo iErrorInfo) {
            AppControl.this.handler.post(new Runnable() { // from class: com.gen.mh.webapp_extensions.AppControl.3.1
                @Override // java.lang.Runnable
                public void run() {
                    if (AppControl.this.downloadListener != null) {
                        AppControl.this.downloadListener.onDownloadFail(iErrorInfo);
                    }
                }
            });
        }

        @Override // com.gen.p059mh.webapp_extensions.utils.MultiDownload.OnRequestListener
        public void onComplete(MultiDownload multiDownload) {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(multiDownload.loadData());
            try {
                ZipInputStream zipInputStream = new ZipInputStream(byteArrayInputStream);
                byte[] bArr = new byte[512];
                while (true) {
                    ZipEntry nextEntry = zipInputStream.getNextEntry();
                    if (nextEntry == null) {
                        break;
                    }
                    String name = nextEntry.getName();
                    if (nextEntry.isDirectory()) {
                        File file = new File(AppControl.this.getAppDir().getAbsolutePath() + "/" + name);
                        if (!file.exists()) {
                            file.mkdirs();
                        } else if (!file.isDirectory()) {
                            file.delete();
                            file.mkdirs();
                        }
                    } else {
                        File file2 = new File(AppControl.this.getAppDir().getAbsolutePath() + "/" + name);
                        File parentFile = file2.getParentFile();
                        if (!parentFile.exists()) {
                            parentFile.mkdirs();
                        } else if (!parentFile.isDirectory()) {
                            parentFile.delete();
                            parentFile.mkdirs();
                        }
                        FileOutputStream fileOutputStream = new FileOutputStream(file2);
                        while (true) {
                            int read = zipInputStream.read(bArr);
                            if (read == -1) {
                                break;
                            }
                            fileOutputStream.write(bArr, 0, read);
                        }
                        fileOutputStream.close();
                        zipInputStream.closeEntry();
                    }
                }
                zipInputStream.close();
                byteArrayInputStream.close();
                AppControl.this.writeMarkFile();
                AppControl.this.appInfo.setVersion(AppControl.this.onlineVersion);
                AppControl.this.appInfo.setNeedUpdate(0);
                AppControl.this.appInfo.save();
                if (AppControl.this.listener == null) {
                    return;
                }
                AppControl.this.listener.onReady();
            } catch (Exception e) {
                e.printStackTrace();
                AppControlListener appControlListener = AppControl.this.listener;
                if (appControlListener == null) {
                    return;
                }
                appControlListener.onFail(ErrorInfoImpl.newInstance(203, e.getMessage()));
            }
        }

        @Override // com.gen.p059mh.webapp_extensions.utils.MultiDownload.OnRequestListener
        public void onProgress(long j, long j2) {
            AppControlListener appControlListener = AppControl.this.listener;
            if (appControlListener != null) {
                if (j > j2) {
                    appControlListener.onProgress(j2, j2);
                } else {
                    appControlListener.onProgress(j, j2);
                }
            }
        }
    };

    public void setDownloadListener(DownloadListener downloadListener) {
        this.downloadListener = downloadListener;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.gen.mh.webapp_extensions.AppControl$DownloadThread */
    /* loaded from: classes2.dex */
    public class DownloadThread extends Thread {
        public String url;

        DownloadThread() {
        }

        byte[] readAll(InputStream inputStream) throws IOException {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bArr = new byte[1048576];
            while (true) {
                int read = inputStream.read(bArr);
                if (read > 0) {
                    byteArrayOutputStream.write(bArr, 0, read);
                } else {
                    return byteArrayOutputStream.toByteArray();
                }
            }
        }

        void checkDir(String str) {
            File file = new File(str.substring(0, str.lastIndexOf("/")));
            if (!file.exists()) {
                file.mkdirs();
            }
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            try {
                ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = new ArrayList();
                String[] split = new String(readAll(new URL(this.url + "/update.txt?r=" + Math.random()).openStream())).split("\n");
                int length = split.length;
                char c = 0;
                int i = 0;
                long j = 0;
                while (i < length) {
                    String[] split2 = split[i].trim().split("##");
                    if (split2.length >= 3) {
                        String str = split2[c];
                        String str2 = split2[1];
                        long parseLong = Long.parseLong(split2[2]);
                        File file = new File(AppControl.this.getAppDir().getPath() + str2);
                        arrayList2.add(file.getAbsolutePath());
                        if (!file.exists() || !str.equals(MD5Utils.encode(new FileInputStream(file)))) {
                            j += parseLong;
                            arrayList.add(str2);
                        }
                    }
                    i++;
                    c = 0;
                }
                if (isInterrupted()) {
                    return;
                }
                if (arrayList.size() == 0) {
                    AppControl.this.complete();
                } else {
                    AppControl.this.listener.onProgress(0L, j);
                    int size = arrayList.size();
                    long j2 = 0;
                    for (int i2 = 0; i2 < size; i2++) {
                        if (isInterrupted()) {
                            return;
                        }
                        String str3 = (String) arrayList.get(i2);
                        String str4 = this.url + str3 + "?r=" + Math.random();
                        URL url = new URL(str4);
                        String str5 = AppControl.this.getAppDir().getPath() + str3;
                        checkDir(str5);
                        Logger.m4112i("downLoadUpdate", "Url = " + str4);
                        FileOutputStream fileOutputStream = new FileOutputStream(str5);
                        InputStream openStream = url.openStream();
                        byte[] bArr = new byte[1048576];
                        while (true) {
                            int read = openStream.read(bArr);
                            if (read <= 0) {
                                break;
                            }
                            fileOutputStream.write(bArr, 0, read);
                            j2 += read;
                            if (j2 > j) {
                                AppControl.this.listener.onProgress(j, j);
                            } else {
                                AppControl.this.listener.onProgress(j2, j);
                            }
                        }
                        if (isInterrupted()) {
                            return;
                        }
                    }
                    AppControl.this.complete();
                }
                AppControl.this.deleteAppsPath(arrayList2);
            } catch (Exception e) {
                AppControl.this.listener.onFail(LoadErrorInfo.DOWNLOAD_FILE_FAIL);
                e.printStackTrace();
            }
        }
    }

    public void deleteAppsPath(List<String> list) {
        ArrayList arrayList = new ArrayList();
        this.filelist = new ArrayList();
        this.filelist = getFileList(WebAppFragment.appRootDir.getAbsolutePath() + "/" + this.appInfo.getAppID());
        Iterator<File> it2 = this.filelist.iterator();
        while (it2.hasNext()) {
            File next = it2.next();
            if (!next.isDirectory() && isDeletePath(list, next.getAbsolutePath())) {
                arrayList.add(next);
                it2.remove();
            }
        }
        deleteFile(arrayList);
        deleteDir(this.filelist);
    }

    public List<File> getFileList(String str) {
        File[] listFiles = new File(str).listFiles();
        if (listFiles != null) {
            for (int i = 0; i < listFiles.length; i++) {
                if (listFiles[i].isDirectory()) {
                    this.filelist.add(listFiles[i]);
                    getFileList(listFiles[i].getAbsolutePath());
                } else {
                    this.filelist.add(listFiles[i]);
                }
            }
        }
        return this.filelist;
    }

    public boolean isDeletePath(List<String> list, String str) {
        String str2 = WebAppFragment.appRootDir.getAbsolutePath() + "/" + this.appInfo.getAppID() + "/";
        if (!str.equals(str2 + WebAppFragment.fileLoadImg)) {
            if (!str.equals(str2 + WebAppFragment.fileLoadBg)) {
                if (list != null && list.size() != 0) {
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).equals(str)) {
                            return false;
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }

    public void deleteFile(List<File> list) {
        while (list.size() > 0) {
            list.get(0).delete();
            list.remove(0);
        }
    }

    public void deleteDir(List<File> list) {
        File file;
        File file2 = null;
        for (int i = 0; i < list.size(); i++) {
            if (file2 == null) {
                file = list.get(i);
            } else {
                if (file2.isDirectory() && !list.get(i).getAbsolutePath().startsWith(file2.getAbsolutePath())) {
                    file2.delete();
                }
                file = list.get(i);
            }
            file2 = file;
        }
    }

    public void setListener(AppControlListener appControlListener) {
        this.listener = appControlListener;
    }

    public File getAppDir() {
        if (this.appDir == null) {
            this.appDir = new File(WebAppFragment.appRootDir.getAbsolutePath() + "/" + this.appInfo.getAppID());
            if (!this.appDir.exists()) {
                this.appDir.mkdirs();
            }
        }
        return this.appDir;
    }

    public AppControl(String str) {
        this.appInfo = AppInfo.fromAppID(str);
    }

    public AppInfo getAppInfo() {
        return this.appInfo;
    }

    public File iconFile() {
        if (this.appInfo.getAppID() != null) {
            return new File(getAppDir().getAbsolutePath() + "/.appicon.img");
        }
        return null;
    }

    public File markFile() {
        return new File(getAppDir().getAbsolutePath() + "/.mrk");
    }

    public boolean weakUpdate() {
        if (DOWNLOAD_MODE.WEAK_UPDATE == WebAppFragment.download_mode) {
            if (!markFile().exists()) {
                forceUpdate();
                return true;
            } else if (this.appInfo.isNeedUpdate() == 1) {
                forceUpdate();
                return true;
            } else {
                loadInfo();
                return false;
            }
        }
        forceUpdate();
        return true;
    }

    public void complete() {
        writeMarkFile();
        this.appInfo.setVersion(this.onlineVersion);
        this.appInfo.setNeedUpdate(0);
        this.appInfo.save();
        this.listener.onReady();
    }

    public void forceUpdate() {
        this.listener.onStart();
        DownloadListener downloadListener = this.downloadListener;
        if (downloadListener != null) {
            downloadListener.onRequestAppInfo(this.appInfo.getAppID(), this.requestInfoHandler);
        }
    }

    public void loadInfo() {
        DownloadListener downloadListener = this.downloadListener;
        if (downloadListener != null) {
            downloadListener.onRequestAppInfo(this.appInfo.getAppID(), this.requestInfoHandler);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void writeMarkFile() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(markFile());
            fileOutputStream.write(this.onlineVersion.getBytes());
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cancel() {
        DownloadThread downloadThread = this.downloadThread;
        if (downloadThread != null) {
            downloadThread.interrupt();
        }
        MultiDownload multiDownload = this.zipDownloader;
        if (multiDownload != null) {
            multiDownload.cancel();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void downloadApp(String str) {
        this.downloadThread = new DownloadThread();
        DownloadThread downloadThread = this.downloadThread;
        downloadThread.url = str;
        downloadThread.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void downloadAppZip(final String str) {
        this.handler.post(new Runnable() { // from class: com.gen.mh.webapp_extensions.AppControl.2
            @Override // java.lang.Runnable
            public void run() {
                try {
                    AppControl.this.zipDownloader = new MultiDownload(new URL(str), AppControl.this.getAppDir().getAbsolutePath());
                    AppControl.this.zipDownloader.setOnRequestListener(AppControl.this.onDownloadListener);
                    AppControl.this.zipDownloader.start();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void deleteApp() {
        if (getAppDir() != null) {
            Logger.m4113i("delete db record");
            getAppInfo().remove();
        }
        if (getAppDir() != null) {
            Logger.m4113i("delete file");
            if (!this.appDir.exists()) {
                return;
            }
            Utils.deleteDirWithFile(this.appDir);
            this.appDir.delete();
        }
    }
}
