package com.p097ta.utdid2.p100b.p101a;

import com.p097ta.utdid2.p100b.p101a.AbstractC3211b;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.WeakHashMap;
import org.xmlpull.v1.XmlPullParserException;

/* renamed from: com.ta.utdid2.b.a.d */
/* loaded from: classes3.dex */
public class C3215d {

    /* renamed from: b */
    private static final Object f1925b = new Object();

    /* renamed from: a */
    private File f1926a;

    /* renamed from: a */
    private final Object f1927a = new Object();

    /* renamed from: a */
    private HashMap<File, C3216a> f1928a = new HashMap<>();

    public C3215d(String str) {
        if (str != null && str.length() > 0) {
            this.f1926a = new File(str);
            return;
        }
        throw new RuntimeException("Directory can not be empty");
    }

    /* renamed from: a */
    private File m3631a(File file, String str) {
        if (str.indexOf(File.separatorChar) < 0) {
            return new File(file, str);
        }
        throw new IllegalArgumentException("File " + str + " contains a path separator");
    }

    /* renamed from: a */
    private File m3634a() {
        File file;
        synchronized (this.f1927a) {
            file = this.f1926a;
        }
        return file;
    }

    /* renamed from: b */
    private File m3628b(String str) {
        File m3634a = m3634a();
        return m3631a(m3634a, str + ".xml");
    }

