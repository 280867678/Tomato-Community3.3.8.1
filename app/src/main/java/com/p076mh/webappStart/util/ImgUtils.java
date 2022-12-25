package com.p076mh.webappStart.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import com.gen.p059mh.webapp_extensions.matisse.internal.entity.SelectionSpec;
import com.gen.p059mh.webapps.utils.Logger;
import com.p076mh.webappStart.android_plugin_impl.callback.CommonCallBack;
import com.p076mh.webappStart.util.bean.ImageInfo;
import com.p076mh.webappStart.util.thread.ThreadManager;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/* renamed from: com.mh.webappStart.util.ImgUtils */
/* loaded from: classes3.dex */
public class ImgUtils {
    public static boolean copyFile2Gallery(Context context, String str) throws ExecutionException, InterruptedException {
        File file = new File(str);
        File file2 = new File(Constants.GALLERY_PATH + File.separator + file.getName());
        if (copyFile(str, file2.getAbsolutePath())) {
            Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
            intent.setData(Uri.fromFile(file2));
            context.sendBroadcast(intent);
            return true;
        }
        return false;
    }

    public static boolean copyFile(final String str, final String str2) throws ExecutionException, InterruptedException {
        Logger.m4112i("CZ", "copyFile: oldPath = " + str + ",newPath = " + str2);
        return ((Boolean) ThreadManager.getInstance().submitTaskWithoutException(new Callable<Boolean>() { // from class: com.mh.webappStart.util.ImgUtils.1
            /* JADX WARN: Can't rename method to resolve collision */
            /* JADX WARN: Removed duplicated region for block: B:52:0x0094 A[EXC_TOP_SPLITTER, SYNTHETIC] */
            /* JADX WARN: Removed duplicated region for block: B:57:0x008a A[EXC_TOP_SPLITTER, SYNTHETIC] */
            @Override // java.util.concurrent.Callable
            /* renamed from: call */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public Boolean mo6347call() throws Exception {
                FileOutputStream fileOutputStream;
                FileInputStream fileInputStream = null;
                try {
                    if (new File(str).exists()) {
                        FileInputStream fileInputStream2 = new FileInputStream(str);
                        try {
                            FileUtils.createFile(str2);
                            fileOutputStream = new FileOutputStream(str2);
                            try {
                                byte[] bArr = new byte[1444];
                                while (true) {
                                    int read = fileInputStream2.read(bArr);
                                    if (read == -1) {
                                        break;
                                    }
                                    fileOutputStream.write(bArr, 0, read);
                                }
                                try {
                                    fileInputStream2.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    fileOutputStream.close();
                                } catch (IOException e2) {
                                    e2.printStackTrace();
                                }
                                return true;
                            } catch (Exception e3) {
                                e = e3;
                                fileInputStream = fileInputStream2;
                                try {
                                    System.out.println("复制单个文件操作出错");
                                    e.printStackTrace();
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
                                    return false;
                                } catch (Throwable th) {
                                    th = th;
                                    if (fileInputStream != null) {
                                        try {
                                            fileInputStream.close();
                                        } catch (IOException e6) {
                                            e6.printStackTrace();
                                        }
                                    }
                                    if (fileOutputStream != null) {
                                        try {
                                            fileOutputStream.close();
                                        } catch (IOException e7) {
                                            e7.printStackTrace();
                                        }
                                    }
                                    throw th;
                                }
                            } catch (Throwable th2) {
                                th = th2;
                                fileInputStream = fileInputStream2;
                                if (fileInputStream != null) {
                                }
                                if (fileOutputStream != null) {
                                }
                                throw th;
                            }
                        } catch (Exception e8) {
                            fileOutputStream = null;
                            fileInputStream = fileInputStream2;
                            e = e8;
                        } catch (Throwable th3) {
                            th = th3;
                            fileOutputStream = null;
                        }
                    } else {
                        return false;
                    }
                } catch (Exception e9) {
                    e = e9;
                    fileOutputStream = null;
                } catch (Throwable th4) {
                    th = th4;
                    fileOutputStream = null;
                }
            }
        }).get()).booleanValue();
    }

