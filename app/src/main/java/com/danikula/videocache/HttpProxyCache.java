package com.danikula.videocache;

import android.net.Uri;
import android.text.TextUtils;
import com.danikula.videocache.entity.HlsPlay;
import com.danikula.videocache.entity.HlsPlayConstant;
import com.danikula.videocache.file.FileCache;
import com.danikula.videocache.file.FileNameUtil;
import com.danikula.videocache.file.strategy.StorageCacheManager;
import com.danikula.videocache.hls.FileUtils;
import com.danikula.videocache.net.NetworkUtils;
import com.danikula.videocache.net.OkHttpUrlSource;
import com.google.android.exoplayer2.source.hls.playlist.HlsMediaPlaylist;
import com.google.android.exoplayer2.source.hls.playlist.HlsPlaylist;
import com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistParser;
import com.zzbwuhan.beard.BCrypto;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import timber.log.Timber;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes2.dex */
public class HttpProxyCache extends ProxyCache {
    private final FileCache cache;
    private Config config;
    private Map<String, Object> hlsPlayMap;
    private CacheListener listener;
    public final OkHttpUrlSource source;

    public HttpProxyCache(OkHttpUrlSource okHttpUrlSource, FileCache fileCache, Map<String, Object> map, Config config) {
        super(okHttpUrlSource, fileCache);
        this.cache = fileCache;
        this.source = okHttpUrlSource;
        this.hlsPlayMap = map;
        this.config = config;
    }

    public void registerCacheListener(CacheListener cacheListener) {
        this.listener = cacheListener;
    }

    public void processRequest(GetRequest getRequest, Socket socket) throws IOException, ProxyCacheException {
        Timber.tag("TTT").mo18i("HttpProxyCache==>> processRequest : 开始请求", new Object[0]);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(socket.getOutputStream());
        bufferedOutputStream.write(newResponseHeaders(getRequest).getBytes("UTF-8"));
        Timber.tag("TTT").mo18i("HttpProxyCache==>> processRequest : 将组装的头信息输出到socket的输出流中", new Object[0]);
        long j = getRequest.rangeOffset;
        if (!StorageCacheManager.getInstance().hasStoragePermissions(this.source.sourceInfoStorage.getContext())) {
            Timber.Tree tag = Timber.tag("TTT");
            tag.mo18i("HttpProxyCache==>> processRequest : has no permission," + this.source.getUrl(), new Object[0]);
            responseWithoutCache(bufferedOutputStream, j);
        } else if (HlsPlayConstant.isReloadM3U8(this.hlsPlayMap)) {
            Timber.Tree tag2 = Timber.tag("TTT");
            tag2.mo18i("HttpProxyCache==>> processRequest : reload m3u8 from net," + this.source.getUrl(), new Object[0]);
            responseWithoutCache(bufferedOutputStream, j);
        } else if (!NetworkUtils.isAvailable(this.source.sourceInfoStorage.getContext()) || !NetworkUtils.isConnected(this.source.sourceInfoStorage.getContext())) {
            Timber.Tree tag3 = Timber.tag("TTT");
            tag3.mo18i("HttpProxyCache==>> processRequest : load m3u8 not net," + this.source.getUrl(), new Object[0]);
            if (isUseCache()) {
                if (!FileNameUtil.isPlayCache(this.config.sourceInfoStorage.getContext(), this.config.cacheRoot.getAbsolutePath())) {
                    responseWithCache(bufferedOutputStream);
                    HlsPlayConstant.putPlayCurrentId(this.hlsPlayMap, this.source.getUrl());
                    return;
                } else if (HlsPlayConstant.isNextTsPlay(this.hlsPlayMap, this.source.getUrl())) {
                    responseWithCache(bufferedOutputStream);
                    HlsPlayConstant.putPlayCurrentId(this.hlsPlayMap, this.source.getUrl());
                    return;
                } else {
                    this.source.close();
                    return;
                }
            }
            this.source.close();
        } else if (StorageCacheManager.getInstance().useNotCache()) {
            Timber.Tree tag4 = Timber.tag("TTT");
            tag4.mo18i("HttpProxyCache==>> processRequest : use storage less," + this.source.getUrl(), new Object[0]);
            responseWithoutCache(bufferedOutputStream, j);
        } else if (isUseCache()) {
            Timber.Tree tag5 = Timber.tag("TTT");
            tag5.mo18i("HttpProxyCache==>> processRequest : has cache," + this.source.getUrl(), new Object[0]);
            responseWithCache(bufferedOutputStream);
            HlsPlayConstant.putPlayCurrentId(this.hlsPlayMap, this.source.getUrl());
        } else {
            Timber.Tree tag6 = Timber.tag("TTT");
            tag6.mo18i("HttpProxyCache==>> processRequest : has not cache," + this.source.getUrl(), new Object[0]);
            responseWithOnline1(bufferedOutputStream, j);
        }
    }