    /* JADX WARN: Code restructure failed: missing block: B:42:0x007b, code lost:
        if (r3 == null) goto L34;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x004f, code lost:
        if (r0 == null) goto L69;
     */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0086 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* renamed from: a */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public AbstractC3211b m3630a(String str, int i) {
        FileInputStream fileInputStream;
        FileInputStream fileInputStream2;
        FileInputStream fileInputStream3;
        File m3628b = m3628b(str);
        synchronized (f1925b) {
            C3216a c3216a = this.f1928a.get(m3628b);
            if (c3216a == null || c3216a.m3619d()) {
                File m3632a = m3632a(m3628b);
                if (m3632a.exists()) {
                    m3628b.delete();
                    m3632a.renameTo(m3628b);
                }
                HashMap hashMap = null;
                hashMap = null;
                hashMap = null;
                hashMap = null;
                hashMap = null;
                hashMap = null;
                FileInputStream fileInputStream4 = null;
                hashMap = null;
                if (m3628b.exists()) {
                    try {
                        try {
                            if (m3628b.canRead()) {
                                try {
                                    fileInputStream = new FileInputStream(m3628b);
                                } catch (XmlPullParserException unused) {
                                    fileInputStream = null;
                                } catch (Exception unused2) {
                                    fileInputStream = null;
                                }
                                try {
                                    hashMap = C3218e.m3610a(fileInputStream);
                                    fileInputStream.close();
                                } catch (XmlPullParserException unused3) {
                                    try {
                                        fileInputStream3 = new FileInputStream(m3628b);
                                        try {
                                            fileInputStream3.read(new byte[fileInputStream3.available()]);
                                        } catch (Exception unused4) {
                                        } catch (Throwable th) {
                                            th = th;
                                            fileInputStream2 = fileInputStream3;
                                            if (fileInputStream2 != null) {
                                                try {
                                                    fileInputStream2.close();
                                                } catch (Throwable unused5) {
                                                }
                                            }
                                            throw th;
                                        }
                                    } catch (Exception unused6) {
                                        fileInputStream3 = fileInputStream;
                                    } catch (Throwable th2) {
                                        th = th2;
                                        fileInputStream2 = fileInputStream;
                                    }
                                    try {
                                        fileInputStream3.close();
                                    } catch (Throwable unused7) {
                                    }
                                    if (fileInputStream3 != null) {
                                        fileInputStream3.close();
                                    }
                                    synchronized (f1925b) {
                                    }
                                } catch (Exception unused8) {
                                } catch (Throwable th3) {
                                    th = th3;
                                    fileInputStream4 = fileInputStream;
                                    if (fileInputStream4 != null) {
                                        try {
                                            fileInputStream4.close();
                                        } catch (Throwable unused9) {
                                        }
                                    }
                                    throw th;
                                }
                                fileInputStream.close();
                            }
                        } catch (Throwable unused10) {
                        }
                    } catch (Throwable th4) {
                        th = th4;
                    }
                }
                synchronized (f1925b) {
                    if (c3216a != null) {
                        c3216a.m3622a(hashMap);
                    } else {
                        c3216a = this.f1928a.get(m3628b);
                        if (c3216a == null) {
                            c3216a = new C3216a(m3628b, i, hashMap);
                            this.f1928a.put(m3628b, c3216a);
                        }
                    }
                }
                return c3216a;
            }
            return c3216a;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: a */
    public static File m3632a(File file) {
        return new File(file.getPath() + ".bak");
    }

    /* renamed from: com.ta.utdid2.b.a.d$a */
    /* loaded from: classes3.dex */
    private static final class C3216a implements AbstractC3211b {

        /* renamed from: c */
        private static final Object f1929c = new Object();

        /* renamed from: a */
        private Map f1930a;

        /* renamed from: a */
        private WeakHashMap<AbstractC3211b.AbstractC3213b, Object> f1931a;

        /* renamed from: b */
        private final File f1932b;

        /* renamed from: c */
        private final int f1933c;

        /* renamed from: c */
        private final File f1934c;

        /* renamed from: j */
        private boolean f1935j = false;

        C3216a(File file, int i, Map map) {
            this.f1932b = file;
            this.f1934c = C3215d.m3632a(file);
            this.f1933c = i;
            this.f1930a = map == null ? new HashMap() : map;
            this.f1931a = new WeakHashMap<>();
        }

        @Override // com.p097ta.utdid2.p100b.p101a.AbstractC3211b
        /* renamed from: b */
        public boolean mo3620b() {
            File file = this.f1932b;
            return file != null && new File(file.getAbsolutePath()).exists();
        }

        /* renamed from: a */
        public void m3621a(boolean z) {
            synchronized (this) {
                this.f1935j = z;
            }
        }

        /* renamed from: d */
        public boolean m3619d() {
            boolean z;
            synchronized (this) {
                z = this.f1935j;
            }
            return z;
        }

        /* renamed from: a */
        public void m3622a(Map map) {
            if (map != null) {
                synchronized (this) {
                    this.f1930a = map;
                }
            }
        }

        @Override // com.p097ta.utdid2.p100b.p101a.AbstractC3211b
        public Map<String, ?> getAll() {
            HashMap hashMap;
            synchronized (this) {
                hashMap = new HashMap(this.f1930a);
            }
            return hashMap;
        }

        @Override // com.p097ta.utdid2.p100b.p101a.AbstractC3211b
        public String getString(String str, String str2) {
            String str3;
            synchronized (this) {
                str3 = (String) this.f1930a.get(str);
                if (str3 == null) {
                    str3 = str2;
                }
            }
            return str3;
        }

        @Override // com.p097ta.utdid2.p100b.p101a.AbstractC3211b
        public long getLong(String str, long j) {
            synchronized (this) {
                Long l = (Long) this.f1930a.get(str);
                if (l != null) {
                    j = l.longValue();
                }
            }
            return j;
        }

        /* renamed from: com.ta.utdid2.b.a.d$a$a */
        /* loaded from: classes3.dex */
        public final class C3217a implements AbstractC3211b.AbstractC3212a {

            /* renamed from: b */
            private final Map<String, Object> f1937b = new HashMap();

            /* renamed from: k */
            private boolean f1938k = false;

            public C3217a() {
            }

            @Override // com.p097ta.utdid2.p100b.p101a.AbstractC3211b.AbstractC3212a
            /* renamed from: a */
            public AbstractC3211b.AbstractC3212a mo3613a(String str, String str2) {
                synchronized (this) {
                    this.f1937b.put(str, str2);
                }
                return this;
            }

            @Override // com.p097ta.utdid2.p100b.p101a.AbstractC3211b.AbstractC3212a
            /* renamed from: a */
            public AbstractC3211b.AbstractC3212a mo3615a(String str, int i) {
                synchronized (this) {
                    this.f1937b.put(str, Integer.valueOf(i));
                }
                return this;
            }

            @Override // com.p097ta.utdid2.p100b.p101a.AbstractC3211b.AbstractC3212a
            /* renamed from: a */
            public AbstractC3211b.AbstractC3212a mo3614a(String str, long j) {
                synchronized (this) {
                    this.f1937b.put(str, Long.valueOf(j));
                }
                return this;
            }

            @Override // com.p097ta.utdid2.p100b.p101a.AbstractC3211b.AbstractC3212a
            /* renamed from: a */
            public AbstractC3211b.AbstractC3212a mo3616a(String str, float f) {
                synchronized (this) {
                    this.f1937b.put(str, Float.valueOf(f));
                }
                return this;
            }

            @Override // com.p097ta.utdid2.p100b.p101a.AbstractC3211b.AbstractC3212a
            /* renamed from: a */
            public AbstractC3211b.AbstractC3212a mo3612a(String str, boolean z) {
                synchronized (this) {
                    this.f1937b.put(str, Boolean.valueOf(z));
                }
                return this;
            }

            @Override // com.p097ta.utdid2.p100b.p101a.AbstractC3211b.AbstractC3212a
            /* renamed from: a */
            public AbstractC3211b.AbstractC3212a mo3617a(String str) {
                synchronized (this) {
                    this.f1937b.put(str, this);
                }
                return this;
            }

            @Override // com.p097ta.utdid2.p100b.p101a.AbstractC3211b.AbstractC3212a
            /* renamed from: b */
            public AbstractC3211b.AbstractC3212a mo3611b() {
                synchronized (this) {
                    this.f1938k = true;
                }
                return this;
            }

            @Override // com.p097ta.utdid2.p100b.p101a.AbstractC3211b.AbstractC3212a
            public boolean commit() {
                boolean z;
                ArrayList arrayList;
                HashSet<AbstractC3211b.AbstractC3213b> hashSet;
                boolean m3618e;
                synchronized (C3215d.f1925b) {
                    z = C3216a.this.f1931a.size() > 0;
                    arrayList = null;
                    if (z) {
                        arrayList = new ArrayList();
                        hashSet = new HashSet(C3216a.this.f1931a.keySet());
                    } else {
                        hashSet = null;
                    }
                    synchronized (this) {
                        if (this.f1938k) {
                            C3216a.this.f1930a.clear();
                            this.f1938k = false;
                        }
                        for (Map.Entry<String, Object> entry : this.f1937b.entrySet()) {
                            String key = entry.getKey();
                            Object value = entry.getValue();
                            if (value == this) {
                                C3216a.this.f1930a.remove(key);
                            } else {
                                C3216a.this.f1930a.put(key, value);
                            }
                            if (z) {
                                arrayList.add(key);
                            }
                        }
                        this.f1937b.clear();
                    }
                    m3618e = C3216a.this.m3618e();
                    if (m3618e) {
                        C3216a.this.m3621a(true);
                    }
                }
                if (z) {
                    for (int size = arrayList.size() - 1; size >= 0; size--) {
                        String str = (String) arrayList.get(size);
                        for (AbstractC3211b.AbstractC3213b abstractC3213b : hashSet) {
                            if (abstractC3213b != null) {
                                abstractC3213b.m3641a(C3216a.this, str);
                            }
                        }
                    }
                }
                return m3618e;
            }
        }

        @Override // com.p097ta.utdid2.p100b.p101a.AbstractC3211b
        /* renamed from: a */
        public AbstractC3211b.AbstractC3212a mo3627a() {
            return new C3217a();
        }

        /* renamed from: a */
        private FileOutputStream m3623a(File file) {
            try {
                return new FileOutputStream(file);
            } catch (FileNotFoundException unused) {
                if (!file.getParentFile().mkdir()) {
                    return null;
                }
                try {
                    return new FileOutputStream(file);
                } catch (FileNotFoundException unused2) {
                    return null;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* renamed from: e */
        public boolean m3618e() {
            if (this.f1932b.exists()) {
                if (!this.f1934c.exists()) {
                    if (!this.f1932b.renameTo(this.f1934c)) {
                        return false;
                    }
                } else {
                    this.f1932b.delete();
                }
            }
            try {
                FileOutputStream m3623a = m3623a(this.f1932b);
                if (m3623a == null) {
                    return false;
                }
                C3218e.m3607a(this.f1930a, m3623a);
                m3623a.close();
                this.f1934c.delete();
                return true;
            } catch (Exception unused) {
                if (this.f1932b.exists()) {
                    this.f1932b.delete();
                }
                return false;
            }
        }
    }
}