    public static boolean copyFileAndDeleteOldFile(final String str, final String str2) throws ExecutionException, InterruptedException {
        Logger.m4112i("CZ", "copyFile: oldPath = " + str + ",newPath = " + str2);
        return ((Boolean) ThreadManager.getInstance().submitTaskWithoutException(new Callable<Boolean>() { // from class: com.mh.webappStart.util.ImgUtils.2
            /* JADX WARN: Can't rename method to resolve collision */
            /* JADX WARN: Removed duplicated region for block: B:53:0x0093 A[EXC_TOP_SPLITTER, SYNTHETIC] */
            /* JADX WARN: Removed duplicated region for block: B:58:0x0089 A[EXC_TOP_SPLITTER, SYNTHETIC] */
            @Override // java.util.concurrent.Callable
            /* renamed from: call */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public Boolean mo6348call() throws Exception {
                FileOutputStream fileOutputStream;
                FileInputStream fileInputStream = null;
                try {
                    File file = new File(str);
                    if (file.exists()) {
                        FileInputStream fileInputStream2 = new FileInputStream(str);
                        try {
                            FileUtils.createFile(str2);
                            fileOutputStream = new FileOutputStream(str2);
                            try {
                                byte[] bArr = new byte[1444];
                                while (true) {
                                    int read = fileInputStream2.read(bArr);
                                    if (read == -1) {
                                        break;
                                    }
                                    fileOutputStream.write(bArr, 0, read);
                                }
                                file.delete();
                                try {
                                    fileInputStream2.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    fileOutputStream.close();
                                } catch (IOException e2) {
                                    e2.printStackTrace();
                                }
                                return true;
                            } catch (Exception e3) {
                                e = e3;
                                fileInputStream = fileInputStream2;
                                try {
                                    System.out.println("复制单个文件操作出错");
                                    e.printStackTrace();
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
                                    return false;
                                } catch (Throwable th) {
                                    th = th;
                                    if (fileInputStream != null) {
                                        try {
                                            fileInputStream.close();
                                        } catch (IOException e6) {
                                            e6.printStackTrace();
                                        }
                                    }
                                    if (fileOutputStream != null) {
                                        try {
                                            fileOutputStream.close();
                                        } catch (IOException e7) {
                                            e7.printStackTrace();
                                        }
                                    }
                                    throw th;
                                }
                            } catch (Throwable th2) {
                                th = th2;
                                fileInputStream = fileInputStream2;
                                if (fileInputStream != null) {
                                }
                                if (fileOutputStream != null) {
                                }
                                throw th;
                            }
                        } catch (Exception e8) {
                            e = e8;
                            fileOutputStream = null;
                        } catch (Throwable th3) {
                            th = th3;
                            fileOutputStream = null;
                        }
                    } else {
                        return false;
                    }
                } catch (Exception e9) {
                    e = e9;
                    fileOutputStream = null;
                } catch (Throwable th4) {
                    th = th4;
                    fileOutputStream = null;
                }
            }
        }).get()).booleanValue();
    }

