package com.gen.p059mh.webapp_extensions.unity;

import android.util.Base64;
import com.gen.p059mh.webapps.unity.Unity;
import com.gen.p059mh.webapps.utils.Logger;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.gen.mh.webapp_extensions.unity.FileSystem */
/* loaded from: classes2.dex */
public class FileSystem extends Unity {
    int size = 4096;
    Unity.Method createReadStream = new Unity.Method() { // from class: com.gen.mh.webapp_extensions.unity.FileSystem.1
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            File file = new File(FileSystem.this.getWebViewFragment().realPath(((ArrayList) objArr[0]).get(0).toString()));
            try {
                final FileInputStream fileInputStream = new FileInputStream(file);
                if (!file.exists()) {
                    HashMap hashMap = new HashMap();
                    hashMap.put("success", false);
                    hashMap.put("message", "can not find the target path");
                    methodCallback.run(hashMap);
                    return;
                }
                ReadableStream readableStream = new ReadableStream() { // from class: com.gen.mh.webapp_extensions.unity.FileSystem.1.1
                    @Override // com.gen.p059mh.webapp_extensions.unity.ReadableStream
                    public Map inputData() {
                        try {
                            byte[] bArr = new byte[FileSystem.this.size];
                            int read = fileInputStream.read(bArr, 0, FileSystem.this.size);
                            if (read != -1) {
                                String str = new String(bArr, 0, read);
                                HashMap hashMap2 = new HashMap();
                                hashMap2.put("success", true);
                                hashMap2.put("ret", Integer.valueOf(str.length()));
                                hashMap2.put("content", Base64.encodeToString(str.getBytes(), 0));
                                return hashMap2;
                            }
                            HashMap hashMap3 = new HashMap();
                            hashMap3.put("success", true);
                            hashMap3.put("ret", 0);
                            hashMap3.put("content", null);
                            complete();
                            return hashMap3;
                        } catch (IOException e) {
                            e.printStackTrace();
                            return null;
                        }
                    }

                    @Override // com.gen.p059mh.webapp_extensions.unity.ReadableStream
                    public void complete() {
                        try {
                            fileInputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Logger.m4113i("complete");
                    }
                };
                readableStream.setWebViewFragment(FileSystem.this.getWebViewFragment());
                readableStream.setExecutor(FileSystem.this.getExecutor());
                readableStream.onInitialize(null);
                HashMap hashMap2 = new HashMap();
                hashMap2.put("success", true);
                hashMap2.put("result", readableStream);
                methodCallback.run(hashMap2);
            } catch (Exception e) {
                e.printStackTrace();
                HashMap hashMap3 = new HashMap();
                hashMap3.put("success", false);
                hashMap3.put("message", "can not find the target path");
                methodCallback.run(hashMap3);
            }
        }
    };
    Unity.Method createWriteStream = new Unity.Method() { // from class: com.gen.mh.webapp_extensions.unity.FileSystem.2
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            File file = new File(FileSystem.this.getWebViewFragment().realPath(((ArrayList) objArr[0]).get(0).toString()));
            try {
                final FileOutputStream fileOutputStream = new FileOutputStream(file, true);
                if (!file.exists()) {
                    HashMap hashMap = new HashMap();
                    hashMap.put("success", false);
                    hashMap.put("message", "can not find the target path");
                    methodCallback.run(hashMap);
                    return;
                }
                WritableStream writableStream = new WritableStream() { // from class: com.gen.mh.webapp_extensions.unity.FileSystem.2.1
                    @Override // com.gen.p059mh.webapp_extensions.unity.WritableStream
                    public int onWriteData(byte[] bArr) {
                        try {
                            fileOutputStream.write(bArr);
                            return bArr.length;
                        } catch (IOException e) {
                            e.printStackTrace();
                            return -1;
                        }
                    }

                    @Override // com.gen.p059mh.webapp_extensions.unity.WritableStream
                    public void onFinish() {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                };
                writableStream.setWebViewFragment(FileSystem.this.getWebViewFragment());
                writableStream.setExecutor(FileSystem.this.getExecutor());
                writableStream.onInitialize(null);
                HashMap hashMap2 = new HashMap();
                hashMap2.put("success", true);
                hashMap2.put("result", writableStream);
                methodCallback.run(hashMap2);
            } catch (Exception e) {
                e.printStackTrace();
                HashMap hashMap3 = new HashMap();
                hashMap3.put("success", false);
                hashMap3.put("message", "can not find the target path");
                methodCallback.run(hashMap3);
            }
        }
    };

    public FileSystem() {
        registerMethod("createReadStream", this.createReadStream);
        registerMethod("createWriteStream", this.createWriteStream);
    }
}
