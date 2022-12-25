package com.tencent.liteav.txcvodplayer.p131a;

import android.net.Uri;
import android.util.Log;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import com.tencent.ijk.media.player.IjkMediaMeta;
import java.io.File;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/* renamed from: com.tencent.liteav.txcvodplayer.a.b */
/* loaded from: classes3.dex */
public class TXCVodCacheMgr {

    /* renamed from: a */
    private static final String f5379a = "b";

    /* renamed from: b */
    private static TXCVodCacheMgr f5380b = new TXCVodCacheMgr();

    /* renamed from: c */
    private ArrayList<C3657a> f5381c;

    /* renamed from: d */
    private HashSet<C3657a> f5382d;

    /* renamed from: e */
    private String f5383e;

    /* renamed from: f */
    private int f5384f;

    /* renamed from: g */
    private String f5385g;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: TXCVodCacheMgr.java */
    /* renamed from: com.tencent.liteav.txcvodplayer.a.b$a */
    /* loaded from: classes3.dex */
    public static class C3657a implements Serializable {
        String fileType;
        String path;
        Long time;
        String url;

        C3657a() {
        }

        /* renamed from: a */
        public String m654a() {
            return this.url;
        }

        /* renamed from: a */
        public void m652a(String str) {
            this.url = str;
        }

        /* renamed from: b */
        public String m651b() {
            return this.path;
        }

        /* renamed from: b */
        public void m650b(String str) {
            this.path = str;
        }

        /* renamed from: c */
        public Long m649c() {
            return this.time;
        }

        /* renamed from: a */
        public void m653a(Long l) {
            this.time = l;
        }

        /* renamed from: c */
        public void m648c(String str) {
            this.fileType = str;
        }

        /* renamed from: d */
        public String m647d() {
            String str;
            if (this.fileType == null && (str = this.path) != null) {
                if (str.endsWith("mp4")) {
                    return "mp4";
                }
                if (this.path.endsWith("m3u8.sqlite")) {
                    return IjkMediaMeta.IJKM_KEY_M3U8;
                }
            }
            return this.fileType;
        }
    }

    /* renamed from: a */
    public static TXCVodCacheMgr m671a() {
        return f5380b;
    }

    /* renamed from: a */
    public void m669a(int i) {
        this.f5384f = i;
    }

    /* renamed from: a */
    public void m666a(String str) {
        this.f5385g = str;
    }

    /* renamed from: b */
    public void m661b(String str) {
        String concat;
        if (str.endsWith("/")) {
            concat = str.concat("txvodcache");
        } else {
            concat = str.concat("/txvodcache");
        }
        String str2 = this.f5383e;
        if (str2 == null || !str2.equals(concat)) {
            this.f5383e = concat;
            String str3 = this.f5383e;
            if (str3 == null) {
                return;
            }
            new File(str3).mkdirs();
            if (m663b()) {
                return;
            }
            m658d();
        }
    }

    /* renamed from: c */
    public TXCVodCacheInfo m659c(String str) {
        String str2 = this.f5383e;
        if (str2 != null && str != null) {
            File file = new File(str2);
            if (!file.mkdirs() && !file.isDirectory()) {
                return null;
            }
            Iterator<C3657a> it2 = this.f5381c.iterator();
            while (it2.hasNext()) {
                C3657a next = it2.next();
                if (next.url.equals(str)) {
                    m667a(next);
                    this.f5382d.add(next);
                    return new TXCVodCacheInfo(next.path, this.f5383e, next.fileType);
                }
            }
            Iterator<C3657a> it3 = this.f5381c.iterator();
            while (it3.hasNext() && this.f5381c.size() > this.f5384f) {
                C3657a next2 = it3.next();
                if (!this.f5382d.contains(next2)) {
                    m662b(next2);
                    it3.remove();
                }
            }
            C3657a m656e = m656e(str);
            if (m656e != null) {
                this.f5382d.add(m656e);
                return new TXCVodCacheInfo(m656e.path, this.f5383e, m656e.fileType);
            }
        }
        return null;
    }

    /* renamed from: d */
    public boolean m657d(String str) {
        Uri parse = Uri.parse(str);
        if (parse == null || parse.getPath() == null || parse.getScheme() == null || !parse.getScheme().startsWith("http")) {
            return false;
        }
        return parse.getPath().endsWith(".mp4") || parse.getPath().endsWith(IjkMediaMeta.IJKM_KEY_M3U8) || parse.getPath().endsWith(".MP4") || parse.getPath().endsWith("M3U8");
    }