    public static ImageInfo getLocalImageInfo(String str) {
        try {
            ImageInfo imageInfo = new ImageInfo();
            ExifInterface exifInterface = new ExifInterface(str);
            String attribute = exifInterface.getAttribute(android.support.media.ExifInterface.TAG_IMAGE_LENGTH);
            String attribute2 = exifInterface.getAttribute(android.support.media.ExifInterface.TAG_IMAGE_WIDTH);
            String attribute3 = exifInterface.getAttribute(android.support.media.ExifInterface.TAG_ORIENTATION);
            imageInfo.setWidth(Integer.parseInt(attribute2));
            imageInfo.setHeight(Integer.parseInt(attribute));
            imageInfo.setPath(str);
            imageInfo.setOrientation(attribute3);
            String[] split = str.split("\\.");
            imageInfo.setType(split[split.length - 1]);
            return imageInfo;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void getNetImageInfo(Context context, String str, final CommonCallBack<ImageInfo> commonCallBack) {
        SelectionSpec.getInstance().imageEngine.download(context, str, new CommonCallBack<File>() { // from class: com.mh.webappStart.util.ImgUtils.3
            @Override // com.p076mh.webappStart.android_plugin_impl.callback.CommonCallBack
            public void onResult(File file) {
                ImageInfo localImageInfo = ImgUtils.getLocalImageInfo(file.getAbsolutePath());
                CommonCallBack commonCallBack2 = CommonCallBack.this;
                if (commonCallBack2 != null) {
                    commonCallBack2.onResult(localImageInfo);
                }
            }

            @Override // com.p076mh.webappStart.android_plugin_impl.callback.CommonCallBack
            public void onFailure(Exception exc) {
                CommonCallBack commonCallBack2 = CommonCallBack.this;
                if (commonCallBack2 != null) {
                    commonCallBack2.onFailure(exc);
                }
            }
        });
    }

    public static boolean qualityCompress(final Bitmap bitmap, final File file, final int i) throws ExecutionException, InterruptedException {
        return ((Boolean) ThreadManager.getInstance().submitTaskWithoutException(new Callable() { // from class: com.mh.webappStart.util.ImgUtils.4
            @Override // java.util.concurrent.Callable
            public Object call() throws Exception {
                Throwable th;
                ByteArrayOutputStream byteArrayOutputStream;
                Exception e;
                FileOutputStream fileOutputStream;
                FileOutputStream fileOutputStream2 = null;
                try {
                    byteArrayOutputStream = new ByteArrayOutputStream();
                    try {
                        try {
                            bitmap.compress(Bitmap.CompressFormat.JPEG, i, byteArrayOutputStream);
                            FileUtils.createFile(file.getAbsolutePath());
                            fileOutputStream = new FileOutputStream(file);
                        } catch (Exception e2) {
                            e = e2;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                    }
                } catch (Exception e3) {
                    e = e3;
                    byteArrayOutputStream = null;
                } catch (Throwable th3) {
                    th = th3;
                    byteArrayOutputStream = null;
                }
                try {
                    fileOutputStream.write(byteArrayOutputStream.toByteArray());
                    fileOutputStream.flush();
                    try {
                        fileOutputStream.close();
                    } catch (IOException e4) {
                        e4.printStackTrace();
                    }
                    try {
                        byteArrayOutputStream.close();
                    } catch (IOException e5) {
                        e5.printStackTrace();
                    }
                    return true;
                } catch (Exception e6) {
                    e = e6;
                    fileOutputStream2 = fileOutputStream;
                    e.printStackTrace();
                    if (fileOutputStream2 != null) {
                        try {
                            fileOutputStream2.close();
                        } catch (IOException e7) {
                            e7.printStackTrace();
                        }
                    }
                    if (byteArrayOutputStream != null) {
                        try {
                            byteArrayOutputStream.close();
                        } catch (IOException e8) {
                            e8.printStackTrace();
                        }
                    }
                    return false;
                } catch (Throwable th4) {
                    th = th4;
                    fileOutputStream2 = fileOutputStream;
                    if (fileOutputStream2 != null) {
                        try {
                            fileOutputStream2.close();
                        } catch (IOException e9) {
                            e9.printStackTrace();
                        }
                    }
                    if (byteArrayOutputStream != null) {
                        try {
                            byteArrayOutputStream.close();
                        } catch (IOException e10) {
                            e10.printStackTrace();
                        }
                    }
                    throw th;
                }
            }
        }).get()).booleanValue();
    }

    public static Bitmap zoomImage(Bitmap bitmap, double d, double d2) {
        float width = bitmap.getWidth();
        float height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(((float) d) / width, ((float) d2) / height);
        return Bitmap.createBitmap(bitmap, 0, 0, (int) width, (int) height, matrix, true);
    }
}