    private String newResponseHeaders(GetRequest getRequest) throws IOException, ProxyCacheException {
        StringBuilder sb = new StringBuilder();
        sb.append(getRequest.partial ? "HTTP/1.1 206 PARTIAL CONTENT\n" : "HTTP/1.1 200 OK\n");
        sb.append("Accept-Ranges: bytes\n");
        sb.append("\n");
        return sb.toString();
    }

    private boolean isUseCache() throws ProxyCacheException {
        long available = this.cache.available();
        if (available == 0 || available < 10) {
            return false;
        }
        return this.cache.isCompleted();
    }

    private void responseWithCache(OutputStream outputStream) throws ProxyCacheException, IOException {
        byte[] bArr = new byte[102400];
        if (!FileNameUtil.isM3U8File(this.source.getUrl())) {
            long j = 0;
            while (true) {
                int read = this.cache.read(bArr, j, bArr.length);
                if (read == -1) {
                    break;
                }
                responseIsTs(outputStream, bArr, read, j, HlsPlayConstant.getKeyPtr().longValue());
                j += read;
            }
        } else {
            responseIsM3U8(outputStream);
        }
        outputStream.flush();
    }

    private void responseWithoutCache(OutputStream outputStream, long j) throws ProxyCacheException, IOException {
        OkHttpUrlSource okHttpUrlSource = new OkHttpUrlSource(this.source);
        try {
            okHttpUrlSource.open((int) j);
            byte[] bArr = new byte[102400];
            long j2 = j;
            byte[] bArr2 = new byte[0];
            while (true) {
                int read = okHttpUrlSource.read(bArr);
                if (read != -1) {
                    if (FileNameUtil.isM3U8File(this.source.getUrl())) {
                        bArr2 = addBytes(bArr2, bArr, read);
                    } else {
                        responseIsTs(outputStream, bArr, read, j2, HlsPlayConstant.getKeyPtr().longValue());
                    }
                    j2 += read;
                } else {
                    responseIsM3U8(outputStream, bArr2);
                    outputStream.flush();
                    return;
                }
            }
        } finally {
            okHttpUrlSource.close();
        }
    }

    private void responseWithOnline1(OutputStream outputStream, long j) throws ProxyCacheException, IOException {
        byte[] bArr = new byte[102400];
        File file = this.cache.getFile();
        if (file.getName().contains("seg-1-v1-a1.ts") && file.length() > 10) {
            Timber.tag("TTT").mo18i("HttpProxyCache==>> responseWithOnline1 : 读取已缓存的第一个ts文件内容", new Object[0]);
            while (true) {
                int read = this.cache.read(bArr, j, bArr.length);
                if (read == -1) {
                    break;
                }
                responseIsTs(outputStream, bArr, read, j, HlsPlayConstant.getKeyPtr().longValue());
                j += read;
            }
            outputStream.flush();
        }
        while (true) {
            int read2 = read(bArr, j, bArr.length);
            if (read2 == -1) {
                break;
            }
            if (!FileNameUtil.isM3U8File(this.source.getUrl())) {
                responseIsTs(outputStream, bArr, read2, j, HlsPlayConstant.getKeyPtr().longValue());
            }
            j += read2;
        }
        if (FileNameUtil.isM3U8File(this.source.getUrl())) {
            responseIsM3U8Online(outputStream);
        }
        outputStream.flush();
    }

    private void responseIsM3U8Online(OutputStream outputStream) throws IOException {
        responseIsM3U8(outputStream, true, null);
    }

    private void responseIsM3U8(OutputStream outputStream) throws IOException {
        responseIsM3U8(outputStream, false, null);
    }

    private void responseIsM3U8(OutputStream outputStream, byte[] bArr) throws IOException {
        responseIsM3U8(outputStream, false, bArr);
    }