    /* renamed from: b */
    boolean m663b() {
        this.f5381c = new ArrayList<>();
        this.f5382d = new HashSet<>();
        try {
            for (Node firstChild = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(this.f5383e + "/tx_cache.xml")).getElementsByTagName("caches").item(0).getFirstChild(); firstChild != null; firstChild = firstChild.getNextSibling()) {
                C3657a c3657a = new C3657a();
                for (Node firstChild2 = firstChild.getFirstChild(); firstChild2 != null; firstChild2 = firstChild2.getNextSibling()) {
                    if (firstChild2.getNodeName().equals("path")) {
                        c3657a.m650b(firstChild2.getFirstChild().getNodeValue());
                    } else if (firstChild2.getNodeName().equals(AopConstants.TIME_KEY)) {
                        c3657a.m653a(Long.valueOf(Long.parseLong(firstChild2.getFirstChild().getNodeValue())));
                    } else if (firstChild2.getNodeName().equals("url")) {
                        c3657a.m652a(firstChild2.getFirstChild().getNodeValue());
                    } else if (firstChild2.getNodeName().equals("fileType")) {
                        c3657a.m648c(firstChild2.getFirstChild().getNodeValue());
                    }
                }
                this.f5381c.add(c3657a);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    /* renamed from: c */
    void m660c() {
        try {
            Document newDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element createElement = newDocument.createElement("caches");
            newDocument.appendChild(createElement);
            Iterator<C3657a> it2 = this.f5381c.iterator();
            while (it2.hasNext()) {
                C3657a next = it2.next();
                Element createElement2 = newDocument.createElement("cache");
                createElement.appendChild(createElement2);
                Element createElement3 = newDocument.createElement("path");
                createElement3.appendChild(newDocument.createTextNode(next.m651b()));
                createElement2.appendChild(createElement3);
                Element createElement4 = newDocument.createElement(AopConstants.TIME_KEY);
                createElement4.appendChild(newDocument.createTextNode(next.m649c().toString()));
                createElement2.appendChild(createElement4);
                Element createElement5 = newDocument.createElement("url");
                createElement5.appendChild(newDocument.createTextNode(next.m654a()));
                createElement2.appendChild(createElement5);
                Element createElement6 = newDocument.createElement("fileType");
                createElement6.appendChild(newDocument.createTextNode(next.m647d()));
                createElement2.appendChild(createElement6);
            }
            Transformer newTransformer = TransformerFactory.newInstance().newTransformer();
            DOMSource dOMSource = new DOMSource(newDocument);
            StreamResult streamResult = new StreamResult();
            streamResult.setSystemId(new File(this.f5383e, "tx_cache.xml").getAbsolutePath());
            newTransformer.transform(dOMSource, streamResult);
            System.out.println("File saved!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* renamed from: a */
    void m667a(C3657a c3657a) {
        c3657a.time = Long.valueOf(System.currentTimeMillis());
        this.f5381c.remove(c3657a);
        this.f5381c.add(c3657a);
        m660c();
    }

    /* renamed from: e */
    C3657a m656e(String str) {
        C3657a c3657a = new C3657a();
        c3657a.url = str;
        c3657a.time = Long.valueOf(System.currentTimeMillis());
        String m655f = m655f(str);
        Uri parse = Uri.parse(str);
        if (parse.getPath().endsWith(".mp4") || parse.getPath().endsWith(".MP4")) {
            if (this.f5385g != null) {
                c3657a.m650b(m655f + "." + this.f5385g);
            } else {
                c3657a.m650b(m655f + ".mp4");
            }
            c3657a.m648c("mp4");
        } else if (!parse.getPath().endsWith(".m3u8") && !parse.getPath().endsWith(".M3U8")) {
            return null;
        } else {
            c3657a.m650b(m655f + ".m3u8.sqlite");
            c3657a.m648c(IjkMediaMeta.IJKM_KEY_M3U8);
        }
        this.f5381c.add(c3657a);
        m660c();
        return c3657a;
    }

    /* renamed from: f */
    public static String m655f(String str) {
        try {
            byte[] digest = MessageDigest.getInstance("MD5").digest(str.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder(digest.length * 2);
            for (byte b : digest) {
                int i = b & 255;
                if (i < 16) {
                    sb.append("0");
                }
                sb.append(Integer.toHexString(i));
            }
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        } catch (NoSuchAlgorithmException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    /* renamed from: d */
    private void m658d() {
        if (new File(this.f5383e).listFiles().length > 0) {
            String str = f5379a;
            Log.w(str, "!!!警告：TXVodPlayer缓存目录不为空 " + this.f5383e + "!!!");
        }
    }

    /* renamed from: a */
    private void m665a(String str, String str2) {
        String str3 = this.f5383e + "/" + str;
        new File(str3).delete();
        if (str2.equals("mp4")) {
            new File(str3.concat(".info")).delete();
        }
        Log.w(f5379a, "delete " + str3);
    }

    /* renamed from: b */
    private void m662b(C3657a c3657a) {
        m665a(c3657a.m651b(), c3657a.m647d());
    }

    /* renamed from: a */
    public void m664a(String str, boolean z) {
        Iterator<C3657a> it2 = this.f5382d.iterator();
        while (it2.hasNext()) {
            C3657a next = it2.next();
            if (next.m651b().equals(str)) {
                it2.remove();
                if (z) {
                    m662b(next);
                    this.f5381c.remove(next);
                    m660c();
                }
            }
        }
    }
}
