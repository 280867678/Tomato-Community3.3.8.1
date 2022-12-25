package org.xutils.http.loader;

import android.text.TextUtils;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Date;
import org.xutils.cache.DiskCacheEntity;
import org.xutils.cache.DiskCacheFile;
import org.xutils.cache.LruDiskCache;
import org.xutils.common.Callback;
import org.xutils.common.util.IOUtil;
import org.xutils.common.util.LogUtil;
import org.xutils.common.util.ProcessLock;
import org.xutils.http.RequestParams;
import org.xutils.http.request.UriRequest;
import org.xutils.p149ex.FileLockedException;
import org.xutils.p149ex.HttpException;

/* loaded from: classes4.dex */
public class FileLoader extends Loader<File> {
    private long contentLength;
    private DiskCacheFile diskCacheFile;
    private boolean isAutoRename;
    private boolean isAutoResume;
    private String responseFileName;
    private String saveFilePath;
    private String tempSaveFilePath;

    @Override // org.xutils.http.loader.Loader
    public void save2Cache(UriRequest uriRequest) {
    }

    @Override // org.xutils.http.loader.Loader
    public Loader<File> newInstance() {
        return new FileLoader();
    }

    @Override // org.xutils.http.loader.Loader
    public void setParams(RequestParams requestParams) {
        if (requestParams != null) {
            this.params = requestParams;
            this.isAutoResume = requestParams.isAutoResume();
            this.isAutoRename = requestParams.isAutoRename();
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.xutils.http.loader.Loader
    /* renamed from: load */
    public File mo6877load(InputStream inputStream) throws Throwable {
        BufferedInputStream bufferedInputStream;
        FileOutputStream fileOutputStream;
        BufferedOutputStream bufferedOutputStream;
        FileInputStream fileInputStream;
        BufferedOutputStream bufferedOutputStream2 = null;
        try {
            File file = new File(this.tempSaveFilePath);
            if (file.isDirectory()) {
                IOUtil.deleteFileOrDir(file);
            }
            if (!file.exists()) {
                File parentFile = file.getParentFile();
                if (!parentFile.exists() && !parentFile.mkdirs()) {
                    throw new IOException("can not create dir: " + parentFile.getAbsolutePath());
                }
            }
            long length = file.length();
            if (this.isAutoResume && length > 0) {
                long j = length - 512;
                try {
                    if (j > 0) {
                        fileInputStream = new FileInputStream(file);
                        try {
                            if (!Arrays.equals(IOUtil.readBytes(inputStream, 0L, 512), IOUtil.readBytes(fileInputStream, j, 512))) {
                                IOUtil.closeQuietly(fileInputStream);
                                IOUtil.deleteFileOrDir(file);
                                throw new RuntimeException("need retry");
                            }
                            this.contentLength -= 512;
                            IOUtil.closeQuietly(fileInputStream);
                        } catch (Throwable th) {
                            th = th;
                            IOUtil.closeQuietly(fileInputStream);
                            throw th;
                        }
                    } else {
                        IOUtil.deleteFileOrDir(file);
                        throw new RuntimeException("need retry");
                    }
                } catch (Throwable th2) {
                    th = th2;
                    fileInputStream = null;
                }
            }
            if (this.isAutoResume) {
                fileOutputStream = new FileOutputStream(file, true);
            } else {
                fileOutputStream = new FileOutputStream(file);
                length = 0;
            }
            long j2 = this.contentLength + length;
            bufferedInputStream = new BufferedInputStream(inputStream);
            try {
                BufferedOutputStream bufferedOutputStream3 = new BufferedOutputStream(fileOutputStream);
                try {
                    if (this.progressHandler != null) {
                        bufferedOutputStream = bufferedOutputStream3;
                        try {
                            if (!this.progressHandler.updateProgress(j2, length, true)) {
                                throw new Callback.CancelledException("download stopped!");
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            bufferedOutputStream2 = bufferedOutputStream;
                            IOUtil.closeQuietly(bufferedInputStream);
                            IOUtil.closeQuietly(bufferedOutputStream2);
                            throw th;
                        }
                    } else {
                        bufferedOutputStream = bufferedOutputStream3;
                    }
                    byte[] bArr = new byte[4096];
                    while (true) {
                        long j3 = length;
                        int read = bufferedInputStream.read(bArr);
                        if (read != -1) {
                            if (!file.getParentFile().exists()) {
                                file.getParentFile().mkdirs();
                                throw new IOException("parent be deleted!");
                            }
                            bufferedOutputStream.write(bArr, 0, read);
                            length = read + j3;
                            if (this.progressHandler != null && !this.progressHandler.updateProgress(j2, length, false)) {
                                bufferedOutputStream.flush();
                                throw new Callback.CancelledException("download stopped!");
                            }
                        } else {
                            bufferedOutputStream.flush();
                            if (this.diskCacheFile != null) {
                                file = this.diskCacheFile.commit();
                            }
                            if (this.progressHandler != null) {
                                this.progressHandler.updateProgress(j2, j3, true);
                            }
                            IOUtil.closeQuietly(bufferedInputStream);
                            IOUtil.closeQuietly(bufferedOutputStream);
                            return autoRename(file);
                        }
                    }
                } catch (Throwable th4) {
                    th = th4;
                    bufferedOutputStream = bufferedOutputStream3;
                }
            } catch (Throwable th5) {
                th = th5;
            }
        } catch (Throwable th6) {
            th = th6;
            bufferedInputStream = null;
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.xutils.http.loader.Loader
    /* renamed from: load */
    public File mo6878load(UriRequest uriRequest) throws Throwable {
        File file;
        File autoRename;
        ProcessLock processLock = null;
        try {
            try {
                this.saveFilePath = this.params.getSaveFilePath();
                this.diskCacheFile = null;
                if (TextUtils.isEmpty(this.saveFilePath)) {
                    if (this.progressHandler != null && !this.progressHandler.updateProgress(0L, 0L, false)) {
                        throw new Callback.CancelledException("download stopped!");
                    }
                    initDiskCacheFile(uriRequest);
                } else {
                    this.tempSaveFilePath = this.saveFilePath + ".tmp";
                }
                if (this.progressHandler != null && !this.progressHandler.updateProgress(0L, 0L, false)) {
                    throw new Callback.CancelledException("download stopped!");
                }
                processLock = ProcessLock.tryLock(this.saveFilePath + "_lock", true);
            } catch (HttpException e) {
                if (e.getCode() == 416) {
                    if (this.diskCacheFile != null) {
                        file = this.diskCacheFile.commit();
                    } else {
                        file = new File(this.tempSaveFilePath);
                    }
                    if (file != null && file.exists()) {
                        if (this.isAutoRename) {
                            this.responseFileName = getResponseFileName(uriRequest);
                        }
                        autoRename = autoRename(file);
                    } else {
                        IOUtil.deleteFileOrDir(file);
                        throw new IllegalStateException("cache file not found" + uriRequest.getCacheKey());
                    }
                } else {
                    throw e;
                }
            }
            if (processLock == null || !processLock.isValid()) {
                throw new FileLockedException("download exists: " + this.saveFilePath);
            }
            this.params = uriRequest.getParams();
            long j = 0;
            if (this.isAutoResume) {
                File file2 = new File(this.tempSaveFilePath);
                long length = file2.length();
                if (length <= 512) {
                    IOUtil.deleteFileOrDir(file2);
                } else {
                    j = length - 512;
                }
            }
            RequestParams requestParams = this.params;
            requestParams.setHeader("RANGE", "bytes=" + j + "-");
            if (this.progressHandler != null && !this.progressHandler.updateProgress(0L, 0L, false)) {
                throw new Callback.CancelledException("download stopped!");
            }
            uriRequest.sendRequest();
            this.contentLength = uriRequest.getContentLength();
            if (this.isAutoRename) {
                this.responseFileName = getResponseFileName(uriRequest);
            }
            if (this.isAutoResume) {
                this.isAutoResume = isSupportRange(uriRequest);
            }
            if (this.progressHandler != null && !this.progressHandler.updateProgress(0L, 0L, false)) {
                throw new Callback.CancelledException("download stopped!");
            }
            if (this.diskCacheFile != null) {
                DiskCacheEntity cacheEntity = this.diskCacheFile.getCacheEntity();
                cacheEntity.setLastAccess(System.currentTimeMillis());
                cacheEntity.setEtag(uriRequest.getETag());
                cacheEntity.setExpires(uriRequest.getExpiration());
                cacheEntity.setLastModify(new Date(uriRequest.getLastModified()));
            }
            autoRename = mo6877load(uriRequest.getInputStream());
            return autoRename;
        } finally {
            IOUtil.closeQuietly((Closeable) null);
            IOUtil.closeQuietly(this.diskCacheFile);
        }
    }

    private void initDiskCacheFile(UriRequest uriRequest) throws Throwable {
        DiskCacheEntity diskCacheEntity = new DiskCacheEntity();
        diskCacheEntity.setKey(uriRequest.getCacheKey());
        this.diskCacheFile = LruDiskCache.getDiskCache(this.params.getCacheDirName()).createDiskCacheFile(diskCacheEntity);
        DiskCacheFile diskCacheFile = this.diskCacheFile;
        if (diskCacheFile != null) {
            this.saveFilePath = diskCacheFile.getAbsolutePath();
            this.tempSaveFilePath = this.saveFilePath;
            this.isAutoRename = false;
            return;
        }
        throw new IOException("create cache file error:" + uriRequest.getCacheKey());
    }

    private File autoRename(File file) {
        if (this.isAutoRename && file.exists() && !TextUtils.isEmpty(this.responseFileName)) {
            File file2 = new File(file.getParent(), this.responseFileName);
            while (file2.exists()) {
                String parent = file.getParent();
                file2 = new File(parent, System.currentTimeMillis() + this.responseFileName);
            }
            return file.renameTo(file2) ? file2 : file;
        } else if (this.saveFilePath.equals(this.tempSaveFilePath)) {
            return file;
        } else {
            File file3 = new File(this.saveFilePath);
            return file.renameTo(file3) ? file3 : file;
        }
    }

    private static String getResponseFileName(UriRequest uriRequest) {
        int indexOf;
        if (uriRequest == null) {
            return null;
        }
        String responseHeader = uriRequest.getResponseHeader("Content-Disposition");
        if (!TextUtils.isEmpty(responseHeader) && (indexOf = responseHeader.indexOf("filename=")) > 0) {
            int i = indexOf + 9;
            int indexOf2 = responseHeader.indexOf(";", i);
            if (indexOf2 < 0) {
                indexOf2 = responseHeader.length();
            }
            if (indexOf2 > i) {
                try {
                    String decode = URLDecoder.decode(responseHeader.substring(i, indexOf2), uriRequest.getParams().getCharset());
                    return (!decode.startsWith("\"") || !decode.endsWith("\"")) ? decode : decode.substring(1, decode.length() - 1);
                } catch (UnsupportedEncodingException e) {
                    LogUtil.m43e(e.getMessage(), e);
                }
            }
        }
        return null;
    }

    private static boolean isSupportRange(UriRequest uriRequest) {
        if (uriRequest == null) {
            return false;
        }
        String responseHeader = uriRequest.getResponseHeader("Accept-Ranges");
        if (responseHeader != null) {
            return responseHeader.contains("bytes");
        }
        String responseHeader2 = uriRequest.getResponseHeader("Content-Range");
        return responseHeader2 != null && responseHeader2.contains("bytes");
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.xutils.http.loader.Loader
    /* renamed from: loadFromCache */
    public File mo6879loadFromCache(DiskCacheEntity diskCacheEntity) throws Throwable {
        return LruDiskCache.getDiskCache(this.params.getCacheDirName()).getDiskCacheFile(diskCacheEntity.getKey());
    }
}