    private void responseIsM3U8(OutputStream outputStream, boolean z, byte[] bArr) throws IOException {
        if (FileNameUtil.isM3U8File(this.source.getUrl())) {
            StringBuffer stringBuffer = new StringBuffer();
            if (bArr == null) {
                FileUtils.readM3U8Buf(stringBuffer, this.cache.file.getAbsolutePath());
            } else {
                FileUtils.readM3U8Buf(stringBuffer, bArr);
            }
            String stringBuffer2 = stringBuffer.toString();
            Timber.Tree tag = Timber.tag("TTT");
            tag.mo18i("HttpProxyCache==>> responseIsM3U8 : m3u8\n" + stringBuffer2, new Object[0]);
            parseM3U8(stringBuffer2.getBytes());
            if (HlsPlayConstant.getHlsPlayTsCount(this.hlsPlayMap) <= 1 && HlsPlayConstant.getHLSPlayEroor()) {
                this.listener.onFileLoadIsWrong();
                Timber.tag("TTT").mo21e("HttpProxyCache==>> responseIsM3U8 : segment is one ", new Object[0]);
                outputStream.flush();
                return;
            }
            outputStream.write(stringBuffer.toString().getBytes("UTF-8"));
        }
        if (z) {
            StorageCacheManager.getInstance().runningCacheTs(this.cache.file.getAbsolutePath());
        }
    }

    private void responseIsTs(OutputStream outputStream, byte[] bArr, int i, long j, long j2) throws IOException {
        if (j2 == 0) {
            outputStream.write(bArr, 0, i);
        } else {
            outputStream.write(bArr, 0, BCrypto.decodeVideoBuf2(bArr, i, j2, (int) j));
        }
    }

    public byte[] addBytes(byte[] bArr, byte[] bArr2, int i) {
        byte[] bArr3 = new byte[bArr.length + i];
        System.arraycopy(bArr, 0, bArr3, 0, bArr.length);
        System.arraycopy(bArr2, 0, bArr3, bArr.length, i);
        return bArr3;
    }

    @Override // com.danikula.videocache.ProxyCache
    protected void onCachePercentsAvailableChanged(int i) {
        CacheListener cacheListener = this.listener;
        if (cacheListener == null || i != 100) {
            return;
        }
        cacheListener.onCacheAvailable(this.cache.file, this.source.getUrl(), i);
        HlsPlayConstant.putPlayCurrentId(this.hlsPlayMap, this.source.getUrl());
    }

    private void parseM3U8(byte[] bArr) {
        if (bArr == null) {
            return;
        }
        try {
            HlsPlaylist mo6240parse = new HlsPlaylistParser().mo6240parse(Uri.parse(this.source.getUrl()), (InputStream) new ByteArrayInputStream(bArr));
            HlsMediaPlaylist hlsMediaPlaylist = null;
            if (mo6240parse instanceof HlsMediaPlaylist) {
                hlsMediaPlaylist = (HlsMediaPlaylist) mo6240parse;
            }
            List<HlsMediaPlaylist.Segment> list = hlsMediaPlaylist.segments;
            if (list != null && list.size() != 0) {
                int i = 1;
                if (list.size() == 1 && list.get(0).url.startsWith("http")) {
                    HlsPlayConstant.putHLSPlayError(true);
                }
                String rootUrl = FileNameUtil.getRootUrl(hlsMediaPlaylist.baseUri);
                HlsPlayConstant.putHlsPlayTsCount(this.hlsPlayMap, list.size());
                for (HlsMediaPlaylist.Segment segment : list) {
                    String str = segment.url;
                    String fileNameKey = FileNameUtil.getFileNameKey(str);
                    Timber.Tree tag = Timber.tag("TTT");
                    tag.mo18i("HttpProxyCache==>> parseM3U8: Segment\nsegmentUrl = " + str + "\nsegmentName = " + fileNameKey, new Object[0]);
                    HlsPlay hlsPlay = new HlsPlay();
                    hlsPlay.rootUrl = rootUrl;
                    hlsPlay.url = hlsMediaPlaylist.baseUri;
                    String str2 = segment.fullSegmentEncryptionKeyUri;
                    if (!TextUtils.isEmpty(str2)) {
                        String fileNameKey2 = FileNameUtil.getFileNameKey(str2);
                        if (!this.hlsPlayMap.containsKey(fileNameKey2)) {
                            this.hlsPlayMap.put(fileNameKey2, hlsPlay);
                        }
                    }
                    hlsPlay.currentNum = i;
                    Timber.Tree tag2 = Timber.tag("TTT");
                    tag2.mo18i("HttpProxyCache==>> parseM3U8: HlsPlay \nrootUrl = " + hlsPlay.rootUrl + "\nurl = " + hlsPlay.url + "\ncurrentNum = " + hlsPlay.currentNum, new Object[0]);
                    this.hlsPlayMap.put(fileNameKey, hlsPlay);
                    i++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
