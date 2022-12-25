package com.github.amr.mimetypes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class MimeTypes {
    private static MimeTypes singleton;
    private static final Object singletonMonitor = new Object();
    private final Map<String, MimeType> mimeTypes = new HashMap();
    private final Map<String, MimeType> extensions = new HashMap();

    public MimeTypes() {
        load(getDefaultMimeTypesDefinition());
    }

    public static InputStream getDefaultMimeTypesDefinition() {
        InputStream resourceAsStream = MimeTypes.class.getClassLoader().getResourceAsStream("mime.types");
        if (resourceAsStream != null) {
            return resourceAsStream;
        }
        throw new IllegalStateException("Could not find the built-in mime.types definition file");
    }

    public static MimeTypes getInstance() {
        if (singleton == null) {
            synchronized (singletonMonitor) {
                if (singleton == null) {
                    singleton = new MimeTypes();
                }
            }
        }
        return singleton;
    }

    public MimeTypes load(InputStream inputStream) {
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            while (true) {
                try {
                    String readLine = bufferedReader.readLine();
                    if (readLine != null) {
                        loadOne(readLine);
                    } else {
                        bufferedReader.close();
                        inputStreamReader.close();
                        return this;
                    }
                } catch (Throwable th) {
                    try {
                        throw th;
                    } catch (Throwable th2) {
                        try {
                            bufferedReader.close();
                        } catch (Throwable th3) {
                            th.addSuppressed(th3);
                        }
                        throw th2;
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public MimeTypes loadOne(String str) {
        if (!str.trim().isEmpty() && !str.startsWith("#")) {
            String[] split = str.toLowerCase().split("\\s", 2);
            if (split.length >= 2) {
                register(new MimeType(split[0], split[1].trim().split("\\s")));
            } else if (split.length == 1) {
                register(new MimeType(split[0], new String[0]));
            }
        }
        return this;
    }

    public MimeTypes register(MimeType mimeType) {
        this.mimeTypes.put(mimeType.getMimeType(), mimeType);
        for (String str : mimeType.getExtensions()) {
            this.extensions.put(str, mimeType);
        }
        return this;
    }

    public MimeType getByExtension(String str) {
        return this.extensions.get(str);
    }
}
